
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;


import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.EventQueue;
import javax.swing.table.DefaultTableModel;


import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class employeePage {

	public JFrame frame;
	private JTable employeeTable;
	private String user;
	private String pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeePage window = new employeePage();
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
	public employeePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		database.connect();
		setupClosingDBConnection();
		accessUser();
		accessPass();
		createPaneTable();
		populateTable();
		 
		JButton customerview = new JButton("View Customers");
	    customerview.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		tocustomerView();
	    	}
	    });
	    customerview.setBounds(10, 238, 109, 23);
	    frame.getContentPane().add(customerview);
	    
	    JButton addProduct = new JButton("Create Products");
	    addProduct.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		toProductCreator();
	    	}
	    });
	    addProduct.setBounds(266, 238, 127, 23);
	    frame.getContentPane().add(addProduct);
	    
	    JButton btnNewButton = new JButton("Main Menu");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		toMainMenu();
	    	}
	    });
	    btnNewButton.setBounds(149, 238, 89, 23);
	    frame.getContentPane().add(btnNewButton);
		
		
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
		user = employeereturnlogin.getUserValue();
	}
	public void accessPass() {
		pass = employeereturnlogin.getPassValue();
	}
	
	public void populateTable() {
		accessUser();
		accessPass();
		System.out.println(user);
		System.out.println(pass);
		try {
			Connection connection = database.connection;
			String query = "SELECT EmployeeUsername, EmployeePassword, employeeFirstName, employeeLastName, empEmail FROM employees WHERE EmployeeUsername = ? AND EmployeePassword = ?";
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
		        
		        employeeTable.setModel(model);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void createPaneTable() {
		frame.getContentPane().setLayout(null);
		employeeTable = new JTable();
	    JScrollPane scrollPane = new JScrollPane(employeeTable);
	    scrollPane.setBounds(10, 10, 383, 220);
	    frame.getContentPane().add(scrollPane);
	    
	   
	}
	
	
	public void tocustomerView() {
		frame.dispose();
		employeeCustomerView CBD = new employeeCustomerView();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	
	public void toProductCreator() {
		frame.dispose();
		productCreator CBD = new productCreator();
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
	

	

