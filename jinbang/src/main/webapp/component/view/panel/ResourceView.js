Ext.require(['component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.define('component.view.panel.ResourceView', {
	extend : 'Ext.Component',
	alias : 'widget.resourceView',
	requires:[],
	config:{
	},
	constructor : function(config) {
		var me = this;
		config=config||{};
		me.callParent([config]);
		me.on('afterrender',Ext.bind(me.loadUrl,me));
	},
	bindEvent : function(cExt,rf){
		var me = this;
		var buttons = cExt.ComponentQuery.query('button');
		for(var i = 0;i<buttons.length;i++){
			var btn = buttons[i];
			btn.handler=Ext.emptyFn();
			if(btn.getEl()&&btn.getEl().dom){
				var $co = $(btn.getEl().dom);
				var $editorMask = $('<div class="translate"></div>');
				$editorMask.offset($co.offset()).width(btn.getWidth()).height(btn.getHeight())
					.css({position: 'absolute',cursor:'pointer'}).appendTo(rf.contentWindow.document.body);
				$editorMask.data('btn',btn);
				me.checkBtn($editorMask,btn);
				$editorMask.click(function(e){
					e.stopPropagation();
					if($(this).data('status')){
						$(this).toggleClass('translate');
						$(this).toggleClass('disable');
						$(this).data('status',false);
						me.cascadeBy($(this).data('btn'),false);
					}
					else{
						$(this).toggleClass('translate');
						$(this).toggleClass('disable');
						$(this).data('status',true);
						me.cascadeBy($(this).data('btn'),true);
					}
					return false;
				});
				if(!Ext.isEmpty(btn.name)){
//					btn.hide();
				}
//				$clone.hide();
//										btn.hide();
			}
		}
	},
	checkBtn:function($editor,btn){
		var me = this;
		if(!Ext.isArray(me.btns))return;
		for(var k in me.btns){
			var key = null;
			if(btn.privilegeCode){
				key = btn.privilegeCode;
			}
			else if(btn.text){
				key = btn.text;
			}else if(btn.itemId){
				key = btn.itemId;
			}else if(btn.iconCls){
				key = btn.iconCls;
			}else{
				key = btn.id;
			}
			console.info(key);
			if(key&&me.btns[key]){
				$editor.toggleClass('translate');
				$editor.toggleClass('disable');
				$editor.data('status',true);
				break;
			}
		}
	},
	/**
	 * 呃，考虑到同一个text或者iconCls,icon这样的定位一个按钮可能在多个Ext.panel.tabpanel下同时存在。
	 * 但反过来想一下，多个tabpanel下的panel。这个多个可能是无穷无尽的，可能是树或者grid触发临时插入的panel
	 * 为了方便起见，以至于配置不至于太繁琐
	 * 约定以下结论：
	 * 在button不配置privilegeCode这个关键字段时，不同tabpanel下的所有按钮button都视为同一资源
	 * so……(～ o ～)Y  by 李飞 569934930 2014-10-31
	 * 不服可以来打死我！
	 * @param {} btn
	 */
	cascadeBy:function(btn,checked,rf){
		var me = this;
		if(!Ext.isArray(me.btns))me.btns = [];		
		if(btn.privilegeCode){
			me.btns[btn.privilegeCode] = checked;
		}
		else if(btn.text){
			me.btns[btn.text] = checked;
		}else if(btn.itemId){
			me.btns[btn.itemId] = checked;
		}else if(btn.iconCls){
			me.btns[btn.iconCls] = checked;
		}else{
			me.btns[btn.id] = checked;
		}
		console.info(me.btns,checked);
	},
	loadUrl:function(){
		var me = this;
		this.update('<iframe class="main_frame" id="'+me.itemId+'" frameborder="no"   border="no" style="border:0;width:100%;height:100%;"></iframe>',false,function(c,dom){
			Ext.getBody().mask('页面加载中……');
			if(me.url.indexOf('?')==-1){
				$(me.getEl().dom).find('.main_frame').attr('src',webRoot+me.url+'?theme=gray');
			}
			else{
				$(me.getEl().dom).find('.main_frame').attr('src',webRoot+me.url+'&theme=gray');
			}
			$(me.getEl().dom).find('.main_frame').load(function(){
				var rf = this;
				$(this).css({width: '100%',height: '100%'});
				var cExt = this.contentWindow.Ext;
				cExt.onReady(function(){
					var tabs = cExt.ComponentQuery.query('tabpanel');
					for(var i=0;i<tabs.length;i++){
						tabs[i].on('tabchange',function(){
							me.bindEvent.call(me,cExt,rf);
						});
					}
					me.bindEvent.call(me,cExt,rf);
					Ext.getBody().unmask();
				});
			});
		});
	}
});