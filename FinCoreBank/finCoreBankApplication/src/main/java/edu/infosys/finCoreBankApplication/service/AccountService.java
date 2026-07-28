package edu.infosys.finCoreBankApplication.service;

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
	 
//
//public Account setCustomerDetails(Account account) {
//	Account account=service.getUser();
//	customer.setCustomerName(account.getPersonalName());
//	customer.setUsername(account.getUsername());
//	customer.setEmail(account.getEmail());
//	return customer;
//}
 
}
