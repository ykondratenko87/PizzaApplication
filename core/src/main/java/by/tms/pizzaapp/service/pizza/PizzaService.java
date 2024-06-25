package by.tms.pizzaapp.service.pizza;

import by.tms.pizzaapp.dto.pizza.PizzaRequest;
import by.tms.pizzaapp.dto.pizza.PizzaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PizzaService {
    List<PizzaResponse> getAllPizzas();

    Optional<PizzaResponse> getPizzaById(long id);

    PizzaResponse createPizza(PizzaRequest pizzaRequest);

    void deletePizzaById(long id);

    PizzaResponse updatePizza(long id, PizzaRequest pizzaRequest);

    Optional<PizzaResponse> getPizzaByName(String name);

    Page<PizzaResponse> getAllPizzasWithPagination(Pageable pageable);
}