package edu.infosys.finCoreBankApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import edu.infosys.finCoreBankApplication.bean.CustomerLoan;
import edu.infosys.finCoreBankApplication.bean.LoanRepayment;
import edu.infosys.finCoreBankApplication.service.CustomerLoanService;

@RestController
@RequestMapping("/fincore/")
@CrossOrigin(origins="http://localhost:3737",allowCredentials = "true")

public class CustomerLoanController {
	
	@Autowired
	private CustomerLoanService service;
	
	
    @PostMapping("/loan-applications")
    public ResponseEntity<CustomerLoan> apply(@RequestBody CustomerLoan application) {
        return ResponseEntity.ok(service.apply(application));
    }

    @GetMapping("/loan-applications")   //getAllCustomerLoans
    public List<CustomerLoan> getAll() { return service.getAll(); }

    @GetMapping("/loan-applications/{applicationId}")
    public CustomerLoan getById(@PathVariable String applicationId) { return service.getById(applicationId); }

    @GetMapping("/loan-applications/status/{status}")
    public List<CustomerLoan> getByStatus(@PathVariable String status) { return service.getByStatus(status.toUpperCase()); }

    @GetMapping("/loan-applications/customer/{customerId}")
    public List<CustomerLoan> getByCustomer(@PathVariable Long customerId) { return service.getByCustomer(customerId); }

    @GetMapping("/loan-applications/account/{accountNumber}")
    public List<CustomerLoan> getByAccount(@PathVariable Long accountNumber) { return service.getByAccount(accountNumber); }

    @PutMapping("/loan-applications/{applicationId}/approve")
    public CustomerLoan approve(@PathVariable String applicationId) { return service.approve(applicationId); }

    @PutMapping("/loan-applications/{applicationId}/reject")
    public CustomerLoan reject(@PathVariable String applicationId, @RequestBody(required = false) Map<String, String> body) {
        String reason = body == null ? null : body.get("reason");
        return service.reject(applicationId, reason);
    }

    @PostMapping("/loan-applications/{applicationId}/repayments")
    public LoanRepayment repay(@PathVariable String applicationId, @RequestBody LoanRepayment repayment) {
        return service.repay(applicationId, repayment);
    }

    @GetMapping("/loan-applications/{applicationId}/repayments")
    public List<LoanRepayment> getRepayments(@PathVariable String applicationId) {
        return service.getRepayments(applicationId);
    }

    @GetMapping("/loan-repayments/customer/{customerId}")
    public List<LoanRepayment> getRepaymentsByCustomer(@PathVariable Long customerId) {
        return service.getRepaymentsByCustomer(customerId);
    }

    @GetMapping("/loan-repayments/account/{accountNumber}")
    public List<LoanRepayment> getRepaymentsByAccount(@PathVariable Long accountNumber) {
        return service.getRepaymentsByAccount(accountNumber);
    }

    @GetMapping("/loan-application-id")
    public String generateCustomerLoanId() { return service.generateCustomerLoanId(); }

    @GetMapping("/loan-repayment-id")
    public String generateRepaymentId() { return service.generateRepaymentId(); }
	

	
	
	
/*	ACTAUL FUNCTION TO BE IN THIS FILE RE-DEVELOPMENT OF LOAN APPLICATION
 * 
 * @GetMapping("/cust-loan-cust/{id}")
	public CustomerLoan checkPaymentStatus(@PathVariable String id) {
			return service.checkPaymentStatus(id);
	}
	@GetMapping("/cust-loan/{id}")
	public CustomerLoan getLoanById (@PathVariable String id) {
	return customerLoanDao.getLoanById(id);
	}
	@GetMapping("/cust-loan/{id}/{status}")
	public CustomerLoan getLoanById(@PathVariable String id,@PathVariable String status) {
		CustomerLoan customerLoan=customerLoanDao.getLoanById(id);
		customerLoan.setStatus(status);
		if(status.equalsIgnoreCase("A")) {
			Long newId=aService.generateAccountNumber();
			customerLoan.setPaymentAccountNumber(newId);
			customerLoan.setLoanDate(LocalDate.now().toString());
			}
		return customerLoan;
	}
	@GetMapping("/cust-loan-cust")
	public List<CustomerLoan> getCustomerLoanByCustomerId(){
		return service.getCustomerLoanByCustomerId();
	}
	@GetMapping("/cust-loan")
	public List<CustomerLoan> getAllCustomerLoans() {
	return customerLoanDao.getAllCustomerLoans();
	}
	@DeleteMapping("/cust-loan/{id}")
	public void deleteCustomerLoanById(@PathVariable String id) {
	customerLoanDao.deleteCustomerLoanById(id);
	}
	@GetMapping("/cust-loan-id")
	public String generateCustomerLoanId() {
	return service.generateCustomerLoanId();
	}
	@GetMapping("/cust-loan/{status}")
	public List<CustomerLoan> getCustomerLoanByStatus(@PathVariable String status) {
	return customerLoanDao.getCustomerLoanByStatus (status);
	}
	*/
}
