package app.com.daos;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import app.com.pojos.Book;
import app.com.pojos.IssueRecord;
import app.com.pojos.Ptype;
import app.com.pojos.User;

public interface IUserDao 
{
	User validatUser(String email,String pass);

	User changePassword(String email, String password);
	User changeProfile(String email, String password, String name,String phone);
	Book findBook(String bookname);
	void register(User u);
	void makePayment(Integer id, Ptype type, Double amount);
	void savaData(MultipartFile datafile) throws Exception;

	void daleteBook(Book book);

	void insertDetail(IssueRecord rf,Integer userId,Integer bookId);

	List<IssueRecord> getIssueDetail(Integer userId);
}
