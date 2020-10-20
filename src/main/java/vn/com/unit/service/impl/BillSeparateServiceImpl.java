package vn.com.unit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.entity.BillSeparate;
import vn.com.unit.entity.BillSeparateShop;
import vn.com.unit.entity.HistoryBillSeparate;
import vn.com.unit.entity.billItemSeparate;
import vn.com.unit.repository.BillSeparateRepository;
import vn.com.unit.service.BillSeparateService;

@Service
@Transactional
public class BillSeparateServiceImpl implements BillSeparateService {

	@Autowired
	BillSeparateRepository billSeparateRepository;
	
	@Override
	public List<BillSeparate> findBillSeparatePaymentSuccessAndStatusWaitingByShopId(Long shop_id) {
		// TODO Auto-generated method stub
		
		return billSeparateRepository.findBillSeparatePaymentSuccessAndStatusWaitingByShopId(shop_id);
	}
	
	@Override
	public List<BillSeparateShop> findBillSeparateByPaymentAndStatusAndShopId(Long payment,Long status,Long shop_id,int limit,int offset) {
		// TODO Auto-generated method stub
		
		return billSeparateRepository.findBillSeparateByPaymentAndStatusAndShopId(payment, status, shop_id,limit, offset);
	}
	
	@Override
	public int countBillSeparateByPaymentAndStatusAndShopId(Long payment,Long status,Long shop_id) {
		// TODO Auto-generated method stub
		int total = billSeparateRepository.countBillSeparateByPaymentAndStatusAndShopId(payment, status, shop_id) ;
		
		return total;
	}
	
	
	
	@Override
	public void saveBillSeparateStatus(Long bill_separate_id, int status) {
		billSeparateRepository.saveBillSeparateStatus(bill_separate_id, status);
		
	}

	@Override
	public List<HistoryBillSeparate> findAllBillSeparateByAccountId(Long account_id, Long status, Long payment,int limit,int offset) {
		// TODO Auto-generated method stub
		try {
			return billSeparateRepository.findAllBillSeparateByAccountId(account_id, status, payment,limit,offset);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<billItemSeparate> findBillItemByBillSeparateId(Long bill_separate_id,Long account) {
		// TODO Auto-generated method stub
		try {
			return billSeparateRepository.findBillItemByBillSeparateId(bill_separate_id,account);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public HistoryBillSeparate findBillSeparateById(Long id) {
		// TODO Auto-generated method stub
		try {
			return billSeparateRepository.findBillSeparateItemById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public int countBillSeparateByAccountId(Long account_id, Long status, Long payment) {
		// TODO Auto-generated method stub
		try {
			return billSeparateRepository.countAllBillSeparateByAccountId(account_id, status, payment);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	};

}
