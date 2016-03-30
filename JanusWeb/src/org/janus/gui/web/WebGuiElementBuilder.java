package org.janus.gui.web;

import janus.tech.web.html.GuiComponentVerwalter;

import org.janus.actions.Action;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.NamedActionValue;
import org.janus.gui.basis.Attribut2GuiComponent;
import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.basis.TableColumnDescription;
import org.janus.gui.builder.GuiElementBuilder;
import org.janus.gui.enums.GuiField;
import org.janus.gui.enums.GuiType;
import org.jdom2.Attribute;
import org.jdom2.Element;

public class WebGuiElementBuilder implements GuiElementBuilder {

	public WebGuiElementBuilder() {
		super();
	}

	@Override
	public GuiComponent createGuiElement(Element elem, Action a,
			ActionDictionary dict) {
		PrototypeGuiComponent comp = createGuiElementIntern(elem, a, dict);
		String actionName = elem.getAttributeValue("name");
		if (actionName != null) {
			NamedActionValue value = dict.getAction(actionName);
			comp.setValue(value);
			GuiComponentVerwalter.getVerwalter().add(comp);
		}
		comp.setHeight(2.0f);
		comp.setWidth(10.0f);
		for (Attribute attr : elem.getAttributes()) {
			try {
				GuiField field = GuiField.valueOf(attr.getName().toUpperCase());
				Attribut2GuiComponent.setField(comp, field, attr.getValue());
			} catch (Exception ex) {

			}
		}
		if (comp.isTable()) {
			comp.setLength(5);
			comp.setPattern("^[0-9]+$");
		}
		switch (comp.getGuiType()) {
		case BUTTON:
			comp.setLength(1);
			comp.setPattern("^1$");
			break;
		case CHECK:
			break;
		case COMBO:
			break;
		case DATEFIELD:
			break;
		case FRAME:
			break;
		case GLUE:
			break;
		case GUI:
			break;
		case HBOX:
			break;
		case LABEL:
			break;
		case LIST:
			break;
		case MENU:
			break;
		case MENUBAR:
			break;
		case MENUITEM:
			break;
		case MONEYFIELD:
			break;
		case POPUP:
			break;
		case RADIO:
			break;
		case SHOWTABLE:
			addTableColumnDescriptions(elem,comp);
			break;
		case TAB:
			break;
		case TABS:
			comp.setLength(5);
			comp.setPattern("^[0-9]+$");
			break;
		case TEXTFIELD:
			comp.setLength(100);
			comp.setPattern("^[0-9a-zäöüß \\.\\-\\,A-ZÄÖÜ]*$");
			break;
		case VBOX:
			break;
		default:
			break;

		}
		return comp;
	}

	private void addTableColumnDescriptions(Element elem,
			PrototypeGuiComponent comp) {
		for( Element column : elem.getChildren()) {
			if ("COLUMN".equals(column.getName().toUpperCase())) {
				TableColumnDescription d = new TableColumnDescription(column.getAttributeValue("class"),column.getAttributeValue("header") ,column.getAttributeValue("name"));
				comp.addDescriptions(d);
			}
			
		}
		
	}

	private PrototypeGuiComponent createGuiElementIntern(Element elem,
			Action a, ActionDictionary dict) {
		GuiType type = GuiType.valueOf(elem.getName());
		if (type.equals(GuiType.GUI)) {
			return new RootPrototypeGuiComponent(type, (JanusPage) dict);
		}
		if (type.equals(GuiType.SHOWTABLE)) {
			
		}
		return new PrototypeGuiComponent(type, (JanusPage) dict);
	}

}
