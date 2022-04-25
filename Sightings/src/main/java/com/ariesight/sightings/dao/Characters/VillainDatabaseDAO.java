package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
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
public class VillainDatabaseDAO implements VillainDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Villain getVillainById(int id) {
        try {
            final String GET_VILLAIN_BY_ID = "SELECT * FROM Villains WHERE id = ?";
            return jdbc.queryForObject(GET_VILLAIN_BY_ID, new VillainMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Villain> getAllVillains() {
        final String GET_ALL_VILLAINS = "SELECT * FROM Villains order by Villains.name";
        return jdbc.query(GET_ALL_VILLAINS, new VillainMapper());
    }

    @Override
    @Transactional
    public Villain addVillain(Villain villain) {

        final String INSERT_VILLAIN = "INSERT INTO Villains(name, description, superpower) VALUES(?,?,?)";
        jdbc.update(INSERT_VILLAIN,
                villain.getName(),
                villain.getDescription(),
                villain.getSuperpower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        villain.setId(newId);
        return villain;
    }

    @Override
    public void updateVillain(Villain villain) {
        final String UPDATE_VILLAIN = "UPDATE Villains SET name = ?, description = ?, superpower = ? WHERE id = ?";
        jdbc.update(UPDATE_VILLAIN,
                villain.getName(),
                villain.getDescription(),
                villain.getSuperpower(),
                villain.getId());
    }

    @Override
    public void deleteVillainById(int id) {
        final String DELETE_VILLAIN = "DELETE FROM Villains WHERE id = ?";
        jdbc.update(DELETE_VILLAIN, id);
    }

    @Override
    public Villain getVillainByName(String villainName) {
        try {
            final String GET_VILLAIN_BY_NAME = "SELECT * FROM Villains WHERE name = ?";
            return jdbc.queryForObject(GET_VILLAIN_BY_NAME, new VillainMapper(), villainName);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Villain> getVillainsByLocation(Location location) {
        final String GET_ALL_VILLAINS_BY_LOCATION = "SELECT Villains.id as \"id\", Villains.name as \"name\", Villains.description as \"description\", superpower \n" +
                                      "FROM Villains\n" +
                                      "INNER JOIN VillainSightings ON VillainSightings.characterID = Villains.id\n" +
                                      "INNER JOIN Locations ON VillainSightings.locationID = Locations.id\n" +
                                      "WHERE Locations.id = ?";
        return jdbc.query(GET_ALL_VILLAINS_BY_LOCATION, new VillainMapper(), location.getId());
    }

    @Override
    public List<Villain> getVillainsByOrganisation(VillainOrganisation organisation) {
        final String GET_ALL_VILLAINS_BY_ORGANISATION = "SELECT Villains.id as \"id\", Villains.name as \"name\", Villains.description as \"description\", superpower \n" +
                                                      "FROM Villains\n" +
                                                      "INNER JOIN VillainAffiliations ON VillainAffiliations.villainID = Villains.id\n" +
                                                      "INNER JOIN VillainOrganisations ON VillainAffiliations.villainOrganisationID = VillainOrganisations.id\n" +
                                                      "WHERE VillainOrganisations.id = ?";
        
        return jdbc.query(GET_ALL_VILLAINS_BY_ORGANISATION, new VillainMapper(), organisation.getId());
    }

    public static final class VillainMapper implements RowMapper<Villain> {

        @Override
        public Villain mapRow(ResultSet rs, int index) throws SQLException {
            Villain villain = new Villain();
            villain.setId(rs.getInt("id"));
            villain.setName(rs.getString("name"));
            villain.setDescription(rs.getString("description"));
            villain.setSuperpower(rs.getString("superpower"));
            return villain;
        }
    }
}
