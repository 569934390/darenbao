define([
    'text!modules/indent/templates/indentShipView.html',
    'modules/indent/indentWindow/views/indentDetail',
    'modules/indent/indentWindow/views/indentShip',
    'Portal',
], function (indentShipViewTpl,indentDetail,indentShip,Portal) {
    var GRID_DOM = "#indet-ship-content-div";
    return club.View.extend({
        template: club.compile(indentShipViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .ship-indent .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
        	$('.ship-indent input[field=datetime]').datetimepicker({
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
                case 'show': this.show();break;
                case 'shipping':this.shipping();break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        shipping:function(){
            var record = this.selectRecord();
            if (!record || !record.id) {
                return club.toast('warn', '请选择记录！');
            };
            if (this.selectRecords().length>1){
                return club.toast('warn', '只能选择1条！');
            };
            indentShip.openShipWin(this,record);
        },show:function(){
        	var record = this.selectRecord();
        	if (record&&!record.id) {
                return club.toast('warn', '请选择记录！');
            };
            if (this.selectRecords().length>1){
                return club.toast('warn', '只能选择1条！');
            };
            indentDetail.openDetail(record.id);
        },
        search:function(){
            this.pageData(1);
        },
        refresh:function(){
        	$('.ship-indent input[name=istoreName]').val('');
        	$('.ship-indent input[name=iindentName]').val('');
        	$('.ship-indent input[name=ireceiverPhone]').val('');
        	$('.ship-indent input[name=istartTime]').val('');
        	$('.ship-indent input[name=iendTime]').val('');
        	$('.ship-indent input[name=ibuyerName]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            var param = {};
            if($('.ship-indent input[name=istoreName]').val()){
            	param["storeName"]="%"+$('.ship-indent input[name=istoreName]').val()+"%";
            }if($('.ship-indent input[name=iindentName]').val()){
            	param["indentName"]="%"+$('.ship-indent input[name=iindentName]').val()+"%";
            }if($('.ship-indent input[name=ireceiverPhone]').val()){
            	param["receiverPhone"]="%"+$('.ship-indent input[name=ireceiverPhone]').val()+"%";
            }if($('.ship-indent input[name=ibuyerName]').val()){
            	param["buyerName"]="%"+$('.ship-indent input[name=ibuyerName]').val()+"%";
            }if($('.ship-indent input[name=istartTime]').val()){
            	param["startTime"]=$('.ship-indent input[name=istartTime]').val();
            }if($('.ship-indent input[name=iendTime]').val()){
            	param["endTime"]=$('.ship-indent input[name=iendTime]').val();
            }
        	param["indentStatus"]=2;
        	$('.ship-indent .loading').show();
            $.post(Portal.webRoot+'/deal/indent/list.do',{
                conditionStr:JSON.stringify(param),start:(page-1)*rowNum,limit:rowNum},function(result){
	                $('.ship-indent .loading').hide();
			var indentTotalAmount = 0,indentPaymentAmount = 0;
                	if(result.code == 1){
                		indentTotalAmount = result.indentTotalAmount;
	                	indentPaymentAmount = result.indentPaymentAmount;
		                var tempList = result.page.resultList;
                		var pageResult = Portal.convertPage(result.page);
                		pageResult.page = page;
    	                $(GRID_DOM).grid("reloadData", pageResult);
    	                for(var i = 0; i < tempList.length; i++){
    	                	var obj = tempList[i];
    	                	if(obj.shipNotice == 1){
    			                $(GRID_DOM+" td[title="+obj.name+"]").parent("tr").css({ "color": "#ff0011" });
    	                	}
    	                }
	                }
            });
            return false;
        },
        _initListView:function(){
            var me = this;
            //参数配置
            var opt = {
                height:$(window).height()-175,
                width:this.$el.width(),
                colModel: [
						{name: 'id',sorttype: "long",hidden:true},
						{name: 'name',label: '订单号',order: 1,width: 60,align: "center",headertitle : '订单号'}, 
						{name: 'storeName',label: '店铺名称',order: 2,width: 90,align: "center",headertitle : '店铺名称'}, 
						{name: 'buyerName',label: '买家昵称',order: 3,width: 70,align: "center",headertitle : '买家昵称'},
						{name: 'receiver',label: '收货人',order: 4,width: 60,align: "center",headertitle : '收货人'},						
						{name: 'receiverPhone',label: '收货人手机号',order: 5,width: 90,align: "center",headertitle : '收货人手机号'},
						{name: 'fullAddress',label: '收货地址',order: 6,width: 110,align: "center",headertitle : '收货地址'},
						{name: 'number',label: '总数',order: 7,width: 40,align: "center",headertitle : '总数'},
						{name: 'typeName',label: '订单类型',order: 8,width: 60,align: "center",headertitle : '订单类型'},
						{name: 'buyTypeText',label: '购买类型',order: 9,width: 50,align: "center",headertitle : '购买类型'},
						{name: 'totalAmount',label: '商品总金额',order: 10,width: 60,align: "center",headertitle : '商品总金额'},
						{name: 'buyerCarriage',label: '买家运费',order: 11,width: 50,align: "center",headertitle : '买家运费'},
						{name: 'paymentAmount',label: '实际金额',order: 12,width: 70,align: "center",headertitle : '实际金额'},
						{name: 'needInvoiceText',label: '是否要发票',order: 13,width: 60,align: "center",headertitle : '是否要发票'},
						{name: 'invoiceText',label: '抬头',order: 14,width: 60,align: "center",headertitle : '抬头'},
						{name: 'statusName',label: '状态',order: 15,width: 60,align: "center",headertitle : '状态'},
						{name: 'createTime',label: '创建时间',order: 16,width: 100,align: "center",headertitle : '创建时间'}
                    ]
            	,pager: true
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
