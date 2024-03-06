-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema schoolcompetition
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema schoolcompetition
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `schoolcompetition` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `schoolcompetition` ;

-- -----------------------------------------------------
-- Table `schoolcompetition`.`round`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`round` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `competition_id` INT NULL DEFAULT NULL,
  `map` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`bracket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`bracket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `round_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKlv22et9dw1fp5rfyf1f83h2yv` (`round_id` ASC) VISIBLE,
  CONSTRAINT `FKlv22et9dw1fp5rfyf1f83h2yv`
    FOREIGN KEY (`round_id`)
    REFERENCES `schoolcompetition`.`round` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`school`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`school` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`coach`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`coach` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `dob` DATE NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `sex` CHAR(1) NULL DEFAULT NULL,
  `school_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKerb7n9wr98dt5b67rhoh1g0sk` (`school_id` ASC) VISIBLE,
  CONSTRAINT `FKerb7n9wr98dt5b67rhoh1g0sk`
    FOREIGN KEY (`school_id`)
    REFERENCES `schoolcompetition`.`school` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`school_year`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`school_year` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `year` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`competition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`competition` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `hold_place` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `school_year_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK5nyhptam2buhpe9kv209t26ox` (`school_year_id` ASC) VISIBLE,
  CONSTRAINT `FK5nyhptam2buhpe9kv209t26ox`
    FOREIGN KEY (`school_year_id`)
    REFERENCES `schoolcompetition`.`school_year` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`team` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `coach_id` BIGINT NULL DEFAULT NULL,
  `competition_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK3kq9y3up07so7lqkt4cpe6xb0` (`coach_id` ASC) VISIBLE,
  INDEX `FKqhhapgh63c9yjo4tc0uf6ynt1` (`competition_id` ASC) VISIBLE,
  CONSTRAINT `FK3kq9y3up07so7lqkt4cpe6xb0`
    FOREIGN KEY (`coach_id`)
    REFERENCES `schoolcompetition`.`coach` (`id`),
  CONSTRAINT `FKqhhapgh63c9yjo4tc0uf6ynt1`
    FOREIGN KEY (`competition_id`)
    REFERENCES `schoolcompetition`.`competition` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`car`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`car` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `type` VARCHAR(255) NULL DEFAULT NULL,
  `team_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKig9be06ddnjce3c5wnaw624cc` (`team_id` ASC) VISIBLE,
  CONSTRAINT `FKig9be06ddnjce3c5wnaw624cc`
    FOREIGN KEY (`team_id`)
    REFERENCES `schoolcompetition`.`team` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`student` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `dob` DATETIME(6) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `sex` CHAR(1) NULL DEFAULT NULL,
  `school_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK1vm0oqhk9viil6eocn49rj1l9` (`school_id` ASC) VISIBLE,
  CONSTRAINT `FK1vm0oqhk9viil6eocn49rj1l9`
    FOREIGN KEY (`school_id`)
    REFERENCES `schoolcompetition`.`school` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`contestant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`contestant` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `coach_id` BIGINT NULL DEFAULT NULL,
  `student_id` BIGINT NULL DEFAULT NULL,
  `team_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_2r4tmubo6wg75se78692859ds` (`student_id` ASC) VISIBLE,
  INDEX `FKcy0a27uw9fv5iwjix7d54lx2e` (`coach_id` ASC) VISIBLE,
  INDEX `FKatk3f3308i2kcuddpq3xnykkj` (`team_id` ASC) VISIBLE,
  CONSTRAINT `FK7yc8g4b13b7k2iia0rr1sa16y`
    FOREIGN KEY (`student_id`)
    REFERENCES `schoolcompetition`.`student` (`id`),
  CONSTRAINT `FKatk3f3308i2kcuddpq3xnykkj`
    FOREIGN KEY (`team_id`)
    REFERENCES `schoolcompetition`.`team` (`id`),
  CONSTRAINT `FKcy0a27uw9fv5iwjix7d54lx2e`
    FOREIGN KEY (`coach_id`)
    REFERENCES `schoolcompetition`.`coach` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`matchh`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`matchh` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `lap` INT NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `place` VARCHAR(255) NULL DEFAULT NULL,
  `start_time` DATETIME(6) NULL DEFAULT NULL,
  `bracket_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK5kwqp7dw56egmwivx1irtd7gc` (`bracket_id` ASC) VISIBLE,
  CONSTRAINT `FK5kwqp7dw56egmwivx1irtd7gc`
    FOREIGN KEY (`bracket_id`)
    REFERENCES `schoolcompetition`.`bracket` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schoolcompetition`.`result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolcompetition`.`result` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `finish_time` VARCHAR(255) NULL DEFAULT NULL,
  `score` INT NULL DEFAULT NULL,
  `car_id` INT NULL DEFAULT NULL,
  `contestant_id` BIGINT NULL DEFAULT NULL,
  `match_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_ouq7svbikqk11stethmj4kexy` (`car_id` ASC) VISIBLE,
  INDEX `FK2mjv11qv7ll4uu1c8tbiiskvv` (`contestant_id` ASC) VISIBLE,
  INDEX `FKh84qavstbekrwngqe7euxkoq5` (`match_id` ASC) VISIBLE,
  CONSTRAINT `FK2dgvg77x2dhycis18ibjfek6r`
    FOREIGN KEY (`car_id`)
    REFERENCES `schoolcompetition`.`car` (`id`),
  CONSTRAINT `FK2mjv11qv7ll4uu1c8tbiiskvv`
    FOREIGN KEY (`contestant_id`)
    REFERENCES `schoolcompetition`.`contestant` (`id`),
  CONSTRAINT `FKh84qavstbekrwngqe7euxkoq5`
    FOREIGN KEY (`match_id`)
    REFERENCES `schoolcompetition`.`matchh` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
