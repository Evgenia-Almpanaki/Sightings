 DROP DATABASE IF EXISTS sightings;
 CREATE DATABASE sightings;
USE sightings;

CREATE TABLE Characters (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    description MEDIUMTEXT,
    superpower VARCHAR(30),
    isHero BOOLEAN,
    isVillain BOOLEAN
);
CREATE TABLE Organisations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    description MEDIUMTEXT,
    address VARCHAR(30),
    contact VARCHAR(20)
);
CREATE TABLE Affiliations (
    characterID INT,
    CONSTRAINT fk_Character FOREIGN KEY (characterID)
        REFERENCES Characters (id),
    organisationID INT,
    CONSTRAINT fk_Organisation FOREIGN KEY (organisationID)
        REFERENCES Organisations (id)
);
CREATE TABLE Locations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    description MEDIUMTEXT,
    address VARCHAR(30),
    latitude VARCHAR(30),
    longitude VARCHAR(30)
);
CREATE TABLE Sightings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    characterID INT,
    locationID INT,
    date varchar(20),
    CONSTRAINT fk_CharacterS FOREIGN KEY (characterID)
        REFERENCES Characters (id),
    CONSTRAINT fk_Location FOREIGN KEY (LocationID)
        REFERENCES Locations (id)
);


