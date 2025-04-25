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

import com.kjh.entity.QBoard;
import com.kjh.service.BoardService;

@Controller("boardController")
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;
	
	
	private List<QBoard> articleList;

	Logger logger = LoggerFactory.getLogger("com.kjh.controller.BoardController");
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping({"/list", "/"})	
	public String getArticleList(Model model) {
		articleList = boardService.listArticles();
		model.addAttribute("dataList", articleList);
		return "list";
	}
	
	@GetMapping("/add")
	public String writeArticle() {
		return "write";
	}
	
	@PostMapping("/addarticle")
	public String addArticle(@RequestParam(value="i_title") String title, @RequestParam(value="i_content") String content) {
		QBoard board = new QBoard();
		board.setTitle(title);
		board.setContent(content);
		board.setWriteId("bit");
		
		boardService.addArticle(board);
		
		return "redirect:list";
	}
	
	@GetMapping("/view")
	public ModelAndView viewArticle(@RequestParam(value="no") String articleNo) {
		logger.info("viewArticle => articleNo: " + articleNo);
		QBoard board = new QBoard();
		board = boardService.viewArticle(Integer.parseInt(articleNo));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view");
		mv.addObject("article", board);
		return mv;
	}
	
	@PostMapping("/edit")
	public String editArticle(@RequestParam String articleNo, @RequestParam String title,
								@RequestParam String content, RedirectAttributes redirectAttr) {
		QBoard board = new QBoard();
		board.setId(Integer.parseInt(articleNo));
		board.setTitle(title);
		board.setContent(content);		
		boardService.editArticle(board);
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
