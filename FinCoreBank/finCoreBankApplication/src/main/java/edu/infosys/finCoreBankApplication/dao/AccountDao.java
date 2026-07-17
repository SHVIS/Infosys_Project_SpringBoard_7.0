package edu.infosys.finCoreBankApplication.dao;

import java.util.List;

import edu.infosys.finCoreBankApplication.bean.Account;

public interface AccountDao {
    public void addAccount(Account account);
    public Account getAccountByNumber(Long accountNumber);
    public List<Account> getAccounts();
    public void deleteAccountByNumber(Long accountNumber);
    public Long getMaxAccountNumber();
    public List<Account> getAccountsByStatus(String status);
    public List<Account> getAccountsByCustomerId(Long customerId);
    public Double getBalanceByAccountNumber(Long accountNumber);
}