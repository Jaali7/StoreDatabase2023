

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

public class custDelOrder {

	public JFrame frame;
	private JTable orderTable;
	private JTextField uorderID;
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
					custDelOrder window = new custDelOrder();
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
	public custDelOrder() {
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 93);
		frame.getContentPane().add(scrollPane);
		
		orderTable = new JTable();
		scrollPane.setViewportView(orderTable);
		
		JLabel lblNewLabel = new JLabel("Enter the ID of the order you would like to delete");
		lblNewLabel.setBounds(20, 115, 255, 14);
		frame.getContentPane().add(lblNewLabel);
		
		uorderID = new JTextField();
		uorderID.setBounds(23, 140, 86, 20);
		frame.getContentPane().add(uorderID);
		uorderID.setColumns(10);
		
		JButton delOrder = new JButton("Delete");
		delOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
		delOrder.setBounds(20, 171, 89, 23);
		frame.getContentPane().add(delOrder);
		
		btnNewButton = new JButton("Go back");
		btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		toCustomerPage();
	    	}
	    });
		btnNewButton.setBounds(20, 205, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		database.connect();
		setupClosingDBConnection();
		setLookAndFeel();
		populateTable();
		
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
	
	public void populateTable() {
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
			String query = "SELECT * FROM orders WHERE customerID = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, cid);
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
		        
		        orderTable.setModel(model);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void delete(){
		try {
			Connection connection = database.connection;
			String query = "DELETE FROM orders WHERE orderID = ?";
			
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, uorderID.getText());
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "order deleted successfully!");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error deleting order: " + e.getMessage());
		}
	}
	public void toCustomerPage() {
		frame.dispose();
		customerPage CBD = new customerPage();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}

}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

