define(
		[ 'text!modules/autoRepeat/templates/defaultRepeatManager.html',
    'modules/autoRepeat/views/editDefaultRepeat',
    'Portal'
], function (defaultRepeatManager,editDefaultRepeat,Portal) {
	var GRID_DOM = "#defaultRepeat-content-div";
	var Records = {};
	return club.View.extend({
        template: club.compile(defaultRepeatManager),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .defaultRepeat-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            console.info('12344');
            switch(action){
                case 'refresh': this.refresh();break;
                case 'edit': this.edit(action,this.selectRecord());break;
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
            editDefaultRepeat.openAddwin({
                callback:this
            },action,record,false);
        },
      
        pageData:function (page, rowNum, sortname, sortorder) {    	
        	
            var conditions = $('input[name=conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
           $.post(Portal.webRoot+'/autoRepeat/defaultRepeatPage.do',{
                conditionStr:JSON.stringify({}),
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
                    name: 'content',
                    label: '内容',
                    width:100,
                    align: "left"
                },
 
                   {name: 'updateTime',
                    width: 100,
                    align: "center",
                    label: '更新时间'
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
        },
 	});
});