Ext.require(['component.public.CustomPanelComponent']);

//读出的字段值再处理
function testFun(value){
	return value+"_OKAY";
}

//动态加载css
function loadCssFile(filename){
    var fileref = document.createElement('link');
    fileref.setAttribute("rel","stylesheet");
    fileref.setAttribute("type","text/css");
    fileref.setAttribute("href",filename);
    if(typeof fileref != "undefined"){
        document.getElementsByTagName("head")[0].appendChild(fileref);
    }
}

Ext.onReady(function() {
	loadCssFile(webRoot+'common/css/alarm.css');
	//查询框下拉项定义
	var testStore = Ext.create('Ext.data.Store', {
	    fields: ['value', 'text'],
	    data: [
	        {'value': '00A','text': '有效'},
	        {'value': '00X','text': '无效'}
	    ]
	});
	
	var testCmp = Ext.create('Ext.form.ComboBox', {
	    name: 'NODE_NAME',
	    store: testStore,
	    width: 100,
	    queryMode: 'local',
	    displayField: 'text',
	    valueField: 'value'
	});
	
    Ext.create('Ext.container.Viewport', {
        layout: 'fit',
        items: [{
   	 		xtype: 'customPanel',
   	 		aliasName: 'NODE_ELEMENT_GRID',
   	 		searchPanel: true,
   	 		where: [{
   	 			key: 'NODE_ID',
   	 			opt: '>',
   	 			value: '3'
   	 		}],
   	 		order: [{
   	 			key: 'NODE_ID',
   	 			dir: 'asc'
   	 		}],
   	 		gridCfg: {
	   	 		multiSelect: false,
	   	 		selModel: {
	   	 			selType:'rowmodel'
	   	 		}
   	 		},
   	 		title: '演示',
   	 		pageSize: 20,
   	 		searchComponents: [{
   	 			key: 'STATUS',
   	 			store: testStore
   	 		},{
   	 			key: 'NODE_NAME',
   	 			item: testCmp
   	 		}],
   	 		displayFunctions: [{
   	 			key: 'NODE_NAME',
   	 			fun: testFun
   	 		}],
	   	 	searchInit: [{//*注释4
	   	 		key: 'NODE_NAME',
	   	 		hidden: true
	   	 	}],
	   	 	displayInit: [{//*注释4
	   	 		key: 'NODE_NAME',
	   	 		hidden: true
	   	 	}],
   	 		init: function(panel, toolPanel, grid) {
//   	 			grid.where = [{
//	   	 			key: 'NODE_ID',
//	   	 			opt: '>',
//	   	 			value: '3'
//	   	 		},{
//	   	 			key: 'NODE_NAME',
//	   	 			opt: 'like',
//	   	 			value: 'think'  
//   	 			}];
//   	 			grid.customLoad();
   	 			panel.addDocked([{//菜单栏
   	 				xtype: 'toolbar',
   	 				dock: 'top',
   	 				items:[{
   		   	  	    	text: '菜单',
   		   	 	    	menu: Ext.create('Ext.menu.Menu',{
   		   	 				items:[{
   		   				        text: '复选框',
   		   				        checked: true
   		   				    }]
   		   	 			})
   	   	 			}]
				},{//菜单栏
   	 				xtype: 'toolbar',
   	 				dock: 'top',
   	 				items:[{
   	   	 				text: '按钮1',
   	   	 				handler: function(){
   	   	    	 			var records = grid.getSelectionModel().getSelection();
   	   	    	 			alert(records[0].data['NODE_NAME']);
   	   	 				}
   	   	 			},'-',{
   	   	 				text: '按钮2',
   	   	 				handler: function(){
   	   	 					alert(grid.store.getAt(0).data['NODE_NAME']);
   	   	 				}
   	   	 			}]
				}]);
   	 			
   	 			grid.setContextMenu({
   	 				view: function() {
   	 				},
   	 				edit: function() {
   	 				},
   	 				add: function() {
   	 				},
   	 				del: function() {
   	 				}
   	 			});
   	 			
   	 		}
        }]
    });
});