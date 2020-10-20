package vn.com.unit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.entity.Brand;
import vn.com.unit.repository.BrandRepository;
import vn.com.unit.repository.CategoryRepository;
import vn.com.unit.repository.ProductRepository;
import vn.com.unit.repository.ShopRepository;
import vn.com.unit.service.BrandService;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	ShopRepository shopRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	BrandRepository brandRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public Brand findBrandByProductId(Long product_id) {
		
		return brandRepository.findBrandByProductId(product_id);
		
	};
	
	@Override
	public List<Brand> findAllBrand(){
		
		return brandRepository.findAllBrand();
	}
	
	@Override
	public List<Brand> findAllBrandByShopId(Long shop_id){
		
		return brandRepository.findAllBrandByShopId(shop_id);
	}

	@Override
	public List<Brand> findBrandPageable(int limit, int offset) {
		// TODO Auto-generated method stub
		try {
			return brandRepository.findBrandPageable(limit, offset);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public int countAllBrand() {
		// TODO Auto-generated method stub
		try {
			return brandRepository.countAllBrand();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  0;
	}

	@Override
	public Long createCategory(Brand brand) {
		// TODO Auto-generated method stub
		try {
			return brandRepository.createNewBrand(brand.getName());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public Brand findBrandByName(String name) {
		// TODO Auto-generated method stub
		try {
			return brandRepository.findBrandByName(name);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public Brand findBrandById(Long id) {
		// TODO Auto-generated method stub
		try {
			return brandRepository.findBrandById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public void updateBrandById(Brand brand) {
		// TODO Auto-generated method stub
		try {
			brandRepository.updateBrandById(brand.getName(),brand.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void deleteBrandById(Long id, Long disable) {
		// TODO Auto-generated method stub
		
		try {
			brandRepository.deleteBrandById(id, disable);
		} catch (Exception e) {
			// TODO: handle exception
		}
	};
}
