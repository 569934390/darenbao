define([
    'text!modules/memberlevel/templates/MemberLevelView.html',
    'modules/memberlevel/addlevel/views/AddClientLevelWindow',
    'Portal'
], function (memberLevelViewTpl,AddClientLevelWindow,Portal) {
    var memberinfoMapping = {
        "memberinfo-refresh-btn": "",
    };
    var GRID_DOM = "#member-level-content-div";
    return club.View.extend({
        template: club.compile(memberLevelViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .member-level-main-btngroup .btn": "tbarHandler"
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
            AddClientLevelWindow.openAddWin({
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
            $.post(Portal.webRoot+'/client/updateLevelState.do',{
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
            rowNum = rowNum || $("#member-level-content-div").grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:(page-1)*rowNum,limit:page*rowNum,name:'client_level',conditionStr:JSON.stringify({
                    'state':'%%'})},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $("#member-level-content-div").grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
            //参数配置
            var opt = {
                height:$(window).height()-125,
                width:this.$el.width(),
                colModel: [{
                    name: 'bizId',
                    sorttype: "int",
                    label: 'Inv No',
                    hidden:true,
                    key:true
                }, {
                    name: 'name',
                    label: '等级名称',
                    width: 90,
                    align: "center",
                    headertitle : '等级名称' //列头的提示
                }, {
                    name: 'icon',
                    label: '类型图标',
                    width: 100,
                    align: "center",
                    formatter:function(val){
                        var imagUrl =  "./image/user4-128x128.jpg";
                        if (val) {
                            imagUrl = Portal.webRoot+'/upload.do?getthumb='+val+'&size=120';
                        };
                        return '<img src = "'+imagUrl+'" class="client-face-img"/> ';
                    },
                    title: false //内容没有提示
                }, {
                    name: 'orderSort',
                    label: '等级排序',
                    width: 80,
                    align: "right"
                }, {
                    name: 'lng',
                    label: '是否横向',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==1) {return '是'};
                        return '否';
                    }
                }, {
                    name: 'lngNum',
                    label: '横向升级人数',
                    width: 90,
                    align: "center"
                }, {
                    name: 'lat',
                    label: '是否纵向',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==2) {return '是'};
                        return '否';
                    }
                }, {
                    name: 'latNum',
                    label: '纵向升级人数',
                    width: 90,
                    align: "right",
                    sorttype: "float"
                }, {
                    name: 'state',
                    label: '状态',
                    width: 100,
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='00A') {return '已审核'};
                        if (cellval=='00X') {return '未审核'};
                        if (cellval=='00B') {return '停用'};
                        return cellval;
                    }
                }],pager: true
                ,rowNum: 10
                ,rowList: [10, 20, 50]
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
            $grid = $("#member-level-content-div").grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
