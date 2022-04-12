package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.SCharacter;
import com.ariesight.sightings.dto.Organisation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrganisationDatabaseDAO implements OrganisationDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Organisation> getOrganisationsOf(SCharacter character) {
        final String SELECT_ORGANISATIONS_BY_CHARACTER = "SELECT organisations.id, organisations.name, organisations.description, organisations.address, organisations.contact\n"
                + "FROM organisations\n"
                + "INNER JOIN affiliations ON organisations.id = affiliations.organisationID\n"
                + "WHERE affiliations.characterID = ?\n"
                + "GROUP BY organisations.id;";

        return jdbc.query(SELECT_ORGANISATIONS_BY_CHARACTER, new OrganisationMapper());
    }

    @Override
    public Organisation getOrganisationById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Organisation> getAllOrganisations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Organisation addOrganisation(Organisation organisation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateOrganisation(Organisation organisation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteOrganisationById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<SCharacter> getOrganisationMembers(Organisation organisation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static final class OrganisationMapper implements RowMapper<Organisation> {

        @Override
        public Organisation mapRow(ResultSet rs, int index) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
