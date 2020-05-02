package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Content;
import com.example.demo.service.ContentService;

/**
 * トップページ情報取得用コントローラ．
 */
@Controller
public class TopController {

	@Autowired
	private ContentService service;

	private static final String VIEW = "index";

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		//お知らせ

		List<Content> tileList = service.findAll();
		model.addAttribute("tileList", tileList);

		return VIEW;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String post(Model model, @RequestParam List<String> tileList) {

		List<Content> tileList2 = service.findByContentType(tileList.get(0));
		model.addAttribute("tileList", tileList2);

		return VIEW;
	}

}
