package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.promo.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    Optional<Promo> findByName(String name);
}