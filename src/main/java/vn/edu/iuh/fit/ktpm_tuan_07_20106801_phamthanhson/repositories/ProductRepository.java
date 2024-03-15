package vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}