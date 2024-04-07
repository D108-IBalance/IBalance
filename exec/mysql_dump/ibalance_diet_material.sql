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
-- Table structure for table `diet_material`
--

DROP TABLE IF EXISTS `diet_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diet_material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `material` varchar(20) NOT NULL,
  `picky` tinyint(1) DEFAULT '0',
  `diet_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `diet_material_diet_fk` (`diet_id`),
  CONSTRAINT `diet_material_diet_fk` FOREIGN KEY (`diet_id`) REFERENCES `diet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6612 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet_material`
--

LOCK TABLES `diet_material` WRITE;
/*!40000 ALTER TABLE `diet_material` DISABLE KEYS */;
INSERT INTO `diet_material` VALUES (232,'스프',0,11),(233,'크림스프',0,11),(234,'마늘',0,11),(235,'멸치',0,11),(236,'배추김치',0,11),(237,'대파',0,11),(238,'김치',0,11),(239,'숙주나물',0,11),(240,'마늘',0,11),(241,'참기름',0,11),(242,'설탕',0,11),(243,'미나리',0,11),(244,'김',0,11),(245,'브로콜리',1,12),(246,'양파',1,12),(247,'크림',1,12),(248,'후추',1,12),(249,'버터',1,12),(250,'밀가루',1,12),(251,'생강',1,12),(252,'소고기',1,12),(253,'마늘',1,12),(254,'대파',1,12),(255,'후추',1,12),(256,'조선무',1,12),(257,'고추가루',1,12),(258,'배추',1,12),(259,'마늘',1,12),(260,'참기름',1,12),(261,'설탕',1,12),(262,'대파',1,12),(263,'소고기',1,12),(264,'우엉',1,12),(265,'마늘',1,12),(266,'설탕',1,12),(267,'후추',1,12),(268,'쌀',1,13),(269,'대추',1,13),(270,'닭고기',1,13),(271,'후추',1,13),(272,'수삼',1,13),(273,'인삼',1,13),(274,'미역',1,13),(275,'들깨',1,13),(276,'들기름',1,13),(277,'다시마',1,13),(278,'멸치',0,13),(279,'재래간장',1,13),(280,'생강',1,13),(281,'고추',1,13),(282,'애호박',0,13),(283,'당근',0,13),(284,'마늘',0,13),(285,'호박',0,13),(286,'닭고기',1,13),(287,'대파',1,13),(288,'풋고추',0,13),(289,'표고버섯',0,13),(290,'마늘',0,13),(291,'설탕',0,13),(292,'참깨',0,13),(293,'쌀',0,14),(294,'기장',0,14),(295,'팥',0,14),(296,'수수',0,14),(297,'부추',0,14),(298,'다시마',0,14),(299,'멸치',0,14),(300,'재래간장',0,14),(301,'계란',0,14),(302,'대파',0,14),(303,'생강',0,14),(304,'고추가루',0,14),(305,'양파',0,14),(306,'마늘',0,14),(307,'참기름',0,14),(308,'미나리',0,14),(309,'고등어',0,14),(310,'조선무',0,14),(311,'새우',0,14),(312,'마늘',0,14),(313,'참기름',0,14),(314,'마늘쫑',0,14),(315,'쌀',0,15),(316,'기장',0,15),(317,'소고기',0,15),(318,'쌀',0,15),(319,'떡',0,15),(320,'다시마',0,15),(321,'멸치',0,15),(322,'계란',0,15),(323,'대파',0,15),(324,'대구',1,15),(325,'고추가루',0,15),(326,'가지',0,15),(327,'마늘',0,15),(328,'피망',0,15),(329,'쌀',0,16),(330,'다랑어',0,16),(331,'당근',0,16),(332,'마늘',0,16),(333,'참기름',0,16),(334,'참치',0,16),(335,'대파',0,16),(336,'애호박',0,16),(337,'고추가루',0,16),(338,'감자',0,16),(339,'다시마',0,16),(340,'멸치',0,16),(341,'마늘',0,16),(342,'호박',0,16),(343,'대파',0,16),(344,'브로콜리',0,16),(345,'계란',0,16),(346,'오이피클',0,16),(347,'오이',1,16),(348,'소고기',0,17),(349,'쌀',0,17),(350,'양파',0,17),(351,'당근',0,17),(352,'표고버섯',0,17),(353,'마늘',0,17),(354,'참기름',0,17),(355,'설탕',0,17),(356,'대파',0,17),(357,'들깨',0,17),(358,'다시마',0,17),(359,'마늘',0,17),(360,'멸치',0,17),(361,'재래간장',0,17),(362,'참기름',0,17),(363,'조선무',0,17),(364,'도라지',0,17),(365,'재래간장',0,17),(366,'마늘',0,17),(367,'참기름',0,17),(368,'청경채',0,17),(369,'참기름',0,17),(370,'느타리버섯',0,17),(371,'쌀',0,18),(372,'대추',0,18),(373,'닭고기',0,18),(374,'후추',0,18),(375,'수삼',0,18),(376,'인삼',0,18),(377,'쑥갓',0,18),(378,'다시마',0,18),(379,'마늘',0,18),(380,'참기름',0,18),(381,'피망',0,18),(382,'대합',0,18),(383,'우유',0,18),(384,'계란',0,18),(385,'가지',0,18),(386,'참기름',0,18),(387,'대파',0,18),(388,'밀가루',0,18),(389,'쌀',0,19),(390,'잣',0,19),(391,'애호박',0,19),(392,'고추가루',0,19),(393,'감자',0,19),(394,'다시마',0,19),(395,'멸치',0,19),(396,'마늘',0,19),(397,'호박',0,19),(398,'대파',0,19),(399,'대구',0,19),(400,'애호박',0,19),(401,'호박',0,19),(402,'계란',0,19),(403,'콩기름',0,19),(404,'밀가루',0,19),(405,'쌀',0,20),(406,'양파',0,20),(407,'감자',0,20),(408,'마늘',0,20),(409,'멸치',0,20),(410,'대파',0,20),(411,'된장',0,20),(412,'검정깨',0,20),(413,'참깨',0,20),(414,'설탕',0,20),(415,'배추김치',0,20),(416,'후추',0,20),(417,'김치',0,20),(418,'콩기름',0,20),(419,'돼지고기',0,20),(420,'브로콜리',0,20),(421,'양파',0,20),(422,'계란',0,20),(423,'콩기름',0,20),(424,'조개살',0,20),(425,'밀가루',0,20),(426,'쌀',0,21),(427,'브로콜리',0,21),(428,'당근',0,21),(429,'표고버섯',0,21),(430,'참기름',0,21),(431,'양송이',0,21),(432,'양파',0,21),(433,'감자',0,21),(434,'다시마',0,21),(435,'마늘',0,21),(436,'멸치',0,21),(437,'재래간장',0,21),(438,'호박',0,21),(439,'대파',0,21),(440,'오징어',0,21),(441,'양파',0,21),(442,'당근',0,21),(443,'마늘',0,21),(444,'호박',0,21),(445,'참기름',0,21),(446,'설탕',0,21),(447,'후추',0,21),(448,'단호박',0,21),(449,'양파',0,21),(450,'당근',1,21),(451,'두부',0,21),(452,'전분',0,21),(453,'감자녹말',0,21),(454,'시금치',1,21),(455,'콩기름',0,21),(456,'쌀',0,22),(457,'기장',0,22),(458,'오징어',0,22),(459,'다시마',0,22),(460,'마늘',0,22),(461,'멸치',0,22),(462,'재래간장',0,22),(463,'대파',0,22),(464,'조선무',0,22),(465,'고추가루',0,22),(466,'두부',0,22),(467,'마늘',0,22),(468,'멸치',0,22),(469,'참기름',0,22),(470,'콩기름',0,22),(471,'감자',0,22),(472,'마늘',0,22),(473,'참기름',0,22),(474,'설탕',0,22),(475,'콩기름',0,22),(476,'소고기',0,23),(477,'쌀',0,23),(478,'양파',0,23),(479,'당근',0,23),(480,'표고버섯',0,23),(481,'마늘',0,23),(482,'참기름',0,23),(483,'설탕',0,23),(484,'대파',0,23),(485,'콩가루',0,23),(486,'마늘',0,23),(487,'멸치',0,23),(488,'재래간장',0,23),(489,'대파',0,23),(490,'냉이',0,23),(491,'된장',0,23),(492,'생강',0,23),(493,'청주',0,23),(494,'마늘',0,23),(495,'전분',0,23),(496,'닭고기',0,23),(497,'설탕',0,23),(498,'후추',0,23),(499,'감자녹말',0,23),(500,'콩기름',0,23),(501,'고추가루',0,23),(502,'가지',0,23),(503,'마늘',0,23),(504,'피망',0,23),(505,'쌀',0,24),(506,'현미',0,24),(507,'마늘',0,24),(508,'멸치',0,24),(509,'참기름',0,24),(510,'대파',0,24),(511,'피망',0,24),(512,'조선무',0,24),(513,'콩나물',0,24),(514,'연근',0,24),(515,'마늘',0,24),(516,'설탕',0,24),(517,'후추',0,24),(518,'된장',0,24),(519,'돼지고기',0,24),(520,'양파',0,24),(521,'감자',0,24),(522,'옥수수',0,24),(523,'당근',0,24),(524,'어묵',0,24),(525,'옥수수통조림',0,24),(526,'게맛살',0,24),(527,'피망',0,24),(528,'콩기름',0,24),(529,'밀가루',0,24),(530,'호박',0,25),(531,'찹쌀가루',0,25),(532,'설탕',0,25),(533,'단호박',0,25),(534,'새우',0,25),(535,'다시마',0,25),(536,'마늘',0,25),(537,'멸치',0,25),(538,'재래간장',0,25),(539,'대파',0,25),(540,'시금치',0,25),(541,'된장',0,25),(542,'부추',0,25),(543,'두부',0,25),(544,'청주',0,25),(545,'계란',0,25),(546,'후추',0,25),(547,'미역',0,25),(548,'고추가루',0,25),(549,'식초',0,25),(550,'고추장',0,25),(551,'마늘',0,25),(552,'참기름',0,25),(553,'설탕',0,25),(554,'대파',0,25),(555,'조선무',0,25),(3467,'소고기',0,148),(3468,'쌀',0,148),(3469,'옥수수',0,148),(3470,'감자',0,148),(3471,'당근',0,148),(3472,'시금치',0,148),(3473,'돼지고기',0,148),(3474,'소고기',0,148),(3475,'두부',0,148),(3476,'양지',0,148),(3477,'마늘',0,148),(3478,'다시마',0,148),(3479,'재래간장',0,148),(3480,'참기름',0,148),(3481,'조선무',0,148),(3482,'쌀',0,148),(3483,'떡',0,148),(3484,'양파',0,148),(3485,'표고버섯',0,148),(3486,'마늘',0,148),(3487,'설탕',0,148),(3488,'후추',0,148),(3489,'돼지고기',0,148),(3490,'배',0,148),(3491,'소고기',0,148),(3492,'당근',0,148),(3493,'청주',0,148),(3494,'참기름',0,148),(3495,'대파',0,148),(3496,'다시마',0,148),(3497,'설탕',0,148),(3498,'콩기름',0,148),(3499,'쌀',0,149),(3500,'대추',0,149),(3501,'닭고기',0,149),(3502,'후추',0,149),(3503,'인삼',0,149),(3504,'수삼',0,149),(3505,'양파',0,149),(3506,'감자',0,149),(3507,'마늘',0,149),(3508,'멸치',0,149),(3509,'대파',0,149),(3510,'된장',0,149),(3511,'소고기',0,149),(3512,'양파',0,149),(3513,'당근',0,149),(3514,'두부',0,149),(3515,'계란',0,149),(3516,'콩기름',0,149),(3517,'밀가루',0,149),(3518,'돼지고기',0,149),(3519,'다랑어',0,149),(3520,'검정깨',0,149),(3521,'참깨',0,149),(3522,'배추김치',0,149),(3523,'참치',0,149),(3524,'김치',0,149),(3525,'스프',0,150),(3526,'크림스프',0,150),(3527,'다랑어',0,150),(3528,'양파',0,150),(3529,'감자',0,150),(3530,'마늘',0,150),(3531,'멸치',0,150),(3532,'배추김치',0,150),(3533,'참치',0,150),(3534,'대파',0,150),(3535,'김치',0,150),(3536,'콩기름',0,150),(3537,'식초',0,150),(3538,'마늘',0,150),(3539,'참기름',0,150),(3540,'설탕',0,150),(3541,'조선무',0,150),(3542,'명태',0,150),(3543,'계란',0,150),(3544,'알',0,150),(3545,'식빵',0,151),(3546,'우유',0,151),(3547,'계란',0,151),(3548,'콩기름',0,151),(3549,'마늘',0,151),(3550,'멸치',0,151),(3551,'다시마',0,151),(3552,'대파',0,151),(3553,'된장',0,151),(3554,'무청시래기',0,151),(3555,'브로콜리',0,151),(3556,'양파',0,151),(3557,'계란',0,151),(3558,'콩기름',0,151),(3559,'조개살',0,151),(3560,'밀가루',0,151),(3561,'고추가루',0,151),(3562,'양파',0,151),(3563,'부추',0,151),(3564,'참기름',0,151),(3565,'돌나물',0,151),(3566,'브로콜리',0,152),(3567,'양파',0,152),(3568,'크림',0,152),(3569,'후추',0,152),(3570,'버터',0,152),(3571,'밀가루',0,152),(3572,'마늘',0,152),(3573,'멸치',0,152),(3574,'배추김치',0,152),(3575,'대파',0,152),(3576,'김치',0,152),(3577,'명태',0,152),(3578,'고추가루',0,152),(3579,'마늘',0,152),(3580,'참기름',0,152),(3581,'설탕',0,152),(3582,'후추',0,152),(3583,'조선무',0,152),(3584,'청경채',0,152),(3585,'마늘',0,152),(3586,'참기름',0,152),(3587,'양송이',0,152),(3588,'콩기름',0,152),(3589,'소고기',0,153),(3590,'쌀',0,153),(3591,'우엉',0,153),(3592,'당근',0,153),(3593,'두부',0,153),(3594,'피망',0,153),(3595,'소고기',0,153),(3596,'생강',0,153),(3597,'마늘',0,153),(3598,'후추',0,153),(3599,'대파',0,153),(3600,'조선무',0,153),(3601,'도라지',0,153),(3602,'재래간장',0,153),(3603,'마늘',0,153),(3604,'참기름',0,153),(3605,'도라지',0,153),(3606,'고추가루',0,153),(3607,'고추장',0,153),(3608,'오이',0,153),(3609,'기장',0,154),(3610,'쌀',0,154),(3611,'고추',0,154),(3612,'고추가루',0,154),(3613,'두부',0,154),(3614,'멸치',0,154),(3615,'다시마',0,154),(3616,'참기름',0,154),(3617,'대파',0,154),(3618,'피망',0,154),(3619,'조선무',0,154),(3620,'양파',0,154),(3621,'청주',0,154),(3622,'설탕',0,154),(3623,'피망',0,154),(3624,'콩기름',0,154),(3625,'새우튀김',0,154),(3626,'밀가루',0,154),(3627,'브로콜리',0,154),(3628,'고추가루',0,154),(3629,'식초',0,154),(3630,'고추장',0,154),(3631,'마늘',0,154),(3632,'설탕',0,154),(3633,'소고기',0,155),(3634,'쌀',0,155),(3635,'양파',0,155),(3636,'당근',0,155),(3637,'표고버섯',0,155),(3638,'마늘',1,155),(3639,'참기름',0,155),(3640,'설탕',0,155),(3641,'대파',0,155),(3642,'소고기',0,155),(3643,'두부',0,155),(3644,'마늘',0,155),(3645,'다시마',0,155),(3646,'양지',0,155),(3647,'재래간장',0,155),(3648,'참기름',0,155),(3649,'조선무',0,155),(3650,'양파',0,155),(3651,'청주',0,155),(3652,'설탕',0,155),(3653,'피망',0,155),(3654,'콩기름',0,155),(3655,'새우튀김',0,155),(3656,'밀가루',0,155),(3657,'새우',0,155),(3658,'마늘',0,155),(3659,'참기름',0,155),(3660,'마늘쫑',0,155),(3661,'쌀',0,156),(3662,'브로콜리',0,156),(3663,'당근',0,156),(3664,'표고버섯',0,156),(3665,'참기름',0,156),(3666,'양송이',0,156),(3667,'부추',0,156),(3668,'다시마',0,156),(3669,'멸치',0,156),(3670,'재래간장',0,156),(3671,'계란',0,156),(3672,'대파',0,156),(3673,'브로콜리',0,156),(3674,'계란',0,156),(3675,'소고기',0,156),(3676,'우엉',0,156),(3677,'마늘',0,156),(3678,'설탕',0,156),(3679,'후추',0,156),(3680,'쌀',0,157),(3681,'다랑어',0,157),(3682,'당근',0,157),(3683,'마늘',0,157),(3684,'참기름',0,157),(3685,'참치',0,157),(3686,'대파',0,157),(3687,'애호박',0,157),(3688,'고추가루',0,157),(3689,'감자',0,157),(3690,'마늘',0,157),(3691,'다시마',0,157),(3692,'호박',0,157),(3693,'멸치',0,157),(3694,'대파',0,157),(3695,'부추',0,157),(3696,'전분',0,157),(3697,'참기름',0,157),(3698,'콩기름',0,157),(3699,'감자녹말',0,157),(3700,'돼지고기',0,157),(3701,'청경채',0,157),(3702,'참기름',0,157),(3703,'느타리버섯',0,157),(3704,'기장',0,158),(3705,'쌀',0,158),(3706,'팥',0,158),(3707,'수수',0,158),(3708,'다랑어',0,158),(3709,'양파',0,158),(3710,'감자',0,158),(3711,'마늘',0,158),(3712,'멸치',0,158),(3713,'배추김치',0,158),(3714,'참치',0,158),(3715,'대파',0,158),(3716,'콩기름',0,158),(3717,'김치',0,158),(3718,'감자',0,158),(3719,'청주',0,158),(3720,'설탕',0,158),(3721,'참깨',0,158),(3722,'비엔나',0,158),(3723,'피망',0,158),(3724,'콩기름',0,158),(3725,'소시지',0,158),(3726,'도라지',0,158),(3727,'재래간장',0,158),(3728,'마늘',0,158),(3729,'참기름',0,158),(3730,'소고기',0,159),(3731,'쌀',0,159),(3732,'마늘',0,159),(3733,'재래간장',0,159),(3734,'참기름',0,159),(3735,'대파',0,159),(3736,'돼지고기',0,159),(3737,'양파',0,159),(3738,'감자',0,159),(3739,'마늘',0,159),(3740,'멸치',0,159),(3741,'대파',0,159),(3742,'된장',0,159),(3743,'오징어',0,159),(3744,'양파',0,159),(3745,'당근',0,159),(3746,'마늘',0,159),(3747,'호박',0,159),(3748,'참기름',0,159),(3749,'설탕',0,159),(3750,'후추',0,159),(3751,'단호박',0,159),(3752,'다랑어',0,159),(3753,'배추김치',0,159),(3754,'설탕',0,159),(3755,'참치',0,159),(3756,'김치',0,159),(3757,'콩기름',0,159),(3758,'기장',0,160),(3759,'쌀',0,160),(3760,'양파',0,160),(3761,'감자',0,160),(3762,'마늘',0,160),(3763,'다시마',0,160),(3764,'호박',0,160),(3765,'멸치',0,160),(3766,'재래간장',0,160),(3767,'대파',0,160),(3768,'청주',0,160),(3769,'마늘',0,160),(3770,'배추김치',0,160),(3771,'김치',0,160),(3772,'된장',0,160),(3773,'돼지고기',0,160),(3774,'어묵',0,160),(3775,'게맛살',0,160),(3776,'콩기름',0,160),(3777,'밀가루',0,160),(3778,'소고기',0,161),(3779,'쌀',0,161),(3780,'옥수수',0,161),(3781,'감자',0,161),(3782,'당근',0,161),(3783,'시금치',0,161),(3784,'돼지고기',0,161),(3785,'마늘',0,161),(3786,'멸치',0,161),(3787,'배추김치',0,161),(3788,'대파',0,161),(3789,'김치',0,161),(3790,'어묵',0,161),(3791,'계란',0,161),(3792,'게맛살',0,161),(3793,'콩기름',0,161),(3794,'밀가루',0,161),(3795,'감자',0,161),(3796,'콩기름',0,161),(3797,'밀가루',0,161),(4001,'쌀',0,171),(4002,'생강',1,171),(4003,'양파',0,171),(4004,'표고버섯',0,171),(4005,'마늘',0,171),(4006,'설탕',0,171),(4007,'참깨',0,171),(4008,'후추',0,171),(4009,'조선무',0,171),(4010,'소고기',0,171),(4011,'고추가루',0,171),(4012,'배추',0,171),(4013,'당근',0,171),(4014,'참기름',0,171),(4015,'대파',0,171),(4016,'쌀',0,172),(4017,'미역',0,172),(4018,'숙주나물',0,172),(4019,'옥수수',0,172),(4020,'재래간장',0,172),(4021,'다시마',0,172),(4022,'마늘',0,172),(4023,'참깨',0,172),(4024,'돼지고기',0,172),(4025,'소고기',0,172),(4026,'감자',0,172),(4027,'당근',0,172),(4028,'비름',0,172),(4029,'참기름',0,172),(4030,'어묵',0,172),(4031,'게맛살',0,172),(4032,'시금치',0,172),(4033,'쌀',0,173),(4034,'우엉',0,173),(4035,'애호박',0,173),(4036,'청경채',0,173),(4037,'재래간장',0,173),(4038,'마늘',0,173),(4039,'다시마',0,173),(4040,'호박',0,173),(4041,'설탕',0,173),(4042,'후추',0,173),(4043,'돼지고기',0,173),(4044,'소고기',0,173),(4045,'고추가루',0,173),(4046,'감자',0,173),(4047,'멸치',0,173),(4048,'참기름',0,173),(4049,'대파',0,173),(4050,'느타리버섯',0,173),(4051,'쌀',0,174),(4052,'부추',0,174),(4053,'마늘',0,174),(4054,'재래간장',0,174),(4055,'다시마',0,174),(4056,'계란',0,174),(4057,'다랑어',0,174),(4058,'브로콜리',0,174),(4059,'고추가루',0,174),(4060,'당근',0,174),(4061,'가지',0,174),(4062,'멸치',0,174),(4063,'참기름',0,174),(4064,'참치',0,174),(4065,'대파',0,174),(4066,'피망',0,174),(4067,'기장',0,175),(4068,'쌀',0,175),(4069,'오징어',0,175),(4070,'양파',0,175),(4071,'재래간장',0,175),(4072,'다시마',0,175),(4073,'마늘',0,175),(4074,'호박',0,175),(4075,'설탕',0,175),(4076,'후추',0,175),(4077,'도라지',0,175),(4078,'감자',0,175),(4079,'당근',0,175),(4080,'멸치',0,175),(4081,'참기름',0,175),(4082,'대파',0,175),(4083,'단호박',0,175),(4084,'기장',0,176),(4085,'쌀',0,176),(4086,'팥',0,176),(4087,'재래간장',0,176),(4088,'다시마',0,176),(4089,'마늘',0,176),(4090,'조선무',0,176),(4091,'마늘쫑',0,176),(4092,'새우',0,176),(4093,'들깨',0,176),(4094,'멸치',0,176),(4095,'수수',0,176),(4096,'참기름',0,176),(4097,'쌀',0,177),(4098,'고구마',0,177),(4099,'포도',0,177),(4100,'마요네즈',0,177),(4101,'옥수수',0,177),(4102,'표고버섯',0,177),(4103,'마늘',0,177),(4104,'호박',0,177),(4105,'참깨',0,177),(4106,'설탕',0,177),(4107,'무말랭이',0,177),(4108,'옥수수통조림',0,177),(4109,'김치',0,177),(4110,'건포도',0,177),(4111,'브로콜리',0,177),(4112,'고추가루',0,177),(4113,'당근',0,177),(4114,'멸치',0,177),(4115,'참기름',0,177),(4116,'양송이',0,177),(4117,'배추김치',0,177),(4118,'대파',0,177),(4119,'단호박',0,177),(4120,'미역',0,178),(4121,'양파',0,178),(4122,'부추',0,178),(4123,'들기름',0,178),(4124,'재래간장',0,178),(4125,'다시마',0,178),(4126,'마늘',0,178),(4127,'크림',0,178),(4128,'설탕',0,178),(4129,'참깨',0,178),(4130,'후추',0,178),(4131,'브로콜리',0,178),(4132,'들깨',0,178),(4133,'식초',0,178),(4134,'멸치',0,178),(4135,'참기름',0,178),(4136,'버터',0,178),(4137,'미나리',0,178),(4138,'밀가루',0,178),(4931,'쌀',0,219),(4932,'애호박',0,219),(4933,'양파',0,219),(4934,'표고버섯',0,219),(4935,'마늘',0,219),(4936,'호박',0,219),(4937,'다시마',0,219),(4938,'설탕',0,219),(4939,'새우튀김',0,219),(4940,'소고기',0,219),(4941,'고추가루',0,219),(4942,'배추',0,219),(4943,'당근',0,219),(4944,'감자',0,219),(4945,'청주',0,219),(4946,'멸치',0,219),(4947,'참기름',0,219),(4948,'대파',0,219),(4949,'피망',1,219),(4950,'콩기름',0,219),(4951,'밀가루',0,219),(4952,'쌀',0,220),(4953,'미역',0,220),(4954,'고구마',0,220),(4955,'양파',0,220),(4956,'마늘',0,220),(4957,'참깨',0,220),(4958,'설탕',0,220),(4959,'현미',0,220),(4960,'검정깨',0,220),(4961,'감자',0,220),(4962,'멸치',0,220),(4963,'대파',0,220),(4964,'튀각',0,220),(4965,'콩기름',0,220),(4966,'된장',0,220),(4967,'쌀',0,221),(4968,'대추',0,221),(4969,'양파',0,221),(4970,'마늘',0,221),(4971,'호박',1,221),(4972,'재래간장',0,221),(4973,'다시마',0,221),(4974,'닭고기',0,221),(4975,'계란',0,221),(4976,'후추',0,221),(4977,'감자',0,221),(4978,'멸치',0,221),(4979,'어묵',0,221),(4980,'대파',0,221),(4981,'대구',0,221),(4982,'게맛살',0,221),(4983,'수삼',0,221),(4984,'인삼',0,221),(4985,'콩기름',0,221),(4986,'밀가루',0,221),(4987,'쌀',0,222),(4988,'우엉',0,222),(4989,'두부',0,222),(4990,'표고버섯',0,222),(4991,'마늘',0,222),(4992,'재래간장',0,222),(4993,'다시마',0,222),(4994,'설탕',0,222),(4995,'참깨',0,222),(4996,'후추',0,222),(4997,'조선무',0,222),(4998,'소고기',0,222),(4999,'다랑어',0,222),(5000,'당근',0,222),(5001,'양지',0,222),(5002,'참기름',0,222),(5003,'참치',0,222),(5004,'대파',0,222),(5005,'쌀',0,223),(5006,'기장',0,223),(5007,'부추',0,223),(5008,'재래간장',0,223),(5009,'다시마',0,223),(5010,'계란',0,223),(5011,'참깨',0,223),(5012,'설탕',0,223),(5013,'비엔나',0,223),(5014,'브로콜리',0,223),(5015,'감자',0,223),(5016,'청주',0,223),(5017,'멸치',0,223),(5018,'대파',0,223),(5019,'피망',0,223),(5020,'콩기름',0,223),(5021,'소시지',0,223),(5022,'쌀',0,224),(5023,'콩가루',0,224),(5024,'멸치',0,224),(5025,'마늘',0,224),(5026,'재래간장',0,224),(5027,'다시마',0,224),(5028,'닭고기',0,224),(5029,'설탕',0,224),(5030,'대파',0,224),(5031,'냉이',0,224),(5032,'콩기름',0,224),(5033,'된장',0,224),(5034,'쌀',0,225),(5035,'청경채',0,225),(5036,'양파',0,225),(5037,'마늘',0,225),(5038,'재래간장',0,225),(5039,'김치',0,225),(5040,'돼지고기',0,225),(5041,'소고기',0,225),(5042,'다랑어',0,225),(5043,'고추가루',0,225),(5044,'감자',0,225),(5045,'가지',0,225),(5046,'멸치',0,225),(5047,'참기름',0,225),(5048,'배추김치',0,225),(5049,'참치',0,225),(5050,'대파',0,225),(5051,'피망',0,225),(5052,'콩기름',0,225),(5053,'느타리버섯',0,225),(5054,'쌀',0,226),(5055,'기장',0,226),(5056,'생강',0,226),(5057,'고구마',0,226),(5058,'팥',0,226),(5059,'마늘',0,226),(5060,'재래간장',0,226),(5061,'계란',0,226),(5062,'후추',0,226),(5063,'조선무',0,226),(5064,'소고기',0,226),(5065,'도라지',0,226),(5066,'수수',0,226),(5067,'참기름',0,226),(5068,'대파',0,226),(5069,'콩기름',0,226),(5070,'밀가루',0,226),(5071,'쌀',0,227),(5072,'부추',0,227),(5073,'표고버섯',0,227),(5074,'마늘',0,227),(5075,'재래간장',0,227),(5076,'다시마',0,227),(5077,'설탕',0,227),(5078,'조선무',0,227),(5079,'감자녹말',0,227),(5080,'김치',0,227),(5081,'돼지고기',0,227),(5082,'브로콜리',0,227),(5083,'다랑어',0,227),(5084,'들깨',0,227),(5085,'당근',0,227),(5086,'멸치',0,227),(5087,'참기름',0,227),(5088,'전분',0,227),(5089,'양송이',0,227),(5090,'배추김치',0,227),(5091,'참치',0,227),(5092,'콩기름',0,227),(5093,'미역',0,228),(5094,'양파',0,228),(5095,'들기름',0,228),(5096,'재래간장',0,228),(5097,'다시마',0,228),(5098,'마늘',0,228),(5099,'크림',0,228),(5100,'후추',0,228),(5101,'김치',0,228),(5102,'마늘쫑',0,228),(5103,'돼지고기',0,228),(5104,'새우',0,228),(5105,'브로콜리',0,228),(5106,'들깨',0,228),(5107,'청주',0,228),(5108,'멸치',0,228),(5109,'참기름',0,228),(5110,'배추김치',0,228),(5111,'버터',0,228),(5112,'밀가루',0,228),(5113,'된장',0,228),(5114,'쌀',0,229),(5115,'우엉',0,229),(5116,'애호박',0,229),(5117,'양파',0,229),(5118,'표고버섯',0,229),(5119,'마늘',0,229),(5120,'호박',0,229),(5121,'다시마',0,229),(5122,'설탕',0,229),(5123,'후추',0,229),(5124,'새우튀김',0,229),(5125,'소고기',0,229),(5126,'고추가루',0,229),(5127,'당근',0,229),(5128,'감자',0,229),(5129,'청주',0,229),(5130,'멸치',0,229),(5131,'참기름',0,229),(5132,'대파',0,229),(5133,'피망',0,229),(5134,'콩기름',0,229),(5135,'밀가루',0,229),(5136,'쌀',0,230),(5137,'청경채',0,230),(5138,'양파',0,230),(5139,'옥수수',0,230),(5140,'마늘',0,230),(5141,'돼지고기',0,230),(5142,'소고기',0,230),(5143,'고추가루',0,230),(5144,'감자',0,230),(5145,'당근',0,230),(5146,'가지',0,230),(5147,'멸치',0,230),(5148,'참기름',0,230),(5149,'대파',0,230),(5150,'피망',0,230),(5151,'시금치',0,230),(5152,'느타리버섯',0,230),(5153,'된장',0,230),(5154,'쌀',0,231),(5155,'마른김',0,231),(5156,'대추',0,231),(5157,'양파',0,231),(5158,'마늘',0,231),(5159,'호박',0,231),(5160,'재래간장',0,231),(5161,'다시마',0,231),(5162,'닭고기',0,231),(5163,'계란',0,231),(5164,'후추',0,231),(5165,'조선무',0,231),(5166,'마늘쫑',0,231),(5167,'돼지고기',0,231),(5168,'새우',0,231),(5169,'소고기',0,231),(5170,'감자',0,231),(5171,'당근',0,231),(5172,'멸치',0,231),(5173,'참기름',0,231),(5174,'대파',0,231),(5175,'수삼',0,231),(5176,'인삼',0,231),(5177,'콩기름',0,231),(5178,'쌀',0,232),(5179,'생강',0,232),(5180,'고추',0,232),(5181,'애호박',0,232),(5182,'표고버섯',0,232),(5183,'두부',0,232),(5184,'마늘',0,232),(5185,'재래간장',0,232),(5186,'다시마',0,232),(5187,'호박',0,232),(5188,'닭고기',0,232),(5189,'설탕',0,232),(5190,'조선무',0,232),(5191,'풋고추',0,232),(5192,'소고기',0,232),(5193,'브로콜리',0,232),(5194,'당근',0,232),(5195,'양지',0,232),(5196,'참기름',0,232),(5197,'양송이',0,232),(5198,'대파',0,232),(5199,'콩기름',0,232),(5200,'쌀',0,233),(5201,'기장',0,233),(5202,'부추',0,233),(5203,'재래간장',0,233),(5204,'다시마',0,233),(5205,'계란',0,233),(5206,'참깨',0,233),(5207,'설탕',0,233),(5208,'비엔나',0,233),(5209,'브로콜리',0,233),(5210,'감자',0,233),(5211,'청주',0,233),(5212,'멸치',0,233),(5213,'대파',0,233),(5214,'피망',0,233),(5215,'콩기름',0,233),(5216,'소시지',0,233),(5217,'쌀',0,234),(5218,'양파',0,234),(5219,'마늘',0,234),(5220,'설탕',0,234),(5221,'김치',0,234),(5222,'돼지고기',0,234),(5223,'다랑어',0,234),(5224,'고추가루',0,234),(5225,'배추',0,234),(5226,'당근',0,234),(5227,'감자',0,234),(5228,'청주',0,234),(5229,'멸치',0,234),(5230,'참기름',0,234),(5231,'참치',0,234),(5232,'배추김치',0,234),(5233,'대파',0,234),(5234,'콩기름',0,234),(5235,'된장',0,234),(5236,'쌀',0,235),(5237,'기장',0,235),(5238,'생강',0,235),(5239,'부추',0,235),(5240,'마요네즈',0,235),(5241,'팥',0,235),(5242,'마늘',0,235),(5243,'후추',0,235),(5244,'상추',0,235),(5245,'조선무',0,235),(5246,'감자녹말',0,235),(5247,'돼지고기',0,235),(5248,'소고기',0,235),(5249,'올리브유',0,235),(5250,'수수',0,235),(5251,'전분',0,235),(5252,'참기름',0,235),(5253,'양상추',0,235),(5254,'대파',0,235),(5255,'요구르트',0,235),(5256,'콩기름',0,235),(5257,'쌀',0,236),(5258,'오징어',0,236),(5259,'양파',0,236),(5260,'표고버섯',0,236),(5261,'마늘',0,236),(5262,'재래간장',0,236),(5263,'다시마',0,236),(5264,'호박',0,236),(5265,'설탕',0,236),(5266,'참깨',0,236),(5267,'후추',0,236),(5268,'조선무',0,236),(5269,'돼지고기',0,236),(5270,'소고기',0,236),(5271,'들깨',0,236),(5272,'당근',0,236),(5273,'멸치',0,236),(5274,'참기름',0,236),(5275,'대파',0,236),(5276,'단호박',0,236),(5277,'미역',0,237),(5278,'양파',0,237),(5279,'들기름',0,237),(5280,'재래간장',0,237),(5281,'다시마',0,237),(5282,'마늘',0,237),(5283,'크림',0,237),(5284,'설탕',0,237),(5285,'후추',0,237),(5286,'김치',0,237),(5287,'브로콜리',0,237),(5288,'도라지',0,237),(5289,'다랑어',0,237),(5290,'들깨',0,237),(5291,'멸치',0,237),(5292,'참기름',0,237),(5293,'배추김치',0,237),(5294,'참치',0,237),(5295,'버터',0,237),(5296,'콩기름',0,237),(5297,'밀가루',0,237),(5298,'쌀',0,238),(5299,'생강',0,238),(5300,'양파',0,238),(5301,'표고버섯',0,238),(5302,'마늘',1,238),(5303,'설탕',0,238),(5304,'참깨',0,238),(5305,'후추',0,238),(5306,'조선무',0,238),(5307,'소고기',0,238),(5308,'고추가루',0,238),(5309,'배추',0,238),(5310,'당근',0,238),(5311,'참기름',0,238),(5312,'대파',1,238),(5313,'쌀',0,239),(5314,'우엉',0,239),(5315,'애호박',0,239),(5316,'청경채',0,239),(5317,'마늘',0,239),(5318,'재래간장',0,239),(5319,'호박',0,239),(5320,'다시마',0,239),(5321,'설탕',0,239),(5322,'후추',0,239),(5323,'돼지고기',0,239),(5324,'소고기',0,239),(5325,'고추가루',0,239),(5326,'감자',0,239),(5327,'멸치',0,239),(5328,'참기름',0,239),(5329,'대파',0,239),(5330,'느타리버섯',0,239),(5331,'쌀',0,240),(5332,'부추',0,240),(5333,'마늘',0,240),(5334,'재래간장',0,240),(5335,'다시마',0,240),(5336,'계란',0,240),(5337,'다랑어',0,240),(5338,'브로콜리',0,240),(5339,'도라지',0,240),(5340,'당근',0,240),(5341,'멸치',0,240),(5342,'참기름',0,240),(5343,'참치',0,240),(5344,'대파',0,240),(5345,'쌀',0,241),(5346,'기장',0,241),(5347,'오징어',0,241),(5348,'양파',0,241),(5349,'마늘',0,241),(5350,'호박',0,241),(5351,'재래간장',0,241),(5352,'다시마',0,241),(5353,'설탕',0,241),(5354,'후추',0,241),(5355,'고추가루',0,241),(5356,'감자',0,241),(5357,'당근',0,241),(5358,'가지',0,241),(5359,'멸치',0,241),(5360,'참기름',0,241),(5361,'대파',0,241),(5362,'피망',0,241),(5363,'단호박',0,241),(5364,'쌀',0,242),(5365,'기장',0,242),(5366,'팥',0,242),(5367,'마늘',0,242),(5368,'재래간장',0,242),(5369,'다시마',0,242),(5370,'참깨',0,242),(5371,'무말랭이',0,242),(5372,'조선무',0,242),(5373,'마늘쫑',0,242),(5374,'새우',0,242),(5375,'들깨',0,242),(5376,'고추가루',0,242),(5377,'멸치',0,242),(5378,'수수',0,242),(5379,'참기름',0,242),(5380,'쌀',0,243),(5381,'표고버섯',0,243),(5382,'마늘',0,243),(5383,'설탕',0,243),(5384,'조선무',0,243),(5385,'김치',0,243),(5386,'브로콜리',0,243),(5387,'당근',0,243),(5388,'멸치',0,243),(5389,'참기름',0,243),(5390,'양송이',0,243),(5391,'배추김치',0,243),(5392,'대파',0,243),(5393,'미나리',0,243),(5394,'포도',0,244),(5395,'양파',0,244),(5396,'부추',0,244),(5397,'마요네즈',0,244),(5398,'들기름',0,244),(5399,'재래간장',0,244),(5400,'호박',0,244),(5401,'크림',0,244),(5402,'돌나물',0,244),(5403,'후추',0,244),(5404,'옥수수통조림',0,244),(5405,'브로콜리',0,244),(5406,'들깨',0,244),(5407,'참기름',0,244),(5408,'미역',0,244),(5409,'고구마',0,244),(5410,'옥수수',0,244),(5411,'다시마',0,244),(5412,'설탕',0,244),(5413,'건포도',0,244),(5414,'고추가루',0,244),(5415,'당근',0,244),(5416,'멸치',0,244),(5417,'버터',0,244),(5418,'밀가루',0,244),(5419,'단호박',0,244),(5965,'쌀',0,272),(5966,'우엉',0,272),(5967,'애호박',0,272),(5968,'양파',0,272),(5969,'마늘',1,272),(5970,'호박',0,272),(5971,'다시마',0,272),(5972,'설탕',0,272),(5973,'후추',0,272),(5974,'새우튀김',0,272),(5975,'현미',0,272),(5976,'소고기',0,272),(5977,'고추가루',0,272),(5978,'감자',0,272),(5979,'청주',0,272),(5980,'멸치',0,272),(5981,'대파',0,272),(5982,'피망',1,272),(5983,'콩기름',0,272),(5984,'밀가루',0,272),(5985,'쌀',0,273),(5986,'양파',0,273),(5987,'옥수수',0,273),(5988,'마늘',0,273),(5989,'마늘쫑',0,273),(5990,'돼지고기',0,273),(5991,'소고기',0,273),(5992,'새우',0,273),(5993,'고추가루',0,273),(5994,'감자',0,273),(5995,'당근',0,273),(5996,'가지',0,273),(5997,'멸치',0,273),(5998,'참기름',0,273),(5999,'대파',0,273),(6000,'피망',0,273),(6001,'시금치',0,273),(6002,'된장',0,273),(6003,'쌀',0,274),(6004,'마른김',0,274),(6005,'대추',0,274),(6006,'양파',0,274),(6007,'마늘',0,274),(6008,'호박',0,274),(6009,'재래간장',0,274),(6010,'다시마',0,274),(6011,'닭고기',0,274),(6012,'계란',0,274),(6013,'후추',0,274),(6014,'조선무',0,274),(6015,'돼지고기',0,274),(6016,'소고기',0,274),(6017,'감자',0,274),(6018,'당근',0,274),(6019,'멸치',0,274),(6020,'참기름',0,274),(6021,'대파',0,274),(6022,'수삼',0,274),(6023,'인삼',0,274),(6024,'콩기름',0,274),(6025,'단호박',0,274),(6026,'밀가루',0,274),(6027,'쌀',0,275),(6028,'오징어',0,275),(6029,'양파',0,275),(6030,'표고버섯',0,275),(6031,'두부',0,275),(6032,'마늘',0,275),(6033,'재래간장',0,275),(6034,'다시마',0,275),(6035,'호박',0,275),(6036,'설탕',0,275),(6037,'계란',0,275),(6038,'후추',0,275),(6039,'조선무',0,275),(6040,'소고기',0,275),(6041,'브로콜리',0,275),(6042,'당근',0,275),(6043,'양지',0,275),(6044,'참기름',0,275),(6045,'양송이',0,275),(6046,'단호박',0,275),(6047,'쌀',0,276),(6048,'기장',0,276),(6049,'부추',0,276),(6050,'표고버섯',0,276),(6051,'재래간장',0,276),(6052,'다시마',0,276),(6053,'마늘',0,276),(6054,'계란',0,276),(6055,'설탕',0,276),(6056,'참깨',0,276),(6057,'김치',0,276),(6058,'돼지고기',0,276),(6059,'청주',0,276),(6060,'멸치',0,276),(6061,'배추김치',0,276),(6062,'대파',0,276),(6063,'된장',0,276),(6064,'쌀',0,277),(6065,'양파',0,277),(6066,'마늘',0,277),(6067,'설탕',0,277),(6068,'참깨',0,277),(6069,'비엔나',0,277),(6070,'김치',0,277),(6071,'다랑어',0,277),(6072,'고추가루',0,277),(6073,'배추',0,277),(6074,'당근',0,277),(6075,'감자',0,277),(6076,'청주',0,277),(6077,'멸치',0,277),(6078,'참기름',0,277),(6079,'참치',0,277),(6080,'배추김치',0,277),(6081,'대파',0,277),(6082,'피망',0,277),(6083,'콩기름',0,277),(6084,'소시지',0,277),(6085,'쌀',0,278),(6086,'기장',0,278),(6087,'생강',0,278),(6088,'부추',0,278),(6089,'팥',0,278),(6090,'마늘',0,278),(6091,'양배추',0,278),(6092,'오이',0,278),(6093,'참깨',0,278),(6094,'설탕',0,278),(6095,'후추',0,278),(6096,'조선무',0,278),(6097,'감자녹말',0,278),(6098,'돼지고기',0,278),(6099,'소고기',0,278),(6100,'고추가루',0,278),(6101,'식초',0,278),(6102,'수수',0,278),(6103,'전분',0,278),(6104,'참기름',0,278),(6105,'대파',0,278),(6106,'콩기름',0,278),(6107,'쌀',0,279),(6108,'생강',0,279),(6109,'고추',0,279),(6110,'애호박',0,279),(6111,'마늘',0,279),(6112,'재래간장',0,279),(6113,'다시마',0,279),(6114,'호박',0,279),(6115,'닭고기',0,279),(6116,'조선무',0,279),(6117,'풋고추',0,279),(6118,'돼지고기',0,279),(6119,'소고기',0,279),(6120,'도라지',0,279),(6121,'들깨',0,279),(6122,'당근',0,279),(6123,'멸치',0,279),(6124,'참기름',0,279),(6125,'대파',0,279),(6126,'미역',0,280),(6127,'청경채',0,280),(6128,'양파',0,280),(6129,'들기름',0,280),(6130,'재래간장',0,280),(6131,'다시마',0,280),(6132,'크림',0,280),(6133,'설탕',0,280),(6134,'후추',0,280),(6135,'김치',0,280),(6136,'브로콜리',0,280),(6137,'다랑어',0,280),(6138,'들깨',0,280),(6139,'멸치',0,280),(6140,'참기름',0,280),(6141,'배추김치',0,280),(6142,'참치',0,280),(6143,'버터',0,280),(6144,'콩기름',0,280),(6145,'느타리버섯',0,280),(6146,'밀가루',0,280),(6147,'쌀',0,281),(6148,'생강',0,281),(6149,'양파',0,281),(6150,'표고버섯',0,281),(6151,'마늘',1,281),(6152,'설탕',0,281),(6153,'참깨',0,281),(6154,'후추',0,281),(6155,'조선무',0,281),(6156,'소고기',0,281),(6157,'고추가루',0,281),(6158,'배추',0,281),(6159,'당근',1,281),(6160,'참기름',0,281),(6161,'대파',0,281),(6162,'쌀',0,282),(6163,'미역',0,282),(6164,'숙주나물',0,282),(6165,'옥수수',0,282),(6166,'재래간장',0,282),(6167,'다시마',0,282),(6168,'마늘',0,282),(6169,'참깨',0,282),(6170,'돼지고기',0,282),(6171,'소고기',0,282),(6172,'감자',0,282),(6173,'당근',0,282),(6174,'비름',0,282),(6175,'참기름',0,282),(6176,'어묵',0,282),(6177,'게맛살',0,282),(6178,'시금치',0,282),(6179,'쌀',0,283),(6180,'우엉',0,283),(6181,'애호박',0,283),(6182,'청경채',0,283),(6183,'마늘',0,283),(6184,'재래간장',0,283),(6185,'호박',0,283),(6186,'다시마',0,283),(6187,'설탕',0,283),(6188,'후추',0,283),(6189,'돼지고기',0,283),(6190,'소고기',0,283),(6191,'고추가루',0,283),(6192,'감자',0,283),(6193,'멸치',0,283),(6194,'참기름',0,283),(6195,'대파',0,283),(6196,'느타리버섯',0,283),(6197,'쌀',0,284),(6198,'부추',0,284),(6199,'양파',0,284),(6200,'재래간장',0,284),(6201,'다시마',0,284),(6202,'마늘',0,284),(6203,'계란',0,284),(6204,'돌나물',0,284),(6205,'설탕',0,284),(6206,'조선무',0,284),(6207,'고추가루',0,284),(6208,'식초',0,284),(6209,'파래',0,284),(6210,'멸치',0,284),(6211,'참기름',0,284),(6212,'대파',0,284),(6213,'쌀',0,285),(6214,'기장',0,285),(6215,'오징어',0,285),(6216,'양파',0,285),(6217,'마늘',0,285),(6218,'호박',0,285),(6219,'재래간장',0,285),(6220,'다시마',0,285),(6221,'설탕',0,285),(6222,'후추',0,285),(6223,'도라지',0,285),(6224,'감자',0,285),(6225,'당근',0,285),(6226,'멸치',0,285),(6227,'참기름',0,285),(6228,'대파',0,285),(6229,'단호박',0,285),(6230,'쌀',0,286),(6231,'기장',0,286),(6232,'팥',0,286),(6233,'마늘',0,286),(6234,'재래간장',0,286),(6235,'다시마',0,286),(6236,'조선무',0,286),(6237,'마늘쫑',0,286),(6238,'새우',0,286),(6239,'들깨',0,286),(6240,'멸치',0,286),(6241,'수수',0,286),(6242,'참기름',0,286),(6243,'쌀',0,287),(6244,'고구마',0,287),(6245,'포도',0,287),(6246,'마요네즈',0,287),(6247,'옥수수',0,287),(6248,'표고버섯',0,287),(6249,'마늘',0,287),(6250,'호박',0,287),(6251,'참깨',0,287),(6252,'설탕',0,287),(6253,'무말랭이',0,287),(6254,'옥수수통조림',0,287),(6255,'김치',0,287),(6256,'건포도',0,287),(6257,'브로콜리',0,287),(6258,'고추가루',0,287),(6259,'당근',0,287),(6260,'멸치',0,287),(6261,'참기름',0,287),(6262,'양송이',0,287),(6263,'배추김치',0,287),(6264,'대파',0,287),(6265,'단호박',0,287),(6266,'미역',0,288),(6267,'양파',0,288),(6268,'부추',0,288),(6269,'들기름',0,288),(6270,'재래간장',0,288),(6271,'다시마',0,288),(6272,'마늘',0,288),(6273,'크림',0,288),(6274,'설탕',0,288),(6275,'참깨',0,288),(6276,'후추',0,288),(6277,'브로콜리',0,288),(6278,'들깨',0,288),(6279,'식초',0,288),(6280,'멸치',0,288),(6281,'참기름',0,288),(6282,'버터',0,288),(6283,'미나리',0,288),(6284,'밀가루',0,288),(6285,'쌀',0,289),(6286,'우엉',0,289),(6287,'애호박',0,289),(6288,'양파',0,289),(6289,'표고버섯',0,289),(6290,'마늘',1,289),(6291,'호박',0,289),(6292,'다시마',0,289),(6293,'설탕',0,289),(6294,'후추',0,289),(6295,'새우튀김',0,289),(6296,'소고기',0,289),(6297,'고추가루',0,289),(6298,'당근',0,289),(6299,'감자',0,289),(6300,'청주',0,289),(6301,'멸치',0,289),(6302,'참기름',0,289),(6303,'대파',0,289),(6304,'피망',1,289),(6305,'콩기름',0,289),(6306,'밀가루',0,289),(6307,'쌀',0,290),(6308,'두부',0,290),(6309,'마늘',0,290),(6310,'재래간장',0,290),(6311,'다시마',0,290),(6312,'계란',0,290),(6313,'설탕',0,290),(6314,'조선무',0,290),(6315,'소고기',0,290),(6316,'다랑어',0,290),(6317,'브로콜리',0,290),(6318,'고추가루',0,290),(6319,'배추',0,290),(6320,'당근',0,290),(6321,'양지',0,290),(6322,'참기름',0,290),(6323,'참치',0,290),(6324,'대파',0,290),(6325,'쌀',0,291),(6326,'기장',0,291),(6327,'부추',0,291),(6328,'표고버섯',0,291),(6329,'재래간장',0,291),(6330,'다시마',0,291),(6331,'마늘',0,291),(6332,'계란',0,291),(6333,'설탕',0,291),(6334,'참깨',0,291),(6335,'감자녹말',0,291),(6336,'돼지고기',0,291),(6337,'멸치',0,291),(6338,'전분',0,291),(6339,'참기름',0,291),(6340,'대파',0,291),(6341,'콩기름',0,291),(6342,'쌀',0,292),(6343,'양파',0,292),(6344,'마늘',0,292),(6345,'재래간장',0,292),(6346,'호박',0,292),(6347,'후추',0,292),(6348,'김치',0,292),(6349,'돼지고기',0,292),(6350,'다랑어',0,292),(6351,'감자',0,292),(6352,'참기름',0,292),(6353,'배추김치',0,292),(6354,'대파',0,292),(6355,'콩기름',0,292),(6356,'소시지',0,292),(6357,'오징어',0,292),(6358,'참깨',0,292),(6359,'설탕',0,292),(6360,'비엔나',0,292),(6361,'소고기',0,292),(6362,'당근',0,292),(6363,'청주',0,292),(6364,'멸치',0,292),(6365,'참치',0,292),(6366,'피망',0,292),(6367,'단호박',0,292),(6368,'쌀',0,293),(6369,'기장',0,293),(6370,'생강',0,293),(6371,'팥',0,293),(6372,'마늘',0,293),(6373,'후추',0,293),(6374,'조선무',0,293),(6375,'김치',0,293),(6376,'돼지고기',0,293),(6377,'소고기',0,293),(6378,'고추가루',0,293),(6379,'가지',0,293),(6380,'청주',0,293),(6381,'수수',0,293),(6382,'배추김치',0,293),(6383,'대파',0,293),(6384,'피망',0,293),(6385,'된장',0,293),(6386,'쌀',0,294),(6387,'생강',0,294),(6388,'고추',0,294),(6389,'애호박',0,294),(6390,'청경채',0,294),(6391,'표고버섯',0,294),(6392,'마늘',0,294),(6393,'재래간장',0,294),(6394,'다시마',0,294),(6395,'호박',0,294),(6396,'닭고기',0,294),(6397,'조선무',0,294),(6398,'풋고추',0,294),(6399,'브로콜리',0,294),(6400,'들깨',0,294),(6401,'당근',0,294),(6402,'멸치',0,294),(6403,'참기름',0,294),(6404,'양송이',0,294),(6405,'대파',0,294),(6406,'느타리버섯',0,294),(6407,'미역',0,295),(6408,'양파',0,295),(6409,'들기름',0,295),(6410,'재래간장',0,295),(6411,'다시마',0,295),(6412,'마늘',0,295),(6413,'크림',0,295),(6414,'설탕',0,295),(6415,'후추',0,295),(6416,'김치',0,295),(6417,'브로콜리',0,295),(6418,'다랑어',0,295),(6419,'도라지',0,295),(6420,'들깨',0,295),(6421,'멸치',0,295),(6422,'참기름',0,295),(6423,'배추김치',0,295),(6424,'참치',0,295),(6425,'버터',0,295),(6426,'콩기름',0,295),(6427,'밀가루',0,295),(6428,'쌀',0,296),(6429,'우엉',0,296),(6430,'양파',0,296),(6431,'표고버섯',0,296),(6432,'마늘',1,296),(6433,'설탕',0,296),(6434,'후추',0,296),(6435,'김치',1,296),(6436,'새우튀김',0,296),(6437,'소고기',0,296),(6438,'다랑어',0,296),(6439,'당근',1,296),(6440,'감자',0,296),(6441,'청주',0,296),(6442,'멸치',0,296),(6443,'참기름',0,296),(6444,'배추김치',0,296),(6445,'참치',0,296),(6446,'대파',0,296),(6447,'피망',0,296),(6448,'콩기름',0,296),(6449,'밀가루',0,296),(6450,'쌀',0,297),(6451,'양파',0,297),(6452,'옥수수',0,297),(6453,'마늘',0,297),(6454,'설탕',0,297),(6455,'옥수수통조림',0,297),(6456,'돼지고기',0,297),(6457,'소고기',0,297),(6458,'고추장',0,297),(6459,'감자',0,297),(6460,'당근',0,297),(6461,'멸치',0,297),(6462,'뱅어',0,297),(6463,'참기름',0,297),(6464,'어묵',0,297),(6465,'대파',0,297),(6466,'피망',0,297),(6467,'게맛살',0,297),(6468,'시금치',0,297),(6469,'콩기름',0,297),(6470,'된장',0,297),(6471,'밀가루',0,297),(6472,'쌀',0,298),(6473,'대추',0,298),(6474,'양파',0,298),(6475,'마늘',0,298),(6476,'호박',0,298),(6477,'재래간장',0,298),(6478,'닭고기',0,298),(6479,'베이컨',0,298),(6480,'후추',0,298),(6481,'돼지고기',0,298),(6482,'감자',0,298),(6483,'대파',0,298),(6484,'인삼',0,298),(6485,'스파게티',0,298),(6486,'다시마',0,298),(6487,'돈까스소스',0,298),(6488,'치즈',0,298),(6489,'소고기',0,298),(6490,'당근',0,298),(6491,'멸치',0,298),(6492,'양송이',0,298),(6493,'대구',0,298),(6494,'피망',0,298),(6495,'버터',0,298),(6496,'수삼',0,298),(6497,'쌀',0,299),(6498,'부추',0,299),(6499,'표고버섯',0,299),(6500,'두부',0,299),(6501,'마늘',0,299),(6502,'재래간장',0,299),(6503,'다시마',0,299),(6504,'설탕',0,299),(6505,'조선무',0,299),(6506,'감자녹말',0,299),(6507,'돼지고기',0,299),(6508,'소고기',0,299),(6509,'브로콜리',0,299),(6510,'고추가루',0,299),(6511,'배추',0,299),(6512,'당근',0,299),(6513,'양지',0,299),(6514,'참기름',0,299),(6515,'전분',0,299),(6516,'양송이',0,299),(6517,'대파',0,299),(6518,'콩기름',0,299),(6519,'쌀',0,300),(6520,'기장',0,300),(6521,'부추',0,300),(6522,'재래간장',0,300),(6523,'다시마',0,300),(6524,'마늘',0,300),(6525,'계란',0,300),(6526,'김치',0,300),(6527,'돼지고기',0,300),(6528,'브로콜리',0,300),(6529,'청주',0,300),(6530,'멸치',0,300),(6531,'배추김치',0,300),(6532,'대파',0,300),(6533,'된장',0,300),(6534,'쌀',0,301),(6535,'생강',0,301),(6536,'고추',0,301),(6537,'애호박',0,301),(6538,'마요네즈',0,301),(6539,'마늘',0,301),(6540,'호박',0,301),(6541,'다시마',0,301),(6542,'닭고기',0,301),(6543,'상추',0,301),(6544,'풋고추',0,301),(6545,'다랑어',0,301),(6546,'고추가루',0,301),(6547,'올리브유',0,301),(6548,'당근',0,301),(6549,'감자',0,301),(6550,'멸치',0,301),(6551,'참기름',0,301),(6552,'참치',0,301),(6553,'양상추',0,301),(6554,'대파',0,301),(6555,'요구르트',0,301),(6556,'쌀',0,302),(6557,'기장',0,302),(6558,'생강',0,302),(6559,'팥',0,302),(6560,'표고버섯',0,302),(6561,'마늘',0,302),(6562,'설탕',0,302),(6563,'참깨',0,302),(6564,'후추',0,302),(6565,'비엔나',0,302),(6566,'조선무',0,302),(6567,'소고기',0,302),(6568,'감자',0,302),(6569,'청주',0,302),(6570,'수수',0,302),(6571,'대파',0,302),(6572,'피망',0,302),(6573,'콩기름',0,302),(6574,'소시지',0,302),(6575,'쌀',0,303),(6576,'마늘',0,303),(6577,'재래간장',0,303),(6578,'설탕',0,303),(6579,'김치',0,303),(6580,'돼지고기',0,303),(6581,'소고기',0,303),(6582,'콩가루',0,303),(6583,'다랑어',0,303),(6584,'고추가루',0,303),(6585,'가지',0,303),(6586,'멸치',0,303),(6587,'참기름',0,303),(6588,'배추김치',0,303),(6589,'참치',0,303),(6590,'대파',0,303),(6591,'피망',0,303),(6592,'냉이',0,303),(6593,'콩기름',0,303),(6594,'된장',0,303),(6595,'미역',0,304),(6596,'청경채',0,304),(6597,'양파',0,304),(6598,'들기름',0,304),(6599,'재래간장',0,304),(6600,'다시마',0,304),(6601,'마늘',0,304),(6602,'크림',0,304),(6603,'후추',0,304),(6604,'브로콜리',0,304),(6605,'도라지',0,304),(6606,'들깨',0,304),(6607,'멸치',0,304),(6608,'참기름',0,304),(6609,'버터',0,304),(6610,'느타리버섯',0,304),(6611,'밀가루',0,304);
/*!40000 ALTER TABLE `diet_material` ENABLE KEYS */;
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