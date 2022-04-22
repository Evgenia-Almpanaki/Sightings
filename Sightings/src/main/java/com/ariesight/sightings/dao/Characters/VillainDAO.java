package com.ariesight.sightings.dao.Characters;

import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
import java.util.List;

public interface VillainDAO {

    Villain getVillainById(int id);

    List<Villain> getAllVillains();

    Villain addVillain(Villain villain);

    void updateVillain(Villain villain);

    void deleteVillainById(int id);

    Villain getVillainByName(String villainName);

    public List<Villain> getVillainsByOrganisation(VillainOrganisation organisation);

    public List<Villain> getVillainsByLocation(Location location);
}
