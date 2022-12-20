package com.yelpcamp.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class YelpCampUser extends User{

    private Long id;
    private String name;
    private static final String USER_ROLE = "USER";
    private static final long serialVersionUID = -9067019942441810669L;

    public YelpCampUser(com.yelpcamp.model.User user){
        super(user.getEmail(), user.getPassword(),
                getAuthorities(Collections.singletonList(USER_ROLE)));
        this.id = user.getId();
        this.name = user.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        YelpCampUser yelpcampUser = (YelpCampUser) o;
        return id.equals(yelpcampUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "YelpCampUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
