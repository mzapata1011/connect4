-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.24-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para proyect
CREATE DATABASE IF NOT EXISTS `proyect` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `proyect`;

-- Volcando estructura para tabla proyect.board
CREATE TABLE IF NOT EXISTS `board` (
  `game` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `player` int(11) DEFAULT NULL,
  PRIMARY KEY (`game`,`number`),
  KEY `player` (`player`),
  CONSTRAINT `board_ibfk_1` FOREIGN KEY (`game`) REFERENCES `games` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `board_ibfk_2` FOREIGN KEY (`player`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla proyect.board: ~501 rows (aproximadamente)
INSERT INTO `board` (`game`, `number`, `player`) VALUES
	(1, 2, 10),
	(1, 4, 10),
	(1, 6, 10),
	(1, 7, 10),
	(1, 9, 10),
	(1, 10, 10),
	(1, 16, 10),
	(1, 18, 10),
	(1, 21, 10),
	(1, 22, 10),
	(1, 23, 10),
	(1, 24, 10),
	(1, 27, 10),
	(1, 28, 10),
	(1, 32, 10),
	(1, 34, 10),
	(3, 0, 10),
	(3, 1, 10),
	(3, 3, 10),
	(3, 5, 10),
	(3, 10, 10),
	(3, 11, 10),
	(3, 14, 10),
	(3, 15, 10),
	(3, 17, 10),
	(3, 20, 10),
	(3, 21, 10),
	(3, 22, 10),
	(3, 25, 10),
	(3, 30, 10),
	(3, 31, 10),
	(3, 32, 10),
	(3, 33, 10),
	(3, 35, 10),
	(4, 2, 10),
	(4, 4, 10),
	(4, 5, 10),
	(4, 7, 10),
	(4, 9, 10),
	(4, 10, 10),
	(4, 11, 10),
	(4, 16, 10),
	(4, 17, 10),
	(4, 21, 10),
	(4, 23, 10),
	(4, 24, 10),
	(4, 26, 10),
	(4, 28, 10),
	(4, 30, 10),
	(4, 31, 10),
	(4, 33, 10),
	(4, 35, 10),
	(5, 1, 10),
	(5, 4, 10),
	(5, 10, 10),
	(5, 11, 10),
	(5, 13, 10),
	(5, 15, 10),
	(5, 16, 10),
	(5, 17, 10),
	(5, 19, 10),
	(5, 20, 10),
	(5, 22, 10),
	(5, 23, 10),
	(5, 25, 10),
	(5, 27, 10),
	(5, 29, 10),
	(5, 31, 10),
	(5, 33, 10),
	(6, 0, 10),
	(6, 4, 10),
	(6, 6, 10),
	(6, 9, 10),
	(6, 10, 10),
	(6, 12, 10),
	(6, 13, 10),
	(6, 15, 10),
	(6, 17, 10),
	(6, 18, 10),
	(6, 20, 10),
	(6, 22, 10),
	(6, 25, 10),
	(6, 27, 10),
	(6, 30, 10),
	(6, 32, 10),
	(6, 34, 10),
	(6, 35, 10),
	(30, 2, 10),
	(30, 4, 10),
	(30, 9, 10),
	(31, 2, 10),
	(31, 4, 10),
	(31, 10, 10),
	(32, 3, 10),
	(32, 4, 10),
	(33, 3, 10),
	(33, 9, 10),
	(34, 2, 10),
	(34, 4, 10),
	(34, 8, 10),
	(34, 9, 10),
	(34, 10, 10),
	(34, 15, 10),
	(35, 3, 10),
	(38, 0, 10),
	(38, 1, 10),
	(38, 4, 10),
	(38, 6, 10),
	(38, 8, 10),
	(38, 10, 10),
	(38, 12, 10),
	(38, 13, 10),
	(38, 14, 10),
	(38, 15, 10),
	(38, 17, 10),
	(38, 21, 10),
	(38, 22, 10),
	(38, 24, 10),
	(38, 25, 10),
	(38, 26, 10),
	(38, 28, 10),
	(38, 35, 10),
	(40, 0, 10),
	(40, 5, 10),
	(40, 6, 10),
	(40, 10, 10),
	(40, 11, 10),
	(40, 13, 10),
	(40, 15, 10),
	(40, 16, 10),
	(40, 18, 10),
	(40, 22, 10),
	(40, 25, 10),
	(40, 26, 10),
	(40, 27, 10),
	(40, 28, 10),
	(40, 30, 10),
	(40, 33, 10),
	(40, 34, 10),
	(41, 0, 10),
	(41, 5, 10),
	(41, 7, 10),
	(41, 8, 10),
	(41, 9, 10),
	(41, 10, 10),
	(41, 14, 10),
	(41, 15, 10),
	(41, 18, 10),
	(41, 19, 10),
	(41, 20, 10),
	(41, 21, 10),
	(41, 23, 10),
	(41, 28, 10),
	(41, 32, 10),
	(41, 33, 10),
	(42, 1, 10),
	(42, 3, 10),
	(42, 7, 10),
	(42, 8, 10),
	(42, 10, 10),
	(42, 12, 10),
	(42, 13, 10),
	(42, 15, 10),
	(42, 16, 10),
	(42, 18, 10),
	(42, 19, 10),
	(42, 20, 10),
	(42, 21, 10),
	(42, 25, 10),
	(42, 28, 10),
	(42, 29, 10),
	(42, 34, 10),
	(42, 35, 10),
	(45, 0, 10),
	(45, 1, 10),
	(45, 4, 10),
	(45, 6, 10),
	(45, 7, 10),
	(45, 8, 10),
	(45, 11, 10),
	(45, 12, 10),
	(45, 13, 10),
	(45, 14, 10),
	(45, 18, 10),
	(45, 19, 10),
	(45, 20, 10),
	(45, 24, 10),
	(45, 25, 10),
	(45, 26, 10),
	(45, 31, 10),
	(45, 32, 10),
	(1, 0, 11),
	(1, 1, 11),
	(1, 3, 11),
	(1, 5, 11),
	(1, 8, 11),
	(1, 11, 11),
	(1, 12, 11),
	(1, 13, 11),
	(1, 14, 11),
	(1, 15, 11),
	(1, 17, 11),
	(1, 19, 11),
	(1, 20, 11),
	(1, 25, 11),
	(1, 26, 11),
	(1, 29, 11),
	(1, 30, 11),
	(1, 31, 11),
	(1, 33, 11),
	(1, 35, 11),
	(3, 2, 11),
	(3, 4, 11),
	(3, 6, 11),
	(3, 7, 11),
	(3, 8, 11),
	(3, 9, 11),
	(3, 12, 11),
	(3, 13, 11),
	(3, 16, 11),
	(3, 18, 11),
	(3, 19, 11),
	(3, 23, 11),
	(3, 24, 11),
	(3, 26, 11),
	(3, 27, 11),
	(3, 28, 11),
	(3, 29, 11),
	(3, 34, 11),
	(4, 0, 11),
	(4, 1, 11),
	(4, 3, 11),
	(4, 6, 11),
	(4, 8, 11),
	(4, 12, 11),
	(4, 13, 11),
	(4, 14, 11),
	(4, 15, 11),
	(4, 18, 11),
	(4, 19, 11),
	(4, 20, 11),
	(4, 22, 11),
	(4, 25, 11),
	(4, 27, 11),
	(4, 29, 11),
	(4, 32, 11),
	(4, 34, 11),
	(5, 0, 11),
	(5, 2, 11),
	(5, 3, 11),
	(5, 5, 11),
	(5, 6, 11),
	(5, 7, 11),
	(5, 8, 11),
	(5, 9, 11),
	(5, 12, 11),
	(5, 14, 11),
	(5, 18, 11),
	(5, 21, 11),
	(5, 24, 11),
	(5, 26, 11),
	(5, 28, 11),
	(5, 30, 11),
	(5, 32, 11),
	(5, 34, 11),
	(5, 35, 11),
	(6, 1, 11),
	(6, 2, 11),
	(6, 3, 11),
	(6, 5, 11),
	(6, 7, 11),
	(6, 8, 11),
	(6, 11, 11),
	(6, 14, 11),
	(6, 16, 11),
	(6, 19, 11),
	(6, 21, 11),
	(6, 23, 11),
	(6, 24, 11),
	(6, 26, 11),
	(6, 28, 11),
	(6, 29, 11),
	(6, 31, 11),
	(6, 33, 11),
	(30, 3, 11),
	(30, 5, 11),
	(30, 10, 11),
	(30, 15, 11),
	(31, 3, 11),
	(31, 5, 11),
	(31, 8, 11),
	(32, 0, 11),
	(32, 2, 11),
	(32, 5, 11),
	(33, 2, 11),
	(33, 5, 11),
	(33, 15, 11),
	(34, 0, 11),
	(34, 1, 11),
	(34, 3, 11),
	(34, 5, 11),
	(34, 7, 11),
	(34, 14, 11),
	(34, 21, 11),
	(35, 4, 11),
	(35, 5, 11),
	(36, 1, 11),
	(46, 3, 11),
	(46, 5, 11),
	(46, 6, 11),
	(46, 7, 11),
	(46, 8, 11),
	(46, 10, 11),
	(46, 12, 11),
	(46, 13, 11),
	(46, 15, 11),
	(46, 17, 11),
	(46, 18, 11),
	(46, 20, 11),
	(46, 22, 11),
	(46, 26, 11),
	(46, 29, 11),
	(46, 32, 11),
	(46, 33, 11),
	(46, 34, 11),
	(47, 1, 11),
	(47, 4, 11),
	(47, 5, 11),
	(47, 6, 11),
	(47, 8, 11),
	(47, 14, 11),
	(47, 15, 11),
	(47, 18, 11),
	(47, 20, 11),
	(47, 21, 11),
	(47, 22, 11),
	(47, 23, 11),
	(47, 24, 11),
	(47, 25, 11),
	(47, 28, 11),
	(47, 30, 11),
	(47, 33, 11),
	(47, 35, 11),
	(45, 2, 14),
	(45, 3, 14),
	(45, 5, 14),
	(45, 9, 14),
	(45, 10, 14),
	(45, 15, 14),
	(45, 16, 14),
	(45, 17, 14),
	(45, 21, 14),
	(45, 22, 14),
	(45, 23, 14),
	(45, 27, 14),
	(45, 28, 14),
	(45, 29, 14),
	(45, 30, 14),
	(45, 33, 14),
	(45, 34, 14),
	(45, 35, 14),
	(46, 0, 14),
	(46, 1, 14),
	(46, 2, 14),
	(46, 4, 14),
	(46, 9, 14),
	(46, 11, 14),
	(46, 14, 14),
	(46, 16, 14),
	(46, 19, 14),
	(46, 21, 14),
	(46, 23, 14),
	(46, 24, 14),
	(46, 25, 14),
	(46, 27, 14),
	(46, 28, 14),
	(46, 30, 14),
	(46, 31, 14),
	(46, 35, 14),
	(47, 0, 14),
	(47, 2, 14),
	(47, 3, 14),
	(47, 7, 14),
	(47, 9, 14),
	(47, 10, 14),
	(47, 11, 14),
	(47, 12, 14),
	(47, 13, 14),
	(47, 16, 14),
	(47, 17, 14),
	(47, 19, 14),
	(47, 26, 14),
	(47, 27, 14),
	(47, 29, 14),
	(47, 31, 14),
	(47, 32, 14),
	(47, 34, 14),
	(38, 2, 20),
	(38, 3, 20),
	(38, 5, 20),
	(38, 7, 20),
	(38, 9, 20),
	(38, 11, 20),
	(38, 16, 20),
	(38, 18, 20),
	(38, 19, 20),
	(38, 20, 20),
	(38, 23, 20),
	(38, 27, 20),
	(38, 29, 20),
	(38, 30, 20),
	(38, 31, 20),
	(38, 32, 20),
	(38, 33, 20),
	(38, 34, 20),
	(40, 1, 20),
	(40, 2, 20),
	(40, 3, 20),
	(40, 4, 20),
	(40, 7, 20),
	(40, 8, 20),
	(40, 9, 20),
	(40, 12, 20),
	(40, 14, 20),
	(40, 17, 20),
	(40, 19, 20),
	(40, 20, 20),
	(40, 21, 20),
	(40, 24, 20),
	(40, 31, 20),
	(40, 32, 20),
	(41, 1, 20),
	(41, 2, 20),
	(41, 3, 20),
	(41, 4, 20),
	(41, 6, 20),
	(41, 11, 20),
	(41, 12, 20),
	(41, 13, 20),
	(41, 16, 20),
	(41, 17, 20),
	(41, 22, 20),
	(41, 24, 20),
	(41, 25, 20),
	(41, 26, 20),
	(41, 27, 20),
	(41, 29, 20),
	(42, 0, 20),
	(42, 2, 20),
	(42, 4, 20),
	(42, 5, 20),
	(42, 6, 20),
	(42, 9, 20),
	(42, 11, 20),
	(42, 14, 20),
	(42, 17, 20),
	(42, 22, 20),
	(42, 23, 20),
	(42, 24, 20),
	(42, 26, 20),
	(42, 27, 20),
	(42, 30, 20),
	(42, 31, 20),
	(42, 32, 20),
	(42, 33, 20),
	(44, 2, 20),
	(44, 3, 20),
	(44, 4, 20),
	(44, 5, 20),
	(44, 6, 20),
	(44, 11, 20),
	(44, 12, 20),
	(44, 13, 20),
	(44, 15, 20),
	(44, 16, 20),
	(44, 18, 20),
	(44, 23, 20),
	(44, 26, 20),
	(44, 28, 20),
	(44, 29, 20),
	(44, 31, 20),
	(44, 34, 20),
	(44, 35, 20),
	(44, 0, 28),
	(44, 1, 28),
	(44, 7, 28),
	(44, 8, 28),
	(44, 9, 28),
	(44, 10, 28),
	(44, 14, 28),
	(44, 17, 28),
	(44, 19, 28),
	(44, 20, 28),
	(44, 21, 28),
	(44, 22, 28),
	(44, 24, 28),
	(44, 25, 28),
	(44, 27, 28),
	(44, 30, 28),
	(44, 32, 28),
	(44, 33, 28);

-- Volcando estructura para tabla proyect.games
CREATE TABLE IF NOT EXISTS `games` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `player_one` int(11) DEFAULT NULL,
  `player_two` int(11) DEFAULT NULL,
  `public` tinyint(1) NOT NULL DEFAULT 1,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `turn` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`game_id`),
  KEY `player_one` (`player_one`),
  KEY `player_two` (`player_two`),
  CONSTRAINT `games_ibfk_1` FOREIGN KEY (`player_one`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `games_ibfk_2` FOREIGN KEY (`player_two`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla proyect.games: ~23 rows (aproximadamente)
INSERT INTO `games` (`game_id`, `player_one`, `player_two`, `public`, `active`, `turn`) VALUES
	(1, 11, 10, 1, 0, 0),
	(3, 11, 10, 0, 0, 1),
	(4, 11, 10, 1, 0, 1),
	(5, 11, 10, 1, 0, 1),
	(6, 11, 10, 1, 0, 0),
	(30, 11, 10, 1, 1, 0),
	(31, 11, 10, 1, 1, 1),
	(32, 11, 10, 1, 1, 0),
	(33, 11, 10, 1, 1, 0),
	(34, 11, 10, 1, 1, 0),
	(35, 11, 10, 1, 1, 0),
	(36, 11, 18, 1, 1, 0),
	(37, 18, 10, 1, 1, 1),
	(38, 10, 20, 1, 0, 1),
	(40, 10, 20, 1, 1, 0),
	(41, 20, 10, 1, 1, 1),
	(42, 20, 10, 1, 0, 1),
	(43, 20, 14, 1, 1, 1),
	(44, 28, 20, 1, 0, 1),
	(45, 10, 14, 1, 0, 1),
	(46, 11, 14, 1, 0, 1),
	(47, 14, 11, 1, 0, 1),
	(48, 29, NULL, 1, 1, 1);

-- Volcando estructura para tabla proyect.partidasterminadas
CREATE TABLE IF NOT EXISTS `partidasterminadas` (
  `game_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `result` enum('w','l','t') DEFAULT NULL,
  `horizontal` int(11) NOT NULL,
  `vertical` int(11) NOT NULL,
  `diagonal` int(11) NOT NULL,
  PRIMARY KEY (`game_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `partidasterminadas_ibfk_1` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`),
  CONSTRAINT `partidasterminadas_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla proyect.partidasterminadas: ~18 rows (aproximadamente)
INSERT INTO `partidasterminadas` (`game_id`, `user_id`, `result`, `horizontal`, `vertical`, `diagonal`) VALUES
	(3, 10, 't', 2, 1, 1),
	(3, 11, 't', 2, 1, 1),
	(4, 10, 'l', 0, 1, 3),
	(4, 11, 'w', 1, 1, 5),
	(5, 10, 'l', 0, 3, 1),
	(5, 11, 'w', 1, 3, 3),
	(38, 10, 't', 1, 0, 2),
	(38, 20, 't', 2, 0, 1),
	(42, 10, 'w', 1, 2, 2),
	(42, 20, 'l', 1, 1, 1),
	(44, 20, 'l', 1, 0, 0),
	(44, 28, 'w', 2, 0, 3),
	(45, 10, 't', 0, 7, 0),
	(45, 14, 't', 0, 6, 1),
	(46, 11, 'l', 0, 0, 3),
	(46, 14, 'w', 0, 0, 4),
	(47, 11, 'w', 1, 0, 3),
	(47, 14, 'l', 0, 0, 0);

-- Volcando estructura para tabla proyect.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `pwd` char(100) NOT NULL DEFAULT '',
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla proyect.users: ~9 rows (aproximadamente)
INSERT INTO `users` (`user_id`, `username`, `pwd`, `email`) VALUES
	(10, 'prueba', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'prueba@gmail.com'),
	(11, 'manu', 'aa11b3a598ef74368054112e3cec8b75189535df70de730022133f2b230c031c', 'manu@gmail.com'),
	(14, 'prueba2', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'prueba2@gmail.com'),
	(15, 'Prueba1702', '7a64ce427ce0ca963ce9c3ab0da2db27c1f3ac9620444e1b4312422af8e093b9', 'prueba1702@gmail.com'),
	(18, 'john doe', '748e02a3018e07f45b9416266ae928c3fb42780999a1a017e0436df6d6a998c4', 'jdn@gmail.com'),
	(20, 'Testing', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'testing@gmail.com'),
	(27, 'test2', '37b37a3816e2574d26a418fd93d8790f86abe73398bf7dbb3104890cab087a27', 'test12@gmail.com'),
	(28, 'test02', 'ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae', 'test010@gmail.com'),
	(29, 'jelopez', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'jelopez@prurba.com');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
