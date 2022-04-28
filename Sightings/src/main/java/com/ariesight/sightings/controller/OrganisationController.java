package com.ariesight.sightings.controller;

import com.ariesight.sightings.dao.Characters.HeroDAO;
import com.ariesight.sightings.dao.Characters.VillainDAO;
import com.ariesight.sightings.dto.Organisations.Organisation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ariesight.sightings.dao.Organisations.HeroOrganisationDAO;
import com.ariesight.sightings.dao.Organisations.VillainOrganisationDAO;
import com.ariesight.sightings.dto.Characters.Hero;
import com.ariesight.sightings.dto.Characters.SCharacter;
import com.ariesight.sightings.dto.Characters.Villain;
import com.ariesight.sightings.dto.Organisations.HeroOrganisation;
import com.ariesight.sightings.dto.Organisations.VillainOrganisation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@Controller
public class OrganisationController {

    @Autowired
    private HeroOrganisationDAO heroOrganisationDAO;
    @Autowired
    private VillainDAO villainDAO;
    @Autowired
    private HeroDAO heroDAO;

    @Autowired
    private VillainOrganisationDAO villainOrganisationDAO;

    Set<ConstraintViolation<Organisation>> violations = new HashSet<>();

    /**
     * Endpoint to add a hero organisation.
     *
     * @param request
     * @return The page to be redirected to
     */
    @PostMapping("addOrganisation/hero")
    public String addHeroOrganisation(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");

        HeroOrganisation organisation = new HeroOrganisation();
        organisation.setName(name);
        organisation.setDescription(description);
        organisation.setContact(contact);
        organisation.setAddress(address);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organisation);

        if (violations.isEmpty()) {
            heroOrganisationDAO.addOrganisation(organisation);
        }
        return "redirect:/heroOrganisations";
    }

    /**
     * Endpoint to display hero organisations.
     *
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("heroOrganisations")
    public String displayOrganisations(Model model) {
        List<HeroOrganisation> heroOrganisations = heroOrganisationDAO.getAllOrganisations();
        model.addAttribute("heroOrganisations", heroOrganisations);
        model.addAttribute("errors", violations);

        return "heroOrganisations";
    }

    /**
     * Endpoint to delete a hero organisation.
     *
     * @param request
     * @return The page to be redirected to
     */
    @GetMapping("deleteOrganisation/hero")
    public String deleteOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroOrganisationDAO.deleteOrganisationById(id);

        return "redirect:/heroOrganisations";
    }

    /**
     * Endpoint to edit a hero organisation.
     *
     * @param request
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("editHeroOrganisation")
    public String editHeroOrganisation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organisation organisation = heroOrganisationDAO.getOrganisationById(id);

        model.addAttribute("organisation", organisation);
        return "editHeroOrganisation";
    }

    /**
     * Endpoint to perform organisation editing.
     *
     * @param request
     * @return The page to be redirected to
     */
    @PostMapping("editHeroOrganisation")
    public String performEditOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        HeroOrganisation organisation = heroOrganisationDAO.getOrganisationById(id);

        organisation.setName(request.getParameter("name"));
        organisation.setDescription(request.getParameter("description"));
        organisation.setContact(request.getParameter("contact"));
        organisation.setAddress(request.getParameter("address"));

        heroOrganisationDAO.updateOrganisation(organisation);

        return "redirect:/heroOrganisations";
    }

    /**
     * Endpoint to add a villain organisation.
     *
     * @param request
     * @return The page to be redirected to
     */
    @PostMapping("addOrganisation/villain")
    public String addVillainOrganisation(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");

        VillainOrganisation organisation = new VillainOrganisation();
        organisation.setName(name);
        organisation.setDescription(description);
        organisation.setContact(contact);
        organisation.setAddress(address);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organisation);

        if (violations.isEmpty()) {
            villainOrganisationDAO.addOrganisation(organisation);
        }
        return "redirect:/villainOrganisations";
    }

    /**
     * Endpoint to display villain organisations.
     *
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("villainOrganisations")
    public String displayVillainOrganisations(Model model) {
        List<VillainOrganisation> organisations = villainOrganisationDAO.getAllOrganisations();
        model.addAttribute("villainOrganisations", organisations);
        model.addAttribute("errors", violations);

        return "villainOrganisations";
    }

    /**
     * Endpoint to delete a villain organisation.
     *
     * @param request
     * @return The page to be redirected to
     */
    @GetMapping("deleteOrganisation/villain")
    public String deleteVillainOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        villainOrganisationDAO.deleteOrganisationById(id);

        return "redirect:/villainOrganisations";
    }

    /**
     * Endpoint to edit a villain organisation.
     *
     * @param request
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("editVillainOrganisation")
    public String editVillainOrganisation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organisation organisation = villainOrganisationDAO.getOrganisationById(id);

        model.addAttribute("organisation", organisation);
        return "editVillainOrganisation";
    }

    /**
     * Endpoint to permorm villain organisation editing.
     *
     * @param request
     * @return
     */
    @PostMapping("editVillainOrganisation")
    public String performEditVillainOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        VillainOrganisation organisation = villainOrganisationDAO.getOrganisationById(id);

        organisation.setName(request.getParameter("name"));
        organisation.setDescription(request.getParameter("description"));
        organisation.setContact(request.getParameter("contact"));
        organisation.setAddress(request.getParameter("address"));

        villainOrganisationDAO.updateOrganisation(organisation);

        return "redirect:/villainOrganisations";
    }

    /**
     * Get the details for a villain organisation.
     *
     * @param id The organisation id
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("villainOrganisationDetail")
    public String villainOrganisationDetail(Integer id, Model model) {
        Organisation organisation = villainOrganisationDAO.getOrganisationById(id);
        List<SCharacter> characters = new ArrayList<>();
        for (Villain villain : villainDAO.getVillainsByOrganisation((VillainOrganisation) organisation)) {
            characters.add(villain);
        }
        organisation.setMembers(characters);
        model.addAttribute("organisation", organisation);
        return "villainOrganisationDetail";
    }

    /**
     * Get the details for a villain organisation.
     *
     * @param id The organisation id
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("heroOrganisationDetail")
    public String heroOrganisationDetail(Integer id, Model model) {
        Organisation organisation = heroOrganisationDAO.getOrganisationById(id);
        List<SCharacter> characters = new ArrayList<>();
        for (Hero hero : heroDAO.getHeroesByOrganisation((HeroOrganisation) organisation)) {
            characters.add(hero);
        }
        organisation.setMembers(characters);
        model.addAttribute("organisation", organisation);
        return "heroOrganisationDetail";
    }
}
