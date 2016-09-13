Ext.define('component.public.PanelResizerPlugin', {
	extend : 'Ext.AbstractPlugin',
	alias : 'widget.panelResizerPlugin',
	init : function(panel) {
		this.panel = panel;
		panel.on('afterrender',Ext.bind(this.afRender,this));
		this.callParent(arguments);
	},
	afRender : function(ct, position) {
		this.resizer = Ext.create('Ext.resizer.Resizer', {
		    el: this.panel.el,
		    handles: 'e',
		    minWidth: this.panel.minWidth50,
		    maxWidth: this.panel.maxWidth||500
		});
		this.resizer.on('beforeresize',this.onBeforeResizer,this);
		this.resizer.on('resize', this.onResizer, this);
	},
	onBeforeResizer:function( oResizable, width, height, e, eOpts ){
		this.oldWidth = width;
	},
	onResizer : function(oResizable, iWidth, iHeight, e) {
		var subWidth = this.oldWidth-iWidth;
//		this.panel.setWidth(iWidth);//不可用，在border布局中会触发事件
		this.panel.width=iWidth;
		$(this.panel.body.dom).width(iWidth);
		$(this.panel.getView().getEl().dom).width(iWidth-27);
		if(!Ext.isEmpty(this.panel.up('silderPanel')))
			this.panel.up('silderPanel').resizeLefts(this.panel,subWidth);
	}
});
