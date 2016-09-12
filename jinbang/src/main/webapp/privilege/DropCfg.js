var Privilege = {
	loadWinData:function(win,sqlKey,iconUrl,idKey){
		var record=Privilege[win].getRecord();
		Privilege[win].clear();
		var params = {};
		params[idKey] = record.get('id');
		Ext.Persistent.getPage({sqlKey:sqlKey,conditionsStr:Ext.encode(params)},function(data){
			console.info(data);
			for(var i=0;i<data.result.length;i++){
				data.result[i].src = ctx+iconUrl;
			}
			Privilege[win].getStore().loadData(data.result);
		});
	},
	winSubmit:function(win,url,idKey,anotherKey){
		var store = Privilege[win].getStore();
		var length = store.getCount(),list = [],record=Privilege[win].getRecord();
		for(var i=0;i<length;i++){
			var storeRecord = store.getAt(i);
			var obj={id:'NULL'};
			obj[idKey]=record.raw.id;
			obj[anotherKey]=storeRecord.get('nodeId');
			list.push(obj);
		}
		var params={};
		params[idKey]=record.get('id');
		params['list']=list;
		
		Ext.Persistent.updateItems(url,params,function(data){
			Ext.Msg.alert('提示','保存成功!');
		});
	},
	privilegeCfg:function(){
		var view = template.generalGird,record=null,saveUrl="",loadUrl="",idKey="",anotherKey="privilegeId",recordKey="",iconUrl='/common/images/privilege/key_power_16.png';
		record=Ext.gridSelectCheck(view);
		if(template.type=='userManager'){
			saveUrl='DopPrivilegeUserPrivilege';
			loadUrl='DopPrivilegeUserPrivilege.selectList';
			idKey='userId';
			recordKey='userId';
		}else{
			saveUrl='DopPrivilegeRolePrivilege';
			loadUrl='DopPrivilegeRolePrivilege.selectList';
			idKey='roleId';
			recordKey='roleId';
		}
		if(!record) return;
		if(!Privilege.privilegeWin){
			Privilege.privilegeWin = Ext.create('component.business.SelectorWindow', {
			    title: '权限选择器',border:false,draggable:true,maximizable:true,minimizable:true,resizable:true,
			    width: Ext.getBody().getWidth()*0.8,
			    height: Ext.getBody().getHeight()*0.8,
			    selector:'component.business.UserTree',
			    selectorCfg:{title:'权限选择器',rootText:'系统所有权限',
			    	paramMap:{
				    	sqlKey : 'DopPrivilege.selectLevel',
				    	iconStr:',,/common/images/privilege/computer_16.png,'+iconUrl,
						valueField:'id',
						displayField:'name'
			    	}
			    },
			    setRecord:function(record){
			    	this.record=record;
			    },
			    getRecord:function(record){
			    	return this.record;
			    },
			    dropView:'component.business.DropView',
			    dropViewCfg:{
			    },
			    buttons:[{
			    	text:'删除选中',
			    	iconCls:'toolbar-invalid',
			    	handler:function(){
			    		Privilege.privilegeWin.deleteSelect();
			    	}
			    },{
			    	text:'清空',
			    	iconCls:'toolbar-delete',
			    	handler:function(){
			    		Privilege.privilegeWin.clear();
			    	}
			    },{
			    	text:'保存配置',
			    	handler:Ext.bind(Privilege.winSubmit,Privilege,["privilegeWin",saveUrl,idKey,anotherKey,recordKey],0)
			    },{
			    	text:'取消',
			    	handler:function(){
			    		Privilege.privilegeWin.hide();
			    	}
			    }]
			});
			Privilege.privilegeWin.on('beforeshow',Ext.bind(Privilege.loadWinData,Privilege,["privilegeWin",loadUrl,iconUrl,idKey],0));
		}
		Privilege.privilegeWin.setRecord(record);
		Privilege.privilegeWin.show();
	},
	roleCfg:function(){
		var view = template.generalGird;
		var record=Ext.gridSelectCheck(view);
		if(!record) return;
		var saveUrl='DopPrivilegeUserRole';
		var loadUrl='DopPrivilegeUserRole.selectList';
		var iconUrl='/common/images/privilege/key_role_16.png';
		var idKey='userId';
		var anotherKey='roleId';
		var recordKey='userId';
		if(!Privilege.roleWin){
			Privilege.roleWin = Ext.create('component.business.SelectorWindow', {
			    title: '角色选择器',border:false,draggable:true,maximizable:true,minimizable:true,resizable:true,
			    width: Ext.getBody().getWidth()*0.8,
			    height: Ext.getBody().getHeight()*0.8,
			    selector:'component.business.UserTree',
			    selectorCfg:{title:'角色选择器',rootText:'系统所有角色',
			    	paramMap:{
				    	sqlKey : 'DopPrivilegeRole.selectLevel',
				    	iconStr:',,/common/images/privilege/computer_16.png,'+iconUrl,
						valueField:'id',
						displayField:'name'
			    	}
			    },
			    setRecord:function(record){
			    	this.record=record;
			    },
			    getRecord:function(record){
			    	return this.record;
			    },
			    dropView:'component.business.DropView',
			    dropViewCfg:{
			    },
			    buttons:[{
			    	text:'删除选中',
			    	iconCls:'toolbar-invalid',
			    	handler:function(){
			    		Privilege.roleWin.deleteSelect();
			    	}
			    },{
			    	text:'清空',
			    	iconCls:'toolbar-delete',
			    	handler:function(){
			    		Privilege.roleWin.clear();
			    	}
			    },{
			    	text:'保存配置',
			    	handler:Ext.bind(Privilege.winSubmit,Privilege,["roleWin",saveUrl,idKey,anotherKey,recordKey],0)
			    },{
			    	text:'取消',
			    	handler:function(){
			    		Privilege.roleWin.hide();
			    	}
			    }]
			});
			Privilege.roleWin.on('beforeshow',Ext.bind(Privilege.loadWinData,Privilege,["roleWin",loadUrl,iconUrl,idKey],0));
		}
		Privilege.roleWin.setRecord(record);
		Privilege.roleWin.show();
	}
};