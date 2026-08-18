package edu.infosys.finCoreBankApplication.dao;

import java.util.List;

import edu.infosys.finCoreBankApplication.bean.CustomerLoan;

public interface CustomerLoanDao {
	public void addCustomerLoan (CustomerLoan customerLoan);
	public CustomerLoan getLoanById(String customerLoanId);
	public List<CustomerLoan> getAllCustomerLoans();
	public void deleteCustomerLoanById(String customerLoanId);
	public Long getMaxCustomerLoanId();
	public List<CustomerLoan> getCustomerLoanByStatus (String status);
	List<CustomerLoan> getCustomerById(Long customerId);
	List<CustomerLoan> getByAccountNumber(Long accountNumber);
 
}
