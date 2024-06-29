package by.tms.pizzaapp.service.constructor;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;
import by.tms.pizzaapp.dto.constructor.CustomPizzaRequest;
import by.tms.pizzaapp.dto.constructor.CustomPizzaResponse;
import by.tms.pizzaapp.entity.basket.Basket;
import by.tms.pizzaapp.entity.constructor.CustomPizza;
import by.tms.pizzaapp.entity.ingredient.Ingredient;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.order.OrderStatus;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.exception.IngredientNotFoundException;
import by.tms.pizzaapp.exception.UserNotFoundException;
import by.tms.pizzaapp.mapper.BasketMapper;
import by.tms.pizzaapp.mapper.CustomPizzaMapper;
import by.tms.pizzaapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomPizzaServiceImpl implements CustomPizzaService {
    private final CustomPizzaRepository customPizzaRepository;
    private final IngredientRepository ingredientRepository;
    private final CustomPizzaMapper customPizzaMapper;
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final OrderRepository orderRepository;
    private final BasketMapper basketMapper;

    @Override
    public CustomPizzaResponse addIngredientToCustomPizza(CustomPizzaRequest customPizzaRequest) {
        validateUserAndCustomPizza(customPizzaRequest.getUserId(), customPizzaRequest.getIngredientId());

        Ingredient ingredient = ingredientRepository.findById(customPizzaRequest.getIngredientId())
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found"));

        // Check if enough portions of the ingredient are available
        if (ingredient.getPortion() < 1) {
            throw new IngredientNotFoundException("Not enough portions of the ingredient available");
        }

        CustomPizza customPizza = customPizzaRepository.findByUserId(customPizzaRequest.getUserId())
                .orElseGet(() -> {
                    CustomPizza newCustomPizza = createNewCustomPizza(customPizzaRequest.getUserId());
                    customPizzaRepository.save(newCustomPizza);
                    return newCustomPizza;
                });

        customPizza.getIngredients().add(ingredient);
        updateCustomPizzaDetails(customPizza, customPizzaRequest.getCount(), ingredient.getPrice());

        ingredient.setPortion(ingredient.getPortion() - 1);
        ingredientRepository.save(ingredient);

        customPizzaRepository.save(customPizza);
        return customPizzaMapper.toResponse(customPizza);
    }

    private void validateUserAndCustomPizza(Long userId, Long ingredientId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
        if (!ingredientRepository.existsById(ingredientId)) {
            throw new IngredientNotFoundException("Ingredient not found");
        }
    }

    private CustomPizza createNewCustomPizza(Long userId) {
        CustomPizza newCustomPizza = new CustomPizza();
        newCustomPizza.setUser(userRepository.getById(userId));
        newCustomPizza.setIngredients(new ArrayList<>());
        return newCustomPizza;
    }

    private void updateCustomPizzaDetails(CustomPizza customPizza, long countChange, double priceChange) {
        long newCount = customPizza.getCount() + countChange;
        double newTotalPrice = customPizza.getTotalSum() + priceChange;

        customPizza.setCount(Math.max(newCount, 0)); // Ensure count is not negative
        customPizza.setTotalSum(Math.max(newTotalPrice, 0)); // Ensure totalPrice is not negative
    }

    @Override
    public CustomPizzaResponse removeIngredientFromCustomPizza(Long customPizzaId, Long ingredientId) {
        CustomPizza customPizza = customPizzaRepository.findById(customPizzaId)
                .orElseThrow(() -> new NoSuchElementException("CustomPizza not found"));
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new NoSuchElementException("Ingredient not found"));

        if (customPizza.getIngredients().contains(ingredient)) {
            customPizza.getIngredients().remove(ingredient);
            updateCustomPizzaDetails(customPizza, -1, -ingredient.getPrice());

            ingredient.setPortion(ingredient.getPortion() + 1);
            ingredientRepository.save(ingredient);

            if (customPizza.getIngredients().isEmpty()) {
                customPizzaRepository.delete(customPizza);
            } else {
                customPizzaRepository.save(customPizza);
            }
        } else {
            throw new NoSuchElementException("Ingredient not found in customPizza");
        }

        return customPizzaMapper.toResponse(customPizza);
    }
    @Override
    public BasketResponse addCustomPizzaToBasket(Long customPizzaId, BasketRequest basketRequest) {
        validateUser(customPizzaId, basketRequest.getUserId());

        CustomPizza customPizza = customPizzaRepository.findById(customPizzaId)
                .orElseThrow(() -> new NoSuchElementException("CustomPizza not found"));
        Basket basket = basketRepository.findByUserId(basketRequest.getUserId())
                .orElseGet(() -> {
                    Basket newBasket = createNewBasket(basketRequest.getUserId());
                    Order newOrder = createNewOrder(basketRequest.getUserId());
                    newBasket.setOrders(new ArrayList<>());
                    newBasket.getOrders().add(newOrder);
                    orderRepository.save(newOrder);
                    basketRepository.save(newBasket);
                    return newBasket;
                });

        basket.getCustomPizzas().add(customPizza);
        updateBasketDetails(basket, basketRequest.getCount(), customPizza.getTotalSum());

        Order currentOrder = basket.getOrders().stream()
                .filter(order -> order.getStatus() == OrderStatus.ORDERING)
                .findFirst()
                .orElseGet(() -> {
                    Order newOrder = createNewOrder(basketRequest.getUserId());
                    newOrder.setTotalPrice(basket.getTotalPrice());
                    newOrder.setAddress("");
                    basket.getOrders().add(newOrder);
                    orderRepository.save(newOrder);
                    return newOrder;
                });

        currentOrder.setTotalPrice(basket.getTotalPrice());

        basketRepository.save(basket);
        orderRepository.save(currentOrder);
        return basketMapper.toResponse(basket);
    }

    private void validateUser(Long customPizzaId, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
        if (!customPizzaRepository.existsById(customPizzaId)) {
            throw new NoSuchElementException("CustomPizza not found");
        }
    }

    private Basket createNewBasket(Long userId) {
        Basket newBasket = new Basket();
        newBasket.setUser(userRepository.getById(userId));
        newBasket.setCustomPizzas(new ArrayList<>());
        return newBasket;
    }

    private Order createNewOrder(Long userId) {
        Order newOrder = new Order();
        newOrder.setUser(userRepository.getById(userId));
        newOrder.setStatus(OrderStatus.ORDERING);
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setTotalPrice(0.0);
        newOrder.setAddress("");
        return newOrder;
    }

    private void updateBasketDetails(Basket basket, long countChange, double priceChange) {
        long newCount = basket.getCount() + countChange;
        double newTotalPrice = basket.getTotalPrice() + priceChange;

        basket.setCount(Math.max(newCount, 0));
        basket.setTotalPrice(Math.max(newTotalPrice, 0));
    }
}