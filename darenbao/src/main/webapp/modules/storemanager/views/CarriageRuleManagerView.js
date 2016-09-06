define([
    'text!modules/storemanager/templates/CarriageRuleManagerView.html',
    'modules/storemanager/addCarriageRule/views/AddCarriageRuleWindow',
    'modules/storemanager/addCarriageRule/views/CarriageRuleDetailWindow',
    'Portal'
], function (CarriageRuleManagerViewTpl,AddCarriageRuleWindow,CarriageRuleDetailWindow,Portal) {
	var GRID_DOM = "#carriageRule-info-content-div";
	return club.View.extend({
        template: club.compile(CarriageRuleManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            
        },
        events:{
        	"click .carriageRule-info-main-btngroup .btn": "tbarHandler"
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
                case 'edit': 
                case 'add': this.add(action,$(event.currentTarget).attr("id"));break;
                case 'del': this.doDelete(action,$(event.currentTarget).attr("id"));break;
                case 'details':this.detail(action,$(event.currentTarget).attr("id"));break;
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
             this.pageData(1);
        },
        //新增和修改
        add:function(action,record){
            if (action=='edit'&& record == '') {
                return club.toast('warn', '请选择记录！');
            };
            AddCarriageRuleWindow.openAddWin({
                callback:this
            },action,record);
        },
        //删除
        doDelete:function(action,records){
        	if (action=='edit'&& record == '') {
                return club.toast('warn', '请选择记录！');
            };
            var me = this
            var t = club.confirm('您确定要删除所选包邮规则吗？');
            t.result.then(function resolve(){
            	$.post(Portal.webRoot+'/carriageRuleController/deleteCarriageRule.do',{
            		carriageRuleId:records},function(result){
            			if (result.code) {
            				club.toast('info', '操作成功！');
            			} else{
            				club.toast('error', result.msg);
            			}
            			me.refresh();
            		});
             }, function reject(){
	    		 return;
	    	 });
        },
        //查看详情
        detail:function(action,record){
            if (record == '') {
                return club.toast('warn', '请选择记录！');
            };
            CarriageRuleDetailWindow.openAddWin({
                callback:this
            },action,record);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/carriageRuleController/selectCarriageRule.do',{
                start:(page-1)*rowNum,limit:page*rowNum},function(result){
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
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'templateName',
                    label: '模板名称',
                    width: 150,
                    align: "center"
                }, {
                    name: 'useGoodsNum',
                    label: '使用商品个数',
                    width: 90,
                    align: "center"
                }, {
                    name: 'updateTime',
                    label: '更新时间',
                    width: 90,
                    align: "center"
                }, {
                	width: 90,
                    label: '操作',
                    align: "center",
                    formatter:function(cellval, opts, rwdat, _act){
                    	var value='<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="details">查看详情</a>&nbsp;' +
                    	'<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="edit">编辑</a>&nbsp;' +
                    	'<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="del">删除</a>&nbsp;';
                     return value;
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
//                ,multiselect:true
                ,pageData:this.pageData
            };
            this.pageData(1);
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
 	});
});