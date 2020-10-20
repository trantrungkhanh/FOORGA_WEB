package vn.com.unit.entity;

public class BillItem {

	private Long id;
	
	private int product;
	
	private String name;
	
	private Long price;
	
	private int quantity;
	
	private Product product_name;
	
	public BillItem() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Long getTotal() {
		return price * quantity;
	}

	public Product getProduct_name() {
		return product_name;
	}

	public void setProduct_name(Product product_name) {
		this.product_name = product_name;
	}
	
}