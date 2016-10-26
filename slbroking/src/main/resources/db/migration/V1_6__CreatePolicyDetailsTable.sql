CREATE TABLE `Policy_Details` (
	`Policy_ID` INT NOT NULL AUTO_INCREMENT,
	`Insurance_Type` VARCHAR(50) NOT NULL,
	`Product_Name` VARCHAR(50) NOT NULL,
	`Policy_Details` VARCHAR(200) NOT NULL,
	`Person_Details_ID` INT NOT NULL,
	PRIMARY KEY (`Policy_ID`),
	CONSTRAINT `PersaonlID` FOREIGN KEY (`Person_Details_ID`) REFERENCES `personal_details` (`Personal_ID`)
)
COMMENT='its policy details of insured'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

ALTER TABLE `policy_details`
	ADD COLUMN `P_Number` VARCHAR(50) NULL AFTER `Person_Details_ID`,
	ADD CONSTRAINT `P_Number_fkey` FOREIGN KEY (`P_Number`) REFERENCES `insurance_comapny_details` (`Policy_Number`);

	