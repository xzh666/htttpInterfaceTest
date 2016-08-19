package pu.hui.httpTest;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;

import pu.hui.httpTest.util.Constants;
import pu.hui.httpTest.util.ExcelSheet;
import pu.hui.httpTest.util.ExcelUtil;

public class TestCaseBase {
	private static Logger logger = Logger.getLogger(TestCaseBase.class);
	protected static ExcelUtil eu = new ExcelUtil(
			"D:\\PuhuiWorkSpace\\dubboToRest2\\src\\test\\resource\\dubboToRest.xls");
	protected static ExcelSheet excelSheet = eu.getSheet("Interface");
	static DefaultHttpClient httpClient = new DefaultHttpClient();
	public static String token = "";
	//成功个数
	public static int sucCount = 0;
	//失败个数
	public static int failCount = 0;

	@BeforeSuite
	public void setUp() {

	}

	public static TestCaseEntity readTestCaseFromExcel(String testCaseNo) {

		int rowCount = excelSheet.getRowCount();
		TestCaseEntity testCaseEntity = new TestCaseEntity();
		for (int i = 0; i < rowCount; i++) {
			String testCaseNum = excelSheet.getCellValue(i, 0);
			if (testCaseNo.equals(testCaseNum)) {
				String interfaceCatatory = excelSheet.getCellValue(i, Constants.COL_CATETORY);
				String interfaceDesc = excelSheet.getCellValue(i, Constants.COL_INTERFACE_DESC);
				String interfaceName = excelSheet.getCellValue(i, Constants.COL_INTERFACE_NAME);
				String requestMethod = excelSheet.getCellValue(i, Constants.COL_METHOD);
				String paramType = excelSheet.getCellValue(i, Constants.COL_type);
				String isRun = excelSheet.getCellValue(i, Constants.COL_IS_RUN);
				String interfaceAddress = excelSheet.getCellValue(i, Constants.COL_INTERFACE_ADDRESS);
				String requesParamKey = excelSheet.getCellValue(i, Constants.COL_INTERFACE_REQ_PARAM_KEY);
				String requesParamKeyValues = excelSheet.getCellValue(i, Constants.COL_INTERFACE_REQ_VALUE);
				String expectValues = excelSheet.getCellValue(i, Constants.COL_EXPECT_VALUE);
				String serverResponseValues = excelSheet.getCellValue(i, Constants.COL_SERVER_RESPONSE_VALUE);
				String serverResponse = excelSheet.getCellValue(i, Constants.COL_SERVER_RESPONSE);
				String result  = excelSheet.getCellValue(i, Constants.COL_RESULT);
				String replaceParams = excelSheet.getCellValue(i, Constants.COL_REPLACE_PARAM);
				
				testCaseEntity.setTestCaseId(testCaseNo);
				testCaseEntity.setInterfaceCatatory(interfaceCatatory);
				testCaseEntity.setInterfaceDesc(interfaceDesc);
				testCaseEntity.setInterfaceName(interfaceName);
				testCaseEntity.setRequestMethod(requestMethod);
				testCaseEntity.setParamType(paramType);
				testCaseEntity.setIsRun(isRun);
				testCaseEntity.setInterfaceAddress(interfaceAddress);
				testCaseEntity.setRequesParamKey(requesParamKey);
				testCaseEntity.setRequesParamKeyValues(requesParamKeyValues);
				testCaseEntity.setExpectValues(expectValues);
				testCaseEntity.setServerResponseValues(serverResponseValues);
				testCaseEntity.setServerResponse(serverResponse);
				//testCaseEntity.setResult(result);
				testCaseEntity.setReplaceParams(replaceParams);
				testCaseEntity.setRowNum(i);
				
				
				if("json".equals(paramType)){
					replaceRequestParam(testCaseEntity);
				}
				else{
					if("@Parameters".equals(testCaseEntity.getRequesParamKeyValues())){
						testCaseEntity.setParameters(true);
						testCaseEntity.setIsParametersSize(eu.getSheet(testCaseEntity.getInterfaceName()).getRowCount()-1);
					}else{
						handleNameValuePair(testCaseEntity);
					}
				}

				break;
			}

		}
		return testCaseEntity;
	}

	public TestCaseEntity post(TestCaseEntity testCaseEntity) throws Exception {
		WrapedHttpResponse response = null;
		WrapedHttpClient wrapedHttpClient = new WrapedHttpClient(httpClient);
		response = wrapedHttpClient.sendPostRequest(testCaseEntity);
		testCaseEntity.setServerResponse(response.getBody());		
		return testCaseEntity;
	}

	public TestCaseEntity get(TestCaseEntity testCaseEntity) throws Exception {
		WrapedHttpResponse response = null;
		WrapedHttpClient wrapedHttpClient = new WrapedHttpClient(httpClient);
		response = wrapedHttpClient.sendGetRequest(testCaseEntity);
		testCaseEntity.setServerResponse(response.getBody());		

		return testCaseEntity;
	}

	public TestCaseEntity assertResult(TestCaseEntity testCaseEntity) {

		Map<String, String> keyValueMap = dealExpectValue(testCaseEntity.getExpectValues());
		fillActualValue(keyValueMap, testCaseEntity);
		
		boolean result = compareResult(keyValueMap, testCaseEntity.getServerResponse());
		testCaseEntity.setResult(result);
		setExcelValue(testCaseEntity);
		if(result){
			sucCount++;
		}else{
			failCount++;
		}
		return testCaseEntity;
	}

