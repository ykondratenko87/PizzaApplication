package by.tms.pizzaapp.service.constructor;

import by.tms.pizzaapp.dto.constructor.CustomPizzaRequest;
import by.tms.pizzaapp.dto.constructor.CustomPizzaResponse;
import by.tms.pizzaapp.entity.constructor.CustomPizza;
import by.tms.pizzaapp.entity.ingredient.Ingredient;
import by.tms.pizzaapp.exception.IngredientNotFoundException;
import by.tms.pizzaapp.exception.UserNotFoundException;
import by.tms.pizzaapp.mapper.CustomPizzaMapper;
import by.tms.pizzaapp.repository.CustomPizzaRepository;
import by.tms.pizzaapp.repository.IngredientRepository;
import by.tms.pizzaapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomPizzaServiceImpl implements CustomPizzaService {
    private final CustomPizzaRepository customPizzaRepository;
    private final IngredientRepository ingredientRepository;
    private final CustomPizzaMapper customPizzaMapper;
    private final UserRepository userRepository;

    @Override
    public CustomPizzaResponse addIngredientToCustomPizza(CustomPizzaRequest customPizzaRequest) {
        validateUserAndCustomPizza(customPizzaRequest.getUserId(), customPizzaRequest.getIngredientId());

        Ingredient ingredient = ingredientRepository.getById(customPizzaRequest.getIngredientId());
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
}