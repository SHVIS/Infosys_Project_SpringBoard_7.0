package edu.infosys.finCoreBankApplication.dao;

import java.util.List;

import edu.infosys.finCoreBankApplication.bean.Loan;


public interface LoanDao {

		public void addLoan(Loan loan);
		public Loan getLoanById(String loanId);
		public List<Loan> getLoans();
		public void deleteLoanById(String loanId);
		public Long getMaxLoanId();
		public List<Loan> getLoansByStatus(String status);
		public List<String> getLoanIdList();
/*
public List<Loan> getLoanChart();
*/		
}