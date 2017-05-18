-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: origami
-- ------------------------------------------------------
-- Server version	5.5.54-0ubuntu0.12.04.1

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
-- Table structure for table `tb_category`
--

DROP TABLE IF EXISTS `tb_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_category`
--

LOCK TABLES `tb_category` WRITE;
/*!40000 ALTER TABLE `tb_category` DISABLE KEYS */;
INSERT INTO `tb_category` VALUES (29,'动物',1),(30,'植物',2),(31,'昆虫',3),(32,'交通',4);
/*!40000 ALTER TABLE `tb_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_image`
--

DROP TABLE IF EXISTS `tb_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `model_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` bigint(20) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tb_model` (`model_id`),
  CONSTRAINT `fk_tb_model` FOREIGN KEY (`model_id`) REFERENCES `tb_model` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=270 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_image`
--

LOCK TABLES `tb_image` WRITE;
/*!40000 ALTER TABLE `tb_image` DISABLE KEYS */;
INSERT INTO `tb_image` VALUES (176,17,'17_5.png',5,NULL),(177,17,'17_3.png',3,NULL),(178,17,'17_2.png',2,NULL),(179,17,'17_1.png',1,NULL),(180,17,'17_4.png',4,NULL),(181,17,'17_6.png',6,NULL),(182,17,'17_8.png',8,NULL),(183,17,'17_9.png',9,NULL),(184,17,'17_7.png',7,NULL),(185,18,'18_2.png',2,NULL),(186,18,'18_1.png',1,NULL),(187,18,'18_7.png',7,NULL),(188,18,'18_8.png',8,NULL),(189,18,'18_4.png',4,NULL),(190,18,'18_9.png',9,NULL),(191,18,'18_6.png',6,NULL),(192,18,'18_5.png',5,NULL),(193,18,'18_3.png',3,NULL),(194,18,'18_10.png',10,NULL),(195,19,'19_2.png',2,NULL),(196,19,'19_1.png',1,NULL),(197,19,'19_3.png',3,NULL),(198,19,'19_5.png',5,NULL),(199,19,'19_0.png',0,NULL),(200,19,'19_4.png',4,NULL),(201,19,'19_6.png',6,NULL),(202,19,'19_8.png',8,NULL),(203,19,'19_7.png',7,NULL),(204,19,'19_9.png',9,NULL),(205,19,'19_11.png',11,NULL),(206,19,'19_15.png',15,NULL),(207,19,'19_10.png',10,NULL),(208,19,'19_16.png',16,NULL),(209,19,'19_13.png',13,NULL),(210,19,'19_12.png',12,NULL),(211,19,'19_17.png',17,NULL),(212,19,'19_18.png',18,NULL),(213,19,'19_14.png',14,NULL),(214,19,'19_19.png',19,NULL),(215,19,'19_21.png',21,NULL),(216,19,'19_20.png',20,NULL),(217,20,'20_1.png',1,NULL),(218,20,'20_4.png',4,NULL),(219,20,'20_2.png',2,NULL),(220,20,'20_5.png',5,NULL),(221,20,'20_6.png',6,NULL),(222,20,'20_3.png',3,NULL),(223,20,'20_7.png',7,NULL),(224,20,'20_8.png',8,NULL),(225,20,'20_9.png',9,NULL),(226,21,'21_2.png',2,NULL),(227,21,'21_1.png',1,NULL),(228,21,'21_5.png',5,NULL),(229,21,'21_3.png',3,NULL),(230,21,'21_4.png',4,NULL),(231,21,'21_6.png',6,NULL),(232,22,'22_2.png',2,NULL),(233,22,'22_1.png',1,NULL),(234,22,'22_7.png',7,NULL),(235,22,'22_5.png',5,NULL),(236,22,'22_4.png',4,NULL),(237,22,'22_6.png',6,NULL),(238,22,'22_3.png',3,NULL),(239,22,'22_8.png',8,NULL),(240,22,'22_9.png',9,NULL),(241,22,'22_10.png',10,NULL),(242,22,'22_13.png',13,NULL),(243,22,'22_11.png',11,NULL),(244,22,'22_12.png',12,NULL),(245,23,'23_1.png',1,NULL),(246,23,'23_2.png',2,NULL),(247,23,'23_3.png',3,NULL),(248,23,'23_5.png',5,NULL),(249,23,'23_6.png',6,NULL),(250,23,'23_4.png',4,NULL),(251,23,'23_7.png',7,NULL),(252,23,'23_8.png',8,NULL),(253,23,'23_9.png',9,NULL),(254,23,'23_10.png',10,NULL),(255,23,'23_11.png',11,NULL),(256,24,'24_2.png',2,NULL),(257,24,'24_4.png',4,NULL),(258,24,'24_3.png',3,NULL),(259,24,'24_5.png',5,NULL),(260,24,'24_6.png',6,NULL),(261,24,'24_1.png',1,NULL),(262,24,'24_7.png',7,NULL),(263,24,'24_8.png',8,NULL),(264,24,'24_9.png',9,NULL),(265,24,'24_10.png',10,NULL),(266,25,'25_4.png',4,NULL),(267,25,'25_1.png',1,NULL),(268,25,'25_3.png',3,NULL),(269,25,'25_2.png',2,NULL);
/*!40000 ALTER TABLE `tb_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_model`
--

DROP TABLE IF EXISTS `tb_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tb_category` (`category_id`),
  CONSTRAINT `fk_tb_category` FOREIGN KEY (`category_id`) REFERENCES `tb_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_model`
--

LOCK TABLES `tb_model` WRITE;
/*!40000 ALTER TABLE `tb_model` DISABLE KEYS */;
INSERT INTO `tb_model` VALUES (17,29,'猫',1),(18,29,'小鸟',2),(19,29,'比卡超',3),(20,30,'树',1),(21,30,'小草',2),(22,30,'四叶草',3),(23,31,'蝴蝶',1),(24,31,'甲虫',2),(25,32,'小船',1);
/*!40000 ALTER TABLE `tb_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'origami'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-18 17:50:49
