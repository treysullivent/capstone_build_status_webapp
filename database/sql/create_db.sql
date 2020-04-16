CREATE DATABASE pci_database;
USE pci_database;
SET SQL_MODE='ALLOW_INVALID_DATES';

CREATE TABLE jobs (
ID int NOT NULL AUTO_INCREMENT,
Name varchar(128) NOT NULL,
Type varchar(32) NOT NULL CHECK (type IN ('DOWNLOAD','EXTRACT','INSTALL')),
PRIMARY KEY (ID),
UNIQUE KEY (ID, Name)
);


CREATE TABLE download_jobs (
ID int NOT NULL,
Name varchar(128) NOT NULL,
ClientName varchar(32) NOT NULL,
ServerName varchar(64) NOT NULL,
StartTime timestamp NULL DEFAULT 0,
TargetServer varchar(128),
PCIVersion varchar(16),
DatabaseVersion varchar(16),
PCIInstallVersion varchar(16),
LicenseGroup varchar(128),
EndTime timestamp NULL DEFAULT 0,
Status varchar(32),
FOREIGN KEY (ID) REFERENCES jobs(ID),
PRIMARY KEY (ID)
);

CREATE TABLE extract_jobs (
ID int NOT NULL,
Name varchar(128) NOT NULL,
ClientName varchar(32) NOT NULL,
ServerName varchar(64) NOT NULL,
StartTime timestamp NULL DEFAULT 0,
TargetServer varchar(128),
PCIVersion varchar(16),
DatabaseVersion varchar(16),
PCIInstallVersion varchar(16),
LicenseGroup varchar(128),
FOREIGN KEY (ID) REFERENCES jobs(ID),
PRIMARY KEY (ID)
);

CREATE TABLE install_jobs (
ID int NOT NULL,
Name varchar(128) NOT NULL,
ClientName varchar(32) NOT NULL,
ServerName varchar(64) NOT NULL,
StartTime timestamp NULL DEFAULT 0,
TargetServer varchar(128),
PCIVersion varchar(16),
DatabaseVersion varchar(16),
PCIInstallVersion varchar(16),
LicenseGroup varchar(128),
EndTime timestamp NULL DEFAULT 0,
Status varchar(32),
FOREIGN KEY (ID) REFERENCES jobs(ID),
PRIMARY KEY (ID)
);

CREATE TABLE notifications (
ID int NOT NULL AUTO_INCREMENT,
/*JobID int NOT NULL, */
JobName varchar(128) NOT NULL,
Type varchar(32),
Message text,
Time timestamp NULL DEFAULT 0,
PRIMARY KEY (ID)/*,
FOREIGN KEY (JobID, JobName) REFERENCES jobs(ID, Name)*/
);