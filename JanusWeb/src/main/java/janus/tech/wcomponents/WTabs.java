package janus.tech.wcomponents;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;


public class WTabs extends TemplateGuiComponent {
	

	public WTabs(WebGuiContext context,
			PrototypeGuiComponent prototyp) {
		super(context, prototyp);
	}
	


	
	@Override
	public float getHeight() {
		float x = super.getHeight();
		if (x < 0) {
			calculateSize();
			return super.getHeight();
		}
		return x;
	}
	
	public float getWidtht() {
		float x = super.getWidth();
		if (x < 0) {
			calculateSize();
			return super.getWidth();
		}
		return x;
	}

	public void calculateSize() {
		float w = 0;
		float h = 0;
		for( GuiComponent c : children) {
			w = Math.max(w,c.getWidth());
			h = Math.max(h,c.getHeight());
		}
		setWidth(w);
		setHeight(h+2.0f);
		
	}
	
	


	
	
}
