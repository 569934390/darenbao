define([
	'text!modules/trademanager/addheadStore/templates/addView.html',
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
            
            $.post(Portal.webRoot+'/trade/headStore/getClient.do',function(result){
            	 if (result.success) {
            		 var list=result.list;
            		 var length=list.length;
            		 var optionStr="";
            	     for(var i=0;i<length;i++){
            	    	 //console.log(list[i].staffId+" "+list[i].staffName);
            	    	 if(action=="edit"&&editValue.owner==list[i].staffId){
            	    		 optionStr+="<option value="+list[i].staffId+" selected='selected' >"+list[i].staffName+"</option>";
            	    	 }else{
            	    		 optionStr+="<option value="+list[i].staffId+" >"+list[i].staffName+"</option>";
            	    	 }
            	     }
            	     //console.log(optionStr);
            	     $("#owner").append(optionStr);
            	     $("#owner").combobox();
                 }
                 else{
                     club.toast('error', result.msg);
                 }
            });

            $('.headStoreManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=headStore-form]').isValid();
                         var isValid = $('form[name=headStore-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=headStore-form').form('value');
                            $.post(Portal.webRoot+'/trade/headStore/saveOrUpdateTradeHeadStore.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=headStore-form').form('clear');
                        if (action=='edit') {
                            $('form[name=headStore-form').form('value',editValue);
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
                $('form[name=headStore-form').form('value',editValue);
                $('.modal-title').html('编辑总店信息');
            }
		}
	};
});