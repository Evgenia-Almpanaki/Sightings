DROP DATABASE IF EXISTS sightings;
CREATE DATABASE sightings;
USE sightings;

CREATE TABLE Characters(
	id int primary key,
    name varchar(50),
    description mediumtext,
    superpower varchar(30),
    isHero boolean,
    isVillain boolean);

CREATE TABLE Organisations(
id int primary key,
name varchar(50),
    description mediumtext,
    address varchar(30),
    contact varchar(20));

CREATE TABLE affiliations(
characterID int, 
CONSTRAINT fk_Character FOREIGN KEY (characterID) REFERENCES Characters(id),
organisationID int, 
CONSTRAINT fk_Organisation FOREIGN KEY (organisationID) REFERENCES Organisations(id)
);

CREATE TABLE Locations(
id int primary key,
name varchar(50),
    description mediumtext,
    address varchar(30),
    latitude varchar(30),
    longitude varchar(30)
);

CREATE TABLE Sightings(
characterID int, 
LocationID int, 
CONSTRAINT fk_CharacterS FOREIGN KEY (characterID) REFERENCES Characters(id),
CONSTRAINT fk_Location FOREIGN KEY (LocationID) REFERENCES Locations(id)
);
