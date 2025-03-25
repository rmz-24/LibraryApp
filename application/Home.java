package APP;

import javax.swing.*;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Home{
    private JFrame frmDashboard;
    
    public Home(String user , String level) {
    	
        frmDashboard = new JFrame("Home");
        frmDashboard.setTitle("Dashboard");
        frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmDashboard.setSize(1500, 900);
        frmDashboard.setLocationRelativeTo(null);
        frmDashboard.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(0, 0, 202, 1122);
        frmDashboard.getContentPane().add(panel);
        panel.setLayout(null);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(145, 149, 153));
        panel_1.setBounds(201, 0, 1494, 88);
        frmDashboard.getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("CONNECTED AS:");
        lblNewLabel.setFont(new Font("Jost", Font.BOLD, 20));
        lblNewLabel.setBounds(928, 11, 178, 16);
        panel_1.add(lblNewLabel);
        
        JLabel lblAccessLevel = new JLabel("ACCESS LEVEL :");
        lblAccessLevel.setFont(new Font("Jost", Font.BOLD, 20));
        lblAccessLevel.setBounds(926, 48, 168, 16);
        panel_1.add(lblAccessLevel);
        
        
        JLabel lblusername = new JLabel(user);
        lblusername.setFont(new Font("Jost", Font.BOLD, 20));
        lblusername.setBounds(1116, 11, 120, 16);
        panel_1.add(lblusername);
        
        JLabel lblacceslevel = new JLabel(level);
        lblacceslevel.setFont(new Font("Jost", Font.BOLD, 20));
        lblacceslevel.setBounds(1116, 48, 120, 16);
        panel_1.add(lblacceslevel);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		frmDashboard.dispose();
        		LibraryApp.main(null);
        		
        	}
        });
        btnNewButton.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\log-out_10024482.png"));
        btnNewButton.setBounds(1411, 787, 65, 65);
        frmDashboard.getContentPane().add(btnNewButton);

        frmDashboard.setVisible(true);
    }
}
