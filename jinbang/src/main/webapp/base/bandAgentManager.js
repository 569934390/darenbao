var template = {
	edit:{
		title:'品牌运营商编辑',
		width:550,
		height:300,
		beforeSubmit:function(entity){
			var me = this;
			Ext.Ajax.request({
				url:ctx+'/agentInfo/addOrUpdate.do',
				method:"post",
				params:entity,
				success:function(response){
					if(!Ext.isEmpty(response.responseText)){
						var result = Ext.decode(response.responseText);
						if(result.success){
							Ext.Msg.alert('提示','保存成功！');
							me.callback();
						}else{
							Ext.Msg.alert('提示',result.msg);
							me.callback();
						}
					}
				},
				failure:function(){
					return Ext.Msg.alert('提示','操作失败');
				}
			});
			return false;;
		},
		sqlKey:'agentInfo.DB',
		id:'agentId',
		cascade:true,
		items:[
			{
				xtype:'hidden',
				name:'agentId',
				value:null
			},
			{
				xtype:'clearTextField',
				fieldLabel:'运营商名称',
				name:'agentName',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'运营商编号',
				name:'agentCode',
				editable:false,
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'创建时间',
				name:'createDate',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'运营商类型',
				name:'agentCategory',
				editable:false,
				readOnly:true,
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=agentCategory&pid=0',
				value:'3',
				queryMode:'remote'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'负责人名称',
				name:'responsiblePerson',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'联系电话',
				name:'mobile',
				value:''
			},
			{
				xtype:'clearTextField',
				fieldLabel:'身份证号码',
				name:'idCard',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'所在省份',
				name:'nativeProvinceId',
				id:'nativeProvinceId',
				displayField: 'provinceName',
				valueField: 'provinceCode',
				url:ctx+'/sprovince/get.do',
				queryMode:'remote',
				listeners : {
					'change': function () {
						provinceCode = this.value;
						Ext.getCmp('nativeCityId').setValue('');
						Ext.getCmp('nativeAreaId').setValue('');
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
						Ext.getCmp('nativeAreaId').setValue('');
						Ext.getCmp('nativeAreaId').store.proxy.extraParams={cityCode:cityCode};
						Ext.getCmp('nativeAreaId').store.load();
					}
				}
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'所在区域',
				name:'nativeAreaId',
				id:'nativeAreaId',
				displayField: 'areaName',
				valueField: 'areaCode',
				//store:storeAreas,
				url:ctx+'/sdistrict/getListByCityCode.do',
				queryMode:'remote'
			},
			{
				xtype:'clearTextField',
				fieldLabel:'详细地址',
				name:'location',
				value:''
			},
			{
				xtype:'ajaxComboBox',
				fieldLabel:'审核状态',
				name:'reviewStatus',
				displayField: 'displayField',
				valueField: 'valueField',
				url:ctx+'/dict/listDict.do?type=agentStatus&pid=0',
				queryMode:'remote'
			}
		]
	},
	autoSearch:true,
	search:[
		{
			xtype:'hidden',
			fieldLabel:'运营商类型',
			name:'%agentCategory%',
			value:'4'
		},
		{
			xtype:'clearTextField',
			fieldLabel:'运营商名称',
			name:'%agentName%',
			value:''
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
		},
		{
			xtype:'clearTextField',
			fieldLabel:'负责人名称',
			name:'%responsiblePerson%',
			value:''
		},
		{
			xtype:'clearTextField',
			fieldLabel:'联系电话',
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
			xtype:'ajaxComboBox',
			fieldLabel:'审核状态',
			clear:true,
			name:'%reviewStatus%',
			displayField: 'displayField',
			valueField: 'valueField',
			url:ctx+'/dict/listDict.do?type=agentStatus&pid=0',
			queryMode:'remote'
		}
	],
	store:{
		fields:['agentId','agentName','agentCode','createDate','agentCategory','responsiblePerson','mobile','nativeProvinceId','nativeProvinceName','nativeCityId','nativeCityName','nativeAreaId','nativeAreaName','location','reviewStatus','idCard','virtualCard','totalIncome','turnoutMoney','settleMoney','staySettleMoney','regNumber','businessLicense','visaCategory','bankCategory','visaNumber','parentAgentId'],
		sqlKey:'agentInfo.selectList',
		type:'DB'
	},
	grid:[
		{ text: '主键',  dataIndex: 'agentId' ,flex:1,hidden:true},
		{ text: '运营商名称',  dataIndex: 'agentName' ,flex:1},
		{ text: '运营商编号',  dataIndex: 'agentCode' ,flex:1},
		{ text: '创建时间',  dataIndex: 'createDate' ,flex:1},
		{ text: '负责人名称',  dataIndex: 'responsiblePerson' ,flex:1},
		{ text: '联系电话',  dataIndex: 'mobile' ,flex:1},
		{ text: '所在省名称',  dataIndex: 'nativeProvinceName' ,flex:1},
		{ text: '所在市名称',  dataIndex: 'nativeCityName' ,flex:1},
		{ text: '所在区名称',  dataIndex: 'nativeAreaName' ,flex:1},
		{ text: '地址',  dataIndex: 'location' ,flex:1},
		{ text: '审核状态',  dataIndex: 'reviewStatus' ,flex:1,renderer:function(val){
			if(val==='1')return '审核通过';
			else if(val==='0')return '待审核';
			else if(val==='-1')return '审核不通过';
		}}
	]
}