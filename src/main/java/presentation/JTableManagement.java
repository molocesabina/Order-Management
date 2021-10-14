package presentation;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
* JTableManagement este clasa care se ocupa cu managementul tabelor (crearea prin utilizand reflexi)
* @author Moloce Sabina
*/

public class JTableManagement {
    /**
     * 
     * @param objects lista cu obiecte
     * @return tabelul creeat
     */
	public static JTable createTable(List<?> objects) {
		Object obj = objects.get(0);
		int nbColumns = obj.getClass().getDeclaredFields().length;
		String[] headOfTable = new String[nbColumns];
		int i=0;
		int ok=1;
		for (Field field : obj.getClass().getDeclaredFields()) {
			if(i==0) {
				field.setAccessible(true); 
				try {
					ok =(Integer)field.get(obj);	
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					field.setAccessible(false); 
				}
			}
			headOfTable[i] = field.getName();
			i++;
		}
		Object[][] dataFromTable = null;
		if(ok!=0) {
			dataFromTable = getDataTable(objects);
		}
		DefaultTableModel m = new DefaultTableModel() {
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		m.setDataVector(dataFromTable, headOfTable);
		JTable t = new JTable(m);
		return t;
	}
	
	
	
	private static Object[][] getDataTable(List<?> objects) {
		Object obj = objects.get(0);
		int nbColumns = obj.getClass().getDeclaredFields().length;
		Object[][] dataFromTable = new Object[objects.size()][nbColumns];
		int i=0;
		for(Object o: objects) {
			int j=0;
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true); 
				try {
					dataFromTable[i][j] = field.get(o);	
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} finally {
					field.setAccessible(false); 
				}
				j++;
			   }
			i++;
		}
		return dataFromTable;
	}

}
