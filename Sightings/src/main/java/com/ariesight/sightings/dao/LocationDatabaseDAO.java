package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
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
public class LocationDatabaseDAO implements LocationDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM Locations WHERE id = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM Locations";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {

        final String INSERT_LOCATION = "INSERT INTO Locations(name, description, address, longitude, latitude) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLongitude(),
                location.getLatitude());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Locations SET name = ?, description = ?, address = ?, longitude = ?, latitude = ? WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLongitude(),
                location.getLatitude(),
                location.getId());
    }

    @Override
    public void deleteLocationById(int id) {
        final String DELETE_LOCATION = "DELETE FROM Locations WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public Location getLocationByName(String locationName) {
        try {
            final String GET_LOCATION_BY_NAME = "SELECT * FROM Locations WHERE name = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_NAME, new LocationMapper(), locationName);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getLocationsByHero(Hero hero) {
        final String GET_LOCATIONS_BY_CHARACTER = "SELECT Locations.id as \"id\", Locations.name as \"name\", Locations.description as \"description\", address, latitude, longitude\n" +
                                        "FROM Superheroes\n" +
                                        "INNER JOIN SuperheroSightings ON SuperheroSightings.characterID = Superheroes.id\n" +
                                        "INNER JOIN Locations ON SuperheroSightings.locationID = Locations.id\n" +
                                        "WHERE Superheroes.id = ?";
        return jdbc.query(GET_LOCATIONS_BY_CHARACTER, new LocationMapper(), hero.getId());
    }

    @Override
    public List<Location> getLocationsByVillain(Villain villain) {
        final String GET_LOCATIONS_BY_CHARACTER = "SELECT Locations.id as \"id\", Locations.name as \"name\", Locations.description as \"description\", address, latitude, longitude\n" +
                                                "FROM Villains\n" +
                                                "INNER JOIN VillainSightings ON VillainSightings.characterID = Villains.id\n" +
                                                "INNER JOIN Locations ON VillainSightings.locationID = Locations.id\n" +
                                                "WHERE Villains.id = ?";
        return jdbc.query(GET_LOCATIONS_BY_CHARACTER, new LocationMapper(), villain.getId());
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLongitude(rs.getString("longitude"));
            location.setLatitude(rs.getString("latitude"));
            return location;
        }
    }

}
