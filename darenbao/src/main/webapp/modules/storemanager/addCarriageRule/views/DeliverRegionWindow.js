define([
	'text!modules/storemanager/addCarriageRule/templates/deliverRegionView.html',
	'data/cityData',
    'Portal'
], function(addViewTpl, cityData, Portal) {
	var options = {
		height:'80%',
        modal: false,
        draggable: true,
        content: addViewTpl,
        autoResizable: true
    };
    var popup;
    var par = {};
    
    var initPar = function(ids) {
		if(ids != undefined && ids != ""){
			var idArray = ids.split(",");
			for(var tmp in idArray){
				par["ID_"+idArray[tmp]] = idArray[tmp];
			}
		}
	}
    
    var addParam = function(id, attr){
		var param = {};
		param["id"] = id;
		if(attr == "province"){
    		param["first"] = id;
    		param["second"] = "";
    	} else if(attr == "city"){
    		param["first"] = cityData.getParentId(id);
    		param["second"] = id;
    	} else{
    		var layerId = cityData.getParentId(id);
    		param["first"] = cityData.getParentId(layerId);
    		param["second"] = layerId;
    	}
		par["ID_"+id] = param;
	}
    
    var updateParam = function(id, attr, flag){
    	if(attr == "province"){
    		if(flag == "add"){
    			par["ID_"+id] = id;
    			$("input[name=cityChk"+id+"]").prop('checked',true);
    		} else{
    			delete par["ID_"+id];
    			$("input[name=cityChk"+id+"]").prop('checked',false);
    		}
			var firstChildren = cityData.get(id).children;
			if(typeof(firstChildren) != "undefined"){
				for(var i=0; i<firstChildren.length; i++){
					var tmp = firstChildren[i];
					if(flag == "add"){
						par["ID_"+tmp.id] = tmp.id;
//						addParam(tmp.id, "city");
					} else{
						delete par["ID_" + tmp.id];
					}
					var secnodChildren = tmp.children;
					if(typeof(secnodChildren) != "undefined"){
						for(var j=0; j<secnodChildren.length; j++){
							var secnodTmp = secnodChildren[j];
							if(flag == "add"){
								par["ID_"+secnodTmp.id] = secnodTmp.id;
//								addParam(secnodTmp.id, "region");
							} else{
								delete par["ID_" + secnodTmp.id];
							}
						}
					}
				}
			}
		} else if(attr == "city"){
			if(flag == "add"){
				par["ID_"+id] = id;
    			$("input[name=regionChk"+id+"]").prop('checked',true);
    			var parentId = cityData.getParentId(id);
    			par["ID_"+parentId] = parentId;
    			$("input[name=provinceChk"+parentId+"]").prop('checked',true);
    		} else{
    			delete par["ID_"+id];
    			$("input[name=regionChk"+id+"]").prop('checked',false);
    			var parentId = cityData.getParentId(id);
    			var checked = $("input[name=cityChk"+parentId+"]").is(':checked');
    			if(!checked){
    				delete par["ID_" + parentId];
    				$("input[name=provinceChk"+parentId+"]").prop('checked',false);    				
    			}
    		}
			var firstChildren = cityData.get(id).children;
			if(typeof(firstChildren) != "undefined"){
				for(var i=0; i<firstChildren.length; i++){
					var tmp = firstChildren[i];
					if(flag == "add"){
						par["ID_"+tmp.id] = tmp.id;
//						addParam(tmp.id, "region");				
					} else{
						delete par["ID_" + tmp.id];
					}
				}
			}
		} else{
			if(flag == "add"){
				par["ID_"+id] = id;
//				addParam(id, "region");
				var parentId = cityData.getParentId(id);
				par["ID_"+parentId] = parentId;
				$("input[id="+parentId+"]").prop('checked',true);
				var upparentId = cityData.getParentId(parentId);
				par["ID_"+upparentId] = upparentId;
    			$("input[name=provinceChk"+upparentId+"]").prop('checked',true);
			} else{
				delete par["ID_"+id];
				var parentId = cityData.getParentId(id);
    			var checked = $("input[name=regionChk"+parentId+"]").is(':checked');
    			if(!checked){
    				delete par["ID_"+parentId];
    				$("input[id="+parentId+"]").prop('checked',false);    				
    			}
    			var upparentId = cityData.getParentId(parentId);
    			var upchecked = $("input[name=cityChk"+upparentId+"]").is(':checked');
    			if(!upchecked){
    				delete par["ID_"+upparentId];
    				$("input[name=provinceChk"+upparentId+"]").prop('checked',false);    				
    			}
			}
		}
    }
    
	return {
		openAddWin:function(listView,addViewTpl,action,editValue){
            popup = club.popup($.extend({ }, options, {
                modal: true
            }))
            par = {};
            var td = $(editValue.find("td")[3]);
            //初始化已选中地址
            initPar(td.find("input").val());
            var cityAll = cityData.getAll();
            for(var i=0; i<cityAll.length; i++){
            	var option = "";
            	var checkIs = par["ID_"+cityAll[i].id] != undefined ? true : false;
            	option = '<div style="margin: 0px 0px 2px 0px;">'+
            	'<input class="checkbox-inline" attr="province" type="checkbox" '+(checkIs?"checked":"")+' name="provinceChk'+cityAll[i].id+'" id="'+cityAll[i].id+'"/>'+
            	'<button type="button" attr="province" class="btn btn-default" style="width:90%" id="'+cityAll[i].id+'">'+cityAll[i].name+'</button></div>';
            	$('.province').append(option);
            }
            
            $(".deliverRegionWindow").on("click",".btn",function(){
            	var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                    	var ids = "";
                    	var names = "";
                    	for(var tmp in par){
                			var obj = par[tmp];
                			ids = ids + obj + ",";
                		}
                    	if(ids.length > 0){
                    		ids = ids.substr(0,ids.length-1);
                    		names = cityData.getProvinceName(ids);
                    	}
//                    	console.log(names);
                    	var param = {};
                    	param["ids"] = ids;
                    	param["names"] = names;
                    	addViewTpl.getAddress(param,editValue);
                    	popup.close();
                        break;
                    case 'cancel-button': popup.close();break;
                }
                if(btnAction == undefined){
                	var id = $(this).attr('id');
                	var attr = $(this).attr('attr');
                	if(attr == "province"){
                		$('.city').html("");
                		$('.region').html("");
                	} else if(attr == "city"){
                		$('.region').html("");
                	}
                	
                	var children = cityData.get(id).children;            	
                	if(typeof(children) != "undefined"){
                		for(var i=0; i<children.length; i++){
                			var option = "";
                			var checkIs = par["ID_"+children[i].id] != undefined ? true : false;
                			if(attr == "province"){
                				option = '<div style="margin: 0px 0px 2px 0px;">'+
                				'<input class="checkbox-inline" attr="city" type="checkbox" '+(checkIs?"checked":"")+' name="cityChk'+id+'" id="'+children[i].id+'"/>'+
                            	'<button type="button" attr="city" class="btn btn-default" style="width:90%" id="'+children[i].id+'">'+children[i].name+'</button></div>';
                				$('.city').append(option);
                			} else if(attr == "city"){
                				option = '<div style="margin: 0px 0px 2px 0px;">'+
                				'<input class="checkbox-inline" attr="region" type="checkbox" '+(checkIs?"checked":"")+'  name="regionChk'+id+'" id="'+children[i].id+'"/>'+
                            	'<button type="button" attr="region" class="btn btn-default" style="width:90%" id="'+children[i].id+'">'+children[i].name+'</button></div>';
                				$('.region').append(option);
                			}
                		}
                	}
                }              
        	});
            
            $(".deliverRegionWindow").on("click",".checkbox-inline",function(){
            	var id = $(this).attr('id');
            	var attr = $(this).attr('attr');
            	var checked = $("input[id="+id+"]").is(':checked');
            	if(checked){
            		updateParam(id, attr, "add");
                	console.log(par);
            	} else{
            		updateParam(id, attr, "del");
            		console.log(par);
            	}
            });
		}
	};
});