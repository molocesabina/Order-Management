package model;

/**
* Order este clasa care reprezinta tabelul order din baza de date
* @author Moloce Sabina
*/
public class Order {
	private int id;
	private int idClient;
	private int idProduct;
	private int quantity;
	
	/**
	 * Constructor fara param
	 */
	public Order() {
		
	}
	
	/**
	 * Constructor
	 * @param idClient id-ul clientului
	 * @param idProduct id-ul produsului
	 * @param quantity cate bucati din produs
	 */
	public Order(int idClient, int idProduct, int quantity) {
		this.idClient=idClient;
		this.idProduct=idProduct;
		this.quantity=quantity;
	}
	
	/**
	 * Constructor
	 * @id id-ul din baza de date
	 * @param idClient id-ul clientului
	 * @param idProduct id-ul produsului
	 * @param quantity cate bucati din produs
	 */
	public Order(int id, int idClient, int idProduct, int quantity) {
		this.id=id;
		this.idClient=idClient;
		this.idProduct=idProduct;
		this.quantity=quantity;
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
	 * @return idClient
	 */
	public int getIdClient() {
		return idClient;
	}
	
	/**
	 * 
	 * @param idClient noul idClient
	 */
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	
	/**
	 * 
	 * @return idProduct
	 */
	public int getIdProduct() {
		return idProduct;
	}
	
	/**
	 * 
	 * @param idProduct noul idProduct
	 */
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	
	/**
	 * 
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * 
	 * @param quantity noul quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
	

}
