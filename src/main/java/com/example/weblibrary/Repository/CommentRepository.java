package com.example.weblibrary.Repository;

import com.example.weblibrary.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	@Query("select c from Comment c where c.book.bookID = ?1")
	List<Comment> findByProduct(int productId);

	@Query("select c from Comment c where c.user.userId = ?1")
	List<Comment> findByUser(int userId);
	

}
