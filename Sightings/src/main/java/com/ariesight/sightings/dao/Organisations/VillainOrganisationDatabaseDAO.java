package com.ariesight.sightings.dao.Organisations;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
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
public class VillainOrganisationDatabaseDAO implements VillainOrganisationDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public VillainOrganisation getOrganisationById(int id) {
        try {
            final String GET_ORGANISATION_BY_ID = "SELECT * FROM VillainOrganisations WHERE id = ?";
            return jdbc.queryForObject(GET_ORGANISATION_BY_ID, new OrganisationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<VillainOrganisation> getAllOrganisations() {
        final String GET_ALL_ORGANISATIONS = "SELECT * FROM VillainOrganisations order by VillainOrganisations.name";
        return jdbc.query(GET_ALL_ORGANISATIONS, new OrganisationMapper());
    }

    @Override
    @Transactional
    public VillainOrganisation addOrganisation(VillainOrganisation organisation) {

        final String INSERT_ORGANISATION = "INSERT INTO VillainOrganisations(name, description, address, contact) VALUES(?,?,?,?)";
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
    public void updateOrganisation(VillainOrganisation organisation) {
        final String UPDATE_ORGANISATION = "UPDATE VillainOrganisations SET name = ?, description = ?, address = ?, contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANISATION,
                organisation.getName(),
                organisation.getDescription(),
                organisation.getAddress(),
                organisation.getContact(),
                organisation.getId());
    }

    @Override
    public void deleteOrganisationById(int id) {
        final String DELETE_ORGANISATION = "DELETE FROM VillainOrganisations WHERE id = ?";
        jdbc.update(DELETE_ORGANISATION, id);
    }

    @Override
    public List<VillainOrganisation> getOrganisationsByVillain(Villain villain) {
        final String GET_ORGANISATIONS_BY_VILLAIN = "SELECT VillainOrganisations.id as \"id\", VillainOrganisations.name as \"name\", VillainOrganisations.description as \"description\", address, contact\n" +
                                                    "FROM Villains\n" +
                                                    "INNER JOIN VillainAffiliations ON VillainAffiliations.villainID = Villains.id\n" +
                                                    "INNER JOIN VillainOrganisations ON VillainAffiliations.villainOrganisationID = VillainOrganisations.id\n" +
                                                    "WHERE Villains.id = ?";
        return jdbc.query(GET_ORGANISATIONS_BY_VILLAIN, new OrganisationMapper(), villain.getId());
    }

    public static final class OrganisationMapper implements RowMapper<VillainOrganisation> {

        @Override
        public VillainOrganisation mapRow(ResultSet rs, int index) throws SQLException {
            VillainOrganisation organisation = new VillainOrganisation();
            organisation.setId(rs.getInt("id"));
            organisation.setName(rs.getString("name"));
            organisation.setDescription(rs.getString("description"));
            organisation.setContact(rs.getString("contact"));
            organisation.setAddress(rs.getString("address"));
            return organisation;
        }
    }
    
    
    @Override
    public void addAffiliation(VillainOrganisation organisation, Villain villain) {
        
        final String INSERT_ORGANISATION = "INSERT INTO VillainAffiliations(villainID, villainOrganisationID) VALUES(?,?)";
        jdbc.update(INSERT_ORGANISATION,
                villain.getId(),
                organisation.getId());
    }
}
