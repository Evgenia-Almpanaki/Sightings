package com.ariesight.sightings.dao.Organisations;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import java.util.List;

public interface HeroOrganisationDAO {

    HeroOrganisation getOrganisationById(int id);

    List<HeroOrganisation> getAllOrganisations();

    HeroOrganisation addOrganisation(HeroOrganisation organisation);

    void updateOrganisation(HeroOrganisation organisation);

    void deleteOrganisationById(int id);

    public List<HeroOrganisation> getOrganisationsByHero(Hero hero);
    
    void addAffiliation(HeroOrganisation organisation, Hero hero);
}
