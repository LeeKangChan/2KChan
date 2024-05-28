package com.chan.demo.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chan.demo.product.entity.FileVO;
import com.chan.demo.product.repo.FileRepo;

@Service
public class FileService {
	@Autowired
	private FileRepo fileRepo;
	
	public FileVO findById(Long id) {
		Optional<FileVO> opFile = fileRepo.findById(id);
		
		if(opFile.isEmpty()) {
			return null;
		}
		
		FileVO file = new FileVO();
		file = opFile.get();
		
		return file;
	}
	
	// 업로드 파일 정보 저장
	public void uploadFileSave(FileVO fileVo) {
		
		fileRepo.save(fileVo);

	}
	
	// 업로드 파일 정보 저장
	public void updateFile(FileVO fileVo) {
		
		Long id = fileVo.getId();
		
		Optional<FileVO> opFile = fileRepo.findById(id);
		
		if(opFile.isEmpty()) {
			fileRepo.save(fileVo);
		} else {
			FileVO oldFile = new FileVO();
			oldFile = opFile.get();
			
			oldFile.setFileName(fileVo.getFileName());
			oldFile.setOrgFileName(fileVo.getOrgFileName());
			oldFile.setFilePath(fileVo.getFilePath());
			oldFile.setRegDate(fileVo.getRegDate());
			
			fileRepo.save(oldFile);
		}
	}
	
	public int lastFileNum() {
		
		int num = 0;
	
		
		FileVO fileVo = new FileVO();
		
		fileVo = fileRepo.findTop1ByOrderByFileNumDesc();
		
		if(fileVo != null) {
			num = fileVo.getFileNum();
		}
		
		return num;
	}
	
	public List<FileVO> getFileList(int fileNum) {
		return fileRepo.findByFileNumOrderByIdAsc(fileNum);
	}
	
	public int countFile(Long id) {
		int cnt = 0;
		
		cnt = fileRepo.countById(id);
		
		return cnt;
	}
	
	public void deleteFile(Long id) {
		fileRepo.deleteById(id);
	}
	
	public void deleteFile(int fileNum) {
		fileRepo.deleteByFileNum(fileNum);
	}

}
