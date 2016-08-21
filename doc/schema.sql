-- MySQL dump 10.13  Distrib 5.6.29, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: aimeipin
-- ------------------------------------------------------
-- Server version	5.6.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `backend_user`
--

DROP TABLE IF EXISTS `backend_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `backend_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `flag` int(11) NOT NULL DEFAULT '0' COMMENT '0 普通管理员  1超级管理员',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT ' 0 未激活 1激活',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `buy_notice`
--

DROP TABLE IF EXISTS `buy_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buy_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text,
  `flag` tinyint(1) DEFAULT NULL COMMENT '1 拼团 2福袋 3特惠 4咨询',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dic_city`
--

DROP TABLE IF EXISTS `dic_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dic_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `city_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=347 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dic_province`
--

DROP TABLE IF EXISTS `dic_province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dic_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `flag` char(1) DEFAULT '0' COMMENT '0 非锁定  1 锁定',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dic_tag`
--

DROP TABLE IF EXISTS `dic_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dic_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flag` tinyint(1) DEFAULT NULL COMMENT '1 项目标签  2 渠道 ',
  `name` varchar(1000) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `index_image`
--

DROP TABLE IF EXISTS `index_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `index_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_name` varchar(255) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL COMMENT '链接',
  `weight` tinyint(2) DEFAULT '0' COMMENT '首页轮播图 显示顺序 权重',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interest_commodity`
--

DROP TABLE IF EXISTS `interest_commodity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interest_commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interest_commodity_son`
--

DROP TABLE IF EXISTS `interest_commodity_son`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interest_commodity_son` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `md_commodity`
--

DROP TABLE IF EXISTS `md_commodity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `md_commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_code` varchar(16) DEFAULT '' COMMENT '项目编码',
  `province_id` int(11) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `flag` int(1) NOT NULL DEFAULT '0' COMMENT '0 普通项目 1拼团  2福袋 3特惠 4咨询',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `keyword` varchar(255) NOT NULL,
  `price` int(11) DEFAULT NULL COMMENT '项目原价＝商品原价之和',
  `unit` varchar(255) DEFAULT NULL COMMENT '单位 例如 ml  次 疗程',
  `discount_unit` varchar(255) DEFAULT NULL COMMENT '单位 例如 ml  次 疗程',
  `discount` float(5,2) DEFAULT NULL COMMENT '折扣',
  `discount_price` int(11) DEFAULT NULL COMMENT '折后价',
  `deposit` int(11) DEFAULT '0' COMMENT '项目订金',
  `commodity_number` int(3) DEFAULT '0' COMMENT '商品总量  0为不限量',
  `sold` int(3) DEFAULT '0' COMMENT '已售',
  `custom_sold` int(3) DEFAULT '0' COMMENT '自定义已售',
  `state` int(1) DEFAULT '0' COMMENT '状态 -1  删除  0 未上架  1 上架',
  `start_date` date DEFAULT NULL COMMENT '为空则表示不限',
  `end_date` date DEFAULT NULL COMMENT '为空则表示不限',
  `tags` varchar(255) NOT NULL COMMENT '标签id以逗号隔开',
  `people_number` int(1) DEFAULT '0' COMMENT '拼团人数 只有拼团才有',
  `alone_price` int(11) DEFAULT '0' COMMENT '拼团一人购买价格  只有拼团项目才有',
  `label_flag` int(1) DEFAULT '0' COMMENT '0默认 1最新  2最热 3限时 4限量',
  `case_url` varchar(255) DEFAULT NULL COMMENT '案例分析链接',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` text,
  `weight` int(11) NOT NULL DEFAULT '2' COMMENT '权重',
  `alone_price_double` double NOT NULL DEFAULT '0',
  `deposit_double` double NOT NULL DEFAULT '0',
  `discount_price_double` double NOT NULL DEFAULT '0',
  `price_double` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_g6ywcd596bxf76jtshngdp06e` (`city_id`),
  KEY `FK_o47lh5e57nnkcqge97tfnnmmm` (`province_id`),
  CONSTRAINT `FK_g6ywcd596bxf76jtshngdp06e` FOREIGN KEY (`city_id`) REFERENCES `dic_city` (`id`),
  CONSTRAINT `FK_o47lh5e57nnkcqge97tfnnmmm` FOREIGN KEY (`province_id`) REFERENCES `dic_province` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `add_commodity_code` BEFORE INSERT ON `md_commodity` FOR EACH ROW BEGIN  declare n int;SELECT IFNULL(MAX(RIGHT(commodity_code,4)),0) INTO n FROM md_commodity WHERE MID(commodity_code,3,8)=DATE_FORMAT(CURDATE(),'%Y%m%d');SET NEW.commodity_code= CONCAT('',DATE_FORMAT(CURDATE(),'%Y%m%d'),RIGHT(10001+n,4));END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `md_commodity_photo`
--

DROP TABLE IF EXISTS `md_commodity_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `md_commodity_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_id` int(11) DEFAULT NULL,
  `image_name` varchar(255) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_avoy2aysy3f9u2rqdstyupr8r` (`commodity_id`),
  CONSTRAINT `FK_avoy2aysy3f9u2rqdstyupr8r` FOREIGN KEY (`commodity_id`) REFERENCES `md_commodity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `md_group_launch`
