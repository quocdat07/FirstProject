package Tuan2;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import DAO_UI.Database;
import DAO_UI.Main_UI;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
	private JPasswordField txtPass;
	public String database,user,pass;
	Main_UI mainUI;
	
	public Login() {
		setBounds(0, 0, 1264, 776);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setBounds(196, 5, 872, 138);
		lblNewLabel.setBorder(new LineBorder(Color.BLACK, 3));
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK, 2, true));
		panel.setBounds(196, 146, 872, 630);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("User");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1_1.setBounds(96, 115, 155, 50);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("PassWord");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1_2.setBounds(96, 265, 155, 50);
		panel.add(lblNewLabel_1_2);
		
		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtUser.setColumns(10);
		txtUser.setBounds(261, 119, 429, 43);
		panel.add(txtUser);
		
		txtPass = new JPasswordField();
		txtPass.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtPass.setBounds(261, 269, 429, 43);
		panel.add(txtPass);
		
		JButton btnNewButton = new JButton("Đăng nhập");
		btnNewButton.setBounds(214, 429, 167, 49);
		panel.add(btnNewButton);
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnngK = new JButton("Đăng ký");
		btnngK.setBounds(434, 429, 167, 49);
		panel.add(btnngK);
		btnngK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new DangKy());
                revalidate();
				repaint();
			}
		});
		btnngK.setFont(new Font("Tahoma", Font.PLAIN, 20));
		//Event
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        user = txtUser.getText();
		        pass = String.valueOf(txtPass.getPassword());
		        if (user.equals("") || pass.equals("")) {
		            JOptionPane.showMessageDialog(panel, "Bạn Chưa Nhập Đủ Thông Tin!");
		            return;
		        } else {
		            Database.user = user;
		            Database.pass = pass;
		            try {
						Database.GetConnection();
						if (Database.conn != null) {
			                JOptionPane.showMessageDialog(panel, "Đăng Nhập Thành công!");
			                removeAll();
			                revalidate();
							repaint();
							Main_UI.UserString(user);
			            } else {
			                return;
			            }
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(panel, "Đăng Nhập Thất Bại!");
//						e1.printStackTrace();
					}
		        }
		    }
		});
		
	}
}
