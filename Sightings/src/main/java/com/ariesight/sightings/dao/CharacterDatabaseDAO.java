package com.ariesight.sightings.dao;

import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.SCharacter;
import com.ariesight.sightings.dto.Characters.Villain;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CharacterDatabaseDAO implements CharacterDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SCharacter getCharacterById(int id) {
        try {
            final String GET_CHARACTER_BY_ID = "SELECT * FROM Characters WHERE id = ?";
            return jdbc.queryForObject(GET_CHARACTER_BY_ID, new CharacterMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SCharacter> getAllCharacters() {
        final String GET_ALL_CHARACTERS = "SELECT * FROM Characters";
        return jdbc.query(GET_ALL_CHARACTERS, new CharacterMapper());
    }

    @Override
    @Transactional
    public SCharacter addCharacter(SCharacter character) {
        String classProperty = "";
        if (character.getClass() == Hero.class) {
            classProperty = "isHero";
        } else {
            classProperty = "isVillain";
        }

        final String INSERT_CHARACTER = "INSERT INTO Characters(name, description, superpower, ?) VALUES(?,?,?,?)";
        jdbc.update(INSERT_CHARACTER,
                classProperty,
                character.getName(),
                character.getDescription(),
                character.getSuperpower(),
                true);

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        character.setId(newId);
        return character;
    }

    @Override
    public void updateCharacter(SCharacter character) {
        final String UPDATE_CHARACTER = "UPDATE teacher SET name = ?, description = ?, superpower = ?, isHero = ?, isVillain = ? WHERE id = ?";
        jdbc.update(UPDATE_CHARACTER,
                character.getName(),
                character.getDescription(),
                character.getSuperpower(),
                character.getClass() == Hero.class,
                character.getClass() == Villain.class,
                character.getId());
    }

    @Override
    public void deleteCharacterById(int id) {
        final String DELETE_CHARACTER = "DELETE FROM Characters WHERE id = ?";
        jdbc.update(DELETE_CHARACTER, id);
        final String DELETE_AFFILIATIONS = "DELETE FROM Affiliations WHERE characterID = ?";
        jdbc.update(DELETE_AFFILIATIONS, id);
        final String DELETE_SIGHTINGS = "DELETE FROM Sightings WHERE characterID = ?";
        jdbc.update(DELETE_SIGHTINGS, id);
    }

    public static final class CharacterMapper implements RowMapper<SCharacter> {

        @Override
        public SCharacter mapRow(ResultSet rs, int index) throws SQLException {
            SCharacter character = new SCharacter();
            character.setId(rs.getInt("id"));
            character.setName(rs.getString("firstName"));
            character.setDescription(rs.getString("description"));
            character.setSuperpower(rs.getString("superpower"));
            boolean isHero = rs.getBoolean("isHero");
            if (isHero) {
                return (Hero) character;
            }
            return (Villain) character;
        }
    }
}
