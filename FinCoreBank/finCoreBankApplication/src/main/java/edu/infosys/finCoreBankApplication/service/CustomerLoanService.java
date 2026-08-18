package edu.infosys.finCoreBankApplication.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.infosys.finCoreBankApplication.bean.Account;
import edu.infosys.finCoreBankApplication.bean.Customer;
import edu.infosys.finCoreBankApplication.bean.CustomerLoan;
import edu.infosys.finCoreBankApplication.bean.Loan;
import edu.infosys.finCoreBankApplication.bean.LoanRepayment;
import edu.infosys.finCoreBankApplication.bean.Transaction;
import edu.infosys.finCoreBankApplication.dao.AccountDao;
import edu.infosys.finCoreBankApplication.dao.CustomerDao;
import edu.infosys.finCoreBankApplication.dao.CustomerLoanDao;
import edu.infosys.finCoreBankApplication.dao.LoanDao;
import edu.infosys.finCoreBankApplication.dao.LoanRepaymentDao;
import edu.infosys.finCoreBankApplication.dao.TransactionDao;

@Service
public class CustomerLoanService {

    private static final String PENDING = "PENDING";
    private static final String APPROVED = "APPROVED";
    private static final String REJECTED = "REJECTED";
    private static final String CLOSED = "CLOSED";
    private static final String ACTIVE = "A";
    private static final String LOAN_ACCOUNT = "LOAN";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired private LoanRepaymentDao repaymentDao;
    @Autowired private CustomerDao customerDao;
    @Autowired private AccountDao accountDao;
    @Autowired private TransactionDao transactionDao;
    @Autowired private AccountService accountService;
    @Autowired private TransactionService transactionService;


// --------------------------Customer Loan Service Sir function Start

	
	@Autowired private LoanDao loanDao;
	@Autowired private CustomerLoanDao customerLoanDao;
	@Autowired private CustomerService service;
	
	public String generateCustomerLoanId() {
		Long value=customerLoanDao.getMaxCustomerLoanId();
		 if(value==null)
			 value=1000001L;
		 else
			 value=value+1;
		 String newId="CL"+value;
		  return newId;
	}
	public CustomerLoan setAppliedCustomerLoan(CustomerLoan customerLoan) {
		   Customer customer=service.getCustomerByUsername();
		   Loan loan=loanDao.getLoanById(customerLoan.getLoanId());
		   Integer totalTenure=loan.getTotalTenure();
		   customerLoan.setTotalTenure(totalTenure);
		   customerLoan.setCustomerId(customer.getCustomerId());
		   Double loanAmount=customerLoan.getLoanAmount();
		   double loanTimes=loanAmount/100000.00;
		   customerLoan.setEmiPayable(loan.getEmiPayable()*loanTimes);
		   Double totalInterestPayable=loan.getTotalInterestPayable()*loanTimes;
		   customerLoan.setTotalInterestPayable(totalInterestPayable);
		   Double totalCost=totalInterestPayable+loanAmount;
		   customerLoan.setTotalCost(totalCost);
		   customerLoan.setStatus("P");
		   return customerLoan;
	   }
	
//	public List<CustomerLoan> getCustomerLoanByCustomerId() {
//	Customer customer=service.getCustomerByUsername();
//	return customerLoanDao.getCustomerLoanByCustomerId(customer.getCustomerId());}
	
	public CustomerLoan checkPaymentStatus(String customerLoanId) {
		CustomerLoan customerLoan=customerLoanDao.getLoanById(customerLoanId);
	return	(customerLoan.getPaidTenure()==customerLoan.getTotalTenure()) 
			? null : customerLoan;
	}

//	--------------------------Customer Loan Service Sir function End
	
	
	
	public String generateRepaymentId() {
        String value = repaymentDao.getMaxRepaymentId();
        return value == null ? "RP1000001" : "RP" + (Long.parseLong(value.substring(2)) + 1);
    }

