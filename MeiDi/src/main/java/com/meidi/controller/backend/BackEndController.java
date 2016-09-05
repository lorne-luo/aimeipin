package com.meidi.controller.backend;

import com.meidi.controller.weixin.WxBaseController;
import com.meidi.domain.*;
import com.meidi.repository.*;
import com.meidi.util.*;
import com.sun.javafx.sg.prism.NGShape;
import org.apache.http.HttpException;
import org.apache.log4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
//import sun.invoke.util.VerifyAccess;

import javax.annotation.Resource;
import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by luanpeng on 16/3/25.
 */
@Controller
@RequestMapping("/backend")
public class BackEndController implements MdConstants {

    @Resource
    private DicProvinceRepository dicProvinceRepository;
    @Resource
    private DicCityRepository dicCityRepository;
    @Resource
    private DicTagRepository dicTagRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private IndexImageRepository indexImageRepository;
    @Resource
    private OthersRepository othersRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserIntegralRepository userIntegralRepository;
    @Resource
    private CommodityRepository commodityRepository;
    @Resource
    private BuyNoticeRepository buyNoticeRepository;
    @Resource
    private CategoryRepository categoryRepository;
    @Resource
    private InterestCommodityRepository interestCommodityRepository;
    @Resource
    private InterestCommoditySonRepository interestCommoditySonRepository;
    @Resource
    private GroupLaunchRepository groupLaunchRepository;
    @Resource
    private BackendUserRepository backendUserRepository;
    @Resource
    private GroupLaunchUserRepository groupLaunchUserRepository;

    @Resource
    private WxTicketRepository wxTicketRepository;

    /**
     * 后台首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {

        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        return new ModelAndView("backend/index", model);
    }


    /**
     * 添加商品页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addCommodityPage", method = RequestMethod.GET)
    public ModelAndView addCommodityPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }

        Iterable<DicProvince> provinceIterable = dicProvinceRepository.findAll();
        List<DicProvince> provinceList = new ArrayList<>();
        for (DicProvince dicProvince : provinceIterable) {
            provinceList.add(dicProvince);
        }
        model.put("provinceList", provinceList);

        List<DicCity> cityList = dicCityRepository.findByParentId(provinceList.get(0).getId());
        model.put("cityList", cityList);

        Iterable<Category> categoryIterable = categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<>();
        for (Category category : categoryIterable) {
            categoryList.add(category);
        }
        model.put("categoryList", categoryList);

        List<DicTag> tagList = dicTagRepository.findByFlag(1);
        model.put("tagList", tagList);

        return new ModelAndView("backend/commodity", model);
    }

    /**
     * 添加新商品
     *
     * @param commodity
     * @param commonParam
     * @return
     */
    @RequestMapping(value = "/addCommodity", method = RequestMethod.POST)
    public ModelAndView addCommodity(@ModelAttribute Commodity commodity,
                                     @ModelAttribute CommonParam commonParam) {

        if (MdCommon.isEmpty(commodity.getCommodityNumber())) {
            commodity.setCommodityNumber(0);//不限量
        }
        if (MdCommon.isEmpty(commodity.getStartDate())) {
            commodity.setStartDate(null);
        }
        if (MdCommon.isEmpty(commodity.getEndDate())) {
            commodity.setEndDate(null);
        }
        if (MdCommon.isEmpty(commodity.getWeight())) {
            commodity.setWeight(0);
        }
        if (MdCommon.isEmpty(commodity.getCaseUrl())) {
            commodity.setCaseUrl(null);
        }
        if (MdCommon.isEmpty(commodity.getCustomSold())) {
            commodity.setCustomSold(0);
        }

        if (!MdCommon.isEmpty(commodity.getPriceDouble())) {
            commodity.setPrice((int) (commodity.getPriceDouble() * 100));
        }
        if (!MdCommon.isEmpty(commodity.getDiscountPriceDouble())) {
            commodity.setDiscountPrice((int) (commodity.getDiscountPriceDouble() * 100));
        }
        if (!MdCommon.isEmpty(commodity.getAlonePriceDouble())) {
            commodity.setAlonePrice((int) (commodity.getAlonePriceDouble() * 100));
        }
        if (!MdCommon.isEmpty(commodity.getDepositDouble())) {
            commodity.setDeposit((int) (commodity.getDepositDouble() * 100));
        }

        commodity.setCommodityPhotoList(commonParam.getCommodityPhotosList(commodity.getCommodityPhotoList()));
        commodity.setCreateTime(new Date());
        commodityRepository.save(commodity);
        return new ModelAndView(new RedirectView(PATH + "/backend/commodityListPage"));
    }

