package com.meidi.controller.weixin;

import com.meidi.domain.*;
import com.meidi.repository.*;
import com.meidi.util.MdCommon;
import com.meidi.util.MdModel;
import com.meidi.util.WxTemplate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by luanpeng on 16/3/30.
 */
@Controller
@RequestMapping("/business")
public class BusinessController extends WxBaseController {

    @Resource
    private UserFavoriteRepository userFavoriteRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private GroupLaunchUserRepository groupLaunchUserRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private GroupLaunchRepository groupLaunchRepository;
    @Resource
    private OthersRepository othersRepository;
    @Resource
    private WxTicketRepository wxTicketRepository;

    @Resource
    private CommodityRepository commodityRepository;
    @Resource
    private CommodityPhotoRepository commodityPhotoRepository;
    @Resource
    private BuyNoticeRepository buyNoticeRepository;
    @Resource
    private UserIntegralRepository userIntegralRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView wxLogin(HttpServletRequest request) {
        String url = request.getHeader("referer");
        if (url == null || url == "" || !url.contains(DOMAIN))
            url = HOME;
        HttpSession session = request.getSession();
        session.setAttribute(USER_URL, url);

        return new ModelAndView(new RedirectView("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WX_APP_ID + "&"
                + "redirect_uri=" + HOME + "/wxAuth/&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"));
    }

