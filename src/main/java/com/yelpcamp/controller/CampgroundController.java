package com.yelpcamp.controller;

import com.yelpcamp.model.Campground;
import com.yelpcamp.model.Review;
import com.yelpcamp.model.YelpCampUser;
import com.yelpcamp.service.CampgroundService;
import com.yelpcamp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static com.yelpcamp.controller.ControllerConstants.*;

@Controller
public class CampgroundController {

    @Autowired
    private CampgroundService campgroundService;

    @Autowired
    private ReviewService reviewService;


    @GetMapping(ROUTES.CAMPGROUNDS_INDEX)
    public String index(Model model){
        model.addAttribute(ATTRIBUTES.TITLE, TITLES.CAMPGROUNDS);
        var campgrounds = campgroundService.getAllCampgrounds();
        model.addAttribute(ATTRIBUTES.CAMPGROUNDS, campgrounds);
        return VIEWS.CAMPGROUND_INDEX;
    }

    @PostMapping(ROUTES.CAMPGROUNDS_INDEX)
    public String createNewCampground(@ModelAttribute @Valid Campground campground,
                                      @AuthenticationPrincipal YelpCampUser loggedInUser,
                                      @RequestParam("files") MultipartFile[] images,
                                      RedirectAttributes redirectAttributes) throws IOException {
        campgroundService.addCampground(campground, images, loggedInUser);
        redirectAttributes.addFlashAttribute(ATTRIBUTES.SUCCESS, "Campground Added successfully");
        return ROUTES.CAMPGROUND_INDEX_REDIRECT + campground.getId();
    }

    @GetMapping(ROUTES.CAMPGROUND)
    public String showCampground(@PathVariable Long id, Model model){
        var campground = campgroundService.getCampground(id);
        var reviews = reviewService.getCampgroundReviews(id);
        model.addAttribute(ATTRIBUTES.TITLE, campground.getTitle());
        model.addAttribute(ATTRIBUTES.CAMPGROUND, campground);
        model.addAttribute(ATTRIBUTES.REVIEWS, reviews);
        model.addAttribute(ATTRIBUTES.NEW_REVIEW, new Review());
        return VIEWS.CAMPGROUND_SHOW;
    }

    @PutMapping(ROUTES.CAMPGROUND)
    public String editCampground(@ModelAttribute @Valid Campground campground,
                                 @RequestParam("files") MultipartFile[] images,
                                 @Nullable @RequestParam("deleteImages") List<String> deleteImages,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) throws IOException {
        campgroundService.updateCampground(campground, images, deleteImages);
        redirectAttributes.addFlashAttribute(ATTRIBUTES.SUCCESS, "Campground updated successfully");
        return ROUTES.CAMPGROUND_INDEX_REDIRECT + campground.getId();
    }

    @DeleteMapping(ROUTES.CAMPGROUND)
    public String deleteCampground(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        campgroundService.deleteCampground(id);
        redirectAttributes.addFlashAttribute(ATTRIBUTES.SUCCESS, "Successfully Deleted Campground");
        return ROUTES.CAMPGROUND_INDEX_REDIRECT;
    }

    @GetMapping(ROUTES.CAMPGROUNDS_NEW)
    public String renderNewForm(Model model){
        model.addAttribute(ATTRIBUTES.CAMPGROUND, new Campground());
        model.addAttribute(ATTRIBUTES.TITLE, "New Campground");
        return VIEWS.CAMPGROUND_NEW;
    }

    @GetMapping(ROUTES.CAMPGROUNDS_EDIT)
    public String renderEditForm(@PathVariable Long id, Model model){
        var campground = campgroundService.getCampground(id);
        model.addAttribute(ATTRIBUTES.CAMPGROUND, campground);
        model.addAttribute(ATTRIBUTES.TITLE, campground.getTitle());
        return VIEWS.CAMPGROUND_EDIT;
    }

}