	public Map<String, String> dealExpectValue(String expectValue) {
		// status=904##code=1##body.mobile=18211097924
		Map<String, String> keyValueMap = new HashMap<String, String>();
		String[] expectValues = expectValue.split("##");
		for (int i = 0; i < expectValues.length; i++) {
			String[] keyValues = expectValues[i].split("=");
			keyValueMap.put(keyValues[0], keyValues[1]);
		}
		return keyValueMap;
	}

	public void setExcelValue(TestCaseEntity testCaseEntity) {

		excelSheet.setValue(testCaseEntity.getRowNum(), Constants.COL_SERVER_RESPONSE,
				testCaseEntity.getServerResponse());
		excelSheet.setValue(testCaseEntity.getRowNum(), Constants.COL_SERVER_RESPONSE_VALUE, testCaseEntity.getServerResponseValues());
		excelSheet.setValue(testCaseEntity.getRowNum(), Constants.COL_RESULT, testCaseEntity.isResult() + "");

		eu.save();
	}

	public boolean compareResult(Map<String, String> keyValueMap, String result) {

		boolean flag = false;
		int size = keyValueMap.keySet().size();
		int count = 0;
		for (String key : keyValueMap.keySet()) {
			count++;
			String value = keyValueMap.get(key);
			String actualValue = JsonPath.from(result).get(key) + "";
			if (!value.equals(actualValue)) {
				return flag;
			}
			if (count == size) {
				return true;
			}
		}
		return flag;
	}

	public void fillActualValue(Map<String, String> keyValueMap, TestCaseEntity testCaseEntity) {

		StringBuffer sb = new StringBuffer();
		for (String key : keyValueMap.keySet()) {
			String actualValue = JsonPath.from(testCaseEntity.getServerResponse()).get(key) + "";
			sb.append(key).append("=").append(actualValue).append("##");
		}
		String value = sb.toString();
		if (value.endsWith("##")) {
			testCaseEntity.setServerResponseValues(value.substring(0, value.lastIndexOf("##")));
		}

	}

	public static void replaceRequestParam(TestCaseEntity testCaseEntity) {
		
		Map<String,Object> fromatParams = new HashMap<String,Object>();

		String jsonObjectParam = testCaseEntity.getRequesParamKeyValues();
		String replaceParam = testCaseEntity.getReplaceParams();
		String[] replaceParamValuesArr = replaceParam.split(",");
		if(!"".equals(replaceParam)){
			Map<String, String> nameValuePair = new HashMap<String, String>();
			String[] values = new String[replaceParamValuesArr.length+1];
			for (int i = 0; i < replaceParamValuesArr.length; i++) {
				values[i] = replaceParamValuesArr[i].split("=")[1];			
			}
			if(!"".equals(token)){
				values[values.length] = token;
			}
			jsonObjectParam = String.format(jsonObjectParam, values);
			
			System.out.println("替换过后的json参数："+jsonObjectParam);
		}
		jsonObjectParam = String.format(jsonObjectParam, new String[]{token});
		fromatParams.put("json", jsonObjectParam);
		testCaseEntity.setFromatParams(fromatParams);
	}
	
	public void calculateSuccAndFail(){
		
		float susseccRate = ((float)sucCount)/(sucCount+failCount)*100;//((double)la)/b;
		excelSheet.setValue(0, 0, "运行总数："+(sucCount+failCount)+"    "+"成功个数："+sucCount+"    "+"失败个数："+failCount+"   "+"通过率："+susseccRate+"%");
		eu.save();
	}
	
	public Map<String,String> keyValueToMap(TestCaseEntity testCaseEntity){
		Map<String,String> map = new HashMap<String,String>();
		String requestParam = testCaseEntity.getRequesParamKeyValues();
		//key1=value1##key2=value2
		String[] requestParams = requestParam.split("##");
		for(int i = 0 ;i<requestParams.length;i++){
			String key = requestParams[i].split("=")[0];
			String value = requestParams[i].split("=")[1];
			map.put(key, value);
		}		
		return map;
	}
	
	
	public static TestCaseEntity handleNameValuePair(TestCaseEntity testCaseEntity) {
		
		if(testCaseEntity.isParameters()){
			List<Map<String,Object>> fromatParamsList = new ArrayList<Map<String,Object>>();
			for(int i = 0;i<testCaseEntity.getIsParametersSize();i++){
				Map<String,Object> fromatParams = new HashMap<String,Object>();
				StringBuilder sb = new StringBuilder();
				String str = null;
				int columns = testCaseEntity.getRequesParamKey().split(",").length;
				for(int j = 0;j<columns;j++){
					sb.append(eu.getSheet(testCaseEntity.getInterfaceName()).getCellValue(i, j)).append("##");
				}
				if(sb.toString().endsWith("##")){
					str = sb.toString().substring(0, sb.toString().lastIndexOf("##"));
				}
				List<NameValuePair> formparam = getNameValuePair(str);
				fromatParams.put("nameValuePair", formparam);
				fromatParamsList.add(fromatParams);
			}
			testCaseEntity.setFromatParamsList(fromatParamsList);
			
		}else{
			Map<String,Object> fromatParams = new HashMap<String,Object>();
			List<NameValuePair> formparam = getNameValuePair(testCaseEntity.getRequesParamKeyValues());
			fromatParams.put("nameValuePair", formparam);
			testCaseEntity.setFromatParams(fromatParams);
		}
		return testCaseEntity;
	}
	
	public static List<NameValuePair> getNameValuePair(String str){
		List<NameValuePair> formparam = new ArrayList<NameValuePair>();
		String[] keyValuePair = str.split("##");
		for (int i = 0; i < keyValuePair.length; i++) {
			String[] keyValues = keyValuePair[i].split("=");
			formparam.add(new BasicNameValuePair(keyValues[0], keyValues[1]));
		}	
		return formparam;
	}
}
