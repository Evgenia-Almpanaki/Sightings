package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dao.Characters.HeroDAO;
import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Sightings.HeroSighting;
import com.ariesight.sightings.dto.Sightings.Sighting;
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
public class HeroSightingDatabaseDAOTest {

    @Autowired
    HeroSightingDAO sightingDAO;

    @Autowired
    HeroDAO characterDAO;

    @Autowired
    LocationDAO locationDAO;

    public HeroSightingDatabaseDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        for (HeroSighting sighting : sightingDAO.getAllSightings()) {
            sightingDAO.deleteSightingById(sighting.getId());
        }
        for (Location location : locationDAO.getAllLocations()) {
            locationDAO.deleteLocationById(location.getId());
        }
        for (Hero hero : characterDAO.getAllHeroes()) {
            characterDAO.deleteHeroById(hero.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetSighting() {

        Hero character = new Hero();
        character.setName("Test Character Name");
        character.setDescription("Test Description");
        character.setSuperpower("Test Superpower");
        characterDAO.addHero(character);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        HeroSighting sighting = new HeroSighting();
        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate("2020-10-02");
        sightingDAO.addSighting(sighting);

        Sighting sightingFromDAO = sightingDAO.getSightingById(sighting.getId());
        assertEquals(sighting, sightingFromDAO);
    }
    
    @Test
    public void testGetAllSightings() {

        Hero character = new Hero();
        character.setName("Test Character Name");
        character.setDescription("Test Description");
        character.setSuperpower("Test Superpower");
        characterDAO.addHero(character);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        HeroSighting sighting = new HeroSighting();
        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate("2020-10-02");
        sightingDAO.addSighting(sighting);
        
        Hero character2 = new Hero();
        character2.setName("Test Character Name 2");
        character2.setDescription("Test Description 2");
        character2.setSuperpower("Test Superpower 2");
        characterDAO.addHero(character2);

        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("Test Address 2");
        location2.setLatitude("Test Latitude 2");
        location2.setLongitude("Test Longitude 2");
        locationDAO.addLocation(location2);

        HeroSighting sighting2 = new HeroSighting();
        sighting2.setCharacterID(character2.getId());
        sighting2.setCharacterName(character2.getName());
        sighting2.setLocationID(location2.getId());
        sighting2.setLocationName(location2.getName());
        sighting2.setDate("2021-11-03");
        sightingDAO.addSighting(sighting2);

        List<HeroSighting> sightings = sightingDAO.getAllSightings();

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {

        Hero character = new Hero();
        character.setName("Test Character Name");
        character.setDescription("Test Description");
        character.setSuperpower("Test Superpower");
        characterDAO.addHero(character);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        HeroSighting sighting = new HeroSighting();
        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate("2020-10-02");
        sightingDAO.addSighting(sighting);

        Sighting sightingFromDAO = sightingDAO.getSightingById(sighting.getId());
        assertEquals(sighting, sightingFromDAO);

        sighting.setDate("2021-11-02");
        assertNotEquals(sighting, sightingFromDAO);

        sightingDAO.updateSighting(sighting);
        sightingFromDAO = sightingDAO.getSightingById(sighting.getId());
        assertEquals(sighting, sightingFromDAO);
    }

    @Test
    public void testDeleteSightingByID() {

        Hero character = new Hero();
        character.setName("Test Character Name");
        character.setDescription("Test Description");
        character.setSuperpower("Test Superpower");
        characterDAO.addHero(character);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        HeroSighting sighting = new HeroSighting();
        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate("2020-10-02");
        sightingDAO.addSighting(sighting);

        Sighting sightingFromDAO = sightingDAO.getSightingById(sighting.getId());
        assertEquals(sighting, sightingFromDAO);

        sightingDAO.deleteSightingById(sighting.getId());

        sightingFromDAO = sightingDAO.getSightingById(sighting.getId());
        assertNull(sightingFromDAO);
    }
    
    @Test
    public void testGetHeroSightingsByDate(){
        
        Hero hero1 = new Hero();
        hero1.setName("Test Hero 1");
        hero1.setDescription("Test Description 1");
        hero1.setSuperpower("Test Superpower 1");
        characterDAO.addHero(hero1);
        
        Hero hero2 = new Hero();
        hero2.setName("Test Hero 2");
        hero2.setDescription("Test Description 2");
        hero2.setSuperpower("Test Superpower 2");
        characterDAO.addHero(hero2);
        
        Location location1 = new Location();
        location1.setName("Test Name");
        location1.setDescription("Test Description");
        location1.setAddress("Test Address");
        location1.setLatitude("Test Latitude");
        location1.setLongitude("Test Longitude");
        locationDAO.addLocation(location1);
        
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
        sighting.setLocationID(location1.getId());
        sighting.setLocationName(location1.getName());
        sighting.setDate("2020-10-20");
        sightingDAO.addSighting(sighting);
        
        HeroSighting sighting2 = new HeroSighting();
        sighting2.setCharacterID(hero2.getId());
        sighting2.setCharacterName(hero2.getName());
        sighting2.setLocationID(location2.getId());
        sighting2.setLocationName(location2.getName());
        sighting2.setDate("2020-10-20");
        sightingDAO.addSighting(sighting2);
        
        HeroSighting sighting3 = new HeroSighting();
        sighting3.setCharacterID(hero1.getId());
        sighting3.setCharacterName(hero1.getName());
        sighting3.setLocationID(location2.getId());
        sighting3.setLocationName(location2.getName());
        sighting3.setDate("2022-10-20");
        sightingDAO.addSighting(sighting3);
      
        List<HeroSighting> sightings = sightingDAO.getHeroSightingsByDate("2020-10-20");

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }
}
