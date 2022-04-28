package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dto.Sightings.Sighting;
import java.util.List;

public interface SightingDAO {

    /**
     * Returns the most recent sightings.
     *
     * @return A list of sightings
     */
    List<Sighting> getRecentSightings();

}
