/**
 * 拓扑链路
 */
Ext.define('component.topo.TopoLinkWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoLinkWin',
	requires: [
        'component.public.AjaxComboBox'
	],
	constructor:function(condition){
		var me = this;
		me.lineParams = condition.lineParams;
		me.createLinkTree();
		me.createLinkTabPanel();
		
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[me.linkTree,me.linkTabPanel]
		});
		
		me.callParent([config]);
		me.setLayoutValue();
	},
	setLayoutValue : function(){
		var me = this;
		
		if(!Ext.isEmpty(me.lineParams.lineStyle)){
			if(me.lineParams.lineStyle>1){
				me.linkTree.hide();
			}
			me.linkTabPanel.setActiveTab(1);
			//lineStyle：2为物理链路，1/3：物理链路
			if(me.lineParams.lineStyle==1||me.lineParams.lineStyle==3){
				me.lineStyle = 1;
				if(!Ext.isEmpty(Ext.getCmp('phyLinkProTab'))){
					Ext.getCmp('phyLinkProTab').close();
				}
			}
		}
	},
	createLinkTree : function(){
		var me = this;
		
		var store = Ext.create('Ext.data.TreeStore', {
		    root: {
		        expanded: true,
		        children: [
		            { text: "逻辑链路",id:'1',select:true, leaf: true },
		            { text: "物理链路",id:'2', leaf: true }
		        ]
		    }
		});
		me.linkTree = Ext.create('Ext.tree.Panel', {
			region : 'west',
		    width: 120,
		    store: store,
		    split : true,
		    rootVisible: false,
		});
		me.linkTree.on('itemclick', function(view, node, item, index,e,eOpts){
			if(node.data.id==1){
				me.lineStyle = 1;
				if(!Ext.isEmpty(Ext.getCmp('phyLinkProTab'))){
					Ext.getCmp('phyLinkProTab').close();
				}
			}else{
				me.lineStyle = 2;
				me.createPhyLinkForm(me.lineParams);
				if(Ext.isEmpty(Ext.getCmp('phyLinkProTab'))){
					me.linkTabPanel.add({
						region : 'center',
						id : 'phyLinkProTab',
						title : '物理链路',
						border : false,
						frame : false,
						items : [me.phyLinkForm]
					});
				}
			}
		});
	},
	createLinkTabPanel : function(){
		var me = this;
		var lineParams = me.lineParams;
		me.createLinkFormPanel(lineParams);
		me.createPhyLinkForm(lineParams);
		
		me.linkTabPanel = Ext.create('Ext.tab.Panel', {
			region : 'center',
			frame : false,
		    items: [{
		        title: '链路属性',
				border : false,
				frame : false,
				layout :'border',
		        items : [me.lineForm]
		    },{
				region : 'center',
				id : 'phyLinkProTab',
				title : '物理链路',
				border : false,
				frame : false,
				items : [me.phyLinkForm]
			}],
			buttonAlgin:'center',
			buttons : [{
				text : '保存',
				hidden : Ext.isEmpty(lineParams.isReadOnly)?false:lineParams.isReadOnly,
				handler : me.buttonEvent.createDelegate(me,['saveLinkData'],0)
			},{
				text : '取消',
				handler : me.buttonEvent.createDelegate(me,['cancelLink'],0)
			}]
		});
	},
	createLinkFormPanel : function (lineParams){
		var me = this;
		var directionCombo = new Ext.form.ComboBox({
			emptyText : "请选择",name : 'direction',fieldLabel : "链路方向",
			typeAhead: true,triggerAction: 'all',lazyRender:true,
			editable : false,width : 160,value : Ext.isEmpty(lineParams.direction)?'0':lineParams.direction+"",
			mode: 'local',labelWidth : 70,labelAlign : 'right',readOnly : lineParams.isReadOnly,
			store: new Ext.data.ArrayStore({
				fields: ['myId','displayText'],
				data: [['0','无向'],['1','单向'],['2','双向']]
			}),
			valueField: 'myId',displayField: 'displayText'
		});
		var colorCombo = new Ext.form.ComboBox({
			emptyText : "请选择",name : 'color',fieldLabel : "颜色",
			typeAhead: true,triggerAction: 'all',lazyRender:true,
			editable : false,width : 160,value : '4',mode: 'local',
			labelWidth : 70,labelAlign : 'right',
		    store: new Ext.data.ArrayStore({
		        fields: ['myId','displayText'],
		        data: [['1','red'],['2','green'],['3','blue'],['4','black']]
		    }),
		    valueField: 'myId',displayField: 'displayText'
		});
		var widthCombo = new Ext.form.ComboBox({
			emptyText : "请选择",name : 'width',fieldLabel : "宽度",
			typeAhead: true,triggerAction: 'all',lazyRender:true,
			editable : false,width : 160,value : Ext.isEmpty(lineParams.width)?'2':lineParams.width+"",
			mode: 'local',labelWidth : 70,labelAlign : 'right',readOnly : lineParams.isReadOnly,
		    store: new Ext.data.ArrayStore({
		        fields: ['myId','displayText'],
		        data: [['3','3'],['4','4'],['6','6'],['8','8'],['10','10'],['12','12'],['15','15']]
			}),
			valueField: 'myId',displayField: 'displayText'
		});
		var styleCombo = new Ext.form.ComboBox({
			emptyText : "请选择",name : 'style',fieldLabel : "样式",
			typeAhead: true,triggerAction: 'all',lazyRender:true,
			editable : false,width : 160,value : Ext.isEmpty(lineParams.style)?'0':lineParams.style+"",
			mode: 'local',labelWidth : 70,labelAlign : 'right',readOnly : lineParams.isReadOnly,
		    store: new Ext.data.ArrayStore({
		        fields: ['myId','displayText'],
		         data: [['0','直线'],['1','长破拆线'],['2','长破点线'],['3','点线'],['8','折线'],['4','缆线'],['5','闪电线'],['6','波浪线'],['7','箭头线'],['9','双链路']]
			}),
			valueField: 'myId',displayField: 'displayText'
		});
		var shapeCombo = new Ext.form.ComboBox({
			emptyText : "请选择",name : 'shape',fieldLabel : "链路形状",
			typeAhead: true,triggerAction: 'all',lazyRender:true,
			editable : false,width : 160,value : Ext.isEmpty(lineParams.shape)?'0':lineParams.shape+"",
			mode: 'local',labelWidth : 70,labelAlign : 'right',readOnly : lineParams.isReadOnly,
		    store: new Ext.data.ArrayStore({
		        fields: ['myId','displayText'],
		        data: [['0','平行'],['1','纵向分割'],['2','横向分割']]
		    }),
		    valueField: 'myId',displayField: 'displayText'
		});
		var filedSet = new Ext.form.FieldSet({
			title : '',
			border : false,
			layout : 'column',
			height : 240,
			items : [{
				columnWidth : 1,
				layout : 'form',
				border : false,
				items : [{
					xtype : 'textfield',name:'srcSymbolName',fieldLabel:'源节点名称',
					labelWidth : 70,labelAlign : 'right',
					value:lineParams.srcSymbolName,readOnly : true
				},{
					xtype : 'textfield',name :'destSymbolName',fieldLabel:'宿节点名称',
					labelWidth : 70,labelAlign : 'right',
					value:lineParams.destSymbolName,readOnly : true
				},{
					xtype : 'textfield',name : 'linkName1',fieldLabel : '链路名称',
					labelWidth : 70,labelAlign : 'right',
					value:lineParams.linkName1,readOnly : lineParams.isReadOnly
				},directionCombo]
			},{
				columnWidth : 1,
				layout : 'form',
				border : false,
				items :[widthCombo,styleCombo,shapeCombo,{
					xtype : 'textarea',name:'remark',fieldLabel:'描述信息',
					labelWidth : 70,height : 40,labelAlign : 'right',
					value:lineParams.remark,readOnly : lineParams.isReadOnly
				},{
					xtype : 'textfield',name:'srcSymbolId',
					height : 0,value:lineParams.srcSymbolId,hidden : true
				},{
					xtype : 'textfield',name :'destSymbolId',
					height : 0,value:lineParams.destSymbolId,hidden : true
				},{
					xtype : 'textfield',name :'linkSymbolId',
					height : 0,value:lineParams.linkSymbolId,hidden : true
				}]
			}]
		});

		me.lineForm = new Ext.form.FormPanel({
			region : 'center',
			labelAlign : 'right',
			border : false,
			frame : false,
			deferredRender : false,
			style : 'padding:0 0 0 0',
			activeTab : 0,
			autoHeight : true,
			defaults : {
				autoScroll : false
			},
			layoutOnTabChange : true,
			items : [filedSet]
		});
	},
	createPhyLinkForm : function(lineParams){
		var me = this;
		
		var directionCombo = new Ext.form.ComboBox({
			emptyText : "请选择",name : 'linkDirection',fieldLabel : "链路方向",
			typeAhead: true,triggerAction: 'all',lazyRender:true,
			editable : false,width : 160,value : Ext.isEmpty(lineParams.linkDirection)?'1':lineParams.linkDirection+"",
			mode: 'local',labelWidth : 80,labelAlign : 'right',readOnly : lineParams.isReadOnly,
			store: new Ext.data.ArrayStore({
				fields: ['myId','displayText'],
				data: [['1','单向'],['2','双向']]
			}),
			valueField: 'myId',displayField: 'displayText'
		});

		var portComboSql = "com.compses.dao.sqlmap.NodeAttrMapper.loadAddPortCombo";
		if(!Ext.isEmpty(lineParams.linkSymbolId)){
			portComboSql = 'com.compses.dao.sqlmap.NodeAttrMapper.loadEditPortCombo';
		}
		
		var filedSet = new Ext.form.FieldSet({
			title : '',
			border : false,
			layout : 'column',
			height : 240,
			items : [{
				columnWidth : 1,
				layout : 'form',
				border : false,
				items : [{
					xtype : 'textfield',fieldLabel:'源节点名称',
					labelWidth : 80,labelAlign : 'right',
					value:lineParams.srcSymbolName,readOnly : true
				},{
					xtype : 'textfield',fieldLabel:'宿节点名称',
					labelWidth : 80,labelAlign : 'right',
					value:lineParams.destSymbolName,readOnly : true
				},{
					xtype : 'textfield',name :'friendlyName',fieldLabel:'链路友好名称',
					labelWidth : 80,labelAlign : 'right',
					value:lineParams.friendlyName,readOnly : lineParams.isReadOnly
				},{
					xtype : 'ajaxComboBox',
//					trigger1Cls: 'x-form-clear-trigger',
					trigger2Cls: 'x-form-search-trigger',
					fieldLabel : '源端端口名称',
					labelWidth : 80,labelAlign : 'right',allowBlank : false,
					displayField : 'attrValue',
					queryMode : 'remote',
					displaySet : 'nodeTypeAttrId',
					name : 'srcPortId',
					valueField : 'nodeId',
					value:lineParams.srcPortId,
					readOnly : lineParams.isReadOnly,
					typeAhead: true,
					editable : false,
				 	triggerAction: 'all',
					sqlKey : portComboSql,
					plugins : [Ext.create("component.public.ComboSetStylePlugin")],
					fields : ['nodeId', 'attrValue','nodeTypeAttrId'],
					tpl: Ext.create('Ext.XTemplate',
				        '<tpl for=".">',
					        '<tpl if="nodeTypeAttrId == 2">',
						        '<div class="x-boundlist-item" style="color:red">{attrValue}</div>',
				            '</tpl>',
				            '<tpl if="nodeTypeAttrId != 2">',
			            		'<div class="x-boundlist-item" style="color:green">{attrValue}</div>',
				            '</tpl>',
				        '</tpl>'
				    ),
					paramMap :{
						nodeId : Ext.isEmpty(lineParams.srcNodeId)?0:lineParams.srcNodeId
					},
					listeners :{
						change : function(combo,newVal,val){
							var length = combo.store.getRange().length;
							if(length>0){
								for(var i=0;i<length;i++){
									var data=combo.store.getRange()[i].data;
									if(data[combo.valueField]==newVal){
										combo.setValue(combo.store.getRange()[i].data[combo.valueField]);
										var comboName = combo.store.getRange()[i].data[combo.displayField];
										if(data[combo.displaySet]==2){
											combo.setFieldStyle('color:red;');
										}else{
											combo.setFieldStyle('color:green');
										}
									}
								}
							}
						}
					},
					onTrigger2Click : function(){
			            var val = me.phyLinkForm.getForm().findField('srcPortId').getValue();
			            var name = me.phyLinkForm.getForm().findField('srcPortId').getRawValue();
			            if((name.indexOf("VLAN")>=0||name.indexOf("Vlan")>=0)&&name.indexOf("interface")==-1){
			            	var nameArr = name.split("(");
			            	var vlanArr = nameArr[0];
			            	var vlanIndex = name.indexOf("VLAN")>=0?name.indexOf("VLAN"):name.indexOf("Vlan");
			            	var vlanId = vlanArr.substr(vlanIndex+4,vlanArr.length);
			            	var params ={};
			            	params.portType =1;
			            	params.nodeId =lineParams.srcNodeId;
			            	params.portId =val;
			            	params.vlanId =vlanId;
			            	me.createVlanWin(params);
						}else{
							return Ext.Msg.alert("提示","请选择VLAN端口");
						}
					}
				},{
					xtype : 'ajaxComboBox',
					fieldLabel : '宿端端口名称',
					trigger2Cls: 'x-form-search-trigger',
					labelWidth : 80,labelAlign : 'right',allowBlank : false,
					displayField : 'attrValue',
					queryMode : 'remote',
					displaySet : 'nodeTypeAttrId',
					name : 'desPortId',
					valueField : 'nodeId',
					value:lineParams.desPortId,
					readOnly : lineParams.isReadOnly,
					typeAhead: true,
					editable : false,
				 	triggerAction: 'all',
					sqlKey : portComboSql,
					plugins : [Ext.create("component.public.ComboSetStylePlugin")],
					fields : ['nodeId', 'attrValue','nodeTypeAttrId'],
					tpl: Ext.create('Ext.XTemplate',
				        '<tpl for=".">',
					        '<tpl if="nodeTypeAttrId == 2">',
						        '<div class="x-boundlist-item" style="color:red">{attrValue}</div>',
				            '</tpl>',
				            '<tpl if="nodeTypeAttrId != 2">',
				            	'<div class="x-boundlist-item" style="color:green">{attrValue}</div>',
				            '</tpl>',
				        '</tpl>'
				    ),
					paramMap :{
						nodeId : Ext.isEmpty(lineParams.desNodeId)?0:lineParams.desNodeId
					},
					listeners :{
						change : function(combo,newVal,val){
							var length = combo.store.getRange().length;
							if(length>0){
								for(var i=0;i<length;i++){
									var data=combo.store.getRange()[i].data;
									if(data[combo.valueField]==newVal){
										combo.setValue(combo.store.getRange()[i].data[combo.valueField]);
										var comboName = combo.store.getRange()[i].data[combo.displayField];
										if(data[combo.displaySet]==2){
											combo.setFieldStyle('color:red;');
										}else{
											combo.setFieldStyle('color:green');
										}
									}
								}
							}
						}
					},
					onTrigger2Click : function(){
			            var val = me.phyLinkForm.getForm().findField('desPortId').getValue();
			            var name = me.phyLinkForm.getForm().findField('desPortId').getRawValue();

			            if((name.indexOf("VLAN")>=0||name.indexOf("Vlan")>=0)&&name.indexOf("interface")==-1){
			            	var nameArr = name.split("(");
			            	var vlanArr = nameArr[0];
			            	var vlanIndex = name.indexOf("VLAN")>=0?name.indexOf("VLAN"):name.indexOf("Vlan");
			            	var vlanId = vlanArr.substr(vlanIndex+4,vlanArr.length);
			            	var params ={};
			            	params.portType =2;
			            	params.nodeId =lineParams.desNodeId;
			            	params.portId =val;
			            	params.vlanId =vlanId;
			            	me.createVlanWin(params);
						}else{
							return Ext.Msg.alert("提示","请选择VLAN端口");
						}
					}
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				border : false,
				items : [{
					xtype : 'textfield',name : 'lineRate',fieldLabel : '链路带宽',
					labelWidth : 80,labelAlign : 'right',
					value:lineParams.lineRate,readOnly : lineParams.isReadOnly
				},directionCombo]
			},{
				columnWidth : 1,
				layout : 'form',
				border : false,
				items :[{
					xtype : 'textarea',name:'linkRemark',fieldLabel:'描述信息',
					labelWidth : 80,height : 40,labelAlign : 'right',
					value:lineParams.linkRemark,readOnly : lineParams.isReadOnly
				}]
			}]
		});
		
		me.phyLinkForm = new Ext.form.FormPanel({
			region : 'center',
			labelAlign : 'right',
			border : false,
			frame : false,
			deferredRender : false,
			style : 'padding:0 0 0 0',
			activeTab : 0,
			autoHeight : true,
			defaults : {
				autoScroll : false
			},
			layoutOnTabChange : true,
			items : [filedSet]
		});
	},
	createVlanWin : function(params){
		var me = this;
		Ext.Ajax.request({
			url: webRoot + 'vlanInfo/findOnusOfOltPage.do',
			method : 'POST',
			params : params,
			success : function(resp, action) {
				if(!Ext.isEmpty(resp)){
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(!Ext.isEmpty(respText)&&respText.length>0){
						var vlanPorts = respText[0].vlanPorts;
						var vlanArr = vlanPorts.split("(");
						var portIndexs ="";
						for (var i=1;i<vlanPorts.length;i++){
							var vlanVal = vlanArr[i];
							if(!Ext.isEmpty(vlanVal)){
								var indexArr = vlanVal.split(")");
								portIndexs = portIndexs+indexArr[0]+",";
							}
						}
						portIndexs=portIndexs.substr(0,portIndexs.length-1);
						
						var nodeAttr ={};
						nodeAttr.parentNodeId = params.nodeId;
						nodeAttr.portIndex = portIndexs;
						nodeAttr.portType = params.portType;
						Ext.create('component.topo.TopoLinkVlanWin',{
							title: 'VLAN树',
							iconCls : 'win-link',
							resizable : true,
							width : 260,
							height : 300,
							lineParams : nodeAttr,
							modal : true,
							callback : function(port){
								if(port.portType==1){
									var desPortName = me.phyLinkForm.getForm().findField('srcPortId').getRawValue();
									var desportArr = desPortName.split("(");
									var desIpArr = desportArr[1].split(")");
									
									var portName = port.portName +"("+desIpArr[0]+")";
									me.srcPortId = port.portId;
									me.srcIp = desIpArr[0];
									me.phyLinkForm.getForm().findField('srcPortId').setValue(port.portId);
									me.phyLinkForm.getForm().findField('srcPortId').setRawValue(portName);
								}else{
									var desPortName = me.phyLinkForm.getForm().findField('desPortId').getRawValue();
									var desportArr = desPortName.split("(");
									var desIpArr = desportArr[1].split(")");
									
									var portName = port.portName +"("+desIpArr[0]+")";
									me.desPortId = port.portId;
									me.desIp = desIpArr[0];
									me.phyLinkForm.getForm().findField('desPortId').setValue(port.portId);
									me.phyLinkForm.getForm().findField('desPortId').setRawValue(portName);
								}
							}
						}).show();
					}
				}
			},
			failure: function(){
				
			}
		});
	},
	saveLinkData : function(){
		var me = this;
		
		var lineData = me.lineForm.getForm().getValues();

		if(!me.lineForm.form.isValid()){
			Ext.Msg.alert("提示","请填写完整您的链路属性信息!");
			return;
		}
		
		Ext.Msg.confirm("提示", "确定保存链路信息?", function(btn) {
			if(btn=='yes'){
				if(me.lineStyle==1){
					var actionUrl;
					if(Ext.isEmpty(lineData.linkSymbolId)){
						actionUrl = webRoot+'/linkSymbol/addSymbolLink.do';
						lineData.sNodeId = me.lineParams.sNodeId;
						lineData.dNodeId = me.lineParams.dNodeId;
					}else{
						actionUrl = webRoot+'/linkSymbol/editSymbolLink.do';
					}
					
					Ext.Ajax.request({
						url: actionUrl,
						method : 'POST',
						params : lineData,
						success : function(resp, action) {
							var mapPanel = document.getElementById('frame_main');
							if(Ext.isEmpty(lineData.linkSymbolId)){
								var respText = Ext.JSON.decode(resp.responseText);
								mapPanel.contentWindow.drawMapLine(respText[0]);
							}else{
								mapPanel.contentWindow.reDrawLink(lineData);
							}
							me.close();
						},
						failure: function(){
						}
					});
				}else{
					if(!me.phyLinkForm.form.isValid()){
						Ext.Msg.alert("提示","请填写完整您的物理链路信息!");
						return; 
					}
					var phyLinkData = me.phyLinkForm.getForm().getValues();

					if(!Ext.isEmpty(me.srcPortId)){
						if(phyLinkData.srcPortId!=me.srcPortId){
							phyLinkData.srcPortId = me.srcPortId;
							phyLinkData.srcIp = me.srcIp;
						}
					}
					if(!Ext.isEmpty(me.desPortId)){
						if(phyLinkData.desPortId!=me.desPortId){
							phyLinkData.desPortId = me.desPortId;
							phyLinkData.desIp = me.desIp;
						}
					}

					var sf = me.query('ajaxComboBox[name=srcPortId]')[0].fieldStyle;
					var df = me.query('ajaxComboBox[name=desPortId]')[0].fieldStyle;
					if(sf.indexOf("red")>=0||df.indexOf("red")>=0){
						lineData.color='red';
					}else{
						lineData.color='yellow';
					}
					var topoLinkData = extend({}, [lineData,phyLinkData]);
					var actionUrl;
					if(Ext.isEmpty(lineData.linkSymbolId)){
						actionUrl = webRoot+'/linkSymbol/addPhyLink.do';
						topoLinkData.sNodeId = me.lineParams.sNodeId;
						topoLinkData.dNodeId = me.lineParams.dNodeId;
					}else{
						topoLinkData.linkId = me.lineParams.linkId;
						actionUrl = webRoot+'/linkSymbol/editPhyLink.do';
					}
		
					Ext.Ajax.request({
						url: actionUrl,
						method : 'POST',
						params : topoLinkData,
						success : function(resp, action) {
							var mapPanel = document.getElementById('frame_main');
							if(Ext.isEmpty(lineData.linkSymbolId)){
								var respText = Ext.JSON.decode(resp.responseText);
								mapPanel.contentWindow.drawMapLine(respText[0]);
							}else{
								mapPanel.contentWindow.reDrawLink(lineData);
							}
							
							me.close();
						},
						failure: function(){
						}
					});
				}
			}
		});
	},
	cancelLink : function(){
		var me = this;
		var mapPanel = document.getElementById('frame_main');
		mapPanel.contentWindow.isLineEnd=true;
		mapPanel.contentWindow.isDraw=false;
		me.close();
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'saveLinkData':
				me.saveLinkData();
				break;
			case 'cancelLink':
				me.cancelLink();
				break;
		}
	}
});

