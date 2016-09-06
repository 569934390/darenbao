define([
	'text!modules/version/addversion/templates/addVersion.html',
    'Portal',
    'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
    'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor'
], function(addVersionTpl,Portal,UE) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addVersionTpl,
        autoResizable: true
    };
    var popup;

	return {
		openAddWin:function(listView,action,editValue){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            
            $('.addVersionWindow select[name=status]').combobox();
            $('.addVersionWindow select[name=platform]').combobox();    
            $('.addVersionWindow select[name=downloadWay]').combobox();    
            
            if(UE){
            	UE.delEditor('editorDescription');
            }
    		var editor = UE.getEditor('editorDescription');
            
            
            $('.addVersionWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=version-form]').isValid();
                         var isValid = $('form[name=version-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=version-form').form('value');
                            var url = Portal.webRoot+'/module/version/'+(action=='edit'?'modify':'add')+'.do';
                            $.post(url,{modelJson:JSON.stringify(value)},function(result){
                                if (result.code == 1) {
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
                    	$('form[name=version-form').form('clear');
                    	editor.ready(function(){  
                        	editor.setContent('');  
                        }); 
                        if (action=='edit') {
                            $('form[name=version-form').form('value',editValue);
                            editor.ready(function(){  
                            	editor.setContent(editValue.description);  
                            }); 
                        };
                        break;
                }
            });
            $('.addVersionWindow select[name=status]').combobox({
            	onSelect:function(result){
            		var value = $(this).combobox('getValue');
            	}
            });
            $('.addVersionWindow select[name=platform]').combobox({
            	onSelect:function(result){
            		var value = $(this).combobox('getValue');
            	}
            });
            $('.addVersionWindow select[name=downloadWay]').combobox({
            	onSelect:function(result){
            		var value = $(this).combobox('getValue');
            	}
            });
            if (action=='edit') {
                $('form[name=version-form').form('value',editValue);
              //ueditor编辑复制
                editor.ready(function(){  
                	editor.setContent(editValue.description);  
                }); 
                $('.modal-title').html('编辑版本');
            }
		}
	};
});