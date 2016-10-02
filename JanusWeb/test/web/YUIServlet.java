package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class YUIServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(YUIServlet.class);
	static String text = "";
	static String contentType = "application/javascript";
	static String charSet = "utf-8";

	public static void initStatic() throws ServletException {
		try {
			File f = new File("yui");
			if (f.exists()) {
				int s = (int)f.length();
				byte is[]  = new byte[s];
				FileInputStream in = new FileInputStream(f);
				int sRead = in.read(is);
				in.close();
				text = new String(is);
				if (s != sRead) {
				    throw new IOException("Anzahl der gelesenen Bytes != Dateigroesse");
				}
			} else {

				WebConversation wc = new WebConversation();
				WebRequest req = new GetMethodWebRequest(
						"http://yui.yahooapis.com/3.13.0/build/yui/yui-debug.js");
				WebResponse resp = wc.getResponse(req);
				contentType = resp.getContentType();
				charSet = resp.getCharacterSet();
				
				text = resp.getText();

				FileOutputStream out = new FileOutputStream(f);
				out.write(text.getBytes());
				out.close();

			}
		} catch (Exception e) {
			LOG.error("Fehler",e);;
		}
	}

	public YUIServlet() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		respondToMessage(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		respondToMessage(request, response);
	}

	private void respondToMessage(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(contentType);
		response.setCharacterEncoding(charSet);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(text);
			out.flush();
		} catch (IOException e) {
			LOG.error("Fehler",e);;
		}
	}

}
