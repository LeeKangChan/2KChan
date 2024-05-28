package com.chan.demo.main.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chan.demo.common.StringUtil;
import com.chan.demo.product.entity.FileVO;
import com.chan.demo.product.entity.Product;
import com.chan.demo.product.service.FileService;
import com.chan.demo.product.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {
	
	@Autowired
    private ProductService productService;
	
	@Autowired
    private FileService fileService;
	
	@RequestMapping("/")
	public String Index(HttpServletRequest request, ModelMap model) {
		
			return "index";
		}
	
	@RequestMapping("/main")
	public String Main(HttpServletRequest request, ModelMap model) {
		
		// 검색
		String searchType = "";
		String searchContent = "";
		
		// 검색 타입
		if(request.getParameter("searchType") != null && !request.getParameter("searchType").equals("")) {
			searchType = request.getParameter("searchType");
		}
		
		// 검색내용
		if(request.getParameter("searchContent") != null && !request.getParameter("searchContent").equals("")) {
			searchContent = request.getParameter("searchContent");
		}

		String strPageNum = "";
		int pageNum = 0; //현재 페이지

		if(request.getParameter("pageNum") != null && !request.getParameter("pageNum").equals("")) {
			strPageNum = "" + request.getParameter("pageNum");
			
			if(StringUtil.chkInt(strPageNum)) {
				pageNum = Integer.parseInt(strPageNum);
				
				if(pageNum < 0) {
					pageNum = 0;
				}
			}
		}
		
		// 페이징 처리;
		int articleCount = productService.countAll(searchType, searchContent); // 총 글 갯수
		int countList = 8; // 한 페이지에 보여줄 상품 갯수
		int countPage = 5; // 페이지 갯수 ex ) [1] [2] [3] 다음
		int block = articleCount / countList ;
		if(articleCount % countList != 0){
			block++;
		}
		
		if(pageNum >= block) {
			pageNum = block - 1;
		}
		
		int startPage = (pageNum - 1) / countPage * countPage + 1; // 시작 페이지
		int endPage = startPage + countPage - 1; // 끝 페이지
		if (endPage > block) {
			endPage = block;
		}
		

		Pageable pageable = PageRequest.of(pageNum, 8);
		
	
		List<Product> productList = productService.findAll(pageable, searchType, searchContent);

		if(productList != null && productList.size() != 0) {
			for(int i=0; i<productList.size(); i++) {
				
				Product product = new Product();
				
				product = productList.get(i);
				
				int fileNum = product.getFileNum();
				
				List<FileVO> fileList = fileService.getFileList(fileNum);
				
				if(fileList.size() != 0) {
					product.setFileList(fileList);
					product.setFileYN("Y");
				}
			}
		}
		
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchContent", searchContent);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("articleCount", articleCount);
		model.addAttribute("countPage", countPage);
		
		model.addAttribute("productList", productList);
		return "main/main";
	}
}
