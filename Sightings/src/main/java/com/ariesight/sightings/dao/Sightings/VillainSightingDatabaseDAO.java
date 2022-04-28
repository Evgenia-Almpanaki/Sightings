package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dao.Characters.VillainDAO;
import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Characters.SCharacter;
import com.ariesight.sightings.dto.Sightings.VillainSighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VillainSightingDatabaseDAO implements VillainSightingDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    VillainDAO characterDAO;

    @Autowired
    LocationDAO locationDAO;

    /**
     * Returns the sighting with the given id.
     *
     * @param id The id of the sighting
     * @return The sighting
     */
    @Override
    public VillainSighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM VillainSightings "
                    + "join Villains on Villains.id = VillainSightings.characterID "
                    + "join Locations on Locations.id = VillainSightings.locationID "
                    + "WHERE VillainSightings.id = ?";
            return jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /**
     * Returns all the villain sightings.
     *
     * @return A list of sightings.
     */
    @Override
    public List<VillainSighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT VillainSightings.id, characterID, locationID, date, Villains.name , Locations.name FROM VillainSightings "
                + "join Locations on Locations.id = VillainSightings.locationID "
                + "join Villains on Villains.id = VillainSightings.characterID "
                + "order by date desc";
        return jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
    }

    /**
     * Adds a new sighting.
     *
     * @param sighting The sighting to be added
     * @return The sighting with the updated id.
     */
    @Override
    public VillainSighting addSighting(VillainSighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO VillainSightings(characterID, locationID, date) VALUES(?,?,?)";

        SCharacter character = characterDAO.getVillainByName(sighting.getCharacterName());
        Location location = locationDAO.getLocationByName(sighting.getLocationName());
        jdbc.update(INSERT_SIGHTING,
                character.getId(),
                location.getId(),
                sighting.getDate());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    /**
     * Updates a sighting.
     *
     * @param sighting The sighting to be updated
     */
    @Override
    public void updateSighting(VillainSighting sighting) {
        final String UPDATE_LOCATION = "UPDATE VillainSightings SET characterID = ?, locationID = ?, date = ? WHERE id = ?";
        int characterID = characterDAO.getVillainByName(sighting.getCharacterName()).getId();
        int locationID = locationDAO.getLocationByName(sighting.getLocationName()).getId();
        jdbc.update(UPDATE_LOCATION,
                characterID,
                locationID,
                sighting.getDate(),
                sighting.getId());
    }

    /**
     * Deletes the sighting with the given id.
     *
     * @param id The id of the sighting to be deleted
     */
    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM VillainSightings WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);

    }

    /**
     * Returns all the villain sightings on the given date.
     *
     * @param date The date
     * @return A list of sightings
     */
    @Override
    public List<VillainSighting> getVillainSightingsByDate(String date) {
        final String GET_SIGHTINGS_BY_DATE = "SELECT * \n"
                + "FROM VillainSightings\n"
                + "INNER JOIN Villains ON Villains.id = VillainSightings.characterID\n"
                + "INNER JOIN Locations ON Locations.id = VillainSightings.locationID\n"
                + "WHERE date = ?";
        return jdbc.query(GET_SIGHTINGS_BY_DATE, new SightingMapper(), date);
    }

    /**
     * Class that maps VillainSighting objects
     */
    public static final class SightingMapper implements RowMapper<VillainSighting> {

        @Override
        public VillainSighting mapRow(ResultSet rs, int index) throws SQLException {
            VillainSighting sighting = new VillainSighting();
            sighting.setId(rs.getInt("id"));
            sighting.setCharacterID(rs.getInt("characterID"));
            sighting.setCharacterName(rs.getString("Villains.name"));
            sighting.setLocationID(rs.getInt("locationID"));
            sighting.setLocationName(rs.getString("Locations.name"));
            sighting.setDate(rs.getString("date"));
            return sighting;
        }
    }
}
