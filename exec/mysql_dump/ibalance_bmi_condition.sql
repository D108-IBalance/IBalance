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
-- Table structure for table `bmi_condition`
--

DROP TABLE IF EXISTS `bmi_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bmi_condition` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gender` enum('MALE','FEMALE') NOT NULL,
  `grow_month` int NOT NULL,
  `standard` double NOT NULL,
  `weight_condition` enum('LOW_WEIGHT','HIGH_WEIGHT') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=817 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bmi_condition`
--

LOCK TABLES `bmi_condition` WRITE;
/*!40000 ALTER TABLE `bmi_condition` DISABLE KEYS */;
INSERT INTO `bmi_condition` VALUES (1,'MALE',24,14.2,'LOW_WEIGHT'),(2,'MALE',25,14.1,'LOW_WEIGHT'),(3,'MALE',26,14.1,'LOW_WEIGHT'),(4,'MALE',27,14,'LOW_WEIGHT'),(5,'MALE',28,14,'LOW_WEIGHT'),(6,'MALE',29,14,'LOW_WEIGHT'),(7,'MALE',30,13.9,'LOW_WEIGHT'),(8,'MALE',31,13.9,'LOW_WEIGHT'),(9,'MALE',32,13.9,'LOW_WEIGHT'),(10,'MALE',33,13.8,'LOW_WEIGHT'),(11,'MALE',34,13.8,'LOW_WEIGHT'),(12,'MALE',35,13.8,'LOW_WEIGHT'),(13,'MALE',36,14.1,'LOW_WEIGHT'),(14,'MALE',37,14.1,'LOW_WEIGHT'),(15,'MALE',38,14.1,'LOW_WEIGHT'),(16,'MALE',39,14.1,'LOW_WEIGHT'),(17,'MALE',40,14.1,'LOW_WEIGHT'),(18,'MALE',41,14.1,'LOW_WEIGHT'),(19,'MALE',42,14.1,'LOW_WEIGHT'),(20,'MALE',43,14.1,'LOW_WEIGHT'),(21,'MALE',44,14.1,'LOW_WEIGHT'),(22,'MALE',45,14,'LOW_WEIGHT'),(23,'MALE',46,14,'LOW_WEIGHT'),(24,'MALE',47,14,'LOW_WEIGHT'),(25,'MALE',48,14,'LOW_WEIGHT'),(26,'MALE',49,14,'LOW_WEIGHT'),(27,'MALE',50,14,'LOW_WEIGHT'),(28,'MALE',51,14,'LOW_WEIGHT'),(29,'MALE',52,14,'LOW_WEIGHT'),(30,'MALE',53,14,'LOW_WEIGHT'),(31,'MALE',54,14,'LOW_WEIGHT'),(32,'MALE',55,14,'LOW_WEIGHT'),(33,'MALE',56,14,'LOW_WEIGHT'),(34,'MALE',57,14,'LOW_WEIGHT'),(35,'MALE',58,14,'LOW_WEIGHT'),(36,'MALE',59,14,'LOW_WEIGHT'),(37,'MALE',60,14,'LOW_WEIGHT'),(38,'MALE',61,14,'LOW_WEIGHT'),(39,'MALE',62,14,'LOW_WEIGHT'),(40,'MALE',63,14,'LOW_WEIGHT'),(41,'MALE',64,14,'LOW_WEIGHT'),(42,'MALE',65,13.9,'LOW_WEIGHT'),(43,'MALE',66,13.9,'LOW_WEIGHT'),(44,'MALE',67,13.9,'LOW_WEIGHT'),(45,'MALE',68,13.9,'LOW_WEIGHT'),(46,'MALE',69,13.9,'LOW_WEIGHT'),(47,'MALE',70,13.9,'LOW_WEIGHT'),(48,'MALE',71,13.9,'LOW_WEIGHT'),(49,'MALE',72,13.9,'LOW_WEIGHT'),(50,'MALE',73,13.9,'LOW_WEIGHT'),(51,'MALE',74,13.9,'LOW_WEIGHT'),(52,'MALE',75,13.9,'LOW_WEIGHT'),(53,'MALE',76,13.9,'LOW_WEIGHT'),(54,'MALE',77,14,'LOW_WEIGHT'),(55,'MALE',78,14,'LOW_WEIGHT'),(56,'MALE',79,14,'LOW_WEIGHT'),(57,'MALE',80,14,'LOW_WEIGHT'),(58,'MALE',81,14,'LOW_WEIGHT'),(59,'MALE',82,14,'LOW_WEIGHT'),(60,'MALE',83,14,'LOW_WEIGHT'),(61,'MALE',84,14,'LOW_WEIGHT'),(62,'MALE',85,14,'LOW_WEIGHT'),(63,'MALE',86,14,'LOW_WEIGHT'),(64,'MALE',87,14,'LOW_WEIGHT'),(65,'MALE',88,14.1,'LOW_WEIGHT'),(66,'MALE',89,14.1,'LOW_WEIGHT'),(67,'MALE',90,14.1,'LOW_WEIGHT'),(68,'MALE',91,14.1,'LOW_WEIGHT'),(69,'MALE',92,14.1,'LOW_WEIGHT'),(70,'MALE',93,14.1,'LOW_WEIGHT'),(71,'MALE',94,14.1,'LOW_WEIGHT'),(72,'MALE',95,14.2,'LOW_WEIGHT'),(73,'MALE',96,14.2,'LOW_WEIGHT'),(74,'MALE',97,14.2,'LOW_WEIGHT'),(75,'MALE',98,14.2,'LOW_WEIGHT'),(76,'MALE',99,14.2,'LOW_WEIGHT'),(77,'MALE',100,14.3,'LOW_WEIGHT'),(78,'MALE',101,14.3,'LOW_WEIGHT'),(79,'MALE',102,14.3,'LOW_WEIGHT'),(80,'MALE',103,14.3,'LOW_WEIGHT'),(81,'MALE',104,14.3,'LOW_WEIGHT'),(82,'MALE',105,14.4,'LOW_WEIGHT'),(83,'MALE',106,14.4,'LOW_WEIGHT'),(84,'MALE',107,14.4,'LOW_WEIGHT'),(85,'MALE',108,14.4,'LOW_WEIGHT'),(86,'MALE',109,14.5,'LOW_WEIGHT'),(87,'MALE',110,14.5,'LOW_WEIGHT'),(88,'MALE',111,14.5,'LOW_WEIGHT'),(89,'MALE',112,14.5,'LOW_WEIGHT'),(90,'MALE',113,14.6,'LOW_WEIGHT'),(91,'MALE',114,14.6,'LOW_WEIGHT'),(92,'MALE',115,14.6,'LOW_WEIGHT'),(93,'MALE',116,14.6,'LOW_WEIGHT'),(94,'MALE',117,14.7,'LOW_WEIGHT'),(95,'MALE',118,14.7,'LOW_WEIGHT'),(96,'MALE',119,14.7,'LOW_WEIGHT'),(97,'MALE',120,14.7,'LOW_WEIGHT'),(98,'MALE',121,14.8,'LOW_WEIGHT'),(99,'MALE',122,14.8,'LOW_WEIGHT'),(100,'MALE',123,14.8,'LOW_WEIGHT'),(101,'MALE',124,14.9,'LOW_WEIGHT'),(102,'MALE',125,14.9,'LOW_WEIGHT'),(103,'MALE',126,14.9,'LOW_WEIGHT'),(104,'MALE',127,15,'LOW_WEIGHT'),(105,'MALE',128,15,'LOW_WEIGHT'),(106,'MALE',129,15,'LOW_WEIGHT'),(107,'MALE',130,15,'LOW_WEIGHT'),(108,'MALE',131,15.1,'LOW_WEIGHT'),(109,'MALE',132,15.1,'LOW_WEIGHT'),(110,'MALE',133,15.1,'LOW_WEIGHT'),(111,'MALE',134,15.2,'LOW_WEIGHT'),(112,'MALE',135,15.2,'LOW_WEIGHT'),(113,'MALE',136,15.3,'LOW_WEIGHT'),(114,'MALE',137,15.3,'LOW_WEIGHT'),(115,'MALE',138,15.3,'LOW_WEIGHT'),(116,'MALE',139,15.4,'LOW_WEIGHT'),(117,'MALE',140,15.4,'LOW_WEIGHT'),(118,'MALE',141,15.4,'LOW_WEIGHT'),(119,'MALE',142,15.5,'LOW_WEIGHT'),(120,'MALE',143,15.5,'LOW_WEIGHT'),(121,'MALE',144,15.5,'LOW_WEIGHT'),(122,'MALE',145,15.6,'LOW_WEIGHT'),(123,'MALE',146,15.6,'LOW_WEIGHT'),(124,'MALE',147,15.7,'LOW_WEIGHT'),(125,'MALE',148,15.7,'LOW_WEIGHT'),(126,'MALE',149,15.7,'LOW_WEIGHT'),(127,'MALE',150,15.8,'LOW_WEIGHT'),(128,'MALE',151,15.8,'LOW_WEIGHT'),(129,'MALE',152,15.8,'LOW_WEIGHT'),(130,'MALE',153,15.9,'LOW_WEIGHT'),(131,'MALE',154,15.9,'LOW_WEIGHT'),(132,'MALE',155,16,'LOW_WEIGHT'),(133,'MALE',156,16,'LOW_WEIGHT'),(134,'MALE',157,16.1,'LOW_WEIGHT'),(135,'MALE',158,16.1,'LOW_WEIGHT'),(136,'MALE',159,16.1,'LOW_WEIGHT'),(137,'MALE',160,16.2,'LOW_WEIGHT'),(138,'MALE',161,16.2,'LOW_WEIGHT'),(139,'MALE',162,16.3,'LOW_WEIGHT'),(140,'MALE',163,16.3,'LOW_WEIGHT'),(141,'MALE',164,16.3,'LOW_WEIGHT'),(142,'MALE',165,16.4,'LOW_WEIGHT'),(143,'MALE',166,16.4,'LOW_WEIGHT'),(144,'MALE',167,16.5,'LOW_WEIGHT'),(145,'MALE',168,16.5,'LOW_WEIGHT'),(146,'MALE',169,16.6,'LOW_WEIGHT'),(147,'MALE',170,16.6,'LOW_WEIGHT'),(148,'MALE',171,16.7,'LOW_WEIGHT'),(149,'MALE',172,16.7,'LOW_WEIGHT'),(150,'MALE',173,16.7,'LOW_WEIGHT'),(151,'MALE',174,16.8,'LOW_WEIGHT'),(152,'MALE',175,16.8,'LOW_WEIGHT'),(153,'MALE',176,16.9,'LOW_WEIGHT'),(154,'MALE',177,16.9,'LOW_WEIGHT'),(155,'MALE',178,17,'LOW_WEIGHT'),(156,'MALE',179,17,'LOW_WEIGHT'),(157,'MALE',180,17,'LOW_WEIGHT'),(158,'MALE',181,17.1,'LOW_WEIGHT'),(159,'MALE',182,17.1,'LOW_WEIGHT'),(160,'MALE',183,17.2,'LOW_WEIGHT'),(161,'MALE',184,17.2,'LOW_WEIGHT'),(162,'MALE',185,17.2,'LOW_WEIGHT'),(163,'MALE',186,17.3,'LOW_WEIGHT'),(164,'MALE',187,17.3,'LOW_WEIGHT'),(165,'MALE',188,17.4,'LOW_WEIGHT'),(166,'MALE',189,17.4,'LOW_WEIGHT'),(167,'MALE',190,17.4,'LOW_WEIGHT'),(168,'MALE',191,17.5,'LOW_WEIGHT'),(169,'MALE',192,17.5,'LOW_WEIGHT'),(170,'MALE',193,17.5,'LOW_WEIGHT'),(171,'MALE',194,17.6,'LOW_WEIGHT'),(172,'MALE',195,17.6,'LOW_WEIGHT'),(173,'MALE',196,17.6,'LOW_WEIGHT'),(174,'MALE',197,17.7,'LOW_WEIGHT'),(175,'MALE',198,17.7,'LOW_WEIGHT'),(176,'MALE',199,17.7,'LOW_WEIGHT'),(177,'MALE',200,17.8,'LOW_WEIGHT'),(178,'MALE',201,17.8,'LOW_WEIGHT'),(179,'MALE',202,17.8,'LOW_WEIGHT'),(180,'MALE',203,17.9,'LOW_WEIGHT'),(181,'MALE',204,17.9,'LOW_WEIGHT'),(182,'MALE',205,17.9,'LOW_WEIGHT'),(183,'MALE',206,18,'LOW_WEIGHT'),(184,'MALE',207,18,'LOW_WEIGHT'),(185,'MALE',208,18,'LOW_WEIGHT'),(186,'MALE',209,18.1,'LOW_WEIGHT'),(187,'MALE',210,18.1,'LOW_WEIGHT'),(188,'MALE',211,18.1,'LOW_WEIGHT'),(189,'MALE',212,18.1,'LOW_WEIGHT'),(190,'MALE',213,18.2,'LOW_WEIGHT'),(191,'MALE',214,18.2,'LOW_WEIGHT'),(192,'MALE',215,18.2,'LOW_WEIGHT'),(193,'MALE',216,18.3,'LOW_WEIGHT'),(194,'MALE',217,18.3,'LOW_WEIGHT'),(195,'MALE',218,18.3,'LOW_WEIGHT'),(196,'MALE',219,18.3,'LOW_WEIGHT'),(197,'MALE',220,18.4,'LOW_WEIGHT'),(198,'MALE',221,18.4,'LOW_WEIGHT'),(199,'MALE',222,18.4,'LOW_WEIGHT'),(200,'MALE',223,18.5,'LOW_WEIGHT'),(201,'MALE',224,18.5,'LOW_WEIGHT'),(202,'MALE',225,18.5,'LOW_WEIGHT'),(203,'MALE',226,18.5,'LOW_WEIGHT'),(204,'MALE',227,18.6,'LOW_WEIGHT'),(205,'MALE',24,17.4,'HIGH_WEIGHT'),(206,'MALE',25,17.4,'HIGH_WEIGHT'),(207,'MALE',26,17.3,'HIGH_WEIGHT'),(208,'MALE',27,17.3,'HIGH_WEIGHT'),(209,'MALE',28,17.2,'HIGH_WEIGHT'),(210,'MALE',29,17.2,'HIGH_WEIGHT'),(211,'MALE',30,17.2,'HIGH_WEIGHT'),(212,'MALE',31,17.1,'HIGH_WEIGHT'),(213,'MALE',32,17.1,'HIGH_WEIGHT'),(214,'MALE',33,17,'HIGH_WEIGHT'),(215,'MALE',34,17,'HIGH_WEIGHT'),(216,'MALE',35,17,'HIGH_WEIGHT'),(217,'MALE',36,17,'HIGH_WEIGHT'),(218,'MALE',37,17,'HIGH_WEIGHT'),(219,'MALE',38,17,'HIGH_WEIGHT'),(220,'MALE',39,17.1,'HIGH_WEIGHT'),(221,'MALE',40,17.1,'HIGH_WEIGHT'),(222,'MALE',41,17.1,'HIGH_WEIGHT'),(223,'MALE',42,17.1,'HIGH_WEIGHT'),(224,'MALE',43,17.1,'HIGH_WEIGHT'),(225,'MALE',44,17.1,'HIGH_WEIGHT'),(226,'MALE',45,17.1,'HIGH_WEIGHT'),(227,'MALE',46,17.1,'HIGH_WEIGHT'),(228,'MALE',47,17.2,'HIGH_WEIGHT'),(229,'MALE',48,17.2,'HIGH_WEIGHT'),(230,'MALE',49,17.2,'HIGH_WEIGHT'),(231,'MALE',50,17.2,'HIGH_WEIGHT'),(232,'MALE',51,17.2,'HIGH_WEIGHT'),(233,'MALE',52,17.2,'HIGH_WEIGHT'),(234,'MALE',53,17.2,'HIGH_WEIGHT'),(235,'MALE',54,17.3,'HIGH_WEIGHT'),(236,'MALE',55,17.3,'HIGH_WEIGHT'),(237,'MALE',56,17.3,'HIGH_WEIGHT'),(238,'MALE',57,17.3,'HIGH_WEIGHT'),(239,'MALE',58,17.3,'HIGH_WEIGHT'),(240,'MALE',59,17.3,'HIGH_WEIGHT'),(241,'MALE',60,17.4,'HIGH_WEIGHT'),(242,'MALE',61,17.4,'HIGH_WEIGHT'),(243,'MALE',62,17.4,'HIGH_WEIGHT'),(244,'MALE',63,17.4,'HIGH_WEIGHT'),(245,'MALE',64,17.4,'HIGH_WEIGHT'),(246,'MALE',65,17.5,'HIGH_WEIGHT'),(247,'MALE',66,17.5,'HIGH_WEIGHT'),(248,'MALE',67,17.5,'HIGH_WEIGHT'),(249,'MALE',68,17.5,'HIGH_WEIGHT'),(250,'MALE',69,17.5,'HIGH_WEIGHT'),(251,'MALE',70,17.6,'HIGH_WEIGHT'),(252,'MALE',71,17.6,'HIGH_WEIGHT'),(253,'MALE',72,17.6,'HIGH_WEIGHT'),(254,'MALE',73,17.7,'HIGH_WEIGHT'),(255,'MALE',74,17.7,'HIGH_WEIGHT'),(256,'MALE',75,17.8,'HIGH_WEIGHT'),(257,'MALE',76,17.8,'HIGH_WEIGHT'),(258,'MALE',77,17.9,'HIGH_WEIGHT'),(259,'MALE',78,18,'HIGH_WEIGHT'),(260,'MALE',79,18,'HIGH_WEIGHT'),(261,'MALE',80,18.1,'HIGH_WEIGHT'),(262,'MALE',81,18.1,'HIGH_WEIGHT'),(263,'MALE',82,18.2,'HIGH_WEIGHT'),(264,'MALE',83,18.2,'HIGH_WEIGHT'),(265,'MALE',84,18.3,'HIGH_WEIGHT'),(266,'MALE',85,18.4,'HIGH_WEIGHT'),(267,'MALE',86,18.4,'HIGH_WEIGHT'),(268,'MALE',87,18.5,'HIGH_WEIGHT'),(269,'MALE',88,18.6,'HIGH_WEIGHT'),(270,'MALE',89,18.6,'HIGH_WEIGHT'),(271,'MALE',90,18.7,'HIGH_WEIGHT'),(272,'MALE',91,18.8,'HIGH_WEIGHT'),(273,'MALE',92,18.9,'HIGH_WEIGHT'),(274,'MALE',93,18.9,'HIGH_WEIGHT'),(275,'MALE',94,19,'HIGH_WEIGHT'),(276,'MALE',95,19.1,'HIGH_WEIGHT'),(277,'MALE',96,19.2,'HIGH_WEIGHT'),(278,'MALE',97,19.2,'HIGH_WEIGHT'),(279,'MALE',98,19.3,'HIGH_WEIGHT'),(280,'MALE',99,19.4,'HIGH_WEIGHT'),(281,'MALE',100,19.5,'HIGH_WEIGHT'),(282,'MALE',101,19.6,'HIGH_WEIGHT'),(283,'MALE',102,19.7,'HIGH_WEIGHT'),(284,'MALE',103,19.8,'HIGH_WEIGHT'),(285,'MALE',104,19.8,'HIGH_WEIGHT'),(286,'MALE',105,19.9,'HIGH_WEIGHT'),(287,'MALE',106,20,'HIGH_WEIGHT'),(288,'MALE',107,20.1,'HIGH_WEIGHT'),(289,'MALE',108,20.2,'HIGH_WEIGHT'),(290,'MALE',109,20.3,'HIGH_WEIGHT'),(291,'MALE',110,20.4,'HIGH_WEIGHT'),(292,'MALE',111,20.5,'HIGH_WEIGHT'),(293,'MALE',112,20.5,'HIGH_WEIGHT'),(294,'MALE',113,20.6,'HIGH_WEIGHT'),(295,'MALE',114,20.7,'HIGH_WEIGHT'),(296,'MALE',115,20.8,'HIGH_WEIGHT'),(297,'MALE',116,20.9,'HIGH_WEIGHT'),(298,'MALE',117,21,'HIGH_WEIGHT'),(299,'MALE',118,21.1,'HIGH_WEIGHT'),(300,'MALE',119,21.1,'HIGH_WEIGHT'),(301,'MALE',120,21.2,'HIGH_WEIGHT'),(302,'MALE',121,21.3,'HIGH_WEIGHT'),(303,'MALE',122,21.4,'HIGH_WEIGHT'),(304,'MALE',123,21.5,'HIGH_WEIGHT'),(305,'MALE',124,21.6,'HIGH_WEIGHT'),(306,'MALE',125,21.6,'HIGH_WEIGHT'),(307,'MALE',126,21.7,'HIGH_WEIGHT'),(308,'MALE',127,21.8,'HIGH_WEIGHT'),(309,'MALE',128,21.9,'HIGH_WEIGHT'),(310,'MALE',129,22,'HIGH_WEIGHT'),(311,'MALE',130,22,'HIGH_WEIGHT'),(312,'MALE',131,22.1,'HIGH_WEIGHT'),(313,'MALE',132,22.2,'HIGH_WEIGHT'),(314,'MALE',133,22.3,'HIGH_WEIGHT'),(315,'MALE',134,22.3,'HIGH_WEIGHT'),(316,'MALE',135,22.4,'HIGH_WEIGHT'),(317,'MALE',136,22.5,'HIGH_WEIGHT'),(318,'MALE',137,22.5,'HIGH_WEIGHT'),(319,'MALE',138,22.6,'HIGH_WEIGHT'),(320,'MALE',139,22.7,'HIGH_WEIGHT'),(321,'MALE',140,22.7,'HIGH_WEIGHT'),(322,'MALE',141,22.8,'HIGH_WEIGHT'),(323,'MALE',142,22.9,'HIGH_WEIGHT'),(324,'MALE',143,22.9,'HIGH_WEIGHT'),(325,'MALE',144,23,'HIGH_WEIGHT'),(326,'MALE',145,23,'HIGH_WEIGHT'),(327,'MALE',146,23.1,'HIGH_WEIGHT'),(328,'MALE',147,23.1,'HIGH_WEIGHT'),(329,'MALE',148,23.2,'HIGH_WEIGHT'),(330,'MALE',149,23.2,'HIGH_WEIGHT'),(331,'MALE',150,23.3,'HIGH_WEIGHT'),(332,'MALE',151,23.3,'HIGH_WEIGHT'),(333,'MALE',152,23.4,'HIGH_WEIGHT'),(334,'MALE',153,23.4,'HIGH_WEIGHT'),(335,'MALE',154,23.5,'HIGH_WEIGHT'),(336,'MALE',155,23.5,'HIGH_WEIGHT'),(337,'MALE',156,23.6,'HIGH_WEIGHT'),(338,'MALE',157,23.6,'HIGH_WEIGHT'),(339,'MALE',158,23.6,'HIGH_WEIGHT'),(340,'MALE',159,23.7,'HIGH_WEIGHT'),(341,'MALE',160,23.7,'HIGH_WEIGHT'),(342,'MALE',161,23.7,'HIGH_WEIGHT'),(343,'MALE',162,23.8,'HIGH_WEIGHT'),(344,'MALE',163,23.8,'HIGH_WEIGHT'),(345,'MALE',164,23.8,'HIGH_WEIGHT'),(346,'MALE',165,23.9,'HIGH_WEIGHT'),(347,'MALE',166,23.9,'HIGH_WEIGHT'),(348,'MALE',167,23.9,'HIGH_WEIGHT'),(349,'MALE',168,23.9,'HIGH_WEIGHT'),(350,'MALE',169,24,'HIGH_WEIGHT'),(351,'MALE',170,24,'HIGH_WEIGHT'),(352,'MALE',171,24,'HIGH_WEIGHT'),(353,'MALE',172,24,'HIGH_WEIGHT'),(354,'MALE',173,24.1,'HIGH_WEIGHT'),(355,'MALE',174,24.1,'HIGH_WEIGHT'),(356,'MALE',175,24.1,'HIGH_WEIGHT'),(357,'MALE',176,24.1,'HIGH_WEIGHT'),(358,'MALE',177,24.2,'HIGH_WEIGHT'),(359,'MALE',178,24.2,'HIGH_WEIGHT'),(360,'MALE',179,24.2,'HIGH_WEIGHT'),(361,'MALE',180,24.2,'HIGH_WEIGHT'),(362,'MALE',181,24.3,'HIGH_WEIGHT'),(363,'MALE',182,24.3,'HIGH_WEIGHT'),(364,'MALE',183,24.3,'HIGH_WEIGHT'),(365,'MALE',184,24.3,'HIGH_WEIGHT'),(366,'MALE',185,24.4,'HIGH_WEIGHT'),(367,'MALE',186,24.4,'HIGH_WEIGHT'),(368,'MALE',187,24.4,'HIGH_WEIGHT'),(369,'MALE',188,24.4,'HIGH_WEIGHT'),(370,'MALE',189,24.5,'HIGH_WEIGHT'),(371,'MALE',190,24.5,'HIGH_WEIGHT'),(372,'MALE',191,24.5,'HIGH_WEIGHT'),(373,'MALE',192,24.5,'HIGH_WEIGHT'),(374,'MALE',193,24.6,'HIGH_WEIGHT'),(375,'MALE',194,24.6,'HIGH_WEIGHT'),(376,'MALE',195,24.6,'HIGH_WEIGHT'),(377,'MALE',196,24.6,'HIGH_WEIGHT'),(378,'MALE',197,24.7,'HIGH_WEIGHT'),(379,'MALE',198,24.7,'HIGH_WEIGHT'),(380,'MALE',199,24.7,'HIGH_WEIGHT'),(381,'MALE',200,24.7,'HIGH_WEIGHT'),(382,'MALE',201,24.7,'HIGH_WEIGHT'),(383,'MALE',202,24.8,'HIGH_WEIGHT'),(384,'MALE',203,24.8,'HIGH_WEIGHT'),(385,'MALE',204,24.8,'HIGH_WEIGHT'),(386,'MALE',205,24.8,'HIGH_WEIGHT'),(387,'MALE',206,24.9,'HIGH_WEIGHT'),(388,'MALE',207,24.9,'HIGH_WEIGHT'),(389,'MALE',208,24.9,'HIGH_WEIGHT'),(390,'MALE',209,24.9,'HIGH_WEIGHT'),(391,'MALE',210,25,'HIGH_WEIGHT'),(392,'MALE',211,25,'HIGH_WEIGHT'),(393,'MALE',212,25,'HIGH_WEIGHT'),(394,'MALE',213,25,'HIGH_WEIGHT'),(395,'MALE',214,25.1,'HIGH_WEIGHT'),(396,'MALE',215,25.1,'HIGH_WEIGHT'),(397,'MALE',216,25.1,'HIGH_WEIGHT'),(398,'MALE',217,25.1,'HIGH_WEIGHT'),(399,'MALE',218,25.2,'HIGH_WEIGHT'),(400,'MALE',219,25.2,'HIGH_WEIGHT'),(401,'MALE',220,25.2,'HIGH_WEIGHT'),(402,'MALE',221,25.2,'HIGH_WEIGHT'),(403,'MALE',222,25.2,'HIGH_WEIGHT'),(404,'MALE',223,25.3,'HIGH_WEIGHT'),(405,'MALE',224,25.3,'HIGH_WEIGHT'),(406,'MALE',225,25.3,'HIGH_WEIGHT'),(407,'MALE',226,25.3,'HIGH_WEIGHT'),(408,'MALE',227,25.4,'HIGH_WEIGHT'),(409,'FEMALE',24,13.7,'LOW_WEIGHT'),(410,'FEMALE',25,13.7,'LOW_WEIGHT'),(411,'FEMALE',26,13.7,'LOW_WEIGHT'),(412,'FEMALE',27,13.7,'LOW_WEIGHT'),(413,'FEMALE',28,13.6,'LOW_WEIGHT'),(414,'FEMALE',29,13.6,'LOW_WEIGHT'),(415,'FEMALE',30,13.6,'LOW_WEIGHT'),(416,'FEMALE',31,13.6,'LOW_WEIGHT'),(417,'FEMALE',32,13.5,'LOW_WEIGHT'),(418,'FEMALE',33,13.5,'LOW_WEIGHT'),(419,'FEMALE',34,13.5,'LOW_WEIGHT'),(420,'FEMALE',35,13.5,'LOW_WEIGHT'),(421,'FEMALE',36,13.9,'LOW_WEIGHT'),(422,'FEMALE',37,13.9,'LOW_WEIGHT'),(423,'FEMALE',38,13.9,'LOW_WEIGHT'),(424,'FEMALE',39,13.9,'LOW_WEIGHT'),(425,'FEMALE',40,13.9,'LOW_WEIGHT'),(426,'FEMALE',41,13.9,'LOW_WEIGHT'),(427,'FEMALE',42,13.9,'LOW_WEIGHT'),(428,'FEMALE',43,13.9,'LOW_WEIGHT'),(429,'FEMALE',44,13.8,'LOW_WEIGHT'),(430,'FEMALE',45,13.8,'LOW_WEIGHT'),(431,'FEMALE',46,13.8,'LOW_WEIGHT'),(432,'FEMALE',47,13.8,'LOW_WEIGHT'),(433,'FEMALE',48,13.8,'LOW_WEIGHT'),(434,'FEMALE',49,13.8,'LOW_WEIGHT'),(435,'FEMALE',50,13.8,'LOW_WEIGHT'),(436,'FEMALE',51,13.8,'LOW_WEIGHT'),(437,'FEMALE',52,13.8,'LOW_WEIGHT'),(438,'FEMALE',53,13.8,'LOW_WEIGHT'),(439,'FEMALE',54,13.8,'LOW_WEIGHT'),(440,'FEMALE',55,13.8,'LOW_WEIGHT'),(441,'FEMALE',56,13.8,'LOW_WEIGHT'),(442,'FEMALE',57,13.8,'LOW_WEIGHT'),(443,'FEMALE',58,13.7,'LOW_WEIGHT'),(444,'FEMALE',59,13.7,'LOW_WEIGHT'),(445,'FEMALE',60,13.7,'LOW_WEIGHT'),(446,'FEMALE',61,13.7,'LOW_WEIGHT'),(447,'FEMALE',62,13.7,'LOW_WEIGHT'),(448,'FEMALE',63,13.7,'LOW_WEIGHT'),(449,'FEMALE',64,13.7,'LOW_WEIGHT'),(450,'FEMALE',65,13.7,'LOW_WEIGHT'),(451,'FEMALE',66,13.7,'LOW_WEIGHT'),(452,'FEMALE',67,13.7,'LOW_WEIGHT'),(453,'FEMALE',68,13.7,'LOW_WEIGHT'),(454,'FEMALE',69,13.7,'LOW_WEIGHT'),(455,'FEMALE',70,13.7,'LOW_WEIGHT'),(456,'FEMALE',71,13.7,'LOW_WEIGHT'),(457,'FEMALE',72,13.7,'LOW_WEIGHT'),(458,'FEMALE',73,13.7,'LOW_WEIGHT'),(459,'FEMALE',74,13.7,'LOW_WEIGHT'),(460,'FEMALE',75,13.7,'LOW_WEIGHT'),(461,'FEMALE',76,13.7,'LOW_WEIGHT'),(462,'FEMALE',77,13.7,'LOW_WEIGHT'),(463,'FEMALE',78,13.7,'LOW_WEIGHT'),(464,'FEMALE',79,13.7,'LOW_WEIGHT'),(465,'FEMALE',80,13.7,'LOW_WEIGHT'),(466,'FEMALE',81,13.7,'LOW_WEIGHT'),(467,'FEMALE',82,13.7,'LOW_WEIGHT'),(468,'FEMALE',83,13.7,'LOW_WEIGHT'),(469,'FEMALE',84,13.7,'LOW_WEIGHT'),(470,'FEMALE',85,13.7,'LOW_WEIGHT'),(471,'FEMALE',86,13.7,'LOW_WEIGHT'),(472,'FEMALE',87,13.7,'LOW_WEIGHT'),(473,'FEMALE',88,13.7,'LOW_WEIGHT'),(474,'FEMALE',89,13.8,'LOW_WEIGHT'),(475,'FEMALE',90,13.8,'LOW_WEIGHT'),(476,'FEMALE',91,13.8,'LOW_WEIGHT'),(477,'FEMALE',92,13.8,'LOW_WEIGHT'),(478,'FEMALE',93,13.8,'LOW_WEIGHT'),(479,'FEMALE',94,13.8,'LOW_WEIGHT'),(480,'FEMALE',95,13.8,'LOW_WEIGHT'),(481,'FEMALE',96,13.8,'LOW_WEIGHT'),(482,'FEMALE',97,13.9,'LOW_WEIGHT'),(483,'FEMALE',98,13.9,'LOW_WEIGHT'),(484,'FEMALE',99,13.9,'LOW_WEIGHT'),(485,'FEMALE',100,13.9,'LOW_WEIGHT'),(486,'FEMALE',101,13.9,'LOW_WEIGHT'),(487,'FEMALE',102,14,'LOW_WEIGHT'),(488,'FEMALE',103,14,'LOW_WEIGHT'),(489,'FEMALE',104,14,'LOW_WEIGHT'),(490,'FEMALE',105,14,'LOW_WEIGHT'),(491,'FEMALE',106,14,'LOW_WEIGHT'),(492,'FEMALE',107,14.1,'LOW_WEIGHT'),(493,'FEMALE',108,14.1,'LOW_WEIGHT'),(494,'FEMALE',109,14.1,'LOW_WEIGHT'),(495,'FEMALE',110,14.1,'LOW_WEIGHT'),(496,'FEMALE',111,14.2,'LOW_WEIGHT'),(497,'FEMALE',112,14.2,'LOW_WEIGHT'),(498,'FEMALE',113,14.2,'LOW_WEIGHT'),(499,'FEMALE',114,14.2,'LOW_WEIGHT'),(500,'FEMALE',115,14.2,'LOW_WEIGHT'),(501,'FEMALE',116,14.3,'LOW_WEIGHT'),(502,'FEMALE',117,14.3,'LOW_WEIGHT'),(503,'FEMALE',118,14.3,'LOW_WEIGHT'),(504,'FEMALE',119,14.3,'LOW_WEIGHT'),(505,'FEMALE',120,14.4,'LOW_WEIGHT'),(506,'FEMALE',121,14.4,'LOW_WEIGHT'),(507,'FEMALE',122,14.4,'LOW_WEIGHT'),(508,'FEMALE',123,14.5,'LOW_WEIGHT'),(509,'FEMALE',124,14.5,'LOW_WEIGHT'),(510,'FEMALE',125,14.5,'LOW_WEIGHT'),(511,'FEMALE',126,14.6,'LOW_WEIGHT'),(512,'FEMALE',127,14.6,'LOW_WEIGHT'),(513,'FEMALE',128,14.6,'LOW_WEIGHT'),(514,'FEMALE',129,14.7,'LOW_WEIGHT'),(515,'FEMALE',130,14.7,'LOW_WEIGHT'),(516,'FEMALE',131,14.7,'LOW_WEIGHT'),(517,'FEMALE',132,14.8,'LOW_WEIGHT'),(518,'FEMALE',133,14.8,'LOW_WEIGHT'),(519,'FEMALE',134,14.9,'LOW_WEIGHT'),(520,'FEMALE',135,14.9,'LOW_WEIGHT'),(521,'FEMALE',136,14.9,'LOW_WEIGHT'),(522,'FEMALE',137,15,'LOW_WEIGHT'),(523,'FEMALE',138,15,'LOW_WEIGHT'),(524,'FEMALE',139,15.1,'LOW_WEIGHT'),(525,'FEMALE',140,15.1,'LOW_WEIGHT'),(526,'FEMALE',141,15.2,'LOW_WEIGHT'),(527,'FEMALE',142,15.2,'LOW_WEIGHT'),(528,'FEMALE',143,15.2,'LOW_WEIGHT'),(529,'FEMALE',144,15.3,'LOW_WEIGHT'),(530,'FEMALE',145,15.3,'LOW_WEIGHT'),(531,'FEMALE',146,15.4,'LOW_WEIGHT'),(532,'FEMALE',147,15.4,'LOW_WEIGHT'),(533,'FEMALE',148,15.5,'LOW_WEIGHT'),(534,'FEMALE',149,15.5,'LOW_WEIGHT'),(535,'FEMALE',150,15.6,'LOW_WEIGHT'),(536,'FEMALE',151,15.6,'LOW_WEIGHT'),(537,'FEMALE',152,15.7,'LOW_WEIGHT'),(538,'FEMALE',153,15.7,'LOW_WEIGHT'),(539,'FEMALE',154,15.8,'LOW_WEIGHT'),(540,'FEMALE',155,15.8,'LOW_WEIGHT'),(541,'FEMALE',156,15.9,'LOW_WEIGHT'),(542,'FEMALE',157,15.9,'LOW_WEIGHT'),(543,'FEMALE',158,16,'LOW_WEIGHT'),(544,'FEMALE',159,16,'LOW_WEIGHT'),(545,'FEMALE',160,16,'LOW_WEIGHT'),(546,'FEMALE',161,16.1,'LOW_WEIGHT'),(547,'FEMALE',162,16.1,'LOW_WEIGHT'),(548,'FEMALE',163,16.2,'LOW_WEIGHT'),(549,'FEMALE',164,16.2,'LOW_WEIGHT'),(550,'FEMALE',165,16.3,'LOW_WEIGHT'),(551,'FEMALE',166,16.3,'LOW_WEIGHT'),(552,'FEMALE',167,16.4,'LOW_WEIGHT'),(553,'FEMALE',168,16.4,'LOW_WEIGHT'),(554,'FEMALE',169,16.5,'LOW_WEIGHT'),(555,'FEMALE',170,16.5,'LOW_WEIGHT'),(556,'FEMALE',171,16.6,'LOW_WEIGHT'),(557,'FEMALE',172,16.6,'LOW_WEIGHT'),(558,'FEMALE',173,16.6,'LOW_WEIGHT'),(559,'FEMALE',174,16.7,'LOW_WEIGHT'),(560,'FEMALE',175,16.7,'LOW_WEIGHT'),(561,'FEMALE',176,16.8,'LOW_WEIGHT'),(562,'FEMALE',177,16.8,'LOW_WEIGHT'),(563,'FEMALE',178,16.9,'LOW_WEIGHT'),(564,'FEMALE',179,16.9,'LOW_WEIGHT'),(565,'FEMALE',180,16.9,'LOW_WEIGHT'),(566,'FEMALE',181,17,'LOW_WEIGHT'),(567,'FEMALE',182,17,'LOW_WEIGHT'),(568,'FEMALE',183,17,'LOW_WEIGHT'),(569,'FEMALE',184,17.1,'LOW_WEIGHT'),(570,'FEMALE',185,17.1,'LOW_WEIGHT'),(571,'FEMALE',186,17.1,'LOW_WEIGHT'),(572,'FEMALE',187,17.2,'LOW_WEIGHT'),(573,'FEMALE',188,17.2,'LOW_WEIGHT'),(574,'FEMALE',189,17.2,'LOW_WEIGHT'),(575,'FEMALE',190,17.3,'LOW_WEIGHT'),(576,'FEMALE',191,17.3,'LOW_WEIGHT'),(577,'FEMALE',192,17.3,'LOW_WEIGHT'),(578,'FEMALE',193,17.3,'LOW_WEIGHT'),(579,'FEMALE',194,17.3,'LOW_WEIGHT'),(580,'FEMALE',195,17.4,'LOW_WEIGHT'),(581,'FEMALE',196,17.4,'LOW_WEIGHT'),(582,'FEMALE',197,17.4,'LOW_WEIGHT'),(583,'FEMALE',198,17.4,'LOW_WEIGHT'),(584,'FEMALE',199,17.4,'LOW_WEIGHT'),(585,'FEMALE',200,17.4,'LOW_WEIGHT'),(586,'FEMALE',201,17.4,'LOW_WEIGHT'),(587,'FEMALE',202,17.5,'LOW_WEIGHT'),(588,'FEMALE',203,17.5,'LOW_WEIGHT'),(589,'FEMALE',204,17.5,'LOW_WEIGHT'),(590,'FEMALE',205,17.5,'LOW_WEIGHT'),(591,'FEMALE',206,17.5,'LOW_WEIGHT'),(592,'FEMALE',207,17.5,'LOW_WEIGHT'),(593,'FEMALE',208,17.5,'LOW_WEIGHT'),(594,'FEMALE',209,17.5,'LOW_WEIGHT'),(595,'FEMALE',210,17.5,'LOW_WEIGHT'),(596,'FEMALE',211,17.5,'LOW_WEIGHT'),(597,'FEMALE',212,17.6,'LOW_WEIGHT'),(598,'FEMALE',213,17.6,'LOW_WEIGHT'),(599,'FEMALE',214,17.6,'LOW_WEIGHT'),(600,'FEMALE',215,17.6,'LOW_WEIGHT'),(601,'FEMALE',216,17.6,'LOW_WEIGHT'),(602,'FEMALE',217,17.6,'LOW_WEIGHT'),(603,'FEMALE',218,17.6,'LOW_WEIGHT'),(604,'FEMALE',219,17.6,'LOW_WEIGHT'),(605,'FEMALE',220,17.6,'LOW_WEIGHT'),(606,'FEMALE',221,17.6,'LOW_WEIGHT'),(607,'FEMALE',222,17.6,'LOW_WEIGHT'),(608,'FEMALE',223,17.6,'LOW_WEIGHT'),(609,'FEMALE',224,17.6,'LOW_WEIGHT'),(610,'FEMALE',225,17.6,'LOW_WEIGHT'),(611,'FEMALE',226,17.6,'LOW_WEIGHT'),(612,'FEMALE',227,17.7,'LOW_WEIGHT'),(613,'FEMALE',24,17.2,'HIGH_WEIGHT'),(614,'FEMALE',25,17.1,'HIGH_WEIGHT'),(615,'FEMALE',26,17.1,'HIGH_WEIGHT'),(616,'FEMALE',27,17.1,'HIGH_WEIGHT'),(617,'FEMALE',28,17,'HIGH_WEIGHT'),(618,'FEMALE',29,17,'HIGH_WEIGHT'),(619,'FEMALE',30,17,'HIGH_WEIGHT'),(620,'FEMALE',31,17,'HIGH_WEIGHT'),(621,'FEMALE',32,16.9,'HIGH_WEIGHT'),(622,'FEMALE',33,16.9,'HIGH_WEIGHT'),(623,'FEMALE',34,16.9,'HIGH_WEIGHT'),(624,'FEMALE',35,16.9,'HIGH_WEIGHT'),(625,'FEMALE',36,16.8,'HIGH_WEIGHT'),(626,'FEMALE',37,16.8,'HIGH_WEIGHT'),(627,'FEMALE',38,16.9,'HIGH_WEIGHT'),(628,'FEMALE',39,16.9,'HIGH_WEIGHT'),(629,'FEMALE',40,16.9,'HIGH_WEIGHT'),(630,'FEMALE',41,16.9,'HIGH_WEIGHT'),(631,'FEMALE',42,16.9,'HIGH_WEIGHT'),(632,'FEMALE',43,16.9,'HIGH_WEIGHT'),(633,'FEMALE',44,17,'HIGH_WEIGHT'),(634,'FEMALE',45,17,'HIGH_WEIGHT'),(635,'FEMALE',46,17,'HIGH_WEIGHT'),(636,'FEMALE',47,17,'HIGH_WEIGHT'),(637,'FEMALE',48,17,'HIGH_WEIGHT'),(638,'FEMALE',49,17,'HIGH_WEIGHT'),(639,'FEMALE',50,17.1,'HIGH_WEIGHT'),(640,'FEMALE',51,17.1,'HIGH_WEIGHT'),(641,'FEMALE',52,17.1,'HIGH_WEIGHT'),(642,'FEMALE',53,17.1,'HIGH_WEIGHT'),(643,'FEMALE',54,17.1,'HIGH_WEIGHT'),(644,'FEMALE',55,17.1,'HIGH_WEIGHT'),(645,'FEMALE',56,17.2,'HIGH_WEIGHT'),(646,'FEMALE',57,17.2,'HIGH_WEIGHT'),(647,'FEMALE',58,17.2,'HIGH_WEIGHT'),(648,'FEMALE',59,17.2,'HIGH_WEIGHT'),(649,'FEMALE',60,17.2,'HIGH_WEIGHT'),(650,'FEMALE',61,17.3,'HIGH_WEIGHT'),(651,'FEMALE',62,17.3,'HIGH_WEIGHT'),(652,'FEMALE',63,17.3,'HIGH_WEIGHT'),(653,'FEMALE',64,17.3,'HIGH_WEIGHT'),(654,'FEMALE',65,17.3,'HIGH_WEIGHT'),(655,'FEMALE',66,17.4,'HIGH_WEIGHT'),(656,'FEMALE',67,17.4,'HIGH_WEIGHT'),(657,'FEMALE',68,17.4,'HIGH_WEIGHT'),(658,'FEMALE',69,17.4,'HIGH_WEIGHT'),(659,'FEMALE',70,17.4,'HIGH_WEIGHT'),(660,'FEMALE',71,17.5,'HIGH_WEIGHT'),(661,'FEMALE',72,17.5,'HIGH_WEIGHT'),(662,'FEMALE',73,17.6,'HIGH_WEIGHT'),(663,'FEMALE',74,17.6,'HIGH_WEIGHT'),(664,'FEMALE',75,17.7,'HIGH_WEIGHT'),(665,'FEMALE',76,17.7,'HIGH_WEIGHT'),(666,'FEMALE',77,17.7,'HIGH_WEIGHT'),(667,'FEMALE',78,17.8,'HIGH_WEIGHT'),(668,'FEMALE',79,17.8,'HIGH_WEIGHT'),(669,'FEMALE',80,17.9,'HIGH_WEIGHT'),(670,'FEMALE',81,17.9,'HIGH_WEIGHT'),(671,'FEMALE',82,18,'HIGH_WEIGHT'),(672,'FEMALE',83,18,'HIGH_WEIGHT'),(673,'FEMALE',84,18.1,'HIGH_WEIGHT'),(674,'FEMALE',85,18.2,'HIGH_WEIGHT'),(675,'FEMALE',86,18.2,'HIGH_WEIGHT'),(676,'FEMALE',87,18.3,'HIGH_WEIGHT'),(677,'FEMALE',88,18.3,'HIGH_WEIGHT'),(678,'FEMALE',89,18.4,'HIGH_WEIGHT'),(679,'FEMALE',90,18.5,'HIGH_WEIGHT'),(680,'FEMALE',91,18.5,'HIGH_WEIGHT'),(681,'FEMALE',92,18.6,'HIGH_WEIGHT'),(682,'FEMALE',93,18.7,'HIGH_WEIGHT'),(683,'FEMALE',94,18.7,'HIGH_WEIGHT'),(684,'FEMALE',95,18.8,'HIGH_WEIGHT'),(685,'FEMALE',96,18.8,'HIGH_WEIGHT'),(686,'FEMALE',97,18.9,'HIGH_WEIGHT'),(687,'FEMALE',98,19,'HIGH_WEIGHT'),(688,'FEMALE',99,19.1,'HIGH_WEIGHT'),(689,'FEMALE',100,19.1,'HIGH_WEIGHT'),(690,'FEMALE',101,19.2,'HIGH_WEIGHT'),(691,'FEMALE',102,19.3,'HIGH_WEIGHT'),(692,'FEMALE',103,19.3,'HIGH_WEIGHT'),(693,'FEMALE',104,19.4,'HIGH_WEIGHT'),(694,'FEMALE',105,19.5,'HIGH_WEIGHT'),(695,'FEMALE',106,19.6,'HIGH_WEIGHT'),(696,'FEMALE',107,19.6,'HIGH_WEIGHT'),(697,'FEMALE',108,19.7,'HIGH_WEIGHT'),(698,'FEMALE',109,19.8,'HIGH_WEIGHT'),(699,'FEMALE',110,19.8,'HIGH_WEIGHT'),(700,'FEMALE',111,19.9,'HIGH_WEIGHT'),(701,'FEMALE',112,20,'HIGH_WEIGHT'),(702,'FEMALE',113,20.1,'HIGH_WEIGHT'),(703,'FEMALE',114,20.1,'HIGH_WEIGHT'),(704,'FEMALE',115,20.2,'HIGH_WEIGHT'),(705,'FEMALE',116,20.3,'HIGH_WEIGHT'),(706,'FEMALE',117,20.3,'HIGH_WEIGHT'),(707,'FEMALE',118,20.4,'HIGH_WEIGHT'),(708,'FEMALE',119,20.5,'HIGH_WEIGHT'),(709,'FEMALE',120,20.6,'HIGH_WEIGHT'),(710,'FEMALE',121,20.6,'HIGH_WEIGHT'),(711,'FEMALE',122,20.7,'HIGH_WEIGHT'),(712,'FEMALE',123,20.8,'HIGH_WEIGHT'),(713,'FEMALE',124,20.8,'HIGH_WEIGHT'),(714,'FEMALE',125,20.9,'HIGH_WEIGHT'),(715,'FEMALE',126,21,'HIGH_WEIGHT'),(716,'FEMALE',127,21,'HIGH_WEIGHT'),(717,'FEMALE',128,21.1,'HIGH_WEIGHT'),(718,'FEMALE',129,21.2,'HIGH_WEIGHT'),(719,'FEMALE',130,21.2,'HIGH_WEIGHT'),(720,'FEMALE',131,21.3,'HIGH_WEIGHT'),(721,'FEMALE',132,21.4,'HIGH_WEIGHT'),(722,'FEMALE',133,21.4,'HIGH_WEIGHT'),(723,'FEMALE',134,21.5,'HIGH_WEIGHT'),(724,'FEMALE',135,21.6,'HIGH_WEIGHT'),(725,'FEMALE',136,21.6,'HIGH_WEIGHT'),(726,'FEMALE',137,21.7,'HIGH_WEIGHT'),(727,'FEMALE',138,21.8,'HIGH_WEIGHT'),(728,'FEMALE',139,21.8,'HIGH_WEIGHT'),(729,'FEMALE',140,21.9,'HIGH_WEIGHT'),(730,'FEMALE',141,21.9,'HIGH_WEIGHT'),(731,'FEMALE',142,22,'HIGH_WEIGHT'),(732,'FEMALE',143,22.1,'HIGH_WEIGHT'),(733,'FEMALE',144,22.1,'HIGH_WEIGHT'),(734,'FEMALE',145,22.2,'HIGH_WEIGHT'),(735,'FEMALE',146,22.2,'HIGH_WEIGHT'),(736,'FEMALE',147,22.3,'HIGH_WEIGHT'),(737,'FEMALE',148,22.3,'HIGH_WEIGHT'),(738,'FEMALE',149,22.4,'HIGH_WEIGHT'),(739,'FEMALE',150,22.4,'HIGH_WEIGHT'),(740,'FEMALE',151,22.5,'HIGH_WEIGHT'),(741,'FEMALE',152,22.5,'HIGH_WEIGHT'),(742,'FEMALE',153,22.6,'HIGH_WEIGHT'),(743,'FEMALE',154,22.7,'HIGH_WEIGHT'),(744,'FEMALE',155,22.7,'HIGH_WEIGHT'),(745,'FEMALE',156,22.8,'HIGH_WEIGHT'),(746,'FEMALE',157,22.8,'HIGH_WEIGHT'),(747,'FEMALE',158,22.8,'HIGH_WEIGHT'),(748,'FEMALE',159,22.9,'HIGH_WEIGHT'),(749,'FEMALE',160,22.9,'HIGH_WEIGHT'),(750,'FEMALE',161,23,'HIGH_WEIGHT'),(751,'FEMALE',162,23,'HIGH_WEIGHT'),(752,'FEMALE',163,23,'HIGH_WEIGHT'),(753,'FEMALE',164,23.1,'HIGH_WEIGHT'),(754,'FEMALE',165,23.1,'HIGH_WEIGHT'),(755,'FEMALE',166,23.2,'HIGH_WEIGHT'),(756,'FEMALE',167,23.2,'HIGH_WEIGHT'),(757,'FEMALE',168,23.3,'HIGH_WEIGHT'),(758,'FEMALE',169,23.3,'HIGH_WEIGHT'),(759,'FEMALE',170,23.3,'HIGH_WEIGHT'),(760,'FEMALE',171,23.3,'HIGH_WEIGHT'),(761,'FEMALE',172,23.4,'HIGH_WEIGHT'),(762,'FEMALE',173,23.4,'HIGH_WEIGHT'),(763,'FEMALE',174,23.4,'HIGH_WEIGHT'),(764,'FEMALE',175,23.5,'HIGH_WEIGHT'),(765,'FEMALE',176,23.5,'HIGH_WEIGHT'),(766,'FEMALE',177,23.5,'HIGH_WEIGHT'),(767,'FEMALE',178,23.6,'HIGH_WEIGHT'),(768,'FEMALE',179,23.6,'HIGH_WEIGHT'),(769,'FEMALE',180,23.6,'HIGH_WEIGHT'),(770,'FEMALE',181,23.6,'HIGH_WEIGHT'),(771,'FEMALE',182,23.6,'HIGH_WEIGHT'),(772,'FEMALE',183,23.7,'HIGH_WEIGHT'),(773,'FEMALE',184,23.7,'HIGH_WEIGHT'),(774,'FEMALE',185,23.7,'HIGH_WEIGHT'),(775,'FEMALE',186,23.7,'HIGH_WEIGHT'),(776,'FEMALE',187,23.7,'HIGH_WEIGHT'),(777,'FEMALE',188,23.7,'HIGH_WEIGHT'),(778,'FEMALE',189,23.7,'HIGH_WEIGHT'),(779,'FEMALE',190,23.8,'HIGH_WEIGHT'),(780,'FEMALE',191,23.8,'HIGH_WEIGHT'),(781,'FEMALE',192,23.8,'HIGH_WEIGHT'),(782,'FEMALE',193,23.8,'HIGH_WEIGHT'),(783,'FEMALE',194,23.8,'HIGH_WEIGHT'),(784,'FEMALE',195,23.8,'HIGH_WEIGHT'),(785,'FEMALE',196,23.8,'HIGH_WEIGHT'),(786,'FEMALE',197,23.8,'HIGH_WEIGHT'),(787,'FEMALE',198,23.8,'HIGH_WEIGHT'),(788,'FEMALE',199,23.8,'HIGH_WEIGHT'),(789,'FEMALE',200,23.8,'HIGH_WEIGHT'),(790,'FEMALE',201,23.8,'HIGH_WEIGHT'),(791,'FEMALE',202,23.8,'HIGH_WEIGHT'),(792,'FEMALE',203,23.8,'HIGH_WEIGHT'),(793,'FEMALE',204,23.8,'HIGH_WEIGHT'),(794,'FEMALE',205,23.8,'HIGH_WEIGHT'),(795,'FEMALE',206,23.8,'HIGH_WEIGHT'),(796,'FEMALE',207,23.8,'HIGH_WEIGHT'),(797,'FEMALE',208,23.7,'HIGH_WEIGHT'),(798,'FEMALE',209,23.7,'HIGH_WEIGHT'),(799,'FEMALE',210,23.7,'HIGH_WEIGHT'),(800,'FEMALE',211,23.7,'HIGH_WEIGHT'),(801,'FEMALE',212,23.7,'HIGH_WEIGHT'),(802,'FEMALE',213,23.7,'HIGH_WEIGHT'),(803,'FEMALE',214,23.7,'HIGH_WEIGHT'),(804,'FEMALE',215,23.7,'HIGH_WEIGHT'),(805,'FEMALE',216,23.7,'HIGH_WEIGHT'),(806,'FEMALE',217,23.7,'HIGH_WEIGHT'),(807,'FEMALE',218,23.7,'HIGH_WEIGHT'),(808,'FEMALE',219,23.7,'HIGH_WEIGHT'),(809,'FEMALE',220,23.7,'HIGH_WEIGHT'),(810,'FEMALE',221,23.7,'HIGH_WEIGHT'),(811,'FEMALE',222,23.7,'HIGH_WEIGHT'),(812,'FEMALE',223,23.6,'HIGH_WEIGHT'),(813,'FEMALE',224,23.6,'HIGH_WEIGHT'),(814,'FEMALE',225,23.6,'HIGH_WEIGHT'),(815,'FEMALE',226,23.6,'HIGH_WEIGHT'),(816,'FEMALE',227,23.6,'HIGH_WEIGHT');
/*!40000 ALTER TABLE `bmi_condition` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-05  9:19:49
