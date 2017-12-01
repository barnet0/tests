
  var ChartStore = {
  	

  	
  	createNew: function(xa, ya, sumStore){
  		 var cs = {};
  		 cs.sum = sumStore;
  		 cs.xArray = xa;
  		 cs.yArray = ya;
  		 cs.runChart = function() {
  		 	  cs.eChart.setOption(option); //顯示 chart.
  		 };
  		 
  		 cs.setX = function(xData) {
  		 	  var max = cs.xArray.length;
  		 	  for (var i =0; i < xData.length; i++) {
  		 	  	cs.xArray[max + i] = xData[i];
  		 	  }
  		 };
  		 
  		 cs.setY = function(yData) {
  		 	  var max = cs.yArray.length;
  		 	  for (var i =0; i < yData.length; i++) {
  		 	  	cs.yArray[max + i] = yData[i];
  		 	  	cs.sum[max + i] = cs.calData(yData, i)
  		 	  }
  		 }
  		 cs.setOption = function(op) {
  		 	  cs.option = op;
  		 };
  		 
  		 cs.setEChart = function(ec) {
  		 	  cs.eChart = ec;
  		 };
  		 cs.calData = function (data, endIndex) {
			    var sum = 0;
			    for (var i = 0; i <= endIndex; i++) {
				     sum += data[i];
			    }
			    return sum;
		   };
  		 
  		 return cs;
	 }
};
  
	function getXmlHttp() {
		 var xmlhttp;
	   if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
	     xmlhttp=new XMLHttpRequest();
	   } else {// code for IE6, IE5
	     xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   return xmlhttp;
	}
	
	/**
	* 執行 ajax.
	*/
  function doOperate(startDate, cs){
	   var xmlhttp = getXmlHttp();
	   
	   xmlhttp.onreadystatechange=function() {
	     if (xmlhttp.readyState==4 && xmlhttp.status==200) {
	     	  /*if (opType == 1) {
	     	  	doLoadConfig(xmlhttp.responseText);
	     	  } else  if (opType == 2) {
	     	  	if (Object.keys(updateData).length == 0) {
	     	  		alert("None change any system parameter!");
	     	  	} else {
	     	  		doUpdate(xmlhttp.responseText);
	     	  	}	     	  	
	     	  }*/
	     	  
	     	  var jsonData = JSON.parse(xmlhttp.responseText);
	     	  cs.setX(jsonData['X']);
	     	  cs.setY(jsonData['Y']);
	     	  //alert('go');
	     	  //cs.runChart();
	     } else {
	   	    //alert(xmlhttp.responseText);
	     }
	   }
	   
	   var by_post='startDate=' + startDate; //document.getElementById('startDate'); //將變數放進字串
	   xmlhttp.open('POST', '/dsmWeb/data.jsp',true);
	   xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');  //**重要一定要加上
	   xmlhttp.send(by_post);
  }