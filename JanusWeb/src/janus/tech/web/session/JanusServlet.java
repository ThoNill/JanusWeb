package janus.tech.web.session;

import janus.tech.wcomponents.WFrame;
import janus.tech.web.html.GuiComponentVerwalter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.janus.appbuilder.AppBuilder;
import org.janus.gui.basis.JanusApplication;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.basis.JanusSession;
import org.janus.gui.basis.PageContext;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiElementBuilder;

public class JanusServlet extends HttpServlet {
	Logger log = Logger.getLogger(JanusServlet.class);

	JanusApplication app;

	public JanusServlet() {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			String appname = getInitParameter("appname");
			String pagelist = getInitParameter("pagelist");
			if (appname == null || pagelist == null) {
				log.error("Bitte den Paramter 'appname' oder 'pagelist' anlegen");
			} else {
				AppBuilder appBuilder = new AppBuilder(
						new WebGuiElementBuilder());
				appBuilder.setPageListe(pagelist);
				app = appBuilder.getApplication(appname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		if (app == null) {
			return;
		}

		boolean ajax = "true".equals(request.getParameter("ajax"));

		respondToMessage(request, response, ajax);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		if (app == null) {
			return;
		}

		boolean ajax = "true".equals(request.getParameter("ajax"));
		respondToMessage(request, response, ajax);
	}

	private void respondToMessage(HttpServletRequest request,
			HttpServletResponse response, boolean ajax) {

		HtmlSession session = HtmlSession.getSession(request, app);

		if (session.wasNotActiv()) {
			try {

				respondToMessage(request, response, session, ajax);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(e1.getMessage());
			}
			session.setInactive();
		}
		;

	}

	private void respondToMessage(HttpServletRequest request,
			HttpServletResponse response, HtmlSession session, boolean ajax)
			throws IOException {
		boolean ok = checkAllParameterValues(request);
		if (ok) {
			processAllParameterValues(request, session);
		}
		createOutput(response, session, ajax);
	}

	private void createOutput(HttpServletResponse response,
			HtmlSession session, boolean ajax) throws IOException {
		JanusApplication app = session.getApplicaton();
		JanusPage currentPage = app.getPage(session.getCurrentPage());
		if (ajax) {
			createAjaxResult(response, session);
		} else {
			createPageResult(response, session, currentPage);
		}

	}

	private void createPageResult(HttpServletResponse response,
			HtmlSession session, JanusPage currentPage) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		WFrame frame = session.getFrame(currentPage);
		if (frame != null) {
			frame.layout();
			frame.printHtml(out);
		}
		out.flush();
	}

	private void createAjaxResult(HttpServletResponse response,
			HtmlSession session) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		AjaxHelper h = new AjaxHelper();
		// PrintWriter w = new PrintWriter(System.out);
		PrintWriter w = response.getWriter();
		h.createOutput(session, w);
		w.flush();
	}

	private boolean checkAllParameterValues(HttpServletRequest request) {
		boolean ok = true;
		Enumeration<String> parameterNames = request.getParameterNames();
		if (parameterNames != null) {
			while (ok && parameterNames.hasMoreElements()) {
				String parameterName = parameterNames.nextElement();
				ok = ok && checkParameterValue(request, parameterName);
				System.out.println("Test von " + parameterName + " ist " + ok);
			}
		}
		return ok;
	}

	private boolean istspeziell(String parameterName) {

		return "ajax".equals(parameterName);
	}

	private void processAllParameterValues(HttpServletRequest request,
			JanusSession session) {
		Enumeration<String> parameterNames = request.getParameterNames();
		if (parameterNames != null) {
			while (parameterNames.hasMoreElements()) {
				String parameterName = parameterNames.nextElement();
				processParameterValue(request, parameterName, session);
			}
		}
		session.perform();
	}

	private void processParameterValue(HttpServletRequest request,
			String parameterName, JanusSession session) {
		if (!istspeziell(parameterName)) {
			String parameterValue = request.getParameter(parameterName);

			PrototypeGuiComponent prototyp = getGuiComponent(request,
					parameterName);
			JanusPage page = prototyp.getPage();
			PageContext context = session.getPageContext(page);
			prototyp.setObject(context, parameterValue);
		}
	}

	public PrototypeGuiComponent getGuiComponent(HttpServletRequest request,
			String parameterName) {
		int actionId = Integer.parseInt(parameterName.substring(3));
		return GuiComponentVerwalter.getVerwalter().getComponent(actionId);
	}

	public boolean checkParameterValue(HttpServletRequest request,
			String parameterName) {
		if (!istspeziell(parameterName)) {
			String parameterValue = request.getParameter(parameterName);
			PrototypeGuiComponent connector = getGuiComponent(request,
					parameterName);
			return (connector == null) ? false : connector
					.checkValue(parameterValue);
		} else {
			return true;
		}
	}
}