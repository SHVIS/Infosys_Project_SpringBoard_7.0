package edu.infosys.finCoreBankApplication.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.infosys.finCoreBankApplication.bean.Account;

@Repository
@Service
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private AccountRepository repository;

	@Override
	public void addAccount(Account account) {
		repository.save(account);
		
	}

	@Override
	public Account getAccountByNumber(Long accountNumber) {
		return repository.findById(accountNumber).get();
	}

	@Override
	public List<Account> getAccounts() {
		return repository.findAll();
	}

	@Override
	public void deleteAccountByNumber(Long accountNumber) {
		repository.deleteById(accountNumber);
		
	}

	@Override
	public Long getMaxAccountNumber() {
		return repository.getMaxAccountNumber();
	}

	@Override
	public List<Account> getAccountsByStatus(String status) {
		return repository.getAccountsByStatus(status);
	}

	@Override
	public List<Account> getAccountsByCustomerId(Long accountId) {
		return repository.getAccountsByCustomerId(accountId);
	}

	@Override
	public Double getBalanceByAccountNumber(Long accountNumber) {
		return repository.getBalanceByAccountNumber(accountNumber);
	}

}
