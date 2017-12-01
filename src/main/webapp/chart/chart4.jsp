<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ECharts</title>

</head>
<body>
<!-- 准备数据 -->
<script>
	
     var time = new Array();
		 var data = new Array();
		 var sum = new Array();
		 //初始值
		 for (var i = 0; i < 110; i++) {
		 	  time[i] = 'n/a' + i;
		 	  data[i] = 0;
		 	  sum[i] = 0;
		 }
		 
		/*
		var sumData=[];

		for (var i = 0; i <= data.length; i++) {
			sumData[i] = sum(data, i);
		};

		function sum(data, endIndex) {
			var sum = 0;
			for (var i = 0; i <= endIndex; i++) {
				sum += data[i];
			};
			return sum;
		}
		*/
	</script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="height:400px"></div>
<div id="totalMoney"></div>

<!-- ECharts单文件引入 -->
<!-- <script src="echarts.js"></script> -->
<script src="digiwin.js"></script>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">

	      var cs = ChartStore.createNew(time, data, sum);
	      //doOperate('2015-11-12 00:00:00', cs); //從遠端拿資料	
	
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                var initialLen = 100;
                var option = {
				    title : {
				        text: '销售额实时趋势图',
				        subtext: '水星家纺2015年双十一',
				        x: 'center'
				    }, // end title
				    tooltip : {
				        trigger: 'axis'
				    }, // end tooltip
				    legend: {
				        data:['总销售额','销售额'],
				        x : 'left'
				    }, // end legend
				    /*toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },*/ // end toolbox
				    calculable : true,
				    /*dataZoom : {
				        show : true,
				        realtime: true,
				        start : 0,
				        end : 30
				    },*/
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : (function (){				                
				            	var i = initialLen;
				                var res = [];
				                while (i--) {
				                	var now = time[initialLen - (i + 1)];
				                    res.push(now);
				                }
				                return res;
				            })()
				        }
				    ], // end xAxis
				    yAxis : [
				        {
				            type : 'value',
				            scale: true,
				            name: '总销售额',
				            axisLabel : {
				                formatter: '￥{value}'
				            }
				        },
				        {
				            type : 'value',
				            scale: true,
				            name: '销售额',
				            axisLabel : {
				                formatter: '￥{value}'
				            }
				        }
				    ], // end yAxis
				    series : [
				        {
				            name:'总销售额',
				            type:'line',
				            data:(function (){
				                var res = [];
				                var i = initialLen;
				                while (i--) {
				                    res.push(sum[initialLen - (i + 1)]);
				                }
				                return res;
				            })(),
				            markLine : {
				                data : [
				                    {type : 'average', name: '平均值'}
				                ]
				            }
				        },
				        {
				            name:'销售额',
				            type:'line',
            				yAxisIndex: 1,
				            data:(function (){
				                var res = [];
				                var i = initialLen;
				                while (i--) {
				                    res.push(data[initialLen - (i + 1)]);
				                }
				                return res;
				            })(),
				            markLine : {
				                data : [
				                    {type : 'average', name: '平均值'}
				                ]
				            }
				        }
				    ] // end series
				};  // end option

				// 刷新数据的设定

				// 刷新的起始位置，需要与上方设定的初始值相连，图才能连续
				var intervalInitial = initialLen;
				var lastData;
				var lastSumData;
				var axisData;
				//clearInterval(timeTicket);
				timeTicket = setInterval(function (){
					if (intervalInitial == data.length) return;
				    axisData = time[intervalInitial];
				    lastSumData = sum[intervalInitial];
				    lastData = data[intervalInitial];
				    
				    // 动态数据接口 addData
				    myChart.addData([
				        [
				            0,        // 系列索引
				            lastSumData, // 新增数据
				            false,    // 新增数据是否从队列头部插入
				            false,    // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
				            axisData  // 坐标轴标签
				        ],
				    	[
				            1,        // 系列索引
				            lastData, // 新增数据
				            false,     // 新增数据是否从队列头部插入
				            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
				            axisData
				        ]
				    ]);
				    
				    document.getElementById("totalMoney").innerHTML = 'intervalInitial:' + intervalInitial + ',' + data[intervalInitial];
			    	intervalInitial++;
			    	
			    	//最後十筆, 新增資料
				    if (intervalInitial > (data.length - 10)) {
				    	var d = new Date();
              var str = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate() + ' ';
              str += (d.getHours() < 10) ? '0' + d.getHours(): d.getHours();
              str += ':' + d.getMinutes() + ':' + d.getSeconds();

				    	doOperate(str, cs); //從遠端拿資料
				    }

			    	var prevOption = myChart.getOption();
			    	prevOption.title.subtext = new Date().toLocaleTimeString();
			    	myChart.setOption(prevOption);
				}, 1 * 1000);

                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
    </script>
</body>