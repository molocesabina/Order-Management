package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* ConnectionFactory este clasa care se ocupa de stabilrea conexiunii cu baza de date
* @author Moloce Sabina
*/
public class ConnectionFactory {

	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/warehousedb";
	private static final String USER = "root";
	private static final String PASS = "root";

	private static ConnectionFactory singleInstance = new ConnectionFactory();

	/**
	 * Constructor
	 */
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DBURL+"?useTimezone=true&serverTimezone=UTC", USER, PASS);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
			e.printStackTrace();
		} catch(IndexOutOfBoundsException ex) {
			//System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return connection;
	}

	/**
	 * 
	 * @return conexiunea la baza de date
	 */
	public static Connection getConnection() {
		return singleInstance.createConnection();
	}

	/**
	 * 
	 * @param connection conexiunea pe care dorim sa o inchidem
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
			}
		}
	}

	/**
	 * 
	 * @param statement pe care dormi sa-l inchidem
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
			}
		}
	}

	/**
	 * 
	 * @param resultSet pe care vrem sa-l inchidem
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
			}
		}
	}
}

