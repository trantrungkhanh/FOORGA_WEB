package vn.com.unit.controller.admin;

import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.entity.Role;
import vn.com.unit.entity.Shop;
import vn.com.unit.pageable.PageRequest;
import vn.com.unit.service.AccountService;
import vn.com.unit.service.RoleService;
import vn.com.unit.service.ShopService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminShopManagementController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RoleService roleService;
	@GetMapping("/admin/shop/list")
	public ModelAndView ShopList(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			HttpServletRequest request) {

		int totalitems = shopService.countShopByStatus(1);
		int totalpages = (int) Math.ceil((double) totalitems / (double) limit);

		PageRequest pageable = new PageRequest(page, limit, totalitems, totalpages);

		List<Shop> shops = shopService.findShopByStatus(pageable.getLimit(), pageable.getOffset(), 1);
		model.addAttribute("shops", shops);
		model.addAttribute("pageable", pageable);

		return new ModelAndView("admin/shop/shop-table");
	}

	@GetMapping("/admin/shop/accept-list")
	public ModelAndView ShopAcceptList(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			HttpServletRequest request) {
		int totalitems = shopService.countShopByStatus(0);
		int totalpages = (int) Math.ceil((double) totalitems / (double) limit);
		PageRequest pageable = new PageRequest(page, limit, totalitems, totalpages);
		List<Shop> shops = shopService.findShopByStatus(pageable.getLimit(), pageable.getOffset(), 0);
		model.addAttribute("shops", shops);
		model.addAttribute("pageable", pageable);

		return new ModelAndView("admin/shop/shop-accept-table");
	}

	@GetMapping("/admin/shop/deactive-list")
	public ModelAndView ShopDeactiveList(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			HttpServletRequest request) {
		int totalitems3 = shopService.countShopByStatus(3);
		int totalitems2 = shopService.countShopByStatus(2);
		int totalitems=totalitems2+totalitems3;
		int totalpages = (int) Math.ceil((double) totalitems / (double) limit);
		PageRequest pageable = new PageRequest(page, limit, totalitems, totalpages);
		List<Shop> deactiveByAdmin = shopService.findShopByStatus(pageable.getLimit(), pageable.getOffset(), 3);
		List<Shop> deactiveByVendor = shopService.findShopByStatus(pageable.getLimit(), pageable.getOffset(), 2);
		deactiveByAdmin.addAll(deactiveByVendor);
		model.addAttribute("shops", deactiveByAdmin);
		model.addAttribute("pageable", pageable);
		return new ModelAndView("admin/shop/shop-deactive-table");
	}
	@PutMapping("/admin/shop/accept/{shop_id}")
	public ResponseEntity<Boolean> ShopAccept(Model model, @PathVariable("shop_id") Long shop_id,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			HttpServletRequest request) {
		if (shopService.setDisableShop(shop_id, (long) 1)) {
			List<Role> roles = roleService.findRoleByAccountId(shop_id);
			int index = IntStream.range(0,roles.size()  )
					.filter(i -> "ROLE_VENDOR".equals(roles.get(i).getName()))
					.findFirst()
					.orElse(-1);
			
			if(index < 0) {
				
				accountService.setRoleByAccountId(shop_id, roleService.findRoleIdByName("ROLE_VENDOR"));
			}
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}


	@DeleteMapping("/admin/shop/delete/{shop_id}")
	public ResponseEntity<Boolean> AdminDisableShop(Model model, @PathVariable("shop_id") Long shop_id,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			HttpServletRequest request) {
		if (shopService.setDisableShop(shop_id, (long) 2)) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

}
