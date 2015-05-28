package janus.tech.web.session;

import janus.tech.wcomponents.FromPrototypToTemplateComponents;
import janus.tech.wcomponents.WFrame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.JanusApplication;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.basis.JanusSession;
import org.janus.gui.enums.GuiType;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;

public class HtmlSession extends JanusSession {
	/**
	 * 
	 */
	private static final long serialVersionUID = 923212941956452478L;

	static final String SESSIONREF = "org.janus.session";
	static final String LOGINPAGE = "login";

	transient HttpSession httpSession;
	boolean active;
	transient WFrame frames[];
	JanusPage currentPage;

	public HtmlSession(JanusApplication model) {
		super(model);
		frames = new WFrame[model.getSize()];
	}

	public boolean isActive() {
		return active;
	}

	public void remove() {
		if (httpSession != null) {
			httpSession.removeAttribute(getSessionAttributName(getApplicaton()));
		}
		this.httpSession = null;
	}

	public static HtmlSession getSession(HttpServletRequest request,
			JanusApplication model) {
		HttpSession hSession = request.getSession(true);
		while (hSession == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			hSession = request.getSession(true);
		}
		Object obj = null;
		synchronized (hSession) {
			String attributName = getSessionAttributName(model);
			obj = hSession.getAttribute(attributName);
			if (obj == null) {
				obj = new HtmlSession(model);
				hSession.setAttribute(attributName, obj);
			} else {
				((HtmlSession) obj).setHttpSession(hSession);
			}
		}
		return (HtmlSession) obj;
	}

	private static String getSessionAttributName(JanusApplication model) {
		String appname = model.getName();
		StringBuilder bsessionref = new StringBuilder(SESSIONREF.length()
				+ appname.length() + 1);
		bsessionref.append(appname);
		bsessionref.append(".");
		bsessionref.append(SESSIONREF);
		return bsessionref.toString();
	}

	public String getFilename(String name) {
		String real = httpSession.getServletContext().getRealPath(name);
		return real;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public synchronized boolean wasFree() {
		if (active)
			return false;
		active = true;
		return true;
	}

	public synchronized boolean wasNotActiv() {
		if (active)
			return false;
		active = true;
		return true;
	}

	public void setInactive() {
		active = false;
	}

	
	@Override
	public WebGuiContext createDataContext(JanusPage page) {
		return new WebGuiContext(page);
	}
	
	public WFrame getFrame(JanusPage page) {
		if (frames[page.getIndex()] == null) {
			GuiComponent gui = (GuiComponent) page.getGui();
			if (gui != null) {
				WebGuiContext context = (WebGuiContext)getPageContext(page);
				frames[page.getIndex()] = createFrame( context,(PrototypeGuiComponent)gui);
			}
		}
		return frames[page.getIndex()];
	}

	private WFrame createFrame(WebGuiContext context, PrototypeGuiComponent gui) {
		return (WFrame)FromPrototypToTemplateComponents.createTemplateElement(context,gui);
	}
}
