package edu.infosys.finCoreBankApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.infosys.finCoreBankApplication.dao.PaymentDao;

@Service
public class PaymentService {
	@Autowired
	private PaymentDao paymentDao;
	public Long generatePaymentId() {
	Long value=paymentDao.getMaxPaymentId();
	if (value==null)
	value=1001L;
	else
	value=value+1;
	return value;
	}
}
