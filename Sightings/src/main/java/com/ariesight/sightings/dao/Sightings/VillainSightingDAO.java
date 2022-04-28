package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dto.Sightings.VillainSighting;
import java.util.List;

public interface VillainSightingDAO {

    /**
     * Returns the sighting with the given id.
     *
     * @param id The id of the sighting
     * @return The sighting
     */
    VillainSighting getSightingById(int id);

    /**
     * Returns all the villain sightings.
     *
     * @return A list of sightings.
     */
    List<VillainSighting> getAllSightings();

    /**
     * Adds a new sighting.
     *
     * @param sighting The sighting to be added
     * @return The sighting with the updated id.
     */
    VillainSighting addSighting(VillainSighting sighting);

    /**
     * Updates a sighting.
     *
     * @param sighting The sighting to be updated
     */
    void updateSighting(VillainSighting sighting);

    /**
     * Deletes the sighting with the given id.
     *
     * @param id The id of the sighting to be deleted
     */
    void deleteSightingById(int id);

    /**
     * Returns all the villain sightings on the given date.
     *
     * @param date The date
     * @return A list of sightings
     */
    public List<VillainSighting> getVillainSightingsByDate(String date);
}
