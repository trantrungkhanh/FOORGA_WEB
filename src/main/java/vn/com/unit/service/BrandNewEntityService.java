package vn.com.unit.service;

import vn.com.unit.entity.BrandNewEntity;

public interface BrandNewEntityService {
	
	public BrandNewEntity add(BrandNewEntity brandNewEntity);
	
	public void edit(BrandNewEntity brandNewEntity);

	public BrandNewEntity findBrandNewEntityByBrandId(Long brandId);


}
