Ext.namespace("hrmsys.ontimetask.info");

var OnTimeTaskGridPanel = Ext.extend(Ext.grid.GridPanel,{
	id: 'onTimeTaskGridPanelId',
	autoScroll : true,
	constructor: function(ctx){
		Ext.QuickTips.init();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var number = new Ext.grid.RowNumberer();
		var cm = new Ext.grid.ColumnModel([
			number, sm,
		{
			header: '编号',
			dataIndex: 'id',
			align: 'center',
			hidden:true
		},{
			header: '任务编码',
			dataIndex: 'code',
			align: 'left',
			width:220
		},{
			header: '任务名称',
			dataIndex: 'name',
			align: 'left',
			width:230
		},{
			header: '有效状态(配置里)',
			dataIndex: 'status',
			align: 'center',
			renderer: function(value){
				if(value == 1) return "1-有效";
				return "2-无效";
			}
		},{
			header: '运行状态(调度器里)',
			dataIndex: 'runStatus',
			align: 'center',
			renderer: function(value){
				//if(value == 1) return "1-未加载";
				//return "2-已加载";
				return value;
			}
		},{
			header: '执行者名称',
			dataIndex: 'exceName',
			align: 'left',
			width:260
		},{
			header: '执行者类型',
			dataIndex: 'exceType',
			align: 'center',
			renderer: function(value){
				if(value == 1){
					return "1-@service服务";
				}else if(value == 2){
					return "2-@EJB服务";
				}else if(value == 3){
					return "3-SQL";
				}else if(value == 4){
					return "4-@ecfx_service分销服务";
				}else if(value == 5) {
					return "5-ReCycleCheckData";
				}
			}
		},{
			header: '定时表达式',
			dataIndex: 'cronVal',
			align: 'center',
			renderer: function(value){
				return "<a href='ontimeTask/showCronHelp.do' target='_blank'><font color='#FF0000'>"+value+"</font></a>"
			}
		},{
			header: '运行环境组',
			dataIndex: 'dataSource',
			align: 'center',
			renderer: function(value){
				if(value == 1){
					return "1-业务系统";
				}else if(value == 2){
					return "2-对接系统";
				}
			}
		},{
			header: '运行机器IP',
			dataIndex: 'runIp',
			align: 'center'
		},{
			header: '操作',
			dataIndex: 'id',
			align: 'center',
			renderer: function(value){
				return "<a href='javascript:void(0)' onclick='toCallNow("+value+")'><font color='#FF0000'>立即调用</font></a>"
			}
		}]);
		var onTimeTaskStore = new Ext.data.JsonStore({
			url:'ontimeTask/queryOntimeTask.do',
			root: 'root',
 			totalProperty: 'totalProperty',
			fields: ['id','code','name','status',
			         'runStatus','exceName','exceType',
			         'cronVal','dataSource','runIp']
		});
		
		//add by sen.shen add on 2015-07-21
		//分頁仍有條件能查詢
		onTimeTaskStore.on('beforeload',function(){
			onTimeTaskStore.baseParams = {
				code:Ext.getCmp("taskCode").getValue(),
				name:Ext.getCmp("taskName").getValue(),
				status:Ext.getCmp("taskStatus").getValue()
			};
		});
		
		OnTimeTaskGridPanel.superclass.constructor.call(this, {
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
			store: onTimeTaskStore,
			tbar: new Ext.Toolbar({
				items: [{
					text: '重启调度器',
					iconCls: 'all',
					handler: function(){
						Ext.Msg.confirm("信息提示","是否确定重启作业调度器?",function(button, text){
							if(button == "yes"){
								Ext.Ajax.request({
								url: "ontimeTask/restartDispatcher.do",
								timeout: 900000,//default 30000 milliseconds 
								success: function(response, options){
											var data = Ext.util.JSON.decode(response.responseText);
											if(data == '0'){
												Ext.Msg.alert('信息提示',"重启失败!");
								            }
								            else if(data == '1'){	            	
								            	Ext.Msg.alert('信息提示',"重启成功!");
								            	Ext.getCmp('onTimeTaskGridPanelId').getStore().reload();
								            }
								            else {
								            	Ext.Msg.alert('信息提示',data);
								            }
										},
								failure: function(response, options){
											Ext.Msg.alert("信息提示", '数据库连接失败', function(){});
										},
								});
							}
						});
					}
				},'-',{
					text: '添加',
					iconCls: 'add',
					id: 'ePunish_add',
					//hidden: 'false',
					handler: onTimeTaskAddFn
				},'-',{
					text: '修改',
					iconCls: 'update',
					id: 'ePunish_update',
					//hidden: 'false',
					handler: onTimeTaskUpdateFn
				},'-',{
					text: '删除',
					iconCls: 'delete',
					id: 'ePunish_delete',
					//hidden: 'false',
					handler: onTimeTaskDelFn
				},'-',{
					text: '详情',
					iconCls: 'detail',
					id: 'ePunish_detail',
					//hidden: 'false',
					handler: onTimeTaskDetailFn
				}]
			}),
			bbar: new PagingToolbar(onTimeTaskStore,20)
		});
		onTimeTaskStore.load({
			params: {
				start: 0,
				limit: 20
			}});
	}
});

