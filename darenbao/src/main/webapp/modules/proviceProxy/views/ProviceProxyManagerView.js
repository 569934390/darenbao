define([
    'text!modules/proviceProxy/templates/ProviceProxyView.html',
    'modules/proviceProxy/addProxy/views/AddProviceProxyWindow',
    'Portal',
    'tableInfo!provice_proxy'
], function (proviceProxyViewTpl,AddProviceProxyWindow,Portal,proviceProxyInfo) {
    var memberinfoMapping = {
        "memberinfo-refresh-btn": "",
    };
    var GRID_DOM = "#provice-proxy-content-div";
    return club.View.extend({
        template: club.compile(proviceProxyViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .provice-proxy-main-btngroup .btn": "tbarHandler"
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
            if (action=='edit'&&record&&!record.bizId) {
                return club.toast('warn', '请选择记录！');
            };
            AddProviceProxyWindow.openAddWin({
                callback:this
            },action,record);
        },
        convertState:function(action){
            if (action=='del') {return '00X'}
            else if (action=='disabled') {return '00B'}
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
            var me = this,bizIds = [];
            for (var i = records.length - 1; i >= 0; i--) {
                bizIds.push(records[i].bizId);
            };
            $.post(Portal.webRoot+'/client/updateProxyState.do',{
                bizIdStr:bizIds.join(','),action:this.convertState(action)},function(result){
                club.toast('info', '操作成功！');
                me.refresh();
            });
        },
        refresh:function(){
            console.log("刷新Grid列表");
            this.pageData(1);
        },
        pageData :function(page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $("#provice-proxy-content-div").grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:(page-1)*rowNum,limit:page*rowNum,name:'provice_proxy',conditionStr:JSON.stringify({
                    'state':'%%'})},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $("#provice-proxy-content-div").grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
            //参数配置
            var opt = Portal.extend(proviceProxyInfo.pageOptions,{
                height:$(window).height()-125,
                width:this.$el.width(),
                colModel: [{
                    name: 'bizId',
                    hidden:true,
                    key:true
                },{
                    name: 'createDate',
                    hidden:true
                },{
                    name: 'modifyer',
                    hidden:true
                },{
                    name: 'name',
                    label: '市代理名称',
                    width: 90,
                    align: "center",
                    headertitle : '市代理名称' //列头的提示
                },{
                    name: 'state',
                    label: '状态',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='00A') {return '启用'};
                        if (cellval=='00B') {return '停用'};
                        if (cellval=='00X') {return '删除'};
                        return cellval;
                    }
                }]
                ,pageData:this.pageData
            });
            this.pageData(1);
            //加载grid
            $grid = $("#provice-proxy-content-div").grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
