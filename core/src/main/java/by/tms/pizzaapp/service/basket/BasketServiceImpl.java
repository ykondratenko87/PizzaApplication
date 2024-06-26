package by.tms.pizzaapp.service.basket;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;
import by.tms.pizzaapp.entity.basket.Basket;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.mapper.BasketMapper;
import by.tms.pizzaapp.repository.BasketRepository;
import by.tms.pizzaapp.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final PizzaRepository pizzaRepository;
    private final BasketMapper basketMapper;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository, PizzaRepository pizzaRepository, BasketMapper basketMapper) {
        this.basketRepository = basketRepository;
        this.pizzaRepository = pizzaRepository;
        this.basketMapper = basketMapper;
    }

    @Override
    public List<BasketResponse> getAllBaskets() {
        return basketRepository.findAll().stream().map(basketMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<BasketResponse> getBasketById(long id) {
        return basketRepository.findById(id).map(basketMapper::toResponse);
    }

    @Override
    public BasketResponse createBasket(BasketRequest basketRequest) {
        Basket basket = basketMapper.toEntity(basketRequest);

        // Получаем все пиццы по их id
        List<Pizza> pizzas = pizzaRepository.findAllById(basketRequest.getPizzaIds());

        // Проверяем, что количество найденных пицц равно количеству id
        if (pizzas.size() != basketRequest.getPizzaIds().size()) {
            // Если найдена не вся пицца, можно выбросить исключение или обработать ошибку
            throw new IllegalArgumentException("One or more pizzas were not found");
        }

        basket.setPizzas(pizzas);
        basket.setTotalPrice(pizzas.stream().mapToDouble(Pizza::getPrice).sum());
        Basket savedBasket = basketRepository.save(basket);
        return basketMapper.toResponse(savedBasket);
    }

    @Override
    public void clearBasket(Long basketId) {
        basketRepository.deleteById(basketId);
    }
}