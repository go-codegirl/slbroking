CREATE TABLE `Insurance_Comapny_Details` (
	`Insurance_Company_ID` INT NOT NULL AUTO_INCREMENT,
	`Policy_Type` VARCHAR(50) NOT NULL,
	`Policy_Number` VARCHAR(50) NOT NULL,
	`Office_Code` VARCHAR(50) NOT NULL,
	`Agent` VARCHAR(50) NOT NULL,
	`Endorse_Number` VARCHAR(50) NOT NULL,
	`Policy_Start_Date` DATE NOT NULL,
	`Policy_End_Date` DATE NOT NULL,
	`Insurance_company_name` VARCHAR(100) NOT NULL,
	`Insurance_branch_address` VARCHAR(200) NOT NULL,
	`Reference` VARCHAR(100) NOT NULL,
	`Personal_Details_ID` INT NOT NULL,
	UNIQUE INDEX `Policy_Number` (`Policy_Number`),
	UNIQUE INDEX `Endorse_Number` (`Endorse_Number`),
	PRIMARY KEY (`Insurance_Company_ID`),
	CONSTRAINT `Personal_Details_id` FOREIGN KEY (`Personal_Details_ID`) REFERENCES `personal_details` (`Personal_ID`)
)
COMMENT='its holds the details of insurance company details of insured policy'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
