define([
    'text!modules/memberinfo/templates/MemberInfoView.html',
    'text!modules/memberinfo/templates/CheckView.html',
    'modules/memberinfo/addmember/views/AddMemberWindow',
    'modules/memberinfo/memberTree/views/MemberTreeWindow',
    'Portal',
    'text!data/cityData.json'
], function (memberinfoViewTpl,checkViewTpl,AddMemberWindow,MemberTreeWindow,Portal,regionData) {
    var memberinfoMapping = {
        "memberinfo-refresh-btn": ""
    },regionData = JSON.parse(regionData),provice={},city={},region={};
    for (var i = regionData.length - 1; i >= 0; i--) {
        provice[regionData[i].id] = regionData[i];
        for (var j = regionData[i].children.length - 1; j >= 0; j--) {
            city[regionData[i].children[j].id]=regionData[i].children[j];
            if (regionData[i].children[j].children) {
                for (var k = regionData[i].children[j].children.length - 1; k >= 0; k--) {
                    region[regionData[i].children[j].children[k].id] = regionData[i].children[j].children[k];
                };
            };
        };
    };
    var GRID_DOM = "#member-info-content-div";
    return club.View.extend({
        template: club.compile(memberinfoViewTpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .member-info-main-btngroup .btn": "tbarHandler"
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
                case 'jinGou':
                case 'jinYan': this.doAction(action,this.selectRecords());break;
                case 'check': this.doCheck(action,this.selectRecords());break;
                case 'tree' : this.treeInfo(action,this.selectRecord());break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        treeInfo:function(action,record){
            if (record==null||record.clientName==null) {
                return club.toast('warn', '请选择记录！');
            };
            MemberTreeWindow.openAddWin({
                callback:this
            },record);
        },
        add:function(action,record){
            if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords()[0].bizId!=record.bizId) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            if (action=='edit'&&record&&!record.bizId) {
                return club.toast('warn', '请选择记录！');
            };
            AddMemberWindow.openAddWin({
                callback:this
            },action,record);
        },
        search:function(){
            this.pageData(1);
        },
        convertState:function(action){
            if (action=='del') {return '00X'}
            else if (action=='jinYan') {return '00D'}
            else if (action=='jinGou') {return '00C'}
            else if (action=='check') {return '00A'}
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
            $.post(Portal.webRoot+'/client/updateClientState.do',{
                bizIdStr:bizIds.join(','),action:this.convertState(action),context:action},function(result){
                club.toast('info', '操作成功！');
                me.refresh();
            });
        },
        doCheck:function(action,records){
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
            
            var popup = club.popup({
                modal: true,
                height: 300,
                draggable: true,
                content: checkViewTpl,
                autoResizable: true
            });

            $('.memberCheckWindow select[name=action]').combobox();
            $('.memberCheckWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'check-button': 
                         $('form[name=check-form]').isValid();
                         var isValid = $('form[name=check-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=check-form').form('value');
                            value.bizIdStr = bizIds.join(',');
                            $.post(Portal.webRoot+'/client/updateClientState.do',value,function(result){
                                club.toast('info', '操作成功！');
                                me.refresh();
                                popup.close();
                            });
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                    case 'clear-button': $('form[name=check-form').form('clear');break;
                }
            });
            
        },
        refresh:function(){
            console.log("刷新Grid列表");
            $('input[name=member-conditions]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var conditions = $('input[name=member-conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/client/clientInfoPage.do',{
                conditionStr:JSON.stringify({
                    'clientName':'%'+conditions+'%',
                    'levelName':'%'+conditions+'%',
                    'state':'%'+($('#stateBtn').data('filter')||'')+'%'}),
                start:(page-1)*rowNum,limit:page*rowNum,name:'client'},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
            var me = this;
            //参数配置
            var opt = {
                height:$(window).height()-145,
                width:this.$el.width(),
                colModel: [{
                    name: 'bizId',
                    sorttype: "int",
                    hidden:true,
                    key:true
                }, {
                    name: 'icon',
                    hidden:true
                }, {
                    name: 'subClient',
                    hidden:true
                }, {
                    name: 'clientNumber',
                    width:90,
                    label: '会员编号'
                },  {
                    name: 'clientName',
                    label: '会员名称',
                    width: 150,
                    align: "left",
                    formatter:function(cellval,opts,rwdat,_act){
                        var imagUrl =  "./image/user5-128x128.jpg";
                        if (rwdat.icon) {
                            imagUrl = Portal.webRoot+'/upload.do?getthumb='+rwdat.icon+'&size=150';
                        };
                        return '<img src = "'+imagUrl+'" class="client-face-img"/> '+cellval;
                    },
                    headertitle : '会员名称' //列头的提示
                }, {
                    name: 'levelName',
                    label: '会员级别',
                    width: 80,
                    align: "center",
                    title: false //内容没有提示
                }, {
                    name: 'age',
                    width:100,
                    align: "center",
                    label: '年龄'
                },  {
                    name: 'phone',
                    width:100,
                    label: '电话号码'
                }, {
                    name: 'region',
                    label: '地区',
                    width: 160,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (!provice[rwdat.provice]||!city[rwdat.city]) {
                            return '未知';
                        };
                        var area = provice[rwdat.provice].name+' '+city[rwdat.city].name;
                        if(region[rwdat.region]&&region[rwdat.region].name){
                            area+=' ' + region[rwdat.region].name;
                        }
                        return area;
                    }
                },{
                    name: 'age',
                    sorttype: "int",
                    hidden:true,
                    key:true
                }, {
                    name: 'bankNumber',
                    label: '银行卡号',
                    width: 135,
                    align: "center"
                }, {
                    name: 'subNums',
                    label: '团队人数',
                    width: 80,
                    align: "right",
                    sorttype: "float"
                }, {
                    name: 'parentClientName',
                    label: '直接上级',
                    width: 80,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (!cellval) {return '无'};
                        return cellval;
                    }
                }, {
                    name: 'state',
                    label: '状态',
                    width: 65,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='00A') {return '已审核'};
                        if (cellval=='00B') {return '待充值'};
                        if (cellval=='00C') {return '待审核'};
                        if (cellval=='00D') {return '禁言'};
                        if (cellval=='00E') {return '审核失败'};
                        if (cellval=='00X') {return '已删除'};
                        return cellval;
                    }
                }, {
                    name: 'stateTime',
                    label: '更新时间',
                    width: 120
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
            $('#stateFilter a').click(function(){
                $('#stateBtn').data('filter',$(this).attr('filter')).html($(this).html());
                me.refresh();
            });
        }
    });
});
