
/**
 * @Auto Sen.Shen
 */
var storeType;
var shop_store;

var ManualScheduleQueryPanel = Ext.extend(Ext.Panel,{
	id: 'manualScheduleQueryPanelId',
	constructor: function(){
		ManualScheduleQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '手动拉取推送管理',
			border: false,
			frame: true,
			autoWidth: true,
			defaultType: 'fieldset',
			items: [{
				title: '根据创建时间查询',
				layout: 'table',
				layoutConfig: {
					columns:3
				},
				items: [{
					xtype:'radio',
					boxLabel: '创建时间 ', 
					name: 'rb-col', 
					height:40,
					inputValue: 1,
					width:200,
					id:'createTimeRadio',
					listeners:{     
						'check':function(cb, value){
							if(value){
				        		Ext.getCmp("createStart").setDisabled(false);
				        		Ext.getCmp("createEnd").setDisabled(false);
				        		Ext.getCmp("modifyStart").setDisabled(true);
				        		Ext.getCmp("modifyEnd").setDisabled(true);
				        		Ext.getCmp("orderId").setDisabled(true);
				        		Ext.getCmp("payStart").setDisabled(true);
				        		Ext.getCmp("payEnd").setDisabled(true);
				        	}
						}
					}
				},{
					width: 350,
					layout: 'form',
					items: [{
						xtype: 'datefield',
						fieldLabel: '起始时间',
						dateFormat : 'time' ,
						format:'Y-m-d H:i:s',
						id: 'createStart',
						style:'margin-bottom:18px',
						width:200,
						disabled:true
					}]
				},{
					width: 350,
					layout: 'form',
					items: [{
						xtype: 'datefield',
						fieldLabel: '结束时间',
						dateFormat : 'time' ,
						format:'Y-m-d H:i:s',
						id: 'createEnd',
						style:'margin-bottom:18px',
						width:200,
						disabled:true
					}]
				}]
			},{
				title: '根据修改时间',
				layout: 'table',
				layoutConfig: {
					columns:3
				},
				items: [{
					xtype:'radio',
					boxLabel: '修改时间 ', 
					name: 'rb-col', 
					height:40,
					inputValue: 2,
					width:200,
					id:'modifyTimeRadio',
					listeners:{     
						'check':function(cb, value){
							if(value){
				        		Ext.getCmp("modifyStart").setDisabled(false);
				        		Ext.getCmp("modifyEnd").setDisabled(false);
				        		Ext.getCmp("createStart").setDisabled(true);
				        		Ext.getCmp("createEnd").setDisabled(true);
				        		Ext.getCmp("orderId").setDisabled(true);
				        		Ext.getCmp("payStart").setDisabled(true);
				        		Ext.getCmp("payEnd").setDisabled(true);
				        	}
						}
					}
				},{
					width: 350,
					layout: 'form',
					items: [{
						xtype: 'datefield',
						fieldLabel: '起始时间',
						dateFormat : 'time' ,
						format:'Y-m-d H:i:s',
						id: 'modifyStart',
						style:'margin-bottom:18px',
						width:200,
						disabled:true
					}]
				},{
					width: 350,
					layout: 'form',
					items: [{
						xtype: 'datefield',
						fieldLabel: '结束时间',
						dateFormat : 'time' ,
						format:'Y-m-d H:i:s',
						id: 'modifyEnd',
						style:'margin-bottom:18px',
						width:200,
						disabled:true
					}]
				}]
			},{
				title: '根据订单编号',
				layout: 'table',
				layoutConfig: {
					columns:3
				},
				items: [{
					xtype:'radio',
					boxLabel: '订单编号', 
					name: 'rb-col', 
					height:40,
					inputValue: 3,
					width:200,
					id:'orderIdRadio',
					listeners:{     
						'check':function(cb, value){
							if(value){
				        		Ext.getCmp("orderId").setDisabled(false);
				        		Ext.getCmp("createStart").setDisabled(true);
				        		Ext.getCmp("createEnd").setDisabled(true);
				        		Ext.getCmp("modifyStart").setDisabled(true);
				        		Ext.getCmp("modifyEnd").setDisabled(true);
				        		Ext.getCmp("payStart").setDisabled(true);
				        		Ext.getCmp("payEnd").setDisabled(true);
				        	}
						}
					}
				},{
					width: 700,
					layout: 'form',
					colspan: 2,
					items: [{
						xtype: 'textfield',
						fieldLabel: '订单编号',
						id: 'orderId',
						style:'margin-bottom:18px',
						width:200,
						disabled:true
					}]
				}]
			},{
				title: '根据支付时间',
				layout: 'table',
				layoutConfig: {
					columns:3
				},
				items: [{
					xtype:'radio',
					boxLabel: '支付时间 ', 
					name: 'rb-col', 
					height:40,
					inputValue: 2,
					width:200,
					id:'payTimeRadio',
					listeners:{     
						'check':function(cb, value){
							if(value){
				        		Ext.getCmp("modifyStart").setDisabled(true);
				        		Ext.getCmp("modifyEnd").setDisabled(true);
				        		Ext.getCmp("createStart").setDisabled(true);
				        		Ext.getCmp("createEnd").setDisabled(true);
				        		Ext.getCmp("orderId").setDisabled(true);
				        		Ext.getCmp("payStart").setDisabled(false);
				        		Ext.getCmp("payEnd").setDisabled(false);
				        		
				        		var processHand = Ext.getCmp("processHand");
				        		processHand.setValue("pushHand");
				        		processHand.setDisabled(true);
				        		
				        	} else {
				        		var processHand = Ext.getCmp("processHand");
				        		processHand.setValue("");
				        		processHand.setDisabled(false);
				        	}
						}
					}
				},{
					width: 350,
					layout: 'form',
					items: [{
						xtype: 'datefield',
						fieldLabel: '起始时间',
						dateFormat : 'time' ,
						format:'Y-m-d H:i:s',
						id: 'payStart',
						style:'margin-bottom:18px',
						width:200,
						disabled:true
					}]
				},{
					width: 350,
					layout: 'form',
					items: [{
						xtype: 'datefield',
						fieldLabel: '结束时间',
						dateFormat : 'time' ,
						format:'Y-m-d H:i:s',
						id: 'payEnd',
						style:'margin-bottom:18px',
						width:200,
						disabled:true
					}]
				}]
			},{
				layout: 'table',
				layoutConfig: {
					columns:4
				},
				items: [{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '选择平台',
						width:150,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [
							       ["淘宝","0"],
							       ["淘宝分销","A"],
							       ["京东","1"],
							       ["一号店","2"],
							       ["工商银行","3"],
							       ["苏宁","4"],
							       ["当当","5"]
							       //["网易考拉","E"],
							       //["拼多多","J"]
							      ]
						}),
						value: '',//預設選到的value
						selectOnFocus:true,//設為onFocus==true
						displayField: 'name',
						valueField: 'value',
						id: 'storeType',
						listeners:{     
							'change':function(){
								var storeType=Ext.getCmp("storeType").getValue();
								Ext.Ajax.request({
							        url: 'manualschedule/manualScheduleCombo.do',
							        timeout: 900000,//default 30000 milliseconds 
							        method:'POST',
							        success: function(response, options){
							        	var jsonResp = Ext.util.JSON.decode(response.responseText);
							        	var convertData = [];
							        	for (var i = 0; i < jsonResp.length; i++) {
							        		convertData.push( {'aomsshop001':jsonResp[i].aomsshop001,'aomsshop001-002': jsonResp[i].aomsshop001+' ('+jsonResp[i].aomsshop002+' )'} );
							        	}
							        	
							        	shop_store = new Ext.data.JsonStore({
							        		fields: ['aomsshop001','aomsshop001-002'],
							        		data : convertData
							        	});
							        	if(Ext.getCmp("storeId").getValue()!=''){
							        		Ext.getCmp("storeId").setValue('');
							        	}
							        	
							        	Ext.getCmp("storeId").bindStore(shop_store);
							        },
									params:{
										storeType:storeType,
									},
									scope:this
								});
							}
						}

					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择店铺',
						width:250,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
//						value: '',//預設選到的value
//						selectOnFocus:true,//設為onFocus==true
						emptytext:'可选',
						displayField: 'aomsshop001-002',
						valueField: 'aomsshop001',
						id: 'storeId'
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作类型',
						width:150,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [
							       ["拉取资料","syncHand"],
							       ["推送资料","pushHand"]
							      ]
						}),
						value: '',//預設選到的value
						selectOnFocus:true,//設為onFocus==true
						displayField: 'name',
						valueField: 'value',
						id: 'processHand',
					}]
				},{
					style: 'margin: 0px 10px 0px 20px;',
					xtype: 'button',
					text: '执行',
					width: 80,
					iconCls: 'accept',
					id:'doStart',
					handler: doManualScheduleFn
				},{
					style: 'margin: 0px 10px 0px 20px;',
					xtype: 'button',
					text: '取消',
					width: 80,
					iconCls: 'cancel',
					id:'doCancel',
					handler: cancelManualScheduleFn
				}]
			},{
				layout: 'table',
				layoutConfig: {
					columns:1
				},
				items: [{
					colspan: 2,
					layout: 'form',
					items: [{
						xtype: 'textarea',
						fieldLabel: '操作信息',
						width:760,
						height:225,
						id: 'console',
						disabled:true
					}]
				}]
			}]
		})
	}
});


