define([
    'text!modules/billManager/templates/AlreadySettlePaymentManagerView.html',
    'Portal'
], function (AlreadySettlePaymentManagerViewTpl,Portal) {
	var GRID_DOM = "#AlreadySettlePayment-info-content-div";
	return club.View.extend({
        template: club.compile(AlreadySettlePaymentManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .AlreadySettlePayment-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
            $('.AlreadySettlePayment-info-main-btngroup input[field=datetime]').datetimepicker({
                format: 'yyyy-mm-dd'
            });
            /*var startTime = new Date(),endTime = new Date();
            startTime.setDate(startTime.getDate()-90);
            endTime.setDate(endTime.getDate()+1);
            $('.AlreadySettlePayment-info-main-btngroup input[name=startTimeByPay]').datetimepicker('value',club.dateutil.format(startTime,'yyyy/mm/dd hh:ii:ss'));
            $('.AlreadySettlePayment-info-main-btngroup input[name=endTimeByPay]').datetimepicker('value',club.dateutil.format(endTime,'yyyy/mm/dd hh:ii:ss'));*/
            $.post(Portal.webRoot+'/settlementBillController/getSubbranch.do',{},function(result){
            	for(var i=0; i<result.length; i++){
            		var option = "";
            		option = '<option value="'+result[i].id+'">'+result[i].name+'</option>';
            		$('select[name=shopByPay]').append(option);
            	}
            });
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
            	case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
                case 'pay': this.doSettlement(action,this.selectRecords(),'您确定要付款所选订单吗？',3);break;
                case 'cancel': this.doSettlement(action,this.selectRecords(),'您确定要取消结算所选订单吗？',1);break;
                case 'export': this.exportExcel(action,this.selectRecords());break;
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
             $('input[name=orderIdByPay]').val('');
             this.pageData(1);
        },
        //修改账单状态
        doSettlement:function(action,records,msg,status){
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
             var t = club.confirm(msg);
             t.result.then(function resolve(){
            	 $.post(Portal.webRoot+'/settlementBillController/updateBillStatus.do',{
            		 ids:ids.join(','),status:status},function(result){
            			 if (result.success) {
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
        exportExcel:function(action,records){
        	var orderId = $('input[name=orderIdByPay]').val();
        	var shopId = $('select[name=shopByPay]').val();
        	var startTime = $('input[name=startTimeByPay]').val();
        	var endTime = $('input[name=endTimeByPay]').val();
            var t = club.confirm('您确定要导出结算单吗？');
            t.result.then(function resolve(){
            	$.post(Portal.webRoot+'/settlementBillController/exportExcel.do',{
                	orderId:orderId,
                	shopId:shopId,
                	startTime:startTime,
                	endTime:endTime,
                	status:2},function(result){
           			 if (result.success) {
           				 location.href = Portal.webRoot+result.msg;
           			 } else{
           				 club.toast('error', result.msg);
           			 }
           		 });
            }, function reject(){
	    		 return;
	    	 });
        },
        pageData :function (page, rowNum, sortname, sortorder) {
        	var orderId = $('input[name=orderIdByPay]').val();
        	var shopId = $('select[name=shopByPay]').val();
        	var startTime = $('input[name=startTimeByPay]').val();
        	var endTime = $('input[name=endTimeByPay]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/settlementBillController/selectSettlementBill.do',{
            	orderId:orderId,
            	shopId:shopId,
            	startTime:startTime,
            	endTime:endTime,
            	status:2,
                start:(page-1)*rowNum,limit:page*rowNum},function(result){
                var pageResult = result.page;
                pageResult = Portal.convertPage(pageResult);
                pageResult.page = page;
                $(GRID_DOM).grid("reloadData", pageResult);
                $('#payTotalAmountByPay').text(result.paymentAmountCount);
                $('#settlementTotalAmountByPay').text(result.supplyPriceCount);
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