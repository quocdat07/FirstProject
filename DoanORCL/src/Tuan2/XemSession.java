package Tuan2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO_UI.Database;
import oracle.jdbc.OracleTypes;
import javax.swing.JCheckBox;

public class XemSession extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextField txt_SID;
    private JTextField txt_SERIAL;

    /**
     * Create the panel.
     */
    public XemSession() {
        setBounds(0, 0, 1264, 776);
        setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        scrollPane.setBounds(95, 61, 1073, 503);
        add(scrollPane);
        
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Object SID = table.getValueAt(selectedRow, 0);
                    txt_SID.setText(SID.toString());
                    Object SERIAL = table.getValueAt(selectedRow, 1);
                    txt_SERIAL.setText(SERIAL.toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel = new JLabel("SESSION");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(425, 11, 413, 33);
        add(lblNewLabel);
        
        txt_SID = new JTextField();
        txt_SID.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txt_SID.setBounds(309, 575, 326, 33);
        add(txt_SID);
        txt_SID.setColumns(10);
        
        txt_SERIAL = new JTextField();
        txt_SERIAL.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txt_SERIAL.setBounds(309, 636, 326, 33);
        add(txt_SERIAL);
        txt_SERIAL.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("SID");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel_1.setBounds(231, 584, 57, 24);
        add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("SERIAL");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel_2.setBounds(231, 645, 68, 14);
        add(lblNewLabel_2);
        
        JButton btnNewButton = new JButton("Kill Session");

        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.setBounds(658, 595, 180, 65);
        add(btnNewButton);
        
        JButton btnViewProcesses = new JButton("View Processes");
        btnViewProcesses.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnViewProcesses.setBounds(971, 595, 200, 65);
        add(btnViewProcesses);
        
        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTable();
                getSessionInfo();
            }
        });
        btnNewButton_1.setIcon(new ImageIcon("D:\\HomeWork\\DoanORCL\\reset.png"));
        btnNewButton_1.setBounds(1178, 60, 30, 23);
        add(btnNewButton_1);
        
        JCheckBox chckbxNewCheckBox = new JCheckBox("BACKGROUND");
        
        chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        chckbxNewCheckBox.setBounds(981, 667, 171, 23);
        add(chckbxNewCheckBox);
        Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
        getSessionInfo();
        
        //Event
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                killSession();    
            }
        });
        btnViewProcesses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProcesses();
            }
        });
        chckbxNewCheckBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(chckbxNewCheckBox.isSelected()) {
        			getSessionInfoNBG();
        		}
        		else {	
        			getSessionInfo();
        		}
        	}
        });
    }

    public void getSessionInfo() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call GetSessionInfo(?)}")){
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
    
    public void getSessionInfoNBG() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call GetSessionInfoNBG(?)}")){
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

    public void killSession() {
        String sid = txt_SID.getText();
        String serial = txt_SERIAL.getText();

        if (sid.isEmpty() || serial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both SID and SERIAL.");
            return;
        }

        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                CallableStatement callableStatement = connection.prepareCall("{call KillSession(?, ?)}")) {
                callableStatement.setString(1, sid);
                callableStatement.setString(2, serial);
                callableStatement.execute();

                JOptionPane.showMessageDialog(this, "Session killed successfully.");
                clearTable();
                getSessionInfo();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: Unable to kill session.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please log in first.");
        }
    }


    public void clearTable() {
        table.setModel(new DefaultTableModel());
    }

    public void viewProcesses() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                CallableStatement callableStatement = connection.prepareCall("{call GetProcessesInfo(?)}")){
            	callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            	callableStatement.execute();
            	ResultSet rs = (ResultSet) callableStatement.getObject(1);
            	populateTable(rs);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database." );
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
}
