package vn.com.unit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.entity.Shop;
import vn.com.unit.repository.ShopRepository;
import vn.com.unit.service.ShopService;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

	@Autowired
	ShopRepository shopRepository;
	
	
	
	
	@Override
	public Shop findShopByAccountId(Long account_id) {
		return shopRepository.findShopByAccountId(account_id);
	}
	
	//setInforShop
	@Override
	public void saveShop(Long shop_id, String name,String email, String phone, String address, String detail) {
		try {
			shopRepository.saveShop(shop_id, name, email, phone, address, detail);
		}catch (Exception e) {		
		}	
	}
	
	
	//createShop
	@Override
	public void createShop(Long account_id,String name,String email, String phone, String address, String detail, int status) {
		try {
			shopRepository.createShop(account_id, name, email, phone, address, detail,status);
		}catch (Exception e) {		
		}	
	}
	
	@Override
	public boolean setActivateShop(Long shop_id, int status) {
		try {
			shopRepository.setActivateShop(shop_id,status);
			return true;
		} catch(Exception e) {
			
		}
		return false;
	}
	
	//set disable shop
	
	@Override
	public boolean setDisableShop(Long shop_id, Long status) {
		try {
			shopRepository.setDisableShop(shop_id,status);
			return true;
		} catch(Exception e) {
			
		}
		return false;
	}

	@Override
	public List<Shop> findAllShop(int limit, int offset) {
		List<Shop> shops = new ArrayList<Shop>();
		try {
			shops = shopRepository.findAllShop(limit, offset);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return shops;
	}

	@Override
	public int countAllShop() {
		// TODO Auto-generated method stub
		try {
			return shopRepository.countAllShop();
			} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	@Override
	public List<Shop> findShopByStatus(int limit, int offset, int status) {
		List<Shop> shops = new ArrayList<Shop>();
		try {
			shops = shopRepository.findShopByStatus(limit, offset, status);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return shops;
	}
	
	@Override
	public List<Shop> searchAllShop(){
		
		return shopRepository.searchAllShop();
		
	}

	@Override
	public int countShopByStatus(int status) {
		// TODO Auto-generated method stub
		try {
			return shopRepository.countShopByStatus(status);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
}
