define([
	'text!modules/cargomanager/templates/CargoSkuTypeWindow.html',
    'Portal',
], function(cargoSkuTypeWindow, Portal) {
	var options = {
		width: $(window).width()*0.5,
        height: $(window).height()*0.6,
        modal: true,
        draggable: false,
        content: cargoSkuTypeWindow,
        autoResizable: true
    };
    var popup;
    var popWin = {};
    var totalType = {};
    
    var _open = function(id, selected, callback, type){
    	_load_resources(id, selected, type);
    }
    
    var _init = function(id, callback){
		popup = club.popup(options);
    	popWin.main = $(".cargo-sku-type-window");
    	popWin.title = $(".cargo-info-window > .modal-header > .modal-title");
    	popWin.body =$(".cargo-sku-type-window .modal-body .panel-body");
    	popWin.main.on("click", ".modal-footer button.btn-cancel", function(){
    		popup.close();
    	});
    	popWin.main.on("click", ".modal-footer button.btn-save", function(){
    		var result = {};
    		var list = [];
    		result.parent = id;
    		result.list = list;
    		popWin.main.find("input[type=checkbox]:checked").each(function(){
    			var cb = $(this);
    			list.push({
    				id: cb.attr("data-id"), 
    				value: cb.attr("data-value"), 
    				name: cb.attr("data-name"), 
    				type: cb.attr("data-type")
    			});
    		});
    		if(callback)
    			callback(result);
    		popup.close();
    	});
    }
    
    var _load_sku_type_resource = function(cargoId, selected){
    	$.post(Portal.webRoot+'/cargoBaseSkuTypeController/selectCargoBaseSkuTypeList.do', function(data){
    		popWin.body.html("");
    		if(data && data.length>0) {
    			$.each(data, function(i, obj){
    				if(!totalType[obj.id+""])
    					totalType[obj.id+""] = obj;
    				var label = $("<label class='checkbox-inline'></label>");
    				popWin.body.append(label);
    				var cb = $("<input type='checkbox' class='check-box cargo-sku-type' data-id='"+obj.id+"' data-name='"+obj.name+"' data-type='"+obj.type+"' />");
    				label.append(cb).append(obj.name);
    				if(selected[obj.id+""])
    					cb.attr("checked", "checked");
    			});
    		}
    	});
    }
    
    var _load_sku_item_resource = function(skuTypeId, skuTypeValue, selected){
    	$.post(Portal.webRoot+'/cargoBaseSkuItemController/selectSkuItemBySkuTypeId.do', 
    			{id: skuTypeId, type: skuTypeValue}, function(data){
    		popWin.body.html("");
    		if(data && data.length>0) {
    			$.each(data, function(i, obj){
    				if(!totalType[obj.id+""])
    					totalType[obj.id+""] = obj;
    				var label = $("<label class='checkbox-inline'></label>");
    				popWin.body.append(label);
    				var cb = $("<input type='checkbox' class='check-box cargo-sku-type' data-id='"+obj.id+"' data-value='"+obj.value+"' data-name='"+obj.name+"' />");
    				label.append(cb).append(obj.name);
    				if(selected[obj.id+""])
    					cb.attr("checked", "checked");
    			});
    		}
    	});
    }
    
	return {
		openSkuType: function(cargoId, selected, callback){
	    	_init(cargoId, callback);
	    	_load_sku_type_resource(cargoId || 0, selected|| {});
		}, 
		openSkuItem: function(skuTypeId, skuTypeValue, selected, callback){
	    	_init(skuTypeId, callback);
	    	_load_sku_item_resource(skuTypeId || 0, skuTypeValue || 0, selected|| {});
		}
	};
});