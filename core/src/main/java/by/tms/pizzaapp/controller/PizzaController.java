package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.pizza.PizzaRequest;
import by.tms.pizzaapp.dto.pizza.PizzaResponse;
import by.tms.pizzaapp.service.pizza.PizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pizzas")
@Tag(name = "Pizza Controller", description = "Endpoints for managing pizza controller")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Operation(summary = "getAllPizzas")
    @GetMapping
    public List<PizzaResponse> getAllPizzas() {
        log.info("Fetching all pizzas");
        return pizzaService.getAllPizzas();
    }

    @Operation(summary = "getPizzaById")
    @GetMapping("/{id}")
    public PizzaResponse getPizzaById(@PathVariable long id) {
        log.info("Fetching pizza with id: {}", id);
        return pizzaService.getPizzaById(id).orElse(null);
    }

    @Operation(summary = "createPizza")
    @PostMapping
    public PizzaResponse createPizza(@Valid @RequestBody PizzaRequest pizzaRequest) {
        log.info("Creating new pizza with data: {}", pizzaRequest);
        return pizzaService.createPizza(pizzaRequest);
    }

    @Operation(summary = "deletePizza")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable long id) {
        log.info("Deleting pizza with id: {}", id);
        pizzaService.deletePizzaById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "updatePizza")
    @PutMapping("/{id}")
    public PizzaResponse updatePizza(@PathVariable long id, @Valid @RequestBody PizzaRequest pizzaRequest) {
        log.info("Updating pizza with id: {} and data: {}", id, pizzaRequest);
        return pizzaService.updatePizza(id, pizzaRequest);
    }

    @Operation(summary = "getPizzaByName")
    @GetMapping("/name/{name}")
    public PizzaResponse getPizzaByName(@PathVariable String name) {
        log.info("Fetching pizza with name: {}", name);
        return pizzaService.getPizzaByName(name).orElse(null);
    }

    @Operation(summary = "getAllPizzasWithPagination")
    @GetMapping("/paged")
    public Page<PizzaResponse> getAllPizzasWithPagination(Pageable pageable) {
        log.info("Fetching all pizzas with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return pizzaService.getAllPizzasWithPagination(pageable);
    }
}