-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema alquiler_bicis
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema alquiler_bicis
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `alquiler_bicis` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `alquiler_bicis` ;

-- -----------------------------------------------------
-- Table `alquiler_bicis`.`bici`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alquiler_bicis`.`bici` (
  `cod_bici` INT NOT NULL,
  `libre` TINYINT NOT NULL,
  PRIMARY KEY (`cod_bici`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `alquiler_bicis`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alquiler_bicis`.`usuario` (
  `cod_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `bici_cod_bici` INT NOT NULL,
  PRIMARY KEY (`cod_usuario`),
  INDEX `fk_usuario_bici_idx` (`bici_cod_bici` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_bici`
    FOREIGN KEY (`bici_cod_bici`)
    REFERENCES `alquiler_bicis`.`bici` (`cod_bici`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
