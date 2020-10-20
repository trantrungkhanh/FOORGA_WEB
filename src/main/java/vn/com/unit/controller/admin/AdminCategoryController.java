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

import vn.com.unit.entity.Category;
import vn.com.unit.entity.categoryEntity;
import vn.com.unit.pageable.PageRequest;
import vn.com.unit.service.CategoryService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminCategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/admin/category/list")
	public ModelAndView accountList(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			HttpServletRequest request) {

		int totalitems =  categoryService.countAllCategory();
		int totalpages = (int) Math.ceil((double) totalitems / (double) limit);
		PageRequest pageable = new PageRequest(page, limit, totalitems, totalpages);
		List<Category> categories = categoryService.findCategoryPageable(pageable.getLimit(), pageable.getOffset());
		model.addAttribute("categories", categories);
		model.addAttribute("pageable", pageable);
		return new ModelAndView("admin/category/category-table");
	}
	@GetMapping("/admin/category/add")
	public ModelAndView categoryAdd(Model model,
			HttpServletRequest request) {

		return new ModelAndView("admin/category/category-add");
	}
	
	@GetMapping("/admin/category/edit/{category_id}")
	public ModelAndView categoryEdit(@PathVariable("category_id") long category_id, Model model,
			HttpServletRequest request) {
		Category category = categoryService.findCategoryById(category_id);
		model.addAttribute("category",category);
		return new ModelAndView("admin/category/category-edit");
	}
	
	@PostMapping("/admin/category/add")
	@ResponseBody
	public ResponseEntity<String> createCategory(@RequestBody Category category, Model model) {
		if (categoryService.findCategoryByName(category.getName()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Category already exists\" }");
			}
		if (category.getName() == "") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Name cannot be empty\" }");
		}
		Long category_id = categoryService.createCategory(category);
		if (category_id != null) {
			return ResponseEntity.ok("{ \"id\" : " + category_id + ", \"msg\" : \"Create category successfully\" }");
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("{ \"msg\" : \"You can't create an category right now. Try again later\" }");
		
	}
	@PutMapping("/admin/category/edit")
	@ResponseBody
	public ResponseEntity<String> editCategory(@RequestBody Category category, Model model) {
		
		
		if (categoryService.findCategoryByName(category.getName()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Category already exists\" }");}
		
		if (category.getName() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Name cannot be empty\" }");
		}
		categoryService.updateCategoryById(category);

		return ResponseEntity.ok("{ \"msg\" : \"update category successfully\" }");
	}
	@DeleteMapping("/admin/category/delete/{category_id}")
	public ResponseEntity<Boolean> AdminDisableShop(Model model, @PathVariable("category_id") Long category_id,
			HttpServletRequest request) {
		categoryService.deleteCategoryById(category_id,(long) 1);
		return  ResponseEntity.ok(null);
	}
	
	
	@PostMapping("/admin/category/addnew")
	@ResponseBody
	public ResponseEntity<String> createNewCategory(@RequestBody categoryEntity category, Model model) {
		if (categoryService.findCategoryByName(category.getName()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Category already exists\" }");
			}
		if (category.getName() == "") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"msg\" : \"Name cannot be empty\" }");
		}
		categoryEntity result = categoryService.createNewCategory(category);
		if (result.getId() != null) {
			return ResponseEntity.ok("{ \"id\" : " + result.getId() + ", \"msg\" : \"Create category successfully\" }");
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("{ \"msg\" : \"You can't create an category right now. Try again later\" }");
		
	}
	
}
