var template = {
	edit:{
		title:'组织机构编辑',
		width:450,
		height:250,
		sqlKey:'DopPrivilegeDepartment',
		idKey:'id',
		cascade:true,
		subSqlKey:'DopPrivilegeResource.privilegeId,',
		items:[{xtype:'hidden',name:'id',value:'NULL'},
			{xtype:'hidden',name:'modifyTime',value:Ext.util.Format.date(new Date(),'Y-m-d H:i:s')},
			{xtype:'clearTextField', fieldLabel:'机构名称', name:'name',value:'',width:350},{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'状态',
    			queryMode:'local',
    			name:'state',
    			value:'00A',
				data:[['有效','00A'],['无效','00X']]
			},
			{xtype:'textarea', fieldLabel:'说明', name:'direction',value:'',width:350,height:100}]
		},
	search:[{
				xtype : 'clearTextField', fieldLabel:'机构名称', name:'%name%',
				emptyText : '名称'
			},{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'状态',
    			queryMode:'local',
    			name:'state',
				data:[['有效','00A'],['无效','00X']]
			}],
	store:{
		fields:['id','name','state','modifyTime','direction'],
		sqlKey:'DopPrivilegeDepartment.selectList'
	},
	grid:[{ text: '组织机构名称', dataIndex: 'name' ,width:150},
			{ text: '说明',  dataIndex: 'direction' ,flex:1},
			{ text: '修改时间',  dataIndex: 'modifyTime' ,width:140},
			{ text: '状态',  dataIndex: 'state' ,width:140,renderer:function(val){
				if(val==='00X')return '无效';
				else return '有效';
			}}]
}