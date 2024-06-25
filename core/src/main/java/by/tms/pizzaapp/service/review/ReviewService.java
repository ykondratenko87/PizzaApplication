package by.tms.pizzaapp.service.review;

import by.tms.pizzaapp.dto.review.ReviewRequest;
import by.tms.pizzaapp.dto.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewResponse> getAllReviews();

    Optional<ReviewResponse> getReviewById(long id);

    ReviewResponse createReview(ReviewRequest reviewRequest);

    boolean deleteReviewById(long id);

    Optional<ReviewResponse> updateReview(long id, ReviewRequest reviewRequest);

    List<ReviewResponse> getReviewByPizzaName(String pizzaName);

    Page<ReviewResponse> getAllReviewsWithPagination(Pageable pageable);
}