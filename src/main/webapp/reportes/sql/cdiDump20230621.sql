-- MariaDB dump 10.19  Distrib 10.4.25-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: cdi
-- ------------------------------------------------------
-- Server version	10.4.25-MariaDB

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
-- Table structure for table `audits_socio`
--

DROP TABLE IF EXISTS `audits_socio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audits_socio` (
  `idsocio` int(11) NOT NULL DEFAULT 0,
  `nrosocio` int(11) DEFAULT NULL,
  `nombre` varchar(200) DEFAULT NULL,
  `fechanac` datetime DEFAULT NULL,
  `fechaingreso` datetime DEFAULT NULL,
  `medica` varchar(45) DEFAULT NULL,
  `ci` varchar(45) DEFAULT NULL,
  `foto` longblob DEFAULT NULL,
  `actividad` varchar(45) DEFAULT NULL,
  `idfecivil` int(11) DEFAULT NULL,
  `idnacionalidad` int(11) DEFAULT NULL,
  `idTipoCuota` int(11) DEFAULT NULL,
  `idcategoria` int(11) DEFAULT NULL,
  `idcestado` int(11) DEFAULT NULL,
  `corre` int(11) DEFAULT NULL,
  `idmediopago` int(11) NOT NULL,
  `fechaGrabado` timestamp NOT NULL DEFAULT current_timestamp(),
  `operacion` varchar(20) DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `fechaGrabado_automatico` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audits_socio`
--

LOCK TABLES `audits_socio` WRITE;
/*!40000 ALTER TABLE `audits_socio` DISABLE KEYS */;
/*!40000 ALTER TABLE `audits_socio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Escolar-grande'),(2,'Adolescente'),(3,'Adulto'),(4,'Adulto mayor');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Activo'),(2,'Inactivo');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `anio` int(11) NOT NULL,
  `fechaGrabado` datetime NOT NULL DEFAULT curdate(),
  `importe` int(11) NOT NULL,
  `mes` int(11) NOT NULL,
  `idSocio` int(11) NOT NULL,
  `idTipoCuota` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pagos_idUsuario` (`idUsuario`),
  KEY `FK_pagos_idTipoCuota` (`idTipoCuota`),
  KEY `FK_pagos_idSocio` (`idSocio`),
  CONSTRAINT `FK_pagos_idSocio` FOREIGN KEY (`idSocio`) REFERENCES `socio` (`id`),
  CONSTRAINT `FK_pagos_idTipoCuota` FOREIGN KEY (`idTipoCuota`) REFERENCES `tipocuota` (`id`),
  CONSTRAINT `FK_pagos_idUsuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (2,2022,'2023-06-08 14:36:27',1210,7,1,7,1),(3,2022,'2023-06-10 20:49:21',1200,8,1,7,1),(4,2022,'2023-06-10 20:50:50',1200,9,1,7,1),(5,2022,'2023-06-10 20:53:00',1200,10,1,7,1),(21,2023,'2023-06-21 18:31:43',1100,6,9,1,1);
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `estado` tinyint(1) DEFAULT 0,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'administrador',1,'admin');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socio`
--

DROP TABLE IF EXISTS `socio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `APELLIDO` varchar(255) DEFAULT NULL,
  `CELULAR` varchar(255) DEFAULT NULL,
  `CELULAREMERGENCIA` varchar(255) DEFAULT NULL,
  `CI` int(11) DEFAULT NULL,
  `DOMICILIO` varchar(255) DEFAULT NULL,
  `EMERGENCIAMOVIL` varchar(255) DEFAULT NULL,
  `FECHAINSCRIPCION` datetime DEFAULT NULL,
  `FECHANACIMIENTO` datetime DEFAULT NULL,
  `foto` longblob DEFAULT NULL,
  `MUTUALISTA` varchar(255) DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `NUMEROSOCIO` int(11) DEFAULT NULL,
  `VENCIMIENTOCSALUD` datetime DEFAULT NULL,
  `idCategoria` int(11) DEFAULT NULL,
  `idcestado` int(11) DEFAULT NULL,
  `idTipoCuota` int(11) DEFAULT NULL,
  `EMAIL` varchar(200) DEFAULT NULL,
  `OBSERVACIONES` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_socio_idCategoria` (`idCategoria`),
  KEY `FK_socio_idcestado` (`idcestado`),
  KEY `FK_socio_idTipoCuota` (`idTipoCuota`),
  CONSTRAINT `FK_socio_idCategoria` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`),
  CONSTRAINT `FK_socio_idTipoCuota` FOREIGN KEY (`idTipoCuota`) REFERENCES `tipocuota` (`id`),
  CONSTRAINT `FK_socio_idcestado` FOREIGN KEY (`idcestado`) REFERENCES `estado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socio`
