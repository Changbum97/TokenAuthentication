package com.example.jwttokenreview.repository;

import com.example.jwttokenreview.domain.entity.Review;
import com.example.jwttokenreview.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
