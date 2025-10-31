-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Hospital
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Hospital
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Hospital` DEFAULT CHARACTER SET utf8 ;
USE `Hospital` ;

-- -----------------------------------------------------
-- Table `Hospital`.`Doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hospital`.`Doctor` (
  `ID` VARCHAR(20) NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `especialidad` VARCHAR(45) NULL,
  `clave` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`, `Nombre`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hospital`.`Paciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hospital`.`Paciente` (
  `ID` VARCHAR(45) NOT NULL,
  `Nombre` VARCHAR(45) NULL,
  `Telefono` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)

ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hospital`.`Medicamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hospital`.`Medicamento` (
  `ID` VARCHAR(25) NOT NULL,
  `Nombre` VARCHAR(45) NULL,
  `Presentacion` DOUBLE NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hospital`.`Farmaceuta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hospital`.`Farmaceuta` (
  `ID` VARCHAR(25) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `clave` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hospital`.`Receta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hospital`.`Receta` (
  `numero` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NULL,
  `duracion` INT NULL,
  `indicaciones` VARCHAR(45) NULL,
  `medicamentos` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`numero`),
  INDEX `fk_Receta_Medicamento_idx` (`medicamentos` ASC) VISIBLE,
  CONSTRAINT `fk_Receta_Medicamento`
    FOREIGN KEY (`medicamentos`)
    REFERENCES `Hospital`.`Medicamento` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hospital`.`Prescripcion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hospital`.`Prescripcion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `estado` VARCHAR(45) NULL,
  `fechaConfeccion` VARCHAR(45) NULL,
  `fechaRetiro` VARCHAR(45) NULL,
  `receta` INT NOT NULL,
  `paciente` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Prescripcion_Receta1_idx` (`receta` ASC) VISIBLE,
  INDEX `fk_Prescripcion_Paciente1_idx` (`paciente` ASC) VISIBLE,
  CONSTRAINT `fk_Prescripcion_Receta1`
    FOREIGN KEY (`receta`)
    REFERENCES `Hospital`.`Receta` (`numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescripcion_Paciente1`
    FOREIGN KEY (`paciente`)
    REFERENCES `Hospital`.`Paciente` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hospital`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hospital`.`Usuario`
(
    `ID`       VARCHAR(25) NOT NULL,
    `clave`    VARCHAR(45) NULL,
    `userType` VARCHAR(45) NULL,
    `message`  VARCHAR(45) NULL,
    `logged`   TINYINT     NULL,
    PRIMARY KEY (`ID`)
)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
