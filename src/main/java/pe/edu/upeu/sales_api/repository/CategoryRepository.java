package pe.edu.upeu.sales_api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sales_api.entity.Category;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Category> findByNameContainingIgnoreCase(String name);

}
