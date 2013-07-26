package visualizer;

import initial.GroupFormatter;
import initial.TableFormatter;
import initial.VisualizerTable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.xhtmlrenderer.resource.XMLResource;
import org.xhtmlrenderer.simple.FSScrollPane;
import org.xhtmlrenderer.simple.XHTMLPanel;

import statePersistence.State;
import utilities.SaveHtmlToFile;
import validation.WarningService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JSeparator;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class Visualizer extends FrameWithMenu {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visualizer frame = new Visualizer(new VisualizerService(), new WarningService());
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
	private JComboBox comboBox;
	private VisualizerService visualizerService;
	private WarningService warningService;
	private JTabbedPane tabbedPane;
	
	public Visualizer(VisualizerService vService, WarningService warningService) {
		super(warningService);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Visualizar");
		this.warningService = warningService;
		visualizerService = vService;
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{147, 208, 55, 0};
		gridBagLayout.rowHeights = new int[]{0, 22, 183, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 0;
		getContentPane().add(separator, gbc_separator);
		
		JLabel lblAgrupamento = new JLabel("Agrupamento:");
		GridBagConstraints gbc_lblAgrupamento = new GridBagConstraints();
		gbc_lblAgrupamento.insets = new Insets(0, 0, 5, 5);
		gbc_lblAgrupamento.anchor = GridBagConstraints.WEST;
		gbc_lblAgrupamento.gridx = 0;
		gbc_lblAgrupamento.gridy = 1;
		getContentPane().add(lblAgrupamento, gbc_lblAgrupamento);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Salas"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBox, gbc_comboBox);
		
		JButton btnNewButton = new JButton("Salvar HTML");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	SaveHtmlToFile.SaveHtml(visualizerService.groupByClassroom());
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 3;
		gbc_tabbedPane.insets = new Insets(0, 0, 0, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 2;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		setVisible(true);
	}
	
	private void refreshTable(){
		if(comboBox.getSelectedItem().equals("Salas")){
			clearTabs();
			Vector<Group> groups = visualizerService.groupByClassroom();
			for(final Group g : groups){
				Component table = new VisualizerTable(new GroupFormatter(g));
				tabbedPane.addTab(g.groupName, new JScrollPane(table));
			}
		}
	}
	
	
	private void clearTabs(){
		tabbedPane.removeAll();
	}
	
	//ao carregar um estado previamente salvo
	protected void onLoadNewState(State s){
		super.onLoadNewState(s);
		refreshTable();
	}
	
	//ao editar informações de uma turma
	public void onEditClassInformation() {
		super.onEditClassInformation();
		refreshTable();
	}
}
