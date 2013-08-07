package statePersistence;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import classEditor.NamedPair;

import exceptions.StateIOException;

public class LoadStateFrame extends ChooseStateFrame {
	private static final long serialVersionUID = -8927467849438582539L;
	
	private ChangeStateListener loadStateListener;
	
	public LoadStateFrame(ChangeStateListener loadListener){
		setEditable(false);
		setVisible(true);
		this.loadStateListener = loadListener;
		
		stateList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				NamedPair<StateDescription> selected = stateList.getSelectedValue();
				if(selected != null){
					draftCheck.setSelected(selected.data.isDraft());
					description.setText(selected.data.getDescription());
				}
				else{
					draftCheck.setSelected(false);
					description.setText("");
				}
			}
		});
	}
	
	protected void onOkButton() {
		if(getSelected() != null){
			boolean canLoad = true;
			if(getSelected().data.isDraft()){
				String message = "Você está carregando um estado salvo como 'draft'.\n"+
						"Suas alterações serão periodicamente salvas e, portanto, esse estado poderá\n"+
						"ser alterado. É recomendado que os drafts sejam somente leitura, para que\n"+
						"sirvam como base em alocações futuras. Caso deseje usar esse draft como base\n"+
						"para uma nova alocação use a opção 'Estado > Nova alocação'.\n"+
						"Deseja continuar?";	
				int ans = JOptionPane.showOptionDialog(this, message, "Atanção!", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, 
						new String[]{"Sim", "Não", "Cancelar"}, "Não");
				canLoad = (ans == JOptionPane.YES_OPTION);
			}
			
			if(!canLoad) return;
			
			StateService service = StateService.getInstance(); 
			try {
				service.setCurrentState(getSelected().data);
				loadStateListener.onChangeState(service.getCurrentState());
				this.dispose();
			} catch (StateIOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}