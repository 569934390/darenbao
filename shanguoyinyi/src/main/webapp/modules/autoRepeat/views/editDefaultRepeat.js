define([
	'text!modules/autoRepeat/templates/editDefaultRepeat.html',
    'Portal',
    'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
    'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor'
], function(addViewTpl,Portal,UE) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true,
    };
    var popup;

	return {
		openAddwin: function(listView,action,editValue,readOnly){
			
		      popup = club.popup($.extend({}, options, {
	                modal: true
	            }))
	            console.log("------"+editValue);  
		      if(readOnly==false){
	          if(UE){
	            	UE.delEditor('contentEditor');
	            	var editorstorePro = UE.getEditor('contentEditor');
	            }
		      }
		      
	          
	    		if(action=="edit"){
	    			if(readOnly==false){
	    				editorstorePro.ready(function(){  
			    			editorstorePro.setContent(editValue.content);  
			  	        }); 	
	    			}
	              	$('input[name=id]').val(editValue.id);
	              }
            
            $('.editDefaultRepeatWindow button').click(function(){
                var btnAction = $(this).attr('action');
                
                switch(btnAction){
                    case 'save-button': 
                    	
                   	  $('form[name=defaultRepeat-form]').isValid();
                      var isValid = $('form[name=defaultRepeat-form]').isValid();
                      if (isValid) {
                    	  var r= club.confirm('您确定要保存吗？');
             			 r.result.then(function resolve(){
                               var value = $('form[name=defaultRepeat-form]').form('value');
                               $.post(Portal.webRoot+'/autoRepeat/editDefaultRepeat.do',{modelJson:JSON.stringify(value)},function(result){
                                   if (result.success) {
                                       listView.callback.refresh();
                                       popup.close();
                                   }
                                   else{
                                       club.toast('error', result.msg);
                                   }
                                   
                               });
             			 }, function reject(){
             			     return;
             			 });
                    
                       };
                      break;

                    case 'cancel-button': 
                    	
                    	 var r= club.confirm('您确定要取消吗？');
            			 r.result.then(function resolve(){
            				 popup.close();
            			 }, function reject(){
            			 return;
            			 });
            			 
                    	break;

                }
            });
		}
	};
});