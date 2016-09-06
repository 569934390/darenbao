define([
    'text!modules/financemanager/templates/FinanceAbnormalView.html',
    'Portal'
], function (financeAbnormalViewViewTpl,Portal) {
    var GRID_DOM = "#finance-abnormal-content-div";
    return club.View.extend({
        template: club.compile(financeAbnormalViewViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .finance-abnormal-main-btngroup .btn": "tbarHandler"
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
                case 'synOrder': this.synOrder();break;
                case 'synAccept': this.synAccept();break;
            }
        },
        synOrder:function(){
            this.synData('异常销售单',Portal.webRoot+'/deal/u8/batch/order.do');
        },
        synAccept:function(){
            this.synData('异常收款单',Portal.webRoot+'/deal/u8/batch/accept.do');
        },
        synData:function(name,url){
        	var me = this;
            club.confirm("是否同步所有"+name+"！").result.then(function(){
	            $.post(url,{},function(result){
	                club.toast('info', '同步'+name+'成功！');
	                me.refresh();
	            });
	    	},function(){
	    		return;
	    	});
        },
        search :function(){
        	this.pageData(1);
        },
        refresh:function(){
            $(".finance-abnormal-main-btngroup select[name=iorderType]").val('');
            $(".finance-abnormal-main-btngroup select[name=istatus]").val('');
            this.pageData(1);
        },
        pageData :function(page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            var condit = {};
            if($(".finance-abnormal-main-btngroup select[name=iorderType]").val()){
            	condit["orderType"] = $(".finance-abnormal-main-btngroup select[name=iorderType]").val();
            }if($(".finance-abnormal-main-btngroup select[name=istatus]").val()){
            	condit["status"] = $(".finance-abnormal-main-btngroup select[name=istatus]").val();
            }
            $.post(Portal.webRoot+'/deal/u8/list.do',{start:(page-1)*rowNum,limit:page*rowNum,conditionStr:JSON.stringify(condit)},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	var me = this;
            var opt = {
                height:$(window).height()-145,
                width:this.$el.width(),
                colModel:[
   						{name: 'id',sorttype: "long",hidden:true},
   						{name: 'orderId',label: '订单id',order: 1,width: 100,align: "center",headertitle : '订单id'}, 
   						{name: 'orderName',label: '订单号',order: 2,width: 100,align: "center",headertitle : '订单号'}, 
   						{name: 'u8orderid',label: 'U8销售单号',order: 3,width: 100,align: "center",headertitle : 'U8销售单号'},
   						{name: 'u8accountsid',label: 'U8收款单号',order: 4,width: 100,align: "center",headertitle : 'U8收款单号'},						
   						{name: 'statusText',label: '状态',order: 5,width: 80,align: "center",headertitle : '状态'}
                       ]
            	,pager: true
                ,rowNum: 20
                ,rowList: [10,20,50,100,200]
                ,datatype: "json"
                ,recordtext:"{0} 到 {1} 总共: {2} "
                ,pgtext : " {0} / {1}"
                ,rowtext:"{0}"
                ,gotext:'跳转至第{0}页'
                ,pageData:this.pageData
            };
            this.pageData(1);
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
