define([
    'text!modules/cargoclassifymanager/templates/CargoClassifyView.html',
    'modules/cargoclassifymanager/addcargoclassify/views/AddCargoClassifyWindow',
    'Portal'
], function (cargoClassifyViewTpl,AddCargoClassifyWindow,Portal) {
    var GRID_DOM = "#cargo-classify-content-div";
    return club.View.extend({
        template: club.compile(cargoClassifyViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .cargo-classify-main-btngroup .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            switch(action){
                case 'refresh': this.refresh();break;
                case 'edit':
                case 'add': this.add(action,this.selectRecord());break;
                case 'del': this.del(this.selectRecords());break;
                case 'start':
                case 'stop':this.doAction(action,this.selectRecords());break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        add:function(action,record){
            if (action=='edit'&&record&&!record.id) {
                return club.toast('warn', '请选择记录！');
            };
            AddCargoClassifyWindow.openAddWin({
                callback:this
            },action,record);
        },
        del:function(records){
        	if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.id) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
        	var me = this;
        	club.confirm("是否删除！").result.then(function(){
        		var ids = [];
                for (var i = records.length - 1; i >= 0; i--) {
                	ids.push(records[i].id);
                };
                $.post(Portal.webRoot+'/cargo/classify/delete.do',{
                    ids:ids.join(',')},function(result){
                    	if(result.code == 1){
                    		club.toast('info', '操作成功！');
                            me.refresh();
                    	}else{
                    		club.toast('error', result.msg);
                    	}                    
                });
        	},function(){
        		return;
        	});
        },
        doAction:function(action,records){
            if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.id) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
            var me = this;
            var title = action == "stop"?"是否禁用(连带禁用所有子分类)":"是否启用(连带启用所有父分类)";
            club.confirm(title).result.then(function(){
        		var ids = [];
        		for (var i = records.length - 1; i >= 0; i--) {
                	ids.push(records[i].id);
                };
                $.post(Portal.webRoot+'/cargo/classify/'+action+'.do',{
                    ids:ids.join(',')},function(result){
                    club.toast('info', '操作成功！');
                    me.refresh();
                });
        	},function(){
        		return;
        	});
        },
        refresh:function(){
            console.log("刷新Grid列表");
            this.pageData(1);
        },
        pageData :function(page, rowNum, sortname, sortorder) {
            $.post(Portal.webRoot+'/cargo/classify/list.do',{},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $("#cargo-classify-content-div").grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
//            参数配置
            var opt = {
        		height:$(window).height()-125
                ,width:this.$el.width()
                ,colModel: [
                    {name: 'id',sorttype: "long",label: 'Inv No',hidden:true,key:true},
                    {name: 'imgUrl', label: '缩略图', width: 60, align: "center", headertitle : '缩略图', sortable: false,order: 1,  //列头的提示
                    	formatter: function(cellval, opts, rwdat, _act){
                    		var value = '';
		                        if(rwdat.imgUrl != undefined && rwdat.imgUrl != null && rwdat.imgUrl.length != 0){
			                        value = '<img src = "'+rwdat.imgUrl+'" class="client-face-img"/>'
		                        }
	                        return value;
                    	}
                    }, 
                    {name: 'name',label: '名称',order: 2,width: 200,align: "left",headertitle : '名称'}, 
                	{name: 'orderIndex',label: '展示排序',sorttype:"int",order: 3,width: 80,align: "center",headertitle : '展示排序'}, 
                    {name: 'statusText',label: '状态',order: 4,width: 100,align: "center",headertitle : '状态'},
                    {name: 'updateTime',label: '更新时间',order: 5,width: 150,align: "center",headertitle : '更新时间'}
                ],datatype : "json"
			    ,ExpandColumn:"name"
			    ,treeGrid:true
			    ,onRowExpand:function(e, rowData){//展开父节点rowData
			        var children = $("#cargo-classify-content-div").grid("getNodeChildren", rowData);
			        if(children.length==0){
			            $.ajax({
			            	url:Portal.webRoot+'/cargo/classify/queryByParentId.do',
			            	data:{parentId:rowData.id},
			            	async: false,
			            	type:'post',
			            	success:function(result){
			            		if(result != undefined && result.length > 0){
			    		            $("#cargo-classify-content-div").grid('addChildNodes', result, rowData);	
			            		}
			            	}
			            });
			        }
			    }
                ,multiselect:true
            	,pager: false
                ,pageData:this.pageData
            };        	
            this.pageData(1);
//            加载grid
            $grid = $("#cargo-classify-content-div").grid(opt);       
        }
    });
});
