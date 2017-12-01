package com.digiwin.ecims.system.service.ontime.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.system.service.LogOnTimeReGetService;

/**
 * 重新获取数据线程
 * 
 * 通过单据类型，进行每个类型单据的拉取
 * 单据类型包括
 * 0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊
 * 详细需跟相关人确认
 * 
 * @author Xavier
 *
 */
public class ReGetRunnable implements Runnable {
	
	/**
	 * 用來記錄, 是哪一個類型的 Error
	 * @author Xavier
	 *
	 */
	public enum ErrorBillType {
		/**
		 * 訂單
		 * err_bill_id = aomsord001 = id = pkField
		 * other_key = aomsord060 = aoms060 = subPkField
		 * err_store_type = aomsord057 = storeTypeField
		 * 該table 更新時間欄位 = aomsord007 = modifiedField
		 */
		AOMSORD_T("AomsordT", "id", "aoms060", "aomsord057", "aomsord007"),
		/**
		 * 商品
		 * err_bill_id = aomsitem001 = id = pkField
		 * other_key = aomsitem004 = subPkField
		 * err_store_type = aomsitem011 = storeTypeField
		 * 該table 更新時間欄位 = aomsitem012 = modifiedField
		 */
		AOMSITEM_T("AomsitemT", "id", "aomsitem004", "aomsitem011", "aomsitem012"), 
		/**
		 * 退換貨 (沒有使用 other_key = subPkFile 的欄位)
		 * err_bill_id = aomsrefund001 = id = pkField
		 * other_key = aomsitem004 = subPkField
		 * err_store_type = aomsitem011 = storeTypeField
		 * 該table 更新時間欄位 = aomsitem012 = modifiedField 
		 * 
		 */
		AOMSREFUND_T("AomsrefundT", "id", "null", "aomsrefund044", "aomsrefund012"); 
		
		private String table;
		private String pkField, subPkField, storeTypeField, modifyField;
		private String storeType; //記錄是哪家店商
		private ErrorBillType(String table, String pkField, String subPkField, String storeTypeField, String modifiedField) {
			this.table = table;
			this.modifyField = modifiedField;
			this.pkField = pkField;
			this.subPkField = subPkField;
			this.storeTypeField = storeTypeField;
		}
		
		@Override
		public String toString() {
			return table;
		}

		public String getPkField() {
			return pkField;
		}

		public String getSubPkField() {
			return subPkField;
		}

		public String getModifyField() {
			return this.modifyField;
		}

		public String getStoreTypeField() {
			return storeTypeField;
		}

		public String getStoreType() {
			return storeType;
		}

		public void setStoreType(String storeType) {
			this.storeType = storeType;
		}
	}
	
	private static final Logger logger = LoggerFactory.getLogger(ReGetRunnable.class);
	
	private EcImsApiService service; //店商 Service
	private List<ReTryDataBean> processData; //該店商的相關資料
	
	//取得資料庫調用工具.
	private LogOnTimeReGetService reGetService =  (LogOnTimeReGetService) MySpringContext.getContext().getBean("aomsReGetServiceImpl");
	
	public ReGetRunnable (EcImsApiService service) {
		this.processData = new ArrayList<ReGetRunnable.ReTryDataBean>();
		this.service = service;
	}
	
	/**
	 * 新增處理資料
	 * @param row
	 * @return 是否滿足一次可處理筆數.
	 */
	public void addProcessData(ReTryDataBean row) {
		this.processData.add(row);
	}

