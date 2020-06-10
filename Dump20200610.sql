-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: uni_db
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_name` varchar(30) NOT NULL,
  `semester` int DEFAULT NULL,
  `prof_id` varchar(20) NOT NULL,
  `passed` int DEFAULT NULL,
  `examined` int DEFAULT NULL,
  `course_id` varchar(45) NOT NULL,
  `school` varchar(45) DEFAULT NULL,
  `dept` varchar(45) DEFAULT NULL,
  `subclass` varchar(45) DEFAULT NULL,
  `id` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`prof_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('er',1,'19277',120,300,'5','Money','Tech',NULL,'19127','m'),('fghj',2,'20198',250,300,'6','Tech','Man',NULL,'19127','o'),('er',1,'19277',120,300,'5','Money','Man',NULL,'19248','m'),('tyert',3,'20198',10,300,'7','Tech','Tech',NULL,'19248','o');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forms`
--

DROP TABLE IF EXISTS `forms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forms` (
  `id` varchar(45) NOT NULL,
  `course1` varchar(45) DEFAULT NULL,
  `course2` varchar(45) DEFAULT NULL,
  `course3` varchar(45) DEFAULT NULL,
  `course4` varchar(45) DEFAULT NULL,
  `course5` varchar(45) DEFAULT NULL,
  `course6` varchar(45) DEFAULT NULL,
  `course7` varchar(45) DEFAULT NULL,
  `course8` varchar(45) DEFAULT NULL,
  `formscol6` varchar(45) DEFAULT NULL,
  `formscol7` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forms`
--

LOCK TABLES `forms` WRITE;
/*!40000 ALTER TABLE `forms` DISABLE KEYS */;
INSERT INTO `forms` VALUES ('19127','fghj',NULL,NULL,NULL,NULL,NULL,NULL,'sfdgsdfghdsfghdfgvbn',NULL,NULL),('19248',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `forms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grades` (
  `grade` int DEFAULT NULL,
  `course_id` varchar(45) NOT NULL,
  `id` varchar(45) NOT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES (5,'5','19248'),(3,'6','19127'),(7,'7','19248');
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `user_id` varchar(45) NOT NULL,
  `course_id` varchar(45) DEFAULT NULL,
  `school` varchar(45) DEFAULT NULL,
  `text` mediumtext,
  `title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES ('20189',NULL,NULL,NULL,NULL),('20198',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planner`
--

DROP TABLE IF EXISTS `planner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planner` (
  `id` varchar(45) NOT NULL,
  `event_name` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `hour` varchar(45) DEFAULT NULL,
  `description` tinytext,
  `event_id` varchar(45) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planner`
--

LOCK TABLES `planner` WRITE;
/*!40000 ALTER TABLE `planner` DISABLE KEYS */;
INSERT INTO `planner` VALUES ('19127',NULL,NULL,NULL,NULL,'19127'),('19127',NULL,NULL,NULL,NULL,'191271'),('19248','sd',NULL,NULL,'sdfg','19248'),('20189',NULL,NULL,NULL,NULL,'20189');
/*!40000 ALTER TABLE `planner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presentations`
--

DROP TABLE IF EXISTS `presentations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `presentations` (
  `id` varchar(45) NOT NULL,
  `course_id` varchar(45) NOT NULL,
  `file` blob,
  `file_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presentations`
--

LOCK TABLES `presentations` WRITE;
/*!40000 ALTER TABLE `presentations` DISABLE KEYS */;
INSERT INTO `presentations` VALUES ('19277','5',NULL,NULL),('20198','6',NULL,NULL);
/*!40000 ALTER TABLE `presentations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests` (
  `id` varchar(45) NOT NULL,
  `school` varchar(45) DEFAULT NULL,
  `request1` varchar(45) DEFAULT NULL,
  `request2` varchar(45) DEFAULT NULL,
  `info` tinytext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES ('19127','Tech','Πιστοποιητικό Αναλυτικής Βαθμολογίας','Πιστοποιητικό Σπουδών',''),('19248','Tech','Πιστοποιητικό Αναλυτικής Βαθμολογίας','Πιστοποιητικό Σπουδών',NULL);
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `usname` varchar(40) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `id` varchar(20) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `user_type` varchar(45) NOT NULL,
  `semester` int DEFAULT NULL,
  `school` varchar(45) NOT NULL,
  `dept` varchar(45) DEFAULT NULL,
  `subclass` varchar(45) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('alex','alex','deon','19127','patatas@gmail.com','stud',2,'Tech','Applied','Applied','1234'),('peos','peos','lolo','19248','peos@gmail.com','stud',3,'Tech','Applied','Applied','4567'),('zeus','zeus','lion','19277','cyka@gmail.com','prof',NULL,'Tech','Applied','Applied','2345'),('foo','foo','foo','20189','f@gmail.com','sec',NULL,'Tech','','','3456'),('sa','sa','sa','20198','s@gmail.com','prof',NULL,'Tech','Applied','Applied','5678');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-10 18:38:40
