package history.gui;

import history.HistoryTableModel;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

public class HistoryTablePanel extends JPanel {
	private static final long serialVersionUID = 4463115005718460232L;
	
	private JTable historyTable;

	/**
	 * Create the panel.
	 */
	public HistoryTablePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		historyTable = new HistoryTable(new HistoryTableModel());
		scrollPane.setViewportView(historyTable);

	}

}
