package web;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.parsing.HTMLParserFactory;
import com.meterware.httpunit.parsing.HTMLParserListener;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;


public class WebServletTest implements HTMLParserListener{



	
	@Test
	public void testIt() {
		try {
			HttpUnitOptions.setScriptingEnabled( false );
			HTMLParserFactory.addHTMLParserListener(this);
			HTMLParserFactory.setReturnHTMLDocument(true);
			HTMLParserFactory.setParserWarningsEnabled(true);
			
     		YUIServlet.initStatic();
	
		
			ServletRunner sr = new ServletRunner(new File("test/web.xml"));
			
			
			
			
			ServletUnitClient sc = sr.newClient();
			WebRequest request = new PostMethodWebRequest(
					"http://localhost/test");
			
			WebResponse response = sc.getResponse(request);
			Document doc = response.getDOM();
			
			listDivs(doc,"INPUT");
			listDivs(doc,"BUTTON");
			
			request = new PostMethodWebRequest(
					"http://localhost/test?DIV10=thomas");
			
			response = sc.getResponse(request);
						
			request = new PostMethodWebRequest(
					"http://localhost/test?DIV14=1");
			
			response = sc.getResponse(request);
			
			doc = response.getDOM();
			
			listDivs(doc,"INPUT");
			listDivs(doc,"BUTTON");
		
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	protected void listDivs(Document doc,String elementName) {
		System.out.println("DIV's mit name " + elementName);
		NodeList inputFields = doc.getElementsByTagName(elementName);
		for (int i=0;i<inputFields.getLength();i++) {
			Node field = inputFields.item(i);
			NamedNodeMap map = field.getAttributes();
			Node attribut = map.getNamedItem("id");
			System.out.println(attribut.getNodeValue());
		}
	}

	protected String YUIAufruf() throws IOException, SAXException {
		WebConversation wc = new WebConversation();
		WebRequest     req = new GetMethodWebRequest( "http://yui.yahooapis.com/3.13.0/build/yui/yui-debug.js" );
		WebResponse   resp = wc.getResponse( req );
		return resp.getText();
	}

	@Override
	public void warning(URL url, String msg, int line, int column) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}

	@Override
	public void error(URL url, String msg, int line, int column) {
		
		System.out.println(msg);
	}

}

