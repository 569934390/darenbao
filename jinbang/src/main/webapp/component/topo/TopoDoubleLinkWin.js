/**
 * 拓扑双链路
 */
Ext.define('component.topo.TopoDoubleLinkWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoDoubleLinkWin',
	requires: [
        'component.public.AjaxComboBox'
	],
	constructor:function(condition){
		var me = this;
		me.lineParams = condition.lineParams;
		me.createLinkTabPanel();
		
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[me.linkTabPanel]
		});
		
		me.callParent([config]);
	},
	createLinkTabPanel : function(){
		var me = this;
		var lineParams = me.lineParams
		me.createLinkFormPanel();
		me.createPhyLinkGrid();
		
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
				layout :'border',
				items : [me.topoPhyLineGrid]
			}],
			buttonAlgin:'center',
			buttons : [{
				text : '删除',
				hidden : Ext.isEmpty(lineParams.isReadOnly)?false:lineParams.isReadOnly,
						handler : me.buttonEvent.createDelegate(me,['delLinkData'],0)
			},{
				text : '保存',
				hidden : Ext.isEmpty(lineParams.isReadOnly)?false:lineParams.isReadOnly,
				handler : me.buttonEvent.createDelegate(me,['saveLinkData'],0)
			},{
				text : '取消',
				handler : me.buttonEvent.createDelegate(me,['cancelLink'],0)
			}]
		});
	},
	createLinkFormPanel : function (){
		var me = this;
		var lineParams = me.lineParams
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
	createPhyLinkGrid : function(){
		var me = this;
		var lineParams = me.lineParams;
		var directionCombo = new Ext.form.ComboBox({
			emptyText : "请选择",name : 'direction',editable : false,
			store: new Ext.data.ArrayStore({
				fields: ['dirdction','dirdctionName'],
				data: [['0','无向'],['1','单向'],['2','双向']]
			}),
			valueField: 'dirdction',displayField: 'dirdctionName'
		});
		var widthCombo = new Ext.form.ComboBox({
			emptyText : "请选择",name : 'width',editable : false,
		    store: new Ext.data.ArrayStore({
		        fields: ['width','widthName'],
		        data: [['3','3'],['4','4'],['6','6'],['8','8'],['10','10'],['12','12'],['15','15']]
			}),
			valueField: 'width',displayField: 'widthName'
		});
		me.srcPortCombo = Ext.create('component.public.AjaxComboBox',{
			displayField : 'attrValue',
			name : 'srcPortId',
			valueField : 'nodeId',
			value:lineParams.srcPortId,
			readOnly : lineParams.isReadOnly,
			editable : false,
		 	triggerAction: 'all',
			sqlKey : 'com.compses.dao.sqlmap.NodeAttrMapper.loadEditPortCombo',
			plugins : [Ext.create("component.public.ComboSelectFirstPlugin")],
			fields : ['nodeId', 'attrValue'],
			paramMap :{
				nodeId : Ext.isEmpty(lineParams.srcNodeId)?0:lineParams.srcNodeId
			}
		});
		me.desPortCombo = Ext.create('component.public.AjaxComboBox',{
			displayField : 'attrValue',
			queryMode : 'remote',
			name : 'desPortId',
			valueField : 'nodeId',
			value:lineParams.desPortId,
		 	triggerAction: 'all',
			sqlKey : 'com.compses.dao.sqlmap.NodeAttrMapper.loadEditPortCombo',
			plugins : [Ext.create("component.public.ComboSelectFirstPlugin")],
			paramMap :{
				nodeId : Ext.isEmpty(lineParams.desNodeId)?0:lineParams.desNodeId
			}
		});
		
		var store = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot + '/linkSymbol/findPhyLinkPage.do',
		        reader: {
		            type: 'json',
		            root: 'result',
		            totalProperty : 'totalCount'
		        }
		    },
		    pageSize: 20,
		    fields: ['srcSymbolName','desSymbolName','linkDirection','lineRate','friendlyName','srcPortId','desPortId','linkRemark']
		});
