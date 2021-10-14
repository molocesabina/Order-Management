package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import dao.ProductDAO;
import model.Client;
import model.Product;

/**
* ProductBLL este clasa care incapsuleaza logica pentru produse
* @author Moloce Sabina
*/
public class ProductBLL {
	private ProductDAO productDAO;

	/**
	 * Constructor
	 */
	public ProductBLL() {
		productDAO = new ProductDAO();
	}
	
	
	/**
	 * 
	 * @param id produsului din baza de date
	 * @return produsul cu acel id
	 */
	public Product findProductById(int id) {
		Product st = productDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The product with id =" + id + " was not found!");
		}
		return st;
	}
	
	/**
	 * 
	 * @param product pe care vrem sa-l adaugam
	 * @return produsul, dar cu id-ul din baza de date
	 */
	public Product insertProduct(Product product) {
		return productDAO.insert(product);
	}
	
	/**
	 * 
	 * @param product cu care facem update
	 */
	public void updateProduct(Product product) {
		productDAO.update(product);
	}
	
	/**
	 * 
	 * @param id stergem din baza de date produsul cu acest id
	 */
	public void deleteProduct(int id) {
		productDAO.delete(id);
	}
	
	/**
	 * 
	 * @return lista cu produsele din baza de date
	 */
	public List<Product> findAllProducts(){
		if(productDAO.findAll()!=null)
			return productDAO.findAll();
		else {
			List<Product> l = new ArrayList<Product>();
			l.add(new Product());
			return l;
		}
	}

}

