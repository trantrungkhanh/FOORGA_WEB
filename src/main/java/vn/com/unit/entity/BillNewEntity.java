package vn.com.unit.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;


@Table(name = "p2p_bill")
public class BillNewEntity {

	@Id
	@PrimaryKey(generationType = GenerationType.IDENTITY) // Primary key // Auto increment
	@Column(name = "id")
	private Long id;
	
	@Column(name = "account")
	private Long account;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "payment")
	private Long payment;
	
	@Column(name = "create_at")
	private Date createAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPayment() {
		return payment;
	}

	public void setPayment(Long payment) {
		this.payment = payment;
	}

	public Date getCreateAt() {
		return createAt;
	}

//	public void setCreateAt(Date createAt) {
//		this.createAt = createAt;
//	}
	
}
