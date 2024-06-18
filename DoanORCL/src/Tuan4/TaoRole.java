package Tuan4;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO_UI.Database;
import oracle.jdbc.OracleTypes;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class TaoRole extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField txt_RoleName;
	JComboBox<String> comboBox;
	private JTable table_1;
	/**
	 * Create the panel.
	 */
	public TaoRole() {
		setBounds(0, 0, 1264, 776);
		setLayout(null);
		Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 329, 661);
		add(scrollPane);
		
		table = new JTable();
		
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("ROLE");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(10, 11, 329, 31);
		lblNewLabel.setBorder(borderDen);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(349, 56, 313, 111);
		add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Tạo");
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(214, 54, 89, 31);
		panel.add(btnNewButton);
		
		JButton btnXa = new JButton("Xóa");
		
		btnXa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnXa.setBounds(117, 54, 89, 31);
		panel.add(btnXa);
		
		txt_RoleName = new JTextField();
		txt_RoleName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txt_RoleName.setColumns(10);
		txt_RoleName.setBounds(10, 11, 293, 32);
		panel.add(txt_RoleName);
		
		JLabel lblNewLabel_1_1 = new JLabel("Cấp quyền");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_1_1.setBounds(688, 6, 329, 41);
		lblNewLabel_1_1.setBorder(borderDen);
		add(lblNewLabel_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(688, 56, 329, 111);
		add(panel_1);
		
		JButton btnNewButton_1 = new JButton("Cấp");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(230, 667, 89, 31);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Cấp quyền");
		
		btnNewButton_2.setBounds(203, 54, 116, 34);
		panel_1.add(btnNewButton_2);
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 11, 309, 32);
		panel_1.add(comboBox);
		
		JLabel lblDanhSchQuyn = new JLabel("Tạo Role");
		lblDanhSchQuyn.setBounds(349, 11, 313, 40);
		add(lblDanhSchQuyn);
		lblDanhSchQuyn.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchQuyn.setBorder(borderDen);
		lblDanhSchQuyn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(349, 225, 905, 479);
		add(scrollPane_1);
		
		table_1 = new JTable();
		
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblQuynUserang = new JLabel("Quyền user đang có");
		lblQuynUserang.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuynUserang.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblQuynUserang.setBounds(349, 178, 905, 49);
		lblQuynUserang.setBorder(borderDen);
		add(lblQuynUserang);
		//event
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creRole();
				showAllRole();
			}
		});
		btnXa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drpRole();
				showAllRole();
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            Object RoleName = table.getValueAt(selectedRow, 0);
		            txt_RoleName.setText((String) RoleName);
		            addOwnPrivs();
		        }
			}
		});
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grant_Privs();
				addOwnPrivs();
			}
		});
		showAllRole();
		addPrivs();
	}
	public void showAllRole() {
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection()) {
                CallableStatement stmt = connection.prepareCall("{call DAT_ADMIN.showRole(?)}");
                stmt.registerOutParameter(1, Types.REF_CURSOR);
                stmt.execute();
                ResultSet rs = (ResultSet) stmt.getObject(1);
                populateTable(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please log in first.");
        }
    }
	
	public void creRole() {
		String nameRole = txt_RoleName.getText().toUpperCase();
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection()) {
                CallableStatement stmt = connection.prepareCall("{call DAT_ADMIN.creRole(?)}");
                stmt.setString(1, nameRole);
                stmt.execute();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gặp lỗi khi thêm.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please log in first.");
        }
    }
	
	public void drpRole() {
		String nameRole = txt_RoleName.getText().toUpperCase();
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection()) {
                CallableStatement stmt = connection.prepareCall("{call DAT_ADMIN.DrpRole(?)}");
                stmt.setString(1, nameRole);
                stmt.execute();
                JOptionPane.showMessageDialog(this, "Xóa thành công");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gặp lỗi khi xóa.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please log in first.");
        }
    }
	
	public void addPrivs() {
        List<String> privsList = new ArrayList<>();
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.add_privs(?)}")) {
                
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.execute();
                
                try (ResultSet rs = (ResultSet) callableStatement.getObject(1)) {
                    while (rs.next()) {
                        String privsName = rs.getString("NAME");
                        privsList.add(privsName);
                    }
                }

                for (String privsName : privsList) {
                    comboBox.addItem(privsName);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Unable to fetch data from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please log in first.", "Login Required", JOptionPane.WARNING_MESSAGE);
        }
    }
	
	public void addOwnPrivs() {
		String txt_userName = txt_RoleName.getText();
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.add_Own_privs(?, ?)}")) {
                
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.setString(2, txt_userName);
                callableStatement.execute();
                
                ResultSet rs = (ResultSet) callableStatement.getObject(1);
                populateTable_1(rs);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Unable to fetch data from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please log in first.", "Login Required", JOptionPane.WARNING_MESSAGE);
        }
    }
	
	public void Grant_Privs() {
	    String txt_userName = txt_RoleName.getText();
	    String txt_Privs = comboBox.getSelectedItem().toString();
	    if (Database.conn != null) {
	        try (Connection connection = Database.GetConnection()) {
	            // Prepare the CallableStatement with the correct SQL syntax
	            CallableStatement stmt = connection.prepareCall("{call grant_User(?, ?)}");
	            
	            // Register the output parameter as an integer (assuming the function returns 1 or 0)
	            stmt.setString(1, txt_userName);
	            stmt.setString(2, txt_Privs);
	            stmt.execute();
	            JOptionPane.showMessageDialog(null, "Grant thành công");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Please log in first.");
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
	
	private void populateTable_1(ResultSet resultSet) throws SQLException {
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
        table_1.setModel(model);
    }
}
