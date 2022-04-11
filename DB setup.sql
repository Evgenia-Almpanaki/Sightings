 DROP DATABASE IF EXISTS sightings;
 CREATE DATABASE sightings;
USE sightings;

CREATE TABLE Characters (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    description MEDIUMTEXT,
    superpower VARCHAR(30),
    isHero BOOLEAN,
    isVillain BOOLEAN
);
CREATE TABLE Organisations (
    id INT PRIMARY KEY,
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
    id INT PRIMARY KEY,
    name VARCHAR(50),
    description MEDIUMTEXT,
    address VARCHAR(30),
    latitude VARCHAR(30),
    longitude VARCHAR(30)
);
CREATE TABLE Sightings (
    characterID INT,
    LocationID INT,
    CONSTRAINT fk_CharacterS FOREIGN KEY (characterID)
        REFERENCES Characters (id),
    CONSTRAINT fk_Location FOREIGN KEY (LocationID)
        REFERENCES Locations (id)
);

INSERT INTO Characters  values (1, "ariel", "ocea nnn", "s1", 1, 0), (2, "triton", "seas ide", "s2", 0, 1);
INSERT INTO Organisations (id, name) values (1, "SHIELD"), (2, "HYDRA"), (3, "AVENGERS");
INSERT INTO Affiliations values (1, 2);
INSERT INTO Affiliations values (2, 2);
INSERT INTO Affiliations values (2, 1);

