define([
    'text!modules/spreadmanager/templates/MarketSpreadManager.html',
      'modules/spreadmanager/views/SpreadDetailView',
      'modules/spreadmanager/addSpread/views/SelectGood',
      'modules/spreadmanager/editSpread/views/EditSpreadWindow',
      'Portal'
], function (spreadViewTpl,SpreadDetail,SelectGood,EditSpreadWindow,Portal) {
	var GRID_DOM = "#spread-info-content-div";
	var spreadRecords = {};
	return club.View.extend({
        template: club.compile(spreadViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .spread-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            
            $.post(Portal.webRoot+'/spreadClassify/findAllSpreadClassify.do',{},function(result){
            	if(result!=null&&result.length>0){
            		var text='';
            		for(var i=0;i<result.length;i++){
            			text+='<option value="'+result[i].id+'">'+result[i].name+'</option>'
            		}
            		$('select[name=classify-conditions]').append(text);
            	}
            });
            this._initListView();
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            console.info('12344');
            switch(action){
                case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
                case 'edit': this.edit(action,this.selectRecord());break;
                case 'add': this.add(action,this.selectRecord());break;
                case 'del': this.doDelete(action,this.selectRecords());break;
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
        refresh:function(){
        	 console.log("刷新Grid列表");
             $('input[name=spread-conditions]').val('');
             $('select[name=classify-conditions] option:first').attr('selected','selected');
             this.pageData(1);
        },
        //新增
        add:function(action,record){
        	console.log(record);
        	SelectGood.openAddwin({
                callback:this
            },action,record);
        },
//        //编辑
        edit:function(action,record){
            if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords().length>1) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            console.log(record);
            if (action=='edit'&&record&&!record.id) {
                return club.toast('warn', '请选择记录！');
            };
            EditSpreadWindow.openAddWin({
                callback:this
            },action,record);
        },
        //删除
        doDelete:function(action,records){
        	 console.log(records);
             if (this.selectRecords().length==0) {
                 return club.toast('warn', '请选择某条相应的推广！');
             }
             var me = this,ids = [];
             for (var i = records.length - 1; i >= 0; i--) {
            	 ids.push(records[i].id);
             };
             
             var r= club.confirm('您确定要删除选中的推广吗？');
	       	 r.result.then(function resolve(){
	       		$.post(Portal.webRoot+'/spreadManager/deleteSpread.do',{
	                 IdStr:ids.join(',')},function(result){
	                	 if (result.success) {
                             club.toast('info', '操作成功！');
                         }
                         else{
                             club.toast('error', result.msg);
                         } 	 
	                	 me.refresh();
	             });
	       	 }, function reject(){
	    		 return;
	    	 });    
        },
        
        pageData :function (page, rowNum, sortname, sortorder) {
            var conditions = $('input[name=spread-conditions]').val();
            var classifyConditions = $('select[name=classify-conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
           $.post(Portal.webRoot+'/spreadManager/spreadPage.do',{
                conditionStr:JSON.stringify({
                    'conditions':'%'+conditions+'%','classifyConditions':classifyConditions}),
                start:(page-1)*rowNum,limit:rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	//参数配置
            var opt = {
                height:$(window).height()-125,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'id',
                    hidden:true,
                    key:true
                },

                {
                    name: 'name',
                    label: '推广标题',
                    width: 80,
                    align: "center"
                }, {
                    name: 'classifyName',
                    label: '推广分类',
                    width: 80,
                    align: "center",
                }, {
                    name: 'readNum',
                    width: 80,
                    align: "center",
                    label: '阅读数'
                }, {
                    name: 'author',
                    width: 80,
                    align: "center",
                    label: '作者'
                }, {
                    name: 'spreadContentTypeName',
                    width: 80,
                    align: "center",
                    label: '推广内容类型'
                },

                   {name: 'updateTime',
                    width: 80,
                    align: "center",
                    label: '发布时间'
                },

                {name: 'state', label: '操作', width: 180, align: "center", sortable: false, 
                    formatter: function(cellval, opts, rwdat, _act) {
                    	var detail = ' <a href="javascript:void(0);" spread-data-operation="detail" data-value="'+rwdat.id+'" title="查看明细">查看明细</a> ';
                    	spreadRecords[rwdat.id] = rwdat;
                    	return detail;
                    }
                }              
                
                ],pager: true
                ,rowNum: 20
                ,rowList: [10,20,50,100,200]
                ,datatype: "json"
                ,recordtext:"{0} 到 {1} 总共: {2} "
                ,pgtext : " {0} / {1}"
                ,rowtext:"{0}"
                ,gotext:'跳转至第{0}页'
                ,multiselect:true
                ,pageData:this.pageData
            };
            this.pageData(1);
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
            this._init_operations();
        },
        _init_operations: function(){
        	var me = this;
        	$(document).on("click", "a[spread-data-operation]", function(){
        		var operation = $(this).attr("spread-data-operation");
        		switch(operation){
        		case "detail": me._open_spread_info_window(spreadRecords[$(this).attr("data-value")], true);
        		console.log(spreadRecords[$(this).attr("data-value")].id);
        		break;
        		}
        	});
        }, 
        _open_spread_info_window: function(Record, readOnly) {
        	if(readOnly !== true)
        		readOnly = false;
        	SpreadDetail.openAddWin({
                callback:this
            }, Record, readOnly);
        }
 	});
});