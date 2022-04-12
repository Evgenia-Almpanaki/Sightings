package com.ariesight.sightings.dto;

import java.util.ArrayList;
import java.util.List;

public class Organisation {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String name;
    private String description;
    private String address;
    private String contact;
    private List<SCharacter> members = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void addMember(SCharacter character) {
        members.add(character);
    }

    public void removeMember(SCharacter character) {
        members.remove(character);
    }
}
