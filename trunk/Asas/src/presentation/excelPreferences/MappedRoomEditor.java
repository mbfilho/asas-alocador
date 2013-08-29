package presentation.excelPreferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import utilities.ClassroomList;

public abstract class MappedRoomEditor extends MappedRoomEditorLayout {
	private static final long serialVersionUID = -8917701648713941524L;
	
	private DefaultComboBoxModel<String> cboxModel;
	private DefaultListModel<String> listModel;
	
	public abstract void onOkButton();
	
	public MappedRoomEditor(JFrame parent){
		super(parent);
		cboxModel = (DefaultComboBoxModel<String>) (getAllRoomsCBox().getModel());
		listModel = (DefaultListModel<String>) getRoomsList().getModel();
		addRoomsToCBox();
		configureActions();
	}

	private void addRoomsToCBox() {
		for(String roomName : ClassroomList.readClassroomListFromFile())
			cboxModel.addElement(roomName);
	}
	
	private void moveSelected(int shift){
		int from = getRoomsList().getSelectedIndex(), to = from + shift;
		if(from != -1 && to >= 0 && to < listModel.getSize()){
			String element = listModel.get(from);
			listModel.remove(from);
			listModel.add(to, element);
			getRoomsList().setSelectedIndex(to);
		}
	}
	
	private void configureActions(){
		getUpButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveSelected(-1);
			}
		});
		
		getDownButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveSelected(1);
			}
		});
		
		getAddButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String room = (String) getAllRoomsCBox().getSelectedItem();
				if(room != null) 
					listModel.addElement(room);
			}
		});
		
		getRemoveButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getRoomsList().getSelectedIndex() != -1)
					listModel.remove(getRoomsList().getSelectedIndex());
			}
		});
		
		getOkButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onOkButton();
				setVisible(false);
			}
		});
	}
	
	public void setSelectedList(List<String> selected){
		listModel.clear();
		for(String s : selected) listModel.addElement(s);
	}
	
	public List<String> getSelectedValues(){
		List<String> selected = new LinkedList<String>();
		for(int i = 0; i < listModel.getSize(); ++i) selected.add(listModel.get(i));
		return selected;
	}
}
