package com.ariesight.sightings.dto.Organisations;

import com.ariesight.sightings.dto.Characters.SCharacter;
import java.util.ArrayList;
import java.util.List;

public class Organisation {

    private String name;
    private String description;
    private String address;
    private String contact;
    private List<SCharacter> members = new ArrayList<>();

    public void addMember(SCharacter character) {
        members.add(character);
    }

    public void removeMember(SCharacter character) {
        members.remove(character);
    }
}
