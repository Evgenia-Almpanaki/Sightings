package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dao.Organisations.VillainOrganisationDAO;
import com.ariesight.sightings.dao.Sightings.VillainSightingDAO;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.Organisation;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
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
public class VillainDatabaseDAOTest {

    @Autowired
    VillainDAO villainDAO;
    @Autowired
    LocationDAO locationDAO;
    @Autowired
    VillainSightingDAO villainSightingDAO;
    @Autowired
    VillainOrganisationDAO organisationDAO;

    public VillainDatabaseDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        for (Villain villain : villainDAO.getAllVillains()) {
            villainDAO.deleteVillainById(villain.getId());
        }
        for (Location location : locationDAO.getAllLocations()) {
            locationDAO.deleteLocationById(location.getId());
        }
        for (VillainSighting sighting : villainSightingDAO.getAllSightings()) {
            villainSightingDAO.deleteSightingById(sighting.getId());
        }
        for (Organisation organisation : organisationDAO.getAllOrganisations()) {
            organisationDAO.deleteOrganisationById(organisation.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetVillain() {
        Villain villain = new Villain();
        villain.setName("Test Name");
        villain.setDescription("Test Description");
        villain.setSuperpower("Test Superpower");

        villainDAO.addVillain(villain);
        assertEquals(villain, villainDAO.getVillainById(villain.getId()));
    }

    @Test
    public void testGetAllVillains() {

        Villain villain = new Villain();
        villain.setName("Test Name");
        villain.setDescription("Test Description");
        villain.setSuperpower("Test Superpower");
        villainDAO.addVillain(villain);

        Villain villain2 = new Villain();
        villain2.setName("Test Name 2");
        villain2.setDescription("Test Description 2");
        villain2.setSuperpower("Test Superpower 2");
        villainDAO.addVillain(villain2);

        List<Villain> villains = villainDAO.getAllVillains();

        assertEquals(2, villains.size());
        assertTrue(villains.contains(villain));
        assertTrue(villains.contains(villain2));
    }

    @Test
    public void testUpdateVillain() {

        Villain villain = new Villain();
        villain.setName("Test Name");
        villain.setDescription("Test Description");
        villain.setSuperpower("Test Superpower");
        villainDAO.addVillain(villain);

        Villain villainFromDAO = villainDAO.getVillainById(villain.getId());
        assertEquals(villain, villainFromDAO);

        villain.setName("New Name");
        assertNotEquals(villainFromDAO, villain);

        villainDAO.updateVillain(villain);
        villainFromDAO = villainDAO.getVillainById(villain.getId());
        assertEquals(villain, villainFromDAO);
    }

    @Test
    public void testDeleteVillainByID() {

        Villain villain = new Villain();
        villain.setName("Test Name");
        villain.setDescription("Test Description");
        villain.setSuperpower("Test Superpower");
        villainDAO.addVillain(villain);

        Villain villainFromDAO = villainDAO.getVillainById(villain.getId());
        assertEquals(villain, villainFromDAO);

        villainDAO.deleteVillainById(villainFromDAO.getId());

        villainFromDAO = villainDAO.getVillainById(villainFromDAO.getId());
        assertNull(villainFromDAO);

    }

    @Test
    public void testGetVillainByName() {

        Villain villain = new Villain();
        villain.setName("Test Name");
        villain.setDescription("Test Description");
        villain.setSuperpower("Test Superpower");

        villainDAO.addVillain(villain);
        assertEquals(villain, villainDAO.getVillainByName(villain.getName()));

    }

    @Test
    public void testGetVillainsByLocation() {

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

        Villain villain3 = new Villain();
        villain3.setName("Test Villain 3");
        villain3.setDescription("Test Description 3");
        villain3.setSuperpower("Test Superpower 3");
        villainDAO.addVillain(villain3);

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
        sighting3.setLocationID(location2.getId());
        sighting3.setLocationName(location2.getName());
        sighting3.setDate("2022-02-22");
        villainSightingDAO.addSighting(sighting3);

        List<Villain> villains = villainDAO.getVillainsByLocation(location2);

        assertEquals(2, villains.size());
        assertTrue(villains.contains(villain1));
        assertTrue(villains.contains(villain2));
    }

    @Test
    public void testGetVillainsByOrganisation() {
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

        VillainOrganisation organisation = new VillainOrganisation();
        organisation.setName("Test Name");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation);

        VillainOrganisation organisation2 = new VillainOrganisation();
        organisation2.setName("Test Name 2");
        organisation2.setDescription("Test Description 2");
        organisation2.setAddress("Test Address 2");
        organisation2.setContact("Test Contact 2");
        organisationDAO.addOrganisation(organisation2);

        organisationDAO.addAffiliation(organisation, villain1);
        organisationDAO.addAffiliation(organisation, villain2);
        organisationDAO.addAffiliation(organisation2, villain2);

        List<Villain> villains = villainDAO.getVillainsByOrganisation(organisation);

        assertEquals(2, villains.size());
        assertTrue(villains.contains(villain1));
        assertTrue(villains.contains(villain2));
    }

}
