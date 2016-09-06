define([
    'text!modules/storemanager/templates/HomePageImgManagerView.html',
    'modules/storemanager/addHomePageImg/views/AddHomePageImgWindow',
    'Portal'   
], function (HomePageImgViewTpl,AddHomePageImgWindow,Portal) {
	 var GRID_DOM = "#homePageImg-manager-content-div";
	
    return club.View.extend({
        template: club.compile(HomePageImgViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .homePageImg-manager-main-btngroup .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
                case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
                case 'edit': selectRecord = this.selectRecord();
                case 'add': this.add(action,selectRecord);break;
                case 'del': this.delet(this.selectRecords());break;
                case 'active':
                case 'disabled':this.doAction(action,this.selectRecords());break;
            }
        },
        selectRecord:function(){
        	
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        add:function(action,record){
        
            AddHomePageImgWindow.openAddWin({
                callback:this
            },action,record);
        },
        convertState:function(action){
            if (action=='disabled') {return '1'}
            else if (action=='active') {return '0'}
        },
        delet:function(records){
            console.log(records);
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
            $.post(Portal.webRoot+'/HomePageImg/deletHomePageImg.do',{
            	imgIds:ids.join(',')},function(result){
                club.toast('info', '操作成功！');
                me.refresh();
            });
        },
        refresh:function(){
            console.log("刷新Grid列表");
            this.pageData(1);
        },
        
		pageData : function(page, rowNum, sortname, sortorder) {
							rowNum = rowNum
									|| $(GRID_DOM).grid("getGridParam",
											"rowNum");
							$.post(Portal.webRoot+ '/HomePageImg/getHomePageImg.do',
							{
								start : (page - 1) * rowNum,
								limit : page * rowNum,
								conditionStr : JSON.stringify({
									'groupid' : $("#groupid").val()
								})
							}, function(result) {
								result = Portal.convertPage(result);
								result.page = page;
								$(GRID_DOM).grid("reloadData", result);
							});
							return false;
		},
		search:function(){
	            console.log("刷新Grid列表");
	            this.pageDataSearch(1);
	        },
	   
        _initListView:function(){
        	 var opt = {
                     height:$(window).height()-125,
                     width:this.$el.width(),
                     rownumbers:true,
                     colModel: [{
                         name: 'id',
                         sorttype: "int",
                         hidden:true,
                         key:true
                     }, {
                         name: 'groupid',
                         sorttype: "int",
                         hidden:true
                     }, {
                         name: 'createBy',
                         sorttype: "int",
                         hidden:true
                     },{
                    	 name:'picUrl',
                    	 label:'图片',
                    	 width:20,
                    	 formatter:function(val){
                             var imagUrl =  "./image/user4-128x128.jpg";
                             if (val) {
                                 imagUrl = val;
                             };
                             return '<img src = "'+imagUrl+'" class="client-face-img"/> ';
                         },
                     },{
                         name: 'createTime',
                         width:30,
                         align: "center",
                         label: '创建时间'
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
