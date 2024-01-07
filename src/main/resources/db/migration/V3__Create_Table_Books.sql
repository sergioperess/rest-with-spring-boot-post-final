
DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(180) NOT NULL,
  `launch_date` datetime(6) NOT NULL,
  `price` decimal(65,2) NOT NULL,  
  `title` varchar(250) NOT NULL,  
  PRIMARY KEY (`id`)
);


