package edu.infosys.finCoreBankApplication.dao;
	import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.infosys.finCoreBankApplication.bean.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT MAX(accountNumber) FROM Account")
    public Long getMaxAccountNumber();

    @Query("SELECT a FROM Account a WHERE a.status = ?1")
    public List<Account> getAccountsByStatus(String status);

    @Query("Select a from Account a where a.customerId=?1 ")
	public List<Account> getAccountsByCustomerId(Long customerId);
    
	@Query("Select balance from Account where accountNumber=?1 ")
	public Double getBalanceByAccountNumber(Long accountNumber);	
}
