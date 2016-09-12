var template = {
	edit:{
		title:'电子协议信息表',
		width:400,
		height:480,
		sqlKey:'agreementInfo.DB',
		idKey:'id',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'id',
				value:'0'
			},
			{
				xtype:'textfield',
				fieldLabel:'发布者',
				width:350,
				name:'releaseUser',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'类型',
				queryMode:'local',
				name:'type',
				value:'server',
				data:[['我的、设置','server'],['登陆、注册','client']]
			},
			{
				xtype:'datefield',
				fieldLabel:'发布时间',
				width:350,
				name:'releaseDate',
				editable:false,
				value: new Date(),
				format: 'Y-m-d'
			},
			{
				xtype:'textfield',
				fieldLabel:'版本号',
				width:350,
				name:'versionId',
				value:''
			},
			{
				xtype:'textarea',
				fieldLabel:'协议内容',
				width:350,
				height:250,
				name:'content',
				value:''
			}
		]
	},
	search:[
		{
			xtype:'textfield',
			fieldLabel:'发布者',
			name:'%releaseUser%',
			value:''
		},
		{
			xtype:'ajaxComboBox',
			clear:true,
			fieldLabel:'类型',
			queryMode:'local',
			name:'%type%',
			value:'',
			data:[['我的、设置','server'],['登陆、注册','client']]
		},
		{
			xtype:'datefield',
			fieldLabel:'发布时间',
			name:'%releaseDate%',
			editable:false,
			value: '',
			format: 'Y-m-d'
		},
		{
			xtype:'textfield',
			fieldLabel:'版本号',
			name:'%versionId%',
			value:''
		},
		{
			xtype:'textfield',
			fieldLabel:'协议内容',
			name:'%content%',
			value:''
		}
	],
	store:{
		fields:['id','releaseUser','releaseDate','versionId','type','content'],
		sqlKey:'agreementInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: '',  dataIndex: 'id' ,flex:1,hidden:true},
		{ text: '发布者',  dataIndex: 'releaseUser' ,flex:1},
		{ text: '类型',  dataIndex: 'type' ,width:140,renderer:function(val){
			if(val==='server')return '我的、设置';
			else return '登陆、注册';
		}},
		{ text: '发布时间',  dataIndex: 'releaseDate' ,flex:1},
		{ text: '版本号',  dataIndex: 'versionId' ,flex:1},
		{ text: '协议内容',  dataIndex: 'content' ,flex:1}
	]
}