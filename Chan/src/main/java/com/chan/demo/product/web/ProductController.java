package com.chan.demo.product.web;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chan.demo.common.AlertUtil;
import com.chan.demo.member.service.Member;
import com.chan.demo.product.service.FileService;
import com.chan.demo.product.service.FileVO;
import com.chan.demo.product.service.Product;
import com.chan.demo.product.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class ProductController {

	@Autowired
    private FileService fileService;
	
	@Autowired
    private ProductService productService;
	
	// 상품 등록 페이지 이동
	@RequestMapping("/productReg")
	public String ProductReg(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}

		return "product/productReg";
	}
	
	// 상품 등록
	@RequestMapping("/productRegAction")
	public String ProductRegAction(HttpServletRequest request, ModelMap model, @RequestParam("img") MultipartFile[] files) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		String name = "";
		String price = "";
		String description = "";
		String category = "";
		String status = "";
		String fileId = "";
		
		String msg = "";
		
		if(request.getParameter("name") == null || request.getParameter("name").equals("")) {
			msg = "상품명을 입력해 주세요";
		}
		
		if(request.getParameter("price") == null || request.getParameter("price").equals("")) {
			msg = "상품가격을 입력해 주세요";
		}
		
		if(request.getParameter("description") == null || request.getParameter("description").equals("")) {
			msg = "상품 상세 정보를 입력해 주세요";
		}
		
		if(request.getParameter("category") == null || request.getParameter("category").equals("")) {
			msg = "상품 분류를 입력해 주세요";
		}
		
		if(request.getParameter("status") == null || request.getParameter("status").equals("")) {
			msg = "상품 상태를 선택해 주세요";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		name = request.getParameter("name");
		price = request.getParameter("price");
		description = request.getParameter("description");
		category = request.getParameter("category");
		status = request.getParameter("status");
		
		LocalDateTime now = LocalDateTime.now();
		String today = "";
		// 파일 업로드 처리 시작
		try {
			if(files.length != 0) {
				for(int i=0; i<files.length; i++) {
					MultipartFile file = files[i];
					
					String savePath = System.getProperty("user.dir") + "/files/product";
                    // 폴더 생성
                    if (!new File(savePath).exists()) {
                        try {
                            new File(savePath).mkdir();
                        } catch(Exception e) {
                            e.getStackTrace();
                        }
                    }

					today = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					
					String orgFileName = file.getOriginalFilename(); // 실제 파일이름
					
					String fileName = "product_" + today+"_" + i; // 파일 이름
			        
					String filePath = savePath + "/" + fileName; // 파일 경로
					
			        File saveFile = new File(savePath, fileName); // projectPath는 위에서 작성한 경로, name은 전달받을 이름

			        file.transferTo(saveFile);
			        
			        FileVO fileVo = new FileVO();
			        
			        today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			        
			        fileVo.setFile_name(fileName);
			        fileVo.setOrg_file_name(orgFileName);
			        fileVo.setFile_path(filePath);
			        fileVo.setReg_date(today);
			        // 파일 업로드 처리 끝
			        
			        int id = fileService.uploadFileSave(fileVo);
			        
			        if(fileId.equals("")) {
			        	fileId = "" + id;
			        } else {
			        	fileId = fileId + "," + id;
			        }
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Product pro = new Product();
		pro.setName(name);
		pro.setPrice(price);
		pro.setDescription(description);
		pro.setCategory(category);
		pro.setStatus(status);
		pro.setViews(0);
		pro.setFile_id(fileId);
		pro.setReg_id(member.getId());
		pro.setReg_date(today);
		pro.setMod_id("");
		pro.setMod_date("");
		
		productService.productReg(pro);
		
		AlertUtil.returnAlert(model, "상품 등록이 완료 되었습니다.", "/", "url");
		return "inc/alert";
	}
}
