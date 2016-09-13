var template = {
	edit:{
		title:'作品管理',
		width:650,
		height:480,
		sqlKey:'bookInfo.DB',
		idKey:'bizId',
		cascade:true,
		items:[
				{xtype:'hidden',name:'bizId',value:'NULL'},
				{xtype:'numberfield', fieldLabel:'projectId', name:'projectId',value:0},
				{xtype:'numberfield', fieldLabel:'项目人数', name:'projectNums',value:0},
				{xtype:'datetimefield', fieldLabel:'订单日期', name:'orderTime',value:'2016-03-05 22:29:00',format:'Y-m-d'},
				{xtype:'clearTextField', fieldLabel:'用户集合', name:'clientIds',value:''},
				{xtype:'clearTextField', fieldLabel:'状态', name:'state',value:''},
				{xtype:'clearTextField', fieldLabel:'状态图', name:'photos',value:''}
		]
	},
	search:[
		{xtype:'numberfield', fieldLabel:'projectId', name:'projectId',value:0},
		{xtype:'numberfield', fieldLabel:'项目人数', name:'projectNums',value:0},
		{xtype:'datetimefield', fieldLabel:'订单日期', name:'orderTime',value:'2016-03-05 22:29:00',format:'Y-m-d'},
		{xtype:'clearTextField', fieldLabel:'用户集合', name:'%clientIds%',value:''},
		{xtype:'clearTextField', fieldLabel:'状态', name:'%state%',value:''},
		{xtype:'clearTextField', fieldLabel:'状态图', name:'%photos%',value:''},
		{xtype:'numberfield', fieldLabel:'projectId', name:'projectId',value:0},
		{xtype:'numberfield', fieldLabel:'项目人数', name:'projectNums',value:0},
		{xtype:'datetimefield', fieldLabel:'订单日期', name:'orderTime',value:'2016-03-05 22:29:00',format:'Y-m-d'},
		{xtype:'clearTextField', fieldLabel:'用户集合', name:'%clientIds%',value:''},
		{xtype:'clearTextField', fieldLabel:'状态', name:'%state%',value:''},
		{xtype:'clearTextField', fieldLabel:'状态图', name:'%photos%',value:''},
		{xtype:'numberfield', fieldLabel:'projectId', name:'projectId',value:0},
		{xtype:'numberfield', fieldLabel:'项目人数', name:'projectNums',value:0},
		{xtype:'datetimefield', fieldLabel:'订单日期', name:'orderTime',value:'2016-03-05 22:29:00',format:'Y-m-d'},
		{xtype:'clearTextField', fieldLabel:'用户集合', name:'%clientIds%',value:''},
		{xtype:'clearTextField', fieldLabel:'状态', name:'%state%',value:''},
		{xtype:'clearTextField', fieldLabel:'状态图', name:'%photos%',value:''},
		
	],
	store:{
		fields:['bizId','projectId','projectNums','orderTime','clientIds','state','photos'],
		sqlKey:'bookInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: '主键',  dataIndex: 'bizId' ,flex:1,hidden:true},
		{ text: '',  dataIndex: 'projectId' ,flex:1},
		{ text: '项目人数',  dataIndex: 'projectNums' ,flex:1},
		{ text: '订单日期',  dataIndex: 'orderTime' ,flex:1},
		{ text: '用户集合',  dataIndex: 'clientIds' ,flex:1},
		{ text: '状态',  dataIndex: 'state' ,flex:1},
		{ text: '状态图',  dataIndex: 'photos' ,flex:1}
	]
}