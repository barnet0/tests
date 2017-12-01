
var LogMaintainGridPanel = Ext.extend(Ext.grid.GridPanel,{
	id: 'logMaintainGridPanelId',
	constructor: function(ctx){
		Ext.QuickTips.init();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var number = new Ext.grid.RowNumberer();
		var cm = new Ext.grid.ColumnModel([
			number, sm,
		{
			header: '日志编号',
			dataIndex: 'logId',
			align: 'center',
			hidden:false
		},{
			header: '响应IP位置',
			dataIndex: 'ipAddress',
			align: 'center',
			hidden:true
		},{
			header: '呼叫方法',
			dataIndex: 'callMethod',
			align: 'center',
			hidden:true
		},{
			header: '业务类型',
			dataIndex: 'businessType',
			align: 'center',
			width:140,
			renderer: function(value){
				if(value == "OMS-REQUEST"){
					return "OMS指令请求";
				} else if(value == "ECI-PUSH"){
					return "ECI排程推送";
				} else if(value == "ECI-REQUEST"){
					return "ECI排程拉取";
				} else if(value == "SYS-PARAM-UPDATE"){
					return "SYSPARAM更新";
				} else if(value == "SYS-PARAM-SAVE"){
					return "SYSPARAM新增";
				} else if(value == "SYS-PARAM-DELETE"){
					return "SYSPARAM删除";
				} else if(value == "MANUAL-SYNC"){
					return "SYNC手动拉取";
				} else if(value == "MANUAL-PUSH"){
					return "PUSH手动推送";
				}
				return "ERROR错误业务类型";
			}
		},{
			header: '指令类型',
			dataIndex: 'reqType',
			align: 'center'
		},{
			header: '请求JSON',
			dataIndex: 'reqParam',
			align: 'center',
			hidden:true
		},{
			header: '是否请求成功',
			width:140,
			dataIndex: 'isSuccess',
			align: 'center',
			renderer: function(value){
				if(value == '1'){
					return "1-成功";
				}else if(value == '0'){
					return "0-失败";
				}else{
					return value+"-其他";
				}
			}
		},{
			header: '日志最终处理结果',
			dataIndex: 'final_status',
			align: 'center',
			width:150,
			renderer: function(value){
				if(value == '1'){
					return "1-成功";
				}else if(value == '0'){
					return "0-失败";
				}else{
					return value+"-其他";
				}
			}
		},{
			header: '请求时间',
			dataIndex: 'reqTime',
			align: 'center',
			width:170,
			dateFormat : 'time' ,
			renderer:function(value){
				if(value!=null && value != "" && value != "null"){
					return Ext.util.Format.date(value,'Y-m-d H:i:s');
				}else{
					return Ext.util.Format.date(new Date(),'Y-m-d H:i:s');
				}
			}
		},{
			header: '回应时间',
			dataIndex: 'resTime',
			align: 'center',
			width:170,
			dateFormat : 'time' ,
			renderer:function(value){
				if(value!=null && value != "" && value != "null"){
					return Ext.util.Format.date(value,'Y-m-d H:i:s');
				}else{
					return Ext.util.Format.date(new Date(),'Y-m-d H:i:s');
				}
			}
		},{
			header: '错误单据类型',
			dataIndex: 'errBillType',
			width:180,
			align: 'center'
		},{
			header: '错误信息说明',
			dataIndex: 'resErrMsg',
			width:180,
			align: 'center'
		},{
			header: '系统错误',
			dataIndex: 'errMsg',
			width:120,
			align: 'center',
			hidden:true
		},{
			header: '错误单据单号',
			dataIndex: 'errBillId',
			align: 'center',
			hidden:true
		},{
			header: '剩余重推次数',
			dataIndex: 'pushLimits',
			align: 'center',
			hidden:true
		},{
			header: '回应数量',
			dataIndex: 'resSize',
			align: 'center',
			hidden:true
		},{
			header: '自定义错误代码',
			dataIndex: 'resErrCode',
			width:140,
			align: 'center',
			hidden:true
		},{
			header: '备注',
			dataIndex: 'remark',
			align: 'center',
			hidden:true
		}]);
		var logMaintainStore = new Ext.data.JsonStore({
			url:'logMaintain/queryLogMaintain.do',
			root: 'root',
 			totalProperty: 'totalProperty',
			fields: ['logId','ipAddress','callMethod','businessType',
			         'reqType','reqParam','isSuccess','resSize',
			         'resErrCode','resErrMsg','errMsg',
			         {  
			        	 name : 'reqTime',
			        	 type : 'date',
			        	 dateFormat : 'time',
			        	 convert: function(v){
			        		 if(v != null && v != "" && v != "null"){
			        			 return new Date(v.time);
			        		 }else{
			        			 return new Date();
			        		 }
			        	 } 
		        	 },{
		        		 name : 'resTime',  
		        		 type : 'string',
		        		 dateFormat : 'time',
		        		 convert: function(v){
							 if(v != null && v != "" && v != "null"){
								 return new Date(v.time);
							 }else{
								 return new Date();
							 }
						 }
		        	 },
				     'errBillType','errBillId','pushLimits',
			         'final_status','remark']
		});
		logMaintainStore.on('beforeload',function(){
			logMaintainStore.baseParams = {
				reqTime:Ext.getCmp("reqTime").getValue(),
				businessType:Ext.getCmp("businessType").getValue(),
				isSuccess:Ext.getCmp("isSuccess").getValue(),
				exceptionType:Ext.getCmp("exceptionType").getValue()
			};
		});  
	
		LogMaintainGridPanel.superclass.constructor.call(this, {
			/**表格高度自适应 document.body.clientHeight浏览器页面高度 start**/
			monitorResize: true, 
			doLayout: function() { 
				this.setWidth(document.body.clientWidth);
				this.setHeight(document.body.clientHeight-115);
				Ext.grid.GridPanel.prototype.doLayout.call(this); 
			} ,
			viewConfig: {
				forceFit: false,
				autoFill: false,
				columnsText : "显示/隐藏列",
                sortAscText : "正序排列",
                sortDescText : "倒序排列"
			},
			border: false,
			frame: true,
			cm: cm,
			sm: sm,
			store: logMaintainStore,
			tbar: new Ext.Toolbar({
			items:[{
				text: '查看日志详情',
				iconCls: 'detail',
				id: 'show_log_detail',				
				handler: showLogDetailFn
				},'-',{
					text: '成功处理日志-状态批量修改',
					iconCls: 'update',
					id: 'update_status',				
					handler: batchUpdateStatusFn
					}]
			}),
			
			bbar: new PagingToolbar(logMaintainStore,20)
		});
		
		logMaintainStore.load({
			params: {
				start: 0,
				limit: 20
			}});
	}
});


