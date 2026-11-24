package edu.infosys.finCoreBankApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import edu.infosys.finCoreBankApplication.bean.Loan;

public interface LoanRepository extends JpaRepository<Loan, String> {
	//got mixed query due to store id as String "L"+1000008
	@Query(value = "SELECT MAX(CAST(SUBSTRING(loan_id, 2) AS INTEGER)) FROM loan",nativeQuery = true)
	public Long getMaxLoanId();
    @Query("Select l from Loan l where l.loanStatus=?1")
    public List<Loan> getLoansByStatus(String status);
    @Query(value = "SELECT loanId FROM Loan")
    public List<String> getLoanIdList();
 
    
    
    
    
//    @Query("SELECT l FROM Loan l WHERE l.customerId = ?1")
//    public List<Loan> getLoansByCustomerId(Long customerId);
//
//    @Query("SELECT l FROM Loan l WHERE l.accountNumber = ?1")
//    public List<Loan> getLoansByAccountNumber(Long accountNumber);
//
//    @Query("SELECT l FROM Loan l WHERE l.loanAmount = ?1")
//    public List<Loan> getLoansByLoanAmount(Double loanAmount);
//
//    @Query("SELECT l FROM Loan l WHERE l.interestRate = ?1")
//    public List<Loan> getLoansByInterestRate(Double interestRate);
//
//    @Query("SELECT l FROM Loan l WHERE l.loanTenure = ?1")
//    public List<Loan> getLoansByLoanTenure(Integer loanTenure);
//
//    @Query("SELECT totalCost FROM Loan WHERE loanId = ?1")
//    public Double getOutstandingAmount(String loanId);
}