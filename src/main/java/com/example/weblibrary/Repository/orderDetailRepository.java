package com.example.weblibrary.Repository;

import com.example.weblibrary.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface orderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	
	
	@Query("SELECT O FROM OrderDetail O WHERE O.saleOrder.saleOrderId IN (SELECT S.saleOrderId FROM SaleOrder S WHERE S.user.userId = :userID)")
	List<OrderDetail> getPurchased(int userID);
	
}
