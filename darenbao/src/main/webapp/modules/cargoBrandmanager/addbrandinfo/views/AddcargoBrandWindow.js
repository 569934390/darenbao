define([
	'text!modules/cargoBrandmanager/addbrandinfo/templates/addView.html',
	'modules/upload/Uploader',
    'Portal'
], function(addViewTpl,uploader,Portal) {
	var options = {
		height:'auto',
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
            var optionimage ={
            	container: "logo_img_container", 
            	max_file_count: 1
            };
            if(action=='edit'){
               if (editValue.logo) {
            	   optionimage.image_list=[{id:editValue.id, url: editValue.logo}];
               }
            }
            var brandLogoUploader = uploader.create(optionimage);
            
            $('.cargoBrandManagerWindow select[name=brandRecommendation]').combobox();

            $('.cargoBrandManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=cargoBrand-form]').isValid();
                         var isValid = $('form[name=cargoBrand-form]').isValid();
                         if (isValid) {
                        	var logo;
                        	if(brandLogoUploader.get().length>0)
                        		 logo=brandLogoUploader.get()[0].url;
                        	$("input[name=logo]").val(logo);
                            var value = $('form[name=cargoBrand-form').form('value');
                            $.post(Portal.webRoot+'/cargoBrand/saveOrUpdateCargoBrand.do',{modelJson:JSON.stringify(value)},function(result){
                                if (result.success) {
                                    listView.callback.refresh();
                                    popup.close();
                                }
                                else{
                                    club.toast('error', result.msg);
                                }
                                
                            });
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                    case 'clear-button': $('form[name=cargoBrand-form').form('clear');
                        if (action=='edit') {
                            $('form[name=cargoBrand-form').form('value',editValue);
                        };
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
                        $('.goodManagerWindow input[name=logo]').val(file.name);
                    } else if (file.error) {
                        club.toast('info', file.error);
                    }
                });
            }).on('fileupload:fail', function (e, data) {
                $.each(data.files, function (index) {
                    club.toast('info', 'File upload failed.');
                });
            });
            
            if (action=='edit') {
                console.info(editValue);
                var temp = [];
                temp.push(editValue.lng)
                temp.push(editValue.lat)
                editValue.rateType = temp;
                $('form[name=cargoBrand-form').form('value',editValue);
                $('.modal-title').html('编辑品牌信息');
     
//                if (editValue.logo) {
//                    var imageUrl = Portal.webRoot+'/upload.do?getthumb='+editValue.logo+'&size=150';
//                    $('.addBrandLogo').attr('src',imageUrl);
//                };
            }
            
	      
       
		}
	};
});