package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.SCharacter;
import com.ariesight.sightings.dto.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SightingDatabaseDAO implements SightingDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    CharacterDAO characterDAO;

    @Autowired
    LocationDAO locationDAO;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM Sightings "
                    + "join Characters on Characters.id = Sightings.characterID "
                    + "join Locations on Locations.id = Sightings.locationID "
                    + "WHERE Sightings.id = ?";
            return jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT Sightings.id, characterID, locationID, date, Characters.name , Locations.name FROM Sightings "
                + "join Locations on Locations.id = Sightings.locationID "
                + "join Characters on Characters.id = Sightings.characterID";
        return jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO Sightings(characterID, locationID, date) VALUES(?,?,?)";

        SCharacter character = characterDAO.getCharacterByName(sighting.getCharacterName());
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
    public void updateSighting(Sighting sighting) {
        final String UPDATE_LOCATION = "UPDATE Sightings SET characterID = ?, locationID = ?, date = ? WHERE id = ?";
        int characterID = characterDAO.getCharacterByName(sighting.getCharacterName()).getId();
        int locationID = locationDAO.getLocationByName(sighting.getLocationName()).getId();
        jdbc.update(UPDATE_LOCATION,
                characterID,
                locationID,
                sighting.getDate(),
                sighting.getId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM Sightings WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);

    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setCharacterID(rs.getInt("characterID"));
            sighting.setCharacterName(rs.getString("Characters.name"));
            sighting.setLocationID(rs.getInt("locationID"));
            sighting.setLocationName(rs.getString("Locations.name"));
            sighting.setDate(rs.getString("date"));
            return sighting;
        }
    }
}
