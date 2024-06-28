//package by.tms.pizzaapp.controller;
//
//import by.tms.pizzaapp.dto.pizza.CustomPizzaRequest;
//import by.tms.pizzaapp.dto.pizza.CustomPizzaResponse;
//import by.tms.pizzaapp.service.pizza.CustomPizzaService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/custom-pizzas")
//@Tag(name = "Custom Pizza Controller")
//@RequiredArgsConstructor
//public class CustomPizzaController {
//    private final CustomPizzaService customPizzaService;
//
//    @Operation(summary = "Create custom pizza")
//    @PostMapping
//    public CustomPizzaResponse createCustomPizza(@Valid @RequestBody CustomPizzaRequest customPizzaRequest) {
//        log.info("Creating custom pizza with request: {}", customPizzaRequest);
//        return customPizzaService.createCustomPizza(customPizzaRequest);
//    }
//}