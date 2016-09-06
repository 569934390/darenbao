define([
	'text!modules/integralmanager/addrule/templates/addRuleView.html',
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
            $('.ruleInfoWindow input[field=datetime]').datetimepicker({
                format: 'yyyy/mm/dd hh:ii:ss'
            });
            $('.ruleInfoWindow select[field=combobox]').combobox();
            $('.ruleInfoWindow input[field=spinner]').spinner({
                min:0
            });

            $('input[name=excType]').click(function(){
                $('.excTypes').hide();
                $('.'+$(this).val()).show();
            });
            $('input[name=ruleType]').click(function(){
                $('.ruleTypes').hide();
                $('.'+$(this).val()).show();
                var excType = $('input[name=excType]:checked').val();
                $('.excTypes').hide();
                $('.'+excType).show();
            });
            var startTime = new Date();
            startTime.setDate(startTime.getDate()-90);
            $('.ruleInfoWindow input[name=aruleStartTime]').datetimepicker('value',club.dateutil.format(startTime,'yyyy/mm/dd hh:ii:ss'));
            $('.ruleInfoWindow input[name=aruleEndTime]').datetimepicker('value',club.dateutil.format(new Date(),'yyyy/mm/dd hh:ii:ss'));

            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:0,limit:100,name:'client_level'},function(result){
                $('select[name=clientLevels]').multiselect({
                    dataSource: result.resultList,
                    value:action=='edit'?editValue.levelId:''
                });

            });
            $('.memberInfoWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=client-form]').isValid();
                         var isValid = $('form[name=client-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=client-form').form('value');
                            $.post(Portal.webRoot+'/client/saveOrUpdateClientInfo.do',{clientJson:JSON.stringify(value)},function(result){
                                listView.callback.refresh();
                                popup.close();
                            });
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                    case 'clear-button': $('form[name=client-form').form('clear');
                        if (action=='edit') {
                            $('form[name=client-form').form('value',editValue);
                            $('input[name=levelId]').combobox('value',editValue.levelId);
                        };
                        break;
                }
            });
            if (action=='edit') {
                console.info(editValue);
                editValue.password2 = editValue.password;
                $('form[name=client-form').form('value',editValue);
                $('.modal-title').html('编辑会员信息');
                $('#verifyPhoneCol').hide();
            }
            $('#verifyPhone').click(function(){
                if (action=='edit') {
                }
                else{
                    $.post(Portal.webRoot+'/index/verifyPhone.do',{phoneCode:$('input[name=phone]').val()},function(result){
                        club.toast('info', result.msg);
                        var timeout = result.timeout/1000;
                        var interval = setInterval(function(){
                            if (timeout<=0) {clearInterval(interval);};
                            $('#timer').html((timeout--)||'');
                        },1000);
                    });
                }
            });
		}
	};
});