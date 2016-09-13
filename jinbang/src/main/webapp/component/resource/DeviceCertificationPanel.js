/**
 * 设备认证
 */
Ext.define('component.resource.DeviceCertificationPanel', {
	extend:'component.public.CustomPanelComponent',
	aliasName :'NODE_ELEMENT_ATTR_GRID',
	title : '设备认证',
	pageSize : 25,
	constructor:function(config){
		var me=this;
		config=config||{};
		Ext.apply(config,{
			searchComponents : [{
				key : 'AAUTHSTATUS',
				item : Ext.create('component.public.AjaxComboBox', {
					name : 'AAUTHSTATUS',
					clear:true,
					plugins:[Ext.create('component.public.ComboSelectFirstPlugin')],
					data : common.constant.authStatus
				})
			}],
			displayFunctions : [
			{
				key : 'AAUTHSTATUS',
				fun : function(value, metadata, record) {
					return Ext.getArrayValue(common.constant.authStatus,value, 1);
				}
			}],
			dockedItems:[{//菜单栏
						xtype: 'toolbar',
						dock: 'top',
						items:[{
		   	  	    	text: '设备认证',iconCls : 'toolbar-view',name:'deviceCertification'
//			 			},'-',{
//		   	 			text: '新增',iconCls : 'toolbar-add',name:'roleAdd'
//			 			},'-',{
//		   	 			text: '修改',iconCls : 'toolbar-edit',name:'roleEdit',resourceId:6
//			 			},'-',{
//		   	 			text: '删除',iconCls : 'toolbar-delete',name:'roleDelete'
//			 			},'-',{
//		   	 			text: '权限配置',iconCls : 'alarmRelateInfo',name:'privCfg'
			 			}]
			}],
			init: function(panel, toolPanel, grid) {
			},
			deviceCetrification:function(){
				var records=Ext.gridSelectCheck(me.getGrid(),true);
				if(!records) return;
				var inputXML=new Object();
				var deviceInfoArray=[];
				Ext.each(records,function(record,index){
					var deviceInfo=new Object();
					deviceInfo.deviceIP=record.get('IPADDRESS');
					deviceInfo.devOutID=record.get('NODE_ID');
					deviceInfoArray.push(deviceInfo);
				});
				inputXML.deviceInfoArray=deviceInfoArray;
				Ext.Ajax.request({
		    		url : ctx+'/stationSync/getGpmsInfo.do',
		    		method : 'POST',
		    		params : {inputXML:Ext.encode(inputXML),method:'DeviceCertificate'},
		    		success : function(resp, action) {
		    			me.getGrid().getStore().reload();
		    		},
		    		failure : function() {
		    			Ext.Msg.alert('提示',msg);
		    		}
		    	});
			},
			gridCfg: {
			 		columnLines: true,
			 		multiSelect: false,
			 		selModel: {
			 			selType:'checkboxmodel'
			 		}
			}
		});
		this.callParent([config]);
		this.down('[name=deviceCertification]').on('click',this.deviceCetrification);
	},

});