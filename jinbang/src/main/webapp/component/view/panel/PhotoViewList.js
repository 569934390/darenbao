Ext.define('component.view.panel.PhotoViewList', {
	extend : 'Ext.view.View',
	alias : 'widget.photoViewList',
	cls:'video-view-list video-view-bg',
    itemSelector: 'div.thumb-wrap',
    overItemCls : 'thumb-warp-over',
    selectedItemCls :'thumb-warp-selected',
    emptyText: '没有可以预览的视频',
    multiSelect:true,
    simpleSelect:true,
	requires:[],
	config:{
	},
	constructor : function(config) {
		var me = this;
		config=config||{};
		
        var store = Ext.create('Ext.data.Store', {
             fields:config.fields,
             proxy: {
                 type: 'ajax',
                 url: ctx+'/base/getPage.do',
                 reader: {
                    type: 'json',
                    root: 'result',
                    totalProperty: 'totalCount'
                 },
                 extraParams:{conditionsStr:Ext.encode({staffId:session.user.id,albumId:config.albumId}),sqlKey:config.sqlKey,type:config.type}
             },
             pageSize:50,
             autoLoad: true
         });
         var plugin=[Ext.create('component.public.GridToolTipPlugin',{
            toolTipRenderer:function(grid,record,value,label,cellIndex,cellElement,dataIndex){
                if(cellIndex==15||cellIndex==16){
                    return value;
                }
                return false;
            }
         })];

        var imageTpl = new Ext.XTemplate(
            '<tpl for=".">',
                '<div class="thumb-wrap">',
                    '<div class="imagDiv">',
                    	'<img src="'+webRoot+'businessInfo/upload.do?getthumb={fileName}&width=200&height=150">',
                    '</div>',
                    '<div class="title">{name}</div>',
                '</div>',
            '</tpl>'
        );

		Ext.applyIf(config,{
			store: store,
            tpl: imageTpl,
		});
		me.callParent([config]);
	}
});