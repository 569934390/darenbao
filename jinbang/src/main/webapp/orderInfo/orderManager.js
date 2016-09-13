var template = {
	edit:{
		title:'订单详情',
		width:550,
		height:300,
		sqlKey:'orderInfo.DB',
		id:'orderId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'orderId',
				value:'NULL'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'名称',
				name:'orderName',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'订单编号',
				name:'orderCode',
				value:''
			},
			{
				xtype:'datefield',
				fieldLabel:'下单时间',
				name:'orderDate',
				value:'Fri Aug 05 2016 01:57:08 GMT+0800 (中国标准时间)',format:'Y-m-d'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'价格',
				name:'orderPrice',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'下单人姓名',
				name:'publisherName',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'订单状态',
				name:'orderStatus',
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=orderStatus&pid=0',
				queryMode:'remote'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'接单人姓名',
				name:'pickerName',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'接单人手机',
				name:'pickerMobile',
				value:''
			},
			{
				xtype:'datefield',
				fieldLabel:'接单时间',
				name:'pickDate',
				value:'Fri Aug 05 2016 01:57:08 GMT+0800 (中国标准时间)',format:'Y-m-d'
			},
			//{
			//	xtype:'clearTextField',
			//	fieldLabel:'留言',
			//	name:'remark',
			//	value:''
			//},
			{
				xtype:'numberfield',
				fieldLabel:'购买数量',
				name:'buyNum',
				value:0
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'订单类型',
				name:'orderType',
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=orderType&pid=0',
				queryMode:'remote'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'服务名称',
				name:'content',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'服务类别',
				name:'content',
				value:''
			}
		]
	},
	search:[
		{
			xtype:'clearTextField',
			fieldLabel:'下单人姓名',
			name:'%publisherName%',
			value:''
		},
		//{
		//	xtype:'clearTextField',
		//	fieldLabel:'名称',
		//	name:'%orderName%',
		//	value:''
		//},
		{
			xtype:'clearTextField',
			fieldLabel:'订单编号',
			name:'%orderCode%',
			value:''
		},
		{
			xtype:'clearTextField',
			fieldLabel:'联系电话',
			name:'%publisherMobile%',
			value:''
		},
		{
			xtype:'ajaxComboBox',
			fieldLabel:'订单状态',
			clear:true,
			name:'%orderStatus%',
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=orderStatus&pid=0',
			queryMode:'remote'
		},
		{
			xtype:'ajaxComboBox',
			fieldLabel:'订单类型',
			clear:true,
			name:'%orderType%',
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=orderType&pid=0',
			queryMode:'remote'
		},
		{
			xtype:'datefield',
			fieldLabel:'创建时间',
			name:'%searchBeginDate%',
			clear:true,
			format: 'Y-m-d',
			clear:true
		},
		{
			xtype:'datefield',
			fieldLabel:'至',
			name:'%searchEndDate%',
			clear:true,
			format: 'Y-m-d',
			clear:true
		}
	],
	store:{
		fields:['orderId','orderName','reqId','serviceId','orderCode','orderPrice','orderCityId','orderCityName','publisherId','publisherName','publisherMobile','validityPeriod','orderStatus','nativeProviceId','nativeProviceName','nativeCityId','nativeCityName','nativeAreaId','nativeAreaName','pickerId','pickerName','pickerMobile','pickDate','payCode','payChannel','remark','buyNum','orderType','orderDate','publishCategory','content','media'],
		sqlKey:'orderInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: 'id',  dataIndex: 'orderId' ,flex:1,hidden:true},
		{ text: '订单编号',  dataIndex: 'orderCode' ,flex:1},
		{ text: '订单类型',  dataIndex: 'orderType' ,flex:1,renderer:function(val){
			if(val==='1')return '同城订单';
			else if(val==='2')return '异城订单';
			else if(val==='3')return '五元橙子卡';
		}},
		{ text: '所属品牌运营官',  dataIndex: 'nativeProviceName' ,flex:1},
		{ text: '所属区域运营官',  dataIndex: 'nativeCityName' ,flex:1},
		{ text: '所属中国区运营官',  dataIndex: 'nativeAreaName' ,flex:1},
		{ text: '下单人姓名',  dataIndex: 'publisherName' ,flex:1},
		{ text: '发布人联系方式',  dataIndex: 'publisherMobile' ,flex:1},
		{ text: '产品名称',  dataIndex: 'orderName' ,flex:1},
		{ text: '价格',  dataIndex: 'orderPrice' ,flex:1},
		{ text: '订单状态',  dataIndex: 'orderStatus' ,flex:1,renderer:function(val){
				if(val==='1')return '待付款';
				else if(val==='2')return '已支付';
				else if(val==='3')return '服务中';
				else if(val==='4')return '待评价';
				else if(val==='5')return '已结束';
				else if(val==='-1')return '退款中';
				else if(val==='-2')return '已退款';
			}}
	]
}