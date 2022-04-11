package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Characters.SCharacter;
import com.ariesight.sightings.dto.Organisations.Organisation;
import java.util.List;

public interface OrganisationDao {
    Organisation getOrganisationById(int id);
    List<Organisation> getAllOrganisations();
    Organisation addOrganisation(Organisation organisation);
    void updateOrganisation(Organisation organisation);
    void deleteOrganisationById(int id);
    
    List<SCharacter> getOrganisationMembers(Organisation organisation);
}
