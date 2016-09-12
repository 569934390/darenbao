var template = {
	type:'userManager',
	tbar:['-',{text:'角色配置',iconCls : 'alarmRelateInfo',handler:Privilege.roleCfg},
		{text:'权限配置',iconCls : 'alarmRelateInfo',handler:Privilege.privilegeCfg},
		{text:'上传图片',iconCls : 'alarmRelateInfo',handler:function(){
			var win = Ext.create('component.public.ImageUploadWindow',{
				closeAction:'destory',
				title:'图片上传'
			});
			win.show();
		}},
		{text:'经纬度选择器',iconCls : 'alarmRelateInfo',handler:function(){
			var win = Ext.create('component.public.LocalSelectWindow',{
				closeAction:'destory',
				title:'经纬度选择器',
				callback:function(localInofo){
					console.info(localInofo);
					win.close();
				}
			});
			win.show();
		}}],
	edit:{
		title:'用户编辑',
		width:800,
		height:450,
		beforeSubmit:function(entity){
			if(entity.password.length==32){
				entity.password = 'NULL';
			}
			else{
				entity.password = MD5(entity.password);
			}
			return entity;
		},
		sqlKey:'DopPrivilegeUser',
		idKey:'id',
		items:[{xtype:'hidden',name:'id',value:'NULL'},
			{xtype:'hidden',name:'modifyTime',value:Ext.util.Format.date(new Date(),'Y-m-d H:i:s')},
			{xtype:'hidden',name:'modifyPasswordTime',value:Ext.util.Format.date(new Date(),'Y-m-d H:i:s')},
			{xtype:'clearTextField', fieldLabel:'用户名称', name:'name',value:''},{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'组织机构',
    			name:'department',
    			displayField: 'name',
				valueField: 'id',
				url:ctx+'/base/getList.do?sqlKey=DopPrivilegeDepartment.selectList',
				queryMode:'remote'
			},
			{xtype:'clearTextField', fieldLabel:'真实名称', name:'realName',value:''},
			{xtype:'clearTextField', fieldLabel:'密码', name:'password',inputType:'password',value:''},
			{xtype:'clearTextField', fieldLabel:'电话号码', name:'phone',value:''},
			{xtype:'clearTextField', fieldLabel:'邮箱', name:'email',value:''},
			{xtype:'clearTextField', fieldLabel:'地址', name:'address',value:'',width:700},
			{xtype:'datetimefield', fieldLabel:'生效时间', name:'startTime',value:new Date(),format:'Y-m-d'},
			{xtype:'datetimefield', fieldLabel:'失效时间', name:'endTime',value:new Date(new Date().getTime()+1000*60*60*365*30),format:'Y-m-d'},
			{xtype:'numberfield', fieldLabel:'允许密码错误次数', name:'allowError',value:3,labelWidth:120,width:220},
				{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'状态',
    			queryMode:'local',
    			name:'state',
    			value:'00A',
				data:[['有效','00A'],['无效','00X']]
			},
			{xtype:'textarea', fieldLabel:'说明', name:'direction',value:'',width:700,height:100}]
		},
	search:[{
				xtype : 'clearTextField', fieldLabel:'用户名称', name:'%name%',
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
		fields:['id','name','department','realName','password','phone','email','address','startTime','endTime','modifyTime','allowError','modifyPasswordTime','state','direction'],
		sqlKey:'DopPrivilegeUser.selectList'
	},
	grid:[{ text: '用户名称', dataIndex: 'name' ,width:150},
			{ text: '真实名称',  dataIndex: 'realName' , width:150},
			{ text: '电话号码',  dataIndex: 'phone' , width:150},
			{ text: '地址',  dataIndex: 'address' ,flex:1},
			{ text: '生效时间',  dataIndex: 'startTime' ,width:140},
			{ text: '失效时间',  dataIndex: 'endTime' ,width:140},
			{ text: '修改时间',  dataIndex: 'modifyTime' ,width:140},
			{ text: '状态',  dataIndex: 'state' ,width:140,renderer:function(val){
				if(val==='00X')return '无效';
				else return '有效';
			}}]
}