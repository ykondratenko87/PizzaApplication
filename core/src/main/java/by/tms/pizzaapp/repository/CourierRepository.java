package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.courier.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> findByLogin(String login);
}