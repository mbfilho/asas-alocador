package classEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;

import javax.swing.JComboBox;

import validation.WarningService;

import basic.SlotRange;
import basic.Class;

public class EditableSlotList extends EditableJList<SlotRange> {

	public EditableSlotList(String title, final WarningService service, final JComboBox<NamedPair<Class>> classesToSelect) {
		super(title, null);
		
		for(ActionListener al : addButton.getActionListeners()) addButton.removeActionListener(al);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				NamedPair<Class> pair = (NamedPair<Class>) classesToSelect.getSelectedItem();
				new SlotChooser(service, pair.data) {
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
					  final NamedPair<Class> pair = (NamedPair<Class>) classesToSelect.getSelectedItem();
					  final NamedPair<SlotRange> selectedSlotRange = list.getSelectedValue();
					  
					  new SlotChooser(service, pair.data, selectedSlotRange.data) {
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

}
