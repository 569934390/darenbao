var template = {
	edit:{
		title:'账单记录表',
		width:650,
		height:480,
		sqlKey:'billRecord.DB',
		id:'recordId',
		cascade:true,
		items:[
			{
				xtype:'clearTextField',
				fieldLabel:'用户名称',
				name:'agentName',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'用户角色',
				name:'roleType',
				readOnly:true,
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=roleType&pid=0',
				queryMode:'remote'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'订单编号',
				name:'orderCode',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'交易金额',
				name:'tradingAmount',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'交易时间',
				name:'tradingDate',
				format:'Y-m-d'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'结算时间',
				name:'settlementDate',
				format:'Y-m-d'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'结算金额',
				name:'settlementMoney',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'操作类型',
				name:'operationCategory',
				readOnly:true,
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=operationCategory&pid=0',
				queryMode:'remote'
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'结算状态',
				readOnly:true,
				name:'settlementStatus',
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=settlementStatus&pid=0',
				queryMode:'remote'
			}
		]
	},
	search:[
		{
			xtype:'hidden',
			name:'%roleType%',
			value:'0'
		},
		{
			xtype:'clearTextField',
			fieldLabel:'角色名称',
			name:'%userName%',
			value:''
		},
		{
			xtype:'clearTextField',
			fieldLabel:'订单编号',
			name:'%orderCode%',
			value:''
		},
		{
			xtype:'datefield',
			fieldLabel:'交易时间',
			name:'tradingDate',
			value:'',
			format:'Y-m-d'
		},
		{
			xtype:'ajaxComboBox',
			clear:true,
			fieldLabel:'操作类型',
			name:'%operationCategory%',
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=operationCategory&pid=0',
			queryMode:'remote'
		},
		{
			xtype:'ajaxComboBox',
			clear:true,
			fieldLabel:'结算状态',
			name:'%settlementStatus%',
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=settlementStatus&pid=0',
			queryMode:'remote'
		}
	],
	store:{
		fields:['recordId','userName','agentName','payUserId','receivingUserId','roleType','orderCode','tradingAmount','tradingDate','settlementDate','settlementMoney','remark','operationCategory','enterCardId','cashStatus','cashDate','balanceDate','cardDate','settlementStatus'],
		sqlKey:'billRecord.selectList',
		type:'DB'
	},
	grid:[
		{ text: '主键id',  dataIndex: 'recordId' ,flex:1,hidden:true},
		{ text: '用户名称',  dataIndex: 'agentName' ,flex:1},
		{ text: '角色类型',  dataIndex: 'roleType' ,flex:1,renderer:function(val){
			if(val==='1')return '用户';
			else if(val==='0')return '运营商';
		}},
		{ text: '订单编号',  dataIndex: 'orderCode' ,flex:1},
		{ text: '交易金额',  dataIndex: 'tradingAmount' ,flex:1},
		{ text: '交易时间',  dataIndex: 'tradingDate' ,flex:1},
		{ text: '结算时间',  dataIndex: 'settlementDate' ,flex:1},
		{ text: '结算金额',  dataIndex: 'settlementMoney' ,flex:1},
		{ text: '操作类型',  dataIndex: 'operationCategory' ,flex:1,renderer:function(val){
			if(val==='1')return '交易';
			else if(val==='2')return '返佣';
			else if(val==='3')return '提现';
			else if(val==='-1')return '退款';
		}},
		{ text: '结算状态',  dataIndex: 'settlementStatus' ,flex:1,renderer:function(val){
			if(val==='0')return '未结算';
			else if(val==='1')return '待结算';
			else if(val==='2')return '已结算';
		}}
	]
}