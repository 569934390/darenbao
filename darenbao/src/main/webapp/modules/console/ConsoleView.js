define([
    'text!modules/console/ConsoleView.html',
    'Portal'
], function (ConsoleView, Portal) {

	var page = {};
	
	var initContainer = function() {
    	page.container = $(".console-view-container");
    	page.operationContainer = page.container.find(".operation-container");
    	page.console = page.container.find(".console");
    	page.container.css({
    		"height": ($(window).height()-90)+"px"
        });
	}
	
	var initEvent = function() {
		page.operationContainer.on("click", "a[operation]", function(){
			var operation = $(this).attr("operation");
			switch(operation) {
			case "reload_authority": send("reload_authority", Portal.webRoot+"/security/common/reload.do"); return;
			}
			
		});
	}
	
	var send = function(type, url, params) {
		$.post(url, params, function(data){
			page.console.prepend("<div>"+type+": "+JSON.stringify(data)+"</div>")
		});
	}
	
	
    return club.View.extend({
        template: club.compile(ConsoleView),
//        i18nData: club.extend({}),
//        listView: {},
//        views: {},
//        events: {},

        _afterRender: function () {
        	initContainer();
        	initEvent();
        }
//        loadRoleData: function() {
//        	$.post(Portal.webRoot+'/security/role/getlist.do',{},function(result){
//        		page.roleMgrTable.grid('clearGridData');
//        		for (var i=0; i<result.data.length; i++)
//        			page.roleMgrTable.grid('addRowData', i + 1, result.data[i]);
//			});
//        }, 
    });
});
