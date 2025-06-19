
import java.sql.*;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
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
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class productCreator {

	public JFrame frame;
	private JTextField productName;
	private JTextField productPrice;
	private JTextField productQuantity;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					productCreator window = new productCreator();
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
	public productCreator() {
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
		
		
		database.connect();
		setupClosingDBConnection();
		createAddProductButton();
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(51, 33, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		productName = new JTextField();
		productName.setBounds(30, 58, 86, 20);
		frame.getContentPane().add(productName);
		productName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Price");
		lblNewLabel_1.setBounds(282, 33, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		productPrice = new JTextField();
		productPrice.setBounds(263, 58, 86, 20);
		frame.getContentPane().add(productPrice);
		productPrice.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Quantity");
		lblNewLabel_2.setBounds(51, 130, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		productQuantity = new JTextField();
		productQuantity.setBounds(30, 155, 86, 20);
		frame.getContentPane().add(productQuantity);
		productQuantity.setColumns(10);
		
		btnNewButton = new JButton("Go Back");
		btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		toEmployeePage();
	    	}
	    });
		btnNewButton.setBounds(30, 210, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		
	}
	
	
	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            try { database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
				} catch (SQLException e) { e.printStackTrace(); }
	        }
	    }, "Shutdown-thread"));
	}
	
	
	public void addProduct() {
		try {
			Connection connection = database.connection;
			String query = "INSERT INTO products(name, price, productQuantity) VALUES ( ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, productName.getText());
			stm.setString(2, productPrice.getText());
			stm.setString(3, productQuantity.getText());
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "Product created successfully!");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error creating Employee: " + e.getMessage());
		}
	}
	
	
	public void createAddProductButton() {
		JButton addProduct = new JButton("Create");
		addProduct.setBounds(260, 154, 89, 23);
		frame.getContentPane().add(addProduct);
		addProduct.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
	}
	
	
	public void toEmployeePage() {
		frame.dispose();
		employeePage CBD = new employeePage();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	
	
	
	
	
	
	
	
	
	
}
