package janus.tech.web.html;

import java.util.Iterator;

import org.janus.table.ExtendedTableModel;

public class TableModelIterator implements Iterator<IndexAndTextItem> {
	ExtendedTableModel model;
	int index;
	
	public TableModelIterator(ExtendedTableModel model) {
		super();
		this.model = model;
		this.index = 0;
	}
	
	@Override
	public boolean hasNext() {
		return index < model.getRowCount();
	}
	@Override
	public IndexAndTextItem next() {
		if (!hasNext()) {
			return null;
		};
		IndexAndTextItem item = new IndexAndTextItem(index,model.getValueAt(index, 1).toString(),index== model.getCurrentRow());
		index++;
		return item;
	}
	@Override
	
	public void remove() {
		
		
	}

	
	
	
}
