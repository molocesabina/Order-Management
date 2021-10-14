package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
* AbstractDAO este clasa generica care se ocupa generarea de queries pentru baza de date
* @author Moloce Sabina
*/

public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	/**
	 * Constructor
	 */
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}


	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append("`"+type.getSimpleName()+"`");
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

    /**
     * 
     * @param id cautam randul cu acest id
     * @return randul din baza de date ca si obiect
     */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
            
			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * 
	 * @param resultSet setul de date rezultat in urm query-ului
	 * @return lista cu obiectele din tabel
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if(list.isEmpty())
			return null;
		return list;
	}

    

	private String createInsertQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO "+"`"+type.getSimpleName()+"`"+" (");
		int ok=0;
		List<Object> values=new ArrayList<Object>();
		for (Field field : type.getDeclaredFields()) {
			field.setAccessible(true); 
			try {
				if(ok!=0) {
					sb.append(field.getName()+",");
					values.add(field.get(t));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} finally {
				field.setAccessible(false);
			}
			ok=1;
		}
		sb = sb.deleteCharAt(sb.length()-1);
		sb.append(") VALUES (");
		for(Object v: values) {
			if(v instanceof String) sb.append("'"+v+"'");
			else sb.append(v);
		    sb.append(",");	
		}
		sb = sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @param t obiectul pe care vrem sa-l inseram
	 * @return obictul cu id-ul dat de baza de date
	 */
	public T insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createInsertQuery(t);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		List<T> list=findAll();
		return list.get(list.size()-1);
	}
	

	private String createUpdateQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE "+"`"+type.getSimpleName()+"`");
		sb.append(" SET ");
		int ok=0;
	    int id=0;
		for (Field field : type.getDeclaredFields()) {
			field.setAccessible(true); 
			try {
				if(ok!=0) {
					Object v=field.get(t);
					sb.append(field.getName()+"=");
					if(v instanceof String) sb.append("'"+v+"'");
					else sb.append(v);
				    sb.append(",");	
				} else {
					id=(Integer)field.get(t);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} finally {
				field.setAccessible(false);
			}
			ok=1;
		}
		sb = sb.deleteCharAt(sb.length()-1);
		sb.append(" WHERE id="+id);
		return sb.toString();
	}
	
	public void update(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createUpdateQuery(t);
		int id = 0;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
			
			Field idF = type.getDeclaredField("id");
			idF.setAccessible(true);
			id = idF.getInt(t);
			idF.setAccessible(false);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:update" + e.getMessage());
			System.out.println(e.getMessage());
		} catch (Exception ex) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:update" + ex.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}
	
	private String createSelectAllQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append("`"+type.getSimpleName()+"`");
		return sb.toString();
	}
	
	/**
	 * 
	 * @return lista cu toate obiectele din tabel
	 */
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectAllQuery();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} 
		catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} 
		finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
	
	private String createDeleteQuery(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE from ");
		sb.append("`"+type.getSimpleName()+"`");
		sb.append(" WHERE id ="+id);
		return sb.toString();
	}
	
	/**
	 * 
	 * @param id stergem randul cu acest id
	 */
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createDeleteQuery(id);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}	
	}
}

