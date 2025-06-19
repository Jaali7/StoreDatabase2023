

import java.sql.*;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;


import javax.swing.JFrame;

public class custModName {

	public JFrame frame;
	private JTextField newFirst;
	private JTextField newLast;
	private String user;
	private String pass;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					custModName window = new custModName();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public custModName() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Choose a new First and Last name");
		lblNewLabel.setBounds(135, 11, 194, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblDontLeaveAnything = new JLabel("Dont leave anything blank");
		lblDontLeaveAnything.setBounds(155, 36, 194, 14);
		frame.getContentPane().add(lblDontLeaveAnything);
		
		newFirst = new JTextField();
		newFirst.setBounds(119, 61, 86, 20);
		frame.getContentPane().add(newFirst);
		newFirst.setColumns(10);
		
		newLast = new JTextField();
		newLast.setBounds(229, 61, 86, 20);
		frame.getContentPane().add(newLast);
		newLast.setColumns(10);
		
		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
		update.setBounds(173, 92, 89, 23);
		frame.getContentPane().add(update);
		
		btnNewButton = new JButton("Go back");
		btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		toCustomerPage();
	    	}
	    });
		btnNewButton.setBounds(173, 128, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		database.connect();
		setupClosingDBConnection();
		setLookAndFeel();
		
		
		
	}
	
	
	public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    database.connection.close();
                    System.out.println("Application Closed - DB Connection Closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, "Shutdown-thread"));
    }
	
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e) {}
	}
	
	public void accessUser() {
		user = returnlogin.getUserValue();
	}
	public void accessPass() {
		pass = returnlogin.getPassValue();
	}
	

	public void update() {
		accessUser();
		accessPass();
		int cid = 0; 
		try { //grabs customerid
			Connection connection = database.connection;
			String query = "SELECT customerID FROM customers WHERE username = ? AND password = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, user);
			stm.setString(2, pass);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				cid = rs.getInt("customerID");
			}
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error accessing database: " + e.getMessage());
		}
		
		
		
		
		try {
			Connection connection = database.connection;
			String query = "UPDATE customers SET customerFirstName = ?, customerLastName = ? WHERE customerID = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, newFirst.getText());
			stm.setString(2, newLast.getText());
			stm.setInt(3, cid);
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "Succesfully updated First and Last name! ");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error accessing database: " + e.getMessage());
		}
	}
	
	
	public void toCustomerPage() {
		frame.dispose();
		customerPage CBD = new customerPage();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
