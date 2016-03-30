package org.janus.gui.web;



import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class GuiElementChanges implements Serializable{
	
	Vector<ChangeOfGuiElement> changeLog = new Vector<>();
	HashMap<ChangeKey,Serializable> aktualValues = new HashMap<>();


	private static final long serialVersionUID = 1L;

	
	public void add(ChangeOfGuiElement change) {
		changeLog.add(change);
		aktualValues.put(change.getKey(), change.getNewValue());
	}


	public List<ChangeOfGuiElement> getChangeLog() {
		return changeLog;
	}
	
	public void clearChangeLog() {
		changeLog.removeAllElements();
	}
	
	public Serializable getValue(ChangeKey key) {
		return aktualValues.get(key);
	}
}
