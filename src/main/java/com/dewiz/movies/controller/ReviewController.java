package com.dewiz.movies.controller;

import com.dewiz.movies.entity.Review;
import com.dewiz.movies.service.MovieService;
import com.dewiz.movies.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final MovieService movieService;
    private static final String IMDB_ID = "imdbId";
    private static final String REVIEW_BODY = "reviewBody";

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(
                reviewService.createReview(payload.get(REVIEW_BODY), payload.get(IMDB_ID)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{imdbId}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable String imdbId) {
        return new ResponseEntity<>(movieService.getReviewsByImdbId(imdbId), HttpStatus.FOUND);
    }
}
