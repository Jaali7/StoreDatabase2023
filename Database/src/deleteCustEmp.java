

import java.sql.*;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class deleteCustEmp {

	public JFrame frame;
	private JTextField empID;
	private JTextField custID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					deleteCustEmp window = new deleteCustEmp();
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
	public deleteCustEmp() {
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
		
		JLabel lblNewLabel = new JLabel("Delete Employee");
		lblNewLabel.setBounds(174, 11, 89, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("EmployeeID");
		lblNewLabel_1.setBounds(184, 36, 65, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		empID = new JTextField();
		empID.setBounds(174, 61, 86, 20);
		frame.getContentPane().add(empID);
		empID.setColumns(10);
		
		JButton delEmp = new JButton("Delete");
		delEmp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });
		delEmp.setBounds(174, 92, 89, 23);
		frame.getContentPane().add(delEmp);
		
		JLabel lblNewLabel_2 = new JLabel("Delete Customer");
		lblNewLabel_2.setBounds(174, 140, 89, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("CustomerID");
		lblNewLabel_3.setBounds(184, 165, 79, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		custID = new JTextField();
		custID.setBounds(174, 193, 86, 20);
		frame.getContentPane().add(custID);
		custID.setColumns(10);
		
		JButton delCust = new JButton("Delete");
		 delCust.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                deleteCustomer();
	            }
	        });
		delCust.setBounds(174, 227, 89, 23);
		frame.getContentPane().add(delCust);
	}

	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            try { database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
				} catch (SQLException e) { e.printStackTrace(); }
	        }
	    }, "Shutdown-thread"));
	}
	
	
	public void deleteEmployee() {

			try {
				Connection connection = database.connection;
				String query = "DELETE FROM employees WHERE employeeID = ?";
				PreparedStatement stm = connection.prepareStatement(query);
				
				stm.setString(1, empID.getText());
				stm.executeUpdate();
				JOptionPane.showMessageDialog(frame, "Employee deleted successfully!");
			}catch(Exception e) {
				JOptionPane.showMessageDialog(frame, "Error deleting Employee: " + e.getMessage());
			}
		}
	
	public void deleteCustomer() {

		try {
			Connection connection = database.connection;
			String query = "DELETE FROM customers WHERE customerID = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, custID.getText());
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "Customer deleted successfully!");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error deleting Customer: " + e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
