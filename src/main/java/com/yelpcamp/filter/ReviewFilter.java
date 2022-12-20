package com.yelpcamp.filter;

import com.yelpcamp.config.ConfigConstants.ROUTES_PATTERN;
import com.yelpcamp.model.Review;
import com.yelpcamp.service.ReviewService;
import com.yelpcamp.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewFilter extends OncePerRequestFilter {

    @Autowired
    ReviewService reviewService;

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
        requestTypeList.add(new RequestType(ROUTES_PATTERN.REVIEW, HttpMethod.DELETE.name()));
        return requestTypeList;
    }

    private boolean isAuthor(HttpServletRequest request){
        var review = getReview(request);
        var loggedInUser = SecurityUtil.getLoggedInUser();
        return review.getAuthor().getId().equals(loggedInUser.getId());
    }

    private Review getReview(HttpServletRequest request){
        return reviewService.getReview(getReviewId(request));
    }

    private static Long getReviewId(HttpServletRequest request){
        return Long.parseLong(request.getRequestURI().split("/")[4]);
    }
}
