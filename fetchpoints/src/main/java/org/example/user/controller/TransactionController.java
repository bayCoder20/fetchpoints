package org.example.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.example.user.model.BalanceModel;
import org.example.user.model.PayerModel;
import org.example.user.model.TransactionModel;
import org.example.user.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fetchpoints/")
public class TransactionController {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@GetMapping("/balance")
	public ResponseEntity<ArrayList<Map<String, Object>>> balance() {
		List<BalanceModel> balanceResult = transactionRepository.getBalanceQuery();
		
		Map<String, Object>  map = null;
		ArrayList<Map<String, Object>>  mapList = new ArrayList<Map<String, Object>>();
		
		for (BalanceModel balance : balanceResult) {
			map = new LinkedHashMap<String, Object>();
			map.put("totalPoints", balance.getPoints());
			map.put("payerName", balance.getPayerName());
			mapList.add(map);
		}

		return new ResponseEntity<ArrayList<Map<String, Object>>>(mapList, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public String addPoints(@RequestBody TransactionModel pointObject) {
		String payerName = pointObject.getPayerName();
		Integer pointChange = pointObject.getPoints(); //negative if deduction
		Integer newPointValue = null;
		Integer deductionAmount = null;
		
		if( pointChange < 0) {			
			
			List<PayerModel> payerPointsResult = transactionRepository.getPayerPoints(pointObject.getPayerName());		

			Integer pointsId = null;
			
			for (int i = 0; i< payerPointsResult.size(); i++) {
				pointsId = payerPointsResult.get(i).getId();
				pointChange = pointChange + payerPointsResult.get(i).getPoints();				
						
				if(pointChange > 0) {  //check if existing points remain positive
					newPointValue = pointChange;
					deductionAmount = pointObject.getPoints();
					Date updatedDateTime = new java.sql.Timestamp(new java.util.Date().getTime());					
					transactionRepository.updatePointBalance(newPointValue, 1, updatedDateTime, pointsId);
					transactionRepository.addLogEntry("Payer specific point deduction", payerName, deductionAmount, pointsId);
					break;
				}
				else {
					newPointValue = 0;
					Date updatedDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
					deductionAmount = payerPointsResult.get(i).getPoints() * -1;  //make negative
					transactionRepository.updatePointBalance(newPointValue, 0, updatedDateTime, pointsId);
					transactionRepository.addLogEntry("Payer specific point deduction", payerName, deductionAmount, pointsId);
				}
			}				
		}
		else if (pointChange > 0) {				
			transactionRepository.addPayer(payerName, pointChange);
			transactionRepository.addLogEntry("Added points", payerName, pointChange, null);
		}
		return "Applied "+pointObject.getPoints()+" points to "+payerName;
	}
	
	@PostMapping("/deduct")
	public ResponseEntity<ArrayList<Map<String, Object>>> deduct(@RequestBody TransactionModel pointObject) {		
		Map<String, Object>  map = null;
		ArrayList<Map<String, Object>>  mapList = new ArrayList<Map<String, Object>>();
		
		Integer adder = pointObject.getPoints();
		
		List<TransactionModel> availablePointsResult = transactionRepository.getAvailablePoints();	
		
		for (int i = 0; i< availablePointsResult.size(); i++) {				
			if(adder > 0) {
				Integer deductionAmount = null;
				Integer remainingPoints = null;
				Integer availableFlag = null;
				map = new LinkedHashMap<String, Object>();
				Integer pointsId = availablePointsResult.get(i).getId();
				String payerName = availablePointsResult.get(i).getPayerName();
				map.put("payerName", payerName); //set payer name
				
				if((availablePointsResult.get(i).getPoints() - adder) < 0 ) {  //test whether the deduction uses all points in record
					
					adder = adder - availablePointsResult.get(i).getPoints();  //subtract available points from deduct amount
					deductionAmount = availablePointsResult.get(i).getPoints() * -1;
					map.put("deductionAmount", deductionAmount);
					remainingPoints = 0;
					availableFlag = 0;
					Date updatedDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
					transactionRepository.updatePointBalance(remainingPoints, availableFlag, updatedDateTime, pointsId);
					transactionRepository.addLogEntry("General point deduction", payerName, deductionAmount, pointsId);
					mapList.add(map);
				}
				else {  //test whether the deduction does not use all points in record
					deductionAmount = adder * -1;
					map.put("deductionAmount", deductionAmount);
					remainingPoints = availablePointsResult.get(i).getPoints() - adder;
					Date updatedDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
					if(availablePointsResult.get(i).getPoints() - adder <= 0) {
						availableFlag = 0; //set availability flag
					}
					else {
						availableFlag  = 1; //set availability flag
					}
					
					transactionRepository.updatePointBalance(remainingPoints, availableFlag, updatedDateTime, pointsId);
					transactionRepository.addLogEntry("General point deduction", payerName, deductionAmount, pointsId);
					mapList.add(map);
					break;
				}
			}
		}
		return new ResponseEntity<ArrayList<Map<String, Object>>>(mapList, HttpStatus.OK);
	}
	
	@RequestMapping("/simpleTest")
	@Transactional
	@CrossOrigin
    public String basicTest() {
        return "For Controller Testing Example";
    }
}
