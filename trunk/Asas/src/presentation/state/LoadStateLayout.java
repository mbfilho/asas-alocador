package presentation.state;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utilities.DisposableOnEscFrame;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class LoadStateLayout extends DisposableOnEscFrame {

	private static final long serialVersionUID = -4555977675697820514L;
	private JPanel contentPane;
	private JTable stateTable;
	private JButton okButton;
	private JButton removeStateButton;

	public LoadStateLayout() {
		setTitle("Carregar estado salvo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{332, 322, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		stateTable = new JTable(new StateTableModel());
		stateTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(stateTable);
		
		okButton = new JButton("Carregar");
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.insets = new Insets(0, 0, 0, 5);
		gbc_okButton.gridx = 0;
		gbc_okButton.gridy = 1;
		contentPane.add(okButton, gbc_okButton);
		
		removeStateButton = new JButton("Remover");
		GridBagConstraints gbc_removeStateButton = new GridBagConstraints();
		gbc_removeStateButton.gridx = 1;
		gbc_removeStateButton.gridy = 1;
		contentPane.add(removeStateButton, gbc_removeStateButton);
	}

	protected JButton getOkButton() {
		return okButton;
	}
	
	protected StateTableModel getTableModel(){
		return (StateTableModel) stateTable.getModel();
	}
	protected JTable getStateTable() {
		return stateTable;
	}
	protected JButton getRemoveStateButton() {
		return removeStateButton;
	}
}
