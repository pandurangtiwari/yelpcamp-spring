package com.yelpcamp.repository;

import com.yelpcamp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    //Pagination to be added
    @Query(value = "Select r from Review r join fetch r.author where r.campground.id = :campgroundId")
    List<Review> findByCampgroundId(@Param("campgroundId") Long campgroundId);

    @Modifying
    @Query(value = "Delete from Review r where r.campground.id = :campgroundId")
    void deleteByCampgroundId(@Param("campgroundId") Long campgroundId);
}
