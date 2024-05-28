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
import com.chan.demo.common.StringUtil;
import com.chan.demo.member.entity.Member;
import com.chan.demo.product.entity.FileVO;
import com.chan.demo.product.entity.Product;
import com.chan.demo.product.entity.ProductReply;
import com.chan.demo.product.service.FileService;
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
	public String ProductRegAction(HttpServletRequest request, ModelMap model, @RequestParam("img") List<MultipartFile> files) {
			
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
		int num = 0;
		
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
		
		int cnt = 0;
		
		// 파일 업로드 처리 시작
		try {
			
			// 마지막 파일 아이디 추출
			num = fileService.lastFileNum();
			
			if(num == 0) {
				num = 1;
			} else {
				num = num + 1;
			}
			
			for(int i=0; i<files.size(); i++) {
				MultipartFile file = files.get(i);
				
				// 파일 첨부가 없으면 다음 파일로
				if(file.getSize() == 0 || file.getOriginalFilename().equals("")) {
					continue;
				}
				
				cnt = cnt + 1;
				
				// 경로는 개발 상황에 맞게 변경 필요
				String uploadPath = System.getProperty("user.dir") + "\\src\\main\\webapp\\uploadFile\\product";
				String savePath = "/uploadFile/product";
				
                // 폴더 생성
                if (!new File(uploadPath).exists()) {
                    try {
                        new File(uploadPath).mkdir();
                    } catch(Exception e) {
                        e.getStackTrace();
                    }
                }

				today = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
				
				String orgFileName = file.getOriginalFilename(); // 실제 파일이름
				
				int sub = orgFileName.indexOf(".");
				
				String extension = orgFileName.substring(sub);
				
				String fileName = "product_" + today + "_" + i + extension; // 파일 이름
		        
				String filePath = savePath + "/" + fileName; // 파일 경로
				
		        File saveFile = new File(uploadPath, fileName); // projectPath는 위에서 작성한 경로, name은 전달받을 이름

		        file.transferTo(saveFile);
		        
		        FileVO fileVo = new FileVO();
		        
		        today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		        
		        fileVo.setFileNum(num);
		        fileVo.setFileName(fileName);
		        fileVo.setOrgFileName(orgFileName);
		        fileVo.setFilePath(filePath);
		        fileVo.setRegDate(today);
		        // 파일 업로드 처리 끝
		        
		        fileService.uploadFileSave(fileVo);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 첨부 파일 없으면 상품정보에 파일 아이디 0으로 수정
		if(cnt == 0) {
			num = 0;
		}
		
		today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Product pro = new Product();
		pro.setName(name);
		pro.setPrice(price);
		pro.setDescription(description);
		pro.setCategory(category);
		pro.setStatus(status);
		pro.setViews(0);
		pro.setFileNum(num);
		pro.setRegName(member.getName());
		pro.setRegId(member.getId());
		pro.setRegDate(today);
		pro.setModId("");
		pro.setModDate("");
		
		try {
			productService.productReg(pro);
		} catch (Exception e) {
			e.printStackTrace();
			AlertUtil.returnAlert(model, "저장 오류! 담당자 문의 바람", "/", "url");
			return "inc/alert";
		}
		
		
		AlertUtil.returnAlert(model, "상품 등록이 완료 되었습니다.", "/", "url");
		return "inc/alert";
	}
	
	// 상품 상세 페이지
	@RequestMapping("/productDetail")
	public String ProductDetail(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
		
		String strId = "";
		
		if(request.getParameter("id") == null || request.getParameter("id").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근1", "/", "url");
			return "inc/alert";
		}

		strId = request.getParameter("id");
		
		if(!StringUtil.chkInt(strId)) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/", "url");
			return "inc/alert";
		}
		
		Long id = Long.parseLong(strId);
		
		Product product = new Product();
		
		product = productService.findById(id);
		
		if(product == null) {
			AlertUtil.returnAlert(model, "상품이 존재 하지 않습니다.", "/", "url");
			return "inc/alert";
		}
		
		// 조회수 + 1
		productService.plusViews(id);
		
		int fileNum = 0;
		
		// 해당 상품의 파일 리스트 가져오기
		if(!product.getFileNum().equals("")) { 
			fileNum = product.getFileNum();
			
			List<FileVO> fileList = fileService.getFileList(fileNum);
			
			if(fileList != null) {
				product.setFileYN("Y");
				model.addAttribute("fileList", fileList);
			}
		}
		
		// 해당 상품의 댓글 리스트 가져오기
		int productId = Integer.parseInt(strId);
		
		List<ProductReply> replyList = productService.getProductReplyList(productId);
		
		model.addAttribute("replyList", replyList);
		model.addAttribute("product", product);
		model.addAttribute("member", member);
		return "product/productDetail";
	}
	
	// 상품 수정 페이지 이동
	@RequestMapping("/productMod")
	public String modProduct(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		String id = "";
		String strProductId = "";
		String err = "";
		
		if(request.getParameter("memberId") == null || request.getParameter("memberId").equals("")) {
			err = "y";
		}
		
		if(request.getParameter("productId") == null || request.getParameter("productId").equals("")) {
			err = "y";
		}
		
		if(err.equals("y")) {
			AlertUtil.returnAlert(model, "잘못된 접근", "/", "url");
			return "inc/alert";
		}
		
		id = request.getParameter("memberId");
		strProductId = request.getParameter("productId");
		
		if(!StringUtil.chkInt(strProductId)) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/", "url");
			return "inc/alert";
		}
		
		if(!member.getId().equals(id)) {
			AlertUtil.returnAlert(model, "글 수정은 본인만 가능합니다.", "/", "url");
			return "inc/alert";
		}
		
		Long productId = Long.parseLong(strProductId);
		
		Product product = new Product();
		
		product = productService.findById(productId);
		
		if(product == null) {
			AlertUtil.returnAlert(model, "상품이 존재하지 않습니다.", "/", "url");
			return "inc/alert";
		}
		
		if(product.getFileNum() != null && !product.getFileNum().equals("")) {
			
			
			int num = product.getFileNum();
			
			List<FileVO> fileList = fileService.getFileList(num);
			
			if(fileList != null && fileList.size() > 0) {
				product.setFileYN("Y");
				model.addAttribute("fileList", fileList);
			}
		}
		
		model.addAttribute("product", product);
		
		return "product/productMod";
	}
	
	// 상품 수정
	@RequestMapping("/productModAction")
	public String ProductModAction(HttpServletRequest request, ModelMap model, @RequestParam("img") List<MultipartFile> files) {
			
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
		int num = 0;
		
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
		
		if(request.getParameter("productId") == null || request.getParameter("productId").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("fileNum") == null || request.getParameter("fileNum").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/", "url");
			return "inc/alert";
		}
		
		String strProductId = request.getParameter("productId");
		String strFileNum = request.getParameter("fileNum");
		
		if(!StringUtil.chkInt(strProductId)) {
			AlertUtil.returnAlert(model, "잘못된 접근3", "/", "url");
			return "inc/alert";
		}
		
		if(!StringUtil.chkInt(strFileNum)) {
			AlertUtil.returnAlert(model, "잘못된 접근4", "/", "url");
			return "inc/alert";
		}
		
		Long id = Long.parseLong(strProductId);
		
		num = Integer.parseInt(strFileNum);
		
		if(num == 0) {
			num = fileService.lastFileNum();
			num = num + 1;
			
		}
		
		name = request.getParameter("name");
		price = request.getParameter("price");
		description = request.getParameter("description");
		category = request.getParameter("category");
		status = request.getParameter("status");
		
		LocalDateTime now = LocalDateTime.now();
		String today = "";
		
		// 경로는 개발 상황에 맞게 변경 필요
		String uploadPath = System.getProperty("user.dir") + "\\src\\main\\webapp\\uploadFile\\product";
		String savePath = "/uploadFile/product";
		
		// 폴더 생성
        if (!new File(uploadPath).exists()) {
            try {
                new File(uploadPath).mkdir();
            } catch(Exception e) {
                e.getStackTrace();
            }
        }
		
		// 파일 업로드 처리 시작
		try {
			for(int i=0; i<files.size(); i++) {
				MultipartFile file = files.get(i);

                // 기존 파일 유무
                String fileYn = "";
                String strFileId = "";
                
                if(request.getParameter("fileId_" + i) != null && !request.getParameter("fileId_"+i).equals("")) {
                	fileYn = "Y";
                	strFileId = request.getParameter("fileId_" + i);

                	if(!StringUtil.chkInt(strFileId)) {
                		AlertUtil.returnAlert(model, "잘못된 접근5", "/", "url");
            			return "inc/alert";
                	}
                }
				
				// 기존 첨부파일이 있으면
				if(fileYn.equals("Y")) {
					// 기존 첨부파일이 있고 새로운 첨부 파일이 없으면
					if(file.getSize() == 0 || file.getOriginalFilename().equals("")) {
							Long fileId = Long.parseLong(strFileId);
							
							// 기존 파일 삭제 체크
							if(request.getParameter("delFile_" + i) != null && request.getParameter("delFile_" + i).equals("y")) {
								fileService.deleteFile(fileId);
							}
					} else {
						// 기존첨부파일이 있고 새로운 첨부파일이 있으면
						// 기존 파일이 아이디 확인
						if(request.getParameter("fileId_" + i) != null && !request.getParameter("fileId_"+i).equals("")) {
							
							// 기존 파일 아이디							
							Long fileId = Long.parseLong(strFileId);
							
							today = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
							
							String orgFileName = file.getOriginalFilename(); // 실제 파일이름
							
							int sub = orgFileName.indexOf(".");
							
							String extension = orgFileName.substring(sub); // 확장자
							
							String fileName = "product_" + today + "_" + i + extension; // 파일 이름
					        
							String filePath = savePath + "/" + fileName; // 파일 경로
							
					        File saveFile = new File(uploadPath, fileName); // projectPath는 위에서 작성한 경로, name은 전달받을 이름

					        file.transferTo(saveFile);
					        
					        FileVO fileVo = new FileVO();
					        
					        today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					        
					        fileVo.setId(fileId);
					        fileVo.setFileNum(num);
					        fileVo.setFileName(fileName);
					        fileVo.setOrgFileName(orgFileName);
					        fileVo.setFilePath(filePath);
					        fileVo.setRegDate(today);
					        
					        fileService.uploadFileSave(fileVo);
						}
					}
				} else {
					// 기존 첨부파일이 없으면
					if(file.getSize() == 0 || file.getOriginalFilename().equals("")) {
						// 새로운 첨부파일이 없으면
						continue;
					}
					
					// 새로운 첨부파일이 있으면
					today = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					
					String orgFileName = file.getOriginalFilename(); // 실제 파일이름
					
					int sub = orgFileName.indexOf(".");
					
					String extension = orgFileName.substring(sub); // 확장자
					
					String fileName = "product_" + today + "_" + i + extension; // 파일 이름
			        
					String filePath = savePath + "/" + fileName; // 파일 경로
					
			        File saveFile = new File(uploadPath, fileName); // projectPath는 위에서 작성한 경로, name은 전달받을 이름

			        file.transferTo(saveFile);
			        
			        FileVO fileVo = new FileVO();
			        
			        today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			        fileVo.setFileNum(num);
			        fileVo.setFileName(fileName);
			        fileVo.setOrgFileName(orgFileName);
			        fileVo.setFilePath(filePath);
			        fileVo.setRegDate(today);
			        fileService.uploadFileSave(fileVo);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Product pro = new Product();
		pro.setId(id);
		pro.setName(name);
		pro.setPrice(price);
		pro.setDescription(description);
		pro.setCategory(category);
		pro.setStatus(status);
		pro.setViews(0);
		pro.setFileNum(num);
		pro.setRegId(member.getId());
		pro.setRegDate(today);
		pro.setModId(member.getId());
		pro.setModDate(today);
		
		try {
			productService.productMod(pro);
		} catch (Exception e) {
			e.printStackTrace();
			AlertUtil.returnAlert(model, "수정 오류! 담당자 문의 바람", "/", "url");
			return "inc/alert";
		}
		AlertUtil.returnAlert(model, "상품 수정이 완료 되었습니다.", "/productDetail?id="+id, "url");
		return "inc/alert";
	}
	
	// 상품 수정 페이지 이동
	@RequestMapping("/productDel")
	public String DelProduct(HttpServletRequest request, ModelMap model) {
		
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		String strProductId = "";
		String memberId = "";
		String msg = "";
		
		if(request.getParameter("productId") == null || request.getParameter("productId").equals("")) {
			msg = "잘못된 접근1";
		}
		
		if(request.getParameter("memberId") == null || request.getParameter("memberId").equals("")) {
			msg = "잘못된 접근2";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "/", "url");
			return "inc/alert";
		}
		
		strProductId = request.getParameter("productId");
		memberId = request.getParameter("memberId");
		
		if(!StringUtil.chkInt(strProductId)) {
			AlertUtil.returnAlert(model, "잘못된 접근3", "/", "url");
			return "inc/alert";
		}
		
		if(!memberId.equals(member.getId())) {
			AlertUtil.returnAlert(model, "작성자 본인만 삭제가 가능합니다.", "/", "url");
			return "inc/alert";
		}
		
		int productId = Integer.parseInt(strProductId);
		Long id = Long.parseLong(strProductId);
		
		try {
			Product product = new Product();
			
			product = productService.findById(id);
			
			if(product == null) {
				AlertUtil.returnAlert(model, "상품이 존재하지 않습니다.", "/", "url");
				return "inc/alert";
			}
			
			// 파일 번호 검사
			int fileNum = product.getFileNum();
			
			// 파일 번호 있으면 파일 삭제
			if(fileNum != 0) {
				fileService.deleteFile(fileNum);
			}
			
			// 댓글 삭제
			productService.delProductReply(productId);
			
			// 상품 삭제
			productService.delProduct(id);
		} catch(Exception e) {
			e.printStackTrace();
			AlertUtil.returnAlert(model, "상품 삭제 오류! 담당자에게 문의 바랍니다.", "/", "url");
			return "inc/alert";
		}
		
		AlertUtil.returnAlert(model, "상품 삭제가 완료 되었습니다.", "/", "url");
		return "inc/alert";
	}
	
	// 댓글 추가
	@RequestMapping("/regProductReply")
	public String regProductReply(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		String strProductId = "";
		int productId = 0;
		String content = "";
		
		if(request.getParameter("productId") == null || request.getParameter("productId").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근1", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("content") == null || request.getParameter("content").equals("")) {
			AlertUtil.returnAlert(model, "댓글 내용을 입력해 주세요", "history.back()", "script");
			return "inc/alert";
		}

		strProductId = request.getParameter("productId");
		content = request.getParameter("content");
		
		if(!StringUtil.chkInt(strProductId)) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/", "url");
			return "inc/alert";
		}
		
		productId = Integer.parseInt(strProductId);
		
		LocalDateTime now = LocalDateTime.now();
		String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		ProductReply pr = new ProductReply();
		
		pr.setProductId(productId);
		pr.setContent(content);
		pr.setRegName(member.getName());
		pr.setRegId(member.getId());
		pr.setRegDate(today);
		pr.setModId("");
		pr.setModDate("");
		
		try {
			productService.regProductreply(pr);
		} catch (Exception e) {
			e.printStackTrace();
			AlertUtil.returnAlert(model, "댓글 저장 오류! 담당자 문의 바람", "/", "url");
			return "inc/alert";
		}
		
		AlertUtil.returnAlert(model, "등록완료", "/productDetail?id="+productId, "url");
		return "inc/alert";
	}
	
	// 댓글 수정
	@RequestMapping("/modProductReply")
	public String modProductReply(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		String strProductId = "";
		String memberId = "";		
		String strReplyId = "";
		String content = "";
		
		if(request.getParameter("productId") == null || request.getParameter("productId").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근1", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("replyId") == null || request.getParameter("replyId").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근1", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("memberId") == null || request.getParameter("memberId").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("content") == null || request.getParameter("content").equals("")) {
			AlertUtil.returnAlert(model, "댓글 내용을 입력해 주세요", "history.back()", "script");
			return "inc/alert";
		}
		
		strProductId = request.getParameter("productId");
		strReplyId = request.getParameter("replyId");
		memberId = request.getParameter("memberId");
		content = request.getParameter("content");
		
		if(!memberId.equals(member.getId())) {
			AlertUtil.returnAlert(model, "작성자 본인만 수정 가능합니다.", "/", "url");
			return "inc/alert";
		}
		
		if(!StringUtil.chkInt(strReplyId)) {
			AlertUtil.returnAlert(model, "잘못된 접근3", "/", "url");
			return "inc/alert";
		}
		
		if(!StringUtil.chkInt(strProductId)) {
			AlertUtil.returnAlert(model, "잘못된 접근4", "/", "url");
			return "inc/alert";
		}
		
		int productId = Integer.parseInt(strProductId);
		
		Long id = Long.parseLong(strReplyId);
		
		LocalDateTime now = LocalDateTime.now();
		String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		ProductReply pr = new ProductReply();
		
		pr.setId(id);
		pr.setContent(content);
		pr.setModId(member.getId());
		pr.setModDate(today);
		
		int cnt = 0;
		
		cnt = productService.countById(id);
		
		if(cnt == 0) {
			AlertUtil.returnAlert(model, "존재하지 않는 댓글입니다.", "/productDetail?id="+productId, "url");
			return "inc/alert";
		}
		
		try {
			productService.modProductReply(pr);
		} catch (Exception e) {
			e.printStackTrace();
			AlertUtil.returnAlert(model, "댓글 수정 오류! 담당자 문의 바람", "/", "url");
			return "inc/alert";
		}
		
		AlertUtil.returnAlert(model, "댓글 수정 완료", "/productDetail?id="+productId, "url");
		return "inc/alert";
	}
	
	
	// 댓글 삭제
	@RequestMapping("/delProductReply")
	public String delProductReply(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		String strProductId = "";
		String memberId = "";		
		String strReplyId = "";

		if(request.getParameter("productId") == null || request.getParameter("productId").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근1", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("replyId") == null || request.getParameter("replyId").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근1", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("memberId") == null || request.getParameter("memberId").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/", "url");
			return "inc/alert";
		}
		

		
		strProductId = request.getParameter("productId");
		strReplyId = request.getParameter("replyId");
		memberId = request.getParameter("memberId");
		
		if(!memberId.equals(member.getId())) {
			AlertUtil.returnAlert(model, "작성자 본인만 삭제 가능합니다.", "/", "url");
			return "inc/alert";
		}
		
		if(!StringUtil.chkInt(strReplyId)) {
			AlertUtil.returnAlert(model, "잘못된 접근3", "/", "url");
			return "inc/alert";
		}
		
		if(!StringUtil.chkInt(strProductId)) {
			AlertUtil.returnAlert(model, "잘못된 접근4", "/", "url");
			return "inc/alert";
		}
		
		int productId = Integer.parseInt(strProductId);
		
		Long id = Long.parseLong(strReplyId);
		
		int cnt = 0;
		
		cnt = productService.countById(id);
		
		if(cnt == 0) {
			AlertUtil.returnAlert(model, "존재하지 않는 댓글입니다.", "/productDetail?id="+productId, "url");
			return "inc/alert";
		}
		
		try {
			productService.delProductReply(id);
		} catch (Exception e) {
			e.printStackTrace();
			AlertUtil.returnAlert(model, "댓글 삭제 오류! 담당자 문의 바람", "/", "url");
			return "inc/alert";
		}
		
		AlertUtil.returnAlert(model, "댓글 삭제 완료", "/productDetail?id="+productId, "url");
		return "inc/alert";
	}
}
