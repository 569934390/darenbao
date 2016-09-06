$(function() {
    //初始化加载元素
    customer.order.init();
    //customer.order.getPostage();
    //
    var needInvoice, invoiceName, invoiceContent;
    needInvoice = false;
    invoiceName = "";
    invoiceContent = "";
    $("input[name=needInvoice]").val(needInvoice);
    $("input[name=invoiceName]").val(invoiceName);
    $("input[name=invoiceContent]").val(invoiceContent);
    $('#pc-judge-radio').on('click', function(){
        if($(this).is(':checked')){
            $('.packet-mask').show();
            $('#unit-person').on('click', function(){
                if($(this).is(':checked')) {
                    $('.unit-input-text').hide();
                }
            });
            $('#unit-company').on('click', function(){
                if($(this).is(':checked')) {
                    $('.unit-input-text').show();
                }
            });
            $('#no-packet').on('click', function(){
                needInvoice = false;
                invoiceName = "";
                invoiceContent = "";
                $("input[name=needInvoice]").val(needInvoice);
                $("input[name=invoiceName]").val(invoiceName);
                $("input[name=invoiceContent]").val(invoiceContent);
                $('.packet-mask').hide();
                $('#pc-judge-radio').removeAttr('checked');
            });
            $('.packet-submit').on('click', function(){
                if($('#unit-company').is(':checked')){
                    if($('.unit-input-text').val() == '') {
                        Util.msg.show('msgId', '请输入正确的单位发票抬头');
                    } else {
                        needInvoice = true;
                        invoiceName = "公司";
                        invoiceContent = $('.unit-input-text').val();
                        $('.packet-mask').hide();
                    }
                } else {
                    needInvoice = true;
                    invoiceName = "个人";
                    invoiceContent = "";
                    $('.packet-mask').hide();
                }
                $("input[name=needInvoice]").val(needInvoice);
                $("input[name=invoiceName]").val(invoiceName);
                $("input[name=invoiceContent]").val(invoiceContent);
            });
        }
    });
});
var customer = customer || {};
customer.order={
    //初始化加载元素
    init:function(){
        this.loadPage();

        var orderPage = localStorage.getItem('orderPage');
        console.info(orderPage)
        if(orderPage){
            $('#back').attr('href',orderPage);
        }
    },
    loadPage:function(){
        var shopCartInfo = Util.common.getParameter("shopCartInfo");
        var shopCartInfoJson = JSON.parse(shopCartInfo);
        var buyerCarriage = {"userId": localStorage.getItem('buyerCarriage')};
        //shopCartInfoJson.unshift(buyerCarriage);  //往shopCartInfoJson中添加运费
        console.log(shopCartInfo);
        console.log(shopCartInfoJson);
        $("#goodDetails").html($("#goodDetails_t").tmpl(shopCartInfoJson));
        $("#goodDetail_total").html($("#goodDetail_total_t").tmpl(shopCartInfoJson));
        $("#order-cast-info-id").html($("#order-cast-info_t").tmpl(shopCartInfoJson));
        //自己购买
        if(Util.common.getParameter("buyType") == 0) {
            //get addr
            var url_addr = Util.common.baseUrl +"/weixin/addr/query.do";
            //var param_addr = {"userId": "1"};i
            var param_addr = {"userId": localStorage.getItem('userid')};
            console.log(param_addr);
            Util.common.executeAjaxCallback(url_addr, param_addr, function (data) {
                var get_address = "";
                for(var i=0;i< data.length;i++) {
                    if(data[i].status == 1) {
                        $("input[name=province]").val(data[i].provinceName);
                        $("input[name=city]").val(data[i].cityName);
                        $("input[name=town]").val(data[i].areaName);
                        if(data[i].areaCode==""){
                            $("input[name=townCode]").val(data[i].cityCode);
                        }else{
                            $("input[name=townCode]").val(data[i].areaCode);
                        }
                        console.log($("input[name=townCode]").val());
                        $("input[name=address]").val(data[i].detailAddr);
                        $("input[name=receiver]").val(data[i].receiptName);
                        $("input[name=receiverPhone]").val(data[i].mobile);

                        get_address = data[i].provinceName + data[i].cityName + data[i].areaName + data[i].detailAddr;
                        var temp = {
                            "provinceName": data[i].provinceName,
                            "cityName": data[i].cityName,
                            "areaName": data[i].areaName,
                            "townCode": data[i].areaCode,
                            "detailAddr": data[i].detailAddr,
                            "receiptName": data[i].receiptName,
                            "mobile": data[i].mobile
                        };
                        localStorage.setItem("one_location", JSON.stringify(temp));
                    }

                }
                if(get_address != "") {
                    $('#pay-address-edit').html(get_address);
                }
                customer.order.getPostage();
        });
            $("#send-info-div").hide();
            //$("#packet-needed").hide();
            $("#pay-address-edit").on('click',function(){

                localStorage.setItem('addressPage', 'html/customer/order/order-detail-submit.html');
                document.location.href = 'html/customer/address/index.html?type=html/customer/order/order-detail-submit.html';
            });
        }
        //我要送人
        if(Util.common.getParameter("buyType") == 1) {
            var one_location = JSON.parse(localStorage.getItem('one_location'));
            if(one_location == null) {
                $("#pay-address-edit").html('编辑收货地址').css({
                    'position': 'relative',
                    'top': '6px'
                }).on('click',function(){
                    document.location.href = 'html/customer/address/edit.html?type=add&&use=now';
                });
            } else {
                if(one_location.areaCode==""){
                    one_location.areaCode=one_location.cityCode;
                }
                $("input[name=townCode]").val(one_location.areaCode);
                customer.order.getPostage();
                $("#pay-address-edit").html(one_location.provinceName + one_location.cityName + one_location.areaName + one_location.detailAddr).css({
                    'position': 'relative',
                    'top': '5px'
                }).on('click',function(){
                    document.location.href = 'html/customer/address/edit.html?type=add&&use=now';
                });
            }
        }



    },
    //计算邮费
    getPostage:function () {
        var url = Util.common.baseUrl + "/weixin/common/getCarriageByRegionIdAndGoodId.do";
        var regionId = $("input[name=townCode]").val();
        if(regionId == null|| regionId==""){
            regionId = 110101;
        }
        var shopCartInfo = Util.common.getParameter("shopCartInfo");
        var shopCartInfoJson = JSON.parse(shopCartInfo);
        var shopCarts = shopCartInfoJson.shopCarts;
        console.log(JSON.stringify(shopCarts));
        var indentInfo = [];
        for(var i=0; i < shopCarts.length; i++){
            var indentInfodata = {};
            var salePrice = shopCarts[i].salePrice;
            var goodCount = shopCarts[i].goodCount;
            indentInfodata.goodId = shopCarts[i].goodsId;
            indentInfodata.money = salePrice*goodCount;
            indentInfodata.regionId = regionId;
            indentInfo.push(indentInfodata);
        }
        var param2 = {"indentInfo": JSON.stringify(indentInfo)};
         Util.common.executeAjaxCallback(url, param2, function (data) {
            console.log(data+'邮费');
             $(".postage").html(data);
             var total = Number(data)+Number(shopCartInfoJson.all);
             $(".total, #pay-all").html(total.toFixed(2));
             if(Number(data)==0){
                 $(".orderbaoyou").html("包邮");
             }else {
                 $(".orderbaoyou").html(Number(data));
                 $("#join-cart-popup").popup("open").css({'visibility': 'visible'});
                 $("#join-cart-popup-popup").css({'top': '30%'});
             }
         });
    },
    loadTemplate: function (render, templateId, data) {
        $(render).html($(templateId).tmpl(data));
    },
    goDetailPro: function() {
        document.location.href = 'html/customer/details/detail.html?id=' + $(this).attr('value');
    },
    goPay: function() {
        this.submit();
    },
    submit:function(){
        var modelJson = {};
        modelJson.subbranchId = localStorage.getItem("shopId");
        modelJson.buyerId = localStorage.getItem("userid");
        //
        if(Util.common.getParameter('buyType') == 0) {
            modelJson.buyType = "z";
        } else {
            modelJson.buyType = "s";
        }
        modelJson.type = "1";
        modelJson.ticketNum = "";
        modelJson.buyerCarriage = "";
        modelJson.province = JSON.parse(localStorage.getItem('one_location')).provinceName ;
        modelJson.city = JSON.parse(localStorage.getItem('one_location')).cityName;
        modelJson.town = JSON.parse(localStorage.getItem('one_location')).areaName;
        modelJson.townCode = $("input[name=townCode]").val();
        modelJson.address = JSON.parse(localStorage.getItem('one_location')).detailAddr;
        modelJson.receiver = JSON.parse(localStorage.getItem('one_location')).receiptName;
        modelJson.receiverPhone = JSON.parse(localStorage.getItem('one_location')).mobile;
        modelJson.buyerRemark = $("input[name=buyerRemark]").val();
        modelJson.needInvoice = $("input[name=needInvoice]").val();
        modelJson.invoiceName = $("input[name=invoiceName]").val();
        modelJson.invoiceContent = $("input[name=invoiceContent]").val();
        var indentList =[];
        var goodCount = $("input[name=goodCount]").val();

        var shopCartInfo = Util.common.getParameter("shopCartInfo");
        var shopCartInfoJson = JSON.parse(shopCartInfo);
        for(var i = 0; i < shopCartInfoJson.shopCarts.length; i++){
            var shopCart = shopCartInfoJson.shopCarts[i];
            var indent = {};
            indent.number  = shopCart.goodCount;
            indent.finalAmount  = shopCart.salePrice;
            indent.tradeGoodSkuId  = shopCart.skuId;
            indentList.push(indent);
        }
        console.log(indentList);
        modelJson.indentList = indentList;


        var url = Util.common.baseUrl + "/weixin/indent/add.do";
        var param = {"modelJson":JSON.stringify(modelJson)};
        if($('#pay-address-edit').html() == '编辑收货地址' || $('#pay-address-edit').html() == '收货地址:') {
            Util.msg.show("msgId", "请填写收货地址");
        } else {
            Util.common.executeAjaxCallback(url, param, function (data) {
                if (data.code==1){
                    var all = parseFloat($('#pay-all').html());
                    console.log($('#pay-all').html());
                    document.location.href = "html/customer/order/order-pay.html" +
                      "?goodCount="+goodCount+"&all=" + all+'&orderId='+data.msg + "&goodId=" + Util.common.getParameter('goodId')+"&shopCartInfo=" + shopCartInfo;
                }
                else{
                    if(data.msg){
                        Util.msg.show("msgId", data.msg);
                    }
                    else{
                        Util.msg.show("msgId", "库存不足");
                    }
                }
            });
        }
    }
}
