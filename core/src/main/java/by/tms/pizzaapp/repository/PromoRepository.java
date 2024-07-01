package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.promo.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    Optional<Promo> findByName(String name);

    @Query("SELECT COUNT(p) > 0 FROM Promo p WHERE p.name = :name")
    boolean existsByName(@Param("name") String name);
}