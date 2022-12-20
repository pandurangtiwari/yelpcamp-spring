package com.yelpcamp.config;

import com.yelpcamp.service.YelpCampUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static com.yelpcamp.controller.ControllerConstants.ROUTES;
import static com.yelpcamp.config.ConfigConstants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .antMatchers(ANT_MATCHERS.HOME, ANT_MATCHERS.REGISTER, ANT_MATCHERS.LOGIN).permitAll()
            .antMatchers(HttpMethod.GET,  ANT_MATCHERS.CAMPGROUNDS_INDEX, ANT_MATCHERS.CAMPGROUND).permitAll()
            .anyRequest().authenticated()
            .and().formLogin().loginPage(ROUTES.LOGIN)
            .and().logout().invalidateHttpSession(true)
            .and().headers(WebSecurityConfig::headerConfig);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new YelpCampUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static void headerConfig(HeadersConfigurer<HttpSecurity> headers) {
        headers.contentSecurityPolicy(
            cspConfig -> cspConfig.policyDirectives(getCspDirectives()));
    }

    private static String getCspDirectives(){
        final String SCRIPT_SRC = "script-src 'self' https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js https://stackpath.bootstrapcdn.com;";
        final String STYLE_SRC = "style-src 'unsafe-inline' 'self' https://stackpath.bootstrapcdn.com;";
        final String IMG_SRC = "img-src 'self' blob: data: https://res.cloudinary.com/dqbrojvar/ https://images.unsplash.com/;";
        final String OBJECT_SRC = "object-src 'none';";
        final String BASE_URI = "base-uri 'none';";
        final String REQUIRE_TRUSTED_TYPES_FOR = "require-trusted-types-for 'script';";
        final String FORM_ACTION = "form-action 'self';";

        List<String> cspDirectives = new ArrayList<>();
        cspDirectives.add(SCRIPT_SRC); cspDirectives.add(STYLE_SRC); cspDirectives.add(IMG_SRC);
        cspDirectives.add(OBJECT_SRC); cspDirectives.add(BASE_URI);
        cspDirectives.add(REQUIRE_TRUSTED_TYPES_FOR); cspDirectives.add(FORM_ACTION);

        return String.join("", cspDirectives);
    }
}
