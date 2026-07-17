package edu.infosys.finCoreBankApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.infosys.finCoreBankApplication.dao.AccountDao;

@Service
public class AccountService {
	
	@Autowired
	private AccountDao accountDao;
	public Long generateAccountNumber() {
	Long value=accountDao.getMaxAccountNumber();
	if (value==null)
	value=8000001L;
	else
	value=value+1;
	return value;
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
