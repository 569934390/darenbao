define([
    'text!modules/message/templates/noticelist.html',
    'Portal',
], function (newslistTpl,Portal) {
    var GRID_DOM = "#notice-content-div";   
    
    return club.View.extend({
        template: club.compile(newslistTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click #notice-content-div .newsClick": "newsClick",
            "click #notice-content-div .noticeClick": "noticeClick"
        },
        _afterRender: function () {
            this._initListView();
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/message/notice/list.do',{start:(page-1)*rowNum,limit:rowNum},function(result){
	                result = Portal.convertPage(result);
	                result.page = page;
	                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        refrashNoticeData : function(messageId,status,contentStatus){
        	if(contentStatus == 0){
        		var me = this;
        		$.post(Portal.webRoot+'/message/notice/updateStatus.do',{messageId:messageId},function(result){
                    if(result.code == 0){
                    	club.toast('error', result.msg);
                    }else{
                    	me.pageData(1);
                    }
                	switch(status){
            		case '1' : $("#FUNC_SHIP_INDENT").click(); break;
            		case '2' : $("#FUNC_SHIP_INDENT").click();break;
            		case '3' : $("#FUNC_REFUND_INDENT").click(); break;
                	}
            	});
        	}else{
        		switch(status){
        		case '1' : $("#FUNC_SHIP_INDENT").click(); break;
        		case '2' : $("#FUNC_SHIP_INDENT").click();break;
        		case '3' : $("#FUNC_REFUND_INDENT").click(); break;
            	}
        	}
        	
        },
        newsClick : function (event) {
        	$("#FUNC_NEWS_LIST").click();
        },
        noticeClick : function (event) {
            var messageId = $(event.currentTarget).attr("messageId");
            var status = $(event.currentTarget).attr("status");
            var contentStatus = $(event.currentTarget).attr("contentStatus");
        	this.refrashNoticeData(messageId,status,contentStatus);
        },
        _initListView:function(){
            var me = this;
            //参数配置
            var opt = {
                height:$(window).height()-100,
                width:this.$el.width(),
                colModel: [
						{name: 'icon',label: '', width: 20, align: "center", sortable: false,order: 0,
							formatter: function(cellval, opts, rwdat, _act){
								var value = '<i class="glyphicon glyphicon-envelope"></i>';
	                    		if(rwdat.contentStatus == 1){
	                    			value = '<i class="glyphicon glyphicon-folder-open"></i>';
	                    		}
		                        return value;
							}
						}, 
						{name: 'content', label: '内容', width: 260, align: "center", headertitle : '内容', sortable: false,order: 1,
	                    	formatter: function(cellval, opts, rwdat, _act){
	                    		var value = "";
	                    		if(rwdat.contentStatus == 1){
	                    			value += '<span ';
	                    		}else{
	                    			value += '<a ';
	                    		}
	                    		if(rwdat.type == 1){
	                    			value += 'class="newsClick" style="cursor: pointer;">'+rwdat.content;
	                    		}else{
                    				value += 'class="noticeClick" messageId="'+rwdat.id+'" status="'+rwdat.status+'" contentStatus="'+rwdat.contentStatus+'" style="cursor: pointer;">'+rwdat.content;
	                    		}
	                    		if(rwdat.contentStatus == 1){
	                    			value += '</span>';
	                    		}else{
	                    			value += '</a>';
	                    		}
		                        return value;
	                    	}
	                    }, 
						{name: 'contentTime',label: '发送日期',order: 2,width: 100,align: "center",headertitle : '发送日期'},
						{name: 'contentStatus',label: '是否已读',order: 3,width: 80,align: "center",headertitle : '是否已读',
							formatter: function(cellval, opts, rwdat, _act){
	                    		var value = '否';
	                    		if(rwdat.contentStatus == 1){
	                    			value = '是';
	                    		}
		                        return value;
	                    	}
						}
	                    ]
            	,pager: true
                ,rowNum: 20
                ,rowList: [10,20,50,100,200]
                ,datatype: "json"
                ,recordtext:"{0} 到 {1} 总共: {2} "
                ,pgtext : " {0} / {1}"
                ,rowtext:"{0}"
                ,gotext:'跳转至第{0}页'
                ,pageData:this.pageData
            };
            this.pageData(1);
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
    });
});
