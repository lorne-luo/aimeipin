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
            String sex = MdCommon.null2String(ret.get("sex"));;
            String province = new String(MdCommon.null2String(ret.get("province")).getBytes("ISO-8859-1"), "utf8");
            String city = new String(MdCommon.null2String(ret.get("city")).getBytes("ISO-8859-1"), "utf8");
            String country = new String(MdCommon.null2String(ret.get("country")).getBytes("ISO-8859-1"), "utf8");
            String unionid = MdCommon.null2String(ret.getOrDefault("unionid",null));

            System.out.println("nickname======" + nickname);
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
        return new ModelAndView(new RedirectView(url));
    }
}
