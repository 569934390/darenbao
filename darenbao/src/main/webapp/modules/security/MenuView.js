define([
    'text!modules/security/MenuView.html',
    'Portal'
], function (MenuView, Portal) {
	
	var nodeMap = {};
	
	var page = {};
	
	var initParams = function() {
		page.container = $(".menu-manager-view");
		page.tree = page.container.find(".menu-tree-view");
		page.info = page.container.find(".node-info-view");

    	page.container.css({
        	"max-height": ($(window).height()-90)+"px"
        });
	}
	
	var generateTree = function(container, nodeList) {
		for(var i=0; i<nodeList.length; i++) {
			var node = nodeList[i];
			nodeMap[node.name] = node;
			var li = $("<li node-name='"+node.name+"'></li>");
			var a = $("<a expan href='javascript:void(0);'>"+node.note+"</a>");
			var a2 = $("<a edit href='javascript:void(0);'>编辑</a>");
			li.append(a);
			li.append(a2);
			container.append(li);
			if(node.children && node.children.length>0) {
				var ul = $("<ul></ul>");
				li.append(ul);
				generateTree(ul, node.children);
			}
		}
	}
	
	var initTree = function() {
		$.post(Portal.webRoot+"/security/function/getTree.do", {}, function(data){
			console.log(data);
			if(data.code != "SUCCESS")
				return;
			var ul = $("<ul></ul>");
			page.tree.html(ul);
			generateTree(ul, data.data);
		});
	};
	
	var initEvent = function() {
		page.tree.on("click", "a[expan]", function(){
			$(this).siblings("ul").toggleClass("height-0");
		});
	}
	
    return club.View.extend({
        template: club.compile(MenuView),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
//            "click .menu-manager-view": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
        	initParams();
        	initTree();
        	initEvent();
//            this._initListView();
        },
        
        //刷新Grid列表
//        tbarHandler: function (event) {
//            var action = $(event.currentTarget).attr("action"),selectRecord;
//            switch(action){
//                case 'search' : this.search(); break;
//                case 'refresh': this.refresh(); break;
//                case 'add': this._open_cargo_info_window(); break;
//                case 'del': this.del(action,this.selectRecords()); break;
//            }
//        },
//        selectRecord:function(){
//            return $(GRID_DOM).grid('getSelection');
//        },
//        selectRecords:function(){
//            return $(GRID_DOM).grid('getCheckRows');
//        },
//        search:function(){
//            this.pageData(1);
//        },
//        del:function(action,records){
//            console.log(action,records);
//            if (records.length==0) {
//                var record = this.selectRecord();
//                if (record&&record.bizId) {
//                    records.push(record);
//                };
//            };
//            if (records.length==0) {
//                return club.toast('warn', '请选择记录！');
//            }
//            var me = this;
//            var ids = [];
//            for (var i = records.length - 1; i >= 0; i--) {
//            	ids.push(records[i].id);
//            };
//            this._operation_delete(ids.join(","));
//        },
//        refresh:function(){
//            console.log("刷新Grid列表");
//            $('input[name=member-conditions]').val('');
//            this.pageData(1);
//        },
//        pageData :function (page, rowNum, sortname, sortorder) {
//            var conditions = $('input[name=cargo-conditions]').val();
//            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
//            var classifyId = classifySelect.get();
//            var data = {
//            	'name':'%'+conditions+'%',
//                'classifyId': classifyId || "0", 
//                'brandId': brandSelect.get() || "0"
//            };
//            
//            $.post(Portal.webRoot+'/cargo/getList.do',{
//                conditionStr:JSON.stringify(data),
//                start:(page-1)*rowNum,limit: rowNum,name:'client'},function(result){
//                result = Portal.convertPage(result);
//                result.page = page;
//                $(GRID_DOM).grid("reloadData", result);
//            });
//            return false;
//        },
//        _initListView:function(){
//            var me = this;
//            classifySelect = classifySelectFile.init(".cargo-manager-classify-group", "info");
//            brandSelect = brandSelectFile.init(".cargo-manager-btn-brand-select", "info");
//            //参数配置
//            var opt = {
//                height:$(window).height()-125,
//                width:this.$el.width(),
//                colModel: [
//                    {name: 'id', hidden:true, key:true}, 
//                    {name: 'cargoNo', label: '货品编号', width:60, align: "center", sortable: false},  
//                    {name: 'smallImage', label: '缩略图', width: 60, align: "center", headertitle : '名称', sortable: false,  //列头的提示
//                    	formatter: function(cellval, opts, rwdat, _act){
//	                        var imagUrl =  "./image/user5-128x128.jpg";
//	                        if (rwdat.smallImage) {
//	                            imagUrl = rwdat.smallImage;
//	                        };
//	                        return '<img src = "'+imagUrl+'" class="client-face-img"/>';
//                    	}
//                    }, 
//                    {name: 'name', label: '名称', width: 60, align: "center", title: false, sortable: false},  //内容没有提示
//                    {name: 'classify', label: '分类', width: 60, align: "center", sortable: false}, 
//                    {name: 'brand', label: '品牌', width: 60, align: "center", sortable: false}, 
//                    {name: 'supplier', label: '供应商', width: 60, align: "center", sortable: false}, 
//                    {name: 'specs', label: '规格', width: 60, align: "center", sortable: false}, 
//                    {name: 'state', label: '操作', width: 60, align: "center", sortable: false, 
//	                    formatter: function(cellval, opts, rwdat, _act) {
//	                    	var detail = ' <a href="javascript:void(0);" cargo-data-operation="detail" data-value="'+rwdat.id+'" title="查看明细">查看明细</a> ';
//	                    	var edit = ' <a href="javascript:void(0);" cargo-data-operation="edit" data-value="'+rwdat.id+'" title="编辑">编辑</a> ';
//	                    	var del = ' <a href="javascript:void(0);" cargo-data-operation="delete" data-value="'+rwdat.id+'" title="删除">删除</a> ';
//                            return detail+edit+del;
//	                    }
//                    }
//                ], 
//                pager: true, 
//                rowNum: 20, 
//                rowList: [10,20,50,100,200], 
//                datatype: "json", 
//                recordtext:"{0} 到 {1} 总共: {2} ", 
//                pgtext : " {0} / {1}", 
//                rowtext:"{0}", 
//                gotext:'跳转至第{0}页', 
//                multiselect:true, 
//                pageData:this.pageData
//            };
//            this.pageData(1);
//            //加载grid
//            $grid = $(GRID_DOM).grid(opt);
//            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
//            this._init_operations();
//        }, 
//        _init_operations: function(){
//        	var me = this;
//        	$(document).on("click", "a[cargo-data-operation]", function(){
//        		var id = $(this).attr("data-value");
//        		var operation = $(this).attr("cargo-data-operation");
//        		switch(operation){
//        		case "detail": me._open_cargo_info_window(id, true); break;
//        		case "edit": me._open_cargo_info_window(id); break;
//        		case "delete": me._operation_delete(id); break;
//        		}
//        	});
//        }, 
//        _operation_delete: function(cargoIds){
//        	var me = this;
//        	club.confirm('确认删除？').result.then(function resolve(){
//                $.post(Portal.webRoot+'/cargo/delete.do',
//                		{cargoIds:cargoIds}, function(result){
//                	if(result.code) {
//                		club.toast('info', result.msg);
//                        me.refresh();
//                	} else {
//                		club.toast('warning', result.msg);
//                	}
//                });
//        	 }, function reject(){
//        		 return;
//        	 });
//        }, 
//        _open_cargo_info_window: function(cargoId, readOnly) {
//        	if(readOnly !== true)
//        		readOnly = false;
//        	cargoInfoWindow.open({
//                callback:this
//            }, cargoId, readOnly);
//        }
    });
});
