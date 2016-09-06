define([
	'text!modules/proviceProxy/addProxy/templates/addView.html',
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
            $('.proviceProxyWindow select[name=state]').combobox();
            $('.proviceProxyWindow input[field=spinner]').spinner({
                min:0
            });

            $('.proviceProxyWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=provice-proxy-form]').isValid();
                         var isValid = $('form[name=provice-proxy-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=provice-proxy-form').form('value');
                            $.post(Portal.webRoot+'/client/saveOrUpdateProxyInfo.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=provice-proxy-form').form('clear');
                        if (action=='edit') {
                            $('form[name=provice-proxy-form').form('value',editValue);
                        };
                        break;
                }
            });
            if (action=='edit') {
                console.info(editValue);
                $('form[name=provice-proxy-form').form('value',editValue);
                $('.modal-title').html('编辑省代理信息');
            }
		}
	};
});