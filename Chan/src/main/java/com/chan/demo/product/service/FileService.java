package com.chan.demo.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chan.demo.product.repo.FileRepo;

@Service
public class FileService {
	@Autowired
	private FileRepo productRepo;
	
	// 업로드 파일 정보 저장
	public int uploadFileSave(FileVO fileVo) {
		
		Long longId = productRepo.save(fileVo).getFile_id();
		
		int id = longId.intValue();
		
		return id;
	}

}
