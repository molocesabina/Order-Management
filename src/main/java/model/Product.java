package model;

/**
* Product este clasa care reprezinta tabelul product din baza de date
* @author Moloce Sabina
*/
public class Product {
	private int id;
	private String name;
	private int stock;
	private int price;
	
	/**
	 * Constructor fara param
	 */
	public Product() {
		
	}
	
	/**
	 * Constructor
	 * @param name nume
	 * @param stock stoc
	 * @param price pret
	 */
	public Product(String name, int stock, int price) {
		this.name=name;
		this.stock=stock;
		this.price=price;
	}
	
	/**
	 * Constructor
	 * @param id id
	 * @param name nume
	 * @param stock stoc
	 * @param price pret
	 */
	public Product(int id, String name, int stock, int price) {
		this.id=id;
		this.name=name;
		this.stock=stock;
		this.price=price;
	}
	
	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id noul id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name noul nume
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return stock
	 */
	public int getStock() {
		return stock;
	}
	
	/**
	 * 
	 * @param stock noul stoc
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	/**
	 * 
	 * @return price
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * 
	 * @param price noul price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	

}
