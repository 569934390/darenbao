/**
 * 物理链路列表
 */
Ext.define('component.resource.LinkInfoPanel',{
	extend : 'Ext.grid.Panel',
	alias : 'widget.linkInfoPanel',
	requires:['component.public.AjaxComboBox'],
	config:{
		cableGrid:null,
		linkView:null,
		containerPanel:null
	},
	constructor:function(config){
		config=config||{};
		var me = this;
		me.store=me.createStore();
		Ext.applyIf(config,{
			title:'链路信息',
			store: me.store,
		    forceFit : true,
			multiSelect: true,
		    selModel: {
		    	selType:'checkboxmodel',
		    	listeners:{
		    		selectionchange:function(selModel,selected,eOpts){
		    			if(!Ext.isEmpty(selected)){
			    			me.linkOperate('GetLinkInfo');
		    			}else{
		    				me.updateLinkInfo(null);
		    			}
		    		}
		    	}
			},
		    columns: [{ 
		    	header: "链路ID",dataIndex: 'linkId',hidden:true,sortable: true
		    },{
		    	header: "链路名称",dataIndex: 'friendlyName', sortable: true
		    },{
		    	header: "源端网元",dataIndex: 'srcSymbolName', sortable: true
		    },{
		    	header: "源端IP",dataIndex: 'srcIpaddress', sortable: true
		    },{
		    	header: "源端端口索引",dataIndex: 'srcPortIndex', sortable: true
		    },{
		    	header: "源端MAC地址",dataIndex: 'srcMacValue', sortable: true
		    },{ 
		    	header: "宿端网元",dataIndex: 'desSymbolName', sortable: true
		    },{ 
		    	header: "宿端IP",dataIndex: 'desIpaddress', sortable: true
		    },{
		    	header: "宿端端口索引",dataIndex: 'desPortIndex', sortable: true
		    },{ 
		    	header: "宿端MAC地址",dataIndex: 'desMacValue', sortable: true
			}],
			dockedItems:[{
				xtype:'toolbar',
				dock:'top',
				items:[{
						text : '配置信息查询',
						iconCls : 'toolbar-view',
						handler : me.buttonEvent.createDelegate(me,[ 'linkInfo' ], 0)
					},'-',{
						text : '删除',
						iconCls : 'toolbar-view',
						handler : me.buttonEvent.createDelegate(me,[ 'linkDelete' ], 0)
					},'-',{
						text : '告警分析',
						iconCls : 'toolbar-view',
						handler : me.buttonEvent.createDelegate(me,[ 'linkWarnAnalyse' ], 0)
					},'-',{
						text : '告警确认',
						iconCls : 'toolbar-view',
						disabled:true,
						name:'linkWariningConfirm',
						handler : me.buttonEvent.createDelegate(me,[ 'linkWariningConfirm' ], 0)
					},'-',{
						text : '告警取消',
						disabled:true,
						iconCls : 'toolbar-view',
						name:'linkWariningCancel',
						handler : me.buttonEvent.createDelegate(me,[ 'linkWariningCancel' ], 0)
					}]
				},{
				xtype:'toolbar',
				dock:'top',
				items:[{
					xtype:'ajaxComboBox',
					fieldLabel:'链路过滤',
					name:'linkType',
					queryMode:'model',
					labelWidth:60,
					labelAlign : 'right',
					plugins:[Ext.create('component.public.ComboSelectFirstPlugin')],
					listeners:{
						change:function(combo,newValue,oldValue,eOpts){
							if(newValue=='0'){
								me.getStore().proxy.url=webRoot + '/linkSymbol/findPhyLinkPage.do';
								me.gridSearch();
							}else{
								me.getStore().proxy.url=webRoot + '/linkSymbol/findWarnPhyLinkPage.do';
								me.gridSearch();
							}
						}
					},
					data:[['所有链路','0'],['告警链路','1']]
				},{
						xtype : 'textfield',
						fieldLabel : '链路名称',
						name : 'linkName',
						labelWidth:60,
						labelAlign : 'right'
					},{
						text : '查询',
						iconCls : 'toolbar-search',
						handler : me.buttonEvent.createDelegate(me,[ 'gridSearch' ], 0)
					}]
				},{
				xtype:'pagingtoolbar',
				dock:'bottom',
	            pageSize: 20,
	            store: me.store,
	            displayInfo: true
			}],
			listeners:{
				itemclick:function(grid, record, item, index,e,eOpts){
					
				}
			},
			updateLinkInfo:function(outputXML){
				var linkView=this.getLinkView();
				linkView.tpl.overwrite(linkView.body,outputXML);
			},
			updateCableGridInfo:function(outputXML){
				var cableIDArray=outputXML.warnCableArray.cableID;
				var records=[];
				Ext.each(cableIDArray,function(record,index){
					console.info(record);
					records.push({cableID:record});
				});
				console.info(records);
				me.getCableGrid().getStore().loadRawData(records);
			},
//			tbar : new Ext.Toolbar({
//				items : [{
//						xtype : 'textfield',
//						fieldLabel : '链路名称',
//						name : 'linkName',
//						labelWidth : 60,
//						labelAlign : 'right',
//						width : 200
//					},{
//						xtype : 'tbfill'
//					},{
//						xtype : 'button',
//						text : '查询',
//						tooltip : '查询',
//						iconCls : 'toolbar-search',
//						handler : me.buttonEvent.createDelegate(me,[ 'gridSearch' ], 0)
//					} ]
//			}),
//			bbar : new Ext.PagingToolbar({
//	            pageSize: 20,
//	            store: me.store,
//	            displayInfo: true
//	        })
		});
		me.callParent([config]);
	},
	createStore : function(){
		return new Ext.data.JsonStore({
//			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: webRoot + '/linkSymbol/findPhyLinkPage.do',
		        reader: {
		            type: 'json',
		            root: 'result',
		            totalProperty : 'totalCount'
		        }
		    },
	        fields: [
	             'linkId','friendlyName','srcSymbolName', 'srcIpaddress', 'srcPortIndex','srcMacValue',
	             'desSymbolName','desIpaddress','desPortIndex','desMacValue'
	        ],
		    pageSize: 20,
	        listeners:{
				beforeload:function() {
//					me.store.proxy.url = webRoot+'/linkSymbol/findPhyLinkPage.do';//?result='+resultTypeShow;
				}
			}
		});
	},
	gridSearch : function(){
		var me = this;
		var linkName = me.down("[name=linkName]").getValue();
		var params = {};
		params.start=0;
		params.limit=20;
		params.page=1;
		if(!Ext.isEmpty(linkName)){
			params.friendlyName = "%"+linkName+"%";
		}
		me.getStore().loadPage(1);
		me.getStore().load({params:params});
	},
	linkOperate:function(operate){
		var me=this;
		var inputXML=new Object();
		if(operate=="GetLinkInfo"||operate=="DeleteLink"||operate=='LinkWarnAnalyse'){
			var records=Ext.gridSelectCheck(me,true);
			if(!records) return;
			var linkInfoArray=[];
			Ext.each(records,function(record,index){
				var linkInfo=new Object();
				linkInfo.deviceIDA=record.raw.srcNodeId;
				linkInfo.deviceTermA=record.raw.srcPortIndex;
				linkInfo.deviceIDB=record.raw.desNodeId;
				linkInfo.deviceTermB=record.raw.desPortIndex;
				linkInfo.linkOutID=record.raw.linkId;
				linkInfoArray.push(linkInfo);
			});
			inputXML.linkInfoArray=linkInfoArray;
		}
		if(operate=="DeviceWarining"||operate=="DeviceWariningCancel"){
			var records=Ext.gridSelectCheck(me.getCableGrid(),true);
			if(!records) return;
			var warningInfoArray=[];
			if(operate=="DeviceWarining"){
				Ext.each(records,function(record,index){
					var warningInfo=new Object();
					warningInfo.deviceID=record.get('cableID');
					warningInfo.warningType='Warning';
					warningInfoArray.push(warningInfo);
				});
			}else{
				Ext.each(records,function(record,index){
					var warningInfo=new Object();
					warningInfo.deviceID=record.get('cableID');
					warningInfo.warningType='NoWarning';
					warningInfoArray.push(warningInfo);
				});
				operate="DeviceWarining";
			}
			inputXML.warningInfoArray=warningInfoArray;
		}
		Ext.Ajax.request({
    		url : ctx+'/stationSync/getGpmsInfo.do',
    		method : 'POST',
    		params : {method:operate,inputXML:Ext.encode(inputXML)},
    		success : function(response, action) {
    			if(operate=="GetLinkInfo"){
    				me.getLinkView().show();
    				me.getCableGrid().hide();
    				me.down('[name="linkWariningConfirm"]').disable();
    				me.down('[name="linkWariningCancel"]').disable();
    				me.getContainerPanel().setTitle("链路信息配置");
    				me.updateLinkInfo(Ext.decode(response.responseText));
    			}
    			if(operate=="LinkWarnAnalyse"){
    				me.getLinkView().hide();
    				me.getCableGrid().show();
    				me.down('[name="linkWariningConfirm"]').enable();
    				me.down('[name="linkWariningCancel"]').enable();
    				me.getContainerPanel().setTitle("故障光缆列表");
    				me.updateCableGridInfo(Ext.decode(response.responseText));
    			}
    		},
    		failure : function(response,action) {
    			Ext.Msg.alert('提示','操作失败');
    		}
    	});
	
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'gridSearch':
				me.gridSearch();
				break;
			case 'linkInfo':
				me.linkOperate('GetLinkInfo');
				break;
			case 'linkDelete':
//				Ext.bind(Ext.showConfirmWindow,null,[me,me.linkOperate,common.constant.buttonOperate.remove,'DeleteLink'],0);
				Ext.showConfirmWindow(me,me.linkOperate,common.constant.buttonOperate.remove,'DeleteLink');
				break;
//				me.linkOperate('DeleteLink');
			case 'linkWarnAnalyse':
				if(me.down('[name="linkType"]').getValue()=="0"){
					Ext.Msg.alert('提示','请先查询有告警的链路!');
					break;
				}
				me.linkOperate('LinkWarnAnalyse');
				break;
			case 'linkWariningConfirm':
				me.linkOperate('DeviceWarining');
				break;
			case 'linkWariningCancel':
				me.linkOperate('DeviceWariningCancel');
				break;
		}
	}
});