package app.com.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.com.pojos.IssueRecord;

public interface IssueRecordDaoInter extends JpaRepository<IssueRecord, Integer> {

	
	@Query("select i from IssueRecord i where LOWER(i.userId) = LOWER(:userId)")
	List<IssueRecord> findByUserId(Integer userId);


	
	
}

