package service;

import by.tms.pizzaapp.dto.ingredient.*;
import by.tms.pizzaapp.entity.ingredient.Ingredient;
import by.tms.pizzaapp.mapper.IngredientMapper;
import by.tms.pizzaapp.repository.IngredientRepository;
import by.tms.pizzaapp.service.ingredient.IngredientServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    @InjectMocks
    private IngredientServiceImpl ingredientServiceImpl;

    private Ingredient ingredient;
    private IngredientRequest ingredientRequest;
    private IngredientResponse ingredientResponse;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Tomato");
        ingredient.setPrice(1.0);
        ingredient.setPortion(1);

        ingredientRequest = new IngredientRequest();
        ingredientRequest.setName("Tomato");
        ingredientRequest.setPrice(1.0);
        ingredientRequest.setPortion(1);

        ingredientResponse = new IngredientResponse();
        ingredientResponse.setId(1L);
        ingredientResponse.setName("Tomato");
        ingredientResponse.setPrice(1.0);
        ingredientResponse.setPortion(1);
    }

    @Test
    void getAllIngredients() {
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient));
        when(ingredientMapper.toResponse(ingredient)).thenReturn(ingredientResponse);

        List<IngredientResponse> responses = ingredientServiceImpl.getAllIngredients();

        assertEquals(1, responses.size());
        assertEquals("Tomato", responses.get(0).getName());
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    void createIngredient() {
        when(ingredientRepository.findByNameAndPrice(ingredientRequest.getName(), ingredientRequest.getPrice()))
                .thenReturn(Optional.empty());
        when(ingredientMapper.toEntity(ingredientRequest)).thenReturn(ingredient);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapper.toResponse(ingredient)).thenReturn(ingredientResponse);

        IngredientResponse response = ingredientServiceImpl.createIngredient(ingredientRequest);

        assertEquals("Tomato", response.getName());
        verify(ingredientRepository, times(1)).findByNameAndPrice(ingredientRequest.getName(), ingredientRequest.getPrice());
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void deleteIngredientById() {
        doNothing().when(ingredientRepository).deleteById(1L);

        ingredientServiceImpl.deleteIngredientById(1L);

        verify(ingredientRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateIngredientById() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapper.toResponse(ingredient)).thenReturn(ingredientResponse);

        IngredientResponse response = ingredientServiceImpl.updateIngredientById(1L, ingredientRequest);

        assertEquals("Tomato", response.getName());
        verify(ingredientRepository, times(1)).findById(1L);
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void updateIngredientById_NotFound() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ingredientServiceImpl.updateIngredientById(1L, ingredientRequest);
        });

        assertEquals("Ingredient not found", exception.getMessage());
        verify(ingredientRepository, times(1)).findById(1L);
    }
}