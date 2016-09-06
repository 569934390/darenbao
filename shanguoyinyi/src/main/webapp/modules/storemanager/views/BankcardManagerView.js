define([
    'text!modules/storemanager/templates/BankcardManagerView.html',
    'modules/storemanager/addBankcard/views/AddBankcardWindow',
    'Portal'
], function (bankCardViewTpl,AddBankcardWindow,Portal) {
   
    var GRID_DOM = "#bankCard-manager-content-div";
    return club.View.extend({
        template: club.compile(bankCardViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .bankCard-manager-main-btngroup .btn": "tbarHandler"
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
                case 'edit': selectRecord = this.selectRecord();
                case 'add': this.add(action,selectRecord);break;
                case 'del':
                case 'active':
                case 'disabled':this.doAction(action,this.selectRecords());break;
            }
        },
        selectRecord:function(){
        	
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        add:function(action,record){
        	  
            if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords()[0].bankCardId!=record.bankCardId) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            if (action=='edit'&&record&&!record.bankCardId) {
                return club.toast('warn', '请选择记录！');
            };
            AddBankcardWindow.openAddWin({
                callback:this
            },action,record);
        },
        convertState:function(action){
            if (action=='disabled') {return '1'}
            else if (action=='active') {return '0'}
        },
        doAction:function(action,records){
            console.log(action,records);
            if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.bankCardIdString) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
            var me = this,bankCardIds = [];
            for (var i = records.length - 1; i >= 0; i--) {
            	bankCardIds.push(records[i].bankCardIdString);
            };
            $.post(Portal.webRoot+'/BankCard/updateBankCardState.do',{
            	bankCardIds:bankCardIds.join(','),action:this.convertState(action)},function(result){
                club.toast('info', '操作成功！');
                me.refresh();
            });
        },
        refresh:function(){
            console.log("刷新Grid列表");
            $("#bankCardCondition").val("");
            this.pageData(1);
        },
        
		pageData : function(page, rowNum, sortname, sortorder) {
							rowNum = rowNum
									|| $(GRID_DOM).grid("getGridParam",
											"rowNum");
							$.post(Portal.webRoot+ '/BankCard/getBankCardList.do',
							{
								start : (page - 1) * rowNum,
								limit : page * rowNum,
							    name : $("#bankCardCondition").val(),
								conditionStr : JSON.stringify({
									'name' : $("#bankCardCondition").val()
								})
							}, function(result) {
								result = Portal.convertPage(result);
								result.page = page;
								$(GRID_DOM).grid("reloadData", result);
							});
							return false;
		},
		search:function(){
	            console.log("刷新Grid列表");
	            this.pageDataSearch(1);
	        },
	    checkBoxList:function( ){
	    	$.post(Portal.webRoot+ '/BankCard/getBankCardTypeList.do',{},
	    			function(result){
	    	});
	    },
	    pageDataSearch : function(page, rowNum, sortname, sortorder) {
								rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
								$.post(Portal.webRoot+ '/BankCard/getBankCardList.do',
								{
									start : (page - 1) * rowNum,
									limit : page * rowNum,
									name : $("#bankCardCondition").val(),
									conditionStr : JSON.stringify({
										'name' : $("#bankCardCondition").val()
									})
								}, function(result) {
									result = Portal.convertPage(result);
									result.page = page;
									$(GRID_DOM).grid("reloadData", result);
								});
								return false;
			},
        _initListView:function(){

            /*var opt = Portal.extend(bankCardTalbeInfo.pageOptions,
            		{
                height:$(window).height()-125
                ,width:this.$el.width()
                ,colModel:[{
                    name: 'state',
                    label: '状态',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='00A') {return '启用'};
                        if (cellval=='00X') {return '停用'};
                        return cellval;
                    }
                },{
                    name: 'type',
                    label: '账号类型',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '支付宝'};
                        if (cellval=='2') {return '储蓄卡'};
                        if (cellval=='3') {return '信用卡'};
                        return cellval;
                    }
                }]
                ,pageData:this.pageData
            }
            
            );*/
        	 var opt = {
                     height:$(window).height()-125,
                     width:this.$el.width(),
                     rownumbers:true,
                     colModel: [{
                         name: 'bankCardId',
                         sorttype: "int",
                         hidden:true,
                         key:true
                     },{
                         name: 'bankCardIdString',
                         hidden:true
                     },{
                         name: 'name',
                         width:20,
                         label: '姓名',
                         align: "center"
                     },  {
                         name: 'mobile',
                         label: '手机号',
                         width: 30,
                         align: "center",
                     },  {
                         name: 'idCard',
                         label: '身份证',
                         width: 30,
                         align: "center",
                     }, {
                         name: 'bankName',
                         label: '银行全称',
                         width: 30,
                         align: "center"         
                     }, {
                         name: 'bankAddress',
                         width:30,
                         align: "center",
                         label: '开户行'
                     }, {
                         name: 'bankCard',
                         width:30,
                         align: "center",
                         label: '银行卡号'
                     }, {
                         name: 'connectName',
                         width:50,
                         align: "center",
                         label: '所属分店/会员/总店名称'
                     }, {
                         name: 'connectTypeName',
                         width:20,
                         align: "center",
                         label: '所属类型'
                     }, {
                         name: 'createTime',
                         width:30,
                         align: "center",
                         label: '创建时间'
                     }, {
                         name: 'updateTime',
                         width:30,
                         align: "center",
                         label: '更新时间'
                     }, {
                         name: 'state',
                         label: '禁用状态',
                         width: 30,
                         align: "center",
                         title: false, //内容没有提示
                         formatter: function(state) {
                             if (state=='1') {return '是'};
                             if (state=='0') {return '否'};
                             return state;
                         }
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
