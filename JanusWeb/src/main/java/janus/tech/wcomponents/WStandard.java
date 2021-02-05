package janus.tech.wcomponents;

import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;


public class WStandard extends TemplateGuiComponent {
	float w;
	float h;
	
	public WStandard(WebGuiContext context,
			PrototypeGuiComponent prototyp,float width,float height) {
		super(context, prototyp);
		w = width;
		h = height;
	}
	
	public void calculate() {
		if (super.getWidth()<w) {
			super.setWidth(w);
		};
		if (super.getHeight()<h) {
			super.setHeight(h);
		}
	}
	
	@Override
	public float getHeight() {
		float x = super.getHeight();
		if (x < 0) {
			calculate();
			return super.getHeight();
		}
		return x;
	}

	@Override
	public float getWidth() {
		float x = super.getWidth();
		if (x < 0) {
			calculate();
			return super.getWidth();
		}
		return x;
	}

	
	
}
