CREATE DATABASE  IF NOT EXISTS `sistemaconsorcio` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_general_ci */;
USE `sistemaconsorcio`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sistemaconsorcio
-- ------------------------------------------------------
-- Server version	5.6.21-log

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
-- Table structure for table `administradora`
--

DROP TABLE IF EXISTS `administradora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administradora` (
  `codigoAdm` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`codigoAdm`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administradora`
--

LOCK TABLES `administradora` WRITE;
/*!40000 ALTER TABLE `administradora` DISABLE KEYS */;
INSERT INTO `administradora` VALUES (1,'Consórcio Honda'),(2,'Consórcio Yamaha'),(3,'Consórcio BB');
/*!40000 ALTER TABLE `administradora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `codigoCliente` int(11) NOT NULL AUTO_INCREMENT,
  `CPF` varchar(45) COLLATE latin1_general_ci DEFAULT NULL,
  `nome` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  `endereco` varchar(256) COLLATE latin1_general_ci DEFAULT NULL,
  `cidade` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  `estado` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  `foneResidencial` varchar(45) COLLATE latin1_general_ci DEFAULT NULL,
  `foneCelular` varchar(45) COLLATE latin1_general_ci DEFAULT NULL,
  `email` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`codigoCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'66275954191','Marcelo de Andréa Nahabedian','Rua PHK, 470','Campo Grande','MS','(67)9234-4882','2',NULL),(2,'11111111111','Izaias Xavier Araujo',NULL,NULL,NULL,NULL,'2',NULL);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comissao`
--

DROP TABLE IF EXISTS `comissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comissao` (
  `codigoComissao` int(11) NOT NULL AUTO_INCREMENT,
  `codigoVenda` int(11) DEFAULT NULL,
  `nroParcela` int(11) DEFAULT NULL,
  `dtEfetivaRecebimento` date DEFAULT NULL,
  `vlrEfetivoRecebimento` double DEFAULT NULL,
  `dtPrevista` date DEFAULT NULL,
  `vlrPrevisto` double DEFAULT NULL,
  `tipoComissao` int(1) DEFAULT NULL,
  `dtCancelamentoEstorno` date DEFAULT NULL,
  PRIMARY KEY (`codigoComissao`),
  KEY `fk_Comissao_1_idx` (`codigoVenda`),
  CONSTRAINT `fk_Comissao_1` FOREIGN KEY (`codigoVenda`) REFERENCES `venda` (`codigoVenda`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comissao`
--

LOCK TABLES `comissao` WRITE;
/*!40000 ALTER TABLE `comissao` DISABLE KEYS */;
/*!40000 ALTER TABLE `comissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modeloveiculo`
--

DROP TABLE IF EXISTS `modeloveiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modeloveiculo` (
  `codigoModelo` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(256) COLLATE latin1_general_ci DEFAULT NULL,
  `marca` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`codigoModelo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modeloveiculo`
--

LOCK TABLES `modeloveiculo` WRITE;
/*!40000 ALTER TABLE `modeloveiculo` DISABLE KEYS */;
INSERT INTO `modeloveiculo` VALUES (1,'Honda Titan','Honda'),(2,'Honda Biz','Honda'),(3,'Motor 15HP','Yamaha'),(4,'Gol','Volkswagen'),(5,'Palio','Fiat'),(6,'Uno','Fiat'),(7,'Hilux','Toyota');
/*!40000 ALTER TABLE `modeloveiculo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pontovenda`
--

DROP TABLE IF EXISTS `pontovenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pontovenda` (
  `codigoPtoVenda` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`codigoPtoVenda`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pontovenda`
--

LOCK TABLES `pontovenda` WRITE;
/*!40000 ALTER TABLE `pontovenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `pontovenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regrascomissao`
--

DROP TABLE IF EXISTS `regrascomissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regrascomissao` (
  `codigoRegra` int(11) NOT NULL AUTO_INCREMENT,
  `percentual` double DEFAULT NULL,
  `dtIniVigenciaComissao` date DEFAULT NULL,
  `dtFimVigenciaComissao` date DEFAULT NULL,
  `parcelamentoComissao` int(11) DEFAULT NULL,
  `formaEstorno` varchar(45) COLLATE latin1_general_ci DEFAULT NULL,
  `codigoAdm` int(11) DEFAULT NULL,
  `codigoComissao` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigoRegra`),
  KEY `fk_RegrasComissao_1_idx` (`codigoComissao`),
  KEY `fk_RegrasComissao_2_idx` (`codigoAdm`),
  CONSTRAINT `fk_RegrasComissao_1` FOREIGN KEY (`codigoComissao`) REFERENCES `comissao` (`codigoComissao`) ON UPDATE CASCADE,
  CONSTRAINT `fk_RegrasComissao_2` FOREIGN KEY (`codigoAdm`) REFERENCES `administradora` (`codigoAdm`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regrascomissao`
--

LOCK TABLES `regrascomissao` WRITE;
/*!40000 ALTER TABLE `regrascomissao` DISABLE KEYS */;
/*!40000 ALTER TABLE `regrascomissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subpontovenda`
--

DROP TABLE IF EXISTS `subpontovenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subpontovenda` (
  `codigoSbPtoVenda` int(11) NOT NULL AUTO_INCREMENT,
  `codigoPtoVenda` int(11) DEFAULT NULL,
  `nome` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`codigoSbPtoVenda`),
  KEY `fk_SubPontoVenda_1_idx` (`codigoPtoVenda`),
  CONSTRAINT `fk_SubPontoVenda_1` FOREIGN KEY (`codigoPtoVenda`) REFERENCES `pontovenda` (`codigoPtoVenda`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subpontovenda`
--

LOCK TABLES `subpontovenda` WRITE;
/*!40000 ALTER TABLE `subpontovenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `subpontovenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxa`
--

DROP TABLE IF EXISTS `taxa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taxa` (
  `codigoTaxa` int(11) NOT NULL AUTO_INCREMENT,
  `percentualTaxa` double DEFAULT NULL,
  `codigoAdm` int(11) DEFAULT NULL,
  `codigoTpTaxa` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigoTaxa`),
  KEY `fk_Taxa_1_idx` (`codigoTpTaxa`),
  KEY `fk_Taxa_2_idx` (`codigoAdm`),
  CONSTRAINT `fk_Taxa_1` FOREIGN KEY (`codigoTpTaxa`) REFERENCES `tipotaxa` (`codigoTpTaxa`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Taxa_2` FOREIGN KEY (`codigoAdm`) REFERENCES `administradora` (`codigoAdm`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxa`
--

LOCK TABLES `taxa` WRITE;
/*!40000 ALTER TABLE `taxa` DISABLE KEYS */;
/*!40000 ALTER TABLE `taxa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipotaxa`
--

DROP TABLE IF EXISTS `tipotaxa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipotaxa` (
  `codigoTpTaxa` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(128) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`codigoTpTaxa`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipotaxa`
--

LOCK TABLES `tipotaxa` WRITE;
/*!40000 ALTER TABLE `tipotaxa` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipotaxa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venda`
--

DROP TABLE IF EXISTS `venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venda` (
  `codigoVenda` int(11) NOT NULL AUTO_INCREMENT,
  `dataCadastro` date DEFAULT NULL,
  `nroContrato` varchar(20) COLLATE latin1_general_ci DEFAULT NULL,
  `dtIniVigencia` date DEFAULT NULL,
  `qtdParcelas` int(11) DEFAULT NULL COMMENT '	',
  `dtParcEntrada` date DEFAULT NULL,
  `vlrParcEntrada` double DEFAULT NULL,
  `vlrBem` double DEFAULT NULL,
  `observacao` varchar(256) COLLATE latin1_general_ci DEFAULT NULL,
  `grupoConsorcio` int(11) DEFAULT NULL,
  `cotaConsorcio` int(11) DEFAULT NULL,
  `codigoCliente` int(11) DEFAULT NULL,
  `codigoModelo` int(11) DEFAULT NULL,
  `codigoAdm` int(11) DEFAULT NULL,
  `codigoVendedor` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigoVenda`),
  KEY `fk_Venda_1_idx` (`codigoCliente`),
  KEY `fk_Venda_2_idx` (`codigoAdm`),
  KEY `fk_Venda_3_idx` (`codigoVendedor`),
  KEY `fk_Venda_4_idx` (`codigoModelo`),
  CONSTRAINT `fk_Venda_1` FOREIGN KEY (`codigoCliente`) REFERENCES `cliente` (`codigoCliente`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Venda_2` FOREIGN KEY (`codigoAdm`) REFERENCES `administradora` (`codigoAdm`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Venda_3` FOREIGN KEY (`codigoVendedor`) REFERENCES `vendedor` (`codigoVendedor`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Venda_4` FOREIGN KEY (`codigoModelo`) REFERENCES `modeloveiculo` (`codigoModelo`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venda`
--

LOCK TABLES `venda` WRITE;
/*!40000 ALTER TABLE `venda` DISABLE KEYS */;
INSERT INTO `venda` VALUES (1,'2014-11-07','1222333','2014-12-31',18,'2014-11-10',500,1800,'teste',3,3001,1,7,3,1),(2,'2014-11-07','233','2014-11-07',14,'2014-11-07',1000,14000,'vai dar certo',1,3,1,3,3,2),(4,'2014-11-09','666','2014-11-10',12,'2014-11-10',3000,7000,'Izaias Caloteiro',666,13,2,2,1,1),(5,'2014-11-09','555','2014-11-10',12,'2014-11-10',5000,60000,NULL,666,1,1,1,2,1),(6,'2014-11-09','999999','2014-11-01',12,'2014-11-10',1000,13000,'',1234,123456,2,3,2,2),(8,'2014-11-09','999123','2014-12-01',13,'2014-11-10',2000,15000,'foi alterado',1234,123456,2,3,3,1);
/*!40000 ALTER TABLE `venda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendedor`
--

DROP TABLE IF EXISTS `vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendedor` (
  `codigoVendedor` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) COLLATE latin1_general_ci DEFAULT NULL,
  `dtInicio` date DEFAULT NULL,
  `dtFim` date DEFAULT NULL,
  `codigoSbPtoVenda` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigoVendedor`),
  KEY `fk_Vendedor_1_idx` (`codigoSbPtoVenda`),
  CONSTRAINT `fk_Vendedor_1` FOREIGN KEY (`codigoSbPtoVenda`) REFERENCES `subpontovenda` (`codigoSbPtoVenda`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendedor`
--

LOCK TABLES `vendedor` WRITE;
/*!40000 ALTER TABLE `vendedor` DISABLE KEYS */;
INSERT INTO `vendedor` VALUES (1,'Smaily','2014-01-01','2014-12-31',NULL),(2,'Jairo',NULL,NULL,NULL);
/*!40000 ALTER TABLE `vendedor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-10 16:41:27
