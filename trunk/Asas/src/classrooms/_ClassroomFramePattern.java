package classrooms;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

import basic.Classroom;

public abstract class _ClassroomFramePattern extends JFrame{
	private static final long serialVersionUID = -4995820049688086655L;
	private JTextField txtNome;
	private JTextField txtCap;
	
	public abstract void onOkButton();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					_ClassroomFramePattern frame = new _ClassroomFramePattern() {
						public void onOkButton() {
							
						}
					};
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public _ClassroomFramePattern() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{116, 71, 130, 21, 0};
		gridBagLayout.rowHeights = new int[] {35, 0, 0, 31, 38, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		getContentPane().add(label, gbc_label);
		
		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		getContentPane().add(lblNome, gbc_lblNome);
		
		txtNome = new JTextField();
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridwidth = 2;
		gbc_txtNome.insets = new Insets(0, 0, 5, 0);
		gbc_txtNome.gridx = 1;
		gbc_txtNome.gridy = 1;
		getContentPane().add(txtNome, gbc_txtNome);
		txtNome.setColumns(10);
		
		JLabel lblCapacidade = new JLabel("Capacidade:");
		GridBagConstraints gbc_lblCapacidade = new GridBagConstraints();
		gbc_lblCapacidade.anchor = GridBagConstraints.EAST;
		gbc_lblCapacidade.insets = new Insets(0, 0, 5, 5);
		gbc_lblCapacidade.gridx = 0;
		gbc_lblCapacidade.gridy = 2;
		getContentPane().add(lblCapacidade, gbc_lblCapacidade);
		
		txtCap = new JTextField();
		GridBagConstraints gbc_txtCap = new GridBagConstraints();
		gbc_txtCap.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCap.insets = new Insets(0, 0, 5, 5);
		gbc_txtCap.gridx = 1;
		gbc_txtCap.gridy = 2;
		getContentPane().add(txtCap, gbc_txtCap);
		txtCap.setColumns(10);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOkButton();
				dispose();
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 4;
		getContentPane().add(btnOk, gbc_btnOk);
		setMaximumSize(new Dimension(333,179));
		setResizable(false);
		setVisible(true);
	}
	

}
