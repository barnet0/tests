/**
 * @author sux
 * @desc 用户信息
 */
UserInfoPanel = Ext.extend(Ext.grid.EditorGridPanel,{
	id: 'userInfoPanelId',
	title:"系统用户管理",
	constructor: function(){
		Ext.QuickTips.init();
		userStore = new Ext.data.JsonStore({
			url: 'system/list.do',
			root: 'root',
 			totalProperty: 'totalProperty',
			fields: ['userId','userRealName','userName','userRemark',{name: 'userDate', mapping: 'userDate.time', dateFormat : 'time', type: 'date' }]
		});
		var rowNumber = new Ext.grid.RowNumberer(); //序列号	
		var checkbox = new Ext.grid.CheckboxSelectionModel(); //{默认是多选singleSelect: false}
		UserInfoPanel.superclass.constructor.call(this,{
			/**表格高度自适应 document.body.clientHeight浏览器页面高度 start**/
			monitorResize: true, 
			doLayout: function() { 
				this.setWidth(document.body.clientWidth);
				this.setHeight(document.body.clientHeight);
				Ext.grid.GridPanel.prototype.doLayout.call(this); 
			} ,
			viewConfig: {
				forceFit: true,
				columnsText : "显示/隐藏列",
                sortAscText : "正序排列",
                sortDescText : "倒序排列"
			},
			sm: checkbox,
			store: userStore,
			columns: [
				rowNumber, checkbox,
				{
					header: '用户编号',
					dataIndex: 'userId',
					align: 'center'
				},{
					header: '用户登录名称',
					dataIndex: 'userName',
					align: 'center'
				},{
					header: '用户真实姓名',
					dataIndex: 'userRealName',
					align: 'center'
				},{
					header: '创建日期',
					dataIndex: 'userDate',
					renderer: Ext.util.Format.dateRenderer('Y-m-d'),
					align: 'center'
				},{
					header: '备注',
					dataIndex: 'userRemark',
					align: 'center'
				}],
			tbar: new Ext.Toolbar({
				style: 'padding: 5px;',
				id: 'userToolbar',
				items: ['条目:',{
					xtype: 'combo',
					width: 150,
					triggerAction: 'all',
					editable: false,
					mode: 'local',
					store: new Ext.data.SimpleStore({
						fields: ['name','value'],
						data: [[" "," "],["userName","用户登录名"],["userRealName","用户真实名"]]
					}),
					id: 'user_condition',
					displayField: 'value',
					valueField: 'name'
				},'内容:',{
					id: 'user_conditionValue',
					xtype: 'textfield',
					width: 150
				},{
					text: '查询',
					tooltip: '查询用户',
					iconCls: 'search',
					id: 'user_query',
					handler: queryUserFn
				},{
					text: '添加',
					tooltip: '添加用户',
					id: 'user_add',
					//hidden: 'true',
					iconCls: 'add',
					handler: userAddFn
				},{
					text: '修改',
					id: 'user_update',
					iconCls: 'update',
					//hidden: 'true',
					tooltip: '修改用户',
					handler: userUpdateFn
				},{
					text: '删除',
					tooltip: '删除用户',
					id: 'user_delete',
					iconCls: 'delete',
					//hidden: 'true',
					handler: delUserFn
				}]
			}),
			bbar: new PagingToolbar(userStore, 20)
		});
		this.getStore().load({
			params: {
				start: 0,
				limit: 20
			}
		});
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
delUserFn = function(){
	var selectionModel = Ext.getCmp('userInfoPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length != 1){
		Ext.Msg.alert('信息提示','请选择一条要删除的用户信息!');
		return;
	}
	var userIdV = record[0].get('userId');
	if(userIdV != null && userIdV != ""){
		Ext.Msg.confirm("信息提示","是否确定删除这条用户记录?",function(button, text){
			if(button == "yes"){
				Ext.Ajax.request({
					url: "system/delete.do",
					success: function(response, options){
								var datas = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert("信息提示", datas.msg, function(){
									Ext.getCmp('userInfoPanelId').getStore().reload();
								});
							},
					failure: function(response, options){
								Ext.Msg.alert("信息提示", '数据库连接失败', function(){});
							},
					params: {
						ids: userIdV
					}
				});
			}
		});
	}else{
		Ext.Msg.alert("信息提示","请选择您要删除的记录！",function(){});
	}
};

queryUserFn = function(){
	var condition = Ext.getCmp('user_condition').getValue();
	var conditionValue = Ext.getCmp("user_conditionValue").getValue();
	Ext.getCmp("userInfoPanelId").getStore().reload({
		params: {
			condition: condition,
			conditionValue : conditionValue,
			start: 0,
			limit: 20
		}
	});
};
userAddFn = function(){
	var userAddWin = new UserAddWin("用户添加");
	userAddWin.show();
};
userUpdateFn = function(){
	var userUpdateWin = new UserUpdateWin("用户修改");
	var selectionModel = Ext.getCmp('userInfoPanelId').getSelectionModel();
		var record = selectionModel.getSelections();
		if(record.length != 1){
			Ext.Msg.alert('提示','请选择一个');
			return;
		}
		var userId = record[0].get('userId');
	Ext.getCmp('userUpdateFormId').getForm().load({
		url: 'system/intoUpdate.do',
		params: {
			userId: userId
		}
	});
	userUpdateWin.show();
};