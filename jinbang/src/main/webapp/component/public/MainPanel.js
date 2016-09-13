Ext.define('component.public.MainPanel', {
	extend : 'Ext.tab.Panel',
	config:{
		topPanel:null
	},
	initComponent : function() {
		// 一些初始化工作
		this.plugins = Ext.create('Ext.ux.TabCloseMenu');
		this.region='center';
//		var homeItem = Ext.create('component.public.MainItemPanel', {
//			id : 'home',
//			title : '首页',
//			url : 'serviceFlow/queryServiceFlow.jsp',
//			draggable : false,
//			closable : false
//		});
//		this.items=[homeItem];
		this.callParent([ arguments ]);
		this._cache = {};
	},
    setTheme:function(theme,url) {
    	url+="?theme="+theme;
    	return url;
    },
	loadTab : function(node) {
		if(Ext.isEmpty(node.url)) return;
		var me=this;
		node.url=me.setTheme(me.getTopPanel()&&me.getTopPanel().getQueryParam('theme')||me.getTopPanel()&&me.getTopPanel().cookieProvider.get('theme'), node.url);
		var n = this.findPanel(node.id);
		if (n) {
			this.setActiveTab(n);
//			this.refreshTab(n);
		} else {
			var c = {
				id : node.id,
				title : node.title,
				src : ctx+'/'+node.url,
				loadMask:'页面加载中 .....',
				draggable : Ext.isEmpty(node.draggable)?true:node.draggable,
				closable : Ext.isEmpty(node.closeable)?true:node.closable,
				listeners : {
					activate : function(tab) {
					},
					beforeclose:function(tab){
						if(!me.closeValidation(tab)) return false;
					},
					close:function(tab){
						me.removeTab(tab);
					}
				}
			};
//			if(this.items.length > 5){
//				Ext.Msg.alert("操作提示","您打开的窗口数已经达到最大值，请先关闭闲置窗口!");
//				return ;
//			}
//			var panel = Ext.create('component.public.MainItemPanel', c);
			var panel =Ext.create('Ext.ux.IFrame',c);
			this.addPanel(node.id, panel);
			this.add(panel);
			this.setActiveTab(panel);
		}
	},
	refreshTab:function(panel){
		if(!this.closeValidation(panel)) return;
		panel.load();
//		var iframe=document.getElementsByName('iframe_'+panel.getId())[0];
//			window.frames[iframe.id].window.loaction.reload();
//    		iframe.src=ctx+'/'+panel.url;
//			panel.body.mask('页面载入中......');
	},
	removeTab:function(panel){
		if(!this.closeValidation(panel)) return;
		this.remove(panel);
		delete this._cache[panel.getId()];
	},
	closeValidation:function(panel){
		if(panel.getWin().onbeforeunload){
			Ext.Msg.alert('提示','场景未结束,不可以关闭,请先退出测试状态');
			return false;
		}else{
			return true;
		}
	},
	findPanel : function(id) {
		var ret = this._cache[id];
		if (!ret) {
			ret = this.query('[id=' + id + ']');
			return Ext.isEmpty(ret) ? null : ret[0];
		}
		return ret;
	},
	addPanel : function(name, panel) {
		if (!this._cache) {
			this._cache = {};
		}
		this._cache[name] = panel;
	}
});

