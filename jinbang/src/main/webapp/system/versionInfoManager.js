var template = {
	edit:{
		title:'版本管理表',
		width:550,
		height:200,
		sqlKey:'versionInfo.DB',
		idKey:'id',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'id',
				value:'NULL'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'版本号',
				name:'versionCode',
				value:''
			},
			//{
			//	xtype:'ajaxComboBox',
			//	fieldLabel:'类型',
			//	queryMode:'local',
			//	name:'type',
			//	value:'server',
			//	data:[['商家端','server'],['用户端','client']]
			//},
			{
				xtype:'clearTextField',
				fieldLabel:'当前版本',
				name:'versionCurrent',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'版本名称',
				name:'versionName',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'更新内容',
				name:'updateContent',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'url',
				name:'url',
				value:''
			}
		]
	},
	search:[
		{
			xtype:'clearTextField',
			fieldLabel:'版本号',
			name:'%versionCode%',
			value:''
		},
		{
			xtype:'clearTextField',
			fieldLabel:'版本名称',
			name:'%versionName%',
			value:''
		}

	],
	store:{
		fields:['id','versionCode','versionCurrent','type','versionName','updateContent','url'],
		sqlKey:'versionInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: '',  dataIndex: 'id' ,flex:1,hidden:true},
		{ text: '版本号',  dataIndex: 'versionCode' ,flex:1},
		{ text: '当前版本',  dataIndex: 'versionCurrent' ,flex:1},
		{ text: '版本名称',  dataIndex: 'versionName' ,flex:1},
		{ text: '类型',  dataIndex: 'type' ,width:140,renderer:function(val){
			if(val==='server')return '商家端';
			else return '用户端';
		}},
		{ text: '更新内容',  dataIndex: 'updateContent' ,flex:1},
		{ text: 'url',  dataIndex: 'url' ,flex:1}
	]
}