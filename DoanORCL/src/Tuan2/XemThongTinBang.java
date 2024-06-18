package Tuan2;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePickerImpl;

import DAO_UI.Database;
import oracle.jdbc.OracleTypes;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XemThongTinBang extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField txt_1;
	private JTextField txt_2;
	private JTextField txt_3;
	private JTextField txt_4;
	private JTextField txt_5;
	private JTextField txt_6;
	private JTextField txt_7;
	private JTextField txt_8;
	private JComboBox comboBox;
	private JDatePicker txt_5_1;
	/**
	 * Create the panel.
	 */
	public XemThongTinBang() {
		setBounds(0, 0, 1264, 776);
		setLayout(null);
		Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		scrollPane.setBounds(95, 64, 1073, 503);
		add(scrollPane);
		
		table = new JTable();
		
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(419, 11, 425, 42);
		lblNewLabel.setVisible(false);
		lblNewLabel.setBorder(borderDen);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Table:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(466, 578, 84, 29);
		add(lblNewLabel_1);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Đọc giả", "Sách", "Phiếu mượn", "Phiếu trả", "Tài khoản"}));
		comboBox.setBounds(577, 578, 371, 29);
		add(comboBox);
		
		txt_1 = new JTextField();
		txt_1.setEnabled(false);
		txt_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_1.setBounds(133, 628, 191, 29);
		txt_1.setVisible(false);
		add(txt_1);
		txt_1.setColumns(10);
		
		txt_2 = new JTextField();
		txt_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_2.setColumns(10);
		txt_2.setBounds(133, 695, 191, 29);
		txt_2.setVisible(false);
		add(txt_2);
		
		txt_3 = new JTextField();
		txt_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_3.setColumns(10);
		txt_3.setBounds(445, 628, 191, 29);
		txt_3.setVisible(false);
		add(txt_3);

		txt_4 = new JTextField();
		txt_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_4.setColumns(10);
		txt_4.setBounds(445, 695, 191, 29);
		txt_4.setVisible(false);
		add(txt_4);
		
		txt_5 = new JTextField();
		txt_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_5.setColumns(10);
		txt_5.setBounds(766, 628, 191, 29);
		txt_5.setVisible(false);
		add(txt_5);
		
		txt_6 = new JTextField();
		txt_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_6.setColumns(10);
		txt_6.setBounds(766, 695, 191, 29);
		txt_6.setVisible(false);
		add(txt_6);
		
		txt_7 = new JTextField();
		txt_7.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_7.setColumns(10);
		txt_7.setBounds(1052, 628, 191, 29);
		txt_7.setVisible(false);
		add(txt_7);
		
		txt_8 = new JTextField();
		txt_8.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_8.setColumns(10);
		txt_8.setBounds(1052, 695, 191, 29);
		txt_8.setVisible(false);
		add(txt_8);
		
		JLabel lbl_1 = new JLabel("New label");
		lbl_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_1.setBounds(10, 628, 113, 28);
		lbl_1.setVisible(false);
		add(lbl_1);
		
		JLabel lbl_2 = new JLabel("New label");
		lbl_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_2.setBounds(10, 696, 113, 28);
		lbl_2.setVisible(false);
		add(lbl_2);
		
		JLabel lbl_3 = new JLabel("New label");
		lbl_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_3.setBounds(334, 628, 101, 28);
		lbl_3.setVisible(false);
		add(lbl_3);
		
		JLabel lbl_4 = new JLabel("New label");
		lbl_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_4.setBounds(334, 695, 101, 28);
		lbl_4.setVisible(false);
		add(lbl_4);
		
		JLabel lbl_5 = new JLabel("New label");
		lbl_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_5.setBounds(646, 629, 110, 28);
		lbl_5.setVisible(false);
		add(lbl_5);
		
		JLabel lbl_6 = new JLabel("New label");
		lbl_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_6.setBounds(646, 696, 110, 28);
		lbl_6.setVisible(false);
		add(lbl_6);
		
		JLabel lbl_7 = new JLabel("New label");
		lbl_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_7.setBounds(967, 628, 75, 28);
		lbl_7.setVisible(false);
		add(lbl_7);
		
		JLabel lbl_8 = new JLabel("New label");
		lbl_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_8.setBounds(967, 695, 75, 28);
		lbl_8.setVisible(false);
		add(lbl_8);
		
		JButton btnThm = new JButton("Thêm");
		
		btnThm.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnThm.setBounds(95, 23, 95, 30);
		btnThm.setVisible(false);
		add(btnThm);
		
		JButton btnXa = new JButton("Xóa");
		
		btnXa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnXa.setBounds(200, 23, 95, 30);
		btnXa.setVisible(false);
		add(btnXa);
		
		JButton btnSa = new JButton("Sửa");
		
		btnSa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSa.setBounds(305, 23, 95, 30);
		btnSa.setVisible(false);
		add(btnSa);
		
		JButton btnNewButton = new JButton("Thêm ảnh");
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(1024, 25, 150, 30);
		add(btnNewButton);
		
		//event
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setDialogTitle("Choose Image");
		        int userSelection = fileChooser.showSaveDialog(null);

		        if (userSelection == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = fileChooser.getSelectedFile();
		            String selectedPath = selectedFile.getAbsolutePath();
		            String selectedID = txt_1.getText();
		            try {
		                updateBookImage(selectedID, selectedFile);
		                System.out.println("Image updated successfully for book ID: " + selectedID);
		            } catch (SQLException | IOException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Error: Unable to update image for book ID: " + selectedID);
		            }
		        }
		    }
		});
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = comboBox.getSelectedItem().toString();
				switch (selectedItem) {
				case "Đọc giả": {
					btnSa.setVisible(true);
					btnThm.setVisible(true);
					btnXa.setVisible(true);
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);;
					showDataDocGia();
					lbl_1.setVisible(true);
					txt_1.setVisible(true);
					txt_1.setEnabled(false);
					lbl_1.setText("Mã đọc giả");
					
					lbl_2.setVisible(true);
					txt_2.setVisible(true);
					txt_2.setEnabled(false);
					lbl_2.setText("Tên đăng nhập");
					
					lbl_3.setVisible(true);
					txt_3.setVisible(true);
					lbl_3.setText("Họ tên");
					
					lbl_4.setVisible(true);
					txt_4.setVisible(true);
					lbl_4.setText("Giới tính");
					lbl_5.setVisible(true);
					txt_5.setVisible(true);
					lbl_5.setText("Năm sinh");
					lbl_6.setVisible(true);
					txt_6.setVisible(true);
					lbl_6.setText("Địa chỉ");
					lbl_7.setVisible(false);
					txt_7.setVisible(false);
					lbl_8.setVisible(false);
					txt_8.setVisible(false);
					btnXa.setVisible(true);
					btnThm.setVisible(false);
					btnSa.setVisible(true);
					break;	
				}
				case "Sách":{
					btnSa.setVisible(true);
					btnThm.setVisible(true);
					btnXa.setVisible(true);
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					showDataSach();
					lbl_1.setVisible(true);
					txt_1.setVisible(true);
					txt_1.setEnabled(false);
					lbl_1.setText("Mã sách");
					lbl_2.setVisible(true);
					txt_2.setVisible(true);
					txt_2.setEnabled(true);
					lbl_2.setText("Tên sách");
					lbl_3.setVisible(true);
					txt_3.setVisible(true);
					lbl_3.setText("Tác giả");
					lbl_4.setVisible(true);
					txt_4.setVisible(true);
					lbl_4.setText("Thể loại");
					lbl_5.setVisible(true);
					txt_5.setVisible(true);
					lbl_5.setText("Nhà xuất bản");
					lbl_6.setVisible(true);
					txt_6.setVisible(true);
					lbl_6.setText("Giá sách");
					lbl_7.setVisible(true);
					txt_7.setVisible(true);
					lbl_7.setText("Số lượng");
					lbl_8.setVisible(true);
					txt_8.setVisible(true);
					lbl_8.setText("Tình trạng");
					btnXa.setVisible(true);
					btnThm.setVisible(true);
					btnSa.setVisible(true);
					break;
				}
				case "Phiếu mượn":{
					btnSa.setVisible(true);
					btnThm.setVisible(true);
					btnXa.setVisible(true);
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					showDataPhieuMuon();
					lbl_1.setVisible(true);
					txt_1.setVisible(true);
					txt_1.setEnabled(false);
					lbl_1.setText("Mã phiếu");
					lbl_2.setVisible(true);
					txt_2.setVisible(true);
					txt_2.setEnabled(true);
					lbl_2.setText("Mã đọc giả");
					lbl_3.setVisible(true);
					txt_3.setVisible(true);
					lbl_3.setText("Mã sách");
					lbl_4.setVisible(true);
					txt_4.setVisible(true);
					lbl_4.setText("Ngày mượn");
					lbl_5.setVisible(true);
					txt_5.setVisible(true);
					lbl_5.setText("Ngày phải trả");
					lbl_6.setVisible(false);
					txt_6.setVisible(false);
					lbl_7.setVisible(false);
					txt_7.setVisible(false);
					lbl_8.setVisible(false);
					txt_8.setVisible(false);
					btnXa.setVisible(true);
					btnThm.setVisible(true);
					btnSa.setVisible(true);
					break;
				}
				case "Phiếu trả": {
					btnSa.setVisible(true);
					btnThm.setVisible(true);
					btnXa.setVisible(true);
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					showDataPhieuTra();
					lbl_1.setVisible(true);
					txt_1.setVisible(true);
					txt_1.setEnabled(false);
					lbl_1.setText("Mã phiếu");
					lbl_2.setVisible(true);
					txt_2.setVisible(true);
					txt_2.setEnabled(true);
					lbl_2.setText("Mã đọc giả");
					lbl_3.setVisible(true);
					txt_3.setVisible(true);
					lbl_3.setText("Mã sách");
					lbl_4.setVisible(true);
					txt_4.setVisible(true);
					lbl_4.setText("Ngày trả");
					lbl_5.setVisible(false);
					txt_5.setVisible(false);
					lbl_6.setVisible(false);
					txt_6.setVisible(false);
					lbl_7.setVisible(false);
					txt_7.setVisible(false);
					lbl_8.setVisible(false);
					txt_8.setVisible(false);
					btnXa.setVisible(true);
					btnThm.setVisible(true);
					btnSa.setVisible(true);
					break;
				}
				case "Tài khoản":{
					btnSa.setVisible(true);
					btnThm.setVisible(true);
					btnXa.setVisible(true);
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					showDataTaiKhoan();
					lbl_1.setVisible(true);
					txt_1.setVisible(true);
					txt_1.setEnabled(false);
					lbl_1.setText("Mã đọc giả");
					lbl_2.setVisible(true);
					txt_2.setVisible(true);
					txt_2.setEnabled(false);
					lbl_2.setText("Tên đăng nhập");
					lbl_3.setVisible(true);
					txt_3.setVisible(true);
					lbl_3.setText("Họ tên");
					lbl_4.setVisible(true);
					txt_4.setVisible(true);
					lbl_4.setText("email");
					lbl_5.setVisible(false);
					txt_5.setVisible(false);
					lbl_6.setVisible(false);
					txt_6.setVisible(false);
					lbl_7.setVisible(false);
					txt_7.setVisible(false);
					lbl_8.setVisible(false);
					txt_8.setVisible(false);
					btnXa.setVisible(false);
					btnThm.setVisible(false);
					btnSa.setVisible(false);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + selectedItem);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String TableName = comboBox.getSelectedItem().toString();
				int selectedRow = table.getSelectedRow();
				switch (TableName) {
				case "Đọc giả":
					if (selectedRow != -1) {
	                    Object MaDocGia = table.getValueAt(selectedRow, 0);
	                    txt_1.setText(MaDocGia.toString().trim());
	                    Object TenDangNhap = table.getValueAt(selectedRow, 1);
	                    txt_2.setText(TenDangNhap.toString().trim());
	                    Object HoTen = table.getValueAt(selectedRow, 2);
	                    txt_3.setText(HoTen.toString().trim());
	                    Object GioiTinh = table.getValueAt(selectedRow, 3);
	                    txt_4.setText(GioiTinh.toString().trim());
	                    Object NamSinh = table.getValueAt(selectedRow, 4);
	                    txt_5.setText(NamSinh.toString().trim());
	                    Object DiaChi = table.getValueAt(selectedRow, 5);
	                    txt_6.setText(DiaChi.toString().trim());
	                }
					break;
				case "Sách":{
					if (selectedRow != -1) {
						Object MASACH = table.getValueAt(selectedRow, 0);
	                    txt_1.setText(MASACH.toString().trim());
	                    Object TENSACH = table.getValueAt(selectedRow, 1);
	                    txt_2.setText(TENSACH.toString().trim());
	                    Object TACGIA = table.getValueAt(selectedRow, 2);
	                    txt_3.setText(TACGIA.toString().trim());
	                    Object THELOAI = table.getValueAt(selectedRow, 3);
	                    txt_4.setText(THELOAI.toString().trim());
	                    Object NHAXUATBAN = table.getValueAt(selectedRow, 4);
	                    txt_5.setText(NHAXUATBAN.toString().trim());
	                    Object GIASACH = table.getValueAt(selectedRow, 5);
	                    txt_6.setText(GIASACH.toString().trim());
	                    Object SOLUONG = table.getValueAt(selectedRow, 6);
	                    txt_7.setText(SOLUONG.toString().trim());
	                    Object TINHTRANG = table.getValueAt(selectedRow, 7);
	                    txt_8.setText(TINHTRANG.toString().trim());
	                }
					break;
				}
				case "Phiếu mượn":{
					if (selectedRow != -1) {
						Object MaPhieu = table.getValueAt(selectedRow, 0);
	                    txt_1.setText(MaPhieu.toString().trim());
	                    Object MaDocGia = table.getValueAt(selectedRow, 1);
	                    txt_2.setText(MaDocGia.toString().trim());
	                    Object MaSach = table.getValueAt(selectedRow, 2);
	                    txt_3.setText(MaSach.toString().trim());
	                    Object NgayMuon = table.getValueAt(selectedRow, 3);
	                    txt_4.setText(NgayMuon.toString().trim());
	                    Object NgayPhaiTra = table.getValueAt(selectedRow, 4);
	                    txt_5.setText(NgayPhaiTra.toString().trim());
	                }
					break;
				}
				case "Phiếu trả": {
					if (selectedRow != -1) {
						Object MaPhieu = table.getValueAt(selectedRow, 0);
	                    txt_1.setText(MaPhieu.toString().trim());
	                    Object MaDocGia = table.getValueAt(selectedRow, 1);
	                    txt_2.setText(MaDocGia.toString().trim());
	                    Object MaSach = table.getValueAt(selectedRow, 2);
	                    txt_3.setText(MaSach.toString().trim());
	                    Object NgayTra = table.getValueAt(selectedRow, 3);
	                    txt_4.setText(NgayTra.toString().trim());
	                }
					break;
				}
				case "Tài khoản":{
					if (selectedRow != -1) {
						Object MaDocGia = table.getValueAt(selectedRow, 0);
	                    txt_1.setText(MaDocGia.toString().trim());
	                    Object TenDangNhap = table.getValueAt(selectedRow, 1);
	                    txt_2.setText(TenDangNhap.toString().trim());
	                    Object HoTen = table.getValueAt(selectedRow, 2);
	                    txt_3.setText(HoTen.toString().trim());
	                    Object email = table.getValueAt(selectedRow, 3);
	                    txt_4.setText(email.toString().trim());
	                }
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + TableName);
				}
			}
		});
		btnThm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ThemData();
			}
		});
		btnXa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xoaDuLieu();
			}
		});
		btnSa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SuaData();
			}
		});
	}
	private void showDataSach() {
		if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.showSach(?)}")){
            	callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            	callableStatement.execute();
            	ResultSet rs = (ResultSet) callableStatement.getObject(1);
            	populateTable(rs);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Please log in first.");
	    }
	}
	private void showDataDocGia() {
		if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.showDOCGIA(?)}")){
            	callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            	callableStatement.execute();
            	ResultSet rs = (ResultSet) callableStatement.getObject(1);
            	populateTable(rs);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Please log in first.");
	    }
	}
	private void showDataPhieuMuon() {
		if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.ShowPhieuMuon(?)}")){
            	callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            	callableStatement.execute();
            	ResultSet rs = (ResultSet) callableStatement.getObject(1);
            	populateTable(rs);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Please log in first.");
	    }
	}
	private void showDataPhieuTra() {
		if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.ShowPhieuTra(?)}")){
            	callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            	callableStatement.execute();
            	ResultSet rs = (ResultSet) callableStatement.getObject(1);
            	populateTable(rs);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Please log in first.");
	    }
	}
	private void showDataTaiKhoan() {
		if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.ShowACCOUNT(?)}")){
            	callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            	callableStatement.execute();
            	ResultSet rs = (ResultSet) callableStatement.getObject(1);
            	populateTable(rs);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Please log in first.");
	    }
	}
	
	private void ThemData() {
		String TableName = comboBox.getSelectedItem().toString();
		String txt_s1 = txt_1.getText();
		String txt_s2 = txt_2.getText();
		String txt_s3 = txt_3.getText();
		String txt_s4 = txt_4.getText();
		String txt_s5 = txt_5.getText();
		String txt_s6 = txt_6.getText();
		String txt_s7 = txt_7.getText();
		String txt_s8 = txt_8.getText();
		if(Database.conn != null) {
			switch (TableName) {
			case "Sách":{
				try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{call DAT_ADMIN.insertSach(?, ?, ?, ?, ?, ?, ?, ?)}")){
						callSM.setString(1, txt_s1);  
	                	callSM.setString(2, txt_s2);  
	                	callSM.setString(3, txt_s3);    
	                	callSM.setString(4, txt_s4);  
	                	callSM.setString(5, txt_s5);
	                	callSM.setString(6, txt_s6);
	                	callSM.setString(7, txt_s7);
	                	callSM.setString(8, txt_s8);
	                	callSM.execute();
	                	showDataSach();
	                	JOptionPane.showMessageDialog(this, "Thêm thành công.");
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                }
				break;
			}
			case "Phiếu mượn":{
				try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{? = call DAT_ADMIN.ThemPhieuMuon(?, ?, ?, ?, ?)}")){
						callSM.registerOutParameter(1, Types.BOOLEAN);
						callSM.setString(2, txt_s1);
	                	callSM.setString(3, txt_s2);
	                	callSM.setString(4, txt_s3);
	                	
	                	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
					    Date date = sdf.parse(txt_s4);
					    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					    
	                	callSM.setDate(5, sqlDate);
	                	
	                	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/YYYY");
					    Date date1 = sdf1.parse(txt_s5);
					    java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
	                	callSM.setDate(6, sqlDate1);
	                	callSM.execute();
	                	boolean chekExists = callSM.getBoolean(1);
	                	if(chekExists == true) {
	                	showDataPhieuMuon();
	                	JOptionPane.showMessageDialog(this, "Thêm thành công.");
	                	}
	                	else {
	                		JOptionPane.showMessageDialog(this, "Thêm thất bại công.");
	                		return;
	                	}
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
					} catch (ParseException e) {
					    e.printStackTrace();
					    JOptionPane.showMessageDialog(this, "Error: Invalid date format.");
					}
				break;
			}
			case "Phiếu trả": {
				try (Connection conn = Database.GetConnection();
					     CallableStatement callSM = conn.prepareCall("{? = call DAT_ADMIN.ThemPhieuTra(?, ?, ?, ?)}")) {
					    callSM.registerOutParameter(1, Types.BOOLEAN);
					    callSM.setString(2, txt_s1);
					    callSM.setString(3, txt_s2);
					    callSM.setString(4, txt_s3);
					    
					    // Convert Java String to SQL Date
					    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
					    Date date = sdf.parse(txt_s4);
					    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					    
					    callSM.setDate(5, sqlDate);
					    
					    callSM.execute();
					    
					    boolean checkExists = callSM.getBoolean(1);
					    if (checkExists) {
					        showDataPhieuTra();
					        JOptionPane.showMessageDialog(this, "Thêm thành công.");
					    } else {
					        JOptionPane.showMessageDialog(this, "Thêm thất bại");
					        return;
					    }
					    JOptionPane.showMessageDialog(this, "Thêm thành công.");
					} catch (SQLException e) {
					    e.printStackTrace();
					    JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
					} catch (ParseException e) {
					    e.printStackTrace();
					    JOptionPane.showMessageDialog(this, "Error: Invalid date format.");
					}
				}
			}
		}
	}
	
	private void xoaDuLieu() {
		String TableName = comboBox.getSelectedItem().toString();
		String txt_s1 = txt_1.getText();
		if(Database.conn != null) {
			switch (TableName) {
			case "Đọc giả":
	                try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{call DAT_ADMIN.deleteDOCGIA(?)}")){
	                	callSM.setString(1, txt_s1);
	                	callSM.execute();
	                	showDataDocGia();
	                	JOptionPane.showMessageDialog(this, "Xóa thành công.");
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                }
				break;
			case "Sách":{
				System.out.println(txt_s1);
				try(Connection conn = Database.GetConnection();
	                CallableStatement callSM = conn.prepareCall("{? = call DAT_ADMIN.delete_sach(?)}")){
						callSM.registerOutParameter(1, Types.BOOLEAN);
	                	callSM.setString(2, txt_s1);
	                	callSM.execute();
	                	boolean checkResult = callSM.getBoolean(1);
	                	System.out.println(callSM);
	                	if(checkResult) {
	                		showDataSach();
		                	JOptionPane.showMessageDialog(this, "Xóa thành công.");
	                	}else {
	                		JOptionPane.showMessageDialog(this, "Xóa thất bại.");
	                	}
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                }
				break;
			}
			case "Phiếu mượn":{
				try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{call DAT_ADMIN.XoaPhieuMuon(?)}")){
	                	callSM.setString(1, txt_s1);
	                	callSM.execute();
	                	showDataPhieuMuon();
	                	JOptionPane.showMessageDialog(this, "Xóa thành công.");
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                }
				break;
			}
			case "Phiếu trả": {
				try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{call DAT_ADMIN.XoaPhieuTra(?)}")){
	                	callSM.setString(1, txt_s1);
	                	callSM.execute();
	                	showDataPhieuTra();
	                	JOptionPane.showMessageDialog(this, "Xóa thành công.");
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                }
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + TableName);
			}
		} else {
            JOptionPane.showMessageDialog(this, "Please log in first.");
        }
	}
	
	private void SuaData() {
		String TableName = comboBox.getSelectedItem().toString();
		String txt_s1 = txt_1.getText();
		String txt_s2 = txt_2.getText();
		String txt_s3 = txt_3.getText();
		String txt_s4 = txt_4.getText();
		String txt_s5 = txt_5.getText();
		String txt_s6 = txt_6.getText();
		String txt_s7 = txt_7.getText();
		String txt_s8 = txt_8.getText();
		if(Database.conn != null) {
			switch (TableName) {
			case "Đọc giả":
	                try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{call DAT_ADMIN.updateDOCGIA(?, ?, ?, ?, ?, ?)}")){
	                	callSM.setString(1, txt_s1);
	                	callSM.setString(2, txt_s2);
	                	callSM.setString(3, txt_s3);
	                	callSM.setString(4, txt_s4);
	                	
	                	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/YYYY");
					    Date date1 = sdf1.parse(txt_s5);
					    java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
	                	callSM.setDate(5, sqlDate1);
	                	
	                	callSM.setString(6, txt_s6);
	                	callSM.execute();
	                	showDataDocGia();
	                	JOptionPane.showMessageDialog(this, "Sửa thành công.");
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                } catch (ParseException e) {
					    e.printStackTrace();
					    JOptionPane.showMessageDialog(this, "Error: Invalid date format.");
					}
				break;
			case "Sách":{
				try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{call DAT_ADMIN.update_sach(?, ?, ?, ?, ?, ?, ?, ?)}")){
	                	callSM.setString(1, txt_s1); 
	                	callSM.setString(2, txt_s2);
	                	callSM.setString(3, txt_s3);
	                	callSM.setString(4, txt_s4);
	                	callSM.setString(5, txt_s5);
	                	callSM.setString(6, txt_s6);
	                	callSM.setString(7, txt_s7);
	                	callSM.setString(8, txt_s8);
	                	callSM.execute();
	                	showDataSach();
	                	JOptionPane.showMessageDialog(this, "Sửa sách thành công.");
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                }
				break;
			}
			case "Phiếu mượn":{
				try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{call DAT_ADMIN.SuaPhieuMuon(?, ?, ?, ?, ?)}")){
	                	callSM.setString(1, txt_s1);
	                	callSM.setString(2, txt_s2);
	                	callSM.setString(3, txt_s3);
	                	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/YYYY");
					    Date date1 = sdf1.parse(txt_s4);
					    java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
	                	callSM.setDate(4, sqlDate1);
	                	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
					    Date date = sdf.parse(txt_s5);
					    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	                	callSM.setDate(5, sqlDate);
	                	callSM.execute();
	                	showDataPhieuMuon();
	                	JOptionPane.showMessageDialog(this, "Sửa thành công.");
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                } catch (ParseException e) {
					    e.printStackTrace();
					    JOptionPane.showMessageDialog(this, "Error: Invalid date format.");
					}
				break;
			}
			case "Phiếu trả": {
				try(Connection conn = Database.GetConnection();
	                	CallableStatement callSM = conn.prepareCall("{call DAT_ADMIN.SuaPhieuTra(?, ?, ?, ?)}")){
	                	callSM.setString(1, txt_s1);
	                	callSM.setString(2, txt_s2);
	                	callSM.setString(3, txt_s3);
	                	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/YYYY");
	                	Date date1 = sdf1.parse(txt_s4);
					    java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
	                	callSM.setDate(4, sqlDate1);
	                	callSM.execute();
	                	showDataPhieuTra();
	                	JOptionPane.showMessageDialog(this, "Sửa thành công.");
	                }catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	                } catch (ParseException e) {
					    e.printStackTrace();
					    JOptionPane.showMessageDialog(this, "Error: Invalid date format.");
					}
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + TableName);
			}
		} else {
            JOptionPane.showMessageDialog(this, "Please log in first.");
        }
	}
	
	private static void updateBookImage(String bookId, File imageFile) throws SQLException, IOException {
	    String sql = "{call UpdateBookImage(?, ?)}";

	    try (Connection conn = Database.GetConnection();
	         CallableStatement stmt = conn.prepareCall(sql);
	         FileInputStream fis = new FileInputStream(imageFile)) {
	        // Set input parameters
	        stmt.setString(1, bookId);
	        stmt.setString(2, imageFile.getName());

	        // Execute the stored procedure
	        stmt.execute();
	    } catch (SQLException | IOException e) {
	        throw e;
	    }
	}
	
	private void populateTable(ResultSet resultSet) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        // Thêm các cột vào model dựa trên metadata của ResultSet
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            model.addColumn(resultSet.getMetaData().getColumnName(i));
        }
        // Thêm dữ liệu từ ResultSet vào model
        while (resultSet.next()) {
            Object[] rowData = new Object[resultSet.getMetaData().getColumnCount()];
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            model.addRow(rowData);
        }
        // Hiển thị dữ liệu trên bảng
        table.setModel(model);
    }
}
