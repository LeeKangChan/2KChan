package com.chan.demo.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.chan.demo.product.entity.ProductReply;

import jakarta.transaction.Transactional;

import java.util.List;


@Repository
public interface ProductReplyRepo extends JpaRepository<ProductReply, Long> {
	public List<ProductReply> findByProductId(int productId);
	
	public int countById(Long id);
	
	@Transactional
	public void deleteByProductId(int productId);
}
