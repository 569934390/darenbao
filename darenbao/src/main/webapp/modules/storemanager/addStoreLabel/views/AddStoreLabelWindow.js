define([
	'text!modules/storemanager/addStoreLabel/templates/addView.html',
	'i18n!modules/common/i18n/TextView.i18n',
    'Portal'
], function(addViewTpl,i18nText,Portal) {
	var options = {
		height:'auto',
        modal: false,
        draggable: true,
        content: club.compile(addViewTpl)(i18nText),
        autoResizable: true
    };
    var popup;
    
	return {
		openAddWin:function(listView,action,editValue){
			
            popup = club.popup($.extend({
            
            }, options, {
                modal: true
            }))
            
            //$('.cargoClassifyWindow select[name=status]').combobox();
            $('.storeLabelManagerWindow input[field=spinner]').spinner({
                min:0
            });
            
    	          
            $('.storeLabelManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=storeLabel-form]').isValid();
                         var isValid = $('form[name=storeLabel-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=storeLabel-form').form('value');
                            $.post(Portal.webRoot+'/goodsBaseLabelController/addOrUpdGoodsBaseLabel.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=storeLabel-form').form('clear');
                        if (action=='edit') {
                            $('form[name=storeLabel-form').form('value',editValue);
                        };
                        break;
                }
            });
            
            if (action=='edit') {
                console.info(editValue);
                var temp = [];
                temp.push(editValue.lng)
                temp.push(editValue.lat)
                editValue.rateType = temp;
                $('form[name=storeLabel-form').form('value',editValue)
                $('.modal-title').html('编辑商品标签信息');
            }
		}
	};
});