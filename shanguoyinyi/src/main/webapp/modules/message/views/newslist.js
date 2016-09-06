define([
    'text!modules/message/templates/newslist.html',
    'modules/message/detail/views/replyDetail',
    'Portal',
], function (newslistTpl,replyDetail,Portal) {
    var GRID_DOM = "#news-content-div";
    return club.View.extend({
        template: club.compile(newslistTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .message-manager .btn": "tbarHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
        	$('.message-manager input[field=datetime]').datetimepicker({
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
                case 'reply': this.reply(this.selectRecord());break;
                case 'operateReply': this.operateReply();break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        operateReply:function(){
            var me = this;
        	var records = this.selectRecords();
        	if (!records || records.length == 0) {
                return club.toast('warn', '请选择记录！');
            };
            var flag = false;
            for(var i = 0; i < records.length; i++){
            	var record = records[i];
            	if(record.replyStatus != '未回复'){
            		flag = true;
            	}            	
            }
            if(flag){
                return club.toast('warn', '存在不为未回复状态的数据，请重新选择！');
            }
            club.confirm("是否设置已回复状态？").result.then(function(){
            	var ids = [];
                for (var i = records.length - 1; i >= 0; i--) {
                	ids.push(records[i].replyId);
                };
                $.post(Portal.webRoot+'/message/news/replay.do',{replyIds:ids.join(',')},
        			function(result){
    					if(result.code == 1){
    						club.toast('info', '操作成功！');
    	                    me.pageData(1);
    					}else{
    		                return club.toast('error', result.msg);
    					}
    				}
            	);
        	},function(){
        		return;
        	});
        },
        reply:function(record){
        	if (!record || !record.id) {
                return club.toast('warn', '请选择记录！');
            };
            if (this.selectRecords().length>1){
                return club.toast('warn', '只能选择1条！');
            };
            replyDetail.reply(this,record);
        },
        search:function(){
            this.pageData(1);
        },
        refresh:function(){
        	$('input[name=iclientName]').val('');
        	$('select[name=ireplyStatus]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            var clientName = $('input[name=iclientName]').val();
            var replyStatus = $('select[name=ireplyStatus]').val();
            var conditionStr={};
            if(clientName != undefined && clientName != ''){
            	conditionStr['clientName']="%"+clientName+"%";
            }if(replyStatus != undefined && replyStatus != ''){
            	conditionStr['replyStatus']=replyStatus;
            }
            $.post(Portal.webRoot+'/message/news/list.do',{
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
                height:$(window).height()-145,
                width:this.$el.width(),
                colModel: [
						{name: 'id',sorttype: "long",hidden:true},
						{name: 'clientPic', label: '头像', width: 60, align: "center", headertitle : '头像', sortable: false,order: 1,
	                    	formatter: function(cellval, opts, rwdat, _act){
	                    		var value = '';
			                        if(rwdat.clientPic != undefined && rwdat.clientPic != null && rwdat.clientPic.length != 0){
				                        value = '<img src = "'+rwdat.clientPic+'" class="client-face-img"/>'
			                        }
		                        return value;
	                    	}
	                    },
						{name: 'clientName',label: '会员昵称',order: 2,width: 80,align: "center",headertitle : '会员昵称'}, 
						{name: 'storeName',label: '店铺名称',order: 3,width: 80,align: "center",headertitle : '店铺名称'},
						{name: 'replyContent',label: '消息内容',order: 4,width: 160,align: "left",headertitle : '消息内容'},
						{name: 'replyTime',label: '回复时间',order: 5,width: 80,align: "center",headertitle : '回复时间'},
						{name: 'replyStatus',label: '回复状态',order: 6,width: 80,align: "center",headertitle : '回复状态'}
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
