package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Organisation;
import java.util.List;

public interface OrganisationDAO {

    Organisation getOrganisationById(int id);

    List<Organisation> getAllOrganisations();

    Organisation addOrganisation(Organisation organisation);

    void updateOrganisation(Organisation organisation);

    void deleteOrganisationById(int id);

    //List<SCharacter> getOrganisationMembers(Organisation organisation);
    //List<Organisation> getOrganisationsOf(SCharacter character);
}
