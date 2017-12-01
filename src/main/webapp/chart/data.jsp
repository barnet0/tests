<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.fasterxml.jackson.databind.*"%>
<%
  Connection conn = null;
  ResultSet rs = null;
	Statement stat = null;
	String error = null;
	List<String> dataX = new ArrayList<String>();
	List<Integer> dataY = new ArrayList<Integer>();
  try {
    String endDate = (String)request.getParameter("startDate");
  	java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  	java.util.Date ed = format2.parse(endDate);
  	System.out.println("-------------------------------------");
  	System.out.println("endDate=>" + endDate);
  	
  	//扣兩小時
  	Calendar cal = Calendar.getInstance(); 
    cal.setTime(ed);
    cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 2);
    System.out.println(cal.getTime());
    String startDate = format2.format(cal.getTime());
  	System.out.println("startDate=>" + startDate);
  	System.out.println("-------------------------------------");
  	
  	String sql = "SELECT substr(aomsord006, 1, 16), SUM(aomsord022) AS num FROM aomsord_t "
  	     + " WHERE aomsord006 >= '" + startDate + "' "
  	     + " AND aomsord006 <='" + endDate + "' "
  	     + " GROUP BY substr(aomsord006, 1, 16) "
  	     + " ORDER BY substr(aomsord006, 1, 16) ";
  	//System.out.println(sql);

  	
  	String driverClass = "com.mysql.jdbc.Driver";
    String jdbcUrl = "jdbc:mysql://192.168.58.199:3306/mercuryecinf?useUnicode=true&amp;characterEncoding=utf8&amp;zeroDateTimeBehavior=convertToNull";
    String user = "shuixing";
    String password = "shuixing123";
    Class.forName(driverClass);
    conn =  DriverManager.getConnection(jdbcUrl, user, password);
    stat = conn.createStatement();			
		rs = stat.executeQuery(sql);
		
    while(rs.next()) {
    	 String colTime = rs.getObject(1).toString().substring(10, 16);
    	 int colMoney = rs.getInt(2);
    	 dataX.add(colTime);
    	 dataY.add(colMoney);
    }
  } catch (Exception e) {
  	e.printStackTrace();
  	error = e.getMessage();
	} finally {
		if (rs != null) {rs.close();}
		if (stat != null) {stat.close();}
		if (conn != null) {conn.close();}
	}
%>

<%
Map<String, Object[]> mData = new HashMap<String, Object[]>();
mData.put("X", dataX.toArray(new String[0]));
mData.put("Y", dataY.toArray(new Integer[0]));

ObjectMapper m = new ObjectMapper();  
String value = m.writeValueAsString(mData);  
out.println(value);
%>
