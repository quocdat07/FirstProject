package Tuan2;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO_UI.Database;
import oracle.jdbc.OracleTypes;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class XemTableSpace extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextField txt_tenUser;
    private JTable table_1;
    private JTextField txt_TBS;

    /**
     * Create the panel.
     */
    public XemTableSpace() {
        setBounds(0, 0, 1264, 776);
        Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
        setLayout(null);
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setBounds(95, 64, 1073, 263);
        scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        add(scrollPane);

        JLabel lblNewLabel = new JLabel("TableSpace");
        lblNewLabel.setBounds(419, 11, 425, 42);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setVisible(false);
        lblNewLabel.setBorder(borderDen);
        add(lblNewLabel);
        add(scrollPane);

        table = new JTable();
        
        scrollPane.setViewportView(table);

        txt_tenUser = new JTextField();
        txt_tenUser.setBounds(151, 338, 146, 33);
        txt_tenUser.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txt_tenUser.setColumns(10);
        add(txt_tenUser);

        JLabel lblNewLabel_1 = new JLabel("User");
        lblNewLabel_1.setBounds(95, 350, 46, 14);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblNewLabel_1);

        JButton btnNewButton = new JButton("Xem tableSpace");
        btnNewButton.setBounds(307, 338, 190, 33);

        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(btnNewButton);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        scrollPane_1.setBounds(95, 413, 1073, 263);
        add(scrollPane_1);

        table_1 = new JTable();
        scrollPane_1.setViewportView(table_1);

        JButton btnXemTonB = new JButton("Xem toàn bộ datafile");

        btnXemTonB.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnXemTonB.setBounds(978, 338, 190, 33);
        add(btnXemTonB);

        JButton btnXemTonB_2 = new JButton("Xem toàn bộ TableSpace");

        btnXemTonB_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnXemTonB_2.setBounds(743, 338, 225, 33);
        add(btnXemTonB_2);
        
        txt_TBS = new JTextField();
        txt_TBS.setHorizontalAlignment(SwingConstants.CENTER);
        txt_TBS.setEditable(false);
        txt_TBS.setEnabled(false);
        txt_TBS.setFont(new Font("Tahoma", Font.PLAIN, 30));
        txt_TBS.setColumns(10);
        txt_TBS.setBounds(548, 369, 146, 33);
        add(txt_TBS);
        // event
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String tenUser = txt_tenUser.getText();
                showTableSpace(tenUser);
            }
        });
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	String nameDBA = txt_TBS.getText();
                showDataFiles(nameDBA);
            }
        });
        btnXemTonB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDataALLFiles();
            }
        });
        btnXemTonB_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAllTableSpace();
            }
        });
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int selectedRow = table.getSelectedRow();
        		if(selectedRow != -1) {
        			Object NameUSer = table.getValueAt(selectedRow, 0);
        			txt_tenUser.setText(NameUSer.toString());
        			Object TableSpaceName = table.getValueAt(selectedRow, 1);
        			txt_TBS.setText(TableSpaceName.toString());
        			String nameDBA = txt_TBS.getText();
                    showDataFiles(nameDBA);
        		}
        	}
        });
        showAllTableSpace();
    }

    private void showTableSpace(String tenUser) {
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowTablespace(?, ?)}")) {
                callableStatement.setString(1, tenUser);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                populateTable(resultSet);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Please log in first.");
	        }
    }

    private void showAllTableSpace() {
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowAllTablespaces(?)}")) {
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                populateTable(resultSet);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Please log in first.");
	        }
    }

    private void showDataFiles(String tbName) {
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowDatafiles(?, ?)}")) {
                callableStatement.setString(1, tbName);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                populateTable2(resultSet);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Please log in first.");
	        }
    }
    
    private void showDataALLFiles() {
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call ShowAllDataFile(?)}")){
            	callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            	callableStatement.execute();
            	ResultSet rs = (ResultSet) callableStatement.getObject(1);
            	populateTable2(rs);
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
    
    private void populateTable2(ResultSet resultSet) throws SQLException {
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

    public boolean createTablespace(String tablespaceName, String[] datafiles) {
        // Assume Database.conn != null establishes the database connection
        if (Database.conn != null) {
            try (Connection connection = Database.GetConnection()) {
                // Construct SQL statement to create tablespace
                String sqlCreateTablespace = "CREATE TABLESPACE " + tablespaceName + " DATAFILE ";
                for (int i = 0; i < datafiles.length; i++) {
                    sqlCreateTablespace += "'" + datafiles[i] + "'";
                    if (i < datafiles.length - 1) {
                        sqlCreateTablespace += ",";
                    }
                }
                try (PreparedStatement statement = connection.prepareStatement(sqlCreateTablespace)) {
                    statement.executeUpdate();
                    return true; // Tablespaces created successfully
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Failed to create tablespaces
    }

    public class TablespaceManager {

        public boolean addDatafileToTablespace(String tablespaceName, String datafile) {
            // Assume Database.conn != null establishes the database connection
            if (Database.conn != null) {
                try (Connection connection = Database.GetConnection()) {
                    // Construct SQL statement to add datafile to tablespace
                    String sqlAddDatafile = "ALTER TABLESPACE " + tablespaceName + " ADD DATAFILE '" + datafile + "'";
                    try (PreparedStatement statement = connection.prepareStatement(sqlAddDatafile)) {
                        statement.executeUpdate();
                        return true; // Datafile added successfully
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false; // Failed to add datafile
        }
    }
}

