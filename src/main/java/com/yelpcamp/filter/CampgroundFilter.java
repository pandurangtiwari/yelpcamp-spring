package com.yelpcamp.filter;

import com.yelpcamp.model.Campground;
import com.yelpcamp.service.CampgroundService;
import com.yelpcamp.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.yelpcamp.config.ConfigConstants.ROUTES_PATTERN;

@Component
public class CampgroundFilter extends OncePerRequestFilter {

    @Autowired
    private CampgroundService campgroundService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(!isAuthor(request)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "You do not have permission to do that");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        for(var requestType : getRestrictedRequests()){
            if(requestType.matches(request.getRequestURI(), request.getMethod()))
                return false;
        }
        return true;
    }

    private static List<RequestType> getRestrictedRequests(){
        List<RequestType> requestTypeList = new ArrayList<>();
        requestTypeList.add(new RequestType(ROUTES_PATTERN.CAMPGROUND_EDIT_FORM, RequestMethod.GET.name()));
        requestTypeList.add(new RequestType(ROUTES_PATTERN.CAMPGROUND, RequestMethod.PUT.name()));
        requestTypeList.add(new RequestType(ROUTES_PATTERN.CAMPGROUND, RequestMethod.DELETE.name()));
        return requestTypeList;
    }

    private boolean isAuthor(HttpServletRequest request) {
        var campground = getCampground(request);
        var loggedInUser = SecurityUtil.getLoggedInUser();
        return campground.getAuthor().getId().equals(loggedInUser.getId());
    }

    private Campground getCampground(HttpServletRequest request){
        Long campgroundId = getCampgroundId(request);
        return campgroundService.getCampground(campgroundId);
    }

    private static Long getCampgroundId(HttpServletRequest request){
        return Long.parseLong(request.getRequestURI().split("/")[2]);
    }
}
