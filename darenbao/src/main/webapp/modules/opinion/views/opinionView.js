define([
    'text!modules/opinion/templates/opinionView.html',
    'text!modules/opinion/templates/showOpinion.html',
    'Portal',
], function (opinionViewTpl,showOpinionTpl,Portal) {
    var GRID_DOM = "#opinion-manager-content-div";
    return club.View.extend({
        template: club.compile(opinionViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .opinion-manager .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
        	$('.opinion-manager input[field=datetime]').datetimepicker({
                format: 'yyyy/mm/dd hh:ii:ss'
            });
            this._initListView();
        },
        //刷新Grid列表
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            switch(action){
            	case 'refresh': this.refresh();break;
                case 'search' : this.search();break;
                case 'show': this.show();break;
                case 'del': this.del();break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        show:function(){
        	var record = this.selectRecord();
        	if (record&&!record.id) {
                return club.toast('warn', '请选择记录！');
            };
            if (this.selectRecords().length>1){
                return club.toast('warn', '只能选择1条！');
            };
            popup = club.popup($.extend({}, {
		                height: $(window).height()*0.6,
		                modal: false,
		                draggable: false,
		                content: showOpinionTpl,
		                autoResizable: true
		            }, {
			            modal: true
			        })
		        );
            $('form[name=opinion-form] label[name=clientName]').html(record.clientName);
            $('form[name=opinion-form] label[name=versionName]').html(record.versionName);
            $('form[name=opinion-form] label[name=typeName]').html(record.typeName);
            $('form[name=opinion-form] label[name=clientPhone]').html(record.clientPhone);
            $('form[name=opinion-form] label[name=platformText]').html(record.platformText);
            $('form[name=opinion-form] label[name=createTime]').html(record.createTime);
            $('form[name=opinion-form] label[name=description]').html(record.description);
            $('.opinionWindow button').click(function(){
                popup.close();
            });
        },
        search:function(){
            this.pageData(1);
        },
        del:function(){
        	var records = this.selectRecords();
        	if (records.length==0) {
                return club.toast('warn', '请选择记录！');
            }
        	var me = this;
        	club.confirm("是否删除！").result.then(function(){
        		var ids = [];
                for (var i = records.length - 1; i >= 0; i--) {
                	ids.push(records[i].id);
                };
                $.post(Portal.webRoot+'/module/opinion/delete.do',{
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
        	$('input[name=iversionName]').val('');
        	$('select[name=iplatform]').val('');
        	$('select[name=iopinionType]').val('');
        	$('input[name=istartTime]').val('');
        	$('input[name=iendTime]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            var versionName = $('input[name=iversionName]').val();
            var opinionType = $('select[name=iopinionType]').val();
            var platform = $('select[name=iplatform]').val();
            var startTime = $('input[name=istartTime]').val();
            var endTime = $('input[name=iendTime]').val();
            var conditionStr={};
            if(versionName != undefined || versionName != ''){
            	conditionStr['versionName']=versionName;
            }if(opinionType != undefined || opinionType != ''){
            	conditionStr['opinionType']=opinionType;
            }if(platform != undefined || platform != ''){
            	conditionStr['platform']=platform;
            }if(startTime != undefined || startTime != ''){
            	conditionStr['startTime']=startTime;
            }if(endTime != undefined || endTime != ''){
            	conditionStr['endTime']=endTime;
            }
            $.post(Portal.webRoot+'/module/opinion/list.do',{
                conditionStr:JSON.stringify(conditionStr),start:(page-1)*rowNum,limit:rowNum},function(result){
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
                height:$(window).height()-175,
                width:this.$el.width(),
                colModel: [
						{name: 'id',sorttype: "long",hidden:true},
						{name: 'clientName',label: '反馈用户',order: 1,width: 100,align: "center",headertitle : '反馈用户'}, 
						{name: 'clientPhone',label: '反馈手机号',order: 2,width: 110,align: "center",headertitle : '反馈手机号'}, 
						{name: 'versionName',label: '反馈版本名',order: 3,width: 90,align: "center",headertitle : '反馈版本名'},
						{name: 'platformText',label: '平台',order: 4,width: 50,align: "center",headertitle : '平台'},						
						{name: 'typeName',label: '反馈类型',order: 5,width: 80,align: "center",headertitle : '反馈类型'},
						{name: 'descriptionText',label: '描述',order: 6,width: 160,align: "left",headertitle : '描述'},
						{name: 'createTime',label: '反馈时间',order: 7,width: 100,align: "center",headertitle : '反馈时间'}
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
