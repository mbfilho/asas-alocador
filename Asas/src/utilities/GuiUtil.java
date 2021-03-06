package utilities;

import java.awt.Component;
import java.io.File;
import java.util.Collection;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;

import presentation.NamedPair;

import data.NamedEntity;

public class GuiUtil {

	public static <T extends NamedPair<?>> void setSelectedValue(JComboBox<T> cbox, Object value){
		ComboBoxModel<T> model = cbox.getModel();
		
		for(int i = 0; i < model.getSize(); ++i){
			NamedPair<?> pair = model.getElementAt(i);
			if((pair.data == null && value == null) || (pair.data != null && pair.data.equals(value))){
				cbox.setSelectedIndex(i);
				break;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSelectedItem(JComboBox<NamedPair<T>> box){
		NamedPair<T> pair = (NamedPair<T>) box.getSelectedItem();
		if(pair == null) return null;
		return pair.data;
	}
	
	public static <T extends NamedEntity> Vector<NamedPair<T>> createNamedPairs(Collection<T> objs){
		Vector<NamedPair<T>> namedPairs = new Vector<NamedPair<T>>();
		for(T obj : objs)
			namedPairs.add(new NamedPair<T>(StringUtil.truncate(obj.getName(), 70), obj));
		return namedPairs;
	}
	
	public static void selectTabWithThisTitle(JTabbedPane pane, String title){
		if(null == title) return;
		
		for(int i = 0; i < pane.getTabCount(); ++i){
			if(pane.getTitleAt(i).equals(title)){
				pane.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public static String getSelectedTabTitle(JTabbedPane pane){
		int selectedIndex = pane.getSelectedIndex();
		if(selectedIndex == -1) return null;
		return pane.getTitleAt(selectedIndex);
	}

	public static <T extends NamedEntity> void reloadCBox(JComboBox<NamedPair<T>> cbox, Collection<T> allElements, NamedPair<T> first){
		T selected = GuiUtil.getSelectedItem(cbox);
		DefaultComboBoxModel<NamedPair<T>> model = (DefaultComboBoxModel<NamedPair<T>>) cbox.getModel();
		model.removeAllElements();
		NamedPair<T> newSelected = first;
		model.addElement(first);
		for(T elementToAdd : allElements){
			NamedPair<T> toAdd = new NamedPair<T>(elementToAdd.getName(), elementToAdd);
			if(selected != null && elementToAdd.getName().equals(selected.getName())) newSelected = toAdd;
			model.addElement(toAdd);
		}
		cbox.setSelectedItem(newSelected);
	}
	
	public static File promptForHtmlFileCreation(Component parent, File defaultLocation){
		JFileChooser chooser = new JFileChooser(defaultLocation);
		if(chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION){
			File f = chooser.getSelectedFile();
			String name = f.getAbsolutePath();
			if(!(name.endsWith(".html") || name.endsWith(".html")))
				f = new File(name + ".html");
			return f;
		}
		return null;
	}
}
