package com.yelpcamp.controller;

public class ControllerConstants {

    public static class ROUTES{
        public static final String CAMPGROUNDS_INDEX = "/campgrounds/";
        public static final String CAMPGROUND_INDEX_REDIRECT = "redirect:/campgrounds/";
        public static final String CAMPGROUNDS_NEW = "/campgrounds/new";
        public static final String CAMPGROUND = "/campgrounds/{id}";
        public static final String CAMPGROUNDS_EDIT = "/campgrounds/{id}/edit";
        public static final String REVIEWS_NEW = "/campgrounds/{campgroundId}/reviews";
        public static final String REVIEWS_DELETE = "/campgrounds/{campgroundId}/reviews/{reviewId}";
        public static final String REGISTER = "/register";
        public static final String REGISTER_REDIRECT = "redirect:/register";
        public static final String LOGIN = "/login";
        public static final String LOGIN_REDIRECT = "redirect:/login";
        public static final String HOME = "/";
    }

    public static class VIEWS{
        public static final String CAMPGROUND_INDEX = "/campgrounds/index";
        public static final String CAMPGROUND_SHOW = "/campgrounds/show";
        public static final String CAMPGROUND_NEW = "/campgrounds/new";
        public static final String CAMPGROUND_EDIT = "/campgrounds/edit";
        public static final String ERROR = "/controllerError";
        public static final String NOT_FOUND = "/notfound";
        public static final String REGISTER = "/users/register";
        public static final String LOGIN = "/users/login";
        public static final String HOME = "/home";
    }

    public static class ATTRIBUTES{
        public static final String TITLE = "title";
        public static final String SUCCESS = "success";
        public static final String ERROR = "error";
        public static final String CAMPGROUNDS = "campgrounds";
        public static final String CAMPGROUND = "campground";
        public static final String NEW_REVIEW = "newReview";
        public static final String REVIEWS = "reviews";
        public static final String USER = "user";
    }

    public static class TITLES{
        public static final String CAMPGROUNDS = "Campgrounds";
    }
}
