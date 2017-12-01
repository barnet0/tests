package com.digiwin.ecims.ontime.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.digiwin.ecims.ontime.dao.OnTimeDAO;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

// 不声明为@Service,必须在 web环境下的 spring xml 中去声明,需注入jdbcTemplate.因为这是类库，避免重复。
// 必须是多实例,因为有一个执行的SQL语句参数
public class JdbcJobExecService implements OnTimeTaskBusiJob {

  // spring xml 中注入
  @Autowired
  private OnTimeDAO onTimeDAO;

  // 由定时作业调度器从数据库中取出预设值语句放入
  private String sql = "";

  public JdbcJobExecService() {}

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    if (sql == null || sql.length() <= 0) {
      return false;
    }
    try {
      onTimeDAO.update(sql);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }


}

