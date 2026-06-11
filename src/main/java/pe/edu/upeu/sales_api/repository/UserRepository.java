package pe.edu.upeu.sales_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sales_api.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}