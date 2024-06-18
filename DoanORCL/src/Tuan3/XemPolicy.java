package Tuan3;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import DAO_UI.Database;
import oracle.jdbc.OracleTypes;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XemPolicy extends JPanel {

	private static final long serialVersionUID = 1L;
	JComboBox<String> comboBox;
	/**
	 * Create the panel.
	 */
	public XemPolicy() {
		setBounds(0, 0, 1264, 776);
		Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
		setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 68, 324, 31);
		add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Danh sách các policy hiện có");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 26, 255, 31);
		add(lblNewLabel);
		add_policy();
	}
	private void add_policy() {
		List<String> policyNames = new ArrayList<>();
		if (Database.conn != null) {
            try (Connection connection = Database.GetConnection();
                 CallableStatement callableStatement = connection.prepareCall("{call DAT_ADMIN.add_policy(?)}")){
            	callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            	callableStatement.execute();
            	ResultSet rs = (ResultSet) callableStatement.getObject(1);
            	while (rs.next()) {
                    String policyName = rs.getString("OBJECT_NAME");
                    policyNames.add(policyName);
                }
                for (String policyName : policyNames) {
                    comboBox.addItem(policyName);
                }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: Unable to fetch data from the database.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Please log in first.");
	    }
	}
}
