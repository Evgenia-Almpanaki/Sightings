package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
import java.util.List;

public interface VillainDAO {

    /**
     * Returns the villain with the given id.
     * @param id The id of the villain
     * @return The villain
     */
    Villain getVillainById(int id);

    /**
     * Returns all the villains.
     * @return A list of villains
     */
    List<Villain> getAllVillains();

    /**
     * Adds a villain.
     * @param villain The villain to be added
     * @return The Villain object
     */
    Villain addVillain(Villain villain);

    /**
     * Updates a villain.
     * @param villain The villain to be updated
     */
    void updateVillain(Villain villain);

    /**
     * Deletes a villain.
     * @param id The id of the villain to be deleted
     */
    void deleteVillainById(int id);

    /**
     * Returns the villain with the given name.
     * @param villainName The name of the villain
     * @return A Villain
     */
    Villain getVillainByName(String villainName);

    /**
     * Returns all the villains associated with an organisation.
     *
     * @param organisation The organisation
     * @return A list of villains.
     */
    public List<Villain> getVillainsByOrganisation(VillainOrganisation organisation);

    /**
     * Returns all the villains seen in a location.
     *
     * @param location The location
     * @return A list of villains.
     */
    public List<Villain> getVillainsByLocation(Location location);
}
