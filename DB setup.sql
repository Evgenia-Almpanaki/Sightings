DROP DATABASE IF EXISTS sightings;
CREATE DATABASE sightings;
USE sightings;

CREATE TABLE Superheroes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description MEDIUMTEXT,
    superpower VARCHAR(50)
);
CREATE TABLE Villains (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description MEDIUMTEXT,
    superpower VARCHAR(50)
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

INSERT INTO Superheroes(name, description, superpower) values 
("Scarlet Witch", "Native of Sokovia who grew up with her fraternal twin brother, Pietro. Born with the latent mythical ability to harness Chaos Magic.", "Chaos Magic"),
("Storm","Descended from a long line of African witch-priestesses, Storm is able to control the weather and atmosphere and is considered to be one of the most powerful mutants on the planet.","Weather Control"),
("Thor","God of Thunder","Thunder"),
("Human Torch","Exposed to high levels of cosmic radiation when older sister Sue Storm's boyfriend and scientist; Dr. Reed Richards took them and pilot Ben Grimm, into space in the stolen rocket; Marvel-1. The radiation mutated him and his friends, turning his entire body into a fiery, plasma-like state.","Fire"),
("Green Lantern","Summoned to the crashed wreckage of a spaceship belonging to an alien named Abin Sur, who explained that he was a member of the Green Lantern Corps, an organization of beings from across the cosmos, armed with power rings fueled by the green energy of all willpower in the universe. Upon his death, Abin entrusted his ring and duties as the Green Lantern of Earth’s space sector to Hal Jordan.","Hard light constructs");

INSERT INTO Villains(name, description, superpower) values 
("Doctor Doom","Latverian politician who serves as the Monarch and Supreme Leader for the Kingdom of Latveria.[35] He was scarred from an accident and wears an iron mask and armor to hide his true face. He is considered one of the most brilliant minds and scientists on the planet Earth.","Mystical Force-Fields"),
("Ultron","Ultron was an Artificial Intelligence, who was originally designed to be part of a Peacekeeping Program created by both Tony Stark and Bruce Banner, using the decrypted code derived from the Mind Stone encased within Loki's Scepter. However, once he had been activated, Ultron's original programming directive, which was to protect Earth from all domestic and extraterrestrial threats, had swiftly become corrupted.","Superhuman Strength & Reflexes"),
("Malekith","Also called the \"Witch King of Naggaroth\ he is the monarch of the kingdoms of Naggaroth in the New World and the ruler of the Dark Elves.","Dark magic"),
("Thanos","Warlord from Titan, whose objective is to bring stability to the universe by wiping out half of all life at every level.","Superhuman Speed & Strength");

INSERT INTO SuperheroOrganisations(name, description, address, contact) values 
("S.H.I.E.L.D.","The acronym originally stood for Supreme Headquarters, International Espionage and Law-Enforcement Division. It was changed in 1991 to Strategic Hazard Intervention Espionage Logistics Directorate. Within the various films set in the Marvel Cinematic Universe, as well as multiple animated and live-action television series, the backronym stands for Strategic Homeland Intervention, Enforcement and Logistics Division.","New York","090-342-2334"),
("Fantastic Four","Team of adventurers who accidentally received powers from \"cosmic radiation\" during a short trip to space.","New York City","208-234-2424"),
("Guardians of the Galaxy.","The Guardians of the Galaxy are a team of intergalactic outlaws who banded together to protect the galaxy from planetary threats. In 2014, they managed to save the planet of Xandar from Ronan the Accuser and have been recognized by the Nova Empire. Two months later, they defeated the Abilisk, attracted the unwelcome attentions of Ayesha and her Sovereign fleet, and defeated Ego while gaining a new member, Mantis.","Unknown","Unknown");

INSERT INTO VillainOrganisations(name, description, address, contact) values 
("HYDRA","Paramilitary-subversive terrorist organization bent on world domination. It was founded in ancient times, formerly as a cult centered around the fanatical worship of Hive, a powerful Inhuman that was exiled to the planet Maveth by ancient Inhumans. Ever since his banishment, the cult had been determined to bring him back to Earth to commence a planetary takeover.","Unknown","Unknown"),
("Suicide Squad","Officially known as Task Force X, is a covert paramilitary team comprised of incarcerated super-criminals, orchestrated by Amanda Waller out of Belle Reve Penitentiary.","Belle Reve","902-302-4023"),
("O.M.N.I.U.M.","Secret think tank organization with a hidden agenda.","Earth-616","543-545-2526");

INSERT INTO Locations(name, description, address, latitude, longitude) values 
("Asgard","One of the Nine Worlds surrounding the tree Yggdrasil.","Unknown","Unknown","Unknown"),
("Avengers Headquarters","It has traditionally been the base of the Avengers.","890 Fifth Avenue, Manhattan","-73° 58' 4.8288", "40° 46' 15.8556"),
("New York","City in the USA","-","73° 56' 6.8712'' W","40° 43' 50.1960'' N"),
("Sokovia","Country","Europe","59.44585","164.34648"),
("Hydra Base","Hidden from the sun","Pacific Ocean","-25.70169","-64.99876");

INSERT INTO SuperheroAffiliations(heroID, organisationID) values 
(1, 2),
(2, 3),
(1, 3),
(4, 2),
(3, 1);

INSERT INTO VillainAffiliations(villainID, villainOrganisationID) values 
(1, 2),
(2, 3),
(1, 3),
(4, 2),
(3, 1);

INSERT INTO SuperheroSightings(characterID, locationID, date) values 
(5, 1, '2020-12-02'),
(1, 2, '2015-10-28'),
(2, 3, '1920-02-12'),
(4, 4, '2013-11-01'),
(5, 5, '2017-03-13'),
(2, 1, '2003-02-25'),
(4, 2, '2005-06-30');

INSERT INTO VillainSightings(characterID, locationID, date) values 
(1, 3, '2022-12-02'),
(1, 4, '2018-10-28'),
(2, 5, '1920-02-12'),
(4, 1, '2011-11-01'),
(3, 3, '2017-03-13'),
(2, 2, '2019-02-25'),
(4, 4, '2005-06-30');

DROP DATABASE IF EXISTS sightingsTest;
CREATE DATABASE sightingsTest;
USE sightingsTest;

CREATE TABLE Superheroes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description MEDIUMTEXT,
    superpower VARCHAR(50)
);
CREATE TABLE Villains (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description MEDIUMTEXT,
    superpower VARCHAR(50)
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