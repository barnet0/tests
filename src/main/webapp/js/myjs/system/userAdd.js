Ext.namespace("hrmsys.user.add");

UserAddWin = Ext.extend(Ext.Window,{
	id: 'userAddWinId',
	addForm: null,
	constructor: function(title){
		var addForm = new UserAddForm();
		UserAddWin.superclass.constructor.call(this,{
			title: title,
			width: 450,
			modal: true,
			height: 310,
			resizable: false,
			collapsible: true,
			colsable: true,
			layout: 'form',
			items: [addForm]
		});
	}
});
UserAddForm = Ext.extend(Ext.form.FormPanel,{
	id: 'userAddFormId', 
	constructor: function(){
	 	Ext.form.Field.prototype.msgTarget = 'side';
		Ext.QuickTips.init();
	    UserAddWin.superclass.constructor.call(this, {
	    	height: 275,
	    	labelWidth: 100,
	    	padding: '20 0 0 50',
	    	labelAlign: 'right',
	    	border: false,
	    	frame: true,
	    	items: [{
				xtype: 'textfield',
	    		width: 200,
	    		fieldLabel: '用户编号',
	    		id: 'userId',
	    		name: 'user.userId',
	    		readOnly:true,
	    		value:0
			},{
	    		xtype: 'textfield',
	    		fieldLabel: '用户登录名',
	    		width: 200,
	    		id: 'userName',
	    		name: 'user.userName'
	    	},{
	    		xtype: 'textfield',
	    		fieldLabel: '用户真实姓名',
	    		allowBlank: false,
	    		msgTarget: 'side',
	    		blankText: '不能为空',
	    		width: 200,
	    		id: 'userRealName',
	    		name: 'user.userRealName'
	    	},{
	    		xtype: 'textarea',
	    		fieldLabel: '备注',
	    		width: 200,
	    		height: 80,
	    		name: 'user.userRemark'
	    	},{
				xtype:"label",
				width: 200,
				html:"<font color='#FF0000' style='font-family:宋体;font-size:14px'>默认密码：123456</font></br>"
			}],
	    	buttonAlign: 'center',
	    	buttons: [{
	    		text: '保存',
	    		iconCls: 'save',
	    		handler: function(){
	    			var userForm = Ext.getCmp('userAddFormId').getForm();
	    			if(!userForm.isValid()){
						return;
					}
	    			var jsonUser = Ext.util.JSON.encode(userForm.getValues());
	    			Ext.getCmp('userAddFormId').getForm().submit({
	    				url: 'system/saveorupdate.do',
	    				method: 'post',
	    				waitMsg: '正在保存数据...',
	    				waitTitle: '信息提示',
	    				scope: this,
	    				params:{jsonUser:jsonUser},
	    				success: saveUserSuccess,
	    				failure: saveUserfailure
	    			});
	    		}
	    	},{
	    		text: '取消',
	    		iconCls: 'cancel',
	    		handler: function(){
	    			Ext.getCmp('userAddFormId').getForm().reset();
	    		}
	    	}]
	    });
	}
});
saveUserSuccess = function(form, action){
	Ext.Msg.alert('信息提示', action.result.msg, function(button, text){
		form.reset();
		Ext.getCmp('userAddWinId').destroy();
		Ext.getCmp('userInfoPanelId').getStore().reload();//刷新部门显示列表
	});
};
saveUserfailure = function(response, options){
	Ext.Msg.alert("信息提示","连接失败", function(button, text){});
};
user_blurFn = function(value){
	var empId = value.getRawValue();
	Ext.Ajax.request({
		url: 'emp_isExist.action',
		method: 'post',
		params: {
			empId: empId
		},
		success: user_isExistSuccessFn,
		failure: save_failure
	});
};
user_isExistSuccessFn = function(response, options){
	if(response.responseText != ""){
		Ext.get('userEmpName').dom.value = response.responseText;
		Ext.get('userUserName').dom.value = response.responseText;
	}else{
		Ext.getCmp('userEmpId').markInvalid('此工号不存在');
	}
};
