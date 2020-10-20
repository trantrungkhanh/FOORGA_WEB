package vn.com.unit.controller.admin;

import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.entity.Brand;
import vn.com.unit.entity.BrandNewEntity;
import vn.com.unit.pageable.PageRequest;
import vn.com.unit.service.BrandNewEntityService;
import vn.com.unit.service.BrandService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminBrandManagementController {

	@Autowired
	private BrandService brandService;
	
	@Autowired
	private BrandNewEntityService brandNewEntityService;
	

	@GetMapping("/admin/brand/list")
	public ModelAndView BrandList(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			HttpServletRequest request) {

		int totalitems = brandService.countAllBrand();
		int totalpages = (int) Math.ceil((double) totalitems / (double) limit);

		PageRequest pageable = new PageRequest(page, limit, totalitems, totalpages);

		List<Brand> brands = brandService.findBrandPageable(pageable.getLimit(), pageable.getOffset());
		model.addAttribute("brands", brands);
		model.addAttribute("pageable", pageable);

		return new ModelAndView("admin/brand/brand-table");
	}

	@GetMapping("/admin/brand/add")
	public ModelAndView categoryAdd(Model model, HttpServletRequest request) {

		return new ModelAndView("admin/brand/brand-add");
	}

	@GetMapping("/admin/brand/edit/{brand_id}")
	public ModelAndView categoryEdit(@PathVariable("brand_id") long brand_id, Model model, HttpServletRequest request) {
		Brand brand = brandService.findBrandById(brand_id);
		model.addAttribute("brand", brand);
		return new ModelAndView("admin/brand/brand-edit");
	}

	@PostMapping("/admin/brand/add")
	@ResponseBody
	public ResponseEntity<String> createCategory(@RequestBody Brand brand, Model model) {
		if (brandService.findBrandByName(brand.getName()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"brand already exists\" }");
		}

		if (brand.getName() == "") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Name cannot be empty\" }");
		}
		Long brand_id = brandService.createCategory(brand);
		if (brand_id != null) {
			return ResponseEntity.ok("{ \"id\" : " + brand_id + ", \"msg\" : \"Create brand successfully\" }");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("{ \"msg\" : \"You can't create an brand right now. Try again later\" }");

	}
	
	
	@PostMapping("/admin/brand/add2")
	@ResponseBody
	public ResponseEntity<String> createCategory2(@RequestBody Brand brand, Model model) {
		BrandNewEntity brandNewEntity = new BrandNewEntity();
		brandNewEntity.setName(brand.getName());
		brandNewEntity.setId(brand.getId());
//		billNewEntity.setCreateAt((new Date()));
		
		BrandNewEntity b = brandNewEntityService.add(brandNewEntity);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body("{ \"msg\" : \"You can't create an brand right now. Try again later\" }");

	}

	@PutMapping("/admin/brand/edit")
	@ResponseBody
	public ResponseEntity<String> editCategory(@RequestBody Brand brand, Model model) {

		if (brandService.findBrandByName(brand.getName()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Brand already exists\" }");
		}

		if (brand.getName() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Name cannot be empty\" }");
		}
		brandService.updateBrandById(brand);

		return ResponseEntity.ok("{  \"msg\" : \"Update brand successfully\" }");
	}
	
	
	@PutMapping("/admin/brand/edit2")
	@ResponseBody
	public ResponseEntity<String> editCategory2(@RequestBody BrandNewEntity brand, Model model) {

		if (brandService.findBrandByName(brand.getName()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Brand already exists\" }");
		}

		if (brand.getName() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Name cannot be empty\" }");
		}
		
		brandNewEntityService.edit(brand);
		return ResponseEntity.ok("{  \"msg\" : \"Update brand successfully\" }");
	}

	@DeleteMapping("/admin/brand/delete/{brand_id}")
	public ResponseEntity<Boolean> AdminDisableShop(Model model, @PathVariable("brand_id") Long brand_id,
			HttpServletRequest request) {
		brandService.deleteBrandById(brand_id, (long) 1);
		return ResponseEntity.ok(null);
	}

}
