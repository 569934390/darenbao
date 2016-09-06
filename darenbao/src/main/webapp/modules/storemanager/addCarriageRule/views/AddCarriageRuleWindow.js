define([
	'text!modules/storemanager/addCarriageRule/templates/addCarriageRuleView.html',
	'modules/storemanager/addCarriageRule/views/DeliverRegionWindow',
	'data/cityData',
    'Portal'
], function(addViewTpl, DeliverRegionWindow, cityData, Portal) {
	var options = {
		height:"80%",
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
            if(action=='edit'){
               $('.modal-title').html('编辑模板');
               $.post(Portal.webRoot+'/carriageRuleController/queryCarriageRuleDetail.do',{
            	   carriageRuleId:editValue},function(result){
            		   $('input[name=templateName]').val(result.templateName);
            		   $('input[name=carriage]').val(result.carriage);
            		   $('input[name=carriageRuleId]').val(result.id);
            		   var address = result.detail;
            		   for(var i=0; i<address.length; i++){
            			   var option = "";
            			   var names = cityData.getProvinceName(address[i].deliverRegion);
            			   option = '<tr style="border-bottom: 1px dotted #cccccc"><td><input type="hidden" name="id" value="'+address[i].id+'"/><input class="form-control" name="indentMoneyFull" value="'+address[i].indentMoneyFull+'"></td>'+
            			   		'<td><input class="form-control" name="carriageFull" value="'+address[i].carriageFull+'"></td>'+
            			   		'<td><input class="form-control" name="carriageNotFull" value="'+address[i].carriageNotFull+'"></td>'+
            			   		'<td><span>'+names+'</span><input type="hidden" class="form-control" name="deliverRegion" value="'+address[i].deliverRegion+'"></td>'+
            			   		'<td><a href="javaScript:void(0)" class="btn" action="edit">编辑</a>'+
            			   		'<a href="javaScript:void(0)" class="btn" action="del">删除</a></td></tr>';
            			   $('.gridtable').append(option);
            		   }           	
        	   });
            }
            
            $('.carriageRuleWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                    	var carriageRuleId = $('input[name=carriageRuleId').val();
                    	var templateName = $('input[name=templateName').val();
                    	var carriage = $('input[name=carriage').val();
                    	var param = {};
                    	var list = [];
                    	var reg=new RegExp("^(0|[1-9][0-9]*)$");
                    	if(!reg.test(carriage)){
                    		return club.toast('error', "默认运费应为正整数");
                    	}
                    	var addIs = false;
                    	$(".gridtable tr").each(function(){
                    		var id = $(this).find("input[name=id]").val();
                    		var indentMoneyFull = $(this).find("input[name=indentMoneyFull]").val();
                    		var carriageFull = $(this).find("input[name=carriageFull]").val();
                    		var carriageNotFull = $(this).find("input[name=carriageNotFull]").val();
                    		var deliverRegion = $(this).find("input[name=deliverRegion]").val();
                    		param = {};
                    		param.id = id;
                    		param.indentMoneyFull = indentMoneyFull;
                    		param.carriageFull = carriageFull;
                    		param.carriageNotFull = carriageNotFull;
                    		param.deliverRegion = deliverRegion;
                    		if(addIs){
                    			list.push(param);                    			
                    		} else{
                    			addIs = true;
                    		}
                    	});
                        $.post(Portal.webRoot+'/carriageRuleController/addOrUpdCarriageRule.do',{
                        	carriageRuleId:carriageRuleId, templateName:templateName, carriage:carriage, 
                        	carriageRuleDetail:JSON.stringify(list)},function(result){
                        	if(result.code) {
                        		listView.callback.refresh();
                        		popup.close(); 
                        	} else{
                        		club.toast('error', result.msg);
                        	}
                         });
                         break;
                    case 'cancel-button': popup.close();break;
                }
            });
            
            $(".carriageRuleWindow").on("click",".btn",function(){
            	var btnAction = $(this).attr('action');
            	switch(btnAction){
                	case 'add': 
                		var tr = '<tr style="border-bottom: 1px dotted #cccccc"><td><input class="form-control" name="indentMoneyFull"></td>'+
                		'<td><input class="form-control" name="carriageFull"></td>'+
                		'<td><input class="form-control" name="carriageNotFull"></td>'+
                		'<td><input type="hidden" class="form-control" name="deliverRegion"></td>'+
                		'<td><a href="javaScript:void(0)" class="btn" action="edit">编辑</a>'+
                		'<a href="javaScript:void(0)" class="btn" action="del">删除</a></td></tr>';
                		$('.gridtable').append(tr);
                	break;
                	case 'del':
                		var row = $(this).parent().parent();
                		row.remove();
                	break;
                	case 'edit':
                		var row = $(this).parent().parent();
                		DeliverRegionWindow.openAddWin({
//                            callback:this
                        },me,btnAction,row);
                	break;
            	}
        	});
		},
		getAddress: function(data, row){
			var td = $(row.find("td")[3]);
			td.find("span").remove();
			td.append('<span>'+data.names+'</span>');
			$(td.find("input").val(data.ids));
		}
	};
});