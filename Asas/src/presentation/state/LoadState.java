package presentation.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import logic.services.StateService;

import data.configurations.StateDescription;
import exceptions.StateIOException;

public class LoadState extends LoadStateLayout {
	private static final long serialVersionUID = -7084701940128455895L;

	public LoadState(){
		setVisible(true);
		configureOkButton();
		configureDoubleClick();
		configureRemoveButton();
	}
	
	private void configureOkButton(){
		getOkButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadSelectedState();
			}
		});
	}
	
	private void configureDoubleClick(){
		getStateTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg) {
				if(arg.getClickCount() == 2)
					loadSelectedState();
			}
		});
		
	}
	
	private void configureRemoveButton(){
		getRemoveStateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int op = JOptionPane.showOptionDialog(
						LoadState.this, 
						"Essa operação não poderá ser revertida. Deseja continuar?",
						"Atenção",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, 
						null, 
						new String[]{"Sim", "Não"}, 
						"Não"
					);
				System.out.println(op);
				if(op == 0){
					StateDescription selected = getSelectedDescription();
					StateService.getInstance().remove(selected);
					getTableModel().remove(selected);
				}
			}
		});
	}
	
	private StateDescription getSelectedDescription(){
		int row = getStateTable().getSelectedRow();
		if(row != -1)
			return getTableModel().getStateDescriptionAt(row);
		return null;
	}
	
	private void loadSelectedState(){
		StateDescription selected = getSelectedDescription();
		if(selected != null){
			StateService stateService = StateService.getInstance();
			
			try {
				stateService.setCurrentState(selected);
				dispose();
			} catch (StateIOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(LoadState.this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
