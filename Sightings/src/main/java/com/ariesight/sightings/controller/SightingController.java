package com.ariesight.sightings.controller;

import com.ariesight.sightings.dao.SightingDAO;
import com.ariesight.sightings.dto.Sighting;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SightingController {

    @Autowired
    private SightingDAO sightingDAO;

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {

        String character = request.getParameter("character");
        String location = request.getParameter("location");
        String date = request.getParameter("date");

        Sighting sighting = new Sighting();
        sighting.setCharacterName(character);
        sighting.setLocationName(location);
        sighting.setDate(date);

        sightingDAO.addSighting(sighting);
        return "redirect:/sightings";
    }

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDAO.getAllSightings();
        model.addAttribute("sightings", sightings);
        return "sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDAO.deleteSightingById(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDAO.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDAO.getSightingById(id);

        sighting.setCharacterName(request.getParameter("characterName"));
        sighting.setLocationName(request.getParameter("locationName"));
        sighting.setDate(request.getParameter("date"));

        sightingDAO.updateSighting(sighting);
        return "redirect:/sightings";
    }
}
