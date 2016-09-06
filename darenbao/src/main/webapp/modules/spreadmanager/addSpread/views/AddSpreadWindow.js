define([
	'text!modules/spreadmanager/addSpread/templates/addView.html',
	'modules/upload/Uploader',
    'Portal',
    'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
    'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor'
], function(addViewTpl,uploader,Portal,UE) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true,
    };
    var popup;

	return {
		openAddWin:function(listView,action,editValue,spreadContentType){
			
		      popup = club.popup($.extend({}, options, {
	                modal: true
	            }))
	            var optionimage ={
		            	container: "logo_img_container", 
		            	max_file_count: 1
		            };
		        var brandLogoUploader = uploader.create(optionimage);
	            var str='';
	           if(spreadContentType==1){
	        	  $("input[name=goodName]").val(editValue.name);
	 	          $("input[name=cargoBrand]").val(editValue.brand);
	 		      $("input[name=cargoType]").val(editValue.classify);
	 		      $("input[name=cargoNo]").val(editValue.cargoNo);
	 		      $("input[name=goodId]").val(editValue.id);
                   str+='<option value="1">'+'商品'+'</option>';
	           }else{
	        	   str+='<option value="0">'+'店铺'+'</option>';
	        	   $(".goodName").hide();
	        	   $(".cargoCode").hide();
	           }
		      $('select[name=spreadContentType]').append(str);
	          console.log(editValue);
	          if(UE){
	            	UE.delEditor('contentEditor');
	            }
	    		var editorstorePro = UE.getEditor('contentEditor');
	    		
	    		$.post(Portal.webRoot+'/spreadClassify/findAllSpreadClassifyOnStatus.do',{},function(result){
	            	if(result!=null&&result.length>0){
	            		var text='';
	            		for(var i=0;i<result.length;i++){
	            			if(i==0){
	            			text+='<option value="'+result[i].id+'" selected="selected">'+result[i].name+'</option>';	
	            			}else{
	            			text+='<option value="'+result[i].id+'">'+result[i].name+'</option>';	
	            			}      			
	            		}
	            		$('select[name=classifyId]').append(text);
	            	}
	            });
            
            $('.goodManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                    	
                   	  $('form[name=spreadInfo-form]').isValid();
                      var isValid = $('form[name=spreadInfo-form]').isValid();
                      if (isValid) {
                    	  var r= club.confirm('您确定要保存吗？');
             			 r.result.then(function resolve(){
             				  var logo;
                            	if(brandLogoUploader.get().length>0)
                            	 logo=brandLogoUploader.get()[0].url;
                            	$("input[name=logo]").val(logo);
                               var value = $('form[name=spreadInfo-form').form('value');
                               $.post(Portal.webRoot+'/spreadManager/addOrUpdateSpreadInfo.do',{modelJson:JSON.stringify(value)},function(result){
                                   if (result.success) {
                                       listView.callback.refresh();
                                       popup.close();
                                   }
                                   else{
                                       club.toast('error', result.msg);
                                   }
                                   
                               });
             			 }, function reject(){
             			     return;
             			 });
                    
                       };
                      break;

                    case 'cancel-button': 
                    	
                    	 var r= club.confirm('您确定要取消吗？');
            			 r.result.then(function resolve(){
            				 popup.close();
            			 }, function reject(){
            			 return;
            			 });
            			 
                    	break;

                }
            });
              
            
            $('.logo-fileupload-select').fileupload({
                url: Portal.webRoot+'/upload.do',
                dataType: 'json',
                xhrFields: {
                    withCredentials: true
                },
                autoUpload: true,
                previewCanvas: false,
                acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                maxFileSize: 999000,
                disableImageResize: /Android(?!.*Chrome)|Opera/
                    .test(window.navigator.userAgent),
                previewMaxWidth: 150,
                previewMaxHeight: 150,
                previewCrop: true
            }).on('fileupload:add', function (e, data) {
                data.context = $('.addBrandLogo');
            }) .on('fileupload:processalways', function (e, data) {
                var index = data.index,
                    file = data.files[index];
                if (file.preview) {
                    data.context.attr("src", file.preview.toDataURL());
                }
                if (file.error) {
                    club.toast('info', file.error);
                }
            }).on('fileupload:done', function (e, data) {
                $.each(data.result, function (index, file) {
                    if (file.url) {
                        console.info(file)
                        $('.cargoBrandManagerWindow input[name=logo]').val(file.name);
                    } else if (file.error) {
                        club.toast('info', file.error);
                    }
                });
            }).on('fileupload:fail', function (e, data) {
                $.each(data.files, function (index) {
                    club.toast('info', 'File upload failed.');
                });
            });
		}
	};
});