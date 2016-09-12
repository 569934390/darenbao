Ext.define('workflow.forms.${className}',{
	name:'${className}',
	extend:'Ext.form.FieldSet',
	relevance:'${(relevance)!}',
	requires:['component.public.TextButtonField','component.public.AjaxComboBox','component.public.ComboSelectFirstPlugin','Ext.ux.form.DateTimeField'],
	layout: 'absolute',
	<#if width??>
		width:${width},
	</#if>
	<#if height??>
		height:${height},
	</#if>
	defaultType: 'textfield',
	defaults:{
		labelAlign:'right'
	},
	items: [
		<#list actReFormFields as item>
          	{
			<#if item.xtype=='localCombo'>
				xtype: 'ajaxComboBox',
				plugins : ['comboSelectFirstPlugin'],
				queryMode:'local',
				${item.comboStore},
			<#elseif  item.xtype=='remoteCombo'>
				xtype: 'ajaxComboBox',
				queryMode:'remote',
				${item.comboStore},
			<#else>
				xtype: '${item.xtype}',
			</#if>
			<#if item.width??>
				width: ${(item.width)!},
			</#if>
			<#if item.defaultValue??>
				value:${item.defaultValue},
			</#if>
			<#if item.fieldLabel??>
				fieldLabel: '${(item.fieldLabel)!}',
			</#if>
			<#if item.extaCondition??>
				${(item.extaCondition)!},
			</#if>
			<#if item.fieldWidth??>
				fieldWidth: ${(item.fieldWidth)!},
			</#if>
			<#if item.x??>
				x: ${(item.x)!},
			</#if>
			<#if item.y??>
				y: ${(item.y)!},
			</#if>
				name: '${item.name}'
			}
			<#if (item_has_next) >
			,
			</#if>
        </#list>
		]
});