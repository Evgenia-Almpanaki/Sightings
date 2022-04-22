package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import java.util.List;

public interface HeroDAO {

    Hero getHeroById(int id);

    List<Hero> getAllHeroes();

    Hero addHero(Hero character);

    void updateHero(Hero character);

    void deleteHeroById(int id);

    Hero getHeroByName(String characterName);

    public List<Hero> getHeroesByLocation(Location location);

    public List<Hero> getHeroesByOrganisation(HeroOrganisation organisation);
}