/**
 * 分页栏类
 * @param {Object} store 表格存储store
 * @param {Object} pageSize 页面大小
 * @memberOf {TypeName} 
 */
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
onTimeTaskDelFn = function(){
	var selectionModel = Ext.getCmp('onTimeTaskGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length != 1){
		Ext.Msg.alert('信息提示','请选择一条要删除的任务信息!');
		return;
	}
	var ontimeTaskIdV = record[0].get('id');
	if(ontimeTaskIdV != null && ontimeTaskIdV != ""){
		Ext.Msg.confirm("信息提示","是否确定删除这条任务记录?",function(button, text){
			if(button == "yes"){
				Ext.Ajax.request({
					url: "ontimeTask/delete.do",
					success: function(response, options){
								var datas = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert("信息提示", datas.msg, function(){
									Ext.getCmp('onTimeTaskGridPanelId').getStore().reload();
								});
							},
					failure: function(response, options){
								Ext.Msg.alert("信息提示", '数据库连接失败', function(){});
							},
					params: {
						id: ontimeTaskIdV
					}
				});
			}
		});
	}else{
		Ext.Msg.alert("信息提示","请选择您要删除的记录！",function(){});
	}
};
onTimeTaskAddFn = function(){
	var onTimeTaskAddWin = new OnTimeTaskAddWin();
	onTimeTaskAddWin.show();
	Ext.Ajax.request({
		url: "ontimeTask/getLocalIP.do",
		success: function(response, options){
			Ext.getCmp('ontimeTaskRunIp').setValue(response.responseText);
			Ext.getCmp('thirdLabel4IP').el.dom.innerHTML="<font color='#FF0000' style='font-family:宋体;font-size:14px'>三，运行机器IP,自动识别为本地IP地址:*****<a href='javascript:void(0)'>"+response.responseText+"</a></font></br>";
		},
		failure: function(response, options){
			Ext.Msg.alert("信息提示", '获取IP地址失败!', function(){});
		}
	});
	
	
}

/**
 * 查询面板
 * @author sux 2011-02-21
 * @memberOf {TypeName} 
 */
