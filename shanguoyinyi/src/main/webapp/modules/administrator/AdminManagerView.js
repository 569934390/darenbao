define([
    'text!modules/administrator/AdminManagerView.html',
    'modules/administrator/AddAdminWindow',
    'Portal'
], function (AdminManagerView, AddAdminWindow, Portal) {
	
    var GRID_DOM = ".admin-manager-view .main-content-table";
    var me = this;
    
    var roles = {};
    
    return club.View.extend({
        template: club.compile(AdminManagerView),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .admin-manager-view .search-bar .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
                case 'search' : this.search(); break;
                case 'refresh': this.refresh(); break;
                case 'add': this._open_add_admin_window(); break;
                case 'del': this.del(action,this.selectRecords()); break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        search:function(){
            this.pageData(1);
        },
        del:function(action,records){
            if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.staffId) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
            var me = this;
            var ids = [];
            for (var i = records.length - 1; i >= 0; i--) {
            	ids.push(records[i].staffId);
            };
            this._operation_delete(ids.join(","));
        },
        refresh:function(){
            $('input[name=search-conditions]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var conditions = $('input[name=search-conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/staffManager/staffPage.do',{conditionStr:JSON.stringify({'conditions':'%'+conditions+'%'}), start:(page-1)*rowNum, limit: rowNum},function(result){
            	result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
            var me = this;
            //参数配置
            var opt = {
                height:$(window).height()-125,
                width:this.$el.width(),
                colModel: [
                    {name: 'staffId', hidden:true, key:true}, 
                    {name: 'loginName', label: '登录名', width: "100px", align: "center", sortable: false},  
                    {name: 'staffName', label: '备注', width: "120px", align: "center", sortable: false},  //内容没有提示
                    {name: 'roles', label: '授权角色', align: "center", sortable: false}, 
                    {name: 'updateTime', label: '更新时间', width: "80px", align: "center", sortable: false},
                    {name: 'operation', label: '操作', width: "80px", align: "center", sortable: false, title: false, 
	                    formatter: function(cellval, opts, rwdat, _act) {
	                    	roles[rwdat.loginName] = rwdat;
	                    	var edit = '<a href="javascript:void(0);" data-operation="edit" data-value="'+rwdat.loginName+'" title="编辑">编辑</a> ';
	                    	var del;
	                    	if (rwdat.staffId==1)
	                    		del = '';
	                    	else
	                    		del = ' <a href="javascript:void(0);" data-operation="delete" data-value="'+rwdat.staffId+'" title="删除">删除</a> ';
                            return edit+del;
	                    }
                    }
                ], 
                pager: true, 
                rowNum: 20, 
                rowList: [10,20,50,100,200], 
                datatype: "json", 
                recordtext:"{0} 到 {1} 总共: {2} ", 
                pgtext : " {0} / {1}", 
                rowtext:"{0}", 
                gotext:'跳转至第{0}页', 
                multiselect: false, 
                pageData:this.pageData
            };
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            this.pageData(1);
            this.initOperations();
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        },
        initOperations: function(){
        	var me = this;
        	$(GRID_DOM).on("click", "a[data-operation]", function(){
        		var value = $(this).attr("data-value");
        		var operation = $(this).attr("data-operation");
        		switch(operation){
        		case "edit": 
        			var data = roles[value];
        			if(!data)
        				return;
        			me._open_add_admin_window(data); 
        			break;
        		case "delete": me._operation_delete(value); break;
        		}
        	});
        }, 
        _operation_delete: function(ids) {
        	var me = this;
            $.post(Portal.webRoot+'/staffManager/delete.do',{params: ids},function(result){
            	if(result)
            		if(result.code===1)
                		club.toast("info", result.msg);
            		else
            			club.toast("error", result.msg);
                me.pageData(1);
            });
        }, 
        _open_add_admin_window: function(data) {
        	AddAdminWindow.open(this, data);
        }
    });
});
