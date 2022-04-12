package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Organisation;
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
public class OrganisationDatabaseDAO implements OrganisationDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organisation getOrganisationById(int id) {
        try {
            final String GET_ORGANISATION_BY_ID = "SELECT * FROM Organisations WHERE id = ?";
            return jdbc.queryForObject(GET_ORGANISATION_BY_ID, new OrganisationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organisation> getAllOrganisations() {
        final String GET_ALL_ORGANISATIONS = "SELECT * FROM Organisations";
        return jdbc.query(GET_ALL_ORGANISATIONS, new OrganisationMapper());
    }

    @Override
    @Transactional
    public Organisation addOrganisation(Organisation Organisation) {

        final String INSERT_ORGANISATION = "INSERT INTO Organisations(name, description, address, contact) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORGANISATION,
                Organisation.getName(),
                Organisation.getDescription(),
                Organisation.getAddress(),
                Organisation.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        Organisation.setId(newId);
        return Organisation;
    }

    @Override
    public void updateOrganisation(Organisation Organisation) {
        final String UPDATE_ORGANISATION = "UPDATE Organisations SET name = ?, description = ?, address = ?, contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANISATION,
                Organisation.getName(),
                Organisation.getDescription(),
                Organisation.getAddress(),
                Organisation.getContact(),
                Organisation.getId());
    }

    @Override
    public void deleteOrganisationById(int id) {
        final String DELETE_ORGANISATION = "DELETE FROM Organisations WHERE id = ?";
        jdbc.update(DELETE_ORGANISATION, id);
        final String DELETE_AFFILIATIONS = "DELETE FROM Affiliations WHERE OrganisationID = ?";
        jdbc.update(DELETE_AFFILIATIONS, id);
        //todo delete data from other tables
    }

    public static final class OrganisationMapper implements RowMapper<Organisation> {

        @Override
        public Organisation mapRow(ResultSet rs, int index) throws SQLException {
            Organisation Organisation = new Organisation();
            Organisation.setId(rs.getInt("id"));
            Organisation.setName(rs.getString("name"));
            Organisation.setDescription(rs.getString("description"));
            Organisation.setContact(rs.getString("contact"));
            Organisation.setAddress(rs.getString("address"));
            return Organisation;
        }
    }
}
