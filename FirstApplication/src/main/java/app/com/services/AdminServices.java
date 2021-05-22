package app.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.com.daos.BookDaoInter;
import app.com.daos.UserDaoInter;
import app.com.pojos.Book;
import app.com.pojos.User;

@Service
public class AdminServices {
	
	
	@Autowired
	private UserDaoInter userInterface;
	
	@Autowired
	private BookDaoInter bookInterface;
  
	public List<User> listAllUsers() {
		return this.userInterface.findAll();
	}
	
	public List<Book> listAllbooks() {
		return this.bookInterface.findAll();
	}
	
	public List<User> findListOfUser(String name) {
		return this.userInterface.findByName(name);
	}

}
