CREATE DATABASE LIBRARY;
USE LIBRARY;

CREATE TABLE `library`.`author` (
  `ID_AUTHOR` INT(11) NOT NULL AUTO_INCREMENT,
  `SURNAME` VARCHAR(45) NOT NULL,
  `NAME` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_AUTHOR`));

  CREATE TABLE `library`.`book` (
  `ID_BOOK` INT(11) NOT NULL,
  `ID_LANGUAGE` INT(11) NOT NULL,
  `TITLE` VARCHAR(200) NOT NULL,
  `ISBN` VARCHAR(17) NULL DEFAULT NULL,
  `QUANTITY` INT(11) NOT NULL,
  PRIMARY KEY (`ID_BOOK`, `ID_LANGUAGE`));

CREATE TABLE `library`.`book2author` (
  `ID_BOOK` INT(11) NOT NULL,
  `ID_AUTHOR` INT(11) NOT NULL,
  PRIMARY KEY (`ID_BOOK`, `ID_AUTHOR`),
  INDEX `idAuthor_idx` (`ID_AUTHOR` ASC) VISIBLE,
  CONSTRAINT `IDofBook`
    FOREIGN KEY (`ID_BOOK`)
    REFERENCES `library`.`book` (`ID_BOOK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `idAuthor`
    FOREIGN KEY (`ID_AUTHOR`)
    REFERENCES `library`.`author` (`ID_AUTHOR`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE `library`.`book2genre` (
  `ID_BOOK` INT(11) NOT NULL,
  `ID_LANGUAGE` INT(11) NOT NULL,
  `ID_GENRE` INT(11) NOT NULL,
  PRIMARY KEY (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`),
  INDEX `idGenre_idx` (`ID_GENRE` ASC) VISIBLE,
  INDEX `languages_idx` (`ID_LANGUAGE` ASC) VISIBLE,
  CONSTRAINT `idBook`
    FOREIGN KEY (`ID_BOOK`)
    REFERENCES `library`.`book` (`ID_BOOK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `idGenre`
    FOREIGN KEY (`ID_GENRE`)
    REFERENCES `library`.`genre` (`ID_GENRE`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `languages`
    FOREIGN KEY (`ID_LANGUAGE`)
    REFERENCES `library`.`language` (`ID_LANGUAGE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `library`.`booking` (
  `ID_ORDER` INT(11) NOT NULL AUTO_INCREMENT,
  `ID_BOOK` INT(11) NOT NULL,
  `ORDER_DATE` DATE NOT NULL,
  `ACCEPTED_DATE` DATE NULL DEFAULT NULL,
  `RETURN_DATE` DATE NULL DEFAULT NULL,
  `ACTUALLY_RETURNED` DATE NULL DEFAULT NULL,
  `ID_STATE` INT(11) NOT NULL,
  PRIMARY KEY (`ID_ORDER`),
  INDEX `con_idx` (`ID_STATE` ASC) VISIBLE,
  INDEX `idbooks_idx` (`ID_BOOK` ASC) VISIBLE,
  CONSTRAINT `con`
    FOREIGN KEY (`ID_STATE`)
    REFERENCES `library`.`state` (`ID_STATE`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `idbooks`
    FOREIGN KEY (`ID_BOOK`)
    REFERENCES `library`.`book` (`ID_BOOK`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);
DROP TRIGGER IF EXISTS `library`.`booking_AFTER_DELETE`;

DELIMITER $$
USE `library`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `booking_AFTER_DELETE` AFTER DELETE ON `booking` FOR EACH ROW BEGIN
DELETE FROM order2user WHERE (ID_ORDER = old.id_order);
END$$
DELIMITER ;

CREATE TABLE `library`.`genre` (
  `ID_GENRE` INT(11) NOT NULL,
  `ID_LANGUAGE` INT(11) NOT NULL,
  `GENRE_NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_GENRE`, `ID_LANGUAGE`));

CREATE TABLE `library`.`language` (
  `ID_LANGUAGE` INT(11) NOT NULL,
  `LANGUAGE` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_LANGUAGE`));

CREATE TABLE `library`.`order2user` (
  `ID_ORDER` INT(11) NOT NULL,
  `ID_USER` INT(11) NOT NULL,
  PRIMARY KEY (`ID_ORDER`, `ID_USER`),
  INDEX `idUser_idx` (`ID_USER` ASC) VISIBLE,
  CONSTRAINT `idOrder`
    FOREIGN KEY (`ID_ORDER`)
    REFERENCES `library`.`booking` (`ID_ORDER`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `idUser`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `library`.`user` (`ID_USER`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `library`.`role` (
  `ID_ROLE` INT(11) NOT NULL,
  `ROLE_NAME` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`ID_ROLE`));

  CREATE TABLE `library`.`state` (
  `ID_STATE` INT(11) NOT NULL,
  `ID_LANGUAGE` INT(11) NOT NULL,
  `NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_STATE`, `ID_LANGUAGE`),
  INDEX `ID_LANGUAGE_idx` (`ID_LANGUAGE` ASC) VISIBLE,
  CONSTRAINT `ID_LANGUAGE`
    FOREIGN KEY (`ID_LANGUAGE`)
    REFERENCES `library`.`language` (`ID_LANGUAGE`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT);

CREATE TABLE `library`.`user1` (
  `ID_USER` INT(11) NOT NULL AUTO_INCREMENT,
  `PASSWORD` VARCHAR(32) NOT NULL,
  `SURNAME` VARCHAR(25) NOT NULL,
  `NAME` VARCHAR(25) NOT NULL,
  `MAIL` VARCHAR(25) NOT NULL,
  `TELEPHONE` BIGINT(11) NOT NULL,
  `BIRTH_DAY` DATE NOT NULL,
  `BLOCK` VARCHAR(1) NOT NULL,
  `ROLE` INT(11) NOT NULL,
  PRIMARY KEY (`ID_USER`),
  INDEX `role_idx` (`ROLE` ASC) VISIBLE,
  CONSTRAINT `role`
    FOREIGN KEY (`ROLE`)
    REFERENCES `library`.`role` (`ID_ROLE`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

INSERT INTO `library`.`author` (`ID_AUTHOR`, `SURNAME`, `NAME`) VALUES ('1', 'Блинов', 'Игорь');
INSERT INTO `library`.`author` (`ID_AUTHOR`, `SURNAME`, `NAME`) VALUES ('2', 'Романчик', 'Валерий');
INSERT INTO `library`.`author` (`ID_AUTHOR`, `SURNAME`, `NAME`) VALUES ('3', 'Дорофеев', 'Максим');
INSERT INTO `library`.`author` (`ID_AUTHOR`, `SURNAME`, `NAME`) VALUES ('4', 'Фримен', 'Эрик');
INSERT INTO `library`.`author` (`ID_AUTHOR`, `SURNAME`, `NAME`) VALUES ('5', 'Фримен', 'Элизабет');
INSERT INTO `library`.`author` (`ID_AUTHOR`, `SURNAME`, `NAME`) VALUES ('6', 'Хокинг', 'Стивен');
INSERT INTO `library`.`author` (`ID_AUTHOR`, `SURNAME`, `NAME`) VALUES ('7', 'Желязны', 'Роджер');
INSERT INTO `library`.`author` (`ID_AUTHOR`, `SURNAME`, `NAME`) VALUES ('9', 'Белов', 'Дмитрий');

INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('1', '1', 'JAVA. Промышленное программирование', '978-985-6699-63-7', '4');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('1', '2', 'JAVA.industrial programming', '978-985-6699-63-7', '4');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('2', '1', 'Джедайские техники', '978-5-00100-685-5', '7');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('2', '2', 'Jedi technology', '978-5-00100-685-5', '7');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('3', '1', 'Паттерны проектирования', '978-5-459-00435-9', '8');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('3', '2', 'design patterns', '978-5-459-00435-9', '8');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('4', '1', 'Краткая история времени', '978-5-17-102284-6', '4');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('4', '2', 'A breifer history of time', '978-5-17-102284-6', '4');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('6', '1', 'Девять принцев Амбера', '5-699-07498-8', '17');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('6', '2', 'Nine princes of Amber', '5-699-07498-8', '17');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('7', '1', 'Сталкер', '6516126', '9');
INSERT INTO `library`.`book` (`ID_BOOK`, `ID_LANGUAGE`, `TITLE`, `ISBN`, `QUANTITY`) VALUES ('7', '2', 'stalker', '6516126', '7');

INSERT INTO `library`.`book2author` (`ID_BOOK`, `ID_AUTHOR`) VALUES ('1', '1');
INSERT INTO `library`.`book2author` (`ID_BOOK`, `ID_AUTHOR`) VALUES ('1', '2');
INSERT INTO `library`.`book2author` (`ID_BOOK`, `ID_AUTHOR`) VALUES ('2', '3');
INSERT INTO `library`.`book2author` (`ID_BOOK`, `ID_AUTHOR`) VALUES ('3', '4');
INSERT INTO `library`.`book2author` (`ID_BOOK`, `ID_AUTHOR`) VALUES ('3', '5');
INSERT INTO `library`.`book2author` (`ID_BOOK`, `ID_AUTHOR`) VALUES ('4', '6');
INSERT INTO `library`.`book2author` (`ID_BOOK`, `ID_AUTHOR`) VALUES ('6', '7');
INSERT INTO `library`.`book2author` (`ID_BOOK`, `ID_AUTHOR`) VALUES ('7', '9');


INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('1', '1', '3');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('2', '1', '2');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('3', '1', '3');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('4', '1', '4');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('6', '1', '1');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('6', '1', '6');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('7', '1', '3');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('7', '1', '6');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('1', '2', '3');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('2', '2', '2');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('3', '2', '3');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('4', '2', '4');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('6', '2', '1');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('6', '2', '6');
INSERT INTO `library`.`book2genre` (`ID_BOOK`, `ID_LANGUAGE`, `ID_GENRE`) VALUES ('7', '2', '6');

INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ACTUALLY_RETURNED`, `ID_STATE`) VALUES ('1', '1', '2018-09-01', '2018-09-01', '2018-09-16', '2018-09-15', '3');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ACTUALLY_RETURNED`, `ID_STATE`) VALUES ('2', '3', '2018-09-01', '2018-09-01', '2018-09-16', '2018-09-12', '3');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ACTUALLY_RETURNED`, `ID_STATE`) VALUES ('3', '2', '2018-10-18', '2018-10-18', '2018-10-30', '2018-10-25', '3');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ACTUALLY_RETURNED`, `ID_STATE`) VALUES ('5', '4', '2018-10-22', '2018-10-22', '2018-11-05', '2018-11-03', '3');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ACTUALLY_RETURNED`, `ID_STATE`) VALUES ('6', '3', '2018-11-03', '2018-11-03', '2018-11-15', '2018-11-16', '3');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ID_STATE`) VALUES ('51', '1', '2019-03-10', '2019-03-10', '2019-03-19', '2');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ID_STATE`) VALUES ('52', '2', '2019-03-15', '2019-03-15', '2019-03-20', '5');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `ID_STATE`) VALUES ('53', '6', '2019-03-17', '2019-03-17', '1');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ID_STATE`) VALUES ('54', '2', '2019-03-26', '2019-03-26', '2019-03-20', '4');
INSERT INTO `library`.`booking` (`ID_ORDER`, `ID_BOOK`, `ORDER_DATE`, `ACCEPTED_DATE`, `RETURN_DATE`, `ID_STATE`) VALUES ('55', '7', '2019-03-26', '2019-03-26', '2019-03-19', '1');

INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('1', '1', 'Фэнтази');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('1', '2', 'Fantasy');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('2', '1', 'Психология');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('2', '2', 'Psychology');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('3', '1', 'Учебник');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('3', '2', 'Textbook');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('4', '1', 'Научно-популярный');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('4', '2', 'popular-science');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('5', '1', 'Журналистика');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('5', '2', 'Journalism');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('6', '1', 'Приключения');
INSERT INTO `library`.`genre` (`ID_GENRE`, `ID_LANGUAGE`, `GENRE_NAME`) VALUES ('6', '2', 'Adventure');

INSERT INTO `library`.`language` (`ID_LANGUAGE`, `LANGUAGE`) VALUES ('1', 'RU');
INSERT INTO `library`.`language` (`ID_LANGUAGE`, `LANGUAGE`) VALUES ('2', 'ENG');

INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('1', '1');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('1', '3');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('1', '7');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('2', '3');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('2', '7');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('3', '2');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('3', '7');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('3', '9');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('5', '5');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('5', '9');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('6', '2');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('6', '7');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('51', '1');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('51', '7');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('52', '1');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('52', '7');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('53', '1');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('53', '7');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('54', '1');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('54', '7');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('55', '1');
INSERT INTO `library`.`order2user` (`ID_ORDER`, `ID_USER`) VALUES ('55', '7');

INSERT INTO `library`.`role` (`ID_ROLE`, `ROLE_NAME`) VALUES ('1', 'guest');
INSERT INTO `library`.`role` (`ID_ROLE`, `ROLE_NAME`) VALUES ('2', 'reader');
INSERT INTO `library`.`role` (`ID_ROLE`, `ROLE_NAME`) VALUES ('3', 'librarian');

INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('1', '1', 'новый');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('1', '2', 'new');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('2', '1', 'в процессе');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('2', '2', 'in progress');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('3', '1', 'завершен');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('3', '2', 'finished');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('4', '1', 'отказ');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('4', '2', 'rejection');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('5', '1', 'выдан');
INSERT INTO `library`.`state` (`ID_STATE`, `ID_LANGUAGE`, `NAME`) VALUES ('5', '2', 'issued');

INSERT INTO `library`.`user` (`ID_USER`, `PASSWORD`, `SURNAME`, `NAME`, `MAIL`, `TELEPHONE`, `BIRTH_DAY`, `BLOCK`, `ROLE`) VALUES ('2', '0651c2ec445d77d9deda37a9332812e3', 'Бауэр', 'Екатерина', 'kat@gmail.com', '87017456321', '1988-01-18', 'n', '2');
INSERT INTO `library`.`user` (`ID_USER`, `PASSWORD`, `SURNAME`, `NAME`, `MAIL`, `TELEPHONE`, `BIRTH_DAY`, `BLOCK`, `ROLE`) VALUES ('3', 'f67c181d767b6b9990ce445f9cdc86dd', 'Швец', 'Павел', 'fire@list.ru', '87029379992', '2000-07-01', 'n', '2');
INSERT INTO `library`.`user` (`ID_USER`, `PASSWORD`, `SURNAME`, `NAME`, `MAIL`, `TELEPHONE`, `BIRTH_DAY`, `BLOCK`, `ROLE`) VALUES ('4', '198d544e87046360d6c276d863080be6', 'Петров', 'Алексей', 'lehashow@mail.ru', '87077564214', '1998-09-25', 'n', '2');
INSERT INTO `library`.`user` (`ID_USER`, `PASSWORD`, `SURNAME`, `NAME`, `MAIL`, `TELEPHONE`, `BIRTH_DAY`, `BLOCK`, `ROLE`) VALUES ('5', 'fa91161320ac0ac1cca39cf4efb998e6', 'Глазко', 'Ольга', 'beatiful@gmail.com', '87775263978', '1985-11-06', 'n', '2');
INSERT INTO `library`.`user` (`ID_USER`, `PASSWORD`, `SURNAME`, `NAME`, `MAIL`, `TELEPHONE`, `BIRTH_DAY`, `BLOCK`, `ROLE`) VALUES ('6', '0306325364e667748d820df5196d0a48', 'Мироненко', 'Тимур', 'timapeace@mail.ru', '87029421365', '1994-08-30', 'y', '2');
INSERT INTO `library`.`user` (`ID_USER`, `PASSWORD`, `SURNAME`, `NAME`, `MAIL`, `TELEPHONE`, `BIRTH_DAY`, `BLOCK`, `ROLE`) VALUES ('7', 'f6d7015fb9d9bed80cff989ba19345d1', 'Шелест', 'Ирина', 'angel@gmail.com', '87755681213', '1978-08-16', 'n', '3');
INSERT INTO `library`.`user` (`ID_USER`, `PASSWORD`, `SURNAME`, `NAME`, `MAIL`, `TELEPHONE`, `BIRTH_DAY`, `BLOCK`, `ROLE`) VALUES ('8', 'f39a6336cad619c0b734da7d6f89f505', 'Цой', 'Вадим', 'legend@mail.ru', '87022468615', '1975-04-24', 'n', '3');
INSERT INTO `library`.`user` (`ID_USER`, `PASSWORD`, `SURNAME`, `NAME`, `MAIL`, `TELEPHONE`, `BIRTH_DAY`, `BLOCK`, `ROLE`) VALUES ('9', 'fd3d2f9e5efdf49c4371afabfa59f4da', 'Соколова', 'Наталья', 'bird87@mail.ru', '87022468615', '1975-04-24', 'n', '3');

