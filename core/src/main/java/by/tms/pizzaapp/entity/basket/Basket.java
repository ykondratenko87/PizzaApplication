package by.tms.pizzaapp.entity.basket;

import by.tms.pizzaapp.entity.constructor.CustomPizza;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(schema = "pizzaapp", name = "baskets")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long count;
    private double totalPrice;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "pizzaapp", name = "basket_pizzas",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    private List<Pizza> pizzas;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "pizzaapp", name = "basket_orders",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "pizzaapp", name = "basket_custom_pizzas",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "custom_pizza_id"))
    private List<CustomPizza> customPizzas;
}