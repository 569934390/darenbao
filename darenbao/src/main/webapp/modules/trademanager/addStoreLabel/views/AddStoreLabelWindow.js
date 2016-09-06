define([
	'text!modules/trademanager/addStoreLabel/templates/addView.html',
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
            
            $('.tradeStoreLabelManagerWindow input[field=spinner]').spinner({
                min:0
            });
    	          
            $('.tradeStoreLabelManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=tradeStoreLabel-form]').isValid();
                         var isValid = $('form[name=tradeStoreLabel-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=tradeStoreLabel-form').form('value');
                            $.post(Portal.webRoot+'/goodsBaseLabelController/addOrUpdGoodsBaseLabel.do',{modelJson:JSON.stringify(value)},function(result){
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
                    case 'clear-button': $('form[name=tradeStoreLabel-form').form('clear');
                        if (action=='edit') {
                            $('form[name=tradeStoreLabel-form').form('value',editValue);
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
                $('form[name=tradeStoreLabel-form]').form('value',editValue)
                $('form[name=tradeStoreLabel-form] input[name=shopFlag]').val("Y");
                $('.modal-title').html('编辑商品标签信息');
            }
		}
	};
});