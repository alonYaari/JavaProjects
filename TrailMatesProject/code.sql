CREATE TABLE `travel_company` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `company_rating` double DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `company_id_UNIQUE` (`company_id`),
  CONSTRAINT `company_rating_check` CHECK ((`company_rating` between 0 and 10))
) 
