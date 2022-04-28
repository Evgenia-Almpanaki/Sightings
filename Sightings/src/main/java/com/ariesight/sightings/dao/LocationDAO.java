package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import java.util.List;

public interface LocationDAO {

    /**
     * Returns the location of the given id.
     *
     * @param id The id of the location
     * @return A location
     */
    Location getLocationById(int id);

    /**
     * Returns all locations
     *
     * @return A list of locations
     */
    List<Location> getAllLocations();

    /**
     * Adds a new location.
     *
     * @param location The location to be added
     * @return The Location object
     */
    Location addLocation(Location location);

    /**
     * Updates a location.
     *
     * @param location The location to be updated
     */
    void updateLocation(Location location);

    /**
     * Deletes a location.
     *
     * @param id The id of the location to be deleted
     */
    void deleteLocationById(int id);

    /**
     * Returns the location with the given name.
     *
     * @param locationName The name of the location
     * @return The Location object
     */
    Location getLocationByName(String locationName);

    /**
     * Returns all the locations a hero has been seen.
     *
     * @param hero The hero
     * @return A list of locations
     */
    public List<Location> getLocationsByHero(Hero hero);

    /**
     * Returns all the locations a villain has been seen.
     *
     * @param villain The villain
     * @return A list of locations
     */
    public List<Location> getLocationsByVillain(Villain villain);
}
