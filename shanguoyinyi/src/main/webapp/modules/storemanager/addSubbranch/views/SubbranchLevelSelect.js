define(['Portal'], function(Portal) {
	
	var _brand;
	var btnSelector = "btn-subbranchLevel-value";
	
	var _init = function(brand, btnType, hasAll){
		
		btnType = btnType || "default";
    	if(hasAll !== false)
    		hasAll = true;
    	if(typeof brand == "string")
    		brand = $(brand);
    	
    	_brand = brand;
    	var btn = $("<button type='button' class='btn btn-"+btnType+" dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></button>");
    	brand.append(btn);
    	var btnValue = $("<span class='"+btnSelector+"' data=''>店铺等级</span>");
    	btn.append(btnValue);
    	var filter = $("<ul class='dropdown-menu'></ul>");
    	brand.append(filter);
        $.post(Portal.webRoot+'/store/level/findAllStoreLevel.do', function(data){
        	filter.html("");
    		if(hasAll)
    			filter.append("<li><a href='javascript:void(0);' data=''>所有品牌</a></li>");
        	if(data)
        		for(var i=0; i<data.length; i++)
        			filter.append("<li><a href='javascript:void(0);' name='levelId' data='"+data[i]["levelId"]+"'>"+data[i]["name"]+"</a></li>");
        });
        brand.on("click", "ul > li > a", function(){
        	
        	brand.find('.'+btnSelector).attr('data', $(this).attr('data')).html($(this).html());
        	
        });
	};
	
     var _initset = function(brand, btnType, hasAll,levelId){
		
		btnType = btnType || "default";
    	if(hasAll !== false)
    		hasAll = true;
    	if(typeof brand == "string")
    		brand = $(brand);
    	
    	_brand = brand;
    	var btn = $("<button type='button' class='btn btn-"+btnType+" dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></button>");
    	brand.append(btn);
    	var btnValue = $("<span class='"+btnSelector+"' data=''>店铺等级</span>");
    	var filter = $("<ul class='dropdown-menu'></ul>");
    	brand.append(filter);
        $.post(Portal.webRoot+'/store/level/findAllStoreLevel.do', function(data){
        	filter.html("");
        	if(data){
        	for(var i=0; i<data.length; i++)
        			filter.append("<li><a href='javascript:void(0);' name='levelId' data='"+data[i]["levelId"]+"'>"+data[i]["name"]+"</a></li>");}
        	for(var i=0; i<data.length; i++){
        		if(data[i]["levelId"]==levelId && levelId!=null){
        			btnValue = $("<span class='"+btnSelector+"' data='"+data[i]["levelId"]+"'>"+data[i]["name"]+"</span>");
        	    	
        			brand.find('.'+btnSelector).attr('data', levelId).html($(this).html());
        		}
        	}
        	btn.append(btnValue);
        });
        brand.on("click", "ul > li > a", function(){
        	
        	brand.find('.'+btnSelector).attr('data', $(this).attr('data')).html($(this).html());
        	
        });
	};
	
	var _getSelectedValue = function(){
        if(_brand)
        	return _brand.find('.'+btnSelector).attr('data');
	}
	
	return {
        init: function(brand, btnType, hasAll){
        	_init(brand, btnType, hasAll);
        },
        get: function(){
            return _getSelectedValue();
        },
        initset: function(brand, btnType, hasAll,levelId){
            return _initset(brand, btnType, hasAll,levelId);
        }
	}
});