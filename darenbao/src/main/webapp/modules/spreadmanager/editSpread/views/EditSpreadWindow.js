define([
	'text!modules/spreadmanager/editSpread/templates/editView.html',
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
		openAddWin:function(listView,action,editValue){
			
		      popup = club.popup($.extend({}, options, {
	                modal: true
	            }))
	            
	            var optionimage ={
            	container: "logo_img_container", 
            	max_file_count: 1
            };
		    
	              $("input[name=id]").val(editValue.id);
	              $("input[name=goodName]").val(editValue.goodName);
	 		      $("input[name=cargoNo]").val(editValue.cargoNo);
	 		      $("input[name=goodId]").val(editValue.goodId);
	 		      $("input[name=author]").val(editValue.author);
	 		      $("input[name=name]").val(editValue.name);
	 		      $("input[name=content]").html(editValue.content);
	 		     if (editValue.logo) {
	            	   optionimage.image_list=[{id:editValue.logo, url: editValue.url}];
	               }
	 		    var brandLogoUploader = uploader.create(optionimage);
	 		      console.log(editValue);
	            var str='';
	           if(editValue.spreadContentType==1){
                   str+='<option value="1">'+'商品'+'</option>';
	           }else{
	        	   str+='<option value="0">'+'店铺'+'</option>';
	           }
		      $('select[name=spreadContentType]').append(str);
	          console.log(editValue);
	          if(UE){
	            	UE.delEditor('contentEditor');
	            }
	    		var editorstorePro = UE.getEditor('contentEditor');
	    		editorstorePro.ready(function(){  
	    			editorstorePro.setContent(editValue.content);  
	  	        });  
	    		
	    		$.post(Portal.webRoot+'/spreadClassify/findAllSpreadClassifyOnStatus.do',{},function(result){
	            	if(result!=null&&result.length>0){
	            		var text='';
	            		for(var i=0;i<result.length;i++){
	            			if(result[i].id==editValue.classifyId){
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
        
                      
		}
	};
});