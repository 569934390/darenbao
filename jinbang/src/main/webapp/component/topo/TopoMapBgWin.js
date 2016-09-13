/**
 * 设置背景图
 */
Ext.define('component.topo.TopoMapBgWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoMapBgWin',
	constructor:function(condition){
		var me = this;
		me.mapBgData = condition.mapBgData;
		me.createMapBgForm(condition.mapBgData);
		
		var config = Ext.applyIf(condition,{
			border : false,
		    layout: 'fit',
			items:[me.mapBgForm]
		});
		me.callParent([config]);
	},
	createMapBgGrid : function(){
		var me = this;

		var store = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot + '/topoSymbol/findBCIMGList.do',
		        reader: {
		            type: 'json',
		            root: 'colunmsData'
		        }
		    },
		   	fields: ['fileUrl']
		});
		store.load();

		mapBgGrid = Ext.create('Ext.grid.Panel', {
			width : '100%',
		    store: store,
		    columns: [
		        {text: '系统图片', dataIndex: 'fileUrl'}
		    ],
		    forceFit : true,
		    height: 220
		});
		me.mapBgGrid = mapBgGrid;
		
		mapBgGrid.on('itemclick', function(gd, rowIndex, event){
	        var record =  gd.getSelectionModel().getSelection();
	        var fileUrl='../common/topoImages/bgImages/'+record[0].data['fileUrl'];
			if (!record) {
				Ext.Msg.alert('提示', '请选择一个表单');
				return;
			}else{
				Ext.getCmp('bgImage').el.dom.src=fileUrl;
			}
		});
	},
	createMapBgForm : function(mapBgData) {
		var me = this;
		me.createMapBgGrid();
		
		var filedSet = Ext.create('Ext.form.FieldSet',{
			title : '',
			frame : false,
			border : false,
			height : 300,
			labelWidth : 80,
	        bodyPadding: 0,
			layout : 'column',
			items : [{
				columnWidth :.2,
				border : false,
				layout : 'form',
				items : [{
					xtype : 'radio',
					boxLabel:'自定义图片',
					id:'bcRadioZ',
					name:'bcRadio'
				}]
			},{
				columnWidth :.55,
				border : false,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					id : "bgFile",
					name : "bgFile",
					inputType : "file",
					width : 200,
					style : 'accept="image/*"',
					fileType:['gif','jpg','png'],
					regex:/(doc)|(pdf)$/i,
					editable : false,
					allowBlank : true
				}]
			},{
				columnWidth :.2,
				border : false,
				layout : 'form',
				items : [{
					defaultType : 'checkbox',
					fieldLabel:'',
					border : false,
					id:'upServer',
					name:'upServer',
					layout : 'form',
					items : [{
						boxLabel : '上传到服务器',
						id : 'isUpServer',
						width :90,
						disabled : true,
						checked : true
					}]
				}]
			},{
				columnWidth :.35,
				border : false,
				layout : 'form',
				items : [{
					xtype : 'radio',
					boxLabel:'系统图片',
					id:'bcRadioS',
					name:'bcRadio',
					checked:true	
				},me.mapBgGrid]
			},{
				columnWidth :.6,
				border : false,
				labelWidth : 20,
				layout : 'form',
				items : [{
					defaultType : 'checkbox',
					fieldLabel:'',
					border : false,
					id:'upData',
					name:'upData',
					items : [{
						boxLabel : '更新数据库',
						id : 'isUpData',
						width :90,
						checked : true
					}]
				},{
					xtype: 'box',
					id : 'bgImage',
					labelWidth : 10,      
					width: '100%', 		//图片宽度              
					height: 220, 		//图片高度             
					autoEl: {           
						tag: 'img', 	//指定为img标签          
						src: '../common/topoImages/bgImages/china.png'
					}
				}]
			}]
		});
		mapBgForm = Ext.create('Ext.form.Panel', {
			region : 'center',
			id : 'mapBgForm',
			labelAlign : 'right',
			border : true,
			frame : false,
	        minWidth: 200,
			deferredRender : false,
	        bodyPadding: 0,
			autoHeight : true,
			defaults : {
				autoScroll : true
			},
			layoutOnTabChange : true,
			items : [filedSet],
			buttons : [{
				text : '预览',
				handler : me.buttonEvent.createDelegate(me,['topoBgView',0])
			},{
				id : 'saveBtn',
				text : '保存',
				handler : me.buttonEvent.createDelegate(me,['topoBgSave'],0)
			},{
				text : '取消',
				buttonAlgin:'center',
				handler : function() {
					me.hide();
				}
			}]
		});
		me.mapBgForm = mapBgForm;
	},
	topoBgView : function(){
		var me= this;
		
		var bcRadioZ = Ext.getCmp('bcRadioZ').getValue();
		var bcRadioS = Ext.getCmp('bcRadioS').getValue();
		var bgFile = Ext.getCmp('bgFile').getValue();
		var isUpData = Ext.getCmp('isUpData').getValue();
		var mapPanel = document.getElementById('frame_main');
		if(bcRadioZ){
	        Ext.Msg.alert('提示', '本地图片不能预览！');
	        return;
		}else{
	        var record = me.mapBgGrid.getSelectionModel().getSelection();
			if (!record[0]) {
				Ext.Msg.alert('提示', '请选择一张系统图片！');
				return;
			}else{
				var fileUrl='../common/topoImages/bgImages/'+record[0].data['fileUrl'];
//				mapPanel.contentWindow.topology.config.backgroundImage = fileUrl;
//				mapPanel.contentWindow.topology.backGround.redraw();
				mapPanel.contentWindow.document.getElementById('container').style.backgroundImage="url("+fileUrl+")";
			}
		}
	},
	topoBgSave : function(){
		var me = this;
		
		var form ={};
		form.symbolId = me.mapBgData.mapParentId;
		var record = me.mapBgGrid.getSelectionModel().getSelection();
		var fileUrl = "";
		if (!record[0]) {
			Ext.Msg.alert('提示', '请选择一张系统图片！');
			return;
		}else{
			fileUrl='../common/topoImages/bgImages/'+record[0].data['fileUrl'];
			form.mapBackground = fileUrl;
		}
		
		Ext.Ajax.request({
			url : webRoot + '/topoSymbol/editSymbolNode.do',
			method : 'POST',
			params : form,
			success : function(resp, action) {
				var mapPanel = document.getElementById('frame_main');
				mapPanel.contentWindow.document.getElementById('container').style.backgroundImage="url("+fileUrl+")";
				me.hide();
			},
			failure : function() {
			}
		});
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'topoBgView':
				me.topoBgView();
				break;
			case 'topoBgSave':
				me.topoBgSave();
				break;
		}
	}
});