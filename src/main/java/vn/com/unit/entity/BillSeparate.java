package vn.com.unit.entity;

import java.util.Date;

public class BillSeparate {

	private Long id;

	private Long bill;
	private Long shop;
	private Long status;
	private Long account;
	private Long refund;
	
	private Date createAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBill() {
		return bill;
	}

	public void setBill(Long bill) {
		this.bill = bill;
	}

	public Long getShop() {
		return shop;
	}

	public void setShop(Long shop) {
		this.shop = shop;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getRefund() {
		return refund;
	}

	public void setRefund(Long refund) {
		this.refund = refund;
	}
	
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}
	
}
