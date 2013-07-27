package edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import basic.Classroom;
import basic.SlotRange;

public class EditableSlotList extends EditableJList<SlotRange> {

	public EditableSlotList(String title, final Vector<Classroom> allRooms) {
		super(title, null);
		
		for(ActionListener al : addButton.getActionListeners()) addButton.removeActionListener(al);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.out.println("Sim");
				new SlotChooser(allRooms) {
					public void onChooseSlot(Vector<SlotRange> chosen) {
						addElements(chosen);
						changeListener.actionPerformed(e);
					}
				};
			}
		});
	}

}
