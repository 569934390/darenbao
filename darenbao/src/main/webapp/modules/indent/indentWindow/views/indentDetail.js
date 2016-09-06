define([
	'text!modules/indent/indentWindow/templates/indentDetail.html',
    'Portal'
	], function(indentDetailTpl,Portal) {
	var options = {
            height: $(window).height()*0.9,
            modal: false,
            draggable: false,
            content: indentDetailTpl,
            autoResizable: true
        };
    
	return {
		openDetail:function(id){
			var me = this;
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            
            var setValue = function(value){
            	$(".indentDetail label[name=name]").html(value.name);
                $(".indentDetail label[name=buyerName]").html(value.buyerName);
                $(".indentDetail label[name=createTime]").html(value.createTime);
                $(".indentDetail label[name=statusName]").html(value.statusName);
                $(".indentDetail label[name=typeName]").html(value.typeName);
                $(".indentDetail label[name=payTime]").html(value.payTime);
                $(".indentDetail label[name=payTypeName]").html(value.payTypeName);
                $(".indentDetail label[name=payAccount]").html(value.payAccount);
                $(".indentDetail label[name=buyerRemark]").html(value.buyerRemark);
                $(".indentDetail label[name=remark]").html(value.remark);
                $(".indentDetail label[name=expressNumber]").html(value.expressNumber);
                $(".indentDetail label[name=shipper]").html(value.shipper);
                $(".indentDetail label[name=receiver]").html(value.receiver);
                $(".indentDetail label[name=receiverPhone]").html(value.receiverPhone);
                $(".indentDetail label[name=weight]").html(value.weight);
                $(".indentDetail label[name=expressCompany]").html(value.expressCompany);
                $(".indentDetail label[name=shipTime]").html(value.shipTime);
                $(".indentDetail label[name=finishTime]").html(value.finishTime);
                $(".indentDetail label[name=buyerCarriage]").html(value.buyerCarriage);
                $(".indentDetail label[name=fullAddress]").html(value.fullAddress);
                $(".indentDetail label[name=needInvoiceText]").html(value.needInvoiceText);
                $(".indentDetail label[name=invoiceName]").html(value.invoiceName);
                $(".indentDetail label[name=invoiceContent]").html(value.invoiceContent);
                if(value.status == 3 || value.status == 8){
                	$(".indentDetail .refund-div").show();
                    $(".indentDetail label[name=refundRemark]").html(value.refundRemark);
                }if(value.status == 6 || value.status == 7 || value.status == 9){
                	$(".indentDetail .return-div").show();
                    $(".indentDetail label[name=returnName]").html(value.returnName);
                    $(".indentDetail label[name=returnRemark]").html(value.returnRemark);
                }
                var html = '';
                if(value.indentList != undefined && value.indentList instanceof Array && value.indentList.length != 0){
                	for(var i = 0; i < value.indentList.length; i++){
                		var indentListValue = value.indentList[i];
                		console.log(indentListValue);
                		var goodHtml = '<div class="col-md-12"><div class="col-md-6">'+
                        '<div class="col-md-12 form-group">'+
                        '<label class="col-md-4 control-label">商品编号：</label>'+
                        '<label class="col-md-8">'+(indentListValue.tradeGoodSku != undefined && indentListValue.tradeGoodSku.goodId != undefined?indentListValue.tradeGoodSku.goodId:'')+'</label>'+
                        '</div>'+
                        '<div class="col-md-12 form-group">'+
                        '<label class="col-md-4 control-label">规格：</label>'+
                        '<label class="col-md-4">'+indentListValue.tradeGoodType+'</label>'+
                        '</div>'+
                        '<div class="col-md-12 form-group">'+
                        '<label class="col-md-4 control-label">缩略图：</label>'+
                        '<div class="col-md-8"><img height="60px" width="60px" src="'+indentListValue.tradeGoodImgUrl+'"/></div>'+
                        '</div>'+                    
                        '</div>'+
                        '<div class="col-md-6">'+
                        '<div class="col-md-12 form-group">'+
                        '<label class="col-md-4 control-label">商品名称：</label>'+
                        '<label class="col-md-8">'+indentListValue.tradeGoodName+'</label>'+
                        '</div>'+
                        '<div class="col-md-12 form-group">'+
                        '<label class="col-md-4 control-label">数量：</label>'+
                        '<label class="col-md-8">'+indentListValue.number+'</label>'+
                        '</div>'+	
                        '<div class="col-md-12 form-group">'+
                        '<label class="col-md-4 control-label">销售价：</label>'+
                        '<label class="col-md-8">'+indentListValue.finalAmount+'</label>'+
                        '</div></div><div class="col-md-12"><hr/></div></div>';
                		html += goodHtml;
                	}
                }
                $(".indentDetail #good-sku-list").html(html);
            }
            
            $.post(Portal.webRoot+'/deal/indent/detail.do',{"id":id},function(result){
	               if(result){
	            	   setValue(result);
	               }
            });
            $('.indentDetail button').click(function(){
                popup.close();
            });
		},
	};
});