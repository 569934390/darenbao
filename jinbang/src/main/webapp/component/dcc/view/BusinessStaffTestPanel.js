Ext.define('component.dcc.view.BusinessStaffTestPanel', {
	extend : 'Ext.panel.Panel',
	requires:['component.public.AjaxComboBox','component.public.TextButtonField','component.socket.Message'],
	layout:'border',
	config:{
		cfgs:null
	},
	constructor : function(config) {
		var me = this;
		me.config = config;
		config=config||{};
		me.businessStaffDccTree = me.createBusinessStaffDccTree();
		me.executeForm = me.createExecuteForm();
		me.resultView=me.createResultView();
		me.resultViewPanel=Ext.create('Ext.panel.Panel',{
			layout:'fit',
			region:'east',
			width:Ext.getBody().getWidth()-510,
			dockedItems:[{
				xtype:'toolbar',
				docked:'top',
				items:[{
					text:'清除',
					name:'clearBtn',
					iconCls:'toolbar-delete'
				}]
			}],
			items:[me.resultView]
		})
		Ext.applyIf(config,{
			title:'营业员测试',
			frame:false,
			items:[me.executeForm,me.businessStaffDccTree,me.resultViewPanel]
		});
		me.callParent([config]);
		me.bindEvent();
//				var store=me.resultView.store;
//				Ext.each(data.jsonData,function(record){
//					var message=Ext.decode(record.message);
//					if(message.uuid){
//						var index=me.resultView.store.findExact('uuid',message.uuid);
//						index=1;
//						var record=store.getAt(index);
//						record.set('current',Number(record.get('current'))+1);
//						record.set('success',message.success);
//						record.set('src',message.src);
//					}
//				});
//				store.each(function(record){
//					if(record.get('uuid')==data.uuid){
//						
//					}
//				});
//		},1000);
	},
	bindEvent:function(){
		var me=this;
		me.businessStaffDccTree.on('itemclick',Ext.bind(me.itemClickHandler,me));
		me.executeForm.down('[name=executeBtn]').on('click',Ext.bind(me.executeHandler,me));
		MessageUtils.bind('dccdop.server.web.simulator.recelive',Ext.bind(me.messageHandler,me));
		me.resultViewPanel.down('[name=clearBtn]').on('click',function(){
			me.resultView.store.removeAll();
		});
	},
	messageHandler:function(data){
		var me =this;
		var store=me.resultView.store;
//		Ext.each(data.jsonData,function(jsonData){
			var message=Ext.decode(data);
			console.info(message.uuid)
			if(message.uuid){
				var index=me.resultView.store.findExact('uuid',message.uuid);
//				index=1;
				if(index>-1){
					var record=store.getAt(index);
					console.info(message);
					if(message&&message.message&&(message.message+'').indexOf('自动发包')!=-1){
						record.set('current',Number(record.get('current'))+1);
						record.set('success',message.success);
						record.set('src',webRoot+ 'common/topoImages/16x16/ok.png');
					}
					else if(message&&message.message&&(message.message+'').indexOf('返回码')!=-1){
						var element = message.message;
						console.info(message.message,element);
						element=element.substring(element.indexOf('[')+1,element.indexOf(']'));
						console.info(element);
						record.set('resultCode',element);
						record.set('resultDesc',avpCodes[element].avpDesc);
						if(message&&message.message&&(message.message+'').indexOf('red')!=-1){
							record.set('success',false);
						record.set('src',webRoot+ 'common/topoImages/16x16/cancel.png');
						}
					}
				}
				
			}
//		});
//		store.each(function(record){
//			if(record.get('uuid')==data.uuid){
//				
//			}
//		});
	
	},
	getSelectedRecord:function(){
		return this.businessStaffDccTree.getSelectionModel().getSelection()[0];
	},
	executeHandler:function(){
		var me=this;
		if(Ext.isEmpty(me.cfgs)){
			return Ext.Msg.alert('提示','请到配置界面配置号码');
		}else{
			if(!me.executeForm.getForm().isValid()) return;
			var values=me.executeForm.getForm().getValues();
			Ext.each(me.cfgs,function(cfg,index){
				cfg['avpValue']=values['avpCfg'+cfg['dccId']+cfg['path']];
			});
			var uuid=Math.uuid();
			var params={};
			params.udid=uuid;
			var businessSystem=me.executeForm.down('[name=businessSystem]').getValue().split('@');
			params.dccAgent=businessSystem[1];
			params.ip=businessSystem[2];
			params.port=businessSystem[3];
			params.cfgStr=Ext.encode(me.cfgs);
			params.dccId=me.dccId;
			Ext.Ajax.request({
				url:ctx+'/businessStaff/doCmd.do',
				params:params,
				success:function(response){
					var responseText=Ext.ResponseDecode(response);
					responseText.text=me.getSelectedRecord().get('text');
					me.resultView.store.add(responseText);
					var record=me.resultView.store.getAt(0);
					setTimeout((function(record) {
						if (record.get('success') == false) {
							record.set('src',webRoot+ 'common/topoImages/16x16/cancel.png');
							record.set('success', false);
							record.set('result', '执行超时！');
						}
					})(record), 60000);
				}
			});
		}
	},
	itemClickHandler:function(tree,record,item,index,e,eOpts){
		var me=this;
		if(!Ext.isEmpty(record.childNodes)){
			var fieldSet=me.executeForm.down('[name=cfg]');
			me.executeForm.remove(fieldSet);
			return;
		};
		me.dccId=record.raw.dcc_id;
		Ext.Ajax.request({
			url:ctx+'/businessStaff/loadCfgByParentId.do',
			params:{dccId:record.raw.dcc_id},
			success:function(response){
				var cfgs=Ext.ResponseDecode(response);
				me.cfgs=cfgs;
				var fieldSet=me.executeForm.down('[name=cfg]');
				if(Ext.isEmpty(cfgs)){
					if(!Ext.isEmpty(fieldSet)){
						me.executeForm.remove(fieldSet);
					}
					return Ext.Msg.alert('提示','请到配置界面配置号码');
				}
				var obj=[];
				Ext.each(cfgs,function(cfg,index){
					obj.push({
								xtype : 'textfield',
								name : 'avpCfg' + cfg['dccId']+cfg['path'],
								allowBlank:false,
								vtype:'phoneNumber',
								value:18978833611,
								fieldLabel : cfg['avpDesc']
							})
				});
				if(Ext.isEmpty(fieldSet)){
					fieldSet=Ext.create('Ext.form.FieldSet',{
						title:'号码配置',
						layout:'form',
						name:'cfg',
						style:'padding-bottom:5px',
						items:obj
					});
					me.executeForm.add(fieldSet);
				}else{
					fieldSet.removeAll();
					fieldSet.add(obj);
				}
			}
		});
	},
	createExecuteForm:function(){
		return Ext.create('Ext.form.Panel', {
					region : 'center',
					frame:true,
					items : [{
						xtype : 'fieldset',
						title : '测试',
						layout:'column',
						style:'padding-bottom:5px',
						defaults:{
							style:'margin-bottom:5px;margin-left:10px;'
						},
						items : [{
							xtype : 'ajaxComboBox',
							labelWidth:70,
							fieldLabel : '业务系统',
							name : 'businessSystem',
							queryMode : 'local',
							labelAlign : 'left',
							data : business.staff.constants.businessSystem,
							plugins:Ext.create('component.public.ComboSelectFirstPlugin')
						}, {
							xtype : 'button',
							iconCls : 'toolbar-play',
							name:'executeBtn'
						}]
					}]
				});
	},
	createResultView:function(){
		return Ext.create('component.business.BusinessStaffCommandView', {
					title : '日志',
					style : 'background-color:#FFFFFF'
				});
	},
	createBusinessStaffDccTree:function(){
		var me = this;
		var treePanel = Ext.create('component.dcc.view.BusinessStaffDccTreePanel', {
			region : 'west',
			width : 200,
			collapsible: false,
			title:'',
			sqlKey:'DopTestDcc.selectBusinessStaffByParentId',
			frame:true
		});
		return treePanel;
	}
});