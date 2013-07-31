package classrooms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import basic.Classroom;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public abstract class ClassroomFramePattern extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCap;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassroomFramePattern frame = new ClassroomFramePattern() {
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

	/**
	 * Create the frame.
	 */
	public ClassroomFramePattern() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{17, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 1;
		gbc_lblNome.gridy = 1;
		contentPane.add(lblNome, gbc_lblNome);
		
		txtNome = new JTextField();
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.gridwidth = 2;
		gbc_txtNome.insets = new Insets(0, 0, 5, 5);
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridx = 2;
		gbc_txtNome.gridy = 1;
		contentPane.add(txtNome, gbc_txtNome);
		txtNome.setColumns(10);
		
		JLabel lblCapacidade = new JLabel("Capacidade:");
		GridBagConstraints gbc_lblCapacidade = new GridBagConstraints();
		gbc_lblCapacidade.anchor = GridBagConstraints.EAST;
		gbc_lblCapacidade.insets = new Insets(0, 0, 5, 5);
		gbc_lblCapacidade.gridx = 1;
		gbc_lblCapacidade.gridy = 2;
		contentPane.add(lblCapacidade, gbc_lblCapacidade);
		
		txtCap = new JTextField();
		GridBagConstraints gbc_txtCap = new GridBagConstraints();
		gbc_txtCap.insets = new Insets(0, 0, 5, 5);
		gbc_txtCap.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCap.gridx = 2;
		gbc_txtCap.gridy = 2;
		contentPane.add(txtCap, gbc_txtCap);
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
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 4;
		contentPane.add(btnOk, gbc_btnOk);
		setVisible(true);
	}
	
	public abstract void onOkButton();
	
	protected Classroom getClassroomFromFields(){
		Classroom room = new Classroom(txtNome.getText(), txtCap.getText());
		return room;
	}
	
	protected void setFieldsFromClassroom(Classroom room){
		txtNome.setText(room.getName());
		if(room.getCapacity() == -1) txtCap.setText("");
		else txtCap.setText(room.getCapacity() + "");
	}

}
