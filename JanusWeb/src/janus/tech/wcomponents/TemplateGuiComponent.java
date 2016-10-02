package janus.tech.wcomponents;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.log4j.Logger;
import org.janus.data.DefaultClassFactory;
import org.janus.gui.web.DefaultGuiComponent;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;

public class TemplateGuiComponent extends DefaultGuiComponent {
    private static final Logger LOG = Logger.getLogger(TemplateGuiComponent.class);


	static private StringTemplateGroup group = null;
	
	static private String groupName = "YUI";

	int inBoxOfType;

	public TemplateGuiComponent(WebGuiContext context, PrototypeGuiComponent prototyp) {
		super(context, prototyp);
	}
	
	
	protected static void setGroupName(String groupName) {
		TemplateGuiComponent.groupName = groupName;
	}
	
	public int isInHbox() {
		return inBoxOfType;
	}

	public int getInBoxOfType() {
		return inBoxOfType;
	}

	public void setInBoxOfType(int inBoxOfType) {
		this.inBoxOfType = inBoxOfType;
	}

	public static StringTemplateGroup getGroup() {
		if (group == null) {
			Reader r = DefaultClassFactory.FACTORY
					.getReader("templates/" + groupName + ".st");
			group = new StringTemplateGroup(r, DefaultTemplateLexer.class);
		}
		return group;
	}

	public String getJsTemplate() {
		return "Js" + getGuiType().name();
	}

	public String getHtmlTemplate() {
		return getGuiType().name();
	}

	public void printAjax(PrintWriter out) {
		printHtml(out);
	}

	public void printHtml(PrintWriter out) {
		out.print(renderHtml());
	}
	
	public String renderHtml() {
		return renderTemplate(getHtmlTemplate());
	}

	public void printJs(PrintWriter out) {
		out.print(renderTemplate(getJsTemplate()));
	}

	public String renderTemplate(String name) {
		try {
			StringTemplate t = getGroup().getInstanceOf(name);
			t.setAttribute("it", this);
			return t.toString();
		} catch (Exception ex) {
			LOG.error("Fehler",ex);;
		}
		return "";
	}

	public static String umlauteWeg(String textfield) {
		if (textfield == null)
			return null;
		int anz = 0;
		for (int i = 0; i < textfield.length(); i++) {
			if ("äöüÄÖÜß§".indexOf(textfield.charAt(i)) >= 0) {
				anz += 7;
			}
		}
		if (anz > 0) {
			StringBuilder b = new StringBuilder(textfield.length() + anz);
			for (int i = 0; i < textfield.length(); i++) {
				char c = textfield.charAt(i);
				if (c == 'ä') {
					b.append("&auml;");
				} else if (c == 'Ä') {
					b.append("&Auml;");
				} else if (c == 'ö') {
					b.append("&ouml;");
				} else if (c == 'Ö') {
					b.append("&Ouml;");
				} else if (c == 'ü') {
					b.append("&uuml;");
				} else if (c == 'Ü') {
					b.append("&Uuml;");
				} else if (c == 'ß') {
					b.append("&szlig;");
				} else if (c == '§') {
					b.append("&sect;");
				} else {
					b.append(c);
				}
			}
			return b.toString();
		} else {
			return textfield;
		}
	}

	public String getColorAsString(Color c) {
		if (Color.green.equals(c)) {
			return "green";
		}
		if (Color.red.equals(c)) {
			return "red";
		}
		if (Color.blue.equals(c)) {
			return "blue";
		}
		if (Color.yellow.equals(c)) {
			return "yellow";
		}
		if (Color.black.equals(c)) {
			return "black";
		}
		if (Color.white.equals(c)) {
			return "white";
		}
		return null;
	}

	protected void printStylePice(PrintWriter out, String name, String value) {
		if (value == null)
			return;
		out.print(name);
		out.print(": ");
		out.print(value);
		out.print("; ");
	}

	public String getIdRef() {
		String snr = Integer.toString(getId());
		StringBuilder b = new StringBuilder(snr.length() + 1);
		b.append('v');
		b.append(snr);
		return b.toString();
	}

	public float getWidthEm() {
		return getWidth();
	}

	public float getHeightEm() {
		return getHeight();
	}

	@Override
	public String getStyle() {
		StringBuilder b = new StringBuilder(20);
		b.append("{");
		if (getFont() != null) {
			b.append(JavaFont2HTMLFont.transform(getFont()));
		}
		if (getForeground() != null) {
			b.append(" \"color\": \"");
			b.append(JavaColor2HTMLColor.transform(getForeground()));
			b.append("\", ");
		}
		if (getBackground() != null) {
			b.append(" \"backgroundColor\": \"");
			b.append(JavaColor2HTMLColor.transform(getBackground()));
			b.append("\" ");
		}
		b.append("}");
		return b.toString();
	}
	
	public List<TemplateGuiComponent> getChildren() {
		return (List)children;
	}



}