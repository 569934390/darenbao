define([
	'text!modules/storemanager/addBankcard/templates/addView.html',
    'Portal'
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
            }))
            $('.bankCardManagerWindow select[name=state]').combobox();
            $('.bankCardManagerWindow input[field=spinner]').spinner({
                min:0
            });

            $('.bankCardManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                    	
                         $('form[name=bankCard-form]').isValid();
                         var isValid = $('form[name=bankCard-form]').isValid();
                         var reg = /^(1)[0-9]{10}$/;
                         if($("#mobile")==null){
                        	 alert('手机号码不能为空');
                        	 $("#mobile").select;
                        	 return
                         };
                         if(!reg.test($("#mobile").val())){
                        	 alert($("#mobile").val()+"手机号格式不正确");
                        	    return 
                         };
                         if($("#name")==null){
                        	 alert('姓名不能为空');
                        	 $("#name").select;
                        	 return
                         };
                         if($("#bankCard")==null){
                        	 alert('电话号码不能为空');
                        	 $("#bankCard").select;
                        	 return
                         };
                         if (isValid) {
                            var value = $('form[name=bankCard-form').form('value');
                            $.post(Portal.webRoot+'/BankCard/saveOrUpdateBankCard.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=bankCard-form').form('clear');
                        if (action=='edit') {
                            $('form[name=bankCard-form').form('value',editValue);
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
                $('form[name=bankCard-form').form('value',editValue);
                $('.modal-title').html('编辑账号信息');
            }
		}
	};
});