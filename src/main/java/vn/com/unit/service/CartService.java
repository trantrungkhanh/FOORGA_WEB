package vn.com.unit.service;

import java.util.List;

import vn.com.unit.entity.CartItem;

public interface CartService {

	public List<CartItem> findAllCartItemByCurrentAccount();
	
	public int countAllCartItemByCurrentAccount(Long account_id);
	
	public List<CartItem> findAllCartItemByAccountId(Long account_id);

	public Long calculateCartTotalByCurrentAccount();

	public void addCartItemCurrentAccount(Long product_id, int i);
	
	public void deleteCartItemCurrentAccount(Long product_id, Long account_id);
	
//	public void deleteCartItemCurrentAccountByProductId(Long product_id);
}
