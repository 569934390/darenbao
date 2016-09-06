define([
	'text!modules/memberinfo/addmember/templates/addView.html',
    'Portal',
    'text!data/cityData.json'
], function(addViewTpl,Portal,regionData) {
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
            }));
            $('.memberInfoWindow input[type=text]').clearinput();
            $('.memberInfoWindow input[type=password]').clearinput();
            $('.memberInfoWindow select[name=state]').combobox();
            $('.memberInfoWindow select[name=sex]').combobox();
            var cache = {};
            $('.memberInfoWindow input[name=parentClientName]').autocomplete({
                minLength: 0,
                source: function (request, response) {
                    var term = request.term;
                    if (term in cache) {
                        response(cache[ term ]);
                        return;
                    }
                    $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:0,limit:200,conditionStr:JSON.stringify({'clientName':'%'+term+'%','levelName':'%'+term+'%',state:'%%'}),name:'client'},function(result){
                       
                        if (result&&_.isArray(result.resultList)) {
                             var datas = [];
                            for (var i = result.resultList.length - 1; i >= 0; i--) {
                                datas.push({
                                    id:result.resultList[i].bizId,
                                    label:result.resultList[i].clientName,
                                    value:result.resultList[i].bizId
                                });
                            };
                            cache[ term ] = datas;
                            console.info(cache)
                            response(datas);
                        };
                    });
                },
                focus: function (e, ui) {
                    $(".memberInfoWindow input[name=parentClientName]").val(ui.item.label);
                    return false;
                },
                select: function (e, ui) {
                    $(".memberInfoWindow input[name=parentClientName]").val(ui.item.label+'('+ui.item.value+')');
                    $(".memberInfoWindow input[name=parentClientId]").val(ui.item.value);
                    return false;
                },
                itemRenderer: function (item) {
                    return item.label + "(" + item.value+')';
                }
            });

            $('.memberInfoWindow input[name=levelId]').combobox({
                placeholder: '请选择会员等级',
                dataTextField: 'name',
                dataValueField: 'bizId',
                dataSource: []
            });
            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:0,limit:100,name:'client_level',conditionStr:JSON.stringify({
                    'state':'%00A%'})},function(result){
                $('.memberInfoWindow input[name=levelId]').combobox({
                    dataSource: result.resultList,
                    value:action=='edit'?editValue.levelId:''
                });

            });
            $('.area').cascadeselect({
                dataSource: JSON.parse(regionData),
                selectors: ['.provice', '.city', '.region']
            });
            if (action=='edit') {
                $('.area').cascadeselect('value',[parseInt(editValue.provice),parseInt(editValue.city),parseInt(editValue.region)]);
            };
            
            
            $('.memberInfoWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=client-form]').isValid();
                         var isValid = $('form[name=client-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=client-form').form('value');
                            if (value.bizId==value.parentClientId) {
                                return club.toast('info', '父会员不能为本身！');
                            };
                            if (action=='edit') {
                                if (editValue.password!=value.password) {
                                    value.password = club.MD5(value.password);
                                };
                            }
                            else{
                                value.password = club.MD5(value.password);
                            }
                            $.post(Portal.webRoot+'/client/saveOrUpdateClientInfo.do',{clientJson:JSON.stringify(value)},function(result){
                                listView.callback.refresh();
                                popup.close();
                            });
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                    case 'clear-button': $('form[name=client-form').form('clear');
                        if (action=='edit') {
                            $('form[name=client-form').form('value',editValue);
                            $('input[name=levelId]').combobox('value',editValue.levelId);
                        };
                        break;
                }
            });

            $('.fileupload-select').fileupload({
                url: Portal.webRoot+'/upload.do',
                dataType: 'json',
                xhrFields: {
                    withCredentials: true
                },
                autoUpload: true,
                previewCanvas: false,
                acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                maxFileSize: 2999000,
                disableImageResize: /Android(?!.*Chrome)|Opera/
                    .test(window.navigator.userAgent),
                previewMaxWidth: 150,
                previewMaxHeight: 150,
                previewCrop: true
            }).on('fileupload:add', function (e, data) {
                data.context = $('.addUserFace');
            }) .on('fileupload:processalways', function (e, data) {
                var index = data.index,
                    file = data.files[index];
                if (file.preview) {
                    data.context.attr("src", file.preview.toDataURL());
                }
                if (file.error) {
                    club.toast('info', file.error);
                }
                else{
                    $.blockUI();
                }
            }).on('fileupload:done', function (e, data) {
                $.unblockUI();
                $.each(data.result, function (index, file) {
                    if (file.url) {
                        console.info(file)
                        $('.memberInfoWindow input[name=icon]').val(file.name);
                    } else if (file.error) {
                        club.toast('info', file.error);
                    }
                });
            }).on('fileupload:fail', function (e, data) {
                $.unblockUI();
                $.each(data.files, function (index) {
                    club.toast('info', 'File upload failed.');
                });
            });
            if (action=='edit') {
                editValue.password2 = editValue.password;
                $('form[name=client-form').form('value',editValue);
                $('.modal-title').html('编辑会员信息');
                if (editValue.icon) {
                    var imageUrl = Portal.webRoot+'/upload.do?getthumb='+editValue.icon+'&size=150';
                    $('.addUserFace').attr('src',imageUrl);
                };
            }
		}
	};
});