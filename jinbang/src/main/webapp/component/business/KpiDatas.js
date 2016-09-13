Ext.define('component.business.KpiDatas', {
	getKpiByNodeTypeId:function(nodeTypeId,connect){
		var kpi='';
		if(connect=='snmp'){
			switch(Number(nodeTypeId)){
				case 12220001: kpi =  '20122200011303';break;
				case 11220002: kpi =  '20112200021304';break;
				case 11220001: kpi =  '20112200011304';break;
				case 11220003: kpi =  '20112200031304';break;
				case 11220004: kpi =  '20112200041304';break;
				case 15220001: kpi =  '20152200011303';break;
			}
			kpi+=',';
		}
		switch(nodeTypeId){
			case 12220001:kpi+=  '20122200011302';break;
			case 11220002:kpi+=  '20112200021303';break;
			case 11220001:kpi+=  '20112200011303';break;
			case 11220003:kpi+=  '20112200031303';break;
			case 11220004:kpi+=  '20112200041303';break;
			case 15220001:kpi+=  '20152200011302';break;
		}
		return kpi;
	},
	getPortKpiByNodeTypeId:function(nodeTypeId){
		var kpi='';
		switch(nodeTypeId){
			case 11250001:kpi+= '20112500011502';break;//【告警】SR8808端口运行状态告警          
			case 12250001:kpi+= '20122500011501';break;//【告警】SICOM6000Series设备端口运行状态?
			case 11250001:kpi+= '20112500011501';break;//【告警】SR6608端口运行状态告警          
			case 16250001:kpi+= '20162500011501';break;//【告警】华环H9M0-LMXE设备端口运行状态告?
			case 11250001:kpi+= '20112500011504';break;//【告警】华三ET824端口运行状态告警       
			case 11250001:kpi+= '20112500011503';break;//【告警】华三7506端口运行状态告警        
			case 16250001:kpi+= '20162500011512';break;//【告警】华环H9M0-LM设备端口运行状态告警 
			case 16250001:kpi+= '20162500011523';break;//【告警】华环H9GK-103设备端口运行状态告警
			case 15250001:kpi+= '20152500011501';break;//【告警】业通达交换机设备端口运行状态告警

		}
		console.info(nodeTypeId,kpi);
		return kpi;
	}
});