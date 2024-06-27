package by.tms.pizzaapp.service.basket;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;
import by.tms.pizzaapp.entity.basket.Basket;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.order.OrderStatus;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.exception.UserNotFoundException;
import by.tms.pizzaapp.exception.PizzaNotFoundException;
import by.tms.pizzaapp.mapper.BasketMapper;
import by.tms.pizzaapp.repository.BasketRepository;
import by.tms.pizzaapp.repository.OrderRepository;
import by.tms.pizzaapp.repository.PizzaRepository;
import by.tms.pizzaapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final PizzaRepository pizzaRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BasketMapper basketMapper;

    @Override
    public BasketResponse addPizzaToBasket(BasketRequest basketRequest) {
        validateUserAndPizza(basketRequest.getUserId(), basketRequest.getPizzaId());

        Pizza pizza = pizzaRepository.getById(basketRequest.getPizzaId());
        Basket basket = basketRepository.findByUserId(basketRequest.getUserId())
                .orElseGet(() -> {
                    Basket newBasket = createNewBasket(basketRequest.getUserId());
                    Order newOrder = createNewOrder(basketRequest.getUserId());
                    newBasket.setOrders(new ArrayList<>());
                    newBasket.getOrders().add(newOrder);
                    orderRepository.save(newOrder); // Сохранить новый заказ в базу данных
                    basketRepository.save(newBasket); // Сохранить новую корзину
                    return newBasket;
                });

        basket.getPizzas().add(pizza);
        updateBasketDetails(basket, basketRequest.getCount(), pizza.getPrice());

        // Уменьшение количества пиццы в базе данных
        pizza.setQuantity(pizza.getQuantity() - 1);
        pizzaRepository.save(pizza); // Сохранить обновленное количество пиццы

        // Обновление заказа
        Order currentOrder = basket.getOrders().stream()
                .filter(order -> order.getStatus() == OrderStatus.ORDERING)
                .findFirst()
                .orElseGet(() -> {
                    Order newOrder = createNewOrder(basketRequest.getUserId());
                    newOrder.setTotalPrice(basket.getTotalPrice());
                    newOrder.setAddress(""); // Можно установить начальный адрес, если он имеется
                    basket.getOrders().add(newOrder);
                    orderRepository.save(newOrder);
                    return newOrder;
                });

        currentOrder.setTotalPrice(basket.getTotalPrice());

        basketRepository.save(basket);
        orderRepository.save(currentOrder);
        return basketMapper.toResponse(basket);
    }

    @Override
    public BasketResponse removePizzaFromBasket(Long basketId, Long pizzaId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NoSuchElementException("Basket not found"));
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new NoSuchElementException("Pizza not found"));

        if (basket.getPizzas().contains(pizza)) {
            basket.getPizzas().remove(pizza);
            updateBasketDetails(basket, -1, -pizza.getPrice());

            // Увеличение количества пиццы в базе данных
            pizza.setQuantity(pizza.getQuantity() + 1);
            pizzaRepository.save(pizza); // Сохранить обновленное количество пиццы

            // Обновление заказа
            Order currentOrder = basket.getOrders().stream()
                    .filter(order -> order.getStatus() == OrderStatus.ORDERING)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Current ordering order not found"));

            if (basket.getPizzas().isEmpty()) {
                // Удалить корзину и заказ, если корзина пуста
                basket.getOrders().clear(); // Удалить все заказы из корзины
                orderRepository.delete(currentOrder);
                basketRepository.delete(basket);
            } else {
                currentOrder.setTotalPrice(basket.getTotalPrice());
                basketRepository.save(basket); // Сохранить изменения в корзине
                orderRepository.save(currentOrder);
            }
        } else {
            throw new NoSuchElementException("Pizza not found in basket");
        }

        return basketMapper.toResponse(basket);
    }

    @Override
    public BasketResponse getBasketByUserId(Long userId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Basket not found"));
        return basketMapper.toResponse(basket);
    }

    private void validateUserAndPizza(Long userId, Long pizzaId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
        if (!pizzaRepository.existsById(pizzaId)) {
            throw new PizzaNotFoundException("Pizza not found");
        }
    }

    private Basket createNewBasket(Long userId) {
        Basket newBasket = new Basket();
        newBasket.setUser(userRepository.getById(userId));
        newBasket.setPizzas(new ArrayList<>());
        return newBasket;
    }

    private Order createNewOrder(Long userId) {
        Order newOrder = new Order();
        newOrder.setUser(userRepository.getById(userId));
        newOrder.setStatus(OrderStatus.ORDERING);
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setTotalPrice(0.0); // Изначально цена равна 0
        newOrder.setAddress(""); // Можно установить начальный адрес, если он имеется
        return newOrder;
    }

    private void updateBasketDetails(Basket basket, long countChange, double priceChange) {
        long newCount = basket.getCount() + countChange;
        double newTotalPrice = basket.getTotalPrice() + priceChange;

        basket.setCount(Math.max(newCount, 0)); // Ensure count is not negative
        basket.setTotalPrice(Math.max(newTotalPrice, 0)); // Ensure totalPrice is not negative
    }
}