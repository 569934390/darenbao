function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
    	return unescape(r[2]); 
    return null;
}
define(['app/dock','app/toolbar','app/tabpanel'],function (dock,toolbar,tab) {
	if(getQueryString('fullscreen')){
		var MainWin = window.open(webRoot+'portal/portalView.jsp','门户首页',' left=0,top=0,width='+ (screen.availWidth) +',height='+ (screen.availHeight) +',scrollbars,resizable=yes,toolbar=no');
		MainWin.focus();
		self.blur();
		window.opener = 'xxx';
		window.open('','_parent','');
	}
	else{
		$(window).on('resize',function(){
			constans.width = $(document.body).width();
			constans.height = $(document.body).height();
			$('.mainPanel').css({
				width:constans.width-20,
				height:constans.height-45
			});
		});
	}
	constans.width = $(document.body).width();
	constans.height = $(document.body).height();
	$.post(webRoot+'tree/getRecTree.do',{paramMap:JSON.stringify({
		sqlKey:'DopMenu.getLevelDockList',
		rootId:-1,
		valueField:'id',
		displayField:'url'
	})},function(taskMenus){
		constans.taskMenus = [];
		taskMenus = taskMenus.children;
		for(var i=0;i<taskMenus.length;i++){
			constans.taskMenus.push(taskMenus[i].attributeMap);
			if(taskMenus[i].children){
				constans.taskMenus[constans.taskMenus.length-1].mores=[];
				for(var j=0;j<taskMenus[i].children.length;j++){
					constans.taskMenus[constans.taskMenus.length-1].mores.push(taskMenus[i].children[j].attributeMap);
				}
			}
		}
		toolbar.init();
	    dock.init({
			items:constans.taskMenus,
			range:2,
			renderTo:document.body
		});
		require(['app/contextmenus']);
		require(['app/drop']);
		
		var id = $.state.get('lastOpenId')||0,index=1;
		for(var i=0;i<constans.taskMenus.length;i++){
			if(constans.taskMenus[i].id==id){
				index=i;
			}
		}
	    tab.openUrl(constans.taskMenus[index].id,constans.taskMenus[index].url);
	});
});