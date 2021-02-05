package janus.tech.wcomponents;

import janus.tech.web.html.TableModelIterator;
import janus.tech.web.session.AjaxHelper;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

import org.janus.gui.basis.TableColumnDescription;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;
import org.janus.table.ExtendedTableModel;

public class WTable extends TemplateGuiComponent {


	private int currentRow;
	private long generation;
	private long tableId;
	


	public WTable(WebGuiContext context,
			PrototypeGuiComponent prototyp) {
		super(context, prototyp);
	}
	

	public int getCurrentRow() {
		return getTableModel().getCurrentRow();
	}


	public void setCurrentRow(int currentRow) {
		getTableModel().setCurrentRow(currentRow);
	}
	
	
	private PrototypeGuiComponent getTableGui() {
		return super.getPrototyp();
	}

	
	public TableModelIterator getItemIterator() {
		return new TableModelIterator(getTableModel());
	}
	
	public int getRowCount() {
	 return getTableModel().getRowCount();
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

	public List<TableColumnDescription> getTableColumnDescriptions() {
		return getTableGui().getDescriptions();
	}
	
	private ExtendedTableModel getTableModel() {
		return (ExtendedTableModel)getGuiValue();
	}
	
	public String getTableData() {
		OutputStream out = new ByteArrayOutputStream();
		PrintWriter w = new PrintWriter(out);
		AjaxHelper h = new AjaxHelper();
		ExtendedTableModel m = getTableModel();
		h.printTableData(w, m,m.getRowCount(),m.getColumnCount());
		w.close();
		return out.toString();
	}
}
