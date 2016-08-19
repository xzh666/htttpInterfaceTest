package pu.hui.httpTest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import pu.hui.httpTest.util.WebConstant;

public class WrapedHttpClient {

	private DefaultHttpClient client;

	public WrapedHttpClient(DefaultHttpClient httpClient) {
		super();
		this.client = httpClient;
	}

	private Logger logger = Logger.getLogger(WrapedHttpClient.class);



	public WrapedHttpResponse sendGetRequest(TestCaseEntity testCaseEntity) throws Exception {
		
		String url = testCaseEntity.getInterfaceAddress();
		List<NameValuePair> formparams = (List<NameValuePair>) testCaseEntity.getFromatParams().get("nameValuePair");
		URI uu2 = new URI(url);
		URI uri = URIUtils.createURI("http", uu2.getHost() + ":" + uu2.getPort(), -1, uu2.getPath(),
				URLEncodedUtils.format(formparams, "UTF-8"), null);
		// HttpGet httpget = new HttpGet(uri);
		HttpGet method = new HttpGet(uri);
		// method.addHeader(new BasicHeader("Accept",
		// WebConstant.WEB_ACCEPT_JSON));
		WrapedHttpResponse response = sendRequest(method);
		return response;
	}

	public WrapedHttpResponse sendPostRequest(TestCaseEntity testCaseEntity) throws Exception {
		WrapedHttpResponse response = null;
		HttpPost method = new HttpPost(testCaseEntity.getInterfaceAddress());
		method.addHeader(new BasicHeader("Accept", WebConstant.WEB_ACCEPT_JSON));
		if("json".equals(testCaseEntity.getParamType())){
			String jsonParam = (String) testCaseEntity.getFromatParams().get("json");
			 method.addHeader(new BasicHeader("ContentType",
					 WebConstant.WEB_CONTENT_TYPE_JSON));
			 StringEntity se = new StringEntity(jsonParam);
				se.setContentEncoding(WebConstant.CHARSET_UTF8);
				se.setContentType(WebConstant.WEB_ACCEPT_JSON);
				method.setEntity(se);
				response = sendRequest(method);
		}else{
			List<NameValuePair> formparams = (List<NameValuePair>) testCaseEntity.getFromatParams().get("nameValuePair");
			UrlEncodedFormEntity se = new UrlEncodedFormEntity(formparams, "UTF-8");
			se.setContentEncoding(WebConstant.CHARSET_UTF8);
			method.setEntity(se);
			response = sendRequest(method);
		}
		return response;
	}

	private WrapedHttpResponse sendRequest(HttpRequestBase method) {

		WrapedHttpResponse res = new WrapedHttpResponse();
		HttpEntity entity = null;

		try {
			HttpResponse HttpResponse = client.execute(method);
			res.setStatusCode(HttpResponse.getStatusLine().getStatusCode());
			res.setStatusLine(HttpResponse.getStatusLine().toString());
			entity = HttpResponse.getEntity();
			String strReponse = EntityUtils.toString(entity, EntityUtils.getContentCharSet(entity));
			res.setBody(strReponse);
			JSONObject jsonReponse = JSONObject.fromObject(strReponse);
			res.setJsonReponse(jsonReponse);
			// res.setToken(jsonReponse.getString("token"));
		} catch (Exception e) {
			logger.error("sendRequest error : ", e);
		}

		return res;

	}
}
