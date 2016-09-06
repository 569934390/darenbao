define([
	'text!modules/indent/indentWindow/templates/indentShip.html',
    'Portal'
	], function(indentShipTpl,Portal) {
		var options = {
            height: $(window).height()*0.4,
            width: $(window).width()*0.3,
            modal: false,
            draggable: false,
            content: indentShipTpl,
            autoResizable: true
        };
    
	return {
		openShipWin:function(list,value){
			var me = this;
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            
            $('.indent-ship input[name=expressCompany]').combobox({
                placeholder: '请选择快递公司',
                dataTextField: 'name',
                dataValueField: 'name'
            });
            $.post(Portal.webRoot+'/Expressage/getExpressageUseList.do',{"limit":10,"start":0},function(result){
                $('.indent-ship input[name=expressCompany]').combobox({
                    dataSource: result
                });
            });
            
            
            $('.indent-ship input[name=id]').val(value.id);

            $('.indent-ship button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('.indent-ship form[name=indent-ship-form]').isValid();
                         var isValid = $('.indent-ship form[name=indent-ship-form]').isValid();
                         if (isValid) {
                            var value = $('.indent-ship form[name=indent-ship-form]').form('value');
                            var id = $('.indent-ship input[name=id]').val();
                            $.post(Portal.webRoot+'/deal/indent/update/receive.do',{ids:id,mapStr:JSON.stringify(value)},function(result){
                                if (result.code == 1) {
                                	list.search();
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
                    	$('.indent-ship form[name=indent-ship-form]').form('clear');
                        break;
                }
            });
		},
	};
});