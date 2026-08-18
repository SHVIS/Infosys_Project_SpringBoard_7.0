package edu.infosys.finCoreBankApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.infosys.finCoreBankApplication.bean.CustomerLoan;

public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, String> {
	@Query(value = "SELECT MAX(CAST(SUBSTRING(customer_loan_id, 3) AS INTEGER)) FROM customer_loan", nativeQuery = true)
    public Long getMaxCustomerLoanId();
	@Query("Select a from CustomerLoan a where a.status=?1 ")
	public List<CustomerLoan> getCustomerLoanByStatus (String status);
	@Query("Select a from CustomerLoan a where a.customerId=?1")
	public List<CustomerLoan> getCustomerLoanByCustomerId(Long customerId);
 
	List<CustomerLoan> findByCustomerIdOrderByLoanDateDesc(Long customerId);
    List<CustomerLoan> findBySavingsAccountNumberOrderByLoanDateDesc(Long accountNumber);

}
