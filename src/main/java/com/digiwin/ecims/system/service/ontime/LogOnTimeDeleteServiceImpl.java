package com.digiwin.ecims.system.service.ontime;

import java.math.BigInteger;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.enums.RegEnums;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.SystemEnum;
import com.digiwin.ecims.system.service.ReLogProcessService;

@Service("aomsLogDeleteServiceImpl")
public class LogOnTimeDeleteServiceImpl implements OnTimeTaskBusiJob{

	@Autowired
	private ReLogProcessService reLogProcessService;
	
	@Autowired
	private ParamSystemService paramSystemService;
	
	@Autowired
	private LoginfoOperateService loginfoOperateService;
	
	private final static String PRESERVE_PARAM_SUFFIX = "_PreservePeriod";
	
	private final static Logger logger = LoggerFactory.getLogger(LogOnTimeDeleteServiceImpl.class);
	
	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		
		// modi by mowj 20150904
		// 操作日志记录的类型与数量分析。从正式上线开始：2015-09-01 00:00:00~2015-09-04 14:32:29
		//	ECI-PUSH	155633
		//  ECI-REQUEST	372
		//  ECI-REQUEST-CHECK	25
		//  OMS-REQUEST	9247912
		//  SYS-PARAM-DELETE	1
		// log_info_t表的大小已经到了4.6GB
		// 得到结论：
		// 1. OMS的请求数量最大，已经逼近千万行。原因：OMS的库存更新十分频繁，且使用脚本批处理，所以量会十分庞大。所以更新失败的记录也会很多
		// 2. 中台推送的数量排第二。
		// 3. 剩下其他类型的日志数量不多，所以没有关系
		// 对策：
		// 1. OMS-REQUEST 类型的日志保留7天
		// 2. ECI-PUSH    类型的日志保留14天
		// 3. 其他                      类型的日志保留90天
		
		// 删除log_info_t中的日志
		// 1. 删除OMS-REQUEST类型的日志（OMS请求）
		deleteLogInfo(LogInfoBizTypeEnum.OMS_REQUEST);
		// 2. 删除ECI-PUSH类型的日志（中台推送）
		deleteLogInfo(LogInfoBizTypeEnum.ECI_PUSH);
		// 3. 删除其他类型的日志
		deleteLogInfo(LogInfoBizTypeEnum.OTHER);
//		System.out.println("ECI-PUSH 執 行 刪 除 共 ："
//				+ reLogProcessService.deleteLogByCondition(prepareDeleteHQL(
//						" 1=1 ").toString()) + " 筆資料");
//		System.out.println("其 他 非 ECI-PUSH 執 行 刪 除 共 ："
//				+ reLogProcessService.deleteLogByCondition(prepareDeleteHQL(
//						" businessType!='ECI-PUSH' ").toString()) + " 筆資料");
		
		// modi by mowj 20150904
		// 平台原始数据类型与数量分析。从正式上线开始：2015-09-01 00:00:00~2015-09-04 14:57:23
		// 4380604	AomsitemT
		// 138992	AomsordT
		// 44899	AomsrefundT
		// source_log_t表的大小已经到了23.8GB
		// 得到结论：
		// 1. AomsitemT类型的source_log已经十分庞大，逼近440万行。原因：平台商品库存一有修改就能从API拿到，所以不断变化的库存导致如此大的数量
		// 2. AomsordT与AomsrefundT的行数还可以接受
		// 对策：
		// 1. AomsitemT   类型的原始数据保留7天
		// 2. AomsordT    类型的原始数据保留35天(配合水星的财务结算周期)
		// 3. AomsrefundT 类型的原始数据保留35天(配合水星的财务结算周期)
		
		// 删除source_log_t中的记录
		// 1. 删除AomsordT类型的日志（OMS请求）
		deleteSourceLog(SourceLogBizTypeEnum.AOMSORDT);
		// 1. 删除AomsrefundT类型的日志（OMS请求）
		deleteSourceLog(SourceLogBizTypeEnum.AOMSREFUNDT);
		// 1. 删除AomsitemT类型的日志（OMS请求）
		deleteSourceLog(SourceLogBizTypeEnum.AOMSITEMT);
		
//		System.out.println("刪除Source Log 資料 ："
//				+ reLogProcessService
//						.deleteLogByCondition(prepareDeleteSourceLogHQL()
//								.toString()) + " 筆資料");
		return false;
	}
	
