package bll;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;


import dao.ClientDAO;
import model.Client;


/**
* ClientBLL este clasa care incapsuleaza logica pentru clienti
* @author Moloce Sabina
*/
public class ClientBLL {

	private ClientDAO clientDAO;
    
    /**
	* Constructor
	*/
	public ClientBLL() {
		clientDAO = new ClientDAO();
	}


	/**
	 * 
	 * @param id ce id are in baza de date clientul 
	 * @return clientul cu id cerut
	 */
	public Client findClientById(int id) {
		Client st = clientDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The client with id =" + id + " was not found!");
		}
		return st;
	}
	
	/**
	 * @param client trebuie inserta in baza de date
	 * @return clientul cu noul id din baza de date
	 */
	public Client insertClient(Client client) {
		return clientDAO.insert(client);
	}
	
	/**
	 * @param client la care trebuie facut update in baza de date
	 */
	public void updateClient(Client client) {
		clientDAO.update(client);
	}
	
	/**
	 * @param id clientul cu acest id trebuie sters
	 */
	public void deleteClient(int id) {
		clientDAO.delete(id);
	}
	
	/**
	 * @return lista cu toti clientii din baza de date
	 */
	public List<Client> findAllClients(){
		if(clientDAO.findAll()!=null)
			return clientDAO.findAll();
		else {
			List<Client> l = new ArrayList<Client>();
			l.add(new Client());
			return l;
		}
	}
}

