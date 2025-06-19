

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class employeeCustomerView {

	public JFrame frame;
	private JTable customers;
	private JScrollPane scrollPane_1;
	private JTable ctable;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JScrollPane scrollPane_2;
	private JTable totalorder;
	private JLabel lblNewLabel_3;
	private JScrollPane scrollPane_3;
	private JTable orderitems;
	private JTextField orderidview;
	private JButton sam;
	private JLabel lblNewLabel_4;
	private JTextField coupon;
	private JButton couponBtn;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JTextField discount;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeeCustomerView window = new employeeCustomerView();
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
	public employeeCustomerView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(900, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		sam = new JButton("View Items");
		sam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	populateitems();
            }
        });
		sam.setBounds(10, 317, 89, 23);
		frame.getContentPane().add(sam);
		
		couponBtn = new JButton("Create");
		couponBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	createCoupon();
            }
        });
		couponBtn.setBounds(660, 288, 89, 23);
		frame.getContentPane().add(couponBtn);
		
		database.connect();
		setupClosingDBConnection();
		createPaneTable();
		populateTable();
		populatectable();
		populatetotalorder();
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
	 
	 public void populateTable() {
			try {
				Connection connection = database.connection;
				String query = "SELECT c.customerID, c.customerFirstName, c.customerLastName, c.username, c.password, c.address, o.orderID, o.total_amount FROM orders o JOIN customers c ON o.customerID = c.customerID";
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
			        
			        customers.setModel(model);
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		
	 
	 public void populatectable() {
		 try {
				Connection connection = database.connection;
				String query = "SELECT * FROM customers";
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
			        
			        ctable.setModel(model);
			}catch(Exception e) {
				System.out.println(e);
			}
	 }
	 
	 public void populatetotalorder() {
		 try {
				Connection connection = database.connection;
				String query = "SELECT c.customerID, c.customerFirstName, c.customerLastName, SUM(o.total_amount) AS total_amount FROM orders o JOIN customers c ON o.customerID = c.customerID GROUP BY c.customerID";			
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
			        
			        totalorder.setModel(model);
			}catch(Exception e) {
				System.out.println(e);
			}
	 }
	 
	 public void populateitems() {
		 
		 
		 try {
				Connection connection = database.connection;
				String query = "SELECT o.customerID, oi.orderID, oi.item_name, oi.price FROM orders o JOIN orderitems oi ON o.orderID = oi.orderID WHERE o.customerID = ?";			
				PreparedStatement stm = connection.prepareStatement(query);
				stm.setString(1, orderidview.getText());
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
			        
			        orderitems.setModel(model);
			}catch(Exception e) {
				System.out.println(e);
			}
	 }
	 
	 public void createCoupon(){
		 
				try {
					Connection connection = database.connection;
					String query = "INSERT INTO coupons(code, discount) VALUES ( ?, ?)";
					PreparedStatement stm = connection.prepareStatement(query);
					
					stm.setString(1, coupon.getText());
					stm.setString(2, discount.getText());
					stm.executeUpdate();
					JOptionPane.showMessageDialog(frame, "coupon created successfully!");
				}catch(Exception e) {
					JOptionPane.showMessageDialog(frame, "Error creating coupon: " + e.getMessage());
				}
			
			
	 }
	
	 public void createPaneTable() {
			customers = new JTable();
			JScrollPane scrollPane = new JScrollPane(customers);
			scrollPane.setBounds(10, 33, 414, 109);
			frame.getContentPane().add(scrollPane);
			
			scrollPane_1 = new JScrollPane((Component) null);
			scrollPane_1.setBounds(10, 166, 414, 84);
			frame.getContentPane().add(scrollPane_1);
			
			ctable = new JTable();
			scrollPane_1.setViewportView(ctable);
			
			JLabel lblNewLabel = new JLabel("All customers");
			lblNewLabel.setBounds(162, 153, 165, 14);
			frame.getContentPane().add(lblNewLabel);
			
			lblNewLabel_1 = new JLabel("Customers with orders");
			lblNewLabel_1.setBounds(147, 8, 132, 14);
			frame.getContentPane().add(lblNewLabel_1);
			
			lblNewLabel_2 = new JLabel("Order totals");
			lblNewLabel_2.setBounds(665, 8, 125, 14);
			frame.getContentPane().add(lblNewLabel_2);
			
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(460, 33, 414, 109);
			frame.getContentPane().add(scrollPane_2);
			
			totalorder = new JTable();
			scrollPane_2.setViewportView(totalorder);
			
			lblNewLabel_3 = new JLabel("Select a customerID that you want to see the items for");
			lblNewLabel_3.setBounds(10, 261, 331, 14);
			frame.getContentPane().add(lblNewLabel_3);
			
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setBounds(109, 288, 414, 109);
			frame.getContentPane().add(scrollPane_3);
			
			orderitems = new JTable();
			scrollPane_3.setViewportView(orderitems);
			
			orderidview = new JTextField();
			orderidview.setBounds(10, 286, 86, 20);
			frame.getContentPane().add(orderidview);
			orderidview.setColumns(10);
			
			lblNewLabel_4 = new JLabel("Create coupon");
			lblNewLabel_4.setBounds(665, 153, 101, 14);
			frame.getContentPane().add(lblNewLabel_4);
			
			coupon = new JTextField();
			coupon.setBounds(663, 197, 86, 20);
			frame.getContentPane().add(coupon);
			coupon.setColumns(10);
			
			
			
			lblNewLabel_5 = new JLabel("Code");
			lblNewLabel_5.setBounds(675, 172, 46, 14);
			frame.getContentPane().add(lblNewLabel_5);
			
			lblNewLabel_6 = new JLabel("Discount");
			lblNewLabel_6.setBounds(673, 228, 76, 14);
			frame.getContentPane().add(lblNewLabel_6);
			
			discount = new JTextField();
			discount.setBounds(665, 258, 86, 20);
			frame.getContentPane().add(discount);
			discount.setColumns(10);
			
			btnNewButton = new JButton("Go back");
			btnNewButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		toEmployeePage();
		    	}
		    });
			btnNewButton.setBounds(660, 333, 89, 23);
			frame.getContentPane().add(btnNewButton);
			
			
		}
	 public void toEmployeePage() {
			frame.dispose();
			employeePage CBD = new employeePage();
			CBD.initialize();
			CBD.frame.setVisible(true);
		}
}
