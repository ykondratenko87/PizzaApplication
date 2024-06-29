package by.tms.pizzaapp.service.review;

import by.tms.pizzaapp.dto.review.*;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.entity.review.Review;
import by.tms.pizzaapp.mapper.ReviewMapper;
import by.tms.pizzaapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final PizzaRepository pizzaRepository;

    @Override
    @Cacheable(value = "reviews")
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream().map(reviewMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "reviews", key = "#id")
    public Optional<ReviewResponse> getReviewById(long id) {
        return reviewRepository.findById(id).map(reviewMapper::toResponse);
    }

    @Override
    @CacheEvict(value = "reviews", allEntries = true)
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        Pizza pizza = pizzaRepository.findById(reviewRequest.getPizzaId())
                .orElseThrow(() -> new IllegalArgumentException("Pizza with ID " + reviewRequest.getPizzaId() + " not found"));
        Review review = reviewMapper.toEntity(reviewRequest);
        review.setPizza(pizza);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toResponse(savedReview);
    }

    @Override
    @CacheEvict(value = "reviews", key = "#id")
    public boolean deleteReviewById(long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @CachePut(value = "reviews", key = "#id")
    public Optional<ReviewResponse> updateReview(long id, ReviewRequest reviewRequest) {
        Pizza pizza = pizzaRepository.findById(reviewRequest.getPizzaId())
                .orElseThrow(() -> new IllegalArgumentException("Pizza with ID " + reviewRequest.getPizzaId() + " not found"));
        return reviewRepository.findById(id).map(existingReview -> {
            existingReview.setDescription(reviewRequest.getDescription());
            existingReview.setPizza(pizza);
            return reviewMapper.toResponse(reviewRepository.save(existingReview));
        });
    }

    @Override
    @Cacheable(value = "reviews", key = "#pizzaName")
    public List<ReviewResponse> getReviewByPizzaName(String pizzaName) {
        Optional<Pizza> pizza = pizzaRepository.findByName(pizzaName);
        if (pizza.isPresent()) {
            return reviewRepository.findByPizza(pizza.get()).stream()
                    .map(reviewMapper::toResponse)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "reviews")
    public Page<ReviewResponse> getAllReviewsWithPagination(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toResponse);
    }
}