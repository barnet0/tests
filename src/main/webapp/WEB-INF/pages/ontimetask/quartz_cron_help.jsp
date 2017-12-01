<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>定时作业定时表达式帮助指南</title>
<style type="text/css">
	body{ margin:0 ;padding:0px; line-height:150%; font-family: Verdana, Arial ; font-size:12px; background:#ececec; }
	.main{ background:#fff;width:900px; padding:0 10px; margin:0 auto; border-left:1px solid #ddd; border-right:1px solid #ddd; }
	.tab_table{ border-bottom:1px solid #ddd; border-right:1px solid #ddd; width:100%;}
	.tab_table td,.tab_table th{ border-left:1px solid #ddd; border-top:1px solid #ddd; padding:3px 5px; }
	.tab_table th{ font-weight:normal; background:#ececec;}
	h1{margin:0px;  padding:10px 20px; font-size:16px; line-height:40px; font-family: Verdana, Arial ; text-indent:8em;}
	ul,li{ margin:0px; padding:0px;}
	ul{ margin-top:20px;}
	li{ list-style-type: disc; list-style-position: inside;  line-height:30px; font-weight:bold; color:#666;}
	li span{ margin-left:15px; display:block;  background:#ececec; padding:5px ; color:#4d4d4d; font-weight:normal;}
</style>
</head>
<body>
<div class="main">
<h1></h1>
 <table cellpadding="0" cellspacing="0" border="0" class="tab_table">
    <tr>
        <th  colspan="3">quartz cron 表达式详解</th>
    </tr>
    <tr>
        <th>字段</th>
        <th>允许值</th>
        <th>允许的特殊字符 </th>
    </tr>
    <tr>
        <td>秒</td>
        <td>0-59 </td>
        <td> , - * /</td>
    </tr>
    <tr>
        <td>分</td>
        <td>0-59 </td>
        <td>, - * / </td>
    </tr>
    <tr>
        <td>小时 </td>
        <td>0-23 </td>
        <td>, - * / </td>
    </tr>
    <tr>
        <td>日期</td>
        <td> 1-31</td>
        <td>, - * ? / L W C</td>
    </tr>
    <tr>
        <td>月份</td>
        <td>1-12 或者 JAN-DEC</td>
        <td>, - * /</td>
    </tr>
    <tr>
        <td>星期</td>
        <td> 1-7 或者 SUN-SAT </td>
        <td>, - * ? / L C #</td>
    </tr>
    <tr>
        <td>年（可选）</td>
        <td>留空, 1970-2099</td>
        <td> , - * /</td>
    </tr>
 </table>
 <ul>
<li>“*”字符被用来指定所有的值。<span>如：”*“在分钟的字段域里表示“每分钟”。</span></li>
<li>“?”字符只在日期域和星期域中使用。它被用来指定“非明确的值”。当你需要通过在这两个域中的一个来指定一些东西的时候，它是有用的。看下面的例子你就会明白。 
<span>月份中的日期和星期中的日期这两个元素时互斥的一起应该通过设置一个问号(?)来表明不想设置那个字段。 </span></li>
   <li>“-”字符被用来指定一个范围。
 <span>如：“10-12”在小时域意味着“10点、11点、12点”。</span>
   <li> “,”字符被用来指定另外的值。
 <span>如：“MON,WED,FRI”在星期域里表示”星期一、星期三、星期五”。 </span>
 </li>
   <li> “/”字符用于指定增量
 <span>如：“0/15”在秒域意思是没分钟的0，15，30和45秒。“5/15”在分钟域表示没小时的5，20，35 和50。符号“*”在“/”前面（如：*/10）等价于0在“/”前面（如：0/10）。记住一条本质：表达式的每个数值域都是一个有最大值和最小值的集合，如：秒域和分钟域的集合是0-59，日期域是1-31，月份域是1-12。字符“/”可以帮助你在每个字符域中取相应的数值。如：“7/6”在月份域的时候只有当7月的时候才会触发，并不是表示每个6月。</span>
 </li>
 
  <li>L是‘last’的省略写法
 <span>可以表示day-of-month和day-of-week域，但在两个字段中的意思不同，例如day- of-month域中表示一个月的最后一天。如果在day-of-week域表示‘7’或者‘SAT’，如果在day-of-week域中前面加上数字，它表示一个月的最后几天，例如‘6L’就表示一个月的最后一个星期五。 </span>
 </li>

  <li> 字符“W”只允许日期域出现
 <span>这个字符用于指定日期的最近工作日。例如：如果你在日期域中写 “15W”，表示：这个月15号最近的工作日。所以，如果15号是周六，则任务会在14号触发。如果15好是周日，则任务会在周一也就是16号触发。如果是在日期域填写“1W”即使1号是周六，那么任务也只会在下周一，也就是3号触发，“W”字符指定的最近工作日是不能够跨月份的。字符“W”只能配合一个单独的数值使用，不能够是一个数字段，如：1-15W是错误的。</span>
 </li>

  <li> “L”和“W”可以在日期域中联合使用
 <span>LW表示这个月最后一周的工作日。 </span>
 </li>
  <li> 字符“#”只允许在星期域中出现
 <span>这个字符用于指定本月的某某天。例如：“6#3”表示本月第三周的星期五（6表示星期五，3表示第三周）。“2#1”表示本月第一周的星期一。“4#5”表示第五周的星期三。 </span>
 </li>
  <li> 字符“C”允许在日期域和星期域出现
 <span>这个字符依靠一个指定的“日历”。也就是说这个表达式的值依赖于相关的“日历”的计算结果，如果没有 “日历”关联，则等价于所有包含的“日历”。如：日期域是“5C”表示关联“日历”中第一天，或者这个月开始的第一天的后5天。星期域是“1C”表示关联 “日历”中第一天，或者星期的第一天的后1天，也就是周日的后一天（周一）。 </span>
 </li>
  
  <li> 
 <span></span>
 </li>
 
 </ul>
 

表达式举例：<br/>
   <br/>
   
   
"0 0 12 * * ?"         每天中午12点触发<br/>
"0 15 10 ? * *"        每天上午10:15触发<br/>
"0 15 10 * * ?"        每天上午10:15触发<br/>
"0 15 10 * * ? *"      每天上午10:15触发<br/>
"0 15 10 * * ? 2005"   2005年的每天上午10:15触发<br/>
"0 * 14 * * ?"         在每天下午2点到下午2:59期间的每1分钟触发<br/>
"0 0/5 14 * * ?"       在每天下午2点到下午2:55期间的每5分钟触发<br/>
"0 0/5 14,18 * * ?"    在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发<br/>
"0 0-5 14 * * ?"       在每天下午2点到下午2:05期间的每1分钟触发<br/>
"0 10,44 14 ? 3 WED"   每年三月的星期三的下午2:10和2:44触发<br/>
"0 15 10 ? * MON-FRI"  周一至周五的上午10:15触发<br/>
"0 15 10 15 * ?"       每月15日上午10:15触发<br/>
"0 15 10 L * ?"        每月最后一日的上午10:15触发<br/>
"0 15 10 ? * 6L"       每月的最后一个星期五上午10:15触发 <br/>
"0 15 10 ? * 6L 2002-2005"     2002年至2005年的每月的最后一个星期五上午10:15触发<br/>
"0 15 10 ? * 6#3"      每月的第三个星期五上午10:15触发<br/>
0 6 * * *              每天早上6点<br/>
0 */2 * * *            每两个小时<br/>
0 23-7/2，8 * * *      晚上11点到早上8点之间每两个小时，早上八点<br/>
0 11 4 * 1-3           每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点<br/>
0 4 1 1 *              1月1日早上4点<br/>
<br/>

<br/>
</body>
</html>