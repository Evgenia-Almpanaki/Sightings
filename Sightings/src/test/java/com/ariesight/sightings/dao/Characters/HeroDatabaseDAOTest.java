package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dao.Organisations.HeroOrganisationDAO;
import com.ariesight.sightings.dao.Sightings.HeroSightingDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import com.ariesight.sightings.dto.Organisations.Organisation;
import com.ariesight.sightings.dto.Sightings.HeroSighting;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HeroDatabaseDAOTest {

    @Autowired
    HeroDAO heroDAO;
    
    @Autowired
    LocationDAO locationDAO;

    @Autowired
    HeroSightingDAO heroSightingDAO;
    
    @Autowired
    HeroOrganisationDAO organisationDAO;

    public HeroDatabaseDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        for (HeroSighting sighting : heroSightingDAO.getAllSightings()) {
            heroSightingDAO.deleteSightingById(sighting.getId());
        }
        for (Hero hero : heroDAO.getAllHeroes()) {
            heroDAO.deleteHeroById(hero.getId());
        }
        for (Location location : locationDAO.getAllLocations()) {
            locationDAO.deleteLocationById(location.getId());
        }
        for (Organisation organisation : organisationDAO.getAllOrganisations()) {
            organisationDAO.deleteOrganisationById(organisation.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetHero() {
        Hero hero = new Hero();
        hero.setName("Test Name");
        hero.setDescription("Test Description");
        hero.setSuperpower("Test Superpower");

        heroDAO.addHero(hero);
        assertEquals(hero, heroDAO.getHeroById(hero.getId()));
    }

    @Test
    public void testGetAllHeroes() {

        Hero hero = new Hero();
        hero.setName("Test Name");
        hero.setDescription("Test Description");
        hero.setSuperpower("Test Superpower");
        heroDAO.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setName("Test Name 2");
        hero2.setDescription("Test Description 2");
        hero2.setSuperpower("Test Superpower 2");
        heroDAO.addHero(hero2);

        List<Hero> heroes = heroDAO.getAllHeroes();

        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));
    }

    @Test
    public void testUpdateHero() {

        Hero hero = new Hero();
        hero.setName("Test Name");
        hero.setDescription("Test Description");
        hero.setSuperpower("Test Superpower");
        heroDAO.addHero(hero);

        Hero heroFromDAO = heroDAO.getHeroById(hero.getId());
        assertEquals(hero, heroFromDAO);

        hero.setName("New Name");
        assertNotEquals(heroFromDAO, hero);

        heroDAO.updateHero(hero);
        heroFromDAO = heroDAO.getHeroById(hero.getId());
        assertEquals(hero, heroFromDAO);
    }

    @Test
    public void testDeleteHeroByID() {

        Hero hero = new Hero();
        hero.setName("Test Name");
        hero.setDescription("Test Description");
        hero.setSuperpower("Test Superpower");
        heroDAO.addHero(hero);

        Hero heroFromDAO = heroDAO.getHeroById(hero.getId());
        assertEquals(heroFromDAO, hero);

        heroDAO.deleteHeroById(hero.getId());

        heroFromDAO = heroDAO.getHeroById(hero.getId());
        assertNull(heroFromDAO);

    }

    @Test
    public void testGetHeroByName() {
        Hero hero = new Hero();
        hero.setName("Test Name");
        hero.setDescription("Test Description");
        hero.setSuperpower("Test Superpower");

        heroDAO.addHero(hero);
        assertEquals(hero, heroDAO.getHeroByName(hero.getName()));
    }

    @Test
    public void testGetHeroesByLocation() {
        Hero hero1 = new Hero();
        hero1.setName("Test Hero 1");
        hero1.setDescription("Test Description 1");
        hero1.setSuperpower("Test Superpower 1");
        heroDAO.addHero(hero1);

        Hero hero2 = new Hero();
        hero2.setName("Test Hero 2");
        hero2.setDescription("Test Description 2");
        hero2.setSuperpower("Test Superpower 2");
        heroDAO.addHero(hero2);

        Hero hero3 = new Hero();
        hero3.setName("Test Hero 3");
        hero3.setDescription("Test Description 3");
        hero3.setSuperpower("Test Superpower 3");
        heroDAO.addHero(hero3);

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("Test Address 2");
        location2.setLatitude("Test Latitude 2");
        location2.setLongitude("Test Longitude 2");
        locationDAO.addLocation(location2);

        HeroSighting sighting = new HeroSighting();
        sighting.setCharacterID(hero1.getId());
        sighting.setCharacterName(hero1.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate("2020-10-20");
        heroSightingDAO.addSighting(sighting);

        HeroSighting sighting2 = new HeroSighting();
        sighting2.setCharacterID(hero1.getId());
        sighting2.setCharacterName(hero1.getName());
        sighting2.setLocationID(location2.getId());
        sighting2.setLocationName(location2.getName());
        sighting2.setDate("2021-01-21");
        heroSightingDAO.addSighting(sighting2);

        HeroSighting sighting3 = new HeroSighting();
        sighting3.setCharacterID(hero2.getId());
        sighting3.setCharacterName(hero2.getName());
        sighting3.setLocationID(location2.getId());
        sighting3.setLocationName(location2.getName());
        sighting3.setDate("2022-02-22");
        heroSightingDAO.addSighting(sighting3);
        
        List<Hero> heroes = heroDAO.getHeroesByLocation(location2);
        
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero1));
        assertTrue(heroes.contains(hero2));
    }

    @Test
    public void testGetHeroesByOrganisation() {
        Hero hero1 = new Hero();
        hero1.setName("Test Hero 1");
        hero1.setDescription("Test Description 1");
        hero1.setSuperpower("Test Superpower 1");
        heroDAO.addHero(hero1);

        Hero hero2 = new Hero();
        hero2.setName("Test Hero 2");
        hero2.setDescription("Test Description 2");
        hero2.setSuperpower("Test Superpower 2");
        heroDAO.addHero(hero2);

        HeroOrganisation organisation = new HeroOrganisation();
        organisation.setName("Test Name");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation);
        
        HeroOrganisation organisation2 = new HeroOrganisation();
        organisation2.setName("Test Name 2");
        organisation2.setDescription("Test Description 2");
        organisation2.setAddress("Test Address 2");
        organisation2.setContact("Test Contact 2");
        organisationDAO.addOrganisation(organisation2);

        organisationDAO.addAffiliation(organisation, hero1);
        organisationDAO.addAffiliation(organisation, hero2);
        organisationDAO.addAffiliation(organisation2, hero2);
        
        List<Hero> heroes = heroDAO.getHeroesByOrganisation(organisation);
        
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero1));
        assertTrue(heroes.contains(hero2));
    }

}
