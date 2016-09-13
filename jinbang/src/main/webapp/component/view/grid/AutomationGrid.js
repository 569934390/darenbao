Ext.define('component.view.grid.AutomationGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.automationGrid',
	requires:[],
	config:{
	},
	viewConfig:{
		enableTextSelection:true
	},
	name:'messageGrid',
	selModel:Ext.create('Ext.selection.CheckboxModel',{ checkOnly :true }),
	constructor : function(config) {
		var me = this;
		config=config||{};
		var store = Ext.create('Ext.data.Store', {
		     fields:['simulatorName','version','dccName','dccId','content','system','msgType','sendContent','avps'],
		     proxy: {
		         type: 'ajax',
		         url: ctx+'/simulator/selectAutomationList.do',
		         reader: {
		             type: 'json',
		            root: 'result',
	        		totalProperty: 'totalCount'
		         },
		         extraParams:{conditionsStr:Ext.encode({staffId:session.user.id,version:-1}),type:config.gtype||'version'}
		     },
		     pageSize:50,
		     autoLoad: true
		 });
		 var plugin=Ext.create('component.public.GridToolTipPlugin',{
		 	toolTipRenderer:function(grid,record,value,label,cellIndex,cellElement,dataIndex){
		 		if(cellIndex==0||cellIndex==9){
		 			return value;
		 		}else if(cellIndex==11){
		 			return me.retrunCodeDescRender(value);
		 		}
		 		return false;
		 	}
		 });
		me.store = store;
		me.plugins = plugin;
		me.columns = [
			{ text: '版本号',  dataIndex: 'version' ,width:230},
	        { text: '业务', dataIndex: 'system' ,width:100},
	        { text: '场景', dataIndex: 'simulatorName' ,width:100},
	        { text: '包名',  dataIndex: 'dccName' ,width:100},
	        { text: '模板', dataIndex: 'content' ,width:35,renderer:function(){
	        	return '<div  style="height:14px;cursor:pointer;background:url('+webRoot+'common/images/16x16/look.png) no-repeat;" ></div>';
	        }},
	        { text: '返回包', dataIndex: 'sendContent' ,width:45,renderer:function(){
	        	return '<div  style="height:14px;cursor:pointer;background:url('+webRoot+'common/images/16x16/look.png) no-repeat;" ></div>';
	        }},
	        { text: '配置请求avp', dataIndex: 'avps' ,flex:1}];
	    if(config.columns){me.columns = config.columns}
    	me.dockedItems = [{
	        xtype: 'pagingtoolbar',
	        store: store,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }];
		me.callParent([config]);
		me.on('cellclick',function( grid, td, cellIndex, record, tr, rowIndex, e, eOpts ){
    		if(cellIndex==5||cellIndex==6){
    			var jsonstr = record.get('content');
    			if(cellIndex==5)jsonstr = record.get('sendContent');
	    		var viewer = Ext.create('component.dcc.view.MessageDetailEditor',{title:'',border:false,hideFlag:'msg',url:ctx+'/simulator/convertConfig.do'});
				viewer.getStore().getProxy().extraParams.jsonstr=jsonstr;
				viewer.getStore().load();
				Ext.create('Ext.window.Window',{
					title:'消息包详情',
					layout:'border',
					closeAction:'destroy',
					width:Ext.getBody().getWidth()-50,
					height:Ext.getBody().getHeight()-50,
					items:[{
						region:'center',
						xtype:'tabpanel',
						items:[{
							xtpe:'panel',
							title:'格式化内容',
							layout:'fit',
							items:viewer
						},{
							xtpe:'panel',
							title:'XML内容',
							layout:'fit',
							items:[{
								xtype:'box',
								html:'safas',
								listeners:{
									afterrender:function(box){
										Ext.Ajax.request({
											url:ctx+'/simulator/convertConfigXML.do',
											params:{jsonstr:jsonstr},
											success:function(response){
												if(!Ext.isEmpty(response.responseText)){
													var result = Ext.decode(response.responseText).xml;
													result = result.replace(/\\n/g,'\n');
													box.update('<textarea style="width:100%;height:100%;">'+result+'</textarea>');
													var editor = CodeMirror.fromTextArea(box.getEl().dom.childNodes[0], {
												        mode: "text/html",
												        lineNumbers: true
												    });
												}
											},
											failure:function(){
												return Ext.Msg.alert('提示','操作失败');
											}
										});
									}
								}
							}]
						}]
					}]
				}).show();
    		}
		});
	}
});