package utilities;

import java.util.Collection;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import basic.NamedEntity;

import classEditor.NamedPair;

public class GuiUtil {

	public static <T extends NamedPair> void setSelectedValue(JComboBox<T> cbox, Object value){
		ComboBoxModel<T> model = cbox.getModel();
		
		for(int i = 0; i < model.getSize(); ++i){
			NamedPair pair = model.getElementAt(i);
			if((pair.data == null && value == null) || (pair.data != null && pair.data.equals(value))){
				cbox.setSelectedIndex(i);
				break;
			}
		}
	}
	
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
	
}
