define([
	'text!modules/financemanager/addAccount/templates/addView.html',
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
            $('.accountManagerWindow select[name=state]').combobox();
            $('.accountManagerWindow input[field=spinner]').spinner({
                min:0
            });

            $('.accountManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=account-form]').isValid();
                         var isValid = $('form[name=account-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=account-form').form('value');
                            $.post(Portal.webRoot+'/audit/saveOrUpdateAccount.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=account-form').form('clear');
                        if (action=='edit') {
                            $('form[name=account-form').form('value',editValue);
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
                $('form[name=account-form').form('value',editValue);
                $('.modal-title').html('编辑账号信息');
            }
		}
	};
});