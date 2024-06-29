package by.tms.pizzaapp.entity.ingredient;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "pizzaapp", name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int portion;
}