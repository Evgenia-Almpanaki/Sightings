package com.ariesight.sightings.dao;

import com.ariesight.sightings.dao.Characters.HeroDAO;
import com.ariesight.sightings.dao.Characters.VillainDAO;
import com.ariesight.sightings.dao.Sightings.HeroSightingDAO;
import com.ariesight.sightings.dao.Sightings.VillainSightingDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Sightings.HeroSighting;
import com.ariesight.sightings.dto.Sightings.VillainSighting;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationDatabaseDAOTest {

    @Autowired
    LocationDAO locationDAO;
    
    @Autowired
    HeroDAO heroDAO;
    
    @Autowired
    VillainDAO villainDAO;

    @Autowired
    HeroSightingDAO heroSightingDAO;
    
    @Autowired
    VillainSightingDAO villainSightingDAO;
    
    public LocationDatabaseDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        for (Location location : locationDAO.getAllLocations()) {
            locationDAO.deleteLocationById(location.getId());
        }
        for (Hero hero : heroDAO.getAllHeroes()) {
            heroDAO.deleteHeroById(hero.getId());
        }
        for (Villain villain : villainDAO.getAllVillains()) {
            villainDAO.deleteVillainById(villain.getId());
        }
        for (HeroSighting sighting : heroSightingDAO.getAllSightings()) {
            heroSightingDAO.deleteSightingById(sighting.getId());
        }
        for (VillainSighting sighting : villainSightingDAO.getAllSightings()) {
            villainSightingDAO.deleteSightingById(sighting.getId());
        }
        
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");

        locationDAO.addLocation(location);
        assertEquals(location, locationDAO.getLocationById(location.getId()));
    }

    @Test
    public void testGetAllLocations() {

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

        List<Location> locations = locationDAO.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        Location locationFromDAO = locationDAO.getLocationById(location.getId());
        assertEquals(location, locationFromDAO);

        location.setName("New Name");
        assertNotEquals(location, locationFromDAO);

        locationDAO.updateLocation(location);
        locationFromDAO = locationDAO.getLocationById(location.getId());
        assertEquals(location, locationFromDAO);
    }

    @Test
    public void testDeleteLocationByID() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        Location locationFromDAO = locationDAO.getLocationById(location.getId());
        assertEquals(locationFromDAO, location);

        locationDAO.deleteLocationById(location.getId());

        locationFromDAO = locationDAO.getLocationById(location.getId());
        assertNull(locationFromDAO);

    }

    @Test
    public void testGetLocationByName() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        Location locationFromDAO = locationDAO.getLocationByName(location.getName());
        assertEquals(locationFromDAO, location);
    }
    
    @Test
    public void testGetLocationsByHero(){
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
        
        Location location3 = new Location();
        location3.setName("Test Name 3");
        location3.setDescription("Test Description 3");
        location3.setAddress("Test Address 3");
        location3.setLatitude("Test Latitude 3");
        location3.setLongitude("Test Longitude 3");
        locationDAO.addLocation(location3);

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
        sighting3.setLocationID(location3.getId());
        sighting3.setLocationName(location3.getName());
        sighting3.setDate("2022-02-22");
        heroSightingDAO.addSighting(sighting3);
        
        Hero heroFromDAO = heroDAO.getHeroById(hero1.getId());
        List<Location> locations = locationDAO.getLocationsByHero(heroFromDAO);
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }
    
        @Test
    public void testGetLocationsByVillain(){
        Villain villain1 = new Villain();
        villain1.setName("Test Villain 1");
        villain1.setDescription("Test Description 1");
        villain1.setSuperpower("Test Superpower 1");
        villainDAO.addVillain(villain1);
        
        Villain villain2 = new Villain();
        villain2.setName("Test Villain 2");
        villain2.setDescription("Test Description 2");
        villain2.setSuperpower("Test Superpower 2");
        villainDAO.addVillain(villain2);
        
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
        
        Location location3 = new Location();
        location3.setName("Test Name 3");
        location3.setDescription("Test Description 3");
        location3.setAddress("Test Address 3");
        location3.setLatitude("Test Latitude 3");
        location3.setLongitude("Test Longitude 3");
        locationDAO.addLocation(location3);

        VillainSighting sighting = new VillainSighting();
        sighting.setCharacterID(villain1.getId());
        sighting.setCharacterName(villain1.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate("2020-10-20");
        villainSightingDAO.addSighting(sighting);
        
        VillainSighting sighting2 = new VillainSighting();
        sighting2.setCharacterID(villain1.getId());
        sighting2.setCharacterName(villain1.getName());
        sighting2.setLocationID(location2.getId());
        sighting2.setLocationName(location2.getName());
        sighting2.setDate("2021-01-21");
        villainSightingDAO.addSighting(sighting2);
        
        VillainSighting sighting3 = new VillainSighting();
        sighting3.setCharacterID(villain2.getId());
        sighting3.setCharacterName(villain2.getName());
        sighting3.setLocationID(location3.getId());
        sighting3.setLocationName(location3.getName());
        sighting3.setDate("2022-02-22");
        villainSightingDAO.addSighting(sighting3);
        
        Villain villainFromDAO = villainDAO.getVillainById(villain1.getId());
        List<Location> locations = locationDAO.getLocationsByVillain(villainFromDAO);
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }
}
