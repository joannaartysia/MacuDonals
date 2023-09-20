package model;

public class Food {

	private Integer id;
	private String name;
	private String type;
	private Integer price;
	private Integer stock;
	
	public Food(Integer id, String name, String type, Integer price, Integer stock) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.stock = stock;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	};
	
	
}