/**
 * 查询面板
 * @author sux 2011-02-21
 * @memberOf {TypeName} 
 */
var LogMaintainQueryPanel = Ext.extend(Ext.Panel,{
	id: 'logMaintainQueryId',
	constructor: function(){
		LogMaintainQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '系统日志管理',
			border: false,
			frame: true,
			autoWidth: true,
			defaultType: 'fieldset',
			items: [{
				title: '查询条件',
				itemId:'queryConditionFields',
				layout: 'table',
				layoutConfig: {
					columns: 6 //5
				},
				defaults: {
					labelWidth: 70,
					labelAlign: 'right'
				},
				items: [
				// {
				// 	itemId:'ecSelectComp',
				// 	layout:'table',
				// 	tableconfig:{
				// 		column:2
				// 	},
				// 	defaults: {
				// 		labelWidth: 70,
				// 		labelAlign: 'right'
				// 	},
				// 	/*
				// 	items: [{s
				// 		xtype:'compositefield',
				// 		fieldLabel:'平台类型',
				// 		width:120,
				// 		id:'storeType'
				// 	},{
				// 		xtype:'button',
				// 		style:'margin: 0px 10px 0px 20px;',
				// 		text:'查询平台类型',
				// 		width:30,
				// 		iconCls:'search',
				// 		handler:logMaintainQueryFn
				// 	}]*/
				// 	items: [{  
				// 		xtype: 'button',
				// 		text:'选择平台',
				// 		iconCls:'search',
				// 		handler:logMaintainEcQueryFn
				// 	},{
				// 		xtype: 'textfield',
				// 		fieldLabel:'电商平台',
				// 		itemId:'ecNamesTextField'
				// 	}]  
				// },
				{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '是否成功',
						width:80,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [["全部",""],["0-失败","0"],["1-成功","1"]]
						}),
						value: '',//預設選到的value
						selectOnFocus:true,//設為onFocus==true
						displayField: 'name',
						valueField: 'value',
						id: 'isSuccess'
					}]
				},
				{
					layout: 'form',
					items: [{
						xtype: 'datefield',
						fieldLabel: '请求时间',
						dateFormat : 'time' ,
						format:'Y-m-d',
						width: 120,
						id: 'reqTime'
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '业务类型',
						width:130,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [
							       ["全部",""],
							       ["OMS指令请求","OMS-REQUEST"],
							       ["ECI排程推送","ECI-PUSH"],
							       ["ECI排程拉取","ECI-REQUEST"],
							       ["SYSPARAM更新","SYS-PARAM-UPDATE"],
							       ["SYSPARAM新增","SYS-PARAM-SAVE"],
							       ["SYSPARAM删除","SYS-PARAM-DELETE"],
							       ["SYNC手动拉取","MANUAL-SYNC"],
							       ["PUSH手动推送","MANUAL-PUSH"]
							]
						}),
						value: '',//預設選到的value
						selectOnFocus:true,//設為onFocus==true
						displayField: 'name',
						valueField: 'value',
						id: 'businessType'
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '异常类型',
						width:130,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [["全部",""],
									["0-系统数据异常","0"],
									["1-业务数据异常","1"]]
						}),
						value: '',//預設選到的value
						selectOnFocus:true,//設為onFocus==true
						displayField: 'name',
						valueField: 'value',
						id: 'exceptionType'
					}]
				},
				
				{
					style: 'margin: 0px 10px 0px 20px;',
					xtype: 'button',
					text: '查询',
					width: 70,
					iconCls: 'search',
					handler: logMaintainQueryFn
				},{
					xtype: 'button',
					text: '取消',
					width: 70,
					iconCls: 'cancel',
					handler: logMaintainCancelFn
				}
				// {
				// 	layout:'table',
				// 	layoutConfig:{
				// 		columns:2
				// 	},
				// 	items:[{
				// 		xtype:'button',
				// 		style:'margin: 0px 10px 0px 20px;',
				// 		text:'查询',
				// 		width:70,
				// 		iconCls:'search',
				// 		handler:logMaintainQueryFn
				// 	},{
				// 		xtype:'button',
				// 		text:'取消',
				// 		width:70,
				// 		iconCls:'cancel',
				// 		handler:logMaintainCancelFn
				// 	}]
				// }
				]
			}]
		})
	}
});

