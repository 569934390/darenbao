define([
    'text!modules/financemanager/templates/AccountManagerView.html',
    'tableInfo!account',
    'modules/financemanager/addAccount/views/AddAccountWindow',
    'Portal'
], function (memberLevelViewTpl,accountTalbeInfo,AddAccountWindow,Portal) {
    var memberinfoMapping = {
        "memberinfo-refresh-btn": "",
    };
    var GRID_DOM = "#account-manager-content-div";
    return club.View.extend({
        template: club.compile(memberLevelViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .account-manager-main-btngroup .btn": "tbarHandler"
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
                if (this.selectRecords()[0].accountId!=record.accountId) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            if (action=='edit'&&record&&!record.accountId) {
                return club.toast('warn', '请选择记录！');
            };
            AddAccountWindow.openAddWin({
                callback:this
            },action,record);
        },
        convertState:function(action){
            if (action=='disabled') {return '00X'}
            else if (action=='active') {return '00A'}
        },
        doAction:function(action,records){
            console.log(action,records);
            if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.accountId) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
            var me = this,accountIds = [];
            for (var i = records.length - 1; i >= 0; i--) {
                accountIds.push(records[i].accountId);
            };
            $.post(Portal.webRoot+'/audit/updateAccountState.do',{
                bizIdStr:accountIds.join(','),action:this.convertState(action)},function(result){
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
            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:(page-1)*rowNum,limit:page*rowNum,name:'account',conditionStr:JSON.stringify({
                    'state':'%%'})},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){

            var opt = Portal.extend(accountTalbeInfo.pageOptions,{
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
            });
            this.pageData(1);
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
