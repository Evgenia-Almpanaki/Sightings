package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Location;
import java.util.List;

public interface LocationDAO {
    
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationById(int id);
    
    //List<Location> getLocationsByCharacter(String name);
    
    
}
