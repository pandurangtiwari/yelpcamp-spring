package com.yelpcamp.seed;

import com.yelpcamp.config.DataConfig;
import com.yelpcamp.model.Campground;
import com.yelpcamp.model.Review;
import com.yelpcamp.repository.CampgroundRepository;
import com.yelpcamp.repository.ImageRepository;
import com.yelpcamp.repository.ReviewRepository;
import com.yelpcamp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeedHelperTest {

    private ApplicationContext context;
    private CampgroundRepository campgroundRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void buildContext(){
        context = new AnnotationConfigApplicationContext(DataConfig.class);
        campgroundRepository = context.getBean(CampgroundRepository.class);
        userRepository = context.getBean(UserRepository.class);
    }

    @AfterEach
    public void destroyContext(){
        context = null;
        campgroundRepository = null;
        userRepository = null;
    }


    @Test
    @Disabled
    public void seedDBTest(){
        SeedHelper.seedDB(campgroundRepository, userRepository);
    }

    @Test
    @Disabled
    public void fetchReviewsAuthorTest(){
        var reviewRepository = context.getBean(ReviewRepository.class);
        List<Review> reviews = reviewRepository.findByCampgroundId(5L);
    }

    @Test
    @Disabled
    public void fetchCampgroundAuthorTest(){
        Optional<Campground> campground = campgroundRepository.findById(5L);
    }

    @Test
    @Disabled
    public void fetchReviewTest(){
        var reviewRepository = context.getBean(ReviewRepository.class);
        reviewRepository.findById(1L);
    }

    @Test
    @Disabled
    public void fetchUserTest(){
        var user = userRepository.findByEmail("xyz' OR true --");
        System.out.println(user);
    }

    @Test
    @Disabled
    public void getCspDirectives(){
        final String SCRIPT_SRC = "script-src 'self' 'strict-dynamic' 'nonce-rAnd0m123' 'unsafe-inline' http: https: https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com/;";
        final String STYLE_SRC = "style-src 'unsafe-inline' 'self' https://stackpath.bootstrapcdn.com;";
        final String IMG_SRC = "img-src 'self' blob: data: https://res.cloudinary.com/dqbrojvar/ https://images.unsplash.com/;";
        final String OBJECT_SRC = "object-src 'none';";
        final String BASE_URI = "base-uri 'none';";
        final String REQUIRE_TRUSTED_TYPES_FOR = "require-trusted-types-for 'script';";

        List<String> cspDirectives = new ArrayList<>();
        cspDirectives.add(SCRIPT_SRC); cspDirectives.add(STYLE_SRC); cspDirectives.add(IMG_SRC);
        cspDirectives.add(OBJECT_SRC); cspDirectives.add(BASE_URI); cspDirectives.add(REQUIRE_TRUSTED_TYPES_FOR);

        System.out.println(String.join("", cspDirectives));
    }

}
