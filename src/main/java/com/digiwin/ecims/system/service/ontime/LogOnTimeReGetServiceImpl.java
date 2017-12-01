package com.digiwin.ecims.system.service.ontime;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.dangdang.service.DangdangApiService;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiService;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiService;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiService;
import com.digiwin.ecims.system.service.LogOnTimeReGetService;
import com.digiwin.ecims.system.service.ontime.util.ReGetRunnable;
import com.digiwin.ecims.system.service.ontime.util.ReGetRunnable.ErrorBillType;
import com.digiwin.ecims.system.service.ontime.util.ReGetRunnable.ReTryDataBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 日志中的错误订单的定时重新拉取任务
 * 
 * @author Xavier
 *
 */
@Service("aomsReGetServiceImpl")
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LogOnTimeReGetServiceImpl implements LogOnTimeReGetService {
	
	private static final Logger logger = LoggerFactory.getLogger(LogOnTimeReGetServiceImpl.class);
	
//	public enum DBOperate {SAVE, UPDATE}
	
	@Autowired
	private LoginfoOperateService loginfoOperateService;
	
	@Autowired
	private BaseDAO baseDAO;
	
//	@Autowired
//	private ApplicationContext springContext;
	
	//取得相關的 service.
	@Autowired
	private TaobaoApiService taobaoService; //淘寶
	@Autowired
	private JingdongApiService jingdongService;//京東
	@Autowired
	private YhdApiService yhdService;//一號店
	@Autowired
	private IcbcApiService icbcService;//中國工商
	@Autowired
	private SuningApiService suningService;//蘇寧
	@Autowired
	private DangdangApiService dandanService; //當當
	
	@Autowired
	private TaskService taskService;

//	public Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		return this.executeReTry(null, 0);
	}
	
	/**
	 * 取得 storeId 的資料
	 * 暫時不使用
	 * @return
	 */
	@Deprecated
	private Map<String, String> getScheduleTypeStoreIdMap() {
		
		//只取 PULL 的資料
//		String hql = "select scheduleType, shopId from TaskScheduleConfig where runType = 'PULL'";	
//		List<Object[]> data = taskService.executeQueryByHql(hql);
		
		Map<String, String> m = new HashMap<String, String>();
//		for (Object[] row :data) {
//			m.put(row[0].toString(), row[1].toString());
//		}
		return m;
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
	public boolean executeReTry(List<LogInfoT> data, int startProcessRow) {
		//基本訊息
		String scheduleType = "LogOnTimeReGet";
		TaskScheduleConfig taskScheduleConfig = taskService.getTaskScheduleConfigInfo(scheduleType);
		
		final int maxReadRows = taskScheduleConfig.getMaxReadRow(); //每次可處理筆數.
		
		//for test
//		final int maxReadRows = 300; //每次可處理筆數.

		//step1. 先取得 pushLimits > 0 and final_status = '0' 的資料
		if (data == null) {
			String hql = "from LogInfoT where 1=1 "
					+ " and pushLimits > 0 "
					+ " and final_status = '0' "
					+ " and err_store_type in ('0', 'A', '1', '2', '3', '4', '5')" ;
//			        + " and err_store_type in ('4')" ; // for test

//			data = reGetService.getQueryLogInfoData(hql);
			data = taskService.executeQueryByHql(hql);
			startProcessRow = 0;
		}
		
		//取得 storeId.
		Map<String, String> scheduleTypeStoreMap = this.getScheduleTypeStoreIdMap();
		
		List<ReGetRunnable> threadList = new ArrayList<ReGetRunnable>();
		
		ReGetRunnable runStore_0 = new ReGetRunnable(this.taobaoService); //淘寶
		ReGetRunnable runStore_1 = new ReGetRunnable(this.jingdongService); //京東
		ReGetRunnable runStore_2 = new ReGetRunnable(this.yhdService); //一號店
		ReGetRunnable runStore_3 = new ReGetRunnable(this.icbcService); //中國工商
		ReGetRunnable runStore_4 = new ReGetRunnable(this.suningService); //蘇寧
		ReGetRunnable runStore_5 = new ReGetRunnable(this.dandanService); //當當
		
		threadList.add(runStore_0);
		threadList.add(runStore_1);
		threadList.add(runStore_2);
		threadList.add(runStore_3);
		threadList.add(runStore_4);
		threadList.add(runStore_5);
		
		for (; startProcessRow < data.size(); startProcessRow++) {
			LogInfoT logIt = data.get(startProcessRow);
			String errStoreType = logIt.getErrStoreType();//電商 ID, [0, A]淘寶, [1]京東, [2]一號店, [3]中國工商, [4]蘇寧, [5]當當
			
			if ("0".equals(errStoreType)) { // 淘寶
				 CmdReq req = new CmdReq();
				 ErrorBillType type = this.getRequest(logIt, req, scheduleTypeStoreMap); //FIXME 回傳 null 的處理方式
				 ReTryDataBean rtdb = new ReTryDataBean(logIt, type, req);
				 runStore_0.addProcessData(rtdb);
			} else if ("A".equals(errStoreType)) { // 淘寶
				 CmdReq req = new CmdReq();
				 ErrorBillType type = this.getRequest(logIt, req, scheduleTypeStoreMap); //FIXME 回傳 null 的處理方式
				 ReTryDataBean rtdb = new ReTryDataBean(logIt, type, req);
				 runStore_0.addProcessData(rtdb);
			} else if ("1".equals(errStoreType)) { // 京東
				 CmdReq req = new CmdReq();
				 ErrorBillType type = this.getRequest(logIt, req, scheduleTypeStoreMap); //FIXME 回傳 null 的處理方式
				 ReTryDataBean rtdb = new ReTryDataBean(logIt, type, req);
				 runStore_1.addProcessData(rtdb);
			} else if ("2".equals(errStoreType)) { // 一號店
				 CmdReq req = new CmdReq();
				 ErrorBillType type = this.getRequest(logIt, req, scheduleTypeStoreMap); //FIXME 回傳 null 的處理方式
				 ReTryDataBean rtdb = new ReTryDataBean(logIt, type, req);
				 runStore_2.addProcessData(rtdb);
			} else if ("3".equals(errStoreType)) { // 中國工商
				 CmdReq req = new CmdReq();
				 ErrorBillType type = this.getRequest(logIt, req, scheduleTypeStoreMap); //FIXME 回傳 null 的處理方式
				 ReTryDataBean rtdb = new ReTryDataBean(logIt, type, req);
				 runStore_3.addProcessData(rtdb);
			} else if ("4".equals(errStoreType)) { // 蘇寧
				 CmdReq req = new CmdReq();
				 ErrorBillType type = this.getRequest(logIt, req, scheduleTypeStoreMap); //FIXME 回傳 null 的處理方式
				 ReTryDataBean rtdb = new ReTryDataBean(logIt, type, req);
				 runStore_4.addProcessData(rtdb);
			} else if ("5".equals(errStoreType)) { // 當當
				 CmdReq req = new CmdReq();
				 ErrorBillType type = this.getRequest(logIt, req, scheduleTypeStoreMap); //FIXME 回傳 null 的處理方式
				 ReTryDataBean rtdb = new ReTryDataBean(logIt, type, req);
				 runStore_5.addProcessData(rtdb);
			} else {
				logger.error("Error Store Type: {}", logIt.getErrStoreType());
			}
			
			//超過可執行筆數, 就中止回圈. 其用筆數留待下次處理.
			if ((startProcessRow + 1) % maxReadRows == 0) {
				break;
			}
		}
		
		//进行线程处理
		if (threadList.size() > 0) {
			ExecutorService executorService = Executors.newFixedThreadPool(threadList.size());
			for (ReGetRunnable getRunnable : threadList) {
				executorService.execute(getRunnable);
			}
			executorService.shutdown();
			while (!executorService.isTerminated()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		//檢查是否有未處理的筆數
		if (startProcessRow < data.size()) {
			return this.executeReTry(data, (startProcessRow + 1));
		} else {
			return Boolean.FALSE;
		}

	}
	
	/**
	 * 取得對應的 request.
	 * @param logIt
	 * @return
	 */
	private ErrorBillType getRequest(LogInfoT logIt, CmdReq req, Map<String, String> scheduleTypeStoreMap) {
		
		//共通參數
		if (StringUtils.isBlank(logIt.getErrStoreId())) {
			//若 logInfoT 查不到 storeId, 則用 scheduleType 去反查 storeId add by xavier on 20150901
			req.setStoreid(scheduleTypeStoreMap.get(logIt.getErrBillType()));
		} else {
			req.setStoreid(logIt.getErrStoreId());
		}
		
		if (logIt.getErrBillType().equals(ErrorBillType.AOMSORD_T.toString())) {
			//单笔订单查询 
			req.setApi("digiwin.order.detail.get");
			req.getParams().put("id", logIt.getErrBillId());
			return ErrorBillType.AOMSORD_T;
		} else if (logIt.getErrBillType().equals(ErrorBillType.AOMSREFUND_T.toString())) {
			//单笔查询退货信息
			req.setApi("digiwin.refund.get");
			req.getParams().put("id", logIt.getErrBillId());
			return ErrorBillType.AOMSREFUND_T;
		} else if (logIt.getErrBillType().equals(ErrorBillType.AOMSITEM_T.toString())) {
			//單筆鋪貨詳情
			req.setApi("digiwin.item.detail.get");
			req.getParams().put("numid", logIt.getErrBillId());
			return ErrorBillType.AOMSITEM_T;
		}
		return null;
	}
	
	/**
	 * 取得單筆資料
	 * @param hql
	 * @return
	 */
	public Object getUniqueData(String hql) {
		Object uniqueRow = this.baseDAO.findUniqueResultBySQL(hql); //取得舊資料
		return uniqueRow;
	}
	
	/**
	 *  統一DB的操作
	 * @param operateType
	 * @param row
	 */
	public<T> void saveReGetDoDBOperate(List<T> entityList) {
		this.baseDAO.saveOrUpdateByCollectionWithLog(entityList);
	}
	
//	@SuppressWarnings("unchecked")
//	@Transactional
//	public List<LogInfoT> getQueryLogInfoData(String hql) {
//		Query query = this.baseDAO.getSession().createQuery(hql);
//		return query.list();
//	}
	
//	public static void main(String[] args) throws Exception {
//		LogOnTimeReGetServiceImpl lotrgs = new LogOnTimeReGetServiceImpl();
//		lotrgs.timeOutExecute();
//		System.out.println();
//	}
	

	
	
}
