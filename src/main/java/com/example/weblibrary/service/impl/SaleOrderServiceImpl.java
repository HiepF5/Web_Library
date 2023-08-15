package com.example.weblibrary.service.impl;

import com.example.weblibrary.Repository.BookRepository;
import com.example.weblibrary.Repository.SaleOrderRepository;
import com.example.weblibrary.Repository.UserRepository;
import com.example.weblibrary.Repository.orderDetailRepository;
import com.example.weblibrary.entity.OrderDetail;
import com.example.weblibrary.entity.SaleOrder;
import com.example.weblibrary.entity.dto.Cart;
import com.example.weblibrary.service.SaleOrderService;
import com.example.weblibrary.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {

	@Autowired
	private SaleOrderRepository saleOrderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private orderDetailRepository OrderDetailRepository;
	@Autowired
	private BookRepository bookRepository;

	@Override
	public boolean addOrder(Map<Integer, Cart> carts, int id) {
		try {
			SaleOrder saleOrder = new SaleOrder();
			saleOrder.setQuantity(utils.count(carts));
			saleOrder.setUser(userRepository.findById(id).orElseThrow());
			saleOrder.setAmount(0.0);
			saleOrderRepository.save(saleOrder);
			System.out.print(carts);
			for (Cart cart : carts.values()) {
				System.out.println(cart.getBookID());
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setSaleOrder(saleOrder);
				orderDetail.setBook(bookRepository.findById(cart.getBookID()).orElseThrow());
				orderDetail.setQuantity(cart.getQuantity());
				OrderDetailRepository.save(orderDetail);
			}
			return true;
		} catch (Exception ex) {
			ex.getStackTrace();
			return false;
		}
	}

}
