define([
	'text!modules/sg_bannermanager/add/templates/addCarouselImgView.html',
    'i18n!modules/basedatamanager/add/i18n/addcarouseimg.i18n',
    'Portal',
    'modules/upload/Uploader',
    'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
    'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor'
], function(AddCarouselViewTpl, i18n,Portal,uploader,UE) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: true,
        content: club.compile(AddCarouselViewTpl)(i18n), 
        autoResizable: true
    };
    var popup;
	return {
		openAddWin:function(listView,action,record){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            if(UE){
            	UE.delEditor('editor_carousel');
            }
            $('#line_status').change(function(){
            	var tx=$('select[name=lineStatus]').val();
            	if(tx=='0'){
            		$('#no_rich_ds').hide();
            		$('#rich_ds').show();
            	}else{
            		$('#no_rich_ds').show();
            		$('#rich_ds').hide();
            	}
            });
            var optionimage ={
                	container: "carousel_img_container", 
                	max_file_count: 1
                };
            if(action=='carousel-edit-btn'){
               if (record.picUrl) {
            	   optionimage.image_list=[{id:record.id, url: record.picUrl}];
               }
            }
            var brandLogoUploader = uploader.create(optionimage);
    		var editorstorePro = UE.getEditor('editor_carousel');
            $('.carousel-img-manager-add button').click(function(){
                var btnAction = $(this).attr('action');
                $(this).attr("disabled", true);
                var that=this;
                if(btnAction==undefined||btnAction==''||btnAction==null){
                	popup.close();
                	return;
                }
                switch(btnAction){
                    case 'save-button-carousel': 
                         var isValid = $('form[name=client-form-carousel]').isValid();
                         if (isValid) {
                         	var tx=$('select[name=lineStatus]').val();
                        	if(tx=='0'){
                        		/*var txd=editorstorePro.getContent();  
                        		if(txd==''||txd==null||txd==undefined){
                        			$(that).attr("disabled", false);
                       			  	club.toast('warn', "内联文本不能为空");
                       			  	return;
                        		}*/
                       		}else{
                        		var txd=$('#richTextUrl').val();
                        		if(txd==''||txd==null||txd==undefined){
                        			 $(that).attr("disabled", false);
                        			 club.toast('warn', "商品编号不能为空");
                        			 return;
                        		}
                        	}
                        	if(brandLogoUploader.get().length>0){
                        		$("input[name=picUrl]").val(brandLogoUploader.get()[0].url);
                        	}else{
                        		$(that).attr("disabled", false);
                   			 	club.toast('warn', "请上传轮播图片");
                   			 	return;
                        	}
                            var value = $('form[name=client-form-carousel').form('value');
                            $.post(Portal.webRoot+'/carousel/saveCarouselImg.do',
                            	{conditionStr:JSON.stringify(value)},
                            	function(result){
	                                if(result!=null){
	  	                              var resultCode=result.code;
	  	                              if(resultCode=='000000'){
	  	                            	  listView.callback.refresh();
	  	                            	  club.toast('info',result.msg );
	  	                            	  popup.close();
	  	                              }else{
	  	                            	  $(that).attr("disabled", false);
	  	                            	  club.toast('warn',result.msg ); 
	  	                              }
	  	                            }else{
	  	                            	 $(that).attr("disabled", false);
	  	                            	 club.toast('warn','保存失败' ); 
	  	                            }
                            });
                         }else{
                        	 $(that).attr("disabled", false); 
                         }
                         break;
                    case 'cancel-button-carousel': 
                    		popup.close();
                    		break;
                    case 'clear-button-carousel': 
                    	$('form[name=client-form-carousel').form('clear');
                        editorstorePro.ready(function(){  
                        	editorstorePro.setContent('');  
                        });  
                        $(this).attr("disabled", false);
                        break;
                }
            });
            if(action=='carousel-edit-btn'&&record!=null){
            	$('#carousel-title').text("编辑广告信息");
            	$('input[name=carouse_id]').val(record.id);
            	$('input[name=remk]').val(record.remk);
            	$('select[name=status]').val(record.status);
            	$('select[name=category]').val(record.category);
            	$('input[name=sort]').val(record.sort);
            	$('select[name=lineStatus]').val(record.lineStatus);
            	$('input[name=picUrl]').val(record.picUrl);
            	if(record.lineStatus==0){
            		$('#no_rich_ds').hide();
            		$('#rich_ds').show();
                    editorstorePro.ready(function(){  
                    	editorstorePro.setContent(record.richText);  
                    }); 
            	}else{
            		$('#no_rich_ds').show();
            		$('#rich_ds').hide();
            		$('input[name=richTextUrl]').val(record.richText);
            	}
            }
		}
	};
});