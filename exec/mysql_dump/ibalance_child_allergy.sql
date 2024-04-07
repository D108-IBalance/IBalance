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
-- Table structure for table `child_allergy`
--

DROP TABLE IF EXISTS `child_allergy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `child_allergy` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allergy_id` int NOT NULL,
  `child_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcw65vwo3p2d3c08u47sc5nn8v` (`allergy_id`),
  KEY `FKipp7tulnowi3eujxj6jw7ew0k` (`child_id`),
  CONSTRAINT `FKcw65vwo3p2d3c08u47sc5nn8v` FOREIGN KEY (`allergy_id`) REFERENCES `allergy` (`id`),
  CONSTRAINT `FKipp7tulnowi3eujxj6jw7ew0k` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `child_allergy`
--

LOCK TABLES `child_allergy` WRITE;
/*!40000 ALTER TABLE `child_allergy` DISABLE KEYS */;
INSERT INTO `child_allergy` VALUES (1,2,1),(2,3,1),(3,1,1),(4,4,1),(5,15,1),(11,1,6),(12,5,6),(13,6,6),(14,8,6),(16,2,8),(17,3,8),(51,2,31),(52,3,31),(53,4,31),(54,9,31),(57,9,37),(58,1,40),(59,2,43),(60,4,37),(61,1,45),(62,2,45),(63,5,45),(65,9,48),(66,1,49),(67,1,50),(68,5,52),(69,5,53),(70,15,53),(75,6,58),(76,6,59),(77,6,60),(78,1,61),(79,6,62),(80,9,62),(81,10,63);
/*!40000 ALTER TABLE `child_allergy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-05  9:19:51
