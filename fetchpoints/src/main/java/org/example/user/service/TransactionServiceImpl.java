package org.example.user.service;

import org.example.user.model.TransactionModel;
import org.example.user.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{
	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
