package janus.tech.wcomponents;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;

public class WBox extends TemplateGuiComponent {
	public static final int HBOX = 0;
	public static final int VBOX = 1;
	
	int art;
	
	public int getBoxArt() {
		return art;
	}

	public WBox(WebGuiContext context,
			PrototypeGuiComponent prototyp,int art) {
		super(context, prototyp);
		this.art = art;
	}
	
	public float getSumValue(GuiComponent c) {
		if (art == HBOX){
			return c.getWidth();
		} else {
			return c.getHeight();
		}
	};
	
	public float getMaxValue(GuiComponent c) {
		if (art == HBOX){
			return c.getHeight();
		} else {
			return c.getWidth();
		}
	}
	
	public void setWidthAndHeight(float x,float y) {
		if (art == HBOX){
			setWidth(x);
			setHeight(y);
		} else {
			setWidth(y);
			setHeight(x);
		}
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
		float a = 1;
		float m = 0;
		for( GuiComponent c : children) {
			a += getSumValue(c);
			m = Math.max(m,getMaxValue(c));
		}
		setWidthAndHeight(a,m);
	}
	
	
	

	
	
}
