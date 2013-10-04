SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `LIVECON` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `LIVECON` ;

-- -----------------------------------------------------
-- Table `LIVECON`.`server`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`server` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`server` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `unique_name` VARCHAR(500) NOT NULL,
  `name` VARCHAR(500) NOT NULL,
  `num_players` INT(1) NOT NULL,
  `dir` VARCHAR(500) NOT NULL,
  `archived` INT(1) NOT NULL DEFAULT 0,
  `diplo` INT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `unique_name_UNIQUE` (`unique_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`account_level`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`account_level` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`account_level` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(500) NOT NULL,
  `value` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`account` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account_level_id` BIGINT NOT NULL,
  `username` VARCHAR(500) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_account_account_level_idx` (`account_level_id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_account_account_level`
    FOREIGN KEY (`account_level_id`)
    REFERENCES `LIVECON`.`account_level` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`game`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`game` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`game` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `server_id` BIGINT NOT NULL,
  `date_start` DATETIME NULL,
  `date_end` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_game_server1_idx` (`server_id` ASC),
  CONSTRAINT `fk_game_server1`
    FOREIGN KEY (`server_id`)
    REFERENCES `LIVECON`.`server` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`player`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`player` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`player` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `keyid` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `keyid_UNIQUE` (`keyid` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`territory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`territory` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`territory` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(500) NOT NULL,
  `show_name` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`game_has_player`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`game_has_player` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`game_has_player` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `territory_id` BIGINT NOT NULL,
  `game_id` BIGINT NOT NULL,
  `player_id` BIGINT NOT NULL,
  `ip` VARCHAR(500) NOT NULL,
  `score` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_game_has_player_player1_idx` (`player_id` ASC),
  INDEX `fk_game_has_player_game1_idx` (`game_id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `id2_unique` (`player_id` ASC, `game_id` ASC),
  INDEX `fk_game_has_player_territory1_idx` (`territory_id` ASC),
  CONSTRAINT `fk_game_has_player_game1`
    FOREIGN KEY (`game_id`)
    REFERENCES `LIVECON`.`game` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_game_has_player_player1`
    FOREIGN KEY (`player_id`)
    REFERENCES `LIVECON`.`player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_game_has_player_territory1`
    FOREIGN KEY (`territory_id`)
    REFERENCES `LIVECON`.`territory` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`team` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`team` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(500) NOT NULL,
  `show_color` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `color_UNIQUE` (`color` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`banlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`banlist` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`banlist` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `date_start` DATETIME NOT NULL,
  `date_end` DATETIME NULL,
  `keyid` VARCHAR(500) NULL,
  `ip_or_range` VARCHAR(500) NULL,
  `reason` VARCHAR(4000) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`dcrec`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`dcrec` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`dcrec` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `game_id` BIGINT NOT NULL,
  `dir` VARCHAR(500) NOT NULL,
  `full_path` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_drec_game1_idx` (`game_id` ASC),
  UNIQUE INDEX `game_id_UNIQUE` (`game_id` ASC),
  CONSTRAINT `fk_drec_game1`
    FOREIGN KEY (`game_id`)
    REFERENCES `LIVECON`.`game` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`ingame_name`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`ingame_name` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`ingame_name` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `game_has_player_id` BIGINT NOT NULL,
  `name` VARCHAR(1000) NOT NULL,
  `orderdir` INT NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_ingame_names_game_has_player1_idx` (`game_has_player_id` ASC),
  CONSTRAINT `fk_ingame_names_game_has_player1`
    FOREIGN KEY (`game_has_player_id`)
    REFERENCES `LIVECON`.`game_has_player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`autobanlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`autobanlist` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`autobanlist` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `sql_pattern` VARCHAR(4000) NULL,
  `regex` VARCHAR(4000) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`autoban_ignore_list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`autoban_ignore_list` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`autoban_ignore_list` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `keyid` VARCHAR(500) NULL,
  `name` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`admin` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`admin` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(500) NULL,
  `keyid` VARCHAR(500) NULL,
  `level` INT(1) NOT NULL,
  `password` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`protected_player`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`protected_player` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`protected_player` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(500) NOT NULL,
  `keyid` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LIVECON`.`game_has_player_has_team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LIVECON`.`game_has_player_has_team` ;

CREATE TABLE IF NOT EXISTS `LIVECON`.`game_has_player_has_team` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `game_has_player_id` BIGINT NOT NULL,
  `team_id` BIGINT NOT NULL,
  `orderdir` INT NOT NULL,
  INDEX `fk_game_has_player_has_team_team1_idx` (`team_id` ASC),
  INDEX `fk_game_has_player_has_team_game_has_player1_idx` (`game_has_player_id` ASC),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_game_has_player_has_team_game_has_player1`
    FOREIGN KEY (`game_has_player_id`)
    REFERENCES `LIVECON`.`game_has_player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_game_has_player_has_team_team1`
    FOREIGN KEY (`team_id`)
    REFERENCES `LIVECON`.`team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `LIVECON`.`account_level`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIVECON`;
INSERT INTO `LIVECON`.`account_level` (`id`, `code`, `value`) VALUES (NULL, 'ROOT', 'Root access');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIVECON`.`account`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIVECON`;
INSERT INTO `LIVECON`.`account` (`id`, `account_level_id`, `username`, `password`) VALUES (NULL, 1, 'root', 'blank');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIVECON`.`territory`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIVECON`;
INSERT INTO `LIVECON`.`territory` (`id`, `name`, `show_name`) VALUES (1, 'NA', 'North America');
INSERT INTO `LIVECON`.`territory` (`id`, `name`, `show_name`) VALUES (2, 'SA', 'South America');
INSERT INTO `LIVECON`.`territory` (`id`, `name`, `show_name`) VALUES (3, 'EU', 'Europe');
INSERT INTO `LIVECON`.`territory` (`id`, `name`, `show_name`) VALUES (4, 'RU', 'Russia');
INSERT INTO `LIVECON`.`territory` (`id`, `name`, `show_name`) VALUES (5, 'AS', 'Asia');
INSERT INTO `LIVECON`.`territory` (`id`, `name`, `show_name`) VALUES (6, 'AF', 'Africa');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIVECON`.`team`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIVECON`;
INSERT INTO `LIVECON`.`team` (`id`, `color`, `show_color`) VALUES (1, 'RED', 'Red');
INSERT INTO `LIVECON`.`team` (`id`, `color`, `show_color`) VALUES (2, 'GREEN', 'Green');
INSERT INTO `LIVECON`.`team` (`id`, `color`, `show_color`) VALUES (3, 'BLUE', 'Blue');
INSERT INTO `LIVECON`.`team` (`id`, `color`, `show_color`) VALUES (4, 'YELLOW', 'Yellow');
INSERT INTO `LIVECON`.`team` (`id`, `color`, `show_color`) VALUES (5, 'ORANGE', 'Orange');
INSERT INTO `LIVECON`.`team` (`id`, `color`, `show_color`) VALUES (6, 'TURQ', 'Turq');
INSERT INTO `LIVECON`.`team` (`id`, `color`, `show_color`) VALUES (0, 'WHITE', 'White');

COMMIT;

