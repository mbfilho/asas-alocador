package edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;

import validation.WarningService;

import basic.Classroom;
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
	}

}
