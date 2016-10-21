package janus.tech.wcomponents;

import java.io.Serializable;

import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;

public class WCheck extends WStandard {

	
	public WCheck(WebGuiContext context, PrototypeGuiComponent prototyp,
			float width, float height) {
		super(context, prototyp, width, height);
		
	}

	public boolean isSelected() {
		return "true".equals(getGuiValue());
	}
	
	@Override
	public Serializable getGuiValue() {
		return "true".equals(super.getGuiValue()) ? "true" : "false";
	}
	
}
