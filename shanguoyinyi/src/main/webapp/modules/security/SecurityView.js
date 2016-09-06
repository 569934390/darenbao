define([
    'text!modules/security/SecurityView.html',
    'modules/security/AddRoleWindow',
    'Portal'
], function (SecurityView, AddRoleWindow, Portal) {

	var nodeMap = {};
	
	var page = {};
	
	var generateTree = function(container, nodeList, parents) {
		for(var i=0; i<nodeList.length; i++) {
			var node = nodeList[i];
			nodeMap[node.name] = node;
			var li = $("<li node-name='"+node.name+"'></li>");
			var ckb = $("<input type='checkbox' node-id='"+node.id+"' node-name='"+node.name+"' cur-deep="+parents.length+" />");
			var a = $("<a expan href='javascript:void(0);'>"+node.note+"</a>");
			for(var j=0; j<parents.length; j++)
				ckb.attr("deep-"+j, parents[j]);
			li.append(ckb);
			li.append(a);
			container.append(li);
			if(node.children && node.children.length>0) {
				var ul = $("<ul class='hidden'></ul>");
				li.append(ul);
				generateTree(ul, node.children, parents.concat([node.name]));
			}
		}
	}
	
	var initTree = function() {
		$.post(Portal.webRoot+"/security/function/getTree.do", {}, function(data){
			if(data.code != "SUCCESS")
				return;
			var ul = $("<ul></ul>");
			page.tree.html(ul);
			generateTree(ul, data.data, []);
		});
	};
	
	var initEvent = function() {
		page.treeOperations.on("click", "a[operation='save']", treeSave);
		page.treeOperations.on("click", "a[operation='close']", treeClose);
		page.treeOperations.on("click", "a[operation='expan-all']", treeExpanAll);
		page.treeOperations.on("click", "a[operation='deexpan-all']", treeDeexpanAll);
		page.treeOperations.on("click", "a[operation='reset']", function(){
        	club.confirm('确认重置？').result.then(function resolve(){
        		treeReset();
        	}, function reject(){
        		return;
    		});
		});
		page.treeOperations.on("click", "a[operation='select-all']", function(){
        	club.confirm('确认选择所有？').result.then(function resolve(){
        		treeSelectAll();
        	}, function reject(){
        		return;
    		});
		});
		page.treeOperations.on("click", "a[operation='clear-all']", function(){
        	club.confirm('确认清除所有？').result.then(function resolve(){
    			treeClearAll();
        	}, function reject(){
        		return;
    		});
		});
		
		page.tree.on("click", "input[type='checkbox']", function(){
			var ckb = $(this);
			var nodeName = ckb.attr("node-name");
			var curDeep = parseInt(ckb.attr("cur-deep"));
			page.tree.find("input[type='checkbox'][deep-"+curDeep+"='"+nodeName+"']").prop("checked", ckb.prop("checked"));
			changeParents(ckb, curDeep);
		});
		
		page.tree.on("click", "a[expan]", function(){
			$(this).siblings("ul").toggleClass("hidden");
		});
	}
	var changeParents = function(node, deep) {
		var parentNodeName = node.attr("deep-"+(deep-1));
		var totalNum = page.tree.find("input[type='checkbox'][deep-"+(deep-1)+"='"+parentNodeName+"'][cur-deep="+deep+"]").length;
		var checkedNum = page.tree.find("input[type='checkbox'][deep-"+(deep-1)+"='"+parentNodeName+"'][cur-deep="+deep+"]:checked").length;
		var pnode = page.tree.find("input[type='checkbox'][node-name='"+parentNodeName+"']")
		pnode.prop("indeterminate", false);
		if(checkedNum==0)
			pnode.prop("checked", false);
		else if(totalNum==checkedNum)
			pnode.prop("checked", true);
		else
			pnode.prop("indeterminate", true);
		if(deep>0)
			changeParents(node, deep-1);
	}
	var treeSave = function() {
		var selected = page.tree.find("input[type='checkbox']:checked");
		var functionIds = [];
		for(var i=0; i<selected.length; i++)
			functionIds.push(selected[i]["attributes"]["node-id"]["value"]);
		$.post(Portal.webRoot+"/security/role/function/save.do", {
			roleId: page.curRole, 
			functionIds: functionIds.join(",")
		}, function(result){
    		club.toast("info", result.msg);
    		treeClose();
		});
	}
	var treeClose = function() {
		page.treeView.addClass("hidden");
	}
	var treeOpen = function() {
		page.treeView.removeClass("hidden");

		/** ↓ 这段代码不要动，暂定处理方法，后面再说 BEGIN */
		var ckb1 = page.treeView.find("input[node-id='1']");
		ckb1.prop("checked", false);
		ckb1.attr("disabled", true);
		
		var ckb2 = page.treeView.find("input[node-id='2']");
		ckb2.prop("checked", true);
		ckb2.attr("disabled", true);
		/** ↑ 这段代码不要动，暂定处理方法，后面再说 END */
		
	}
	var treeExpanAll = function() {
		page.tree.find("li > ul").removeClass("hidden");
	}
	var treeDeexpanAll = function() {
		page.tree.find("li > ul").addClass("hidden");
	}
	var treeReset = function() {
		if(!page.curRole)
			return;
		treeClearAll();
		initRoleFunctions(page.curRole);
	}
	var treeSelectAll = function() {
		page.tree.find("input[type='checkbox']").prop("checked", true);

		/** ↓ 这段代码不要动，暂定处理方法，后面再说 BEGIN */
		var ckb1 = page.treeView.find("input[node-id='1']");
		ckb1.prop("checked", false);
		ckb1.attr("disabled", true);
		/** ↑ 这段代码不要动，暂定处理方法，后面再说 END */
	}
	var treeClearAll = function() {
		page.tree.find("input[type='checkbox']").prop("checked", false);
		page.tree.find("input[type='checkbox']").prop("indeterminate", false);

		/** ↓ 这段代码不要动，暂定处理方法，后面再说 BEGIN */
		var ckb2 = page.treeView.find("input[node-id='2']");
		ckb2.prop("checked", true);
		ckb2.attr("disabled", true);
		/** ↑ 这段代码不要动，暂定处理方法，后面再说 END */
	}
	
	var editTree = function(roleId) {
		treeClearAll();
		initRoleFunctions(roleId);
		page.curRole = roleId;
	}
	
	var initRoleFunctions = function(roleId) {
		$.post(Portal.webRoot+"/security/role/function/getlist.do", {
			roleId: roleId
		}, function(data){
			if(data.code != "SUCCESS")
				return;
			data = data.data;
			for(var i=0; i<data.length; i++) {
				var node = page.tree.find("input[node-id='"+data[i]+"']");
				var deep = parseInt(node.attr("cur-deep"));
				if(page.tree.find("input[type='checkbox'][deep-"+deep+"='"+node.attr("node-name")+"']").length==0) {
					node.prop("checked", true);
					changeParents(node, deep);
				}
			}
			treeOpen();
			location.href = "#function-tree-view-div";
		});
	}
	
    return club.View.extend({
        template: club.compile(SecurityView),
        i18nData: club.extend({}),

        listView: {},
        views: {},
        events: {
            "click .security-view-container .role-manager .search-bar .btn": "roleMgrBtnHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
        	page.container = $(".security-view-container");
        	page.roleManager = $(".security-view-container .role-manager")
        	page.roleMgrTable = $(".security-view-container .role-manager .role-manager-table");

        	page.treeView = $(".security-view-container .function-tree-view")
        	page.treeOperations = $(".security-view-container .function-tree-view .function-tree-operations");
    		page.tree = $(".security-view-container .function-tree-view .function-tree");

    		
        	initTree();
        	initEvent();

        	page.tree.css({
            	"height": ($(window).height()-130)+"px"
            });
        	
        	page.container.css({
        		"height": ($(window).height()-90)+"px"
            });
        	
        	this._initListView();
        },
        loadRoleData: function() {
        	$.post(Portal.webRoot+'/security/role/getlist.do',{},function(result){
        		page.roleMgrTable.grid('clearGridData');
        		for (var i=0; i<result.data.length; i++)
        			page.roleMgrTable.grid('addRowData', i + 1, result.data[i]);
			});
        }, 
        //刷新Grid列表
        roleMgrBtnHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            switch(action){
                case 'refresh': this.loadRoleData(); break;
                case 'add': this.openAddRoleWindow(); break;
                case 'del': this.del(this.selectRecord()); break;
            }
        },
        selectRecord:function(){
            return page.roleMgrTable.grid('getSelection');
        },
        del:function(id){
        	var me = this;
            $.post(Portal.webRoot+'/security/role/delete.do',{
            	id: id
            },function(result){
        		club.toast("info", result.msg);
            	me.loadRoleData();
            });
        },
//        refresh:function(){
//            console.log("刷新Grid列表");
//            $('input[name=member-conditions]').val('');
//            this.pageData(1);
//        },
        _initListView:function(){
            var me = this;
            //参数配置
            var opt = {
            	height: ($(window).height()-160)+"px", 
                colModel: [
                    {name: 'id', width: 80, hidden: true, align: "center"}, 
                    {name: 'name', label: '名称', align: "center", sortable: false},  //内容没有提示
                    {name: 'note', label: '备注', width: 100, align: "center", sortable: false},
                    {name: 'operation', label: '操作', width: 80, align: "center", sortable: false, 
	                    formatter: function(cellval, opts, rwdat, _act) {
	                    	if(rwdat.id==1)
	                    		return "";
	                    	var edit = ' <a href="javascript:void(0);" data-operation="edit" data-value="'+rwdat.id+'" title="编辑">编辑</a> ';
	                    	var del = ' <a href="javascript:void(0);" data-operation="delete" data-value="'+rwdat.id+'" title="删除">删除</a> ';
                            return edit+del;
	                    }
                    }
                ], 
                autowidth: true, 
                pager: false, 
                multiselect: false
            };
            //加载grid
            page.roleMgrTable.grid(opt);
            this.initOperations();
            this.loadRoleData();
        }, 
        initOperations: function(){
        	var me = this;
        	page.roleMgrTable.on("click", "a[data-operation]", function(){
        		var id = $(this).attr("data-value");
        		var operation = $(this).attr("data-operation");
        		switch(operation){
        		case "edit": editTree(id); break;
        		case "delete": me.del(id); break;
        		}
        	});
        }, 
        openAddRoleWindow: function() {
        	AddRoleWindow.open(this);
        }
    });
});
