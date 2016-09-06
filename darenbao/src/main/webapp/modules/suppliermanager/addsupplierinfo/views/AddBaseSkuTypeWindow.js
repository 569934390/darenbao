define([
	'text!modules/suppliermanager/addsupplierinfo/templates/baseSkuType.html',
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
            
            $('.memberInfoWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=skuType-form]').isValid();
                         var isValid = $('form[name=skuType-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=skuType-form').form('value');
                            if (value.skuName=="") {
                                return club.toast('info', '规格名称不能为空！');
                            }
                            console.info(JSON.stringify(value));
                            $.post(Portal.webRoot+'/cargoBaseSkuTypeController/addOrUpdCargoBaseSkuType.do',{"modelJson":JSON.stringify(value)},function(result){
                                listView.callback.refresh();
                                popup.close();
                            });
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                }
            });
            
            if(action == 'edit') {
                $('form[name=skuType-form').form('value',editValue);
                $('.modal-title').html('编辑规格');
                $('.editType').attr('disabled',true);
            }
		}
	};
});