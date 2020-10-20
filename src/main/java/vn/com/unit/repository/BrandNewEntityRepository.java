package vn.com.unit.repository;

import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.entity.BrandNewEntity;

public interface BrandNewEntityRepository extends MirageRepository<BrandNewEntity, Long> {

	public BrandNewEntity findBrandNewEntityByBrandId(@Param("brandId") Long brandId);
	
}
