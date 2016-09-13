Ext.ns('main.index');
Ext.require(['component.public.MainPanel']);
main.index = function() {
	var cp = new Ext.state.CookieProvider();
	Ext.state.Manager.setProvider(cp);
	var me=this;
	var initMainPanel=function(){
		me.mainPanel=Ext.create('component.public.MainPanel',{border:false,region:'center'});
		Ext.mainPanel = me.mainPanel;
	};
	var initMenu=function(){
		Ext.Ajax.request({
			url:ctx+'/menu/getMenuList.do',
			success:function(response){
				var responseText=Ext.ResponseDecode(response);
				var menu=createMenu(responseText);
				initTop(menu);
				initMainPanel();
				initView();
				me.menuPanel.setMainPanel(me.mainPanel);
				me.mainPanel.setTopPanel(me.topPanel);
				initTopMenu();
	    		me.mainPanel.setActiveTab(0);
			}
		});
		me.menuPanel=Ext.create('component.public.MainMenuPanel');
	};
	var createMenu=function(responseText){
		var me=this;
		var items=[];
		var childMenuItem=null;
		Ext.each(responseText,function(menu,index){
			var childItems=[];
			Ext.each(menu.children, function(childMenu, index) {
				var childItem = {
					text : childMenu.menuName,
					iconCls : childMenu.iconCls,
					handler : function() {
						me.mainPanel.loadTab({
									id : childMenu.menuId,
									title : childMenu.menuName,
									url : childMenu.url
								});
					}
				};
				childItems.push(childItem);
			});
			var menuItem={
				xtype: 'menu',
           		plain: true,
				style : {
					overflow : 'visible' // For the Combo popup
				},
				items : childItems
			};
			var item={
				text:menu.menuName,
	            iconCls: menu.iconCls,
	            menu:menuItem
			};
			items.push(item);
		})
		var toolbarConfig={
			        xtype: 'toolbar',
					dock : 'top',
					items : items
				};
		return toolbarConfig;
	};
	var initTop=function(menu){
		me.topPanel=Ext.create('component.public.MainTopPanel',{border:false,cookieProvider:cp,menu:menu});
//		me.topPanel=Ext.create('Ext.panel.Panel',{
//			region:'north',
//			height:50,
//			style : 'border-radius:0',
//			frame:true,
//			html:'xxx'
//		});
	};
	var initView=function(){
		Ext.create('Ext.container.Viewport', {
			layout:'border',
			items:[me.mainPanel,me.topPanel]
		});
	};
	var initTopMenu=function(){
		me.mainPanel.loadTab({id:'systemHealthyState',title:'首页',closeable:false,url:'protal/protalView.jsp'});
//		me.mainPanel.loadTab({id:'messageSearch',title:'消息查询',closeable:false,url:'debug/debug.jsp'});
//		me.mainPanel.loadTab({id:'testDcc',title:'功能测试',closeable:false,url:'testDcc/testDcc.jsp'});
//		me.mainPanel.loadTab({id:'dccSimulator',title:'场景测试',closeable:false,url:'simulator/dccSimulator.jsp'});
//		me.mainPanel.loadTab({id:'task',title:'任务管理',closeable:false,url:'task/task.jsp'});
//		me.mainPanel.loadTab({id:'performance',title:'性能测试',closeable:false,url:'performance/performance.jsp'});
	};
	return {
		init : function() {
			initMenu();
		}
	};
}();
Ext.onReady(main.index.init, main.index);