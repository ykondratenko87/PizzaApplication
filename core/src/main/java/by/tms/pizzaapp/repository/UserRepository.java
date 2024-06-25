package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.user.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByLogin(String login);
}