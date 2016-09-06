define([
	'text!modules/financemanager/addItem/templates/addView.html',
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
            $('.itemManagerWindow select[name=state]').combobox();
            $('.itemManagerWindow input[field=spinner]').spinner({
                min:0
            });

            $('.itemManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=item-form]').isValid();
                         var isValid = $('form[name=item-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=item-form').form('value');
                            $.post(Portal.webRoot+'/audit/saveOrUpdateItem.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=item-form').form('clear');
                        if (action=='edit') {
                            $('form[name=item-form').form('value',editValue);
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
                $('form[name=item-form').form('value',editValue);
                $('.modal-title').html('编辑科目信息');
            }
		}
	};
});