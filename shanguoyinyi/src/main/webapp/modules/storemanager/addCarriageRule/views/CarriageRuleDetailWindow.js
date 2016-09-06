define([
	'text!modules/storemanager/addCarriageRule/templates/carriageRuleDetailView.html',
	'data/cityData',
    'Portal'
], function(addViewTpl, cityData, Portal) {
	var options = {
		height: 'auto',
        modal: false,
        draggable: true,
        content: addViewTpl,
        autoResizable: true
    };
    var popup;
    
	return {
		openAddWin:function(listView,action,editValue){
            popup = club.popup($.extend({ }, options, {
                modal: true
            }))
            var me = this;
            $.post(Portal.webRoot+'/carriageRuleController/queryCarriageRuleDetail.do',{
            	carriageRuleId:editValue},function(result){
            		$('#templateName').append('<span>'+result.templateName+'</span>');
            		$('#carriage').append('<span>'+result.carriage+'元</span>');
            		var address = result.detail;
            		for(var i=0; i<address.length; i++){
            			var option = "";
            			var names = cityData.getProvinceName(address[i].deliverRegion);
            			option = '<tr style="border-bottom: 1px dotted #cccccc"><td class="col-md-2" align="center"><span>'+address[i].indentMoneyFull+'</span></td>'+
            			'<td class="col-md-2" align="center"><span>'+address[i].carriageFull+'</span></td>'+
            			'<td class="col-md-2" align="center"><span>'+address[i].carriageNotFull+'</span></td>'+
            			'<td class="col-md-6"><span>'+names+'</span></td></tr>';
            			$('.gridtable').append(option);
            		}           	
            	});
		}
	};
});