package org.janus.gui.web;



import java.util.List;

import org.janus.gui.basis.GuiComponent;

public abstract class GuiOutputCreator<K>  {

	public GuiOutputCreator() {

	}

	public void createOutput(GuiComponent connector, WebGuiContext context,
			K out) {
		GuiComponent comp = getGuiComponent(connector, context);
		pre(comp,context, out);
		List<GuiComponent> connectors = connector.getChildComponents();
		if (connectors != null) {
			preAllChilds(comp, context,out);
			for (GuiComponent c : connectors) {
				preChild(c,context, out);
				createOutput(c, context, out);
				postChild(c, context,out);
			}
			preAllChilds(comp,context, out);
		}
		post(comp, context,out);
	}

	private GuiComponent getGuiComponent(GuiComponent connector,
			WebGuiContext context) {
		return null;
	}

	protected abstract void postChild(GuiComponent c,WebGuiContext context, K out);

	protected abstract void preChild(GuiComponent c,WebGuiContext context, K out);

	protected abstract void preAllChilds(GuiComponent comp,WebGuiContext context, K out);

	protected abstract void post(GuiComponent comp,WebGuiContext context, K out);

	protected abstract void pre(GuiComponent comp,WebGuiContext context, K out);
}
