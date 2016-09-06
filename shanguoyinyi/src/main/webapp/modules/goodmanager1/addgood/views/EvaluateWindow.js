define([
	'text!modules/goodmanager1/addgood/templates/evaluate.html',
	'css!styles/css/good.css',
	'modules/upload/Uploader',
    'Portal'
], function(addViewTpl,addCss,uploader,Portal) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true,
        dialogClass:addCss
    };
    var popup;

	return {
		openAddWin:function(listView,editValue){
			
		      popup = club.popup($.extend({}, options, {
	                modal: true
	            }))
	          var popWin = {};
		      var optionimage ={
		            	container: "logo_img_container", 
		            	max_file_count: 3
		            };
		      var brandLogoUploader = uploader.create(optionimage);
             $("input[name=tradeGoodId]").val(editValue.tradeGoodId);
	          console.log(editValue.brand);
            $.post(Portal.webRoot+'/good/selectGoodSku.do',{tradeGoodId:editValue.tradeGoodId},function(result){
            	console.log(result);
               var length =result.list.length;
               var skuList=result.list;
               var optionStr="";
               var i;
               for(var i=0;i<length;i++){
            	   if(editValue!=null && (i==0)){
            		   optionStr+="<option value='"+skuList[i].id+"'selected='selected'>"+skuList[i].cargoSkuName+"</option>";
            	   }else{
            		   optionStr+="<option value='"+skuList[i].id+"'>"+skuList[i].cargoSkuName+"</option>";
            	   }
            	
               }
               $(".goodSku").append(optionStr);
            });
		     
            
            $('.goodManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
  	            var labelIds=[];
  	            var cargoSkuIdList=[];
  	            var cargoSkuNameList=[];
  	            var marketPriceList=[];
  	            var salePriceList=[];
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=good-form]').isValid();
                         var isValid = $('form[name=good-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=good-form]').form('value');
                            console.log(value);
                            var logo=[];
                        	if(brandLogoUploader.get().length>0){
                        		for(var i=0;i<brandLogoUploader.get().length;i++){
                        			logo.push(brandLogoUploader.get()[i].url);
                        		}
                        		 
                        	}    
                        	console.log(logo);
                        	//$("input[name=logo]").val(logo);
                            $.post(Portal.webRoot+'/good/addEvaluation.do',{modelJson:JSON.stringify(value),
                              cargoSkuIdList:JSON.stringify(cargoSkuIdList),logo1:JSON.stringify(logo)
                            	},function(result){
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
                        $('.goodManagerWindowinput[name=logo]').val(file.name);
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