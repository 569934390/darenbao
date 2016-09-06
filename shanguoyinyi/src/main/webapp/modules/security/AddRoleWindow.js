define([
    'text!modules/security/AddRoleWindow.html',
    'Portal'
], function(AddRoleWindow, Portal) {
	var options = {
		width: $(window).width()*0.3, 
        modal: true,
        draggable: false,
        content: AddRoleWindow,
        autoResizable: true
    };
    var popup;
    
    var popWin = {};
    var init = function(securityView){
    	popWin.main = $(".add-role-window");
    	popWin.body = $(".add-role-window > .modal-body");
    	popWin.title = $(".add-role-window > .modal-header > .modal-title");
    	popWin.footer = $(".add-role-window > .modal-footer");
    	popWin.commit = $(".add-role-window > .modal-footer > .commit");
    	popWin.cancel = $(".add-role-window > .modal-footer > .cancel");
    	popWin.securityView = securityView;
    }
    
    var addEvents = function() {
    	popWin.commit.click(function(){
    		commit();
    		popup.close();
    	});
    	popWin.cancel.click(function(){
    		popup.close();
    	});
    }
    
    var commit = function() {
    	var name = $(".add-role-window input[name='name']").val();
    	if(!name || name.trim()=='') {
    		club.toast("warning", "名称不能为空");
    		return;
    	}
    	var note = $(".add-role-window input[name='note']").val();
    	if(!note || note.trim()=='') {
    		club.toast("warning", "备注不能为空");
    		return;
    	}
        $.post(Portal.webRoot+'/security/role/save.do',{
        	id: 0, 
        	name: name, 
        	note: note
        },function(result){
    		club.toast("info", result.msg);
        	popWin.securityView.loadRoleData();
        });
    }
    
	return {
		open: function(securityView){
			popup = club.popup(options);
			init(securityView);
			addEvents();
		}
	}
});