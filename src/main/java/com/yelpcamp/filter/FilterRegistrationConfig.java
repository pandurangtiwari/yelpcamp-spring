package com.yelpcamp.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {

    @Autowired
    CampgroundFilter campgroundFilter;

    @Autowired
    ReviewFilter reviewFilter;

    @Bean
    public FilterRegistrationBean<SanitizeHtmlFilter> sanitizeHtmlFilter(){
        final String URL_PATTERN = "/*";
        FilterRegistrationBean<SanitizeHtmlFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SanitizeHtmlFilter());
        registrationBean.addUrlPatterns(URL_PATTERN);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CampgroundFilter> campgroundAuthorFilter(){
        final String CAMPGROUND_URL_PATTERN = "/campgrounds/*";
        FilterRegistrationBean<CampgroundFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(campgroundFilter);
        registrationBean.addUrlPatterns(CAMPGROUND_URL_PATTERN);
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ReviewFilter> reviewAuthorFilter(){
        final String REVIEW_URL_PATTERN = "/campgrounds/*";
        FilterRegistrationBean<ReviewFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(reviewFilter);
        registrationBean.addUrlPatterns(REVIEW_URL_PATTERN);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
