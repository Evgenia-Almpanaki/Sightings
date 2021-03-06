package com.ariesight.sightings.controller;

import com.ariesight.sightings.dao.LocationDAO;
import com.ariesight.sightings.dto.Location;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LocationController {

    @Autowired
    private LocationDAO locationDAO;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    /**
     * Endpoint to add a new location.
     *
     * @param request
     * @return The page to be redirected to
     */
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLongitude(longitude);
        location.setLatitude(latitude);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if (violations.isEmpty()) {
            locationDAO.addLocation(location);
        }
        return "redirect:/locations";
    }

    /**
     * Endpoint to display all locations
     *
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);

        return "locations";
    }

    /**
     * Endpoint to delete a location
     *
     * @param request
     * @return The page to be redirected to
     */
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDAO.deleteLocationById(id);

        return "redirect:/locations";
    }

    /**
     * Endpoint to edit a location
     *
     * @param request
     * @param model
     * @return The page to be redirected to
     */
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDAO.getLocationById(id);

        model.addAttribute("location", location);
        return "editLocation";
    }

    /**
     * Endpoint to perform location editing
     *
     * @param request
     * @return The page to be redirected to
     */
    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDAO.getLocationById(id);

        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLongitude(request.getParameter("longitude"));
        location.setLatitude(request.getParameter("latitude"));

        locationDAO.updateLocation(location);

        return "redirect:/locations";
    }
}
