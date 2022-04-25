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
import com.ariesight.sightings.dao.Organisations.HeroOrganisationDAO;
import com.ariesight.sightings.dao.Organisations.VillainOrganisationDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import com.ariesight.sightings.dto.Organisations.Organisation;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.validation.BindingResult;

@Controller
public class CharacterController {

    @Autowired
    private HeroOrganisationDAO heroOrganisationDAO;
    @Autowired
    private VillainOrganisationDAO villainOrganisationDAO;
    @Autowired
    private HeroDAO heroDAO;
    @Autowired
    private VillainDAO villainDAO;
    Set<ConstraintViolation<SCharacter>> violations = new HashSet<>();

    @PostMapping("addCharacter/hero")
    public String addHero(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpower = request.getParameter("superpower");
        Hero hero = new Hero();
        hero.setName(name);
        hero.setDescription(description);
        hero.setSuperpower(superpower);

        String[] organisationIds = request.getParameterValues("organisationId");
        List<Organisation> organisations = new ArrayList<>();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        if (violations.isEmpty()) {
            for (String organisationId : organisationIds) {
                HeroOrganisation organisation = heroOrganisationDAO.getOrganisationById(Integer.parseInt(organisationId));
                organisations.add(organisation);
            }
            hero.setOrganisations(organisations);
            heroDAO.addHero(hero);
        }
        return "redirect:/heroes";
    }

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDAO.getAllHeroes();
        model.addAttribute("organisations", heroOrganisationDAO.getAllOrganisations());
        model.addAttribute("heroes", heroes);
        model.addAttribute("errors", violations);
        return "heroes";
    }

    @GetMapping("deleteCharacter/hero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDAO.deleteHeroById(id);

        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {

        SCharacter character = heroDAO.getHeroById(id);
        model.addAttribute("character", character);
        model.addAttribute("organisations", heroOrganisationDAO.getAllOrganisations());
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(@Valid Hero character, BindingResult result, HttpServletRequest request) {

        String[] organisationIds = request.getParameterValues("organisationId");

        List<Organisation> organisations = new ArrayList<>();
        for (String organisationId : organisationIds) {
            organisations.add(heroOrganisationDAO.getOrganisationById(Integer.parseInt(organisationId)));
        }
        character.setOrganisations(organisations);
        heroDAO.updateHero(character);
        if (result.hasErrors()) {
            return "editHero";
        }
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

        String[] organisationIds = request.getParameterValues("organisationId");
        List<Organisation> organisations = new ArrayList<>();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(villain);

        if (violations.isEmpty()) {
            for (String organisationId : organisationIds) {
                VillainOrganisation organisation = villainOrganisationDAO.getOrganisationById(Integer.parseInt(organisationId));
                organisations.add(organisation);
            }
            villain.setOrganisations(organisations);
            villainDAO.addVillain(villain);
        }
        return "redirect:/villains";
    }

    @GetMapping("villains")
    public String displayVillains(Model model) {
        List<Villain> villains = villainDAO.getAllVillains();
        model.addAttribute("organisations", villainOrganisationDAO.getAllOrganisations());
        model.addAttribute("villains", villains);
        model.addAttribute("errors", violations);
        return "villains";
    }

    @GetMapping("deleteCharacter/villain")
    public String deleteVillain(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        villainDAO.deleteVillainById(id);

        return "redirect:/villains";
    }

    @GetMapping("editVillain")
    public String editVillain(Integer id, Model model) {

        SCharacter character = villainDAO.getVillainById(id);
        model.addAttribute("character", character);
        model.addAttribute("organisations", villainOrganisationDAO.getAllOrganisations());
        return "editVillain";
    }

    @PostMapping("editVillain")
    public String performEditVillain(@Valid Villain character, BindingResult result, HttpServletRequest request) {

        String[] organisationIds = request.getParameterValues("organisationId");

        List<Organisation> organisations = new ArrayList<>();
        for (String organisationId : organisationIds) {
            organisations.add(villainOrganisationDAO.getOrganisationById(Integer.parseInt(organisationId)));
        }
        character.setOrganisations(organisations);
        villainDAO.updateVillain(character);
        if (result.hasErrors()) {
            return "editVillain";
        }
        return "redirect:/villains";
    }

}
