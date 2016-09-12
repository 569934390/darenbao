var projectType = [['建筑设计','001'],['幕墙设计','002'],['景观设计','003'],['夜景设计','004'],['其它','004']];
var template = {
	tbar:['-',{text:'上传',iconCls : 'alarmRelateInfo',handler:function(){
		var generalGird = template.generalGird;
		var selectRecords = generalGird.getSelectionModel().getSelection();
		if(selectRecords.length==0){
			return Ext.Msg.alert('提示','请选择记录');
		}
		if(selectRecords.length>1){
			return Ext.Msg.alert('提示','只能选择一条');
		}
		var uploadPanel = Ext.create('Ext.ux.uploadPanel.UploadPanel',{
			addFileBtnText : '选择文件...',
			uploadBtnText : '上传',
			removeBtnText : '移除所有',
			cancelBtnText : '取消上传',
			file_size_limit : 10000,//MB
			post_params:{'userId':session.user.id,projectId:selectRecords[0].get('projectId')},
			upload_url : webRoot + 'photo/webUpload.do'
		});
		var pwin = new Ext.Window({
			title: '上传照片'
			, width: 680
			, constrain:true
			, height: 330
			, resizable: true
			, items: [ uploadPanel ]
		}); // End Window

		pwin.show();
	}}
	,'-',{text:'相册照片',iconCls : 'toolbar-customer',handler:function(){
		var generalGird = template.generalGird;
		var selectRecords = generalGird.getSelectionModel().getSelection();
		if(selectRecords.length==0){
			return Ext.Msg.alert('提示','请选择记录');
		}
		if(selectRecords.length>1){
			return Ext.Msg.alert('提示','只能选择一条');
		}
		var pwin = new Ext.Window({
			title: '查看照片'
			, width: '80%'
			, height: '90%'
			,tbar:[{
				text:'删除',iconCls : 'toolbar-del',handler:function(){
					var photoViewList = pwin.down('photoViewList');
					var records=photoViewList.getSelectionModel().getSelection(),idStr = [];
					for(var i=0;i<records.length;i++){
						idStr.push(records[i].get('id'));
					}
					Ext.Persistent.post(ctx+'/photo/deletePhotos.do',{ids:idStr.join(',')},function(){
						Ext.Msg.alert('提示','删除成功!');
						photoViewList.getStore().load();
						photoViewList.getSelectionModel().deselectAll();
					});
				}
			}]
			, constrain:true
			, resizable: false
			, items: [Ext.create('component.view.panel.PhotoViewList',{
				albumId:selectRecords[0].get('id'),
				fields:['id','name','fileName','remark','albumId','createTime','userId','userType'],
				sqlKey:'photoInfo.selectList',type:'DB'

			})]
		});
		pwin.show();
	}}],
	edit:{
		title:'项目管理',
		width:600,
		height:282,
		sqlKey:'projectInfo.DB',
		idKey:'projectId',
		cascade:true,
		beforeSubmit:function(entity){
			if(entity.projectId=='NULL'){
				entity.createDate = new Date();
			}
			return entity;
		},
		items:[
		{
				xtype:'hidden',
				name:'projectId',
				value:'NULL'
		   },
		   {
				xtype:'clearTextField',
				fieldLabel:'项目编号',
				name:'projectNumber', allowBlank : false,
				value:''
		   },
		   {
				xtype:'ajaxComboBox',
				fieldLabel:'项目类型',
				queryMode:'local',
				name:'type',
				value:'001',
				allowBlank : false,
				data:projectType
		   },
		   {
				xtype:'clearTextField',
				fieldLabel:'项目名称', allowBlank : false,
				name:'projectName',
				value:''
		   },
		   {
				xtype:'ajaxComboBox',
				fieldLabel:'状态',
				queryMode:'local',
				name:'state',
				value:'00B',
				allowBlank : false,
				data:[['待审核','00B'],['已审核','00A'],['已删除','00C']]
		   },
		   {
				xtype:'hidden',
				name:'modifyTime',
				value:new Date(),format:'Y-m-d'
		   },
		   {
				xtype:'clearTextField',
				fieldLabel:'内容', allowBlank : false,
				name:'context',
				value:''
		   },
		   {
				xtype:'clearTextField',
				fieldLabel:'封面',width:470,
				name:'photo',
				value:''
		   },
		   {
				xtype:'textarea',
				fieldLabel:'项目简介',
				name:'descField',
				value:'',width:470,height:50
		   }
		]
	},
	search:[{
				xtype : 'clearTextField', fieldLabel:'项目名称', name:'%projectName%',
				emptyText : '名称'
			},{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'状态',
    			queryMode:'local',
    			name:'state',
				data:[['待审核','00B'],['已审核','00A'],['已删除','00C'] ]
			}],
	store:{
		fields:['projectId','projectNumber','type','projectName','descField','state','modifyTime','createDate','context','photo'],
		sqlKey:'projectInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: '主键',  dataIndex: 'projectId' ,flex:1,hidden:true},
		{ text: '项目编号',  dataIndex: 'projectNumber' ,flex:1},
		{ text: '项目类型',  dataIndex: 'type' ,flex:1,renderer:function(val){
			for (var i = projectType.length - 1; i >= 0; i--) {
				if(projectType[i][1]==val){
					return projectType[i][0];
				}
			};
		}},
		{ text: '项目名称',  dataIndex: 'projectName' ,flex:1},
		{ text: '项目简介',  dataIndex: 'descField' ,flex:1},
		{ text: '状态',  dataIndex: 'state' ,flex:1,renderer:function(val){
			if (val=='00A') {
				return '已审核';
			};
			if (val=='00B') {
				return '待审核';
			};
			if (val=='00C') {
				return '已删除';
			};
		}},
		{ text: '修改时间',  dataIndex: 'modifyTime' ,flex:1},
		{ text: '创建日期',  dataIndex: 'createDate' ,flex:1},
		{ text: '内容',  dataIndex: 'context' ,flex:1},
		{ text: '封面',  dataIndex: 'photo' ,flex:1}
	]
}