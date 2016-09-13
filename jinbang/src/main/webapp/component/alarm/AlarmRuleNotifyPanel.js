/**
 * 例子;
  var treeSelector = Ext.create('component.alarm.AlarmRuleNotifyPanel', {
		width : 400,
		height : 400
	});
 */
/**
 * 告警通知控件
 */
Ext.define('component.alarm.AlarmRuleNotifyPanel', {
	extend:'Ext.panel.Panel',
	alias:'widget.alarmRuleNotifyPanel',
	constructor : function(config){
		config=config||{};
		var me=this;
		var combo=Ext.create('component.public.AjaxComboBox',{
			region:'north',
			name:config.comboName,
			fieldLabel:config.comboLabel||'通知类型',
			readOnly:true,
			value:'1',
			listeners:{
				beforeselect:function(combo,record,index){
					me.treeType=index;
				}
			},
			data:[['固定人员','1'],['值班线','2'],['专项小组','3']],
			queryMode:'local'
		});
		me.treeType=1;
		me.currentAction='org';
		var margin=(config.height?(config.height/3):50);
//		me.getCurrentTreeParam();
		var treePanel=Ext.create('component.public.TreeCommonPanel', {
					height : 200,
					cascadeCheck : 'downmulti',
					autoExpand : true, // 标题是否展开
					checkable : false, // 是否显示可选框(Boolean)
					// checkBox : true, //是否显示可选框(Boolean)
					singleSelect : false, // 单选(Boolean) (级别比cascadeCheck高)
					onlyLeafCheckable : false, // 是否只有叶子节点可选(Boolean)
					// itemcontextmenu : true, //添加树右键菜单(Boolean)
					txtSearch : true, // 是否显示查询框(Boolean)
					searchWidth : '60%', // 查询框的宽度,配合txtSearch使用(String/Long)
					barExpand : true, // 展开按钮(Boolean)
					barAllCheck : true,
					rootVisible : true,					//根节点可见
					rootId : '00A',                 		//根节点值
					rootText : '人员列表',				//根节点text
					iconable : true,					//是否显示图标(Boolean)
//					rootIcon : '../common/topoImages/16x16/physicalsubnet.gif', //根节点图标URL
					idKey : 'staffId',
					nodeId : 'staffId',
					nodeName : 'staffName',
					nodeIcon : 'treeIconPath',
					sqlKey:sqlmapPrefix+'StaffMapper.selectTreeList'
				});
		var treeSelector=Ext.create('component.public.TreeSelector',{
			region:'center',
			fieldLabel:config.treeLabel||'通知对象',
			labelStyle:'margin-top:'+margin+'px',
			fromTitle:'可选对象',
			displayField:'text',
			valueField:'id',
			fromName:config.fromName,
			toName:config.toName,
			treePanel:treePanel,
			toTitle:'已选对象'
		});
		Ext.applyIf(config,{
			layout:'border',
			items:[combo,treeSelector]
		});
		me.treePanel=treePanel;
		me.callParent([config]);
		treePanel.getStore().window=me;
		me.treeSelector=treeSelector;
		treePanel.getStore().on('load',me.onStoreLoad);
	},
	getSelectRawValue:function(){
		var selectedValues="";
		this.treeSelector.toField.store.each(function(record,index){
			if(!Ext.isEmpty(record.get('id'))){
				selectedValues+=(record.get('id')+",");
			}
		});
		return selectedValues.substr(0,selectedValues.length-1);
	},
	getTreePanel:function(){
		return this.treePanel;
	},
	clearSelectList:function(){
		this.treeSelector.toField.store.removeAll();
	},
	setSelectList:function(paramValue,originalValue){
		if(Ext.isEmpty(paramValue)) return;
		 var paramValueList = paramValue.split(",");
		 var oriValList = originalValue.split(",");
		 var records=[];
		 var Model=this.treeSelector.toField.store.model;
		 for(var i in paramValueList){
		 var obj={id:paramValueList[i],text:oriValList[i]};
		 records.push(new Model(obj));
		 }
		 this.treeSelector.toField.store.add(records);
//		console.info(this.treeSelector.displayField);
//		var tt={id:'11',text:'11'};
//		var Model=this.treeSelector.toField.store.model;
//		var record=new Model(tt);
//		this.treeSelector.toField.store.add(record);
	},
	onStoreLoad:function(store, node, records, successful, eOpts){
//		var me=this;
//		if(records.length==0){
//			var params=me.window.setCurrentTreeParam(me.window.treeType,node,node.get("id"));
//			Ext.apply(store.proxy.extraParams,params);
//			if(me.window.currentAction!='end'&&node.currentAction!=me.window.currentAction){
//				node.currentAction=me.window.currentAction;
//				me.window.getTreePanel().refreshNodeData(node.get("id"));
//			}
//		}
	},
//	actionParam:{
//		org:{
//			idKey : 'parentId',
//			nodeId : 'orgId',
//			nodeName : 'orgName',
//			sqlKey:sqlmapPrefix+'StaffMapper.selectTreeList',
//			paramMap:{}
//		},
//		job:{
//			idKey : 'orgId',
//			nodeId : 'jobId',
//			nodeName : 'jobName',
//			sqlKey:sqlmapPrefix+'UosJobMapper.selectTreeList',
//			paramMap:{}
//		},
//		staff:{
//			idKey : 'staffId',
//			nodeId : 'staffId',
//			nodeName : 'staffName',
//			sqlKey:sqlmapPrefix+'UosStaffMapper.selectTreeList',
//			paramMap:{}
//		},
//		duty:{
//			idKey : 'belongOrgId',
//			nodeId : 'dutyModuleId',
//			nodeName : 'dutyModuleName',
//			sqlKey:sqlmapPrefix+'DutyModuleMapper.selectTreeList',
//			paramMap : {}
//		}
//	},
	getAction:function(record){
		var attributes=Ext.decode(record.raw.attributes);
		if(!Ext.isEmpty(attributes.orgName)){
			return 'org';
		}
		if(!Ext.isEmpty(attributes.jobName)){
			return 'job';
		}
		if(!Ext.isEmpty(attributes.staffName)){
			return 'staff';
		}
		if(!Ext.isEmpty(attributes.dutyModuleName)){
			return 'duty';
		}
	},
	setCurrentTreeParam : function(treeType,node,parentId){
		var me=this;
		var currentAction=me.getAction(node);
		if(treeType=="1"){//固定人员树的结构 uos_org,uos_job,uos_staff,
			if(currentAction=='org'){
				me.currentAction='job';
				var paramMap={orgId:parentId};
				me.actionParam[me.currentAction].paramMap=Ext.encode(paramMap);
				return me.actionParam[me.currentAction];
			}
			if(currentAction=='job'){
				me.currentAction='staff';
				var paramMap={staffId:parentId};
				me.actionParam[me.currentAction].paramMap=Ext.encode(paramMap);
				return me.actionParam[me.currentAction];
			}
			if(currentAction=='staff'){
				me.currentAction='end';
				return null;
			}
		}
		if(treeType=="2"){//值班线树的结构 uos_org,duty_module,
			if(currentAction=='org'){
				me.currentAction='duty';
				var paramMap={belongOrgid:parentId};
				me.actionParam[me.currentAction].paramMap=Ext.encode(paramMap);
				return me.actionParam[me.currentAction];
			}
			if(currentAction=='duty'){
				me.currentAction='end';
				return null;
			}
		}
		if(treeType=="3"){//专项小组树的结构 uos_org,uos_job
			if(currentAction=='org'){
				me.currentAction='job';
				var paramMap={staffId:parentId};
				me.actionParam[me.currentAction].paramMap=Ext.encode(paramMap);
				return me.actionParam[me.currentAction];
			}
			if(currentAction=='job'){
				me.currentAction='end';
				return null;
			}
		}
	},
	getCurrentTreeParam : function(treeType,currentAction) {
//		var me=this;
//		if(!me.currentParam){
//			me.currentParam=me.actionParam.org;
//			me.currentParam.paramMap.parentId=0;
//		}
//		return me.currentParam;
	}
});
