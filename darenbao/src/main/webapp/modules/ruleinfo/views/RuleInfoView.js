define([
    'text!modules/ruleinfo/templates/RuleInfoView.html',
    'modules/ruleinfo/levelconf/views/LevelConfWindow',
    'Portal'
], function (ruleinfoViewTpl,LevelConfWindow,Portal) {
    var ruleinfoMapping = {
        "ruleinfo-refresh-btn": ""
    };
    var GRID_DOM = "#member-info-content-div";
    return club.View.extend({
        template: club.compile(ruleinfoViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.ruleinfo-content-div': new ruleinfoImageListViewTpl()
        },
        events: {
            "click .level-action .btn": "tbarHandler",
            "click .rule-info-toolbar .btn": "doToolbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            $('.rule-info input[field=spinner]').spinner();
            $.post(Portal.webRoot+'/integralManger/loadGeneralRule.do',{},function(result){
                $('form[name=rule-info-form').form('value',JSON.parse(result.RULE_INFO));
            });
        },
        doToolbarHandler:function(event){
            var action = $(event.currentTarget).attr("action");
            
            if (action=='edit') {
                var value = $('form[name=rule-info-form').form('value');
                console.info(value)
                value.ruleInfo = JSON.stringify(value);
                $.post(Portal.webRoot+'/integralManger/saveOrUpdateRuleInfo.do',{clientJson:JSON.stringify(value)},function(result){
                    if (result.success) {
                        club.toast('info', "保存成功！");
                    }
                    else{
                        club.toast('error', result.msg);
                    }
                    
                });
            }
            else if (action=='reset') {
                var value = $('form[name=rule-info-form').form('value');
                $('form[name=rule-info-form').form('clear');
                $('form[name=rule-info-form').form('value',value);
            };
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            this.doAction(action);
        },
        doAction:function(action){
            console.info(action);
            if (true) {};
            LevelConfWindow.openAddWin({
                callback:this
            },action,$('input[name='+action+']').val());
        }
    });
});
