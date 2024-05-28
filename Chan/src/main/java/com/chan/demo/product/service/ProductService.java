package com.chan.demo.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chan.demo.common.FilterUtil;
import com.chan.demo.product.entity.Product;
import com.chan.demo.product.entity.ProductReply;
import com.chan.demo.product.repo.ProductReplyRepo;
import com.chan.demo.product.repo.ProductRepo;

@Service
public class ProductService {
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductReplyRepo productReplyRepo;
	
	public int countAll(String ty, String content) {
		
		if(ty.equals("name")) {
			return productRepo.countByNameLike(content);
		} else if(ty.equals("description")) {
			return productRepo.countByDescriptionLike(content);
		} else if(ty.equals("category")) {
			return productRepo.countByCategoryLike(content);
		} else {
			return productRepo.countBy();
		}
	}
	
	// 업로드 파일 정보 저장
	public void productReg(Product product) {
		
		product.setName(FilterUtil.untagscript(product.getName()));
		product.setPrice(FilterUtil.untagscript(product.getPrice()));
		product.setDescription(FilterUtil.untagscript(product.getDescription()));
		product.setCategory(FilterUtil.untagscript(product.getCategory()));
		
		productRepo.save(product);
	}
	
	// 업로드 파일 정보 수정
	public void productMod(Product product) {
		
		Long id = product.getId();
		
		Optional<Product> opProduct = productRepo.findById(id);
		
		product.setName(FilterUtil.untagscript(product.getName()));
		product.setPrice(FilterUtil.untagscript(product.getPrice()));
		product.setDescription(FilterUtil.untagscript(product.getDescription()));
		product.setCategory(FilterUtil.untagscript(product.getCategory()));
		
		if(opProduct.isEmpty()) {
			productRepo.save(product);
		} else {
			Product oldProduct = new Product();
			oldProduct = opProduct.get();
			
			oldProduct.setName(product.getName());
			oldProduct.setPrice(product.getPrice());
			oldProduct.setDescription(product.getDescription());
			oldProduct.setCategory(product.getCategory());
			oldProduct.setStatus(product.getStatus());
			oldProduct.setFileNum(product.getFileNum());
			oldProduct.setModId(product.getModId());
			oldProduct.setModDate(product.getModDate());
			productRepo.save(oldProduct);
		}
	}
	
	public List<Product> findAll(Pageable page, String ty, String content) {
		if(ty.equals("name")) {
			return productRepo.findAllByNameLikeOrderByRegDateDesc(content, page);
		} else if(ty.equals("description")) {
			return productRepo.findAllByDescriptionLikeOrderByRegDateDesc(content, page);
		} else if(ty.equals("category")) {
			return productRepo.findAllByCategoryLikeOrderByRegDateDesc(content, page);
		} else {
			return productRepo.findAllByOrderByRegDateDesc(page);
		}
	}

	
	public List<Product> findTop8() {
		return productRepo.findTop8ByOrderByIdAsc();
	}
	
	public Product findById(Long id) {
		
		Optional<Product> opProduct = productRepo.findById(id);
		
		if(opProduct.isEmpty()) { 
			return null;
		}
		
		Product product = new Product();
		
		product = opProduct.get();
		
		return product;
	}
	
	public void regProductreply(ProductReply pr) {
		
		pr.setContent(FilterUtil.untagscript(pr.getContent()));
		
		productReplyRepo.save(pr);
	}
	
	public List<ProductReply> getProductReplyList(int productId) {
	
		
		List<ProductReply> pr = productReplyRepo.findByProductId(productId);
		
		return pr;
		
	}
	
	public String modProductReply(ProductReply pr) {
		
		try {
			Long id = pr.getId();
			
			Optional<ProductReply> opPr = productReplyRepo.findById(id);
			
			if(opPr.isEmpty()) { 
				return "null";
			}
			
			ProductReply oldPr = opPr.get();
			
			oldPr.setContent(pr.getContent());
			oldPr.setModId(pr.getModId());
			oldPr.setModDate(pr.getModDate());
			
			productReplyRepo.save(oldPr);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "err";
		}
		
		
		return "";
	}
	
	public int countById(Long id) {
		
		int cnt = 0;
		
		cnt = productReplyRepo.countById(id);
		
		return cnt;
	}
	
	// 조회수 +1
	public void plusViews(Long id) {
		Optional<Product> opProduct = productRepo.findById(id);
		
		if(!opProduct.isEmpty()) { 
			Product product = new Product();
			
			product = opProduct.get();
			
			int cnt = product.getViews();
			
			cnt = cnt + 1;
			
			product.setViews(cnt);
			productRepo.save(product);
		}
		
		
	}
	
	// 상품 삭제
	public void delProduct(Long Id) {
		productRepo.deleteById(Id);
	}

	public void delProductReply(Long id) {
		productReplyRepo.deleteById(id);
	}
	
	public void delProductReply(int productId) {
		productReplyRepo.deleteByProductId(productId);
	}
}
