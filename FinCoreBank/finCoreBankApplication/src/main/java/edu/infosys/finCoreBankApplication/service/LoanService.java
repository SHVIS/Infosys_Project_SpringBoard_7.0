package edu.infosys.finCoreBankApplication.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.infosys.finCoreBankApplication.bean.Customer;
import edu.infosys.finCoreBankApplication.bean.Loan;
import edu.infosys.finCoreBankApplication.dao.CustomerDao;
import edu.infosys.finCoreBankApplication.dao.LoanDao;

@Service
public class LoanService {

    @Autowired
    private LoanDao loanDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BankUserService service;

    public String generateLoanId() {
        String value = loanDao.getMaxLoanId();
        if(value == null)
            value = "L1000001";
        else {
            Integer id = Integer.parseInt(value.substring(1));
            id++;
            value = "L" + id;
        }
        return value;
    }
 // Customer side - show only active loan schemes
 	public List<Loan> getActiveLoans(){

 		return loanDao.getLoansByStatus("A");

 	}
    /*
     * Loan Calculation Logic
   
    Total tenure in months -> EMI -> Total interest -> Total cost
    
     */
    public Loan calculateLoanDetails(Loan loan) {
        Double amount = loan.getLoanAmount();
       
        
        // Minimum loan amount validation
        if(amount < 100000) 
            throw new RuntimeException("Minimum loan amount should be 100000");
        /*
         * User enters tenure in years.
         * 1y = 12 months , 5y = 60 months
         */
        Integer months =  loan.getLoanTenure() * 12;
        loan.setTotalTenure(months);
        

        /* Convert Yrs interest(20%/annually) -> monthly interest.
         * Monthly rate = 20/(12*100)
         */
        
        Double monthlyRate = loan.getInterestRate()/(12*100);

        //Formula:  EMI =  (P × R × (1+R)^N ) / ((1+R)^N - 1)
        
        Double emi =(double) Math.round(
        		 (amount *  monthlyRate * Math.pow(1 + monthlyRate, months))
        				/
        		(Math.pow(1 + monthlyRate, months)-1));


        loan.setEmiPayable(emi);

        /*
         * Total amount paid by customer
         */
        Double totalCost =(double) Math.round( emi * months);
        loan.setTotalCost(totalCost);

        /*
         * Total Interest paid to bank
         */
         loan.setTotalInterestPayable(totalCost - amount);
        return loan;
    }
}