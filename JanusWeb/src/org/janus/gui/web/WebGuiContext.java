package org.janus.gui.web;

import java.io.Serializable;
import java.util.List;

import org.janus.gui.basis.JanusPage;
import org.janus.gui.basis.PageContext;


public class WebGuiContext extends PageContext implements Serializable{
	GuiElementChanges guiChanges;

	public WebGuiContext(JanusPage dict) {
		super(dict);
		guiChanges = new GuiElementChanges();
	}

	public List<ChangeOfGuiElement> getChanges() {
		return guiChanges.getChangeLog();
	}


	public void add(ChangeOfGuiElement change) {
		System.out.println("change: " + change);
		guiChanges.add(change);
	}


	public void clearChangeLog() {
		guiChanges.clearChangeLog();
	}


	public Serializable getValue(ChangeKey key) {
		return guiChanges.getValue(key);
	}
	
	

}
