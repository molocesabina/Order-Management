package presentation;


import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;

import bll.OrderBLL;
import model.Order;

/**
* OrderFrame este interfata grafica pentru comenzi
* @author Moloce Sabina
*/
public class OrderFrame extends JFrame {
	private OrderBLL orderBLL;
	private JPanel content;
	private JComboBox txtIdClient;
	private JComboBox txtIdProduct;
	private JTextField txtQuantity;
	private JTable t;
	private ListSelectionListener l;
	private Image img = new ImageIcon(getClass().getClassLoader().getResource("back0.jpg")).getImage().getScaledInstance(710, 500, Image.SCALE_DEFAULT); 
	
	/**
	 * Constructor
	 */
	public OrderFrame() {
		
	    orderBLL = new OrderBLL();
		t = JTableManagement.createTable(orderBLL.findAllOrders());

		
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
        
        JLabel lblIdClient = new JLabel("            IdClient ");
        lblIdClient.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblIdClient.setForeground(Color.DARK_GRAY);
        lblPanel.add(Box.createRigidArea(new Dimension(27,27)));
        lblPanel.add(lblIdClient);
        
        List<Integer> lc = orderBLL.getPossibleClientsIds();
        txtIdClient = new JComboBox();
        for(Integer i: lc) {
        	txtIdClient.addItem(i+"");
        }
        txtIdClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtIdClient.setForeground(Color.GRAY);
        actualFieldPanel.add(Box.createRigidArea(new Dimension(32,32)));
        actualFieldPanel.add(txtIdClient);
        
        JLabel lblIdProduct = new JLabel("            IdProduct ");
        lblIdProduct.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblIdProduct.setForeground(Color.DARK_GRAY);
        lblPanel.add(Box.createRigidArea(new Dimension(25,25)));
        lblPanel.add(lblIdProduct);
        
        List<Integer> lp = orderBLL.getPossibleProductsIds();
        txtIdProduct = new JComboBox();
        for(Integer i: lp) {
        	txtIdProduct.addItem(i+"");
        }
        txtIdProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtIdClient.setForeground(Color.GRAY);
        actualFieldPanel.add(Box.createRigidArea(new Dimension(20,20)));
        actualFieldPanel.add(txtIdProduct);
        
        JLabel lblQuantity = new JLabel("            Quantity ");
        lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblQuantity.setForeground(Color.DARK_GRAY);
        lblPanel.add(Box.createRigidArea(new Dimension(25,25)));
        lblPanel.add(lblQuantity);
        
        
        txtQuantity = new JTextField();
        txtQuantity.setBorder(null);
        txtQuantity.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txtQuantity.setForeground(Color.GRAY);
        actualFieldPanel.add(Box.createRigidArea(new Dimension(20,20)));
        actualFieldPanel.add(txtQuantity);
        
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
				if(!txtQuantity.getText().equals("")) {
					Order c = new Order(Integer.parseInt((String)txtIdClient.getSelectedItem()),Integer.parseInt((String)txtIdProduct.getSelectedItem()),Integer.parseInt(txtQuantity.getText()));
					try {
						Order cWithId = orderBLL.insertOrder(c);
						String id = cWithId.getId()+"";
						DefaultTableModel m = (DefaultTableModel)t.getModel();
						m.addRow(new Object[] {id,txtIdClient.getSelectedItem(),txtIdProduct.getSelectedItem(),txtQuantity.getText()});
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
        
        p1.add(butoanePanel);
        
        JPanel tabelPane = new JPanel();
        tabelPane.add(Box.createRigidArea(new Dimension(50,50)));
        tabelPane.setOpaque(false);
        JScrollPane scroll = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        t.setPreferredScrollableViewportSize(new Dimension(520, 90));
        t.setCellSelectionEnabled(false);
		t.setColumnSelectionAllowed(false);
		t.setRowSelectionAllowed(false);
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
				OrderFrame.this.dispose();
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
        
        this.setTitle("Orders");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       

	}

}
