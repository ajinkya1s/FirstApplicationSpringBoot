package app.com.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.com.pojos.Book;
import app.com.pojos.User;

public interface UserDaoInter extends JpaRepository<User, Integer> {
	
	
    public List<User> findByName(String name);

	@Query("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(:name) and LOWER(u.password) = LOWER(:password)")
	public User validateUser(@Param("name") String name,@Param("password") String password);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
	public User changePassword(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email) and LOWER(u.password) = LOWER(:password)")
	public User changeProfile(String email, String password);

	

}
