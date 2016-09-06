define([
	'text!modules/suppliermanager/addsupplierinfo/templates/baseSkuItem.html',
	'modules/upload/Uploader',
    'Portal',
], function(addViewTpl,uploader,Portal) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true
    };
    var popup;

	return {
		openAddWin:function(listView,action,editValue,result){
			popup = club.popup($.extend({}, options, {
                modal: true
            }));
			var optionimage ={
            	container: "logo_img_container", 
            	max_file_count: 1
            };
            if(action=='edit'){
               if (editValue.logo) {
            	   optionimage.image_list=[{id:editValue.id, url: editValue.logo}];
               }
            }
            var brandLogoUploader = uploader.create(optionimage);
			$('input[name=baseSkuTypeId').val(editValue.id);
			$('input[name=baseSkuTypeId').attr('disabled',true);
			$('input[name=type').val(editValue.type);
			$('input[name=typeName').val(editValue.typeName);
			$('input[name=name').val(editValue.name);
			if(editValue.type == "1"){
				$('input[name=skuName]').css('display','none');
			}
			if(editValue.type != "3"){
				$('.baseSkuItem').css('display','none');
			} else{
				$('input[name=skuValue]').css('display','none');
			}
			var pageParam = {"baseSkuTypeId":$('input[name=baseSkuTypeId').val(),"type":$('input[name=type').val(),"name":$('input[name=name').val()};
			refresh(result);	//对页面赋值
			
			$("#baseSkuItem-info-content-div").on('click',".del-button", function(){
				var id = $(this).attr("id");
				var baseSkuTypeId = $('input[name=baseSkuTypeId').val();
				var param = {"id":id, "baseSkuTypeId":baseSkuTypeId};
				//删除选中项
				var t = club.confirm('您确定要删除所选规格项吗？');
	            t.result.then(function resolve(){
					$.post(Portal.webRoot+'/cargoBaseSkuItemController/deleteSkuItemByBaseSkuTypeId.do',{"modelJson":JSON.stringify(param)},function(result){
						if(result.success){
							$("#"+id+"").remove();	//去掉页面中的选中项
						} else{
							return club.toast('info', '删除失败！');
						}
					});
	            }, function reject(){
		    		 return;
		    	});
			})
            
            $('.baseSkuItemWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                    	 var param = {};
                    	 var logo;
                     	 if(brandLogoUploader.get().length>0){
                     		 logo=brandLogoUploader.get()[0].url;                     		 
                     	 }
                     	 $("input[name=logo]").val(logo);
                		 var skuName = $('input[name=skuName]').val(); 
                         var value = $('input[name=skuValue]').val();
                         var type = $('input[name=type]').val();
                         if(type == '3'){
                        	 value = $('.baseSkuItemWindow input[name=logo]').val();
                         }
                         var baseSkuTypeId = $('input[name=baseSkuTypeId]').val();
                         param = {"value":value, "baseSkuTypeId":baseSkuTypeId, "code":type, "name":skuName};
                         if (value != "") {
                        	 if(type != '1'){
                        		 if(skuName == ""){
                        			 return club.toast('info', '规格名称不能为空！');
                        		 }
                        	 }
                            $.post(Portal.webRoot+'/cargoBaseSkuItemController/addCargoBaseSkuItem.do',{"modelJson":JSON.stringify(param)},function(result){
                            	if(result){
                            		appendData(result);	//追加新增资料到页面
                            		$('input[name=skuValue]').val("");
                            		$('input[name=skuName]').val(""); 
            					} else{
            						return club.toast('info', '新增失败！');
            					}
                            });
                         } else if(value == ""){
                        	 if(type == '3'){
                        		 return club.toast('info', '规格项的图片不能为空！');
                        	 } else{
                        		 return club.toast('info', '规格项不能为空！');                        		 
                        	 }
                         };
                         break;
                    case 'cancel-button': popup.close();break;
                }
            });
		},
	};
	function refresh(result){
		var state = $('input[name=type').val();
		if(state == "1"){
			for(i=0;i<result.length;i++){
				var delBtn = '<span id="'+result[i].id+'">'+result[i].value+'<button class="del-button" type="button" id="'+ result[i].id +'" title="删除规格" ><span class="glyphicon glyphicon-trash"></span></button></span>';
				$("#baseSkuItem-info-content-div").append(delBtn);				
			}
		} else if(state == "2"){
			for(i=0;i<result.length;i++){
				var delBtn = '<span id="'+result[i].id+'">'+result[i].name+':'+result[i].value+'<button class="del-button" type="button" id="'+ result[i].id +'" title="删除规格" ><span class="glyphicon glyphicon-trash"></span></button></span>;';
				$("#baseSkuItem-info-content-div").append(delBtn);				
			}
		} else if(state == "3"){
			for(i=0;i<result.length;i++){
				var imagUrl = "./image/user4-128x128.jpg";
                if (result[i].value) {
                    imagUrl = result[i].value;
                };
                var img = '<img src = "'+imagUrl+'" class="client-face-img"/> ';
				
				var delBtn = '<span id="'+result[i].id+'">'+result[i].name+':'+img+'<button class="del-button" type="button" id="'+ result[i].id +'" title="删除规格" ><span class="glyphicon glyphicon-trash"></span></button></span>';
				$("#baseSkuItem-info-content-div").append(delBtn);			
			}
		}
	};
	
	function appendData(result){
		var state = $('input[name=type').val();
		if(state == "1"){
			var delBtn = '<span id="'+result.id+'">'+result.value+'<button class="del-button" type="button" id="'+ result.id +'" title="删除规格" ><span class="glyphicon glyphicon-trash"></span></button></span>';
			$("#baseSkuItem-info-content-div").append(delBtn);				
		} else if(state == "2"){
			var delBtn = '<span id="'+result.id+'">'+result.name+':'+result.value+'<button class="del-button" type="button" id="'+ result.id +'" title="删除规格" ><span class="glyphicon glyphicon-trash"></span></button></span>';
			$("#baseSkuItem-info-content-div").append(delBtn);				
		} else if(state == "3"){
			var imagUrl = "./image/user4-128x128.jpg";
            if (result.value) {
                imagUrl = result.value;
            };
            var img = '<img src = "'+imagUrl+'" class="client-face-img"/> ';
			
			var delBtn = '<span id="'+result.id+'">'+result.name+':'+img+'<button class="del-button" type="button" id="'+ result.id +'" title="删除规格" ><span class="glyphicon glyphicon-trash"></span></button></span>';
			$("#baseSkuItem-info-content-div").append(delBtn);			
		}
	};
});