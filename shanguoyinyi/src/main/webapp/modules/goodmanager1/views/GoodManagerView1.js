define([
    'text!modules/goodmanager1/templates/goodView.html',
    'modules/goodmanager1/views/GoodInfoWindow',
    'modules/goodmanager1/addgood/views/SelectCargo',
    'modules/goodmanager1/addgood/views/UpGood',
    'modules/goodmanager1/addgood/views/EditGoodWindow',
    'modules/goodmanager1/addgood/views/EvaluateWindow',
    'modules/goodmanager1/addgood/views/EvaluateDetailWindow',
    'modules/cargomanager/views/ClassifySelect',
    'modules/cargomanager/views/BrandSelect',
    'Portal'
], function (goodViewTpl,GoodInfoWindow,SelectCargo,UpGood,EditGoodWindow,EvaluateWindow,EvaluateDetailWindow,classifySelectFile, brandSelectFile,Portal) {
	var GRID_DOM = "#good-info-content-div";
	var classifySelect;
    var brandSelect;
	var goodRecords = {};
	
	var pageNum;
	return club.View.extend({
        template: club.compile(goodViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .good-info-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
            this.getColumns();
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            console.info('12344');
            switch(action){
                case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
                case 'edit': this.edit(action,this.selectRecord());break;
                case 'add': this.add(action,this.selectRecord());break;
                case 'del': this.doDelete(action,this.selectRecords());break;
                case 'up':this.upGood(action,this.selectRecords());break;
                case 'down':this.downGood(action,this.selectRecords());break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        search:function(){
        	if(pageNum!=null){
        		this.pageData(pageNum);
        	}else{
        		this.pageData(1);
        	}
        	 
        },
        refresh:function(){
        	 console.log("刷新Grid列表");
             $('input[name=good-conditions]').val('');
             $('input[name=good-conditions1]').val('');
             $('select[name=status-conditions] option:first').attr('selected','selected');
             var goodColumnId = $('select[name=goodColumnId]').val();
             this.pageData(1);
        },
        //新增
        add:function(action,record){
        	console.log(record);
            SelectCargo.openAddwin({
                callback:this
            },action,record);
        },
        //编辑
        edit:function(action,record){
            if (action=='edit'&&this.selectRecords().length>0 ){
                if (this.selectRecords().length>1) {
                    return club.toast('warn', '有歧义，不能同时选择！');
                };
            };
            console.log(record);
            if (action=='edit'&&record&&!record.tradeGoodId) {
                return club.toast('warn', '请选择记录！');
            };
            EditGoodWindow.openAddWin({
                callback:this
            },action,record);
        },
        //删除
        doDelete:function(action,records){
             if (this.selectRecords().length==0) {
                 return club.toast('warn', '请选择商品记录！');
             }
             if (records.length>0) {
            	 var i=0;
            	 var flag=false;
            	 console.log(records[i].status);
            	 if(records.length==1){
            		 if(records[0].status==1){
            			 return club.toast('warn', '已上架的商品不可删除！');
            		 }
            	 }else{
            		 while(i<records.length-1){
                		 if(records[i].status==1){
                    		 flag=true; 
                    		 break;
                		 }
                		 ++i;
                	 } 
                	 if(flag){
                		 return club.toast('warn', '已上架的商品不可删除！');
                	 }   
            	 }       	        	 
             }
             var me = this,ids = [];
             for (var i = records.length - 1; i >= 0; i--) {
            	 ids.push(records[i].tradeGoodId);
             };
             
             var r= club.confirm('您确定要删除选中的商品吗？');
	       	 r.result.then(function resolve(){
	       		$.post(Portal.webRoot+'/good/deletegood.do',{
	                 IdStr:ids.join(',')},function(result){
	                 club.toast('info', '操作成功！');
	                 me.refresh();
	             });
	       	 }, function reject(){
	    		 return;
	    	 });
             
             
        },
      //上架
        upGood:function(action,record){
            if (action=='up'&&this.selectRecords().length>1 ){
            	return club.toast('warn', '不能同时选择多个商品！');
            };
            console.info(record);
            if (action=='up'&&this.selectRecords().length==0) {
                return club.toast('warn', '请选择商品记录！');
            };
            if(action=='up'&&record[0].status==1){
            	return club.toast('warn', '该商品已经上架！');
            }
            UpGood.openAddWin({
                callback:this
            },action,record);
        },
        //下架
        downGood:function(action,record){
        	var me=this;
            if (action=='down'&&this.selectRecords().length==0 ){
            	return club.toast('warn', '请选择商品记录！');
            };
            console.info(record);
            var ids=[];
            var ifDown=false;
            for (var i = record.length - 1; i >= 0; i--) {
           	 ids.push(record[i].tradeGoodId);
           	 if(record[i].status==0){
           		ifDown=true;
           	 }
            };
            if (action=='down'&&ifDown==true){
            	return club.toast('warn', '所选商品中已经有部分商品下架，请重新选择!');
            };
            
            var t= club.confirm('您确定要下架商品吗？');
	       	 t.result.then(function resolve(){
	       		$.post(Portal.webRoot+'/good/downGoodSku.do',{ids:JSON.stringify(ids)},function(result){
	          		 if(result.success){
	          			me.refresh();
	          			club.toast('info', "下架成功");
	          		 }else{
	          			 club.toast('error', result.msg);
	          		 }
	             });
	       	 }, function reject(){
	    		 return;
	    	 });
                
        },
        
        getColumns:function(){
        	var optionStr="";
        	$.post(Portal.webRoot+'/good/getGoodColumnList.do',{},function(result){
               if(result !=null){
           		  for(var m =0;m<result.length;m++){
           			optionStr+="<option value='"+result[m].id+"'>"+result[m].columnName+"</option>";	
           		  }
                 }
           		
        	$(".goodColumnId").append(optionStr);
        })
        },
        
        pageData:function (page, rowNum, sortname, sortorder) {    	
        	
            var conditions = $('input[name=good-conditions]').val();
            var conditions1 = $('input[name=good-conditions1]').val();
            var statusConditions = $('select[name=status-conditions]').val();
            var goodColumnId = $('select[name=goodColumnId]').val();
            var cargoClassifyId = classifySelect.get()||"-1";
            var cargoBrandId=brandSelect.get()||"-1";
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
           $.post(Portal.webRoot+'/good/goodPage.do',{
                conditionStr:JSON.stringify({
                    'conditions':'%'+conditions+'%','conditions1':'%'+conditions1+'%','statusConditions':statusConditions,'goodColumnId':goodColumnId,
                    'cargoClassifyId':cargoClassifyId,'cargoBrandId':cargoBrandId}),
                start:(page-1)*rowNum,limit:rowNum},function(result){
                console.log(result);
                result = Portal.convertPage(result);
                result.page = page;
                pageNum=page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	//参数配置
        	classifySelect = classifySelectFile.init(".cargo-manager-classify-group", "info");
            brandSelect = brandSelectFile.init(".cargo-manager-btn-brand-select", "info");
            var opt = {
                height:$(window).height()-160,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'tradeGoodId',
                    label: '商品编号',
                    width: 100,
                    align: "center",
                    key:true
                },
                {
                    name: 'post',
                    hidden:true
                },
                {
                    name: 'name',
                    label: '商品名称',
                    width: 80,
                    align: "center"
                }, {
                    name: 'cargoBrand',
                    label: '品牌',
                    width: 80,
                    align: "center",
                    title: false //内容没有提示
                }, {
                    name: 'cargoType',
                    width: 80,
                    align: "center",
                    label: '分类'
                }, {
                    name: 'columnName',
                    width: 80,
                    align: "center",
                    label: '商品栏目'
                }, {
                    name: 'firstMarketPrice',
                    width: 60,
                    align: "center",
                    label: '原价'
                },

                {
                    name: 'firstSalePrice',
                    width: 60,
                    align: "center",
                    label: '现价'
                },
                   {name: 'createTime',
                    width: 80,
                    align: "center",
                    label: '更新时间'
                },
                {
                    name: 'sort',
                    width: 60,
                    align: "center",
                    label: '排序'
                },
                {
                    name: 'statusName',
                    width: 60,
                    align: "center",
                    label: '状态'
                },
                {name: 'state', label: '操作', width: 180, align: "center", sortable: false, 
                    formatter: function(cellval, opts, rwdat, _act) {
                    	var detail = ' <a href="javascript:void(0);" good-data-operation="detail" data-value="'+rwdat.tradeGoodId+'" title="查看明细">商品明细</a> ';
                    	var evaluate = ' <a href="javascript:void(0);" good-data-operation="evaluate" data-value="'+rwdat.tradeGoodId+'" title="编辑">商品评价</a> ';
                    	var evaluateDetail = ' <a href="javascript:void(0);" good-data-operation="evaluateDetail" data-value="'+rwdat.tradeGoodId+'" title="编辑">评价详情</a> ';
                    	goodRecords[rwdat.tradeGoodId] = rwdat;
                    	return detail+evaluate+evaluateDetail;
                    }
                }              
                
                ],pager: true
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
            this._init_operations();
        },
        _init_operations: function(){
        	var me = this;
        	$(document).on("click", "a[good-data-operation]", function(){
        		var operation = $(this).attr("good-data-operation");
        		switch(operation){
        		case "detail": me._open_good_info_window(goodRecords[$(this).attr("data-value")], true);
        		console.log(goodRecords[$(this).attr("data-value")].cargoId);
        		break;
        		case "evaluate":me. _open_evaluate_window(goodRecords[$(this).attr("data-value")]);
        		console.log(goodRecords[$(this).attr("data-value")].cargoId);
        		break;
        		case "evaluateDetail":me. _open_evaluateDetail_window(goodRecords[$(this).attr("data-value")]);
        		console.log(goodRecords[$(this).attr("data-value")].cargoId);
        		break;
        		}
        	});
        }, 
        _open_good_info_window: function(Record, readOnly) {
        	if(readOnly !== true)
        		readOnly = false;
        	GoodInfoWindow.open({
                callback:this
            }, Record, readOnly);
        },
        _open_evaluate_window: function(Record) {
        	
        	EvaluateWindow.openAddWin({
                callback:this
            }, Record);
        },
        _open_evaluateDetail_window: function(Record) {
        	console.log(Record);
        	EvaluateDetailWindow.openAddWin({
                callback:this
            }, Record);
        }
 	});
});