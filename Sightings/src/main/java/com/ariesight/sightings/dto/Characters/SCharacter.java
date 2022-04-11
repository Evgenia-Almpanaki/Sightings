package com.ariesight.sightings.dto.Characters;

import com.ariesight.sightings.dto.Organisations.Organisation;
import java.util.ArrayList;
import java.util.List;

public class SCharacter {

    private String name;
    private String description;
    private String superpower;
    private List<Organisation> affiliations = new ArrayList<>();

    public void addAffiliation(Organisation organisation) {
        affiliations.add(organisation);
        organisation.addMember(this);
    }

    public void removeAffiliation(Organisation organisation) {
        affiliations.remove(organisation);
        organisation.removeMember(this);
    }
}
