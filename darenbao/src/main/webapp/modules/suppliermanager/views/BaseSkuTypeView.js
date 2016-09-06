define([
    'text!modules/suppliermanager/templates/BaseSkuTypeView.html',
    'modules/suppliermanager/addsupplierinfo/views/AddBaseSkuTypeWindow',
    'modules/suppliermanager/addsupplierinfo/views/AddBaseSkuItemWindow',
    'Portal'
], function (supplierinfoViewTpl,AddBaseSkuTypeWindow,AddBaseSkuItemWindow,Portal) {
	var GRID_DOM = "#baseSkuType-info-content-div";
	return club.View.extend({
        template: club.compile(supplierinfoViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .supplier-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            var id = $(event.currentTarget).attr("id");	//获取rowId
            var rowValue = $(GRID_DOM).jqGrid('getRowData',id);	//根据rowId获取当前行
            switch(action){
                case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
                case 'edit': selectRecord = this.selectRecord();
                case 'add': this.add(action,selectRecord);break;
                case 'del': this.doAction(action,this.selectRecords());break;
                case 'sku-item': this.doSkuItem(action, rowValue);
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
             $('input[name=supplier-conditions]').val('');
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
             AddBaseSkuTypeWindow.openAddWin({
                 callback:this
             },action,record);
        },
        //删除
        doAction:function(action,records){
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
             var t = club.confirm('您确定要删除所选规格吗？');
             t.result.then(function resolve(){
            	 $.post(Portal.webRoot+'/cargoBaseSkuTypeController/deleteCargoBaseSkuType.do',{
            		 idStr:ids.join(',')},function(result){
            			 club.toast('info', '操作成功！');
            			 me.refresh();
            		 });            	 
             }, function reject(){
	    		 return;
	    	 });
        },
        //规格项
        doSkuItem:function(action,record){
        	$.post(Portal.webRoot+'/cargoBaseSkuItemController/selectSkuItemBySkuTypeId.do',{
                id:record.id,type:record.type},function(result){
                	AddBaseSkuItemWindow.openAddWin({
                        callback:this
                    }, action, record, result);
//                me.refresh();
            });
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var skuName = $('input[name=supplier-conditions]').val();
            var skuType = $('select[name=skuType-conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/cargoBaseSkuTypeController/selectCargoBaseSkuType.do',{
                	'skuName':skuName, 'skuType': skuType,
                start:(page-1)*rowNum,limit:page*rowNum}, function(result){
//                console.log(result);
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
                    sorttype: 'int',
                    hidden:true,
                    key:true
                }, {
                    name: 'type',
                    hidden:true,
                    label: '规格类型值'
                }, {
                    name: 'typeName',
                    width:90,
                    label: '规格类型'
                }, {
                    name: 'name',
                    label: '规格名称',
                    width: 150,
                    align: "left"
                },  {
                     label: '操作',
                     width: 100,
                     align: "left",
                     formatter:function(cellval, opts, rwdat, _act){
                         return '<a href="javaScript:void(0)" id= '+opts.rowId+ ' class="btn" action="sku-item">规格选项列表</a> ';
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