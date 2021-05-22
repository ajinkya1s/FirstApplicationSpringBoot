package app.com.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import app.com.pojos.Book;
import app.com.pojos.User;

public interface IAdminDao{
	List<User> listAllUser();
	List<Book> listAllBooks();
	String uploadfile(Book book);
	User findUser(String bookname);
	void savaData(MultipartFile datafile) throws Exception;
	void daleteUser(User user);

}
