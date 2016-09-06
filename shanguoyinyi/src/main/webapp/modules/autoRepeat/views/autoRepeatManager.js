define(
		[ 'text!modules/autoRepeat/templates/autoRepeatManager.html',
    'modules/autoRepeat/views/addAutoRepeat',
    'Portal'
], function (autoRepeatManager,addAutoRepeat,Portal) {
	var GRID_DOM = "#autoRepeat-content-div";
	var Records = {};
	return club.View.extend({
        template: club.compile(autoRepeatManager),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .autoRepeat-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
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
             $('input[name=conditions]').val('');
             this.pageData(1);
        },
        //新增
        add:function(action,record){
        	console.log(record);
        	addAutoRepeat.openAddwin({
                callback:this
            },action,record,false);
        },
        //编辑
        edit:function(action,record){
            if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords().length>1) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            console.log(record);
            if (action=='edit'&&record&&!record.id) {
                return club.toast('warn', '请选择记录!');
            };
            addAutoRepeat.openAddwin({
                callback:this
            },action,record,false);
        },
        //删除
        doDelete:function(action,records){
             if (this.selectRecords().length==0) {
                 return club.toast('warn', '请选择记录!');
             }
             var me = this,ids = [];
             for (var i = records.length - 1; i >= 0; i--) {
            	 ids.push(records[i].id);
             };
             
             var r= club.confirm('您确定要删除选中的记录吗？');
	       	 r.result.then(function resolve(){
	       		$.post(Portal.webRoot+'/autoRepeat/deleteAutoRepeat.do',{
	                 IdStr:ids.join(',')},function(result){
	                 club.toast('info', '操作成功！');
	                 me.refresh();
	             });
	       	 }, function reject(){
	    		 return;
	    	 });
             
             
        },
        
        pageData:function (page, rowNum, sortname, sortorder) {    	
        	
            var conditions = $('input[name=conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
           $.post(Portal.webRoot+'/autoRepeat/autoRepeatPage.do',{
                conditionStr:JSON.stringify({
                    'conditions':'%'+conditions+'%'}),
                start:(page-1)*rowNum,limit:rowNum},function(data){
                console.log(data);
                var result = Portal.convertPage(data.page);
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	//参数配置
            var opt = {
                height:$(window).height()-160,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'id',
                    width: 100,
                    align: "center",
                    key:true,
                    hidden:true
                },
                {
                    name: 'title',
                    label: '标题',
                    width: 80,
                    align: "center"
                },
                {
                    name: 'keyword',
                    label: '关键词',
                    width: 80,
                    align: "center"
                },{
                    name: 'weigth',
                    label: '自动回复消息排序',
                    width: 80,
                    align: "center"
                },
                   {name: 'updateTime',
                    width: 80,
                    align: "center",
                    label: '更新时间'
                },
                {name: 'state', label: '操作', width: 180, align: "center", sortable: false, 
                    formatter: function(cellval, opts, rwdat, _act) {
                    	var detail = ' <a href="javascript:void(0);" data-operation="detail" data-value="'+rwdat.id+'" title="查看明细">记录明细</a> ';
                    	Records[rwdat.id] = rwdat;
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
        	$(document).on("click", "a[data-operation]", function(){
        		var operation = $(this).attr("data-operation");
        		switch(operation){
        		case "detail": me._open_detail_window(Records[$(this).attr("data-value")], true);
        		break;
        		}
        	});
        }, 
        _open_detail_window: function(Record, readOnly) {
        	if(readOnly !== true)
        		readOnly = false;
        	addAutoRepeat.openAddwin({
                callback:this
            },"edit",Record,true);
        }
 	});
});