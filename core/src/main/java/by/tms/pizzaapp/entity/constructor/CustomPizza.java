//package by.tms.pizzaapp.entity.constructor;
//
//import by.tms.pizzaapp.entity.ingredient.Ingredient;
//import by.tms.pizzaapp.entity.user.User;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//@Entity
//@Table(schema = "pizzaapp", name = "custom_pizzas")
//public class CustomPizza {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(nullable = false)
//    private double basePrice; //сумма цен ингридиентов
//
//    @Column(nullable = false)
//    private int quantity;
//
//    @ManyToMany
//    @JoinTable(
//            name = "custom_pizza_ingredients",
//            joinColumns = @JoinColumn(name = "custom_pizza_id"),
//            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
//    )
//    private List<Ingredient> ingredients; //возможно нужны ingredientsIds
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//}