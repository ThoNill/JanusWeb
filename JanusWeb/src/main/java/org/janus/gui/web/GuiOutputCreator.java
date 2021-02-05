package org.janus.gui.web;



import java.util.List;



public abstract class GuiOutputCreator<K>  {

	public GuiOutputCreator() {

	}

	public void createOutput(WebGuiComponent connector, WebGuiContext context,
			K out) {
		WebGuiComponent comp = getWebGuiComponent(connector, context);
		pre(comp,context, out);
		List<? extends WebGuiComponent> connectors = connector.getChildComponents();
		if (connectors != null) {
			preAllChilds(comp, context,out);
			for (WebGuiComponent c : connectors) {
				preChild(c,context, out);
				createOutput(c, context, out);
				postChild(c, context,out);
			}
			preAllChilds(comp,context, out);
		}
		post(comp, context,out);
	}

	private WebGuiComponent getWebGuiComponent(WebGuiComponent connector,
			WebGuiContext context) {
		return null;
	}

	protected abstract void postChild(WebGuiComponent c,WebGuiContext context, K out);

	protected abstract void preChild(WebGuiComponent c,WebGuiContext context, K out);

	protected abstract void preAllChilds(WebGuiComponent comp,WebGuiContext context, K out);

	protected abstract void post(WebGuiComponent comp,WebGuiContext context, K out);

	protected abstract void pre(WebGuiComponent comp,WebGuiContext context, K out);
}
