package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Sighting;
import java.util.Date;
import java.util.List;

public interface SightingDAO {
    Sighting getSightingById(int id);
    List<Sighting> getAllSighting();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsByDate(Date date);
    List<Sighting> getSightingsByLocation(Location location);
    boolean registerNewSighting(Sighting sighting);
}
