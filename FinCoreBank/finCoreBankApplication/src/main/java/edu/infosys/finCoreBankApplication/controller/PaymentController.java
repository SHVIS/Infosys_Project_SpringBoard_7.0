package edu.infosys.finCoreBankApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.infosys.finCoreBankApplication.bean.Payment;
import edu.infosys.finCoreBankApplication.dao.PaymentDao;
import edu.infosys.finCoreBankApplication.service.PaymentService;

@RestController
@RequestMapping("/fincore/")
@CrossOrigin(origins="http://localhost:3737",allowCredentials = "true")
public class PaymentController {


	@Autowired
	private PaymentService service;
	@Autowired
	private PaymentDao paymentDao;
	
	
	@PostMapping("/pay")
	public void addPayment(@RequestBody Payment payment) {
		paymentDao.addPayment(payment);
 
	}
 
	@GetMapping("/pay/{id}")
	public Payment getPaymentById(@PathVariable Long id) {
		 return paymentDao.getPaymentById(id);
	}
	
	@GetMapping("/pay")
	public List<Payment> getAllPayments() {
		 return paymentDao.getAllPayments();
	}
 
	@GetMapping("/pay-id")
	public Long generatePaymentId() {
		return service.generatePaymentId();
	}
 
	@GetMapping("/pay-cus/{id}")
	public List<Payment> getPaymentByCustomerId(@PathVariable Long id) {
			return paymentDao.getPaymentByCustomerId(id);
	}
 
	@GetMapping("/pay-loan/{id}")
	public List<Payment> getPaymentByCustomerLoanId(@PathVariable String id) {
		return paymentDao.getPaymentByCustomerLoanId(id);
	}

}