doManualScheduleFn = function(){
	var createTimeRadio = Ext.getCmp("createTimeRadio").getValue();
	var modifyTimeRadio = Ext.getCmp("modifyTimeRadio").getValue();
	var payTimeRadio = Ext.getCmp("payTimeRadio").getValue();
	var orderIdRadio = Ext.getCmp("orderIdRadio").getValue();
	
	if(createTimeRadio == false && modifyTimeRadio == false && orderIdRadio == false
			&& payTimeRadio == false){
		Ext.Msg.alert("错误信息提示", '请选择一个条件选项！', function(){});
		return;
	}else{
		
		var conditionType = 0;
		var conditionStatus = 0;
		var sDate = '';
		var eDate = '';
		
		if(createTimeRadio){
			
			var createStart = Ext.getCmp("createStart").getValue();
			var createEnd = Ext.getCmp("createEnd").getValue();
			conditionStatus = dateValidation(createStart,createEnd);
			if((createEnd - createStart) / 60000 / 60 / 24 / 2 > 1){
				Ext.Msg.alert("错误信息提示", '为了提升本系统的运行效率，目前暂时支持同步的时间区间为2天！不便之处请谅解！', function(){});
				conditionStatus=0;
			}
			
			conditionType = 1;
			sDate = createStart.format("Y-m-d H:i:s");
			eDate = createEnd.format("Y-m-d H:i:s");
			
		} else if(modifyTimeRadio){
			
			var modifyStart = Ext.getCmp("modifyStart").getValue();
			var modifyEnd = Ext.getCmp("modifyEnd").getValue();
			conditionStatus = dateValidation(modifyStart,modifyEnd);
			if((modifyEnd - modifyStart) / 60000 / 60 / 24 / 2 > 1){
				Ext.Msg.alert("错误信息提示", '为了提升本系统的运行效率，目前暂时支持同步的时间区间为2天！不便之处请谅解！', function(){});
				conditionStatus=0;
			}
			conditionType = 2;
			sDate = modifyStart.format("Y-m-d H:i:s");
			eDate = modifyEnd.format("Y-m-d H:i:s");
			
		} else if(orderIdRadio){
			
			var orderId = Ext.getCmp("orderId").getValue();
			if(orderId == ''){
				Ext.Msg.alert("错误信息提示", '单号必填', function(){});
			}else{
				conditionStatus = 1;
				conditionType = 3;
			}
		} else if (payTimeRadio) {
			var payStart = Ext.getCmp("payStart").getValue();
			var payEnd = Ext.getCmp("payEnd").getValue();
//			conditionStatus = dateValidation(payStart,payEnd);
//			if((modifyEnd - modifyStart) / 60000 / 60 / 24 / 2 > 1){
//				Ext.Msg.alert("错误信息提示", '为了提升本系统的运行效率，目前暂时支持同步的时间区间为2天！不便之处请谅解！', function(){});
//				conditionStatus=0;
//			}
			conditionStatus = 1;
			conditionType = 4;
			sDate = payStart.format("Y-m-d H:i:s");
			eDate = payEnd.format("Y-m-d H:i:s");
		}
	}
	
	//
	var storeType = Ext.getCmp("storeType").getValue();
	var storeId = Ext.getCmp("storeId").getValue();
	var processHand = Ext.getCmp("processHand").getValue();
	if (storeType == '') {
		Ext.Msg.alert("错误信息提示", '请至少选择平台！', function(){});
		return;
	}
	if (processHand == ''){
		Ext.Msg.alert("错误信息提示", '请选择操作类型！', function(){});
		return;
	}
	if(conditionStatus == 1){
		if(processHand == 'syncHand'){
			doRequestManual('sync.do',storeId,storeType,conditionType,orderId,sDate,eDate);
		}else{
			doRequestManual('push.do',storeId,storeType,conditionType,orderId,sDate,eDate);
		}
	}
};

