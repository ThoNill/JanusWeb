package janus.tech.web.html;

public class IndexAndTextItem {
	int index;
	String text;
	boolean selected;
	
	public boolean isSelected() {
		return selected;
	}

	public int getIndex() {
		return index;
	}
	
	public String getText() {
		return text;
	}
	
	public IndexAndTextItem(int index, String text,boolean selected) {
		super();
		this.index = index;
		this.text = text;
		this.selected = selected;
	}
	
}
