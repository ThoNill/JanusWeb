package janus.tech.web.stringtemplate;

import java.util.Iterator;

import javax.swing.table.TableModel;

public class ColumnIterator implements Iterator<Object> {
	TableModel m;
	int row =0;
	int col =0;
	
	public ColumnIterator(TableModel m,int row) {
		this.m = m;
		this.row = row;
	}
	
	@Override
	public boolean hasNext() {
		return (col +1 < m.getColumnCount());
	}

	@Override
	public Object next() {
		if (hasNext()) {
			col ++;
			return m.getValueAt(row,col);
		}	
		return null;
	}

	@Override
	public void remove() {
		m = null;
	}

	
}
