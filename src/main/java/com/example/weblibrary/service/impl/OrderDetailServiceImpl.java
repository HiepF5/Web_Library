package com.example.weblibrary.service.impl;

import com.example.weblibrary.Repository.orderDetailRepository;
import com.example.weblibrary.entity.OrderDetail;
import com.example.weblibrary.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	private orderDetailRepository o;

	public List<OrderDetail> getPurchased(int userID) {
		// TODO Auto-generated method stub
		return o.getPurchased(userID);
	}

	@Override
	public void delete(int orderId) {
		o.deleteById(orderId);
	}

}
