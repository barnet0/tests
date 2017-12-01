
var SysParamMaintainGridPanel = Ext.extend(Ext.grid.GridPanel,{
	id: 'sysParamMaintainGridPanelId',
	constructor: function(ctx){
		Ext.QuickTips.init();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var number = new Ext.grid.RowNumberer();
		var cm = new Ext.grid.ColumnModel([
			number, sm,
		{
			header: '系统参数编号',
			dataIndex: 'id',
			align: 'center',
			hidden:true
		},{
			header: '系统参数Key',
			dataIndex: 'pKey',
			align: 'center'
		},{
			header: '系统参数值',
			dataIndex: 'pVal',
			align: 'center'
		},{
			header: '系统参数说明',
			dataIndex: 'pDesc',
			align: 'center'	
		},{
			header: '使用状态',
			dataIndex: 'status',
			align: 'center',
			renderer: function(value){
				if(value==1){
					return "1-有效";
				}else{
					return "2-无效";
				}
			}
		},{
			header: '创建时间',
			dateFormat : 'time',
			dataIndex: 'createdDate',
			align: 'center',
			dateFormat : 'time' ,
			hidden:true,
			renderer:function(value){
				return Ext.util.Format.date(value,'Y-m-d H:i:s');
			}
		},{
			header: '创建用户ID',
			dataIndex: 'creatorId',
			align: 'center',
			hidden:true
		},{
			header: '创建人',
			dataIndex: 'creatorName',
			align: 'center',
			hidden:true
		},{
			header: '修改时间',
			dataIndex: 'modiDate',
			align: 'center',
			dateFormat : 'time' ,
			renderer:function(value){
				return Ext.util.Format.date(value,'Y-m-d H:i:s');
			}
		},{
			header: '修改用户ID',
			dataIndex: 'modifierId',
			align: 'center',
			hidden:true
		},{
			header: '修改人',
			dataIndex: 'modifierName',
			align: 'center'
		},{
			header: '资料来源',
			dataIndex: 'dataSource',
			align: 'center',
			hidden:true		
		},{
			header: '父层',
			dataIndex: 'parent',
			align: 'center',
			hidden:true
		}]);
		var sysParamMaintainStore = new Ext.data.JsonStore({
			url:'sysParamMaintain/querySysParamMaintain.do',
			root: 'root',
 			totalProperty: 'totalProperty',
			fields: ['id','pKey','pVal','pDesc',{  
    					name : 'createdDate',  
    					type : 'date',  
    					mapping : 'createdDate.time',  
    					dateFormat : 'time'  
			         },'creatorId','creatorName',{  
			        	 name : 'modiDate',  
			        	 type : 'date',  
			        	 mapping : 'modiDate.time',  
			        	 dateFormat : 'time'  
			         },'modifierId','modifierName','dataSource','status','parent']
		});
		
		sysParamMaintainStore.on('beforeload',function(){
			sysParamMaintainStore.baseParams = {
				pKey:Ext.getCmp("pKey").getValue(),
				status:Ext.getCmp("status").getValue()
			};
		});  
	
		SysParamMaintainGridPanel.superclass.constructor.call(this, {
			/**表格高度自适应 document.body.clientHeight浏览器页面高度 start**/
			monitorResize: true, 
			doLayout: function() { 
				this.setWidth(document.body.clientWidth);
				this.setHeight(document.body.clientHeight-116);
				Ext.grid.GridPanel.prototype.doLayout.call(this); 
			} ,
			viewConfig: {
				forceFit: true,
				autoFill: true,
				columnsText : "显示/隐藏列",
                sortAscText : "正序排列",
                sortDescText : "倒序排列"
			},
			border: false,
			frame: true,
			cm: cm,
			sm: sm,
			store: sysParamMaintainStore,
			tbar: new Ext.Toolbar({
			items:[{
					text: '重新加载系统参数',
					iconCls: 'all',
					id: 'sys_param_reStart',				
					handler: restartSysParamFn
				},{
					text: '新增系统参数',
					iconCls: 'add',
					id: 'sys_param_add',				
					handler: addSysParamFn
				},{
					text: '修改系统参数',
					iconCls: 'update',
					id: 'sys_param_update',				
					handler: updateSysParamFn
				},{
					text: '删除系统参数',
					iconCls: 'delete',
					id: 'sys_param_delete',				
					handler: deleteSysParamFn
				},{
					text: '查看参数详情',
					iconCls: 'detail',
					id: 'sys_param_showDetail',				
					handler: showSysParamDetailFn
				}]
			}),
			
			bbar: new PagingToolbar(sysParamMaintainStore,20)
		});
		
		sysParamMaintainStore.load({
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
var SysParamMaintainQueryPanel = Ext.extend(Ext.Panel,{
	id: 'sysParamMaintainQueryId',
	constructor: function(){
		SysParamMaintainQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '系统参数维护',
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
					labelWidth: 150,
					labelAlign: 'right'
				},
				items: [{
					layout: 'form',
					items: [{
						xtype: 'textfield',
						fieldLabel: '参数Key查询',
						width: 200,
						id: 'pKey'
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '可用状态查询',
						width:200,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [["全部",""],["1-有效","1"],["2-无效","2"]]
						}),
						value: '',//預設選到的value
						selectOnFocus:true,//設為onFocus==true
						displayField: 'name',
						valueField: 'value',
						id: 'status'
					}]
				},{
					style: 'margin: 0px 10px 0px 20px;',
					xtype: 'button',
					text: '查询',
					width: 80,
					iconCls: 'search',
					handler: sysParamMaintainQueryFn
				},{
					xtype: 'button',
					text: '取消',
					width: 80,
					iconCls: 'cancel',
					handler: sysParamMaintainCancelFn
				}]
			}]
		})
	}
});

