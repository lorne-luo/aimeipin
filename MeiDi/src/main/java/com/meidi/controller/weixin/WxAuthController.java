package com.meidi.controller.weixin;

import com.meidi.domain.User;
import com.meidi.repository.UserRepository;
import com.meidi.util.MdCommon;
import com.meidi.util.MdConstants;
import com.meidi.util.UserSession;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Map;

/**
 * Created by luanpeng on 16/3/31.
 */
@Controller
@RequestMapping("/wxAuth")
public class WxAuthController implements MdConstants {

    @Resource
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView wxAuth(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        System.out.println("code=" + code);
        try {
            String wx_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + MdConstants.WX_APP_ID + "&secret=" + MdConstants.WX_SECRET + "&code=" +
                    code + "&grant_type=authorization_code";
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(wx_url));
            HttpResponse httpResponse = client.execute(httpGet);
            String returnStr = EntityUtils.toString(httpResponse.getEntity());

            Map ret = MdCommon.json2Map(returnStr);
            String token = MdCommon.null2String(ret.get("access_token"));
            String openid = MdCommon.null2String(ret.get("openid"));
            System.out.println("openid=" + openid);

            wx_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid + "&lang=zh_CN";
            httpGet.setURI(new URI(wx_url));
            httpResponse = client.execute(httpGet);
            returnStr = EntityUtils.toString(httpResponse.getEntity());
            ret = MdCommon.json2MapPlus(returnStr);

//            String nickname = new String(MdCommon.null2String(ret.get("nickname")).getBytes("ISO-8859-1"), "utf-8");
//            String nickname = MdCommon.nicknameEscape(MdCommon.null2String(ret.get("nickname")));
            String nickname = new String(MdCommon.null2String(ret.get("nickname")).getBytes("ISO-8859-1"), "utf8");
            String sex = MdCommon.null2String(ret.get("sex"));
            String province = new String(MdCommon.null2String(ret.get("province")).getBytes("ISO-8859-1"), "utf8");
            String city = new String(MdCommon.null2String(ret.get("city")).getBytes("ISO-8859-1"), "utf8");
            String country = new String(MdCommon.null2String(ret.get("country")).getBytes("ISO-8859-1"), "utf8");
            String unionid = MdCommon.null2String(ret.getOrDefault("unionid",null));

            System.out.println("nickname======" + nickname+ "  unionid=" + unionid);
            String photo = MdCommon.null2String(ret.get("headimgurl"));
            User user = userRepository.findByWxOpenid(openid);
            if(MdCommon.isEmpty(user)){
                user = new User();
                user.setWxOpenid(openid);
            }
            user.setHeadimgurl(photo);
            user.setNickname(nickname);
            user.setGender(Integer.parseInt(sex));
            userRepository.save(user);

            UserSession userSession = new UserSession();
            userSession.setWx_openid(openid);
            session.setAttribute(USER_SESSION, userSession);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = (String) session.getAttribute(USER_URL);
        System.out.println("url=" + url);
        session.setAttribute(USER_URL, null);
        if (url == null || url.equals("")) url = HOME;

        return new ModelAndView(new RedirectView(url));
    }

    /**
     * 第一次静默授权商城公号,获取商城公号openid,然后发起对打卡公号的授权
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/dkAuth", method = RequestMethod.GET)
    public ModelAndView dkAuth(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        System.out.println("code=" + code);
        try {
            // 第一次对商城公号发起静默授权,只为获取商城公号的openid
            String wx_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + MdConstants.WX_APP_ID + "&secret=" + MdConstants.WX_SECRET + "&code=" +
                    code + "&grant_type=authorization_code";
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(wx_url));
            HttpResponse httpResponse = client.execute(httpGet);
            String returnStr = EntityUtils.toString(httpResponse.getEntity());

            Map ret = MdCommon.json2Map(returnStr);
            String openid = MdCommon.null2String(ret.get("openid"));

            // 以商城openid创建空用户
            User user = userRepository.findByWxOpenid(openid);
            if(MdCommon.isEmpty(user)){
                user = new User();
                user.setWxOpenid(openid);
                user.setNickname(openid);
                userRepository.save(user);
            }

            UserSession userSession = new UserSession();
            userSession.setWx_openid(openid); // 储存商城公号openid
            session.setAttribute(USER_SESSION, userSession);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 发起第二次对打卡公号的snsapi_userinfo类型授权
        return new ModelAndView(new RedirectView("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WX_DK_APP_ID + "&"
                + "redirect_uri=" + HOME + "/wxAuth/dkAuth/userinfo&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"));
    }

    @RequestMapping(value = "/dkAuth/userinfo", method = RequestMethod.GET)
    public ModelAndView dkAuthUserinfo(HttpServletRequest request) throws NoSuchRequestHandlingMethodException {
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute(USER_SESSION);
        // 第一次授权获取的商城公号openid
        String mallOpenid = userSession.getWx_openid();
        String code = request.getParameter("code");

        try {
            String wx_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + MdConstants.WX_DK_APP_ID + "&secret=" + MdConstants.WX_DK_SECRET + "&code=" +
                    code + "&grant_type=authorization_code";
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(wx_url));
            HttpResponse httpResponse = client.execute(httpGet);
            String returnStr = EntityUtils.toString(httpResponse.getEntity());

            Map ret = MdCommon.json2Map(returnStr);
            String token = MdCommon.null2String(ret.get("access_token"));
            String openid = MdCommon.null2String(ret.get("openid"));
            System.out.println("openid=" + openid);

            wx_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid + "&lang=zh_CN";
            httpGet.setURI(new URI(wx_url));
            httpResponse = client.execute(httpGet);
            returnStr = EntityUtils.toString(httpResponse.getEntity());
            ret = MdCommon.json2MapPlus(returnStr);

            //获取打卡公号用户详细信息
            String nickname = new String(MdCommon.null2String(ret.get("nickname")).getBytes("ISO-8859-1"), "utf8");
            String sex = MdCommon.null2String(ret.get("sex"));
            String unionid = MdCommon.null2String(ret.getOrDefault("unionid",null));
            String photo = MdCommon.null2String(ret.get("headimgurl"));

            // 用第二次打卡公号授权登录拿到的userinfo更新user
            User user = userRepository.findByWxOpenid(mallOpenid);
            if(MdCommon.isEmpty(user)){
                user = new User();
                user.setWxOpenid(mallOpenid);
            }
            user.setHeadimgurl(photo);
            user.setNickname(nickname);
            user.setGender(Integer.parseInt(sex));
            userRepository.save(user);
            userSession.setBooking_wx_openid(openid); // 储存下单公众号openid
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.setAttribute(USER_SESSION, userSession);
        String url = (String) session.getAttribute(USER_URL);
        System.out.println("dakaurl=" + url);
        session.setAttribute(USER_URL, null);
        if (url == null || url.equals("")){
            throw new NoSuchRequestHandlingMethodException("bookingDaka", WxAuthController.class);
        }

        return new ModelAndView(new RedirectView(url));
    }
}
