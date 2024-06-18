package Tuan3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import DAO_UI.Database;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class XemThongTinUser extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTable table_1;
    private JTextField TXT_UserName;

    /**
     * Create the panel.
     */
    public XemThongTinUser() {
        setBounds(0, 0, 1264, 776);
        setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 847, 374);
        add(scrollPane);
        
        table = new JTable();
        
        scrollPane.setViewportView(table);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 391, 847, 374);
        add(scrollPane_1);
        
        table_1 = new JTable();
        scrollPane_1.setViewportView(table_1);
        
        TXT_UserName = new JTextField();
        TXT_UserName.setBounds(950, 9, 185, 23);
        TXT_UserName.setVisible(true);
        add(TXT_UserName);
        TXT_UserName.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("User");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblNewLabel.setBounds(867, 12, 58, 20);
        add(lblNewLabel);
        
        JButton btnNewButton = new JButton("Tìm kiếm");
        
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton.setBounds(1145, 8, 109, 23);
        add(btnNewButton);
        
        Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
        //event
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            Object SID = table.getValueAt(selectedRow, 0);
		            TXT_UserName.setText((String) SID);
		            showUserInformation();
		        }
        	}
        });
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		showUserInformation();
        	}
        });
        showAllUser();
    }
    
    public void showUserInformation() {
        if (Database.conn != null) { // Sử dụng lớp Database để kết nối cơ sở dữ liệu
            try (Connection connection = Database.GetConnection()) {
                String usernameToFind = TXT_UserName.getText().toUpperCase();
                CallableStatement stmt = connection.prepareCall("{call GetUserInformation(?,?,?,?,?,?,?)}");
                stmt.setString(1, usernameToFind);
                stmt.registerOutParameter(2, Types.VARCHAR);
                stmt.registerOutParameter(3, Types.DATE);
                stmt.registerOutParameter(4, Types.DATE);
                stmt.registerOutParameter(5, Types.VARCHAR);
                stmt.registerOutParameter(6, Types.TIMESTAMP);
                stmt.registerOutParameter(7, Types.VARCHAR);
                
                stmt.execute();
    
                String name = stmt.getString(2);
                Date createDate = stmt.getDate(3);
                Date expireDate = stmt.getDate(4);
                String status = stmt.getString(5);
                Timestamp lastLogin = stmt.getTimestamp(6);
                String profile = stmt.getString(7);
    
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Name");
                model.addColumn("Create Date");
                model.addColumn("Expire Date");
                model.addColumn("Status");
                model.addColumn("Last Login");
                model.addColumn("Profile");
    
                Object[] row = {name, createDate, expireDate, status, lastLogin, profile};
                model.addRow(row);
                
                table_1.setModel(model);
    
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please log in first.");
        }
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
}
