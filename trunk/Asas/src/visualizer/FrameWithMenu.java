package visualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utilities.DisposableOnEscFrame;

import java.awt.GridBagLayout;

import data.configurations.ExcelPreferences;
import data.writers.ExcelClassWriter;
import exceptions.StateIOException;
import exceptions.WritingException;

import javax.swing.KeyStroke;

import presentation.ExcelWritingDialog;
import presentation.classes.addition.AddClass;
import presentation.classes.edition.EditClass;
import presentation.classrooms.AddClassroom;
import presentation.classrooms.EditClassroom;
import presentation.excelPreferences.EditExcelPreferences;
import presentation.historySystem.HistoryTablePanel;
import presentation.professors.AddProfessor;
import presentation.professors.EditProfessor;
import presentation.state.LoadStateFrame;
import presentation.warnings.WarningsLayout;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

import logic.dataUpdateSystem.CustomerType;
import logic.dataUpdateSystem.DataUpdateCentral;
import logic.dataUpdateSystem.Updatable;
import logic.dataUpdateSystem.UpdateDescription;
import logic.dto.ProfessorWorkload;
import logic.reports.AllocationReport;
import logic.services.ClassService;
import logic.services.ExcelWritingService;
import logic.services.ReportService;
import logic.services.StateService;
import logic.services.WarningGeneratorService;


public class FrameWithMenu extends JFrame implements Updatable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5703181522055851320L;
	protected JMenuBar mainMenuBar;
	protected JMenu stateMenu;
	protected JMenuItem loadState;
	protected JMenuItem saveState;
	private WarningGeneratorService warningService;
	private JMenu warningMenuItem;
	private JPanel historyTablePanel;
	
	public FrameWithMenu(WarningGeneratorService service) {
		DataUpdateCentral.signIn(this, CustomerType.Gui);
		warningService = service;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuState = new JMenu("Estado");
		menuBar.add(menuState);
		
		JMenuItem mntmCarregar = new JMenuItem("Carregar");
		mntmCarregar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoadStateFrame();
			}
		});
		
		menuState.add(mntmCarregar);
				
		JMenuItem itemSave = new JMenuItem("Salvar");
		itemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!StateService.getInstance().hasValidState()) return;
				
				try{
					StateService.getInstance().save();
				}catch(StateIOException ex){
					JOptionPane.showMessageDialog(FrameWithMenu.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		menuState.add(itemSave);
		
		
		JMenuItem itemLoadFromExcel = new JMenuItem("Carregar Excel");
		itemLoadFromExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new EditExcelPreferences();
			}
		});
		itemLoadFromExcel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		menuState.add(itemLoadFromExcel);
		
		JMenuItem itemSaveToExcel = new JMenuItem("Back 2 Excel");
		itemSaveToExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				ExcelPreferences prefs = StateService.getInstance().getCurrentState().excelPrefs;
				new ExcelWritingDialog(FrameWithMenu.this, prefs);
			}
		});
		
		menuState.add(itemSaveToExcel);
		
		JMenu menuEdit = new JMenu("Editar");
		menuBar.add(menuEdit);
		
		JMenuItem menuClasses = new JMenuItem("Turmas");
		menuClasses.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		menuClasses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditClass();
			}
		});
		
		JMenuItem mntmProfessores = new JMenuItem("Professores");
		mntmProfessores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditProfessor();
			}
		});
		
		menuEdit.add(mntmProfessores);
		
		JMenuItem mntmSalas = new JMenuItem("Salas");
		mntmSalas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditClassroom();
			}
		});
		menuEdit.add(mntmSalas);
		menuEdit.add(menuClasses);
		
		JMenu mnHistrico = new JMenu("Histórico");
		menuBar.add(mnHistrico);
		
		historyTablePanel = new HistoryTablePanel();
		mnHistrico.add(historyTablePanel);
		
		JMenu mnAdicionar = new JMenu("Adicionar");
		menuBar.add(mnAdicionar);
		
		JMenuItem mntmProfessor = new JMenuItem("Professor");
		mntmProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddProfessor();
			}
		});
		mnAdicionar.add(mntmProfessor);
		
		JMenuItem mntmSala = new JMenuItem("Sala");
		mntmSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddClassroom();
			}
		});
		mnAdicionar.add(mntmSala);
		
		JMenuItem mntmTurma = new JMenuItem("Turma");
		mntmTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddClass(warningService);
			}
		});
		mnAdicionar.add(mntmTurma);
		
		warningMenuItem = new JMenu("Alertas");
		menuBar.add(warningMenuItem);
		
		JMenuItem mntmShowWarnings = new JMenuItem("Mostar");
		mntmShowWarnings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new WarningsLayout();
			}
		});
		warningMenuItem.add(mntmShowWarnings);
		
		JMenu mnRelatrios = new JMenu("Relatórios");
		menuBar.add(mnRelatrios);
		
		JMenuItem mntmWorkload = new JMenuItem("Carga Horária");
		mntmWorkload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReportService reportService = new ReportService();
				List<ProfessorWorkload> workload = reportService.calculateProfessorWorkload();
				Object rowData[][] = new Object[workload.size()][2];
				int row = 0;
				for(ProfessorWorkload load : workload){
					rowData[row][0] = load.professor.getName();
					rowData[row++][1] = load.workload;
				}
				JTable table = new JTable(rowData, new String[]{"Professor", "Carga Horária"});
				JFrame frame = new DisposableOnEscFrame();
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(new JScrollPane(table));
				frame.setVisible(true);
			}
		});
		mnRelatrios.add(mntmWorkload);
		
		JMenuItem mntmGo = new JMenuItem("Relatório de Alocação");
		mntmGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if(chooser.showSaveDialog(FrameWithMenu.this) == JFileChooser.APPROVE_OPTION){
					File f = chooser.getSelectedFile();
					String name = f.getAbsolutePath();
					if(!(name.endsWith(".html") || name.endsWith(".html")))
						f = new File(name + ".html");
					
					new AllocationReport().saveToFile(f);
				}
			}
		});
		mnRelatrios.add(mntmGo);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}

	private void updateWarningCountText(){
		int count = warningService.notAllowedWarningsCount();
		String text = "Alertas";
		if(count != 0) text += "(" + count + ")";
		warningMenuItem.setText(text);
	}
	
	public void onDataUpdate(UpdateDescription desc) {
		updateWarningCountText();
		StateService stateService = StateService.getInstance();
		if(stateService.hasValidState())
			setTitle("Visualizar - " + stateService.getCurrentState().getName());
		else
			setTitle("Visualizar - Nenhum estado carregado.");
	}
}