var OnTimeTaskQueryPanel = Ext.extend(Ext.Panel,{
	id: 'onTimeTaskQueryId',
	constructor: function(){
		OnTimeTaskQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '定时作业管理',
			border: false,
			frame: true,
			autoWidth: true,
			defaultType: 'fieldset',
			items: [{
				title: '查询条件',
				layout: 'table',
				layoutConfig: {
					columns: 6
				},
				defaults: {
					labelWidth: 80,
					labelAlign: 'right'
				},
				items: [{
					layout: 'form',
					items: [{
						xtype: 'textfield',
						fieldLabel: '任务编码',
						width: 180,
						id: 'taskCode'
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'textfield',
						fieldLabel: '任务名称',
						width: 180,
						id: 'taskName'
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '有效状态',
						width: 120,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [["全部",""],["1-有效","1"],["2-无效","2"]]
						}),
						displayField: 'name',
						valueField: 'value',
						id: 'taskStatus'
					}]
				},{
					style: 'margin: 0px 10px 0px 20px;',
					xtype: 'button',
					text: '查询',
					width: 80,
					iconCls: 'search',
					handler: onTimeTaskQueryFn
				},{
					xtype: 'button',
					text: '取消',
					width: 80,
					iconCls: 'cancel',
					handler: onTimeTaskCancelFn
				}]
			}]
		})
	}
});
onTimeTaskQueryFn = function(){
	var taskCodeV = Ext.getCmp("taskCode").getValue();
	var taskNameV = Ext.getCmp("taskName").getValue();
	var taskStatusV = Ext.getCmp("taskStatus").getValue();
	Ext.getCmp("onTimeTaskGridPanelId").getStore().load({
		params: {
			type: "query",
			code: taskCodeV,
			name: taskNameV,
			status: taskStatusV,
			start: 0,
			limit: 20
		}
	});
};
onTimeTaskCancelFn = function(){
	Ext.getCmp("taskCode").setValue("");
	Ext.get("taskName").dom.value = "";
	Ext.get("taskStatus").dom.value = "";
};
onTimeTaskUpdateFn = function(){
	var onTimeTaskAddWin = new OnTimeTaskAddWin();
	onTimeTaskAddWin.title = '定时任务信息修改';
	var selectionModel = Ext.getCmp('onTimeTaskGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length != 1){
		Ext.Msg.alert('信息提示','请选择一条要修改的任务信息!');
		return;
	}
	var ontimeTaskIdV = record[0].get('id');
	var updateForm = Ext.getCmp('onTimeTaskAddPanelId').getForm();
	updateForm.load({
		url: 'ontimeTask/editOnTimeTask.do',
		params: {
			ontimeTaskId: ontimeTaskIdV
		}
	});
	//updateForm.findField("ontimetask_code").readOnly=true;
	onTimeTaskAddWin.show();
	Ext.Ajax.request({
		url: "ontimeTask/getLocalIP.do",
		success: function(response, options){
			Ext.getCmp('thirdLabel4IP').el.dom.innerHTML="<font color='#FF0000' style='font-family:宋体;font-size:14px'>三，运行机器IP,自动识别为本地IP地址:*****<a href='javascript:void(0)'>"+response.responseText+"</a></font></br>";
		},
		failure: function(response, options){
			Ext.Msg.alert("信息提示", '获取IP地址失败!', function(){});
		}
	});
};
onTimeTaskDetailFn = function(){
	var onTimeTaskDetailWin = new OnTimeTaskDetailWin();
		var selectionModel = Ext.getCmp('onTimeTaskGridPanelId').getSelectionModel();
		var record = selectionModel.getSelections();
		if(record.length != 1){
			Ext.Msg.alert('信息提示','请选择一条定时任务信息');
			return;
		}
		var ontimeTaskIdV = record[0].get('id');
		Ext.getCmp('onTimeTaskDetailPanelId').getForm().load({
			url: 'ontimeTask/editOnTimeTask.do',
			params: {
				ontimeTaskId: ontimeTaskIdV
			}
		})
	onTimeTaskDetailWin.show();
};
function toCallNow(taskId){
	Ext.Msg.confirm("信息提示","确认要立即执行作业吗？",function(button, text){
		if(button == "yes"){
			Ext.Ajax.request({
			url: "ontimeTask/callNow.do",
			timeout: 900000,//default 30000 milliseconds  
			success: function(response, options){
						var data = Ext.util.JSON.decode(response.responseText);
						if(data == '0'){
							Ext.Msg.alert('信息提示',"调用失败!");
			            }
			            else if(data == '1'){		            	
			            	Ext.Msg.alert('信息提示',"调用成功!");
			            }
			            else {
			            	Ext.Msg.alert('信息提示',data);
			            }
					},
			failure: function(response, options){
						Ext.Msg.alert("信息提示", '数据库连接失败', function(){});
					},
			params: {
				ontimeTaskId: taskId
			}
			});
		}
	});
}
