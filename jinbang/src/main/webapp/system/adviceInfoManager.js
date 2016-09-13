var template = {
	edit:{
		title:'用户反馈',
		width:400,
		height:480,
		sqlKey:'adviceInfo.DB',
		id:'adviceId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'adviceId',
				value:'NULL'
			},
			//{
			//	xtype:'ajaxComboBox',
			//	fieldLabel:'app类型',
			//	queryMode:'local',
			//	width:350,
			//	name:'appType',
			//	value:'',
			//	data:[['商家端','server'],['用户端','client']]
			//},
			{
				xtype:'textfield',
				fieldLabel:'版本号',
				width:350,
				name:'version',
				value:''
			},
			{
				xtype:'textfield',
				fieldLabel:'反馈用户账号',
				name:'adverUserName',
				value:''
			},
			{
				xtype:'textfield',
				fieldLabel:'反馈用户电话',
				width:350,
				name:'adverUserTel',
				value:''
			},
			{
				xtype:'textarea',
				fieldLabel:'反馈内容',
				width:350,
				height:300,
				name:'adverContent',
				value:''
			}
		]
	},
	search:[
		//{
		//	xtype:'ajaxComboBox',
		//	clear:true,
		//	fieldLabel:'app类型',
		//	queryMode:'local',
		//	name:'%appType%',
		//	value:'',
		//	data:[['商家端','server'],['用户端','client']]
		//},
		{
			xtype:'textfield',
			fieldLabel:'版本号',
			name:'version',
			value:''
		},
		{
			xtype:'textfield',
			fieldLabel:'用户账号',
			name:'%adverUserName%',
			value:''
		},
		{
			xtype:'textfield',
			fieldLabel:'用户电话',
			name:'%adverUserTel%',
			value:''
		},
		{
			xtype:'textfield',
			fieldLabel:'反馈内容',
			name:'adverContent',
			value:''
		}
	],
	store:{
		fields:['adviceId','appType','version','adverUserName','adverUserTel','adverContent'],
		sqlKey:'adviceInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: 'id',  dataIndex: 'adviceId' ,flex:1,hidden:true},
		{ text: 'app类型',  dataIndex: 'appType' ,width:140,renderer:function(val){
			if(val==='server')return '商家端';
			else return '用户端';
		}},
		{ text: '版本号',  dataIndex: 'version' ,flex:1},
		{ text: '反馈用户账号',  dataIndex: 'adverUserName' ,flex:1},
		{ text: '反馈用户电话',  dataIndex: 'adverUserTel' ,flex:1},
		{ text: '反馈内容',  dataIndex: 'adverContent' ,flex:1}
	]
}