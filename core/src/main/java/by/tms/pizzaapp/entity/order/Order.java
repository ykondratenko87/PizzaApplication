package by.tms.pizzaapp.entity.order;

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
    @Column(name = "pizza_price", nullable = false)
    private double pizzaPrice;
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String address;
}