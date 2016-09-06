define([
	'text!modules/goodmanager1/addgood/templates/upGood.html',
	'css!styles/css/good.css',
    'Portal'
], function(addViewTpl,addCss,Portal) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true,
        dialogClass:addCss
    };
    var popup;

	return {
		openAddWin:function(listView,action,editValue){
			
		      popup = club.popup($.extend({}, options, {
	                modal: true
	            }))
	            console.log(editValue[0]);
	  
	             $.post(Portal.webRoot+'/good/selectGoodSku.do',{tradeGoodId:editValue[0].tradeGoodId},function(result){
                                if (result.success) {
                                	var list=[];
                                	list=result.list;
                                	console.log(list);
                                   for(var i = 0 ; i< list.length; i++){
                                	//   object.cargoSkuId=list[i].cargoSkuId;
                                	   //goodupList.push(object);
                                	   $(".gridtable").append("<tr><td>"+(i+1)+"</td>+<td>"
                                			   +list[i].cargoSkuName+"</td>+<td>"+list[i].leftNums+"</td>+<td><input type='hidden' name='cargoSkuId' value='"+list[i].cargoSkuId+"' /><input type='hidden' name='goodSkuId' value='"+list[i].id+"' /><input type='hidden' name='leftNums' value='"+list[i].leftNums+"' /><input value='0' name='num'/></td></tr>");
                                   }
                                }
                                else{
                                    club.toast('error', result.msg);
                                }
	                  });
	            
	            
  
            $('.UpGoodWindow button').click(function(){
                var btnAction = $(this).attr('action');
                var cargoSkuId;
                var num;
                var goodSkuId;
                var leftNums;
                switch(btnAction){
                    case 'save-button':
                    	   var goodupList=[];
                           $(".gridtable tr").each(function(){
                        	  cargoSkuId=$(this).children().find("input[name=cargoSkuId]").val();
                        	  num=$(this).children().find("input[name=num]").val();
                        	  goodSkuId=$(this).children().find("input[name=goodSkuId]").val();
                        	  leftNums=$(this).children().find("input[name=leftNums]").val();
                        	  if(cargoSkuId){
                        	     var object={
                        			        num:0,	
                        			        cargoSkuId:"",
                        			        goodSkuId:""
                        			        }
                         	    object.cargoSkuId=cargoSkuId;
                         	    object.num=num;
                         	    object.goodSkuId=goodSkuId;
                    	        goodupList.push(object);
                             }
                           });
                          
                           console.log(goodupList);
                           console.log(typeof num);
                           console.log(typeof leftNums);
              
                    	if(parseInt(num)>0 && num!=null && num!=undefined && num!=""){
                    		if(parseInt(num)<=parseInt(leftNums)){
                    			$.post(Portal.webRoot+'/good/upGoodSku.do',{goodupList:JSON.stringify(goodupList),tradeGoodId:editValue[0].tradeGoodId},function(result){
                           		 if(result.success){
                           			 listView.callback.refresh();
                           			 club.toast('info', "上架成功");
                           		 }else{
                           			 club.toast('error', result.msg);
                           		 }
                            
       	                      });
                           	   popup.close(); 
                               break;	
                    		}
                    		else{
                    			club.toast('warn', "上架数量不能超过库存数量");
                    			break;	
                    		}
                    		 
                    	}else{
                    		club.toast('warn', "输入数量不能为空，且必须大于0");
                    		break;	
                    	}                    	
                    	  
                    case 'cancel-button': popup.close();break;

                }
            });

		}
	};
});