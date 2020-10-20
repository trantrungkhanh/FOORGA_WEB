package vn.com.unit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.entity.Account;
import vn.com.unit.entity.Role;
import vn.com.unit.repository.AccountRepository;
import vn.com.unit.repository.RoleRepository;
import vn.com.unit.service.AccountService;
import vn.com.unit.service.RoleService;
import vn.com.unit.utils.CommonUtils;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	AccountService accountService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Account findByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	public boolean checkLogin(Account account, String rawPassword) {
		try {

			String encodedPassword = account.getPassword();
			if (encodedPassword.equals("")) {
				String defaultRawPassword = CommonUtils.DEFAULT_PASSWORD;
				accountRepository.updateAccountPassword(account.getId(),
						CommonUtils.encodePassword(defaultRawPassword));
				return rawPassword.equals(defaultRawPassword);
			}

			return passwordEncoder.matches(rawPassword, encodedPassword);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}
	
	//check password
	@Override
	public boolean checkPass(Account account, String oldPassword) {
		try {

			String encodedPassword = account.getPassword();
			return passwordEncoder.matches(oldPassword, encodedPassword);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}
	
	
	

//tìm tất cả user kèm role
	@Override
	public List<Account> findAllAccount(int limit,int offset, String keyword, Long role_id) {
		List<Account> accounts = new ArrayList<Account>();
		try {
			accounts = accountRepository.findAllAccountActive(limit,offset,keyword,role_id);

			for (Account account : accounts) {
				List<Role> roles = roleService.findRoleByAccountId(account.getId());
				account.setRoles(roles);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return accounts;
	}
	
	// create new account
	@Override
	public Account createNewAccount(Account account,String role_name) {
		try {
			String username = account.getUsername();
			String password = CommonUtils.encodePassword(account.getPassword());
			Long account_new_id = accountRepository.createNewAccount(username, password);
			if (account_new_id != null) {

				accountService.setRoleByAccountId(account_new_id, roleService.findRoleIdByName(role_name) );

				return accountService.findByUsername(account.getUsername());
			}
			
		} catch (Exception e) {

		}
		return null;
	}
	// getIDsetRole

	@Override
	public void setRoleByAccountId(Long account_id, Long role_id) {
		try {
			accountRepository.setRoleByAccountId(account_id, role_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	// getCurrentAccount
	@Override
	public Account findCurrentAccount() {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Account account = findByUsername(currentUsername);
		List<Role> roles = roleService.findRoleByAccount(account);
		account.setRoles(roles);
		return account;
	}

	// setPassword
	@Override
	public void setAccountPassword(Long account_id, String password) {
		try {
			password = CommonUtils.encodePassword(password);

			accountRepository.setAccountPassword(account_id, password);
		} catch (Exception e) {

		}

	}
	
	
	//setInfor
	@Override
	public void saveAccount(Long account_id, String name,String email, String phone) {
		try {
			accountRepository.saveAccount(account_id, name, email, phone);
		}catch (Exception e) {
			
		}

		
	}

	// getId
	@Override
	public Account findAccountById(Long id) {
		try {
			return accountRepository.findAccountById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public int countAccountActive(String keyword,Long role_id) {
		try {
			return accountRepository.countAccountActive(keyword,role_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setDisableAccount(Long account_id, Long disable) {
		try {
			accountRepository.setDisableAccount(account_id, disable);
			return true;
		} catch(Exception e) {
			
		}
		return false;
	}

	@Override
	public void saveAccountV2(Account account) {
		// TODO Auto-generated method stub
		try {
			accountRepository.save(account);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
