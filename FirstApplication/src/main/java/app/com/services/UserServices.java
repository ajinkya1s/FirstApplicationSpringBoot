package app.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.com.daos.BookDaoInter;
import app.com.daos.PaymentDaoInter;
import app.com.daos.UserDaoInter;
import app.com.daos.IssueRecordDaoInter;
import app.com.pojos.Book;
import app.com.pojos.IssueRecord;
import app.com.pojos.Payments;
import app.com.pojos.Ptype;
import app.com.pojos.User;

@Service
public class UserServices {
	
	
	@Autowired
	private UserDaoInter userInterface;
	
	@Autowired
	private BookDaoInter bookInterface;
	
	@Autowired
	private PaymentDaoInter paymentInterface;
  
	@Autowired
	private IssueRecordDaoInter issueRecordInterface;
	
	
	public User validateUser(String name,String password)
	{
		return this.userInterface.validateUser(name,password);
	}


	public User changePassword(String email, String password) {
		User user=this.userInterface.changePassword(email);
		user.setPassword(password);
		this.userInterface.save(user);
		return user;
	}


	public User changeProfile(String email, String password, String name, String phone) {
		User user=this.userInterface.changeProfile(email,password);
		user.setName(name);
		user.setPhone(phone);
		return user;
	}


	public Book findBook(String bookname) {
		Book book=this.bookInterface.findBook(bookname);
		return book;
	}


	public Book deleteBook(String bookname) {
		Book book=this.bookInterface.findBook(bookname);
		this.bookInterface.delete(book);
		return book;
	}


	public void makePayment(Integer id, Payments p1) {
		User u=new User();
		u.setId(id);
		p1.setUserId(u);
		
		this.paymentInterface.save(p1);
	}
	

	public void register(User u2) {
		User u=this.userInterface.save(u2);
	}


	public void insertDetail(IssueRecord rf, Integer userId, Integer bookId) {
		User u= new User();
		u.setId(userId);
		
		Book b= new Book();
		b.setId(bookId);
		
		rf.setBkid(b);
		rf.setUserId(u);
	}


	public List<IssueRecord> getIssueDetail(Integer userId) {
		
		List<IssueRecord> issueRecord= issueRecordInterface.findByUserId(userId);
		return issueRecord;
	}


	
}
