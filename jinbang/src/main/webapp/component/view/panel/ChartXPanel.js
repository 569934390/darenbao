Ext.require(['component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.define('component.view.panel.ChartXPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.chartXPanel',
	requires:[],
	config:{
	},
	constructor : function(config) {
		var me = this;
		config=config||{};
		Ext.applyIf(config,{
			border:false,
			layout:'border',
			items:[me.createConfigPanel(config.valStr)]
		});
		me.callParent([config]);
	},
	createConfigPanel:function(valStr){
		var me = this,configPanel;
		configPanel = Ext.create('component.view.form.FormPanel', {
    	    region:'north',border:false,frame:true,
    	    name:'searchForm',
    	    items:[
    	    	{xtype:'hidden',name:'detailId',value:''},
				{xtype:'hidden',name:'chartId',value:''},
				{xtype:'hidden',name:'sqlData',value:ChartCfg.xs},
				{xtype:'hidden',name:'xName',value:''},
				{xtype:'hidden',name:'type',value:'x'},
				{xtype:'hidden',name:'yName',value:''},{
				xtype:'box',
				width:Ext.getBody().getWidth()/2-50,
				height:Ext.getBody().getHeight()-70,
				style:'margin:10px',
				html:'',
				listeners:{
					afterrender:function(box){
						box.update('<textarea style="width:100%;height:100%;">'+valStr+'</textarea>');
						me.editor = CodeMirror.fromTextArea(box.getEl().dom.childNodes[0], {
							mode: 'text/javascript',
					        lineNumbers: true
					    });
					    me.editor.on('change',function(editor,option){
					    	me.configPanel.getForm().findField('sqlData').setValue(editor.getValue());
					    });
					}
				}
			}]
    	});
		me.configPanel = configPanel;
		return configPanel;
	}
});