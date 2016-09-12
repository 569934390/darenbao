var template = {
	edit:{
		title:'举报详情',
		width:550,
		height:200,
		sqlKey:'reportInfo.DB',
		id:'reportId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'reportId',
				value:null
			},
			{
				xtype:'clearTextField',
				fieldLabel:'举报者id',
				name:'reporterId',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'举报者姓名',
				name:'reporterName',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'举报者类型',
				name:'reporterType',
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=reportUserType&pid=0',
				queryMode:'remote'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'手机号',
				name:'reporterMobile',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'举报对象',
				name:'beReporterName',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'举报描述',
				name:'description',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'举报时间',
				name:'reportDate',
				value:'CURRENT_TIMESTAMP'
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'举报者类型',
				name:'reporterType',
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=reportUserType&pid=0',
				queryMode:'remote'
			}
		]
	},
	search:[
		{
			xtype:'clearTextField',
			fieldLabel:'举报者姓名',
			name:'%reporterName%',
			value:''
		},
		{
			xtype:'ajaxComboBox',
			fieldLabel:'举报者类型',
			name:'%reporterType%',
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=reportUserType&pid=0',
			queryMode:'remote'
		},
		{
			xtype:'clearTextField',
			fieldLabel:'手机号',
			name:'%reporterMobile%',
			value:''
		},
		{
			xtype:'datefield',
			fieldLabel:'举报时间',
			name:'%searchBeginDate%',
			editable:false,
			clear:true,
			format: 'Y-m-d',
			clear:true
		},
		{
			xtype:'datefield',
			fieldLabel:'至',
			name:'%searchEndDate%',
			editable:false,
			clear:true,
			format: 'Y-m-d',
			clear:true
		}
	],
	store:{
		fields:['reportId','reporterId','reporterName','reporterType','reporterMobile','beReporterId','beReporterName','description','reportDate','operationType','remark'],
		sqlKey:'reportInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: '举报id',  dataIndex: 'reportId' ,flex:1,hidden:true},
		{ text: '举报者id',  dataIndex: 'reporterId' ,flex:1},
		{ text: '举报者姓名',  dataIndex: 'reporterName' ,flex:1},
		{ text: '举报者类型',  dataIndex: 'reporterType' ,flex:1},
		{ text: '举报者手机号',  dataIndex: 'reporterMobile' ,flex:1},
		{ text: '举报对象',  dataIndex: 'beReporterName' ,flex:1},
		{ text: '举报描述',  dataIndex: 'description' ,flex:1},
		{ text: '举报时间',  dataIndex: 'reportDate' ,flex:1},
		{ text: '操作类型',  dataIndex: 'operationType' ,flex:1},
		{ text: '备注',  dataIndex: 'remark' ,flex:1}
	]
}