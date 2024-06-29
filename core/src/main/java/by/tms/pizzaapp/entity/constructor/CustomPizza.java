package by.tms.pizzaapp.entity.constructor;

import by.tms.pizzaapp.entity.ingredient.Ingredient;
import by.tms.pizzaapp.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(schema = "pizzaapp", name = "custom_pizzas")
public class CustomPizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long count;
    private double totalSum;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "pizzaapp", name = "customPizza_ingredients",
            joinColumns = @JoinColumn(name = "customPizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}