package web;

import java.io.PrintWriter;

import janus.tech.wcomponents.FromPrototypToTemplateComponents;
import janus.tech.wcomponents.TemplateGuiComponent;
import janus.tech.web.session.HtmlSession;

import javax.swing.JFrame;

import org.janus.appbuilder.AppBuilder;
import org.janus.gui.basis.JanusApplication;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.basis.JanusSession;
import org.janus.gui.builder.GuiElementBuilder;
import org.janus.gui.swing.JFrameConnector;
import org.janus.gui.swing.builder.DefaultGuiElementBuilder;
import org.janus.gui.web.PrototypeGuiComponent;
import org.janus.gui.web.WebGuiContext;
import org.janus.gui.web.WebGuiElementBuilder;
import org.junit.Assert;

public class TestApp {

	public TestApp() {
		// TODO Auto-generated constructor stub
	}

	
	public static void main(String args[]) {
		try {
			GuiElementBuilder elementBuilder = new WebGuiElementBuilder();
			AppBuilder builder = new AppBuilder(elementBuilder) ;
			builder.setPageListe("data");
			JanusApplication app = builder.getApplication("testapp");
			
			HtmlSession session = new HtmlSession(app);
			
			JanusPage login = session.searchPage("login");
	
			WebGuiContext pageContext = session.createDataContext(login);
			
			TemplateGuiComponent gui = FromPrototypToTemplateComponents.createTemplateElement(pageContext,
					 (PrototypeGuiComponent)login.getGui());
			
			String text = gui.renderHtml();
			System.out.print(text);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getMessage());
		}
	}	
}