CREATE TABLE USER (
         ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'The PRIMARY AUTO INCREMENT identifier',
         FIRST_NAME VARCHAR(100) NOT NULL COMMENT 'The FirstName/GivenName of the registred user',
         MIDDLE_NAME VARCHAR(100) COMMENT 'The MiddleName/Initials of the registred user',
         LAST_NAME VARCHAR(100) NOT NULL COMMENT 'The LastName/SurName of the registred user',
         PASSWORD VARCHAR(512) NOT NULL COMMENT 'The encrypted password of the registred user',
         ROLE VARCHAR(50) NOT NULL COMMENT 'The role of registred user - EMP or HR',
         PRIMARY_EMAIL VARCHAR(100) NOT NULL COMMENT 'The primary email address of this registred user',
         PRIMARY_PHONE VARCHAR(10) COMMENT 'The primary phone number of this registred user',
         STATUS VARCHAR(2) NOT NULL DEFAULT 'PE' COMMENT 'The status of this registred user',
         CREATED_ON TIMESTAMP NOT NULL DEFAULT 0 COMMENT 'The datetime when record was created',
         UPDATED_ON TIMESTAMP NOT NULL DEFAULT 0 COMMENT 'The datetime when record was updated',
         COMMENTS varchar(1000) DEFAULT NULL,
         UNIQUE INDEX PRIMARY_EMAIL_UNIQUE (PRIMARY_EMAIL ASC) 
       );

DELIMITER //
CREATE TRIGGER USER_CRE_UPD_ON_INS_TRG BEFORE INSERT ON USER FOR EACH ROW BEGIN
		SET NEW.CREATED_ON = NOW() + 0 ; 
		SET NEW.UPDATED_ON = NOW() + 0 ; 
    END;//
DELIMITER ;

CREATE TRIGGER USER_UPDATED_ON_TRIGGER BEFORE UPDATE ON USER
    FOR EACH ROW SET NEW.UPDATED_ON = NOW() + 0 ;


ALTER TABLE user ADD COLUMN company_id int NOT NULL;
