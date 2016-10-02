package janus.tech.web.session;

import janus.tech.wcomponents.WTable;



import java.io.PrintWriter;
import java.util.List;

import javax.swing.table.TableModel;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.JanusApplication;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.web.ChangeOfGuiElement;
import org.janus.gui.web.GuiOutputCreator;
import org.janus.gui.web.WebGuiComponent;
import org.janus.gui.web.WebGuiContext;

public class AjaxHelper extends GuiOutputCreator<PrintWriter> {

	public void printAttribut(PrintWriter out, String attribute, String prefix,
			Object value, boolean komma) {
		if (value != null) {
			out.print(" \"");
			out.print(attribute);
			out.print("\": ");
			printValue(out, prefix, value);
			if (komma) {
				out.print(",");
			}
		}
	}

	private void printValue(PrintWriter out, String prefix, Object value) {
		boolean tableData = (value instanceof TableModel);
		if (tableData) {
			printTable(out, (TableModel) value);
		} else {
			String text = value.toString();
			if (text != null && text.length() > 1 && text.charAt(0) == '{') {
				out.print(text);
			} else {
				out.print("\"");
				if (prefix != null) {
					out.print(prefix);
				}
				out.print(value.toString());
				out.print("\" ");
			}
		}
	}


	public void printAllPropertyChangedEvents(PrintWriter out,
			WebGuiContext context) {
		List<ChangeOfGuiElement> changes = context.getChanges();
		out.println(" \"events\": [");
		boolean komma = false;
		for (ChangeOfGuiElement e : changes) {
			if (komma) {
				out.print(",");
			}
			printOneChange(out, e);
			komma = true;
		}
		out.println("] ");
	
	}

	private void printOneChange(PrintWriter out, ChangeOfGuiElement e) {
		out.print('{');
		printAttribut(out, "div", "#DIV", e.getId(), true);
		printAttribut(out, "prop", null, e.getPropertyName().toLowerCase(), true);
		printAttribut(out, "oldvalue", null, e.getOldValue(), true);
		printAttribut(out, "newvalue", null, e.getNewValue(), false);
		out.println('}');
	}

	void printTable(PrintWriter out, TableModel model) {
		out.println(' ');
		out.print('{');
		int rowCount = model.getRowCount();
		int columnCount = model.getColumnCount();
		printAttribut(out, "rows", null, rowCount, true);
		printAttribut(out, "columns", null, columnCount + 1, true);
		out.println(" \"data\": ");
		printTableData(out,model, rowCount, columnCount);
		out.println("}");
	}

	public void printTableData(PrintWriter out,TableModel model,
			int rowCount, int columnCount) {
		out.print("[");
		for (int row = 0; row < rowCount; row++) {
			if (row > 0) {
				out.print(",");
			}
			printTableRow(out, model, columnCount, row);

		}
		out.print("]");
	
	}

	private void printTableRow(PrintWriter out, TableModel model,
			int columnCount, int row) {
		out.print("{ ");
		printAttribut(out, "zeilennr", null, row, false);
		for (int col = 0; col < columnCount; col++) {
			out.print(",");
			printAttribut(out, model.getColumnName(col), null, model
					.getValueAt(row, col), false);
		}
		out.print("} ");
	}

	@Override
	protected void post(WebGuiComponent comp,WebGuiContext context,  PrintWriter out) {
		

	}

	@Override
	protected void postChild(WebGuiComponent c, WebGuiContext context, PrintWriter out) {
		

	}

	@Override
	protected void pre(WebGuiComponent comp,WebGuiContext context,  PrintWriter out) {
		Object model = comp.getGuiValue();
		if (model instanceof TableModel && comp instanceof WTable) {
			printTable(out, (TableModel) model);
		} else {

		}
	}

	@Override
	protected void preAllChilds(WebGuiComponent comp, WebGuiContext context, PrintWriter out) {
		

	}

	@Override
	protected void preChild(WebGuiComponent comp, WebGuiContext context,  PrintWriter out) {
		

	}
	

	public void createOutput(HtmlSession session, PrintWriter out) {
		JanusApplication app = session.getApplicaton();
		JanusPage currentPage = app.getPage(session.getCurrentPage());
		WebGuiContext context = (WebGuiContext) currentPage.getContext(session);

		GuiComponent gui = currentPage.getGui();
		if (gui != null) {
			out.print("{");
			printAttribut(out, "page", "p",currentPage.getIndex(), true);
			printAllPropertyChangedEvents(out, context);
			// createOutput(gui, context, out);
			out.println("} ");
		}
	}
}
