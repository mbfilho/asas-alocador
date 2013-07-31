package visualizer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import statePersistence.LoadStateFrame;
import statePersistence.ChangeStateListener;
import statePersistence.NewStateFrame;
import statePersistence.State;
import statePersistence.StateService;
import validation.WarningService;

import java.awt.GridBagLayout;

import exceptions.StateIOException;

import javax.swing.KeyStroke;

import classEditor.ClassEditor;
import classrooms.AddClassroomFrame;
import classrooms.EditClassroomFrame;

import professors.AddProfessorFrame;
import professors.EditProfessorFrame;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;


public class FrameWithMenu extends JFrame{
	protected JMenuBar mainMenuBar;
	protected JMenu stateMenu;
	protected JMenuItem loadState;
	protected JMenuItem saveState;
	private WarningService warningService;
	private JMenu warningMenuItem;
	
	public FrameWithMenu(WarningService service) {
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
		mntmTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ClassEditor(warningService) {
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
		
		warningMenuItem = new JMenu("Alertas");
		menuBar.add(warningMenuItem);
		
		JMenuItem mntmMostrar = new JMenuItem("Mostrar");
		mntmMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = warningService.getAllWarnings().generateHtml();
				new WarningReportFrame(text);
			}
		});
		warningMenuItem.add(mntmMostrar);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}

	private void updateWarningCountText(){
		int count = warningService.getAllWarnings().getWarningCount();
		String text = "Alertas";
		if(count != 0) text += "(" + count + ")";
		warningMenuItem.setText(text);
	}
	
	protected void onEditProfessorInformation(){
		
	}
	
	//ao editar informações de uma sala
	protected void onEditClassroomInformation(){
		updateWarningCountText();
	}
	
	//ao carregar um estado previamente salvo
	protected void onLoadNewState(State s){
		updateWarningCountText();
	}
	//ao editar informações de uma turma
	protected void onEditClassInformation() {
		updateWarningCountText();
	}
}
