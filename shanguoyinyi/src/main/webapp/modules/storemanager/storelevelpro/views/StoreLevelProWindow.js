define([
	'text!modules/storemanager/storelevelpro/templates/storeProView.html',
    'Portal'
], function(addViewTpl,Portal) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: true,
        content: addViewTpl,
        autoResizable: true,
        resizable: true
    };
    var popup;
    
	return {
		openAddWin:function(listView,action,editValue){
			
            popup = club.popup($.extend({
            	
            }, options, {
                modal: true
            }))
            
            $('.storeLevelManagerWindow .modal-body').html(editValue.storePro);

            $('.storeLevelManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'cancel-button': popup.close();break;
                }
            });
            
		}
	};
});