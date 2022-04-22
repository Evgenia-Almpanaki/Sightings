package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dao.Characters.VillainDAO;
import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Sightings.Sighting;
import com.ariesight.sightings.dto.Sightings.VillainSighting;
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
public class VillainSightingDatabaseDAOTest {

    @Autowired
    VillainSightingDAO sightingDAO;

    @Autowired
    VillainDAO characterDAO;

    @Autowired
    LocationDAO locationDAO;

    public VillainSightingDatabaseDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        for (VillainSighting sighting : sightingDAO.getAllSightings()) {
            sightingDAO.deleteSightingById(sighting.getId());
        }
        for (Location location : locationDAO.getAllLocations()) {
            locationDAO.deleteLocationById(location.getId());
        }
        for (Villain villain : characterDAO.getAllVillains()) {
            characterDAO.deleteVillainById(villain.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetSighting() {

        Villain character = new Villain();
        character.setName("Test Character Name");
        character.setDescription("Test Description");
        character.setSuperpower("Test Superpower");
        characterDAO.addVillain(character);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        VillainSighting sighting = new VillainSighting();
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

        Villain character = new Villain();
        character.setName("Test Character Name");
        character.setDescription("Test Description");
        character.setSuperpower("Test Superpower");
        characterDAO.addVillain(character);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        VillainSighting sighting = new VillainSighting();
        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate("2020-10-02");
        sightingDAO.addSighting(sighting);

        Villain character2 = new Villain();
        character2.setName("Test Character Name 2");
        character2.setDescription("Test Description 2");
        character2.setSuperpower("Test Superpower 2");
        characterDAO.addVillain(character2);

        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("Test Address 2");
        location2.setLatitude("Test Latitude 2");
        location2.setLongitude("Test Longitude 2");
        locationDAO.addLocation(location2);

        VillainSighting sighting2 = new VillainSighting();
        sighting2.setCharacterID(character2.getId());
        sighting2.setCharacterName(character2.getName());
        sighting2.setLocationID(location2.getId());
        sighting2.setLocationName(location2.getName());
        sighting2.setDate("2021-11-03");
        sightingDAO.addSighting(sighting2);

        List<VillainSighting> sightings = sightingDAO.getAllSightings();

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {

        Villain character = new Villain();
        character.setName("Test Character Name");
        character.setDescription("Test Description");
        character.setSuperpower("Test Superpower");
        characterDAO.addVillain(character);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        VillainSighting sighting = new VillainSighting();
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

        Villain character = new Villain();
        character.setName("Test Character Name");
        character.setDescription("Test Description");
        character.setSuperpower("Test Superpower");
        characterDAO.addVillain(character);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude("Test Latitude");
        location.setLongitude("Test Longitude");
        locationDAO.addLocation(location);

        VillainSighting sighting = new VillainSighting();
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
    public void testGetVillainSightingsByDate(){
        
        Villain villain = new Villain();
        villain.setName("Test Villain 1");
        villain.setDescription("Test Description 1");
        villain.setSuperpower("Test Superpower 1");
        characterDAO.addVillain(villain);
        
        Villain villain2 = new Villain();
        villain2.setName("Test Villain 2");
        villain2.setDescription("Test Description 2");
        villain2.setSuperpower("Test Superpower 2");
        characterDAO.addVillain(villain2);
        
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

        VillainSighting sighting = new VillainSighting();
        sighting.setCharacterID(villain.getId());
        sighting.setCharacterName(villain.getName());
        sighting.setLocationID(location1.getId());
        sighting.setLocationName(location1.getName());
        sighting.setDate("2020-10-20");
        sightingDAO.addSighting(sighting);
        
        VillainSighting sighting2 = new VillainSighting();
        sighting2.setCharacterID(villain2.getId());
        sighting2.setCharacterName(villain2.getName());
        sighting2.setLocationID(location2.getId());
        sighting2.setLocationName(location2.getName());
        sighting2.setDate("2020-10-20");
        sightingDAO.addSighting(sighting2);
        
        VillainSighting sighting3 = new VillainSighting();
        sighting3.setCharacterID(villain.getId());
        sighting3.setCharacterName(villain.getName());
        sighting3.setLocationID(location2.getId());
        sighting3.setLocationName(location2.getName());
        sighting3.setDate("2022-10-20");
        sightingDAO.addSighting(sighting3);
      
        List<VillainSighting> sightings = sightingDAO.getVillainSightingsByDate("2020-10-20");

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }
}
