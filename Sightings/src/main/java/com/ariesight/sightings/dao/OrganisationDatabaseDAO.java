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
    public Organisation addOrganisation(Organisation organisation) {

        final String INSERT_ORGANISATION = "INSERT INTO Organisations(name, description, address, contact) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORGANISATION,
                organisation.getName(),
                organisation.getDescription(),
                organisation.getAddress(),
                organisation.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organisation.setId(newId);
        return organisation;
    }

    @Override
    public void updateOrganisation(Organisation organisation) {
        final String UPDATE_ORGANISATION = "UPDATE Organisations SET name = ?, description = ?, address = ?, contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANISATION,
                organisation.getName(),
                organisation.getDescription(),
                organisation.getAddress(),
                organisation.getContact(),
                organisation.getId());
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
            Organisation organisation = new Organisation();
            organisation.setId(rs.getInt("id"));
            organisation.setName(rs.getString("name"));
            organisation.setDescription(rs.getString("description"));
            organisation.setContact(rs.getString("contact"));
            organisation.setAddress(rs.getString("address"));
            return organisation;
        }
    }
}