//	@Deprecated
//	private String prepareDeleteHQL(String businessTypeCondition) {
//		StringBuffer sbfAomsSQL = new StringBuffer();
//
//		Integer decreaseMonth = (Integer.parseInt(paramSystemService
//				.getSysParamByKey("ReserveMonth").toString())) * -1;
//
//		String targetDay = DateTimeTool.format(
//				DateTimeTool.getAfterMonths(new Date(), decreaseMonth),
//				"yyyy-MM-dd HH:mm:ss");
//
//		sbfAomsSQL
//				.append("DELETE FROM LogInfoT WHERE final_status='1' AND reqTime <= '"
//						+ targetDay + "' AND " + businessTypeCondition);
//
//		return sbfAomsSQL.toString();
//	}
	
	/**
	 * 根据不同的业务类型，与系统参数设定，定时删除log_info_t日志中的记录，
	 * 并记录操作的结果
	 * @param loginfoBizTypeEnum 如果为NULL，则删除除了OMS-REQUEST与ECI-PUSH以外的所有日志
	 * @author 维杰
	 * @since 2015.09.06
	 */
	private void deleteLogInfo(LogInfoBizTypeEnum loginfoBizTypeEnum) {
		logger.info("Ready to deleteLogInfo. BizType is {}", loginfoBizTypeEnum.getValueString());
		LogInfoT delActLogInfoT = null;
		// 准备执行删除
		Date startDate = new Date();
		String deleteSql = prepareLogInfoDeleteSql(loginfoBizTypeEnum);
		int affectedRows = 0;
		int loopCount = 0;
		do {
			affectedRows = reLogProcessService.deleteLogBySql(deleteSql);
			loopCount++;
		} while (affectedRows != 0);
		Date endDate = new Date();
		
		// 操作记录到日志
		delActLogInfoT = new LogInfoT();
		delActLogInfoT.setIpAddress(SystemEnum.IP_LOCALHOST.getValueString());
		delActLogInfoT.setCallMethod("aomsLogDeleteServiceImpl.timeOutExecute");
		delActLogInfoT.setBusinessType(LogInfoBizTypeEnum.LOGINFO_DELETE.getValueString());
		delActLogInfoT.setReqType("taskSchedule");
		delActLogInfoT.setReqParam(deleteSql);
		delActLogInfoT.setResSize(BigInteger.valueOf(affectedRows));
		delActLogInfoT.setReqTime(startDate);
		delActLogInfoT.setResTime(endDate);
		delActLogInfoT.setResCode(affectedRows * loopCount + "");
		if (affectedRows != -1) {
			delActLogInfoT.setIsSuccess(true);
			delActLogInfoT.setResMsg(LogInfoBizTypeEnum.LOGINFO_DELETE.getValueString() + " successed ");
			delActLogInfoT.setFinalStatus("1");
		} else {
			delActLogInfoT.setIsSuccess(false);
			delActLogInfoT.setResMsg(LogInfoBizTypeEnum.LOGINFO_DELETE.getValueString() + " failed ");
			delActLogInfoT.setFinalStatus("0");
		}
		loginfoOperateService.newTransaction4SaveLog(delActLogInfoT);
		
		logger.info("DeleteLogInfo succeeded! BizType is {}", loginfoBizTypeEnum.getValueString());
	}
	
	/**
	 * 根据不同的业务类型，准备删除log_info_t的DELETE的HQL语句
	 * @param loginfoBizTypeEnum 如果为NULL，则删除除了OMS-REQUEST与ECI-PUSH以外的所有日志
	 * @return DELETE的HQL语句
	 * @author 维杰
	 * @since 2015.09.06
	 */
	private String prepareLogInfoDeleteSql(LogInfoBizTypeEnum loginfoBizTypeEnum) {
		String preservePeriod = "";
		String logInfoBizType = "";
		if (loginfoBizTypeEnum != null) {
			logInfoBizType = loginfoBizTypeEnum.getValueString();
		}
		if (paramSystemService.getSysParamByKey(logInfoBizType + PRESERVE_PARAM_SUFFIX) == null) {
			preservePeriod = getDefaultLogInfoPreserveDays(loginfoBizTypeEnum);
		} else {
			preservePeriod = (String) paramSystemService.getSysParamByKey(logInfoBizType + PRESERVE_PARAM_SUFFIX);
			if (!preservePeriod.matches(RegEnums.NUMBER.getRegExpString())) {
				preservePeriod = getDefaultLogInfoPreserveDays(loginfoBizTypeEnum);
			}
		}
		int preservePeriodInterger = Integer.parseInt(preservePeriod);
		String preserveDeadLineDay = DateTimeTool.format(
				DateTimeTool.getBeforeDays(new Date(), preservePeriodInterger));
		
		String logInfoDeleteBatchSize = "";
		if (paramSystemService.getSysParamByKey("LogInfoDeleteBatchSize") == null) {
			logInfoDeleteBatchSize = getDefaultDeleteBatchSize();
		} else {
			logInfoDeleteBatchSize = (String) paramSystemService.getSysParamByKey("LogInfoDeleteBatchSize");
			if (!logInfoDeleteBatchSize.matches(RegEnums.NUMBER.getRegExpString())) {
				logInfoDeleteBatchSize = getDefaultDeleteBatchSize();
			}
		}
		
		StringBuffer deleteSql = new StringBuffer();
		deleteSql.append("DELETE t1 FROM log_info_t t1 ");
		deleteSql.append("JOIN ( ");
		deleteSql.append("SELECT log_id FROM log_info_t ");
		deleteSql.append("WHERE 1=1 ");
		deleteSql.append("AND req_time <= '").append(preserveDeadLineDay).append("' ");
		if (logInfoBizType.trim().length() > 0) {
			deleteSql.append("AND business_type = '").append(logInfoBizType).append("' ");
		} else {
			deleteSql.append("AND business_type NOT IN ( ") 
					.append("'").append(LogInfoBizTypeEnum.OMS_REQUEST.getValueString()).append("', ")
					.append("'").append(LogInfoBizTypeEnum.ECI_PUSH.getValueString()).append("' ")
					.append(") ");
		}
		deleteSql.append("LIMIT 0, ").append(logInfoDeleteBatchSize).append(" ) t2 ");
		deleteSql.append("ON t1.log_id=t2.log_id ");
		
		return deleteSql.toString();
		
	}
	
	/**
	 * 根据不同业务类型，获取默认的日志保留天数
	 * @param loginfoBizTypeEnum
	 * @return 默认的日志保留天数
	 * @author 维杰
	 * @since 2015.09.06
	 */
	private String getDefaultLogInfoPreserveDays(LogInfoBizTypeEnum loginfoBizTypeEnum) {
		String preservePeriod = "";
		if (loginfoBizTypeEnum == LogInfoBizTypeEnum.OMS_REQUEST) {
			preservePeriod = "7";
		} else if (loginfoBizTypeEnum == LogInfoBizTypeEnum.ECI_PUSH) {
			preservePeriod = "14";
		} else {
			preservePeriod = "90";
		}
		return preservePeriod;
	}
	
	private String getDefaultDeleteBatchSize() {
		return "5000";
	}
	
