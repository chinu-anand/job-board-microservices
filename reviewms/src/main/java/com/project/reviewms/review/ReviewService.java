package com.project.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    boolean addReview(Long companyId, Review review);
    Review getReviewById(Long reviewId);
    boolean deleteReview(Long reviewId);
    boolean updateReview(Long reviewId, Review review);
}
