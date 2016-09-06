define([
	'text!modules/memberlevel/addlevel/templates/addView.html',
    'Portal'
], function(addViewTpl,Portal) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true
    };
    var popup;

	return {
		openAddWin:function(listView,action,editValue){
            popup = club.popup($.extend({}, options, {
                modal: true
            }))
            $('.clientLevelInfoWindow select[name=state]').combobox();
            $('.clientLevelInfoWindow input[field=spinner]').spinner({
                min:0
            });

            if(editValue&&editValue.lng!='1'){
                $('.clientLevelInfoWindow .num[name=lngNum]').spinner('disable');
            }

            if(editValue&&editValue.lat!='2'){
                $('.clientLevelInfoWindow .num[name=latNum]').spinner('disable');
            }

            $('.clientLevelInfoWindow input[name=rateType]').click(function(){
                $('.clientLevelInfoWindow .num').spinner('disable');
                $('.clientLevelInfoWindow input[name=rateType]:checked').each(function(){ 
                    $('.num[name='+$(this).attr('relate')+']').spinner('enable');
                }); 

            });

            $('.clientLevelInfoWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=client-level-form]').isValid();
                         var isValid = $('form[name=client-level-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=client-level-form').form('value');
                            value.lng = 0;
                            value.lat = 0;
                            if (value.rateType) {
                                for (var i = value.rateType.length - 1; i >= 0; i--) {
                                    if(parseInt(value.rateType[i])==1){
                                        value.lng = 1;
                                    }
                                    if(parseInt(value.rateType[i])==2){
                                        value.lat = 2;
                                    }
                                };
                            };
                            $.post(Portal.webRoot+'/client/saveOrUpdateLevelInfo.do',{clientJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=client-level-form').form('clear');
                        if (action=='edit') {
                            $('form[name=client-level-form').form('value',editValue);
                        };
                        break;
                }
            });
            $('.level-fileupload-select').fileupload({
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
                data.context = $('.addLevelFace');
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
                        $('.clientLevelInfoWindow input[name=icon]').val(file.name);
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
                $('form[name=client-level-form').form('value',editValue);
                $('.modal-title').html('编辑会员等级信息');
                if (editValue.icon) {
                    var imageUrl = Portal.webRoot+'/upload.do?getthumb='+editValue.icon+'&size=150';
                    $('.addLevelFace').attr('src',imageUrl);
                };
            }
		}
	};
});