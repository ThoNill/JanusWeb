package janus.tech.wcomponents;

import janus.tech.web.html.TableModelIterator;

import java.io.Serializable;
import java.util.List;

import org.janus.actions.DataValue;
import org.janus.gui.basis.TableColumnDescription;
import org.janus.gui.basis.TableGuiComponent;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;
import org.janus.table.ExtendedTableModel;

public class WTable extends TemplateGuiComponent implements TableGuiComponent {


	private int currentRow;
	private long generation;
	private long tableId;
	


	public WTable(WebGuiContext context,
			PrototypeGuiComponent prototyp) {
		super(context, prototyp);
	}
	
	@Override
	public DataValue getCurrentRowValue() {
		return ((TableGuiComponent) getPrototyp()).getCurrentRowValue();
	}

	@Override
	public int getCurrentRow() {
		return currentRow;
	}

	@Override
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	
	public TableModelIterator getItemIterator() {
		return new TableModelIterator((ExtendedTableModel)getGuiValue());
	}
	
	public int getRowCount() {
	 return ((ExtendedTableModel)getGuiValue()).getRowCount();
	}
	
	 @Override
	public void setGuiValue(Serializable v) {
		 if (v instanceof ExtendedTableModel) {
			 ExtendedTableModel m = (ExtendedTableModel)v;
			 if (m.getGeneration() != generation || m.getId() != tableId) {
				 generation = m.getGeneration();
				 tableId = m.getId();
				 super.setGuiValue(v);
			 }
		 }
	 }

	@Override
	public List<TableColumnDescription> getDescriptions() {
		return ((TableGuiComponent)getPrototyp()).getDescriptions();
	}
}
