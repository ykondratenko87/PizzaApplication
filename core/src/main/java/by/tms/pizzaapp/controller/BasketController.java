package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;
import by.tms.pizzaapp.service.basket.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/baskets")
@Tag(name = "Basket Controller")
public class BasketController {
    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @Operation(summary = "getAllBaskets")
    @GetMapping
    public List<BasketResponse> getAllBaskets() {
        log.info("Fetching all baskets");
        return basketService.getAllBaskets();
    }

    @Operation(summary = "getBasketById")
    @GetMapping("/{id}")
    public BasketResponse getBasketById(@PathVariable long id) {
        log.info("Fetching basket with id: {}", id);
        return basketService.getBasketById(id).orElse(null);
    }

    @Operation(summary = "createBasket")
    @PostMapping
    public BasketResponse createBasket(@Valid @RequestBody BasketRequest basketRequest) {
        log.info("Creating new basket with data: {}", basketRequest);
        return basketService.createBasket(basketRequest);
    }

    @Operation(summary = "clearBasket")
    @DeleteMapping("/{id}")
    public void clearBasket(@PathVariable long id) {
        log.info("Clearing basket with id: {}", id);
        basketService.clearBasket(id);
    }
}