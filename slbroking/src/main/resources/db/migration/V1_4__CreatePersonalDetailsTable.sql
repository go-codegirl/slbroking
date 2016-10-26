CREATE TABLE `PERSONAL_DETAILS` (
	`Personal_ID` INT NOT NULL AUTO_INCREMENT,
	`Industry` VARCHAR(50) NOT NULL,
	`Name_Of_Individual` VARCHAR(100) NULL,
	`Name_of_Company` VARCHAR(100) NULL,
	`Phone_Number` VARCHAR(20) NOT NULL,
	`Secondary_Number` VARCHAR(20) NULL,
	`Email_Address` VARCHAR(50) NOT NULL,
	`Secondray_Email_Address` VARCHAR(50) NULL,
	`Date_of_Birth` DATE NULL,
	`Gender` VARCHAR(20) NULL,
	`Address_ID` INT NOT NULL,
	PRIMARY KEY (`Personal_ID`),
	UNIQUE INDEX `Phone_Number` (`Phone_Number`),
	UNIQUE INDEX `Email_Address` (`Email_Address`),
	CONSTRAINT `Address_ID` FOREIGN KEY (`Address_ID`) REFERENCES `address` (`Address_ID`)
)
COMMENT='its holds personal details of insured'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
