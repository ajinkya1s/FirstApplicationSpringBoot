package app.com.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.com.pojos.Book;

public interface BookDaoInter extends JpaRepository<Book, Integer> {

	@Query("select b from Book b where LOWER(b.name) = LOWER(:name)")
	Book findBook(@Param("name") String name);
	
}

