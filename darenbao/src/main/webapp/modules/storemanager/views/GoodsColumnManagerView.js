define([
    'text!modules/storemanager/templates/GoodsColumnManagerView.html',
    'modules/storemanager/addGoodsColumn/views/AddGoodsColumnWindow',
    'Portal'
], function (StoreColumnManagerViewTpl,AddStoreColumnWindow,Portal) {
	var GRID_DOM = "#StoreColumn-info-content-div";
	return club.View.extend({
        template: club.compile(StoreColumnManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .StoreColumn-info-main-btngroup .btn": "tbarHandler"
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
             $('input[name=StoreColumn-name-conditions]').val('');
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
            if (action=='edit'&&record.columnName == '卡券') {
                return club.toast('warn', '卡券不能编辑！');
            };
            AddStoreColumnWindow.openAddWin({
                callback:this
            },action,record);
        },
        //删除
        doDelete:function(action,records){
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
            	 ids.push(records[i].id+";"+records[i].valueId);
             };
             var t = club.confirm('您确定要删除所选标签吗？');
             t.result.then(function resolve(){
            	 $.post(Portal.webRoot+'/goodsColumnController/deleteGoodsColumn.do',{
            		 idStr:ids.join(',')},function(result){
            			 if (result.success) {
            				 club.toast('info', '操作成功！');
            			 } else{
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
        		if (records[i].columnName == '卡券') {
                    return club.toast('warn', '卡券不能禁用！');
                };
        		ids.push(records[i].id);
        	};
            var status;
            if(action=='active'){
            	status=1;
            }
            if(action=='disabled'){
            	status=0;
            }
            $.post(Portal.webRoot+'/goodsColumnController/updateStatusForGoodsColumnById.do',{
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
        	var conditions = $('input[name=StoreColumn-name-conditions]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/goodsColumnController/selectGoodsColumn.do',{
            	columnName:conditions,
                start:(page-1)*rowNum,limit:page*rowNum},function(result){
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
                }, {
                    name: 'ruleId',
                    label: '对应规则类型id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                	name: "valueId",
                    label: '规则值id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'ruleVal',
                    label: '规则值',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                	name: "valueId2",
                    label: '规则值id2',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'ruleVal2',
                    label: '规则值2',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'sourceId',
                    label: '规则类型id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'ruleNumber',
                    label: '规则来源key',
                    sorttype: "int",
                    hidden:true,
                    key:true
                }, {
                	name: "showpictureId",
                    label: '展示图id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'showpicture',
                    label: '展示图',
                    width: 150,
                    align: "left",
                    formatter:function(cellval,opts,rwdat,_act){
                        var imagUrl =  "./image/user5-128x128.jpg";
                        if (rwdat.showpicture) {
                            imagUrl = rwdat.showpicture;
                        };
                        return '<img src = "'+imagUrl+'" class="client-face-img"/> ';
                    }
                }, {
                    name: 'columnName',
                    label: '栏目名称',
                    width: 150,
                    align: "left"
                }, {
                    name: 'ruleSourceId',
                    label: '规则来源',
                    hidden:true,
                    key:true
                }, {
                    name: 'ruleName',
                    label: '规则来源名称',
                    width: 90,
                    align: "left"
                }, {
                    name: 'orderBy',
                    width:90,
                    label: '排序',
                    align: "left"
                }, {
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