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
import java.util.Optional;
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
        Optional<Ingredient> existingIngredientOpt = ingredientRepository
                .findByNameAndPrice(ingredientRequest.getName(), ingredientRequest.getPrice());

        Ingredient ingredient;
        if (existingIngredientOpt.isPresent()) {
            // Если ингредиент с таким же именем и ценой существует, увеличиваем порцию
            ingredient = existingIngredientOpt.get();
            ingredient.setPortion(ingredient.getPortion() + ingredientRequest.getPortion());
        } else {
            // Если ингредиент не найден, создаем новый
            ingredient = ingredientMapper.toEntity(ingredientRequest);
        }

        ingredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toResponse(ingredient);
    }

    @Override
    public void deleteIngredientById(Long id) {
        ingredientRepository.deleteById(id);
    }
    @Override
    public IngredientResponse updateIngredientById(Long id, IngredientRequest ingredientRequest) {
        Optional<Ingredient> existingIngredientOpt = ingredientRepository.findById(id);
        if (existingIngredientOpt.isPresent()) {
            Ingredient existingIngredient = existingIngredientOpt.get();
            existingIngredient.setName(ingredientRequest.getName());
            existingIngredient.setPrice(ingredientRequest.getPrice());
            existingIngredient.setPortion(ingredientRequest.getPortion());
            Ingredient updatedIngredient = ingredientRepository.save(existingIngredient);
            return ingredientMapper.toResponse(updatedIngredient);
        } else {
            // Handle the case where the ingredient is not found
            throw new RuntimeException("Ingredient not found");
        }
    }
}