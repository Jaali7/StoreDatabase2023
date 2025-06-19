
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

public class adminPage {

	public JFrame frame;
	private JTable adminTable;
	private String user;
	private String pass;
	private JButton viewData;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminPage window = new adminPage();
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
	public adminPage() {
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
		 
		btnNewButton = new JButton("Modify Customers & Employees");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		toemployeeModify();
	    	}
	    });
	    btnNewButton.setBounds(20, 231, 207, 30);
	    frame.getContentPane().add(btnNewButton);
	    
	    JButton btnNewButton_1 = new JButton("Modify Orders");
	    btnNewButton_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		toorderModify();
	    	}
	    });
	    btnNewButton_1.setBounds(271, 231, 124, 30);
	    frame.getContentPane().add(btnNewButton_1);
		
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
		user = adminreturnlogin.getUserValue();
	}
	public void accessPass() {
		pass = adminreturnlogin.getPassValue();
	}
	
	public void populateTable() {
		accessUser();
		accessPass();
		System.out.println(user);
		System.out.println(pass);
		try {
			Connection connection = database.connection;
			String query = "SELECT AdminUsername, AdminPassword, AdminFirstName, AdminLastName, AdminEmail FROM admins WHERE AdminUsername = ? AND AdminPassword = ?";
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
		        
		        adminTable.setModel(model);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void createPaneTable() {
		adminTable = new JTable();
	    JScrollPane scrollPane = new JScrollPane(adminTable);
	    scrollPane.setBounds(10, 10, 414, 221);
	    frame.getContentPane().add(scrollPane);
	    
	    
	    
	    
	}
	
	public void toAdminView() {
		frame.dispose();
		adminCustomerEmpView CBD = new adminCustomerEmpView();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	
	
	/*
	customerTable = new JTable();
	customerTable.setBounds(319, 233, -232, -201);
	frame.getContentPane().add(customerTable);
	*/
	
	
	public void toemployeeModify() {
		frame.dispose();
		employeeModify CBD = new employeeModify();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	public void toorderModify() {
		frame.dispose();
		orderModify CBD = new orderModify();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
}
	

	

