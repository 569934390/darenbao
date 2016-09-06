define([
    'text!modules/indent/templates/invoiceList.html',
    'Portal',
], function (invoiceListTpl,Portal) {
    var GRID_DOM = "#invoice-list-content-div";
    return club.View.extend({
        template: club.compile(invoiceListTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .invoice-list .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
        	$('.invoice-list input[field=datetime]').datetimepicker({
                format: 'yyyy/mm/dd hh:ii:ss'
            });
            this._initListView();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            switch(action){
            	case 'refresh': this.refresh();break;
                case 'search' : this.search();break;
                case 'exportExcel': this.exportExcel();break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        exportExcel:function(){
        	var param = {};
        	if($('.invoice-list input[name=iindentName]').val()){
            	param["indentName"]="%"+$('.invoice-list input[name=iindentName]').val()+"%";
            }if($('.invoice-list input[name=ireceiver]').val()){
            	param["receiver"]="%"+$('.invoice-list input[name=ireceiver]').val()+"%";
            }if($('.invoice-list input[name=istartTime]').val()){
            	param["startTime"]=$('.invoice-list input[name=istartTime]').val();
            }if($('.invoice-list input[name=iendTime]').val()){
            	param["endTime"]=$('.invoice-list input[name=iendTime]').val();
            }
        	var url=Portal.webRoot+'/deal/invoice/exportExcel.do?condition='+JSON.stringify(param);
        	window.open(encodeURI(url));
        },
        search:function(){
            this.pageData(1);
        },
        refresh:function(){
        	$('.invoice-list input[name=iindentName]').val('')
        	$('.invoice-list input[name=ireceiver]').val('')
        	$('.invoice-list input[name=istartTime]').val('');
        	$('.invoice-list input[name=iendTime]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");            
            var param = {};
            if($('.invoice-list input[name=iindentName]').val()){
            	param["indentName"]="%"+$('.invoice-list input[name=iindentName]').val()+"%";
            }if($('.invoice-list input[name=ireceiver]').val()){
            	param["receiver"]="%"+$('.invoice-list input[name=ireceiver]').val()+"%";
            }if($('.invoice-list input[name=istartTime]').val()){
            	param["startTime"]=$('.invoice-list input[name=istartTime]').val();
            }if($('.invoice-list input[name=iendTime]').val()){
            	param["endTime"]=$('.invoice-list input[name=iendTime]').val();
            }
            $.post(Portal.webRoot+'/deal/invoice/list.do',{
                conditionStr:JSON.stringify(param),start:(page-1)*rowNum,limit:rowNum},function(result){
                	result = Portal.convertPage(result);
	                result.page = page;
	                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
            var me = this;
            //参数配置
            var opt = {
                height:$(window).height()-150,
                width:this.$el.width(),
                colModel: [
						{name: 'indentName',label: '订单号',order: 0,width: 120,align: "center",headertitle : '订单号'}, 
						{name: 'receiver',label: '收货人',order: 1,width: 80,align: "center",headertitle : '收货人'},						
						{name: 'receiverPhone',label: '收货人手机号',order: 2,width: 110,align: "center",headertitle : '收货人手机号'},
						{name: 'invoiceText',label: '抬头',order: 3,width: 150,align: "center",headertitle : '抬头'},
						{name: 'number',label: '商品数量',order:4,width: 60,align: "center",headertitle : '总数'},
						{name: 'statusName',label: '订单状态',order: 5,width: 100,align: "center",headertitle : '订单状态'},
						{name: 'createTime',label: '创建时间',order: 5,width: 100,align: "center",headertitle : '创建时间'}
                    ]
            	,pager: true
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
