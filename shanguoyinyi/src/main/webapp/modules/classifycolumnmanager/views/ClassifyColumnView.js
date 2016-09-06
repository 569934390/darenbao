define([
    'text!modules/classifycolumnmanager/templates/ClassifyColumnView.html',
    'modules/classifycolumnmanager/addclassifycolumn/views/AddClassifyColumnWindow',
    'Portal'
], function (cargoClassifyViewTpl,AddCargoClassifyWindow,Portal) {
    var GRID_DOM = "#classify-column-content-div";
    return club.View.extend({
        template: club.compile(cargoClassifyViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .classify-column-main-btngroup .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            switch(action){
                case 'refresh': this.refresh();break;
                case 'edit':
                case 'add': this.add(action,this.selectRecord());break;
                case 'del': this.del(this.selectRecords());break;
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
            AddCargoClassifyWindow.openAddWin({
                callback:this
            },action,record);
        },
        del:function(records){
        	if (records.length==0) {
                var record = this.selectRecord();
                if (record&&record.id) {
                    records.push(record);
                };
            };
            if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
        	var me = this;
        	club.confirm("是否删除！").result.then(function(){
        		var ids = [];
                for (var i = records.length - 1; i >= 0; i--) {
                	ids.push(records[i].id);
                };
                $.post(Portal.webRoot+'/stock/classifyColumn/delete.do',{
                    ids:ids.join(',')},function(result){
                    	if(result.code == 1){
                    		club.toast('info', '操作成功！');
                            me.refresh();
                    	}else{
                    		club.toast('error', result.msg);
                    	}                    
                });
        	},function(){
        		return;
        	});
        },
        refresh:function(){
            console.log("刷新Grid列表");
            this.pageData(1);
        },
        pageData :function(page, rowNum, sortname, sortorder) {
            $.post(Portal.webRoot+'/stock/classifyColumn/list.do',{},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $("#classify-column-content-div").grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
//            参数配置
            var opt = {
        		height:$(window).height()-125
                ,width:this.$el.width()
                ,colModel: [
                    {name: 'id',sorttype: "long",label: 'Inv No',hidden:true,key:true},
                    {name: 'imgUrl', label: '缩略图', width: 60, align: "center", headertitle : '缩略图', sortable: false,order: 1,  //列头的提示
                    	formatter: function(cellval, opts, rwdat, _act){
                    		var value = '';
		                        if(rwdat.imgUrl != undefined && rwdat.imgUrl != null && rwdat.imgUrl.length != 0){
			                        value = '<img src = "'+rwdat.imgUrl+'" class="client-face-img"/>'
		                        }
	                        return value;
                    	}
                    }, 
                    {name: 'classifyName',label: '关联分类名称',order: 2,width: 200,align: "center",headertitle : '关联分类名称'}, 
                	{name: 'orderIndex',label: '展示排序',sorttype:"int",order: 3,width: 80,align: "center",headertitle : '展示排序'}, 
                    {name: 'statusText',label: '分类状态',order: 4,width: 100,align: "center",headertitle : '分类状态'},
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
//            加载grid
            $grid = $("#classify-column-content-div").grid(opt);       
        }
    });
});
