Ext.define('component.business.BusinessStaffCommandView', {
	extend : 'Ext.view.View',
	style : 'margin:10px;width:100%;height:100%;overflow:auto;',
	itemSelector : 'div.commandCls',
	emptyText : '没有任何数据',
	// overItemCls : 'itemHover',
	multiSelect : true,
	autoScroll : true,
	constructor : function(config) {
		var me = this;
		Ext.define('Image', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'src',
						type : 'string'
					}, {
						name : 'uuid',
						type : 'string'
					}, {
						name : 'text',
						type : 'string'
					}, {
						name : 'success',
						type : 'bool'
					},'resultCode','resultDesc','statusTime','current','total']
		});
		var imagesStore = Ext.create('Ext.data.Store', {
			model : 'Image'
//			data : [{
//				src:webRoot+'common/topoImages/16x16/sync.gif',
//				uuid:Math.uuid(),
//				text:'主叫',
//				success:true,
//				resultCode:2001,
//				resultDesc:'请求成功',
//				current:3,
//				total:3,
//				statusTime:'2011-11-11 10:10:10'
//			},{
//				src:webRoot+'common/topoImages/16x16/sync.gif',
//				uuid:Math.uuid(),
//				text:'主叫',
//				success:true,
//				resultCode:2001,
//				resultDesc:'请求成功',
//				current:1,
//				total:5,
//				statusTime:'2011-11-11 10:10:10'
//			}]
		});
		var imageTpl = new Ext.XTemplate(
				'<tpl for=".">',
					'<div style="padding: 5px;" class="commandCls">',
				'<tpl if="current==total">',
					'<img src="{src}" style="cursor:pointer;margin:5px;margin-top:-3px;margin-bottom:-4px;"/>',
				'<tpl elseif="!success">',
					'<img src="{src}" style="cursor:pointer;margin:5px;margin-top:-3px;margin-bottom:-4px;"/>',
				'<tpl else>',
					'<img style="margin-right:10px;width:16px;height:16px;margin-top:-3px;margin-bottom:-4px;" src="'+ webRoot + 'common/images/loading.gif"/>',
				'</tpl>',
					'<span>{text}({current}/{total})[{statusTime}]</span>',
				'<tpl if="current==total||!success">',
					'<div style="height:20px;line-height:20px;color:#4c4c4c;margin-left:5px;padding-left:25px;background:url('+ webRoot+ 'public/ext-4.2.1/resources/ext-theme-classic/images/tree/elbow.gif) 0 no-repeat !important;overflow:hidden;">测试结果:<tpl if="success"><span style="color:blue">成功</span><tpl else><span style="color:red">失败</span></tpl></div>',
					'<div class="logger" style="height:20px;line-height:20px;color:#4c4c4c;margin-left:5px;padding-left:25px;background:url('+ webRoot+ 'public/ext-4.2.1/resources/ext-theme-classic/images/tree/elbow.gif) 0 no-repeat !important;overflow:hidden;" >返回编码:{resultCode}</div>',
					'<div class="logger" style="height:20px;line-height:20px;color:#4c4c4c;margin-left:5px;padding-left:25px;background:url('+ webRoot+ 'public/ext-4.2.1/resources/ext-theme-classic/images/tree/elbow-end.gif) 0 no-repeat !important;overflow:hidden;" >返回结果:{resultDesc}</div>',
					'</div>', 
				'</tpl>',
				'</tpl>');
		this.tpl = imageTpl;
		this.store = imagesStore;
		var config = Ext.apply({}, config);
		me.callParent([config]);
		me.on('itemclick',function(view, record, item, index, e, eOpts){
//			if(e.target.tagName=='IMG'||e.target.tagName=='img'
//				||e.target.tagName=='image'||e.target.tagName=='IMAGE'){
//				console.info(e.target)
//				if(e.target.className.indexOf('look l_')!=-1){
//					var index = e.target.className.substring(7,e.target.className.length);
//					record.get('look')[index].callback.call(me,record,index);
//				}
//				else if(record.get('loggerBak')==null){
//					record.set('loggerBak',record.get('logger'));
//					record.set('logger',[]);
//				}
//				else{
//					record.set('logger',record.raw.logger);
//					record.set('loggerBak',null);
//				}
//			}
		});
	}
});