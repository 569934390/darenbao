define([
    'text!modules/eventmanager/templates/ActivityManagerView.html',
    'modules/eventmanager/addactivity/views/AddActivityWindow',
    'modules/eventmanager/detailsactivity/views/DetailsActivityWindow',
    'modules/eventmanager/queryactivityuser/views/QueryActivityUserWindow',
    'modules/eventmanager/queryactivitycomment/views/QueryActivityCommentWindow',
    'Portal'
], function (ActivityViewTpl,AddActivityWindow,DetailsActivityWindow,QueryActivityUserWindow,QueryActivityCommentWindow,Portal) {
    var ACTIVITY_NO_CHECK_LISTVIEW = "#activity-manager-no-check-content-div";
    var ACTIVITY_CHECK_LISTVIEW = "#activity-manager-check-content-div";
    return club.View.extend({
        template: club.compile(ActivityViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .activity-manager .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            $('#activity-tabs').tabs();
            $('.activity-manager input[field=datetime]').datetimepicker({
                format: 'yyyy-mm-dd'
            });
            var startTime = new Date(),endTime = new Date();
            startTime.setDate(startTime.getDate()-90);
            endTime.setDate(endTime.getDate()+1);
            $('.activity-manager input[name=beginTime]').datetimepicker('value',club.dateutil.format(startTime,'yyyy-mm-dd'));
           // this._initNoCheckListView();
            this._initCheckListView();
            this._init();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectCheckRecord;
            switch(action){
                case 'tabs-check': this.clickTableCheck();break;
                case 'search-no-check' : this.searchNoCheck();break;
                case 'search-check': this.searchCheck();break;
                case 'refresh-no-check': this.refreshNoCheck();break;
                case 'refresh-check': this.refreshCheck();break;
                case 'no-check-details':
                case 'check-details':this.details(action,$(event.currentTarget).attr("id"));break;
                case 'no-check-queryUser':
                case 'check-queryUser':this.queryUser(action,$(event.currentTarget).attr("id"));break;
                case 'no-check-queryComment':
                case 'check-queryComment':this.queryComment(action,$(event.currentTarget).attr("id"));break;
                case 'start':
                case 'stop':this.doAction(action,this.selectCheckRecords());break;
                case 'no-check':this.doNoCheck(action,this.selectNOCheckRecords());break;
                case 'check':this.doCheck(action,this.selectNOCheckRecords());break;
                case 'check-edit':selectCheckRecord = this.selectCheckRecord();
                case 'check-add':this.add(action,selectCheckRecord);break;
                case 'check-delete':this.doDelete(action,this.selectCheckRecords());break;
            }
        },clickTableCheck:function(){
        	this._initCheckListView();
        },
        selectCheckRecord:function(){
            return $(ACTIVITY_CHECK_LISTVIEW).grid('getSelection');
        },
        selectCheckRecords:function(){
            return $(ACTIVITY_CHECK_LISTVIEW).grid('getCheckRows');
        },
        selectNoCheckRecord:function(){
            return $(ACTIVITY_NO_CHECK_LISTVIEW).grid('getSelection');
        },
        selectNOCheckRecords:function(){
            return $(ACTIVITY_NO_CHECK_LISTVIEW).grid('getCheckRows');
        },
        searchNoCheck:function(){
            this.pageNoCheckData(1);
        },
        searchCheck:function(){
            this.pageCheckData(1);
        },
        refreshCheck:function(){
       	    console.log("刷新Grid列表");
            $('#tabs-check input[name=title]').val('');
            $('#tabs-check select[name=activityTypeId]').val('0');
            $('#tabs-check input[name=beginTime]').val('');
            this.pageCheckData(1);
       },
       refreshNoCheck:function(){
//    	   console.log("刷新Grid列表");
//           $('#tabs-no-check input[name=title]').val('');
//           $('#tabs-no-check select[name=activityTypeId]').val('0');
//           $('#tabs-no-check input[name=beginTime]').val('');
//    	   this.pageNoCheckData(1);
       },
       add:function(action,record){
      	 if (action=='check-edit'&&this.selectCheckRecords().length>0 ){
               if (this.selectCheckRecords()[0].id!=record.id) {
                   return club.toast('warn', '有歧义，不能同时选择！');
               };
           };
           if (action=='check-edit'&&record&&!record.id) {
               return club.toast('warn', '请选择记录！');
           }
           AddActivityWindow.openAddWin({
               callback:this
           },action,record);
      },
      //删除
      doDelete:function(action,records){
      	 console.log(action,records);
           if (records.length==0) {
               var record = this.selectCheckRecord();
               if (record&&record.id) {
                   records.push(record);
               };
           };
           if (records.length==0) {
               return club.toast('warn', '请选择记录！');
           }
           var me = this,ids = [];
           for (var i = records.length - 1; i >= 0; i--) {
          	 ids.push(records[i].id);
           };
        	 var t= club.confirm('您确定要删除所选活动信息吗？');
	       	 t.result.then(function resolve(){
	       		 $.post(Portal.webRoot+'/event/eventActivity/delete.do',{
	       			Ids:ids.join(',')},function(result){
	        	    if (result.success) {
	        	          club.toast('info', '操作成功！');
	                }
	                else{
	                    club.toast('error', result.msg);
	                }
	                 me.refreshNoCheck();
	                 me.refreshCheck();
	             });
	       	 }, function reject(){
	    		 return;
	    	 });
      },
      doCheck:function(action,records){
        	if (records.length == 0) {
				var record = this.selectNoCheckRecord();
				if (record && record.id) {
					records.push(record);
				}
			}
			if (records.length == 0) {
				return club.toast('warn', '请选择记录！');
			}
			var me = this;
			var title = "是否审核通过";
			club.confirm(title).result.then(function() {
				var ids = [];
				for (var i = records.length - 1; i >= 0; i--) {
					ids.push(records[i].id);
				}
				$.post(Portal.webRoot + '/event/eventActivity/check.do', {
					Ids : ids.join(',')
				}, function(result) {
					if (result.success) {
	        	        club.toast('info', '操作成功！');
	                }
	                else{
	                    club.toast('error', result.msg);
	                }
					me.refreshNoCheck();
				});
			}, function() {
				return;
			});
        },
        doNoCheck:function(action,records){
        	if (records.length == 0) {
        		var record = this.selectNoCheckRecord();
        		if (record && record.id) {
        			records.push(record);
        		}
        	}
        	if (records.length == 0) {
        		return club.toast('warn', '请选择记录！');
        	}
        	var me = this;
        	var t=club.prompt('请填写审核不通过原因:');	
        	t.result.then(function resolve(val){
        		if(val==''||val==null||val==undefined){
        			club.toast('warn', '请填写审核不通过原因!');
        		}else{
        			var ids = [];
    				for (var i = records.length - 1; i >= 0; i--) {
    					ids.push(records[i].id);
    				}
    				$.post(Portal.webRoot + '/event/eventActivity/noCheck.do', {
    					Ids : ids.join(','),failure:val
    				}, function(result) {
    					if (result.success) {
	  	        	        club.toast('info', '操作成功！');
	  	                }
	  	                else{
	  	                    club.toast('error', result.msg);
	  	                }
    					me.refreshNoCheck();
    				});
        		}
       	 	}, function reject(){
       	 		return;
       	 	});
        },
        doAction:function(action,records){
        	if (records.length == 0) {
        		var record = this.selectCheckRecord();
        		if (record && record.id) {
        			records.push(record);
        		}
        	}
        	if (records.length == 0) {
        		return club.toast('warn', '请选择记录！');
        	}
        	var me = this;
        	var title = action == "stop" ? "是否禁用": "是否启用";
			club.confirm(title).result.then(function() {
		    	var ids = [];
				for (var i = records.length - 1; i >= 0; i--) {
					ids.push(records[i].id);
				}
				$.post(Portal.webRoot + '/event/eventActivity/' + action + '.do', {
					Ids : ids.join(',')
				}, function(result) {
					if (result.success) {
	        	        club.toast('info', '操作成功！');
	                }
	                else{
	                    club.toast('error', result.msg);
	                }
					me.refreshCheck();
				});
			}, function() {
				return;
			});
        },
        details:function(action,id){
        	var rowValue ;
        	if(action=='no-check-details'){
        	   rowValue = $(ACTIVITY_NO_CHECK_LISTVIEW).grid('getRowData',id);
        	}
        	else{
        	   rowValue = $(ACTIVITY_CHECK_LISTVIEW).grid('getRowData',id);
        	}
        	DetailsActivityWindow.openAddWin({
                callback:this
            },action,rowValue);
        },queryUser:function(action,id){
        	QueryActivityUserWindow.openAddWin({
                callback:this
            },action,id);
        },queryComment:function(action,id){
        	QueryActivityCommentWindow.openAddWin({
                callback:this
            },action,id);
        },
        _init:function(){
        	 $.post(Portal.webRoot+'/event/activityType/findList.do',{},function(result){
        		 var activityTypeStr="<option value='0'>全部</option>";
        		 for(var i=0;i<result.length;i++){
        			 activityTypeStr+="<option value='"+result[i].id+"'>"+result[i].name+"</option>";
        		 }
        		 $(".activity-manager select[name=activityTypeId]").html(activityTypeStr);
             });
        },
        pageNoCheckData :function(page, rowNum, sortname, sortorder) {
            var title = $('#tabs-no-check input[name=title]').val();
            var activityTypeId = $('#tabs-no-check select[name=activityTypeId]').val();
            var beginTime = $('#tabs-no-check input[name=beginTime]').val();
            rowNum = rowNum || $(ACTIVITY_NO_CHECK_LISTVIEW).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/event/eventActivity/eventActivityPage.do',{
                conditionStr:JSON.stringify({
                    'title':'%'+title+'%',
                    'activityTypeId':activityTypeId,
                    'beginTime':beginTime,'activityStatus':0})
                ,start:(page-1)*rowNum,limit:rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(ACTIVITY_NO_CHECK_LISTVIEW).grid("reloadData", result);
            });
            return false;
        },
        _initNoCheckListView:function(){
        	//参数配置
            var opt = {
                height:$(window).height()-175,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                },{
                    name: 'subbranchId',
                    hidden:true
                },{
                    name: 'subbranchName',
                    label: '所属分店',
                    width: 60,
                    align: "center"
                },{
                    name: 'title',
                    width:90,
                    label: '活动主题',
                    align: "center"
                },  {
                    name: 'type',
                    label: '类型',
                    width: 60,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                	   if (cellval=='1') {
                       	return '户外';
                       }else if (cellval=='2'){
                       	return '室内';
                       }else{
                       	return '全部';
                       }
                    }
                },  {
                    name: 'activityTypeId',
                    hidden:true
                },  {
                    name: 'activityTypeName',
                    label: '活动类型',
                    width: 60,
                    align: "center"
                },{
                    name: 'sponsorName',
                    width:100,
                    align: "center",
                    label: '主办方'
                }, {
                    name: 'beginTime',
                    label: '开始时间',
                    width: 100,
                    align: "center"
                }, {
                    name: 'endTime',
                    label: '结束时间',
                    width: 100,
                    align: "center"
                },  {
                    name: 'regStartTime',
                    label: '开始报名时间',
                    width: 100,
                    align: "center"
                },{
                    name: 'regEndTime',
                    label: '截止报名时间',
                    width: 100,
                    align: "center"
                }, {
                    name: 'activityAddress',
                    label: '活动地址',
                    width: 160,
                    align: "center"
                },{
                    name: 'numberLimit',
                    label: '限制人数',
                    width: 40,
                    align: "center"
                }, {
                    name: 'participation',
                    label: '报名人数',
                    width: 40,
                    align: "center"
                }, {
                    name: 'activityStatus',
                    label: '活动状态',
                    width: 60,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='0') {return '待审核'};
                        if (cellval=='1') {return '进行中'};
                        if (cellval=='2') {return '完成'};
                        if (cellval=='3') {return '禁止'};
                        return cellval;
                    }
                }, {
                    name: 'failure',
                    label: '原因',
                    width: 100,
                    align: "center"
                }, {
                    width:120,
                    label: '操作',
                    align: "center",
                    formatter:function(cellval, opts, rwdat, _act){
                    	var value='<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="no-check-details">查看详情</a><br>';
                    	value+='<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="no-check-queryUser">查看报名</a><br>';
                    	value+='<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="no-check-queryComment">查看评论</a>';
                        return value;
                    },
                    title: false //内容没有提示
                },{
                    name: 'content',
                    hidden:true
                }],pager: true
                ,rowNum: 20
                ,rowList: [10,20,50,100,200]
                ,datatype: "json"
                ,recordtext:"{0} 到 {1} 总共: {2} "
                ,pgtext : " {0} / {1}"
                ,rowtext:"{0}"
                ,gotext:'跳转至第{0}页'
                ,multiselect:true
                ,pageData:this.pageNoCheckData
            };
            this.pageNoCheckData(1);
            //加载grid
            $grid = $(ACTIVITY_NO_CHECK_LISTVIEW).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        },
        pageCheckData :function(page, rowNum, sortname, sortorder) {
            var title = $('#tabs-check input[name=title]').val();
            var activityTypeId = $('#tabs-check select[name=activityTypeId]').val();
            var beginTime = $('#tabs-check input[name=beginTime]').val();
            rowNum = rowNum || $(ACTIVITY_CHECK_LISTVIEW).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/event/eventActivity/eventActivityPage.do',{
                conditionStr:JSON.stringify({
                    'title':'%'+title+'%',
                    'activityTypeId':activityTypeId,
                    'beginTime':beginTime,'activityStatus':1})
                ,start:(page-1)*rowNum,limit: rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(ACTIVITY_CHECK_LISTVIEW).grid("reloadData", result);
            });
            return false;
        },
        _initCheckListView:function(){
        	//参数配置
            var opt = {
                height:$(window).height()-205,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                },{
                    name: 'subbranchId',
                    hidden:true
                },{
                    name: 'subbranchName',
                    label: '所属分店',
                    width: 60,
                    align: "left"
                },{
                    name: 'title',
                    width:90,
                    label: '活动主题',
                    align: "center"
                }, {
                    name: 'type',
                    label: '类型',
                    width: 60,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {
                        	return '户外';
                        }else if (cellval=='2'){
                        	return '室内';
                        }else{
                        	return '全部';
                        }
                    }
                },  {
                    name: 'activityTypeId',
                    hidden:true
                },  {
                	name: 'activityPic',
                	hidden:true
                },  {
                    name: 'activityTypeName',
                    label: '活动类型',
                    width: 60,
                    align: "center"
                },{
                    name: 'sponsorName',
                    width:100,
                    align: "center",
                    label: '主办方'
                }, {
                    name: 'beginTime',
                    label: '开始时间',
                    width: 100,
                    align: "center"
                }, {
                    name: 'endTime',
                    label: '结束时间',
                    width: 100,
                    align: "center"
                },  {
                    name: 'regStartTime',
                    label: '开始报名时间',
                    width: 100,
                    align: "center"
                }, {
                    name: 'regEndTime',
                    label: '截止报名时间',
                    width: 100,
                    align: "center"
                }, {
                    name: 'activityAddress',
                    label: '活动地址',
                    width: 160,
                    align: "center"
                },{
                    name: 'numberLimit',
                    label: '限制人数',
                    width: 40,
                    align: "center"
                }, {
            	    name: 'participation',
                    label: '报名人数',
                    width: 40,
                    align: "center"
                },{
                    name: 'activityStatus',
                    label: '活动状态',
                    width: 60,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='0') {return '待审核'};
                        if (cellval=='1') {return '进行中'};
                        if (cellval=='2') {return '完成'};
                        if (cellval=='3') {return '禁止'};
                        return cellval;
                    }
                },{
                    name: 'status',
                    label: '状态',
                    width: 60,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '启用'};
                        if (cellval=='0') {return '禁用'};
                        return cellval;
                    }
                },{
                    width:120,
                    label: '操作',
                    align: "center",
                    formatter:function(cellval, opts, rwdat, _act){
                    	var value='<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="check-details">查看详情</a><br>';
                    	value+='<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="check-queryUser">查看报名</a><br>';
                    	value+='<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="check-queryComment">查看评论</a>';
                        return value;
                    },
                    title: false //内容没有提示
                },{
                    name: 'content',
                    hidden:true
                }],pager: true
                ,rowNum: 20
                ,rowList: [10,20,50,100,200]
                ,datatype: "json"
                ,recordtext:"{0} 到 {1} 总共: {2} "
                ,pgtext : " {0} / {1}"
                ,rowtext:"{0}"
                ,gotext:'跳转至第{0}页'
                ,multiselect:true
                ,pageData:this.pageCheckData
            };
            this.pageCheckData(1);
            //加载grid
            $grid = $(ACTIVITY_CHECK_LISTVIEW).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
