package com.yelpcamp.repository;

import com.yelpcamp.model.Campground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CampgroundRepository extends JpaRepository<Campground, Long> {

    @Query(value = "Select c from Campground c join fetch c.images")
    Set<Campground> getAllCampgrounds();
}
