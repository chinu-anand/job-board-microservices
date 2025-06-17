package com.project.reviewms.review;

import com.project.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    private final ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        boolean isReviewAdded = reviewService.addReview(companyId, review);
        if(!isReviewAdded) {
            return new ResponseEntity<>("Review Not Saved", HttpStatus.NOT_FOUND);
        }

        reviewMessageProducer.sendMessage(review);
        return new ResponseEntity<>("Review Saved Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        if(review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        boolean isDeleted = reviewService.deleteReview(reviewId);

        if(!isDeleted) {
            return new ResponseEntity<>("Unable to Delete the review", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Review Deleted", HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        boolean isReviewAdded = reviewService.updateReview(reviewId, review);
        if(!isReviewAdded) {
            return new ResponseEntity<>("Review Not Updated", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Review Updated Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/averageRating")
    public double getAverageRating(@RequestParam Long companyId) {
        List<Review> reviewList = reviewService.getAllReviews(companyId);

        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

}