	/**
	 * 相關欄位說明
	 * logIt.getErrBillType(); //錯誤Table名稱
	 * logIt.getErrBillId(); //錯誤單號
	 * String errStoreType = logIt.getErrStoreType();//電商 ID, [0, A]淘寶, [1]京東, [2]一號店, [3]中國工商, [4]蘇寧, [5]當當
	 * logIt.setFinal_status(final_status); //成功寫 1, 失敗寫  0
	 * logIt.getErrBillStatus();//電商更新時間, 若沒有更新時間, 則用創建時間
	 * logIt.getOtherKey(); //訂單是 060 productid, 退貨為空, 商品 004.
	 */
	@Override
	public void run() {
		
		if (processData.size() == 0) {
			logger.info("查無[{}]資料, 無須處理!", this.service.getClass().getName());
			return;
		}
		
		CmdRes res = null;
		for (ReTryDataBean rtdb: processData) {
			//取得基本資料
			LogInfoT logIt = rtdb.getLogInfoT();
			ErrorBillType type = rtdb.getErrorBillType(); //哪一種類型, 訂單 or 退貨  or 鋪貨
			CmdReq req = rtdb.getCmdReq();
			type.setStoreType(logIt.getErrStoreType()); //設定是哪家店商 //電商 ID, [0, A]淘寶, [1]京東, [2]一號店, [3]中國工商, [4]蘇寧, [5]當當
			
			//若 storeId 為空, 則不做任何事, 因為會找不到電商驗証碼  add by xavier on 20150901
			if (StringUtils.isBlank(req.getStoreid())) {
				continue;
			}
			
			//step3.拉取店商的資料
			final int reTryNum = logIt.getPushLimits(); //重試次數
			
			try {
				//step3. 從店商取資料
				res = this.reTrySuningSingle(req, reTryNum);
				
				//step3.比較資料, 看哪邊的比較新
				if (res == null) {
					//表示, reTry 到底, 都無法取回資料, 故不做其它事
					logIt.setPushLimits(0);
					logIt.setFinalStatus("0"); //執行失敗
				} else {
					//開始比對新舊資料
//					this.doAnalysisResponse(type, logIt, res); //取消不使用
					
					//直接儲存回傳回來資料
					this.reGetService.saveReGetDoDBOperate(res.getReturnValue());
					logIt.setFinalStatus("1"); //執行成功
				}
				
				//step4.更新 LoginfoT 裡面的資料
				List<LogInfoT> logTList = new ArrayList<LogInfoT>(); //FIXME 待調整, 應該有更好的寫法
				logTList.add(logIt);
				reGetService.saveReGetDoDBOperate(logTList);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
						"StoreType:[{}], ErrorBillId:{}, ErrMsg:{}", logIt.getErrStoreType(), logIt.getErrBillId(), e.getMessage());
			}
		}
		
	}
	
	/**
	 * 重新拉取資料
	 * @param service
	 * @param req
	 * @param reTryNum
	 * @return
	 * @throws Exception
	 */
	private CmdRes reTrySuningSingle(CmdReq req, int reTryNum) throws Exception {
		CmdRes res = null;
		for (int i = 0; i < reTryNum; i++) {
			res = this.service.execute(req);
			if (res.isSuccess()) { //執行成功, 且有回傳值
				return res;
			}
		}
		return null;
	}
	
	/**
	 * 1. 取中台資
	 * 2. 比較更新時間
	 * 3. 中間庫較舊, 則直接 update, 若中間庫沒有, 則直接新增
	 * @param type
	 * @param logIt
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	private void doAnalysisResponse(ErrorBillType type, LogInfoT logIt, CmdRes res) throws Exception {
		//step1. 查詢己存在中間庫的資料
		String oldPkValue = logIt.getErrBillId(); //錯誤單號
		String oldSubValue = logIt.getOtherKey(); //訂單是 060 productid, 退貨為空, 商品 004.
		String storeTypeValue = type.getStoreType(); //店號
		String hql = "from " + type.toString() + " where 1=1 "
				+ " and " + type.getPkField() + " = '" + oldPkValue + "' "
				+ " and " + type.getStoreTypeField() + " = '" + storeTypeValue + "' ";		
		if (StringUtils.isNotBlank(oldSubValue)) {
			//目前只會有, 退貨的狀態, 跑不進來 
			hql +=  " and " + type.subPkField + " = '" + oldSubValue + "' ";
		}
		//FIXME 這邊會有資料查詢的問題, 待確認
		Object oldRowData = this.reGetService.getUniqueData(hql); //取得舊資料
		
		//step2. 查找相同的資料
		for (Object row : res.getReturnValue()) {
			String newPkValue = BeanUtils.getProperty(row, type.getPkField());
			String newSubValue = BeanUtils.getProperty(row, type.getSubPkField());
			
			//新的單筆處理, 為了預防資料重覆, 故會合成 [37865419#1138992340] 這樣的格式, 但在 LogInfoT.otherKey 裡, 只會存#號前的資料.
			if(newSubValue.indexOf("#") > -1) {
				//filter #号, 
				newSubValue = newSubValue.substring(0, newSubValue.indexOf("#")); 
			}
			
//			System.out.println(
//					"newPkValue:" + newPkValue + ", newSubValue:" + newSubValue +
//					" <=> oldPkValue:" + oldPkValue + ", oldSubValue:" + oldSubValue
//			);
			
			if (oldPkValue.equals(newPkValue) && oldSubValue.equals(newSubValue)) {
				//step3. 比較哪邊的資料比較新
				if (oldRowData == null) {
					//中間庫無資料, 直接新增資料
//					reGetService.saveReGetDoDBOperate(DBOperate.SAVE, row);
				} else {
					//新拉回來的資料, 比較新, 更新中問庫的資料
					String oldModifyDateValue = BeanUtils.getProperty(oldRowData, type.getModifyField());
					String newModifyDateValue = BeanUtils.getProperty(row, type.getModifyField());
					Date oldDate = DateTimeTool.parse(oldModifyDateValue, "yyyy-MM-dd HH:mm:ss");
					Date newDate = DateTimeTool.parse(newModifyDateValue, "yyyy-MM-dd HH:mm:ss");
					if (newDate.after(oldDate)) {
						BeanUtils.copyProperties(oldRowData, row);
//						reGetService.saveReGetDoDBOperate(DBOperate.UPDATE, oldRowData);
						break;
					} 					
				}
			}
		}
		System.out.println();
	}
	
	
	/**
	 * 存放待處理的資料
	 * @author Xavier
	 *
	 */
	public static class ReTryDataBean {
		private ErrorBillType errorBillType; //哪一種類型, 訂單 or 退貨  or 鋪貨
		private LogInfoT logInfoT; //該店商的相關資料
		private CmdReq cmdReq; //對應的 request
		
		public ReTryDataBean(LogInfoT logInfoT, ErrorBillType errorBillType, CmdReq cmdReq) {
			this.logInfoT = logInfoT;
			this.errorBillType = errorBillType;
			this.cmdReq = cmdReq;
		}
		
		public ErrorBillType getErrorBillType() {
			return errorBillType;
		}
		public void setErrorBillType(ErrorBillType errorBillType) {
			this.errorBillType = errorBillType;
		}
		public LogInfoT getLogInfoT() {
			return logInfoT;
		}
		public void setLogInfoT(LogInfoT logInfoT) {
			this.logInfoT = logInfoT;
		}
		public CmdReq getCmdReq() {
			return cmdReq;
		}
		public void setCmdReq(CmdReq cmdReq) {
			this.cmdReq = cmdReq;
		}
	}

}
