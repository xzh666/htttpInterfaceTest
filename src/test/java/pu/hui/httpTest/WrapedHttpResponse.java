package pu.hui.httpTest;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import pu.hui.httpTest.util.JsonFormatUtil;

/**
 * A bean class that represents the response from an HTTP request to a service
 * call
 * 
 * @since 0.4.0
 */
public class WrapedHttpResponse {
	JSONObject jsonReponse ;
	public JSONObject getJsonReponse() {
		return jsonReponse;
	}

	public void setJsonReponse(JSONObject jsonReponse) {
		this.jsonReponse = jsonReponse;
	}

	private String statusLine = null;
	private int statusCode = 0;
	private String body;
	private HashMap<String, String> headers = new HashMap<String, String>();
	private static String token;
	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		WrapedHttpResponse.token = token;
	}

	private Logger logger = Logger.getLogger(WrapedHttpResponse.class);

	public JSONObject getBodyAsJson() {
		try {
			return JSONObject.fromObject(body);
		} catch (JSONException e) {
			logger.error("getBodyAsJson error ��", e);
		}
		return null;
	}

	public String getBody() {
		return body;
	}

	void setBody(String body) {
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusLine() {
		return statusLine;
	}

	void setStatusLine(String statusLine) {
		this.statusLine = statusLine;
	}

	void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	public Set<String> getHeaderNames() {
		return headers.keySet();
	}

	/**
	 * Print the response to STD out. Method for testing purposes.
	 */
	public void printResponse() {
		logger.debug("----------------------------------------------");
		logger.debug("Http Response statusCode: " + statusCode);
		logger.debug("Http Response statusLine: " + statusLine);
		logger.debug("Http Response Headers:");
		for (String name : getHeaderNames()) {
			logger.debug(name + ": " + getHeader(name));
		}
		logger.debug("Http Response Body:");
		logger.debug(JsonFormatUtil.formatJson(getBody()));
		logger.debug("----------------------------------------------");
	}

}
