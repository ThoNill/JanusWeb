package org.janus.gui.web;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.janus.actions.ReadValue;
import org.janus.actions.WriteValue;
import org.janus.data.DataContext;
import org.janus.dict.actions.NamedActionValue;
import org.janus.dict.helper.ID;
import org.janus.dict.interfaces.ActionListener;
import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.basis.TableColumnDescription;
import org.janus.gui.enums.GuiField;
import org.janus.gui.enums.GuiType;
import org.janus.table.ExtendedTableModel;
import org.janus.table.SelectRowHint;

/**
 * Dieses GUI Objekt speichert die Defaultwerte eder Propertie, die von XML
 * Attributen festgelegt werden und nicht mehr geändert werden. Diese Prototypen
 * können von verschiedenen Sessions gemeinsam genutzt werden.
 * 
 * @author THOMAS
 * 
 */

public class PrototypeGuiComponent implements WebGuiComponent, ReadValue,
        WriteValue, ActionListener {

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
    private List<WebGuiComponent> childComponents = null;
    private NamedActionValue value;
    private JanusPage page;
    private Pattern pattern = null;
    private int length = 0;
    private List<TableColumnDescription> descriptions = new ArrayList<>();

    public PrototypeGuiComponent(GuiType type, JanusPage page) {
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
        switch (field) {
        case BACKGROUND:
            return background;
        case FOREGROUND:
            return foreground;
        case FONT:
            return font;
        case ENABLED:
            return enabled;
        case VISIBLE:
            return visible;
        case FOCUS:
            return focus;
        case LABEL:
            return label;
        case STYLE:
            return style;
        case TOOLTIP:
            return tooltip;
        case WIDTH:
            return width;
        case HEIGHT:
            return height;
        case X:
            return x;
        case Y:
            return y;
        }
        return null;
    }

    @Override
    public void addComponent(GuiComponent comp) {
        if (childComponents == null) {
            childComponents = new ArrayList<>();
        }
        if (comp instanceof WebGuiComponent) {
            childComponents.add((WebGuiComponent) comp);
        } else {
            throw new IllegalArgumentException(
                    "Keine WebGuiComponente uebergeben");
        }
    }

    @Override
    public List<? extends WebGuiComponent> getChildComponents() {
        if (childComponents == null) {
            return Collections.emptyList();
        }
        return childComponents;
    }

    protected NamedActionValue getValue() {
        return value;
    }

    protected void setValue(NamedActionValue value) {
        if (this.value != null && value != this.value) {
            this.value.removeActionListener(this);
        }
        this.value = value;
        if (value != null) {
            value.addActionListener(this);
        }
    }

    public void setGuiValue(DataContext context, Serializable v) {
    }

    @Override
    public void setObject(DataContext context, Serializable v) {
        if (GuiType.BUTTON == type || GuiType.MENUITEM == type) {
            value.perform(context);
        } else if (isTable()) {
            value.setObject(context, new SelectRowHint(v.toString()));
        } else {
            value.setObject(context, v);
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
            System.out.println("Kein Write Value");
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

    @Override
    public void actionPerformed(Object a, DataContext data) {
        Serializable newValue = value.getObject(data);
        ChangeKey ck = getKey(GuiField.VALUE);
        if (isTable()) {
            ck = getKey(GuiField.CURRENTROW);
            if (newValue instanceof ExtendedTableModel) {
                newValue = ((ExtendedTableModel) newValue).getCurrentRow();
            }
        }
        WebGuiContext guiContext = (WebGuiContext) data;
        Serializable oldValue = getValue(guiContext, ck);
        ChangeOfGuiElement change = new ChangeOfGuiElement(ck, oldValue,
                newValue);
        System.out.println("actionPerformed " + ck + ":" + oldValue + " "
                + newValue);
        guiContext.add(change);
    }

    protected boolean isTable() {
        return GuiType.COMBO == type || GuiType.LIST == type
                || GuiType.RADIO == type || GuiType.SHOWTABLE == type;
    }

    protected Serializable getValue(WebGuiContext guiContext, ChangeKey ck) {
        Serializable value = guiContext.getValue(ck);
        if (value != null) {
            return value;
        }
        return "";
    }

    public List<TableColumnDescription> getDescriptions() {
        return descriptions;
    }

    public void addDescriptions(TableColumnDescription d) {
        descriptions.add(d);
    }

}
