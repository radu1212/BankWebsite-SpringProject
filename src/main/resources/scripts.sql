SQL SCRIPTS :

a)	Creating the tables:

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema a2_sd
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema a2_sd
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `a2_sd` DEFAULT CHARACTER SET utf8mb4 ;
USE `a2_sd` ;

-- -----------------------------------------------------
-- Table `a2_sd`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `a2_sd`.`user` (
  `id` VARCHAR(255) NOT NULL,
  `active` INT(11) NULL DEFAULT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  `date_of_birth` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `firstname` VARCHAR(255) NULL DEFAULT NULL,
  `lastname` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `registration_date` VARCHAR(255) NULL DEFAULT NULL,
  `role` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_ob8kqyqqgmefl0aco34akdtpe` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `a2_sd`.`bank_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `a2_sd`.`bank_account` (
  `id` INT(11) NOT NULL,
  `creation_date` VARCHAR(255) NOT NULL,
  `currency` VARCHAR(255) NOT NULL,
  `iban` VARCHAR(255) NOT NULL,
  `last_modified` VARCHAR(255) NOT NULL,
  `sold` DOUBLE NULL DEFAULT NULL,
  `type` VARCHAR(255) NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_699j998jxie2f134gfnu86q96` (`iban` ASC) VISIBLE,
  INDEX `FK92iik4jwhk7q385jubl2bc2mm` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK92iik4jwhk7q385jubl2bc2mm`
    FOREIGN KEY (`user_id`)
    REFERENCES `a2_sd`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `a2_sd`.`bill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `a2_sd`.`bill` (
  `id` VARCHAR(255) NOT NULL,
  `accepted_currency` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `recipient` VARCHAR(255) NOT NULL,
  `value` DOUBLE NOT NULL,
  `user_id` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKqhq5aolak9ku5x5mx11cpjad9` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKqhq5aolak9ku5x5mx11cpjad9`
    FOREIGN KEY (`user_id`)
    REFERENCES `a2_sd`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `a2_sd`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `a2_sd`.`company` (
  `id` VARCHAR(255) NOT NULL,
  `accepted_currency` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `a2_sd`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `a2_sd`.`hibernate_sequence` (
  `next_val` BIGINT(20) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `a2_sd`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `a2_sd`.`role` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `a2_sd`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `a2_sd`.`transaction` (
  `id` VARCHAR(255) NOT NULL,
  `date` VARCHAR(255) NOT NULL,
  `recipient` VARCHAR(255) NULL DEFAULT NULL,
  `type` VARCHAR(255) NOT NULL,
  `value` DOUBLE NOT NULL,
  `bank_account_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKec44dj1u86xnsku7ld84pirje` (`bank_account_id` ASC) VISIBLE,
  CONSTRAINT `FKec44dj1u86xnsku7ld84pirje`
    FOREIGN KEY (`bank_account_id`)
    REFERENCES `a2_sd`.`bank_account` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `a2_sd`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `a2_sd`.`users_roles` (
  `user_id` VARCHAR(255) NOT NULL,
  `role_id` BIGINT(20) NOT NULL,
  INDEX `FKt4v0rrweyk393bdgt107vdx0x` (`role_id` ASC) VISIBLE,
  INDEX `FKgd3iendaoyh04b95ykqise6qh` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh`
    FOREIGN KEY (`user_id`)
    REFERENCES `a2_sd`.`user` (`id`),
  CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x`
    FOREIGN KEY (`role_id`)
    REFERENCES `a2_sd`.`role` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;







b)	Inserting into a table:

Hibernate: 
    update
        hibernate_sequence 
    set
        next_val= ? 
    where
        next_val=?
Hibernate: 
    /* insert ro.sd.a2.entity.BankAccount
        */ insert 
        into
            bank_account
            (creation_date, currency, iban, last_modified, sold, type, user_id, id) 
        values
            (?, ?, ?, ?, ?, ?, ?, ?)	
-	We have the update as well generated for the auto-incremented id 

c)	Deleting from a table:

Hibernate: 
    /* delete ro.sd.a2.entity.Bill */ delete 
        from
            bill 
        where
            id=?
d)	Updating a table entry:

Hibernate: 
    /* update
        ro.sd.a2.entity.BankAccount */ update
            bank_account 
        set
            creation_date=?,
            currency=?,
            iban=?,
            last_modified=?,
            sold=?,
            type=?,
            user_id=? 
        where
            id=?