    @Transactional
    public CustomerLoan apply(CustomerLoan customerLoanApplication) {
        if (customerLoanApplication.getCustomerId() == null || customerLoanApplication.getLoanId() == null
                || customerLoanApplication.getSavingsAccountNumber() == null) {
            throw new RuntimeException("Customer, loan scheme and savings account are required");
        }

        Customer customer = customerDao.getCustomerById(customerLoanApplication.getCustomerId());
        if (customer == null || !ACTIVE.equalsIgnoreCase(customer.getStatus())) {
            throw new RuntimeException("Customer is not active");
        }

        Account savings = accountDao.getAccountByNumber(customerLoanApplication.getSavingsAccountNumber());
        if (savings == null|| !ACTIVE.equalsIgnoreCase(savings.getStatus())) 
            throw new RuntimeException("Selected savings account is not active");
        
        if (!customerLoanApplication.getCustomerId().equals(savings.getCustomerId())) 
            throw new RuntimeException("Selected savings account does not belong to the customer");
        
        if (LOAN_ACCOUNT.equalsIgnoreCase(savings.getAccountType())) 
            throw new RuntimeException("Please select a savings/current account for loan disbursement");
        

        Loan scheme = loanDao.getLoanById(customerLoanApplication.getLoanId());
        if (scheme == null || !ACTIVE.equalsIgnoreCase(scheme.getLoanStatus())) 
            throw new RuntimeException("Loan scheme is not available");
        

        for (CustomerLoan item : customerLoanDao.getCustomerById(customerLoanApplication.getCustomerId())) {
            if (customerLoanApplication.getLoanId().equals(item.getLoanId())
                    && (PENDING.equals(item.getStatus()) || APPROVED.equals(item.getStatus()))) {
                throw new RuntimeException("Customer already has an active customerLoanApplication for this loan scheme");
            }
        }

        double amount = customerLoanApplication.getLoanAmount() == null || customerLoanApplication.getLoanAmount() <= 0
                ? scheme.getLoanAmount() : customerLoanApplication.getLoanAmount();
        if (amount < 100000) throw new RuntimeException("Minimum loan amount should be 100000");

        int years = customerLoanApplication.getTotalTenure() == null || customerLoanApplication.getTotalTenure() <= 0
                ? scheme.getLoanTenure() : customerLoanApplication.getTotalTenure();
        if (years <= 0) throw new RuntimeException("Loan tenure should be greater than zero");

        double rate = scheme.getInterestRate() == null ? 0 : scheme.getInterestRate();
        if (rate <= 0) throw new RuntimeException("Loan scheme has an invalid interest rate");

        int months = years * 12;
        double monthlyRate = rate / 1200.0;
        double factor = Math.pow(1 + monthlyRate, months);
        double exactEmi = (amount * monthlyRate * factor) / (factor - 1);
        double emi = Math.round(exactEmi);
        double totalCost = emi * months;
        customerLoanApplication.setCustomerLoanId(generateCustomerLoanId());
        customerLoanApplication.setAccountNumber(null);
        customerLoanApplication.setSavingsAccountNumber(savings.getAccountNumber());
        customerLoanApplication.setPaymentAccountNumber(null);

        customerLoanApplication.setLoanAmount(amount);
        customerLoanApplication.setTotalTenure(months);
        customerLoanApplication.setInterestRate(rate);
        customerLoanApplication.setEmiPayable(emi);
        customerLoanApplication.setTotalInterestPayable(totalCost - amount);
        customerLoanApplication.setTotalCost(totalCost);

        customerLoanApplication.setOutstandingAmount(0.0);
        customerLoanApplication.setAmountPaidTillDate(0.0);

        customerLoanApplication.setStatus(PENDING);
        customerLoanApplication.setLoanDate(now());

        customerLoanApplication.setReviewedDate(null);
        customerLoanApplication.setRejectionReason(null);

        customerLoanDao.addCustomerLoan(customerLoanApplication);
        
        return customerLoanApplication;
    }

    public List<CustomerLoan> getAll() {
        return customerLoanDao.getAllCustomerLoans();
    }

    public List<CustomerLoan> getByStatus(String status) {
        return customerLoanDao.getCustomerLoanByStatus(status);
    }

    public List<CustomerLoan> getByCustomer(Long customerId) {
        return customerLoanDao.getCustomerById(customerId);
    }

    public List<CustomerLoan> getByAccount(Long accountNumber) {
        return customerLoanDao.getByAccountNumber(accountNumber);
    }
    public CustomerLoan getById(String id) {
    	CustomerLoan customerLoanApplication = customerLoanDao.getLoanById(id);
        if (customerLoanApplication == null) throw new RuntimeException("Loan customerLoanApplication not found");
        return customerLoanApplication;
    }

