Ext.require(['component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.define('component.view.panel.LimitTestPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.limitTestPanel',
	requires:[],
	config:{
	},
	constructor : function(config) {
		var me = this;
		config=config||{};
		Ext.applyIf(config,{
			border:false,
			layout:'border',
			items:[me.scanTree = me.createScanTree(),me.createConfigPanel(),me.pefTaskGrid = me.createPerfTask()]
		});
		me.callParent([config]);
		me.pefTaskGrid.on('itemdblclick',function( grid, record, item, index, e, eOpts ){
			if(!me.perfTestDatasWin){
				me.perfTestDatasWin = Ext.create('component.view.win.PerfTestDatasWin',{
					title:'详细信息'
				});
			}
			me.perfTestDatasWin.down('grid').getStore().proxy.extraParams = {page:1,start:0,limit:50,conditionsStr:Ext.encode({uuid:record.raw.version}),sqlKey:'DopPerfDatas.selectList'};
			me.perfTestDatasWin.down('grid').getStore().load();
			me.perfTestDatasWin.show();
		});
	},
	createPerfTask:function(){
		var me = this;
		return Ext.create('component.view.grid.PerfTaskGrid',{
			 region:'center',border:true,
			 tbar:[{
			 	text:'立即执行',iconCls:'toolbar-play',
			 	handler:function(){
			 		Ext.Persistent.getPage({conditionsStr:Ext.JSON.encode({status:'00A'}),sqlKey:'DopPerfConfig.selectList',limit:1,pageNo:0,pageSize:1},function(result){
						var simus = me.scanTree.getChecked(),simulators=[],simulatorIds=[],rates=[];
					 	if(simus.length==0)return Ext.Msg.alert('提示','请配置场景及比例');
						if(result.totalCount>0){
							return Ext.Msg.alert('提示','检测尚有性能测试任务未结束，为了保证结果准确，不允许同时发起性能测试任务');
						}
						var entity = me.configPanel.getForm().getValues();
				 		for(var i=0;i<simus.length;i++){
				 			if(!simus[i].get('rate')||parseInt(simus[i].get('rate'))<=0){
				 				return Ext.Msg.alert('提示',simus[i].get('text')+' 必须选择一个比率');
				 			}
				 			simulatorIds.push(simus[i].get('id'));
				 			rates.push(simus[i].get('rate'));
				 			simulators.push(simus[i].get('text')+'@'+simus[i].get('rate')+' ');
				 		}
				 		Ext.state.Manager.set('ipAndPort',entity.ipAndPort);
						Ext.state.Manager.set('dccAgent',entity.dccAgent);
				 		Ext.apply(entity,{
				 			simulators:simulators.join(','),
				 			simulatorIds:simulatorIds.join(','),
				 			rates:rates.join(','),
					 		version:Math.uuidCompact(),startTimer:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
					 		status:"00A"
				 		});
			 			Ext.Persistent.post(ctx+'/dopTaskSend/addPerfTask.do',entity,function(){
			 				me.pefTaskGrid.getStore().load();
			 			});
			 		});
			 	}
			 },{
			 	text:'立即停止',iconCls:'toolbar-invalid',
			 	handler:function(){
			 		var records = me.pefTaskGrid.getSelectionModel().getSelection();
			 		for(var i=0;i<records.length;i++){
			 			var entity = {id:records[i].raw.id,status:"00X",
				 			endTimer:Ext.util.Format.date(new Date(),'Y-m-d H:i:s')};
				 		Ext.Persistent.update('DopPerfConfig',entity,function(){
				 			me.pefTaskGrid.getStore().load();
				 		});
				 		Ext.Persistent.callCommand({uuid:records[i].raw.version,command:'CLOSE_SOCKET',dccAgent:records[i].raw.dccAgent});
			 		}
			 	}
			 },{
			 	text:'删除',iconCls:'toolbar-delete',
			 	handler:function(){
			 		var records = me.pefTaskGrid.getSelectionModel().getSelection(),ids=[];
			 		for(var i=0;i<records.length;i++){
			 			ids.push(records[i].raw.id);
			 		}
			 		Ext.Persistent.deletes('DopPerfConfig',ids.join(','),function(){
			 			me.pefTaskGrid.getStore().load();
			 		});
			 	}
			 },'-',{
				 text:'时延对比',
				 handler:function(){
					 var records = me.pefTaskGrid.getSelectionModel().getSelection(),perfRecords=[];
					 for(var i=0;i<records.length;i++){
						 perfRecords.push(records[i].raw);
					 }
					 if(!window.$panel){
						 window.$panelbg = $('<img class="chart-panel-bg" /src="'+webRoot+'performance/graph/chart_bg.png" />');
						 window.$panel = $('<div class="chart-panel"><div class="chart-box"></div></div>');
						 window.$panel.css({width:Ext.getBody().getWidth()-100,height:Ext.getBody().getHeight()-100,marginTop:50,marginLeft:50});
						 window.$panelbg.appendTo(document.body);
						 window.$panel.appendTo(document.body);
						 window.$panelbg.click(function(e){
							 if(e.target.className=='chart-panel-bg'){
								 if(Ext.isIE8m){
									 window.$panelbg.hide();
									 window.$panel.hide();
								 }
								 else{
									 window.$panelbg.fadeOut();
									 window.$panel.fadeOut();
								 }
							 }
						 });
					 }
					 var params = [];
					 for(var i=0;i<perfRecords.length;i++){
						 params.push({
							 sqlKey:'DopPerfDatas.selectList',
							 conditionStr:{uuid:perfRecords[i].version},
							 limit:25,
							 start:0
						 });
					 }
					 Ext.Persistent.getPages(params,function(data){
						 window.$panel.find('.chart-box').each(function(index,dom){
							 var _data = [];
							 for(var i=data.length-1;i>=0;i--){
								 _data.push(data[i]);
							 }
							 ZTECharts.init({renderTo:dom,data:_data,records:perfRecords});
						 });
					 });
					 if(Ext.isIE8m){
						 window.$panelbg.show();
						 window.$panel.show();
					 }
					 else{
						 window.$panelbg.fadeIn();
						 window.$panel.fadeIn();
					 }
				 }
			 },'-',{
			 	text:'导出报告',iconCls:'toolbar-exportToExcel',
			 	handler:function(){
					var records = me.pefTaskGrid.getSelectionModel().getSelection();
					if(records.length==0){
						return Ext.Msg.alert('提示','请选择一条记录');
					}
					else if(records.length>1){
						return Ext.Msg.alert('提示','只能选一条');
					}

			 		window.open(webRoot+'chartController/exportPerf.do?page=1&start=0&limit=100&conditionsStr='+Ext.encode({uuid:records[0].raw.version})+'&sqlKey=DopPerfDatas.selectList');
			 	}
			 }],
			 style:'margin:5px'
		});
	},
	createConfigPanel:function(){
		var me = this,configPanel;
		var dccAgent = Ext.state.Manager.get('dccAgent')||'127.0.0.1';
		var ipAndPort = Ext.state.Manager.get('ipAndPort')||'127.0.0.1';
		var filters = Ext.state.Manager.get('filters')||['127.0.0.1'];
		var port = Ext.state.Manager.get('port')||'1111';
		var flag = true;
		for(var i=0;i<filters.length;i++){
			if(filters[i]==ipAndPort){
				flag = false;
				break;
			}
		}
		if(flag)
			filters.push(ipAndPort);
		var ipAndPorts = [];
		for(var i=0;i<filters.length;i++){
			ipAndPorts.push([filters[i],filters[i]]	);
		}
		Ext.state.Manager.set('filters',filters);
		
		configPanel = Ext.create('component.view.form.FormPanel', {
    	    region:'north',border:true,frame:true,
    	    name:'searchForm',
    	    items:[
			{xtype:'hidden',name:'version',value:''},
			{xtype:'hidden',name:'simulators',value:''},
			{xtype:'hidden',name:'totalCount',value:0},
			{xtype:'hidden',name:'avgDelay',value:0},
			{xtype:'hidden',name:'maxDelay',value:0},
			{xtype:'hidden',name:'minDelay',value:0},
			{xtype:'hidden',name:'misCount',value:0},
			{xtype:'hidden',name:'delayCount',value:0},
			{xtype:'hidden',name:'startTimer',value:''},
			{xtype:'hidden',name:'endTimer',value:''},
			{
				xtype:'ajaxComboBox',
				editable : true,
				queryMode:'local',
				fieldLabel : 'IP端口',
				name:'ipAndPort',
				displayField: 'name',
				valueField: 'value',
				data:ipAndPorts,
				value:ipAndPort
			},{
				xtype : 'numberfield', fieldLabel:'发送周期', name:'startNum',
				value : 50,blankText :"提示：发送周期数必填"
			},{
				width:200,labelWidth:100,
				xtype : 'numberfield', fieldLabel:'周期内发送包数', name:'loopNum',
				value : 5,blankText :"提示：周期内发送包数必填"
			},{
				width:150,labelWidth:40,
	    		xtype:'ajaxComboBox',editable : true,fieldLabel:'代理',name:'dccAgent',
				displayField: 'avp_code',valueField: 'avp_value',
				url:ctx+'/base/getList.do?sqlKey=AvpCodeDcc.selectAgent',
				queryMode:'remote',autoLoad:false,value:dccAgent
			},{
				width:200,labelWidth:40,
				xtype : 'textfield', fieldLabel:'区间', name:'ranges',
				value : '50-200-3000-8000',blankText :"提示,耗时区间必须填写以“-”隔开",
				maxLength : 64,
				regex : /^[0-9\-]*$/,
				regexText   : "耗时区间必须填写以“-”隔开"

				},{
				xtype : 'textVsButtonField', fieldLabel:'执行周期', name:'execCycle',
				plugins : [Ext.create('performance.plugins.ExecCyclePlugin')],
				emptyText : '默认执行一次',editable : true
			}]
    	});
		me.configPanel = configPanel;
		return configPanel;
	},
	createScanTree:function(){
		var me = this;
		var treePanel = Ext.create('component.tree.SimulatorTree', {
			region : 'west',
			width : 250,border:true,
			collapsible: false,
			headerPosition : 'top',
			title:'',
			paramMap:{
				checked:'2,3'
			},
			plugins : [new Ext.grid.plugin.CellEditing({
	            clicksToEdit: 1
	        })],
			style:'margin:5px;margin-right:-5px;',
			frame:false,
			sqlKey:'DopTestDcc.selectSingleSimulatorByParentId',
			columns : [{
				xtype : 'treecolumn',
				text : '场景',
				flex:1,
				sortable : true,
				dataIndex : 'text'
			}, {
				text : '数目',
				width : 50,
				dataIndex : 'rate',
				editor: {
                    xtype: 'numberfield',
					minValue:0,
					maxValue:100
                },
				sortable : true
			}],
			listeners:{
				load:function(tree,snode, records, successful, eOpts){
				},
				itemclick:function(tree,record,item,index,e,eOpts){
					if(record.data.depth=='3'){
						me.dccId = record.raw.id;
						Ext.Ajax.request({
							url:ctx+'/debug/loadTestDccContext.do',
							params:{dccId:record.raw.id},
							success:function(response){
								if(!Ext.isEmpty(response.responseText)){
									var result = Ext.decode(response.responseText);
									me.messageDetailViewer.getStore().proxy.extraParams.dccId=result['dcc_id'];
									me.messageDetailViewer.getStore().proxy.extraParams.content=result['content'];
									me.dcc=result;
									me.messageDetailViewer.getStore().load();
								}
							},
							failure:function(){
								return Ext.Msg.alert('提示','操作失败');
							}
						});
					}
				}
			}
		});
		return treePanel;
	}
});