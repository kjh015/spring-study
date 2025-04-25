package com.kjh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kjh.model.NoticeBoard;
import com.kjh.service.BoardService;

@Controller("boardController")
@RequestMapping("/notice")
public class BoardController {
	private final BoardService boardService;
	private NoticeBoard noticeBoard;
	
	private List<NoticeBoard> articleList;

	Logger logger = LoggerFactory.getLogger("com.kjh.controller.BoardController");
	
	// 생성자 자동주입 (@Autowired)
	public BoardController(BoardService boardService, NoticeBoard noticeBoard) {
		this.boardService = boardService;
		this.noticeBoard = noticeBoard;
	}
	
	@GetMapping({"/list", "/"})	
	public String getArticleList(Model model) {
		articleList = boardService.listArticles();
		model.addAttribute("dataList", articleList);
		return "list"; //view 의 이름
	}
	
	@GetMapping("/add")
	public String writeArticle() {
		return "write";
	}
	
	@PostMapping("/addarticle")
	public String addArticle(@RequestParam(value="i_title") String title, @RequestParam(value="i_content") String content) { // tag의 name으로 식별
		noticeBoard.setTitle(title);
		noticeBoard.setContent(content);
		noticeBoard.setWriteId("bit");
		
		boardService.addArticle(noticeBoard);
		
		return "redirect:list";
	}
	// 1) Forwarding: 같은 주소 다른 페이지로 이동시킴. /regist -> 오류페이지로 이동
	// 2) Redirecting: 다른 주소 다른 페이지로 이동시킴. /regist -> /temp로 다시요청
	
	@GetMapping("/view")
	public ModelAndView viewArticle(@RequestParam(value="no") String articleNo) {
		logger.info("viewArticle => articleNo: " + articleNo);
		noticeBoard = boardService.viewArticle(Integer.parseInt(articleNo));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view");
		mv.addObject("article", noticeBoard);
		return mv;
	}
	
	@PostMapping("/edit")
	public String editArticle(@RequestParam String articleNo, @RequestParam String title, // (value="")의 이름과 변수의 이름이 같다면 생략 가능
								@RequestParam String content, RedirectAttributes redirectAttr) {
		noticeBoard.setArticleNo(Integer.parseInt(articleNo));
		noticeBoard.setTitle(title);
		noticeBoard.setContent(content);		
		boardService.editArticle(noticeBoard);
		logger.info("editArticle => title: " + title);
		logger.info("editArticle => content: " + content);
		redirectAttr.addAttribute("no", articleNo);
		return "redirect:view";
	}
	
	@PostMapping("/remove")
	public String removeArticle(@RequestParam String articleNo) {
		boardService.removeArticle(Integer.parseInt(articleNo));
		return "redirect:list";
	}
}
