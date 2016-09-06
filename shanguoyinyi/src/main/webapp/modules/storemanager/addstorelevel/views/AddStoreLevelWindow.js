define([
	'text!modules/storemanager/addstorelevel/templates/addView.html',
    'Portal',
    'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
    'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor'
], function(addViewTpl,Portal,UE) {
	var options = {
        height: $(window).height()*0.9,
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
            
            if(UE){
            	UE.delEditor('editorstorePro');
            }
    		var editorstorePro = UE.getEditor('editorstorePro');
    	          
            $('.storeLevelManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=storeLevel-form]').isValid();
                         var isValid = $('form[name=storeLevel-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=storeLevel-form').form('value');
                            $.post(Portal.webRoot+'/store/level/saveOrUpdateStoreLevel.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=storeLevel-form').form('clear');
                        if (action=='edit') {
                            $('form[name=storeLevel-form').form('value',editValue);
                            //ueditor编辑复制
                            editorstorePro.ready(function(){  
                            	editorstorePro.setContent(editValue.storePro);  
                            });  
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
                //$('.headStoreNameDiv').css('display','block')
                $('form[name=storeLevel-form').form('value',editValue)
               
                //ueditor编辑复制
                editorstorePro.ready(function(){  
                	editorstorePro.setContent(editValue.storePro);  
                });  
                $('.modal-title').html('编辑店铺等级信息');
            }
		}
	};
});