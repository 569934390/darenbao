Ext.define('component.resource.LinkInfoView', {
	extend : 'Ext.panel.Panel',
//	style : 'overflow-x:auto;',
	itemSelector : 'div.linkCls',
	emptyText : '没有任何数据',
	title:'链路配置信息',
	frame:true,
	// overItemCls : 'itemHover',
	multiSelect : true,
	autoScroll : true,
	constructor : function(config) {
		var me = this;
		Ext.define('LinkInfo', {
			extend : 'Ext.data.Model',
			fields : ['result','linkSetInfoArray','errorMessage']
		});
		me.outputXML={
				"result" : null,
				"linkSetInfoArray" : [ {
					"deviceIDA" : "AAAAAAAAAAAAAAA",
					"deviceTermA" : "3",
					"deviceIDB" : "BBBBBBBBBBBBBBBB",
					"deviceTermB" : "4",
					"linkOutID" : "123456",
					"passDeviceTermListArray" : [ {
						"passDeviceTermList" : [ {
							"deviceID" : "FDFFFFFFF",
							"deviceTermID" : "3"
						}, {
							"deviceID" : "FDFFFFFFF",
							"deviceTermID" : "3"
						} ]
					},{
						"passDeviceTermList" : [ {
							"deviceID" : "FDFFFFFFF",
							"deviceTermID" : "3"
						}, {
							"deviceID" : "FDFFFFFFF",
							"deviceTermID" : "3"
						} ]
					} ]
				}, {
					"deviceIDA" : "AAAAAAAAAAAAAAA",
					"deviceTermA" : "3",
					"deviceIDB" : "BBBBBBBBBBBBBBBB",
					"deviceTermB" : "4",
					"linkOutID" : "123456",
					"passDeviceTermListArray" : null
				} ],
				"errorMessage" : 'xxx'
			};
		var linkInfoTpl = new Ext.XTemplate(
				'<div style="width:300px;">',
				'<tpl if="!Ext.isEmpty(result)"><span>查询结果:<tpl if="result==0">失败<tpl else>成功</tpl><tpl if="!Ext.isEmpty(errorMessage)"></br>错误信息:{errorMessage}:</tpl></span></tpl>',
				'<tpl for="linkSetInfoArray">',
					'<div><span style="height:20px;line-height:20px;color:#4c4c4c;margin-left:5px;padding-left:15px;background:url('+ webRoot+'common/images/16x16/arrows.png) no-repeat 2px 1px; !important; background-position:-12px 1px; width:16px;"></span>链路{linkOutID}</div>',
						'<div>',
						'<tpl for="passDeviceTermListArray">',
							'<div style="margin-left:10px;"><span style="height:20px;line-height:20px;color:#4c4c4c;margin-left:5px;padding-left:15px;background:url('+ webRoot+'common/images/16x16/arrows.png) no-repeat 2px 1px; !important; background-position:-12px 1px; width:16px;"></span>第{[xindex]}条链路经过的设备</div>',
							'<div>',
							'<tpl for="passDeviceTermList">',
								'<tpl if="!this.isLastRecord(values,xcount,xindex)">',
									'<div style="height:20px;line-height:20px;color:#4c4c4c;margin-left:25px;padding-left:15px;background:url('+ webRoot+ 'public/ext-4.2.1/resources/ext-theme-classic/images/tree/elbow.gif) 0 no-repeat !important;" >光缆标识:{deviceID},光芯标识:{deviceTermID}</div>',
								'</tpl>',
								'<tpl if="this.isLastRecord(values,xcount,xindex)">',
									'<div style="height:20px;line-height:20px;color:#4c4c4c;margin-left:25px;padding-left:15px;background:url('+ webRoot+ 'public/ext-4.2.1/resources/ext-theme-classic/images/tree/elbow-end.gif) 0 no-repeat !important;" >光缆标识:{deviceID},光芯标识:{deviceTermID}</div>',
								'</tpl>',
							'</tpl>',
							'</div>',
						'</tpl>',
						'</div>',
				'</tpl>',
				'</div>',{
					isLastRecord:function(values,count,index){
						console.info(values);
						return count===index;
					}
				});
		this.tpl = linkInfoTpl;
//		this.store = linkInfoStore;
		config =config||{};
		me.callParent(arguments);
//		console.info(me.body);
//		me.body.on('itemclick',function(view, record, item, index, e, eOpts){
//			alert('tt');
//		});
	},
	listeners:{
		render:function(){
			var me=this;
			me.el.on('click',function(evt,target,eOpts){
				if(target.style.backgroundPosition=='0px 1px'){
					target.style.backgroundPosition="-12px 1px";
					target.parentNode.nextSibling.style.display="";
				}else if(target.style.backgroundPosition=='-12px 1px'){
					target.style.backgroundPosition="0px 1px";
					target.parentNode.nextSibling.style.display="none";
				}
			});
		}
	},
	updateOutputXML:function(outputXML){
		this.outputXML=outputXML;
		me.tpl.overwrite(me.body,outputXML);
	}
});