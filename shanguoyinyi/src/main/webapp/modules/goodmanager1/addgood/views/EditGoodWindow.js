define([
	'text!modules/goodmanager1/addgood/templates/editView.html',
	'css!styles/css/good.css',
    'Portal',
    'modules/upload/Uploader'
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
	            console.log(editValue);
		      
		      $('.goodManagerWindow input[field=datetime]').datetimepicker({
	                format: 'yyyy-mm-dd hh:ii:ss'
	            });
		      
		      var optionimage ={
		            	container: "square_img_container", 
		            	max_file_count: 1,
		            	image_list:editValue.imgSquare?[{id:editValue.imgSquare,url : editValue.squareUrl}]:[]
		            };
		        var squareUploader = uploader.create(optionimage);
		        var optionimage1 ={
		            	container: "rectangle_img_container", 
		            	max_file_count: 1,
		             	image_list:editValue.imgRectangle?[{id:editValue.imgRectangle,url : editValue.rectangleUrl}]:[]
		            };
				var rectangleUploader = uploader.create(optionimage1);

		        var largeImgUploader = uploader.create({
	            	container: "large_img_container", 
	            	max_file_count: 1, 
	            	image_list:editValue.imgLarge?[{id:editValue.imgLarge,url : editValue.largeUrl}]:[]
		        });
				
				
	          $("input[name=cargoName]").val(editValue.name);
	          $("input[name=cargoBrand]").val(editValue.brand);
		      $("input[name=cargoType]").val(editValue.classify);
		      $("input[name=cargo]").val(editValue.cargoNo);
		      $("input[name=cargoId]").val(editValue.cargoId);
		      $("input[name=sort]").val(editValue.sort);
		      $("input[name=name]").val(editValue.name);
		      $("input[name=post]").val(editValue.post);
		      $("input[name=describe]").val(editValue.describe);
		      $("input[name=tradeGoodId]").val(editValue.tradeGoodId);
		      $("input[name=cargoType]").val(editValue.cargoType);
		      $("input[name=cargoName]").val(editValue.cargoName);
		      $("input[name=cargoBrand]").val(editValue.cargoBrand);
		      $("input[name=cargo]").val(editValue.cargo);
		      $("input[name=status]").val(editValue.status);
		      $("input[name=saleNum]").val(editValue.saleNum);
		      if(editValue.beginTime == null || editValue.beginTime==undefined || editValue.beginTime==""){
		    	  var date = new Date();
		    	  $("input[name=beginTime]").datetimepicker('value',club.dateutil.format(date,'yyyy-mm-dd hh:ii:ss'));
		    	  $("input[name=endTime]").datetimepicker('value',club.dateutil.format(date,'yyyy-mm-dd hh:ii:ss'));
		      }else{
		    	  $("input[name=beginTime]").val(editValue.beginTime);
	        	  $("input[name=endTime]").val(editValue.endTime);
		      }
		      
	        var rule;
            $.post(Portal.webRoot+'/ goodsColumnController/selectGoodsColumnAndLabelListByShopId.do',{cargoId:editValue.cargoId},function(result){
            	console.log(result);
               var length =result.columns.length;
               var columns=result.columns;
               var optionStr="";
               for(var i=0;i<length;i++){
            	   if(editValue!=null && editValue.category==columns[i].id){
            		   optionStr+="<option value='"+columns[i].id+"'selected='selected' action='"+columns[i].ruleId2+"'>"+columns[i].columnName+"</option>";
            		   if(columns[i].ruleId2 ==undefined || columns[i].ruleId2==null || columns[i].ruleId2 =="" || columns[i].ruleId2 =="null"){
            			   $(".beginTime").hide();
                   		  $(".endTime").hide();
            		   }
            		   else{
            			   rule=columns[i].ruleId2;
            		   }
            	   }else{
            		   optionStr+="<option value='"+columns[i].id+"' action='"+columns[i].ruleId2+"'>"+columns[i].columnName+"</option>";
            		   
            	   }
            	
               }
               $(".category1").append(optionStr);
               
               var length1 =result.labels.length;
               var labels=result.labels;
               var optionStr1="";
               var checkIs = false;
               var goodLabelsList;
               //商品标签（修改）
               $.post(Portal.webRoot+'/good/getGoodLabels.do',{goodId:editValue.tradeGoodId},function(result){
             	   console.log(result);
             	  goodLabelsList=result;
             	 var length2;
                 if(goodLabelsList==null){
              	   length2=0;
                 }else{
              	   length2=goodLabelsList.length;
                 }
                 for(var i=0;i<length1;i++){
              	   checkIs = false;
              	   for(var j=0;j<length2;j++){
              		   if(labels[i].id == goodLabelsList[j].id){
              			   checkIs = true;
              			   break;
              		   }
              	   }
              	   optionStr1+=labels[i].labelName+"<input type='checkbox' class='input-checkbox' "+(checkIs?' checked ':'')+" value='"+labels[i].id+"' name='labelIds'/>";
                 }
                 $(".labels").append(optionStr1);
  	          }); 
               
           
               
//               var storeLevelListStr="";
//               $.post(Portal.webRoot+'/store/level/findAllStoreLevel.do',function(result){
//            	   console.log(result);
//                   for(var j=0;j<result.length;j++){
//                	   storeLevelListStr+=result[j].name+" ";
//                	   storeLevelListStr+="<input  name='supplyPrice'/>";  
//                	   storeLevelListStr+="<input  type='hidden' name='levelId' value='"+result[j].levelId+"'/>";
//                   }
//                   storeLevelListStr+="<input  type='hidden' name='count' value='"+result.length+"'/>";
//                   
//                $(".cargoSkuListaaaaa").html(storeLevelListStr);
// 		          
// 	          });
                   
	            var length2 =result.cargoSkuList.length;
	            var cargoSkuList=result.cargoSkuList;
	            var optionStr2="";
	            if(editValue.status==1){
	            	 for(var i=0;i<length2;i++){         	
	    			     optionStr2+=cargoSkuList[i].sku+"<input type='checkbox' class='input-checkbox'   onclick='return false;' cargoSkuNameList='"+cargoSkuList[i].sku+"' value='"+cargoSkuList[i].id+"' name='cargoSkuIdList'/>"
	       	         	   +"<input type='hidden' value='"+cargoSkuList[i].sku+"'  name='cargoSkuNameList'/>";
	            	
//	    	         	   $(".gridtable").append("<tr id='tabletr"+cargoSkuList[i].id+"' data-id='"+cargoSkuList[i].id+"' data-name='"+cargoSkuList[i].sku+"'><td>"
//	    	        			   +cargoSkuList[i].sku+"</td>+<td>" +
//	    	        			   		"<input name='marketPrice' value='0' onkeyup='this.value=this.value.replace(/[^\d]/g,'')'/>" +"</td><td>" +
//	    	        			   				"<input name='salePrice' value='0' onkeyup='this.value=this.value.replace(/[^\d]/g,'')'/></td>"+      		
//	    	        			   				"<td class='cargoSkuListaaaaa'></td></tr>");
	    	         	   
	    	            }
	            }else{
		            for(var i=0;i<length2;i++){         	
					     optionStr2+=cargoSkuList[i].sku+"<input type='checkbox' class='input-checkbox'   cargoSkuNameList='"+cargoSkuList[i].sku+"' value='"+cargoSkuList[i].id+"' name='cargoSkuIdList'/>"
		   	         	   +"<input type='hidden' value='"+cargoSkuList[i].sku+"'  name='cargoSkuNameList'/>";
		        	
//			         	   $(".gridtable").append("<tr id='tabletr"+cargoSkuList[i].id+"' data-id='"+cargoSkuList[i].id+"' data-name='"+cargoSkuList[i].sku+"'><td>"
//			        			   +cargoSkuList[i].sku+"</td>+<td>" +
//			        			   		"<input name='marketPrice' value='0' onkeyup='this.value=this.value.replace(/[^\d]/g,'')'/>" +"</td><td>" +
//			        			   				"<input name='salePrice' value='0' onkeyup='this.value=this.value.replace(/[^\d]/g,'')'/></td>"+      		
//			        			   				"<td class='cargoSkuListaaaaa'></td></tr>");
			         	   
			            }
	            }
	            $(".cargo").append(optionStr2);
	            
	            $.post(Portal.webRoot+'/good/queryGoodSkuList.do',{tradeGoodId:editValue.tradeGoodId},function(result){
	                   console.log(result);
	            	   for(var i=0;i<result.goodSkuList.length;i++){

	            		  $(".input-checkbox[value='"+result.goodSkuList[i].cargoSkuId+"']").attr("checked",true);

	            		  $(".gridtable").append("<tr id='tabletr"+cargoSkuList[i].id+"' data-id='"+cargoSkuList[i].id+"' data-name='"+cargoSkuList[i].sku+"'><td>"
	   	        			   +cargoSkuList[i].sku+"</td>+<td>" +
	   	        			   		"<input name='marketPrice' value='0' onkeyup='this.value=this.value.replace(/[^\d]/g,'')' data-rule='required'/>" +"</td><td>" +
	   	        			   				"<input name='salePrice' value='0' onkeyup='this.value=this.value.replace(/[^\d]/g,'')' data-rule='required'/></td>"+      		
	   	        			   				"<td class='cargoSkuListaaaaa'></td></tr>");
	            		  var tr = $(".gridtable tr[id='tabletr"+result.goodSkuList[i].cargoSkuId+"']");
	            		  tr.attr("good-sku-id", result.goodSkuList[i].id);
	            		  tr.find("input[name=marketPrice]").val(result.goodSkuList[i].marketPrice);
	            		  tr.find("input[name=salePrice]").val(result.goodSkuList[i].salePrice);

	            	   }
	            	   $.post(Portal.webRoot+'/StoreSupplyPrice/findSupplyPrice.do',{goodId:editValue.tradeGoodId},function(result){
	                 	   console.log(result);
	                       $(".cargoSkuListaaaaa").html("<input  type='hidden' name='count' value='0'/>");
//	               		    var storeLevelListStr="";
	                        for(var j=0;j<result.length;j++){
	                     	   var storeLevelListStr = "<br>"+result[j].storeName+" ";
	                     	   storeLevelListStr+="<input  name='supplyPrice' value='"+result[j].supplyPrice+"'/><br>";  
	                     	   storeLevelListStr+="<input  type='hidden' name='levelId' value='"+result[j].storeLevelId+"'/>";
	                     	   var td = $(".gridtable tr[good-sku-id='"+result[j].goodSkuId+"']").find(".cargoSkuListaaaaa");
	                        	td.append(storeLevelListStr);
	                        	var countinput = td.find("input[name='count']");
	                        	countinput.val(parseInt(countinput.val())+1);
	                        	
	                        }
//	                        storeLevelListStr+="";

	        		          
	        	          });
	             });
            });
            
            
		     if(editValue.status!=1){
		    	 $("div[name='cargoSku']").on("click", '.input-checkbox', function(){
		            	var id=$(this).val();
		            	var check=$(this).is(':checked');
		            	var skuName=$(this).attr("cargoSkuNameList");
		            	console.log(id);
		            	console.log($(this).is(':checked'));
		            	if(check){
		            
		            		$.post(Portal.webRoot+'/StoreSupplyPrice/findSupplyPrice.do',{goodId:editValue.tradeGoodId},function(result){
		                 	   console.log(result);
		               		    var storeLevelListStr="";
		                        for(var j=0;j<result.length;j++){
		                     	   storeLevelListStr+="<br>"+result[j].storeName+" ";
		                     	   storeLevelListStr+="<input  name='supplyPrice' value='"+result[j].supplyPrice+"'/><br>";  
		                     	   storeLevelListStr+="<input  type='hidden' name='levelId' value='"+result[j].storeLevelId+"'/>";
		                        }
		                        storeLevelListStr+="<input  type='hidden' name='count' value='"+result.length+"'/>";
		                    	
		                		$(".gridtable").append("<tr id='tabletr"+id+"' data-id='"+id+"' data-name='"+skuName+"'><td><input type='hidden' name='cargoSkuId' readOnly='true' value='"+id+"' />"
		                 			   +"<input type='hidden' name='cargoSkuName' readOnly='true' value='"+skuName+"' />"+skuName+"</td><td>" +
		                 			   		"<input name='marketPrice' data-rule='required'/></td><td>" +
		                 			   				"<input name='salePrice' data-rule='required'/></td>"+
		                 			   			"<td class='cargoSkuListaaaaa'>"+storeLevelListStr+"</td></tr>");
		                		
		                		
		      	          });
		            		
		            	
		            	}else{
		            		$("#tabletr"+id).remove();
		            	}
		            });
		     }
 
	            $("form[name=good-form] select[name=category]").change(function(){
	            	var a=$("form[name=good-form] select[name=category] option[value="+$(this).val()+"]").attr("action");
	            	console.log($("form[name=good-form] select[name=category] option[value="+$(this).val()+"]").attr("action"));
	            	rule=a;
	            	if(a!=undefined && a!=null && a!="" && a!="null"){
	            		
	            		$(".beginTime").show();
	            		$(".endTime").show();
	            	}else{
	            		
	            		$(".beginTime").hide();
	            		$(".endTime").hide();
	            		
	            	}
	            });
	            
	            $.post(Portal.webRoot+'/carriageRuleController/getCarriageRuleList.do',function(result){
	          	   console.log(result);
	          	   var str="";
	                 for(var j=0;j<result.length;j++){
	                	 if(editValue!=null && editValue != undefined && result[j].id==editValue.postid){
	                		 str+="<option value='"+result[j].id+"' selected='selected'>"+result[j].templateName+"</option>";
	                	 }else{
	                		 str+="<option value='"+result[j].id+"'>"+result[j].templateName+"</option>";
	                	 }	                  
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
  	            var count=[];
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=good-form]').isValid();
                         var isValid = $('form[name=good-form]').isValid();
                         if (isValid) {
                        	 
                         $(".gridtable tr[id]").each(function(){ 
                          	 
                        	var  marketPrice=$(this).find("input[name=marketPrice]").val();
                           	var  salePrice=$(this).find("input[name=salePrice]").val();
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
                    	    console.log(cargoSkuNameList);
                    	    
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
                          	
                          	if(largeImgUploader.get().length>0){
                              	$("input[name=imgLarge]").val(largeImgUploader.get()[0].url);	
                          	}
                          	
                          	
                          	
                            var value = $('form[name=good-form]').form('value');
                            if(value.postid==''|| value.postid==null || value.postid==undefined){
                         	   club.toast('warn', '必须配置商品包邮规则!');
                         	   return ;
                            }	
                            if(rule!=undefined && rule!=null && rule!="" && rule!="null"){
                            	value.beginTimeStr=$("input[name=beginTime]").val();
                                value.endTimeStr=$("input[name=endTime]").val();
                            }
                            else{
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
                                           
        
                            $.post(Portal.webRoot+'/good/editGood.do',{modelJson:JSON.stringify(value),
                            	labelIds:JSON.stringify(labelIds),cargoSkuIdList:JSON.stringify(cargoSkuIdList),
                            	cargoSkuNameList:JSON.stringify(cargoSkuNameList),
                            	marketPriceList:JSON.stringify(marketPriceList),
                            	salePriceList:JSON.stringify(salePriceList),
                            	allPriceList:JSON.stringify(value.supplyPrice),
                            	count:JSON.stringify(count)
                            	},function(result){
                                if (result.success) {
                                    listView.callback.search();
                                    popup.close();
                                }
                                else{
                                    club.toast('error', result.msg);
                                }
                                
                            });
                         };
                         break;
                    case 'cancel-button': popup.close();break;

                }
            });
                      
		}
	};
});