package by.tms.pizzaapp.service.ingredient;

import by.tms.pizzaapp.dto.ingredient.IngredientRequest;
import by.tms.pizzaapp.dto.ingredient.IngredientResponse;
import by.tms.pizzaapp.entity.ingredient.Ingredient;
import by.tms.pizzaapp.mapper.IngredientMapper;
import by.tms.pizzaapp.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public List<IngredientResponse> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientResponse createIngredient(IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientMapper.toEntity(ingredientRequest);
        ingredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toResponse(ingredient);
    }
}