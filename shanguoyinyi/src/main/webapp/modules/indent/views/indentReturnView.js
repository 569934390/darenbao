define([
    'text!modules/indent/templates/indentReturnView.html',
    'modules/indent/indentWindow/views/indentDetail',
    'Portal',
], function (indentReturnViewTpl,indentDetail,Portal) {
    var GRID_DOM = "#indet-return-content-div";
    return club.View.extend({
        template: club.compile(indentReturnViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .return-indent .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
        	$('.return-indent input[field=datetime]').datetimepicker({
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
                case 'returning': this.returning();break;
                case 'receive':this.receive();break;
                case 'show': this.show();break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },show:function(){
        	var record = this.selectRecord();
        	if (record&&!record.id) {
                return club.toast('warn', '请选择记录！');
            };
            if (this.selectRecords().length>1){
                return club.toast('warn', '只能选择1条！');
            };
            indentDetail.openDetail(record);
        },receive:function(){
        	var records = this.selectRecords();
        	if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.id) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
            var me = this;
            club.prompt('请填写拒绝退货原因:').result.then(function resolve(val){
        		if(val==''||val==null||val==undefined){
        			return club.toast('warn', '请填写拒绝退货原因!');
        		}else{
        			var ids = [];
                    for (var i = records.length - 1; i >= 0; i--) {
                    	ids.push(records[i].id);
                    };
                    var map = new Map();
                    map["content"]=val;
                    $.post(Portal.webRoot+'/deal/indent/update/receive.do',{ids:ids.join(','),mapStr:JSON.stringify(map)},
            			function(result){
        					if(result.code == 1){
        						club.toast('info', '操作成功！');
        	                    me.pageData(1);
        					}else{
        		                return club.toast('error', result.msg);
        					}
        				}
                	);
        		}
       	 	}, function reject(){
       	 		return;
       	 	});
        },returning:function(){
        	var records = this.selectRecords();
        	if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.id) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
            var me = this;
            club.confirm("是否同意退货！").result.then(function(){
            	var ids = [];
                for (var i = records.length - 1; i >= 0; i--) {
                	ids.push(records[i].id);
                };
                $.post(Portal.webRoot+'/deal/indent/update/returning.do',{ids:ids.join(',')},
        			function(result){
    					if(result.code == 1){
    						club.toast('info', '操作成功！');
    	                    me.pageData(1);
    					}else{
    		                return club.toast('error', result.msg);
    					}
    				}
            	);
        	},function(){
        		return;
        	});
        },
        show:function(){
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
        	$('.return-indent input[name=istoreName]').val('');
        	$('.return-indent input[name=iindentName]').val('');
        	$('.return-indent input[name=ireceiverPhone]').val('');
        	$('.return-indent input[name=istartTime]').val('');
        	$('.return-indent input[name=iendTime]').val('');
        	$('.return-indent input[name=ibuyerName]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            var param = {};
            if($('.return-indent input[name=istoreName]').val()){
            	param["storeName"]="%"+$('.return-indent input[name=istoreName]').val()+"%";
            }if($('.return-indent input[name=iindentName]').val()){
            	param["indentName"]="%"+$('.return-indent input[name=iindentName]').val()+"%";
            }if($('.return-indent input[name=ireceiverPhone]').val()){
            	param["receiverPhone"]="%"+$('.return-indent input[name=ireceiverPhone]').val()+"%";
            }if($('.return-indent input[name=ibuyerName]').val()){
            	param["buyerName"]="%"+$('.return-indent input[name=ibuyerName]').val()+"%";
            }if($('.return-indent input[name=istartTime]').val()){
            	param["startTime"]=$('.return-indent input[name=istartTime]').val();
            }if($('.return-indent input[name=iendTime]').val()){
            	param["endTime"]=$('.return-indent input[name=iendTime]').val();
            }
        	param["indentStatus"]=6;
        	$('.return-indent .loading').show();
            $.post(Portal.webRoot+'/deal/indent/list.do',{
                conditionStr:JSON.stringify(param),start:(page-1)*rowNum,limit:rowNum},function(result){
	                $('.return-indent .loading').hide();
			var indentTotalAmount = 0,indentPaymentAmount = 0;
                	if(result.code == 1){
                		indentTotalAmount = result.indentTotalAmount;
	                	indentPaymentAmount = result.indentPaymentAmount;
                		var pageResult = Portal.convertPage(result.page);
                		pageResult.page = page;
    	                $(GRID_DOM).grid("reloadData", pageResult);
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
						{name: 'name',label: '订单号',order: 1,width: 80,align: "center",headertitle : '订单号'}, 
						{name: 'storeName',label: '店铺名称',order: 2,width: 100,align: "center",headertitle : '店铺名称'}, 
						{name: 'buyerName',label: '买家昵称',order: 3,width: 70,align: "center",headertitle : '买家昵称'},
						{name: 'receiver',label: '收货人',order: 4,width: 70,align: "center",headertitle : '收货人'},						
						{name: 'receiverPhone',label: '收货人手机号',order: 5,width: 90,align: "center",headertitle : '收货人手机号'},
						{name: 'fullAddress',label: '收货地址',order: 6,width: 130,align: "center",headertitle : '收货地址'},
						{name: 'number',label: '总数',order: 7,width: 40,align: "center",headertitle : '总数'},
						{name: 'typeName',label: '订单类型',order: 8,width: 80,align: "center",headertitle : '订单类型'},
						{name: 'buyTypeText',label: '购买类型',order: 9,width: 50,align: "center",headertitle : '购买类型'},
						{name: 'totalAmount',label: '商品总金额',order: 10,width: 60,align: "center",headertitle : '商品总金额'},
						{name: 'buyerCarriage',label: '买家运费',order: 11,width: 50,align: "center",headertitle : '买家运费'},
						{name: 'paymentAmount',label: '实际金额',order: 12,width: 80,align: "center",headertitle : '实际金额'},
						{name: 'statusName',label: '状态',order: 13,width: 70,align: "center",headertitle : '状态'},
						{name: 'createTime',label: '创建时间',order: 14,width: 120,align: "center",headertitle : '创建时间'}
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
