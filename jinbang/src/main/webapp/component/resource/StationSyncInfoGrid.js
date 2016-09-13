Ext.define('component.resource.StationSyncInfoGrid', {
			extend:'component.public.CustomPanelComponent',
			constructor:function(config){
				var me=this;
				config=config||{};
				Ext.applyIf(config,{
					aliasName : 'STATION_SYNC_INFO',
					title : '站点同步信息列表',
					pageSize : 25,
					searchComponents : [{
						key : 'STATE',
						item : Ext.create('component.public.AjaxComboBox', {
							name : 'STATE',
							clear:true,
							plugins:[Ext.create('component.public.ComboSelectFirstPlugin')],
							data : common.constant.state
						})
					}],
					displayFunctions : [
					{
						key : 'STATE',
						fun : function(value, metadata, record) {
							return Ext.getArrayValue(common.constant.state,
									value, 1);
						}
					}],
					dockedItems:[{//菜单栏
			 				xtype: 'toolbar',
			 				dock: 'top',
			 				items:[{
				   	  	    	text: '站点同步',iconCls : 'toolbar-view',name:'stationSync'
//			   	 			},'-',{
//				   	 			text: '新增',iconCls : 'toolbar-add',name:'roleAdd'
//			   	 			},'-',{
//				   	 			text: '修改',iconCls : 'toolbar-edit',name:'roleEdit',resourceId:6
//			   	 			},'-',{
//				   	 			text: '删除',iconCls : 'toolbar-delete',name:'roleDelete'
//			   	 			},'-',{
//				   	 			text: '权限配置',iconCls : 'alarmRelateInfo',name:'privCfg'
			   	 			}]
					}],
					after : function(grid) {
						// alert(grid);//对读出数据的处理过程均可写在这里
					},
					gridCfg: {
			   	 		columnLines: true,
			   	 		multiSelect: false,
			   	 		selModel: {
			   	 			selType:'checkboxmodel'
			   	 		}
		   	 		},
					init : function(panel, toolPanel, grid) {
					}
				});
				me.callParent([config]);
			}
    	});