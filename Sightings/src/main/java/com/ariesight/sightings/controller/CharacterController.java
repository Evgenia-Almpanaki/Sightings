package com.ariesight.sightings.controller;

import com.ariesight.sightings.dto.Characters.SCharacter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ariesight.sightings.dao.Characters.HeroDAO;
import com.ariesight.sightings.dao.Characters.VillainDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.Villain;

@Controller
public class CharacterController {

    @Autowired
    private HeroDAO heroDAO;
    @Autowired
    private VillainDAO villainDAO;

    @PostMapping("addCharacter/hero")
    public String addHero(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpower = request.getParameter("superpower");

        Hero hero = new Hero();
        hero.setName(name);
        hero.setDescription(description);
        hero.setSuperpower(superpower);

        heroDAO.addHero(hero);
        return "redirect:/heroes";
    }

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDAO.getAllHeroes();
        model.addAttribute("heroes", heroes);
        return "heroes";
    }

    @GetMapping("deleteCharacter/hero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDAO.deleteHeroById(id);

        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SCharacter character = heroDAO.getHeroById(id);

        model.addAttribute("character", character);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero character = heroDAO.getHeroById(id);

        character.setName(request.getParameter("name"));
        character.setDescription(request.getParameter("description"));
        character.setSuperpower(request.getParameter("superpower"));

        heroDAO.updateHero(character);

        return "redirect:/heroes";
    }
    
       @PostMapping("addCharacter/villain")
    public String addVillain(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpower = request.getParameter("superpower");

        Villain villain = new Villain();
        villain.setName(name);
        villain.setDescription(description);
        villain.setSuperpower(superpower);

        villainDAO.addVillain(villain);
        return "redirect:/villains";
    }

    @GetMapping("villains")
    public String displayVillains(Model model) {
        List<Villain> villains = villainDAO.getAllVillains();
        model.addAttribute("villains", villains);
        return "villains";
    }

    @GetMapping("deleteCharacter/villain")
    public String deleteVillain(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        villainDAO.deleteVillainById(id);

        return "redirect:/villains";
    }

    @GetMapping("editVillain")
    public String editVillain(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SCharacter character = villainDAO.getVillainById(id);

        model.addAttribute("character", character);
        return "editVillain";
    }

    @PostMapping("editVillain")
    public String performEditVillain(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Villain character = villainDAO.getVillainById(id);

        character.setName(request.getParameter("name"));
        character.setDescription(request.getParameter("description"));
        character.setSuperpower(request.getParameter("superpower"));

        villainDAO.updateVillain(character);

        return "redirect:/villains";
    }
}
