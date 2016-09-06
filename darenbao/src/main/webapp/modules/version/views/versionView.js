define([
    'text!modules/version/templates/versionView.html',
    'modules/version/addversion/views/AddVersionWindow',
    'Portal',
], function (versionViewTpl,AddVersionWindow,Portal) {
    var GRID_DOM = "#version-manager-content-div";
    return club.View.extend({
        template: club.compile(versionViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .version-manager .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            $('#tabs').tabs();
            this._initVersionListView();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
            	case 'refresh': this.refresh();break;
                case 'search' : this.search();break;
                case 'edit': selectRecord = this.selectRecord();
                case 'add': this.add(action,selectRecord);break;
                case 'del':this.del(this.selectRecords());break;
                case 'able':this.able();break;
                case 'disable': this.disable();break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        add:function(action,record){
        	if (action=='edit'&&record&&!record.id) {
                return club.toast('warn', '请选择记录！');
            };
            if (action=='edit'&&this.selectRecords().length>1){
                return club.toast('warn', '只能选择1条！');
            };
            AddVersionWindow.openAddWin({
                callback:this
            },action,record);
        },del:function(records){
        	if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
        	var me = this;
        	club.confirm("是否删除！").result.then(function(){
        		var ids = [];
                for (var i = records.length - 1; i >= 0; i--) {
                	ids.push(records[i].id);
                };
                $.post(Portal.webRoot+'/module/version/delete.do',{
                    ids:ids.join(',')},function(result){
                    club.toast('info', '操作成功！');
                    me.refresh();
                });
        	},function(){
        		return;
        	});
        },
        search:function(){
            this.pageData(1);
        },able:function(){
        	var record = this.selectRecord();
        	var res = this.selectRecords();
        	if(res.length == 0 && (!record || !record.id)){
                return club.toast('warn', '请选择记录！');
        	}if(this.selectRecords().length > 1){
                return club.toast('warn', "置为'生效'只能选择1条！");
        	}
        	var records = [];
        	records[0] = record;
        	this.doAction("able",records);
        },disable:function(){
        	var records = this.selectRecords();
        	if (records.length==0) {
                return club.toast('warn', '请勾选记录！');
            }
        	this.doAction("disable",records);
        },
        doAction:function(action,records){
            var me = this,ids = [];
            for (var i = records.length - 1; i >= 0; i--) {
                ids.push(records[i].id);
            };
            $.post(Portal.webRoot+'/module/version/'+action+'.do',{
                ids:ids.join(',')},function(result){
                	if(result.code == 1){
                		club.toast('info', '操作成功！');
                        me.refresh();
                	}else{
                		club.toast('error', result.msg);
                	}
            });
        },
        refresh:function(){
        	$('input[name=iname]').val('');
        	$('input[name=icode]').val('');
        	$('select[name=iplatform]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            var conditionStr = {'name':'%'+$('input[name=iname]').val()+'%',
                	'code':'%'+$('input[name=icode]').val()+'%'};
            if($('select[name=iplatform]').val() != undefined &&$('select[name=iplatform]').val() != ''){
            	conditionStr['platform']=$('select[name=iplatform]').val();
            }
            $.post(Portal.webRoot+'/module/version/list.do',{
                conditionStr:JSON.stringify(conditionStr),start:(page-1)*rowNum,limit:rowNum},function(result){
	                result = Portal.convertPage(result);
	                result.page = page;
	                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initVersionListView:function(){
            var me = this;
            //参数配置
            var opt = {
                height:$(window).height()-150,
                width:this.$el.width(),
                colModel: [
						{name: 'id',sorttype: "long",hidden:true},
						{name: 'name',label: '版本名称',order: 1,width: 70,align: "center",headertitle : '版本名'}, 
						{name: 'code',label: '版本号',order: 2,width: 60,align: "center",headertitle : '版本号'}, 
						{name: 'descriptionText',label: '版本描述',order: 3,width: 170,align: "left",headertitle : '版本描述'},
						{name: 'statusText',label: '版本状态',order: 4,width: 70,align: "center",headertitle : '版本状态'},						
						{name: 'platformText',label: '平台',order: 5,width: 60,align: "center",headertitle : '平台'},
						{name: 'urlText',label: '下载链接',order: 6,width: 110,align: "center",headertitle : '下载链接'},
						{name: 'creater',label: '创建人',order: 7,width: 70,align: "center",headertitle : '创建人'},
						{name: 'modifier',label: '修改人',order: 8,width: 70,align: "center",headertitle : '修改人'},
						{name: 'updateTime',label: '更新时间',order: 9,width: 120,align: "center",headertitle : '更新时间'},
						{name: 'effectText',label: '是否生效',order: 10,width: 60,align: "center",headertitle : '是否生效'},
						{name: 'downloadWayText',label: '下载方式',order: 11,width: 60,align: "center",headertitle : '下载方式'}
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