sysParamMaintainQueryFn = function(){
	var pKeyV = Ext.getCmp("pKey").getValue();
	var statusV = Ext.getCmp("status").getValue();
	Ext.getCmp("sysParamMaintainGridPanelId").getStore().load({
		params: {
			type: "query",
			pKey: pKeyV,
			status: statusV,
			start: 0,
			limit: 20
		}
	});
};

sysParamMaintainCancelFn = function(){
	Ext.get("pKey").dom.value = null;
	Ext.get("status").dom.value = "全部";
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

//單筆詳情查閱
showSysParamDetailFn = function(){
	var selectionModel = Ext.getCmp('sysParamMaintainGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length != 1){
		Ext.Msg.alert('信息提示','请选择一条系统参数信息');
		return;
	}
	var sysParamMaintainV = record[0].get('id');
	var showSysParamDetailWin = new SysParamDetailWin(sysParamMaintainV,'detail');

	showSysParamDetailWin.show();
}

//更新系統參數
updateSysParamFn=function(){
	var selectionModel = Ext.getCmp('sysParamMaintainGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length != 1){
		Ext.Msg.alert('信息提示','请选择一条系统参数信息');
		return;
	}
	var sysParamMaintainV = record[0].get('id');
	var showSysParamDetailWin = new SysParamDetailWin(sysParamMaintainV,'update');

	showSysParamDetailWin.show();
}

//新增系統參數
addSysParamFn=function(){
	var showSysParamDetailWin = new SysParamDetailWin('','add');
	showSysParamDetailWin.show();
}


//刪除單筆或多筆資料
deleteSysParamFn=function(){
	var selectionModel = Ext.getCmp('sysParamMaintainGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length == 0){
		Ext.Msg.alert('信息提示','请选择一条系统参数信息');
		return;
	}
	
	var sysParamIds='';
	for(var count=0;count<record.length;count++){
		if(record[count].get('status')=='2'){
			sysParamIds=sysParamIds+record[count].get('id')+',';
		}
	}
	
	if(sysParamIds != null && sysParamIds != ""){
		Ext.Msg.confirm("信息提示","是否确定刪除状态为  2-无效 的资料?",function(button, text){
			if(button == "yes"){
				Ext.Ajax.request({
					url: "sysParamMaintain/deleteSysParam.do",
					success: function(response, options){
								Ext.Msg.alert("信息提示", response.responseText, function(){
									Ext.getCmp('sysParamMaintainGridPanelId').getStore().reload();
								});
							},
					failure: function(response, options){
								Ext.Msg.alert("信息提示", '刪除失败', function(){});
							},
					params: {
						sysParamIds: sysParamIds
					}
				});
			}
		});
	}else{
		Ext.Msg.alert("信息提示","请选择无效的系统参数！",function(){});
	}
}

//重啟參數設定服務
restartSysParamFn=function(){
	Ext.Msg.confirm("信息提示","是否确定重新加载系统参数?",function(button, text){
		if(button == "yes"){
			Ext.Ajax.request({
			url: "sysParamMaintain/restartSysParam.do",
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
						Ext.Msg.alert("信息提示", '重启失败', function(){});
					},
			});
		}
	});
}


