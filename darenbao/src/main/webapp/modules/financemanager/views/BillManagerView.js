define([
    'text!modules/financemanager/templates/BillManagerView.html',
    'tableInfo!bill',
    'dataSet!account',
    'dataSet!item',
    'modules/financemanager/addBill/views/AddBillWindow',
    'Portal'
], function (memberLevelViewTpl,billTalbeInfo,accountInfo,itemInfo,AddBillWindow,Portal) {
    var memberinfoMapping = {
        "memberinfo-refresh-btn": "",
    };
    var GRID_DOM = "#bill-manager-content-div";
    return club.View.extend({
        template: club.compile(memberLevelViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .bill-manager-main-btngroup .btn": "tbarHandler"
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
        add:function(action,record){
            if (action=='edit'&&record&&!record.accountId) {
                return club.toast('warn', '请选择记录！');
            };
            AddBillWindow.openAddWin({
                callback:this
            },action,record);
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        convertState:function(action){
            if (action=='disabled') {return '00X'}
            else if (action=='active') {return '00A'}
        },
        doAction:function(action,records){
            console.log(action,records);
            if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.bizId) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
            var me = this,billIds = [];
            for (var i = records.length - 1; i >= 0; i--) {
                billIds.push(records[i].bizId);
            };
            $.post(Portal.webRoot+'/audit/updateBillState.do',{
                bizIdStr:billIds.join(','),action:this.convertState(action)},function(result){
                club.toast('info', '操作成功！');
                me.refresh();
            });
        },
        refresh:function(){
            console.log("刷新Grid列表");
            this.pageData(1);
        },
        pageData :function(page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:(page-1)*rowNum,limit:page*rowNum,name:'bill',conditionStr:JSON.stringify({
                    'state':'%%'})},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){

            var opt = Portal.extend(billTalbeInfo.pageOptions,{
                height:$(window).height()-125
                ,width:this.$el.width()
                ,colModel:[{
                    name:'createDate',
                    label:'付款日期'
                },{
                    name:'modifyTime',
                    label:'审核日期'
                },{
                    name: 'accountId',
                    label: '账号',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        for (var i = accountInfo.resultList.length - 1; i >= 0; i--) {
                            var account = accountInfo.resultList[i];
                            if (account.accountId==cellval) {
                                return account.name;
                            };
                        };;
                        return cellval;
                    }
                },{
                    name: 'itemId',
                    label: '科目',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        for (var i = itemInfo.resultList.length - 1; i >= 0; i--) {
                            var item = itemInfo.resultList[i];
                            if (item.itemId==cellval) {
                                return item.name;
                            };
                        };;
                        return cellval;
                    }
                },{
                    name: 'type',
                    label: '类型',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (parseInt(cellval)==1) {return '应收'};
                        if (parseInt(cellval)==2) {return '应付'};
                        return cellval;
                    }
                },{
                    name: 'state',
                    label: '状态',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='00A') {return '已审核'};
                        if (cellval=='00B') {return '未审核'};
                        if (cellval=='00X') {return '审核不通过'};
                        return cellval;
                    }
                }]
                ,pageData:this.pageData
            });
            this.pageData(1);
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
