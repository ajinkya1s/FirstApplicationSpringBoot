package app.com.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.com.pojos.Payments;

public interface PaymentDaoInter extends JpaRepository<Payments, Integer> {


	
}

