package com.ariesight.sightings.dao.Organisations;

import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
import java.util.List;

public interface VillainOrganisationDAO {

    /**
     * Returns the organisation with the given id.
     *
     * @param id The id of the organisation
     * @return The organisation
     */
    VillainOrganisation getOrganisationById(int id);

    /**
     * Returns all organisations.
     *
     * @return A list of organisations
     */
    List<VillainOrganisation> getAllOrganisations();

    /**
     * Adds a new organisation.
     *
     * @param organisation The organisation to be added
     * @return The organisation
     */
    VillainOrganisation addOrganisation(VillainOrganisation organisation);

    /**
     * Updates an organisation.
     *
     * @param organisation The organisation to be updated
     */
    void updateOrganisation(VillainOrganisation organisation);

    /**
     * Deletes an organisation.
     *
     * @param id The id of the organisation to be deleted
     */
    void deleteOrganisationById(int id);

    /**
     * Returns all the organisations the character is associated with.
     *
     * @param villain The character
     * @return The list of organisations
     */
    List<VillainOrganisation> getOrganisationsByVillain(Villain villain);

    /**
     * Adds an affiliation between an organisation and a villain.
     *
     * @param organisation The organisation
     * @param villain The villain
     */
    void addAffiliation(VillainOrganisation organisation, Villain villain);
}
