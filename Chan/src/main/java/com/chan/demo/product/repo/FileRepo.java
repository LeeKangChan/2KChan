package com.chan.demo.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.demo.product.entity.FileVO;

import jakarta.transaction.Transactional;

@Repository
public interface FileRepo extends JpaRepository<FileVO, Long> {
	
	public FileVO findTop1ByOrderByFileNumDesc();
	
	public List<FileVO> findByFileNumOrderByIdAsc(int fileNum);
	
	public int countById(Long id);
	
	@Transactional
	public void deleteByFileNum(int fileNum);
}
