define([
    'text!modules/storemanager/templates/StoreLevelManagerView.html',
    'modules/storemanager/addstorelevel/views/AddStoreLevelWindow',
    'modules/storemanager/storelevelpro/views/StoreLevelProWindow',
    'Portal'
], function (storeLevelManagerViewTpl,AddstoreLevelWindow,StoreLevelProWindow,Portal) {
	var GRID_DOM = "#storeLevel-info-content-div";
	return club.View.extend({
        template: club.compile(storeLevelManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .storeLevel-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
                case 'refresh': this.refresh();break;
                case 'edit': selectRecord = this.selectRecord();
                case 'add': this.add(action,selectRecord);break;
                case 'del': this.doDelete(action,this.selectRecords());break;
                case 'active':
                case 'disabled':this.doAction(action,this.selectRecords());break;
                case 'loadstorepro':this.loadstorepro(action,$(event.currentTarget).attr("id"));break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        refresh:function(){
        	 console.log("刷新Grid列表");
             $('input[name=storeLevel-name-conditions]').val('');
             this.pageData(1);
        },
        //新增和修改
        add:function(action,record){
            if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords()[0].levelId!=record.levelId) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            if (action=='edit'&&record&&!record.levelId) {
                return club.toast('warn', '请选择记录！');
            };
            AddstoreLevelWindow.openAddWin({
                callback:this
            },action,record);
        },
        //删除
        doDelete:function(action,records){
        	 console.log(action,records);
             if (records.length==0) {
                 var record = this.selectRecord();
                 if (record&&record.levelId) {
                     records.push(record);
                 };
             };
             if (records.length==0) {
                 return club.toast('warn', '请选择记录！');
             }
             var me = this,ids = [];
             for (var i = records.length - 1; i >= 0; i--) {
            	 ids.push(records[i].levelId);
             };
             var t= club.confirm('您确定要删除所选店铺等级吗？');
	       	 t.result.then(function resolve(){
	             $.post(Portal.webRoot+'/store/level/deleteStoreLevel.do',{
	                 IdStr:ids.join(',')},function(result){
	            	 if (result.success) {
	         	         club.toast('info', '操作成功！');
	                 }
	                 else{
	                     club.toast('error', result.msg);
	                 }
	                 me.refresh();
	             });
	    	 }, function reject(){
	    		 return;
	    	 });
        },
        //启用禁用
        doAction:function(action,records){
        	console.log(action,records);
        	if (records.length==0) {
        		var record = this.selectRecord();
        		if (record&&record.levelId) {
        			records.push(record);
        		};
        	};
        	if (records.length==0) {
        		return club.toast('warn', '请选择记录！');
        	}
        	var me = this,ids = [];
        	for (var i = records.length - 1; i >= 0; i--) {
        		ids.push(records[i].levelId);
        	};
            var statue;
            if(action=='active'){
            	statue=1;
            }
            if(action=='disabled'){
            	statue=0;
            }
            $.post(Portal.webRoot+'/store/level/updateStoreLevelStatue.do',{
                IdStr:ids.join(','),statue:statue},function(result){
            	 if (result.success) {
         	         club.toast('info', '操作成功！');
                 }
                 else{
                     club.toast('error', result.msg);
                 }
                me.refresh();
            });
        },
        //查看协议
        loadstorepro:function(action,id){
            var rowValue = $(GRID_DOM).jqGrid('getRowData',id);
            StoreLevelProWindow.openAddWin({
                callback:this
            },action,rowValue);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/store/level/storeLevelPage.do',{
                start:(page-1)*rowNum,limit:rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	
        	//参数配置
            var opt = {
                height:$(window).height()-125,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'levelId',
                    sorttype: "string",
                    hidden:true,
                    key:true
                },{
                    name: 'name',
                    label: '等级名称',
                    width: 150,
                    align: "left"
                },{
                    name: 'headStoreName',
                    width:90,
                    label: '所属总店',
                    hidden:true,
                    align: "left"
                },{
                    name: 'statue',
                    width:100,
                    align: "left",
                    label: '状态',
                    sortable: false,
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {return '启用'};
                        if (cellval=='0') {return '禁用'};
                        return cellval;
                    }
                },{
                    name: 'storePro',
                    label: '店铺协议',
                    hidden:true
                },{
                    width:60,
                    label: '操作',
                    align: "center",
                    formatter:function(cellval, opts, rwdat, _act){
                        return '<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="loadstorepro">店铺协议</a> ';
                    },
                    title: false //内容没有提示
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