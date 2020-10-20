package vn.com.unit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.entity.CartItem;
import vn.com.unit.repository.CartRepository;
import vn.com.unit.service.AccountService;
import vn.com.unit.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	AccountService accountService;

	@Autowired
	CartRepository cartRepository;

	@Override
	public List<CartItem> findAllCartItemByCurrentAccount() {

		Long curent_account_id = accountService.findCurrentAccount().getId();

		return this.findAllCartItemByAccountId(curent_account_id);
	}
	
	@Override
	public int countAllCartItemByCurrentAccount(Long account_id) {

		int total = cartRepository.countAllCartItemByAccountId(account_id); 

		return total;
	}

	@Override
	public List<CartItem> findAllCartItemByAccountId(Long account_id) {

		return cartRepository.findAllCartItemByAccountId(account_id);
	}

	@Override
	public Long calculateCartTotalByCurrentAccount() {
		Long account_id = accountService.findCurrentAccount().getId();
		return cartRepository.calculateCartTotalByAccountId(account_id);
	}

	@Override
	public void deleteCartItemCurrentAccount(Long product_id, Long account_id) {

		cartRepository.deleteCartItemCurrentAccount(account_id, product_id);
	}
	
	@Override
	public void addCartItemCurrentAccount(Long product_id, int quantity) {
		Long curent_account_id = accountService.findCurrentAccount().getId();

//		select quantity
//		from p2p_cart
//		where account = 1 and product = 2

		// Nead optimize here
		Integer quantity_in_cart = cartRepository.findProductQuantityInCart(curent_account_id, product_id);

		if (quantity_in_cart == null) {
			cartRepository.addCartItemCurrentAccount(curent_account_id, product_id, quantity);
		} else {
			cartRepository.addCartItemCurrentAccount(curent_account_id, product_id, quantity_in_cart + quantity);
		}

	}

}
