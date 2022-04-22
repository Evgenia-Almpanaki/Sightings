package com.ariesight.sightings.controller;

import com.ariesight.sightings.dto.Organisations.Organisation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ariesight.sightings.dao.Organisations.HeroOrganisationDAO;

@Controller
public class OrganisationController {

    @Autowired
    private HeroOrganisationDAO organisationDAO;
/*
    @PostMapping("addOrganisation")
    public String addOrganisation(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");

        Organisation organisation = new Organisation();
        organisation.setName(name);
        organisation.setDescription(description);
        organisation.setContact(contact);
        organisation.setAddress(address);

        organisationDAO.addOrganisation(organisation);
        return "redirect:/organisations";
    }

    @GetMapping("organisations")
    public String displayOrganisations(Model model) {
        List<Organisation> organisations = organisationDAO.getAllOrganisations();
        model.addAttribute("organisations", organisations);
        return "organisations";
    }

    @GetMapping("deleteOrganisation")
    public String deleteOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        organisationDAO.deleteOrganisationById(id);

        return "redirect:/organisations";
    }

    @GetMapping("editOrganisation")
    public String editOrganisation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organisation organisation = organisationDAO.getOrganisationById(id);

        model.addAttribute("organisation", organisation);
        return "editOrganisation";
    }

    @PostMapping("editOrganisation")
    public String performEditOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organisation organisation = organisationDAO.getOrganisationById(id);

        organisation.setName(request.getParameter("name"));
        organisation.setDescription(request.getParameter("description"));
        organisation.setContact(request.getParameter("contact"));
        organisation.setAddress(request.getParameter("address"));

        organisationDAO.updateOrganisation(organisation);

        return "redirect:/organisations";
    }*/
}
