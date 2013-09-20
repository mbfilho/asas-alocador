package presentation.classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import presentation.NamedPair;

import logic.services.WarningGeneratorService;

import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;

public abstract class EditableSlotList extends EditableJList<SlotRange>{
	private static final long serialVersionUID = -1251207105891014457L;

	public abstract Class getSelectedClass();

	public EditableSlotList(String title, final WarningGeneratorService service) {
		super(title, null);
		for(ActionListener al : addButton.getActionListeners()) addButton.removeActionListener(al);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				new SlotChooser(service, getSelectedClass()) {

					private static final long serialVersionUID = 4186798352142104915L;

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
						/**
						 * 
						 */
						 private static final long serialVersionUID = -4297526863022915837L;

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
		super.addElement((SlotRange) range.clone());
	}
}