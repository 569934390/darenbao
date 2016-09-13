var template = {
	edit:{
		title:'编辑服务类别',
		width:600,
		height:250,
		sqlKey:'serviceCategory.DB',
		idKey:'serviceCategoryId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'serviceCategoryId',
				value:'NULL'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'服务名称',
				name:'categoryName',
				value:''
			},
			{
				xtype:'datetimefield',
				fieldLabel:'创建时间',
				name:'createDate',
				value:new Date(),
				format:'Y-m-d'
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'所在省份',
				name:'nativeProviceId',
				displayField: 'provinceName',
				valueField: 'provinceCode',
				url:ctx+'/sprovince/get.do',
				queryMode:'remote',
				listeners : {
					'change': function () {
						provinceCode = this.value;
						Ext.getCmp('nativeCityId').setValue('');
						Ext.getCmp('nativeCityId').store.proxy.extraParams={provinceCode:provinceCode};
						Ext.getCmp('nativeCityId').store.load();
					}
				}
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'所在地市',
				id:'nativeCityId',
				name:'nativeCityId',
				displayField: 'cityName',
				valueField: 'cityCode',
				//store:storeCitys,
				url:ctx+'/scity/getListByProviceCode.do',
				queryMode:'remote',
				listeners : {
					'change': function () {
						cityCode = this.value;
					}
				}
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'状态',
				name:'status',
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=status&pid=0',
				queryMode:'remote',
				value:'0'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'所属用户',
				name:'chargeUserId',
				value:''
			},
			{
				xtype: 'fileuploadfield',
				id: 'form-file',
				width:480,
				emptyText: '选择图片',
				fieldLabel: '图标',
				name: 'iconUrl',
				buttonText: '选择',
				buttonCfg: {
					iconCls: 'upload-icon'
				}
			}
		]
	},
	search:[
		{
			xtype:'clearTextField',
			fieldLabel:'服务名称',
			name:'%categoryName%',
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
					Ext.getCmp('snativeCityId').setValue('');
					Ext.getCmp('snativeArea').setValue('');
					Ext.getCmp('snativeCityId').store.proxy.extraParams={provinceCode:provinceCode};
					Ext.getCmp('snativeCityId').store.load();
				}
			}
		},
		{
			xtype:'ajaxComboBox',
			fieldLabel:'所在地市',
			id:'snativeCityId',
			clear:true,
			name:'snativeCityId',
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
			fieldLabel:'状态',
			clear:true,
			name:'%status%',
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=status&pid=0',
			queryMode:'remote'
		}
	],
	store:{
		fields:['serviceCategoryId','categoryName','createDate','nativeProviceId','nativeProviceName','nativeCityIdId','nativeCityName','status','remark','chargeUserId','iconUrl'],
		sqlKey:'serviceCategory.selectList',
		type:'DB'
	},
	grid:[
		{ text: 'id',  dataIndex: 'serviceCategoryId' ,flex:1,hidden:true},
		{ text: '服务类别名称',  dataIndex: 'categoryName' ,flex:1},
		{ text: '创建时间',  dataIndex: 'createDate' ,flex:1},
		{ text: '所属省名称',  dataIndex: 'nativeProviceName' ,flex:1},
		{ text: '所属市名称',  dataIndex: 'nativeCityName' ,flex:1},
		{ text: '状态',  dataIndex: 'status' ,flex:1,renderer:function(val){
			if(val==='1')return '启用';
			else if(val==='0')return '禁用';
		}}
	]
}