logMaintainQueryFn = function(){
	var reqTimeV = Ext.getCmp("reqTime").getValue();
	var businessTypeV = Ext.getCmp("businessType").getValue();
	var isSuccessV = Ext.getCmp("isSuccess").getValue();
	var exceptionTypeV = Ext.getCmp("exceptionType").getValue();
	Ext.getCmp("logMaintainGridPanelId").getStore().load({
		params: {
			type: "query",
			reqTime: reqTimeV,
			businessType: businessTypeV,
			isSuccess: isSuccessV,
			exceptionType:exceptionTypeV,
			start: 0,
			limit: 20
		}
	});
};

logMaintainCancelFn = function(){
	Ext.get("reqTime").dom.value = null;
	Ext.get("businessType").dom.value = "全部";
};

PagingToolbar = Ext.extend(Ext.PagingToolbar, {
	constructor: function(store, pageSize){
		PagingToolbar.superclass.constructor.call(this, {
			store: store,
			pageSize: pageSize, //页面大小 
			displayInfo: true,
			displayMsg : '共<font color="red"> {2} </font>条记录,当前页记录索引<font color="red"> {0} - {1}</font>&nbsp;&nbsp;&nbsp;',
			emptyMsg: '没有数据',
			refreshText: '刷新',
			firstText: '第一页',
			prevText: '前一页',
			nextText: '下一页',
			lastText: '最后一页'
		});

	}
});

showLogDetailFn = function(){	
	var selectionModel = Ext.getCmp('logMaintainGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length != 1){
		Ext.Msg.alert('信息提示','请选择一条 Log 信息');
		return;
	}
	var logMaintainV = record[0].get('logId');
	var showLogDetailWin = new LogDetailWin(logMaintainV);

	showLogDetailWin.show();
};

batchUpdateStatusFn= function(){	
	var selectionModel = Ext.getCmp('logMaintainGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length == 0){
		Ext.Msg.alert('信息提示','请选择一条日志信息');
		return;
	}
	
	var logIds='';
	for(var count=0;count<record.length;count++){
		if(record[count].get('businessType')!='ECI-PUSH' && record[count].get('final_status')!='1'){
			logIds=logIds+record[count].get('logId')+',';
		}
	}
	
	if(logIds != null && logIds != ""){
		Ext.Msg.confirm("信息提示","是否确定認更新非ECI推送之資料?",function(button, text){
			if(button == "yes"){
				Ext.Ajax.request({
					url: "logMaintain/batchUpdateStatus.do",
					success: function(response, options){
								Ext.Msg.alert("信息提示", response.responseText, function(){
									Ext.getCmp('logMaintainGridPanelId').getStore().reload();
								});
							},
					failure: function(response, options){
								Ext.Msg.alert("信息提示", '数据库连接失败', function(){});
							},
					params: {
						logIds: logIds
					}
				});
			}
		});
	}else{
		Ext.Msg.alert("信息提示","请选择非ECI推送的失败记录！",function(){});
	}

};

logMaintainEcQueryFn = function(){
	var showLogMaintainEcQueryWin = new LogMaintainEcQueryWin();

	showLogMaintainEcQueryWin.show();
};


