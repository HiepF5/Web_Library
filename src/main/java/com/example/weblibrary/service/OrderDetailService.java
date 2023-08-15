package com.example.weblibrary.service;

import com.example.weblibrary.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
	List<OrderDetail> getPurchased(int used);
	void delete(int orderId);
}	
