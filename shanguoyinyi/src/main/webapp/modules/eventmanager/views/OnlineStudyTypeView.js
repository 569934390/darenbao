define([ 'text!modules/eventmanager/templates/OnlineStudyTypeView.html',
		'modules/eventmanager/addonlinestudytype/views/AddOnlineStudyTypeWindow',
		'Portal' ], function(OnlineStudyTypeViewTpl, AddOnlineStudyTypeWindow,
		Portal) {
	var GRID_DOM = "#online-study-type-content-div";
	return club.View.extend({
		template : club.compile(OnlineStudyTypeViewTpl),
		i18nData : club.extend({}),
		listView : {},
		views : {},
		events : {
			"click .online-study-type-main-btngroup .btn" : "tbarHandler"
		},
		// 这里用来初始化页面上要用到的club组件
		_afterRender : function() {
			this._initListView();
		},
		selectRecord : function() {
			return $(GRID_DOM).grid('getSelection');
		},
		selectRecords : function() {
			return $(GRID_DOM).grid('getCheckRows');
		},
		// 刷新Grid列表
		tbarHandler : function(event) {
			var action = $(event.currentTarget).attr("action"),selectRecord;
			switch (action) {
			case 'refresh':this.refresh();break;
			case 'edit':
            case 'add': this.add(action,this.selectRecord());break;
			case 'del':this.del(this.selectRecords());break;
			case 'start':
			case 'stop':this.doAction(action, this.selectRecords());break;
			}
		},
		add : function(action, record) {
		    if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords()[0].id!=record.id) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
	        };
			if (action == 'edit' && record && !record.id) {
				return club.toast('warn', '请选择记录！');
			}
			if(action=='add'&&record&&record.grade==1){
				return club.toast('warn', '二级分类不能添加子分类！');
			}
			AddOnlineStudyTypeWindow.openAddWin({
				callback : this
			}, action, record);
		},
		del : function(records) {
			if (records.length == 0) {
				return club.toast('warn', '请选择记录！');
			}
			var me = this;
			club.confirm("是否删除！").result.then(function() {
				var ids = [];
				for (var i = records.length - 1; i >= 0; i--) {
					ids.push(records[i].id);
				}
				;
				$.post(Portal.webRoot + '/event/onlineStudyType/delete.do', {
					ids : ids.join(',')
				}, function(result) {
					if (result.success) {
	        	        club.toast('info', '操作成功！');
	                }
	                else{
	                    club.toast('error', result.msg);
	                }
					me.refresh();
				});
			}, function() {
				return;
			});
		},
		doAction : function(action, records) {
			if (records.length == 0) {
				var record = this.selectRecord();
				if (record && record.id) {
					records.push(record);
				}
			}
			if (records.length == 0) {
				return club.toast('warn', '请选择记录！');
			}
			var me = this;
			var title = action == "stop" ? "是否禁用(连带禁用所有子分类)"
					: "是否启用(连带启用所有父分类)";
			club.confirm(title).result.then(function() {
				var ids = [];
				for (var i = records.length - 1; i >= 0; i--) {
					ids.push(records[i].id);
				}
				;
				$.post(Portal.webRoot + '/event/onlineStudyType/' + action + '.do', {
					ids : ids.join(',')
				}, function(result) {
					if (result.success) {
	        	        club.toast('info', '操作成功！');
	                }
	                else{
	                    club.toast('error', result.msg);
	                }
					me.refresh();
				});
			}, function() {
				return;
			});
		},
		refresh : function() {
			console.log("刷新Grid列表");
			this.pageData(1);
		},
		pageData : function(page, rowNum, sortname, sortorder) {
			$.post(Portal.webRoot + '/event/onlineStudyType/getParents.do', {},
					function(result) {
						result = Portal.convertPage(result);
						result.page = page;
						$(GRID_DOM).grid("reloadData", result);
					});
			return false;
		},
		_initListView : function() {
			// 参数配置
			var opt = {
				height : $(window).height() - 125,
				width : this.$el.width(),
				colModel : [ {
					name : 'id',
					hidden : true
				}, {
					name : 'parentId',
					hidden : true
				}, {
					name : 'parentName',
					hidden : true
				}, {
					name : 'name',
					label : '名称',
					width : 200,
					align : "left",
					key : true
				}, {
					name : 'status',
					label : '状态',
					width : 100,
					align : "center",
					formatter : function(cellval, opts, rwdat, _act) {
						if (cellval == '1') {
							return '启用'
						}
						;
						if (cellval == '0') {
							return '禁用'
						}
						;
						return cellval;
					}
				} ],
				datatype : "json",
				ExpandColumn : "name",
				treeGrid : true,
				onRowExpand : function(e, rowData) {// 展开父节点rowData
					var childrend=$(GRID_DOM).grid('getNodeChildren', rowData);
					if ((rowData.parentId == null || rowData.parentId == "")&&childrend.length==0) {
						$.post(Portal.webRoot
								+ '/event/onlineStudyType/getByParentId.do', {
							parentId : rowData.id
						}, function(result){
					
						    //删除孩子节点
							for(var i=0;i<result.length;i++){
								delete result[i].children;
							}
						
							$(GRID_DOM).grid('addChildNodes', result, rowData);
						});
					}
				},
				multiselect : true,
				pager : false,
				pageData : this.pageData
			};
			this.pageData(1);
			// 加载grid
			$grid = $(GRID_DOM).grid(opt);
		}
	});
});
