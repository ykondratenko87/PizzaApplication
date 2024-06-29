package by.tms.pizzaapp.service.pizza;

import by.tms.pizzaapp.dto.pizza.*;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.mapper.PizzaMapper;
import by.tms.pizzaapp.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    @Override
    public List<PizzaResponse> getAllPizzas() {
        return pizzaRepository.findAll().stream().map(pizzaMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<PizzaResponse> getPizzaById(long id) {
        return pizzaRepository.findById(id).map(pizzaMapper::toResponse);
    }

    @Override
    public PizzaResponse createPizza(PizzaRequest pizzaRequest) {
        Optional<Pizza> existingPizza = pizzaRepository.findByNameAndDescriptionAndPrice(pizzaRequest.getName(), pizzaRequest.getDescription(), pizzaRequest.getPrice());
        Pizza savedPizza;
        if (existingPizza.isPresent()) {
            Pizza pizza = existingPizza.get();
            pizza.setQuantity(pizza.getQuantity() + pizzaRequest.getQuantity());
            savedPizza = pizzaRepository.save(pizza);
        } else {
            Pizza pizza = pizzaMapper.toEntity(pizzaRequest);
            savedPizza = pizzaRepository.save(pizza);
        }
        return pizzaMapper.toResponse(savedPizza);
    }

    @Override
    public void deletePizzaById(long id) {
        pizzaRepository.deleteById(id);
    }

    @Override
    public PizzaResponse updatePizza(long id, PizzaRequest pizzaRequest) {
        Pizza existingPizza = pizzaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pizza not found with id: " + id));
        existingPizza.setName(pizzaRequest.getName());
        existingPizza.setDescription(pizzaRequest.getDescription());
        existingPizza.setPrice(pizzaRequest.getPrice());
        existingPizza.setQuantity(pizzaRequest.getQuantity());
        Pizza updatedPizza = pizzaRepository.save(existingPizza);
        return pizzaMapper.toResponse(updatedPizza);
    }

    @Override
    public Optional<PizzaResponse> getPizzaByName(String name) {
        return pizzaRepository.findByName(name).map(pizzaMapper::toResponse);
    }

    @Override
    public Page<PizzaResponse> getAllPizzasWithPagination(Pageable pageable) {
        return pizzaRepository.findAll(pageable).map(pizzaMapper::toResponse);
    }
}