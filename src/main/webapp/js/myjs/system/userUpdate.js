Ext.namespace("hrmsys.user.update");

UserUpdateWin = Ext.extend(Ext.Window,{
	id: 'userUpdateWinId',
	addForm: null,
	constructor: function(title){
		var updateForm = new UserUpdateForm();
		UserUpdateWin.superclass.constructor.call(this,{
			title: title,
			width: 450,
			modal: true,
			height: 310,
			collapsible: true,
			colsable: true,
			layout: 'form',
			items: [updateForm]
		});
	}
});
UserUpdateForm = Ext.extend(Ext.form.FormPanel,{
	id: 'userUpdateFormId', 
	constructor: function(){
	 	Ext.form.Field.prototype.msgTarget = 'side';
		Ext.QuickTips.init();
		//加载后台数据，进行转换
		var reader = new Ext.data.JsonReader({},[{
			name: 'user.userId'	, mapping: 'userId'
		},{
			name: 'user.userRealName', mapping: 'userRealName'
		},{
			name: 'user.userName', mapping: 'userName'
		},{
			name: 'user.userRemark', mapping: 'userRemark'
		},{
			name: 'user.userPwd', mapping: 'userPwd'
		}]);
	    UserUpdateForm.superclass.constructor.call(this, {
	    	labelWidth: 100,
	    	height: 275,
	    	padding: '20 0 0 50',
	    	labelAlign: 'right',
	    	border: false,
	    	frame: true,
	    	reader: reader,
	    	items: [{
	    		xtype: 'textfield',
	    		width: 200,
	    		fieldLabel: '用户编号',
	    		id: 'userId',
	    		readOnly: true,
	    		name: 'user.userId'
	    	},{
	    		xtype: 'textfield',
	    		fieldLabel: '用户登录名',
	    		width: 200,
	    		id: 'userName',
	    		name: 'user.userName',
	    		readOnly: false
	    	},{
	    		xtype: 'textfield',
	    		fieldLabel: '用户真实姓名',
	    		width: 200,
	    		readOnly: false,
	    		id: 'updateUserName',
	    		name: 'user.userRealName'
	    	},{
	    		xtype: 'textarea',
	    		fieldLabel: '备注',
	    		width: 200,
	    		height: 80,
	    		name: 'user.userRemark'
	    	},{
	    		xtype: 'hidden',
	    		name: 'user.userPwd'
	    	},{
	    		xtype:'radiogroup',
	    		name:'resetPwdrdg',   
                id: 'resetPwdrdg', 
                fieldLabel: '是否重置密码',
                width: 200,   
                items: [{
                	boxLabel: '是',
                	name: 'resetPwd',
            		inputValue: '1'
        		},{
        			boxLabel: '否',
                	name: 'resetPwd',
            		inputValue: '0',
            		checked:true
        		}]
	    	},{
				xtype:"label",
				width: 200,
				html:"<font color='#FF0000' style='font-family:宋体;font-size:14px'>重置密码：123456</font></br>"
			}],
	    	buttonAlign: 'center',
	    	buttons: [{
	    		text: '保存',
	    		handler: function(){
	    			var userForm = Ext.getCmp('userUpdateFormId').getForm();
	    			if(!userForm.isValid()){
						return;
					}
	    			var jsonUser = Ext.util.JSON.encode(userForm.getValues());
	    			Ext.getCmp('userUpdateFormId').getForm().submit({
	    				url: 'system/saveorupdate.do',
	    				method: 'post',
	    				waitMsg: '正在保存数据...',
	    				waitTitle: '信息提示',
	    				scope: this,
	    				params:{jsonUser:jsonUser},
	    				success: updateUserSuccess,
	    				failure: updateUserfailure
	    			});
	    		}
	    	},{
	    		text: '关闭',
	    		handler: function(){
	    			Ext.getCmp('userUpdateWinId').destroy();
	    		}
	    	}]
	    });
	}
});
updateUserSuccess = function(form, action){
	Ext.Msg.alert('信息提示', action.result.msg, function(button, text){
		form.reset();
		Ext.getCmp('userUpdateWinId').destroy();
		Ext.getCmp('userInfoPanelId').getStore().reload();//刷新部门显示列表
	});
};
updateUserfailure = function(response, options){
	Ext.Msg.alert("信息提示","连接失败", function(button, text){});
};
