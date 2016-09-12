Ext.require(['component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.onReady(function(){
	var grid = null;
	var searchPanel = Ext.create('component.view.form.FormPanel', {
	    region:'north',border:true,frame:true,
	    name:'searchForm',
	    items:[
	    {xtype:'hidden',name:'id',value:'NULL'},
		{xtype:'hidden',name:'version',value:''},
		{xtype:'hidden',name:'simulators',value:''},
		{xtype:'hidden',name:'totalCount',value:0},
		{xtype:'hidden',name:'avgDelay',value:0},
		{xtype:'hidden',name:'maxDelay',value:0},
		{xtype:'hidden',name:'minDelay',value:0},
		{xtype:'hidden',name:'misCount',value:0},
		{xtype:'hidden',name:'delayCount',value:0},
		{xtype:'hidden',name:'startTimer',value:''},
		{xtype:'hidden',name:'endTimer',value:''},
		{
			width:140,
			xtype : 'numberfield', fieldLabel:'初始并发数', name:'startNum',
			value : '50',blankText :"提示：初始并发数必填"
		},{
			width:140,
			xtype : 'numberfield', fieldLabel:'超时(毫秒)', name:'lessDelay',
			value : '200',blankText :"提示：超时时间必填"
		},{
			width:140,
			xtype : 'numberfield', fieldLabel:'递增周期', name:'cycleCount',
			value : '5',blankText :"提示：递增周期"
		}]
	});
	
	var fileUplaodPanel = Ext.create('component.view.form.FormPanel', {
	    region:'north',border:true,frame:true,
	    name:'searchForm',
	    items:[
	    {xtype:'hidden',name:'id',value:'NULL'},
		{xtype:'textfield',name:'fileName',value:'',fieldLabel:'文件名称',blankText :"提示：文件名称必填"},
		{xtype:'textfield',name:'vPath',value:'',fieldLabel:'虚拟路径',blankText :"提示：虚拟路径必填"},
		{xtype:'hidden',name:'pPath',value:''},
		{xtype:'hidden',name:'parentId',value:'NULL'},
		{
			xtype:'ajaxComboBox',
			editable : true,
			queryMode:'local',
			fieldLabel : '状态',
			name:'status',
			displayField: 'name',
			valueField: 'value',
			data:[['生效','00A'],['失效','00X']],
			value:'00X'
		}]
	});
	var store = Ext.create('Ext.data.Store', {
	    storeId:'simpsonsStore',
	    fields:['id','fileName','vPath','pPath','parentId','status'],
	    proxy: {
	         type: 'ajax',
	         url: ctx+'/base/getPage.do',
	         reader: {
	            type: 'json',
	            root: 'result',
        		totalProperty: 'totalCount'
	         },
	         extraParams:{conditionsStr:Ext.encode({staffId:session.staff.userId}),sqlKey:'dopFileManager.selectList'}
	     },
	     pageSize:50,
	     autoLoad: true
	});
	var uploadWin = null;
	function getUplaodWin(){
		if(!uploadWin){
			uploadWin = Ext.create('Ext.window.Window', {
			    title: '新增上传',
			    height: 400,
			    width: 750,
			    layout: 'fit',
			    items: fileUplaodPanel,
			    buttons:[{
			    	text:'保存',
			    	handler:function(){
			    		var entity = uploadWin.down('form').getForm().getValues();
			    		Ext.Persistent.save('dopFileManager',entity,function(){
				 			grid.getStore().load();
				 			Ext.Msg.alert('提示','保存成功！');
				 		});
			    	}
			    },{
			    	text:'重置',
			    	handler:function(){
			    		
			    	}
			    }]
			});
		}
		return uploadWin;
	}
	grid = Ext.create('Ext.grid.Panel', {
		tbar:[{
			text:'新建上传',iconCls:'toolbar-add',
			handler:function(){
				getUplaodWin().show();
			}
		},{
			text:'删除',iconCls:'toolbar-delete',
			handler:function(){
				var records = me.pefTaskGrid.getSelectionModel().getSelection();
		 		for(var i=0;i<records.length;i++){
		 			var entity = {id:records[i].raw.id,status:"00X",
			 			endTimer:new Date().getTime(),
				 		endTimer$type:"Date"};
			 		Ext.Persistent.update('dopPerfConfig',entity,function(){
			 			me.pefTaskGrid.getStore().load();
			 		});
		 		}
			}
		},{
			text:'生效',iconCls:'toolbar-effective',
			handler:function(){
				
			}
		},{
			text:'失效',iconCls:'toolbar-invalid',
			handler:function(){
			
			}
		}],
	    region: 'center',
	    selModel:Ext.create('Ext.selection.CheckboxModel',{ checkOnly :true }),
	    store: store,
	    columns: [
	        { text: '文件名',  dataIndex: 'fileName' },
	        { text: '路径', dataIndex: 'vPath', flex: 1 }
	    ],
	    height: 200,
	    width: 400,
	    dockedItems : [{
	        xtype: 'pagingtoolbar',
	        store: store,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }]
	});
	Ext.create('Ext.container.Viewport', {
	    layout: 'border',
	    items: [searchPanel, grid]
	});
});