package com.ariesight.sightings.dao.Organisations;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import java.util.List;

public interface HeroOrganisationDAO {

    /**
     * Returns the organisation with the given id.
     *
     * @param id The id of the organisation
     * @return The organisation
     */
    HeroOrganisation getOrganisationById(int id);

    /**
     * Returns all organisations.
     *
     * @return A list of organisations
     */
    List<HeroOrganisation> getAllOrganisations();

    /**
     * Adds a new organisation.
     *
     * @param organisation The organisation to be added
     * @return The organisation
     */
    HeroOrganisation addOrganisation(HeroOrganisation organisation);

    /**
     * Updates an organisation.
     *
     * @param organisation The organisation to be updated
     */
    void updateOrganisation(HeroOrganisation organisation);

    /**
     * Deletes an organisation.
     *
     * @param id The id of the organisation to be deleted
     */
    void deleteOrganisationById(int id);

    /**
     * Returns all the organisations the character is associated with.
     *
     * @param hero The character
     * @return The list of organisations
     */
    List<HeroOrganisation> getOrganisationsByHero(Hero hero);

    /**
     * Adds an affiliation between an organisation and a hero.
     *
     * @param organisation The organisation
     * @param hero The hero
     */
    void addAffiliation(HeroOrganisation organisation, Hero hero);
}
