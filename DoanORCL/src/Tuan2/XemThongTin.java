package Tuan2;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO_UI.Database;
import oracle.jdbc.OracleTypes;

import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class XemThongTin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public XemThongTin() {
		setBounds(0, 0, 1264, 776);
		setLayout(null);
		Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		scrollPane.setBounds(95, 64, 1073, 503);
		add(scrollPane);
		
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(419, 11, 425, 42);
		lblNewLabel.setVisible(false);
		lblNewLabel.setBorder(borderDen);
		add(lblNewLabel);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1 = new JLabel("Table:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(434, 578, 84, 29);
		add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();	
		
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"sga", "pga", "process", "instance", "database", "datafile", "control files", "spfile"}));
		comboBox.setBounds(511, 578, 371, 29);
		add(comboBox);
		//event
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = comboBox.getSelectedItem().toString();
				switch (selectedItem) {
				case "sga": {
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					getSGAInfo();
					break;	
				}
				case "pga":{
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					getPGAInfo();
					break;
				}
				case "process":{
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					getProcessInfo();
					break;
				}
				case "instance": {
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					getInstanceInfo();
					break;
				}
				case "database":{
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					getDatabaseInfo();
					break;
				}
				case "spfile":{
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					getSPFileInfo();
					break;
				}
				case "datafile":{
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					getDatafileInfo();
					break;
				}
				case "control files":{
					lblNewLabel.setText(selectedItem);
					lblNewLabel.setVisible(true);
					getControlFileInfo();
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + selectedItem);
				}
			}
		});
	}
	public void getDatabaseInfo() {
		if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowDabaseInfo(?)}")){
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

    public void getSGAInfo() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowSGAInfo(?)}")){
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

    public void getPGAInfo() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowPGAInfo(?)}")){
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

    public void getProcessInfo() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowProcessInfo(?)}")){
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

    public void getInstanceInfo() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowInstanceInfo(?)}")){
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

    public void getDatafileInfo() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowDatafileInfo(?)}")){
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

    public void getControlFileInfo() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowControlFileInfo(?)}")){
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

    public void getSPFileInfo() {
    	if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call SPFileInfo(?)}")){
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
