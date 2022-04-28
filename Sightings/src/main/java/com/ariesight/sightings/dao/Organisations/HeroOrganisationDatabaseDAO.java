package com.ariesight.sightings.dao.Organisations;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
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
public class HeroOrganisationDatabaseDAO implements HeroOrganisationDAO {

    @Autowired
    JdbcTemplate jdbc;

    /**
     * Returns the organisation with the given id.
     *
     * @param id The id of the organisation
     * @return The organisation
     */
    @Override
    public HeroOrganisation getOrganisationById(int id) {
        try {
            final String GET_ORGANISATION_BY_ID = "SELECT * FROM SuperheroOrganisations WHERE id = ?";
            return jdbc.queryForObject(GET_ORGANISATION_BY_ID, new OrganisationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /**
     * Returns all organisations.
     *
     * @return A list of organisations
     */
    @Override
    public List<HeroOrganisation> getAllOrganisations() {
        final String GET_ALL_ORGANISATIONS = "SELECT * FROM SuperheroOrganisations order by SuperheroOrganisations.name";
        return jdbc.query(GET_ALL_ORGANISATIONS, new OrganisationMapper());
    }

    /**
     * Adds a new organisation.
     *
     * @param organisation The organisation to be added
     * @return The organisation
     */
    @Override
    @Transactional
    public HeroOrganisation addOrganisation(HeroOrganisation organisation) {

        final String INSERT_ORGANISATION = "INSERT INTO SuperheroOrganisations(name, description, address, contact) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORGANISATION,
                organisation.getName(),
                organisation.getDescription(),
                organisation.getAddress(),
                organisation.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organisation.setId(newId);
        return organisation;
    }

    /**
     * Updates an organisation.
     *
     * @param organisation The organisation to be updated
     */
    @Override
    public void updateOrganisation(HeroOrganisation organisation) {
        final String UPDATE_ORGANISATION = "UPDATE SuperheroOrganisations SET name = ?, description = ?, address = ?, contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANISATION,
                organisation.getName(),
                organisation.getDescription(),
                organisation.getAddress(),
                organisation.getContact(),
                organisation.getId());
    }

    /**
     * Deletes an organisation.
     *
     * @param id The id of the organisation to be deleted
     */
    @Override
    public void deleteOrganisationById(int id) {
        final String DELETE_ORGANISATION = "DELETE FROM SuperheroOrganisations WHERE id = ?";
        jdbc.update(DELETE_ORGANISATION, id);
    }

    /**
     * Returns all the organisations the character is associated with.
     *
     * @param hero The character
     * @return The list of organisations
     */
    @Override
    public List<HeroOrganisation> getOrganisationsByHero(Hero hero) {
        final String GET_ORGANISATIONS_BY_HERO = "SELECT SuperheroOrganisations.id as \"id\", SuperheroOrganisations.name as \"name\", SuperheroOrganisations.description as \"description\", address, contact\n"
                + "FROM Superheroes\n"
                + "INNER JOIN SuperheroAffiliations ON SuperheroAffiliations.heroID = Superheroes.id\n"
                + "INNER JOIN SuperheroOrganisations ON SuperheroAffiliations.organisationID = SuperheroOrganisations.id\n"
                + "WHERE Superheroes.id = ?";
        return jdbc.query(GET_ORGANISATIONS_BY_HERO, new OrganisationMapper(), hero.getId());
    }

    /**
     * Adds an affiliation between an organisation and a hero.
     *
     * @param organisation The organisation
     * @param hero The hero
     */
    @Override
    public void addAffiliation(HeroOrganisation organisation, Hero hero) {

        final String INSERT_ORGANISATION = "INSERT INTO SuperheroAffiliations(heroID, organisationID) VALUES(?,?)";
        jdbc.update(INSERT_ORGANISATION,
                hero.getId(),
                organisation.getId());
    }

    /**
     * Class that maps hero organisations.
     */
    public static final class OrganisationMapper implements RowMapper<HeroOrganisation> {

        @Override
        public HeroOrganisation mapRow(ResultSet rs, int index) throws SQLException {
            HeroOrganisation organisation = new HeroOrganisation();
            organisation.setId(rs.getInt("id"));
            organisation.setName(rs.getString("name"));
            organisation.setDescription(rs.getString("description"));
            organisation.setContact(rs.getString("contact"));
            organisation.setAddress(rs.getString("address"));
            return organisation;
        }
    }
}
