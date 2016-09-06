define([
    'text!modules/storemanager/templates/SubbranchManagerView.html',
    'tableInfo!store_subbranch',
    'modules/storemanager/addSubbranch/views/AddSubbranchWindow',
    'Portal',
    'text!data/cityData.json',
    'modules/storemanager/addSubbranch/views/SubbranchLevelSelect',
    'modules/storemanager/detailSubbranch/views/DetailSubbranchWindow'
], function (SubbranchManagerViewpl,SubbranchTalbeInfo,AddSubbranchWindow,Portal,regionData,subbranchLevelSelect,DetailSubbranchWindow) {
	 var GRID_DOM = "#subbranch-manager-content-div";
	var regionData = JSON.parse(regionData),provice={},city={},region={};
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
   
    return club.View.extend({
        template: club.compile(SubbranchManagerViewpl),
        i18nData: club.extend({}),

        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events: {
            "click .subbranch-manager-main-btngroup .btn": "tbarHandler"
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
                case 'del': this.delet(this.selectRecords());break;
                case 'active':
                case 'disabled':this.doAction(action,this.selectRecords());break;
                case 'details':this.details(action,$(event.currentTarget).attr("id"));break;
            }
        },
        selectRecord:function(){
        	
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        add:function(action,record){
        	
            if (action=='edit'&&this.selectRecords().length>1 ){
               
                    return club.toast('warn', '有歧义，不能同时选择！');
                
            };
            if (action=='edit'&&this.selectRecords().length==0) {
                return club.toast('warn', '请选择记录！');
            };
            AddSubbranchWindow.openAddWin({
                callback:this
            },action,this.selectRecords()[0],regionData);
        },
        convertState:function(action){
            if (action=='disabled') {return '1'}
            else if (action=='active') {return '0'}
        },
        delet:function(records){
            console.log(records);
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
            $.post(Portal.webRoot+'/Subbranch/deleteSubbranch.do',{
            	subbranchIds:ids.join(',')},function(result){
	        		if(result.success){
	        			club.toast('info', '操作成功！');
	        		}else{
	        			club.toast('error', result.msg);
	        		}
	                me.refresh();
            });
        },
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
            $.post(Portal.webRoot+'/Subbranch/updateSubbranchState.do',{
            	SubbranchIds:ids.join(','),action:this.convertState(action)},function(result){
            		if(result.success){
	        			club.toast('info', '操作成功！');
	        		}else{
	        			club.toast('error', result.msg);
	        		}
                me.refresh();
            });
        },
        refresh:function(){
            console.log("刷新Grid列表");
            $("#SubbranchloginName").val('');
            $("#SubbranchCondition").val('');
            this.pageData(1);
        }, details:function(action,id){
        	var rowValue = $(GRID_DOM).grid('getRowData',id);
        	console.log(rowValue);
        	DetailSubbranchWindow.openAddWin({
                callback:this
            },action,rowValue);
        },
		pageData : function(page, rowNum, sortname, sortorder) {
							rowNum = rowNum
									|| $(GRID_DOM).grid("getGridParam",
											"rowNum");
							$.post(Portal.webRoot+ '/Subbranch/getSubbranchList.do',
							{
								start : (page - 1) * rowNum,
								limit : page * rowNum,
								conditionStr : JSON.stringify({
									'loginName' : $("#SubbranchloginName").val(),
									'Condition' : $("#SubbranchCondition").val()
								})
							}, function(result) {
								result = Portal.convertPage(result);
								result.page = page;
								$(GRID_DOM).grid("reloadData", result);
							});
							return false;
		},
		search:function(){
	            console.log("刷新Grid列表");
	            this.pageData(1);
	     },
        _initListView:function(){

        	 var opt = {
                     height:$(window).height()-125,
                     width:this.$el.width(),
                     rownumbers:true,
                     colModel: [{
                         name: 'id',
                         sorttype: "int",
                         hidden:true,
                         key:true
                     },{
                    	 name:'headImgUrl',
                    	 label:'店铺图标',
                    	 width:20,
                    	 formatter:function(val){
                             var imagUrl =  "./image/user4-128x128.jpg";
                             if (val) {
                                 imagUrl = val;
                             };
                             return '<img src = "'+imagUrl+'" class="client-face-img"/> ';
                         },
                     }, {
                         name: 'storeId',
                         sorttype: "int",
                         hidden:true
                     },{
                         name: 'createBy',
                         hidden:true
                     }, {
                         name: 'name',
                         width:20,
                         label: '店铺名称',
                         align: "center"
                     }, {
                         name: 'number',
                         width:20,
                         label: '店铺编号',
                         align: "center"
                     },{
                         name: 'userName',
                         width:20,
                         label: '茶掌柜',
                         align: "center"
                     }, {   name: 'mobile',
                         label: '手机账号',
                         width: 30,
                         align: "center",
                     }, {   name: 'phone',
                         label: '门店电话',
                         width: 30,
                         align: "center",
                     }, {   name: 'departmentNum',
                         label: '部门编号',
                         width: 30,
                         align: "center",
                     },  {
                         name: 'levelName',
                         label: '等级',
                         width: 15,
                         align: "center",
                     },  {
                         width:80,
                         align: "center",
                         label: '门店地址',
                         formatter:function(cellval, opts, rwdat, _act){
                             if (!provice[rwdat.province]||!city[rwdat.city]) {
                                 return '未知';
                             };
                             var area = provice[rwdat.province].name+' '+city[rwdat.city].name;
                             if(region[rwdat.country]&&region[rwdat.country].name){
                                 area+=' ' + region[rwdat.country].name;
                             }
                             return "<p>"+area+"</p><p>"+rwdat.address+"</p>";
                         }
                     },{
                         name: 'state',
                         label: '禁用状态',
                         width: 30,
                         align: "center",
                         title: false, //内容没有提示
                         formatter: function(state) {
                             if (state=='1') {return '是'};
                             if (state=='0') {return '否'};
                             return state;
                         }
                     },{
                    	  width:40,
                          label: '操作',
                          align: "center",
                          formatter:function(cellval, opts, rwdat, _act){
                          	var value='<a href="javaScript:void(0)" id= "'+opts.rowId+'" class="btn" action="details">查看详情</a><br>';
                           return value;
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
