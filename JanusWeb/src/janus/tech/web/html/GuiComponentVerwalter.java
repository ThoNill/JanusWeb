package janus.tech.web.html;

import java.util.Hashtable;
import java.util.Iterator;

import org.janus.gui.basis.JanusPage;
import org.janus.gui.web.PrototypeGuiComponent;

public class GuiComponentVerwalter {
	private static GuiComponentVerwalter singletonVerwalter = new GuiComponentVerwalter();

	private Hashtable<Integer, PrototypeGuiComponent> connectors = null;

	private GuiComponentVerwalter() {
		connectors = new Hashtable<Integer,PrototypeGuiComponent>();
	}

	public void add(PrototypeGuiComponent connector) {
		if(!connectors.containsKey(connector.getId())){
			connectors.put(connector.getId(), connector);
		}
	}

	public PrototypeGuiComponent getComponent(int n) {
		return connectors.get(n);
	}

	public JanusPage getPagel(int n) {
		return connectors.get(n).getPage();
	}

	public static GuiComponentVerwalter getVerwalter() {
		return singletonVerwalter;
	}

	public int getId(String pageName, String name) {
		Iterator<PrototypeGuiComponent> i = connectors.values().iterator();
		while (i.hasNext()) {
			PrototypeGuiComponent connector = i.next();
			if (pageName.equals(connector.getPage().getName())) {

				String test = name + connector.getId();
				if (test.equals(connector.getName())) {
					return connector.getId();
				}
			}

		}
		return -1;
	}
}
