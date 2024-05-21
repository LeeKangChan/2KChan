package com.chan.demo.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.chan.demo.product.service.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	
}
