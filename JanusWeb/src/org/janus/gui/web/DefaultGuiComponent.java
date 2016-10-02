package org.janus.gui.web;


import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.List;
import java.util.List; import java.util.ArrayList;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.enums.GuiField;
import org.janus.gui.enums.GuiType;

/**
 * Default Implementierung von {@link GuiComponent}
 * 
 * diese Implementierung merkt sich "kurzfristig" die neuen Änderungen von Attributen
 * um sie dann später über http zu versenden oder eine Swing, .... Komponente upzudaten
 * 
 * DatenObjekt
 * 
 * @author THOMAS
 * 
 */

public class DefaultGuiComponent implements WebGuiComponent {

	private static final long serialVersionUID = 4874068929136734100L;
	private WebGuiContext context;
	private PrototypeGuiComponent prototyp;
	protected List<WebGuiComponent> children = new ArrayList<>();
	
	
	public DefaultGuiComponent(WebGuiContext context,
			PrototypeGuiComponent prototyp) {
		super();
		this.context = context;
		this.prototyp = prototyp;
	}
	
	@Override
	public int getId() {
		return prototyp.getId();
	}

	@Override
	public GuiType getGuiType() {
		return prototyp.getGuiType();
	}

	public WebGuiContext getContext() {
		return context;
	}


	protected Serializable getValue(GuiField field) {
		Serializable value = context.getValue(prototyp.getKey(field));
		if (value!= null) {
			return value;
		}
		return prototyp.getField(field);
	}
	
	protected void setValue(GuiField field,Serializable value) {
		Serializable oldValue = getValue(field);
		ChangeOfGuiElement change = new ChangeOfGuiElement(prototyp.getKey(field), oldValue,value);
		context.add(change);
	}

	@Override
	public Font getFont() {
		return (Font)getValue(GuiField.FONT);
	}
	
	@Override
	public void setFont(Font font) {
		setValue(GuiField.FONT, font);
	}

	@Override
	public Color getForeground() {
		return (Color)getValue(GuiField.FOREGROUND);
	}

	@Override
	public void setForeground(Color foreground) {
		setValue(GuiField.FOREGROUND,foreground);
	}


	@Override
	public void setBackground(Color c) {
		setValue(GuiField.BACKGROUND,c);
		
	}

	@Override
	public Color getBackground() {
		return (Color)getValue(GuiField.BACKGROUND);
	}


	@Override
	public void setEnabled(boolean b) {
		setValue(GuiField.ENABLED,b);
		
	}


	@Override
	public boolean isEnabled() {
		return (boolean)getValue(GuiField.ENABLED);
	}


	@Override
	public void setVisible(boolean b) {
		setValue(GuiField.VISIBLE,b);
	}


	@Override
	public boolean isVisible() {
		return (boolean)getValue(GuiField.VISIBLE);
	}


	@Override
	public void setFocus(boolean b) {
		setValue(GuiField.FOCUS,b);
	}


	@Override
	public boolean hasFocus() {
		return (boolean)getValue(GuiField.FOCUS);
	}


	@Override
	public void setStyle(String t) {
		setValue(GuiField.STYLE,t);	}


	@Override
	public String getStyle() {
		return (String)getValue(GuiField.STYLE);
	}


	@Override
	public void setLabel(String t) {
		setValue(GuiField.LABEL,t);
	}


	@Override
	public String getLabel() {
		return (String)getValue(GuiField.LABEL);
	}


	@Override
	public void setTooltip(String t) {
		setValue(GuiField.TOOLTIP,t);	}


	@Override
	public String getTooltip() {
		return (String)getValue(GuiField.TOOLTIP);
	}


	@Override
	public void setWidth(float w) {
		setValue(GuiField.WIDTH,new Float(w));
		
	}


	@Override
	public float getWidth() {
		return ((Float)getValue(GuiField.WIDTH)).floatValue();	
	}


	@Override
	public void setHeight(float h) {
		setValue(GuiField.HEIGHT,new Float(h));
		
	}


	@Override
	public float getHeight() {
		return ((Float)getValue(GuiField.HEIGHT)).floatValue();	
	}


	@Override
	public float getX() {
		return ((Float)getValue(GuiField.X)).floatValue();	
	}


	@Override
	public void setX(float x) {
		setValue(GuiField.X,new Float(x));
	}


	@Override
	public float getY() {
		return ((Float)getValue(GuiField.Y)).floatValue();	
	}


	@Override
	public void setY(float y) {
		setValue(GuiField.Y,new Float(y));
		
	}


	@Override
	public void setGuiValue(Serializable v) {
		prototyp.setObject(context,v);
	}


	@Override
	public Serializable getGuiValue() {
		return prototyp.getObject(context);
	}


	@Override
	public void validate() {
	}


	@Override
	public void addComponent(GuiComponent comp) {
	    if (comp instanceof WebGuiComponent) {
	        children.add((WebGuiComponent)comp);
	    } else {
	        throw new IllegalArgumentException("Keine WebGuiComponente uebergeben");
	    }
	}

	@Override
	public List<WebGuiComponent> getChildComponents() {
		return children;
	}

	public void setPrototyp(PrototypeGuiComponent prototyp) {
		this.prototyp = prototyp;
	}

	protected PrototypeGuiComponent getPrototyp() {
		return prototyp;
	}

	public boolean checkValue(String parameterValue) {
		return prototyp.checkValue(parameterValue);
	}

}
