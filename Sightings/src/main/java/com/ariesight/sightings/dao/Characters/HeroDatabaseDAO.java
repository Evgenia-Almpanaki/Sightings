package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public Hero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM Superheroes WHERE id = ?";
            return jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM Superheroes";
        return jdbc.query(GET_ALL_HEROES, new HeroMapper());
    }

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
        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE Superheroes SET name = ?, description = ?, superpower = ? WHERE id = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpower(),
                hero.getId());
    }

    @Override
    public void deleteHeroById(int id) {
        final String DELETE_HERO = "DELETE FROM Superheroes WHERE id = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public Hero getHeroByName(String heroName) {
        try {
            final String GET_HERO_BY_NAME = "SELECT * FROM Superheroes WHERE name = ?";
            return jdbc.queryForObject(GET_HERO_BY_NAME, new HeroMapper(), heroName);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getHeroesByLocation(Location location) {
        final String GET_ALL_HEROES_BY_LOCATION = "SELECT Superheroes.id as \"id\", Superheroes.name as \"name\", Superheroes.description as \"description\", superpower \n" +
                                      "FROM Superheroes\n" +
                                      "INNER JOIN SuperheroSightings ON SuperheroSightings.characterID = Superheroes.id\n" +
                                      "INNER JOIN Locations ON SuperheroSightings.locationID = Locations.id\n" +
                                      "WHERE Locations.id = ?";
        return jdbc.query(GET_ALL_HEROES_BY_LOCATION, new HeroMapper(), location.getId());
    }

    @Override
    public List<Hero> getHeroesByOrganisation(HeroOrganisation organisation) {
        final String GET_ALL_HEROES_BY_ORGANISATION = "SELECT Superheroes.id as \"id\", Superheroes.name as \"name\", Superheroes.description as \"description\", superpower \n" +
                                                      "FROM Superheroes\n" +
                                                      "INNER JOIN SuperheroAffiliations ON SuperheroAffiliations.heroID = Superheroes.id\n" +
                                                      "INNER JOIN SuperheroOrganisations ON SuperheroAffiliations.organisationID = SuperheroOrganisations.id\n" +
                                                      "WHERE SuperheroOrganisations.id = ?";
        
        return jdbc.query(GET_ALL_HEROES_BY_ORGANISATION, new HeroMapper(), organisation.getId());
    }

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