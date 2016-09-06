define([
	'text!modules/spreadmanager/addSpreadClassify/templates/addView.html',
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
			
            popup = club.popup($.extend({
            	
            }, options, {
                modal: true
            }))
            
            //$('.cargoClassifyWindow select[name=status]').combobox();
            $('.spreadClassifyManagerWindow input[field=spinner]').spinner({
                min:0
            });
            
    	          
            $('.spreadClassifyManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                    	var value = $('form[name=spreadClassify-form').form('value');
                    	var tx=$('select[name=status]').val();
                         var isValid = $('form[name=spreadClassify-form]').isValid();
                         if (isValid) {
                            $.post(Portal.webRoot+'/spreadClassify/addOrUpdateSpreadClassify.do',{modelJson:JSON.stringify(value)},function(result){
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
            if (action=='edit') {
                 console.info(editValue);
                 $('form[name=spreadClassify-form]').form('value',editValue);
                $('.modal-title').html('编辑推广分类信息');
            }
		}
	};
});