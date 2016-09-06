define([
	'text!modules/ruleinfo/levelconf/templates/levelConfView.html',
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
            $.post(Portal.webRoot+'/datasource/wfdataset/getDataByName.do',{start:0,limit:100,name:'client_level',conditionStr:JSON.stringify({
                    'state':'%00A%'})},function(result){
                for(var i=0;i<result.resultList.length;i++){
                    var $item = $('<div class="col-md-12 levelRate">'+
                        '<div class="form-group required">'+
                            '<label class="col-md-4 control-label">“'+result.resultList[i].name+'”会员等级奖励比率：</label>'+
                            '<div class="input-group col-md-2">'+
                                '<input type="text" field="spinner" name="levelRate_'+result.resultList[i].bizId+'" class="form-control" value="'+(i*5)+'">'+
                                '<span class="input-group-addon">%</span>'+
                            '</div>'+
                        '</div>'+
                    '</div>');
                    $('form[name=level-conf-form]').append($item);
                }

                $('.levelConfWindow input[field=spinner]').spinner({
                    min:0,
                    max:100
                });
                if (editValue) {
                    console.info(editValue);
                    $('form[name=level-conf-form').form('value',JSON.parse(editValue));
                }
            });
            
            $('.levelConfWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=level-conf-form]').isValid();
                         var isValid = $('form[name=level-conf-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=level-conf-form').form('value');
                            console.info(value);
                            $('input[name='+action+']').val(JSON.stringify(value));
                            // $.post(Portal.webRoot+'/client/saveOrUpdateClientInfo.do',{clientJson:JSON.stringify(value)},function(result){
                            //     listView.callback.refresh();
                                popup.close();
                            // });
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                }
            });
            
		}
	};
});