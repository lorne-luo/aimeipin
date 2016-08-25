package com.meidi.controller.weixin;

import com.meidi.domain.IndexImage;
import com.meidi.domain.Others;
import com.meidi.domain.User;
import com.meidi.repository.IndexImageRepository;
import com.meidi.repository.OthersRepository;
import com.meidi.repository.UserRepository;
import com.meidi.repository.WxTicketRepository;
import com.meidi.util.MdCommon;
import com.meidi.util.MdModel;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by luanpeng on 16/3/22.
 */
@Controller
@RequestMapping("/")
public class IndexController extends WxBaseController {

    @Resource
    private UserRepository userRepository;
    @Resource
    private IndexImageRepository indexImageRepository;
    @Resource
    private OthersRepository othersRepository;
    @Resource
    private WxTicketRepository wxTicketRepository;



    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request){

        return new ModelAndView(new RedirectView(PATH + "/index"));
    }


    /**
     * 首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView indexPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);

        //网页签名
        Map signature = null;
        try {
            signature = weiXinSignature(request, wxTicketRepository);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.put("signature", signature);


        Sort sort = new Sort(Sort.Direction.ASC, "weight").and(new Sort(Sort.Direction.ASC, "createTime"));
        Iterable<IndexImage> indexImages = indexImageRepository.findAll(sort);
        model.put("images", indexImages);
        model.put("pageActive", "index");
        return new ModelAndView("weixin/index", model);
    }

    /**
     * 分类页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "class", method = RequestMethod.GET)
    public ModelAndView classPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        model.put("pageActive", "class");
        return new ModelAndView("weixin/classification", model);
    }

    /**
     * 咨询页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "consult", method = RequestMethod.GET)
    public ModelAndView consultPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("wx_openid"))) {
//            return wxAuth(request);
        }
        model.put("pageActive", "consult");
        return new ModelAndView("weixin/consult", model);
    }

    /**
     * 我的页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "my", method = RequestMethod.GET)
    public ModelAndView myPage(HttpServletRequest request) throws UnsupportedEncodingException {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("wx_openid"))) {
//            return wxAuth(request);
        }
        User user = userRepository.findByWxOpenid(MdCommon.null2String(model.get("wx_openid")));
        model.put("user", user);

        List<Others> othersList = othersRepository.findByFlag(1);
        model.put("othersList", othersList);

        model.put("pageActive", "my");
        return new ModelAndView("weixin/my", model);
    }

}
