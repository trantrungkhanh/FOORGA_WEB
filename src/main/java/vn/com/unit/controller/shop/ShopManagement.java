package vn.com.unit.controller.shop;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.entity.Account;
import vn.com.unit.entity.Product;
import vn.com.unit.entity.Shop;
import vn.com.unit.service.AccountService;
import vn.com.unit.service.BillItemService;
import vn.com.unit.service.BillSeparateService;
import vn.com.unit.service.BillService;
import vn.com.unit.service.BrandService;
import vn.com.unit.service.CategoryService;
import vn.com.unit.service.ProductService;
import vn.com.unit.service.RoleService;
import vn.com.unit.service.ShopService;
import vn.com.unit.service.UploadImgService;

@Controller

public class ShopManagement {

	@Autowired
	AccountService accountService;

	@Autowired
	RoleService roleService;

	@Autowired
	ShopService shopService;

	@Autowired
	ProductService productService;

	@Autowired
	BillService billService;

	@Autowired
	BillItemService billItemService;

	@Autowired
	BrandService brandService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	BillSeparateService billSeparateService;

	// create shop
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/shop/create")
	@ResponseBody
	public ResponseEntity<String> createShop(@RequestBody Shop new_shop, Model model) {
		Account account = accountService.findCurrentAccount();
		String phone = new_shop.getPhone();
		String email = new_shop.getEmail();
		String name = new_shop.getName();
		String address = new_shop.getAddress();
		String detail = new_shop.getDetail();
		int status = 0;
		shopService.createShop(account.getId(), name, email, phone, address, detail, status);
		;
		return ResponseEntity.status(HttpStatus.OK)
				.body("{ \"msg\" : \"Your Shop Create Success! Please waitting for admin check!\" }");

	}

	// activate shop
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@PutMapping("/shop/activate")
	@ResponseBody
	public ResponseEntity<String> activateShop(Model model) {
		Account account = accountService.findCurrentAccount();
		int status = 0;
		shopService.setActivateShop(account.getId(), status);
		return ResponseEntity.status(HttpStatus.OK)
				.body("{ \"msg\" : \"Activate Request have been send! Please waitting for admin check!\" }");

	}

	// edit SHOP
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@PutMapping("/shop/edit")
	@ResponseBody
	public ResponseEntity<Shop> editShop(@RequestBody Shop shop, Model model) {
		Long shop_id = shop.getId();
		String phone = shop.getPhone();
		String email = shop.getEmail();
		String name = shop.getName();
		String address = shop.getAddress();
		String detail = shop.getDetail();
		shopService.saveShop(shop_id, name, email, phone, address, detail);
		;
		return ResponseEntity.ok(shop);

	}

	// deleteShop
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@DeleteMapping("/shop/delete")
	public ResponseEntity<Void> deleteShop() {
		Account account = accountService.findCurrentAccount();
		Long status = (long) 2;
		if (shopService.setDisableShop(account.getId(), status)) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	// add product
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@PostMapping("/add-product")
	public String addProduct(@ModelAttribute("new_product") Product new_product,
			@RequestParam("file") MultipartFile multipartFile, Model model) {

//		String url = UploadImgService.uploadCloudinary(multipartFile);
//		
//		new_product.setImg(url);
//		
//		Account account = accountService.findCurrentAccount();		
//		productService.createNewProduct(new_product.getName(), new_product.getPrice(), new_product.getQuantity(), new_product.getCategory(), new_product.getBrand(), new_product.getDetail(), new_product.getImg(), account.getId());

		// ---------

//		File file = UploadImgService.getFileFromMultipartFile(multipartFile);
//		String url = UploadImgService.uploadCloudinary(file);
//
//		new_product.setImg(url);
//
//		Account account = accountService.findCurrentAccount();
//		productService.createNewProduct(new_product.getName(), new_product.getPrice(), new_product.getQuantity(),
//				new_product.getCategory(), new_product.getBrand(), new_product.getDetail(), new_product.getImg(),
//				account.getId());

		// ---------

//		final MultipartFile MULTIPART_FILE = multipartFile;
//		
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//	    executorService.submit(() -> {
//	    	String url = UploadImgService.uploadCloudinary(MULTIPART_FILE);
//			
//			new_product.setImg(url);
//			
//			Account account = accountService.findCurrentAccount();		
//			productService.createNewProduct(new_product.getName(), new_product.getPrice(), new_product.getQuantity(), new_product.getCategory(), new_product.getBrand(), new_product.getDetail(), new_product.getImg(), account.getId());
//	    });
//		
		// ---------

		File file = UploadImgService.getFileFromMultipartFile(multipartFile);
		Account account = accountService.findCurrentAccount();
		
		Thread thread = new Thread(new Runnable() {
		    @Override
		    public void run() {
				String url = UploadImgService.uploadCloudinary(file);

				new_product.setImg(url);

				productService.createNewProduct(new_product.getName(), new_product.getPrice(), new_product.getQuantity(),
						new_product.getCategory(), new_product.getBrand(), new_product.getDetail(), new_product.getImg(),
						account.getId());
				file.delete();
		    }
		});
		thread.start();
		// --------

		return "redirect:/shop/myproduct?mode=view";
	}

	// edit Product
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@PutMapping("/product/{product_id}")
	@ResponseBody
	public ResponseEntity<Product> editShop(@RequestBody Product product, @PathVariable("product_id") Long product_id,
			Model model) {
		String name = product.getName();
		int category = product.getCategory();
		int brand = product.getBrand();
		int price = product.getPrice();
		int quantity = product.getQuantity();
		String detail = product.getDetail();
		productService.saveProduct(product_id, name, price, detail, category, brand, quantity);
		return ResponseEntity.ok(product);

	}

	// deleteProduct
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@DeleteMapping("/product/{product_id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("product_id") Long product_id) {
		int status = 1;
		if (productService.setDisableProductByProductId(product_id, status)) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	//confirm bill
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@PutMapping("/bill/confirm")
	@ResponseBody
	public ResponseEntity<String> confirm(Model model, @RequestBody Map<String, String> json) {
		int status = 1;
		
		billSeparateService.saveBillSeparateStatus((Long.valueOf(json.get("bill_id"))), status);
		
		return ResponseEntity.ok(
				"{\"msg\" : \"Confirm Bill succes! Please check again!\" }");
	}
	
	//confirm payment bill
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@PutMapping("/bill/payment")
	@ResponseBody
	public ResponseEntity<String> payment(Model model, @RequestBody Map<String, String> json) {
		int status = 1;
		
		billService.saveBillPaymentStatus((Long.valueOf(json.get("bill_id"))), status);
		
		return ResponseEntity.ok(
				"{\"msg\" : \"Confirm Bill Payment succes! Please check again!\" }");
	}
	
	
	//deny bill
	@PreAuthorize("hasRole('ROLE_VENDOR')")
	@PutMapping("/bill/deny")
	@ResponseBody
	public ResponseEntity<String> deny(Model model, @RequestBody Map<String, String> json) {
		int status = 2;
		
		billSeparateService.saveBillSeparateStatus((Long.valueOf(json.get("bill_id"))), status);
		
		return ResponseEntity.ok(
				"{\"msg\" : \"Deny bill succes! Please check again!\" }");
	}
}
