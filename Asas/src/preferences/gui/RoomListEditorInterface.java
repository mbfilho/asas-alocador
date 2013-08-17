package preferences.gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import javax.swing.CellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import basic.Classroom;

import services.ClassroomService;

public abstract class RoomListEditorInterface extends RoomListEditorInterfaceLayout {

	private ClassroomService roomService;
	private DefaultComboBoxModel<String> cboxModel;
	private DefaultListModel<String> listModel;
	
	public abstract void onOkButton();
	
	public RoomListEditorInterface(JFrame parent){
		super(parent);
		roomService = new ClassroomService();
		cboxModel = (DefaultComboBoxModel<String>) (getAllRoomsCBox().getModel());
		listModel = (DefaultListModel<String>) getRoomsList().getModel();
		configureActions();
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
		for(Classroom room : roomService.all())
			cboxModel.addElement(room.getName());
		cboxModel.addElement("D-005");
		cboxModel.addElement("D-004");
		
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
