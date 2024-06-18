package Tuan4;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO_UI.Database;
import oracle.jdbc.OracleTypes;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PhanQuyen extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField txt_UserName;
	JComboBox<String> comboBox;
	private JTable table_1;
	private JTextField txt_Privs;
	/**
	 * Create the panel.
	 */
	
	public PhanQuyen() {
		setBounds(0, 0, 1264, 776);
		setLayout(null);
		Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(333, 58, 313, 661);
		add(panel_1);
		panel_1.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 11, 293, 32);
		panel_1.add(comboBox);
		
		JButton btnNewButton = new JButton("Cấp quyền");
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(187, 54, 116, 34);
		panel_1.add(btnNewButton);
		
		JButton btnThuQuyn = new JButton("Thu quyền");
		
		btnThuQuyn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnThuQuyn.setBounds(175, 291, 128, 34);
		panel_1.add(btnThuQuyn);
		
		txt_UserName = new JTextField();
		txt_UserName.setHorizontalAlignment(SwingConstants.CENTER);
		txt_UserName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_UserName.setBounds(10, 193, 293, 32);
		panel_1.add(txt_UserName);
		txt_UserName.setEnabled(false);
		txt_UserName.setVisible(true);
		txt_UserName.setColumns(10);
		
		JLabel lblQuynangC = new JLabel("Quyền đang có");
		lblQuynangC.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuynangC.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblQuynangC.setBounds(0, 112, 313, 49);
		lblQuynangC.setBorder(borderDen);
		panel_1.add(lblQuynangC);
		
		txt_Privs = new JTextField();
		txt_Privs.setHorizontalAlignment(SwingConstants.CENTER);
		txt_Privs.setEnabled(false);
		txt_Privs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_Privs.setBounds(10, 238, 293, 32);
		panel_1.add(txt_Privs);
		txt_Privs.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User đang có");
		lblNewLabel.setBounds(10, 11, 313, 49);
		add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBorder(borderDen);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 58, 313, 661);
		add(scrollPane);
		
		table = new JTable();
		
		scrollPane.setViewportView(table);
		
		JLabel lblDanhSchQuyn = new JLabel("Danh sách quyền");
		lblDanhSchQuyn.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchQuyn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblDanhSchQuyn.setBounds(333, 11, 313, 49);
		lblDanhSchQuyn.setBorder(borderDen);
		add(lblDanhSchQuyn);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(656, 58, 598, 661);
		add(scrollPane_1);
		
		table_1 = new JTable();
		
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblQuynUserang = new JLabel("Quyền user đang có");
		lblQuynUserang.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuynUserang.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblQuynUserang.setBounds(656, 11, 598, 49);
		lblQuynUserang.setBorder(borderDen);
		add(lblQuynUserang);
		//Event
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					Object userNames = table.getValueAt(selectedRow, 0).toString();
					txt_UserName.setText(userNames.toString().trim());
					addOwnPrivs();
				}
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grant_Privs();
				addOwnPrivs();
			}
		});
		
		btnThuQuyn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Revoke_Privs();
				addOwnPrivs();
			}
		});
		
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table_1.getSelectedRow();
				if (selectedRow != -1) {
					Object userNames = table_1.getValueAt(selectedRow, 1).toString();
					txt_Privs.setText(userNames.toString().trim());
				}
			}
		});
		showAllUser();
		addPrivs();
	}

	public void showAllUser() {
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection()) {
                CallableStatement stmt = connection.prepareCall("{call GetUserNames(?)}");
                stmt.registerOutParameter(1, Types.REF_CURSOR);
                stmt.execute();
                
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Username");
                
                ResultSet rs = (ResultSet) stmt.getObject(1);
                while (rs.next()) {
                    Object[] row = {rs.getString("USERNAME")};
                    model.addRow(row);
                }
                rs.close();
                stmt.close();
                
                table.setModel(model);
            } catch (SQLException e) {
                e.printStackTrace();
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
		String txt_userName = txt_UserName.getText();
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.add_Own_privs(?, ?)}")) {
                
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.setString(2, txt_userName);
                callableStatement.execute();
                
                ResultSet rs = (ResultSet) callableStatement.getObject(1);
                populateTable(rs);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Unable to fetch data from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please log in first.", "Login Required", JOptionPane.WARNING_MESSAGE);
        }
    }
	
	public void Grant_Privs() {
	    String txt_userName = txt_UserName.getText();
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

	
	public void Revoke_Privs() {
		String txt_userName = txt_UserName.getText();
		String txt_Privs = comboBox.getSelectedItem().toString();
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection()) {
                CallableStatement stmt = connection.prepareCall("{call revoke_Privilege(?, ?)}");
                stmt.setString(1, txt_userName);
                stmt.setString(2, txt_Privs);
                stmt.execute();
	            JOptionPane.showMessageDialog(null, "REVOKE thành công");
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
        table_1.setModel(model);
    }
}
