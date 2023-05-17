package com.dewiz.movies.service;

import com.dewiz.movies.entity.Movie;
import com.dewiz.movies.entity.Review;
import com.dewiz.movies.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final MongoTemplate mongoTemplate;
    private static final String IMDB_ID_FIELD = "imdbId";
    private static final String REVIEW_IDS_FIELD = "reviewIds";

    public Review createReview(String reviewBody, String imdbID) {
        Review review = new Review();
        review.setBody(reviewBody);
        repository.insert(review);

        mongoTemplate
                .update(Movie.class)
                .matching(Criteria.where(IMDB_ID_FIELD).is(imdbID))
                .apply(new Update().push(REVIEW_IDS_FIELD, review))
                .first();

        return review;
    }
}
