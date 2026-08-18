package edu.infosys.finCoreBankApplication.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.infosys.finCoreBankApplication.bean.Account;
import edu.infosys.finCoreBankApplication.bean.Customer;
import edu.infosys.finCoreBankApplication.dao.AccountDao;
import edu.infosys.finCoreBankApplication.dao.CustomerDao;

@Service
public class AccountService {
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private BankUserService service;
	
	public Long generateAccountNumber() {
		Long value=accountDao.getMaxAccountNumber();
		if (value==null)
		value=8000001L;
		else
		value=value+1;
		return value;
	}
	
	public List<Account> getAccountsByCustomerId(){
		String userId=service.getUserId();
		Customer customer=customerDao.getCustomerByUsername(userId);
		return accountDao.getAccountsByCustomerId(customer.getCustomerId());
	}
	
	public List<Long> getAccountIdsByCustomerId(){
		   List<Account> accountList=getAccountsByCustomerId();
		   List<Long> numberList=new ArrayList<Long>();
		   for(Account account:accountList) {
			   numberList.add(account.getAccountNumber());
		   }
		   return numberList;
		}
	
	public List<Account> getAccountsByCustomerIdAndType(String type){
		 List<Account> accountList=getAccountsByCustomerId();
		 List<Account> typeList=new ArrayList<Account>();
		  for(Account account:accountList) {
			   if(account.getAccountType().equalsIgnoreCase(type)){
			    typeList.add(account);
			   }
		   }
		   return typeList;
	}
	public List<Long> getAccountIdsByCustomerIdAndType(String type){
		   List<Account> accountList=getAccountsByCustomerId();
		   List<Long> numberList=new ArrayList<Long>();
		   for(Account account:accountList) {
			   if(account.getAccountType().equalsIgnoreCase(type)){
			   numberList.add(account.getAccountNumber());
			   }
		   }
		   return numberList;
	}
	public Account createAccount(Account account) {
        if (account.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID is required to create an account");
        }
        Customer customer;
        try {
            customer = customerDao.getCustomerById(account.getCustomerId());
        } catch (java.util.NoSuchElementException ex) {
            customer = null;
        }
        if (customer == null)
            throw new IllegalArgumentException("No customer found for customerId: " + account.getCustomerId());
        
        account.setStatus(customer.getStatus());
        accountDao.addAccount(account);
        return account;
    }
//
//public Account setCustomerDetails(Account account) {
//	Account account=service.getUser();
//	customer.setCustomerName(account.getPersonalName());
//	customer.setUsername(account.getUsername());
//	customer.setEmail(account.getEmail());
//	return customer;
//}
	

    private static final String ACTIVE = "A";
    private static final String LOAN = "LOAN";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	 public Account createLoanAccount(Long customerId, Double openingBalance) {
	        if (customerId == null) throw new IllegalArgumentException("Customer ID is required");

	        Customer customer = customerDao.getCustomerById(customerId);
	        if (customer == null || !ACTIVE.equalsIgnoreCase(customer.getStatus())) {
	            throw new IllegalArgumentException("Customer is not active");
	        }

	        Account account = new Account();
	        account.setAccountNumber(generateAccountNumber());
	        account.setCustomerId(customerId);
	        account.setAccountType(LOAN);
	        account.setBalance(openingBalance == null ? 0.0 : openingBalance);
	        account.setStatus(ACTIVE);
	        account.setAccountOpenDate(LocalDateTime.now().format(FORMATTER));

	        accountDao.addAccount(account);
	        return account;
	    }
 
}
