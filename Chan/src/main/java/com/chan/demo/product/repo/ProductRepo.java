package com.chan.demo.product.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.demo.product.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	
	public List<Product> findTop8ByOrderByIdAsc();
	
	public int countBy();
	public int countByNameLike(String name);
	public int countByDescriptionLike(String name);
	public int countByCategoryLike(String name);
	
	public List<Product> findAllByOrderByRegDateDesc(Pageable pageable);
	public List<Product> findAllByNameLikeOrderByRegDateDesc(String name, Pageable pageable);
	public List<Product> findAllByDescriptionLikeOrderByRegDateDesc(String description, Pageable pageable);
	public List<Product> findAllByCategoryLikeOrderByRegDateDesc(String category, Pageable pageable);
	
}
