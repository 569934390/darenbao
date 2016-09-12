Ext.define('component.business.flow.FormsWindow', {
	mixins: {
        FormsController : 'component.business.flow.FormsController'
    },
	refushData:function(data){
		var me = this;
		me.removeAll();
		var forms=[],height=100,width=0;
		var form = window.top.Ext.create('workflow.forms.'+data.formId,{ //    	var form = Ext.create('forms.view.render.Test',{
    		title:data.formName||'流程表单',
    		collapsible:true
    	});
    	var relevance = form.relevance;
    	if(relevance!=''&&!Ext.isEmpty(relevance)){
    		var relevances = relevance.split(',');
    		for(var key in relevances){
    			var relev = relevances[key].split('@');
	    		var relevanceForm = window.top.Ext.create('workflow.forms.'+relev[1],{ //    	var form = Ext.create('forms.view.render.Test',{
		    		title:relev[0]||'流程表单',
		    		collapsible:true
		    	});
		    	relevanceForm.setDisabled(true);
		    	forms.push(relevanceForm);
		    	height+=(relevanceForm.height+30);
		    	if(width<relevanceForm.width)
		    		width = relevanceForm.width;
    		}
    	}
    	forms.push(form);
    	height+=form.height;
    	if(width<form.width){
		    width = form.width;
    	}
    	me.add({
    		xtype:'form',
            border:false,
            margin:'0 0 0 10',
			autoScroll:true,
    		items:forms
    	});
    	me.setWidth(width+50);
    	if(height>window.top.Ext.getBody().getHeight()-50)
    		me.setHeight(window.top.Ext.getBody().getHeight()-50);
    	else
    		me.setHeight(height);
	},
	bindProcessDefinitionKey:function(data){
		var me = this;
		me.data = data;
		var form = me.win.down('form');
		form.getForm().setValues(data.formProperties);
//		form.getForm().findField('createDate').setValue(new Date());
	},
	constructor : function(condition) {
		var me = this;
		var win = window.top.Ext.create('Ext.window.Window',Ext.apply({
			modal:true,
			closable:true,
			bodyStyle:'background:#fff',
			closeAction:'hide',
			layout:'fit',
			buttons:[{
				text:'提交',
				handler:Ext.bind(me.submitData,me)
			},{
				text:'重置',
				handler:function(){
					win.down('form').getForm().reset();
				}
			}]
		},condition));
		win.refushData = Ext.bind(me.refushData,win);
		win.bindProcessDefinitionKey = Ext.bind(me.bindProcessDefinitionKey,me);
		win.getTaskForms = Ext.bind(me.getTaskForms,me);
		me.win = win;
		return win;
	}
});