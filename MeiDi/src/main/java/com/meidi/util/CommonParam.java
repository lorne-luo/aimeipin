package com.meidi.util;

import com.meidi.domain.*;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luanpeng on 16/3/26.
 */
public class CommonParam implements Serializable {

    private String imageName;//图片名字符串
    private String backFlag;//00 表示临时图片 非00 表示已存在的图片ID

    private String commodityIdStr;//福袋项目中子商品的商品ID
    private String commodityFlagStr;//0新增的子项目 1 已经存在的福袋子项目的
    private String deleteSonProjectIdStr;//需要删除的子项目Id


    /**
     * 获取商品图片列表
     * @return
     */
    public List<CommodityPhoto> getCommodityPhotosList(List<CommodityPhoto> commodityPhotoList) {
        if (MdCommon.isEmpty(imageName) || MdCommon.isEmpty(backFlag)) {
            return null;
        }
        List<CommodityPhoto> photosList = new ArrayList<>();
        if(!MdCommon.isEmpty(commodityPhotoList)){
            photosList = commodityPhotoList;
        }


        String[] imgNames = imageName.split(",");
        String[] backFlags = backFlag.split(",");
        for (int i = 0; i < imgNames.length; i++) {
            CommodityPhoto commodityPhoto = null;

            if ("00".equals(backFlags[i])) {//临时图片
                try {
                    //移动临时图片到正式图片
                    String newName = MdCommon.getNewFileName("MP", imgNames[i]);
                    //把临时文件复制到正式文件夹
                    MdCommon.copyFile(new File(MdConstants.IMAGE_TEMPORARY_PATH + "/" + imgNames[i]), new File(MdConstants.IMAGE_FORMAL_PATH + "/" + newName));
                    //删除临时文件
                    MdCommon.delOneFile(MdConstants.IMAGE_TEMPORARY_PATH + "/" + imgNames[i]);


                    commodityPhoto = new CommodityPhoto();
                    commodityPhoto.setImageName(newName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                photosList.add(commodityPhoto);
            } else {//已存在的图片不用处理 只需判断是否修改为首图

            }

        }
        return photosList;
    }


    /**
     * 获取需要删除的ID
     *
     * @return
     */
    public String[] splitDeleteId() {
        if (MdCommon.isEmpty(deleteSonProjectIdStr)) {
            return null;
        }
        String[] ids = deleteSonProjectIdStr.split(",");

        return ids;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag;
    }

    public String getCommodityIdStr() {
        return commodityIdStr;
    }

    public void setCommodityIdStr(String commodityIdStr) {
        this.commodityIdStr = commodityIdStr;
    }

    public String getCommodityFlagStr() {
        return commodityFlagStr;
    }

    public void setCommodityFlagStr(String commodityFlagStr) {
        this.commodityFlagStr = commodityFlagStr;
    }

    public String getDeleteSonProjectIdStr() {
        return deleteSonProjectIdStr;
    }

    public void setDeleteSonProjectIdStr(String deleteSonProjectIdStr) {
        this.deleteSonProjectIdStr = deleteSonProjectIdStr;
    }

}
