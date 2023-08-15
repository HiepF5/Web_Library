package com.example.weblibrary.controler;

import com.example.weblibrary.service.CategoryService;
import com.example.weblibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeControler {
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/view/forbidden")
	public String forbidden() {
		return "403";
	}

	@GetMapping("/view/shop")
	public String shop(Model model, @RequestParam(name = "categoryName", defaultValue = "") String categoryName) {

		String res = (SecurityContextHolder.getContext().getAuthentication().getName());
		if (categoryName.compareToIgnoreCase("") != 0) {
			int categoryID = categoryService.GetCategoryId(categoryName).getCategoryId();
			model.addAttribute("catID", categoryID);
		}
		if (res.equals("anonymousUser"))
			res = null;
		else {
			int userId = userService.findByUserName(res).getUserId();
			model.addAttribute("username", res);
			model.addAttribute("userId", userId);

		}
		return "shop-grid";
	}

	@GetMapping("/view/shop/{categoryName}")
	public String ProductByCateogry(Model model, @PathVariable String categoryName) {
		String res = (SecurityContextHolder.getContext().getAuthentication().getName());
		int categoryID = categoryService.GetCategoryId(categoryName).getCategoryId();
		model.addAttribute("catID", categoryID);
		if (res.equals("anonymousUser"))
			res = null;
		else {
			int userId = userService.findByUserName(res).getUserId();
			model.addAttribute("username", res);
			model.addAttribute("userId", userId);

		}
		return "shop-grid";
	}

	@GetMapping("view/detail/{id}")
	public String detail(@PathVariable int id, Model model) {
		String res = (SecurityContextHolder.getContext().getAuthentication().getName());
		if (res.equals("anonymousUser"))
			res = null;
		else {
			int userId = userService.findByUserName(res).getUserId();
			model.addAttribute("username", res);
			model.addAttribute("userId", userId);
			model.addAttribute("productId", id);
		}
		return "product-details";
	}

	@GetMapping("/view/cart")
	public String cart(Model model) {
		String res = (SecurityContextHolder.getContext().getAuthentication().getName());
		if (res.equals("anonymousUser"))
			res = null;
		else {
			int userId = userService.findByUserName(res).getUserId();
			model.addAttribute("username", res);
			model.addAttribute("userId", userId);
		}
		return "cart";
	}

	@GetMapping("/purchased/{userID}")
	public String purcharsed(Model model, @PathVariable int userID) {

		String res = (SecurityContextHolder.getContext().getAuthentication().getName());
		if (res.equals("anonymousUser")) {
			res = null;
			return "403";
		} else {
			int userId_service = userService.findByUserName(res).getUserId();
			if(userId_service!=userID) {
				return "403";
			}
			model.addAttribute("username", res);
			model.addAttribute("userId", userID);
		}
		return "purcharsed";
	}

	@GetMapping("/kien")
	public String test() {
		return "nhap";
	}

}
