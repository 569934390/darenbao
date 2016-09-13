var template = {
	edit:{
		title:'返利规则',
		width:1150,
		height:450,
		sqlKey:'orderReturnRule.DB',
		id:'returnRuleId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'returnRuleId',
				value:'NULL'
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'订单类型',
				name:'orderCategory',
				editable:false,
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=orderType&pid=0',
				value:'1',
				queryMode:'remote'
			},
			//{
			//	xtype:'clearTextField',
			//	fieldLabel:'中国运营商比例',
			//	name:'chinaRebate',
			//	value:0
			//},
			{
				xtype:'fieldset',
				title:'交易服务抽比',
				width:1100,
				height:100,
				name:'orderRule',
				style:'margin—top:10px',
				layout : 'column',
				items:[
					{
						xtype:'clearTextField',
						fieldLabel:'平台分红',
						columnWidth:.5,
						name:'platformRebate',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						columnWidth:.5,
						name:'platformRebateAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'渠道收入',
						columnWidth:.5,
						name:'channelRebate',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						columnWidth:.5,
						name:'channelRebateAct',
						value:0
					}
				]
			},
			{
				xtype: 'fieldset',
				title: '买方',
				width: 550,
				height: 200,
				name: 'buyer',
				layout: 'column',
				items: [
					{
						xtype:'clearTextField',
						fieldLabel:'中国区运营官',
						name:'sellerChinaZoneAgent',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'sellerChinaZoneAgentAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'地区运营官',
						name:'sellerAreaZoneAgent',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'sellerAreaZoneAgentAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'站长',
						name:'sellerStationmaster',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'sellerStationmasterAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'品牌运营官',
						name:'sellerBandAgent',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'sellerBandAgentAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'一级分销会员',
						name:'sellerFirstLevel',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'sellerFirstLevelAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'二级分销会员',
						name:'sellerSecondLevel',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'sellerSecondLevelAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'三级分销会员',
						name:'sellerThirdLevel',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'sellerThirdLevelAct',
						value:0
					}
				]
			},
			{
				xtype: 'fieldset',
				title: '卖方',
				width: 550,
				height: 200,
				name: 'seller',
				layout: 'column',
				items: [
					{
						xtype:'clearTextField',
						fieldLabel:'中国区运营官',
						name:'buyerChinaZoneAgent',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'buyerChinaZoneAgentAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'地区运营官',
						name:'buyerAreaZoneAgent',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'buyerAreaZoneAgentAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'站长',
						name:'buyerStationmaster',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'buyerStationmasterAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'品牌运营官',
						name:'buyerBandAgent',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'buyerBandAgentAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'一级分销会员',
						name:'buyerFirstLevel',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'buyerFirstLevelAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'二级分销会员',
						name:'buyerSecondLevel',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'buyerSecondLevelAct',
						value:0
					},
					{
						xtype:'clearTextField',
						fieldLabel:'三级分销会员',
						name:'buyerThirdLevel',
						value:0
					},
					{
						xtype:'clearTextField',
						labelAlign : 'right',
						fieldLabel:'%        即',
						name:'buyerThirdLevelAct',
						value:0
					}
				]
			}
		]
	},
	search:[
		{
			xtype:'ajaxComboBox',
			fieldLabel:'订单类型',
			name:'%orderCategory%',
			editable:false,
			clear:true,
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=orderType&pid=0',
			value:'',
			queryMode:'remote'
		}
	],
	storeInstance:Ext.create('Ext.data.Store', {
		fields: ['returnRuleId','orderCategory','platformRebate','channelRebate','sellerChinaZoneAgent','sellerAreaZoneAgent',
			'sellerStationmaster','sellerBandAgent','sellerFirstLevel','sellerSecondLevel','sellerThirdLevel',
		'buyerChinaZoneAgent','buyerAreaZoneAgent','buyerStationmaster','buyerBandAgent','buyerFirstLevel','buyerSecondLevel','buyerThirdLevel'],
		proxy: {
			type: 'ajax',
			url: ctx + '/rule/getRulePage.do',
			reader: {
				type: 'json',
				root: 'result',
				totalProperty: 'totalCount'
			},
			extraParams: {
				conditionsStr: Ext.encode({staffId: session.user.id})
			}
		},
		pageSize: 50,
		autoLoad: true
	}),
	grid:[
		{ text: 'id',  dataIndex: 'returnRuleId' ,flex:1,hidden:true},
		{ text: '订单类型',  dataIndex: 'orderCategory' ,flex:1,renderer:function(val){
			if(val==='1')return '会员订单';
			else if(val==='2')return '5元橙子订单';
		}},
		{ text: '平台比例',  dataIndex: 'platformRebate' ,flex:1},
		{ text: '渠道比例',  dataIndex: 'channelRebate' ,flex:1}
		//{ text: '城市品牌官比例',  dataIndex: 'cityRebate' ,flex:1},
		//{ text: '一级分销会员比例',  dataIndex: 'firstRebate' ,flex:1},
		//{ text: '二级分销会员比例',  dataIndex: 'secondRebate' ,flex:1},
		//{ text: '三级分销会员比例',  dataIndex: 'thirdRebate' ,flex:1},
		//{ text: '平台比例',  dataIndex: 'platformRebate' ,flex:1}
	]
}