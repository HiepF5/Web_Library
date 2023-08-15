package com.example.weblibrary.Repository;

import com.example.weblibrary.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {
	@Query("SELECT b FROM Book b WHERE b.title = :title AND b.bookID != :id")
	Book findBytitleExceptId(String title, int id);

	Book findBytitle(String bookname);

	@Query("select p from Book p where p.title like %?1% ")
	Page<Book> searchByBookName(String bookName, Pageable pageable);

	@Query("select COALESCE(SUM(o.quantity), 0) from OrderDetail o where o.book.bookID = :id")
	int statitics(int id);

	@Query("select p from Book p where p.category.categoryId = ?1")
	Page<Book> getSameCategory(int categoryID, Pageable pageable);



	@Query("select COALESCE(Function('CEIL',AVG(cm.star)),0) from Comment cm where cm.book.bookID = :bookID")
	int BookStart(int bookID);

}
