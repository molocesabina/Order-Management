package bll;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Order;
import model.Product;

/**
* OrderBLL este clasa care incapsuleaza logica pentru comenzi
* @author Moloce Sabina
*/
public class OrderBLL {
	private OrderDAO orderDAO;
	private ClientDAO clientDAO = new ClientDAO();
	private ProductDAO productDAO = new ProductDAO();

	/**
	* Constructor
	*/
	public OrderBLL() {
		orderDAO = new OrderDAO();
		clientDAO = new ClientDAO();
		productDAO = new ProductDAO();
	}
	
	/**
	 * 
	 * @param id ce id are order in baza de date
	 * @return order
	 */
	public Order findOrderById(int id) {
		Order st = orderDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The order with id =" + id + " was not found!");
		}
		return st;
	}
	
	/**
	 * 
	 * @param order pe care vrem sa-l adaugam
	 * @return order, dar cu id din baza de date
	 * @throws Exception cand stocul nu este suficient
	 */
	public Order insertOrder(Order order) throws Exception {
		Client client = clientDAO.findById(order.getIdClient());
		Product product = productDAO.findById(order.getIdProduct());
		Order newOrder = null;
		if(product.getStock() < order.getQuantity()) {
			throw new Exception("Stoc insuficient!");
		} else {
			product.setStock(product.getStock()-order.getQuantity());
			productDAO.update(product);
			newOrder = orderDAO.insert(order);
			createBill(client,product, newOrder);
		}
		return newOrder;
	}
	
	/**
	 * @return lista cu idurile clientilor din baza de date
	 */
	public List<Integer> getPossibleClientsIds(){
		List<Integer> possibleClientsIds = new ArrayList<Integer>();
		for(Client c: clientDAO.findAll()) {
			possibleClientsIds.add(c.getId());
		}
		return possibleClientsIds;
		
	}
	
	/**
	 * 
	 * @return lista cu idurile produselor din baza de date
	 */
	public List<Integer> getPossibleProductsIds(){
		List<Integer> possibleProductsIds = new ArrayList<Integer>();
		for(Product p: productDAO.findAll()) {
			possibleProductsIds.add(p.getId());
		}
		return possibleProductsIds;
		
	}
	
	/**
	 * Creeaza factura pentru o anumita comanda
	 * @param client  clientul pentru care a dat comanada
	 * @param product produsul pe care l-a comandat clientul
	 * @param order comanada plasata
	 */
	private void createBill(Client client, Product product,Order order) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("Order.txt"));
			String s = "Order"+order.getId()+'\n';
			s+= "Client: "+client.getName()+'\n';
			s+="Product: "+product.getName()+'\n';
			s+="Quantity: "+order.getQuantity()+'\n';
			s+="Price: "+(product.getPrice()*order.getQuantity())+'\n';
		    out.write(s);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * 
	 * @param order la care facem update
	 */
	public void updateOrder(Order order) {
		orderDAO.update(order);
	}
	
	/**
	 * 
	 * @param id order pe care vrem sa-l stergem
	 */
	public void deleteOrder(int id) {
		orderDAO.delete(id);
	}
	
	/**
	 * 
	 * @return lista cu toate order din baza de date
	 */
	public List<Order> findAllOrders(){
		if(orderDAO.findAll()!=null)
			return orderDAO.findAll();
		else {
			List<Order> l = new ArrayList<Order>();
			l.add(new Order());
			return l;
		}
	}
}
