
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.EventQueue;
import javax.swing.table.DefaultTableModel;


import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Component;

public class customerPage {

	public JFrame frame;
	private JTable customerTable;
	private String user;
	private String pass;
	private JTable productTable;
	private JTextField productid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customerPage window = new customerPage();
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
	public customerPage() {
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
		accessUser();
		accessPass();
		createPaneTable();
		populateTable();
		setLookAndFeel();
		
	
	    
	    JScrollPane scrollPane_1 = new JScrollPane((Component) null);
	    scrollPane_1.setBounds(10, 81, 414, 79);
	    frame.getContentPane().add(scrollPane_1);
	    
	    productTable = new JTable();
	    scrollPane_1.setViewportView(productTable);
	    
	    JLabel lblNewLabel = new JLabel("Enter the ID of the product you want to add to your order");
	    lblNewLabel.setBounds(31, 171, 303, 14);
	    frame.getContentPane().add(lblNewLabel);
	    
	    productid = new JTextField();
	    productid.setBounds(34, 196, 86, 20);
	    frame.getContentPane().add(productid);
	    productid.setColumns(10);
	    
	    JButton btnNewButton = new JButton("Modify Personal Data");
	    btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toMod();
            }
        });
	    btnNewButton.setBounds(289, 227, 135, 23);
	    frame.getContentPane().add(btnNewButton);
		 
	    populateProductTable();
	    
	    JButton orderBtn = new JButton("Place Order");
	    orderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });
	    orderBtn.setBounds(31, 227, 89, 23);
	    frame.getContentPane().add(orderBtn);
	    
	    JButton delOrder = new JButton("Delete Order(s)");
	    delOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                todelOrder();
            }
        });
	    delOrder.setBounds(149, 227, 115, 23);
	    frame.getContentPane().add(delOrder);
	    
	    JButton btnNewButton_1 = new JButton("Main Menu");
	    btnNewButton_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		toMainMenu();
	    	}
	    });
	    btnNewButton_1.setBounds(319, 196, 89, 23);
	    frame.getContentPane().add(btnNewButton_1);
		
	}
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e) {}
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
	
	public void accessUser() {
		user = returnlogin.getUserValue();
	}
	public void accessPass() {
		pass = returnlogin.getPassValue();
	}
	
	public void populateTable() {
		accessUser();
		accessPass();
		System.out.println(user);
		System.out.println(pass);
		try {
			Connection connection = database.connection;
			String query = "SELECT username, password, customerFirstName, customerLastname, address FROM customers WHERE username = ? AND password = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, user);
			stm.setString(2, pass);
			ResultSet result = stm.executeQuery();
			 ResultSetMetaData metaData = result.getMetaData();
		        int columnCount = metaData.getColumnCount();
		        DefaultTableModel model = new DefaultTableModel();
		        
		        // Add the columns to the table model
		        for (int i = 1; i <= columnCount; i++) {
		            model.addColumn(metaData.getColumnName(i));
		        }
		        
		        // Add the rows to the table model
		        while (result.next()) {
		            Object[] rowData = new Object[columnCount];
		            for (int i = 1; i <= columnCount; i++) {
		                rowData[i - 1] = result.getObject(i);
		            }
		            model.addRow(rowData);
		        }
		        
		        customerTable.setModel(model);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void createPaneTable() {
		customerTable = new JTable();
	    JScrollPane scrollPane = new JScrollPane(customerTable);
	    scrollPane.setBounds(10, 10, 414, 60);
	    frame.getContentPane().add(scrollPane);
	    
	   
	}
	
	
	public void populateProductTable() {

		try {
			Connection connection = database.connection;
			String query = "SELECT * FROM products";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			 ResultSetMetaData metaData = result.getMetaData();
		        int columnCount = metaData.getColumnCount();
		        DefaultTableModel model = new DefaultTableModel();
		        
		        // Add the columns to the table model
		        for (int i = 1; i <= columnCount; i++) {
		            model.addColumn(metaData.getColumnName(i));
		        }
		        
		        // Add the rows to the table model
		        while (result.next()) {
		            Object[] rowData = new Object[columnCount];
		            for (int i = 1; i <= columnCount; i++) {
		                rowData[i - 1] = result.getObject(i);
		            }
		            model.addRow(rowData);
		        }
		        
		        productTable.setModel(model);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void placeOrder() {
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
			
			double price = 0.0;
			String pname= "";
			int quantity = 0;
			try { //grabs product price, name, quantity
				Connection connection = database.connection;
				String query = "SELECT price, name, productQuantity FROM products WHERE productID = ?";
				PreparedStatement stm = connection.prepareStatement(query);
				stm.setString(1, productid.getText());
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
	                price = rs.getDouble("price");
	                pname = rs.getString("name");
	                quantity = rs.getInt("productQuantity");
	            }
				
			}catch (Exception e){
				JOptionPane.showMessageDialog(frame, "Error accessing database: " + e.getMessage());
			}
			
			
			try { //places order
				Connection connection = database.connection;
				String query = "INSERT INTO orders(customerID, total_amount) VALUES(?, ?)";
				PreparedStatement stm = connection.prepareStatement(query);
				stm.setInt(1, cid);
				stm.setDouble(2, price);
				stm.executeUpdate();
				JOptionPane.showMessageDialog(frame,  "Order placed!");
			}catch(Exception e) {
				JOptionPane.showMessageDialog(frame, "Error placing order database: " + e.getMessage());
			}
			
			int oid = 0;
			try { //grabs orderid
				Connection connection = database.connection;
				String query = "SELECT orderID FROM orders WHERE customerID = ?";
				PreparedStatement stm = connection.prepareStatement(query);
				stm.setInt(1, cid);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
	                oid = rs.getInt("orderID");
	               
	            }
			}catch(Exception e) {
				JOptionPane.showMessageDialog(frame, "Error accessing database: " + e.getMessage());
			}
			
			try { //places orderitems
				Connection connection = database.connection;
				String query = "INSERT INTO orderitems(orderID, item_name, price, quantity) VALUES(?, ?, ?, ?)";
				PreparedStatement stm = connection.prepareStatement(query);
				stm.setInt(1, oid);
				stm.setString(2, pname);
				stm.setDouble(3, price);
				stm.setInt(4, quantity);
				stm.executeUpdate();
			}catch(Exception e) {
				JOptionPane.showMessageDialog(frame, "Error accessing database: " + e.getMessage());
				
			}
		
	}
	
	
	public void todelOrder() {
		frame.dispose();
		custDelOrder CBD = new custDelOrder();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	
	public void toMod() {
		frame.dispose();
		custModName CBD = new custModName();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	
	
	public void toMainMenu() {
		frame.dispose();
		MainMenu CBD = new MainMenu();
		CBD.initialize();
		CBD.frame.setVisible(true);
}
	
	
	
	
	
}
	

	

