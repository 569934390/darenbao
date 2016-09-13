Ext.util.CSS.swapStyleSheet('drapViewCss', webRoot+ 'common/css/drapViewCss.css');
Ext.define('component.business.flow.FlowTraceView', {
	extend : 'Ext.view.View',
	cls:'flowTraceView',
	itemSelector : 'div.activiti',
	emptyText : '没有任何数据',
	overItemCls : 'task_div_hover',
	multiSelect : true,
	autoScroll : true,
	constructor : function(config) {
		var me = this;
		Ext.define('Image', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'vars'
					}, {
						name : 'currentActiviti',
						type : 'bool'
					}, 'x', 'y', 'width', 'height']
		});
		var imagesStore = Ext.create('Ext.data.JsonStore', {
			model : 'Image',
			proxy : {
				type : 'ajax',
				url : ctx + '/processController/process/trace.do?pid='+config.processInstanceId,
				reader : {
					type : 'json'
				}
			},
			autoLoad : true
		});
		var imageTpl = new Ext.XTemplate(
//				'<img style="background:#fff;position:absolute; left:0px; top:0px;" src="'+webRoot+'processController/process/trace/auto/'+4139+'.do"/>',
				'<img style="position:absolute; left:50px; top:50px;" src="'+webRoot+'processController/resource/read.do?processDefinitionId='+config.processDefinitionId+'&resourceType=image"/>',
				'<tpl for=".">',
					'<div class="activiti task_div{currentActiviti}" style="width:{[values.width+3]}px;height:{[values.height+3]}px;top:{[values.y-1]}px;left:{[values.x-1]}px;"></div>',
				 '</tpl>');
		this.tpl = imageTpl;
		this.store = imagesStore;
		var conditions = Ext.apply({}, config);
		me.callParent([conditions]);
		me.toolTip = Ext.create('Ext.tip.ToolTip', {
			style:'background:#fff',
		    html: 'Press this button to clear the form'
		});
		me.on('itemmouseenter',function(view, record, item, index, e, eOpts){
			console.info(record,e,me);
			console.info(record.get('vars'))
			var vars = record.get('vars'),innerHTML='';
			for(var key in vars){
				innerHTML+=key+':'+(vars[key]||'')+'</br>';
			}
			me.toolTip.update(innerHTML);
			me.toolTip.showAt([record.get('x')+me.getX(),record.get('y')+(record.get('height'))+me.getY()+5]);
		});
		me.on('itemmouseleave',function(view, record, item, index, e, eOpts){
			me.toolTip.hide();
		});
		me.on('beforehide',function(){
			me.toolTip.hide();
		});
		me.on('beforedestroy',function(){
			me.toolTip.hide();
		});
		
	}
});