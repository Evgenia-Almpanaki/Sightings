package com.ariesight.sightings.dto.Characters;

import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import com.ariesight.sightings.dto.Organisations.Organisation;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SCharacter {

    protected int id;
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 30, message = "Name must be less than 30 characters.")
    protected String name;
    @NotBlank(message = "Description must not be empty.")
    protected String description;
    @NotBlank(message = "Superpower must not be empty.")
    @Size(max = 30, message = "Superpower must be less than 30 characters.")
    protected String superpower;

    protected List<Organisation> organisations;

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }
    
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
