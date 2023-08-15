package com.example.weblibrary.controler;

import com.example.weblibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class Admin {
	@Autowired
	private UserService userService;

	@GetMapping("/admin")
	public String admin(Model model) {
		String res = (SecurityContextHolder.getContext().getAuthentication().getName());
		if (res.equals("anonymousUser")) {
			res = null;
		} else {
			int userID = userService.findByUserName(res).getUserId();
			model.addAttribute("username", res);
			model.addAttribute("userID", userID);
		}
		return "admin-book";

	}

	@GetMapping("/book/addBook/{id}")
	public String addBook(@PathVariable int id, Model model) {
		model.addAttribute("modelbookID", id);
		return "addBook";
	}

	@GetMapping("/bookManagement")
	public String Book() {
		return "admin-book";
	}

	@GetMapping("/admin/category")
	public String Category() {
		return "admin-category";
	}
}
