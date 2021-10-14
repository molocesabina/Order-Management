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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
* GUI este interfata grafica pentru afisare meniului
* @author Moloce Sabina
*/

public class GUI extends JFrame {
    
	JPanel content;
	private Image img = new ImageIcon(getClass().getClassLoader().getResource("back0.jpg")).getImage().getScaledInstance(710, 425, Image.SCALE_DEFAULT); 
	/**
	 * Constructor
	 */
	public GUI() {
		content = new JPanel() {
            public void paintComponent(Graphics g) {
                 g.drawImage(img, 0, 0, this);
            }
        };
       
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS)); 
        content.setOpaque(false);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS)); 
        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        p1.add(Box.createRigidArea(new Dimension(200,300)));
        
        JPanel p2 = new JPanel(new GridLayout(7,1));
        p2.setOpaque(false);
      
        JButton b1 = new JButton("Clients");
        b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.this.dispose();
				ClientFrame c = new ClientFrame();
				c.setVisible(true);
			}
    
        });
        b1.setFont(new Font("Tahoma", Font.BOLD, 20));
        b1.setForeground(Color.GRAY);
        p2.add(Box.createRigidArea(new Dimension(25,25)));
        p2.add(b1);  
        
        p2.add(Box.createRigidArea(new Dimension(25,25)));
       
        JButton b2 = new JButton("Products");
        b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.this.dispose();
				ProductFrame p = new ProductFrame();
				p.setVisible(true);
			}
    
        });
        b2.setFont(new Font("Tahoma", Font.BOLD, 20));
        b2.setForeground(Color.GRAY);
        p2.add(b2); 
        p2.add(Box.createRigidArea(new Dimension(25,25)));
        
        JButton b3 = new JButton("Orders");
        b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.this.dispose();
				OrderFrame o = new OrderFrame();
				o.setVisible(true);
			}
    
        });
        b3.setFont(new Font("Tahoma", Font.BOLD, 20));
        b3.setForeground(Color.GRAY);
        p2.add(b3); 
        p2.add(Box.createRigidArea(new Dimension(25,25)));
       
        
        JPanel p3 = new JPanel();
        p3.setOpaque(false);
        p3.add(Box.createRigidArea(new Dimension(200,300)));
        
        panel.add(p1);
        panel.add(p2);
        panel.add(p3);
        content.add(panel);
        //content.add(panelButon);
 
        this.setContentPane(content);
        this.pack();
        
        this.setTitle("Order Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       

	}

}
