

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





public class orderModify {



	public JFrame frame;

	private JTable OrderItemTable;

	private String user;

	private String pass;

	private JTable orderTable;

	private JLabel lblNewLabel;

	private JTextField order_id;

	public JTextArea txtrClickcreateEmployee;

	private JLabel lblNewLabel_6;

	private JLabel lblNewLabel_7;

	private JButton btnNewButton_1;

	private JButton btnNewButton_2;

	private JLabel lblNewLabel_12;

	private JTextField customer_id;

	private JTextField order_total;

	private JTextField coupon_code;

	private JTextField order_status;

	private JTextArea txtrEnterOnlyordered;

	private JLabel lblNewLabel_13;

	private JLabel lblNewLabel_14;

	private JButton btnNewButton;

	private JButton btnNewButton_3;



	/**

	* Launch the application.

	*/

	public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {

	public void run() {

	try {

	orderModify window = new orderModify();

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

	public orderModify() {

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

	createOrderItemTable();

	populateOrderItemTable();

	createOrderTable();

	populateOrderTable();

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

	

	public void populateOrderItemTable() {

	//	accessUser();

	//	accessPass();

	//	System.out.println(user);

	//	System.out.println(pass);

	try {

	Connection connection = database.connection;

	String query = "SELECT * FROM orderitems"; // WHERE EmployeeUsername = ? AND EmployeePassword = ?";

	PreparedStatement stm = connection.prepareStatement(query);

	//	stm.setString(1, user);

	//	stm.setString(2, pass);

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

	

	OrderItemTable.setModel(model);

	}catch(Exception e) {

	System.out.println(e);

	}

	}

	

	public void createOrderItemTable() {

	OrderItemTable = new JTable();

	JScrollPane scrollPane = new JScrollPane(OrderItemTable);

	scrollPane.setBounds(10, 28, 375, 123);

	frame.getContentPane().add(scrollPane);

	

	

	}

	public void populateOrderTable() {

	//	accessUser();

	//	accessPass();

	//	System.out.println(user);

	//	System.out.println(pass);

	try {

	Connection connection = database.connection;

	String query = "SELECT * FROM orders"; // WHERE username = ? AND password = ?";

	PreparedStatement stm = connection.prepareStatement(query);

	//	stm.setString(1, user);

	//	stm.setString(2, pass);

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

	

	public void createOrderTable() {

	orderTable = new JTable();

	JScrollPane scrollPane_1 = new JScrollPane(orderTable);

	scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

	scrollPane_1.setBounds(10, 175, 375, 123);

	frame.getContentPane().add(scrollPane_1);

	

	}

	public void createUserFields() {

	lblNewLabel = new JLabel("Order ID");

	lblNewLabel.setBounds(535, 60, 96, 14);

	frame.getContentPane().add(lblNewLabel);

	

	order_id = new JTextField();

	order_id.setBounds(535, 75, 96, 20);

	frame.getContentPane().add(order_id);

	order_id.setColumns(10);

	

	txtrClickcreateEmployee = new JTextArea();

	txtrClickcreateEmployee.setEditable(false);

	txtrClickcreateEmployee.setFont(new Font("Monospaced", Font.PLAIN, 9));

	txtrClickcreateEmployee.setText("Click \"Create Item\"/\"Create Order\" only\r\nafter you've entered all of the new Item or\r\nthe new order's relevant data!\r\n");

	txtrClickcreateEmployee.setBounds(501, 4, 249, 38);

	frame.getContentPane().add(txtrClickcreateEmployee);

	

	lblNewLabel_6 = new JLabel("Order Items");

	lblNewLabel_6.setBounds(20, 8, 102, 14);

	frame.getContentPane().add(lblNewLabel_6);

	

	lblNewLabel_7 = new JLabel("Customer Order Data");

	lblNewLabel_7.setBounds(20, 159, 132, 14);

	frame.getContentPane().add(lblNewLabel_7);

	//Create new order

	btnNewButton_1 = new JButton("Delete Order");

	btnNewButton_1.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	populateOrderTable();

	}

	});

	btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 9));

	btnNewButton_1.setBounds(395, 128, 95, 23);

	frame.getContentPane().add(btnNewButton_1);

	

	//Update order

	btnNewButton_2 = new JButton("Update Status");

	btnNewButton_2.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	updateStatus();

	populateOrderTable();

	}

	});

	

	

	btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 9));

	btnNewButton_2.setBounds(669, 128, 95, 23);

	frame.getContentPane().add(btnNewButton_2);

	

	lblNewLabel_12 = new JLabel("Enter New Order Status");

	lblNewLabel_12.setBounds(533, 116, 126, 14);

	frame.getContentPane().add(lblNewLabel_12);

	

	order_status = new JTextField();

	order_status.setBounds(535, 190, 96, 20);

	frame.getContentPane().add(order_status);

	order_status.setColumns(10);

	

	txtrEnterOnlyordered = new JTextArea();

	txtrEnterOnlyordered.setFont(new Font("Monospaced", Font.PLAIN, 11));

	txtrEnterOnlyordered.setText("Enter only \r\n(Ordered, Shipped, \r\nDelivered, Cancelled)");

	txtrEnterOnlyordered.setBounds(514, 135, 151, 52);

	frame.getContentPane().add(txtrEnterOnlyordered);

	

	btnNewButton = new JButton("Go Back");

	btnNewButton.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	toAdminPage();

	}

	});

	btnNewButton.setBounds(826, 19, 89, 23);

	frame.getContentPane().add(btnNewButton);

	

	btnNewButton_3 = new JButton("Main Menu");

	btnNewButton_3.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	toMainMenu();

	}

	});

	btnNewButton_3.setBounds(826, 53, 89, 23);

	frame.getContentPane().add(btnNewButton_3);

	}

	

	

	

	//Update existing order

	public void updateStatus() {

	try {

	Connection connection = database.connection;

	String query = "UPDATE orders SET orderStatus=? WHERE orderID = ?";

	PreparedStatement stm = connection.prepareStatement(query);

	stm.setString(1, order_status.getText());

	stm.setInt(2, Integer.parseInt(order_id.getText()));

	stm.executeUpdate();

	JOptionPane.showMessageDialog(frame, "Order updated successfully!");

	populateOrderTable();

	}catch(Exception e) {

	JOptionPane.showMessageDialog(frame, "Error creating Order: " + e.getMessage());

	}

	}

	public void toAdminPage() {

	frame.dispose();

	adminPage CBD = new adminPage();

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

	