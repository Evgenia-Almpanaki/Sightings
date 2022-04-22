package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dto.Sightings.VillainSighting;
import java.util.List;

public interface VillainSightingDAO {

    VillainSighting getSightingById(int id);

    List<VillainSighting> getAllSightings();

    VillainSighting addSighting(VillainSighting sighting);

    void updateSighting(VillainSighting sighting);

    void deleteSightingById(int id);

    public List<VillainSighting> getVillainSightingsByDate(String date);
}
