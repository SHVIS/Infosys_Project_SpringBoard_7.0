package edu.infosys.finCoreBankApplication.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.infosys.finCoreBankApplication.bean.CustomerLoan;

@Repository
@Service
public class CustomerLoanDaoImpl implements CustomerLoanDao {
    @Autowired
    private CustomerLoanRepository repository;

    @Override
    public void addCustomerLoan(CustomerLoan customerLoan) {
        repository.save(customerLoan);
    }

    @Override
    public CustomerLoan getLoanById(String customerLoanId) {
        return repository.findById(customerLoanId).get();
    }

    @Override
    public List<CustomerLoan> getAllCustomerLoans() {
        return repository.findAll();
    }

    @Override
    public void deleteCustomerLoanById(String customerLoanId) {
        repository.deleteById(customerLoanId);
    }

    @Override
    public Long getMaxCustomerLoanId() {
        return repository.getMaxCustomerLoanId();
    }

    @Override
    public List<CustomerLoan> getCustomerLoanByStatus(String status) {
    	 return repository.getCustomerLoanByStatus (status);
    }
	@Override
	public List<CustomerLoan> getCustomerById(Long customerId) {
	 	return repository.findByCustomerIdOrderByLoanDateDesc(customerId); 
	}
	 @Override
		public 
	    List<CustomerLoan> getByAccountNumber(Long accountNumber)
	    { return repository.findBySavingsAccountNumberOrderByLoanDateDesc(accountNumber); }
	    
    
}