    /**
     * 编辑商品页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/editCommodityPage/{id}", method = RequestMethod.GET)
    public ModelAndView editCommodityPage(HttpServletRequest request,
                                          @PathVariable Integer id) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }

        Iterable<DicProvince> provinceIterable = dicProvinceRepository.findAll();
        List<DicProvince> provinceList = new ArrayList<>();
        for (DicProvince dicProvince : provinceIterable) {
            provinceList.add(dicProvince);
        }
        model.put("provinceList", provinceList);

        Commodity commodity = commodityRepository.findOne(id);
        model.put("commodity", commodity);

        List<DicCity> cityList = dicCityRepository.findByParentId(commodity.getDicProvince().getId());
        model.put("cityList", cityList);

        List<DicTag> tagList = dicTagRepository.findByFlag(1);
        model.put("tagList", tagList);

        Iterable<Category> categoryIterable = categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<>();
        for (Category category : categoryIterable) {
            categoryList.add(category);
        }
        model.put("categoryList", categoryList);

        return new ModelAndView("backend/commodityEdit", model);
    }

    /**
     * 更新商品
     *
     * @param request
     * @param commodity
     * @param commonParam
     * @return
     */
    @RequestMapping(value = "/updateCommodity", method = RequestMethod.POST)
    public ModelAndView editCommodity(HttpServletRequest request,
                                      @ModelAttribute Commodity commodity,
                                      @ModelAttribute CommonParam commonParam) {
        MdModel model = new MdModel(request);

        Commodity newCommodity = commodityRepository.findOne(commodity.getId());
        if (!MdCommon.isEmpty(commodity.getPriceDouble())) {
            newCommodity.setPriceDouble(commodity.getPriceDouble());
            newCommodity.setPrice((int) (commodity.getPriceDouble() * 100));
        }
        if (!MdCommon.isEmpty(commodity.getDiscountPriceDouble())) {
            newCommodity.setDiscountPriceDouble(commodity.getDiscountPriceDouble());
            newCommodity.setDiscountPrice((int) (commodity.getDiscountPriceDouble() * 100));
        }

        if (!MdCommon.isEmpty(commodity.getDepositDouble())) {
            newCommodity.setDepositDouble(commodity.getDepositDouble());
            newCommodity.setDeposit((int) (commodity.getDepositDouble() * 100));
        }



        newCommodity.setName(commodity.getName());
        newCommodity.setKeyword(commodity.getKeyword());
        newCommodity.setDicProvince(commodity.getDicProvince());
        newCommodity.setDicCity(commodity.getDicCity());
        newCommodity.setCategory(commodity.getCategory());
//        newCommodity.setPrice(commodity.getPrice());
        newCommodity.setUnit(commodity.getUnit());
        if (MdCommon.isEmpty(commodity.getDiscountUnit())) {
            newCommodity.setDiscountUnit(commodity.getUnit());
        } else {
            newCommodity.setDiscountUnit(commodity.getDiscountUnit());
        }
        newCommodity.setDiscount(commodity.getDiscount());
//        newCommodity.setDiscountPrice(commodity.getDiscountPrice());
        if (commodity.getFlag() == 1) {
            newCommodity.setPeopleNumber(commodity.getPeopleNumber());
            if (!MdCommon.isEmpty(commodity.getAlonePriceDouble())) {
                newCommodity.setAlonePriceDouble(commodity.getAlonePriceDouble());
                newCommodity.setAlonePrice((int) (commodity.getAlonePriceDouble() * 100));
            }
        }
//        newCommodity.setDeposit(commodity.getDeposit());
        newCommodity.setSold(commodity.getSold());
        newCommodity.setTags(commodity.getTags());
        newCommodity.setLabelFlag(commodity.getLabelFlag());
        newCommodity.setDescription(commodity.getDescription());
        newCommodity.setRemarks(commodity.getRemarks());
        newCommodity.setWeight(commodity.getWeight());
        newCommodity.setSharingSummary(commodity.getSharingSummary());


        List<CommodityPhoto> photoList = commonParam.getCommodityPhotosList(newCommodity.getCommodityPhotoList());
        if (!MdCommon.isEmpty(photoList) && photoList.size() > 0) {
            newCommodity.setCommodityPhotoList(photoList);
        }


        if (MdCommon.isEmpty(commodity.getCommodityNumber())) {
            newCommodity.setCommodityNumber(0);//不限量
        } else {
            newCommodity.setCommodityNumber(commodity.getCommodityNumber());
        }
        if (MdCommon.isEmpty(commodity.getStartDate())) {
            newCommodity.setStartDate(null);
        } else {
            newCommodity.setStartDate(commodity.getStartDate());
        }
        if (MdCommon.isEmpty(commodity.getEndDate())) {
            newCommodity.setEndDate(null);
        } else {
            newCommodity.setEndDate(commodity.getEndDate());
        }

        if (MdCommon.isEmpty(commodity.getCaseUrl())) {
            newCommodity.setCaseUrl(null);
        } else {
            newCommodity.setCaseUrl(commodity.getCaseUrl());
        }

        if (MdCommon.isEmpty(commodity.getCustomSold())) {
            newCommodity.setCustomSold(0);
        } else {
            newCommodity.setCustomSold(commodity.getCustomSold());
        }

        commodityRepository.save(newCommodity);


        return new ModelAndView(new RedirectView(PATH + "/backend/commodityListPage"));
    }


