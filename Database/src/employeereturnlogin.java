
import java.sql.*;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
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

public class employeereturnlogin {

	public JFrame frame;
	private JTextField user_name;
	private JTextField user_pass;
	private static String tfuser;
	private static String tfpass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeereturnlogin window = new employeereturnlogin();
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
	public employeereturnlogin() {
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
		
		createLoginBtn();
		
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(190, 30, 70, 14);
		frame.getContentPane().add(lblNewLabel);
		
		user_name = new JTextField();
		user_name.setBounds(174, 55, 86, 20);
		frame.getContentPane().add(user_name);
		user_name.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(190, 104, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		user_pass = new JTextField();
		user_pass.setBounds(174, 144, 86, 20);
		frame.getContentPane().add(user_pass);
		user_pass.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Employee Login");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(163, 11, 100, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		
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
	
	public void toEmployeePage() {
		frame.dispose();
		employeePage CBD = new employeePage();
		CBD.initialize();
		CBD.frame.setVisible(true);
	}
	

	private void getUser() {
		tfuser = user_name.getText();
	}
	private void getPass() {
		tfpass = user_pass.getText();
	}
	
	public static String getUserValue() {
		return tfuser;
	}
	public static String getPassValue() {
		return tfpass;
	}
	
	
	public void login() {
		getUser();
		getPass();
		try {
			Connection connection = database.connection;
			String query = "SELECT * FROM employees WHERE EmployeeUsername = ? AND EmployeePassword = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, user_name.getText());
			stm.setString(2, user_pass.getText());
			ResultSet rs = stm.executeQuery();
			
			
			if(rs.next()) {
				JOptionPane.showMessageDialog(frame,  "Login Successful!");
				toEmployeePage();
			}else {
				JOptionPane.showMessageDialog(frame,  "Invalid username or password");
			}
		}catch(Exception e) {
			 JOptionPane.showMessageDialog(frame, "Error connecting to database: " + e.getMessage());
		}
	}
	

	
	public void createLoginBtn() {
		JButton login_btn = new JButton("Login");
		login_btn.setBounds(174, 198, 89, 23);
		frame.getContentPane().add(login_btn);
		login_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
	}
	
	
	
	
	

}
