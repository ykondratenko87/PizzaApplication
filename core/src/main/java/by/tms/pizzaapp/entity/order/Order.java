package by.tms.pizzaapp.entity.order;

import by.tms.pizzaapp.entity.basket.Basket;
import by.tms.pizzaapp.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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
}