    /**
     * 项目列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/commodityListPage", method = RequestMethod.GET)
    public ModelAndView commodityListPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }

        Iterable<Category> categoryIterable = categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<>();
        for (Category category : categoryIterable) {
            categoryList.add(category);
        }
        model.put("categoryList", categoryList);

        Iterable<DicProvince> provinceIterable = dicProvinceRepository.findAll();
        model.put("provinceList", provinceIterable);
        return new ModelAndView("backend/commodityList", model);
    }

    /**
     * 获取项目列表
     *
     * @param request
     * @param pageNumber
     * @param flag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCommodityList", method = RequestMethod.POST)
    public Map getCommodityList(HttpServletRequest request,
                                @RequestParam(value = "page") Integer pageNumber,
                                @RequestParam(value = "flag") Integer flag,
                                @RequestParam(value = "state") Integer state,
                                @RequestParam(value = "provinceId") Integer provinceId,
                                @RequestParam(value = "categoryId") Integer categoryId,
                                @RequestParam(value = "queryStr") String queryStr) {

        MdModel model = new MdModel(request);
        Map<String, Object> result = null;
        try {
            result = commodityRepository.findCommodityWithQuery(pageNumber, BE_PAGE_SIZE, flag, state, provinceId,categoryId, queryStr);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Commodity> commodityList = (List<Commodity>) result.get("commodityList");

        model.put("commodityList", commodityList);
        model.put("pageCount", result.get("totalPages"));
        model.put("pageNumber", pageNumber);
        return model;

    }


    /**
     * 上架项目
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upCommodity", method = RequestMethod.POST)
    public Map upCommodity(HttpServletRequest request,
                           @RequestParam(value = "id") Integer id) {
        MdModel model = new MdModel(request);
        Commodity commodity = commodityRepository.findOne(id);
        commodity.setState(1);
        commodityRepository.save(commodity);
        return model;
    }

    /**
     * 下架项目
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/downCommodity", method = RequestMethod.POST)
    public Map downCommodity(HttpServletRequest request,
                             @RequestParam(value = "id") Integer id) {
        MdModel model = new MdModel(request);
        Commodity commodity = commodityRepository.findOne(id);

        // find all unpaid order (state=1) and close it
        List<Order> orders = orderRepository.findByStateAndCommodityId(1,commodity.getId());
        for(Order order : orders){
            order.setState(8);
            orderRepository.save(order);
        }
        // mark commodity as down
        commodity.markAsDown();
        commodity.setWeight(0);
        commodityRepository.save(commodity);

        return model;
    }

    /**
     * 删除项目
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCommodity", method = RequestMethod.POST)
    public Map deleteCommodity(HttpServletRequest request,
                               @RequestParam(value = "id") Integer id) {
        MdModel model = new MdModel(request);
        Commodity commodity = commodityRepository.findOne(id);
        commodity.setState(-1);
        commodityRepository.save(commodity);
        return model;
    }


    /**
     * 订单列表页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderListPage", method = RequestMethod.GET)
    public ModelAndView orderListPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        return new ModelAndView("backend/orderList", model);
    }

    /**
     * 获取订单列表
     *
     * @param request
     * @param pageNumber
     * @param flag
     * @param state
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    public Map getOrderList(HttpServletRequest request,
                            @RequestParam(value = "page") Integer pageNumber,
                            @RequestParam(value = "flag") Integer flag,
                            @RequestParam(value = "state") Integer state,
                            @RequestParam(value = "launchID") Integer launchID,
                            @RequestParam(value = "commodityID") Integer commodityID,
                            @RequestParam(value = "queryStr") String queryStr) {
        MdModel model = new MdModel(request);

        Map result = orderRepository.findOrderWithQuery(pageNumber, BE_PAGE_SIZE, flag, state, launchID, commodityID, queryStr);
        List<Map> orders = new ArrayList<>();
        List<Order> orderList = (List<Order>) result.get("orderList");
        for (Order order : orderList) {
            Map map = new HashMap<>();
            map.put("order", order);
            if (order.getFlag() == 1 && !MdCommon.isEmpty(order.getLaunchId())) {
                GroupLaunch groupLaunch = groupLaunchRepository.findOne(order.getLaunchId());
                map.put("launch", groupLaunch);
            } else {
                map.put("launch", null);
            }
            User user = userRepository.findByWxOpenid(order.getWxOpenid());
            map.put("user", user);
            orders.add(map);
        }


        model.put("orderList", orders);
        model.put("pageCount", result.get("totalPages"));
        model.put("pageNumber", pageNumber);

        return model;
    }


    /**
     * 添加订单备注
     *
     * @param request
     * @param orderId
     * @param remarks
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/remark", method = RequestMethod.POST)
    public Map remark(HttpServletRequest request,
                      @RequestParam(value = "orderId") Integer orderId,
                      @RequestParam(value = "remarks") String remarks) {
        MdModel model = new MdModel(request);
        Order order = orderRepository.findOne(orderId);
        order.setRemarks(remarks);
        orderRepository.save(order);
        return model;
    }

    /**
     * 预约时间
     *
     * @param request
     * @param orderId
     * @param bookingTimeStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bookingTime", method = RequestMethod.POST)
    public Map bookingTime(HttpServletRequest request,
                           @RequestParam(value = "orderId") Integer orderId,
                           @RequestParam(value = "bookingTimeStr") String bookingTimeStr) {
        MdModel model = new MdModel(request);
        Order order = orderRepository.findOne(orderId);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = simpleDateFormat.parse(bookingTimeStr);
            order.setBookingTime(date);
            order.setState(3);
            orderRepository.save(order);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        model.put("bookingTime", order.getBookingTime());
        return model;
    }

    /**
     * 关闭订单
     *
     * @param request
     * @param orderId
     * @param state
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
    public Map closeOrder(HttpServletRequest request,
                          @RequestParam(value = "orderId") Integer orderId,
                          @RequestParam(value = "state") Integer state) {
        MdModel model = new MdModel(request);
        int ret = 0;
        try {
            Order order = orderRepository.findOne(orderId);
            if (state == 6) {//主动申请的退款
                if (order.getFlag() == 1 && order.getState() == 2){
                    //已支付的拼团订单退款,需要对团长取消进行特别处理
                    if (order.getBookingFlag() == 1 && order.getLaunchId() > 0) { //团长取消
                        List<GroupLaunchUser> allGroupUsers = groupLaunchUserRepository.findByLaunchIdOrderByFlagAsc(order.getLaunchId());
                        GroupLaunchUser firstGroupUser = allGroupUsers.get(0);
                        if (firstGroupUser.getWxOpenid().equals(order.getWxOpenid())) { //确认是团长订单
                            if (allGroupUsers.size() > 1) { //团内不止一人
                                GroupLaunchUser secondGroupUser = allGroupUsers.get(1);
                                secondGroupUser.setFlag(1);
                                List<Order> secondUserOrders = orderRepository.findByWxOpenidAndLaunchIdOrderByCreateTimeDesc(secondGroupUser.getWxOpenid(), order.getLaunchId());
                                Order secondUserOrder = secondUserOrders.get(0);
                                secondUserOrder.setBookingFlag(1);

                                groupLaunchUserRepository.save(secondGroupUser);
                                orderRepository.save(secondUserOrder);
                                groupLaunchUserRepository.delete(firstGroupUser);
                                order.setLaunchId(null);
                            } else {//该团只有团长一人
                                GroupLaunch groupLaunch = groupLaunchRepository.findOne(order.getLaunchId());
                                groupLaunch.setState(3); //唯一的团长取消,拼团失败
                                groupLaunchRepository.save(groupLaunch);
                            }
                        }
                    }
                }

                //TODO 走退款流程
//                WxBaseController wxBaseController = new WxBaseController();
//                Map result = wxBaseController.orderRefund(order);

//                String return_code = MdCommon.null2String(result.get("return_code"));
//                String return_msg = MdCommon.null2String(result.get("return_msg"));
//                if (return_code.equals("SUCCESS")) {//表示通信成功
//                    String result_code = MdCommon.null2String(result.get("result_code"));
//                    if ("SUCCESS".equals(result_code)) {
//                        String refund_id = MdCommon.null2String(result.get("refund_id"));//微信退款单号
//                        order.setRefundId(refund_id);
                        order.setState(6);
//
//                    } else {
//                        ret = -2;//退款失败
//                        System.out.println(result.get("err_code"));
//                    }
//                } else {
//                    ret = -2;//退款失败
//                    System.out.println(return_msg);
//                }

            } else if (state == 7) {//不退款(已预约但是超时未消费的)

                if (order.getFlag() == 1 && !MdCommon.isEmpty(order.getLaunchId())) {//拼团订单 关闭该拼团
//                    GroupLaunch groupLaunch = groupLaunchRepository.findOne(order.getLaunchId());
//                    if (groupLaunch.getState() != 3) { // 0 拼团中, 1 拼团成功 拼团结束, 3 拼团失败
//                        groupLaunch.setState(3);
//                        groupLaunchRepository.save(groupLaunch);
//                    }

                    GroupLaunchUser groupLaunchUser = groupLaunchUserRepository.findByLaunchIdAndWxOpenid(order.getLaunchId(),order.getWxOpenid());
                    if(!MdCommon.isEmpty(groupLaunchUser)){
                        groupLaunchUserRepository.delete(groupLaunchUser);
                    }

                    List<GroupLaunchUser> groupLaunchUserList = groupLaunchUserRepository.findByLaunchId(order.getLaunchId());
                    if (MdCommon.isEmpty(groupLaunchUserList) || groupLaunchUserList.size() == 0) {
                        //该拼团下没有用户 设置LaunchId为Null
                        order.setLaunchId(null);
                    }

                    //同时关闭该拼团下的所有用户订单
//                    List<Order> orderList = orderRepository.findByLaunchId(order.getLaunchId());
//                    for (Order o : orderList) {
//                        if (o.getId() != order.getId() && o.getState() != 7) {
//                            o.setLaunchId(null);
//                            o.setState(7);
//                            orderRepository.save(o);
//                        }
//                    }
                }
                order.setState(7);
            }


            if (ret == 0) {
                orderRepository.save(order);
            }
        } catch (Exception e) {
            ret = -1;
            e.printStackTrace();
        }

        model.put("ret", ret);
        return model;
    }

    /**
     * 加积分
     *
     * @param request
     * @param integral
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addIntegral", method = RequestMethod.POST)
    public Map addIntegral(HttpServletRequest request,
                           @RequestParam(value = "integral") Integer integral,
                           @RequestParam(value = "orderId") Integer orderId) {
        MdModel model = new MdModel(request);

        Order order = orderRepository.findOne(orderId);


        User user = userRepository.findByWxOpenid(order.getWxOpenid());
        Integer allIntegral = user.getIntegral();
        user.setIntegral(allIntegral + integral);
        userRepository.save(user);

        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setIntegral(integral);
        userIntegral.setOrderCode(order.getOrderCode());
        userIntegral.setWxOpenid(user.getWxOpenid());
        userIntegralRepository.save(userIntegral);

        order.setState(4);
        orderRepository.save(order);

        //发消息
        WxTicket wxTicket = wxTicketRepository.findByAppid(MdConstants.WX_APP_ID);
        try {
            WxTemplate.orderComplete(wxTicket.getToken(), order);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 用户列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/userListPage", method = RequestMethod.GET)
    public ModelAndView userListPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }

        Iterable<DicProvince> provinceIterable = dicProvinceRepository.findAll();
        model.put("provinceList", provinceIterable);

        return new ModelAndView("backend/userList", model);
    }

    /**
     * @param request
     * @param pageNumber
     * @param cityId
     * @param queryStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public Map getUserList(HttpServletRequest request,
                           @RequestParam(value = "page") Integer pageNumber,
                           @RequestParam(value = "cityId") Integer cityId,
                           @RequestParam(value = "queryStr") String queryStr) {
        MdModel model = new MdModel(request);

        Map result = userRepository.findUserWithQuery(pageNumber, BE_PAGE_SIZE, cityId, queryStr);
        model.put("userList", result.get("userList"));
        model.put("pageCount", result.get("totalPages"));
        model.put("pageNumber", pageNumber);
        return model;
    }

    /**
     * 其他杂项管理
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tagsPage/{flag}", method = RequestMethod.GET)
    public ModelAndView othersPage(HttpServletRequest request,
                                   @PathVariable Integer flag) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        model.put("flag", flag);
        List<DicTag> tagList = null;
        if (flag == 1) {
            tagList = dicTagRepository.findByFlag(1);
        } else if (flag == 2) {
            tagList = dicTagRepository.findByFlag(2);
        }
//        else if (flag == 3) {
//            tagList = dicTagRepository.findByFlag(3);
//        }
        model.put("tagList", tagList);
        return new ModelAndView("backend/tags", model);
    }

    /**
     * 感兴趣的项目页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/interest", method = RequestMethod.GET)
    public ModelAndView interestPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        Iterable<InterestCommodity> interestCommodities = interestCommodityRepository.findAll();
        model.put("interest", interestCommodities);
        return new ModelAndView("backend/interest", model);
    }

    /**
     * 添加感兴趣项目
     *
     * @param request
     * @param name
     * @param level
     * @param parentId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addInterest", method = RequestMethod.POST)
    public Map addInterest(HttpServletRequest request,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "level") Integer level,
                           @RequestParam(value = "parentId") Integer parentId) {
        MdModel model = new MdModel(request);
        if (level == 1) {
            InterestCommodity interestCommodity = new InterestCommodity();
            interestCommodity.setName(name);
            interestCommodity = interestCommodityRepository.save(interestCommodity);
            model.put("id", interestCommodity.getId());
        } else {
            InterestCommoditySon interestCommoditySon = new InterestCommoditySon();
            interestCommoditySon.setName(name);
            interestCommoditySon.setParentId(parentId);

            interestCommoditySon = interestCommoditySonRepository.save(interestCommoditySon);
            model.put("id", interestCommoditySon.getId());
        }
        return model;
    }

    /**
     * 删除
     *
     * @param request
     * @param id
     * @param level
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteInterest", method = RequestMethod.POST)
    public Map deleteInterest(HttpServletRequest request,
                              @RequestParam(value = "id") Integer id,
                              @RequestParam(value = "level") Integer level) {
        MdModel model = new MdModel(request);
        if (level == 1) {
            InterestCommodity interestCommodity = interestCommodityRepository.findOne(id);
            interestCommodityRepository.delete(interestCommodity);
        } else {
            InterestCommoditySon interestCommoditySon = interestCommoditySonRepository.findOne(id);
            interestCommoditySonRepository.delete(interestCommoditySon);
        }
        return model;
    }


    /**
     * 购买须知
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/buyNotice/{flag}", method = RequestMethod.GET)
    public ModelAndView buyNotice(HttpServletRequest request,
                                  @PathVariable Integer flag) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }

        BuyNotice buyNotice = buyNoticeRepository.findByFlag(flag);
        model.put("buyNotice", buyNotice);
        model.put("flag", flag);

        return new ModelAndView("backend/buyNotice", model);
    }

    /**
     * 更新购买须知
     *
     * @param request
     * @param flag
     * @param description
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBuyNotice", method = RequestMethod.POST)
    public Map updateBuyNotice(HttpServletRequest request,
                               @RequestParam(value = "flag") Integer flag,
                               @RequestParam(value = "description") String description) {
        MdModel model = new MdModel(request);
        BuyNotice buyNotice = buyNoticeRepository.findByFlag(flag);
        if (MdCommon.isEmpty(buyNotice)) {
            buyNotice = new BuyNotice();
            buyNotice.setFlag(flag);
        }
        buyNotice.setCreateTime(new Date());
        buyNotice.setDescription(description);
        buyNoticeRepository.save(buyNotice);

        return model;
    }


    /**
     * 首页轮播图
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/indexImage", method = RequestMethod.GET)
    public ModelAndView indexImage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        Iterable<IndexImage> indexImages = indexImageRepository.findAll();
        model.put("images", indexImages);
        return new ModelAndView("backend/indexImage", model);
    }

    /**
     * 常见问题添加
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/commonProblem", method = RequestMethod.GET)
    public ModelAndView commonProblem(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        return new ModelAndView("backend/commonProblem", model);
    }


    /**
     * 关于我们以及常见问题
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/others/{flag}", method = RequestMethod.GET)
    public ModelAndView others(HttpServletRequest request,
                               @PathVariable Integer flag) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        List<Others> othersList = othersRepository.findByFlag(flag);
        model.put("othersList", othersList);
        model.put("flag", flag);
        return new ModelAndView("backend/others", model);
    }

    /**
     * 关于我们以及常见问题保存
     *
     * @param request
     * @param imageName
     * @param backFlag
     * @param flag
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/addOthers", method = RequestMethod.POST)
    public Map addOthers(HttpServletRequest request,
                         @RequestParam(value = "imageName") String imageName,
                         @RequestParam(value = "backFlag") String backFlag,
                         @RequestParam(value = "flag") Integer flag) throws IOException {
        MdModel model = new MdModel(request);

        if (!MdCommon.isEmpty(imageName) && !MdCommon.isEmpty(backFlag)) {
            String[] names = imageName.split(",");
            String[] flags = backFlag.split(",");
            for (int i = 0; i < names.length; i++) {
                if (flags[i].equals("00")) {
                    //移动临时图片到正式图片
                    String newName = MdCommon.getNewFileName("MC", names[i]);
                    //把临时文件复制到正式文件夹
                    MdCommon.copyFile(new File(MdConstants.IMAGE_TEMPORARY_PATH + "/" + names[i]), new File(MdConstants.IMAGE_FORMAL_PATH + "/" + newName));
                    //删除临时文件
                    MdCommon.delOneFile(MdConstants.IMAGE_TEMPORARY_PATH + "/" + names[i]);

                    Others others = new Others();
                    others.setImageName(newName);
                    others.setFlag(flag);
                    others.setCreateTime(new Date());

                    othersRepository.save(others);
                } else {

                }
            }
        }


        return model;
    }

    /**
     * 更新权重
     *
     * @param request
     * @param weight
     * @param commodityId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateWeight", method = RequestMethod.POST)
    public Map updateWeight(HttpServletRequest request,
                            @RequestParam(value = "weight") Integer weight,
                            @RequestParam(value = "commodityId") Integer commodityId) {
        MdModel model = new MdModel(request);

        Commodity commodity = commodityRepository.findOne(commodityId);
        commodity.setWeight(weight);
        commodityRepository.save(commodity);

        return model;
    }

    /**
     * 登陆页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        model.put("version", PomVersion.getVersion());
        return new ModelAndView("backend/login", model);
    }

    /**
     * 登录
     *
     * @param request
     * @param account
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map login(HttpServletRequest request,
                     @RequestParam(value = "account") String account,
                     @RequestParam(value = "password") String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        int ret = 0;
        if (MdCommon.isEmpty(account) || MdCommon.isEmpty(password)) {
            ret = -1;
        } else {
            BackendUser backendUser = backendUserRepository.findByAccountAndPassword(account, MdCommon.getMD5(MdCommon.getSHA1(password)));
            if (MdCommon.isEmpty(backendUser)) {
                ret = -2;
            } else {
                if (backendUser.getState() == 0) {
                    ret = -3;
                } else {
                    UserSession userSession = new UserSession();
                    userSession.setAccount(account);
                    userSession.setBu_flag(backendUser.getFlag());

                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute(USER_SESSION, userSession);
                }
            }
        }

        MdModel model = new MdModel(request);
        model.put("ret", ret);
        return model;
    }

    /**
     * 注册页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerPage", method = RequestMethod.GET)
    public ModelAndView registerPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        return new ModelAndView("backend/register", model);
    }

    /**
     * 注册
     *
     * @param request
     * @param account
     * @param password
     * @param rePassword
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map register(HttpServletRequest request,
                        @RequestParam(value = "account") String account,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "rePassword") String rePassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MdModel model = new MdModel(request);
        int ret = 0;
        if (MdCommon.isEmpty(account) || MdCommon.isEmpty(password) || MdCommon.isEmpty(rePassword) || !password.equals(rePassword)) {
            ret = -1;
        } else {
            BackendUser backendUser = backendUserRepository.findByAccount(account);
            if (!MdCommon.isEmpty(backendUser)) {
                ret = -2;
            } else {
                backendUser = new BackendUser();
                backendUser.setAccount(account);
                backendUser.setPassword(MdCommon.getMD5(MdCommon.getSHA1(password)));
                backendUserRepository.save(backendUser);
            }
        }
        model.put("ret", ret);
        return model;
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(USER_SESSION, null);
        MdModel model = new MdModel(request);
        return new ModelAndView("backend/login", model);
    }

    /**
     * 修改密码页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/editPasswordPage", method = RequestMethod.GET)
    public ModelAndView editPasswordPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        return new ModelAndView("backend/editPassword", model);
    }

    /**
     * 修改密码
     *
     * @param request
     * @param password
     * @param rePassword
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @ResponseBody
    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public Map editPassword(HttpServletRequest request,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "rePassword") String rePassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MdModel model = new MdModel(request);
        int ret = 0;
        if (MdCommon.isEmpty(password) || MdCommon.isEmpty(rePassword) || !password.equals(rePassword)) {
            ret = -1;
        } else {
            BackendUser backendUser = backendUserRepository.findByAccount(model.get("account"));
            if (MdCommon.isEmpty(backendUser)) {
                ret = -2;
            } else {
                backendUser.setPassword(MdCommon.getMD5(MdCommon.getSHA1(password)));
                backendUserRepository.save(backendUser);

                HttpSession httpSession = request.getSession();
                httpSession.setAttribute(USER_SESSION, null);
            }
        }
        model = new MdModel(request);
        model.put("ret", ret);
        return model;
    }

    /**
     * 管理员列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/backendUserListPage", method = RequestMethod.GET)
    public ModelAndView BUserListPage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        if (MdCommon.isEmpty(model.get("account"))) {
            return new ModelAndView(new RedirectView(PATH + "/backend/loginPage"));
        }
        return new ModelAndView("backend/backendUserList", model);
    }

    /**
     * 获取数据
     *
     * @param request
     * @param pageNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBackendUserList", method = RequestMethod.POST)
    public Map getBackendUserList(HttpServletRequest request,
                                  @RequestParam(value = "page") Integer pageNumber) {
        MdModel model = new MdModel(request);
        Page<BackendUser> result = backendUserRepository.findAll(MdCommon.buildPageRequest(pageNumber, BE_PAGE_SIZE, SORT_BY.CREATE_TIME_DESC));
        model.put("userList", result.getContent());
        model.put("pageCount", result.getTotalPages());
        model.put("pageNumber", pageNumber);

        return model;
    }

    /**
     * 激活
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/activationUser", method = RequestMethod.POST)
    public Map activationUser(HttpServletRequest request,
                              @RequestParam(value = "id") Integer id) {
        MdModel model = new MdModel(request);

        BackendUser backendUser = backendUserRepository.findOne(id);
        backendUser.setState(1);
        backendUserRepository.save(backendUser);
        return model;
    }

    /**
     * 禁用
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/disableUser", method = RequestMethod.POST)
    public Map disableUser(HttpServletRequest request,
                           @RequestParam(value = "id") Integer id) {
        MdModel model = new MdModel(request);

        BackendUser backendUser = backendUserRepository.findOne(id);
        backendUser.setState(0);
        backendUserRepository.save(backendUser);
        return model;
    }

    /**
     * 测试页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/test/{testItem}", method = RequestMethod.GET)
    public ModelAndView testPage(HttpServletRequest request,@PathVariable String testItem) throws Exception {
        MdModel model = new MdModel(request);
        // only tester can see this
        if(!model.get("wx_openid").equals(MdConstants.TEST_OPENID))
            throw new HttpException("");

        String token = wxTicketRepository.findByAppid(WX_APP_ID).getToken();
        Iterable<Order> allOrders = orderRepository.findAll();
        Order order = null;
        for (Order o : allOrders) {
            order = o;
            break;
        }
        order.setWxOpenid(MdConstants.TEST_OPENID);

        if(testItem.equals("groupLaunch"))
            WxTemplate.groupLaunch(token,order);
        else if(testItem.equals("groupLaunchOk"))
            WxTemplate.groupLaunchOk(token,order);
        else if(testItem.equals("groupClose"))
            WxTemplate.groupClose(token,order);
        else if(testItem.equals("joinGroup"))
            WxTemplate.joinGroup(token,order);
        else if(testItem.equals("orderComplete"))
            WxTemplate.orderComplete(token,order);

        model.put("trade_state", testItem);
        model.put("trade_state_desc", "456");
        return new ModelAndView("test", model);
    }
}
