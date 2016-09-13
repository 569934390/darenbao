/**
 * var form = Ext.create('component.FormPanel', {
    		 items:[{
    			 fieldLabel:'任务名称',
    			 columnWidth:1,
    			 width:300
    		 },{
    			 fieldLabel:'任务模式',
    			 columnWidth:0.3,
    			 xtype:'combo',
    			 width:200
    		 },{
    			 fieldLabel:'agentID',
    			 columnWidth:0.3,
    			 xtype:'combo',
    			 width:200
    		 },{
    			 fieldLabel:'任务状态',
    			 columnWidth:0.3,
    			 xtype:'combo',
    			 width:200
    		 }]
    	 });
    inputWidth:控件占用多少px的宽度，inputWidth和columnWidth不能共存，按定量和按比例不能同时存在
    
    var form = Ext.create('component.FormPanel', {
    		 items:[{
    			 fieldLabel:'任务名称',
    			 inputWidth:600,
    			 width:600,
    		 },{
    			 fieldLabel:'任务模式',
    			 inputWidth:200,
    			 xtype:'combo',
    			 width:200
    		 },{
    			 fieldLabel:'agentID',
    			 inputWidth:200,
    			 xtype:'combo',
    			 width:200
    		 },{
    			 fieldLabel:'任务状态',
    			 inputWidth:200,
    			 xtype:'combo',
    			 width:200
    		 }]
    	 });
 */
Ext.define('component.public.FormPanel', {
    extend : 'Ext.form.Panel',
    alias : 'widget.columnFormPanel',
    mixins: {
        layout:'component.tools.AutoLayout'
    },
    requires:['component.public.ClearTextField','component.public.AjaxComboBox'],
    constructor : function(config) {
        var me = this,fieldSet = [];
        Ext.applyIf(config,{items:[]});
        var conditions = {
            border:false,
            frame:true,
            height:'100%',
            layout:'absolute',
            defaults: Ext.apply({labelAlign:'right',labelWidth: 70,readOnly:false,defaultType: 'textfield'},config.defaults)
        };
        delete config.defaults;
        me.flags = [],items=[],me.fields=[];
        for(var i=0;i<config.items.length;i++){
            if(config.items[i]=='->'&i>0){
                if(!me.flags[items.length])
                    me.flags[items.length]=0;
                me.flags[items.length]++;
            }
            else{
                config.items[i].autoLayout = true;
                if(config.items[i].allowBlank==false){
                    config.items[i].afterSubTpl = WEBConstants.REQUIRED;
                    if(config.items[i].width){
                        config.items[i].width = config.items[i].width+7;
                    }
                }
                if(Ext.isEmpty(config.items[i].regex)||config.items[i].regex==''){
                    delete config.items[i].regex;
                    delete config.items[i].regexText;
                }
                if(Ext.isEmpty(config.items[i].maxLength)||config.items[i].maxLength==''){
                    delete config.items[i].maxLength;
                }
                if(Ext.isEmpty(config.items[i].minValue)||config.items[i].minValue==''){
                    delete config.items[i].minValue;
                }
                if(Ext.isEmpty(config.items[i].maxValue)||config.items[i].maxValue==''){
                    delete config.items[i].maxValue;
                }
                if(Ext.isEmpty(config.items[i].regexText)||config.items[i].regexText==''){
                    config.items[i].regexText = '违反正则校验';
                }
                items.push(config.items[i]);
            }
        }
        config.items = items;
        me.config = config;
        me.fields = items;
        Ext.apply(conditions,config);
        this.callParent([conditions]);
        me.on('resize',me.resize);
    },
    resize:function(f){
        var me = this;
        var form = me.doAutoLayout(me.fields,me.flags,me.getWidth());
        var boxs = me.query('box');
        Ext.each(boxs,function(item,index){
            if(item.autoLayout){
                var layout = form.fields.pop();
                if(layout&&item.xtype!='hidden'&&item.xtype!='hide'&&item.autoLayout){
                    if(Ext.isEmpty(item.cx)){
                        item.setLocalX(layout.x);
                    }
                    else{
                        item.setLocalX(item.cx);
                    }
                    if(Ext.isEmpty(item.cy)){
                        item.setLocalY(layout.y);
                    }
                    else{
                        item.setLocalY(item.cy);
                    }
                }
            }
        });
        if(!form.height)
            me.setHeight(form.height);
        return;
    },
     /**
     * 设置所有输入框不可用
     */
    disableFields : function() {
        var fields = this.getForm().getFields(),buttons = this.query('button');
        Ext.each(buttons,function(btn){
            btn.hide();
        });
        fields.each(function(field) {
            field.setReadOnly(true);
            // 背景灰化
            field.setFieldStyle('background:#E6E6E6');
        });
    },
    /**
     * 设置所有输入框可用
     */
    enableFields : function() {
        var fields = this.getForm().getFields(),buttons = this.query('button');
        Ext.each(buttons,function(btn){
            btn.show();
        });
        fields.each(function(field) {
            field.setReadOnly(false);
            // 背景还原
            field.setFieldStyle('background:white repeat-x 0 0;background-image: url('+webRoot+'/common/images/text-bg.gif)');
        });
    }
});
