package com.ariesight.sightings.dto.Characters;

import com.ariesight.sightings.dto.Organisations.Organisation;
import java.util.ArrayList;
import java.util.List;

public class SCharacter {

    private int id;
    private String name;
    private String description;
    private String superpower;
    private List<Organisation> affiliations = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    public void addAffiliation(Organisation organisation) {
        affiliations.add(organisation);
        organisation.addMember(this);
    }

    public void removeAffiliation(Organisation organisation) {
        affiliations.remove(organisation);
        organisation.removeMember(this);
    }
}
