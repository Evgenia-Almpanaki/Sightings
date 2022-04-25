package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dao.Characters.HeroDAO;
import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Characters.SCharacter;
import com.ariesight.sightings.dto.Sightings.HeroSighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class HeroSightingDatabaseDAO implements HeroSightingDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    HeroDAO characterDAO;

    @Autowired
    LocationDAO locationDAO;

    @Override
    public HeroSighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM SuperheroSightings "
                    + "join Superheroes on Superheroes.id = SuperheroSightings.characterID "
                    + "join Locations on Locations.id = SuperheroSightings.locationID "
                    + "WHERE SuperheroSightings.id = ?";
            return jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<HeroSighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT SuperheroSightings.id, characterID, locationID, date, Superheroes.name , Locations.name FROM SuperheroSightings "
                + "join Locations on Locations.id = SuperheroSightings.locationID "
                + "join Superheroes on Superheroes.id = SuperheroSightings.characterID "
                + "order by date desc";
        return jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
    }

    @Override
    public HeroSighting addSighting(HeroSighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO SuperheroSightings(characterID, locationID, date) VALUES(?,?,?)";

        SCharacter character = characterDAO.getHeroByName(sighting.getCharacterName());
        Location location = locationDAO.getLocationByName(sighting.getLocationName());
        jdbc.update(INSERT_SIGHTING,
                character.getId(),
                location.getId(),
                sighting.getDate());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(HeroSighting sighting) {
        final String UPDATE_LOCATION = "UPDATE SuperheroSightings SET characterID = ?, locationID = ?, date = ? WHERE id = ?";
        int characterID = characterDAO.getHeroByName(sighting.getCharacterName()).getId();
        int locationID = locationDAO.getLocationByName(sighting.getLocationName()).getId();
        jdbc.update(UPDATE_LOCATION,
                characterID,
                locationID,
                sighting.getDate(),
                sighting.getId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM SuperheroSightings WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);

    }

    @Override
    public List<HeroSighting> getHeroSightingsByDate(String date) {
        final String GET_SIGHTINGS_BY_DATE = "SELECT * \n" +
                                            "FROM SuperheroSightings\n" +
                                            "INNER JOIN Superheroes ON Superheroes.id = SuperheroSightings.characterID\n" +
                                            "INNER JOIN Locations ON Locations.id = SuperheroSightings.locationID\n" +
                                            "WHERE date = ?";
        return jdbc.query(GET_SIGHTINGS_BY_DATE, new SightingMapper(), date);
    }

    public static final class SightingMapper implements RowMapper<HeroSighting> {

        @Override
        public HeroSighting mapRow(ResultSet rs, int index) throws SQLException {
            HeroSighting sighting = new HeroSighting();
            sighting.setId(rs.getInt("SuperheroSightings.id"));
            sighting.setCharacterID(rs.getInt("characterID"));
            sighting.setCharacterName(rs.getString("Superheroes.name"));
            sighting.setLocationID(rs.getInt("locationID"));
            sighting.setLocationName(rs.getString("Locations.name"));
            sighting.setDate(rs.getString("date"));
            return sighting;
        }
    }
}
