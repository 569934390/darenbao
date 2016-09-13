Ext.define('component.dcc.view.EditDccPackagePanel', {
	extend : 'Ext.tab.Panel',
	alias : 'widget.editDccPackagePanel',
	requires:['component.public.TextButtonField'],
	config:{
		dcc:null,
		packageGrid:null,
		comBo:null
	},
	constructor : function(config) {
		var me = this;
		config=config||{};
		me.xmlView=me.createXmlView(config.parentFlag);
		delete config.parentFlag;
		me.gridView=me.createGridView();
		Ext.applyIf(config, {
			layout:'border',
			items : [me.xmlView, me.gridView]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me=this;
		me.on('tabchange',Ext.bind(me.tabChangeHandler,me));
	},
	setDcc:function(dcc){
		var me=this;
		me.dcc = dcc;
		me.resetHandler();
	},
	resetHandler:function(){
		var me=this;
		me.editor.setValue(me.dcc.content||'');
	},
	tabChangeHandler:function(tabPanel, newCard, oldCard, eOpts){
		var me=this;
		if(newCard.itemId=='messageDetailEditor'){
			Ext.apply(me.gridView.getStore().proxy.extraParams,{content:me.editor.getValue()});
			me.gridView.getStore().load();
		}
	},
	createXmlView : function(parentFlag) {
		var me=this;
		var xmlView  = Ext.create('Ext.panel.Panel', {
			region:'center',
			title:'XML内容',
			layout:'fit',
			items:[{
				xtype:'box',
				html:'',
				listeners:{
					afterrender:function(box){
						result='';
						result = result.replace(/\\n/g,'\n');
						box.update('<textarea style="width:100%;height:100%;">'+result+'</textarea>');
						me.editor = CodeMirror.fromTextArea(box.getEl().dom.childNodes[0], {
					        mode: "text/html",
					        lineNumbers: true
					    });
					}
				}
			}]
		});
		return xmlView;
	},
	createGridView:function(){
		return Ext.create('component.dcc.view.MessageDetailEditor',{itemId:'messageDetailEditor',title:'消息包预览',region:'center'});
	}
});