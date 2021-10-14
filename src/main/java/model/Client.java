package model;

/**
* Client este clasa care reprezinta tabelul client din baza de date
* @author Moloce Sabina
*/
public class Client {
		private int id;
		private String name;
		private String address;
		private String email;
		
		/**
		 * Constructor fara parametrii
		 */
		public Client() {
			
		}
		
		/**
		 * Constructor
		 * @param name nume
		 * @param address adresa
		 * @param email email
		 */
		public Client(String name, String address, String email) {
			this.name = name;
			this.address = address;
			this.email = email;
		}
		
		/**
		 * Constructor
		 * @param id id
		 * @param name nume
		 * @param address addresa
		 * @param email email
		 */
		public Client(int id, String name, String address, String email) {
			this.id = id;
			this.name = name;
			this.address = address;
			this.email = email;
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
		 * @return address
		 */
		public String getAddress() {
			return address;
		}
		
		/**
		 * 
		 * @param address noua adresa
		 */
		public void setAddress(String address) {
			this.address = address;
		}
		
		/**
		 * 
		 * @return email
		 */
		public String getEmail() {
			return email;
		}
		
		/**
		 * 
		 * @param email noul email
		 */
		public void setEmail(String email) {
			this.email = email;
		}
		
}
