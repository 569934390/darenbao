$(function(){
    console.log($('input[name=receiptName]'));
});

var customer = customer || {};
customer.address={
    //初始化加载元素
    init:function(){
        this.loadList();
        var addressPage = localStorage.getItem('addressPage');
        if(addressPage){
            $('#back').attr('href',addressPage);
        }
        var type = Util.common.getParameter('type');
        if(type) {
            $('#back').attr('href',type);
        }
    },
    //初始化加载地址列表
    loadList:function(){
        var url = Util.common.baseUrl +"/weixin/addr/query.do";
        var param = {"userId": localStorage.getItem('userid')};
        Util.common.executeAjaxCallback(url , param , function(data){
            customer.address.loadTemplate("#address_list" , "#address_list_t" , data);
        });
    },
    //初始化加载编辑地址
    getAddressInfo:function(){
        var addr_id = Util.common.getParameter("id");
        if(addr_id != undefined){
            var url = Util.common.baseUrl +"/weixin/addr/getAddrById.do";
            var param = {"id": addr_id};//"{value1:'a',value2:'b'}"
            Util.common.executeAjaxCallback(url , param , function(data){
                console.log(data);
               /* data.provinceName = localStorage.getItem('fName');
                data.cityName = localStorage.getItem('sName');
                data.areaName = localStorage.getItem('aName');
                localStorage.setItem('rName', data.receiptName);
                localStorage.setItem('mName', data.mobile);
                localStorage.setItem('dName', data.detailAddr);
                localStorage.setItem('mbName', data.mailbox);*/
                customer.address.loadTemplate("#addressInfo" , "#addressInfo_t" , data);
                $("input[name=provinceName]").attr("data-cs",data.provinceCode);
                $("input[name=cityName]").attr("data-cs",data.cityCode);
                $("input[name=areaName]").attr("data-cs",data.areaCode);
            });
        } else {
            var data = {};
            data.provinceName = localStorage.getItem('fName');
            data.cityName = localStorage.getItem('sName');
            data.areaName = localStorage.getItem('aName');
            data.receiptName = localStorage.getItem('rName');
            data.mobile = localStorage.getItem('mName');
            data.detailAddr = localStorage.getItem('dName');
            data.mailbox = localStorage.getItem('mbName');
            console.log(data);
            customer.address.loadTemplate("#addressInfo" , "#addressInfo_t" , data);
        }
    },
    //初始化加载新增地址
    getAddInfo:function(){
        var data = {};
        /*data.provinceName = localStorage.getItem('fName');
        data.cityName = localStorage.getItem('sName');
        data.areaName = localStorage.getItem('aName');*/
        data.provinceName = "-";
        data.cityName = "";
        data.areaName = "";
        data.receiptName = localStorage.getItem('t_rName');
        data.receiptName = localStorage.getItem('t_rName');
        data.mobile = localStorage.getItem('t_mName');
        console.log(data);
        console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
        customer.address.loadTemplate("#addressInfo" , "#addressInfo_t" , data);
    },
    //添加地址
    addAddress:function(){
        var url = Util.common.baseUrl +"/weixin/addr/save.do";
        var mailbox = $("input[name=mailbox]").val();
        var receiptName = $("input[name=receiptName]").val();
        var mobile = $("input[name=mobile]").val();
        var provinceName = $("input[name=provinceName]").val();
        var cityName = $("input[name=cityName]").val();
        var areaName = $("input[name=areaName]").val();
        var provinceCode = $("input[name=provinceName]").attr("data-cs");
        var cityCode = $("input[name=cityName]").attr("data-cs");
        var areaCode = $("input[name=areaName]").attr("data-cs");
        if(areaName == undefined || areaCode == undefined){
            areaName="";
            areaCode="";
        }
        var detailAddr = $("input[name=detailAddr]").val();
        var param ={
            //"userId":"1",
            "userId": localStorage.getItem('userid'),
            "status":"1",
            "mailbox":mailbox,
            "receiptName":receiptName,
            "mobile":mobile,
            "fixedPhone":"",
            "receiptEmail":"",
            "provinceName":provinceName,
            "provinceCode":provinceCode,
            "cityCode":cityCode,
            "cityName":cityName,
            "areaName":areaName,
            "areaCode":areaCode,
            "detailAddr":detailAddr
        };
        var conditionStr = "conditionStr=" + JSON.stringify(param);
        if(Util.common.getParameter('use') == 'now') {
            //我要送人地址
            localStorage.setItem('one_location', JSON.stringify(param));
            //
            document.location.href = 'html/customer/order/order-detail-submit.html?buyType=' + localStorage.getItem('buyType') + "&&goodId=" + localStorage.getItem('goodId') + "&&shopCartInfo=" + localStorage.getItem('shopCartInfo');

        } else {
            Util.common.executeAjaxCallback(url ,conditionStr,function(data){
                //{"msg":"操作成功","code":"000000"}
                if (data.msg == '保存成功') {
                    setTimeout(function () {
                        history.back();
                        //document.location.href = 'html/customer/address/index.html';
                    }, 300);
                } else {
                    Util.msg.show('msgId', data.msg);
                }
            });
        }
    },
    //删除地址
    delAddress:function(id){
        //测试
        var url = Util.common.baseUrl +"/weixin/addr/delete.do";
        var param = {"userId": localStorage.getItem('userid'),"ids":id};
        //var param = {"userId": "1","ids":id};
        Util.common.executeAjaxCallback(url ,param,function(data){
            //{"msg":"操作成功","code":"000000"}
            document.location.reload();
        });
    },
    //修改地址
    updatAddress:function(){
        var url = Util.common.baseUrl +"/weixin/addr/update.do";
        var id = $("input[name=id]").val();
        var mailbox = $("input[name=mailbox]").val();
        var receiptName = $("input[name=receiptName]").val();
        var mobile = $("input[name=mobile]").val();
        var provinceName = $("input[name=provinceName]").val();
        var cityName = $("input[name=cityName]").val();
        var areaName = $("input[name=areaName]").val();
        var provinceCode = $("input[name=provinceName]").attr("data-cs");
        var cityCode = $("input[name=cityName]").attr("data-cs");
        var areaCode = $("input[name=areaName]").attr("data-cs");
        var detailAddr = $("input[name=detailAddr]").val();
        var param ={
            "id":Util.common.getParameter('id'),
            //"userId":"1",
            "userId":localStorage.getItem('userid'),
            "status":"1",
            "mailbox":mailbox,
            "receiptName":receiptName,
            "mobile":mobile,
            "fiexdPhone":"",
            "receiptEmail":"",
            "provinceName":provinceName,
            "provinceCode":provinceCode,
            "cityCode":cityCode,
            "cityName":cityName,
            "areaName":areaName,
            "areaCode":areaCode,
            "detailAddr":detailAddr
        };
        var conditionStr = "conditionStr=" + JSON.stringify(param);
        Util.common.executeAjaxCallback(url ,conditionStr,function(data){
            //{"msg":"操作成功","code":"000000"}
            console.log(data.msg);
            if(data.msg == '修改成功') {
                setTimeout(function(){
                 //document.location.href = 'html/customer/address/index.html';
                history.back(); 
                }, 300);
            } else {
                Util.msg.show('msgId', data.msg);
            }
        });
    },
    //设置默认地址
    setDefaultAddress:function(obj, id){
        var url = Util.common.baseUrl + "/weixin/addr/update_status.do";
        var param = {
            "id": id,
            //"userId": "1"
            "userId": localStorage.getItem('userid')
        };
        console.log(param);
        Util.common.executeAjaxCallback(url, param, function (data) {
            //{"msg":"操作成功","code":"000000"}
            console.log(data.msg);
			history.back();
        });
    },
    //设置默认地址
    setDefaultAddressBack:function(obj, id){
        var url = Util.common.baseUrl + "/weixin/addr/update_status.do";
        var param = {
            "id": id,
            //"userId": "1"
            "userId": localStorage.getItem('userid')
        };
        console.log(param);
        $(obj).find('input').attr('checked', 'true');
        if(Util.common.getParameter('type') != "html/customer/user/user-info.html") {
            Util.common.executeAjaxCallback(url, param, function (data) {
                //{"msg":"操作成功","code":"000000"}
                console.log(data.msg);
                history.back();
            });
        }
    },
    saveOrUpate:function(){
        $('.ok-order-btn-r').off('click');
        if('edit'==Util.common.getParameter("type")){
            this.updatAddress();
        }else{
            this.addAddress();
        }
    },
    executeAjax:function(url ,param ,render ,templateId){
        Util.common.executeAjaxCallback(url , param , function(data){
            console.log(data);
            customer.address.loadTemplate(render , templateId , data);
        });
    },
    loadTemplate:function(render ,templateId ,data ){
        $(render).html($(templateId).tmpl(data));
        customer.address.initCityPick();
    },
    toAddAddress:function(){
        document.location.href='html/customer/address/edit.html?type=add';
    },
    initCityPick:function(){
        if ($.mobileCityPicker) {
            $.mobileCityPicker({
                id: 'demo_treelist',//容器id
                inputClass: 'cityPickerInput',//
                inputClick: true,
                option: {
                    defaultValue: [0, 0, 0],//默认选项
                    label: ['province', 'city', 'district'],
                    theme: 'android-holo-light',
                    mode: 'scroller',
                    inputClass: 'hidden',
                    display: 'bottom',
                    lang: 'zh',
                },
                callback: function (val, citys) {
                    var vals = val.split(' ');
                    $("input[name=provinceCode]").val(vals[0]);
                    $("input[name=cityCode]").val(vals[1]);
                    if (vals.length > 1) {
                        $("input[name=areaCode]").val(vals[2]);
                    }
                    $("#pca_value").html(citys.join('-'));
                    localStorage.setItem('fName',citys[0]);
                    localStorage.setItem('sName',citys[1]);
                    localStorage.setItem('aName',citys[2]);
                    if($("input[name=areaCode]").val()==""){
                        $("input[name=areaCode]").val("");
                        $("input[name=areaName]").val("");
                        $("input[name=areaName]").attr("data-id","");
                        $("input[name=areaName]").attr("data-cs","");
                    }
                    console.log(val);
                    console.log(citys);
                }
            });
        }
    }
};