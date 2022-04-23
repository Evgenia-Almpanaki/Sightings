package com.ariesight.sightings.controller;

import com.ariesight.sightings.dto.Sightings.Sighting;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ariesight.sightings.dao.Sightings.HeroSightingDAO;
import com.ariesight.sightings.dao.Sightings.VillainSightingDAO;
import com.ariesight.sightings.dto.Sightings.HeroSighting;
import com.ariesight.sightings.dto.Sightings.VillainSighting;

@Controller
public class SightingController {

    @Autowired
    private HeroSightingDAO heroSightingDAO;
    @Autowired
    private VillainSightingDAO villainSightingDAO;

    @PostMapping("addSighting/hero")
    public String addHeroSighting(HttpServletRequest request) {

        String character = request.getParameter("character");
        String location = request.getParameter("location");
        String date = request.getParameter("date");

        HeroSighting sighting = new HeroSighting();
        sighting.setCharacterName(character);
        sighting.setLocationName(location);
        sighting.setDate(date);

        heroSightingDAO.addSighting(sighting);
        return "redirect:/heroSightings";
    }

    @GetMapping("heroSightings")
    public String displaySightings(Model model) {
        List<HeroSighting> sightings = heroSightingDAO.getAllSightings();
        model.addAttribute("heroSightings", sightings);
        return "heroSightings";
    }

    @GetMapping("deleteSighting/hero")
    public String deleteHeroSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroSightingDAO.deleteSightingById(id);

        return "redirect:/heroSightings";
    }

    @GetMapping("editHeroSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = heroSightingDAO.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "editHeroSighting";
    }

    @PostMapping("editHeroSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        HeroSighting sighting = heroSightingDAO.getSightingById(id);

        sighting.setCharacterName(request.getParameter("characterName"));
        sighting.setLocationName(request.getParameter("locationName"));
        sighting.setDate(request.getParameter("date"));

        heroSightingDAO.updateSighting(sighting);
        return "redirect:/heroSightings";
    }
    
    
    @PostMapping("addSighting/villain")
    public String addVillainSighting(HttpServletRequest request) {

        String character = request.getParameter("character");
        String location = request.getParameter("location");
        String date = request.getParameter("date");

        VillainSighting sighting = new VillainSighting();
        sighting.setCharacterName(character);
        sighting.setLocationName(location);
        sighting.setDate(date);

        villainSightingDAO.addSighting(sighting);
        return "redirect:/villainSightings";
    }

    @GetMapping("villainSightings")
    public String displayVillainSightings(Model model) {
        List<VillainSighting> sightings = villainSightingDAO.getAllSightings();
        model.addAttribute("villainSightings", sightings);
        return "villainSightings";
    }

    @GetMapping("deleteSighting/villain")
    public String deleteVillainSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        villainSightingDAO.deleteSightingById(id);

        return "redirect:/villainSightings";
    }

    @GetMapping("editVillainSighting")
    public String editVillainSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = villainSightingDAO.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "editVillainSighting";
    }

    @PostMapping("editVillainSighting")
    public String performEditVillainSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        VillainSighting sighting = villainSightingDAO.getSightingById(id);

        sighting.setCharacterName(request.getParameter("characterName"));
        sighting.setLocationName(request.getParameter("locationName"));
        sighting.setDate(request.getParameter("date"));

        villainSightingDAO.updateSighting(sighting);
        return "redirect:/villainSightings";
    }
}
