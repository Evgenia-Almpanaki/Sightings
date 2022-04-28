package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dto.Sightings.HeroSighting;
import java.util.List;

public interface HeroSightingDAO {

    /**
     * Returns the sighting with the given id.
     *
     * @param id The id of the sighting
     * @return The sighting
     */
    HeroSighting getSightingById(int id);

    /**
     * Returns all the hero sightings.
     *
     * @return A list of sightings.
     */
    List<HeroSighting> getAllSightings();

    /**
     * Adds a new sighting.
     *
     * @param sighting The sighting to be added
     * @return The sighting with the updated id.
     */
    HeroSighting addSighting(HeroSighting sighting);

    /**
     * Updates a sighting.
     *
     * @param sighting The sighting to be updated
     */
    void updateSighting(HeroSighting sighting);

    /**
     * Deletes the sighting with the given id.
     *
     * @param id The id of the sighting to be deleted
     */
    void deleteSightingById(int id);

    /**
     * Returns all the hero sightings on the given date.
     *
     * @param date The date
     * @return A list of sightings
     */
    public List<HeroSighting> getHeroSightingsByDate(String date);

}
