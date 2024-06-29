package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.review.*;
import by.tms.pizzaapp.service.review.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review")
@Tag(name = "Review Controller", description = "Endpoints for managing review controller")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "getAllReviews", description = "Получить все отзывы")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getAllReviews() {
        log.info("Fetching all reviews");
        return reviewService.getAllReviews();
    }

    @Operation(summary = "getReviewById", description = "Получить отзыв по ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable long id) {
        log.info("Fetching review with id: {}", id);
        return reviewService.getReviewById(id).map(ResponseEntity::ok).orElseGet(() -> {
            log.warn("Review with id: {} not found", id);
            return ResponseEntity.notFound().build();
        });
    }

    @Operation(summary = "createReview", description = "Добавить отзыв")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        log.info("Creating new review with data: {}", reviewRequest);
        ReviewResponse createdReview = reviewService.createReview(reviewRequest);
        return ResponseEntity.ok(createdReview);
    }

    @Operation(summary = "deleteReviewById", description = "Удалить отзыв по ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteReviewById(@PathVariable long id) {
        log.info("Deleting review with id: {}", id);
        if (reviewService.deleteReviewById(id)) {
            log.info("Review with id: {} deleted successfully", id);
            return ResponseEntity.ok("Review with ID " + id + " deleted successfully");
        } else {
            log.warn("Review with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "updateReview", description = "Обновить отзыв")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable long id, @Valid @RequestBody ReviewRequest reviewRequest) {
        log.info("Updating review with id: {} and data: {}", id, reviewRequest);
        return reviewService.updateReview(id, reviewRequest).map(ResponseEntity::ok).orElseGet(() -> {
            log.warn("Review with id: {} not found", id);
            return ResponseEntity.notFound().build();
        });
    }

    @Operation(summary = "getReviewByPizzaName", description = "Получить отзыв по названию пиццы")
    @GetMapping("/pizza/{pizzaName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReviewResponse>> getReviewByPizzaName(@PathVariable String pizzaName) {
        log.info("Fetching reviews for pizza: {}", pizzaName);
        List<ReviewResponse> reviewResponses = reviewService.getReviewByPizzaName(pizzaName);
        if (reviewResponses.isEmpty()) {
            log.warn("Reviews for pizza: {} not found", pizzaName);
            throw new IllegalArgumentException("Reviews for pizza: " + pizzaName + " not found");
        }
        return ResponseEntity.ok(reviewResponses);
    }

    @Operation(summary = "getAllReviewsWithPagination", description = "Получить все отзывы с пагинациецй")
    @GetMapping("/paged")
    @ResponseStatus(HttpStatus.OK)
    public Page<ReviewResponse> getAllReviewsWithPagination(Pageable pageable) {
        log.info("Fetching all reviews with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return reviewService.getAllReviewsWithPagination(pageable);
    }
}