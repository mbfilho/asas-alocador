package groupMaker;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import services.ProfessorService;

import basic.Professor;

import classEditor.NamedPair;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

public abstract class FilterChooser extends JPanel {
	private JComboBox<NamedPair<String>> areaCBox;
	private JComboBox<NamedPair<Professor>> profCBox;
	private JComboBox<NamedPair<String>> periodoCBox;
	private JCheckBox periodoCheck;
	private JCheckBox profCheck;
	private JCheckBox areaCheck;

	/**
	 * Create the panel.
	 */
	public FilterChooser() {
		ActionListener onChangeFilter = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				onChangeFilter(getFilter());
			}
		};
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 713, 163, 173, 0};
		gridBagLayout.rowHeights = new int[]{39, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblFiltros = new JLabel("Filtros:");
		GridBagConstraints gbc_lblFiltros = new GridBagConstraints();
		gbc_lblFiltros.anchor = GridBagConstraints.WEST;
		gbc_lblFiltros.insets = new Insets(0, 0, 5, 5);
		gbc_lblFiltros.gridx = 0;
		gbc_lblFiltros.gridy = 0;
		add(lblFiltros, gbc_lblFiltros);
		
		Vector<NamedPair<Professor>> professors = new Vector<NamedPair<Professor>>();
		ProfessorService profService = new ProfessorService();
		for(Professor p : profService.all()) professors.add(new NamedPair<Professor>(p.getName(), p));
		profCBox = new JComboBox<NamedPair<Professor>>(professors);
		profCBox.setEnabled(false);
		profCBox.addActionListener(onChangeFilter);
		GridBagConstraints gbc_profCBox = new GridBagConstraints();
		gbc_profCBox.insets = new Insets(0, 0, 5, 5);
		gbc_profCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_profCBox.gridx = 1;
		gbc_profCBox.gridy = 0;
		add(profCBox, gbc_profCBox);
		
		areaCBox = new JComboBox<NamedPair<String>>();
		areaCBox.setEnabled(false);
		areaCBox.addActionListener(onChangeFilter);
		GridBagConstraints gbc_profileCBox = new GridBagConstraints();
		gbc_profileCBox.insets = new Insets(0, 0, 5, 5);
		gbc_profileCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_profileCBox.gridx = 2;
		gbc_profileCBox.gridy = 0;
		add(areaCBox, gbc_profileCBox);
		
		Vector<NamedPair<String>> periodos = new Vector<NamedPair<String>>();
		for(int i = 1; i <= 10; ++i) periodos.add(new NamedPair<String>(i + "", i + ""));
		periodoCBox = new JComboBox<NamedPair<String>>(periodos);
		periodoCBox.setEnabled(false);
		periodoCBox.addActionListener(onChangeFilter);
		GridBagConstraints gbc_periodoCBox = new GridBagConstraints();
		gbc_periodoCBox.insets = new Insets(0, 0, 5, 0);
		gbc_periodoCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_periodoCBox.gridx = 3;
		gbc_periodoCBox.gridy = 0;
		add(periodoCBox, gbc_periodoCBox);
		
		profCheck = new JCheckBox("Professor");
		profCheck.addActionListener(onChangeFilter);
		profCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profCBox.setEnabled(profCheck.isSelected());
			}
		});
		GridBagConstraints gbc_profCheck = new GridBagConstraints();
		gbc_profCheck.insets = new Insets(0, 0, 0, 5);
		gbc_profCheck.gridx = 1;
		gbc_profCheck.gridy = 1;
		add(profCheck, gbc_profCheck);
		
		areaCheck = new JCheckBox("Perfil");
		areaCheck.addActionListener(onChangeFilter);
		areaCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profCBox.setEnabled(profCheck.isSelected());
			}
		});
		GridBagConstraints gbc_areaCheck = new GridBagConstraints();
		gbc_areaCheck.insets = new Insets(0, 0, 0, 5);
		gbc_areaCheck.gridx = 2;
		gbc_areaCheck.gridy = 1;
		add(areaCheck, gbc_areaCheck);
		
		periodoCheck = new JCheckBox("Período");
		periodoCheck.addActionListener(onChangeFilter);
		periodoCheck.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				periodoCBox.setEnabled(periodoCheck.isSelected());
			}
		});
		GridBagConstraints gbc_periodoCheck = new GridBagConstraints();
		gbc_periodoCheck.gridx = 3;
		gbc_periodoCheck.gridy = 1;
		add(periodoCheck, gbc_periodoCheck);

	}
	
	private <T> T getSelectedItem(JComboBox<NamedPair<T>> box){
		NamedPair<T> pair = (NamedPair<T>) box.getSelectedItem();
		return pair.data;
	}
	
	public ClassFilter getFilter(){
		ClassFilter filter = new ClassFilter();
		if(profCheck.isSelected()) filter.setProfessor(getSelectedItem(profCBox));
		if(areaCheck.isSelected()) filter.setArea(getSelectedItem(areaCBox));
		if(periodoCheck.isSelected()) filter.setSemester(getSelectedItem(periodoCBox));
		return filter;
	}
	
	public abstract void onChangeFilter(ClassFilter newFilter);
}
