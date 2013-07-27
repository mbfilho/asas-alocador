package edit;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import java.awt.Component;
import java.util.Collection;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;

import utilities.Constants;
import javax.swing.DefaultComboBoxModel;

import basic.Classroom;
import basic.NamedEntity;
import basic.SlotRange;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public abstract class SlotChooser extends JFrame {

	private static final long serialVersionUID = -2203432919581579448L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public SlotChooser(Collection<Classroom> allRooms) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 398, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 68, 76, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 32, 69, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSala = new JLabel("Sala");
		GridBagConstraints gbc_lblSala = new GridBagConstraints();
		gbc_lblSala.anchor = GridBagConstraints.WEST;
		gbc_lblSala.insets = new Insets(0, 0, 5, 5);
		gbc_lblSala.gridx = 1;
		gbc_lblSala.gridy = 0;
		contentPane.add(lblSala, gbc_lblSala);
		
		final JComboBox<NamedPair<Classroom>> classrooms = new JComboBox<NamedPair<Classroom>>();
		Vector<NamedPair<Classroom>> namedItens = new Vector<NamedPair<Classroom>>();
		for(Classroom r : allRooms) namedItens.add(new NamedPair<Classroom>(r.getName(), r));
		classrooms.setModel(new DefaultComboBoxModel(namedItens.toArray()));
		GridBagConstraints gbc_classrooms = new GridBagConstraints();
		gbc_classrooms.insets = new Insets(0, 0, 5, 0);
		gbc_classrooms.gridwidth = 6;
		gbc_classrooms.fill = GridBagConstraints.HORIZONTAL;
		gbc_classrooms.gridx = 2;
		gbc_classrooms.gridy = 0;
		contentPane.add(classrooms, gbc_classrooms);
		
		JLabel lblDia = new JLabel("Dia");
		GridBagConstraints gbc_lblDia = new GridBagConstraints();
		gbc_lblDia.anchor = GridBagConstraints.WEST;
		gbc_lblDia.insets = new Insets(0, 0, 5, 5);
		gbc_lblDia.gridx = 1;
		gbc_lblDia.gridy = 1;
		contentPane.add(lblDia, gbc_lblDia);
		
		final JComboBox<String> days = new JComboBox<String>();
		days.setModel(new DefaultComboBoxModel(Constants.days));
		GridBagConstraints gbc_days = new GridBagConstraints();
		gbc_days.gridwidth = 5;
		gbc_days.insets = new Insets(0, 0, 5, 5);
		gbc_days.fill = GridBagConstraints.HORIZONTAL;
		gbc_days.gridx = 2;
		gbc_days.gridy = 1;
		contentPane.add(days, gbc_days);
		
		JLabel lblDas = new JLabel("Das");
		GridBagConstraints gbc_lblDas = new GridBagConstraints();
		gbc_lblDas.anchor = GridBagConstraints.WEST;
		gbc_lblDas.insets = new Insets(0, 0, 5, 5);
		gbc_lblDas.gridx = 1;
		gbc_lblDas.gridy = 2;
		contentPane.add(lblDas, gbc_lblDas);
		
		final JSpinner iniHour = new JSpinner();
		iniHour.setModel(new SpinnerNumberModel(7, 7, 21, 1));
		GridBagConstraints gbc_iniHour = new GridBagConstraints();
		gbc_iniHour.insets = new Insets(0, 0, 5, 5);
		gbc_iniHour.gridx = 2;
		gbc_iniHour.gridy = 2;
		contentPane.add(iniHour, gbc_iniHour);
		
		JLabel lblH = new JLabel("h às");
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 3;
		gbc_lblH.gridy = 2;
		contentPane.add(lblH, gbc_lblH);
		
		final JSpinner endHour = new JSpinner();
		endHour.setModel(new SpinnerNumberModel(8, 8, 22, 1));
		GridBagConstraints gbc_endHour = new GridBagConstraints();
		gbc_endHour.insets = new Insets(0, 0, 5, 5);
		gbc_endHour.gridx = 4;
		gbc_endHour.gridy = 2;
		contentPane.add(endHour, gbc_endHour);
		
		JLabel lblH_1 = new JLabel("h");
		GridBagConstraints gbc_lblH_1 = new GridBagConstraints();
		gbc_lblH_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblH_1.gridx = 5;
		gbc_lblH_1.gridy = 2;
		contentPane.add(lblH_1, gbc_lblH_1);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<SlotRange> selected = new Vector<SlotRange>();
				int ini = ((int) iniHour.getValue()) - 7, end = ((int) endHour.getValue()) - 7;
				while(ini < end){
					NamedPair<Classroom> obj = (NamedPair<Classroom>) classrooms.getSelectedItem();
					SlotRange slot = new SlotRange(days.getSelectedIndex(), ini, (Classroom) obj.data); 
					selected.add(slot);
					++ini;
				}
				if(!selected.isEmpty())	onChooseSlot(selected);
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.SOUTH;
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 3;
		contentPane.add(btnOk, gbc_btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SlotChooser.this.dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancelar.gridwidth = 3;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 3;
		contentPane.add(btnCancelar, gbc_btnCancelar);
		
		setVisible(true);
	}

	public abstract void onChooseSlot(Vector<SlotRange> chosen);
}
