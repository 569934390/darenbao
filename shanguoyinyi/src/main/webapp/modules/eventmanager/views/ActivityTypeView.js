define([
    'text!modules/eventmanager/templates/ActivityTypeView.html',
    'modules/eventmanager/addactivitytype/views/AddActivityTypeWindow',
    'Portal'
], function (ActivityTypeViewTpl,AddActivityTypeWindow,Portal) {
	var GRID_DOM = "#activity-type-content-div";
	return club.View.extend({
        template: club.compile(ActivityTypeViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .activity-type-main-btngroup .btn": "tbarHandler",
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
                case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
                case 'edit': selectRecord = this.selectRecord();
                case 'add': this.add(action,selectRecord);break;
                case 'del': this.doDelete(action,this.selectRecords());break;
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
        search:function(){
        	 this.pageData(1);
        },
        refresh:function(){
        	 console.log("刷新Grid列表");
             $('input[name=activity-type-conditions]').val('');
             this.pageData(1);
        },
        //新增和修改
        add:function(action,record){
            if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords()[0].id!=record.id) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            if (action=='edit'&&record&&!record.id) {
                return club.toast('warn', '请选择记录！');
            };
            AddActivityTypeWindow.openAddWin({
                callback:this
            },action,record);
        },
        //删除
        doDelete:function(action,records){
        	 console.log(action,records);
             if (records.length==0) {
                 var record = this.selectRecord();
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
          	 var t= club.confirm('您确定要删除所选活动部落分类吗？');
	       	 t.result.then(function resolve(){
	       		 $.post(Portal.webRoot+'/event/activityType/delete.do',{
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
        //启用禁用
        doAction:function(action,records){
        	 console.log(action,records);
             if (records.length==0) {
                 var record = this.selectRecord();
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
            var status;
            if(action=='start'){
            	status=1;
            }
            if(action=='stop'){
            	status=0;
            }
            $.post(Portal.webRoot+'/event/activityType/updateStatus.do',{
            	IdStr:ids.join(','),status:status},function(result){
            	 if (result.success) {
         	         club.toast('info', '操作成功！');
                 }
                 else{
                     club.toast('error', result.msg);
                 }
                me.refresh();
            });
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/event/activityType/activityTypePage.do',{
                conditionStr:JSON.stringify({}),
                start:(page-1)*rowNum,limit:rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },_initListView:function(){
        	
        	//参数配置
            var opt = {
                height:$(window).height()-125,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'name',
                    width:90,
                    label: '分类名称',
                    align: "left"
                }, {
                    name: 'status',
                    width:40,
                    align: "center",
                    label: '状态',
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '启用'};
                        if (cellval=='0') {return '禁用'};
                        return cellval;
                    }
                },{
                    name: 'createTime',
                    label: '创建时间',
                    width: 140,
                    align: "left"
                }],pager: true
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
        }
 	});
});