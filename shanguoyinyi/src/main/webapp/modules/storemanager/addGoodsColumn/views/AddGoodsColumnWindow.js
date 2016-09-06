define([
	'text!modules/storemanager/addGoodsColumn/templates/addGoodsColumnView.html',
	'modules/upload/Uploader',
    'Portal'
], function(addViewTpl,uploader,Portal) {
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
            popup = club.popup($.extend({ }, options, {
                modal: true
            }))
            $('.shanGuoStoreColumnManagerWindow input[field=datetime]').datetimepicker({
                format: 'yyyy-mm-dd'
            });
            var startTime = new Date(),endTime = new Date();
            startTime.setDate(startTime.getDate()-90);
            endTime.setDate(endTime.getDate()+1);
            $('.shanGuoStoreColumnManagerWindow input[name=ruleStarttime]').datetimepicker('value',
            		club.dateutil.format(startTime,'yyyy/mm/dd hh:ii:ss'));
            $('.shanGuoStoreColumnManagerWindow input[name=ruleEndtime]').datetimepicker('value',
            		club.dateutil.format(endTime,'yyyy/mm/dd hh:ii:ss'));
            var optionimage ={
            	container: "addStoreColumnLogo", 
            	max_file_count: 1
            };
            if(action=='edit'){
               if (editValue.showpicture) {
            	   optionimage.image_list=[{id:editValue.id, url: editValue.showpicture}];
               }
               $('form[name=shanGuoStoreColumn-form').form('value',editValue);
               $('.modal-title').html('编辑商品栏目信息');
            }
            var brandLogoUploader = uploader.create(optionimage);
            
            $.post(Portal.webRoot+'/goodsColumnController/selectRuleSourceList.do',{
            	ruleType :1},function(result){
                	for(var i=0; i<result.length; i++){
                		var option = "";
                		if(editValue!=null&&editValue.ruleId==result[i].id){
                			option = '<option value="'+result[i].id+'" selected="selected">'+result[i].ruleName+'</option>';                			
                		} else{
                			 option = '<option value="'+result[i].id+'">'+result[i].ruleName+'</option>';
                		}
                		$('select[name=ruleId]').append(option);
                	}               	
            });
            $.post(Portal.webRoot+'/goodsColumnController/selectRuleSourceList.do',{
            	ruleType :2},function(result){
                	for(var i=0; i<result.length; i++){
                		var option = "";
                		if(editValue!=null&&editValue.ruleId2==result[i].id){
                			option = '<option value="'+result[i].id+'" selected="selected">'+result[i].ruleName+'</option>';                			
                		} else{
                			 option = '<option value="'+result[i].id+'">'+result[i].ruleName+'</option>';
                		}
                		$('select[name=ruleId2]').append(option);
                	}               	
            });
            
            $('.shanGuoStoreColumnManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=shanGuoStoreColumn-form]').isValid();
                         var ruleValue;
                         var logo;
                     	 if(brandLogoUploader.get().length>0){
                     		 logo=brandLogoUploader.get()[0].url;                     		 
                     	 }
                     	 $("input[name=showpicture]").val(logo);
                         /*var ruleSourceId = $('select[name=ruleId]').val();
                         if(ruleSourceId != null && ruleSourceId != ""){
                        	 ruleValue = $('input[name=ruleVal]').val();
                        	 if(ruleValue == null || ruleValue == ""){
                        		 return club.toast('error', '规则项不能为空');
                        	 }
                         }*/
                         var isValid = $('form[name=shanGuoStoreColumn-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=shanGuoStoreColumn-form').form('value');
                            $.post(Portal.webRoot+'/shanGuoGoodsColumnController/addOrUpdGoodsColumn.do',{modelJson:JSON.stringify(value)},function(result){
                                if (result.success) {
                                	listView.callback.refresh();
                                    popup.close(); 
                                } else{
                                    club.toast('error', result.msg);
                                }
                            });
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                    case 'clear-button': $('form[name=shanGuoStoreColumn-form').form('clear');
                        if (action=='edit') {
                            $('form[name=shanGuoStoreColumn-form').form('value',editValue);
                        };
                        break;
                }
            });
		}
	};
});