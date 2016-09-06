define(['Portal'], function(Portal) {
	var SupplierSelect = function(){
		var _supplier;
		var btnSelector = "btn-supplier-value";
		var _readOnly;
		
		var _init = function(supplier, btnType, hasAll){
			btnType = btnType || "default";
	    	if(hasAll !== false)
	    		hasAll = true;
	    	if(typeof supplier == "string")
	    		supplier = $(supplier);
	    	_supplier = supplier;
	    	var btn = $("<button type='button' class='btn btn-"+btnType+" dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></button>");
	    	supplier.append(btn);
	    	var btnValue = $("<span class='"+btnSelector+"' data=''>选择供应商</span>");
	    	btn.append(btnValue);
	    	var filter = $("<ul class='dropdown-menu'></ul>");
	    	supplier.append(filter);
	        $.post(Portal.webRoot+'/cargoSupplier/findListAll.do', function(data){
	        	filter.html("");
	    		if(hasAll)
	    			filter.append("<li><a href='javascript:void(0);' data=''>选择供应商</a></li>");
	        	if(data)
	        		for(var i=0; i<data.length; i++)
	        			filter.append("<li><a href='javascript:void(0);' data='"+data[i]["id"]+"'>"+data[i]["name"]+"</a></li>");
	        });
	        supplier.on("click", "ul > li > a", function(){
	        	supplier.find('.'+btnSelector).attr('data', $(this).attr('data')).html($(this).html());
	        });
		};
		
		var _getSelectedValue = function(){
	        if(_supplier)
	        	return _supplier.find('.'+btnSelector).attr('data');
		}
		
		var _setValue = function(value, name){
			var span = _supplier.find('.'+btnSelector);
			if(!value || !name)
				span.attr('data', "").html("选择供应商");
			else
				span.attr('data', value).html(name);
			if(_readOnly) {
				var btn = span.parent();
				btn.attr("data-toggle", "readonly");
    			btn.parent().siblings("a").addClass("hidden");
    			if(span.attr('data')=="")
    				span.attr('data', "").html("无供应商");
			}
		}
		
		return {
	        init: function(supplier, btnType, hasAll){
	        	_init(supplier, btnType, hasAll);
	        },
	        get: function(){
	            return _getSelectedValue();
	        }, 
	        set: function(value, name, readOnly){
	        	_readOnly = readOnly===true? true: false;
	        	_setValue(value, name);
	        }
		}
	}
	return {
		init: function(supplier, btnType, hasAll){
			var select = new SupplierSelect();
			select.init(supplier, btnType, hasAll);
			return select;
		}
	}
});