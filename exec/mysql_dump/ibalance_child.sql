-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: j10d108.p.ssafy.io    Database: ibalance
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `child`
--

DROP TABLE IF EXISTS `child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `child` (
  `id` int NOT NULL AUTO_INCREMENT,
  `birth_date` date NOT NULL,
  `gender` enum('MALE','FEMALE') NOT NULL,
  `height` double NOT NULL,
  `image_url` varchar(255) DEFAULT '초기 이미지 URL',
  `name` varchar(20) NOT NULL,
  `weight` double NOT NULL,
  `member_id` int NOT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgenoanjyi87wspkgvo2poxjs8` (`member_id`),
  CONSTRAINT `FKgenoanjyi87wspkgvo2poxjs8` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `child`
--

LOCK TABLES `child` WRITE;
/*!40000 ALTER TABLE `child` DISABLE KEYS */;
INSERT INTO `child` VALUES (1,'2018-03-21','MALE',131.3,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile1.png','부수환',41.3,11,'2024-03-29 15:30:03.784294'),(6,'2023-01-01','FEMALE',50,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','수달',20,25,'2024-04-01 12:34:19.961513'),(8,'2021-02-15','MALE',99,'https://ibalance.s3.ap-northeast-2.amazonaws.com/96e20add-1profileImg.PNG','박서준',25,8,'2024-04-01 19:55:51.251385'),(18,'2020-04-09','FEMALE',120,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','김주이',30,21,'2024-04-02 13:27:51.149379'),(28,'2020-01-01','MALE',124,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile1.png','테스터',27,26,'2024-04-02 16:16:13.821976'),(30,'2020-11-22','MALE',110,'https://ibalance.s3.ap-northeast-2.amazonaws.com/df107afd-4SCUBA.jpg','고래고래',20,6,'2024-04-03 08:59:35.023880'),(31,'2021-03-21','FEMALE',150,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','내자녀',65,27,'2024-04-03 09:10:31.491844'),(32,'2021-10-09','FEMALE',130,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','만쥬',25,28,'2024-04-03 09:16:47.210314'),(37,'2020-04-09','FEMALE',111,'https://ibalance.s3.ap-northeast-2.amazonaws.com/5a15ab14-aimage.jpeg','김주이',20.5,29,'2024-04-03 11:27:42.104338'),(40,'2018-06-22','FEMALE',100,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','이주미',10,31,'2024-04-03 13:11:14.058097'),(43,'2018-02-19','MALE',98.3,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile1.png','김상목',21.2,11,'2024-04-03 13:44:26.239808'),(45,'2018-05-05','MALE',50,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile1.png','태풍이',16,32,'2024-04-03 14:22:03.303121'),(48,'2020-04-09','FEMALE',110,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','김주이',20,29,'2024-04-03 15:50:45.939496'),(49,'2020-02-02','MALE',123,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile1.png','정동우',32,23,'2024-04-03 20:37:26.196906'),(50,'2020-02-02','MALE',123,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile1.png','정동우',32,23,'2024-04-03 20:37:26.598855'),(52,'2018-11-10','FEMALE',100,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','나나',20,7,'2024-04-03 22:02:30.124229'),(53,'2020-03-23','MALE',150,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile1.png','황찬준',100,31,'2024-04-04 05:34:07.671497'),(58,'2018-02-03','FEMALE',110.3,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','하이요',30,11,'2024-04-04 13:49:38.810340'),(59,'2017-09-04','FEMALE',122.1,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','김싸피',25.4,24,'2024-04-04 13:54:36.017089'),(60,'2017-09-04','FEMALE',122,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','박싸피',25.4,8,'2024-04-04 14:32:31.194958'),(61,'2019-03-02','FEMALE',110.3,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','현지혜',25,11,'2024-04-04 15:25:35.845980'),(62,'2017-06-06','FEMALE',133,'https://ibalance.s3.ap-northeast-2.amazonaws.com/455089fe-bjenkins-devil.png','김수빈',30,35,'2024-04-04 16:04:30.483928'),(63,'2017-01-01','FEMALE',100,'https://ibalance.s3.ap-northeast-2.amazonaws.com/default_profile2.png','응애응애',21,15,'2024-04-04 16:17:06.151038');
/*!40000 ALTER TABLE `child` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-05  9:19:45
