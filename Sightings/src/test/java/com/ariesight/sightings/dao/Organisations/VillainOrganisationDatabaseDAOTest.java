package com.ariesight.sightings.dao.Organisations;

import com.ariesight.sightings.dao.Characters.VillainDAO;
import com.ariesight.sightings.dto.Characters.SCharacter;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Organisations.Organisation;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
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
public class VillainOrganisationDatabaseDAOTest {

    @Autowired
    VillainOrganisationDAO organisationDAO;

    @Autowired
    VillainDAO characterDAO;

    public VillainOrganisationDatabaseDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        for (VillainOrganisation organisation : organisationDAO.getAllOrganisations()) {
            organisationDAO.deleteOrganisationById(organisation.getId());
        }

        for (SCharacter character : characterDAO.getAllVillains()) {
            characterDAO.deleteVillainById(character.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetOrganisation() {

        VillainOrganisation organisation = new VillainOrganisation();
        organisation.setName("Test Name");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation);

        assertEquals(organisation, organisationDAO.getOrganisationById(organisation.getId()));
    }

    @Test
    public void testGetAllOrganisations() {

        VillainOrganisation organisation = new VillainOrganisation();
        organisation.setName("Test Name");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation);

        VillainOrganisation organisation2 = new VillainOrganisation();
        organisation2.setName("Test Name");
        organisation2.setDescription("Test Description");
        organisation2.setAddress("Test Address");
        organisation2.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation2);

        List<VillainOrganisation> organisations = organisationDAO.getAllOrganisations();

        assertEquals(2, organisations.size());
        assertTrue(organisations.contains(organisation));
        assertTrue(organisations.contains(organisation2));
    }

    @Test
    public void testUpdateOrganisation() {

        VillainOrganisation organisation = new VillainOrganisation();
        organisation.setName("Test Name");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation);

        Organisation organisationFromDAO = organisationDAO.getOrganisationById(organisation.getId());
        assertEquals(organisation, organisationFromDAO);

        organisation.setName("New Name");
        assertNotEquals(organisationFromDAO, organisation);

        organisationDAO.updateOrganisation(organisation);
        organisationFromDAO = organisationDAO.getOrganisationById(organisation.getId());
        assertEquals(organisation, organisationFromDAO);
    }

    @Test
    public void testDeleteOrganisationByID() {

        VillainOrganisation organisation = new VillainOrganisation();
        organisation.setName("Test Name");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation);

        Organisation organisationFromDAO = organisationDAO.getOrganisationById(organisation.getId());
        assertEquals(organisation, organisationFromDAO);

        organisationDAO.deleteOrganisationById(organisationFromDAO.getId());

        organisationFromDAO = organisationDAO.getOrganisationById(organisation.getId());
        assertNull(organisationFromDAO);

    }

    @Test
    public void testGetOrganisationsByVillainAndAddAffiliations() {
        Villain villain1 = new Villain();
        villain1.setName("Test Villain 1");
        villain1.setDescription("Test Description 1");
        villain1.setSuperpower("Test Superpower 1");
        characterDAO.addVillain(villain1);

        Villain villain2 = new Villain();
        villain2.setName("Test Villain 2");
        villain2.setDescription("Test Description 2");
        villain2.setSuperpower("Test Superpower 2");
        characterDAO.addVillain(villain2);

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
        organisationDAO.addAffiliation(organisation2, villain1);
        organisationDAO.addAffiliation(organisation2, villain2);

        List<VillainOrganisation> organisations = organisationDAO.getOrganisationsByVillain(villain1);

        assertEquals(2, organisations.size());
        assertTrue(organisations.contains(organisation));
        assertTrue(organisations.contains(organisation2));
    }
}