    @Transactional
    public CustomerLoan approve(String applicationId) {
    	CustomerLoan customerLoanApplication = getById(applicationId);
        if (!PENDING.equals(customerLoanApplication.getStatus())) {
            throw new RuntimeException("Only pending applications can be approved");
        }
        if (customerLoanApplication.getSavingsAccountNumber() == null) 
            throw new RuntimeException("Savings account was not selected in the customerLoanApplication");
        

        Account savings = accountDao.getAccountByNumber(customerLoanApplication.getSavingsAccountNumber());
        if (savings == null || !customerLoanApplication.getCustomerId().equals(savings.getCustomerId())
                || !ACTIVE.equalsIgnoreCase(savings.getStatus())
                || LOAN_ACCOUNT.equalsIgnoreCase(savings.getAccountType())) {
            throw new RuntimeException("Selected savings account is invalid");
        }

       
        double balance = savings.getBalance() == null ? 0.0 : savings.getBalance();
        savings.setBalance(round2(balance + customerLoanApplication.getLoanAmount()));
        accountDao.addAccount(savings);

      
        Account payment = accountService.createLoanAccount(customerLoanApplication.getCustomerId(), 0.0);
        customerLoanApplication.setPaymentAccountNumber(payment.getAccountNumber());
        customerLoanApplication.setAccountNumber(payment.getAccountNumber()); 
        customerLoanApplication.setStatus(APPROVED);
        customerLoanApplication.setAmountPaidTillDate(0.0);
        customerLoanApplication.setOutstandingAmount(customerLoanApplication.getTotalCost());
        customerLoanApplication.setReviewedDate(now());
        customerLoanApplication.setRejectionReason(null);
        customerLoanDao.addCustomerLoan(customerLoanApplication);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionService.generateTransactionNumber());
        transaction.setAccountNumber(savings.getAccountNumber());
        transaction.setCustomerId(customerLoanApplication.getCustomerId());
        transaction.setTransactionAmount(customerLoanApplication.getLoanAmount());
        transaction.setTransactionType("Loan Disbursement");
        transaction.setTransactionDate(now());
        transactionDao.addTransaction(transaction);
        return customerLoanApplication;
    }

    @Transactional
    public CustomerLoan reject(String applicationId, String reason) {
    	CustomerLoan customerLoanApplication = getById(applicationId);
        if (!PENDING.equals(customerLoanApplication.getStatus())) {
            throw new RuntimeException("Only pending applications can be rejected");
        }
        customerLoanApplication.setStatus(REJECTED);
        customerLoanApplication.setAccountNumber(null);
        customerLoanApplication.setPaymentAccountNumber(null);
        customerLoanApplication.setOutstandingAmount(0.0);
        customerLoanApplication.setReviewedDate(now());
        customerLoanApplication.setRejectionReason(reason == null || reason.isBlank() ? "Application rejected by bank" : reason);
        customerLoanDao.addCustomerLoan(customerLoanApplication);
        return customerLoanApplication;
    }

    @Transactional
    public LoanRepayment repay(String applicationId, LoanRepayment request) {
        CustomerLoan customerLoanApplication = getById(applicationId);
        if (!APPROVED.equals(customerLoanApplication.getStatus())) {
            throw new RuntimeException("Repayment is allowed only for approved loans");
        }
        double outstanding = customerLoanApplication.getOutstandingAmount() == null ? 0.0 : customerLoanApplication.getOutstandingAmount();
        if (outstanding <= 0) throw new RuntimeException("Loan has no outstanding amount");

        Double amount = request.getPaymentAmount();
        if (amount == null || amount <= 0) throw new RuntimeException("Repayment amount should be greater than zero");
        amount = round2(amount);
        if (amount > outstanding) throw new RuntimeException("Repayment cannot exceed outstanding amount");

        Long paymentId = customerLoanApplication.getPaymentAccountNumber();
        if (paymentId == null) paymentId = customerLoanApplication.getAccountNumber();
        if (paymentId == null) throw new RuntimeException("Repayment account has not been created");

        Account payment = accountDao.getAccountByNumber(paymentId);
        if (payment == null || !LOAN_ACCOUNT.equalsIgnoreCase(payment.getAccountType())
                || !customerLoanApplication.getCustomerId().equals(payment.getCustomerId())) {
            throw new RuntimeException("Dedicated loan repayment account not found");
        }

        double balance = payment.getBalance() == null ? 0.0 : payment.getBalance();
        if (balance < amount) throw new RuntimeException("Insufficient balance in the loan repayment account");

        double remaining = round2(outstanding - amount);
        double paid = round2((customerLoanApplication.getAmountPaidTillDate() == null ? 0.0 : customerLoanApplication.getAmountPaidTillDate()) + amount);
        payment.setBalance(round2(balance - amount));
        accountDao.addAccount(payment);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionService.generateTransactionNumber());
        transaction.setAccountNumber(paymentId);
        transaction.setCustomerId(customerLoanApplication.getCustomerId());
        transaction.setTransactionAmount(amount);
        transaction.setTransactionType("Loan Repayment");
        transaction.setTransactionDate(now());
        transactionDao.addTransaction(transaction);

        customerLoanApplication.setAmountPaidTillDate(paid);
        customerLoanApplication.setOutstandingAmount(remaining);
        if (remaining <= 0) customerLoanApplication.setStatus(CLOSED);
        customerLoanDao.addCustomerLoan(customerLoanApplication);

        LoanRepayment repayment = new LoanRepayment();
        repayment.setRepaymentId(generateRepaymentId());
        repayment.setApplicationId(applicationId);
        repayment.setCustomerId(customerLoanApplication.getCustomerId());
        repayment.setAccountNumber(paymentId);
        repayment.setPaymentAmount(amount);
        repayment.setPaymentDate(now());
        repayment.setPaymentMode("ACCOUNT");
        repayment.setRemainingOutstanding(remaining);
        repaymentDao.save(repayment);
        return repayment;
    }

    public List<LoanRepayment> getRepayments(String applicationId) { return repaymentDao.findByApplicationId(applicationId); }
    public List<LoanRepayment> getRepaymentsByCustomer(Long customerId) { return repaymentDao.findByCustomerId(customerId); }
    public List<LoanRepayment> getRepaymentsByAccount(Long accountNumber) { return repaymentDao.findByAccountNumber(accountNumber); }
    private String now() { return LocalDateTime.now().format(FORMATTER); }
    private double round2(double value) { return Math.round(value * 100.0) / 100.0; }
}