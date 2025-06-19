

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

public class Login {

	private JFrame frame;
	private JTextField user_name;
	private JTextField user_pass;
	private JTextField user_firstname;
	private JTextField user_address;
	private JTextField user_lastname;
	private String selectedAccountType = "Customer"; // Default value
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		database.connect();
		setupClosingDBConnection();
		
	
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		setLookAndFeel();
		
		createAddUserButton();
		createLoginButton();
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Customer", "Employee", "Admin"}));
		comboBox.setBounds(27, 140, 89, 22);
		frame.getContentPane().add(comboBox);

		

		comboBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
		        String selected = (String) comboBox.getSelectedItem();
		        selectedAccountType = selected;
		    }
		});

		
		JLabel lblNewLabel = new JLabel("Create Account");
		lblNewLabel.setBounds(169, 11, 75, 29);
		frame.getContentPane().add(lblNewLabel);
		
		user_name = new JTextField();
		user_name.setBounds(159, 66, 86, 20);
		frame.getContentPane().add(user_name);
		user_name.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(181, 53, 64, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(181, 116, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		user_pass = new JTextField();
		user_pass.setBounds(159, 141, 86, 20);
		frame.getContentPane().add(user_pass);
		user_pass.setColumns(10);
		
		
		
		
		
		
		JLabel lblNewLabel_3 = new JLabel("click if you alr have acc");
		lblNewLabel_3.setBounds(33, 231, 113, 19);
		frame.getContentPane().add(lblNewLabel_3);
		
		user_firstname = new JTextField();
		user_firstname.setBounds(305, 66, 86, 20);
		frame.getContentPane().add(user_firstname);
		user_firstname.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("First name");
		lblNewLabel_4.setBounds(322, 53, 69, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		user_address = new JTextField();
		user_address.setBounds(305, 185, 86, 20);
		frame.getContentPane().add(user_address);
		user_address.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Address");
		lblNewLabel_5.setBounds(322, 161, 46, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Last name");
		lblNewLabel_6.setBounds(322, 116, 69, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		user_lastname = new JTextField();
		user_lastname.setBounds(305, 141, 86, 20);
		frame.getContentPane().add(user_lastname);
		user_lastname.setColumns(10);
		
		
		JLabel lblNewLabel_2_1 = new JLabel("Account Type");
		lblNewLabel_2_1.setBounds(33, 116, 74, 14);
		frame.getContentPane().add(lblNewLabel_2_1);
	}
	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            try { database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
				} catch (SQLException e) { e.printStackTrace(); }
	        }
	    }, "Shutdown-thread"));
	}

	
	public void addCustomer() {
		try {
			Connection connection = database.connection;
			String query = "INSERT INTO customers(username, password, CustomerFirstName, CustomerLastName, CustomerEmail) VALUES ( ?, ?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, user_name.getText());
			stm.setString(2, user_pass.getText());
			stm.setString(3, user_firstname.getText());
			stm.setString(4, user_lastname.getText());
			stm.setString(5, user_address.getText());
			stm.executeUpdate();
			JOptionPane.showMessageDialog(frame, "User created successfully!");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error creating user: " + e.getMessage());
		}
	}
	
	public void createAddUserButton() {
		JButton create_user = new JButton("Create Account");
		create_user.setBounds(144, 184, 117, 23);
		frame.getContentPane().add(create_user);
		create_user.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addCustomer();
			}
		});
	}
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e) {}
	}
	
	public void toLogin() {
		frame.dispose();
		returnlogin CBD = new returnlogin();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	
	public void createLoginButton() {
		JButton to_login = new JButton("Login");
		to_login.setBounds(156, 227, 89, 23);
		frame.getContentPane().add(to_login);
		to_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toLogin();
			}
		});
	}
}
