CREATE TABLE `Documents_Type` (
	`Doc_Type_ID` INT NOT NULL AUTO_INCREMENT,
	`Doc_Type_Name` VARCHAR(100) NULL,
	PRIMARY KEY (`Doc_Type_ID`),
	UNIQUE INDEX `Doc_Type_Name` (`Doc_Type_Name`)
)
COMMENT='it holds doc types'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

CREATE TABLE `Policy_Documents` (
	`Document_ID` INT NOT NULL AUTO_INCREMENT,
	`Document_Name` VARCHAR(100) NULL,
	`Doc_Type_ID` INT NULL,
	`Comments` VARCHAR(200) NULL,
	`Uploaded_Date` DATETIME NULL,
	`Status` VARCHAR(50) NULL,
	`Person_Det_ID` INT NULL,
	`Policy_Number` VARCHAR(100) NULL,
	PRIMARY KEY (`Document_ID`),
	CONSTRAINT `Doc_type_fk` FOREIGN KEY (`Doc_Type_ID`) REFERENCES `documents_type` (`Doc_Type_ID`),
	CONSTRAINT `personal_details_fk` FOREIGN KEY (`Person_Det_ID`) REFERENCES `personal_details` (`Personal_ID`),
	CONSTRAINT `Pol_num_fk` FOREIGN KEY (`Policy_Number`) REFERENCES `Insurance_Comapny_Details` (`Policy_Number`)
)
COMMENT='it holds the documents of insureds related to policy'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

INSERT INTO `slbroking`.`documents_type` (`Doc_Type_Name`) VALUES ('Policy Documents');
INSERT INTO `slbroking`.`documents_type` (`Doc_Type_Name`) VALUES ('Claims Documents');
INSERT INTO `slbroking`.`documents_type` (`Doc_Type_Name`) VALUES ('E-Cards Documents');
INSERT INTO `slbroking`.`documents_type` (`Doc_Type_Name`) VALUES ('Mandate Documents');
INSERT INTO `slbroking`.`documents_type` (`Doc_Type_Name`) VALUES ('Policy Brochure');
INSERT INTO `slbroking`.`documents_type` (`Doc_Type_Name`) VALUES ('Others');
