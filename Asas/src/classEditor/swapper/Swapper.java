package classEditor.swapper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import classEditor.NamedPair;
import classEditor.OrderedJListModel;

import services.ClassService;
import utilities.GuiUtil;
import basic.Class;
import basic.Professor;
import basic.SlotRange;

public abstract class Swapper extends SwapperLayout{
	private static final long serialVersionUID = 4568376455153350230L;
	
	private boolean swapOccured;
	private ClassService classService;
	private Class theClass;
	
	protected abstract void onOkButton(Collection<Professor> profs, Collection<SlotRange> slots);
	
	public Swapper(JFrame parent, Class selectedClass){
		super(parent, selectedClass);
		classService = new ClassService();
		theClass = selectedClass;
		swapOccured = false;
		
		fillComboBox();
		fillWithSelectedClass(selectedClass, getSelectedClassProfessorList(), getSelectedClassSlotsList());
		configureCombBoxEvent();
		configureProfessorSwap();
		configureSlotSwap();
		configureOkButton();
		
		setVisible(true);
	}
	
	private void fillComboBox(){
		DefaultComboBoxModel<NamedPair<Class>> model = (DefaultComboBoxModel<NamedPair<Class>>) getOtherClassCBox().getModel();
		model.addElement(new NamedPair<Class>("Selecione outra turma", null));
		for(NamedPair<Class> pair : GuiUtil.createNamedPairs(classService.all()))
			model.addElement(pair);
	}
	
	private void fillWithSelectedClass(Class selected, JList<NamedPair<Professor>> profs, JList<NamedPair<SlotRange>> slots){
		OrderedJListModel<Professor, NamedPair<Professor>> profModel = (OrderedJListModel<Professor, NamedPair<Professor>>) profs.getModel();
		OrderedJListModel<SlotRange, NamedPair<SlotRange>> slotModel = (OrderedJListModel<SlotRange, NamedPair<SlotRange>>) slots.getModel();
		profModel.clear(); slotModel.clear();
		for(Professor p : selected.getProfessors())
			profModel.addInOrder(new NamedPair<Professor>(p.getName(), p));
			for(SlotRange slot : selected.getSlots())
			slotModel.addInOrder(new NamedPair<SlotRange>(slot.getName(), slot.clone()));
	}
	
	private void configureCombBoxEvent(){
		getOtherClassCBox().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(swapOccured){
					int op = JOptionPane.showConfirmDialog(Swapper.this,
								"As mudanças feitas até agora serão perdidas. Deseja continuar?",
								"Atenção!",
								JOptionPane.YES_NO_OPTION
							);
					if(op != JOptionPane.YES_OPTION) return;
				}
				Class selected = GuiUtil.getSelectedItem(getOtherClassCBox());
				fillWithSelectedClass(selected, getOtherClassProfessorList(), getOtherClassSlotsList());
				swapOccured = false;
			}
		});
	}
	
	private void configureProfessorSwap(){
		getSwapProfessorsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderedJListModel<Professor, NamedPair<Professor>> modelOfSelectedClass, modelOfOtherClass;
				modelOfSelectedClass = (OrderedJListModel<Professor, NamedPair<Professor>>) getSelectedClassProfessorList().getModel();
				modelOfOtherClass = (OrderedJListModel<Professor, NamedPair<Professor>>) getOtherClassProfessorList().getModel();
				
				NamedPair<Professor> p1 = getSelectedClassProfessorList().getSelectedValue();
				NamedPair<Professor> p2 = getOtherClassProfessorList().getSelectedValue();
				
				modelOfSelectedClass.remove(p1); modelOfOtherClass.remove(p2);
				modelOfSelectedClass.addInOrder(p2); modelOfOtherClass.addInOrder(p1);
				swapOccured |= p1.data != p2.data;
			}
		});
	}
	
	private void configureSlotSwap(){
		getSwapSlotsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderedJListModel<SlotRange, NamedPair<SlotRange>> modelOfSelectedClass, modelOfOtherClass;
				modelOfSelectedClass = (OrderedJListModel<SlotRange, NamedPair<SlotRange>>) getSelectedClassSlotsList().getModel();
				modelOfOtherClass = (OrderedJListModel<SlotRange, NamedPair<SlotRange>>) getOtherClassSlotsList().getModel();
				NamedPair<SlotRange> r1 = getSelectedClassSlotsList().getSelectedValue();
				NamedPair<SlotRange> r2 = getOtherClassSlotsList().getSelectedValue();
				
				modelOfSelectedClass.remove(r1); modelOfOtherClass.remove(r2);
				
				if(getSwapTimeCheck().isSelected()) r1.data.swapTime(r2.data);
				if(getSwapRoomCheck().isSelected())	r1.data.swapClassroom(r2.data);
				
				modelOfSelectedClass.addInOrder(new NamedPair<SlotRange>(r1.data.getName(), r1.data));
				modelOfOtherClass.addInOrder(new NamedPair<SlotRange>(r2.data.getName(), r2.data));
				swapOccured = true;
			}
		});
	}
	
	private void changeClass(Class c, JList<NamedPair<Professor>> profs, JList<NamedPair<SlotRange>> slots){
		c.getProfessors().clear();
		c.getSlots().clear();
		
		{
			OrderedJListModel<Professor, NamedPair<Professor>> model = (OrderedJListModel<Professor, NamedPair<Professor>>) profs.getModel();
			c.getProfessors().addAll(model.getAllDataElements());
		}
		{
			OrderedJListModel<SlotRange, NamedPair<SlotRange>> model = (OrderedJListModel<SlotRange, NamedPair<SlotRange>>) slots.getModel();
			c.getSlots().addAll(model.getAllDataElements());
		}
	}
	
	private void configureOkButton(){
		getOkButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Class other = GuiUtil.getSelectedItem(getOtherClassCBox());
				if(swapOccured && other != null){
					changeClass(theClass, getSelectedClassProfessorList(), getSelectedClassSlotsList());
					changeClass(other, getOtherClassProfessorList(), getOtherClassSlotsList());
					classService.completeSwap(theClass, other);
					onOkButton(theClass.getProfessors(), theClass.getSlots());
				}
				dispose();
			}
		});
	}
}