--

LOCK TABLES `socio` WRITE;
/*!40000 ALTER TABLE `socio` DISABLE KEYS */;
INSERT INTO `socio` VALUES (1,'Castiglioni','099151611','099324708',18729240,'el cairo 3427','semm','2022-06-07 00:00:00','1973-09-26 00:00:00',NULL,'casmu','Maximiliano',0,'2024-09-26 00:00:00',3,1,7,'123',''),(9,'cuchufletti','099151611','1234',12222,'el cairo 3427','213','2023-06-06 00:00:00','1971-03-11 00:00:00',NULL,'123','pepito',0,NULL,1,1,1,'123','');
/*!40000 ALTER TABLE `socio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `sociosview`
--

DROP TABLE IF EXISTS `sociosview`;
/*!50001 DROP VIEW IF EXISTS `sociosview`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `sociosview` (
  `idsocio` tinyint NOT NULL,
  `numerosocio` tinyint NOT NULL,
  `nombre` tinyint NOT NULL,
  `idtipocuota` tinyint NOT NULL,
  `idcategoria` tinyint NOT NULL,
  `idcestado` tinyint NOT NULL,
  `fechaGrabado` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `tipocuota`
--

DROP TABLE IF EXISTS `tipocuota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipocuota` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `importe` int(11) DEFAULT NULL,
  `nombreTipo` varchar(255) DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipocuota`
--

LOCK TABLES `tipocuota` WRITE;
/*!40000 ALTER TABLE `tipocuota` DISABLE KEYS */;
INSERT INTO `tipocuota` VALUES (1,1100,'Infancia-adolesc. (Coop.Mesa3)','Socio Coop. Mesa 3 < 16 años'),(3,1300,'Infancia-adolesc. (Común)','Infancia-adolescentes(Común) < 16 años'),(4,1300,'Adultos-Cooperativa ','Socio cooperativista mesa  (16-59 años)'),(5,1500,'Socio común',' (16-59 años)'),(6,950,'Adultos mayores-Cooperativa.','Adultos mayores a partir de 60 años. Cooperativa'),(7,1200,'Adultos mayores-Común','Adulto mayor socio común');
/*!40000 ALTER TABLE `tipocuota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Clave` varchar(255) NOT NULL,
  `eMail` varchar(255) NOT NULL,
  `Estado` tinyint(1) NOT NULL DEFAULT 0,
  `Nombre` varchar(255) NOT NULL,
  `rol_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_usuario_rol_id` (`rol_id`),
  CONSTRAINT `FK_usuario_rol_id` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'123','ucastiglioni@gmail.com',0,'maxi',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valortipocuota`
--

DROP TABLE IF EXISTS `valortipocuota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valortipocuota` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoCuota` int(11) DEFAULT NULL,
  `importe` int(11) DEFAULT NULL,
  `vigencia` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valortipocuota`
--

LOCK TABLES `valortipocuota` WRITE;
/*!40000 ALTER TABLE `valortipocuota` DISABLE KEYS */;
INSERT INTO `valortipocuota` VALUES (1,1,1100,'2023-05-01 00:00:00'),(2,2,1300,'2023-05-01 00:00:00'),(3,3,1300,'2023-05-01 00:00:00'),(4,4,1500,'2023-05-01 00:00:00'),(5,5,1500,'2023-05-01 00:00:00'),(6,6,950,'2023-05-01 00:00:00'),(7,7,1200,'2023-05-01 00:00:00');
/*!40000 ALTER TABLE `valortipocuota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `sociosview`
--

/*!50001 DROP TABLE IF EXISTS `sociosview`*/;
/*!50001 DROP VIEW IF EXISTS `sociosview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sociosview` AS select `socio`.`id` AS `idsocio`,`socio`.`NUMEROSOCIO` AS `numerosocio`,`socio`.`NOMBRE` AS `nombre`,`socio`.`idTipoCuota` AS `idtipocuota`,`socio`.`idCategoria` AS `idcategoria`,`socio`.`idcestado` AS `idcestado`,`socio`.`FECHAINSCRIPCION` AS `fechaGrabado` from `socio` union select `audits_socio`.`idsocio` AS `idsocio`,`audits_socio`.`nrosocio` AS `nrosocio`,`audits_socio`.`nombre` AS `nombre`,`audits_socio`.`idTipoCuota` AS `idtipocuota`,`audits_socio`.`idcategoria` AS `idcategoria`,`audits_socio`.`idcestado` AS `idcestado`,`audits_socio`.`fechaGrabado` AS `fechaGrabado` from `audits_socio` where `audits_socio`.`operacion` = 'M' */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-21 18:39:36
