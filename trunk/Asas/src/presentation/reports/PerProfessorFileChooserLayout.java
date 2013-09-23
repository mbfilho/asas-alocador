package presentation.reports;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;


public class PerProfessorFileChooserLayout extends JDialog{
	private static final long serialVersionUID = 5756843565024256215L;
	
	private JFileChooser chooser;
	private JRadioButton sortByNameOption;
	private JCheckBox ascendingOrder;
	private JRadioButton sortByLoadOption;
	
	public PerProfessorFileChooserLayout(){
		setTitle("Salvar relatório");
		chooser = new JFileChooser();
		chooser.setVisible(true);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		GridBagConstraints gbc_chooser = new GridBagConstraints();
		gbc_chooser.insets = new Insets(0, 0, 5, 0);
		gbc_chooser.gridx = 0;
		gbc_chooser.gridy = 0;
		gbc_chooser.fill = GridBagConstraints.BOTH;
		getContentPane().add(chooser, gbc_chooser);
		
		JPanel sortingPanel = new JPanel();
		sortingPanel.setBorder(new TitledBorder(null, "Ordena\u00E7\u00E3o dos Professores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_sortingPanel = new GridBagConstraints();
		gbc_sortingPanel.fill = GridBagConstraints.BOTH;
		gbc_sortingPanel.gridx = 0;
		gbc_sortingPanel.gridy = 1;
		getContentPane().add(sortingPanel, gbc_sortingPanel);
		GridBagLayout gbl_sortingPanel = new GridBagLayout();
		gbl_sortingPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_sortingPanel.rowHeights = new int[]{0, 0};
		gbl_sortingPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_sortingPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		sortingPanel.setLayout(gbl_sortingPanel);
		
		ButtonGroup group = new ButtonGroup();
		sortByNameOption = new JRadioButton("Ordem Alfabética");
		sortByNameOption.setSelected(true);
		GridBagConstraints gbc_sortByNameOption = new GridBagConstraints();
		gbc_sortByNameOption.insets = new Insets(0, 0, 0, 5);
		gbc_sortByNameOption.gridx = 0;
		gbc_sortByNameOption.gridy = 0;
		sortingPanel.add(sortByNameOption, gbc_sortByNameOption);
		
		sortByLoadOption = new JRadioButton("Por Carga");
		GridBagConstraints gbc_sortByLoadOption = new GridBagConstraints();
		gbc_sortByLoadOption.insets = new Insets(0, 0, 0, 5);
		gbc_sortByLoadOption.gridx = 1;
		gbc_sortByLoadOption.gridy = 0;
		sortingPanel.add(sortByLoadOption, gbc_sortByLoadOption);
		group.add(sortByLoadOption);
		group.add(sortByNameOption);
		
		ascendingOrder = new JCheckBox("Ordem Crescente");
		ascendingOrder.setSelected(true);
		GridBagConstraints gbc_ascendingOrder = new GridBagConstraints();
		gbc_ascendingOrder.gridx = 2;
		gbc_ascendingOrder.gridy = 0;
		sortingPanel.add(ascendingOrder, gbc_ascendingOrder);
	}
	
	protected JFileChooser getFileChooser(){
		return chooser;
	}
	
	public boolean sortByName(){
		return sortByNameOption.isSelected();
	}
	
	public boolean sortByLoad(){
		return sortByLoadOption.isSelected();
	}
	
	public boolean sortAscending(){
		return ascendingOrder.isSelected();
	}
}