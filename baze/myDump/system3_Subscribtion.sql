CREATE DATABASE  IF NOT EXISTS `system3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `system3`;
-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: localhost    Database: system3
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `Subscribtion`
--

DROP TABLE IF EXISTS `Subscribtion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Subscribtion` (
  `date` datetime NOT NULL,
  `price` int NOT NULL,
  `package` int NOT NULL,
  `user` varchar(255) NOT NULL,
  PRIMARY KEY (`package`,`user`),
  KEY `FK_Subscribtion_user` (`user`),
  CONSTRAINT `FK_Subscribtion_package` FOREIGN KEY (`package`) REFERENCES `Package` (`packageID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Subscribtion_user` FOREIGN KEY (`user`) REFERENCES `User` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subscribtion`
--

LOCK TABLES `Subscribtion` WRITE;
/*!40000 ALTER TABLE `Subscribtion` DISABLE KEYS */;
INSERT INTO `Subscribtion` VALUES ('2024-01-26 17:10:01',1000,3,'kosta01856'),('2024-01-26 17:10:56',800,4,'miki'),('2024-01-26 17:10:22',600,5,'fica'),('2024-01-26 17:11:23',600,5,'wreckreators'),('2024-01-26 17:10:46',300,6,'damjan01'),('2024-01-26 17:09:02',1800,7,'kosta01856');
/*!40000 ALTER TABLE `Subscribtion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-26 17:23:03
