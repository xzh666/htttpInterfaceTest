package pu.hui.httpTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestCaseEntity {
	
	
	private String testCaseId;
	private String interfaceCatatory;
	private String interfaceDesc;
	private String interfaceName;
	private String requestMethod;
	private String paramType;
	private String isRun;
	private String interfaceAddress;
	private String requesParamKey;
	private String requesParamKeyValues;
	private String expectValues;
	private String serverResponseValues;
	private String serverResponse;
	private boolean result;
	private int rowNum ;
	private String replaceParams;
	private Map<String,Object> fromatParam;
	private boolean isParameters;
	private int isParametersSize;
	private List<Map<String,Object>> fromatParamsList ;

	
	
	public Map<String, Object> getFromatParam() {
		return fromatParam;
	}
	public void setFromatParam(Map<String, Object> fromatParam) {
		this.fromatParam = fromatParam;
	}
	public List<Map<String, Object>> getFromatParamsList() {
		return fromatParamsList;
	}
	public void setFromatParamsList(List<Map<String, Object>> fromatParamsList) {
		this.fromatParamsList = fromatParamsList;
	}
	public boolean isParameters() {
		return isParameters;
	}
	public void setParameters(boolean isParameters) {
		this.isParameters = isParameters;
	}
	public int getIsParametersSize() {
		return isParametersSize;
	}
	public void setIsParametersSize(int isParametersSize) {
		this.isParametersSize = isParametersSize;
	}
	public Map<String, Object> getFromatParams() {
		return fromatParam;
	}
	public void setFromatParams(Map<String, Object> fromatParams) {
		this.fromatParam = fromatParams;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getReplaceParams() {
		return replaceParams;
	}
	public void setReplaceParams(String replaceParams) {
		this.replaceParams = replaceParams;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getInterfaceCatatory() {
		return interfaceCatatory;
	}
	public void setInterfaceCatatory(String interfaceCatatory) {
		this.interfaceCatatory = interfaceCatatory;
	}
	public String getInterfaceDesc() {
		return interfaceDesc;
	}
	public void setInterfaceDesc(String interfaceDesc) {
		this.interfaceDesc = interfaceDesc;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getIsRun() {
		return isRun;
	}
	public void setIsRun(String isRun) {
		this.isRun = isRun;
	}
	public String getInterfaceAddress() {
		return interfaceAddress;
	}
	public void setInterfaceAddress(String interfaceAddress) {
		this.interfaceAddress = interfaceAddress;
	}
	public String getRequesParamKey() {
		return requesParamKey;
	}
	public void setRequesParamKey(String requesParamKey) {
		this.requesParamKey = requesParamKey;
	}
	public String getRequesParamKeyValues() {
		return requesParamKeyValues;
	}
	public void setRequesParamKeyValues(String requesParamKeyValues) {
		this.requesParamKeyValues = requesParamKeyValues;
	}
	public String getExpectValues() {
		return expectValues;
	}
	public void setExpectValues(String expectValues) {
		this.expectValues = expectValues;
	}
	public String getServerResponseValues() {
		return serverResponseValues;
	}
	public void setServerResponseValues(String serverResponseValues) {
		this.serverResponseValues = serverResponseValues;
	}
	public String getServerResponse() {
		return serverResponse;
	}
	public void setServerResponse(String serverResponse) {
		this.serverResponse = serverResponse;
	}
	











	
	
	
	
}
