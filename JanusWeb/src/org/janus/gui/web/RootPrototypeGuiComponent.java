package org.janus.gui.web;

import java.util.List;
import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.basis.RootGuiComponent;
import org.janus.gui.enums.GuiType;

public class RootPrototypeGuiComponent extends PrototypeGuiComponent implements RootGuiComponent{
	List<GuiComponent> allComponents;



	/**
	 * 
	 */
	private static final long serialVersionUID = -3097672267086842828L;

	public RootPrototypeGuiComponent(GuiType type, JanusPage page) {
		super(type, page);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<GuiComponent> getAllComponents() {
		return allComponents;
	}

	@Override
	public void setAllComponents(List<GuiComponent> components) {
		allComponents = components;
		
	}

}
