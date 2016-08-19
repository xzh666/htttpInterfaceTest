package pu.hui.httpTest.util;

public class Constants {
	//测试数据Excel全路径
	public static final String PATH_EXCELFILE = "src/test/resource/dubboToRest.xlsx";
	public static final String PATH_CONFIGURATIONFILE = "src/test/resource/objectMap.properties";
	//测试数据sheet中的列号常量设定
	
	//测试用例序号列
	public static final int COL_TESTCASEID = 0;
	
	public static final int COL_CATETORY = 1;
	//接口描述
	public static final int COL_INTERFACE_DESC = 2;
	//接口名称
	public static final int COL_INTERFACE_NAME = 3;
	//请求方式 post或者get
	public static final int COL_METHOD = 4;
	//请求参数的类型，json或者默认（key1=value1##key2=value2）
	public static final int COL_type = 5;
	//是否测试接口
	public static final int COL_IS_RUN = 6;
	//接口地址
	public static final int COL_INTERFACE_ADDRESS = 7;
	//接口请求参数名称
	public static final int COL_INTERFACE_REQ_PARAM_KEY = 8;
	//接口请求参数值
	public static final int COL_INTERFACE_REQ_VALUE = 9;
	//可替换参数
	public static final int COL_REPLACE_PARAM = 10;
	//接口期望返回值
	public static final int COL_EXPECT_VALUE = 11;
	//服务器返回的键值对
	public static final int COL_SERVER_RESPONSE_VALUE = 12;
	//服务器返回信息
	public static final int COL_SERVER_RESPONSE = 13;
	
	//接口测试结果
	public static final int COL_RESULT = 14;
	
	public static final String SHEET_TESTCASES = "Interface";


}
