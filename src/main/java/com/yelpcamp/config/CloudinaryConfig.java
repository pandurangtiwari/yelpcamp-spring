package com.yelpcamp.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CloudinaryConfig {

    @Autowired
    Environment env;

    @Bean()
    public Cloudinary cloudinary(){
        final String CLOUDINARY_URL = "CLOUDINARY_URL";
        return new Cloudinary(env.getProperty(CLOUDINARY_URL));
    }
}
