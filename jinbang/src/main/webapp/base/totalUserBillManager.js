var template = {
	edit:{
		title:'用户总账单表',
		width:530,
		height:200,
		sqlKey:'totalUserBill.DB',
		id:'totalBillId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'totalBillId',
				value:'NULL'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'余额',
				name:'balance',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'收入总金额',
				name:'totalIncomeMoney',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'可转出金额',
				name:'transferableMoney',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'已结算金额',
				name:'settlementedMoney',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'待结算金额',
				name:'toSettlementMoney',
				value:''
			},
		]
	},
	search:[
		{
			xtype:'clearTextField',
			fieldLabel:'余额',
			name:'balance',
			value:''
		}
	],
	store:{
		fields:['totalBillId','userId','balance','totalIncomeMoney','transferableMoney','settlementedMoney','toSettlementMoney','createTime','lastSettlementTime'],
		sqlKey:'totalUserBill.selectList',
		type:'DB'
	},
	grid:[
		{ text: '主键id',  dataIndex: 'totalBillId' ,flex:1,hidden:true},
		{ text: '余额',  dataIndex: 'balance' ,flex:1},
		{ text: '收入总金额',  dataIndex: 'totalIncomeMoney' ,flex:1},
		{ text: '可转出金额',  dataIndex: 'transferableMoney' ,flex:1},
		{ text: '已结算金额',  dataIndex: 'settlementedMoney' ,flex:1},
		{ text: '待结算金额',  dataIndex: 'toSettlementMoney' ,flex:1}
	]
}