package DAO_UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tuan4_UI extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Tuan4_UI() {
		setBounds(1, 0, 1264, 830);
		Border borderDen = BorderFactory.createEtchedBorder(2, Color.BLACK, Color.gray);
		setLayout(null);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblXemThngTin = new JLabel("Profile");
		
		
		lblXemThngTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblXemThngTin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblXemThngTin.setBorder(borderDen);
		lblXemThngTin.setBounds(10, 32, 218, 39);
		
		add(lblXemThngTin);		
		
		JPanel panel_1 = new JPanel();
		
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 97, 1264, 733);
		add(panel_1);
		panel_1.setLayout(new CardLayout(0, 0));
		
		JLabel lblXemThngTin_1 = new JLabel("Quản lý nhóm quyền");
		lblXemThngTin_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblXemThngTin_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblXemThngTin_1.setBounds(251, 32, 218, 39);
		lblXemThngTin_1.setBorder(borderDen);
		add(lblXemThngTin_1);
		
		JLabel lblXemThngTin_1_1 = new JLabel("Tạo ROLE");
		
		lblXemThngTin_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblXemThngTin_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblXemThngTin_1_1.setBounds(479, 32, 218, 39);
		lblXemThngTin_1_1.setBorder(borderDen);
		add(lblXemThngTin_1_1);
		//event
		lblXemThngTin_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_1.removeAll(); 
				panel_1.add(new Tuan4.PhanQuyen());
				panel_1.revalidate();
				panel_1.repaint();
			}
		});
		lblXemThngTin_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_1.removeAll(); 
				panel_1.add(new Tuan4.TaoRole());
				panel_1.revalidate();
				panel_1.repaint();
			}
		});
		lblXemThngTin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_1.removeAll(); 
				panel_1.add(new Tuan4.Profile());
				panel_1.revalidate();
				panel_1.repaint();
			}
		});
	}

}
