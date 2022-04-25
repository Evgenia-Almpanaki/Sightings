package com.ariesight.sightings.controller;

import com.ariesight.sightings.dao.Characters.HeroDAO;
import com.ariesight.sightings.dao.Characters.VillainDAO;
import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dto.Sightings.Sighting;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ariesight.sightings.dao.Sightings.HeroSightingDAO;
import com.ariesight.sightings.dao.Sightings.SightingDAO;
import com.ariesight.sightings.dao.Sightings.VillainSightingDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Location;
import com.ariesight.sightings.dto.Sightings.HeroSighting;
import com.ariesight.sightings.dto.Sightings.VillainSighting;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@Controller
public class SightingController {

    @Autowired
    private HeroSightingDAO heroSightingDAO;
    @Autowired
    private LocationDAO locationDAO;
    @Autowired
    private HeroDAO heroDAO;
    @Autowired
    private SightingDAO sightingDAO;
    @Autowired
    private VillainSightingDAO villainSightingDAO;
    @Autowired
    private VillainDAO villainDAO;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("index")
    public String displayAllSightings(Model model) {
        List<Sighting> sightings = sightingDAO.getAllSightings();
        model.addAttribute("index", sightings);
        return "index";
    }

    @PostMapping("addSighting/hero")
    public String addHeroSighting(HeroSighting sighting, HttpServletRequest request) {

        String characterId = request.getParameter("characterId");
        String locationId = request.getParameter("locationId");
        String date = request.getParameter("date");

        Hero character = heroDAO.getHeroById(Integer.parseInt(characterId));
        Location location = locationDAO.getLocationById(Integer.parseInt(locationId));

        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate(date);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        if (violations.isEmpty()) {
            heroSightingDAO.addSighting(sighting);
        }
        return "redirect:/heroSightings";
    }

    @GetMapping("heroSightings")
    public String displaySightings(Model model) {
        List<HeroSighting> sightings = heroSightingDAO.getAllSightings();
        List<Hero> heroes = heroDAO.getAllHeroes();
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("heroSightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);

        return "heroSightings";
    }

    @GetMapping("deleteSighting/hero")
    public String deleteHeroSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroSightingDAO.deleteSightingById(id);

        return "redirect:/heroSightings";
    }

    @GetMapping("editHeroSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = heroSightingDAO.getSightingById(id);
        List<Hero> heroes = heroDAO.getAllHeroes();
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "editHeroSighting";
    }

    @PostMapping("editHeroSighting")
    public String performEditSighting(HeroSighting sighting, HttpServletRequest request) {

        String characterId = request.getParameter("characterId");
        String locationId = request.getParameter("locationId");
        String date = request.getParameter("date");

        Hero character = heroDAO.getHeroById(Integer.parseInt(characterId));
        Location location = locationDAO.getLocationById(Integer.parseInt(locationId));

        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate(date);

        heroSightingDAO.updateSighting(sighting);
        return "redirect:/heroSightings";
    }

    @PostMapping("addSighting/villain")
    public String addVillainSighting(VillainSighting sighting, HttpServletRequest request) {

        String characterId = request.getParameter("characterId");
        String locationId = request.getParameter("locationId");
        String date = request.getParameter("date");

        Villain character = villainDAO.getVillainById(Integer.parseInt(characterId));
        Location location = locationDAO.getLocationById(Integer.parseInt(locationId));

        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate(date);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            villainSightingDAO.addSighting(sighting);
        }
        return "redirect:/villainSightings";
    }

    @GetMapping("villainSightings")
    public String displayVillainSightings(Model model) {

        List<VillainSighting> sightings = villainSightingDAO.getAllSightings();
        List<Villain> villains = villainDAO.getAllVillains();
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("villainSightings", sightings);
        model.addAttribute("villains", villains);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "villainSightings";
    }

    @GetMapping("deleteSighting/villain")
    public String deleteVillainSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        villainSightingDAO.deleteSightingById(id);

        return "redirect:/villainSightings";
    }

    @GetMapping("editVillainSighting")
    public String editVillainSighting(Integer id, Model model) {

        Sighting sighting = villainSightingDAO.getSightingById(id);
        List<Villain> villains = villainDAO.getAllVillains();
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("villains", villains);
        model.addAttribute("locations", locations);
        model.addAttribute("sighting", sighting);
        return "editVillainSighting";
    }
    
    @PostMapping("editVillainSighting")
    public String performEditVillainSighting(VillainSighting sighting, HttpServletRequest request) {
        
        String characterId = request.getParameter("characterId");
        String locationId = request.getParameter("locationId");
        String date = request.getParameter("date");

        Villain character = villainDAO.getVillainById(Integer.parseInt(characterId));
        Location location = locationDAO.getLocationById(Integer.parseInt(locationId));

        sighting.setCharacterID(character.getId());
        sighting.setCharacterName(character.getName());
        sighting.setLocationID(location.getId());
        sighting.setLocationName(location.getName());
        sighting.setDate(date);

        villainSightingDAO.updateSighting(sighting);
        return "redirect:/villainSightings";
    }
}
