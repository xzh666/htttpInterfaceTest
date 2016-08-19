package pu.hui.httpTest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import pu.hui.httpTest.util.Constants;
import pu.hui.httpTest.util.ExcelUtil;
import pu.hui.httpTest.util.JsonFormatUtil;

public class TestSuiteByExcel extends TestCaseBase {

	private static Logger logger = Logger.getLogger(TestSuiteByExcel.class);
	int testCasesCount;

	public static void main(String[] args) throws Exception {
		TestSuiteByExcel tsb = new TestSuiteByExcel();
		tsb.testTestSuite();
	}

	public TestSuiteByExcel() {
		testCasesCount = excelSheet.getRowCount();
	}

	@Test
	public void testTestSuite() throws Exception {


		// 循环读取"Interface"sheet，执行所有标记为"y"的用例
		for (int testCaseNo = 2; testCaseNo < testCasesCount; testCaseNo++) {
			String testCaseRunFlag = excelSheet.getCellValule(testCaseNo, Constants.COL_IS_RUN);
			String testCaseId = excelSheet.getCellValule(testCaseNo, Constants.COL_TESTCASEID);
			long start = System.currentTimeMillis();
			int runCount = 1;
			// “读取"Interface”sheet中“场景号”为y的用例场景
			if (testCaseRunFlag.equalsIgnoreCase("y")) {
				
				TestCaseEntity testCaseEntity = readTestCaseFromExcel(testCaseId);
				
				
				logger.info("--------------      \"" + testCaseEntity.getTestCaseId() + " \"开始执行----------------");
				logger.info("本次是测试的==============第" + (runCount++) + "个接口================");
				logger.info("本次要测试的接口为： " + testCaseEntity.getInterfaceName());
				logger.info("本次要测试的接口地址为： " + testCaseEntity.getInterfaceAddress());
				logger.info("本次要测试请求方式为： " + testCaseEntity.getRequestMethod());
				logger.info("本次要测试请求参数为： " + testCaseEntity.getRequesParamKeyValues());
				logger.info("本次测试预期返回值： " + testCaseEntity.getExpectValues());
				//发送请求
				if("GET".equals(testCaseEntity.getRequestMethod().toUpperCase())){
					get(testCaseEntity);
				}else{
					post(testCaseEntity);
				}
				//断言结果
				assertResult(testCaseEntity);
				logger.info("本次测试实际返回值： " + testCaseEntity.getServerResponseValues());
				logger.info("本次测试"+testCaseEntity.getInterfaceName()+":" + testCaseEntity.isResult());
				logger.info("--------------      \"" + testCaseId + " \"执行结束----------------");
				
			}
			
		}
		
		//统计结果
		calculateSuccAndFail();
	}

		
		

}
