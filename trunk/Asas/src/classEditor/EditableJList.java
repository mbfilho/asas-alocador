package classEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import basic.NamedEntity;


abstract class SelectElements<T extends NamedEntity> extends JFrame {
	private JList<NamedPair<T>> list;
	
	public abstract void OnSelect(Vector<T> selecteds);
	
	public SelectElements(Iterable<T> all){
		setLayout(null);
		setSize(400, 430);
		configureElements(all);
		setVisible(true);
	}

	private void configureElements(Iterable<T> all) {
		DefaultListModel<NamedPair<T>> model = new DefaultListModel();
		for(T item : all) model.addElement(new NamedPair<T>(item.getName(), item));
		
		list = new JList<NamedPair<T>>(model);
		JScrollPane jp = new JScrollPane(list);
		jp.setBounds(20, 30, 350, 300);
		add(jp);
		
		JButton okButton = new JButton("Ok");
		okButton.setBounds(140, 350, 100, 20);
		add(okButton);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Vector<T> selecteds = new Vector<T>();
				for(NamedPair<T> item : list.getSelectedValuesList()) selecteds.add(item.data);
				OnSelect(selecteds);
				setVisible(false);
			}
		});
	}
}

public class EditableJList<T extends NamedEntity> extends JPanel{
	private DefaultListModel<NamedPair<T>> model;
	protected JList<NamedPair<T>> list;
	protected JButton addButton;
	private JButton removeButton;
	private JLabel titleLabel;
	protected ActionListener changeListener;
	
	public EditableJList(String title, Iterable<T> allObjs){
		this.setLayout(null);
		this.setSize(370, 140);
		
		model = new DefaultListModel();
		configureElements(title, allObjs);
	}
	
	public void clear(){
		model.clear();
	}
	
	public void addElement(T obj){
		model.addElement(new NamedPair<T>(obj.getName(), obj));
	}
	
	public void addElements(Iterable<T> objs){
		for(T obj : objs) addElement(obj);
	}
	
	public void setChangeListener(ActionListener listener){
		changeListener = listener;
	}
	
	private void configureElements(String title, final Iterable<T> objs) {
		int y = 0, space = 10, height = 20;
		titleLabel = new JLabel(title);
		titleLabel.setBounds(0, y, 250, height);
		add(titleLabel);
		
		y += height + space;
		list = new JList(model);
		JScrollPane jp = new JScrollPane(list);
		jp.setBounds(0, y, 300+space, 100);
		add(jp);
		
		removeButton = new JButton("-");
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				List<NamedPair<T>> selecteds = list.getSelectedValuesList();
				for(Object item : selecteds)
					model.removeElement(item);
				changeListener.actionPerformed(e);
			}});
		removeButton.setBounds(300+2*space, y, 45, height);
		add(removeButton);
		
		addButton = new JButton("+");
		addButton.setBounds(300+2*space, y+height+space, 45, height);
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new SelectElements<T>(objs) {
					public void OnSelect(Vector<T> selecteds) {
						addElements(selecteds);
						changeListener.actionPerformed(null);
					}
				};
			}
		});
		add(addButton);
	}
	
	public Iterable<T> getItens(){
		Enumeration<NamedPair<T>> itens = model.elements();
		Vector<T> selectedItens = new Vector();
		while(itens.hasMoreElements()) selectedItens.add(itens.nextElement().data);
		return selectedItens;
	}
}

