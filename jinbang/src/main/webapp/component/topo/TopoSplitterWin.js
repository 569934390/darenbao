/**
 * Epon添加风光器
 */
Ext.define('component.topo.TopoSplitterWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoSplitterWin',
	requires: [
        'component.public.AjaxComboBox'
   	],
	constructor:function(condition){
		var me = this;
		me.curNode = condition.curNode;
		me.splitterData = condition.splitterData;
		me.createTopoPanel();
		me.createNodePanel();
		me.createEponTab();
		var config = Ext.applyIf(condition,{
			border : false,
		    layout: 'fit',
			items:[me.eponTab],
			buttons : [ {
				xtype : 'button',
				text : '保存',
				scope : this,
				handler:me.buttonEvent.createDelegate(me,['eponSave'],0)
			}, {
				xtype : 'button',
				text : '取消',
				scope : this,
				handler : function() {
					me.hide();
				}
			}]
		});
		me.callParent([config]);
	},
	createEponTab : function(){
		var me = this;
		me.eponTab = Ext.create('Ext.tab.Panel', {
			region : 'center',
			border : false,
			frame : false,
		    items: [{
		        title: '基本信息',
				border : false,
				frame : false,
				layout :'border',
		        items : [me.topoForm,me.nodeForm]
		    }]
		});
	},
	createTopoPanel : function(){
		var me = this;
		var filedSet = new Ext.form.FieldSet({
			title : '',
			frame : false,
			border : false,
	        bodyPadding: 0,
			layout : 'column',
			items : [{
				columnWidth :1,
				border : false,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel:'分光器名称',
					name:'symbolName1',
					labelWidth : 65,
					labelAlign : 'right',
					allowBlank : false,
					blankText :"提示：名称必填",
		            enableKeyEvents : true,
					listeners : {
						keyup : function(form,e){
							var txtValue = form.getValue();
							me.query('[name=nodeName]')[0].setValue(txtValue);
						}
					}
				}]
			},{
				columnWidth :1,
				border : false,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel:'所在子网',
					labelWidth : 65,
					value : me.splitterData.mapParentName,
					labelAlign : 'right',
					readOnly : true
				}]
			},{
				columnWidth :.4,
				border : false,
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					name : 'x',
					fieldLabel:'X',
					labelWidth : 65,
					value : me.splitterData.x,
					width : 50,
					labelAlign : 'right',
					allowBlank : false,
					enableKeyEvents : true,
					listeners : {
						keyup: function(src, evt){
							var val;
							if(!Ext.isEmpty(src.getValue())){
								val = src.getValue().toString().replace(/^d+$/,'');
								var valArr = val.split('.');
								if(valArr.length>1){
									val=valArr[0];
								}
								src.setValue(val);
							}
						}
					}
				},{
					xtype : 'textfield',
					name : 'topoTypeName',
					labelWidth : 65,
					value : "分光器",
					emptyText : '节点树选择',
					fieldLabel : '类型',
					labelAlign : 'right',
					allowBlank : false,
					blankText : "提示：节点类型必填",
					readOnly : true
				}]
			},{
				columnWidth :.6,
				border : false,
				labelWidth : 20,
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					labelWidth : 20,
					name : 'y',
					fieldLabel:'Y',
					value : me.splitterData.y,
					width : 53,
					labelAlign : 'right',
					allowBlank : false,
					enableKeyEvents : true,
					listeners : {
						keyup: function(src, evt){
							var val;
							if(!Ext.isEmpty(src.getValue())){
								val = src.getValue().toString().replace(/^d+$/,'');
								var valArr = val.split('.');
								if(valArr.length>1){
									val=valArr[0];
								}
								src.setValue(val);
							}
						}
					}
				},{
					xtype : 'ajaxComboBox',
					displayField : 'symbolName1',
					fieldLabel : "同步Agent",
					style:'text-align:right;',
					labelWidth : 80,
					editable : false,
					width:400,
					queryMode : 'remote',
					hiddenName : 'nodeId',
					name : 'agentId',
					valueField : 'nodeId',
					typeAhead: true,
					editable : false,
				 	triggerAction: 'all',
					sqlKey : 'com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadTopoMapList',
					plugins : [Ext.create("component.public.ComboSelectFirstPlugin")],
					fields : ['nodeId', 'symbolName1'],
					paramMap :{
						nodeTypeId : '13010001'
					}
				}]
		    },{
		    	columnWidth :1,
		    	border : false,
		    	layout : 'form',
		    	items : [{
					xtype : 'textfield',
					fieldLabel:'备注',
					name:'remark',
					labelWidth : 65,
					labelAlign : 'right'
		    	}]
			}]
		});
		me.topoForm = Ext.create('Ext.form.Panel', {
			region : 'north',
			labelAlign : 'right',
			title: '拓扑属性',
			border : true,
			frame : false,
			collapsible: true,
			split : true,
			deferredRender : false,
	        bodyPadding: 0,
	        minHeight: 100,
			height : 195,
			defaults : {
				autoScroll : true
			},
			layoutOnTabChange : true,
			layout : 'fit',
			items : [filedSet]
		});
	},
	createNodePanel : function(){
		var me = this;
		me.nodeForm = Ext.create('Ext.form.Panel', {
			region : 'center',
			title: '网元属性',
			labelAlign : 'right',
			border : true,
			labelWidth : 66,
			deferredRender : false,
	        bodyPadding: 0,
			autoScroll : true,
			layoutOnTabChange : true,
			items : []
		});
		var fd1 = new Ext.form.TextField({
			xtype : 'textfield',
			fieldLabel:'网元名称',
			name:'nodeName',
			labelAlign : 'right',
			labelWidth : 80,
			allowBlank : false,
			blankText:"提示：网元名称必填",
			width : 340
		});
	   	me.nodeForm.items.add(me.nodeForm.items.getCount(),fd1);
		var fd2 = Ext.create('component.public.AjaxComboBox', {
			xtype : 'ajaxComboBox',
			fieldLabel : '区域',
			name : 'regionId',
			displayField : 'regionName',
			labelAlign : 'right',
			labelWidth : 80,
			width : 340,
			queryMode : 'remote',
			hiddenName : 'regionId',
			valueField : 'regionId',
			sqlKey : 'com.compses.dao.sqlmap.ManageRegionMapper.selectAll',
			plugins : [Ext.create("component.public.ComboSelectFirstPlugin")],
			fields : ['regionId', 'regionName']
		});
		me.nodeForm.items.add(me.nodeForm.items.getCount(),fd2);
	   	me.nodeForm.doLayout();
		/**
		 * 获取基本属性数据
		 */
		Ext.Ajax.request({
			url : webRoot + '/nodeTypeAttr/loadNodeTypeAttrByGroup.do',
			method : 'POST',
			params : {
				attrGroupId : 1,
				isCollectParam : 1,
				nodeTypeFlag : 26
			},
			success : function(resp, action) {
				var formList = Ext.JSON.decode(resp.responseText);
				for(var txtNum=0;txtNum<formList.length;txtNum++){
					var fieldTxt = formList[txtNum].attrDesc;
					if(!Ext.isEmpty(formList[txtNum].attrValueUnitName)&&formList[txtNum].attrValueUnitName!="无"){
						fieldTxt = fieldTxt +"<font color='green' weight='bold'>("+formList[txtNum].attrValueUnitName+")</font>"
					}
					if(formList[txtNum].attrValueTypeId==2){  //文本框
						var fd = new Ext.form.TextField({
							labelAlign : 'right',
							labelWidth : 80,
							name:formList[txtNum].nodeTypeAttrId,
							fieldLabel:fieldTxt,
							rendOnly : formList[txtNum].allowUpdateFlag=='0BT'?true:false,
							allowBlank : formList[txtNum].allowNullFlag=='0BT'?true:false,
							width : 340
						});
	                    me.nodeForm.items.add(me.nodeForm.items.getCount(),fd);
	                    me.nodeForm.doLayout();
					}
				}
			},
			failure : function() {
			}
		});
	},
	eponSave : function(){
		var me = this;
		var topoData = me.topoForm.getForm().getValues();
		if(!me.topoForm.form.isValid()){
			Ext.Msg.alert("提示","请填写完整拓扑属性信息");
			return; 
		}
		if(!me.nodeForm.form.isValid()){
			Ext.Msg.alert("提示","请填写完整网元属性信息");
			return; 
		}
		var nodeItems = me.nodeForm.items.items;
		var nodeName;
		var regionId;
		var dataJson="[";
		for(var iNum=0;iNum<nodeItems.length;iNum++){
			if(nodeItems[iNum].name=="nodeName"){
				nodeName = nodeItems[iNum].value;
				if(Ext.isEmpty(nodeName)){
					Ext.Msg.alert("提示","请填写网元名称");
					return;
				}
			}else if(nodeItems[iNum].name=="regionId"){
				regionId = nodeItems[iNum].value;
			}else{
				if(!Ext.isEmpty(nodeItems[iNum].value)){
					if(dataJson=="["){
						var params='{"nodeTypeAttrId":"'+nodeItems[iNum].name+'","attrValue":"'+nodeItems[iNum].value+'"}';
					}else{
						var params=',{"nodeTypeAttrId":"'+nodeItems[iNum].name+'","attrValue":"'+nodeItems[iNum].value+'"}';
					}
					dataJson = dataJson+params;
				}else{
					if(iNum>0){
						Ext.Msg.alert("提示","请填写完整您的输入信息");
						return;
					}
				}
			}
		}
		dataJson = dataJson+"]";
		var nodeId=0;
		if(me.curNode.raw.attributes==null){
			nodeId= curNode.raw.nodeId;
		}else{
			var attributes = Ext.JSON.decode(me.curNode.raw.attributes);
			nodeId=attributes.nodeId;
		}
		Ext.Ajax.request({
			url : webRoot + '/nodeTypeAttr/addSplitterElement.do',
			method : 'POST',
			params : {
				dataJson : dataJson,
				symbolName1 : topoData.symbolName1,
				topoTypeId : 62,
				nodeTypeId : 26,
				treeParentId : me.curNode.data.id,
				mapParentId : me.splitterData.mapParentId,
				nodeId : nodeId,
				x : topoData.x,
				y : topoData.y,
				remark : topoData.remark,
				nodeName : nodeName,
				regionId : regionId,
				agentId : topoData.agentId,
				symbolId : me.curNode.data.id
			},
			success : function(resp, action) {
				var mapData = Ext.JSON.decode(resp.responseText);
				mapData.onlineStatus=1;
				if(!Ext.isEmpty(mapData[0].result)){
					Ext.Msg.alert("提示","存在所添加的IP地址!");
				}else if(!Ext.isEmpty(mapData[0].success)&&!mapData[0].success){
					Ext.Msg.alert("提示","网元域名称已存在!");
				}else{
					if(topoData.treeParentId==me.curNode.data.curNodeId){
						me.curNode.appendChild({id:mapData[0].symbolId,symbolStyle:mapData[0].symbolStyle,nodeTypeId:mapData[0].nodeTypeId,nodeId:mapData[0].nodeId,isShortcut:mapData[0].isShortcut,mapParentId:mapData[0].mapParentId,mapParentName:mapData[0].mapParentName,text:mapData[0].symbolName1, leaf: true,icon:mapData[0].treeIconPath});
						Ext.get(document.body).unmask();
					}else{
						var isChild = true;
						topoTreePanel.getRootNode().cascadeBy(function(node){
							if (node.data.id==topoData.treeParentId) {
						    	if(!Ext.isEmpty(node.childNodes)){
						    		node.appendChild({id:mapData[0].symbolId,symbolStyle:mapData[0].symbolStyle,nodeTypeId:mapData[0].nodeTypeId,nodeId:mapData[0].nodeId,isShortcut:mapData[0].isShortcut,mapParentId:mapData[0].mapParentId,mapParentName:mapData[0].mapParentName,text:mapData[0].symbolName1, leaf: true,icon:mapData[0].treeIconPath});
						    		Ext.get(document.body).unmask();
						    	}else{
						    		isChild = false;
						    	}
							} 
						});
						if(!isChild){
				    		topoTreePanel.refreshData();
						}
					}
					/**
					 * 添加到拓扑图 
					 */
					var mapPanel = document.getElementById('frame_main');
					mapPanel.contentWindow.reLoadMapLayer(false,true);
//					mapPanel.contentWindow.addSplitterToMap(mapData[0]);
				}
				me.close();
			},
			failure : function() {
			}
		});
//		var task = {};
//		
//		task.agentId = me.agentId;
//		var attributes = Ext.JSON.decode(me.curNode.raw.attributes);
//		if(curNode.raw.attributes==null){
//			task.nodeId= curNode.raw.nodeId;
//			task.ipaddress = curNode.raw.ipaddress;
//		}else{
//			task.nodeId=attributes.nodeId;
//			task.ipaddress=attributes.ipaddress;
//		}
//		task.attrValue = curNode.data.text;
//		
//		var mapPanel = document.getElementById('frame_main');
//		mapPanel.contentWindow.topoNodeSynchronous(task);
//		
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'eponSave':
				me.eponSave();
				break;
		}
	}
});