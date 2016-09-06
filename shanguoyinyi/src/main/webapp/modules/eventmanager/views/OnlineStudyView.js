define([
    'text!modules/eventmanager/templates/OnlineStudyView.html',
    'modules/eventmanager/addonlinestudy/views/AddOnlineStudyWindow',
    'modules/eventmanager/onlinestudydetail/views/DetailOnlineStudyWindow',
    'Portal'
], function (OnlineStudyViewTpl,AddOnlineStudyWindow,DetailOnlineStudyWindow,Portal) {
	var GRID_DOM = "#online-study-content-div"
	var pageNum;
	return club.View.extend({
        template: club.compile(OnlineStudyViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .online-study-main-btngroup .btn": "tbarHandler",
        	"change .online-study-main-btngroup select[name=online-study-studyType-conditions]":"selectChange"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
            this.init();
        },selectChange:function(event){
            var value = $(event.currentTarget).val();
            this.initstudyChildType(value);
        } ,
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
                case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
                case 'edit': selectRecord = this.selectRecord();
                case 'add': this.add(action,selectRecord);break;
                case 'del': this.doDelete(action,this.selectRecords());break;
                case 'details':this.details(action,$(event.currentTarget).attr("id"));break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        search:function(){
    	    if(pageNum!=null){
    		   this.pageData(pageNum);
    	    }else{
    		   this.pageData(1);
    	    }
        },
        refresh:function(){
        	 console.log("刷新Grid列表");
             $('input[name=online-study-conditions]').val('');
             $('select[name=online-study-studyType-conditions]').val(0);
             $('select[name=online-study-studyChildType-conditions]').val(0);
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
            AddOnlineStudyWindow.openAddWin({
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
          	 var t= club.confirm('您确定要删除所选学习信息吗？');
	       	 t.result.then(function resolve(){
	       		 $.post(Portal.webRoot+'/event/onlineStudy/delete.do',{
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
        //查看详情
        details:function(action,id){
        	var rowValue = $(GRID_DOM).grid('getRowData',id);
        	DetailOnlineStudyWindow.openAddWin({
                callback:this
            },action,rowValue);
        	
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var conditions = $('input[name=online-study-conditions]').val();
            var studyType = $('select[name=online-study-studyType-conditions]').val();
            var studyChildType = $('select[name=online-study-studyChildType-conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            var json={conditionStr:JSON.stringify({'conditions':'%'+conditions+'%','studyType':studyType,'studyChildType':studyChildType}),start:(page-1)*rowNum,limit:page*rowNum};
            console.log(json)
            $.post(Portal.webRoot+'/event/onlineStudy/onlineStudyPage.do',{
                conditionStr:JSON.stringify({
                    'conditions':'%'+conditions+'%','studyType':studyType,'studyChildType':studyChildType,'createTimeOrder':'desc'}),
                start:(page-1)*rowNum,limit:rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                pageNum=result.page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },initstudyChildType:function(parentId){
        	 $.post(Portal.webRoot+'/event/onlineStudyType/getListByParentId.do',{parentId:parentId},function(result){
	            var studyChildTypeStr="<option value='0'>全部</option>";
	            for(var i=0;i<result.length;i++){
	 	            studyChildTypeStr+='<option value="'+result[i].id+'">'+result[i].name+'</option>';
	            }
	            $('.online-study-main-view select[name=online-study-studyChildType-conditions]').html(studyChildTypeStr);
	          });
        },init:function(){
             var initstudyChildType=this.initstudyChildType;
        	 $.post(Portal.webRoot+'/event/onlineStudyType/getParentList.do',{},function(result){
             	var studyTypeStr="<option value='0'>全部</option>";
             	for(var i=0;i<result.length;i++){
             		studyTypeStr+='<option value="'+result[i].id+'">'+result[i].name+'</option>';
             	}
             	$('.online-study-main-view select[name=online-study-studyType-conditions]').html(studyTypeStr);
             	initstudyChildType(result[0].id);
             });
        },_initListView:function(){
        	
           	$("input[name=online-study-conditions]").css({width:"33%"});  
        	$("select[name=online-study-studyType-conditions]").css({width:"33%"});  
        	$("select[name=online-study-studyChildType-conditions]").css({width:"33%"});  
        	
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
                    name: 'title',
                    width:90,
                    label: '学习标题',
                    align: "left"
                },  {
                    name: 'studyType',
                    hidden:true
                },  {
                    name: 'studyTypeName',
                    label: '学习分类',
                    width: 80,
                    align: "left"
                }, {
                    name: 'studyChildType',
                    hidden:true
                }, {
                    name: 'studyChildTypeName',
                    label: '学习子分类',
                    width: 80,
                    align: "left"
                }, {
                    name: 'type',
                    width:40,
                    align: "center",
                    label: '类型',
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '文章'};
                        if (cellval=='2') {return '视频'};
                        return cellval;
                    }
                },{
                    name: 'videoUrl',
                    hidden:true
                }, {
                    name: 'readNum',
                    label: '阅读数',
                    width: 40,
                    align: "center"
                }, {
                    name: 'author',
                    label: '作者',
                    width: 160,
                    align: "left"
                }, {
                    name: 'createTime',
                    label: '发布时间',
                    width: 140,
                    align: "left"
                },{
                    name: 'covePic',
                    hidden:true
                },{
                    name: 'covePicUrl',
                    hidden:true
                },{
                    width:60,
                    label: '操作',
                    align: "center",
                    formatter:function(cellval, opts, rwdat, _act){
                        return '<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="details">查看详情</a> ';
                    },
                    title: false //内容没有提示
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