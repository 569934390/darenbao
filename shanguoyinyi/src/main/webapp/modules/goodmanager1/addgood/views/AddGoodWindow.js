define([
	'text!modules/goodmanager1/addgood/templates/addView.html',
	'css!styles/css/good.css',
    'Portal',
    'modules/upload/Uploader',
], function(addViewTpl,addCss,Portal,uploader) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true,
        dialogClass:addCss
    };
    var popup;
    
    var forcheck=function(ss){
	        var   type='^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$'; 
    	        var   re   =   new   RegExp(type); 
    	       if(ss.match(re)==null) 
    	        { 
    	         return false;
    	        }
              return true;  
    	};

	return {
		
		openAddWin:function(listView,action,editValue){
			
		      popup = club.popup($.extend({}, options, {
	                modal: true
	            }))
	            $('.goodManagerWindow input[field=datetime]').datetimepicker({
	                format: 'yyyy-mm-dd hh:ii:ss'
	            });
		      
	          $("input[name=cargoName]").val(editValue.name);
	          $("input[name=cargoBrand]").val(editValue.brand);
		      $("input[name=cargoType]").val(editValue.classify);
		      $("input[name=cargo]").val(editValue.cargoNo);
		      $("input[name=cargoId]").val(editValue.id);
		      $("input[name=sort]").val(0);
		      $("input[name=beginTime]").val("");
        	  $("input[name=endTime]").val("");
	          console.log(editValue.brand);
	          
	          
	          
	          var optionimage ={
		            	container: "square_img_container", 
		            	max_file_count: 1
		            };
		        var squareUploader = uploader.create(optionimage);
		        
		        var optionimage1 ={
		            	container: "rectangle_img_container", 
		            	max_file_count: 1
		            };
		        var rectangleUploader = uploader.create(optionimage1);
		        
		        var largeImgUploader = uploader.create({
	            	container: "large_img_container", 
	            	max_file_count: 1
		        });
	   
            $.post(Portal.webRoot+'/goodsColumnController/selectGoodsColumnAndLabelListByShopId.do',{cargoId:editValue.id},function(result){
            	console.log(result);
               var length =result.columns.length;
               var columns=result.columns;
               var optionStr="";
               for(var i=0;i<length;i++){
            	   if(columns[i]!=null && columns[i]!=undefined){
            		   optionStr+="<option value='"+columns[i].id+"'selected='selected' action='"+columns[i].ruleId2+"'>"+columns[i].columnName+"</option>";
            		   if(columns[i].ruleId2 ==undefined || columns[i].ruleId2==null || columns[i].ruleId2 =="" || columns[i].ruleId2 =="null"){
            			   $(".beginTime").hide();
                   		   $(".endTime").hide();
            		   }
            	   }else{
            		   optionStr+="<option value='"+columns[i].id+"' action='"+columns[i].ruleId2+"'>"+columns[i].columnName+"</option>";
            	   }
            	   
               }
               $(".category1").append(optionStr);
               
               var length1 =result.labels.length;
               var labels=result.labels;
               var optionStr1="";
               for(var i=0;i<length1;i++){
            	   optionStr1+=labels[i].labelName+"<input type='checkbox'  class='input-checkbox' value='"+labels[i].id+"' name='labelIds'/>";
               }
               $(".labels").append(optionStr1);
               
               var storeLevelListStr="";
               $.post(Portal.webRoot+'/store/level/findAllStoreLevel.do',function(result){
            	   console.log(result);
                   for(var j=0;j<result.length;j++){
                	   storeLevelListStr+="<br>"+result[j].name+" ";
                	   storeLevelListStr+="<input  name='supplyPrice'/><br>";  
                	   storeLevelListStr+="<input  type='hidden' name='levelId' value='"+result[j].levelId+"'/>";
                   }
                   storeLevelListStr+="<input  type='hidden' name='count' value='"+result.length+"'/>";
                   
                $(".cargoSkuListaaaaaa").html(storeLevelListStr);
 		          
 	          });
         
	            var length2 =result.cargoSkuList.length;
	            var cargoSkuList=result.cargoSkuList;
	            var optionStr2="";
	            for(var i=0;i<length2;i++){
	         	   optionStr2+=cargoSkuList[i].sku+"<input type='checkbox' class='input-checkbox' checked='true' cargoSkuNameList='"+cargoSkuList[i].sku+"' value='"+cargoSkuList[i].id+"' name='cargoSkuIdList'/>"
	         	   +"<input type='hidden' value='"+cargoSkuList[i].sku+"'  name='cargoSkuNameList'/>";
	         	   $(".gridtable").append("<tr id='tabletr"+cargoSkuList[i].id+"' data-id='"+cargoSkuList[i].id+"' data-name='"+cargoSkuList[i].sku+"'><td>"
	        			   +cargoSkuList[i].sku+"</td>+<td>" +
	        			   		"<input name='marketPrice' data-rule='required' />" +"</td><td>" +
	        			   				"<input name='salePrice' data-rule='required' /></td>" +
	        			   				"<td class='cargoSkuListaaaaaa'></td></tr>");
	         	   
	            }
	            $(".cargo").append(optionStr2);
            });
            
            var rule;
            $("form[name=good-form] select[name=category]").change(function(){
            	var a=$("form[name=good-form] select[name=category] option[value="+$(this).val()+"]").attr("action");
            	console.log($("form[name=good-form] select[name=category] option[value="+$(this).val()+"]").attr("action"));
            	if(a!=undefined && a!=null && a!="" && a!="null"){
            		rule=a;
            		$(".beginTime").show();
            		$(".endTime").show();
            	}else{
            		$(".beginTime").hide();
            		$(".endTime").hide();
            	}
            });
		     
            $("div[name='cargoSku']").on("click", '.input-checkbox', function(){
            	var id=$(this).val();
            	var check=$(this).is(':checked');
            	var skuName=$(this).attr("cargoSkuNameList");
            	console.log(id);
            	console.log($(this).is(':checked'));
            	if(check){
            		
            	   var storeLevelListStr="";
                  $.post(Portal.webRoot+'/store/level/findAllStoreLevel.do',function(result){
                  	   console.log(result);
                         for(var j=0;j<result.length;j++){
                      	   storeLevelListStr+="<br>"+result[j].name+" ";
                      	   storeLevelListStr+="<input  name='supplyPrice' /><br>";  
                      	   storeLevelListStr+="<input  type='hidden' name='levelId' value='"+result[j].levelId+"'/>";
                         }
                         storeLevelListStr+="<input  type='hidden' name='count' value='"+result.length+"'/>";
                     	$(".gridtable").append("<tr id='tabletr"+id+"' data-id='"+id+"' data-name='"+skuName+"'><td><input type='hidden' name='cargoSkuId' readOnly='true' value='"+id+"' />"
                  			   +"<input type='hidden' name='cargoSkuName' readOnly='true' value='"+skuName+"' />"+skuName+"</td><td>" +
                  			   		"<input name='marketPrice' data-rule='required' /></td><td>" +
                  			   				"<input name='salePrice' data-rule='required'/></td>"+  			   		 			   			
		   			                          	"<td class='cargoSkuListaaaaaa'>"+storeLevelListStr+"</td></tr>");

       		          
       	          });
            	
            	}else{
            		$("#tabletr"+id).remove();
            	}
            });
            
            $.post(Portal.webRoot+'/carriageRuleController/getCarriageRuleList.do',function(result){
         	   console.log(result);
         	   var str="";
                for(var j=0;j<result.length;j++){
                 str+="<option value='"+result[j].id+"'>"+result[j].templateName+"</option>";
                }
            
             $(".postid").append(str);
		     });
            
            
            $('.goodManagerWindow button').click(function(){
                var btnAction = $(this).attr('action');
  	            var labelIds=[];
  	            var cargoSkuIdList=[];
  	            var cargoSkuNameList=[];
  	            var marketPriceList=[];
  	            var salePriceList=[];
  	            var allPriceList=[];
  	         	var count=[];
                switch(btnAction){
                    case 'save-button': 
                    	if($(".beginTime").css("display") == 'block'){
                    		if($('input[name=beginTime]').val() == ''){
                    			club.toast('error', '开始时间不能为空');
		                		return;
		                   	}
                        }
                        if($(".endTime").css("display") == 'block'){
                        	if($('input[name=endTime]').val() == ''){
                        		club.toast('error', '结束时间不能为空');
	                			return;
	                   	 	}
                        }
                        var isValid = $(".goodManagerWindow form[name=good-form]").isValid();
                        if (isValid) {
                        	 $(".gridtable tr[id]").each(function(){ 
                        	var  marketPrice=$(this).find("input[name=marketPrice]").val();
                           	var  salePrice=$(this).find("input[name=salePrice]").val();
                           	var  count=$(this).find("input[name=count]").val();
  
                           	allPriceList=$(this).find("input[name=supplyPrice]").val();
                           	var  skuName=$(this).attr("data-name");
                           	var  id=$(this).attr("data-id");
                           	if(marketPrice && salePrice){
                           		marketPriceList.push(marketPrice);
                               	salePriceList.push(salePrice);
                               	cargoSkuNameList.push(skuName);
                               	cargoSkuIdList.push(id);
                           
                           	}
       
                         });
                        	for(var i=0;i<marketPriceList.length;i++){
                        		if(forcheck(marketPriceList[i])==false){
                        			club.toast('warn', '原价请输入合理数字');
                        			return;
                        		}
                        	} 
                        	for(var i=0;i<salePriceList.length;i++){
                        		if(forcheck(salePriceList[i])==false){
                        			club.toast('warn', '现价请输入合理数字');
                        			return;
                        		}
                        	} 
                        	
                    	    console.log(marketPriceList);
                    	    console.log(salePriceList);
                    	    console.log(cargoSkuNameList);
                    	    console.log(allPriceList);
                    	    var square;
                          	if(squareUploader.get().length>0){
                          		square=squareUploader.get()[0].url;
                              	$("input[name=imgSquare]").val(square);	
                          	}
                          	
                          	var rectangle;
                          	if(rectangleUploader.get().length>0){
                          		rectangle=rectangleUploader.get()[0].url;
                              	$("input[name=imgRectangle]").val(rectangle);	
                          	}
                          	var largImg;
                          	if(largeImgUploader.get().length>0){
                          		largImg=largeImgUploader.get()[0].url;
                              	$("input[name=imgLarge]").val(largImg);	
                          	}
                          	
                        		
                            var value = $('form[name=good-form]').form('value');
                            console.log(value);
                            console.log(rule);
                            if(rule!=undefined && rule!=null && rule!="" && rule!="null"){
                            	value.beginTimeStr=$("input[name=beginTime]").val();
                                value.endTimeStr=$("input[name=endTime]").val();
                            }else{
                            	value.beginTimeStr="";
                            	value.endTimeStr="";
                            	value.beginTime="";
                            	value.endTime="";
                            }
                            
                            console.log(value);
                            
                            if(value.labelIds != undefined && value.labelIds != ""){
                            	if(value.labelIds instanceof Array){
                            		for(var i=0;i<value.labelIds.length;i++){
                            			labelIds.push(value.labelIds[i]);
                            		}
                                }else{
                                	labelIds.push(value.labelIds);
                                }
                            }
                            
                            if(value.count != undefined && value.count != ""){
                            	if(value.count instanceof Array){
                            		for(var i=0;i<value.count.length;i++){
                            			count.push(value.count[i]);
                            		}
                                }else{
                                	count.push(value.count);
                                }
                            }
                            
                            
        
                            $.post(Portal.webRoot+'/good/addGood.do',{modelJson:JSON.stringify(value),
                            	labelIds:JSON.stringify(labelIds),cargoSkuIdList:JSON.stringify(cargoSkuIdList),
                            	cargoSkuNameList:JSON.stringify(cargoSkuNameList),
                            	marketPriceList:JSON.stringify(marketPriceList),
                            	salePriceList:JSON.stringify(salePriceList),
                            	allPriceList:JSON.stringify(value.supplyPrice),
                            	count:JSON.stringify(count)
                            	},function(result){
                                if (result.success) {
                                    listView.callback.refresh();
                                    popup.close();
                                }
                                else{
                                    club.toast('error', result.msg);
                                }
                                
                            });
                         } else{
                        	 club.toast('error', '必填栏位不能为空');
                         }
                         break;
                    case 'cancel-button': popup.close();break;

                }
            });
                      
		}
	};
});