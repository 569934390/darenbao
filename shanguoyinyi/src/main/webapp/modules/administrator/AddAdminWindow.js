define([
    'text!modules/administrator/AddAdminWindow.html',
    'Portal'
], function(AddAdminWindow, Portal) {

	var popup;
    var popWin = {};
    
    var init = function(managerView){
    	popWin.main = $(".add-admin-window");
    	popWin.body = $(".add-admin-window > .modal-body");
    	popWin.title = $(".add-admin-window > .modal-header > .modal-title");
    	
    	popWin.roleList = $(".add-admin-window > .modal-body .role-list");
    	
    	popWin.footer = $(".add-admin-window > .modal-footer");
    	popWin.commit = $(".add-admin-window > .modal-footer > .commit");
    	popWin.cancel = $(".add-admin-window > .modal-footer > .cancel");
    	popWin.managerView = managerView;

    	popWin.body.find("input[name='a_loginName']").val("");
//    	popWin.body.find(".password-control").removeClass("hidden");
    	popWin.body.find("input[name='staffName']").val("");
    }
    var initRoleData = function(data) {
    	popWin.isEdit = false;
    	if(!data)
    		return;
    	popWin.data = data;
    	popWin.isEdit = true;
    	popWin.title.html("编辑管理员信息");
    	popWin.body.find("input[name='a_loginName']").val(data.loginName);
    	popWin.body.find("input[name='staffName']").val(data.staffName);
    	var roleIds = data.roleIds.split(",");
    	for(var i=0; i<roleIds.length; i++)
    		if(roleIds[i])
		    	popWin.roleList.find("input[type='checkbox'][role-id='"+roleIds[i]+"']").prop("checked", true);
    	if(data.staffId==1)
    		popWin.roleList.find("input[type='checkbox'][role-id]").prop("disabled", true);
    }
    
    var initRoleList = function(data) {
    	$.post(Portal.webRoot+'/security/role/getlist.do',{},function(result){
    		popWin.roleList.html("");
    		for (var i=0; i<result.data.length; i++) {
    			var role = result.data[i];
    			var li = $("<li ></li>");
    			var input = $("<input type='checkbox' role-id='"+role.id+"'/>")
    			var a = $("<a href='javascript:void(0);' role-id='"+role.id+"' >"+role.note+"</a>");
    			li.append(input);
    			li.append(a);
    			popWin.roleList.append(li);
    		}

			initRoleData(data);
		});
    }
    
    var addEvents = function() {
    	popWin.commit.click(function(){
    		commit();
    		popup.close();
    	});
    	popWin.cancel.click(function(){
    		popup.close();
    	});
    	popWin.roleList.on("click", "a[role-id]", function(){
    		$("input[type='checkbox'][role-id='"+$(this).attr("role-id")+"']").click();
    	});
    	
    	popWin.body.find("[name='a_password']").on("keyup", function(e){
    		if(/[\u4e00-\u9fa5]+/.test($(this).val()))
    			$(this).val($(this).val().replace(/[\u4e00-\u9fa5]+/, ''));
    	})
    }
    
    var commit = function() {
    	var loginName = popWin.body.find("input[name='a_loginName']").val();
    	if(!loginName || loginName.trim()=='') {
    		club.toast("warning", "登录名不能为空");
    		return;
    	}
    	var password = popWin.body.find("input[name='a_password']").val();
    	if(!popWin.isEdit && (!password || password.trim()=='')) {
    		club.toast("warning", "登录密码不能为空");
    		return;
    	}
    	var staffName = popWin.body.find("input[name='staffName']").val();
    	if(!staffName)
    		staffName = '';
    	var roles = popWin.roleList.find("input[type='checkbox'][role-id]:checked");
    	var roleList = [];
    	for(var i=0; i<roles.length; i++)
    		roleList.push($(roles[i]).attr("role-id"));
    	
    	var data = {
        	loginName: loginName, 
        	staffName: staffName, 
        	roles: roleList.join(",")
        }
    	if(popWin.isEdit)
    		data.staffId = popWin.data.staffId;
    	if(!popWin.isEdit || password)
    		data.password = $.md5(password);
    	
        $.post(Portal.webRoot+'/staffManager/save.do',{params: JSON.stringify(data)},function(result){
        	popWin.managerView.pageData(1);
        	popup.close();
        });
    }
    
	return {
		open: function(managerView, data){
			popup = club.popup({
				width: $(window).width()*0.5, 
				height: $(window).height()*0.8, 
		        modal: true,
		        draggable: false,
		        content: AddAdminWindow,
		        autoResizable: true
		    });
			init(managerView);
			initRoleList(data);
			addEvents();
		}
	}
});