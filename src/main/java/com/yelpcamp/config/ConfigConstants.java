package com.yelpcamp.config;

public class ConfigConstants {
    public static class ANT_MATCHERS {
        public static final String HOME = "/";
        public static final String REGISTER = "/register";
        public static final String LOGIN = "/login*";
        public static final String CAMPGROUNDS_INDEX = "/campgrounds/";
        public static final String CAMPGROUND = "/campgrounds/{id:[0-9]+}";
    }

    public static class ROUTES_PATTERN {
        public static final String CAMPGROUND = "/campgrounds/[0-9]+";
        public static final String CAMPGROUND_EDIT_FORM = "/campgrounds/[0-9]+/edit";
        public static final String REVIEW = "/campgrounds/[0-9]+/reviews/[0-9]+";
    }
}
