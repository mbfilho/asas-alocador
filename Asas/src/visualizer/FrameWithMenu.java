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

import state.gui.LoadStateFrame;
import state.gui.NewStateFrame;
import utilities.DisposableOnEscFrame;
import utilities.HtmlTableFrame;
import warnings.gui.WarningsFrame;

import java.awt.GridBagLayout;

import excelPreferences.gui.EditExcelPreferences;
import exceptions.StateIOException;

import javax.swing.KeyStroke;

import presentation.classes.addition.AddClass;
import presentation.classes.edition.EditClass;
import presentation.classrooms.AddClassroom;
import presentation.classrooms.EditClassroom;
import presentation.historySystem.HistoryTablePanel;
import presentation.professors.AddProfessor;
import presentation.professors.EditProfessor;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.util.List;
import javax.swing.JPanel;

import logic.allocation.Allocator;
import logic.allocation.DefaultAllocator;
import logic.dataUpdateSystem.CustomerType;
import logic.dataUpdateSystem.DataUpdateCentral;
import logic.dataUpdateSystem.Updatable;
import logic.dataUpdateSystem.UpdateDescription;
import logic.dto.AllocationResult;
import logic.dto.ProfessorWorkload;
import logic.services.AllocationService;
import logic.services.ElectivePreferencesService;
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
		
		JMenu mnEstado = new JMenu("Estado");
		menuBar.add(mnEstado);
		
		JMenuItem mntmCarregar = new JMenuItem("Carregar");
		mntmCarregar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoadStateFrame();
			}
		});
		
		JMenuItem mntmNovaAlocao = new JMenuItem("Nova alocação");
		mntmNovaAlocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewStateFrame();
			}
		});
		mntmNovaAlocao.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnEstado.add(mntmNovaAlocao);
		mnEstado.add(mntmCarregar);
				
		JMenuItem mntmSalvar_1 = new JMenuItem("Salvar");
		mntmSalvar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!StateService.getInstance().hasValidState()) return;
				
				try{
					StateService.getInstance().save();
				}catch(StateIOException ex){
					JOptionPane.showMessageDialog(FrameWithMenu.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenuItem mntmCarregarExcel = new JMenuItem("Carregar Excel");
		mntmCarregarExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new EditExcelPreferences();
			}
		});
		mntmCarregarExcel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnEstado.add(mntmCarregarExcel);
		mntmSalvar_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		mnEstado.add(mntmSalvar_1);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		JMenuItem mntmTurmas = new JMenuItem("Turmas");
		mntmTurmas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		mntmTurmas.addActionListener(new ActionListener() {
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
		
		mnEditar.add(mntmProfessores);
		
		JMenuItem mntmSalas = new JMenuItem("Salas");
		mntmSalas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditClassroom(){
					private static final long serialVersionUID = 1646934026564171682L;

					public void onOkButton(){
						super.onOkButton();
						onEditClassroomInformation();
					}
				};
			}
		});
		mnEditar.add(mntmSalas);
		mnEditar.add(mntmTurmas);
		
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
				new WarningsFrame();
			}
		});
		warningMenuItem.add(mntmShowWarnings);
		
		JMenu mnEltivas = new JMenu("Eletivas(provisório)");
		menuBar.add(mnEltivas);
		
		JMenuItem mntmVer = new JMenuItem("Ver");
		mntmVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ElectivePreferencesService service = new ElectivePreferencesService();
				new HtmlTableFrame(service.getHtmlTableForPreferences(service.all()));
			}
		});
		mnEltivas.add(mntmVer);
		
		JMenuItem mntmAlocar = new JMenuItem("Alocar");
		mntmAlocar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Allocator aloc = new DefaultAllocator();
				AllocationResult result = aloc.allocate(true);

				JFrame frame = new HtmlTableFrame(new ElectivePreferencesService().getHtmlTableForPreferences(result.notAllocated));
				frame.setTitle("Turmas não alocadas (" + result.notAllocated.size() + ")");
				DataUpdateCentral.registerUpdate("Alocação de eletivas realizada");
			}
		});
		mnEltivas.add(mntmAlocar);
		
		JMenuItem mntmLimparAlocao = new JMenuItem("Limpar Alocação");
		mntmLimparAlocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int op = JOptionPane.showConfirmDialog(
							FrameWithMenu.this,
							"Essa ação irá apagar a alocação de todas as disciplinas eletivas,\n" +
							"inclusive as feitas manualmente. Deseja continuar?",
							"Confirmar desalocação de disciplinas eletivas",
							JOptionPane.YES_NO_OPTION
						);
				if(op == JOptionPane.YES_OPTION){
					new AllocationService().clearAllocation();
					DataUpdateCentral.registerUpdate("Alocação de eletivas defeita.");
				}
			}
		});
		mnEltivas.add(mntmLimparAlocao);
		
		JMenuItem mntmVerNoAlocadas = new JMenuItem("Ver não alocadas");
		mntmVerNoAlocadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ElectivePreferencesService prefService = new ElectivePreferencesService();
				AllocationResult allocResult = new AllocationService().getCurrentElectiveAllocation();
				String htmlText = prefService.getHtmlTableForPreferences(allocResult.notAllocated);
				JFrame frame = new HtmlTableFrame(htmlText);
				frame.setTitle("Turmas não alocadas (" + allocResult.notAllocated.size() + ")");
			}
		});
		mnEltivas.add(mntmVerNoAlocadas);
		
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
	
	/**
	 *Ao editar informações de uma sala 
	 */
	protected void onEditClassroomInformation(){
		updateWarningCountText();
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
