-- -----------------------------------------------------
-- Schema alquiler_bicis
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `alquiler_bicis`;
CREATE SCHEMA IF NOT EXISTS `alquiler_bicis`;
USE `alquiler_bicis` ;

-- -----------------------------------------------------
-- Table `alquiler_bicis`.`bici`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bici`;
CREATE TABLE IF NOT EXISTS `bici` (
  `cod_bici` INT NOT NULL AUTO_INCREMENT,
  `libre` VARCHAR(10) DEFAULT "Libre",
  PRIMARY KEY (`cod_bici`))
  AUTO_INCREMENT = 100;

-- -----------------------------------------------------
-- Table `alquiler_bicis`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `cod_usuario` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `bici_cod_bici` INT DEFAULT 100 NOT NULL,
  PRIMARY KEY (`cod_usuario`),
  CONSTRAINT `fk_usuario_bici`
    FOREIGN KEY (`bici_cod_bici`)
    REFERENCES `bici` (`cod_bici`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
CREATE INDEX idx_cod_usu ON usuario (`cod_usuario`);


INSERT INTO bici (cod_bici) VALUES
(100);

INSERT INTO usuario (cod_usuario, nombre) VALUES
(1, 'Juan'),
(2, 'Maria'),
(3, 'Pedro');
