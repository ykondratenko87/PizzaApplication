package by.tms.pizzaapp.entity.order;

import by.tms.pizzaapp.entity.basket.Basket;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(schema = "pizzaapp", name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String address;
    private double totalPrice;
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "pizzaapp", name = "order_pizzas",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    private List<Pizza> pizzas;
}