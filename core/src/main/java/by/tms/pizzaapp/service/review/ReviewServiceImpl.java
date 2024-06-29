package by.tms.pizzaapp.service.review;

import by.tms.pizzaapp.dto.review.*;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.entity.review.Review;
import by.tms.pizzaapp.mapper.ReviewMapper;
import by.tms.pizzaapp.repository.*;
import lombok.RequiredArgsConstructor;
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
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream().map(reviewMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<ReviewResponse> getReviewById(long id) {
        return reviewRepository.findById(id).map(reviewMapper::toResponse);
    }

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        Pizza pizza = pizzaRepository.findById(reviewRequest.getPizzaId())
                .orElseThrow(() -> new IllegalArgumentException("Pizza with ID " + reviewRequest.getPizzaId() + " not found"));
        Review review = reviewMapper.toEntity(reviewRequest);
        review.setPizza(pizza);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toResponse(savedReview);
    }

    @Override
    public boolean deleteReviewById(long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
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
    public Page<ReviewResponse> getAllReviewsWithPagination(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toResponse);
    }
}