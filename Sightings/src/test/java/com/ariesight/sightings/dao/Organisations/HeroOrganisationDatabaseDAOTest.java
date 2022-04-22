package com.ariesight.sightings.dao.Organisations;

import com.ariesight.sightings.dao.Characters.HeroDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.SCharacter;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import com.ariesight.sightings.dto.Organisations.Organisation;
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
public class HeroOrganisationDatabaseDAOTest {

    @Autowired
    HeroOrganisationDAO organisationDAO;

    @Autowired
    HeroDAO characterDAO;

    public HeroOrganisationDatabaseDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        for (Organisation organisation : organisationDAO.getAllOrganisations()) {
            organisationDAO.deleteOrganisationById(organisation.getId());
        }
        for (SCharacter character : characterDAO.getAllHeroes()) {
            characterDAO.deleteHeroById(character.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetOrganisation() {

        HeroOrganisation organisation = new HeroOrganisation();
        organisation.setName("Test Name");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation);

        assertEquals(organisation, organisationDAO.getOrganisationById(organisation.getId()));
    }

    @Test
    public void testGetAllOrganisations() {

        HeroOrganisation organisation = new HeroOrganisation();
        organisation.setName("Test Name");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation);

        HeroOrganisation organisation2 = new HeroOrganisation();
        organisation2.setName("Test Name");
        organisation2.setDescription("Test Description");
        organisation2.setAddress("Test Address");
        organisation2.setContact("Test Contact");
        organisationDAO.addOrganisation(organisation2);

        List<HeroOrganisation> organisations = organisationDAO.getAllOrganisations();

        assertEquals(2, organisations.size());
        assertTrue(organisations.contains(organisation));
        assertTrue(organisations.contains(organisation2));
    }

    @Test
    public void testUpdateOrganisation() {

        HeroOrganisation organisation = new HeroOrganisation();
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

        HeroOrganisation organisation = new HeroOrganisation();
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
    public void testGetOrganisationsByHeroAndAddAffiliations() {
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
        organisationDAO.addAffiliation(organisation2, hero1);
        organisationDAO.addAffiliation(organisation2, hero2);

        List<HeroOrganisation> organisations = organisationDAO.getOrganisationsByHero(hero1);

        assertEquals(2, organisations.size());
        assertTrue(organisations.contains(organisation));
        assertTrue(organisations.contains(organisation2));
    }
}
