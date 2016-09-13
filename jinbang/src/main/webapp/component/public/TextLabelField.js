/* 
 * 文本框加备注
 * @author Damen
 * @date 2013-09-29
 * @define component.public.textLabelField
 * @alias textLabelField
 * 
 * 
 * @title 基本设置
 * @param label : 备注的值（String）
 * @param color : 备注值颜色（String）
 * 
 */

Ext.define('component.public.textLabelField', {
	extend : 'Ext.form.TextField',
    alias: 'widget.textLabelField',
	labelAlign : 'right',
	layout : 'column',
    constructor : function(condition) {
    	var me = this;
    	
    	me.superclass.constructor.apply(me, [{
			id : condition.id,
			fieldLabel : condition.fieldLabel,
			emptyText : condition.emptyText,
			name : condition.name,
			width : condition.width,
			value : condition.value,
			readOnly : condition.readOnly,
			label : condition.label,
			color : condition.color,
			validator:condition.validator
		}]);
    	me.on('render', function(obj) {
			var font = document.createElement("font");
			font.setAttribute("color", condition.color);
			var redStar = document.createTextNode(condition.label);
			font.appendChild(redStar);
			obj.el.dom.parentNode.appendChild(font);
		});
	}
});

/**
 * 使用方法
 */
//xtype : 'textLabelField',
//fieldLabel:'超时(秒)',
//labelWidth : 80,
//name:'symbolName1',
//blankText :"提示：节点名称必填",
//label : '(1000-8000ms)',
//color : 'black',
//width : 300