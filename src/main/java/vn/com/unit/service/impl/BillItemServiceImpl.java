package vn.com.unit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.entity.BillItem;
import vn.com.unit.entity.Product;
import vn.com.unit.repository.BillItemRepository;
import vn.com.unit.repository.BillRepository;
import vn.com.unit.repository.ProductRepository;
import vn.com.unit.repository.ShopRepository;
import vn.com.unit.service.BillItemService;
import vn.com.unit.service.ProductService;

@Service
@Transactional
public class BillItemServiceImpl implements BillItemService {

	@Autowired
	ShopRepository shopRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	BillRepository billRepository;
	
	@Autowired
	BillItemRepository billItemRepository;
	
	
	@Override
	public List<BillItem> findAllBillItemByBillSeparateId(Long bill_separate_id) {

		List<BillItem> billitems = new ArrayList<BillItem>();
		int total = 0;
		try {
			billitems = billItemRepository.findAllBillItemByBillSeparateId(bill_separate_id);
			for (BillItem billitem : billitems) {
				Long product_id = (long) billitem.getProduct();
				Product product_name = productService.findProductByProductId(product_id);
				billitem.setProduct_name(product_name);
				total = total + (product_name.getPrice() * billitem.getQuantity());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return billitems;
	}
	
	@Override
	public int totalPriceOfBillByBillSeparateId(Long bill_separate_id) {

		List<BillItem> billitems = new ArrayList<BillItem>();
		int total = 0;
		try {
			billitems = billItemRepository.findAllBillItemByBillSeparateId(bill_separate_id);
			for (BillItem billitem : billitems) {
				Long product_id = (long) billitem.getProduct();
				Product product_name = productService.findProductByProductId(product_id);
				billitem.setProduct_name(product_name);
				total = total + (product_name.getPrice() * billitem.getQuantity());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	
	@Override
	public List<BillItem> findAllBillItem() {
		
		return billItemRepository.findAllBillItem();
	}

	@Override
	public List<BillItem> findAllBillItemByBillId(Long id) {
		return billItemRepository.findAllBillItemByBillId(id);
	}
	

}
