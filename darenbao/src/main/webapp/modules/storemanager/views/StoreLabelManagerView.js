define([
    'text!modules/storemanager/templates/StoreLabelManagerView.html',
    'modules/storemanager/addStoreLabel/views/AddStoreLabelWindow',
    'i18n!modules/common/i18n/TextView.i18n',
    'Portal'
], function (StoreLabelManagerViewTpl,AddStoreLabelWindow,i18nLogin,Portal) {
	var GRID_DOM = "#StoreLabel-info-content-div";
	return club.View.extend({
        template: club.compile(StoreLabelManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .StoreLabel-info-main-btngroup .btn": "tbarHandler"
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
             $('input[name=StoreLabel-name-conditions]').val('');
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
            if(action=='edit'&&record.shopId!=null){
            	return club.toast('error',record.labelName+'：是总店数据，平台不能操作！');
            }
            AddStoreLabelWindow.openAddWin({
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
            	 if(records[i].shopId!=null){
            		 return club.toast('error',records[i].labelName+'：是总店数据，平台不能操作！');
            	 }
            	 ids.push(records[i].id);
             };
             var t= club.confirm('您确定要删除所选标签吗？');
	       	 t.result.then(function resolve(){
	             $.post(Portal.webRoot+'/goodsBaseLabelController/deleteGoodsBaseLabel.do',{
	            	 idStr:ids.join(',')},function(result){
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
        	   	 if(records[i].shopId!=null){
            		 return club.toast('error',records[i].labelName+'：是总店数据，平台不能操作！');
            	 }
        		ids.push(records[i].id);
        	};
            var status;
            if(action=='active'){
            	status=1;
            }
            if(action=='disabled'){
            	status=0;
            }
            $.post(Portal.webRoot+'/goodsBaseLabelController/changeStatus.do',{
                idStr:ids.join(','),status:status},function(result){
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
        	var conditions = $('input[name=StoreLabel-name-conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/goodsBaseLabelController/selectGoodsBaseLabel.do',{
            	labelName:conditions,
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
                    name: 'id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                },{
                    name: 'shopId',
                    sorttype: "string",
                    hidden:true
                },{
                    name: 'labelName',
                    label: i18nLogin.BASE_LABEL_NAME,
                    width: 150,
                    align: "left"
                },{
                    name: 'orderBy',
                    width:90,
                    label: '排序',
                    align: "left"
                },{
                    name: 'status',
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