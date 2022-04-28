package com.ariesight.sightings.dao.Sightings;

import com.ariesight.sightings.dao.Characters.HeroDAO;
import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dto.Sightings.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SightingDatabaseDAO implements SightingDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    HeroDAO characterDAO;

    @Autowired
    LocationDAO locationDAO;

    /**
     * Returns the most recent sightings.
     *
     * @return A list of sightings
     */
    @Override
    public List<Sighting> getRecentSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT Superheroes.name, Locations.name as \"location\", date FROM SuperheroSightings\n"
                + "join Locations on Locations.id = SuperheroSightings.locationID\n"
                + "join Superheroes on Superheroes.id = SuperheroSightings.characterID\n"
                + "union\n"
                + "SELECT Villains.name, Locations.name as \"location\", date FROM VillainSightings\n"
                + "join Locations on Locations.id = VillainSightings.locationID\n"
                + "join Villains on Villains.id = VillainSightings.characterID\n"
                + "order by date desc\n"
                + "limit 10;";
        return jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
    }

    /**
     * Class that maps Sighting objects
     */
    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setCharacterName(rs.getString("name"));
            sighting.setLocationName(rs.getString("location"));
            sighting.setDate(rs.getString("date"));
            return sighting;
        }
    }
}
