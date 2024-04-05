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
-- Table structure for table `diet`
--

DROP TABLE IF EXISTS `diet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diet` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) DEFAULT NULL,
  `diary` varchar(255) DEFAULT NULL,
  `diet_date` date NOT NULL,
  `is_reviewed` tinyint(1) DEFAULT '0',
  `child_id` int NOT NULL,
  `meal_time` enum('BREAKFAST','LUNCH','DINNER','NONE') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `diet_child_fk` (`child_id`),
  CONSTRAINT `diet_child_fk` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet`
--

LOCK TABLES `diet` WRITE;
/*!40000 ALTER TABLE `diet` DISABLE KEYS */;
INSERT INTO `diet` VALUES (11,'2024-04-01 16:49:26.791763','맛있어요!!','2024-04-01',1,1,'BREAKFAST'),(12,'2024-04-01 16:49:26.794102','좋아요','2024-04-02',1,1,'BREAKFAST'),(13,'2024-04-01 16:49:26.795792','.맛있어요','2024-04-03',1,1,'BREAKFAST'),(14,'2024-04-01 16:49:26.797252','아이가 고등어를 별로 안좋아해요','2024-04-04',1,1,'DINNER'),(15,'2024-04-01 16:49:26.798820','zz','2024-04-05',1,1,'BREAKFAST'),(16,'2024-04-01 16:49:26.800423','우리 아이는 오이를 싫어해요. 근데 참치랑 계란은 또 잘먹군요','2024-04-06',1,1,'DINNER'),(17,'2024-04-01 16:49:26.801990','우리 아이가 좋아하는 식단!','2024-04-07',1,1,'BREAKFAST'),(18,'2024-04-01 20:58:59.312583',NULL,'2024-04-01',0,8,'NONE'),(19,'2024-04-01 20:58:59.317647',NULL,'2024-04-01',0,8,'NONE'),(20,'2024-04-01 20:58:59.319266',NULL,'2024-04-02',0,8,'NONE'),(21,'2024-04-01 20:58:59.320640','시금치를 안먹네.','2024-04-03',1,8,'DINNER'),(22,'2024-04-01 20:58:59.322173',NULL,'2024-04-04',0,8,'NONE'),(23,'2024-04-01 20:58:59.323929',NULL,'2024-04-05',0,8,'NONE'),(24,'2024-04-01 20:58:59.326103',NULL,'2024-04-06',0,8,'NONE'),(25,'2024-04-01 20:58:59.327522',NULL,'2024-04-07',0,8,'NONE'),(148,'2024-04-02 16:19:05.069989','.','2024-04-02',1,28,'BREAKFAST'),(149,'2024-04-02 16:19:05.075023',NULL,'2024-04-03',0,28,'NONE'),(150,'2024-04-02 16:19:05.076667',NULL,'2024-04-04',0,28,'NONE'),(151,'2024-04-02 16:19:05.078372',NULL,'2024-04-05',0,28,'NONE'),(152,'2024-04-02 16:19:05.080151',NULL,'2024-04-06',0,28,'NONE'),(153,'2024-04-02 16:19:05.081743',NULL,'2024-04-07',0,28,'NONE'),(154,'2024-04-02 16:19:05.086086',NULL,'2024-04-08',0,28,'NONE'),(155,'2024-04-03 09:00:45.880618','애가 마늘을 안먹어요... ㅠㅠ','2024-04-03',1,30,'BREAKFAST'),(156,'2024-04-03 09:00:45.887806',NULL,'2024-04-04',0,30,'NONE'),(157,'2024-04-03 09:00:45.889574',NULL,'2024-04-05',0,30,'NONE'),(158,'2024-04-03 09:00:45.895288',NULL,'2024-04-06',0,30,'NONE'),(159,'2024-04-03 09:00:45.897037',NULL,'2024-04-07',0,30,'NONE'),(160,'2024-04-03 09:00:45.898859',NULL,'2024-04-08',0,30,'NONE'),(161,'2024-04-03 09:00:45.900559',NULL,'2024-04-09',0,30,'NONE'),(171,'2024-04-03 13:12:12.817611','DD','2024-04-03',1,40,'BREAKFAST'),(172,'2024-04-03 13:12:12.862617',NULL,'2024-04-03',0,40,'NONE'),(173,'2024-04-03 13:12:12.911551',NULL,'2024-04-04',0,40,'NONE'),(174,'2024-04-03 13:12:12.966850',NULL,'2024-04-05',0,40,'NONE'),(175,'2024-04-03 13:12:13.004960',NULL,'2024-04-06',0,40,'NONE'),(176,'2024-04-03 13:12:13.034454',NULL,'2024-04-07',0,40,'NONE'),(177,'2024-04-03 13:12:13.060104',NULL,'2024-04-08',0,40,'NONE'),(178,'2024-04-03 13:12:13.098295',NULL,'2024-04-09',0,40,'NONE'),(219,'2024-04-03 13:37:56.092993','피망을 잘 안먹었다.','2024-04-03',1,37,'BREAKFAST'),(220,'2024-04-03 13:37:56.118724',NULL,'2024-04-03',0,37,'NONE'),(221,'2024-04-03 13:37:56.138389','닭백숙을 잘 먹네~','2024-04-03',1,37,'LUNCH'),(222,'2024-04-03 13:37:56.162451',NULL,'2024-04-04',0,37,'NONE'),(223,'2024-04-03 13:37:56.185301',NULL,'2024-04-05',0,37,'NONE'),(224,'2024-04-03 13:37:56.206841',NULL,'2024-04-05',0,37,'NONE'),(225,'2024-04-03 13:37:56.223089',NULL,'2024-04-06',0,37,'NONE'),(226,'2024-04-03 13:37:56.245945',NULL,'2024-04-07',0,37,'NONE'),(227,'2024-04-03 13:37:56.267969',NULL,'2024-04-08',0,37,'NONE'),(228,'2024-04-03 13:37:56.292458',NULL,'2024-04-09',0,37,'NONE'),(229,'2024-04-03 14:01:40.152538',NULL,'2024-04-03',0,43,'NONE'),(230,'2024-04-03 14:01:40.177305',NULL,'2024-04-03',0,43,'NONE'),(231,'2024-04-03 14:01:40.200191',NULL,'2024-04-03',0,43,'NONE'),(232,'2024-04-03 14:01:40.228697',NULL,'2024-04-04',0,43,'NONE'),(233,'2024-04-03 14:01:40.254983',NULL,'2024-04-05',0,43,'NONE'),(234,'2024-04-03 14:01:40.276824',NULL,'2024-04-06',0,43,'NONE'),(235,'2024-04-03 14:01:40.300128',NULL,'2024-04-07',0,43,'NONE'),(236,'2024-04-03 14:01:40.325323',NULL,'2024-04-08',0,43,'NONE'),(237,'2024-04-03 14:01:40.349890',NULL,'2024-04-09',0,43,'NONE'),(238,'2024-04-03 14:23:15.422876','냠냠','2024-04-03',1,45,'LUNCH'),(239,'2024-04-03 14:23:15.441290',NULL,'2024-04-04',0,45,'NONE'),(240,'2024-04-03 14:23:15.462368',NULL,'2024-04-05',0,45,'NONE'),(241,'2024-04-03 14:23:15.479774',NULL,'2024-04-06',0,45,'NONE'),(242,'2024-04-03 14:23:15.501007',NULL,'2024-04-07',0,45,'NONE'),(243,'2024-04-03 14:23:15.521072',NULL,'2024-04-08',0,45,'NONE'),(244,'2024-04-03 14:23:15.539497',NULL,'2024-04-09',0,45,'NONE'),(272,'2024-04-04 14:34:34.511304','마늘 잘 안먹어','2024-04-04',1,60,'BREAKFAST'),(273,'2024-04-04 14:34:34.537443',NULL,'2024-04-04',0,60,'NONE'),(274,'2024-04-04 14:34:34.560547',NULL,'2024-04-04',0,60,'NONE'),(275,'2024-04-04 14:34:34.590521',NULL,'2024-04-05',0,60,'NONE'),(276,'2024-04-04 14:34:34.615980',NULL,'2024-04-06',0,60,'NONE'),(277,'2024-04-04 14:34:34.638001',NULL,'2024-04-07',0,60,'NONE'),(278,'2024-04-04 14:34:34.664520',NULL,'2024-04-08',0,60,'NONE'),(279,'2024-04-04 14:34:34.690384',NULL,'2024-04-09',0,60,'NONE'),(280,'2024-04-04 14:34:34.713802',NULL,'2024-04-10',0,60,'NONE'),(281,'2024-04-04 15:28:22.957730','What is 안경?','2024-04-04',1,61,'BREAKFAST'),(282,'2024-04-04 15:28:22.976279',NULL,'2024-04-04',0,61,'NONE'),(283,'2024-04-04 15:28:22.997425',NULL,'2024-04-05',0,61,'NONE'),(284,'2024-04-04 15:28:23.020201',NULL,'2024-04-06',0,61,'NONE'),(285,'2024-04-04 15:28:23.040362',NULL,'2024-04-07',0,61,'NONE'),(286,'2024-04-04 15:28:23.062315',NULL,'2024-04-08',0,61,'NONE'),(287,'2024-04-04 15:28:23.079560',NULL,'2024-04-09',0,61,'NONE'),(288,'2024-04-04 15:28:23.105640',NULL,'2024-04-10',0,61,'NONE'),(289,'2024-04-04 16:06:21.103073','ㅇㅇㅇㅇ','2024-04-04',1,62,'BREAKFAST'),(290,'2024-04-04 16:06:21.129269',NULL,'2024-04-05',0,62,'NONE'),(291,'2024-04-04 16:06:21.151744',NULL,'2024-04-06',0,62,'NONE'),(292,'2024-04-04 16:06:21.171688',NULL,'2024-04-07',0,62,'NONE'),(293,'2024-04-04 16:06:21.200184',NULL,'2024-04-08',0,62,'NONE'),(294,'2024-04-04 16:06:21.221794',NULL,'2024-04-09',0,62,'NONE'),(295,'2024-04-04 16:06:21.245228',NULL,'2024-04-10',0,62,'NONE'),(296,'2024-04-04 16:19:00.319251','ㅁㅇㄴㄹ','2024-04-04',1,63,'BREAKFAST'),(297,'2024-04-04 16:19:00.343921',NULL,'2024-04-04',0,63,'NONE'),(298,'2024-04-04 16:19:00.369327',NULL,'2024-04-04',0,63,'NONE'),(299,'2024-04-04 16:19:00.399715',NULL,'2024-04-05',0,63,'NONE'),(300,'2024-04-04 16:19:00.426712',NULL,'2024-04-06',0,63,'NONE'),(301,'2024-04-04 16:19:00.446894',NULL,'2024-04-07',0,63,'NONE'),(302,'2024-04-04 16:19:00.472585',NULL,'2024-04-08',0,63,'NONE'),(303,'2024-04-04 16:19:00.499540',NULL,'2024-04-09',0,63,'NONE'),(304,'2024-04-04 16:19:00.523387',NULL,'2024-04-10',0,63,'NONE');
/*!40000 ALTER TABLE `diet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-05  9:19:47
