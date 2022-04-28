package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import java.util.List;

public interface HeroDAO {

    /**
     * Returns the hero with the given id.
     *
     * @param id The id of the hero
     * @return A Hero object
     */
    Hero getHeroById(int id);

    /**
     * Returns a list with all the heroes.
     *
     * @return A list of heroes
     */
    List<Hero> getAllHeroes();

    /**
     * Adds a new hero.
     *
     * @param character The hero to be added
     * @return A Hero object
     */
    Hero addHero(Hero character);

    /**
     * Updates a hero.
     *
     * @param character The hero to be updated
     */
    void updateHero(Hero character);

    /**
     * Deletes the hero with the given id.
     *
     * @param id The id of the hero
     */
    void deleteHeroById(int id);

    /**
     * Returns the hero with the given name.
     *
     * @param characterName The name of the character
     * @return The hero with the given name
     */
    Hero getHeroByName(String characterName);

    /**
     * Returns all the heroes seen in the given location.
     *
     * @param location The location
     * @return A list of heroes
     */
    public List<Hero> getHeroesByLocation(Location location);

    /**
     * Returns all the heroes in a given organisation.
     *
     * @param organisation The organiastion
     * @return A list of heroes
     */
    public List<Hero> getHeroesByOrganisation(HeroOrganisation organisation);
}
