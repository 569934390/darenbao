define([
	'text!modules/eventmanager/addonlinestudy/templates/addView.html',
    'Portal',
	'modules/upload/Uploader',
	'modules/upload/UploaderFile',
    'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
    'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor'
], function(addViewTpl,Portal,uploader,uploaderfile,UE) {
	var options = {
	    height: $(window).height()*0.9,
        modal: false,
        draggable: true,
        content: addViewTpl,
        autoResizable: true
    };
    var popup;

	return {
		openAddWin:function(listView,action,editValue){
			
            popup = club.popup($.extend({}, options, {
                modal: true
            }))
            var initstudyChildType=function(parentId){
            	$.post(Portal.webRoot+'/event/onlineStudyType/getListByParentId.do',{parentId:parentId},function(result){
 	            	var studyChildTypeStr="";
 	            	for(var i=0;i<result.length;i++){
 	            		 if(action=="edit"&&editValue.studyChildType==result[i].id){
 	 	            		studyChildTypeStr+='<option value="'+result[i].id+'" selected="selected">'+result[i].name+'</option>';
 	            		 }else{
 	 	            		studyChildTypeStr+='<option value="'+result[i].id+'">'+result[i].name+'</option>';
 	            		 }
 	            	}
 	            	$('.AddOnlineStudyWindow select[name=studyChildType]').html(studyChildTypeStr);
 	            });
            }
            
            var init=function(){
            	 $.post(Portal.webRoot+'/event/onlineStudyType/getParentList.do',{},function(result){
                 	var studyTypeStr="";
                 	for(var i=0;i<result.length;i++){
                 		 if(action=="edit"&&editValue.studyType==result[i].id){
                 			studyTypeStr+='<option value="'+result[i].id+'" selected="selected">'+result[i].name+'</option>';
                 		 }else{
                 			studyTypeStr+='<option value="'+result[i].id+'">'+result[i].name+'</option>';
                 		 }
                 	}
                 	$('.AddOnlineStudyWindow select[name=studyType]').html(studyTypeStr);
                 	if(action=="edit"){
                 		initstudyChildType(editValue.studyType);
                 	}else{
                 		initstudyChildType(result[0].id);
                 	}
                 });
            }
            
            var initVideoUrl=function(){
            	var typeVal=$(".AddOnlineStudyWindow select[name=type]").val();
            	if(typeVal==1){
    				$('.AddOnlineStudyWindow .videoUrl').hide();
    				$('.AddOnlineStudyWindow input[name=videoUrl]').removeAttr("data-rule");
    			}else{
    				$('.AddOnlineStudyWindow .videoUrl').show();
    				$('.AddOnlineStudyWindow input[name=videoUrl]').attr("data-rule","required");
    			}
            	if(typeVal==2){
            		$('.AddOnlineStudyWindow .file').hide();
            	}else{
            		$('.AddOnlineStudyWindow .file').show();
            	}
            }
            
        	var optionimage;
            var covePicUploader;
            var initimage=function(){
	           	  optionimage ={
	                  	container: "covePic_img_container", 
	                  	max_file_count: 1
	              };
	              if(action=='edit'){
	                  if (editValue.covePicUrl) {
	               	    optionimage.image_list=[{id:editValue.id, url: editValue.covePicUrl}];
	                  }
	               }
	        	  covePicUploader = uploader.create(optionimage);
            }
        
            var optionfile;
            var coveFileUploader;
            var initfile=function(){
	           	  optionfile ={
	                  	container: "coveFile_container", 
	                  	max_file_count: 1
	              };
	              if(action=='edit'){
	                  if (editValue.file) {
	               	    optionfile.image_list=[{id:editValue.id+1, url: editValue.file}];
	                  }
	               }
	        	  coveFileUploader = uploaderfile.create_words(optionfile);
            }
            
            if(UE){
            	UE.delEditor('onlineStudyContent');
            }
    		var onlineStudyContent = UE.getEditor('onlineStudyContent');
    		
    		$('.AddOnlineStudyWindow select[name=studyType]').change(function(){
    			var studyTypeVal=$(this).val();
    			initstudyChildType(studyTypeVal);
    		})
    		
    		$('.AddOnlineStudyWindow select[name=type]').change(function(){
    			initVideoUrl();
    		})
            
    		$('.AddOnlineStudyWindow .close').click(function(){
              	var t= club.confirm('您确定要关闭吗？');
              	t.result.then(function resolve(){
              		popup.close();
              	});
              	return false;
    		});
            $('.AddOnlineStudyWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         //$('form[name=online-study-form]').isValid();
                         //var isValid = $('form[name=online-study-form]').isValid();
                         var isValid=true;
						 if (isValid) {
                          	var covePic;
                        	if(covePicUploader.get().length>0)
                        		covePic=covePicUploader.get()[0].url;
							var coveFile;
							if(coveFileUploader.getFiles().length>0){
								console.log(coveFileUploader.getFiles());
								coveFile=coveFileUploader.getFiles()[0].url;
							}
                        	$(".AddOnlineStudyWindow input[name=covePic]").val(covePic);
                        	$(".AddOnlineStudyWindow input[name=file]").val(coveFile);
                            var value = $('form[name=online-study-form]').form('value');
                            $.post(Portal.webRoot+'/event/onlineStudy/saveOrUpdateOnlineStudy.do',{modelJson:JSON.stringify(value)},function(result){
                                if (result.success) {
                                    //listView.callback.refresh();
                                    listView.callback.search();
                                    popup.close();
                                }
                                else{
                                    club.toast('error', result.msg);
                                }
                            });
                         };
                         break;
                    case 'cancel-button': 
	                   	 var t= club.confirm('您确定要关闭吗？');
	        	       	 t.result.then(function resolve(){
	                    	popup.close();
	        	       	 });
                    	break;
                    case 'clear-button': 
                        $('form[name=online-study-form').form('clear');
                        onlineStudyContent.ready(function(){  
                        	onlineStudyContent.setContent("");  
                        });
                        if (action=='edit') {
                            $('form[name=online-study-form').form('value',editValue);
                            //ueditor编辑复制
                            onlineStudyContent.ready(function(){  
                            	onlineStudyContent.setContent(editValue.content);  
                            });  
                        }else{
                        	//ueditor编辑复制
                            onlineStudyContent.ready(function(){  
                            	onlineStudyContent.setContent("");  
                            }); 
                        }
                        break;
                }
            });
    		
    	    init();
            initimage();
			initfile();
            if (action=='edit') {
                console.info(editValue);
                $('form[name=online-study-form]').form('value',editValue);
                
                //ueditor编辑复制
                onlineStudyContent.ready(function(){  
                	onlineStudyContent.setContent(editValue.content);  
                });  
                $('.modal-title').html('编辑在线学习信息');
            }
            
            initVideoUrl();
            
		}
	};
});