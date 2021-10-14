package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import bll.ClientBLL;
import model.Client;

/**
* ClientFrame este interfata grafica pentru clienti
* @author Moloce Sabina
*/

public class ClientFrame extends JFrame {
	private ClientBLL clientBLL;
	private JPanel content;
	private JTextField txtEmail;
	private JTextField txtAddress;
	private JTextField txtName;
	private JTable t;
	private ListSelectionListener l;
	private Image img = new ImageIcon(getClass().getClassLoader().getResource("back0.jpg")).getImage().getScaledInstance(710, 425, Image.SCALE_DEFAULT); 
	/**
	 * Constructor
	 */
	public ClientFrame() {
		
		clientBLL = new ClientBLL();
		t = JTableManagement.createTable(clientBLL.findAllClients());

		
		content = new JPanel() {
            public void paintComponent(Graphics g) {
                 g.drawImage(img, 0, 0, this);
            }
        };
        
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS)); 
        content.setOpaque(false);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS)); 
        
        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS)); 
        
        JPanel txtFieldsPanel = new JPanel();
        txtFieldsPanel.setLayout(new BoxLayout(txtFieldsPanel,BoxLayout.X_AXIS)); 
        txtFieldsPanel.setOpaque(false);
        
        JPanel lblPanel = new JPanel();
        lblPanel.setLayout(new BoxLayout(lblPanel,BoxLayout.Y_AXIS)); 
        lblPanel.setOpaque(false);
        
       
        JPanel actualFieldPanel = new JPanel();
        actualFieldPanel.setLayout(new BoxLayout(actualFieldPanel,BoxLayout.Y_AXIS)); 
        actualFieldPanel.setOpaque(false);
        
        JPanel umplutPanel = new JPanel();
        umplutPanel.add(Box.createRigidArea(new Dimension(50,50)));
        umplutPanel.setOpaque(false);
        
        JLabel lblName = new JLabel("            Name ");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblName.setForeground(Color.DARK_GRAY);
        lblPanel.add(Box.createRigidArea(new Dimension(15,15)));
        lblPanel.add(lblName);
        
        txtName = new JTextField();
        txtName.setBorder(null);
        txtName.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txtName.setForeground(Color.GRAY);
        actualFieldPanel.add(Box.createRigidArea(new Dimension(15,15)));
        actualFieldPanel.add(txtName);
        
        JLabel lblAddress = new JLabel("            Address ");
        lblAddress.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblAddress.setForeground(Color.DARK_GRAY);
        lblPanel.add(Box.createRigidArea(new Dimension(5,5)));
        lblPanel.add(lblAddress);
        
        txtAddress = new JTextField();
        txtAddress.setBorder(null);
        txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txtAddress.setForeground(Color.GRAY);
        actualFieldPanel.add(Box.createRigidArea(new Dimension(5,5)));
        actualFieldPanel.add(txtAddress);
        
        JLabel lblEmail = new JLabel("            Email ");
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblEmail.setForeground(Color.DARK_GRAY);
        lblPanel.add(Box.createRigidArea(new Dimension(5,5)));
        lblPanel.add(lblEmail);
        
        
        txtEmail = new JTextField();
        txtEmail.setBorder(null);
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txtEmail.setForeground(Color.GRAY);
        actualFieldPanel.add(Box.createRigidArea(new Dimension(5,5)));
        actualFieldPanel.add(txtEmail);
        
        lblPanel.add(Box.createRigidArea(new Dimension(5,5)));
        actualFieldPanel.add(Box.createRigidArea(new Dimension(5,5)));
    
        
        txtFieldsPanel.add(lblPanel);
        txtFieldsPanel.add(actualFieldPanel);
        txtFieldsPanel.add(umplutPanel);
        p1.add(txtFieldsPanel);
        
        JPanel butoanePanel = new JPanel();
        butoanePanel.setOpaque(false);
        
        butoanePanel.add(Box.createRigidArea(new Dimension(50,50)));
        JButton b1 = new JButton("INSERT");
        b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtName.getText().equals("") && !txtAddress.getText().equals("") && !txtEmail.getText().equals("")) {
					Client c = new Client(txtName.getText(),txtAddress.getText(),txtEmail.getText());
					try {
						Client cWithId = clientBLL.insertClient(c);
						String id = cWithId.getId()+"";
						DefaultTableModel m = (DefaultTableModel)t.getModel();
						m.addRow(new Object[] {id,txtName.getText(),txtAddress.getText(),txtEmail.getText()});
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Introduceti date in toate casutele!");
				}
			}
        });
        b1.setFont(new Font("Tahoma", Font.BOLD, 17));
        b1.setForeground(Color.DARK_GRAY);
        butoanePanel.add(b1);
        
        butoanePanel.add(Box.createRigidArea(new Dimension(50,50)));
        JButton b2 = new JButton("UPDATE");
        b2.setFont(new Font("Tahoma", Font.BOLD, 17));
        b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!t.getSelectionModel().isSelectionEmpty()) {
					if(!txtName.getText().equals("") && !txtAddress.getText().equals("") && !txtEmail.getText().equals("")) {
						int row = t.getSelectedRow();
						Object o = t.getValueAt(row,0);
						int id = 0;
						if(o instanceof Integer)
							id = (Integer)o;
						else if(o instanceof String) {
							id = Integer.parseInt((String)o);
						}
						String idS = id+"";
						Client c = new Client(id,txtName.getText(),txtAddress.getText(),txtEmail.getText());
						try {
							clientBLL.updateClient(c);
							DefaultTableModel m = (DefaultTableModel)t.getModel();
							m.setValueAt(c.getName(), row, 1);
							m.setValueAt(c.getAddress(), row, 2);
							m.setValueAt(c.getEmail(), row, 3);
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Completati casutele");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selectati clientul pe care vreti sa-l modificati!");
				}
			}
        });
        b2.setForeground(Color.DARK_GRAY);
        butoanePanel.add(b2);
        
        butoanePanel.add(Box.createRigidArea(new Dimension(50,50)));
        JButton b3 = new JButton("DELETE");
        b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!t.getSelectionModel().isSelectionEmpty()) {
					int row = t.getSelectedRow();
					Object o = t.getValueAt(row,0);
					int id = 0;
					if(o instanceof Integer)
						id = (Integer)o;
					else if(o instanceof String) {
						id = Integer.parseInt((String)o);
					}
					String idS = id+"";
					try {
						clientBLL.deleteClient(id);
						DefaultTableModel m = (DefaultTableModel)t.getModel();
						t.getSelectionModel().removeListSelectionListener(l);
						m.removeRow(row);
						t.getSelectionModel().addListSelectionListener(l);
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selectati clientul pe care vreti sa-l stergeti!");
				}
			}
        });
        b3.setFont(new Font("Tahoma", Font.BOLD, 17));
        b3.setForeground(Color.DARK_GRAY);
        butoanePanel.add(b3);
        
        p1.add(butoanePanel);
        
        JPanel tabelPane = new JPanel();
        tabelPane.add(Box.createRigidArea(new Dimension(50,50)));
        tabelPane.setOpaque(false);
        JScrollPane scroll = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        t.setPreferredScrollableViewportSize(new Dimension(520, 90));
        t.setCellSelectionEnabled(false);
		t.setColumnSelectionAllowed(false);
		t.setRowSelectionAllowed(true);
        l = new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int row = t.getSelectedRow();
                txtName.setText(t.getModel().getValueAt(row, 1).toString());
                txtAddress.setText(t.getModel().getValueAt(row, 2).toString());
                txtEmail.setText(t.getModel().getValueAt(row, 3).toString());
            }
        }; 
        t.getSelectionModel().addListSelectionListener(l);
        //t.setFillsViewportHeight(true);
        tabelPane.add(scroll);
        tabelPane.add(Box.createRigidArea(new Dimension(50,50)));
        p1.add(tabelPane);
        panel.add(p1);
        
        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS)); 
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientFrame.this.dispose();
				GUI g = new GUI();
				g.setVisible(true);
			}
        });
        back.setFont(new Font("Tahoma", Font.BOLD, 17));
        back.setForeground(Color.DARK_GRAY);
        p2.add(Box.createRigidArea(new Dimension(25,25)));
        p2.add(back);
        p2.add(Box.createRigidArea(new Dimension(25,25)));
        panel.add(p2);
       
        content.add(panel);
 
 
        this.setContentPane(content);
        this.pack();
        
        this.setTitle("Clients");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       

	}

}
