package visualizer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import services.AllocationService;
import services.ElectivePreferencesService;
import services.WarningGeneratorService;
import statePersistence.LoadStateFrame;
import statePersistence.ChangeStateListener;
import statePersistence.NewStateFrame;
import statePersistence.State;
import statePersistence.StateService;
import warnings.Warning;
import warningsTable.WarningTable;

import java.awt.GridBagLayout;

import exceptions.StateIOException;

import javax.swing.KeyStroke;

import allocation.AllocationResult;
import allocation.Allocator;
import allocation.DefaultAllocator;

import classEditor.AddClassFrame;
import classEditor.EditClassFrame;
import classEditor.NamedPair;
import classrooms.AddClassroomFrame;
import classrooms.EditClassroomFrame;

import professors.AddProfessorFrame;
import professors.EditProfessorFrame;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.util.Vector;


public class FrameWithMenu extends JFrame{
	protected JMenuBar mainMenuBar;
	protected JMenu stateMenu;
	protected JMenuItem loadState;
	protected JMenuItem saveState;
	private WarningGeneratorService warningService;
	private JMenu warningMenuItem;
	
	public FrameWithMenu(WarningGeneratorService service) {
		warningService = service;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnEstado = new JMenu("Estado");
		menuBar.add(mnEstado);
		
		JMenuItem mntmCarregar = new JMenuItem("Carregar");
		mntmCarregar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoadStateFrame(new ChangeStateListener() {
					public void onChangeState(State loaded) {
						onLoadNewState(loaded);
					}
				});
			}
		});
		
		JMenuItem mntmNovaAlocao = new JMenuItem("Nova alocação");
		mntmNovaAlocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewStateFrame(new ChangeStateListener() {
					public void onChangeState(State loaded) {
						onLoadNewState(loaded);
					}
				});
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
		mntmSalvar_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		mnEstado.add(mntmSalvar_1);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		JMenuItem mntmTurmas = new JMenuItem("Turmas");
		mntmTurmas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		mntmTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditClassFrame(warningService) {
					public void classInformationEdited() {
						onEditClassInformation();
					}
				};
			}
		});
		
		JMenuItem mntmProfessores = new JMenuItem("Professores");
		mntmProfessores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditProfessorFrame(){
					protected void onOkButton(){
						super.onOkButton();
						onEditProfessorInformation();
					}
				};
			}
		});
		
		mnEditar.add(mntmProfessores);
		
		JMenuItem mntmSalas = new JMenuItem("Salas");
		mntmSalas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditClassroomFrame(){
					public void onOkButton(){
						super.onOkButton();
						onEditClassroomInformation();
					}
				};
			}
		});
		mnEditar.add(mntmSalas);
		mnEditar.add(mntmTurmas);
		
		JMenu mnAdicionar = new JMenu("Adicionar");
		menuBar.add(mnAdicionar);
		
		JMenuItem mntmProfessor = new JMenuItem("Professor");
		mntmProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddProfessorFrame();
			}
		});
		mnAdicionar.add(mntmProfessor);
		
		JMenuItem mntmSala = new JMenuItem("Sala");
		mntmSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddClassroomFrame();
			}
		});
		mnAdicionar.add(mntmSala);
		
		JMenuItem mntmTurma = new JMenuItem("Turma");
		mntmTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddClassFrame(warningService){
					public void onAddClass() {
						onEditClassInformation();
					}
				};
			}
		});
		mnAdicionar.add(mntmTurma);
		
		warningMenuItem = new JMenu("Alertas");
		menuBar.add(warningMenuItem);
		
		JMenuItem mntmProvisorio = new JMenuItem("Mostar");
		mntmProvisorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				WarningGeneratorService service = new WarningGeneratorService();
				
				JTabbedPane pane = new JTabbedPane();
				for(NamedPair<Vector<Warning>> report : service.getAllWarnings().getAllReports()){
					WarningTable table = new WarningTable(report.data){
						public void onChangeWarningAllowance() {
							onEditWarningInformation();
						}
					};
					pane.addTab(report.name, new JScrollPane(table));
				}
				
				frame.add(new JScrollPane(pane));
				frame.setVisible(true);
			}
		});
		warningMenuItem.add(mntmProvisorio);
		
		JMenu mnEltivas = new JMenu("Eletivas");
		menuBar.add(mnEltivas);
		
		JMenuItem mntmprovisrioVer = new JMenuItem("(provisório) Ver");
		mntmprovisrioVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				JTextPane panel = new JTextPane();
				panel.setContentType("text/html");
				ElectivePreferencesService service = new ElectivePreferencesService();
				panel.setText(service.getHtmlTableForPreferences(service.all()));
				frame.getContentPane().add(new JScrollPane(panel));
				frame.setVisible(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		mnEltivas.add(mntmprovisrioVer);
		
		JMenuItem mntmAlocar = new JMenuItem("Alocar");
		mntmAlocar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Allocator aloc = new DefaultAllocator();
				AllocationResult result = aloc.allocate(true);

				JFrame frame = new JFrame();
				JTextPane panel = new JTextPane();
				panel.setContentType("text/html");
				panel.setText(new ElectivePreferencesService().getHtmlTableForPreferences(result.notAllocated));
				frame.getContentPane().add(new JScrollPane(panel));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
				frame.setVisible(true);
				frame.setTitle("Turmas não alocadas (" + result.notAllocated.size() + ")");
				onEditClassInformation();
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
					onEditClassInformation();
				}
			}
		});
		mnEltivas.add(mntmLimparAlocao);
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
	 * Ao autorizar/desautorizar um alerta
	 */
	protected void onEditWarningInformation(){
		updateWarningCountText();
	}
	/**
		Ao editar informações de um professor
	 */
	protected void onEditProfessorInformation(){
		updateWarningCountText();
	}
	/**
	 *Ao editar informações de uma sala 
	 */
	protected void onEditClassroomInformation(){
		updateWarningCountText();
	}
	
	/**
	 * Ao carregar um estado previamente salvo 
	 * @param s - O estado carregado
	 */
	protected void onLoadNewState(State s){
		updateWarningCountText();
	}
	
	/**
	 *Ao editar informações de uma turma 
	 */
	protected void onEditClassInformation() {
		updateWarningCountText();
	}
}