//Ext.define('component.public.MainItemPanel', {
//	xtype : 'mainItemPanel',
//	extend : 'Ext.panel.Panel',
//	closable : true,
//	constructor : function(config) {
//		var me = this;
//		config = config || {};
//		Ext.apply(config, {
//			border : false,
//			frame : false,
//			layout : 'fit',
//			items : [ {
//				xtype : 'component',
//				border : false,
//				frame : false,
//				autoEl : {
//					tag : 'iframe',
//					style : 'height: 100%; width: 100%; border: none;',
//					scrolling : 'auto',
//					frameborder : 0,
//					name : 'iframe_' + config.id,
//					src :config.src
//				},
//				listeners : {
//					load : {
//						element : 'el',
//						fn : function() {
//							this.parent().unmask();
//							me.loadFlag=true;
//						}
//					},
//					resize : function() {
//						if (!me.loadFlag) {
//							me.body.mask('页面载入中......');
//						}
//					}
//				}
//			} ]
//		});
//		me.callParent([ config ]);
//	},
//	load: function (src) {
////        var me = this,
////            text = me.loadMask,
////            frame = me.getFrame();
////
////        if (me.fireEvent('beforeload', me, src) !== false) {
////            if (text && me.el&&!me.el.isMasked()) {
////                me.el.mask(text);
////            }
////
////            frame.src = me.src = (src || me.src);
////        }
//    }
//});
Ext.define('component.public.MainTopPanel', {
	extend : 'Ext.panel.Panel',
	xtype : 'mainTopPanel',
	requires:['component.public.AjaxComboBox'],
	constructor : function(config) {
		var me=this;
		config = config || {};
		var menu=Ext.create('Ext.toolbar.Toolbar',config.menu);
//		var menuItem=me.createMenu();
//		var menu=Ext.create('Ext.toolbar.Toolbar',{xtype:'toolbar',dock:'top',items:[{text:'test',menu:menuItem}]});
		Ext.applyIf(config,{
			region:'north',
			height:75,
			style : 'border-radius:0',
			frame:false,
			dockedItems: [menu,{
			    xtype: 'toolbar',
				height:49,
				style:'background-image: url('+ctx+'/main/img/logo.png) !important; background-repeat: no-repeat;background-position:center left;',
			    dock: 'top',
				    items : ['->',{
				    	xtype:'box',
				    	html:'<img src="'+ctx+'/main/img/user-32.png" style="width:16px;height:16px;"></img>'
				    },{
						xtype : 'label',
						name : 'staffInfo',
						text : session.user.realName+'('+session.user.name+'),欢迎登陆!'
					},{
						xtype : 'label',
						text : '当前时间:'
					},{
						xtype : 'label',
						name : 'clock',
						style:'margin-right:20px;',
//						text:Ext.Date.format(new Date(), Date.patterns.LongTime),
						listeners:{
							'render': function() {
							    var me=this;
								Ext.TaskManager.start({ 
								    run: function() {
								    	me.setText(Ext.Date.format(new Date(), Date.patterns.Long24Time)); 
								    }, 
								    interval: 1000 
								});
						    }
						}
					},{
						xtype : 'label',
						text : '界面主题:',
						style:'margin-right:5px;'
					},{ xtype:'ajaxComboBox',
						data : [['Classic','classic' ],
								['Accessibility','access' ],
								[ 'Gray','gray' ],
								['Neptune','neptune' ] ],
						listeners:{
							afterrender:function(){
								if(me.getQueryParam('theme')||config.cookieProvider.get('theme')){
									this.setValue(me.getQueryParam('theme')||config.cookieProvider.get('theme'));
								}else{
									this.setValue('classic'||me.getQueryParam('theme'));
									config.cookieProvider.set('theme','classic'||me.getQueryParam('theme'));
								}
								this.on('select',function(combo,records,eOpts){
									var value=records[0].get('value');
									config.cookieProvider.set('theme',value);
									me.setParam({theme:value});
								});
							}
						},
						queryMode:'local'
					},{
					iconCls:'loginOut',
					scale : 'large',
					handler : me.loginOut,
					scope : me
				} ]
			}]
		});
//		delete config.menu;
		me.callParent([config]);
	},
	 getQueryParam:function(name, queryString) {
        var match = RegExp(name + '=([^&]*)').exec(queryString || location.search);
        if(match){
            return decodeURIComponent(match[1]);
        }else{
        	return null;
        }
    },
    setParam:function(param) {
        var queryString = Ext.Object.toQueryString(
            Ext.apply(Ext.Object.fromQueryString(location.search), param)
        );
        location.search = queryString;
    },
	loginOut:function(){
		Ext.Ajax.request({
			url : ctx + '/login/loginOut.do',
			method : 'POST',
			success : function(response) {
				if(!Ext.ExceptionHandler(response)){
					return;
				} 
				window.top.location.href=ctx+'/main/login.jsp';
			},
			failure : function(response, action) {
				Ext.Msg.alert('提示','登出失败');
			}
		});
	},
	createMenu : function() {
		return Ext.create('Ext.menu.Menu', {
//					id : 'mainMenu',
					xtype: 'menu',
               		plain: true,
					style : {
						overflow : 'visible' // For the Combo popup
					},
					items : [{
								text : '测试',
								iconCls:'win-symbol',
								handler:function(){alert('tt')}
							}]
				});
	}
});


