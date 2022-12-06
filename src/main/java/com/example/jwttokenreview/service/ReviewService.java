package com.example.jwttokenreview.service;

import com.example.jwttokenreview.domain.entity.Review;
import com.example.jwttokenreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public String addReview(String username, String content) {
        Review review = Review.builder()
                .username(username)
                .content(content)
                .build();
        reviewRepository.save(review);
        return username + "님의 리뷰가 등록되었습니다.";
    }
}
