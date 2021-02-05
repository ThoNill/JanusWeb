package janus.tech.wcomponents;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class JavaFont2HTMLFont {

	static String transform(Font c) {
		StringBuilder b = new StringBuilder(20);
		if (c.isItalic()) {
			b.append("\"fontStyle\": \"italic\", ");
		}
		;
		if (c.isBold()) {
			b.append("\"fontWeight\": \"bold\", ");
			if (!c.isItalic()) {
				b.append("\"fontStyle\": \"normal\", ");
			};
		};
		if (c.isPlain()) {
			b.append("\"fontStyle\": \"normal\", ");
		}
		;
		b.append("\"fontSize\": ");
		b.append(c.getSize());
		b.append("\", \"fontFamily\": \"");
		b.append(c.getFamily());
		b.append("\",");
		return b.toString();
	}

	public static void main(String args[]) {
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();

		for (String fontName : fontNames) {
			Font f = new Font(fontName, Font.PLAIN, 12);
			f = new Font(fontName, Font.BOLD, 12);
			f = new Font(fontName, Font.ITALIC, 12);
		}
	}
}
