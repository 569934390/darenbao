define([
	'text!modules/eventmanager/queryactivitycomment/templates/queryActivityCommentView.html',
    'Portal'
], function(queryActivityUserView,Portal) {
	var ACTIVITY_LISTVIEW="#query-activity-comment-content-div";
	var options = {
	    height: $(window).height()*0.9,
        modal: false,
        draggable: true,
        content: queryActivityUserView,
        autoResizable: true
    };
    var popup;
    
    var activityId;

	return {
		openAddWin:function(listView,action,id){
			
            popup = club.popup($.extend({}, options, {
                modal: true
            }))
            activityId=id;
            this.initListView();
            
            $('.queryActivityCommentView button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'cancel-button': popup.close();break;
                }
            });
		},
		initListView:function(){
	    	//参数配置
	        var opt = {
	            height:$(window).height()*0.9-125,
	            width:'100%',
	            rownumbers:true,
	            colModel: [{
	                name: 'id',
	                sorttype: "string",
	                hidden:true,
	                key:true
	            },{
	                name: 'headimgurl',
	                width:60,
	                label: '头像',
	                align: "center",
	                formatter:function(val){
	                	var imagUrl =  "./image/user4-128x128.jpg";
                        if (val) {
                            imagUrl = val;
                        };
                        return '<img src = "'+imagUrl+'" class="client-face-img"/> ';
	                },
	                title: false //内容没有提示
	            },{
	                name: 'nickname',
	                width:90,
	                label: '昵称',
	                align: "left"
	            },{
	                name: 'content',
	                width:270,
	                label: '内容',
	                align: "left"
	            }],pager: true
	            ,rowNum: 20
	            ,rowList: [10,20,50,100,200]
	            ,datatype: "json"
	            ,recordtext:"{0} 到 {1} 总共: {2} "
	            ,pgtext : " {0} / {1}"
	            ,rowtext:"{0}"
	            ,gotext:'跳转至第{0}页'
	            ,multiselect:false
	            ,pageData:this.pageData
	        };
	        this.pageData(1);
	        //加载grid
	        $grid = $(ACTIVITY_LISTVIEW).grid(opt);
	        $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
	    },
	    pageData:function(page, rowNum, sortname, sortorder) {
	        rowNum = rowNum || $(ACTIVITY_LISTVIEW).grid("getGridParam", "rowNum");
	        $.post(Portal.webRoot+'/event/eventActivityComment/eventActivityCommentPage.do',{
	            conditionStr:JSON.stringify({'eventActivityId':activityId})
	            ,start:(page-1)*rowNum,limit:rowNum},function(result){
	            result = Portal.convertPage(result);
	            result.page = page;
	            $(ACTIVITY_LISTVIEW).grid("reloadData", result);
	        });
	        return false;
	    }
	};
});