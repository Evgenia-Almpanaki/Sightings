package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dao.Organisations.HeroOrganisationDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import com.ariesight.sightings.dto.Organisations.Organisation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HeroDatabaseDAO implements HeroDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    HeroOrganisationDAO organisationDAO;

    /**
     * Returns the hero with the given id.
     *
     * @param id The id of the hero
     * @return A Hero object
     */
    @Override
    public Hero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM Superheroes WHERE id = ?";
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
            List<Organisation> organisations = new ArrayList<>();
            for (HeroOrganisation org : organisationDAO.getOrganisationsByHero(hero)) {
                organisations.add(org);
            }
            hero.setOrganisations(organisations);
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /**
     * Returns a list with all the heroes.
     *
     * @return A list of heroes
     */
    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM Superheroes order by Superheroes.name";
        List<Hero> heroes = jdbc.query(GET_ALL_HEROES, new HeroMapper());
        associateOrganisationsWithHeroes(heroes);
        return heroes;
    }

    /**
     * Updates the associations between all the heroes in the list and their
     * organisations.
     *
     * @param heroes The list of heroes to be associated
     */
    private void associateOrganisationsWithHeroes(List<Hero> heroes) {
        for (Hero hero : heroes) {
            List<Organisation> organisations = new ArrayList<>();
            for (HeroOrganisation org : organisationDAO.getOrganisationsByHero(hero)) {
                organisations.add(org);
            }
            hero.setOrganisations(organisations);
        }
    }

    /**
     * Adds a new hero.
     *
     * @param hero The hero to be added
     * @return A Hero object
     */
    @Override
    @Transactional
    public Hero addHero(Hero hero) {

        final String INSERT_HERO = "INSERT INTO Superheroes(name, description, superpower) VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        insertAffiliations(hero);
        return hero;
    }

    /**
     * Adds the associations of a hero in the database.
     *
     * @param hero The hero
     */
    private void insertAffiliations(Hero hero) {

        final String INSERT_AFFILIATION = "INSERT INTO SuperheroAffiliations(heroID, organisationID) VALUES(?,?)";

        for (Organisation organisation : hero.getOrganisations()) {
            jdbc.update(INSERT_AFFILIATION, hero.getId(), organisation.getId());
        }
    }

    /**
     * Updates a hero.
     *
     * @param hero The hero to be updated
     */
    @Override
    @Transactional
    public void updateHero(Hero hero) {

        final String DELETE_OLD_AFFILIATIONS = "DELETE FROM SuperheroAffiliations WHERE heroID = ?";
        jdbc.update(DELETE_OLD_AFFILIATIONS, hero.getId());

        String CREATE_AFFILIATION;
        for (Organisation organisation : hero.getOrganisations()) {
            CREATE_AFFILIATION = "INSERT INTO SuperheroAffiliations(heroID, organisationID) VALUES (?, ?)";
            jdbc.update(CREATE_AFFILIATION, hero.getId(), organisation.getId());
        }
        final String UPDATE_HERO = "UPDATE Superheroes SET name = ?, description = ?, superpower = ? WHERE id = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpower(),
                hero.getId());
    }

    /**
     * Deletes the hero with the given id.
     *
     * @param id The id of the hero
     */
    @Override
    public void deleteHeroById(int id) {
        final String DELETE_HERO = "DELETE FROM Superheroes WHERE id = ?";
        jdbc.update(DELETE_HERO, id);
    }

    /**
     * Returns the hero with the given name.
     *
     * @param heroName The name of the character
     * @return The hero with the given name
     */
    @Override
    public Hero getHeroByName(String heroName) {
        try {
            final String GET_HERO_BY_NAME = "SELECT * FROM Superheroes WHERE name = ?";
            return jdbc.queryForObject(GET_HERO_BY_NAME, new HeroMapper(), heroName);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /**
     * Returns all the heroes seen in the given location.
     *
     * @param location The location
     * @return A list of heroes
     */
    @Override
    public List<Hero> getHeroesByLocation(Location location) {
        final String GET_ALL_HEROES_BY_LOCATION = "SELECT Superheroes.id as \"id\", Superheroes.name as \"name\", Superheroes.description as \"description\", superpower \n"
                + "FROM Superheroes\n"
                + "INNER JOIN SuperheroSightings ON SuperheroSightings.characterID = Superheroes.id\n"
                + "INNER JOIN Locations ON SuperheroSightings.locationID = Locations.id\n"
                + "WHERE Locations.id = ?";
        return jdbc.query(GET_ALL_HEROES_BY_LOCATION, new HeroMapper(), location.getId());
    }

    /**
     * Returns all the heroes in a given organisation.
     *
     * @param organisation The organiastion
     * @return A list of heroes
     */
    @Override
    public List<Hero> getHeroesByOrganisation(HeroOrganisation organisation) {
        final String GET_ALL_HEROES_BY_ORGANISATION = "SELECT Superheroes.id as \"id\", Superheroes.name as \"name\", Superheroes.description as \"description\", superpower \n"
                + "FROM Superheroes\n"
                + "INNER JOIN SuperheroAffiliations ON SuperheroAffiliations.heroID = Superheroes.id\n"
                + "INNER JOIN SuperheroOrganisations ON SuperheroAffiliations.organisationID = SuperheroOrganisations.id\n"
                + "WHERE SuperheroOrganisations.id = ?";

        return jdbc.query(GET_ALL_HEROES_BY_ORGANISATION, new HeroMapper(), organisation.getId());
    }

    /**
     * Class to map Hero objects
     */
    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setSuperpower(rs.getString("superpower"));
            return hero;
        }
    }
}
