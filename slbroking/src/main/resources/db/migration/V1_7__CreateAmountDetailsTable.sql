CREATE TABLE `Amount_Details` (
	`Amount_ID` INT NOT NULL AUTO_INCREMENT,
	`Gross_Premium_Amount` DOUBLE NOT NULL,
	`Terrorism_Premium_Amount` DOUBLE NOT NULL,
	`Service_Tax` DOUBLE NOT NULL,
	`Service_Tax_Amount` DOUBLE NOT NULL,
	`Net_Premium_Amount` DOUBLE NOT NULL,
	`Comission_Rate` DOUBLE NOT NULL,
	`Comission_Rate_Amount` DOUBLE NOT NULL,
	`Collection_Date` DATE NOT NULL,
	`Personal_Detail_ID` INT NOT NULL,
	`Policy_Number` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`Amount_ID`),
	CONSTRAINT `per_details_id` FOREIGN KEY (`Personal_Detail_ID`) REFERENCES `personal_details` (`Personal_ID`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

ALTER TABLE `amount_details`
	ADD CONSTRAINT `policy_num_fk` FOREIGN KEY (`Policy_Number`) REFERENCES `Insurance_Comapny_Details` (`Policy_Number`);
