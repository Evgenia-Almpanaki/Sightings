package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dto.Sightings.HeroSighting;
import java.util.List;

public interface HeroSightingDAO {

    HeroSighting getSightingById(int id);

    List<HeroSighting> getAllSightings();

    HeroSighting addSighting(HeroSighting sighting);

    void updateSighting(HeroSighting sighting);

    void deleteSightingById(int id);

    public List<HeroSighting> getHeroSightingsByDate(String date);

}
