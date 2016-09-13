Ext.define('component.agent.ComboPlugin', {
	extend : 'Ext.AbstractPlugin',
	alias : 'widget.agentComboPlugin',
	requires: ['component.public.FormPanel','component.public.CustomPanelComponent'],
	init : function(combo) {
		var me = this;
		me.combo = combo;
		combo.setValue=Ext.bind(me.setValue,me);
		me.initPanel(combo);
		combo.handler=Ext.bind(me.handler,me);
		me.callParent(arguments);
		combo.store = me.store;
		combo.getStore=Ext.bind(me.getStore,me);
	},
	handler:function(){
		this.win.show();
	},
	initPanel:function(combo){
		var me = this;
		var stateStore = Ext.create('Ext.data.Store', {
		    fields: ['value', 'text'],
		    data: [
		        {'value': '00A','text': '有效'},
		        {'value': '00X','text': '无效'}
		    ]
		});
		var panel = Ext.create('Ext.panel.Panel', {
		    width: 730,bodyStyle : 'background-color : #dfe8f6',border:false,
		    height:300,
		    layout:'border',
		    items: [{
		        xtype: 'customPanel',region:'center',border:false,
       	 		aliasName: 'AGENT_LIST',
       	 		pageSize: 20,
       	 		gridCfg:{
       	 			multiSelect: false,
		   	 		selModel: {
		   	 			selType:'rowmodel'
		   	 		}
       	 		},
       	 		order: [{
	   	 			key: 'NODE_ID',
	   	 			dir: 'desc'
	   	 		}],
       	 		searchComponents: [{
       	 			key: 'STATE',
       	 			store: stateStore
       	 		}],
       	 		displayFunctions: [{
       	 			key: 'STATE',
       	 			width:100,
       	 			fun: function (value){
	       	 			var store = stateStore;
       	 				for(var i=0;i<store.getCount();i++){
       	 					if(store.getAt(i).get('value')==value)
       	 						return store.getAt(i).get('text');
       	 				}
	       	 			return value;
	       	 		}
       	 		}],
       	 		init: function(panel, toolPanel, grid) {
       	 			grid.on('itemdblclick',Ext.bind(me.winSubmit,me));
       	 			var store = me.panel.query('grid')[0].getStore();
					store.load({
						callback:function(array){
							for(var i=0,length=array.length;i<length;i++){
								me.combo.setTextAndValue(array[i].get('NODE_NAME'),array[i].raw.NODE_ID);
							}
						}
					});
       	 		}
		    }]
		});
		this.panel = panel;
		this.store = stateStore;
		this.win = Ext.create('component.public.CustomGridEditorWindowComponent', {
		    title: '代理选择器',border:false,
		    width: 730,
		    height: 300,
		    layout: 'fit',
		    items: panel,
		    buttons:[{
		    	text:'确定',
		    	handler:Ext.bind(me.winSubmit,me)
		    },{
		    	text:'取消',
		    	handler:function(){
		    		me.win.hide();
		    	}
		    }]
		});
	},
	winSubmit:function(){
		var me = this;
		var record = this.panel.query('grid')[0].getSelectionModel().getSelection()[0];
		me.combo.setTextAndValue(record.get('NODE_NAME'),record.raw.NODE_ID);
		me.win.hide();
	},
	setValue:function(value){
		var me =this;
		var store = this.panel.query('grid')[0].getStore();
		store.load({
			callback:function(array){
				for(var i=0,length=array.length;i<length;i++){
					if(array[i].raw.NODE_ID==value){
						me.combo.setTextAndValue(array[i].get('NODE_NAME'),value);
						return value;
					}
				}
			}
		});
		return value;
	},
	getStore:function(){
		return this.store;
	}
});
