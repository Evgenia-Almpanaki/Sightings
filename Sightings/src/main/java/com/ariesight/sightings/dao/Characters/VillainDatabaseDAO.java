package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dao.Organisations.VillainOrganisationDAO;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.Organisation;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
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
public class VillainDatabaseDAO implements VillainDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    VillainOrganisationDAO organisationDAO;

    /**
     * Returns the villain with the given id.
     *
     * @param id The id of the villain
     * @return The villain
     */
    @Override
    public Villain getVillainById(int id) {
        try {
            final String GET_VILLAIN_BY_ID = "SELECT * FROM Villains WHERE id = ?";
            Villain villain = jdbc.queryForObject(GET_VILLAIN_BY_ID, new VillainMapper(), id);

            List<Organisation> organisations = new ArrayList<>();
            for (VillainOrganisation org : organisationDAO.getOrganisationsByVillain(villain)) {
                organisations.add(org);
            }
            villain.setOrganisations(organisations);
            return villain;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /**
     * Returns all the villains.
     *
     * @return A list of villains
     */
    @Override
    public List<Villain> getAllVillains() {
        final String GET_ALL_VILLAINS = "SELECT * FROM Villains order by Villains.name";
        List<Villain> villains = jdbc.query(GET_ALL_VILLAINS, new VillainMapper());
        associateOrganisationsWithVillains(villains);
        return villains;
    }

    /**
     * Adds the organisation associations to the villains list.
     *
     * @param villains A list of villains to be updated
     */
    private void associateOrganisationsWithVillains(List<Villain> villains) {
        for (Villain villain : villains) {
            List<Organisation> organisations = new ArrayList<>();
            for (VillainOrganisation org : organisationDAO.getOrganisationsByVillain(villain)) {
                organisations.add(org);
            }
            villain.setOrganisations(organisations);
        }
    }

    /**
     * Adds a villain.
     *
     * @param villain The villain to be added
     * @return The Villain object
     */
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
        insertAffiliations(villain);
        return villain;
    }

    /**
     * Insert the villain-organisations associations in the database.
     *
     * @param villain The villain
     */
    private void insertAffiliations(Villain villain) {

        final String INSERT_AFFILIATION = "INSERT INTO VillainAffiliations(villainID, villainOrganisationID) VALUES(?,?)";

        for (Organisation organisation : villain.getOrganisations()) {
            jdbc.update(INSERT_AFFILIATION, villain.getId(), organisation.getId());
        }

    }

    /**
     * Updates a villain.
     *
     * @param villain The villain to be updated
     */
    @Override
    public void updateVillain(Villain villain) {

        final String DELETE_OLD_AFFILIATIONS = "DELETE FROM VillainAffiliations WHERE villainID = ?";
        jdbc.update(DELETE_OLD_AFFILIATIONS, villain.getId());

        String CREATE_AFFILIATION;
        for (Organisation organisation : villain.getOrganisations()) {
            CREATE_AFFILIATION = "INSERT INTO VillainAffiliations(villainID, villainOrganisationID) VALUES (?, ?)";
            jdbc.update(CREATE_AFFILIATION, villain.getId(), organisation.getId());
        }

        final String UPDATE_VILLAIN = "UPDATE Villains SET name = ?, description = ?, superpower = ? WHERE id = ?";
        jdbc.update(UPDATE_VILLAIN,
                villain.getName(),
                villain.getDescription(),
                villain.getSuperpower(),
                villain.getId());
    }

    /**
     * Deletes a villain.
     *
     * @param id The id of the villain to be deleted
     */
    @Override
    public void deleteVillainById(int id) {
        final String DELETE_VILLAIN = "DELETE FROM Villains WHERE id = ?";
        jdbc.update(DELETE_VILLAIN, id);
    }

    /**
     * Returns the villain with the given name.
     *
     * @param villainName The name of the villain
     * @return A Villain
     */
    @Override
    public Villain getVillainByName(String villainName) {
        try {
            final String GET_VILLAIN_BY_NAME = "SELECT * FROM Villains WHERE name = ?";
            return jdbc.queryForObject(GET_VILLAIN_BY_NAME, new VillainMapper(), villainName);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /**
     * Returns all the villains seen in a location.
     *
     * @param location The location
     * @return A list of villains.
     */
    @Override
    public List<Villain> getVillainsByLocation(Location location) {
        final String GET_ALL_VILLAINS_BY_LOCATION = "SELECT Villains.id as \"id\", Villains.name as \"name\", Villains.description as \"description\", superpower \n"
                + "FROM Villains\n"
                + "INNER JOIN VillainSightings ON VillainSightings.characterID = Villains.id\n"
                + "INNER JOIN Locations ON VillainSightings.locationID = Locations.id\n"
                + "WHERE Locations.id = ?";
        return jdbc.query(GET_ALL_VILLAINS_BY_LOCATION, new VillainMapper(), location.getId());
    }

    /**
     * Returns all the villains associated with an organisation.
     *
     * @param organisation The organisation
     * @return A list of villains.
     */
    @Override
    public List<Villain> getVillainsByOrganisation(VillainOrganisation organisation) {
        final String GET_ALL_VILLAINS_BY_ORGANISATION = "SELECT Villains.id as \"id\", Villains.name as \"name\", Villains.description as \"description\", superpower \n"
                + "FROM Villains\n"
                + "INNER JOIN VillainAffiliations ON VillainAffiliations.villainID = Villains.id\n"
                + "INNER JOIN VillainOrganisations ON VillainAffiliations.villainOrganisationID = VillainOrganisations.id\n"
                + "WHERE VillainOrganisations.id = ?";

        return jdbc.query(GET_ALL_VILLAINS_BY_ORGANISATION, new VillainMapper(), organisation.getId());
    }

    /**
     * Class to map Villain objects.
     */
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
