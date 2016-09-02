package com.meidi.util;

/**
 * Created by luanpeng on 16/3/20.
 */
public interface MdConstants {

    enum SORT_BY{
        ID_ASC,ID_DESC,UPDATE_TIME_ASC,UPDATE_TIME_DESC,CREATE_TIME_ASC,CREATE_TIME_DESC
    }


    String MCH_ID = "1348757901";
    String WX_API_KEY = "UYh9uUYP4ghI0Ir4N3GEZMmMwndltduS";

    Integer WX_PAGE_SIZE = 10;
    Integer BE_PAGE_SIZE = 10;


    String USER_SESSION = "userSession";
    String USER_URL = "userUrl";


    String WX_GROUP_LAUNCH = "VwEjiiMoKn8AwDtHxmIobMzEpJCR5FPYQUuhNIjOOFc";
    String WX_GROUP_LAUNCH_SUCCESS = "Qes8kpBHC29Wh3q6Dm8Zwq7HGsPmkaOicVxhnKqcMyQ";
    String WX_JOIN_GROUP = "clF_I2A60Z1pN6GbvcDcbG5DOIhHHnTOw_Vg2WB_dj4";
    String WX_GROUP_FAIL = "rXaWbwpLQiWFJKEQYUhuOu3fcqxZ1QHmuUcMt82U0Wc";
    String WX_ORDER_COMPLETE = "L_Wawg6RlUQK6OtYVUp_caPpIFUbrzQ5eEU3tmqyKbc";


    //localhost
//    String PATH = "http://localhost:8080/meidi";
//    String COMMODITY_INDEX_PATH = "/Users/luanpeng/MyProject/lucene/CommodityIndex";
//    String MQ_HOST = "localhost:61616";
//    String KEY_STORE = "/Users/luanpeng/Downloads/cert/apiclient_cert.p12";
//    String IMAGE_TEMPORARY_PATH = "/Users/luanpeng/ImageTemporary";
//    String IMAGE_FORMAL_PATH = "/Users/luanpeng/ImageFormal";
//    String IMAGE_TEMPORARY_URL = PATH + "/temporary";
//    String IMAGE_FORMAL_URL = PATH + "/formal";
//    String IMAGE_TEXT_PATH = "/Users/luanpeng/ImageText";



    //localhost ip
//    String PATH = "http://192.168.1.112:8080/meidi";
//    String COMMODITY_INDEX_PATH = "/Users/luanpeng/MyProject/lucene/CommodityIndex";
//    String MQ_HOST = "localhost:61616";
//    String KEY_STORE = "/Users/luanpeng/Downloads/cert/apiclient_cert.p12";
//    String IMAGE_TEMPORARY_PATH = "/Users/luanpeng/ImageTemporary";
//    String IMAGE_FORMAL_PATH = "/Users/luanpeng/ImageFormal";
//    String IMAGE_TEMPORARY_URL = PATH + "/temporary";
//    String IMAGE_FORMAL_URL = PATH + "/formal";
//    String IMAGE_TEXT_PATH = "/Users/luanpeng/ImageText";


    //meidi
//    String WX_APP_ID = "wx79db03e2de09bc1c";
//    String WX_SECRET = "956e9c943571c74ac1d7c7c6cd60eb56";
//    String PATH = "http://wx.meimeida.cn";
//    String COMMODITY_INDEX_PATH = "/Users/luanpeng/MyProject/lucene/CommodityIndex";
//    String MQ_HOST = "localhost:61616";
//    String KEY_STORE = "/usr/local/cert/apiclient_cert.p12";
//    String IMAGE_TEMPORARY_PATH = "/usr/local/imageTemporary";
//    String IMAGE_FORMAL_PATH = "/usr/local/imageFormal";
//    String IMAGE_TEMPORARY_URL = PATH + "/temporary";
//    String IMAGE_FORMAL_URL = PATH + "/formal";
//    String IMAGE_TEXT_PATH = "/usr/local/imageText";

    //aimeipin
    String WX_APP_ID = "wxf3d017eaf0b53463";
    String WX_SECRET = "d6914a211b5175f62cc5dafb389cd074";
    String PATH = "http://www.aimeipin.cc";
    String HOME = "http://www.aimeipin.cc";
    String DOMAIN = "aimeipin.cc";
    String COMMODITY_INDEX_PATH = "/Users/luanpeng/MyProject/lucene/CommodityIndex";
    String MQ_HOST = "localhost:61616";
    String KEY_STORE = "/usr/local/cert/apiclient_cert.p12";
    String IMAGE_TEMPORARY_PATH = "/usr/local/imageTemporary";
    String IMAGE_FORMAL_PATH = "/usr/local/imageFormal";
    String IMAGE_TEMPORARY_URL = PATH + "/temporary";
    String IMAGE_FORMAL_URL = PATH + "/formal";
    String IMAGE_TEXT_PATH = "/usr/local/imageText";

    //test
    Boolean DEBUG = Boolean.parseBoolean(System.getenv("debug"));
    String TEST_OPENID = "obfnSs5n7HU9nRzfGwdJ0NEM3blQ"; //luotao
}
