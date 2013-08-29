package presentation.excelPreferences;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class RoomMappingTableCellEditor extends AbstractCellEditor implements TableCellEditor{
	private static final long serialVersionUID = 7433664152948293436L;
	
	private MappedRoomEditor editorInterface;
	private JButton textField;
	
	public RoomMappingTableCellEditor(JFrame parent){
		editorInterface = new MappedRoomEditor(parent) {
			private static final long serialVersionUID = 458936152066772508L;

			public void onOkButton() {
				fireEditingStopped();
			}
		};
		
		textField = new JButton();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editorInterface.setVisible(true);
			}
		});
		editorInterface.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				fireEditingCanceled();
			}
		});
	}
	
	public Object getCellEditorValue() {
		return editorInterface.getSelectedValues();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		editorInterface.setSelectedList((List<String>) arg1);
		return textField;
	}
	

}
