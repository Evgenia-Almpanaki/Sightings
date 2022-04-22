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

@Controller
public class CharacterController {

    @Autowired
    private HeroDAO characterDAO;
/*
    @PostMapping("addCharacter")
    public String addCharacter(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpower = request.getParameter("superpower");

        SCharacter character = new SCharacter();
        character.setName(name);
        character.setDescription(description);
        character.setSuperpower(superpower);

        characterDAO.addHero(character);
        return "redirect:/characters";
    }

    @GetMapping("characters")
    public String displayCharacters(Model model) {
        List<SCharacter> characters = characterDAO.getAllHeroes();
        model.addAttribute("characters", characters);
        return "characters";
    }
*/
    @GetMapping("deleteCharacter")
    public String deleteCharacter(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        characterDAO.deleteHeroById(id);

        return "redirect:/characters";
    }

    @GetMapping("editCharacter")
    public String editCharacter(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SCharacter character = characterDAO.getHeroById(id);

        model.addAttribute("character", character);
        return "editCharacter";
    }
/*
    @PostMapping("editCharacter")
    public String performEditCharacter(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        SCharacter character = characterDAO.getHeroById(id);

        character.setName(request.getParameter("name"));
        character.setDescription(request.getParameter("description"));
        character.setSuperpower(request.getParameter("superpower"));

        characterDAO.updateHero(character);

        return "redirect:/characters";
    }*/
}
