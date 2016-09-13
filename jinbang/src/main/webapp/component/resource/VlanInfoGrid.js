Ext.define('component.resource.VlanInfoGrid', {
			extend:'component.public.CustomPanelComponent',
			alias:'widget.vlanInfoGrid',
			requires:['component.business.NodeTreePanel'],
			constructor:function(config){
				var me=this;
				config=config||{};
//				me.nodeIdCmp=Ext.create('component.public.TextButtonField',{
//					editable:false,
//					handler:function(){
//						if(!me.nodeTreeWindow){
//							me.nodeTreeWindow = Ext.create('component.business.NodeTreePanelWindow', {
//								callback : function(records, treepanel) {
//									var attributes = Ext.decode(records[0].raw.attributes);
//									console.info(attributes);
//									me.nodeIdCmp.setTextAndValue(attributes.symbolName1,attributes.nodeId);
//								}
//							});
//						}
//						me.nodeTreeWindow.show();
//					}
//				});
				Ext.applyIf(config,{
					aliasName : 'NMS_VLAN_INFO',
					title : '站点同步信息列表',
					pageSize : 25,
//					searchComponents: [{
//	       	 			key:'NODE_ID',
//	       	 			item:me.nodeIdCmp
//	       	 		}],
	       	 		displayFunctions:[],
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