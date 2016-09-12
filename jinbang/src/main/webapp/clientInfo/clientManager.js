var template = {
	edit:{
		title:'用户管理',
		width:800,
		height:308,
		sqlKey:'clientInfo.DB',
		idKey:'clientId',
		items:[
			{xtype:'hidden',name:'clientId',value:'NULL'},
			{xtype:'clearTextField', fieldLabel:'用户类型', name:'clientType',value:''},
			{xtype:'clearTextField', fieldLabel:'电话号码', name:'phone',value:''},
			{xtype:'datetimefield', fieldLabel:'创建时间', name:'createDate',value:'2016-03-05 01:33:04',format:'Y-m-d'},
			{xtype:'clearTextField', fieldLabel:'地区', name:'area',value:''},
			{xtype:'clearTextField', fieldLabel:'姓名', name:'name',value:''},
			{xtype:'clearTextField', fieldLabel:'邮箱', name:'email',value:''},
			{xtype:'numberfield', fieldLabel:'人数', name:'underNum',value:0},
			{xtype:'numberfield', fieldLabel:'服务经验值', name:'integral',value:0},
			{xtype:'numberfield', fieldLabel:'设计级别', name:'level',value:0},
			{xtype:'clearTextField', fieldLabel:'头像', name:'icon',value:''},
			{xtype:'clearTextField', fieldLabel:'状态', name:'state',value:''},
			{xtype:'datetimefield', fieldLabel:'修改时间', name:'modifyTime',value:'2016-03-05 01:33:04',format:'Y-m-d'},
			{xtype:'numberfield', fieldLabel:'修改者', name:'modifyer',value:0},
			{xtype:'clearTextField', fieldLabel:'名称', name:'relayName',value:''},
			{xtype:'clearTextField', fieldLabel:'认证名称', name:'creditName',value:''},
			{xtype:'clearTextField', fieldLabel:'认证号码', name:'creditNumber',value:''},
			{xtype:'clearTextField', fieldLabel:'证件照', name:'creditPhoto',value:''}
		]
	},
	search:[{
				xtype : 'clearTextField', fieldLabel:'用户名称', name:'%name%',
				emptyText : '名称'
			},{
				xtype : 'clearTextField', fieldLabel:'电话号码', name:'%phone%',
				emptyText : '无'
			},{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'用户类型',
    			queryMode:'local',
    			name:'clientType',
				data:[['普通用户','1'],['设计师','2'],['设计公司','3']]
			},{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'状态',
    			queryMode:'local',
    			name:'state',
				data:[['待审核','00B'],['已审核','00A'],['已删除','00C']]
			}],
	store:{
		fields:['clientId','clientType','phone','createDate','area','name','email','underNum','integral','level','icon','state','modifyTime','modifyer','relayName','creditName','creditNumber','creditPhoto'],
		sqlKey:'clientInfo.selectList',
		type:'DB'
	},
	grid:[{ text: '用户名称', dataIndex: 'name' ,width:150},
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