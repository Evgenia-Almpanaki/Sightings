package com.ariesight.sightings.dto.Characters;

import java.util.Objects;

public class SCharacter {

    private int id;
    private String name;
    private String description;
    private String superpower;

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SCharacter other = (SCharacter) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return Objects.equals(this.superpower, other.superpower);
    }

}