CREATE TABLE `ADDRESS` (
	`Address_ID` INT NOT NULL AUTO_INCREMENT,
	`Address` VARCHAR(100) NOT NULL,
	`Address_Line_1` VARCHAR(100) NOT NULL,
	`Address_Line_2` VARCHAR(100) NULL,
	`City` VARCHAR(50) NOT NULL,
	`State` VARCHAR(50) NOT NULL,
	`Zip_Postal_Code` VARCHAR(50) NOT NULL,
	`Country` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`Address_ID`)
)
COMMENT='it holds the address details of insureds'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
