package app.com.controllers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.annotation.MultipartConfig;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import app.com.daos.IUserDao;
import app.com.pojos.Book;
import app.com.pojos.IssueRecord;
import app.com.pojos.Payments;
import app.com.pojos.User;
import app.com.services.UserServices;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@MultipartConfig
public class UserController 
{
	int otpNo;
	@Autowired
	private IUserDao dao;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private JavaMailSender sender;
	
    Logger logger = LogManager.getLogger(UserController.class);


	@PutMapping("/login")
	public ResponseEntity<?> validate(@RequestBody User u)
	{
		logger.debug(u.getEmail() + " "+ u.getPassword());
		
		try {
			User user = userServices.validateUser(u.getEmail(), u.getPassword());
			if (user != null)
				return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);	
		}
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
	}
	@PostMapping("/chpa/{otp}")
	//public ResponseEntity<?> changePassword(@RequestParam String email,@RequestParam String password,@RequestParam String newPassword)
	public ResponseEntity<?> changePassword(@RequestBody User u,@PathVariable Integer otp)
	{
		if(otp==otpNo)
		{
		User user = userServices.changePassword(u.getEmail(),u.getPassword());
		if(user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);			
		
	}
	@PostMapping("/edit")
	public ResponseEntity<?> editProfile(@RequestBody User u )
	{
		User user; 
		try {
			user = userServices.changeProfile(u.getEmail(),u.getPassword(),u.getName(),u.getPhone());
			return new ResponseEntity<User>(user, HttpStatus.OK);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);			
		}
	}
	@GetMapping("/find/{bookname}")
	public ResponseEntity<?> findBook(@PathVariable String bookname)
	{
		Book book = userServices.findBook(bookname);
		if(book != null)
		{
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/deleteBook/{bookname}")
	public ResponseEntity<?> deleteUser(@PathVariable String bookname)
	{
		Book book = userServices.deleteBook(bookname);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	
	@PostMapping("/pay/{id}")
	public ResponseEntity<?> makePayment(@RequestBody Payments p1,@PathVariable Integer id)
	{
		
		try {
			userServices.makePayment(id,p1);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestParam(value="file") MultipartFile file,@RequestParam(value="user") String u1)
	{
		try {
			User u2=new ObjectMapper().readValue(u1,User.class);
			u2.setImageblob(file.getBytes());
			userServices.register(u2);
			//dao.register(u2);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		//dao.register(u1);
		
	}
	
	@GetMapping("/pass/{email}")
	public ResponseEntity<?> checkOtp(@PathVariable String email)
	{
		System.out.println("in ch pass cont");
		Random rand=new Random();
		otpNo=rand.nextInt(1000);
		SimpleMailMessage mesg=new SimpleMailMessage();
		String eemail=email+".com";
		mesg.setTo(eemail);
		mesg.setSubject("Ebook Management System");
		String name="your otp is"+otpNo;
		mesg.setText(name);
		sender.send(mesg);
		return new ResponseEntity<Integer>(otpNo, HttpStatus.OK);
	}
	
	@PutMapping("/getdetails/{bookId}")
	public ResponseEntity<?> getDetails(@RequestBody Integer userId,@PathVariable Integer bookId)
	{
		System.out.println("in con issue");
		IssueRecord rf=new IssueRecord();
		Date date=new Date();
		rf.setIssueDate(date);
		userServices.insertDetail(rf,userId,bookId);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@GetMapping("/getIssueDetail/{userId}")
	public ResponseEntity<?> getIssueDetail(@PathVariable Integer userId)
	{
		List<IssueRecord> detail=userServices.getIssueDetail(userId);
		return new ResponseEntity<List<IssueRecord>>(detail, HttpStatus.OK);
	}
	
	@PostMapping("/uploaddata")
	public ResponseEntity<?> uploadData(@RequestParam("datafile") MultipartFile datafile)
	{
		try {
			dao.savaData(datafile);
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}	

