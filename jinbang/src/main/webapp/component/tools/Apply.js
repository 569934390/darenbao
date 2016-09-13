Ext.define('component.tools.Apply', {
	bindTemplate:function(){
		var me = this;
		Ext.Array.each(me.query('form'),function(form,index){
			if(form.name=='baseInfo'){
				var nodeTaskName = form.getForm().findField('nodeTaskName');
				nodeTaskName.setValue('{nodeName}-{kpiName}');
			}
			else if(form.name=='runtimeConfig'){
				var ip = form.getForm().findField('ip');
				if(!Ext.isEmpty(ip)){
					ip.setValue('{ip}');
				}
			}
		});
	},
	buildData:function(){
		var me = this;
		if(!me.kpiGroupName){
			var checked = me.KPITreePanel.getChecked();
			if(checked.length>0){
				var record = checked[0];
				if(!record.isLeaf()){
	   			 	me.kpiGroupName=record.raw.text;
	   			 }
	   			 else{
	   			 	var parent = record.parentNode;
	   			 	me.kpiGroupName=parent.raw.text;
	   			 }
			}
		}
		var node = me.nodeTreePanel.getSelectionModel().getSelection();
		var attrs = Ext.JSON.decode(node[0].raw.attributes);
		Ext.Array.each(me.query('form'),function(form,index){
			if(form.name=='runtimeConfig'){
				var ip = form.getForm().findField('ip');
				if(!Ext.isEmpty(ip)){
					ip.setValue(ip.getValue().format({ip:attrs.ipaddress}));
				}
			}
			else if(form.name=='baseInfo'){
				var nodeTaskName = form.getForm().findField('nodeTaskName');
				nodeTaskName.setValue(nodeTaskName.getValue().format({ip:attrs.ipaddress,kpiName:me.kpiGroupName,nodeName:attrs.symbolName1}));
			}
		});
	}
});