//	@Deprecated
//	private String prepareDeleteSourceLogHQL(){
//		String hql = "";
//		Integer days = (Integer.parseInt(paramSystemService.getSysParamByKey(
//				"ClearSourceTime").toString()))
//				* -1;
//		String targetDay = DateTimeTool.format(
//				DateTimeTool.getAfterDays(new Date(), days),
//				"yyyy-MM-dd HH:mm:ss");
//		hql = "DELETE FROM SourceLogT WHERE createDate <='" + targetDay + "'";
//		return hql;
//		
//	}
	
	/**
	 * 根据不同的业务类型，与系统参数设定，定时删除source_log_t日志中的记录，
	 * 并记录操作的结果
	 * @param sourceLogBizTypeEnum 如果为NULL，则删除所有的原始数据日志
	 * @author 维杰
	 * @since 2015.09.06
	 */
	private void deleteSourceLog(SourceLogBizTypeEnum sourceLogBizTypeEnum) {
		logger.info("Ready to deleteSourceLog. BizType is {}", sourceLogBizTypeEnum.getValueString());
		LogInfoT delActLogInfoT = null;
		// 准备执行删除
		Date startDate = new Date();
		String deleteSql = prepareSourceLogDeleteSql(sourceLogBizTypeEnum);
		
		int affectedRows = 0;
		int loopCount = 0;
		do {
			affectedRows = reLogProcessService.deleteLogBySql(deleteSql);
			loopCount++;
		} while (affectedRows != 0);
		Date endDate = new Date();
		
		// 操作记录到日志
		delActLogInfoT = new LogInfoT();
		delActLogInfoT.setIpAddress(SystemEnum.IP_LOCALHOST.getValueString());
		delActLogInfoT.setCallMethod("aomsLogDeleteServiceImpl.timeOutExecute");
		delActLogInfoT.setBusinessType(LogInfoBizTypeEnum.SOURCELOG_DELETE.getValueString());
		delActLogInfoT.setReqType("taskSchedule");
		delActLogInfoT.setReqParam(deleteSql);
		delActLogInfoT.setResSize(BigInteger.valueOf(affectedRows));
		delActLogInfoT.setReqTime(startDate);
		delActLogInfoT.setResTime(endDate);
		delActLogInfoT.setResCode(affectedRows * loopCount + "");
		if (affectedRows != -1) {
			delActLogInfoT.setIsSuccess(true);
			delActLogInfoT.setResMsg(LogInfoBizTypeEnum.SOURCELOG_DELETE.getValueString() + " successed ");
			delActLogInfoT.setFinalStatus("1");
		} else {
			delActLogInfoT.setIsSuccess(false);
			delActLogInfoT.setResMsg(LogInfoBizTypeEnum.SOURCELOG_DELETE.getValueString() + " failed ");
			delActLogInfoT.setFinalStatus("0");
		}
		loginfoOperateService.newTransaction4SaveLog(delActLogInfoT);
		
		logger.info("DeleteSourceLog succeeded! BizType is {}", sourceLogBizTypeEnum.getValueString());
	}
	
	/**
	 * 根据不同的业务类型，准备删除source_log_t的DELETE的HQL语句
	 * @param sourceLogBizTypeEnum 如果为NULL，则删除所有的原始数据日志
	 * @return DELETE的HQL语句
	 * @author 维杰
	 * @since 2015.09.06
	 */
	private String prepareSourceLogDeleteSql(SourceLogBizTypeEnum sourceLogBizTypeEnum) {
		String preservePeriod = "";
		String sourceLogBizType = "";
		if (sourceLogBizTypeEnum != null) {
			sourceLogBizType = sourceLogBizTypeEnum.getValueString();
		}
		if (paramSystemService.getSysParamByKey(sourceLogBizType + PRESERVE_PARAM_SUFFIX) == null) {
			preservePeriod = getDefaultSourceLogPreserveDays(sourceLogBizTypeEnum);
		} else {
			preservePeriod = (String) paramSystemService.getSysParamByKey(sourceLogBizType + PRESERVE_PARAM_SUFFIX);
			if (!preservePeriod.matches(RegEnums.NUMBER.getRegExpString())) {
				preservePeriod = getDefaultSourceLogPreserveDays(sourceLogBizTypeEnum);
			}
		}
		int preservePeriodInterger = Integer.parseInt(preservePeriod);
		String preserveDeadLineDay = DateTimeTool.format(
				DateTimeTool.getBeforeDays(new Date(), preservePeriodInterger));
		
		String sourceLogDeleteBatchSize = "";
		if (paramSystemService.getSysParamByKey("SourceLogDeleteBatchSize") == null) {
			sourceLogDeleteBatchSize = getDefaultDeleteBatchSize();
		} else {
			sourceLogDeleteBatchSize = (String) paramSystemService.getSysParamByKey("SourceLogDeleteBatchSize");
			if (!sourceLogDeleteBatchSize.matches(RegEnums.NUMBER.getRegExpString())) {
				sourceLogDeleteBatchSize = getDefaultDeleteBatchSize();
			}
		}
		
		StringBuffer deleteSql = new StringBuffer();
		deleteSql.append("DELETE t1 FROM source_log_t t1 ");
		deleteSql.append("JOIN ( ");
		deleteSql.append("SELECT log_id FROM source_log_t ");
		deleteSql.append("WHERE 1=1 ");
		deleteSql.append("AND create_Date <= '").append(preserveDeadLineDay).append("' ");
		if (sourceLogBizType.trim().length() > 0) {
			deleteSql.append("AND business_type = '").append(sourceLogBizType).append("' ");
		}
		deleteSql.append("LIMIT 0, ").append(sourceLogDeleteBatchSize).append(" ) t2 ");
		deleteSql.append("ON t1.log_id=t2.log_id ");
		
		return deleteSql.toString();
		
	}
	
	/**
	 * 根据不同业务类型，获取默认的日志保留天数
	 * @param sourceLogBizTypeEnum
	 * @return 默认的日志保留天数
	 * @author 维杰
	 * @since 2015.09.06
	 */
	private String getDefaultSourceLogPreserveDays(SourceLogBizTypeEnum sourceLogBizTypeEnum) {
		String preservePeriod = "";
		if (sourceLogBizTypeEnum == SourceLogBizTypeEnum.AOMSORDT) {
			preservePeriod = "35";
		} else if (sourceLogBizTypeEnum == SourceLogBizTypeEnum.AOMSREFUNDT) {
			preservePeriod = "35";
		} else if (sourceLogBizTypeEnum == SourceLogBizTypeEnum.AOMSITEMT) {
			preservePeriod = "7";
		} else {
			preservePeriod = "14";
		}
		return preservePeriod;
	}
	
}
