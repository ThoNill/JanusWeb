package janus.tech.wcomponents;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;

public class FromPrototypToTemplateComponents {

	
	public static TemplateGuiComponent createTemplateElement(WebGuiContext context,PrototypeGuiComponent prototyp) {
		TemplateGuiComponent comp = createTemplateElementIntern(context, prototyp);
		for(GuiComponent pchild : prototyp.getChildComponents()) {
			TemplateGuiComponent tcomp = createTemplateElement(context, (PrototypeGuiComponent)pchild);
			comp.addComponent(tcomp);
		}
		return comp;
	}
	
	private static TemplateGuiComponent createTemplateElementIntern(WebGuiContext context,PrototypeGuiComponent prototyp) {
		switch (prototyp.getGuiType()) {
		case MENUBAR:
			return new WMenuBar(context, prototyp);
		case HBOX:;
			return new WBox(context, prototyp,WBox.HBOX);			
		case VBOX:;
			return new WBox(context, prototyp,WBox.VBOX);		
		case TABS:;
			return new WTabs(context, prototyp);					
		case SHOWTABLE:;
		case LIST:;
		case COMBO:;
		case RADIO:;
			return new WTable(context, prototyp);	
		case CHECK:;
			return new WCheck(context, prototyp,8.0f,1.0f);				
		case GUI: return new WFrame(context, prototyp);
		}
		return new TemplateGuiComponent(context, prototyp);
	}


	


}
