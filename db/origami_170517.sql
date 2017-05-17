-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: origami
-- ------------------------------------------------------
-- Server version	5.5.49

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_category`
--

LOCK TABLES `tb_category` WRITE;
/*!40000 ALTER TABLE `tb_category` DISABLE KEYS */;
INSERT INTO `tb_category` VALUES (2,'军事',2),(3,'动物',1),(4,'工具',3),(23,'test9',9),(24,'test10',10),(25,'test4',4),(26,'test5',5),(27,'test6',6),(28,'test11',11);
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
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_image`
--

LOCK TABLES `tb_image` WRITE;
/*!40000 ALTER TABLE `tb_image` DISABLE KEYS */;
INSERT INTO `tb_image` VALUES (166,7,NULL,3,'2f385091-5c7e-406a-ab33-31bf0010d1e9'),(167,8,NULL,2,'f36b93d1-7f72-48ef-aaa0-c8c445420732'),(168,7,NULL,1,'5ec60104-e423-499f-88e5-cca80795b2dc'),(169,7,NULL,NULL,'58ee06a5-06e4-42ed-ace7-79bbb6665361');
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_model`
--

LOCK TABLES `tb_model` WRITE;
/*!40000 ALTER TABLE `tb_model` DISABLE KEYS */;
INSERT INTO `tb_model` VALUES (7,2,'飞机',1),(8,4,'船',2),(12,3,'牛',2),(16,2,'潜艇',5);
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

-- Dump completed on 2017-05-17 17:28:05
