define([
    'text!modules/integralmanager/templates/IntegralManagerView.html',
    'tableInfo!integral',
    'tableInfo!rule_set',
    'modules/integralmanager/addrule/views/AddRuleWindow',
    'Portal'
], function (memberLevelViewTpl,integalTalbeInfo,ruleSetTalbeInfo,AddRuleWindow,Portal) {
    var INTEGRAL_MANAGER_LISTVIEW = "#integral-manager-content-div";
    var INTEGRAL_RULE_LISTVIEW = "#integral-rule-content-div";
    var memberinfoMapping = {
        "memberinfo-refresh-btn": "",
    };
    return club.View.extend({
        template: club.compile(memberLevelViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .integral-manager .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            $('#tabs').tabs();
            $('.integral-manager input[field=datetime]').datetimepicker({
                format: 'yyyy/mm/dd hh:ii:ss'
            });
            $('.integral-manager select[field=combobox]').combobox();
            var startTime = new Date(),endTime = new Date();
            startTime.setDate(startTime.getDate()-90);
            endTime.setDate(endTime.getDate()+1);
            $('.integral-manager input[name=istartTime]').datetimepicker('value',club.dateutil.format(startTime,'yyyy/mm/dd hh:ii:ss'));
            $('.integral-manager input[name=iendTime]').datetimepicker('value',club.dateutil.format(endTime,'yyyy/mm/dd hh:ii:ss'));
            $('.integral-manager input[name=ruleStartTime]').datetimepicker('value',club.dateutil.format(startTime,'yyyy/mm/dd hh:ii:ss'));
            $('.integral-manager input[name=ruleEndTime]').datetimepicker('value',club.dateutil.format(endTime,'yyyy/mm/dd hh:ii:ss'));
            this._initManagerListView();
            this._initRuleListView();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
                case 'search-integral' : this.searchIntegral();break;
                case 'search-rule': this.searchRule();break;
                case 'add-rule': this.addRule();break;
                case 'activie-rule': this.doAction('00A');break;
                case 'unactive-rule': this.doAction('00X');break;
            }
        },
        selectRuleRecord:function(){
            return $(INTEGRAL_RULE_LISTVIEW).grid('getSelection');
        },
        selectRuleRecords:function(){
            return $(INTEGRAL_RULE_LISTVIEW).grid('getCheckRows');
        },
        addRule:function(action,record){
            if (action=='edit'&&record&&!record.bizId) {
                return club.toast('warn', '请选择记录！');
            };
            AddRuleWindow.openAddWin({
                callback:this
            },action,record);
        },
        searchIntegral:function(){
            this.pageManagerData(1);
        },
        searchRule:function(){
            this.pageRuleData(1);
        },
        doAction:function(action){
            var records = this.selectRuleRecords();
            if (records.length==0) {
                var record = this.selectRuleRecord();
                if (record&&record.bizId) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
            var me = this,bizIds = [];
            for (var i = records.length - 1; i >= 0; i--) {
                bizIds.push(records[i].bizId);
            };
            $.post(Portal.webRoot+'/integralManger/updateState.do',{
                bizIdStr:bizIds.join(','),action:action},function(result){
                club.toast('info', '操作成功！');
                me.searchRule();
            });
        },
        pageManagerData :function(page, rowNum, sortname, sortorder) {
            var clientName = $('input[name=iclientName]').val();
            var phone = $('input[name=iphone]').val();
            var startTime = $('input[name=istartTime]').val();
            var endTime = $('input[name=iendTime]').val();
            rowNum = rowNum || $("#integral-manager-content-div").grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/integralManger/integralInfoPage.do',{
                conditionStr:JSON.stringify({
                    'clientName':'%'+clientName+'%',
                    'phone':'%'+phone+'%',
                    'startTime':startTime,
                    'endTime':endTime}),start:(page-1)*rowNum,limit:page*rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(INTEGRAL_MANAGER_LISTVIEW).grid("reloadData", result);
            });
            return false;
        },
        _initManagerListView:function(){
            //参数配置
            var opt = Portal.extend(integalTalbeInfo.pageOptions,{
                height:$(window).height()-175
                ,width:this.$el.width()
                ,colModel:[{
                    name:'consumerId',
                    hidden:true
                },{
                    name:'incomingId',
                    hidden:true
                },{
                    name:'rulRuleId',
                    hidden:true
                },{
                    name:'orderId',
                    order:1,
                    align: "center",
                    label:'消费单号'
                },{
                    name:'consumerName',
                    order:3,
                    align: "center",
                    label:'消费者'
                },{
                    name:'ruleName',
                    order:4,
                    align: "center",
                    label:'积分方式'
                },{
                    name:'incomingName',
                    order:2,
                    align: "center",
                    label:'获得积分者'
                },{
                    name: 'state',
                    label: '积分标示',
                    width: 70,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='00A') {return '返'};
                        if (cellval=='00B') {return '送'};
                        if (cellval=='00C') {return '提现'};
                        return cellval;
                    }
                },{
                    name: 'payFor',
                    label: '结算方式',
                    width: 70,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '线上支付'};
                        if (cellval=='2') {return '线下支付'};
                        return cellval;
                    }
                }]
                ,pageData:this.pageManagerData
            });
            this.pageManagerData(1);
            //加载grid
            $grid = $(INTEGRAL_MANAGER_LISTVIEW).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        },
        pageRuleData :function(page, rowNum, sortname, sortorder) {
            var ruleName = $('input[name=ruleName]').val();
            var level = $('select[name=ruleLevel]').val();
            var startTime = $('input[name=ruleStartTime]').val();
            var endTime = $('input[name=ruleEndTime]').val();
            rowNum = rowNum || $("#integral-manager-content-div").grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/integralManger/ruleSetPage.do',{
                conditionStr:JSON.stringify({
                    'ruleName':'%'+ruleName+'%',
                    'level':'%'+level+'%',
                    'startTime':startTime,
                    'endTime':endTime}),start:(page-1)*rowNum,limit:page*rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(INTEGRAL_RULE_LISTVIEW).grid("reloadData", result);
            });
            return false;
        },
        _initRuleListView:function(){
            //参数配置
            var opt = Portal.extend(ruleSetTalbeInfo.pageOptions,{
                height:$(window).height()-175
                ,width:this.$el.width()
                ,colModel:[{
                    name: 'state',
                    label: '状态',
                    width: 70,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='00A') {return '启用'};
                        if (cellval=='00X') {return '失效'};
                        return cellval;
                    }
                },{
                    name: 'ruleType',
                    label: '积分类型',
                    width: 90,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '单笔消费积分'};
                        if (cellval=='2') {return '销售单笔积分'};
                        if (cellval=='3') {return '销售业绩奖励'};
                        return cellval;
                    }
                },{
                    name: 'excType',
                    label: '计算方式',
                    width: 90,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '消费金额'};
                        if (cellval=='2') {return '单笔返比率'};
                        return cellval;
                    }
                },{
                    name: 'level',
                    label: '优先级',
                    width: 70,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '高'};
                        if (cellval=='2') {return '中'};
                        if (cellval=='3') {return '低'};
                        return cellval;
                    }
                }]
                ,pageData:this.pageRuleData
            });
            this.pageRuleData(1);
            //加载grid
            $grid = $(INTEGRAL_RULE_LISTVIEW).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
