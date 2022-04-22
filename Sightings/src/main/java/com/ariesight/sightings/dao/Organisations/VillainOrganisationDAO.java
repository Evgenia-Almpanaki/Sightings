package com.ariesight.sightings.dao.Organisations;

import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
import java.util.List;

public interface VillainOrganisationDAO {

    VillainOrganisation getOrganisationById(int id);

    List<VillainOrganisation> getAllOrganisations();

    VillainOrganisation addOrganisation(VillainOrganisation organisation);

    void updateOrganisation(VillainOrganisation organisation);

    void deleteOrganisationById(int id);

    public List<VillainOrganisation> getOrganisationsByVillain(Villain villain);

    public void addAffiliation(VillainOrganisation organisation, Villain villain);
}
