var template = {
	edit:{
		title:'关于我们',
		width:400,
		height:480,
		sqlKey:'aboutUs.DB',
		id:'aboutUsId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'aboutUsId',
				value:null
			},
			{
				xtype:'textfield',
				fieldLabel:'版本号',
				width:350,
				name:'versionId',
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
				xtype:'textarea',
				fieldLabel:'内容',
				width:350,
				height:300,
				name:'commentInfo',
				value:''
			},
			{
				xtype:'textfield',
				fieldLabel:'备注',
				width:350,
				name:'remark',
				value:''
			}
		]
	},
	search:[
		{
			xtype:'clearTextField',
			fieldLabel:'内容',
			name:'%commentInfo%',
			value:''
		},
		{
			xtype:'clearTextField',
			fieldLabel:'版本号',
			name:'%versionId%',
			value:''
		},
		//{
		//	xtype:'ajaxComboBox',
		//	clear:true,
		//	fieldLabel:'类型',
		//	queryMode:'local',
		//	name:'%type%',
		//	value:'',
		//	data:[['商家端','server'],['用户端','client']]
		//},
		{
			xtype:'clearTextField',
			fieldLabel:'备注',
			name:'%remark%',
			value:''
		}
	],
	store:{
		fields:['aboutUsId','commentInfo','versionId','type','remark'],
		sqlKey:'aboutUs.selectList',
		type:'DB'
	},
	grid:[
		{ text: 'id',  dataIndex: 'aboutUsId' ,flex:1,hidden:true},
		{ text: '内容',  dataIndex: 'commentInfo' ,flex:1},
		{ text: '类型',  dataIndex: 'type' ,width:140,renderer:function(val){
			if(val==='server')return '商家端';
			else return '用户端';
		}},
		{ text: '版本号',  dataIndex: 'versionId' ,flex:1},
		{ text: '备注',  dataIndex: 'remark' ,flex:1}
	]
}