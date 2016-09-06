define([
    'text!modules/commontextmanager/templates/CommonTextManagerView.html',
    'Portal',
    'modules/upload/Uploader',
    'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
    'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor'
], function (commonTextManagerViewTpl,Portal,Uploader,UE) {
	
	var ourEditor,myEditor,registerEditor;
	var logoUploader;
	var image_list = [];
	var max_file_count = 1;

	var ourObject,myObject,registerObject;
    return club.View.extend({
        template: club.compile(commonTextManagerViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {},
        events: {
            "click .changeType": "changeType",
            "click #tabs-text-our .btn": "ourHandler",
            "click #tabs-text-my .btn": "myHandler",
            "click #tabs-text-register .btn": "registerHandler"
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
//            $('#tabs').tabs();
            if(UE){
            	UE.delEditor('ourContent');
            	UE.delEditor('myContent');
            	UE.delEditor('registerContent');
            }
        	ourEditor = UE.getEditor('ourContent',{
        		scaleEnabled:true
        		});
        	myEditor = UE.getEditor('myContent',{
        		scaleEnabled:true
        		});
        	registerEditor = UE.getEditor('registerContent',{
        		scaleEnabled:true
        		});
        	this.changeNative("our");
        	var me = this;
        	$.post(Portal.webRoot+'/module/text/our.do',{},function(result){
        		if(result != undefined && result != null && result != ""){
        			ourObject = result;
        			$('form[name=our-common-text-form]').form('value',result);
                    ourEditor.ready(function(){  
                    	ourEditor.setContent(result.content);  
                    });
                    if(result.fileUrl != undefined && result.fileUrl != "" && result.fileUrl != null){
    	            	$('form[name=our-common-text-form] input[name=fileUrl]').val(result.fileUrl);
    	            	image_list.splice(0,image_list.length);
    	            	image_list.push({"id":result.id,"url":result.fileUrl});
                    }
        		}
            	me.loadLogoUploader();
            });
        },
        loadLogoUploader:function(){
        	logoUploader = Uploader.create({
        		container: "small_img_container", 
        		max_file_count: max_file_count,
        		image_list:image_list
        	});
        },
        changeNative:function(type){
        	$("#tabs-text .changeType").each(function(){
        		$(this).parent("li").removeClass("ui-tabs-active");
        	});
        	$("#tabs-text .changeType[type="+type+"]").parent().addClass("ui-tabs-active");
        	$("#tabs-text .ui-tabs-panel").each(function(){
        		$(this).css({"display":"none"});
        	});
        	$("#tabs-text #tabs-text-"+type).removeAttr("style");
        	switch(type){
	            case 'our' : $('form[name='+type+'-common-text-form] input[name=type]').val('1');break;
	            case 'my' : $('form[name='+type+'-common-text-form] input[name=type]').val('2');break;
	            case 'register' : $('form[name='+type+'-common-text-form] input[name=type]').val('3');break;
	        }
        },
        changeType:function(event){
            var type = $(event.currentTarget).attr("type");
            $('form[name='+type+'-common-text-form]').form('clear');
            var tempEditor = UE.getEditor(type+'Content');
            tempEditor.ready(function(){  
            	tempEditor.setContent('');  
            });
            var me = this;
            $.post(Portal.webRoot+'/module/text/'+type+'.do',{},function(result){
            	if(result != undefined && result != null && result != ""){
            		switch(type){
	    	            case 'our' : ourObject = result;
	    	            	if(result.fileUrl != undefined && result.fileUrl != ""){
	        	            	image_list.splice(0,image_list.length);
	    	            		image_list.push({"id":result.id,"url":result.fileUrl});
	    	            	}
	    	            	me.loadLogoUploader();
	    	            	break;
	    	            case 'my' : myObject = result;break;
	    	            case 'register' : registerObject = result;break;
	    	        }
            		$('form[name='+type+'-common-text-form]').form('value',result);
                    tempEditor.ready(function(){  
                    	tempEditor.setContent(result.content);  
                    });
            	}
            });
            this.changeNative(type);
        },
        ourHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            switch(action){
                case 'save-button' : this.save('our');break;
                case 'clear-button': this.clear('our',ourEditor,ourObject);break;
            }
        },myHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            switch(action){
                case 'save-button' : this.save('my');break;
                case 'clear-button': this.clear('my',myEditor,myObject);break;
            }
        },registerHandler: function (event) {
            var action = $(event.currentTarget).attr("action");
            switch(action){
                case 'save-button' : this.save('register');break;
                case 'clear-button': this.clear('register',registerEditor,registerObject);break;
            }
        },clear:function(type,editor,textObject){
        	$('form[name='+type+'-common-text-form]').form('clear');
        	editor.ready(function(){  
            	editor.setContent('');  
            });
        	switch(type){
	            case 'our' : $('form[name='+type+'-common-text-form] input[name=type]').val('1');
	            			$('form[name='+type+'-common-text-form] input[name=fileUrl]').val('');
	            			image_list.splice(0,image_list.length);
	            			break;
	            case 'my' : $('form[name='+type+'-common-text-form] input[name=type]').val('2');break;
	            case 'register' : $('form[name='+type+'-common-text-form] input[name=type]').val('3');break;
	        }
        	if(textObject != undefined){
        		$('form[name='+type+'-common-text-form]').form('value',textObject);
            	editor.ready(function(){  
                	editor.setContent(textObject.content);  
                });
            	if(type == 'our'){
            		image_list.push({"id":textObject.id,"url":textObject.fileUrl});
            	}
        	}
        	this.loadLogoUploader();
        },
        save:function(type){
        	var fileUrlObject = logoUploader.get()[0];        	
        	switch(type){
	            case 'our' : 
	            	if(fileUrlObject == undefined || fileUrlObject == ""){
	                    return club.toast('warn', "请选择上传Logo！");
	            	}if(ourEditor.getContent() == undefined || ourEditor.getContent() == ""){
	                    return club.toast('warn', "描述不能为空！");	            		
	            	};
	            	$('form[name='+type+'-common-text-form] input[name=fileUrl]').val(fileUrlObject.url);
	            	break;
	            case 'my' : 
	            	if(myEditor.getContent() == undefined || myEditor.getContent() == ""){
	                    return club.toast('warn', "描述不能为空！");	            		
	            	}
	            	break;
	            case 'register' : 
	            	if(registerEditor.getContent() == undefined || registerEditor.getContent() == ""){
	                    return club.toast('warn', "描述不能为空！");	            		
	            	}
	            	break;
	        }
            var value = $('form[name='+type+'-common-text-form]').form('value');
            $.post(Portal.webRoot+'/module/text/addOrModify.do',{modelJson:JSON.stringify(value)},function(result){
                if (result.code == 1) {
                	switch(type){
	    	            case 'our' : ourObject = result.msg;break;
	    	            case 'my' : myObject = result.msg;break;
	    	            case 'register' : registerObject = result.msg;break;
	    	        }
                	$('form[name='+type+'-common-text-form]').form('value',result.msg);
                    club.toast('info', '操作成功！');
                }else{
                    club.toast('error', result.msg);
                }
            });
         }
    });
});
