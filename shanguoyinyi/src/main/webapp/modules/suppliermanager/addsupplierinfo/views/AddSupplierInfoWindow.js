define([
	'text!modules/suppliermanager/addsupplierinfo/templates/addView.html',
    'Portal'
], function(addViewTpl,Portal) {
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

            $('.supplierManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=supplier-form]').isValid();
                         var isValid = $('form[name=supplier-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=supplier-form').form('value');
                            $.post(Portal.webRoot+'/cargoSupplier/saveOrUpdateCargoSupplier.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=supplier-form').form('clear');
                        if (action=='edit') {
                            $('form[name=supplier-form').form('value',editValue);
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
                $('form[name=supplier-form').form('value',editValue);
                $('.modal-title').html('编辑供应商信息');
                $('.editSupplierCode').attr('readonly',true);
            }
		}
	};
});