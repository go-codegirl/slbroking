CREATE TABLE `policy_type` (
	`policy_type_id` INT NOT NULL AUTO_INCREMENT,
	`policy_type_name` VARCHAR(50) NULL,
	PRIMARY KEY (`policy_type_id`),
	UNIQUE INDEX `policy_type_name` (`policy_type_name`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

ALTER TABLE `policy_details`
	ADD COLUMN `Policy_Type_Id` INT(11) NOT NULL AFTER `Person_Details_ID`,
	ADD CONSTRAINT `policy_type_id_fk` FOREIGN KEY (`Policy_Type_Id`) REFERENCES `policy_type` (`policy_type_id`);

INSERT INTO `slbroking`.`policy_type` (`policy_type_name`) VALUES ('Health');
INSERT INTO `slbroking`.`policy_type` (`policy_type_name`) VALUES ('Tarvel');
INSERT INTO `slbroking`.`policy_type` (`policy_type_name`) VALUES ('Fire');
INSERT INTO `slbroking`.`policy_type` (`policy_type_name`) VALUES ('Property');
INSERT INTO `slbroking`.`policy_type` (`policy_type_name`) VALUES ('Marine');
INSERT INTO `slbroking`.`policy_type` (`policy_type_name`) VALUES ('Motor');
INSERT INTO `slbroking`.`policy_type` (`policy_type_name`) VALUES ('Engineering');

INSERT INTO `slbroking`.`user` (`ID`, `FIRST_NAME`, `LAST_NAME`, `PASSWORD`, `ROLE`, `PRIMARY_EMAIL`, `PRIMARY_PHONE`, `STATUS`, `company_id`) VALUES (1, 'SafetyLeaf', 'Admin', '8sx7l7pXRM1tiVjiP5uIKA==', 'EMP', 'sladmin@gemini.com', '9494690894', 'AC', 1);

