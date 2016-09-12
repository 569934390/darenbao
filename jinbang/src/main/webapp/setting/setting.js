Ext.require(['component.view.grid.AutomationTestGrid','component.view.grid.AutomationGrid','component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	var tabPanel=Ext.create('Ext.tab.Panel',{
		region:'center',border:false,autoRender:true
	});
	var managerWin,mainForm,render,menu,chartPanel,chartXPanel,xs=ChartCfg.xs,itemNames=[],types=[],total=1;
	menu = {
		xs:xs
	};
	function reloadChart(){
		constans.initMenu(menu,function(obj){
			menu = obj;
			var names = [];
			for(var i=0;i<menu.ys.length;i++){
				names.push(menu.ys[i].name);
			}
			var code = '(function(menu,w,h){ var '+constans.generalChartString+'; return option})(menu,Ext.getBody().getWidth()/2,Ext.getBody().getHeight()-mainForm.getHeight()-60);';
			var option = eval(code);
			render = echarts.init(document.getElementById('chartDiv'));
			render.setOption(option);
		});
	}
	function reload(panel){
		var values = panel.down('form').getForm().getValues();
		itemNames[panel.itemId]=values.title;
		types[panel.itemId] = ChartCfg.typeMemu[values.type];
		drawChart(total,true);
	}
	function drawChart(num,rePanel){
		menu.ys = [];
		total = num;
		if(!rePanel){
			tabPanel.removeAll();
			tabPanel.add(chartXPanel = Ext.create('component.view.panel.ChartXPanel',{valStr:xs,border:false,title:'维度配置'}));
		}
		for(var i=1;i<num+1&&i<50;i++){
			var itemId = 'chartDetail_'+i;
			var temp;
			if(!types[itemId])
				types[itemId] = ChartCfg.typeMemu[i%ChartCfg.typeMemu.length];
			if(!rePanel){
				tabPanel.add(Ext.create('component.view.panel.ChartDetailPanel',{type:i%ChartCfg.typeMemu.length,handler:reload,border:false,title:itemNames[itemId]||'指标'+i,itemId:itemId}));
			}
			menu.ys.push({
				name:itemNames[itemId]||'指标'+i,
	            type:types[itemId],
	            data:ChartCfg.detailSql
			});
		}
		if(!rePanel){
			tabPanel.add(chartPanel = Ext.create('component.view.panel.ChartXPanel',{valStr:constans.generalChartString,border:false,title:'高级配置'}));
			tabPanel.setActiveTab(0);
		}
		
		reloadChart();
	}
	Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[{
			region:'west',
			layout:'border',
			tbar:['->',{
				text:'管理',
				iconCls:'toolbar-edit',
				handler:function(){
					if(!managerWin){
						managerWin = Ext.create('component.view.win.ChartManagerWin',{
							width:Ext.getBody().getWidth()*0.95,
							height:Ext.getBody().getHeight()*0.99,
							title:'图表管理'	,
							editHanlder:function(record){
								Ext.Persistent.post(ctx+'/chartController/load.do',{chartId:record.get('chartId')},function(chart){
									if(chart.type!='generalChart'){
										return Ext.Msg.alert('提示','不允许修改非generalChart图表');
									}
									drawChart(chart.chartDetails.length);
									mainForm.getForm().setValues(chart);
									tabPanel.setActiveTab(0);
									var panels = tabPanel.query('chartDetailPanel'),xPanels = tabPanel.query('chartXPanel');
									xPanels[0].down('form').getForm().setValues(chart.xs);
									xPanels[0].editor.setValue(chart.xs.sqlData);
									constans.generalChartString = chart.adviceConfig;
									tabPanel.setActiveTab(panels.length+1);
									xPanels[1].editor.setValue(chart.adviceConfig);
									for(var i=0;i<panels.length;i++){
										var itemId = 'chartDetail_'+(i+1);
										tabPanel.setActiveTab(i+1);
										panels[i].down('form').getForm().setValues(chart.chartDetails[i]);
										types[itemId] = ChartCfg.typeMemu[parseInt(chart.chartDetails[i].type)];
										itemNames[itemId] = chart.chartDetails[i].title;
										panels[i].setTitle(chart.chartDetails[i].title);
										panels[i].editor.setValue(chart.chartDetails[i].sqlData);
									}
									tabPanel.setActiveTab(0);
									reloadChart();
								},true);
							}
						});
					}
					managerWin.show();
				}
			},'-',{
				text:'刷新',
				iconCls:'toolbar-reLoad',
				handler:function(){
					panels = tabPanel.query('chartDetailPanel');
					for(var i=0;i<panels.length;i++){
						if(panels[i]&&panels[i].editor)
							menu.ys[i].data = panels[i].editor.getValue();
					}
					var names = [];
					for(var i=0;i<menu.ys.length;i++){
						names.push(menu.ys[i].name);
					}
					if(chartPanel&&chartPanel.editor)
						constans.generalChartString = chartPanel.editor.getValue();
					if(chartXPanel&&chartXPanel.editor)
						xs = chartXPanel.editor.getValue();
					menu.xs = xs;
					constans.initMenu(menu,function(obj){
						menu = obj;
						var code = '(function(menu,w,h){ var '+constans.generalChartString+'; return option})(menu,Ext.getBody().getWidth()/2,Ext.getBody().getHeight()-mainForm.getHeight()-60);';
						var option = eval(code);
						render.clear();
						render.dispose();
						render = null;
						render = echarts.init(document.getElementById('chartDiv'));
						render.setOption(option);
					});
				}
			},{
				text:'保存',
				iconCls:'toolbar-save',
				handler:function(){
					if(!mainForm.getForm().isValid()){
						return Ext.Msg.alert('提示','基本信息请填写');
					}
					var dopChart =  mainForm.getForm().getValues();
					dopChart.chartDetails = [];
					var panels = tabPanel.query('chartDetailPanel'),xPanels = tabPanel.query('chartXPanel');
					for(var i=0;i<panels.length;i++){
						var details = panels[i].down('form').getForm().getValues();
						dopChart.chartDetails.push(details);
						tabPanel.setActiveTab(i+1);
						if(!panels[i].down('form').getForm().isValid()){
							return Ext.Msg.alert('提示','指标信息请填写');
						}
						if(Ext.isEmpty(details.title)){
							return Ext.Msg.alert('提示','请填写指标名称');
						}
					}
					tabPanel.setActiveTab(0);
					dopChart.xs = xPanels[0].down('form').getForm().getValues();
					dopChart.adviceConfig = xPanels[1].editor.getValue()||constans.generalChartString;
					Ext.Persistent.post(webRoot+'chartController/save.do',{dopChartStr:Ext.encode(dopChart)},function(){
						Ext.Msg.alert('提示','保存成功!');
					});
				}
			}],
			width:Ext.getBody().getWidth()/2,
			items:[mainForm = Ext.create('component.view.form.FormPanel', {
				    region:'north',border:true,frame:true,
				    name:'mainForm',
				    items:[{xtype:'hidden',name:'chartId',value:''},
					{xtype:'hidden',name:'sort',value:0},
					{xtype:'hidden',name:'type',value:'generalChart'},
					{xtype:'hidden',name:'state',value:'00A'},{
						width:Ext.getBody().getWidth()/2-50,
						xtype : 'clearTextField', fieldLabel:'图表标题', name:'title',allowBlank :false,
						emptyText  : '图表标题',blankText :"提示：图表标题必填"
					},{
						width:Ext.getBody().getWidth()/4-20,
						xtype : 'clearTextField', fieldLabel:'副标题', name:'subTitle',allowBlank :false,
						emptyText  : '副标题',blankText :"提示：图表标题必填"
					},{
						width:Ext.getBody().getWidth()/4-45,
						xtype:'ajaxComboBox',
						editable : true,
						queryMode:'local',
						fieldLabel : '数据源数',
						name:'sourceCount',
						displayField: 'name',
						valueField: 'value',
						data:[['一组数据',1],['二组数据',2],['三组数据',3],['四组数据',4],['五组数据',5],['六组数据',6]],
						value:1,
						listeners:{
							select:function(combo){
								// 图表清空-------------------
								render.clear();
								// 图表释放-------------------
								render.dispose();
								render = null;
								drawChart(combo.getValue());
							}
						}
					}]
			  }),{
				xtype:'box',
				region:'center',
				style:'background:#fff;margin:10px;',
				listeners:{
					afterrender:function(){
						this.update('<div id="chartDiv" style="width:100%;height:100%;"></div>',false,function(){
							setTimeout(function(){
								Ext.syncRequire('component.chart.generalChart');
								drawChart(1);
							},800);
						});
					}
				}
			}]
		},tabPanel]
	});
});
