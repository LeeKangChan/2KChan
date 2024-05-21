package com.chan.demo.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chan.demo.product.repo.ProductRepo;

@Service
public class ProductService {
	@Autowired
	private ProductRepo productRepo;
	
	// 업로드 파일 정보 저장
	public void productReg(Product product) {
		productRepo.save(product);
	}

}
