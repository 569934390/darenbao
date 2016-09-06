define([
	'text!modules/financemanager/addBill/templates/addView.html',
    'Portal'
], function(addViewTpl,Portal) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true
    };
    var popup;

	return {
		openAddWin:function(listView,action,editValue){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            $('.billWindow input[type=text]').clearinput();
            $('.billWindow select').combobox();
            $('.billWindow input[name=accountId]').combobox({
                placeholder: '请选择账号',
                dataTextField: 'name',
                dataValueField: 'accountId',
                dataSource: []
            });
            $('.billWindow input[name=itemId]').combobox({
                placeholder: '请选择科目',
                dataTextField: 'name',
                dataValueField: 'itemId',
                dataSource: []
            });
            $('.integral-manager input[name=createDate]').datetimepicker('value',club.dateutil.format(new Date(),'yyyy/mm/dd hh:ii:ss'));
            
            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:0,limit:100,name:'account',conditionStr:JSON.stringify({
                    'state':'%00A%'})},function(result){
                $('.billWindow input[name=accountId]').combobox({
                    dataSource: result.resultList,
                    value:action=='edit'?editValue.accountId:''
                });

            });
            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:0,limit:100,name:'item',conditionStr:JSON.stringify({
                    'state':'%00A%'})},function(result){
                $('.billWindow input[name=itemId]').combobox({
                    dataSource: result.resultList,
                    value:action=='edit'?editValue.itemId:''
                });

            });
            
            
            $('.billWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=bill-form]').isValid();
                         var isValid = $('form[name=bill-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=bill-form').form('value');
                            if (value.bizId==value.parentbillId) {
                                return club.toast('info', '父会员不能为本身！');
                            };
                            if (action=='edit') {
                                if (editValue.password!=value.password) {
                                    value.password = club.MD5(value.password);
                                };
                            }
                            else{
                                value.password = club.MD5(value.password);
                            }
                            $.post(Portal.webRoot+'/audit/saveOrUpdateBill.do',{modelJson:JSON.stringify(value)},function(result){
                                listView.callback.refresh();
                                popup.close();
                            });
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                    case 'clear-button': $('form[name=bill-form').form('clear');
                        if (action=='edit') {
                            $('form[name=bill-form').form('value',editValue);
                            $('input[name=levelId]').combobox('value',editValue.levelId);
                        };
                        break;
                }
            });

            if (action=='edit') {
                editValue.password2 = editValue.password;
                $('form[name=bill-form').form('value',editValue);
                $('.modal-title').html('编辑账单信息');
            }
		}
	};
});