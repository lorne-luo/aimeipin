package com.meidi.controller;

import com.meidi.domain.*;
import com.meidi.repository.*;
import com.meidi.util.MdCommon;
import com.meidi.util.MdConstants;
import com.meidi.util.MdModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by luanpeng on 16/3/23.
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController implements MdConstants {

    @Resource
    private DicCityRepository dicCityRepository;
    @Resource
    private DicProvinceRepository dicProvinceRepository;
    @Resource
    private DicTagRepository dicTagRepository;
    @Resource
    private IndexImageRepository indexImageRepository;
    @Resource
    private OthersRepository othersRepository;
    @Resource
    private CommodityPhotoRepository commodityPhotoRepository;
    @Resource
    private InterestCommodityRepository interestCommodityRepository;

    /**
     * 上传临时图片
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Map uploadImage(HttpServletRequest request) {
        MdModel model = new MdModel(request);
        List<String> imgList = new ArrayList<>();
        int ret = 0;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //判断是否有文件上传
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

//                Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
                List<MultipartFile> fileList = multipartHttpServletRequest.getFiles("fileName");
                for (MultipartFile multipartFile : fileList) {
                    if (!MdCommon.isEmpty(multipartFile)) {
                        String originalName = multipartFile.getOriginalFilename();
                        if (!MdCommon.isEmpty(originalName)) {
                            //获取文件后缀名
                            String fileNameSuffix = originalName.substring(originalName.lastIndexOf(".") + 1, originalName.length());
                            //生成临时文件名
                            String newName = MdCommon.getNowDate() + MdCommon.getRandomNum(8) + "." + fileNameSuffix;

                            File temporaryFile = new File(IMAGE_TEMPORARY_PATH + "/" + newName);

                            multipartFile.transferTo(temporaryFile);

                            imgList.add(newName);

                        }
                    }
                }
            }

        } catch (IOException e) {
            ret = -1;
            e.printStackTrace();
        }

        model.put("ret", ret);
        model.put("imgList", imgList);
        return model;
    }


    /**
     * 删除项目图片
     * @param request
     * @param imgName
     * @param backTag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCommodityImage", method = RequestMethod.POST)
    public Map deleteCommodityImage(HttpServletRequest request,
                                  @RequestParam(value = "storePicName") String imgName,
                                  @RequestParam(value = "backTag") String backTag) {
        MdModel model = new MdModel(request);
        try {
            if ("00".equals(backTag)) {
                MdCommon.delOneFile(IMAGE_TEMPORARY_PATH + "/" + imgName);
            } else {
                MdCommon.delOneFile(IMAGE_FORMAL_PATH + "/" + imgName);
                CommodityPhoto photo = commodityPhotoRepository.findOne(Integer.parseInt(backTag));
                commodityPhotoRepository.delete(photo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 删除首页轮播图
     * @param request
     * @param imgName
     * @param backTag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteIndexImage", method = RequestMethod.POST)
    public Map deleteIndexImage(HttpServletRequest request,
                                  @RequestParam(value = "storePicName") String imgName,
                                  @RequestParam(value = "backTag") String backTag) {
        MdModel model = new MdModel(request);
        try {
            if ("00".equals(backTag)) {
                MdCommon.delOneFile(IMAGE_TEMPORARY_PATH + "/" + imgName);
            } else {
                MdCommon.delOneFile(IMAGE_FORMAL_PATH + "/" + imgName);
                IndexImage photo = indexImageRepository.findOne(Integer.parseInt(backTag));
                indexImageRepository.delete(photo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }


    /**
     * 删除关于我们活着常见问题图片
     * @param request
     * @param imgName
     * @param backTag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteOthersImage", method = RequestMethod.POST)
    public Map deleteOthersImage(HttpServletRequest request,
                              @RequestParam(value = "storePicName") String imgName,
                              @RequestParam(value = "backTag") String backTag) {
        MdModel model = new MdModel(request);
        try {
            if ("00".equals(backTag)) {
                MdCommon.delOneFile(IMAGE_TEMPORARY_PATH + "/" + imgName);
            } else {
                MdCommon.delOneFile(IMAGE_FORMAL_PATH + "/" + imgName);
                Others others = othersRepository.findOne(Integer.parseInt(backTag));
                othersRepository.delete(others);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 获取城市列表
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCityList", method = RequestMethod.POST)
    public Map getCityList(HttpServletRequest request,
                           @RequestParam(value = "id") Integer id) {
        MdModel model = new MdModel(request);

        List<DicCity> cityList = dicCityRepository.findByParentId(id);
        model.put("cityList", cityList);
        return model;
    }


    /**
     * 获取地区级联
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProvinceAndCity", method = RequestMethod.POST)
    public Map getProvinceAndCity(HttpServletRequest request) {
        MdModel model = new MdModel(request);

        List<Map> resultList = new ArrayList<>();
        Iterable<DicProvince> provinceIterable = dicProvinceRepository.findAll();
        for (DicProvince province : provinceIterable) {
            Map map = new HashMap<>();
            map.put("province", province);

            List<DicCity> cityList = dicCityRepository.findByParentId(province.getId());
            map.put("cityList", cityList);
            resultList.add(map);
        }
        model.put("resultList", resultList);
        return model;
    }

    /**
     * 删除标签
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteTag", method = RequestMethod.POST)
    public Map deleteTag(HttpServletRequest request,
                         @RequestParam(value = "id") Integer id) {
        MdModel model = new MdModel(request);
        DicTag dicTag = dicTagRepository.findOne(id);
        dicTagRepository.delete(dicTag);
        return model;
    }

    /**
     * 添加标签
     *
     * @param request
     * @param flag
     * @param val
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addTag", method = RequestMethod.POST)
    public Map addTag(HttpServletRequest request,
                      @RequestParam(value = "flag") Integer flag,
                      @RequestParam(value = "val") String val) {
        MdModel model = new MdModel(request);
        DicTag dicTag = new DicTag();
        dicTag.setFlag(flag);
        dicTag.setName(val);
        dicTag = dicTagRepository.save(dicTag);
        model.put("id", dicTag.getId());
        return model;
    }

    /**
     * 获取指定的标签
     *
     * @param request
     * @param flag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTags", method = RequestMethod.POST)
    public Map getTags(HttpServletRequest request,
                       @RequestParam(value = "flag") Integer flag) {
        MdModel model = new MdModel(request);

        List<DicTag> tagList = dicTagRepository.findByFlag(flag);
        model.put("tagList", tagList);
        return model;
    }

    /**
     * 获取感兴趣项目
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getInterest", method = RequestMethod.POST)
    public Map getInterest(HttpServletRequest request){
        MdModel model = new MdModel(request);
        Iterable<InterestCommodity> interestCommodities = interestCommodityRepository.findAll();
        model.put("inteList", interestCommodities);
        return model;
    }

    /**
     * 保存首页轮播图
     *
     * @param request
     * @param imageName
     * @param backFlag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveIndexImage", method = RequestMethod.POST)
    public Map saveIndexImage(HttpServletRequest request,
                              @RequestParam(value = "imageName") String imageName,
                              @RequestParam(value = "backFlag") String backFlag,
                              @RequestParam(value = "textarea") String textarea,
                              @RequestParam(value = "weightStr") String weightStr) {
        MdModel model = new MdModel(request);
        int ret = 0;
        try {
            if (!MdCommon.isEmpty(imageName) && !MdCommon.isEmpty(backFlag)) {
                String[] names = imageName.split(",");
                String[] flags = backFlag.split(",");
                String[] texts = textarea.split("~&");
                String[] weights = weightStr.split(",");
                for (int i = 0; i < names.length; i++) {



                    IndexImage indexImage;
                    if ("00".equals(flags[i])) {//新增
                        //移动临时图片到正式图片
                        String newName = MdCommon.getNewFileName("MI", names[i]);
                        //把临时文件复制到正式文件夹
                        MdCommon.copyFile(new File(MdConstants.IMAGE_TEMPORARY_PATH + "/" + names[i]), new File(MdConstants.IMAGE_FORMAL_PATH + "/" + newName));
                        //删除临时文件
                        MdCommon.delOneFile(MdConstants.IMAGE_TEMPORARY_PATH + "/" + names[i]);


                        indexImage = new IndexImage();
                        indexImage.setImageName(newName);
                        if(!MdCommon.isEmpty(texts[i].replace(" ",""))){
                            if(texts[i].indexOf("/") < 0){
                                int id = Integer.parseInt(texts[i]);
                                indexImage.setUrl(PATH + "/business/commodityDetailPage/" + id);
                            }else{
                                indexImage.setUrl(texts[i]);
                            }
                        }

                        indexImage.setCreateTime(new Date());
                        indexImage.setWeight(Integer.parseInt(weights[i]));
                    } else {
                        indexImage = indexImageRepository.findOne(Integer.parseInt(flags[i]));
                        if(MdCommon.isEmpty(texts[i].replace(" ",""))) {
                            indexImage.setUrl(null);
                        }else{
                            if(texts[i].indexOf("/") < 0){
                                int id = Integer.parseInt(texts[i]);
                                indexImage.setUrl(PATH + "/business/commodityDetailPage/" + id);
                            }else{
                                indexImage.setUrl(texts[i]);
                            }
                        }
                        indexImage.setWeight(Integer.parseInt(weights[i]));
                    }
                    indexImageRepository.save(indexImage);
                }

            } else {
                ret = -2;
            }
        } catch (Exception e) {
            ret = -1;
            e.printStackTrace();
        }
        model.put("ret", ret);
        return model;
    }




}
