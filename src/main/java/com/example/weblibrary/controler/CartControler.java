package com.example.weblibrary.controler;

import com.example.weblibrary.entity.dto.Cart;
import com.example.weblibrary.entity.dto.CartMessage;
import com.example.weblibrary.service.OrderDetailService;
import com.example.weblibrary.service.SaleOrderService;
import com.example.weblibrary.utils.utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CartControler {
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private OrderDetailService orderDetailService;

	@PostMapping("/cart")
	public ResponseEntity<?> AddToCart(@RequestBody Cart cart, HttpSession session) {
		Map<Integer, Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
		int bookID = cart.getBookID();
		if (cartMap == null)
			cartMap = new HashMap<>();
		if (cartMap.containsKey(bookID)) {
			Cart c = cartMap.get(bookID);
			c.setQuantity(c.getQuantity() + cart.getQuantity());
		} else {
			cartMap.put(bookID, cart);
		}
		session.setAttribute("cart", cartMap);
		return new ResponseEntity<>(utils.aggregate(cartMap), HttpStatus.OK);
	}

	@GetMapping("/cart/display")
	public ResponseEntity<?> display(HttpSession session) {
		Map<Integer, Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
		if (ObjectUtils.isEmpty(cartMap)) {
			Map<String, Integer> ms = new HashMap<>();
			ms.put("count", 0);
			return new ResponseEntity<>(ms, HttpStatus.OK);
		}
		return new ResponseEntity<>(utils.aggregate(cartMap), HttpStatus.OK);
	}

	@GetMapping("/cart")
	public ResponseEntity<?> cart(HttpSession session) {
		Map<Integer, Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
		if (ObjectUtils.isEmpty(cartMap)) {
			CartMessage c = new CartMessage("Gio Hang Trong");
			return new ResponseEntity<>(c, HttpStatus.OK);
		}
		return new ResponseEntity<>(cartMap, HttpStatus.OK);
	}

	@GetMapping("/cart/pay")
	public ResponseEntity<?> payment(HttpSession session, @RequestParam(name = "id") int id) {
		Map<Integer, Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
		if (cartMap != null) {
			boolean check = saleOrderService.addOrder(cartMap, id);
			if (check == true) {
				session.removeAttribute("cart");
				return new ResponseEntity<>(new CartMessage("Pay Successful!"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new CartMessage("Something went wrong!"), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(new CartMessage("Nothing to pay"), HttpStatus.OK);
		}
	}

	@DeleteMapping("/cart/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable int id, HttpSession session) {
		Map<Integer, Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
		if (cartMap != null && cartMap.containsKey(id)) {
			cartMap.remove(id);
			session.setAttribute("cart", cartMap);
			return new ResponseEntity<>(new CartMessage("Da Xoa Thanh Cong"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new CartMessage("Da co loi xay ra"), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/purchared/{userId}")
	public ResponseEntity<?> getPurcharsed(@PathVariable int userId) {
		return new ResponseEntity<>(orderDetailService.getPurchased(userId), HttpStatus.OK);
	}

	@PutMapping("/cart")
	public ResponseEntity<?> updateCart(@RequestBody Cart cart, HttpSession session) {
		Map<Integer, Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
		Cart c = cartMap.get(cart.getBookID());
		c.setQuantity(cart.getQuantity());
		cartMap.put(cart.getBookID(), cart);
		session.setAttribute("cart", cartMap);
		return new ResponseEntity<>(new CartMessage("Cap Nhat Thanh Cong"), HttpStatus.OK);
	}
	
	@DeleteMapping("/order")
	public ResponseEntity<?> deleteOrder(@RequestParam(name = "orderId") int orderID){
		try {
			orderDetailService.delete(orderID);
			return new ResponseEntity<>(new CartMessage("Xoa Thanh Cong"), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new CartMessage("Co Loi Xay Ra"), HttpStatus.OK);
		}
	}
	
	
}
