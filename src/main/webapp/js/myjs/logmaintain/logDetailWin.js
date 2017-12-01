
LogDetailWin = Ext.extend(Ext.Window,{
	id: 'logDetailWinId',
	constructor: function(logMaintainV){
		Ext.Ajax.request({
			url : 'logMaintain/showLogDetail.do',
			method: 'POST',
			params :{logMaintainId: logMaintainV},
			success: function ( result, request ) {
				var jsonData = Ext.util.JSON.decode(result.responseText);
				Ext.getCmp('log.logId').setValue(jsonData.logId);
				Ext.getCmp('log.ipAddress').setValue(jsonData.ipAddress);
				Ext.getCmp('log.callMethod').setValue(jsonData.callMethod);
				var businessType;
				if(jsonData.businessType == "OMS-REQUEST"){
					businessType = 'OMS指令请求';
				}else if(jsonData.businessType == "ECI-REQUEST"){
					businessType = 'ECI排程拉取';
				}else if(jsonData.businessType == "ECI-PUSH"){
					businessType = 'ECI排程推送';
				}else if(jsonData.businessType == "SYS-PARAM-UPDATE"){
					businessType = "SYSPARAM更新";
				} else if(jsonData.businessType == "SYS-PARAM-SAVE"){
					businessType = "SYSPARAM新增";
				} else if(jsonData.businessType == "SYS-PARAM-DELETE"){
					businessType = "SYSPARAM刪除";
				} else if(jsonData.businessType == "MANUAL-SYNC"){
					businessType = "SYNC手动拉取";
				} else if(jsonData.businessType == "MANUAL-PUSH"){
					businessType = "PUSH手动推送";
				}else{
					businessType = 'ERROR错误业务类型';
				}
				Ext.getCmp('log.businessType').setValue(businessType);
				Ext.getCmp('log.reqType').setValue(jsonData.reqType);
				Ext.getCmp('log.reqParam').setValue(jsonData.reqParam);
				Ext.getCmp('log.isSuccess').setValue(jsonData.isSuccess);
				Ext.getCmp('log.resErrCode').setValue(jsonData.resErrCode);
				Ext.getCmp('log.resErrMsg').setValue(jsonData.resErrMsg);
				Ext.getCmp('log.resSize').setValue(jsonData.resSize);
				Ext.getCmp('log.errMsg').setValue(jsonData.errMsg);
				Ext.getCmp('log.reqTime').setValue(jsonData.reqTimeTrasient);
				Ext.getCmp('log.resTime').setValue(jsonData.resTimeTrasient);
				Ext.getCmp('log.errBillType').setValue(jsonData.errBillType);
				Ext.getCmp('log.errBillId').setValue(jsonData.errBillId);
				Ext.getCmp('log.pushLimits').setValue(jsonData.pushLimits);
				Ext.getCmp('log.final_status').setValue(jsonData.final_status);
				Ext.getCmp('log.remark').setValue(jsonData.remark);
				
			},
			failure: function ( result, request ) {
				alert('查看详情资料错误!!');
			}
		});
		var logDetailPanel = new LogDetailPanel();
		LogDetailWin.superclass.constructor.call(this, {
			title: '日志详情说明窗' ,
			resizable: false,
			width: 750,
			modal: true,
			items: [logDetailPanel]
		})			
	}
});

LogDetailPanel=Ext.extend(Ext.form.FormPanel,{
	id: 'logDetailPanelId',
	constructor: function(){
	LogDetailPanel.superclass.constructor.call(this,{
			padding: '6',
			width: 750,
			height: 460,
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
					xtype: 'textfield',
					fieldLabel: '日志编号',
					id: 'log.logId',
					disabled:true,
					width: 200
				}]
			},{
				layout: 'form',
				width: 350,
				items: [{
					xtype: 'textfield',
					fieldLabel: '响应IP位置',
					id:'log.ipAddress',
					disabled:true,
					width: 200
				}]
			},{
				width: 350,
				layout:'form',
				items:[{
					xtype: 'textfield',
					fieldLabel: '业务类型',
					id: 'log.businessType',
					disabled:true,
					width: 200
				}]
			},{
				layout: 'form',
				width: 350,
				items: [{
					xtype: 'textfield',
					fieldLabel: '指令类型',
					id: 'log.reqType',
					disabled:true,
					width: 200
				}]
			},{
				layout: 'form',
				width: 350,
				items: [{
					xtype: 'textfield',
					fieldLabel: '是否请求成功 ',
					id: 'log.isSuccess',
					disabled:true,
					width: 200
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '回应数量',
					width: 200,
					id: 'log.resSize',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '自定义错误代码',
					width: 200,
					id: 'log.resErrCode',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '自定义错误内容',
					width: 200,
					id: 'log.resErrMsg',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '请求时间',
					dateFormat : 'time' ,
					width: 200,
					id: 'log.reqTime',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '回应时间',
					dateFormat : 'time' ,
					width: 200,
					id: 'log.resTime',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '错误单据类型',
					width: 200,
					id: 'log.errBillType',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '错误单据单号',
					width: 200,
					id: 'log.errBillId',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '推送次数',
					width: 200,
					id: 'log.pushLimits',
					disabled:true
				}]
			},{
				width: 350,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '最后推送结果',
					width: 200,
					id: 'log.final_status',
					disabled:true
				}]
			},{
				colspan: 2,
				width: 700,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '呼叫方法',
					id: 'log.callMethod',
					disabled:true,
					width: 550
				}]
			},{
				colspan: 2,
				width: 700,
				layout: 'form',
				items: [{
					xtype: 'textarea',
					fieldLabel: '请求JSON',
					width: 550,
					height:130,
					id: 'log.reqParam',
					disabled:true
				}]
			},{
				colspan: 2,
				width: 700,
				layout: 'form',
				items: [{
					xtype: 'textarea',
					fieldLabel: '系统错误',
					width: 550,
					id: 'log.errMsg',
					disabled:true
				}]
			},{
				colspan: 2,
				width: 700,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '备注',
					width: 550,
					id: 'log.remark',
					disabled:true
				}]
			}]
		});
	}
});

