package com.yelpcamp.service;

import com.yelpcamp.exception.ResourceNotFoundException;
import com.yelpcamp.model.Campground;
import com.yelpcamp.model.Review;
import com.yelpcamp.model.User;
import com.yelpcamp.model.YelpCampUser;
import com.yelpcamp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Transactional
    public Review addReview(Review review, Long campgroundId, YelpCampUser loggedInUser){
        var author = new User(); author.setId(loggedInUser.getId());
        var campground = new Campground(); campground.setId(campgroundId);
        review.setAuthor(author);
        review.setCampground(campground);
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
    }

    @Transactional
    public List<Review> getCampgroundReviews(Long campgroundId){
        return reviewRepository.findByCampgroundId(campgroundId);
    }

    @Transactional
    public void deleteCampgroundReviews(Long campgroundId){
        reviewRepository.deleteByCampgroundId(campgroundId);
    }

    @Transactional
    public Review getReview(Long id){
        var review = reviewRepository.findById(id);
        if(review.isPresent()) return review.get();
        throw new ResourceNotFoundException("Review Not Found");
    }
}
