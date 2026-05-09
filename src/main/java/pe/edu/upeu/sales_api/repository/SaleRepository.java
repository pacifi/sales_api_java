package pe.edu.upeu.sales_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upeu.sales_api.entity.Sale;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {

    Page<Sale> findAll(Pageable pageable);

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Sale s")
    java.math.BigDecimal sumTotalRevenue();
}