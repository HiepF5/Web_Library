package com.example.weblibrary.service;

import com.example.weblibrary.entity.dto.Cart;

import java.util.Map;



public interface SaleOrderService {
	boolean addOrder(Map<Integer, Cart> carts, int id);
}
