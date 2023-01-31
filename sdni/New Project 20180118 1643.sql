-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.12-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema sdni
--

CREATE DATABASE IF NOT EXISTS sdni;
USE sdni;

--
-- Definition of table `documento`
--

DROP TABLE IF EXISTS `documento`;
CREATE TABLE `documento` (
  `iddocumento` int(11) NOT NULL AUTO_INCREMENT,
  `dtaCadastro` date NOT NULL,
  `nomeDocOriginal` varchar(100) NOT NULL,
  `nomedoc` varchar(100) NOT NULL,
  `status` varchar(7) NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `uriDocumento` varchar(255) NOT NULL,
  PRIMARY KEY (`iddocumento`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `documento`
--

/*!40000 ALTER TABLE `documento` DISABLE KEYS */;
INSERT INTO `documento` (`iddocumento`,`dtaCadastro`,`nomeDocOriginal`,`nomedoc`,`status`,`titulo`,`uriDocumento`) VALUES 
 (1,'0000-00-00','testessfsdfastretsas','sfsdfsdfasdfsafsdfsdf','INATIVO','sfsdfasdfas  asfg asg dafg dafg as fasdfa','http://localhoste.teste.teste'),
 (2,'2018-01-05','arquivo_teste.pdf','arquivo_teste-6108750939007734460.pdf','ATIVO','Arquivo de testes','http://192.168.5.124:8090/sdni/documento/arquivo_teste-6108750939007734460.pdf');
/*!40000 ALTER TABLE `documento` ENABLE KEYS */;


--
-- Definition of table `grupo`
--

DROP TABLE IF EXISTS `grupo`;
CREATE TABLE `grupo` (
  `idgrupo` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(80) DEFAULT NULL,
  `nome` varchar(40) NOT NULL,
  PRIMARY KEY (`idgrupo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `grupo`
--

/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` (`idgrupo`,`descricao`,`nome`) VALUES 
 (1,'Tem acesso a todas as funcionalidades','ADMINISTRADOR'),
 (2,'Acesso cadastro de usuários','ACESSO_USUARIO');
/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;


--
-- Definition of table `grupo_permissao`
--

DROP TABLE IF EXISTS `grupo_permissao`;
CREATE TABLE `grupo_permissao` (
  `grupo_id` int(11) NOT NULL,
  `permissao_id` int(11) NOT NULL,
  KEY `IND_grupo_id` (`grupo_id`) USING BTREE,
  KEY `IND_permissao_id` (`grupo_id`) USING BTREE,
  KEY `FK_permissao_id` (`permissao_id`),
  CONSTRAINT `FK_grupo_id` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `FK_permissao_id` FOREIGN KEY (`permissao_id`) REFERENCES `permissao` (`idpermissao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `grupo_permissao`
--

/*!40000 ALTER TABLE `grupo_permissao` DISABLE KEYS */;
INSERT INTO `grupo_permissao` (`grupo_id`,`permissao_id`) VALUES 
 (1,1),
 (1,2),
 (2,2);
/*!40000 ALTER TABLE `grupo_permissao` ENABLE KEYS */;


--
-- Definition of table `permissao`
--

DROP TABLE IF EXISTS `permissao`;
CREATE TABLE `permissao` (
  `idpermissao` int(10) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  PRIMARY KEY (`idpermissao`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `permissao`
--

/*!40000 ALTER TABLE `permissao` DISABLE KEYS */;
INSERT INTO `permissao` (`idpermissao`,`nome`,`descricao`) VALUES 
 (1,'GERENCIAR_DOCUMENTO','Acesso a funcionalidade de documentos'),
 (2,'GERENCIAR_USUARIO','Acesso a funcionalidade de documentos');
/*!40000 ALTER TABLE `permissao` ENABLE KEYS */;


--
-- Definition of table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `estatus` varchar(7) NOT NULL,
  `loginUsuario` varchar(255) NOT NULL,
  `nomeCompleto` varchar(80) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `UK_sppemarcnb01empec19issnnd` (`loginUsuario`),
  UNIQUE KEY `UK_jluuldl55w1rbbgl080q6qepq` (`nomeCompleto`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usuario`
--

/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`idusuario`,`email`,`estatus`,`loginUsuario`,`nomeCompleto`,`senha`) VALUES 
 (1,'livia.lima@sesc-am.com.br','ATIVO','livia.lima','Livia Januário de Lima','d41d8cd98f00b204e9800998ecf8427e'),
 (2,'hermogenes.silva@sesc-am.com.br','ATIVO','hermogenes.silva','Hermogenes Silva e Silva','6be3cb65cda78221a35772007bbdfa45'),
 (3,'wescley.rabelo@sesc-am.com.br','ATIVO','wescley.rabelo','Wescley Rabelo','202cb962ac59075b964b07152d234b70');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


--
-- Definition of table `usuario_grupo`
--

DROP TABLE IF EXISTS `usuario_grupo`;
CREATE TABLE `usuario_grupo` (
  `usuario_id` int(11) NOT NULL,
  `grupo_id` int(11) NOT NULL,
  KEY `IND_idGrupo` (`grupo_id`) USING BTREE,
  KEY `IND_idUsuario` (`grupo_id`) USING BTREE,
  CONSTRAINT `FK_idGrupo` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `FK_idUsuario` FOREIGN KEY (`grupo_id`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usuario_grupo`
--

/*!40000 ALTER TABLE `usuario_grupo` DISABLE KEYS */;
INSERT INTO `usuario_grupo` (`usuario_id`,`grupo_id`) VALUES 
 (3,1),
 (2,1),
 (1,1);
/*!40000 ALTER TABLE `usuario_grupo` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
