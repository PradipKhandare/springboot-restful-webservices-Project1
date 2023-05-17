package net.javaguides.springboot.repositary;

import net.javaguides.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositary extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
