package Tuan2;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import DAO_UI.Database;
import DAO_UI.Main_UI;
import DAO_UI.Tuan3_UI;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.awt.event.ActionEvent;

public class DangKy extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
	private JTextField txtEmail;
	private JTextField txtFullName;
	private JPasswordField txtPass;
	private JPasswordField txtRePass;

	/**
	 * Create the panel.
	 */
	public DangKy() {
		setBounds(0, 0, 1264, 776);
		Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
		setLayout(null);
		JLabel lblNewLabel = new JLabel("Đăng ký");
		lblNewLabel.setBounds(196, 11, 872, 138);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setBorder(borderDen);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(196, 153, 872, 623);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Tên đăng nhập");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(82, 60, 133, 27);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mật khẩu");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(82, 127, 133, 27);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Nhập lại mật khẩu");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(82, 198, 133, 27);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Email");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_3.setBounds(82, 264, 133, 27);
		panel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Họ và tên");
		lblNewLabel_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_4.setBounds(82, 331, 133, 27);
		panel.add(lblNewLabel_1_4);
		
		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtUser.setBounds(225, 59, 539, 32);
		panel.add(txtUser);
		txtUser.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtEmail.setColumns(10);
		txtEmail.setBounds(225, 259, 539, 32);
		panel.add(txtEmail);
		
		txtFullName = new JTextField();
		txtFullName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtFullName.setColumns(10);
		txtFullName.setBounds(225, 326, 539, 32);
		panel.add(txtFullName);
		
		txtPass = new JPasswordField();
		txtPass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPass.setBounds(225, 126, 539, 32);
		panel.add(txtPass);
		
		txtRePass = new JPasswordField();
		txtRePass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtRePass.setBounds(225, 197, 539, 32);
		panel.add(txtRePass);
		
		JButton btnNewButton = new JButton("Đăng ký");
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnNewButton.setBounds(359, 433, 154, 38);
		panel.add(btnNewButton);
		//event
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUser.getText();
		        String password = new String(txtPass.getPassword());
		        String confirmPassword = new String(txtRePass.getPassword());
		        String email = txtEmail.getText();
		        String fullName = txtFullName.getText();
		        
//		         Kiểm tra xem các trường thông tin có được điền đầy đủ không
		        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || fullName.isEmpty()) {
		            JOptionPane.showMessageDialog(DangKy.this, "Vui lòng nhập đầy đủ thông tin!");
		            return;
		        }
		        
		        // Kiểm tra xác nhận mật khẩu
		        if (!password.equals(confirmPassword)) {
		            JOptionPane.showMessageDialog(DangKy.this, "Mật khẩu xác nhận không khớp!");
		            return;
		        }
		        
		        // Tiến hành tạo người dùng mới hoặc lưu thông tin vào cơ sở dữ liệu
		        boolean success = createUser(username, password, email, fullName);
		        
		        if (success == true) {
		            JOptionPane.showMessageDialog(DangKy.this, "Đăng ký thành công!");
		            clearFields();
		            Main_UI.panel.removeAll(); 
					Main_UI.panel.add(new Login());
					Main_UI.panel.revalidate();
					Main_UI.panel.repaint();
		        } else {
		            JOptionPane.showMessageDialog(DangKy.this, "Đăng ký thất bại! Vui lòng thử lại.");
		        }
		    }
		});
	}
	
	private boolean createUser(String username, String password, String email, String fullName) {
	    Database.user = "DAT_ADMIN";
	    Database.pass = "123";

	    try (Connection conn = Database.GetConnection()) {
	        // Kiểm tra xem user có tồn tại hay không
	        try (CallableStatement checkUserStmt = conn.prepareCall("{ ? = call DAT_ADMIN.QuanLyUser.KiemTra_UserTonTai(?) }")) {
	            checkUserStmt.registerOutParameter(1, Types.BOOLEAN);
	            checkUserStmt.setString(2, username);
	            checkUserStmt.execute();
	            
	            boolean userExists = checkUserStmt.getBoolean(1);
	            if (userExists) {
	                JOptionPane.showMessageDialog(null, "Tài khoản đã tồn tại", "Thông báo", JOptionPane.ERROR_MESSAGE);
	                return false;
	            }
	        }

	        // Nếu user không tồn tại, thêm user mới
	        try (
	            CallableStatement addUserStmt = conn.prepareCall("{call DAT_ADMIN.QuanLyUser.Them_User(?, ?)}");
	            CallableStatement addInfoStmt = conn.prepareCall("{call DAT_ADMIN.QuanLyUser.ThemThongTinDangKy(?, ?, ?)}")
	        ) {
	            // Thêm user
	            addUserStmt.setString(1, username);
	            addUserStmt.setString(2, password);
	            addUserStmt.executeUpdate();
	            
	            // Thêm thông tin đăng ký
	            addInfoStmt.setString(1, username);
	            addInfoStmt.setString(2, fullName);
	            addInfoStmt.setString(3, email);
	            addInfoStmt.executeUpdate();
	            
	            JOptionPane.showMessageDialog(null, "Tài khoản được tạo thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Không thể tạo tài khoản", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return false;
	    } finally {
	        Database.CloseConnection();
	        Database.user = "";
	        Database.pass = "";
	    }
	}

	private void clearFields() {
	    txtUser.setText("");
	    txtPass.setText("");
	    txtRePass.setText("");
	    txtEmail.setText("");
	    txtFullName.setText("");
	}
}
