define([
    'text!modules/trademanager/templates/HeadStoreManagerView.html',
    'modules/trademanager/addheadStore/views/AddheadStoreWindow',
    'Portal'
], function (HeadStoreManagerViewTpl,AddheadStoreWindow,Portal) {
	var GRID_DOM = "#headStore-info-content-div";
	return club.View.extend({
        template: club.compile(HeadStoreManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .headStore-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
                case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
                case 'edit': selectRecord = this.selectRecord();
                case 'add': this.add(action,selectRecord);break;
                case 'del': this.doDelete(action,this.selectRecords());break;
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
        search:function(){
        	 this.pageData(1);
        },
        refresh:function(){
        	 console.log("刷新Grid列表");
             $('input[name=headStore-name-conditions]').val('');
             $('select[name=headStore-statue-conditions] option:first').attr('selected','selected');
             this.pageData(1);
        },
        //新增和修改
        add:function(action,record){
            if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords()[0].id!=record.id) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            if (action=='edit'&&record&&!record.id) {
                return club.toast('warn', '请选择记录！');
            };
            AddheadStoreWindow.openAddWin({
                callback:this
            },action,record);
        },
        //删除
        doDelete:function(action,records){
        	 console.log(action,records);
             if (records.length==0) {
                 var record = this.selectRecord();
                 if (record&&record.id) {
                     records.push(record);
                 };
             };
             if (records.length==0) {
                 return club.toast('warn', '请选择记录！');
             }
             var me = this,ids = [];
             for (var i = records.length - 1; i >= 0; i--) {
            	 ids.push(records[i].id);
             };
             var t= club.confirm('您确定要删除所选总店信息吗？');
	       	 t.result.then(function resolve(){
	             $.post(Portal.webRoot+'/trade/headStore/deleteTradeHeadStore.do',{
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
        		if (record&&record.id) {
        			records.push(record);
        		};
        	};
        	if (records.length==0) {
        		return club.toast('warn', '请选择记录！');
        	}
        	var me = this,ids = [];
        	for (var i = records.length - 1; i >= 0; i--) {
        		ids.push(records[i].id);
        	};
            var statue;
            if(action=='active'){
            	statue=1;
            }
            if(action=='disabled'){
            	statue=0;
            }
            $.post(Portal.webRoot+'/trade/headStore/updateTradeHeadStoreStatue.do',{
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
        pageData :function (page, rowNum, sortname, sortorder) {
            var conditions = $('input[name=headStore-name-conditions]').val();
            var statue = $('select[name=headStore-statue-conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/trade/headStore/tradeHeadStorePage.do',{
                conditionStr:JSON.stringify({
                    'conditions':'%'+conditions+'%','statue':statue}),
                start:(page-1)*rowNum,limit:rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	
        	$("input[name=headStore-name-conditions]").css({width:"50%"});  
        	$("select[name=headStore-statue-conditions]").css({width:"50%"});  
        	
        	//参数配置
            var opt = {
                height:$(window).height()-125,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'ownerName',
                    width:90,
                    label: '店主',
                    align: "left"
                },  {
                    name: 'name',
                    label: '店铺名称',
                    width: 150,
                    align: "left"
                }, {
                    name: 'tel',
                    label: '联系方式',
                    width: 80,
                    align: "left",
                    title: false //内容没有提示
                }, {
                    name: 'owner',
                    hidden:true,
	            }, {
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