package vn.com.unit.controller.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.entity.BillNewEntity;
import vn.com.unit.service.BillNewEntityService;

@Controller
public class TestBillNewEntity {

	@Autowired
	BillNewEntityService billNewEntityService;

	@GetMapping("/test/bill")
	public ModelAndView bill(Model model) {

		BillNewEntity billNewEntity = new BillNewEntity();
		billNewEntity.setAccount(1L);
		billNewEntity.setAddress("An Giang");
		billNewEntity.setPayment(2000L);
//		billNewEntity.setCreateAt((new Date()));

		BillNewEntity b = billNewEntityService.save(billNewEntity);

		b.setAccount(2L);
		b.setAddress("An Giang Change");
		b.setPayment(3000L);

		BillNewEntity c = billNewEntityService.save(b);

		return new ModelAndView("home");
	}

}
