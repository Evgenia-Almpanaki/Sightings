DROP DATABASE IF EXISTS sightings;
CREATE DATABASE sightings;
USE sightings;

CREATE TABLE Superheroes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description MEDIUMTEXT,
    superpower VARCHAR(30)
);
CREATE TABLE Villains (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description MEDIUMTEXT,
    superpower VARCHAR(30)
);
CREATE TABLE SuperheroOrganisations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description MEDIUMTEXT,
    address VARCHAR(50),
    contact VARCHAR(20)
);
CREATE TABLE VillainOrganisations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description MEDIUMTEXT,
    address VARCHAR(50),
    contact VARCHAR(20)
);
CREATE TABLE Locations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    description MEDIUMTEXT,
    address VARCHAR(30),
    latitude VARCHAR(30),
    longitude VARCHAR(30)
);
CREATE TABLE SuperheroAffiliations (
    heroID INT,
    organisationID INT,
    PRIMARY KEY(heroID, organisationID),
    CONSTRAINT fk_Superhero FOREIGN KEY (heroID) REFERENCES Superheroes (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_SuperheroOrganisation FOREIGN KEY (organisationID) REFERENCES SuperheroOrganisations (id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE VillainAffiliations (
    villainID INT,
    villainOrganisationID INT,
    PRIMARY KEY(villainID, villainOrganisationID),
    CONSTRAINT fk_Villain FOREIGN KEY (villainID) REFERENCES Villains (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_VillainOrganisation FOREIGN KEY (villainOrganisationID) REFERENCES VillainOrganisations (id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE SuperheroSightings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    characterID INT,
    locationID INT,
    date date,
    CONSTRAINT fk_hero FOREIGN KEY (characterID) REFERENCES Superheroes (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_heroLocation FOREIGN KEY (locationID) REFERENCES Locations (id)  ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE VillainSightings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    characterID INT,
    locationID INT,
    date date,
    CONSTRAINT fk_villain2 FOREIGN KEY (characterID) REFERENCES Villains (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_villainLocation FOREIGN KEY (locationID) REFERENCES Locations (id) ON UPDATE CASCADE ON DELETE CASCADE
);