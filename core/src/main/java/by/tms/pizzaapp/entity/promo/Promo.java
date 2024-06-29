package by.tms.pizzaapp.entity.promo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "pizzaapp", name = "promo")
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
}