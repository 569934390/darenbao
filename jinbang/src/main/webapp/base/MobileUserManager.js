var template = {
	tbar:[,'-',{text:'团队成员',iconCls : 'alarmRelateInfo',handler:function(){
		var generalGird = template.generalGird;
		var selectRecords = generalGird.getSelectionModel().getSelection();
		if(selectRecords.length==0){
			return Ext.Msg.alert('提示','请选择记录');
		}
		if(selectRecords.length>1){
			return Ext.Msg.alert('提示','只能选择一条');
		}

		var userId = selectRecords[0].get('userId');

		var store = Ext.create('Ext.data.Store', {
			fields: ["userId","realName", "gender", "mobile", "birth", "nativeProvinceName", "nativeCityName",
				"nativeAreaName", "createTime"],
			pageSize: 15,  //页容量5条数据
			//是否在服务端排序 （true的话，在客户端就不能排序）
			remoteSort: false,
			remoteFilter: true,
			proxy: {
				type: 'ajax',
				url : ctx+'/mobileUserInfo/getFirstSub.do',
				method:"POST",
				extraParams: {
					userId: userId,
					level:'1'
				},
				reader: {   //这里的reader为数据存储组织的地方，下面的配置是为json格式的数据，例如：[{"total":50,"rows":[{"a":"3","b":"4"}]}]
					type: 'json',
					root: 'result',
					totalProperty: 'totalCount'
				}
			},
			sorters: [{
				//排序字段。
				property: 'createTime',
				//排序类型，默认为 ASC
				direction: 'desc'
			}],
			autoLoad: true  //即时加载数据
		});

		var grid = Ext.create('Ext.grid.Panel', {
			renderTo: Ext.getBody(),
			store: store,
			//height: 530,
			region:'center',
			width: 1184,
			//selModel: { selType: 'checkboxmodel' },   //选择框
			columns: [
				{
					id : 'term',
					header : "会员昵称",
					flex:1,
					sortable : true,
					dataIndex : 'realName'
				}, {
					header : "性别",
					flex:1,
					sortable : true,
					dataIndex : 'gender',
					renderer:function(val){
						if(val===1)return '男';
						else if(val===-1)return '女';
					}
				}, {
					header : "手机号",
					flex:1,
					sortable : true,
					dataIndex : 'mobile'
				}, {
					header : "出生日期",
					flex:1,
					sortable : true,
					dataIndex : 'birth'
				}, {
					header : "省",
					flex:1,
					sortable : true,
					dataIndex : 'nativeProvinceName'
				}, {
					header : "市",
					flex:1,
					sortable : true,
					dataIndex : 'nativeCityName'
				},
				//{
				//	header : "区",
				//	flex:1,
				//	sortable : true,
				//	dataIndex : 'nativeAreaName'
				//},
				{
					header : "注册时间",
					flex:1,
					sortable : true,
					dataIndex : 'createTime'
				}
			],
			bbar: [{
				xtype: 'pagingtoolbar',
				store: store,
				width: 1184,
				displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
				emptyMsg: "没有数据",
				beforePageText: "当前页",
				afterPageText: "共{0}页",
				displayInfo: true
			}]
		});



		var centerTabs = Ext.createWidget('tabpanel', {
			id:'centerTabs',
			region:'north',
			activeTab: 0,                       //指定默认的活动tab
			//width: 1184,
			//height: 58,
			plain: true,                        //True表示tab候选栏上没有背景图片（默认为false）
			enableTabScroll: true,              //选项卡过多时，允许滚动
			defaults: { autoScroll: true },
			renderTo: document.body,
			items: [
				{
					id:'tab0',
					title: '一级会员'
				},
				{
					id:'tab1',
					title: '二级会员'
				},
				{
					id:'tab2',
					title: '三级会员'
				}
			]
		});

		centerTabs.on('tabchange',function(e,args){
			if(args.getItemId()=='tab0')
			{
				grid.getStore().proxy.extraParams = {conditionsStr:Ext.encode({staffId:session.user.id}),userId: userId,level:'1'};
				grid.getStore().load();
			}
			else if(args.getItemId()=='tab1')
			{
				grid.getStore().proxy.extraParams = {conditionsStr:Ext.encode({staffId:session.user.id}),userId: userId,level:'2'};
				grid.getStore().load();
			}
			else if(args.getItemId()=='tab2')
			{
				grid.getStore().proxy.extraParams = {conditionsStr:Ext.encode({staffId:session.user.id}),userId: userId,level:'3'};
				grid.getStore().load();
			}
		});



		var msgPanel = new Ext.FormPanel({
			//width: 1184,
			//height: 570,
			labelWidth: 60,
			layout:"border",
			region:'center',
			bodyStyle:"text-align:center;padding-top:10px",
			items: [
				centerTabs,
				grid
				]
			//buttons: [
			//	{
			//		text: "关闭",
			//
			//		handler:function () {
			//			pwin.close();
			//		}
			//	}]
		});

		var pwin = new Ext.Window({
			title: '团队成员',
			width: 1200,
			height: 600,
			resizable: true,
			layout:"border",
			bodyStyle:"text-align:center",
			items: [ msgPanel ]
		}); // End Window

		pwin.show();
	}}],
	edit:{
		title:'会员编辑',
		width:650,
		height:480,
		sqlKey:'mobileUserInfo.DB',
		id:'userId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'userId',
				value:null
			},
			{
				xtype:'clearTextField',
				fieldLabel:'真名',
				name:'realName',
				value:''
			},
			{xtype:'clearTextField', fieldLabel:'密码', name:'password',inputType:'password',value:''},
			{
				xtype:'clearTextField',
				fieldLabel:'手机号',
				name:'mobile',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'工号',
				name:'workNumber',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'常住地址',
				name:'residentAddres',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'所在省份',
				name:'nativeProvince',
				displayField: 'provinceName',
				valueField: 'provinceCode',
				url:ctx+'/sprovince/get.do',
				queryMode:'remote',
				listeners : {
					'change': function () {
						provinceCode = this.value;
						Ext.getCmp('nativeCity').setValue('');
						Ext.getCmp('nativeArea').setValue('');
						Ext.getCmp('nativeCity').store.proxy.extraParams={provinceCode:provinceCode};
						Ext.getCmp('nativeCity').store.load();
					}
				}
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'所在地市',
				id:'nativeCity',
				name:'nativeCity',
				displayField: 'cityName',
				valueField: 'cityCode',
				//store:storeCitys,
				url:ctx+'/scity/getListByProviceCode.do',
				queryMode:'remote',
				listeners : {
					'change': function () {
						cityCode = this.value;
						Ext.getCmp('nativeArea').setValue('');
						Ext.getCmp('nativeArea').store.proxy.extraParams={cityCode:cityCode};
						Ext.getCmp('nativeArea').store.load();
					}
				}
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'所在区域',
				name:'nativeArea',
				id:'nativeArea',
				displayField: 'areaName',
				valueField: 'areaCode',
				//store:storeAreas,
				url:ctx+'/sdistrict/getListByCityCode.do',
				queryMode:'remote'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'身份证',
				name:'idCard',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'转账户名',
				name:'transferBankUserName',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'转账银行',
				name:'transferBankCardName',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'转账银行卡',
				name:'transferBankCard',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'创建时间',
				name:'createTime',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'备注',
				name:'remark',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'收款银行号',
				name:'transferBankCardCode',
				value:''
			},
			//{
			//	xtype:'clearTextField',
			//	fieldLabel:'代理商ID',
			//	name:'agentId',
			//	value:''
			//},
			{
				xtype:'clearTextField',
				fieldLabel:'邀请码',
				name:'invitationCode',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'邮箱',
				name:'email',
				value:''
			},
			{
				xtype:'datefield',
				fieldLabel:'生日',
				name:'birth',
				clear:true,
				format: 'Y-m-d',
				clear:true
			},
			{
				xtype: 'fileuploadfield',
				id: 'form-file',
				width:240,
				emptyText: '选择图片',
				fieldLabel: '头像',
				name: 'portrait',
				buttonText: '选择',
				buttonCfg: {
					iconCls: 'upload-icon'
				}
			}
		]
	},
	search:[
		//{
		//	xtype:'clearTextField',
		//	fieldLabel:'',
		//	name:'userId',
		//	value:''
		//},
		{
			xtype:'clearTextField',
			fieldLabel:'会员昵称',
			name:'%realName%',
			value:''
		},
		{
			xtype:'clearTextField',
			fieldLabel:'手机号',
			name:'%mobile%',
			value:''
		},
		{
			xtype:'ajaxComboBox',
			fieldLabel:'所在省份',
			name:'snativeProvince',
			clear:true,
			id:'snativeProvince',
			displayField: 'provinceName',
			valueField: 'provinceCode',
			url:ctx+'/sprovince/get.do',
			queryMode:'remote',
			listeners : {
				'change': function () {
					provinceCode = this.value;
					Ext.getCmp('snativeCity').setValue('');
					Ext.getCmp('snativeArea').setValue('');
					Ext.getCmp('snativeCity').store.proxy.extraParams={provinceCode:provinceCode};
					Ext.getCmp('snativeCity').store.load();
				}
			}
		},
		{
			xtype:'ajaxComboBox',
			fieldLabel:'所在地市',
			id:'snativeCity',
			clear:true,
			name:'snativeCity',
			displayField: 'cityName',
			valueField: 'cityCode',
			//store:storeCitys,
			url:ctx+'/scity/getListByProviceCode.do',
			queryMode:'remote',
			listeners : {
				'change': function () {
					cityCode = this.value;
					Ext.getCmp('snativeArea').setValue('');
					Ext.getCmp('snativeArea').store.proxy.extraParams={cityCode:cityCode};
					Ext.getCmp('snativeArea').store.load();
				}
			}
		},
		{
			xtype:'ajaxComboBox',
			fieldLabel:'所在区域',
			name:'snativeArea',
			id:'snativeArea',
			clear:true,
			displayField: 'areaName',
			valueField: 'areaCode',
			//store:storeAreas,
			url:ctx+'/sdistrict/getListByCityCode.do',
			queryMode:'remote'
		},
		{
			xtype:'datefield',
			fieldLabel:'创建时间',
			name:'%searchBeginDate%',
			clear:true,
			format: 'Y-m-d',
			clear:true
		},
		{
			xtype:'datefield',
			fieldLabel:'至',
			name:'%searchEndDate%',
			clear:true,
			format: 'Y-m-d',
			clear:true
		}
	],
	store:{
		fields:['userId','loginName','password','realName','gender','portrait','mobile','workNumber','residentAddres','nativeProvince','nativeProvinceName','nativeCity','nativeCityName','nativeArea','nativeAreaName','staffLon','staffLat','interviewer','idCard','idCardPhotos','emergencyContact','emergencyContactRelationship','emergencyContactMobilephone','transferBankUserName','transferBankCardName','transferBankCard','autoRelieveTime','createTime','updateTime','forbidTime','examTime','signedTime','status','forbidState','remark','skillTime','skillAge','skill','serviceIntegral','userExtendNumber','staffExtendNumber','imageNumber','approveContent','recommendStaffId','invitationStartTime','invitationEndTime','invitationNumber','invitationState','staffCatalog','receiveCatalog','transferBankCardCode','agentId','authenticationTime','skillImages','skillVideo','areaNumber','skillTerm','areaState','deviceId','invitationCode','funcflag','supacctid','thirdcustid','custproperty','nickname','mobilephone','email','reserve','qrCode','token','parentUserId','brandAgentId','birth'],
		sqlKey:'mobileUserInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: 'id',  dataIndex: 'userId' ,flex:1,hidden:true},
		{ text: '真名',  dataIndex: 'realName' ,flex:1},
		{ text: '性别',  dataIndex: 'gender',flex:1 ,renderer:function(val){
			if(val===1)return '男';
			else if(val===-1)return '女';
		}},
		{ text: '手机号',  dataIndex: 'mobile' ,flex:1},
		//{ text: '工号',  dataIndex: 'workNumber' ,flex:1},
		{ text: '常住地址详细',  dataIndex: 'residentAddres' ,flex:1},
		{ text: '所在省份',  dataIndex: 'nativeProvinceName' ,flex:1},
		{ text: '所在城市',  dataIndex: 'nativeCityName' ,flex:1},
		//{ text: '所在区域',  dataIndex: 'nativeAreaName' ,flex:1},
		{ text: '创建时间',  dataIndex: 'createTime' ,flex:1},
		//{ text: '状态 ',  dataIndex: 'status' ,flex:1,renderer:function(val){
		//	if(val===1)return '男';
		//	else if(val===-1)return '女';
		//}},
	{ text: '邮箱',  dataIndex: 'email' ,flex:1},
	{ text: '生日',  dataIndex: 'birth' ,flex:1}
]
}