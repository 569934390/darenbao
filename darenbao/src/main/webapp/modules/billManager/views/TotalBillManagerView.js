define([
    'text!modules/billManager/templates/TotalBillManagerView.html',
    'Portal'
], function (TotalBillManagerViewTpl,Portal) {
	return club.View.extend({
        template: club.compile(TotalBillManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .TotalBill-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
        	this.pageData(1);
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
            	case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
            }
        },
        search:function(){
       	 this.pageData(1);
        },
        refresh:function(){
        	$('input[name=orderIdByNotSettle]').val('');
        	this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            $.post(Portal.webRoot+'/settlementBillController/selectTotalBill.do',{
            	},function(result){
                $('#paymentAmountCount').text(result.paymentAmountCount);
                $('#alreadypayAmountCount').text(result.alreadypayAmountCount);
                $('#payAmountCount').text(result.payAmountCount);
                $('#settlementAmountCount').text(result.settlementAmountCount);
            });
            return false;
        }
 	});
});