

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

import javax.swing.JFrame;
import javax.swing.JLabel;

public class adminCustomerEmpView {

	public JFrame frame;
	private JTable empTable;
	private JTable custTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminCustomerEmpView window = new adminCustomerEmpView();
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
	public adminCustomerEmpView() {
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
		
		JLabel lblNewLabel = new JLabel("Employees");
		lblNewLabel.setBounds(188, 11, 82, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 414, 87);
		frame.getContentPane().add(scrollPane);
		
		empTable = new JTable();
		scrollPane.setViewportView(empTable);
		
		JLabel lblNewLabel_1 = new JLabel("Customers");
		lblNewLabel_1.setBounds(188, 134, 71, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 151, 414, 87);
		frame.getContentPane().add(scrollPane_1);
		
		custTable = new JTable();
		scrollPane_1.setViewportView(custTable);
		
		JButton delCustEmp = new JButton("Delete an Employee or Customer");
		delCustEmp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                todelete();
            }
        });
		delCustEmp.setBounds(0, 238, 189, 23);
		frame.getContentPane().add(delCustEmp);
		
		JButton modCustEmp = new JButton("Modify Employee or Customer");
		modCustEmp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toModify();
            }
        });
		modCustEmp.setBounds(259, 238, 175, 23);
		frame.getContentPane().add(modCustEmp);
		
		database.connect();
		setupClosingDBConnection();
		populateEmpTable();
		populateCustomerTable();
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
	
	public void populateEmpTable() {

		try {
			Connection connection = database.connection;
			String query = "SELECT * FROM employees";
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
		        
		        empTable.setModel(model);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void populateCustomerTable() {

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
		        
		        custTable.setModel(model);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void todelete() {
		frame.dispose();
		deleteCustEmp CBD = new deleteCustEmp();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	public void toModify() {
		frame.dispose();
		modCustEmp CBD = new modCustEmp();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	

}