doRequestManual = function(doUrl,storeId,storeType,conditionType,orderId,startDate,endDate){
	Ext.getCmp("doStart").setDisabled(true);
	Ext.Ajax.request({
        url: 'order/'+doUrl,
        timeout: 900000,//default 30000 milliseconds 
        method:'POST',
        success: function(response, options){
        	Ext.Msg.alert("信息提示", '执行成功', function(){
        		Ext.getCmp("doStart").setDisabled(false);
        		var showConsole='';
        		var dataRes = Ext.util.JSON.decode(response.responseText);
        		
        		if(dataRes.isSuccess == 0){
        			showConsole += '执行完成 ， 执行交易数为：' + dataRes.resultSize;
        		}else{
        			showConsole += '执行失败 ， 错误信息为==> 代码：' + dataRes.errCode + '信息：' + dataRes.errMsg;
        		}
        		 
        		Ext.getCmp("console").setValue(showConsole);
        	});
        	
        },failure: function(response, options){
			Ext.Msg.alert("信息提示", '操作失败', function(){
				Ext.getCmp("doStart").setDisabled(false);
			});
			Ext.getCmp("console").setValue('系统超时，请重新执行!! ');
		},params:{
			storeId:storeId,
			storeType:storeType,
			conditionType:conditionType,
			orderId:orderId,
			startDate:startDate,
			endDate:endDate
		},
		scope:this
	});
}


dateValidation = function(sDate,eDate){
	var now = new Date();
	var status=0;
	if(sDate == '' || eDate == ''){
		Ext.Msg.alert("错误信息提示", '起始日期和结束日期都为必填!', function(){});
	} else if(sDate>now || eDate>now){
		Ext.Msg.alert("错误信息提示", '日期不能大于当前日期', function(){});
	} else{
		if(sDate>eDate){
			Ext.Msg.alert("错误信息提示", '起始日期不能大于终止日期', function(){});
		}else{
			status=1;
		}
	}
	
	return status;
}


cancelManualScheduleFn=function() {
	
}


