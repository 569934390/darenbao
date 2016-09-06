define([
    'text!modules/billManager/templates/NotSettleBillManagerView.html',
    'Portal'
], function (NotSettleBillManagerViewTpl,Portal) {
	var GRID_DOM = "#NotSettleBill-info-content-div";
	return club.View.extend({
        template: club.compile(NotSettleBillManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .NotSettleBill-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
            $('.NotSettleBill-info-main-btngroup input[field=datetime]').datetimepicker({
                format: 'yyyy-mm-dd'
            });
            /*var startTime = new Date(),endTime = new Date();
            startTime.setDate(startTime.getDate()-90);
            endTime.setDate(endTime.getDate()+1);
            $('.NotSettleBill-info-main-btngroup input[name=startTimeByNotSettle]').datetimepicker('value',
            		club.dateutil.format(startTime,'yyyy/mm/dd hh:ii:ss'));
            $('.NotSettleBill-info-main-btngroup input[name=endTimeByNotSettle]').datetimepicker('value',
            		club.dateutil.format(endTime,'yyyy/mm/dd hh:ii:ss'));*/
            $.post(Portal.webRoot+'/settlementBillController/getSubbranch.do',{},function(result){
            	for(var i=0; i<result.length; i++){
            		var option = "";
            		option = '<option value="'+result[i].id+'">'+result[i].name+'</option>';
            		$('select[name=shopByNotSettle]').append(option);
            	}
            });
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
            	case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
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
        	$('input[name=orderIdByNotSettle]').val('');
        	this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
        	var orderId = $('input[name=orderIdByNotSettle]').val();
        	var shopId = $('select[name=shopByNotSettle]').val();
        	var startTime = $('input[name=startTimeByNotSettle]').val();
        	var endTime = $('input[name=endTimeByNotSettle]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/settlementBillController/selectSettlementBill.do',{
            	orderId:orderId,
            	shopId:shopId,
            	startTime:startTime,
            	endTime:endTime,
            	status:0,
                start:(page-1)*rowNum,limit:page*rowNum},function(result){
                var pageResult = result.page;
                pageResult = Portal.convertPage(pageResult);
                pageResult.page = page;
                $(GRID_DOM).grid("reloadData", pageResult);
                $('#payTotalAmountByNotSettle').text(result.paymentAmountCount);
                $('#settlementTotalAmountByNotSettle').text(result.supplyPriceCount);
            });
            return false;
        },
        _initListView:function(){
        	
        	//参数配置
            var opt = {
                height:$(window).height()-200,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'indentName',
                    label: '订单号',
                    width: 120,
                    align: "left"
                }, {
                    name: 'shopName',
                    label: '店铺名称',
                    width: 150,
                    align: "left"
                }, {
                    name: 'finishTime',
                    label: '订单完成时间',
                    width: 90,
                    align: "left"
                }, {
                    name: 'cycle',
                    label: '结算周期',
                    width:70,
                    align: "left"
                }, {
                    name: 'settlementTime',
                    label: '可结算时间',
                    width:90,
                    align: "left"
                }, {
                    name: 'paymentAmount',
                    label: '支付金额',
                    width:90,
                    align: "left"
                }, {
                    name: 'supplyPrice',
                    label: '结算金额',
                    width:90,
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