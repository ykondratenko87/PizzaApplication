package by.tms.pizzaapp.service.ingredient;

import by.tms.pizzaapp.dto.ingredient.*;
import by.tms.pizzaapp.entity.ingredient.Ingredient;
import by.tms.pizzaapp.mapper.IngredientMapper;
import by.tms.pizzaapp.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ingredients")
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    @Cacheable(value = "ingredients")
    public List<IngredientResponse> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "ingredients", allEntries = true)
    public IngredientResponse createIngredient(IngredientRequest ingredientRequest) {
        Optional<Ingredient> existingIngredientOpt = ingredientRepository
                .findByNameAndPrice(ingredientRequest.getName(), ingredientRequest.getPrice());
        Ingredient ingredient;
        if (existingIngredientOpt.isPresent()) {
            ingredient = existingIngredientOpt.get();
            ingredient.setPortion(ingredient.getPortion() + ingredientRequest.getPortion());
        } else {
            ingredient = ingredientMapper.toEntity(ingredientRequest);
        }
        ingredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toResponse(ingredient);
    }

    @Override
    @CacheEvict(value = "ingredients", key = "#id")
    public void deleteIngredientById(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "ingredients", key = "#id")
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
            throw new RuntimeException("Ingredient not found");
        }
    }
}