package com.digiwin.ecims.core.util;

import java.lang.reflect.Field;
import java.sql.SQLException;

public class ErrorMessageBox {
	/*
	 * 錯誤碼查詢 000-100 validation error 101-200 Json / IO / classNotFound SQL
	 * 開頭就是SQL錯誤
	 */

	// 整體錯誤000~030
	public static final String _000 = "Request 格式錯誤:請下請求 指令請求請下cmd，查詢請求請下query!";
	public static final String _001 = "Request 格式錯誤:請求失敗，指令請求請下cmd，查詢請求請下query!";
	public static final String _002 = "Request 格式錯誤:請求失敗，請檢查是否有遵照JSON格式";
	public static final String _003 = "";
	public static final String _004 = "";
	public static final String _010 = "Redis執行緩存失敗!";
	public static final String _011 = "Request 无法完成:API调用失败!";

	// cmd錯誤031~070
	public static final String _031 = "指令格式錯誤: 參數不得為空";
	public static final String _032 = "平台不在支持范围内，请检查ecno参数！";
	public static final String _033 = "HTTP協議錯誤 或者 結果不為200";
	public static final String _034 = "指令格式錯誤: 無此API方法";
	public static final String _035 = "指令無返回值";
	public static final String _036 = "ClientProtocolException";
	public static final String _037 = "IOException";
	public static final String _038 = "ApiException";
	public static final String _039 = "params 錯誤!";
	public static final String _040 = "NumberFormatException 数值格式不正确";

	public static final String _070 = "未知错误，平台返回值无法解析";

	// query錯誤071~100
	public static final String _071 = "請輸入流水號， transactionId 不能為空!";
	public static final String _072 = "請輸入表名稱， table 不能為空!";
	public static final String _073 = "請輸入欄位名稱， fields 不能為空!";
	public static final String _074 = "頁碼錯誤， transactionId 未使用 pageNo 必須為1!";
	public static final String _075 = "頁數錯誤， transactionId 未使用 pageSize 必須為大於0!";
	public static final String _076 = "要求交易量錯誤， transactionId 未使用 reqTradeCnt 必須大於0!";
	public static final String _077 = "格式錯誤， pageNo,pageSize,reTradeCnt需為數字，請物輸入非數字格式或字串!";
	public static final String _078 = "格式錯誤，pageNo需為大於0的數字，請物輸入非數字格式或字串!";
	public static final String _079 = "資料庫錯誤，流水號存在性驗證失敗!";
	public static final String _080 = "資料庫錯誤，單號分類(distinct失敗)!";
	public static final String _081 = "資料庫錯誤，取出響應資料失敗:請確認欄位及表名稱皆正確!";
	public static final String _082 = "讀取解析錯誤，從暫存取出資料失敗!";
	public static final String _083 = "資料庫錯誤，流水號存在性驗證失敗!";
	public static final String _084 = "資料庫錯誤，流水號存在性驗證失敗!";
	public static final String _085 = "資料庫錯誤，流水號存在性驗證失敗!";
	public static final String _086 = "資料庫錯誤，流水號存在性驗證失敗!";
	public static final String _087 = "資料庫錯誤，流水號存在性驗證失敗!";
	public static final String _088 = "資料庫錯誤，流水號存在性驗證失敗!";
	public static final String _089 = "資料庫錯誤，流水號存在性驗證失敗!";
	public static final String _090 = "資料庫錯誤，流水號存在性驗證失敗!";

	// JSON類錯誤101~130
	public static final String _101 = "JSON-Parse類解析錯誤：";
	public static final String _102 = "JSON-Mapping類解析錯誤：";

	// IO類錯誤131~160
	public static final String _131 = "IO錯誤：";
	public static final String _132 = "JSON格式問題：解析錯誤，請確認json格式是否正確";

	// CLASSNOTFOUND類錯誤161~200
	public static final String _161 = "classNotFound錯誤，請檢查jar包";
	public static final String _162 = "";

	// 同步类错误201~250
	public static final String _201 = "平台未返回正确值，同步失败!";
	
	// 專給SQL用的
	public static String getErrorMsg(Exception ex) {
		String errorMsg = null;
		try {
			if (ex instanceof SQLException) {
				Field errorField = ErrorMessageBox.class
						.getDeclaredField(((SQLException) ex).getErrorCode()
								+ "");
				errorMsg = "SQL錯誤："
						+ (String) errorField.get(ErrorMessageBox.class
								.getClass());
			}
		} catch (Exception e) {
			errorMsg = "尚未建立錯誤代碼，實際錯誤為：" + ex.getMessage();
		}
		return errorMsg;
	}

	// 給一般有定義的
	public static String getErrorMsg(String definitionCode) {
		String errorMsg = null;
		Field errorField;
		try {
			errorField = ErrorMessageBox.class.getDeclaredField(definitionCode);
			errorMsg = (String) errorField
					.get(ErrorMessageBox.class.getClass());
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			errorMsg = "错误代码传入有误";
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return errorMsg;
	}
}
