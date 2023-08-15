package com.example.weblibrary.utils;

import com.example.weblibrary.entity.dto.Cart;

import java.util.HashMap;
import java.util.Map;


public class utils {
	public static int count(Map<Integer, Cart> ms) {
		int c = 0;
		for (Cart cart : ms.values()) {
			c += cart.getQuantity();
		}
		return c;
	}

	public static Map<String, Long> aggregate(Map<Integer, Cart> ms) {
		Map<String, Long> res = new HashMap<>();
		long sum = 0, cnt = 0;
		for (Cart cart : ms.values()) {

			cnt += cart.getQuantity();
		}
		res.put("count", cnt);
		return res;
	}
}
