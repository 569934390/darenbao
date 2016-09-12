/**
 * 文本框+按钮组件
 */
Ext.define('component.public.TextButtonField', {
    extend: 'Ext.form.field.Trigger',
    alias: 'widget.textButtonField',
    trigger1Cls: Ext.baseCSSPrefix + 'form-search-trigger',
    trigger2Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
    initComponent: function(){
        var me = this;
        me.hiddenValue=me.value;
        me.value=me.text;
        if(me.value == null || me.hiddenValue == null) {
        	me.clear();
        }
        me.addEvents('afterSetValue,click');
        me.callParent(arguments);
    },
//	onFocus:function(){
//		if(!this.editable){
//			this.onTrigger1Click(arguments);
//			this.blur();
//		}
//	},
    afterRender: function(){
    	var me=this;
        me.callParent();
        if(!me.editable){
        	me.inputEl.on('click',Ext.bind(me.onTrigger1Click,me));
        }
    },
    clear:function(){
    	this.setTextAndValue('','');
    },
	reset:function(){
		this.setTextAndValue('','');
	},
	setTextAndValue:function(text,value){
		this.setValue(text,true);
		this.hiddenValue=value;
		if(!Ext.isEmpty(value)){
			this.clearInvalid();
		}
	},
	getTextAndValue:function(){
		return [this.getRawValue(),this.hiddenValue];
	},
	setValue:function(value,flag){
		if(flag){//有flag表示要直接设置不要再加判断了
			this.callParent([Ext.isEmpty(value)?'':value]);
		}else if (!Ext.isEmpty(value)&&value.indexOf('@')>0){
			this.setTextAndValue(value.split('@')[0],value.split('@')[1]);
		}else if(!Ext.isEmpty(value)&&value.indexOf(',')>0){
			this.setTextAndValue(value.split(',')[0],value.split(',')[1]);
		}else{
			this.callParent([Ext.isEmpty(value)?'':value]);
		}
		this.fireEvent('afterSetValue',this.getValue());
	},
	getValue:function() {
		return this.hiddenValue;
	},
	getSubmitValue:function() {//提交form需要
		return this.hiddenValue;
	},
    onTrigger1Click : function(){
    	var me=this;
    	if(this.handler){
    		this.handler(me);
    	}else{
		me.fireEvent('click',arguments);
	}
    },
    onTrigger2Click : function(){
    	this.clear();
    }
});