--

DROP TABLE IF EXISTS `md_group_launch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `md_group_launch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `discount_price` int(11) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `pay_amount` int(11) DEFAULT NULL,
  `people_number` int(1) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `unit_price` int(11) DEFAULT NULL COMMENT '单价',
  `unit` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位',
  `commodity_id` int(11) DEFAULT NULL,
  `commodity_number` int(2) DEFAULT NULL,
  `commodity_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `state` int(1) DEFAULT '0' COMMENT '拼团状态  0 发起成功 1拼团成功  2 拼团失败（超时） 3拼团失败（取消订单 包括主动取消 和 超时未消费 被客服取消）',
  `city_id` int(11) DEFAULT NULL,
  `province_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oxa20kcs20b0j7jdvxj331iba` (`city_id`),
  KEY `FK_r53dj081xhknoqk47drbj8w6t` (`province_id`),
  CONSTRAINT `FK_oxa20kcs20b0j7jdvxj331iba` FOREIGN KEY (`city_id`) REFERENCES `dic_city` (`id`),
  CONSTRAINT `FK_r53dj081xhknoqk47drbj8w6t` FOREIGN KEY (`province_id`) REFERENCES `dic_province` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `md_group_launch_user`
--

DROP TABLE IF EXISTS `md_group_launch_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `md_group_launch_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flag` int(11) DEFAULT '0' COMMENT '0 普通参团用户  1 拼团发起人',
  `wx_openid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `launch_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kp5ing6v9jvdshi7ol7k9u2l9` (`launch_id`),
  CONSTRAINT `FK_kp5ing6v9jvdshi7ol7k9u2l9` FOREIGN KEY (`launch_id`) REFERENCES `md_group_launch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `md_order`
--

