define([
    'text!modules/memberexchange/templates/MemberExchangeView.html',
    'Portal'
], function (memberExchangeViewTpl,Portal) {
    var memberinfoMapping = {
        "memberinfo-refresh-btn": "",
    };
    return club.View.extend({
        template: club.compile(memberExchangeViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },

        //刷新Grid列表
        _doRefreshGridListView: function () {
            console.log("刷新Grid列表");
        },
        _initListView:function(){

            function pageData (page, rowNum, sortname, sortorder) {
                rowNum = rowNum || $("#member-exchange-content-div").grid("getGridParam", "rowNum");
                $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:(page-1)*rowNum,limit:page*rowNum,name:'client'},function(result){
                    result = Portal.convertPage(result);
                    result.page = page;
                    $("#member-exchange-content-div").grid("reloadData", result);
                });
                return false;
            };
            //参数配置
            var opt = {
                height:$(window).height()-125,
                width:this.$el.width(),
                colModel: [{
                    name: 'id',
                    sorttype: "int",
                    label: 'Inv No',
                    hidden:true,
                    key:true
                }, {
                    name: 'clientId',
                    sorttype: "int",
                    width:50,
                    label: '会员编号'
                }, {
                    name: 'clientName',
                    label: '会员名称',
                    width: 90,
                    align: "center",
                    headertitle : '会员名称' //列头的提示
                }, {
                    name: 'levelName',
                    label: '提现金额',
                    width: 100,
                    align: "center",
                    title: false //内容没有提示
                }, {
                    name: 'area',
                    label: '账户金额',
                    width: 80,
                    align: "center"
                }, {
                    name: 'perRange',
                    label: '申请提现时间',
                    width: 80,
                    align: "right",
                    sorttype: "float"
                }, {
                    name: 'parentClientId',
                    label: '操作',
                    width: 80,
                    align: "right",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='-1') {return '无'};
                        return cellval;
                    }
                }],pager: true
                ,rowNum: 5
                ,rowList: [2, 5, 10]
                ,datatype: "json"
                ,recordtext:"{0} 到 {1} 总共: {2} "
                ,pgtext : " {0} / {1}"
                ,rowtext:"{0}"
                ,gotext:'跳转至第{0}页'
                ,multiselect:true
                ,pageData:pageData
            };
            pageData(1);
            //加载grid
            $grid = $("#member-exchange-content-div").grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
