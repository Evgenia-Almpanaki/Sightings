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

    /**
     * Endpoint to add a new hero
     *
     * @param request The HTTP request
     * @return The page to be redirected to.
     */
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
            if (organisationIds != null) {
                for (String organisationId : organisationIds) {
                    HeroOrganisation organisation = heroOrganisationDAO.getOrganisationById(Integer.parseInt(organisationId));
                    organisations.add(organisation);
                }
                hero.setOrganisations(organisations);
            } else {
                hero.setOrganisations(new ArrayList<>());
            }
            heroDAO.addHero(hero);
        }
        return "redirect:/heroes";
    }

    /**
     * Endpoint to display the heroes.
     *
     * @param model The model
     * @return The page to be redirected to
     */
    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDAO.getAllHeroes();
        model.addAttribute("organisations", heroOrganisationDAO.getAllOrganisations());
        model.addAttribute("heroes", heroes);
        model.addAttribute("errors", violations);
        return "heroes";
    }

    /**
     * Endpoint to delete a hero.
     *
     * @param request The HTTP request
     * @return The page to be redirected to
     */
    @GetMapping("deleteCharacter/hero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDAO.deleteHeroById(id);

        return "redirect:/heroes";
    }

    /**
     * Endpoint to edit a hero.
     *
     * @param id The hero id
     * @param model The model
     * @return The page to be redirected to
     */
    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {

        SCharacter character = heroDAO.getHeroById(id);
        model.addAttribute("character", character);
        model.addAttribute("organisations", heroOrganisationDAO.getAllOrganisations());
        return "editHero";
    }

    /**
     * Endpoint to perform hero editing.
     *
     * @param character The hero to be edited
     * @param result
     * @param request
     * @return The page to be redirected to
     */
    @PostMapping("editHero")
    public String performEditHero(@Valid Hero character, BindingResult result, HttpServletRequest request) {

        String[] organisationIds = request.getParameterValues("organisationId");

        List<Organisation> organisations = new ArrayList<>();
        if (organisationIds != null) {
            for (String organisationId : organisationIds) {
                organisations.add(heroOrganisationDAO.getOrganisationById(Integer.parseInt(organisationId)));
            }
        }
        character.setOrganisations(organisations);
        heroDAO.updateHero(character);
        if (result.hasErrors()) {
            return "editHero";
        }
        return "redirect:/heroes";
    }

    /**
     * Endpoint to add a new Villain.
     *
     * @param request
     * @return The page to be redirected to
     */
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
            if (organisationIds != null) {
                for (String organisationId : organisationIds) {
                    VillainOrganisation organisation = villainOrganisationDAO.getOrganisationById(Integer.parseInt(organisationId));
                    organisations.add(organisation);
                }
                villain.setOrganisations(organisations);
            } else {
                villain.setOrganisations(new ArrayList<>());
            }
            villainDAO.addVillain(villain);
        }
        return "redirect:/villains";
    }

    /**
     * Endpoint to display all villains.
     *
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("villains")
    public String displayVillains(Model model) {
        List<Villain> villains = villainDAO.getAllVillains();
        model.addAttribute("organisations", villainOrganisationDAO.getAllOrganisations());
        model.addAttribute("villains", villains);
        model.addAttribute("errors", violations);
        return "villains";
    }

    /**
     * Endpoint to dele a villain.
     *
     * @param request
     * @return The page to be redirected to
     */
    @GetMapping("deleteCharacter/villain")
    public String deleteVillain(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        villainDAO.deleteVillainById(id);

        return "redirect:/villains";
    }

    /**
     * Endpoint to edit a villain.
     *
     * @param id The villain id
     * @param model The model
     * @return The page to be redirected to
     */
    @GetMapping("editVillain")
    public String editVillain(Integer id, Model model) {

        SCharacter character = villainDAO.getVillainById(id);
        model.addAttribute("character", character);
        model.addAttribute("organisations", villainOrganisationDAO.getAllOrganisations());
        return "editVillain";
    }

    /**
     * Endpoint to perform villain editing
     *
     * @param character The villain to be edited
     * @param result
     * @param request
     * @return The page to be redirected to
     */
    @PostMapping("editVillain")
    public String performEditVillain(@Valid Villain character, BindingResult result, HttpServletRequest request) {

        String[] organisationIds = request.getParameterValues("organisationId");

        List<Organisation> organisations = new ArrayList<>();
        if (organisationIds != null) {
            for (String organisationId : organisationIds) {
                organisations.add(villainOrganisationDAO.getOrganisationById(Integer.parseInt(organisationId)));
            }
        }
        character.setOrganisations(organisations);
        villainDAO.updateVillain(character);
        if (result.hasErrors()) {
            return "editVillain";
        }
        return "redirect:/villains";
    }

    /**
     * Endpoint for hero details.
     *
     * @param id The id of the hero
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("detail/hero")
    public String heroDetail(Integer id, Model model) {
        Hero character = heroDAO.getHeroById(id);
        model.addAttribute("character", character);
        return "heroDetail";
    }

    /**
     * Endpoint for villain details.
     *
     * @param id The id of the villain
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("detail/villain")
    public String villainDetail(Integer id, Model model) {
        Villain character = villainDAO.getVillainById(id);
        model.addAttribute("character", character);
        return "heroDetail";
    }
}