DROP TABLE IF EXISTS `md_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `md_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(24) DEFAULT '' COMMENT '订单号(退款单号)',
  `refund_code` varchar(24) DEFAULT NULL COMMENT '订单退款编号',
  `refund_id` varchar(255) DEFAULT NULL COMMENT '微信退款单号',
  `transaction_id` varchar(32) DEFAULT NULL COMMENT '微信订单号',
  `wx_openid` varchar(36) NOT NULL,
  `commodity_id` int(11) DEFAULT NULL,
  `ask_id` int(11) DEFAULT NULL COMMENT '如果是咨询订单才有',
  `commodity_name` varchar(255) DEFAULT NULL COMMENT '订单商品名称',
  `province_id` int(11) DEFAULT NULL COMMENT '订单商品的地区',
  `city_id` int(11) DEFAULT NULL COMMENT '订单商品的城市',
  `launch_id` int(11) DEFAULT NULL COMMENT '发起的拼团id（只有订单项目是拼团的才有）',
  `people_number` int(1) DEFAULT '0' COMMENT '拼团不为0',
  `commodity_number` int(11) DEFAULT '0' COMMENT '预定数量',
  `price` int(11) DEFAULT NULL COMMENT '订单总价(原价)',
  `unit_price` int(11) DEFAULT NULL COMMENT '单价',
  `unit` varchar(255) DEFAULT NULL COMMENT '单位',
  `discount` float(5,2) DEFAULT NULL COMMENT '订单折扣 如果是拼团一人支付 则为null',
  `discount_price` int(11) DEFAULT NULL COMMENT '订单折后总价（如果是拼团一人支付 则就是团购的一人价格）',
  `pay_amount` int(11) DEFAULT NULL COMMENT '实付金额(订金总额)',
  `username` varchar(255) DEFAULT NULL COMMENT '登记姓名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '登记手机号',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '支付状态 1 未支付 2已支付  3已预约 4订单完成(加积分 退定金)  5订单取消中 6已取消（已付款的  退款 ）7已取消（已付款的  不退款 ） 8已取消(取消订单 未付款)  9已取消（拼团失败的 退款）',
  `booking_flag` tinyint(1) DEFAULT '3' COMMENT '预订类型 1 发起拼团预订 2 拼团一人预订  3普通预订 4参团预订',
  `flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 普通项目 1拼团  2福袋 3特惠 4 咨询',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remarks` varchar(255) DEFAULT NULL COMMENT '订单备注',
  `weixin` varchar(255) DEFAULT NULL COMMENT '咨询订单微信号',
  `booking_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pq1xtb9loe26x38kpsb7e98f0` (`city_id`),
  KEY `FK_9uovelyq1b0gri5daamkhhhyr` (`province_id`),
  CONSTRAINT `FK_9uovelyq1b0gri5daamkhhhyr` FOREIGN KEY (`province_id`) REFERENCES `dic_province` (`id`),
  CONSTRAINT `FK_pq1xtb9loe26x38kpsb7e98f0` FOREIGN KEY (`city_id`) REFERENCES `dic_city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=512 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `add_order_code` BEFORE INSERT ON `md_order` FOR EACH ROW BEGIN  declare n int;SELECT IFNULL(MAX(LEFT(order_code,4)),0) INTO n FROM md_order WHERE MID(order_code,8,13)=DATE_FORMAT(CURDATE(),'%Y%m%d');SET NEW.order_code= CONCAT('',RIGHT(10001+n,4),ROUND(RAND()*899+100),DATE_FORMAT(CURDATE(),'%Y%m%d'));END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `md_user`
--

DROP TABLE IF EXISTS `md_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `md_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_openid` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `headimgurl` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信头像',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `wx_number` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信号',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别 1男 2女',
  `city_id` int(11) DEFAULT NULL COMMENT '所在城市',
  `channels` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '认识美滴 渠道 以空格隔开',
  `interests` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '感兴趣的医美项目 以空格隔开',
  `wheres` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '去哪做项目  以空格隔开',
  `integral` int(11) DEFAULT '0' COMMENT '积分',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_5qe9lv0mm2qmhluvk8tmb7tar` (`city_id`),
  CONSTRAINT `FK_5qe9lv0mm2qmhluvk8tmb7tar` FOREIGN KEY (`city_id`) REFERENCES `dic_city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1192 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `md_user_favorite`
--

DROP TABLE IF EXISTS `md_user_favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `md_user_favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_openid` varchar(36) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `commodity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `others`
--

DROP TABLE IF EXISTS `others`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `others` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 关于我们  2常见问题',
  `image_name` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_integral`
--

DROP TABLE IF EXISTS `user_integral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_integral` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_openid` varchar(255) NOT NULL COMMENT '用户',
  `order_code` varchar(255) NOT NULL COMMENT '订单编号',
  `integral` int(11) NOT NULL DEFAULT '0' COMMENT '正数为加几分 负数为减积分',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wx_ticket`
--

DROP TABLE IF EXISTS `wx_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wx_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `ticket` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-10 20:30:38