    /**
     * 获取项目列表
     *
     * @param request
     * @param pageNumber
     * @param categoryId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCommodityList", method = RequestMethod.POST)
    public Map getCommodityList(HttpServletRequest request,
                                @RequestParam(value = "page") Integer pageNumber,
                                @RequestParam(value = "categoryId") Integer categoryId,
                                @RequestParam(value = "state") Integer state,
                                @RequestParam(value = "cityId") Integer cityId,
                                @RequestParam(value = "queryStr") String queryStr) {

        MdModel model = new MdModel(request);
        try {
            Map<String, Object> result = commodityRepository.findCommodityWithQuery3(pageNumber, WX_PAGE_SIZE, categoryId, state, cityId, queryStr);
            List<Commodity> commodityList = (List<Commodity>) result.get("commodityList");
            for (Commodity commodity : commodityList) {
                List<CommodityPhoto> commodityPhotoList = commodityPhotoRepository.findByCommodityId(commodity.getId());
                commodity.setCommodityPhotoList(commodityPhotoList);
            }
            model.put("commodityList", commodityList);
            model.put("pageCount", result.get("totalPages"));
            model.put("pageNumber", pageNumber);
            model.put("pageSize", WX_PAGE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }


    /**
     * 项目详情页
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/commodityDetailPage/{id}", method = RequestMethod.GET)
    public ModelAndView groupDetailPage(HttpServletRequest request,
                                        @PathVariable Integer id) {
        MdModel model = new MdModel(request);

        //网页签名
        Map signature = null;
        try {
            signature = weiXinSignature(request, wxTicketRepository);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.put("signature", signature);



        Commodity commodity = commodityRepository.findOne(id);

        UserFavorite userFavorite = userFavoriteRepository.findByWxOpenidAndCommodityId(model.get("wx_openid"), id);
        if (MdCommon.isEmpty(userFavorite)) {
            model.put("favoriteFlag", 1);//未收藏
        } else {
            model.put("favoriteFlag", 2);//以收藏
        }

        BuyNotice buyNotice = buyNoticeRepository.findByFlag(commodity.getFlag());
        model.put("buyNotice", buyNotice);

        model.put("commodity", commodity);

        return new ModelAndView("weixin/commodityDetail", model);
    }


    /**
     * 预定页面
     *
     * @param request
     * @param id      如果是参团 则此时ID 就是grouplaunch 的ID
     * @param flag    1 拼团专享支付（发起拼团）  2 拼团一人支付  3普通(特惠 福袋)支付  4参团支付
     * @return
     */
    @RequestMapping(value = "/bookingPage/{id}/{flag}", method = RequestMethod.GET)
    public ModelAndView bookingPage(HttpServletRequest request,
                                    @PathVariable Integer id,
                                    @PathVariable Integer flag) {
        MdModel model = new MdModel(request);
        try {
            //网页授权
            if (MdCommon.isEmpty(model.get("wx_openid"))) {
                return wxAuth(request);
            }
            model.put("flag", flag);
            Commodity commodity = null;

            if (flag == 4) {//参团支付 单独处理
                //id 是 GroupLaunch id
                Integer launchId = id;
                GroupLaunch groupLaunch = groupLaunchRepository.findOne(launchId);
                if(MdCommon.isEmpty(groupLaunch)) {
                    model.put("groupLaunch", groupLaunch);

                    commodity = commodityRepository.findOne(groupLaunch.getCommodityId());

                    List<Order> unpaidOrderList = orderRepository.findByWxOpenidAndCommodityIdAndBookingFlagAndStateOrderByCreateTimeDesc(
                            MdCommon.null2String(model.get("wx_openid")), commodity.getId(), flag, 1);
                    Order unpaidOrder = unpaidOrderList.get(0);
                    if (MdCommon.isEmpty(unpaidOrder)) {
                        // 没有待支付的本商品订单,可以参团
                        model.put("commodity", commodity);
                        return new ModelAndView("weixin/joinGroupPayment", model);
                    } else {
                        // 本商品已存在未支付订单, 转向该订单支付页面
                        return new ModelAndView(new RedirectView(PATH + "/pay/orderPage/" + unpaidOrder.getId().toString()));
                    }
                }
            } else {
                //id 是 商品 id
                Integer commodityId = id;
                commodity = commodityRepository.findOne(commodityId);
                List<Order> unpaidOrderList = orderRepository.findByWxOpenidAndCommodityIdAndBookingFlagAndStateOrderByCreateTimeDesc(
                        MdCommon.null2String(model.get("wx_openid")), commodity.getId(), flag, 1);
                Order unpaidOrder = unpaidOrderList.get(0);

                if (!MdCommon.isEmpty(unpaidOrder)){
                    // 本商品已存在未支付订单, 转向该订单支付页面
                    return new ModelAndView(new RedirectView(PATH + "/pay/orderPage/" + unpaidOrder.getId().toString()));
                }
                model.put("commodity", commodity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("weixin/bookingPage", model);

    }


    /**
     * 生成订单
     *
     * @param request
     * @param commodityId
     * @param username
     * @param mobile
     * @param reservationCount 预定数量
     * @param flag             预订类型 1 发起拼团预订 2 拼团一人预订  3普通预订 4参团预订
     * @param launchId         表示当前参加的拼团ID  不是参团则为0
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public Map insertOrder(HttpServletRequest request,
                           @RequestParam(value = "commodityId") Integer commodityId,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "mobile") String mobile,
                           @RequestParam(value = "reservationCount") Integer reservationCount,
                           @RequestParam(value = "flag") Integer flag,
                           @RequestParam(value = "launchId") Integer launchId,
                           @RequestParam(value = "remarks") String remarks,
                           @RequestParam(value = "weixin") String weixin) {
        MdModel model = new MdModel(request);
        int ret = 0;
        if (MdCommon.isEmpty(commodityId)) {
            ret = -1;
        } else if (MdCommon.isEmpty(username)) {
            ret = -2;
        } else if (!MdCommon.isMobile(mobile)) {
            ret = -3;
        } else if (MdCommon.isEmpty(reservationCount)) {
            ret = -4;
        } else {

            Commodity commodity = commodityRepository.findOne(commodityId);

            int commodityNumber = commodity.getCommodityNumber();//总量
            int sold = commodity.getSold();//销量


            if(flag == 4){

            }

            //参团情况 不考虑库存
            if(commodityNumber == 0 && flag != 4) {
                ret = -5;//商品剩余库存不足
            }else if(commodityNumber > 0 && reservationCount > commodityNumber && flag != 4){
                ret = -5;//商品剩余库存不足
            }else{// -1 为不限量
                //不限量 或者 剩余商品总量大于等于预订数量
//                if (commodityNumber == -1  || reservationCount <= commodityNumber ) {
                    Order order = new Order();
                    order.setCommodityId(commodityId);
                    order.setWxOpenid(model.get("wx_openid"));
                    order.setMobile(mobile);
                    order.setUsername(username);
                    order.setRemarks(remarks);
                    order.setCommodityName(commodity.getName());
                    order.setDicProvince(commodity.getDicProvince());
                    order.setDicCity(commodity.getDicCity());
                    order.setFlag(commodity.getFlag());
                    order.setBookingFlag(flag);//设置预订类型
                    order.setCommodityNumber(reservationCount);//预定数量
                    order.setCreateTime(new Date());
                    order.setPeopleNumber(commodity.getPeopleNumber());
                    if(!MdCommon.isEmpty(weixin)){
                        order.setWeixin(weixin);
                    }
                    order.setUnitPrice(commodity.getPrice());//单价
                    if(!MdCommon.isEmpty(commodity.getUnit())){
                        order.setUnit(commodity.getUnit());//单位
                    }


                    if (commodity.getFlag() == 4) {
                        order.setPayAmount(commodity.getDiscountPrice());//实付金额= 折扣价
                    } else {
                        order.setPayAmount(commodity.getDeposit() * reservationCount);//实付金额= 订金＊预定数量
                    }

                    if (flag == 1 || flag == 3 || flag == 4) {
                        if (commodity.getFlag() == 4) {//咨询订单
                            order.setDiscount(commodity.getDiscount());//订单折扣
                            order.setDiscountPrice(commodity.getDiscountPrice());//订单折后总额＝ 折后价
                            order.setPrice(commodity.getPrice());//订单总额＝ 原价
                        } else {
                            order.setDiscount(commodity.getDiscount());//订单折扣
                            order.setDiscountPrice(commodity.getDiscountPrice() * reservationCount);//订单折后总额＝ 折后价＊预定数量
                            order.setPrice(commodity.getPrice() * reservationCount);//订单总额＝ 原价＊预定数量
                        }
                    } else if (flag == 2) {//团购一人优惠
                        //团购一人支付已经是优惠价 所以不在有折扣
                        order.setDiscount(null);
                        order.setPrice(commodity.getPrice() * reservationCount);//订单总额＝ 原价＊预定数量
                        order.setDiscountPrice(commodity.getAlonePrice() * reservationCount);//订单折后总额＝ 一人价 ＊ 预定数量

                    }


                    if (flag == 4) {//参团订单
                        order.setLaunchId(launchId);
                    }else{
                        //非参团订单 则改变数量
                        if(commodityNumber > 0){
                            commodity.setCommodityNumber(commodityNumber - reservationCount);
                        }

                        commodity.setSold(sold + reservationCount);
                        commodityRepository.save(commodity);
                    }

                    order = orderRepository.save(order);
                    model.put("orderId", order.getId());
//                } else {
//                    ret = -5;//商品剩余库存不足
//                }
            }
        }
        model.put("ret", ret);

        return model;
    }


    /**
     * 获取当前项目 获取正在被发起的拼团
     *
     * @param request
     * @param commodityId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getGroupLaunch", method = RequestMethod.POST)
    public Map getGroupLaunch(HttpServletRequest request,
                              @RequestParam(value = "commodityId") Integer commodityId) {
        MdModel model = new MdModel(request);
        //获取当前项目 已经被发起的拼团
        List<GroupLaunch> groupLaunchList = groupLaunchRepository.findByCommodityIdAndStateAndEndTimeIsAfter(commodityId, 0, new Date());
        List<Map> result = new ArrayList<>();
        for (GroupLaunch groupLaunch : groupLaunchList) {

            Map launch = new HashMap<>();
            launch.put("groupLaunch", groupLaunch);
            for (GroupLaunchUser groupLaunchUser : groupLaunch.getGroupLaunchUserList()) {
                if (groupLaunchUser.getFlag() == 1) {
                    User user = userRepository.findByWxOpenid(groupLaunchUser.getWxOpenid());
                    launch.put("headimgurl", user.getHeadimgurl());
                    launch.put("nickname", user.getNickname());
                }
            }
            result.add(launch);
        }

        model.put("result", result);
        return model;
    }


    /**
     * 检查参团
     *
     * @param request
     * @param launchId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkJoinGroup", method = RequestMethod.POST)
    public Map checkJoinGroup(HttpServletRequest request,
                              @RequestParam(value = "launchId") Integer launchId) {
        MdModel model = new MdModel(request);
        int ret = 0;
        GroupLaunch groupLaunch = groupLaunchRepository.findByIdAndGroupLaunchUserList_WxOpenid(launchId, model.get("wx_openid"));
        if (!MdCommon.isEmpty(groupLaunch)) {
            ret = -1;//已在此团
        } else {
            groupLaunch = groupLaunchRepository.findOne(launchId);
            if (groupLaunch.getState() == 0) {//拼团未结束
                Date date = new Date();
                if (groupLaunch.getEndTime().getTime() <= date.getTime()) {//团购时间结束
                    ret = -2;
                } else if (groupLaunch.getGroupLaunchUserList().size() >= groupLaunch.getPeopleNumber()) {
                    ret = -3;//拼团人数已满
                }
            } else {
                ret = -4;//拼团已结束
            }
        }
        model.put("ret", ret);
        return model;
    }

    /**
     * 参团页面
     *
     * @param request
     * @param launchId
     * @return
     */
    @RequestMapping(value = "/joinGroupPage/{launchId}", method = RequestMethod.GET)
    public ModelAndView joinGroupPage(HttpServletRequest request,
                                      @PathVariable Integer launchId) {
        MdModel model = new MdModel(request);

        if (MdCommon.isEmpty(model.get("wx_openid"))) {
            return wxAuth(request);
        }
        //网页签名
        Map signature = null;
        try {
            signature = weiXinSignature(request, wxTicketRepository);
            model.put("signature", signature);
        } catch (Exception e) {
            e.printStackTrace();
        }


        GroupLaunch groupLaunch = groupLaunchRepository.findOne(launchId);
        model.put("endTime", groupLaunch.getEndTime().getTime());

        List<GroupLaunchUser> groupLaunchUserList = groupLaunch.getGroupLaunchUserList();
        List<Map> userList = new ArrayList<>();
        for (GroupLaunchUser groupLaunchUser : groupLaunchUserList) {
            Map userMap = new HashMap<>();
            userMap.put("groupLaunchUser", groupLaunchUser);
            User user = userRepository.findByWxOpenid(groupLaunchUser.getWxOpenid());
            userMap.put("user", user);

            if(user.getWxOpenid().equals(model.get("wx_openid"))){//显示分享提示
                model.put("shareFlag",1);  //1 表示在团中
            }

            userList.add(userMap);
        }

        Commodity commodity = commodityRepository.findOne(groupLaunch.getCommodityId());
        model.put("commodity", commodity);

        model.put("groupLaunch", groupLaunch);
        model.put("userList", userList);

        return new ModelAndView("weixin/joinGroup", model);
    }


    /**
     * 获取非当前拼团项目相同地区的项目
     *
     * @param request
     * @param cityId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getYouLikeCommodity", method = RequestMethod.POST)
    public Map getYouLikeCommodity(HttpServletRequest request,
                                   @RequestParam(value = "cityId") Integer cityId,
                                   @RequestParam(value = "commodityId") Integer commodityId) {
        MdModel model = new MdModel(request);

        Commodity commodity=commodityRepository.findOne(commodityId);
        List<Commodity> commodityList;

        if (commodity.getCategory()!=null){
            commodityList = commodityRepository.findByDicCity_IdAndCategory_IdAndStateAndIdIsNot(
                    cityId, commodity.getCategory().getId(), 1, commodityId, MdCommon.buildPageRequest(1, 3, SORT_BY.ID_DESC));
        }else{
            commodityList = commodityRepository.findByDicCity_IdAndStateAndIdIsNot(cityId, 1, commodityId, MdCommon.buildPageRequest(1, 3, SORT_BY.ID_DESC));
        }

        model.put("commodityList", commodityList);
        return model;
    }

    /**
     * 我的订单页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myOrderPage", method = RequestMethod.GET)
    public ModelAndView myOrderPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("wx_openid"))) {
            return wxAuth(request);
        }

        return new ModelAndView("weixin/myOrder", model);
    }

    /**
     * 获取我的订单
     *
     * @param request
     * @param pageNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMyOrderList", method = RequestMethod.POST)
    public Map getMyOrderList(HttpServletRequest request,
                              @RequestParam(value = "pageNumber") Integer pageNumber) {
        MdModel model = new MdModel(request);
        Page<Order> orders = orderRepository.findByWxOpenid(model.get("wx_openid"), MdCommon.buildPageRequest(pageNumber, WX_PAGE_SIZE, SORT_BY.CREATE_TIME_DESC));
        List<Order> list = orders.getContent();
        List<Map> orderList = new ArrayList<>();
        for (Order order : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("order", order);
            if(order.getFlag() == 1 && !MdCommon.isEmpty(order.getLaunchId())){//拼团订单  并且改订单拼团已经被发起
                GroupLaunch groupLaunch = groupLaunchRepository.findOne(order.getLaunchId());
                map.put("launch", groupLaunch);
            }

            Commodity commodity = commodityRepository.findOne(order.getCommodityId());
            map.put("commodity", commodity);
            orderList.add(map);
        }

        model.put("orderList", orderList);
        model.put("pageNumber", pageNumber);
        return model;
    }


    /**
     * 取消订单
     *
     * @param request
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public Map cancelOrder(HttpServletRequest request,
                           @RequestParam(value = "orderId") Integer orderId) {
        MdModel model = new MdModel(request);
        Order order = orderRepository.findOne(orderId);
        int ret = 0;
        //首先订单未支付
        if (order.getState() == 1 ) {
            order.setState(8);//直接取消订单
            orderRepository.save(order);
        } else if (order.getState() == 2) {//已支付
//        通知客服退款 TODO
            try {
                //判断是否是拼团 并且
                if (order.getFlag() == 1 && !MdCommon.isEmpty(order.getLaunchId())) {
                    GroupLaunch groupLaunch = groupLaunchRepository.findOne(order.getLaunchId());
                    //该拼团还未拼团成功 拼团成功不退款
                    if (groupLaunch.getState() == 0) {//拼团还未成功
                        GroupLaunchUser groupLaunchUser = groupLaunchUserRepository.findByLaunchIdAndWxOpenid(order.getLaunchId(), model.get("wx_openid"));
                        if (!MdCommon.isEmpty(groupLaunchUser)) {//删除当前拼团下的 此用户
                            groupLaunchUserRepository.delete(groupLaunchUser);
                        }
                        //判断当前团状态
                        List<GroupLaunchUser> groupLaunchUserList = groupLaunchUserRepository.findByLaunchId(order.getLaunchId());
                        if (MdCommon.isEmpty(groupLaunchUserList) || groupLaunchUserList.size() == 0) {//该拼团下没有用户 表示拼团失败
                            groupLaunch.setState(3);
                            groupLaunch.setGroupLaunchUserList(null);
                            groupLaunchRepository.save(groupLaunch);


//                            Commodity commodity = commodityRepository.findOne(order.getCommodityId());
//                            commodity.setSold(commodity.getSold() - order.getCommodityNumber());//销量还原
//                            if(commodity.getCommodityNumber() > -1){
//                                commodity.setCommodityNumber(commodity.getCommodityNumber() + order.getCommodityNumber());
//                            }
//                            commodityRepository.save(commodity);

//                            //拼团失败
                            WxTicket wxTicket = wxTicketRepository.findByAppid(WX_APP_ID);
                            WxTemplate.groupClose(wxTicket.getToken(), order);
                        }

                        order.setLaunchId(null);
                        order.setState(5);//订单取消中 等待审核
                        order.setRefundCode("MR" + MdCommon.getNowDate() + MdCommon.getRandomNum(5));
                        orderRepository.save(order);

                    } else {//拼团已经成功
                        ret = -3;// 该拼团已成成功无法取消
                    }
                } else if( order.getFlag() != 1){//非拼团项目
                    order.setState(5);//订单取消中 等待审核
                    order.setRefundCode("MR" + MdCommon.getNowDate() + MdCommon.getRandomNum(5));
                    orderRepository.save(order);
                }
            } catch (Exception e) {
                ret = -2;//服务器异常   请稍后再试！
                e.printStackTrace();
            }
        } else {
            ret = -1;//订单已取消或关闭
        }
        model.put("ret", ret);
        return model;
    }


    /**
     * 收藏动作
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/favoriteAction", method = RequestMethod.POST)
    public Map favoriteAction(HttpServletRequest request,
                              @RequestParam(value = "id") Integer id) {
        MdModel model = new MdModel(request);
        UserFavorite userFavorite = userFavoriteRepository.findByWxOpenidAndCommodityId(model.get("wx_openid"), id);
        if (!MdCommon.isEmpty(userFavorite)) {
            userFavoriteRepository.delete(userFavorite);
        } else {
            userFavorite = new UserFavorite();
            userFavorite.setCommodityId(id);
            userFavorite.setWxOpenid(model.get("wx_openid"));

            userFavoriteRepository.save(userFavorite);
        }

        return model;
    }

    /**
     * 我的收藏
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myFavoritePage", method = RequestMethod.GET)
    public ModelAndView myFavoritePage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("wx_openid"))) {
            return wxAuth(request);
        }
        return new ModelAndView("weixin/myFavorite", model);
    }

    /**
     * 获取我的收藏
     *
     * @param request
     * @param pageNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMyFavorite", method = RequestMethod.POST)
    public Map getMyFavorite(HttpServletRequest request,
                             @RequestParam(value = "pageNumber") Integer pageNumber) {
        MdModel model = new MdModel(request);

        String wx_openid = model.get("wx_openid");
        Page<UserFavorite> result = userFavoriteRepository.findByWxOpenid(wx_openid, MdCommon.buildPageRequest(pageNumber, WX_PAGE_SIZE, SORT_BY.ID_DESC));

        List<Commodity> commodityList = new ArrayList<>();
        for (UserFavorite userFavorite : result.getContent()) {
            Commodity commodity = commodityRepository.findOne(userFavorite.getCommodityId());

            commodityList.add(commodity);
        }
        model.put("commodityList", commodityList);
        model.put("pageNumber", pageNumber);
        model.put("pageSize", WX_PAGE_SIZE);
        return model;
    }

    /**
     * 我的资料页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myDataPage", method = RequestMethod.GET)
    public ModelAndView myData(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("wx_openid"))) {
            return wxAuth(request);
        }
        User user = userRepository.findByWxOpenid(model.get("wx_openid"));
        model.put("user", user);

        return new ModelAndView("weixin/myData", model);
    }


    /**
     * 更新资料
     *
     * @param request
     * @param key
     * @param val
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMyInfo", method = RequestMethod.POST)
    public Map updateMyInfo(HttpServletRequest request,
                            @RequestParam(value = "key") String key,
                            @RequestParam(value = "val") String val) {
        MdModel model = new MdModel(request);

        User user = userRepository.findByWxOpenid(model.get("wx_openid"));
        if ("name".equals(key)) {
            user.setName(val);
        } else if ("mobile".equals(key)) {
            user.setMobile(val);
        } else if ("wx_number".equals(key)) {
            user.setWxNumber(val);
        } else if ("gender".equals(key)) {
            user.setGender(Integer.parseInt(val));
        } else if ("city".equals(key)) {
            DicCity dicCity = new DicCity();
            dicCity.setId(Integer.parseInt(val));
            user.setDicCity(dicCity);
        } else if ("channels".equals(key)) {
            user.setChannels(val);
        } else if ("interests".equals(key)) {
            user.setInterests(val);
        } else if ("wheres".equals(key)) {
            user.setWheres(val);
        }

        userRepository.save(user);
        return model;
    }

    /**
     * 我的积分
     * @param request
     * @return
     */
    @RequestMapping(value = "/myIntegral", method = RequestMethod.GET)
    public ModelAndView myIntegral(HttpServletRequest request){
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("wx_openid"))) {
            return wxAuth(request);
        }
        User user = userRepository.findByWxOpenid(model.get("wx_openid"));
        model.put("user", user);
        model.put("pageActive", "my");
        return new ModelAndView("weixin/myIntegral",model);
    }

    /**
     * 获取我的积分
     * @param request
     * @param pageNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMyIntegralList", method = RequestMethod.POST)
    public Map getMyIntegral(HttpServletRequest request,
                             @RequestParam(value = "pageNumber") Integer pageNumber){
        MdModel model = new MdModel(request);

        List<UserIntegral> integralList = userIntegralRepository.findByWxOpenid(model.get("wx_openid"),MdCommon.buildPageRequest(pageNumber, WX_PAGE_SIZE, SORT_BY.ID_DESC));
        List<Map> result = new ArrayList<>();
        for(UserIntegral userIntegral : integralList){
            Map map = new HashMap<>();
            Order order = orderRepository.findByOrderCode(userIntegral.getOrderCode());
            map.put("userIntegral", userIntegral);
            map.put("commodityName",order.getCommodityName());
            result.add(map);
        }

        model.put("result", result);
        model.put("pageNumber", pageNumber);
        model.put("pageSize", WX_PAGE_SIZE);
        return model;
    }


    /**
     * 常见问题
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/commonProblem", method = RequestMethod.GET)
    public ModelAndView commonProblem(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        List<Others> othersList = othersRepository.findByFlag(2);
        model.put("othersList", othersList);
        return new ModelAndView("weixin/commonProblem", model);
    }


}
