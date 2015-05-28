package org.janus.gui.web;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.janus.actions.ReadValue;
import org.janus.actions.WriteValue;
import org.janus.data.DataContext;
import org.janus.dict.actions.NamedActionValue;
import org.janus.dict.helper.ID;
import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.enums.GuiField;
import org.janus.gui.enums.GuiType;

/**
 * Dieses GUI Objekt speichert die Defaultwerte eder Propertie, die von XML Attributen
 * festgelegt werden und nicht mehr geändert werden. Diese Prototypen können von
 * verschiedenen Sessions gemeinsam genutzt werden.
 * 
 * @author THOMAS
 * 
 */

public class PrototypeGuiComponent implements GuiComponent, Serializable, ReadValue, WriteValue {

	private Color foreground;
	private Color background;
	private boolean enabled;
	private boolean focus;
	private boolean visible;
	private String style;
	private String label;
	private Font font;
	private String tooltip;
	private float width;
	private float height;
	private float x;
	private float y;
	private Serializable guiValue;
	private GuiType type;
	private int id;
	private HashMap<GuiField, ChangeKey> changeKeys = new HashMap<>(); 
	private Vector<GuiComponent> childComponents = null;
	private NamedActionValue value;
	private JanusPage page;
	private Pattern pattern = null;
	private int length=0;
	


	
	public PrototypeGuiComponent(GuiType type,JanusPage page) {
		super();
		this.type = type;
		id = ID.getId();
		changeKeys = new HashMap<GuiField, ChangeKey>();
		this.page = page;
	}
	
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public void setPattern(String patternString) {
		setPattern(Pattern.compile(patternString));
	}

	@Override
	public int getId() {
		return id;
	}
	
	
	public String getName() {
		if (value != null) {
			return value.getName() + id;
		}
		return null;
	}
	
	
	public synchronized ChangeKey getKey(GuiField field) {
		ChangeKey key = changeKeys.get(field);
		if (key == null) {
			key = new ChangeKey(id, field);
			changeKeys.put(field, key);
		}
		return key;
	}		
	




	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public Color getForeground() {
		return foreground;
	}

	@Override
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	@Override
	public Color getBackground() {
		return background;
	}

	@Override
	public void setBackground(Color background) {
		this.background = background;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean hasFocus() {
		return focus;
	}

	@Override
	public void setFocus(boolean focus) {
		this.focus = focus;

	}

	@Override
	public String getStyle() {
		return style;
	}

	@Override
	public void setStyle(String style) {
		this.style = style;

	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;

	}

	@Override
	public String getTooltip() {
		return tooltip;
	}

	@Override
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}


	@Override
	public void validate() {
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();

	}

	@Override
	public GuiType getGuiType() {
		return type;
	}

	@Override
	public void setGuiValue(Serializable v) {
		guiValue = v;
	}

	@Override
	public Serializable getGuiValue() {
		return guiValue;
	}

	public Serializable getField(GuiField field) {
		switch(field) {
			case BACKGROUND : return background;
			case FOREGROUND : return foreground;
			case FONT : return font;
			case ENABLED : return enabled;
			case VISIBLE : return visible;
			case FOCUS : return focus;
			case LABEL : return label;
			case STYLE : return style;
			case TOOLTIP : return tooltip;
			case WIDTH : return width;
			case HEIGHT : return height;
			case X : return x;
			case Y : return y;
		}
		return null;
	}




	
	@Override
	public void addComponent(GuiComponent comp) {
		if (childComponents==null) {
			childComponents = new Vector<>();
		}
		childComponents.add(comp);
	}

	@Override
	public List<GuiComponent> getChildComponents() {
		if (childComponents == null) {
			return Collections.emptyList();
		}
		return childComponents;
	}
	
	protected NamedActionValue getValue() {
		return value;
	}

	protected void setValue(NamedActionValue value) {
		this.value = value;
	}
	
	public void setGuiValue(DataContext context, Serializable v) {
	}


	

	@Override
	public void setObject(DataContext context, Serializable v) {
		if (GuiType.BUTTON == type || GuiType.MENUITEM == type) {
			value.perform(context);
		} else {
			value.setObject(context,v);
		}
		
	}

	@Override
	public Serializable getObject(DataContext context) {
		return value.getObject(context);
	}


	public JanusPage getPage() {
		return page;
	}
	
	public boolean checkValue(String paramValue) {
		
		if (value instanceof WriteValue) {
			if (paramValue == null || paramValue.length() > length)
				return false;
			return isPatternMatched(paramValue);
		} else {
			return paramValue == null || "".equals(paramValue);
		}

	}
	
	protected boolean isPatternMatched(String paramValue) {
		if (pattern == null)
			return false;
		Matcher m = pattern.matcher(paramValue);
		return m.matches();
	}

	protected int getLength() {
		return length;
	}

	protected void setLength(int length) {
		this.length = length;
	}
}
