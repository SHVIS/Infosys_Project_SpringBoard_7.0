package edu.infosys.finCoreBankApplication.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {	
	@Id
	private Long accountNumber;
	private Long customerId;
	private String accountType;
	private String accountOpenDate;
	private Double balance;
	private String status;
	
	
	public Account(Long accountNumber, Long customerId, String accountType, String accountOpenDate, Double balance,
			String status) {
		super();
		this.accountNumber = accountNumber;
		this.customerId = customerId;
		this.accountType = accountType;
		this.accountOpenDate = accountOpenDate;
		this.balance = balance;
		this.status = status;
	}
	public String getAccountOpenDate() {
		return accountOpenDate;
	}
	public void setAccountOpenDate(String accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
