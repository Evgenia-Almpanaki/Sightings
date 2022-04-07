package com.mycompany.sightings.dto.Characters;

import com.mycompany.sightings.dto.Organisations.Organisation;
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
//A user must be able to record a superhero/supervillain sighting for a particular location and date.
//The system must be able to report all of the superheroes sighted at a particular location.
//The system must be able to report all of the locations where a particular superhero has been seen.
//The system must be able to report all sightings (hero and location) for a particular date.
//The system must be able to report all of the members of a particular organization.
//The system must be able to report all of the organizations a particular superhero/villain belongs to.