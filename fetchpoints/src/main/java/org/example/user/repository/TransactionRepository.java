package org.example.user.repository;

import java.util.Date;
import java.util.List;

import org.example.user.model.BalanceModel;
import org.example.user.model.PayerModel;
import org.example.user.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
	
	@Query(value = "select payerName, sum(points) as points from tbl_points group by payerName order by payerName",nativeQuery = true)
	public List<BalanceModel> getBalanceQuery();
	
	@Query(value = "select id, points from tbl_points where available = 1 and payerName = ?1",nativeQuery = true)
	public List<PayerModel> getPayerPoints(String payerName); 
	
	@Modifying
	@Transactional
	@Query(value = "update tbl_points set points = ?1, available = ?2, modifiedDate = ?3 where id = ?4",nativeQuery = true)
	public void updatePointBalance(Integer points, Integer available, Date date, Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "insert into tbl_points (payerName, points, available) values(?1, ?2, 1)",nativeQuery = true)
	public void addPayer(String payerName, Integer points);
	
	@Query(value = "select id, points, payerName, available, createdDate, modifiedDate from tbl_points where available = 1 order by id asc",nativeQuery = true)
	public List<TransactionModel> getAvailablePoints();
	
	@Modifying
	@Transactional
	@Query(value = "insert into tbl_log (event, payerName, points, pointsId) values(?1, ?2, ?3, ?4)",nativeQuery = true)
	public void addLogEntry(String event, String payerName, Integer points, Integer pointsId);
	
}
