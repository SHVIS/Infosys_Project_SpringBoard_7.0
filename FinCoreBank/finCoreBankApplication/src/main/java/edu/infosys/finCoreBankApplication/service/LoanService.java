package edu.infosys.finCoreBankApplication.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.infosys.finCoreBankApplication.bean.Loan;
import edu.infosys.finCoreBankApplication.dao.LoanDao;

@Service
public class LoanService {

    @Autowired
    private LoanDao loanDao;

    public String generateLoanId() {
   
    Long value=loanDao.getMaxLoanId();
    if (value==null)
    value=1000001L;
    else
    value=value+1;
    String newId="L"+value;
    return newId; 
    }
    
    public Loan calculateLoanDetails(Loan loan) {

        Double amount = loan.getLoanAmount();

        // Minimum loan amount check
        if (amount==null || amount < 100000)
            throw new RuntimeException("Minimum loan amount should be 100000");

       
        Integer months = (int) (loan.getLoanTenure() * 12); // years --> months tenure
        Double monthlyRate = loan.getInterestRate() / (12 * 100);  // Monthly interest rate

        // EMI Formula: (P × R × (1+R)^N ) / ((1+R)^N - 1)
        Double emi =  (double) Math.round((amount * monthlyRate * Math.pow(1 + monthlyRate, months))
                        / (Math.pow(1 + monthlyRate, months) - 1));
        		
        Double totalCost = emi * months; // Total amount paid
        Double totalInterest = totalCost - amount; // Total interest paid
        if (months <= 0) 
                    throw new RuntimeException("Loan tenure must be at least 1 month");
                
        // Set calculated values
        loan.setTotalTenure(months);
        loan.setEmiPayable(emi);
        loan.setTotalCost(totalCost);
        loan.setTotalInterestPayable(totalInterest);

        return loan;
    }
    
 // Customer side - show only active loan schemes
 	public List<Loan> getActiveLoans(){
 		return loanDao.getLoansByStatus("A");
 	}
}