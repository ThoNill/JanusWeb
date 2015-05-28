package janus.tech.web.stringtemplate;

import java.util.Iterator;

import org.janus.table.ExtendedTableModel;


public class RowIterator implements Iterator<ColumnIterator> {
	ExtendedTableModel m;
	int row =0;
	
	public RowIterator(ExtendedTableModel m) {
		this.m = m;
	}
	
	@Override
	public boolean hasNext() {
		return (row +1 < m.getRowCount());
	}

	@Override
	public ColumnIterator next() {
		if (hasNext()) {
			row ++;
			return new ColumnIterator(m,row);
		}	
		return null;
	}

	@Override
	public void remove() {
		m = null;
	}

	public boolean isCurrentRow() {
		return row == m.getCurrentRow();
	}
}
