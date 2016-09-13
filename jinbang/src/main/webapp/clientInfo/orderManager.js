var template = {
	edit:{
		title:'订单管理',
		width:650,
		height:180,
		sqlKey:'orderInfo.DB',
		idKey:'orderId',
		cascade:true,
		items:[
			{xtype:'hidden',name:'orderId',value:'NULL'},
			{xtype:'datetimefield', fieldLabel:'订单时间', name:'orderTime',value:'2016-03-05 13:03:02',format:'Y-m-d'},
			{xtype:'numberfield', fieldLabel:'clientId', name:'clientId',value:0},
			{xtype:'numberfield', fieldLabel:'projectId', name:'projectId',value:0},
			{xtype:'clearTextField', fieldLabel:'状态', name:'state',value:''}
		]
	},
	search:[{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'状态',
    			queryMode:'local',
    			name:'state',
				data:[['待审核','00B'],['已审核','00A'],['已删除','00C']]
			}],
	store:{
		fields:['orderId','orderTime','clientId','projectId','state'],
		sqlKey:'orderInfo.selectList',
		type:'DB'
	},
	grid:[{ text: '订单时间', dataIndex: 'name' ,width:150},
			{ text: '用户类型',  dataIndex: 'clientType' ,flex:1},
			{ text: '电话号码',  dataIndex: 'phone' ,flex:1},
			{ text: '创建时间',  dataIndex: 'createDate' ,flex:1},
			{ text: '地区',  dataIndex: 'area' ,flex:1},
			{ text: '邮箱',  dataIndex: 'email' ,flex:1},
			{ text: '人数',  dataIndex: 'underNum' ,flex:1},
			{ text: '服务经验值',  dataIndex: 'integral' ,flex:1},
			{ text: '设计级别',  dataIndex: 'level' ,flex:1},
			{ text: '头像',  dataIndex: 'icon' ,flex:1},
			{ text: '状态',  dataIndex: 'state' ,flex:1},
			{ text: '说明',  dataIndex: 'direction' ,flex:1},
			{ text: '修改时间',  dataIndex: 'modifyTime' ,width:140},
			{ text: '状态',  dataIndex: 'state' ,width:140,renderer:function(val){
				if(val==='00X')return '无效';
				else return '有效';
			}}]
}