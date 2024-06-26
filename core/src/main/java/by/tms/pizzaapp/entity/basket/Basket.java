package by.tms.pizzaapp.entity.basket;

import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.pizza.Pizza;
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
    @Column(nullable = false)
    private Long count;
    @Column(name = "total_price", nullable = false)
    private double totalPrice;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "pizzaapp", name = "basket_pizza", joinColumns = @JoinColumn(name = "basket_id"), inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    private List<Pizza> pizzas;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "pizzaapp", name = "basket_order", joinColumns = @JoinColumn(name = "basket_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;
}