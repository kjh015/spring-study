package com.kjh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kjh.service.BoardService;

@Controller("boardController")
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/list")
	public String getArticleList(Model model) {
		model.addAttribute("dataList", boardService.selectAllArticles());
		return "list";
	}
	@GetMapping("/view")
	public String viewArticle(Model model, @RequestParam(value="no") String articleNo) {
		model.addAttribute("article", boardService.viewArticles(Integer.parseInt(articleNo)));
		return "view";
	}
}
