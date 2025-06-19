
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

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
import java.awt.ScrollPane;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

public class employeeModify {

	public JFrame frame;
	private JTable employeeTable;
	private String user;
	private String pass;
	private JTable customerTable;
	private JLabel lblNewLabel;
	private JTextField user_id;
	private JLabel lblNewLabel_1;
	private JTextField user_name;
	private JLabel lblNewLabel_2;
	private JTextField user_pass;
	private JLabel lblNewLabel_3;
	private JTextField user_firstname;
	private JLabel lblNewLabel_4;
	private JTextField user_lastname;
	private JLabel lblNewLabel_5;
	public JTextField user_address;
	public JButton createEmployeeButton;
	public JTextArea txtrClickcreateEmployee;
	private JTextArea txtrClickupdateEmployee;
	private JButton btnNewButton;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JTextArea txtrNoteBeforeDeleting;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeeModify window = new employeeModify();
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
	public employeeModify() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 950, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		database.connect();
		setupClosingDBConnection();
		accessUser();
		accessPass();
		createEmployeeTable();
		populateEmployeeTable();
		createCustomerTable();
		populateCustomerTable();
		createUserFields();
	
		
		
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
	
	public void populateEmployeeTable() {
	//	accessUser();
	//	accessPass();
	//	System.out.println(user);
	//	System.out.println(pass);
		try {
			Connection connection = database.connection;
			String query = "SELECT EmployeeID, EmployeeUsername, EmployeePassword, EmployeeFirstName, EmployeeLastName, EmployeeEmail FROM employees"; // WHERE EmployeeUsername = ? AND EmployeePassword = ?";
			PreparedStatement stm = connection.prepareStatement(query);
		//	stm.setString(1, user);
		//	stm.setString(2, pass);
			ResultSet result = stm.executeQuery();
			 ResultSetMetaData metaData = result.getMetaData();
		        int columnCount = metaData.getColumnCount();
		        DefaultTableModel model = new DefaultTableModel();
		        
		        // Add the columns to the table model
		 //       for (int i = 1; i <= columnCount; i++) {
		 //           model.addColumn(metaData.getColumnName(i));
		  //      }
		        model.addColumn("ID");
		        model.addColumn("Username");
		        model.addColumn("Password");
		        model.addColumn("First Name");
		        model.addColumn("Last Name");
		        model.addColumn("EmailAddress");   
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
	
	public void createEmployeeTable() {
		employeeTable = new JTable();
	    JScrollPane scrollPane = new JScrollPane(employeeTable);
	    scrollPane.setBounds(10, 28, 375, 123);
	    frame.getContentPane().add(scrollPane);
	    
	
	}
	public void populateCustomerTable() {
	//	accessUser();
	//	accessPass();
	//	System.out.println(user);
	//	System.out.println(pass);
		try {
			Connection connection = database.connection;
			String query = "SELECT customerID, username, password, customerFirstName, customerLastname, address FROM customers"; // WHERE username = ? AND password = ?";
			PreparedStatement stm = connection.prepareStatement(query);
		//	stm.setString(1, user);
		//	stm.setString(2, pass);
			ResultSet result = stm.executeQuery();
			 ResultSetMetaData metaData = result.getMetaData();
		        int columnCount = metaData.getColumnCount();
		        DefaultTableModel model = new DefaultTableModel();
		        
		        // Add the columns to the table model
		   //     for (int i = 1; i <= columnCount; i++) {
		    //        model.addColumn(metaData.getColumnName(i));
		     //   }
		        model.addColumn("ID");
		        model.addColumn("Username");
		        model.addColumn("Password");
		        model.addColumn("First Name");
		        model.addColumn("Last Name");
		        model.addColumn("EmailAddress");
		        
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
	
	public void createCustomerTable() {
		customerTable = new JTable();
		 JScrollPane scrollPane_1 = new JScrollPane(customerTable);
		 scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		 scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		    scrollPane_1.setBounds(10, 175, 375, 123);
		    frame.getContentPane().add(scrollPane_1);
	
	}
	public void createUserFields() {
		    lblNewLabel = new JLabel("User ID");
		    lblNewLabel.setBounds(395, 8, 96, 14);
		    frame.getContentPane().add(lblNewLabel);
		    
		    user_id = new JTextField();
		    user_id.setBounds(395, 22, 96, 20);
		    frame.getContentPane().add(user_id);
		    user_id.setColumns(10);
		    
		    lblNewLabel_1 = new JLabel("Username");
		    lblNewLabel_1.setBounds(395, 44, 96, 14);
		    frame.getContentPane().add(lblNewLabel_1);
		    
		    user_name = new JTextField();
		    user_name.setBounds(395, 58, 96, 20);
		    frame.getContentPane().add(user_name);
		    user_name.setColumns(10);
		    
		    lblNewLabel_2 = new JLabel("Password");
		    lblNewLabel_2.setBounds(395, 78, 96, 14);
		    frame.getContentPane().add(lblNewLabel_2);
		    
		    user_pass = new JTextField();
		    user_pass.setBounds(395, 92, 96, 20);
		    frame.getContentPane().add(user_pass);
		    user_pass.setColumns(10);
		    
		    lblNewLabel_3 = new JLabel("First Name");
		    lblNewLabel_3.setBounds(395, 114, 96, 14);
		    frame.getContentPane().add(lblNewLabel_3);
		    
		    user_firstname = new JTextField();
		    user_firstname.setBounds(395, 128, 96, 20);
		    frame.getContentPane().add(user_firstname);
		    user_firstname.setColumns(10);
		    
		    lblNewLabel_4 = new JLabel("Last Name");
		    lblNewLabel_4.setBounds(395, 151, 96, 14);
		    frame.getContentPane().add(lblNewLabel_4);
		    
		    user_lastname = new JTextField();
		    user_lastname.setBounds(395, 164, 96, 20);
		    frame.getContentPane().add(user_lastname);
		    user_lastname.setColumns(10);
		    
		    lblNewLabel_5 = new JLabel("Email");
		    lblNewLabel_5.setBounds(395, 187, 96, 14);
		    frame.getContentPane().add(lblNewLabel_5);
		    
		    user_address = new JTextField();
		    user_address.setBounds(395, 200, 96, 20);
		    frame.getContentPane().add(user_address);
		    user_address.setColumns(10);
		    
		    createEmployeeButton = new JButton("Create Employee");
		    createEmployeeButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    createEmployeeButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		addEmployee();
		    		populateEmployeeTable();
		    	}
		    });
		    createEmployeeButton.setBounds(500, 59, 115, 23);
		    frame.getContentPane().add(createEmployeeButton);
		    
		    txtrClickcreateEmployee = new JTextArea();
		    txtrClickcreateEmployee.setFont(new Font("Monospaced", Font.PLAIN, 9));
		    txtrClickcreateEmployee.setText("Click \"Create Employee\"/\"Create Customer\" only\r\nafter you've entered all of the new customer or\r\nthe new employee's data!\r\n");
		    txtrClickcreateEmployee.setEditable(false);
		    txtrClickcreateEmployee.setBounds(501, 4, 249, 38);
		    frame.getContentPane().add(txtrClickcreateEmployee);
		    
		    txtrClickupdateEmployee = new JTextArea();
		    txtrClickupdateEmployee.setText("Click \"Update Employee\"/\"Update Customer\" only\r\nafter you've selected the ID of whom you would like \r\nto update, and enter all data fields for their new info\r\n(re-enter old info if not changing)");
		    txtrClickupdateEmployee.setFont(new Font("Monospaced", Font.PLAIN, 9));
		    txtrClickupdateEmployee.setBounds(510, 90, 259, 58);
		    frame.getContentPane().add(txtrClickupdateEmployee);
		    
		    btnNewButton = new JButton("Update Employee");
		    btnNewButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		updateEmployee();
		    		populateEmployeeTable();
		    	}
		    });
		    btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    btnNewButton.setBounds(506, 166, 115, 23);
		    frame.getContentPane().add(btnNewButton);
		    
		    lblNewLabel_6 = new JLabel("Employee Data");
		    lblNewLabel_6.setBounds(20, 3, 102, 14);
		    frame.getContentPane().add(lblNewLabel_6);
		    
		    lblNewLabel_7 = new JLabel("Customer Data");
		    lblNewLabel_7.setBounds(20, 159, 96, 14);
		    frame.getContentPane().add(lblNewLabel_7);
		    
		    btnNewButton_1 = new JButton("Create Customer");
		    btnNewButton_1.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		addCustomer();
		    		populateCustomerTable();
		    	}
		    });
		    btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    btnNewButton_1.setBounds(634, 59, 115, 23);
		    frame.getContentPane().add(btnNewButton_1);
		    
		    btnNewButton_2 = new JButton("Update Customer");
		    btnNewButton_2.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		updateCustomer();
		    		populateCustomerTable();
		    	}
		    });
		    btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    btnNewButton_2.setBounds(634, 166, 115, 23);
		    frame.getContentPane().add(btnNewButton_2);
		    
		    btnNewButton_3 = new JButton("Delete Employee");
		    btnNewButton_3.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		deleteEmployee();
		    		populateEmployeeTable();
		    	}
		    });
		    btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    btnNewButton_3.setBounds(510, 199, 111, 23);
		    frame.getContentPane().add(btnNewButton_3);
		    
		    btnNewButton_4 = new JButton("Delete Customer");
		    btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    btnNewButton_4.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		deleteCustomer();
		    		populateCustomerTable();
		    	}
		    });
		    btnNewButton_4.setBounds(634, 199, 116, 23);
		    frame.getContentPane().add(btnNewButton_4);
		    
		    txtrNoteBeforeDeleting = new JTextArea();
		    txtrNoteBeforeDeleting.setText("Note: Before deleting employees/customers make sure\r\nyou specify the ID above in the User ID column");
		    txtrNoteBeforeDeleting.setFont(new Font("Monospaced", Font.PLAIN, 9));
		    txtrNoteBeforeDeleting.setBounds(510, 234, 259, 44);
		    frame.getContentPane().add(txtrNoteBeforeDeleting);
		    
		    btnNewButton_5 = new JButton("Go Back");
		    btnNewButton_5.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		toAdminPage();
		    	}
		    });
		    btnNewButton_5.setBounds(839, 11, 89, 23);
		    frame.getContentPane().add(btnNewButton_5);
		    
		    btnNewButton_6 = new JButton("Main Menu");
		    btnNewButton_6.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		toMainMenu();
		    	}
		    });
		    btnNewButton_6.setBounds(839, 40, 89, 23);
		    frame.getContentPane().add(btnNewButton_6);
		    
		    
	}
	
	// Adding Employees/Modify Customers
	
	public void addEmployee() {
		try {
			Connection connection = database.connection;
			String query = "INSERT INTO employees(EmployeeID, EmployeeUsername, EmployeePassword, EmployeeFirstName, EmployeeLastName, EmployeeEmail) VALUES ( ?, ?, ?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, user_id.getText());
			stm.setString(2, user_name.getText());
			stm.setString(3, user_pass.getText());
			stm.setString(4, user_firstname.getText());
			stm.setString(5, user_lastname.getText());
			stm.setString(6, user_address.getText());
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "Employee created successfully!");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error creating Employee: " + e.getMessage());
		}
	}
	public void updateEmployee() {
		try {
			Connection connection = database.connection;
			String query = "UPDATE employees SET EmployeeID=?, EmployeeUsername=?, EmployeePassword=?, EmployeeFirstName=?, EmployeeLastName=?, EmployeeEmail=? WHERE EmployeeID=?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, user_id.getText());
			stm.setString(2, user_name.getText());
			stm.setString(3, user_pass.getText());
			stm.setString(4, user_firstname.getText());
			stm.setString(5, user_lastname.getText());
			stm.setString(6, user_address.getText());
			stm.setString(7,  user_id.getText());
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "Employee updated successfully!");
			populateEmployeeTable();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error creating Employee: " + e.getMessage());
		}
	}
	public void addCustomer() {
		try {
			Connection connection = database.connection;
			String query = "INSERT INTO customers(customerID, username, password, customerFirstName, customerLastName, address) VALUES ( ?, ?, ?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, user_id.getText());
			stm.setString(2, user_name.getText());
			stm.setString(3, user_pass.getText());
			stm.setString(4, user_firstname.getText());
			stm.setString(5, user_lastname.getText());
			stm.setString(6, user_address.getText());
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "Customer created successfully!");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error creating Customer: " + e.getMessage());
		}
	}
	public void updateCustomer() {
		try {
			Connection connection = database.connection;
			String query = "UPDATE customers SET customerID=?, username=?, password=?, customerFirstName=?, customerLastName=?, address=? WHERE customerID=?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, user_id.getText());
			stm.setString(2, user_name.getText());
			stm.setString(3, user_pass.getText());
			stm.setString(4, user_firstname.getText());
			stm.setString(5, user_lastname.getText());
			stm.setString(6, user_address.getText());
			stm.setString(7, user_id.getText());
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "Customer updated successfully!");
			populateEmployeeTable();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error creating Customer: " + e.getMessage());
		}
	}
	public  void deleteEmployee() {
        try {
            Connection connection = database.connection;
            String query = "DELETE FROM employees WHERE EmployeeID = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, Integer.parseInt(user_id.getText()));
            stm.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Employee Deleted successfully!");
			populateEmployeeTable();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error Deleting Employee: " + e.getMessage());
		}

    }
	public  void deleteCustomer() {
        try {
            Connection connection = database.connection;
            String query = "DELETE FROM customers WHERE customerID = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, Integer.parseInt(user_id.getText()));
            stm.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Customer Deleted successfully!");
			populateEmployeeTable();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error Deleting Customer: " + e.getMessage());
		}

    }
	public void toMainMenu() {
		frame.dispose();
		MainMenu CBD = new MainMenu();
		CBD.initialize();
		CBD.frame.setVisible(true);
}
	public void toAdminPage() {
		frame.dispose();
		adminPage CBD = new adminPage();
		CBD.initialize();
		CBD.frame.setVisible(true);
}
}
	

	

