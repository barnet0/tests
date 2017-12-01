SysParamDetailWin = Ext.extend(Ext.Window,{
	id: 'sysParamDetailWinId',
	constructor: function(sysParamMaintainV,type){
		var urlPath;
		var visibleType=false;
		if(type=='detail'){
			urlPath='sysParamMaintain/showSysParamDetail.do';
			visibleType=true;
		}else if(type=='update'){
			urlPath='sysParamMaintain/showSysParamDetail.do';
		}else{
			//urlPath='sysParamMaintain/addSysParam.do';
		}
		if(type!='add'){
		Ext.Ajax.request({
	         url :urlPath ,
	                  method: 'POST',
	                  params :{sysParamMaintainId: sysParamMaintainV},
	                  success: function ( result, request ) {
	                      var jsonData = Ext.util.JSON.decode(result.responseText);
	                      Ext.getCmp('sys.id').setValue(jsonData.id);
	                      Ext.getCmp('sys.pKey').setValue(jsonData.pKey);
	                      Ext.getCmp('sys.pVal').setValue(jsonData.pVal);
	                      Ext.getCmp('sys.pDesc').setValue(jsonData.pDesc);
	                      Ext.getCmp('sys.createdDateStr').setValue(jsonData.createdDateStr);
	                      Ext.getCmp('sys.creator').setValue(jsonData.creatorId+'-'+jsonData.creatorName);
	                      Ext.getCmp('sys.modiDateStr').setValue(jsonData.modiDateStr);
	                      Ext.getCmp('sys.modifier').setValue(jsonData.modifierId+'-'+jsonData.modifierName);
	                      Ext.getCmp('sys.dataSource').setValue(jsonData.dataSource);                      
	                      Ext.getCmp('sys.status').setValue(jsonData.status);
		                    if(type=='detail'){
		              			Ext.getCmp('sys.pKey').setDisabled(true);
		              			Ext.getCmp('sys.pVal').setDisabled(true);
		              			Ext.getCmp('sys.pDesc').setDisabled(true);
		              			Ext.getCmp('sys.status').setDisabled(true);
		              			Ext.getCmp('saveBtn').setDisabled(true);
		              			Ext.getCmp('cancelBtn').setDisabled(true);
		              		}
	               },
	                  failure: function ( result, request ) {
	                   alert('查看详情资料错误!!');
	               }
	       });
		}
		var sysParamDetailPanel = new SysParamDetailPanel(visibleType);
		SysParamDetailWin.superclass.constructor.call(this, {
			title: '系统参数详情说明' ,
			resizable: false,
			width: 750,
			modal: true,
			items: [sysParamDetailPanel]
		})			
	}
});

SysParamDetailPanel=Ext.extend(Ext.form.FormPanel,{
	id: 'sysParamDetailPanelId',
	constructor: function(visibleType){
	SysParamDetailPanel.superclass.constructor.call(this,{
			padding: '6',
			width: 750,
			height: 200,
			frame: true,
			layout: 'table',
			layoutConfig: {
				columns: 2
			},
			defaults: {
				labelWidth: 100,
				labelAlign: 'right'
			},
			items: [{
				width: 350,
				layout:'form',
				items:[{
					xtype: 'hidden',
					fieldLabel: '参数编号',
					id: 'sys.id',
					disabled:false,
					width: 200
				},{
					xtype: 'textfield',
					fieldLabel: '系统参数Key',
					allowBlank: false,
					emptyText: '不能为空',
					id:'sys.pKey',
					disabled:false,
					width: 200
				}]
			},{
				width: 350,
				layout:'form',
				items:[{
					xtype: 'textfield',
					fieldLabel: '系统参数值',
					allowBlank: false,
					emptyText: '不能为空',
					id: 'sys.pVal',
					disabled:false,
					width: 200
				}]
			},{
				colspan: 2,
				layout: 'form',
				width: 700,
				items: [{
					xtype: 'textfield',
					fieldLabel: '系统参数说明',
					allowBlank: false,
					emptyText: '不能为空',
					id: 'sys.pDesc',
					disabled:false,
					width: 550
				}]
			},{
				layout: 'form',
				width: 350,
				items: [{
					xtype: 'datefield',
					fieldLabel: '创建时间 ',
					dateFormat : 'time' ,
					format:'Y-m-d H:i:s',
					id: 'sys.createdDateStr',
					disabled:true,
					width: 200
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '创建人',
					width: 200,
					id: 'sys.creator',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'datefield',
					fieldLabel: '修改时间',
					format:'Y-m-d H:i:s',
					dateFormat : 'time' ,
					width: 200,
					id: 'sys.modiDateStr',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '修改人',					
					width: 200,
					id: 'sys.modifier',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '资料来源',
					width: 200,
					id: 'sys.dataSource',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'combo',
					fieldLabel: '使用状态',
					width: 200,
					id: 'sys.status',
					triggerAction: 'all',
					mode: 'local',
					editable: false,
					allowBlank: false,
					emptyText: '不能为空',
					store: new Ext.data.SimpleStore({
						fields: ['name','value'],
						data: [["1-有效","1"],["2-无效","2"]]
					}),
					value: '',//預設選到的value
					selectOnFocus:true,//設為onFocus==true
					displayField: 'name',
					valueField: 'value',
					disabled:false
				}]
			}],
			buttonAlign: 'center',
			buttons: [{
				text: '保存',
				iconCls: 'save',
				id:'saveBtn',
				disabled:false,
				handler: saveSysParamFn
			},{
				text: '关闭',
				iconCls: 'cancel',
				id:'cancelBtn',
				disabled:false,
				handler: cancelSysParamFn
			}]
		});
	}
});

saveSysParamFn = function(){
	var detailForm = Ext.getCmp('sysParamDetailPanelId').getForm();
	var jsonSysParam = Ext.util.JSON.encode(detailForm.getValues());
	Ext.Ajax.request({
        url: 'sysParamMaintain/saveSysParam.do',
        method:'POST',
        success: function(response, options){
        	var data = Ext.util.JSON.decode(response.responseText);
			Ext.Msg.alert("信息提示", data.msg, function(){
				Ext.getCmp('sysParamMaintainGridPanelId').getStore().reload();
				Ext.getCmp("sysParamDetailWinId").destroy();
			});
		},
		failure: function(response, options){
			var data = Ext.util.JSON.decode(response.responseText);
			Ext.Msg.alert("信息提示", data.msg, function(){});
		},
		params:{
			jsonSysParam:jsonSysParam,
		},
		scope:this
	});
};

cancelSysParamFn = function(){
	Ext.getCmp("sysParamDetailWinId").destroy();
};