Ext.define('component.public.MainMenuPanel', {
	extend : 'Ext.panel.Panel',
	xtype : 'mainMenu',
	config:{
		mainPanel:null
	},
	constructor : function(config) {
		var me = this;
		var store = Ext.create('component.public.AjaxStore', {
			url : ctx + "/menu/getMenuList.do",
			fields:['menuId','parentMenuId','title','url','sortId']
		});

		
		config = config || {};
		Ext.applyIf(config,{
			width : 200,
			store : store,
			region:'west',
			collapsible : true,
			split : true,
			title : '导航菜单',
			layout : 'accordion'
		});
		store.on('load', me.initMenu.createDelegate(me));
		me.callParent([ config ]);
		me.on('render', function() {
			store.proxy.extraParams.parentMenuId=0;
			store.load();
		});
	},
	initMenu : function(store, records, successful, eOpts) {
		var me = this;
		Ext.each(records, function(record, index) {
			var menuItem = Ext.create('component.public.MainMenuItemPanel',
					record.raw);
			menuItem.setMainMenuPanel(me);
			me.add(menuItem);
		});
	}
});

Ext.define('component.public.MainMenuItemPanel', {
	extend : 'component.public.TreeCommonPanel',
	xtype : 'mainMenuItem',
	config:{
		mainMenuPanel:null
	},
	constructor : function(config) {
		delete config.url;
		var me = this;
		config = config || {};
		Ext.applyIf(config, {
			title : config['title'],
			autoExpand : true, // 是否全展开(checkable : true生效)
			cascadeCheck : 'downmulti',
			checkable : false, // 是否显示可选框(Boolean)
			singleSelect : true, // 单选(Boolean) (级别比cascadeCheck高)
			onlyLeafCheckable : true, // 是否只有叶子节点可选(Boolean)
			rootVisible : false, // 根节点可见
			rootId : config['menuId'], // 根节点值
			iconable : true, // 是否显示图标(Boolean)
			sqlKey : 'PetriMenu,getMenuList', // 获取数据的SQL名称(String)
			idKey : 'parentMenuId', // 树节点查询子节点值关联KEY
			nodeId : 'MENU_ID', // 节点id KEY
			nodeName : 'TITLE', // 节点text KEY
			nodeIcon : 'ICON_PATH', // 节点icon KEY
			listeners : {
				itemclick : function(panel,record, item, index, e, eOpts ) {
					var node=Ext.decode(record.raw.attributes);
					node['id']=node['MENU_ID'];
					node['title']=node['TITLE'];
					node['url']=node['URL'];
					me.getMainMenuPanel().getMainPanel().loadTab(node);
				}
			}
		});
		me.callParent([ config ]);
		me.store.on('load',function(){
			var firstNode=new Object();
			firstNode=Ext.decode(me.getRootNode().childNodes[0].raw.attributes);
			if(firstNode.TITLE=='工作流程定义'){
				firstNode['id']=firstNode['MENU_ID'];
				firstNode['title']=firstNode['TITLE'];
				firstNode['url']=firstNode['URL'];
				me.getMainMenuPanel().getMainPanel().loadTab(firstNode);
			}
		});
	}
});