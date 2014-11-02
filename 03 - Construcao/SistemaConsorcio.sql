SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `sistemaconsorcio` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `sistemaconsorcio` ;

-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`Cliente`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`Cliente` (
  `codigoCliente` INT NOT NULL ,
  `CPF` VARCHAR(45) NULL ,
  `nome` VARCHAR(128) NULL ,
  `endereco` VARCHAR(256) NULL ,
  `cidade` VARCHAR(128) NULL ,
  `estado` VARCHAR(128) NULL ,
  `foneResidencial` VARCHAR(45) NULL ,
  `foneCelular` VARCHAR(45) NULL ,
  `email` VARCHAR(128) NULL ,
  PRIMARY KEY (`codigoCliente`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`Administradora`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`Administradora` (
  `codigoAdm` INT NOT NULL ,
  `nome` VARCHAR(128) NULL ,
  PRIMARY KEY (`codigoAdm`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`PontoVenda`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`PontoVenda` (
  `codigoPtoVenda` INT NOT NULL ,
  `nome` VARCHAR(128) NULL ,
  PRIMARY KEY (`codigoPtoVenda`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`SubPontoVenda`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`SubPontoVenda` (
  `codigoSbPtoVenda` INT NOT NULL ,
  `codigoPtoVenda` INT NULL ,
  `nome` VARCHAR(128) NULL ,
  PRIMARY KEY (`codigoSbPtoVenda`) ,
  INDEX `fk_SubPontoVenda_1` (`codigoPtoVenda` ASC) ,
  CONSTRAINT `fk_SubPontoVenda_1`
    FOREIGN KEY (`codigoPtoVenda` )
    REFERENCES `sistemaconsorcio`.`PontoVenda` (`codigoPtoVenda` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`Vendedor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`Vendedor` (
  `codigoVendedor` INT NOT NULL ,
  `nome` VARCHAR(45) NULL ,
  `dtInicio` DATE NULL ,
  `dtFim` DATE NULL ,
  `codigoSbPtoVenda` INT NULL ,
  PRIMARY KEY (`codigoVendedor`) ,
  INDEX `fk_Vendedor_1` (`codigoSbPtoVenda` ASC) ,
  CONSTRAINT `fk_Vendedor_1`
    FOREIGN KEY (`codigoSbPtoVenda` )
    REFERENCES `sistemaconsorcio`.`SubPontoVenda` (`codigoSbPtoVenda` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`ModeloVeiculo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`ModeloVeiculo` (
  `codigoModelo` INT NOT NULL ,
  `descricao` VARCHAR(256) NULL ,
  `marca` VARCHAR(128) NULL ,
  PRIMARY KEY (`codigoModelo`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`Venda`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`Venda` (
  `codigoVenda` INT NOT NULL ,
  `dataCadastro` DATE NULL ,
  `nroContrato` VARCHAR(20) NULL ,
  `dtIniVigencia` DATE NULL ,
  `qtdParcelas` INT NULL COMMENT '	' ,
  `dtParcEntrada` DATE NULL ,
  `vlrParcEntrada` DOUBLE NULL ,
  `vlrBem` DOUBLE NULL ,
  `observacao` VARCHAR(256) NULL ,
  `grupoConsorcio` INT NULL ,
  `cotaConsorcio` INT NULL ,
  `codigoCliente` INT NULL ,
  `codigoModelo` INT NULL ,
  `codigoAdm` INT NULL ,
  `codigoVendedor` INT NULL ,
  PRIMARY KEY (`codigoVenda`) ,
  INDEX `fk_Venda_1` (`codigoCliente` ASC) ,
  INDEX `fk_Venda_2` (`codigoAdm` ASC) ,
  INDEX `fk_Venda_3` (`codigoVendedor` ASC) ,
  INDEX `fk_Venda_4` (`codigoModelo` ASC) ,
  CONSTRAINT `fk_Venda_1`
    FOREIGN KEY (`codigoCliente` )
    REFERENCES `sistemaconsorcio`.`Cliente` (`codigoCliente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venda_2`
    FOREIGN KEY (`codigoAdm` )
    REFERENCES `sistemaconsorcio`.`Administradora` (`codigoAdm` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venda_3`
    FOREIGN KEY (`codigoVendedor` )
    REFERENCES `sistemaconsorcio`.`Vendedor` (`codigoVendedor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venda_4`
    FOREIGN KEY (`codigoModelo` )
    REFERENCES `sistemaconsorcio`.`ModeloVeiculo` (`codigoModelo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`Comissao`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`Comissao` (
  `codigoComissao` INT NOT NULL ,
  `codigoVenda` INT NULL ,
  `nroParcela` INT NULL ,
  `dtEfetivaRecebimento` DATE NULL ,
  `vlrEfetivoRecebimento` DOUBLE NULL ,
  `dtPrevista` DATE NULL ,
  `vlrPrevisto` DOUBLE NULL ,
  `tipoComissao` INT(1) NULL ,
  `dtCancelamentoEstorno` DATE NULL ,
  PRIMARY KEY (`codigoComissao`) ,
  INDEX `fk_Comissao_1` (`codigoVenda` ASC) ,
  CONSTRAINT `fk_Comissao_1`
    FOREIGN KEY (`codigoVenda` )
    REFERENCES `sistemaconsorcio`.`Venda` (`codigoVenda` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`RegrasComissao`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`RegrasComissao` (
  `codigoRegra` INT NOT NULL ,
  `percentual` DOUBLE NULL ,
  `dtIniVigenciaComissao` DATE NULL ,
  `dtFimVigenciaComissao` DATE NULL ,
  `parcelamentoComissao` INT NULL ,
  `formaEstorno` VARCHAR(45) NULL ,
  `codigoAdm` INT NULL ,
  `codigoComissao` INT NULL ,
  PRIMARY KEY (`codigoRegra`) ,
  INDEX `fk_RegrasComissao_1` (`codigoComissao` ASC) ,
  INDEX `fk_RegrasComissao_2` (`codigoAdm` ASC) ,
  CONSTRAINT `fk_RegrasComissao_1`
    FOREIGN KEY (`codigoComissao` )
    REFERENCES `sistemaconsorcio`.`Comissao` (`codigoComissao` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RegrasComissao_2`
    FOREIGN KEY (`codigoAdm` )
    REFERENCES `sistemaconsorcio`.`Administradora` (`codigoAdm` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`TipoTaxa`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`TipoTaxa` (
  `codigoTpTaxa` INT NOT NULL ,
  `nome` VARCHAR(128) NULL ,
  PRIMARY KEY (`codigoTpTaxa`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemaconsorcio`.`Taxa`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sistemaconsorcio`.`Taxa` (
  `codigoTaxa` INT NOT NULL ,
  `percentualTaxa` DOUBLE NULL ,
  `codigoAdm` INT NULL ,
  `codigoTpTaxa` INT NULL ,
  PRIMARY KEY (`codigoTaxa`) ,
  INDEX `fk_Taxa_1` (`codigoTpTaxa` ASC) ,
  INDEX `fk_Taxa_2` (`codigoAdm` ASC) ,
  CONSTRAINT `fk_Taxa_1`
    FOREIGN KEY (`codigoTpTaxa` )
    REFERENCES `sistemaconsorcio`.`TipoTaxa` (`codigoTpTaxa` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Taxa_2`
    FOREIGN KEY (`codigoAdm` )
    REFERENCES `sistemaconsorcio`.`Administradora` (`codigoAdm` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
