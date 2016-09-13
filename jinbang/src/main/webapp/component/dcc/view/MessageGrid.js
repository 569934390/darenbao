Ext.define('component.dcc.view.MessageGrid',{
	extend:'Ext.grid.Panel',
	constructor:function(config){
		config=config||{};
		var me=this;
		me.plugin=me.createPlugin();
		me.store=me.createStore();
		Ext.applyIf(config,{
//    		region:'center',
    		viewConfig:{
    			enableTextSelection:true
    		},
    		name:'messageGrid',
    		selModel:Ext.create('Ext.selection.RowModel',{mode:'SINGLE'}),
    		store: me.store,
    		plugins:me.plugin,
    	    columns: [
    	        { text: '会话标识',  dataIndex: 'sessionId' ,width:230},
    	        { text: '系统', dataIndex: 'system' ,width:100},
    	        { text: '业务号码', dataIndex: 'phoneCode',width:110},
    	        { text: '状态', dataIndex: 'anaysisCode',width:160,renderer:Ext.bind(me.anaysisCodeRenderer,me)},
    	        { text: '消息类型', dataIndex: 'msgType' ,width:80,renderer:Ext.bind(me.msgTypeRenderer,me)},
    	        { text: '请求类型', dataIndex: 'requestType' ,width:70,renderer:Ext.bind(me.requestTypeRenderer,me)},
    	        { text: '序号', dataIndex: 'sort' ,width:35},
    	        { text: '源IP', dataIndex: 'sourceIp',width:120},
    	        { text: '目标IP', dataIndex: 'targetIp',width:120},
    	        { text: '业务能力', dataIndex: 'kpi',width:170},
    	        { text: '业务类型', dataIndex: 'kpiType',width:170},
    	        { text: '返回码', dataIndex: 'returnCode',width:150,renderer:Ext.bind(me.returnCodeRenderer,me)},
    	        { text: '返回码描述', dataIndex: 'returnCode' ,width:150,renderer:Ext.bind(me.retrunCodeDescRenderer,me)},
//    	        { text: '开始时间', dataIndex: 'startTime',width:120},
//    	        { text: '结束时间', dataIndex: 'endTime',width:120},
    	        { text: '事件时间', dataIndex: 'eventTimeStamp' ,width:150,renderer:Ext.bind(me.eventTimeStampRenderer,me)},
    	        { text: '抓包时间', dataIndex: 'catchTime' ,width:150,renderer:Ext.bind(me.catchTimeRenderer,me)},
    	        { text: '源主机', dataIndex: 'sourceHost',width:160},
    	        { text: '目标主机', dataIndex: 'targetHost',width:120}
    	    ]
    	});
		me.callParent([config]);
	},
	createStore:function(){
		var me=this;
		return Ext.create('component.public.AjaxStore', {
		     model: 'dcc.model.Message',
		     autoLoad: false
		 });
	},
	createPlugin:function(){
		var me=this;
		return Ext.create('component.public.GridToolTipPlugin',{
		 	toolTipRenderer:function(grid,record,value,label,cellIndex,cellElement,dataIndex){
		 		if(cellIndex==0||cellIndex==9){
		 			return value;
		 		}else if(cellIndex==11){
		 			return me.retrunCodeDescRenderer(value);
		 		}
		 		return false;
		 	}
		 });
	},
	retrunCodeDescRenderer:function(val){
    	var desc = '';
    	if(val){
        	var vals = val.split(',');
        	for(var i=0;i<vals.length;i++){
        		if(avpCodes[vals[i]]){
        			desc+=avpCodes[vals[i]].avpDesc+" "
        		}
        	}
    	}
    	return desc;
    },
	anaysisCodeRenderer:function(val,cell,record){
		var me=this;
		var returnCode = record.get('returnCode');
		returnCodeStr = me.returnCodeRenderer(returnCode);
		if(returnCodeStr&&(returnCodeStr.indexOf('错误')!=-1||returnCodeStr.indexOf('失败')!=-1)){
			return '<div style="width:100%;height:100%;background-color:red;color:#fff">返回码:'+returnCodeStr+'</div>';
		}
		if(!val){
			val = '';
		}
		var exceptions = [],bin = val.split(',');
		for(var i=0;i<bin.length;i++){
			if(bin[i]!='')
				exceptions[bin[i]]=true;
		}
		var returnStr = [];
		for(var key in exceptions){
			var c = me.rules[key];
			if(c){
				returnStr.push(c);
			}else{
				//后台格式匹配预留
			}
		}
		if(returnStr.length==0){
			return '<div style="width:100%;height:100%;background-color:green;color:#fff">正常</div>';
		}
		else
			return '<div style="width:100%;height:100%;background-color:red;color:#fff">'+returnStr.join(',')+'错误</div>';
		return '无';
	},
	msgTypeRenderer:function(val){
		if(val=='272-1')
    		return 'CCR';
    	else if(val=='272-2')
    		return 'CCA';
    	return me.returnCodeRenderer(val);
	},
	requestTypeRenderer:function(val){
		if(val=='1')
			return '初始包';
		else if(val=='2')
			return '更新包';
		else if(val=='3')
			return '结束包';
		else if(val=='4')
			return '事件包';
	},
	returnCodeRenderer:function(val){
    	var me  = this;
    	/**
    	 *  - 1xxx （信息类）
			- 2xxx （成功）
			- 3xxx （协议错误）
			- 4xxx （短暂失败）
			- 5xxx （永久失败）
    	 */
    	if(val&&val.indexOf(',')!=-1){
    		var vals = val.split(','),returnCode = '';
    		for(var i=0;i<vals.length;i++){
    			returnCode+= me.returnCodeRender(vals[i]);
    		}
    		return returnCode;
    	}
    	var qf = parseInt(parseInt(val)/1000);
    	if(qf==1){
    		return '信息类('+val+')';
    	}
    	else if(qf==2){
    		return '成功('+val+')';
    	}
    	else if(qf==3){
    		return '协议错误('+val+')';
    	}
    	else if(qf==4){
    		return '短暂失败('+val+')';
    	}
    	else if(qf==5){
    		return '永久失败('+val+')';
    	}
    	return null;
    },
    eventTimeStampRenderer:function(val){
    	var date = Ext.Date.parse(val, "YmdHis");
    	return Ext.util.Format.date(date,'Y-m-d H:i:s');
    },
    catchTimeRenderer:function(val){
    	var date = Ext.Date.parse(val, "YmdHisu");
    	return Ext.util.Format.date(date,'Y-m-d H:i:s');
    },
});