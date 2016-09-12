Ext.require(['component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.define('component.view.panel.ChartDetailPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.chartDetailPanel',
	requires:[],
	config:{
	},
	constructor : function(config) {
		var me = this;
		config=config||{};
		Ext.applyIf(config,{
			border:false,
			layout:'border',
			items:[me.createConfigPanel(config)]
		});
		me.callParent([config]);
	},
	handler:function(){
	
	},
	createConfigPanel:function(config){
		var me = this,configPanel;
		configPanel = Ext.create('component.view.form.FormPanel', {
    	    region:'north',border:false,frame:true,
    	    name:'searchForm',
    	    items:[
    	    {xtype:'hidden',name:'detailId',value:''},
			{xtype:'hidden',name:'chartId',value:''},
			{xtype:'hidden',name:'sqlData',value:ChartCfg.detailSql},
			{xtype:'hidden',name:'xName',value:''},
			{xtype:'hidden',name:'yName',value:''},
			{
				xtype : 'clearTextField', fieldLabel:'指标名称', name:'title',allowBlank :false,
				emptyText : '名称',blankText :"提示：曲线标题必填",
				listeners:{
					blur:function(textField){
						if(textField.getValue()=='')return;
						me.setTitle(textField.getValue());
						if(me.handler)
							me.handler(me);
					}
				}
			},{
				xtype:'ajaxComboBox',
				editable : false,
				queryMode:'local',
				fieldLabel : '指标类型',
				name:'type',
				displayField: 'name',
				valueField: 'value',
				data:[['曲线','0'],['柱状图','1']],
				value:config.type+'',
				listeners:{
					select:function(combo){
						if(me.handler)
							me.handler(me);
					}
				}
			},{
				xtype:'box',
				width:Ext.getBody().getWidth()/2-50,
				height:Ext.getBody().getHeight()-100,
				style:'margin:10px',
				html:'',
				listeners:{
					afterrender:function(box){
						box.update('<textarea style="width:100%;height:100%;">'+ChartCfg.detailSql+'</textarea>');
						me.editor = CodeMirror.fromTextArea(box.getEl().dom.childNodes[0], {
							mode: "text/x-plsql",
					        lineNumbers: true
					    });
					    me.editor.on('change',function(editor,option){
					    	console.info(arguments);
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