define([
	'text!modules/eventmanager/addonlinestudytype/templates/addView.html',
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
            
            $('.AddOnlineStudyTypeWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=online-study-type-form]').isValid();
                         var isValid = $('form[name=online-study-type-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=online-study-type-form]').form('value');
                            $.post(Portal.webRoot+'/event/onlineStudyType/saveOrUpdateOnlineStudyType.do',{modelJson:JSON.stringify(value)},function(result){
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
                        $('form[name=online-study-type-form').form('clear');
	                    if (action=='add') {
	                    	$('form[name=online-study-type-form] input[name=parentId]').val(editValue.id);
	                    	$('form[name=online-study-type-form] input[name=parentName]').val(editValue.name);;
	                    }
                        if (action=='edit') {
                            $('form[name=online-study-type-form').form('value',editValue);
                        };
                        break;
                }
            });
            
            
            if (action=='add') {
            	console.info(editValue);
            	var temp = [];
            	temp.push(editValue.lng)
            	temp.push(editValue.lat)
            	editValue.rateType = temp;
            	$('form[name=online-study-type-form] input[name=parentId]').val(editValue.id);
            	$('form[name=online-study-type-form] input[name=parentName]').val(editValue.name);
            }
            if (action=='edit') {
                console.info(editValue);
                var temp = [];
                temp.push(editValue.lng)
                temp.push(editValue.lat)
                editValue.rateType = temp;
                $('form[name=online-study-type-form]').form('value',editValue);
                $('.modal-title').html('编辑在线学习分类');
            }
		}
	};
});