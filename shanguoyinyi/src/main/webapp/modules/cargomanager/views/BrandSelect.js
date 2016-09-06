define(['Portal'], function(Portal) {
	var BrandSelect = function(){
		var _brand;
		var btnSelector = "btn-brand-value";
		var _readOnly;
		
		var _init = function(brand, btnType, hasAll){
			btnType = btnType || "default";
	    	if(hasAll !== false)
	    		hasAll = true;
	    	if(typeof brand == "string")
	    		brand = $(brand);
	    	_brand = brand;
	    	var btn = $("<button type='button' class='btn btn-"+btnType+" dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></button>");
	    	brand.append(btn);
	    	var btnValue = $("<span class='"+btnSelector+"' data=''>选择品牌</span>");
	    	btn.append(btnValue);
	    	var filter = $("<ul class='dropdown-menu'></ul>");
	    	brand.append(filter);
	        $.post(Portal.webRoot+'/cargoBrand/findListAll.do', function(data){
	        	filter.html("");
	    		if(hasAll)
	    			filter.append("<li><a href='javascript:void(0);' data=''>选择品牌</a></li>");
	        	if(data)
	        		for(var i=0; i<data.length; i++)
	        			filter.append("<li><a href='javascript:void(0);' data='"+data[i]["id"]+"'>"+data[i]["name"]+"</a></li>");
	        });
	        brand.on("click", "ul > li > a", function(){
	        	brand.find('.'+btnSelector).attr('data', $(this).attr('data')).html($(this).html());
	        });
		};
		
		var _getSelectedValue = function(){
	        if(_brand)
	        	return _brand.find('.'+btnSelector).attr('data');
		}
		
		var _setValue = function(value, name){
			var span = _brand.find('.'+btnSelector);
			if(!value || !name)
				span.attr('data', "").html("选择品牌");
			else
				span.attr('data', value).html(name);
			if(_readOnly) {
				var btn = span.parent();
				btn.attr("data-toggle", "readonly");
    			btn.parent().siblings("a").addClass("hidden");
    			if(span.attr('data')=="")
    				span.attr('data', "").html("无品牌");
			}
		}
		
		return {
	        init: function(brand, btnType, hasAll){
	        	_init(brand, btnType, hasAll);
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
		init: function(brand, btnType, hasAll){
			var select = new BrandSelect();
			select.init(brand, btnType, hasAll);
			return select;
		}
	}
});