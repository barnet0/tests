var LogMaintainEcQueryWin = Ext.extend(Ext.Window,{
	id:'logMaintainEcQueryWinId',
	constructor:function () {
		var ecQueryPanel = new EcQueryPanel();
		LogMaintainEcQueryWin.superclass.constructor.call(this, {
			title: '平台类型选择',
			resizable: false,
			closable:false,
			layout:'fit',
			width: 400,
			height:300,
			//autoWidth:true,
			//autoHeight:true,
			modal: true,
			items: [ecQueryPanel],
			buttons: [
				{ xtype: "button", text: "确定", 
					handler: function () { 
						var gsm = ecQueryPanel.getSelectionModel();
						var rows = gsm.getSelections();
						if(rows.length > 0) {
							//Ext.Msg.alert("test",rows.length,function () {});
							var rowLength = rows.length;
							var ecNamesTextField = 
								Ext.getCmp('logMaintainQueryId').
									getComponent('queryConditionFields').
									getComponent('ecSelectComp').
									getComponent('ecNamesTextField');
							var selectedEcs = '';
							for (var i = 0; i < rowLength; i++) {
								//循环迭代所有的选择的row
								var row = rows[i];
								var ecCode = row.get('ecCode');
								var ecName = row.get('ecName');
								selectedEcs += ecCode + '-' + ecName + ' ';
							}
							ecNamesTextField.setValue(selectedEcs);
							Ext.getCmp('logMaintainEcQueryWinId').close();
						}
					}
				},
				{ xtype: "button", text: "取消", 
					handler: function () {  
						Ext.getCmp('logMaintainEcQueryWinId').close();
					} 
				}
			]
		})
	}
});


var EcQueryPanel=Ext.extend(Ext.grid.GridPanel,{
	id: 'ecQueryPanelId',
	constructor: function(){
		var dataProxy = new Ext.data.HttpProxy({
			url : 'shopmaintain/queryEcInfo.do',
			method:'POST'
		});
		
		var jsonReader = new Ext.data.JsonReader(
				{totalProperty:"totalProperty",root:"root"},
				["ecCode","ecName"]
			);  
		
		var store = new Ext.data.Store({
			proxy:dataProxy,
			reader:jsonReader
		});
		
		store.load();
		
		var columnModel = new Ext.grid.ColumnModel([
			new Ext.grid.CheckboxSelectionModel(),
			{header:"代号",width:100,dataIndex:"ecCode"},
			{header:"平台名称",width:100,dataIndex:"ecName"}
		]);
		
		//分页  
		var pageBar = new Ext.PagingToolbar({  
			store:store,  
			pageSize:10,  
			displayInfo:true,  
			displayMsg:"当前为{0}-{1}条,共{2}条",  
			emptyMsg:"没有记录"  
		});
		
		EcQueryPanel.superclass.constructor.call(this,{
			//width: 500,
			//height: 400,
			frame: true,
			autoScroll:true,
			cm:columnModel,
			sm:new Ext.grid.CheckboxSelectionModel(),
			store:store,
			bbar:pageBar
		});
	}
});
