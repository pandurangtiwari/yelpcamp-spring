package com.yelpcamp.controller;

import com.yelpcamp.model.Review;
import com.yelpcamp.model.YelpCampUser;
import com.yelpcamp.service.CampgroundService;
import com.yelpcamp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.yelpcamp.controller.ControllerConstants.ATTRIBUTES;
import static com.yelpcamp.controller.ControllerConstants.ROUTES;

@Controller
public class ReviewController {

    @Autowired
    private CampgroundService campgroundService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping(ROUTES.REVIEWS_NEW)
    public String createReview(@PathVariable Long campgroundId,
                               @ModelAttribute @Valid Review review,
                               @AuthenticationPrincipal YelpCampUser user,
                               RedirectAttributes redirectAttributes){
        reviewService.addReview(review, campgroundId, user);
        redirectAttributes.addFlashAttribute(ATTRIBUTES.SUCCESS, "Review added successfully");
        return ROUTES.CAMPGROUND_INDEX_REDIRECT + campgroundId;
    }

    @DeleteMapping(ROUTES.REVIEWS_DELETE)
    public String deleteReview(@PathVariable Long campgroundId,
                               @PathVariable Long reviewId,
                               RedirectAttributes redirectAttributes){
        reviewService.deleteReview(reviewId);
        redirectAttributes.addFlashAttribute(ATTRIBUTES.SUCCESS, "Review Deleted Successfully");
        return ROUTES.CAMPGROUND_INDEX_REDIRECT + campgroundId;
    }
}