Ext.define('component.topo.TopoLinkVlanWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoLinkVlanWin',
	requires: [
	],
	constructor:function(condition){
		var me = this;
		me.lineParams = condition.lineParams;
		me.createVlanWin();
		
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[me.vlanTreePanel],
			buttons : [ {
				xtype : 'button',
				text : '添加',
				scope : this,
				handler:me.buttonEvent.createDelegate(me,['portSync'],0)
			}, {
				xtype : 'button',
				text : '取消',
				scope : this,
				handler : function() {
					me.hide();
				}
			} ]
		});
		
		me.callParent([config]);
	},
	createVlanWin : function(){
		var me = this;
		var params = {};
		if(!Ext.isEmpty(me.lineParams.portIndex)){
			params.portIndex=me.lineParams.portIndex;
			params.listField='portIndex';
		}
		
		me.vlanTreePanel = Ext.create('component.public.TreeCommonPanel', {
			region : 'center',
			border : false,
			autoExpand : true,                	//是否全展开
			useArrows : true,
			checkable : false,
			readOnly : true,
			singleSelect : false,               //单选(Boolean) (级别比cascadeCheck高)
			onlyLeafCheckable : false,         	//是否只有叶子节点可选(Boolean)
			barAllCheck : false,				//全选按钮(Boolean)
			rootVisible : true,					//根节点可见
			rootId : me.lineParams.parentNodeId,//根节点值
			rootText : 'Vlan树',					//根节点text
			sqlKey : 'com.compses.dao.sqlmap.NodeAttrMapper.loadVlanTreeList',  //获取数据的SQL名称(String)
			idKey : 'parentNodeId',	     		//树节点查询子节点值关联KEY
			nodeId : 'NODE_ID',					//节点id KEY
			nodeName : 'PORT_NAME',				//节点text KEY
			paramMap :params
//			{
//				portIndex : me.lineParams.portIndex,
//				listField:'portIndex'
//			}
		});

		me.vlanTreePanel.on('itemclick', function(view, node, item, index,e,eOpts){
			var attributes = Ext.JSON.decode(node.raw.attributes);
			me.nodeId = node.data.id;
			me.portName = attributes.PORT_INDEX+"--"+node.data.text;
			if(!Ext.isEmpty(attributes.PORT_ID)){
				me.portName + me.portName +"("+attributes.PORT_ID+")";
			}
		});
		me.vlanTreePanel.on('itemdblclick', function(view, node, item, index,e,eOpts){
			var attributes = Ext.JSON.decode(node.raw.attributes);
			me.nodeId = node.data.id;
			me.portName = attributes.PORT_INDEX+"--"+node.data.text;
			if(!Ext.isEmpty(attributes.PORT_ID)){
				me.portName + me.portName +"("+attributes.PORT_ID+")";
			}
			me.portSync();
		});
	},
	portSync : function(){
		var me = this;
		var port = {};
		port.portId = me.nodeId;
		port.portName = me.portName;
		port.portType = me.lineParams.portType;
		me.callback.call(me,port);
		me.close();
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'portSync':
				me.portSync();
				break;
		}
	}
});