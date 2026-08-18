package edu.infosys.finCoreBankApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.infosys.finCoreBankApplication.bean.Transaction;
import edu.infosys.finCoreBankApplication.dao.AccountDao;
import edu.infosys.finCoreBankApplication.dao.TransactionDao;
import edu.infosys.finCoreBankApplication.service.TransactionService;

@RestController
@RequestMapping("/fincore/")
@CrossOrigin(origins="http://localhost:3737",allowCredentials = "true")
public class TransactionController {
	
	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private TransactionService service;
	
	@GetMapping("/trans-info")
	public String generateTransactionNumber() {
		return service.generateTransactionNumber();
	}
 
	/* @GetMapping("/trans-info/{cno}")
	public List<Transaction> getTransactionByCustomer(@PathVariable Long cno) {
		return transactionDao.getTransactionByCustomer(cno);
	}
	@GetMapping("/trans-ac/{acno}")
	public List<Transaction> getTransactionByAccount(@PathVariable Long acno) {
		return transactionDao.getTransactionByAccount(acno);
	}
	@GetMapping("/trans-type/{type}")
	public List<Transaction> getTransactionByType(@PathVariable String type) {
		return transactionDao.getTransactionByType(type);
	}
 */

    @GetMapping("/trans/customer/{customerId}")
    public List<Transaction> getTransactionByCustomer(@PathVariable Long customerId) {
        return transactionDao.getTransactionByCustomer(customerId);
    }

    @GetMapping("/trans/account/{accountNumber}")
    public List<Transaction> getTransactionByAccount(@PathVariable Long accountNumber) {
        return transactionDao.getTransactionByAccount(accountNumber);
    }
	@GetMapping("/trans/type/{type}")
    public List<Transaction> getTransactionByType(@PathVariable String type) {
        return transactionDao.getTransactionByType(type);
    }

	@PostMapping("/trans")
	public Integer addTransaction(@RequestBody Transaction transaction) {
		int flag=0;
	 if((transaction.getTransactionType().equalsIgnoreCase("Withdraw")) && ((accountDao.getBalanceByAccountNumber(transaction.getAccountNumber())<=5000.00))) {
		  flag=1;
	  }
	  else {
	  service.balanceUpdate(transaction);
	  transactionDao.addTransaction(transaction);
	  flag=2;
	  }
	  return flag;
	}
	@GetMapping("/trans")
	public List<Transaction> getAllTransactions() {
		return transactionDao.getAllTransactions();
	}
 
	@GetMapping("/trans/{id}")
	public Transaction getTransactionById(@PathVariable String id) {
		return transactionDao.getTransactionById(id);
	}
 
	@DeleteMapping("/trans/{id}")
	public void deleteTranscationById(@PathVariable String id) {
		transactionDao.deleteTranscationById(id);
	}
//	@GetMapping("/trans-info")
//	public Long getMaxTransactionId() {
//		return transactionDao.getMaxTransactionId();
//	}
	
}
