package classEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;

import javax.swing.JComboBox;

import services.WarningGeneratorService;


import basic.SlotRange;
import basic.Class;

public abstract class EditableSlotList extends EditableJList<SlotRange> {

	public abstract Class getSelectedClass();
	
	public EditableSlotList(String title, final WarningGeneratorService service) {
		super(title, null);
		
		for(ActionListener al : addButton.getActionListeners()) addButton.removeActionListener(al);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				new SlotChooser(service, getSelectedClass()) {
					public void onChooseSlot(SlotRange chosen) {
						addElement(chosen);
						changeListener.actionPerformed(e);
					}
				};
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(java.awt.event.MouseEvent arg){
				  if(arg.getClickCount() == 2){
					  final NamedPair<SlotRange> selectedSlotRange = list.getSelectedValue();
					  new SlotChooser(service, getSelectedClass(), selectedSlotRange.data) {
						public void onChooseSlot(SlotRange chosen) {
							selectedSlotRange.name = chosen.getName();
							changeListener.actionPerformed(null);
							list.repaint();
						}
					};
				  }
			  }
		});
	}
	
	public void addElement(SlotRange range){
		try {
			super.addElement((SlotRange) range.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

}
