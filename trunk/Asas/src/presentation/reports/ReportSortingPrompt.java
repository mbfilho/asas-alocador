package presentation.reports;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;
import java.awt.Insets;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.reports.perProfessor.ProfessorSorting;

public class ReportSortingPrompt extends JDialog {

	private static final long serialVersionUID = -6876449938522951800L;
	
	private final JPanel contentPanel = new JPanel();
	private ProfessorSorting sortingPrefs;
	private JCheckBox ascendingOption;
	private JRadioButton nameOption;
	private JRadioButton loadOption;

	public ReportSortingPrompt(JFrame parent) {
		super(parent,"Ordenação dos professores", true);
		setBounds(100, 100, 445, 174);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{386, 0};
		gridBagLayout.rowHeights = new int[]{97, 33, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_contentPanel = new GridBagConstraints();
		gbc_contentPanel.fill = GridBagConstraints.BOTH;
		gbc_contentPanel.insets = new Insets(0, 0, 5, 0);
		gbc_contentPanel.gridx = 0;
		gbc_contentPanel.gridy = 0;
		getContentPane().add(contentPanel, gbc_contentPanel);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{216, 145, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel sortingLabel = new JLabel("Qual critério deve ser usado para ordenar os professores no relatório?");
			GridBagConstraints gbc_sortingLabel = new GridBagConstraints();
			gbc_sortingLabel.gridwidth = 2;
			gbc_sortingLabel.insets = new Insets(0, 0, 5, 5);
			gbc_sortingLabel.gridx = 0;
			gbc_sortingLabel.gridy = 0;
			contentPanel.add(sortingLabel, gbc_sortingLabel);
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_horizontalStrut.gridx = 0;
			gbc_horizontalStrut.gridy = 1;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		}
		{
			ButtonGroup group = new ButtonGroup();
			nameOption = new JRadioButton("Ordem Alfabética");
			nameOption.setSelected(true);
			GridBagConstraints gbc_nameOption = new GridBagConstraints();
			gbc_nameOption.insets = new Insets(0, 0, 5, 5);
			gbc_nameOption.gridx = 0;
			gbc_nameOption.gridy = 2;
			contentPanel.add(nameOption, gbc_nameOption);
			
			loadOption = new JRadioButton("Ordem de Carga");
			GridBagConstraints gbc_loadOption = new GridBagConstraints();
			gbc_loadOption.insets = new Insets(0, 0, 5, 0);
			gbc_loadOption.gridx = 1;
			gbc_loadOption.gridy = 2;
			contentPanel.add(loadOption, gbc_loadOption);
			group.add(nameOption);
			group.add(loadOption);
		}
		{
			ascendingOption = new JCheckBox("Ascendente");
			ascendingOption.setSelected(true);
			GridBagConstraints gbc_ascendingOption = new GridBagConstraints();
			gbc_ascendingOption.insets = new Insets(0, 0, 0, 5);
			gbc_ascendingOption.gridx = 0;
			gbc_ascendingOption.gridy = 3;
			contentPanel.add(ascendingOption, gbc_ascendingOption);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			GridBagConstraints gbc_buttonPane = new GridBagConstraints();
			gbc_buttonPane.anchor = GridBagConstraints.NORTH;
			gbc_buttonPane.fill = GridBagConstraints.HORIZONTAL;
			gbc_buttonPane.gridx = 0;
			gbc_buttonPane.gridy = 1;
			getContentPane().add(buttonPane, gbc_buttonPane);
			{
				JButton okButton = new JButton("Ok");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						sortingPrefs = new ProfessorSorting(nameOption.isSelected(), loadOption.isSelected(), ascendingOption.isSelected());
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setVisible(true);
	}

	public ProfessorSorting getSortingPreferences(){
		return sortingPrefs;
	}
}
