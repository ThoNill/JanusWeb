package janus.tech.wcomponents;

import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;


public class WFrame extends WBox {
	WMenuBar menuBar;

	public WFrame(WebGuiContext context,
			PrototypeGuiComponent prototyp) {
		super(context, prototyp, WBox.VBOX);
	}
	
	public WMenuBar getMenuBar() {
		return menuBar;
	}

	public boolean isWithMenueBar() {
		return menuBar != null && menuBar.getChildComponents().size() > 0;
	}
	
	public void setMenuBar(WMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public void layout() {
		calculateSize();
	}

}
