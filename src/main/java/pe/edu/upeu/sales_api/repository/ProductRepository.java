package pe.edu.upeu.sales_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sales_api.entity.Product;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAll(Pageable pageable);

    List<Product> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
            String name, String code);
}