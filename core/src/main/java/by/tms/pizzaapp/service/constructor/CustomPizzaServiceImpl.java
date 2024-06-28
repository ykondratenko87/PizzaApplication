//package by.tms.pizzaapp.service.pizza;
//
//import by.tms.pizzaapp.dto.pizza.CustomPizzaRequest;
//import by.tms.pizzaapp.dto.pizza.CustomPizzaResponse;
//import by.tms.pizzaapp.entity.custompizza.CustomPizza;
//import by.tms.pizzaapp.entity.ingredient.Ingredient;
//import by.tms.pizzaapp.mapper.CustomPizzaMapper;
//import by.tms.pizzaapp.repository.CustomPizzaRepository;
//import by.tms.pizzaapp.repository.IngredientRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class CustomPizzaServiceImpl implements CustomPizzaService {
//
//    private final CustomPizzaRepository customPizzaRepository;
//    private final IngredientRepository ingredientRepository;
//    private final CustomPizzaMapper customPizzaMapper;
//
//    @Override
//    public CustomPizzaResponse createCustomPizza(CustomPizzaRequest customPizzaRequest) {
//        CustomPizza customPizza = new CustomPizza();
//        customPizza.setBasePrice(calculateBasePrice(customPizzaRequest.getIngredients()));
//        customPizza.setQuantity(customPizzaRequest.getQuantity());
//        // Set user (assuming you have a way to get the current user)
//        customPizza.setUser(/* get current user */);
//
//        // Map and save ingredients
//        List<Ingredient> ingredients = customPizzaRequest.getIngredients().stream()
//                .map(id -> ingredientRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingredient not found")))
//                .collect(Collectors.toList());
//        customPizza.setIngredients(ingredients);
//
//        customPizza = customPizzaRepository.save(customPizza);
//        return customPizzaMapper.toResponse(customPizza);
//    }
//
//    private double calculateBasePrice(List<Long> ingredientIds) {
//        // Implement your logic to calculate the base price based on ingredient prices
//        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
//        return ingredients.stream()
//                .mapToDouble(Ingredient::getPrice)
//                .sum();
//    }
//}