package com.digiwin.ecims.ontime.service;

public interface ScheduleLastUpdateTimeAdjustService {

	/**
	 * 在系统启动时更新排程的lastUpdateTime
	 * @param timeAhead
	 * @return 更新的行数
	 */
	public int updateLastUpdateTime(String timeAhead);
}
