Ext.define('component.business.CommandView', {
	extend : 'Ext.view.View',
	style : 'margin:10px;background-color:#fff;width:100%;height:100%;overflow:auto;',
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
						name : 'nodeId',
						type : 'string'
					}, {
						name : 'src',
						type : 'string'
					}, {
						name : 'uuid',
						type : 'string'
					}, {
						name : 'value',
						type : 'string'
					}, {
						name : 'success',
						type : 'bool'
					},'result','logger','loggerBak','successColor','ip','bussinceKey','look','statusTime']
		});
		var imagesStore = Ext.create('Ext.data.Store', {
			model : 'Image',
			data : [
//			 {
//			 value:'OID:123.12.123.12',
//			 src:webRoot+'common/topoImages/16x16/sync.gif'
//			 },{
//			 value:'OID:123.12.123.12',
//			 src:webRoot+'common/topoImages/16x16/sync.gif'
//			 },{
//			 value:'OID:123.12.123.12',
//			 src:webRoot+'common/topoImages/16x16/ok.png',
//			 success:true,
//			 logger:[{debug:'执行脚本中'},{debug:'执行脚本中'},{debug:'执行脚本中'},{debug:'执行脚本中'},{debug:'执行脚本中'},{debug:'执行脚本中'}],
//			 result:'成功'
//			 },{
//			 value:'OID:123.12.123.12',
//			 src:webRoot+'common/topoImages/16x16/sync.gif'
//			 }
			]
		});
		var imageTpl = new Ext.XTemplate(
				'<tpl for=".">',
					'<div style="padding: 5px;" class="commandCls">',
				'<tpl if="success">',
				'<img src="{src}" style="cursor:pointer;margin:5px;margin-top:-3px;margin-bottom:-4px;"/>',
				'<span>[{statusTime}]:{value}</span> 完毕!',
				'<tpl else>',
					'<img style="margin-right:10px;width:16px;height:16px;margin-top:-3px;margin-bottom:-4px;" src="'+ webRoot + 'common/images/loading.gif"/>',
					'<span>正在执行[{statusTime}]:{value}</span> 任务',
					'<img src="{src}" style="cursor:pointer;margin:5px;margin-top:-3px;margin-bottom:-4px;"/>',
				'</tpl>',
				'<tpl for="logger">',
					'<div class="logger" style="height:20px;line-height:20px;color:#4c4c4c;margin-left:5px;padding-left:25px;background:url('+ webRoot+ 'public/ext-4.2.1/resources/ext-theme-classic/images/tree/elbow.gif) 0 no-repeat !important;overflow:hidden;" >{debug}</div>',
				'</tpl>',
				'<tpl if="success">',
					'<div style="height:20px;line-height:20px;{successColor}margin:5px;margin-top:0px;padding-left:20px;background:url('+ webRoot+ 'public/ext-4.2.1/resources/ext-theme-classic/images/tree/elbow-end.gif) 0 no-repeat !important;overflow:hidden;" >执行结果:{result}</div>',
				'</tpl>', '</div>', '</tpl>');
		this.tpl = imageTpl;
		this.store = imagesStore;
		var conditions = Ext.apply({}, config);
		me.callParent([conditions]);
		me.on('itemclick',function(view, record, item, index, e, eOpts){
			if(e.target.tagName=='IMG'||e.target.tagName=='img'
				||e.target.tagName=='image'||e.target.tagName=='IMAGE'){
				console.info(e.target)
				if(e.target.className.indexOf('look l_')!=-1){
					var index = e.target.className.substring(7,e.target.className.length);
					record.get('look')[index].callback.call(me,record,index);
				}
				else if(record.get('loggerBak')==null){
					record.set('loggerBak',record.get('logger'));
					record.set('logger',[]);
				}
				else{
					record.set('logger',record.raw.logger);
					record.set('loggerBak',null);
				}
			}
		});
	}
});