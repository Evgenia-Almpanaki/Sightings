package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.SCharacter;
import java.util.List;

public interface CharacterDAO {
    SCharacter getCharacterById(int id);
    List<SCharacter> getAllCharacters();
    SCharacter addCharacter(SCharacter character);
    void updateCharacter(SCharacter character);
    void deleteCharacterById(int id);

    SCharacter getCharacterByName(String characterName);
    
}
