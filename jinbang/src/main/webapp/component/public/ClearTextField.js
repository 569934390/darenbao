/**
 * 带清楚按钮的文本框组件
 */
Ext.define('component.public.ClearTextField', {
	extend : 'Ext.form.field.Trigger',
	alias: ['widget.zteclear', 'widget.clearTextField', 'widget.cleartextfield'],
	trigger1Cls : Ext.baseCSSPrefix + 'form-clear-trigger',
	clear : function() {
		this.setValue('');
	},
	reset : function() {
		this.setValue('');
	},
	onTrigger1Click : function() {
		this.clear();
	}
});