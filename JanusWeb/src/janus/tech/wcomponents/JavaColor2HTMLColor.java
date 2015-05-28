package janus.tech.wcomponents;

import java.awt.Color;

public class JavaColor2HTMLColor {

	static String transform(Color c) {
		StringBuilder b = new StringBuilder(7);
		b.append('#');
		String rgb = Integer.toHexString(c.getRGB());
		rgb = rgb.substring(2, rgb.length());
		b.append(rgb);
		return b.toString();
	}
	
	
	
/*	
	public static void main(String args[]) {
		System.out.println(transform(Color.blue));
	}
	*/
}
