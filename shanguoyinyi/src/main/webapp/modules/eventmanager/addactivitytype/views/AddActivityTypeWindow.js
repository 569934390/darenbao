define([
	'text!modules/eventmanager/addactivitytype/templates/addView.html',
    'Portal'
], function(addViewTpl,Portal) {
	var options = {
	    height: $(window).height()*0.5,
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
            
            $('.AddActivityTypeWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=activity-type-form]').isValid();
                         var isValid = $('form[name=activity-type-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=activity-type-form]').form('value');
                            $.post(Portal.webRoot+'/event/activityType/saveOrUpdateActivityType.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': 
                        $('form[name=activity-type-form').form('clear');
                        if (action=='edit') {
                            $('form[name=activity-type-form').form('value',editValue);
                        }
                        break;
                }
            });
            if (action=='edit') {
                console.info(editValue);
                $('form[name=activity-type-form]').form('value',editValue);
                $('.modal-title').html('编辑活动部落分类信息');
            }
		}
	};
});