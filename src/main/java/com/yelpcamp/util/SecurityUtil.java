package com.yelpcamp.util;

import com.yelpcamp.model.YelpCampUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static YelpCampUser getLoggedInUser(){
        return (YelpCampUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
