package visualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utilities.DisposableOnEscFrame;
import utilities.GuiUtil;

import java.awt.GridBagLayout;

import data.configurations.ExcelPreferences;
import exceptions.StateIOException;

import javax.swing.KeyStroke;

import presentation.ExcelWritingDialog;
import presentation.classes.EditClass;
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
import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.JPanel;

import logic.dataUpdateSystem.CustomerType;
import logic.dataUpdateSystem.DataUpdateCentral;
import logic.dataUpdateSystem.Updatable;
import logic.dataUpdateSystem.UpdateDescription;
import logic.dto.WorkloadReport;
import logic.reports.AllocationPerSemester;
import logic.reports.perProfessor.AllocationPerProfessor;
import logic.services.ConfigurationService;
import logic.services.ProfessorWorkLoadService;
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
					StateService.getInstance().saveCurrentState();
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
		
		JMenuItem itemSaveToExcel = new JMenuItem("Salvar para Excel");
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
				ProfessorWorkLoadService reportService = ProfessorWorkLoadService.createServiceFromCurrentState();
				List<WorkloadReport> workload = reportService.calculateProfessorWorkload();
				Object rowData[][] = new Object[workload.size()][2];
				int row = 0;
				for(WorkloadReport load : workload){
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
		
		JMenuItem mntmGo = new JMenuItem("Alocação por Professor");
		mntmGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File previous = ConfigurationService.getLastFileLocationForProfessorReport();
				File choosen = GuiUtil.promptForHtmlFileCreation(FrameWithMenu.this, previous);
				
				if(choosen != null){
					try {
						new AllocationPerProfessor().saveToFile(choosen);
						ConfigurationService.setLastFileLocationForProfessorReport(choosen);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(
								FrameWithMenu.this, 
								"Ocorreu um erro ao salvar o arquivo. Certifique-se de ter inserido um nome válido.",
								"Erro ao salvar o relatório",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mnRelatrios.add(mntmGo);
		
		JMenu allocPerSemesterMenu = new JMenu("Alocação por período");
		JMenuItem ccSemesters = new JMenuItem("Ciência da Computação");
		ccSemesters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDocumentToFile(true);
			}
		});
		JMenuItem ecSemesters = new JMenuItem("Engenharia da Computação");
		ecSemesters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDocumentToFile(false);
			}
		});
		allocPerSemesterMenu.add(ecSemesters);
		allocPerSemesterMenu.add(ccSemesters);
		mnRelatrios.add(allocPerSemesterMenu);
		
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
	
	
	private void saveDocumentToFile(boolean isCC){
		File previous = ConfigurationService.getLastFileLocationForSemesterReport();
		File choosen = GuiUtil.promptForHtmlFileCreation(FrameWithMenu.this, previous);
		
		try {
			if(choosen != null){
				new AllocationPerSemester(isCC).saveToFile(choosen);
				ConfigurationService.setLastFileLocationForSemesterReport(choosen);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(
					FrameWithMenu.this, 
					"Ocorreu um erro ao salvar o arquivo. Certifique-se de ter inserido um nome válido.",
					"Erro ao salvar o relatório",
					JOptionPane.ERROR_MESSAGE);
		}	
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
