package edu.infosys.finCoreBankApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.infosys.finCoreBankApplication.bean.Account;
import edu.infosys.finCoreBankApplication.dao.AccountDao;
import edu.infosys.finCoreBankApplication.dao.AccountRepository;

import edu.infosys.finCoreBankApplication.service.AccountService;
@RestController
@RequestMapping("/fincore/")
@CrossOrigin(origins="http://localhost:3737",allowCredentials = "true")

public class AccountController{
	
	
	@Autowired
	private AccountService service;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountRepository repository;
	
	
	
	@PostMapping("/account")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        try {
            Account saved = service.createAccount(account);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
//	public void addAccount(@RequestBody Account account) {
//		accountDao.addAccount(account);
//	}
	
	@PutMapping("/account")
	public void updateAccount(@RequestBody Account account) {
		accountDao.addAccount(account);
	}
	
	@GetMapping("/account/{accountNumber}")
	public Account getAccountByNumber(@PathVariable Long accountNumber) {
		return accountDao.getAccountByNumber(accountNumber);
	}
 
	@GetMapping("/account")
	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		return accountDao.getAccounts();
	}
 
	@DeleteMapping("/account/{accountId}")
	public void deleteAccountByNumber(@PathVariable Long accountId) {
		accountDao.deleteAccountByNumber(accountId);
	}
 
	@GetMapping("/account-id")
	public Long generateAccountNumber() {
		return service.generateAccountNumber();
	}
 
	@GetMapping("/account-info")
	public List<Account> getAccountsByCustomerId() {
		return service.getAccountsByCustomerId();
	}
	
	@GetMapping("/balance-info/{accountNumber}")
	public Double getBalanceByAccountNumber(@PathVariable Long accountNumber) {
		return accountDao.getBalanceByAccountNumber(accountNumber);
	}
	@GetMapping("/id-list")
	public List<Long> getAccountIdsByCustomerId(){
		return service.getAccountIdsByCustomerId();
	}
	
	@GetMapping("/id-list/{accountType}")
	public List<Long> getAccountIdsByCustomerIdAndType(@PathVariable String accountType){
		return service.getAccountIdsByCustomerIdAndType(accountType);
	}
 
@	GetMapping("/accounts/{accountType}")
	public List<Account> getAccountsByAccountType(@PathVariable String accountType){
		return accountDao.getAccountsByAccountType(accountType);
	}
	@GetMapping("/account-info/{accountType}")
	public List<Account> getAccountsByCustomerIdAndType(@PathVariable String accountType){
		return service.getAccountsByCustomerIdAndType(accountType);
	}
 
}
