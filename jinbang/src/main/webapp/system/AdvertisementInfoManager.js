var template = {
	edit:{
		title:'广告管理',
		width:400,
		height:250,
		beforeSubmit:function(entity,me){
			me.down('form').getForm().submit({
				//上传图片
				url:webRoot + 'advertisementInfo/uploadPhoto.do',
				params: {aid:entity.id},
				waitMsg: '正在上传文件',

				success: function(fp, o) {
					//选择上传新的图标
					//图标上传成功，执行表的操作
					//me.down('form').getForm().findField("file").clearInvalid();
					//me.down('form').getForm().findField("file").allowBlank = true;
					//entity.iconPath = o.result.iconpath;
					entity.adverPhotos = o.result.iconname;

					for(var key in entity){
						if(entity[key]==null||entity[key]==''){
							delete entity[key]
						}
					}
					//保存商户记录
					Ext.Ajax.request({
						url: ctx + '/advertisementInfo/addOrUpdate.do',
						method: "post",
						params: entity,
						success: function (response) {
							if (!Ext.isEmpty(response.responseText)) {
								var result = Ext.decode(response.responseText);
								if (result.success) {
									Ext.Msg.alert('提示', '保存成功！')
									me.callback();
								}
							}
						},
						failure: function () {
							return Ext.Msg.alert('提示', '操作失败');
						}
					});
				},
				failure:function(fp, o){
					// 修改
					//if(me.winType == WEBConstants.ACTIONTYPE.EDIT){
					if(me.action == 'update' || me.action == 'save'){
						//保存商户记录
						Ext.Ajax.request({
							url: ctx + '/advertisementInfo/addOrUpdate.do',
							method: "post",
							params: entity,
							success: function (response) {
								if (!Ext.isEmpty(response.responseText)) {
									var result = Ext.decode(response.responseText);
									if (result.success) {
										Ext.Msg.alert('提示', '保存成功！')
										me.callback();
									}else{
										Ext.Msg.alert('提示', '操作失败');
									}
								}
							},
							failure: function () {
								Ext.Msg.alert('提示', '操作失败');
							}
						});
					}else{
						Ext.Msg.alert("提示","上传文件失败，请重新上传");
					}
				}
			});

			return false;;
		},
		sqlKey:'advertisementInfo.DB',
		id:'advertisementId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'advertisementId',
				value:null
			},
			{
				xtype:'textfield',
				width:350,
				fieldLabel:'广告标题',
				name:'adverTitle',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'app类型',
				queryMode:'local',
				width:350,
				name:'adverType',
				value:'',
				data:[['商家端','1'],['用户端','2']]
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'是否启用',
				width:350,
				name:'adverStatus',
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=istrue&pid=0',
				queryMode:'remote'
			},
			{
				xtype:'textfield',
				width:350,
				fieldLabel:'请求地址',
				name:'url',
				value:''
			},
			{
				xtype: 'fileuploadfield',
				id: 'form-file',
				width:250,
				emptyText: '选择图片',
				fieldLabel: '广告图片',
				name: 'adverPhotos',
				buttonText: '选择',
				buttonCfg: {
					iconCls: 'upload-icon'
				}
			},
		]
	},
	search:[
		{
			xtype:'textfield',
			fieldLabel:'广告标题',
			name:'%adverTitle%',
			value:''
		},
		{
			xtype:'ajaxComboBox',
			clear:true,
			fieldLabel:'app类型',
			queryMode:'local',
			name:'%adverType%',
			value:'',
			data:[['商家端','1'],['用户端','2']]
		},
		{
			xtype:'ajaxComboBox',
			fieldLabel:'是否启用',
			clear:true,
			name:'adverStatus',
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=istrue&pid=0',
			queryMode:'remote'
		},
		{
			xtype:'textfield',
			fieldLabel:'请求地址',
			name:'%url%',
			value:''
		}
	],
	store:{
		fields:['advertisementId','adverTitle','adverType','adverPhotos','adverStatus','url'],
		sqlKey:'advertisementInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: 'id',  dataIndex: 'advertisementId' ,flex:1,hidden:true},
		{ text: '广告标题',  dataIndex: 'adverTitle' ,flex:1},
		{ text: 'app类型',  dataIndex: 'adverType' ,width:140,renderer:function(val){
			if(val==='1')return '商家端';
			else return '用户端';
		}},
		{ text: '广告图片',  dataIndex: 'adverPhotos' ,flex:1},
		{ text: '是否启用',  dataIndex: 'adverStatus' ,flex:1,renderer:function(val){
			if(val==='0')return '禁用';
			else return '启用';
		}},
		{ text: '请求地址',  dataIndex: 'url' ,flex:1}
	]
}