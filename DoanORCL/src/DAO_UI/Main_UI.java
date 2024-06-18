package DAO_UI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import Tuan2.*;
import oracle.jdbc.OracleTypes;

public class Main_UI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Tuan2.Login tuan2 = new Tuan2.Login();
	private static JLabel lblngNhp_1;
	private static JLabel lblTimelg;
	public static JPanel panel;
	private static JLabel lblngNhp;
	private static JLabel lblTun_4;
	private boolean running = true;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_UI frame = new Main_UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main_UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 920);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 51, 1264, 830);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblT2 = new JLabel("Tuần 2");
		
		lblT2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblT2.setHorizontalAlignment(SwingConstants.CENTER);
		lblT2.setBounds(22, 6, 140, 40);
		lblT2.setBorder(borderDen);
		contentPane.add(lblT2);
		
		JLabel lblTun_1 = new JLabel("Tuần 3");
		
		lblTun_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTun_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTun_1.setBounds(172, 6, 140, 40);
		lblTun_1.setBorder(borderDen);
		contentPane.add(lblTun_1);
		
		JLabel lblTun_2 = new JLabel("Tuần 4");
		
		lblTun_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblTun_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTun_2.setBounds(322, 6, 140, 40);
		lblTun_2.setBorder(borderDen);
		contentPane.add(lblTun_2);
		
		lblngNhp_1 = new JLabel("User: ");
		
		lblngNhp_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblngNhp_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblngNhp_1.setBounds(964, 6, 140, 40);
		lblngNhp_1.setBorder(borderDen);
		lblngNhp_1.setVisible(false);
		contentPane.add(lblngNhp_1);
		
		lblngNhp = new JLabel("Đăng nhập");
		
		lblngNhp.setHorizontalAlignment(SwingConstants.CENTER);
		lblngNhp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblngNhp.setBounds(1114, 6, 140, 40);
		lblngNhp.setBorder(borderDen);
		contentPane.add(lblngNhp);
		
		lblTun_4 = new JLabel("Đăng xuất");
		
		lblTun_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblTun_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTun_4.setBounds(1114, 6, 140, 40);
		lblTun_4.setBorder(borderDen);
		lblTun_4.setVisible(false);
		contentPane.add(lblTun_4);
		
		lblTimelg = new JLabel("Time_LG: ");
		lblTimelg.setBounds(814, 6, 140, 40);
		contentPane.add(lblTimelg);
		lblTimelg.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimelg.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTimelg.setBorder(borderDen);
		lblTimelg.setVisible(false);
		
		//Event Buttons
		lblngNhp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel.removeAll();
				panel.add(new Tuan2.Login());
				panel.revalidate();
				panel.repaint();
				lblngNhp.setVisible(false);
			}
		});
		lblT2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel.removeAll(); 
				panel.add(new Tuan2_UI());
				panel.revalidate();
				panel.repaint();
			}
		});
		lblTun_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel.removeAll(); 
				panel.add(new Tuan3_UI());
				panel.revalidate();
				panel.repaint();
			}
		});
		lblngNhp_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(lblTun_4.isVisible() == true) {
					lblTun_4.setVisible(false);
				}	
				if(lblTun_4.isVisible() == false) {
					lblTun_4.setVisible(true);
				}
			}
		});
		lblTun_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel.removeAll(); 
				panel.add(new Tuan4_UI());
				panel.revalidate();
				panel.repaint();
			}
		});
		lblTun_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DangXuat();
			}
		});
//        new Thread(new ConnectionChecker()).start();
        
	}
	public static void UserString(String userName) {
	    String time_lg = null;
	    String name = userName.toUpperCase();
	    if (Database.conn != null) {
	        try (Connection connection = Database.GetConnection();
	             CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.my_security_pkg.ShowUserLast_LG(?, ?)}")) {

	            // Register the OUT parameter for the cursor
	            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
	            // Set the IN parameter
	            callableStatement.setString(2, name);

	            // Execute the stored procedure
	            callableStatement.execute();

	            // Retrieve the ResultSet from the OUT parameter
	            try (ResultSet rs = (ResultSet) callableStatement.getObject(1)) {
	                while (rs.next()) {
	                    time_lg = rs.getString("LastLogin"); // Assuming logon_time is of a string-compatible type
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error: Unable to fetch data from the database.");
	        }
	    } else {
	    	JOptionPane.showMessageDialog(null, "Chưa đăng nhập");
	    }

	    // Assuming lblngNhp_1 and lblTimelg are components in your UI
	    lblngNhp_1.setText("User: " + userName);
	    lblTimelg.setText(time_lg);
	    lblngNhp_1.setVisible(true);
	    lblTimelg.setVisible(true);
	}
	private void DangXuat() {
		Database.CloseConnection();
		lblngNhp.setVisible(true);
		lblTimelg.setVisible(false);
		lblngNhp_1.setVisible(false);
		lblTun_4.setVisible(false);
		panel.removeAll();
		panel.add(new Tuan2.Login());
		panel.revalidate();
		panel.repaint();
		lblngNhp.setVisible(false);
	}
}
