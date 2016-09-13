/**
 * TREEGRID
 * @author Damen
 * @date 2013-11-23
 * @define component.public.TreeGridCommon
 * @alias treeCommonPanel
 * 
 * @title 基本设置
 * @param autoExpand : 是否全展开(Boolean)
 * @param checkable : 是否显示可选框(Boolean)
 *        
 *        
 */
Ext.define('component.public.TreeGridCommon',{
	extend:'Ext.tree.Panel',
    requires: [
        'Ext.button.Button',
        'Ext.form.field.Text'
    ],
	mixins: {
        dataFilter : 'component.public.TreeGridGlobal'
    },
    selModel:Ext.create('Ext.selection.RowModel'),//单行选择 
    loadmask:null,
    fieldkey:'',//table{id,ftext,faction,fparentid,fsort} //id is primarykey 
    fieldparentkey:'',
    readUrl:'',//读取
    newUrl:'',//新增
    updateUrl:'',//修改
    delUrl:'',//删除action
    
    stateful: true,
    stateId: 'stateTree',
    singleExpand: true,
	columnLines: true,//显示列线
	enableColumnMove:false,
	fit:true,//在父容器中完全填充显示
//    tbar:[{
//		  xtype:'splitbutton',
//		  text:'新增',
//		  tooltip: '新增数据！',
//		  iconCls:'icon-add',
//		  menu:[
//		   {
//		     text:'新增子层',
//		     iconCls:'x-tree-icon-leaf',
//		     handler:function(){this.ownerCt.ownerButton.ownerCt.ownerCt.insertnode(true);}
//		   },
//		   {
//		   	 text:'新增父层',
//		   	 iconCls:'x-tree-icon-parent',
//		   	 handler:function(){this.ownerCt.ownerButton.ownerCt.ownerCt.insertnode(false);}
//		   }
//		  ],
//		  handler:function(){this.ownerCt.ownerCt.insertnode(true);}
//	  },'-',
//	  {
//		  xtype:'button',
//		  text:'删除',
//		  iconCls:'icon-del',
//		  tooltip: '将数据从数据库中删除！',
//		  handler:function(){this.ownerCt.ownerCt.deletedb();}
//	  },'-',
//	  {
//		  xtype:'button',
//		  text:'保存',
//		  iconCls:'icon-save',
//		  tooltip: '将修改后的数据保存入数据库！',
//		  handler:function(){this.ownerCt.ownerCt.savedb();}
//	  },
//	  {
//		  xtype:'button',
//		  text:'刷新',
//		  iconCls:'icon-refresh',
//		  tooltip: '刷新数据！',
//		  handler:function(){this.ownerCt.ownerCt.querydb();}
//	  }
//	],
    viewConfig: {
        stripeRows: true,
        enableTextSelection: true
    },
    plugins:[Ext.create('Ext.grid.plugin.CellEditing',{clicksToEdit:1})],
    selcell:function(arow,acol){
    	this.editingPlugin.startEditByPosition({row:arow,column:acol});
    },
    selcell2:function(node,acol){
        this.editingPlugin.startEdit(node,acol);
    },
    validrecords:function(records){
    	var me=this,i,j;
    	if(!records) return null;
    	if(records.length<1) return null;
    	var keys=[];
    	for(i=0;i<me.columns.length;i++)
    	{
    		if(!Ext.isEmpty(me.columns[i].field)&&!me.columns[i].field.allowBlank)
    		{
    			keys.push(me.columns[i].dataIndex);
    		}
    	}
    	if(keys.length<1) return null;
    	var result=[];
    	for(i=0;i<records.length;i++)
    	{
    		for(j=0;j<keys.length;j++)
    		{
				if(Ext.isEmpty(records[i].data[keys[j]]))
				{
					result.push(records[i]);
				}
    		}
    	}
        return result;
    },
    getselect:function(){
    	var me=this,
    	    smodel=me.getSelectionModel();
    	if(!smodel) return null;
    	var records=smodel.getSelection();
    	if(!records) return null;
    	if(records.length<1) return null;
    	return records[0];
    },
    isvalid:function(records){
    	var me=this,
    	    result=me.validrecords(records);
    	if(!result) return false;
    	return (result.length>0);
    },
    getfields:function(){
        var me=this,
            result=[];
    	for(var i=0;i<me.columns.length;i++) {
    		result.push(me.columns[i].dataIndex);
    	}
    	return result;
    },
    getModifiedFieldNames:function(record){
		var modified = [],
		    me=this;
		for(var i=0;i<me.columns.length;i++) {
			if(record.isModified(me.columns[i].dataIndex)) {
				modified.push(me.columns[i].dataIndex);
			}
		}
		return modified; 
    },
    commit:function(records){
        Ext.Array.each(records,function(record){
           record.updateInfo();
           record.commit();
        });
    },
    insertnode:function(leaf){
	    var me = this,
	        Store = me.store,
	        node,
	        parentnode=null,
	        smodel=me.getSelectionModel(),
	        selectnode=smodel.getSelection()[0];
        
	    if(!selectnode) {
	        node=Store.getRootNode().appendChild({leaf:leaf});
	    } else {   
	    	if(selectnode.data.leaf) {
	        	selectnode.data.leaf=false;
	        }
	        parentnode=selectnode;
	    }
	    if(parentnode) {
	    	var newrecords=Store.getNewRecords();
	    	if(Ext.Array.contains(newrecords,parentnode)) {
	    		MsgBoxWar("该节点不能新增下级节点，因为没有保存！",function(){
	    		  smodel.selectRange(parentnode,parentnode,false);
	    		});
	    		return false;
	    	}
	    	parentnode.expand(false,function(){
	    	   //延迟400毫秒执行，否则编辑框显示异常
	    	   setTimeout(function(){
		    	   node=selectnode.appendChild({leaf:leaf});
		    	   node.data[me.fieldparentkey]=parentnode.data[me.fieldkey];
		    	   smodel.selectRange(node,node,false);
		    	   me.selcell2(node,0);
	    	   },400);
	    	});
	    } else {
	    	smodel.selectRange(node,node,false);
	    	me.selcell2(node,0);
	    }
	    return true;
    },
    querydb : function(){
    	var me=this,
    	    node=me.getselect();
    	if(!node) {   //全部刷新
    		me.store.load();
    	} else {   //叶子节点就不要刷新了
	    	if(node.data.leaf) return;
	    	//刷新指定的节点
	    	me.store.load({parentId:node.data.id});
	    }
    },
    insertdb:function(newrecords){
        var me=this;
        if(!me.fieldkey) return;
        if(!me.fieldparentkey) return;
        if(!me.newUrl) return;
        if(newrecords.length<1) return;
		var jsonData=[],fields=me.getfields();
		fields.push(me.fieldparentkey);
		Ext.Array.each(newrecords,function(record){
			jsonData.push(me.getFieldList(fields,record));
		});
		var jsonStrs = "[";
		for(var jsonNum=0;jsonNum<jsonData.length;jsonNum++){
			var jsonStr = "{";
			var gridData = jsonData[jsonNum];
			for(var gNum=0;gNum<gridData.length;gNum++){
				if(gNum == gridData.length-1){
					jsonStr = jsonStr + '"' + gridData[gNum].name + '":"' + gridData[gNum].value + '"';
				}else{
					jsonStr = jsonStr + '"' + gridData[gNum].name + '":"' + gridData[gNum].value + '",';
				}
			}
			if(jsonNum==jsonData.length-1){
				jsonStrs = jsonStrs + jsonStr + "}";
			}else{
				jsonStrs = jsonStrs + jsonStr + "},";
			}
		}
		jsonStrs = jsonStrs + "]";
		
		/**
		 * 开始提交新增
		 */
		Ext.Ajax.request({
			url: me.newUrl,
			method : 'POST',
			params : {
				jsonData : jsonStrs
			},
			success : function(resp, action) {
//				var mapPanel = document.getElementById('frame_main');
//				if(Ext.isEmpty(lineData.linkSymbolId)){
//					var respText = Ext.JSON.decode(resp.responseText);
//					mapPanel.contentWindow.drawMapLine(respText[0]);
//				}else{
//					mapPanel.contentWindow.reDrawLink(lineData);
//				}
				me.close();
				me.MsgBoxInfo('资料成功保存完毕！');
			},
			failure: function(){
			}
		});
    },
    updatedb:function(updaterecords){
    	var me=this;
    	if(!me.fieldkey) return ;
    	if(!me.updateUrl) return ;
        if(updaterecords.length<1) return ;
		var modifiedlist,jsonData=[];
		Ext.Array.each(updaterecords,function(record){
		   modifiedlist=me.getModifiedFieldNames(record);
		   if(modifiedlist.length>0)
		   {
			 jsonData.push({fields:getFieldList(modifiedlist,record),filters:getFilterList([me.fieldkey],record)});
		   }
		});
		if(jsonData.length>0)
		{
			//开始提交修改
			doAjax(me.updateUrl,jsonData,function(){
			   //客户端标记更新完毕
			   me.commit(updaterecords);
			   MsgBoxInfo('资料成功保存完毕！');
			});
		}
		else
		{
			me.commit(updaterecords);
		}
    },
    savedb:function(){
	    var me = this,
	        Store = me.store,
	        updaterecords=Store.getUpdatedRecords(),
	        newrecords=Store.getNewRecords();
	        
	    me.editingPlugin.completeEdit();
	    if(updaterecords.length<1 && newrecords.length<1)
	    {
	       me.MsgBoxWar('当前没有可更新的资料，无需保存！');
	       return;
	    }
	    if(me.isvalid(updaterecords) || me.isvalid(newrecords))
	    {
	       me.MsgBoxWar('数据未填写完整，请补全！');
	       return;
	    }
	    me.loadmask.show();
	    try{
//		    if(updaterecords.length>0) me.updatedb(updaterecords);
		    if(newrecords.length>0) me.insertdb(newrecords);
	    }finally{
	    	me.loadmask.hide();
	    }
    },
    deletedb:function(){
	    var me = this,
	        Store = me.store;
	        
	    if(!me.fieldkey) return;
	    if(!me.delUrl) return;
	    var sModel=me.getSelectionModel();
	    if(!sModel.hasSelection())
	    {
	    	me.MsgBoxWar('请选择要删除的数据！');
	    	return;
	    }
//	    me.MsgBoxQst('确认要删除所选资料吗?',function(button){
//	        if(button==='yes'){
	        	me.editingPlugin.cancelEdit();//取消编辑状态
			    var records=sModel.getSelection(),jsonData=[];
			    Ext.Array.each(records,function(record){
				   jsonData.push({filters:me.getFilterList([me.fieldkey],record)});
			    });
			    me.loadmask.show();
			    //开始提交修改
//			    doAjax(me.delUrl,jsonData,function(){
				    Ext.Array.each(records,function(record){
					   record.remove();
				    });
			       me.loadmask.hide();
//			    },function(){me.loadmask.hide();});
//	        }
//	     });
    },
    initComponent: function(){
    	var me=this;
        me.callParent(arguments);
        me.loadmask = new Ext.LoadMask(me, {msg:"请稍后，正在处理..."});
    }
});