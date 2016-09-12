Ext.util.CSS.swapStyleSheet('drapViewCss', webRoot+ 'common/css/drapViewCss.css');
Ext.define('component.business.DropView', {
	createDropView : function(obj) {
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
								name : 'name',
								type : 'string'
							}, 'attributes', 'color']
				});
		Ext.create('Ext.data.Store', {
					id : 'imagesStore',
					model : 'Image',
					data : []
				});
		var imageTpl = new Ext.XTemplate(
				'<tpl for=".">',
				'<div style="padding: 5px;" class="dropItem" onselectstart="return false" onselect="document.selection.empty()">',
				'<img src="{src}" style="margin:5px;margin-top:-3px;margin-bottom:-4px;"/>',
				'<span>{name}</span>',
				'<span style="background-color:{color};width:16px;height:16px;margin-left:5px;" >&nbsp;&nbsp;&nbsp;</span>',
				'<div class="itemClose" nodeId={nodeId} ></div>', '</div>',
				'</tpl>');
		var view = Ext.create('Ext.view.View', {
			store : Ext.data.StoreManager.lookup('imagesStore'),
			style : 'margin:10px;background-color:#fff;width:100%;height:100%;overflow:auto;',
			tpl : imageTpl,
			itemSelector : 'div.dropItem',
			emptyText : '无添加选择项',
			overItemCls : 'itemHover',
			multiSelect : true,
			autoScroll : true,
			cls : 'dropView',
			listeners : {
				afterrender : function(form) {
					form.getStore().on('datachanged', function() {
						setTimeout(function() {
							$('#' + form.getEl().id).find('.itemClose')
									.unbind('click').bind('click', function() {
										var nodeId = $(this).attr('nodeId');
										var length = form.getStore().getCount();
										for (var i = 0; i < length; i++) {
											var record = form.getStore()
													.getAt(i);
											if (record.get('nodeId') == nodeId) {
												form.getStore().remove(record);
												break;
											}
										}
									});
							$('#' + form.getEl().id).find('.dropItem')
									.unbind('mouseenter').unbind('mouseleave')
									.hover(function(e) {
										$(this).find('.itemClose').css({
											top : ($(this.parentNode)
													.scrollTop() + $(this)
													.position().top)
													+ 'px',
											left : ($(this).position().left
													+ $(this).width() + 8)
													+ 'px'
										}).show();
									}, function() {
										$(this).find('.itemClose').hide();
									});
						}, 100);
					});
					this.formPanelDropTarget = new Ext.dd.DropTarget(form.getEl(), {
								ddGroup : 'tree-to-form',
								notifyEnter : function(ddSource, e, data) {
									form.getEl().stopAnimation();
									form.getEl().highlight();
								},
								notifyDrop : function(ddSource, e, data) {
									var selectedRecord = ddSource.dragData.records[0];
									var result = me.cascade(selectedRecord), modes = [];
									for (var key in result) {
										var node = result[key];
										if (!me.getByNodeId(form.getStore(),
												node.raw.id)) {
											modes.push({
												nodeId : node.raw.id,
												name : node.raw.text,
												src : node.raw.icon,
												attributes : node.raw.attributes,
												color : node.raw.color
											});
										}
									}
									form.getStore().loadData(modes, true);
									return true;
								}
							});
				}
			}
		});
		return view;
	},
	getByNodeId : function(store, nodeId) {
		for (var i = 0, length = store.getCount(); i < length; i++) {
			if (store.getAt(i).get('nodeId') == nodeId) {
				return store.getAt(i);
			}
		}
		return null;
	},
	getRGB : function() {
		var str = "0123456789abcdef";
		var t = "#", rgb = '';
		for (j = 0; j < 6; j++) {
			rgb = rgb + str.charAt(Math.random() * str.length);
		}
		return t + rgb;
	},
	cascade : function(node) {
		var me = this, result = [], t = me.getRGB();
		for (var key in node.childNodes) {
			result = result.concat(this.cascade(node.childNodes[key]));
			for (var _key in result) {
				if (!result[_key].raw.color) {
					result[_key].raw.color = t;
				}
			}
		}
		if (node.childNodes.length == 0)
			result.push(node);
		return result;
	}
});