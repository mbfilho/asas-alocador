package presentation.state;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import presentation.NamedPair;

import logic.services.StateService;



import data.configurations.StateDescription;

import exceptions.StateIOException;

public class LoadStateFrame extends ChooseStateLayout {
	private static final long serialVersionUID = -8927467849438582539L;
	
	public LoadStateFrame(){
		setEditable(false);
		setVisible(true);
		
		stateList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				NamedPair<StateDescription> selected = stateList.getSelectedValue();
				if(selected != null)
					description.setText(selected.data.getDescription());
				else
					description.setText("");
			}
		});
	}
	
	protected void onOkButton() {
		if(getSelected() != null){
			StateService service = StateService.getInstance(); 
			try {
				service.setCurrentState(getSelected().data);
				this.dispose();
			} catch (StateIOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