//		store.load({params:{start:0, limit:20,linkId : me.lineParams.linkSymbolId}});
		store.load({params:{start:0, limit:20,srcNodeId : me.lineParams.srcNodeId,desNodeId:me.lineParams.desNodeId}});

		me.topoPhyLineGrid =Ext.create('Ext.grid.Panel', {
			region : 'center',
			width : '100%',
		    store: store,
		    border : false,
			multiSelect: true,
			plugins: new Ext.grid.plugin.CellEditing({
		        clicksToEdit: 1
		    }),
		    selModel: {
		    	selType:'checkboxmodel'
			},
		    columns: [{
		        	text: '源端端口名称', dataIndex: 'srcSymbolName'
		    	},{
		    		text: '宿端端口名称', dataIndex: 'desSymbolName'
//		    	},{
//		    		text: '链路方向', dataIndex: 'linkDirection',
//		    		editor:directionCombo,renderer:function(val){
//		        		var length=directionCombo.getStore().getCount(),i=0;
//		        		for(;i<length;i++){
//		        			if(directionCombo.getStore().getAt(i).get(directionCombo.valueField)==val)
//		        				return directionCombo.getStore().getAt(i).get(directionCombo.displayField);
//		        		}
//		        		return val;
//		        	}
//		        },{
//		        	text: '带宽', dataIndex: 'lineRate',
//		        	editor:widthCombo,renderer:function(val){
//		        		var length=widthCombo.getStore().getCount(),i=0;
//		        		for(;i<length;i++){
//		        			if(widthCombo.getStore().getAt(i).get(widthCombo.valueField)==val)
//		        				return widthCombo.getStore().getAt(i).get(widthCombo.displayField);
//		        		}
//		        		return val;
//		        	}
//		        },{
//		        	text: '友好名称', dataIndex: 'friendlyName',
//		            editor: {
//		                allowBlank: false,
//		                maxLength : 50
//		            }
		        },{
		        	text: '原端端口名称', dataIndex: 'srcPortId',
		        	editor :  {
		                allowBlank: false
		            },
		        	renderer:function(val){
		        		var length=me.srcPortCombo.getStore().getCount();
		        		for(var i=0;i<length;i++){
		        			if(me.srcPortCombo.getStore().getAt(i).get(me.srcPortCombo.valueField)==val)
		        				return me.srcPortCombo.getStore().getAt(i).get(me.srcPortCombo.displayField);
		        		}
		        		return val;
		        	}
		        },{
		        	text: '宿端端口名称', dataIndex: 'desPortId',
		        	editor :  {
		                allowBlank: false
		            },
		        	renderer:function(val){
		        		var length=me.desPortCombo.getStore().getCount();
		        		for(var i=0;i<length;i++){
		        			if(me.desPortCombo.getStore().getAt(i).get(me.desPortCombo.valueField)==val)
		        				return me.desPortCombo.getStore().getAt(i).get(me.desPortCombo.displayField);
		        		}
		        		return val;
		        	}
		        },{text: '链路备注', dataIndex: 'linkRemark',
		            editor: {
		                allowBlank: false,
		                maxLength : 50
		            }
		        }
		    ],
		    forceFit : true
//			bbar : new Ext.PagingToolbar({
//	            pageSize: 20,
//				displayMsg : 'Displaying{0} - {1} of {2}',
//	            store: store,
//	            displayInfo: true
//	        })
		});
		me.topoPhyLineGrid.on('beforeedit',function(editor,context,eOp){
			if(context.colIdx==3){
				var raw = context.record.raw;
				context.column.setEditor({
					xtype : 'ajaxComboBox',
					displayField : 'attrValue',
					name : 'srcPortId',
					valueField : 'nodeId',
					value:raw.srcPortId,
					displaySet : 'nodeTypeAttrId',
					readOnly : lineParams.isReadOnly,
					editable : false,
				 	triggerAction: 'all',
					sqlKey : 'com.compses.dao.sqlmap.NodeAttrMapper.loadEditPortCombo',
					plugins : [Ext.create("component.public.ComboSelectFirstPlugin")],
					fields : ['nodeId', 'attrValue','nodeTypeAttrId'],
					paramMap :{
						nodeId : raw.srcNodeId
					},
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
					listeners :{
						change : function(combo,newVal,val){
							var length = combo.store.getRange().length;
							if(length>0){
								for(var i=0;i<length;i++){
									var data=combo.store.getRange()[i].data;
									if(data[combo.valueField]==newVal){
										combo.setValue(combo.store.getRange()[i].data[combo.valueField]);
										if(data[combo.displaySet]==2){
											combo.setFieldStyle('color:red;');//background:#87CEEB
										}else{
											combo.setFieldStyle('color:green');//background:#87CEEB
										}
									}
								}
							}
						}
					}
				});
			}
			if(context.colIdx==4){
				var raw = context.record.raw;
				context.column.setEditor({
					xtype : 'ajaxComboBox',
					displayField : 'attrValue',
					name : 'srcPortId',
					valueField : 'nodeId',
					value:raw.desPortId,
					displaySet : 'nodeTypeAttrId',
					readOnly : lineParams.isReadOnly,
					editable : false,
					triggerAction: 'all',
					sqlKey : 'com.compses.dao.sqlmap.NodeAttrMapper.loadEditPortCombo',
					plugins : [Ext.create("component.public.ComboSelectFirstPlugin")],
					fields : ['nodeId', 'attrValue','nodeTypeAttrId'],
					paramMap :{
						nodeId : raw.desNodeId
					},
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
					listeners :{
						change : function(combo,newVal,val){
							var length = combo.store.getRange().length;
							if(length>0){
								for(var i=0;i<length;i++){
									var data=combo.store.getRange()[i].data;
									if(data[combo.valueField]==newVal){
										combo.setValue(combo.store.getRange()[i].data[combo.valueField]);
										if(data[combo.displaySet]==2){
											combo.setFieldStyle('color:red;');//background:#87CEEB
										}else{
											combo.setFieldStyle('color:green');//background:#87CEEB
										}
									}
								}
							}
						}
					}
				});
			}
		});
	},
	delLinkData : function(){
		var me = this;
		var records = me.topoPhyLineGrid.getSelectionModel().getSelection();
		if(records.length<1) {
			Ext.Msg.alert('提示',"请选择记录");
			return;
		}
		for(var i = 0;i<records.length;i++) {
			me.topoPhyLineGrid.getStore().remove(records[i]);
			if(Ext.isEmpty(me.delIds)){
				me.delIds = records[i].raw.linkId;
			}else{
				me.delIds = me.delIds+","+records[i].raw.linkId;
			}
		}
	},
	saveLinkData : function(){
		var me = this;
		
		var linkData = me.lineForm.getForm().getValues();

		if(!me.lineForm.form.isValid()){
			Ext.Msg.alert("提示","请填写完整您的链路属性信息!");
			return;
		}
		var dataLength = me.topoPhyLineGrid.getStore().data.length;
		var phyList = [];
		for(var i=0;i<dataLength;i++){
			var data = me.topoPhyLineGrid.getStore().getAt(i).data;	//data就是对应record的一个一个的对象
			
			if(Ext.isEmpty(data.srcPortId)||Ext.isEmpty(data.desPortId)){
				Ext.Msg.alert("提示","请选择设备类型!");
				return;
			}
			delete data.srcSymbolName;
			delete data.desSymbolName;
			data.linkId = me.topoPhyLineGrid.getStore().getAt(i).raw.linkId;
			phyList[i]=data;
		}
		
		linkData.color='yellow';
		Ext.Ajax.request({
			url: webRoot+'/linkSymbol/editDoublePhyLink.do',
			method : 'POST',
			params : {
				linkData:Ext.JSON.encode(linkData),
				phyList:Ext.JSON.encode(phyList),
				delIds : me.delIds
			},
			success : function(resp, action) {
				var mapPanel = document.getElementById('frame_main');
				if(Ext.isEmpty(me.delIds)){
					mapPanel.contentWindow.reDrawLink(linkData);
				}else{
//					var respText = Ext.JSON.decode(resp.responseText);
//					mapPanel.contentWindow.drawMapLine(respText[0]);
					mapPanel.contentWindow.reLoadMapLayer(false,true);
				}
				
				me.close();
			},
			failure: function(){
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
			case 'delLinkData':
				me.delLinkData();
				break;
			case 'saveLinkData':
				me.saveLinkData();
				break;
			case 'cancelLink':
				me.cancelLink();
				break;
		}
	}
});