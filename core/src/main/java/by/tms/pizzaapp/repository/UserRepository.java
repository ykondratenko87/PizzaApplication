package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.user.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
}